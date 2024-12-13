/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.common.blockentity;


import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.blockentity.IStorageAccessor;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.blockentity.IStorageControllerProxy;
import com.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.block.storage.StorageStabilizerBlock;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.misc.DepositOrder;
import com.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.klikli_dev.occultism.common.misc.StorageControllerMapItemStackHandler;
import com.klikli_dev.occultism.network.messages.MessageUpdateStacks;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.EntityUtil;
import com.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StorageControllerBlockEntity extends NetworkedBlockEntity implements MenuProvider, IStorageController, IStorageAccessor, IStorageControllerProxy, GeoBlockEntity {

    public static final int MAX_STABILIZER_DISTANCE = 5;

    protected static final List<DeferredBlock<? extends Block>> BLOCK_BLACKLIST = Stream.of(
            OccultismBlocks.STORAGE_CONTROLLER, OccultismBlocks.STORAGE_CONTROLLER_STABILIZED).collect(Collectors.toList());
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    public Map<Integer, ItemStack> matrix = new HashMap<>();
    public ItemStack orderStack = ItemStack.EMPTY;
    public Map<GlobalBlockPos, MachineReference> linkedMachines = new HashMap<>();
    public Map<GlobalBlockPos, UUID> depositOrderSpirits = new HashMap<>();
    public StorageControllerMapItemStackHandler itemStackHandler = new StorageControllerMapItemStackHandler(this,
            Occultism.SERVER_CONFIG.storage.controllerMaxItemTypes.get(),
            Occultism.SERVER_CONFIG.storage.controllerMaxTotalItemCount.get()
    );
    protected SortDirection sortDirection = SortDirection.DOWN;
    protected SortType sortType = SortType.AMOUNT;
    protected int maxItemTypes = Occultism.SERVER_CONFIG.storage.controllerMaxItemTypes.get();
    protected int usedItemTypes = 0;
    protected long maxTotalItemCount = Occultism.SERVER_CONFIG.storage.controllerMaxTotalItemCount.get();
    protected long usedTotalItemCount = 0;

    protected boolean stabilizersInitialized = false;
    protected GlobalBlockPos globalPos;
    protected MessageUpdateStacks cachedMessageUpdateStacks;

    public StorageControllerBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.STORAGE_CONTROLLER.get(), worldPos, state);
    }

    public void tick() {
        if (!this.level.isClientSide) {
            if (!this.stabilizersInitialized) {
                this.stabilizersInitialized = true;
                this.updateStabilizers();
            }
        }
    }

    public void updateStabilizers() {
        int additionalMaxItemTypes = 0;
        long additionalTotalItemCount = 0;
        List<BlockPos> stabilizerLocations = this.findValidStabilizers();
        if (this.getBlockState().getBlock().equals(OccultismBlocks.STORAGE_CONTROLLER.get())) {
            for (BlockPos pos : stabilizerLocations) {
                additionalMaxItemTypes += this.getAdditionalMaxItemTypesForStabilizer(this.level.getBlockState(pos));
                additionalTotalItemCount += this.getAdditionalMaxTotalItemCountForStabilizer(this.level.getBlockState(pos));
            }
            this.setStorageLimits(Occultism.SERVER_CONFIG.storage.controllerMaxItemTypes.get() + additionalMaxItemTypes,
                    Occultism.SERVER_CONFIG.storage.controllerMaxTotalItemCount.get() + additionalTotalItemCount);
        } else {
            this.setStorageLimits(Occultism.SERVER_CONFIG.storage.controllerMaxItemTypes.get() + Occultism.SERVER_CONFIG.storage.stabilizedControllerStabilizers.get() * Occultism.SERVER_CONFIG.storage.stabilizerTier4AdditionalMaxItemTypes.get(),
                    Occultism.SERVER_CONFIG.storage.controllerMaxTotalItemCount.get() + Occultism.SERVER_CONFIG.storage.stabilizedControllerStabilizers.get() * Occultism.SERVER_CONFIG.storage.stabilizerTier4AdditionalMaxTotalItemCount.get());
        }
    }

    public List<BlockPos> findValidStabilizers() {
        ArrayList<BlockPos> validStabilizers = new ArrayList<>();

        BlockPos up = this.getBlockPos().above();
        for (Direction face : Direction.values()) {
            BlockPos hit = Math3DUtil.simpleTrace(up, face, MAX_STABILIZER_DISTANCE, (pos) -> {
                BlockState state = this.level.getBlockState(pos);
                return state.getBlock() instanceof StorageStabilizerBlock;
            });

            if (hit != null) {
                BlockState state = this.level.getBlockState(hit);
                if (state.getValue(DirectionalBlock.FACING) == face.getOpposite()) {
                    validStabilizers.add(hit);
                }
            }
        }
        return validStabilizers;
    }

    protected int getAdditionalMaxItemTypesForStabilizer(BlockState state) {
        Block block = state.getBlock();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier1AdditionalMaxItemTypes.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier2AdditionalMaxItemTypes.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier3AdditionalMaxItemTypes.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier4AdditionalMaxItemTypes.get();
        return 0;
    }

    protected long getAdditionalMaxTotalItemCountForStabilizer(BlockState state) {
        Block block = state.getBlock();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier1AdditionalMaxTotalItemCount.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier2AdditionalMaxTotalItemCount.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier3AdditionalMaxTotalItemCount.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier4AdditionalMaxTotalItemCount.get();
        return 0;
    }

    protected void validateLinkedMachines() {
        // remove all entries that lead to invalid block entities.
        this.linkedMachines.entrySet().removeIf(entry -> !entry.getValue().isValidFor(this.level));
    }

    private List<Predicate<ItemStack>> getComparatorsSortedByAmount(Predicate<ItemStack> comparator) {
        var handler = this.itemStackHandler;
        var map = new HashMap<Item, Integer>();
        for (int i = 0; i < handler.getSlots(); i++) {
            var getStackInSlot = handler.getStackInSlot(i);
            if (comparator.test(getStackInSlot)) {
                var oldCount = map.getOrDefault(getStackInSlot.getItem(), 0);
                map.put(getStackInSlot.getItem(), oldCount + getStackInSlot.getCount());
            }
        }
        return map.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(entry -> (Predicate<ItemStack>) stack -> stack.getItem() == entry.getKey()).toList();
    }

    private <E extends GeoBlockEntity> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin()
                .thenLoop("animation.dimensional_matrix.new"));
        return PlayState.CONTINUE;
    }

    public static CompoundTag saveMatrix(Map<Integer, ItemStack> matrix, HolderLookup.Provider provider) {
        CompoundTag matrixCompound = new CompoundTag();
        ListTag matrixNbt = new ListTag();
        for (int i = 0; i < 9; i++) {
            if (matrix.get(i) != null && !matrix.get(i).isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stackTag.putByte("slot", (byte) i);
                stackTag.put("stack", matrix.get(i).save(provider));
                matrixNbt.add(stackTag);
            }
        }
        matrixCompound.put("matrix", matrixNbt);
        return matrixCompound;
    }

    public static Map<Integer, ItemStack> loadMatrix(CompoundTag matrixCompound, HolderLookup.Provider provider) {
        Map<Integer, ItemStack> matrix = new HashMap<>();
        if (matrixCompound.contains("matrix")) {
            ListTag matrixNbt = matrixCompound.getList("matrix", Tag.TAG_COMPOUND);
            for (int i = 0; i < matrixNbt.size(); i++) {
                CompoundTag stackTag = matrixNbt.getCompound(i);
                int slot = stackTag.getByte("slot");
                ItemStack s = ItemStack.parseOptional(provider, stackTag.getCompound("stack"));
                matrix.put(slot, s);
            }
        }
        return matrix;
    }


    @Override
    public Component getDisplayName() {
        return Component.literal(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType()).getPath());
    }

    @Override
    public IStorageController getLinkedStorageController() {
        return this;
    }

    @Override
    public GlobalBlockPos getLinkedStorageControllerPosition() {
        if (this.globalPos == null)
            this.globalPos = new GlobalBlockPos(this.getBlockPos(), this.level);
        return this.globalPos;
    }

    @Override
    public void setLinkedStorageControllerPosition(GlobalBlockPos blockPos) {
        //Do nothing, block entity cannot move.
    }

    @Override
    public Map<Integer, ItemStack> getMatrix() {
        return this.matrix;
    }

    @Override
    public ItemStack getOrderStack() {
        return this.orderStack;
    }

    @Override
    public void setOrderStack(@Nonnull ItemStack stack) {
        this.orderStack = stack;
    }

    @Override
    public SortDirection getSortDirection() {
        return this.sortDirection;
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public SortType getSortType() {
        return this.sortType;
    }

    @Override
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public List<ItemStack> getStacks() {

        List<ItemStack> result = new ArrayList<>(this.itemStackHandler.getSlots());
        for (var entry : this.itemStackHandler.keyToCountMap().object2IntEntrySet()) {
            result.add(entry.getKey().stack().copyWithCount(entry.getIntValue()));
        }

        this.usedItemTypes = this.itemStackHandler.keyToCountMap().size();
        this.usedTotalItemCount = this.itemStackHandler.totalItemCount();
        return result;
    }

    @Override
    public MessageUpdateStacks getMessageUpdateStacks() {
        if (this.cachedMessageUpdateStacks == null) {
            List<ItemStack> stacks = this.getStacks();
            this.cachedMessageUpdateStacks = new MessageUpdateStacks(stacks, this.maxItemTypes, this.usedItemTypes,
                    this.maxTotalItemCount, this.usedTotalItemCount, this.level.registryAccess());
        }
        return this.cachedMessageUpdateStacks;
    }

    @Override
    public int getMaxItemTypes() {
        return this.maxItemTypes;
    }

    @Override
    public void setStorageLimits(int maxItemTypes, long maxTotalItemCount) {
        this.maxItemTypes = maxItemTypes;
        this.maxTotalItemCount = maxTotalItemCount;
        this.itemStackHandler.maxItemTypes(this.maxItemTypes);
        this.itemStackHandler.maxTotalItemCount(this.maxTotalItemCount);
        //force resync
        this.cachedMessageUpdateStacks = null;
        this.markNetworkDirty();
    }

    @Override
    public int getUsedItemTypes() {
        return this.usedItemTypes;
    }

    @Override
    public Map<GlobalBlockPos, MachineReference> getLinkedMachines() {
        return this.linkedMachines;
    }

    @Override
    public void setLinkedMachines(Map<GlobalBlockPos, MachineReference> machines) {
        this.linkedMachines = machines;
    }

    @Override
    public void linkMachine(MachineReference machine) {
        this.linkedMachines.put(machine.insertGlobalPos, machine);
    }

    @Override
    public void addDepositOrder(GlobalBlockPos linkedMachinePosition, IItemStackComparator comparator, int amount) {
        //check if the item is available in the desired amount, otherwise kill the order.
        ItemStack stack = this.getItemStack(comparator, amount, true);
        if (!stack.isEmpty()) {
            UUID spiritUUID = this.depositOrderSpirits.get(linkedMachinePosition);
            if (spiritUUID != null) {
                EntityUtil.getEntityByUuiDGlobal(this.level.getServer(),
                                spiritUUID).filter(SpiritEntity.class::isInstance).map(SpiritEntity.class::cast)
                        .ifPresent(spirit -> {
                            Optional<ManageMachineJob> job = spirit.getJob().filter(ManageMachineJob.class::isInstance)
                                    .map(ManageMachineJob.class::cast);
                            if (job.isPresent()) {
                                job.get().addDepsitOrder(new DepositOrder((ItemStackComparator) comparator, amount));
                            } else {
                                this.removeDepositOrderSpirit(linkedMachinePosition);
                            }
                        });
            } else {
                //if the entity cannot be found, remove it from the list for now. it will re-register itself on spawn
                this.removeDepositOrderSpirit(linkedMachinePosition);
            }
        }
    }

    @Override
    public void addDepositOrderSpirit(GlobalBlockPos linkedMachinePosition, UUID spiritId) {
        this.depositOrderSpirits.put(linkedMachinePosition, spiritId);
    }

    @Override
    public void removeDepositOrderSpirit(GlobalBlockPos linkedMachinePosition) {
        this.linkedMachines.remove(linkedMachinePosition);
        this.depositOrderSpirits.remove(linkedMachinePosition);
    }

    @Override
    public boolean isBlacklisted(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem itemBlock) {
            return BLOCK_BLACKLIST.stream().map(DeferredBlock::get).anyMatch(block -> itemBlock.getBlock() == block);
        }

        return stack.getItem() == OccultismItems.STORAGE_REMOTE.get();
    }

    @Override
    public int insertStack(ItemStack stack, boolean simulate) {
        if (this.isBlacklisted(stack))
            return stack.getCount();

        if (this.itemStackHandler.insertItem(stack, true).getCount() < stack.getCount()) {
            stack = this.itemStackHandler.insertItem(stack, simulate);
        }

        return stack.getCount();
    }

    @Override
    public ItemStack getOneOfMostCommonItem(Predicate<ItemStack> comparator, boolean simulate) {
        if (comparator == null) {
            return ItemStack.EMPTY;
        }

        var comparators = this.getComparatorsSortedByAmount(comparator);

        //we start with the comparator representing the most common item, and if we don't find anything we move on.
        //Note: unless something weird happens we should always find something.
        for (var currentComparator : comparators) {
            for (int slot = 0; slot < this.itemStackHandler.getSlots(); slot++) {

                //first we force a simulation to check if the stack fits
                ItemStack stack = this.itemStackHandler.extractItem(slot, 1, true);
                if (stack.isEmpty()) {
                    continue;
                }

                if (currentComparator.test(stack)) {
                    //now we do the actual operation (note: can still be a simulation, if caller wants to simulate=
                    return this.itemStackHandler.extractItem(slot, 1, simulate);
                }

                //this slot does not match so we move on in the loop.
            }
        }

        //nothing found
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getItemStack(Predicate<ItemStack> comparator, int requestedSize, boolean simulate) {
        if (requestedSize <= 0 || comparator == null) {
            return ItemStack.EMPTY;
        }

        //Shortcut for exact matches
        if (comparator instanceof ItemStackComparator itemStackComparator && itemStackComparator.getMatchNbt()) {
            return this.itemStackHandler.extractItem(itemStackComparator.getFilterStack(), requestedSize, simulate);
        }

        ItemStack firstMatchedStack = ItemStack.EMPTY;
        int remaining = requestedSize;
        for (int slot = 0; slot < this.itemStackHandler.getSlots(); slot++) {

            //first we force a simulation
            ItemStack stack = this.itemStackHandler.extractItem(slot, remaining, true);
            if (stack.isEmpty()) {
                continue;
            }

            //if we have not found anything yet we can just store the result or move on
            if (firstMatchedStack.isEmpty()) {

                if (!comparator.test(stack)) {
                    //this slot does not match so we move on
                    continue;
                }
                //just take entire stack -> we're in sim mode
                firstMatchedStack = stack.copy();
            } else {
                //we already found something, so we need to make sure the stacks match up, if not we move on.
                if (!ItemStack.isSameItemSameComponents(firstMatchedStack, stack)) {
                    continue;
                }
            }

            //get how many we have to extract in this round, cannot be more than we need nor more than is in this slot.
            int toExtract = Math.min(stack.getCount(), remaining);

            //now we can leave simulation up to the caller
            ItemStack extractedStack = this.itemStackHandler.extractItem(slot, toExtract, simulate);
            remaining -= extractedStack.getCount();

            //if we got all we need we can exit here.
            if (remaining <= 0) {
                break;
            }
        }

        //set the exact output count and return.
        int extractCount = requestedSize - remaining;
        if (!firstMatchedStack.isEmpty() && extractCount > 0) {
            firstMatchedStack.setCount(extractCount);
        }

        return firstMatchedStack;
    }

    public int getAvailableAmount(IItemStackComparator comparator) {
        if (comparator == null) {
            return 0;
        }

        //Shortcut for exact matches
        if (comparator instanceof ItemStackComparator itemStackComparator && itemStackComparator.getMatchNbt()) {
            return this.itemStackHandler.get(itemStackComparator.getFilterStack());
        }

        int totalCount = 0;

        int size = this.itemStackHandler.getSlots();
        for (int slot = 0; slot < size; slot++) {
            ItemStack stack = this.itemStackHandler.getStackInSlot(slot);
            if (comparator.matches(stack))
                totalCount += stack.getCount();
        }
        return totalCount;
    }

    @Override
    public void onContentsChanged() {
        this.cachedMessageUpdateStacks = null;
        this.setChanged();
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        compound.remove("linkedMachines"); //linked machines are not saved, they self-register.
        super.loadAdditional(compound, provider);

        //read stored items
        if (compound.contains("items")) {
            this.itemStackHandler.deserializeNBT(provider, compound.getCompound("items"));
            this.cachedMessageUpdateStacks = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        compound.remove("linkedMachines"); //linked machines are not saved, they self-register.
        compound.put("items", this.itemStackHandler.serializeNBT(provider));
    }

    @Override
    public void loadNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        this.setSortDirection(SortDirection.BY_ID.apply(compound.getInt("sortDirection")));
        this.setSortType(SortType.BY_ID.apply(compound.getInt("sortType")));
        if (compound.contains("maxItemTypes") && compound.contains("maxTotalItemCount")) {
            this.setStorageLimits(compound.getInt("maxItemTypes"), compound.getLong("maxTotalItemCount"));
        }

        //read stored crafting matrix
        if (compound.contains("matrix")) {
            this.matrix = loadMatrix(compound.getCompound("matrix"), provider);
        }

        if (compound.contains("orderStack"))
            this.orderStack = ItemStack.parseOptional(provider, compound.getCompound("orderStack"));

        //read the linked machines
        this.linkedMachines = new HashMap<>();
        if (compound.contains("linkedMachines")) {
            ListTag machinesNbt = compound.getList("linkedMachines", Tag.TAG_COMPOUND);
            for (int i = 0; i < machinesNbt.size(); i++) {
                MachineReference reference = MachineReference.CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), machinesNbt.getCompound(i)).getOrThrow();
                this.linkedMachines.put(reference.insertGlobalPos, reference);
            }
        }
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putInt("sortDirection", this.getSortDirection().ordinal());
        compound.putInt("sortType", this.getSortType().ordinal());
        compound.putInt("maxItemTypes", this.maxItemTypes);
        compound.putLong("maxTotalItemCount", this.maxTotalItemCount);

        //write stored crafting matrix
        compound.put("matrix", saveMatrix(this.matrix, provider));

        if (!this.orderStack.isEmpty())
            compound.put("orderStack", this.orderStack.saveOptional(provider));

        //write linked machines
        ListTag machinesNbt = new ListTag();
        for (Map.Entry<GlobalBlockPos, MachineReference> entry : this.linkedMachines.entrySet()) {
            machinesNbt.add(entry.getValue().serializeNBT(provider));
        }
        compound.put("linkedMachines", machinesNbt);

        return compound;
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput pComponentInput) {
        super.applyImplicitComponents(pComponentInput);

        if (pComponentInput.get(OccultismDataComponents.SORT_DIRECTION) != null)
            this.sortDirection = pComponentInput.get(OccultismDataComponents.SORT_DIRECTION);
        if (pComponentInput.get(OccultismDataComponents.SORT_TYPE) != null)
            this.sortType = pComponentInput.get(OccultismDataComponents.SORT_TYPE);

        if (pComponentInput.get(OccultismDataComponents.CRAFTING_MATRIX) != null) {
            this.matrix = loadMatrix(pComponentInput.get(OccultismDataComponents.CRAFTING_MATRIX).getUnsafe(), this.level.registryAccess());
        }
        if (pComponentInput.get(OccultismDataComponents.ORDER_STACK) != null)
            this.orderStack = ItemStack.parseOptional(this.level.registryAccess(), pComponentInput.get(OccultismDataComponents.ORDER_STACK).getUnsafe());

        if (pComponentInput.get(OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get()) != null) {
            this.itemStackHandler.deserializeNBT(this.level.registryAccess(), pComponentInput.get(OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get()).getUnsafe());
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
        super.collectImplicitComponents(pComponents);

        pComponents.set(OccultismDataComponents.SORT_DIRECTION, this.sortDirection);
        pComponents.set(OccultismDataComponents.SORT_TYPE, this.sortType);

        pComponents.set(OccultismDataComponents.CRAFTING_MATRIX, CustomData.of(saveMatrix(this.matrix, this.level.registryAccess())));

        pComponents.set(OccultismDataComponents.ORDER_STACK, CustomData.of((CompoundTag) this.orderStack.saveOptional(this.level.registryAccess())));

        pComponents.set(OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS, CustomData.of(this.itemStackHandler.serializeNBT(this.level.registryAccess())));
    }

    @Override
    public void removeComponentsFromTag(CompoundTag pTag) {
        //this causes stuff to get lost. Not sure why / how it is used in vanilla shulker boxes
//        pTag.remove("items");
//        pTag.remove("matrix");
//        pTag.remove("orderStack");
//        pTag.remove("sortDirection");
//        pTag.remove("sortType");
//        pTag.remove("linkedMachines");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerEntity) {
        return new StorageControllerContainer(id, playerInventory, this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

}
