/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

package com.github.klikli_dev.occultism.common.tile;


import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.data.SortDirection;
import com.github.klikli_dev.occultism.api.common.data.SortType;
import com.github.klikli_dev.occultism.api.common.tile.IStorageAccessor;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.api.common.tile.IStorageControllerProxy;
import com.github.klikli_dev.occultism.common.block.storage.StorageStabilizerBlock;
import com.github.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.ManageMachineJob;
import com.github.klikli_dev.occultism.common.misc.DepositOrder;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.common.misc.StorageControllerItemStackHandler;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.network.MessageUpdateStacks;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.EntityUtil;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.BlockEntity.TickingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.container.AbstractContainerMenu;
import net.minecraft.inventory.container.MenuProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListNBT;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StorageControllerBlockEntity extends NetworkedBlockEntity implements TickingBlockEntity, MenuProvider, IStorageController, IStorageAccessor, IStorageControllerProxy {

    //region Fields
    public static final int MAX_STABILIZER_DISTANCE = 5;

    protected static final List<RegistryObject<? extends Block>> BLOCK_BLACKLIST = Stream.of(
            OccultismBlocks.STORAGE_CONTROLLER).collect(Collectors.toList());
    public Map<Integer, ItemStack> matrix = new HashMap<>();
    public ItemStack orderStack = ItemStack.EMPTY;
    public Map<GlobalBlockPos, MachineReference> linkedMachines = new HashMap<>();
    public Map<GlobalBlockPos, UUID> depositOrderSpirits = new HashMap<>();
    protected SortDirection sortDirection = SortDirection.DOWN;
    protected SortType sortType = SortType.AMOUNT;
    protected LazyOptional<ItemStackHandler> itemStackHandler =
            LazyOptional.of(() -> new StorageControllerItemStackHandler(this,
                    Occultism.SERVER_CONFIG.storage.controllerBaseSlots.get(),
                    Occultism.SERVER_CONFIG.storage.controllerStackSize.get(),
                    Occultism.SERVER_CONFIG.storage.overrideItemStackSizes.get()
            ));
    protected int maxSlots = Occultism.SERVER_CONFIG.storage.controllerBaseSlots.get();
    protected int usedSlots = 0;
    protected boolean stabilizersInitialized = false;
    protected GlobalBlockPos globalPos;

    protected MessageUpdateStacks cachedMessageUpdateStacks;
    //endregion Fields

    //region Initialization
    public StorageControllerBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismTiles.STORAGE_CONTROLLER.get(), worldPos, state);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Component getDisplayName() {
        return new TextComponent(this.getType().getRegistryName().getPath());
    }

    @Override
    public IStorageController getLinkedStorageController() {
        return this;
    }

    @Override
    public GlobalBlockPos getLinkedStorageControllerPosition() {
        if (this.globalPos == null)
            this.globalPos = new GlobalBlockPos(this.getPos(), this.world);
        return this.globalPos;
    }

    @Override
    public void setLinkedStorageControllerPosition(GlobalBlockPos blockPos) {
        //Do nothing, tile entity cannot move.
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
        ItemStackHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        int size = handler.getSlots();
        int usedSlots = 0;
        List<ItemStack> result = new ArrayList<>(size);
        for (int slot = 0; slot < size; slot++) {
            ItemStack stack = handler.getItem(slot);
            if (!stack.isEmpty()) {
                usedSlots++;
                this.mergeIntoList(result, stack.copy());
            }
        }
        this.usedSlots = usedSlots;
        return result;
    }

    @Override
    public MessageUpdateStacks getMessageUpdateStacks() {
        if (this.cachedMessageUpdateStacks == null) {
            List<ItemStack> stacks = this.getStacks();
            this.cachedMessageUpdateStacks = new MessageUpdateStacks(stacks, this.getUsedSlots(), this.getMaxSlots());
        }
        return this.cachedMessageUpdateStacks;
    }

    @Override
    public int getMaxSlots() {
        return this.maxSlots;
    }

    @Override
    public void setMaxSlots(int slots) {
        this.maxSlots = slots;
        this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new).setSize(this.maxSlots);
        this.markNetworkDirty();
    }

    @Override
    public int getUsedSlots() {
        return this.usedSlots;
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
        this.linkedMachines.put(machine.globalPos, machine);
    }

    @Override
    public void addDepositOrder(GlobalBlockPos linkedMachinePosition, IItemStackComparator comparator, int amount) {
        //check if the item is available in the desired amount, otherwise kill the order.
        ItemStack stack = this.getItemStack(comparator, amount, true);
        if (!stack.isEmpty()) {
            UUID spiritUUID = this.depositOrderSpirits.get(linkedMachinePosition);
            if (spiritUUID != null) {
                EntityUtil.getEntityByUuiDGlobal(this.world.getServer(),
                        spiritUUID).filter(SpiritEntity.class::isInstance).map(SpiritEntity.class::cast)
                        .ifPresent(spirit -> {
                            Optional<ManageMachineJob> job = spirit.getJob().filter(ManageMachineJob.class::isInstance)
                                                                     .map(ManageMachineJob.class::cast);
                            if (job.isPresent()) {
                                job.get().addDepsitOrder(new DepositOrder((ItemStackComparator) comparator, amount));
                            }
                            else {
                                this.removeDepositOrderSpirit(linkedMachinePosition);
                            }
                        });
            }
            else {
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
        if (stack.getItem() instanceof BlockItem) {
            BlockItem itemBlock = (BlockItem) stack.getItem();
            return BLOCK_BLACKLIST.stream().map(RegistryObject::get).anyMatch(block -> itemBlock.getBlock() == block);
        }
        
        if(stack.getItem() == OccultismItems.STORAGE_REMOTE.get())
            return true;
        
        return false;
    }

    @Override
    public int insertStack(ItemStack stack, boolean simulate) {
        if (this.isBlacklisted(stack))
            return stack.getCount();

        ItemStackHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        if (ItemHandlerHelper.insertItem(handler, stack, true).getCount() < stack.getCount()) {
            stack = ItemHandlerHelper.insertItem(handler, stack, simulate);
        }

        return stack.getCount();
    }

    @Override
    public ItemStack getItemStack(Predicate<ItemStack> comparator, int requestedSize, boolean simulate) {
        if (requestedSize <= 0 || comparator == null) {
            return ItemStack.EMPTY;
        }
        ItemStackHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        ItemStack firstMatchedStack = ItemStack.EMPTY;
        int remaining = requestedSize;
        for (int slot = 0; slot < handler.getSlots(); slot++) {

            //first we force a simulation
            ItemStack stack = handler.extractItem(slot, remaining, true);
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
            }
            else {
                //we already found something, so we need to make sure the stacks match up, if not we move on.
                if (!ItemHandlerHelper.canItemStacksStack(firstMatchedStack, stack)) {
                    continue;
                }
            }

            //get how many we have to extract in this round, cannot be more than we need nor more than is in this slot.
            int toExtract = Math.min(stack.getCount(), remaining);

            //now we can leave simulation up to the caller
            ItemStack extractedStack = handler.extractItem(slot, toExtract, simulate);
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
        int totalCount = 0;
        ItemStackHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        int size = handler.getSlots();
        for (int slot = 0; slot < size; slot++) {
            ItemStack stack = handler.getItem(slot);
            if (comparator.matches(stack))
                totalCount += stack.getCount();
        }
        return totalCount;
    }

    @Override
    public void onContentsChanged() {
        this.cachedMessageUpdateStacks = null;
        this.markDirty();
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.itemStackHandler.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction direction) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemStackHandler.cast();
        }
        return super.getCapability(cap, direction);
    }

    @Override
    public void tick() {
        if (!this.world.isClientSide) {
            if (!this.stabilizersInitialized) {
                this.stabilizersInitialized = true;
                this.updateStabilizers();
            }
        }
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
        compound.remove("linkedMachines"); //linked machines are not saved, they self-register.
        super.load(state, compound);

        //read stored items
        if (compound.contains("items")) {
            this.itemStackHandler.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("items")));
            this.cachedMessageUpdateStacks = null;
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.remove("linkedMachines"); //linked machines are not saved, they self-register.
        this.itemStackHandler.ifPresent(handler -> {
            compound.put("items", handler.serializeNBT());
        });
        return compound;
    }

    @Override
    public void loadNetwork(CompoundTag compound) {
        this.setSortDirection(SortDirection.get(compound.getInt("sortDirection")));
        this.setSortType(SortType.get(compound.getInt("sortType")));

        if (compound.contains("maxSlots")) {
            this.setMaxSlots(compound.getInt("maxSlots"));
        }

        //read stored crafting matrix
        this.matrix = new HashMap<Integer, ItemStack>();
        if (compound.contains("matrix")) {
            ListNBT matrixNbt = compound.getList("matrix", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < matrixNbt.size(); i++) {
                CompoundTag stackTag = matrixNbt.getCompound(i);
                int slot = stackTag.getByte("slot");
                ItemStack s = ItemStack.read(stackTag);
                this.matrix.put(slot, s);
            }
        }

        if (compound.contains("orderStack"))
            this.orderStack = ItemStack.read(compound.getCompound("orderStack"));

        //read the linked machines
        this.linkedMachines = new HashMap<>();
        if (compound.contains("linkedMachines")) {
            ListNBT machinesNbt = compound.getList("linkedMachines", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < machinesNbt.size(); i++) {
                MachineReference reference = MachineReference.from(machinesNbt.getCompound(i));
                this.linkedMachines.put(reference.globalPos, reference);
            }
        }
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound) {
        compound.putInt("sortDirection", this.getSortDirection().getValue());
        compound.putInt("sortType", this.getSortType().getValue());
        compound.putInt("maxSlots", this.maxSlots);

        //write stored crafting matrix
        ListNBT matrixNbt = new ListNBT();
        for (int i = 0; i < 9; i++) {
            if (this.matrix.get(i) != null && !this.matrix.get(i).isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stackTag.putByte("slot", (byte) i);
                this.matrix.get(i).write(stackTag);
                matrixNbt.add(stackTag);
            }
        }
        compound.put("matrix", matrixNbt);

        if (!this.orderStack.isEmpty())
            compound.put("orderStack", this.orderStack.write(new CompoundTag()));

        //write linked machines
        ListNBT machinesNbt = new ListNBT();
        for (Map.Entry<GlobalBlockPos, MachineReference> entry : this.linkedMachines.entrySet()) {
            machinesNbt.add(entry.getValue().serializeNBT());
        }
        compound.put("linkedMachines", machinesNbt);

        return compound;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerEntity) {
        return new StorageControllerContainer(id, playerInventory, this);
    }
    //endregion Overrides

    //region Methods
    public void updateStabilizers() {
        int additionalSlots = 0;
        List<BlockPos> stabilizerLocations = this.findValidStabilizers();
        for (BlockPos pos : stabilizerLocations) {
            additionalSlots += this.getSlotsForStabilizer(this.world.getBlockState(pos));
        }

        this.setMaxSlots(Occultism.SERVER_CONFIG.storage.controllerBaseSlots.get() + additionalSlots);
    }

    public List<BlockPos> findValidStabilizers() {
        ArrayList<BlockPos> validStabilizers = new ArrayList<>();

        BlockPos up = this.pos.above();
        for (Direction face : Direction.values()) {
            BlockPos hit = Math3DUtil.simpleTrace(up, face, MAX_STABILIZER_DISTANCE, (pos) -> {
                BlockState state = this.world.getBlockState(pos);
                return state.getBlock() instanceof StorageStabilizerBlock;
            });

            if (hit != null) {
                BlockState state = this.world.getBlockState(hit);
                if (state.get(DirectionalBlock.FACING) == face.getOpposite()) {
                    validStabilizers.add(hit);
                }
            }
        }
        return validStabilizers;
    }

    protected int getSlotsForStabilizer(BlockState state) {
        Block block = state.getBlock();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier1Slots.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier2Slots.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier3Slots.get();
        if (block == OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
            return Occultism.SERVER_CONFIG.storage.stabilizerTier4Slots.get();
        return 0;
    }


    protected void mergeIntoList(List<ItemStack> list, ItemStack stackToAdd) {
        boolean merged = false;
        for (ItemStack stack : list) {
            if (ItemHandlerHelper.canItemStacksStack(stackToAdd, stack)) {
                stack.setCount(stack.getCount() + stackToAdd.getCount());
                merged = true;
                break;
            }
        }
        if (!merged) {
            list.add(stackToAdd);
        }
    }

    protected void validateLinkedMachines() {
        // remove all entries that lead to invalid tile entities.
        this.linkedMachines.entrySet().removeIf(entry -> entry.getValue().getBlockEntity(this.world) == null);
    }
    //endregion Methods

}
