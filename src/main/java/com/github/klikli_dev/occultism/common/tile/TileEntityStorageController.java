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


import com.github.klikli_dev.occultism.OccultismConfig;
import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.data.SortDirection;
import com.github.klikli_dev.occultism.api.common.data.SortType;
import com.github.klikli_dev.occultism.api.common.tile.IStorageAccessor;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.api.common.tile.IStorageControllerProxy;
import com.github.klikli_dev.occultism.common.block.BlockStorageStabilizer;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.jobs.DepositOrder;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobManageMachine;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.common.misc.StorageControllerItemStackHandler;
import com.github.klikli_dev.occultism.network.MessageUpdateStacks;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.*;

public class TileEntityStorageController extends TileEntityBase implements ITickable, IStorageController, IStorageAccessor, IStorageControllerProxy {

    //region Fields

    public static final int DEFAULT_STACK_SIZE = 1024;
    public static final int MAX_STABILIZER_DISTANCE = 5;

    protected static final List<Block> BLOCK_BLACKLIST = new ArrayList<Block>(
            Arrays.asList(BlockRegistry.STORAGE_CONTROLLER));
    public Map<Integer, ItemStack> matrix = new HashMap<Integer, ItemStack>();
    public ItemStack orderStack = ItemStack.EMPTY;
    public Map<GlobalBlockPos, MachineReference> linkedMachines = new HashMap<>();
    public Map<GlobalBlockPos, UUID> depositOrderSpirits = new HashMap<>();
    protected SortDirection sortDirection = SortDirection.DOWN;
    protected SortType sortType = SortType.AMOUNT;
    protected ItemStackHandler itemStackHandler = new StorageControllerItemStackHandler(this,
            OccultismConfig.storage.controllerBaseSlots, DEFAULT_STACK_SIZE);
    protected int maxSlots = OccultismConfig.storage.controllerBaseSlots;
    protected int usedSlots = 0;
    protected boolean stabilizersInitialized = false;
    protected GlobalBlockPos globalPos;

    protected MessageUpdateStacks cachedMessageUpdateStacks;
    //endregion Fields

    //region Overrides
    @Override
    public IStorageController getLinkedStorageController() {
        return this;
    }

    @Override
    public GlobalBlockPos getLinkedStorageControllerPosition() {
        if (this.globalPos == null)
            this.globalPos = new GlobalBlockPos(this.getPos(), this.world.provider.getDimension());
        return this.globalPos;
    }

    @Override
    public void setLinkedStorageControllerPosition(GlobalBlockPos blockPos) {
        //Do nothing, tile entity cannot move.
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (!this.stabilizersInitialized) {
                this.stabilizersInitialized = true;
                this.updateStabilizers();
            }
        }
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
        int size = this.itemStackHandler.getSlots();
        int usedSlots = 0;
        List<ItemStack> result = new ArrayList<>(size);
        for (int slot = 0; slot < size; slot++) {
            ItemStack stack = this.itemStackHandler.getStackInSlot(slot);
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
            this.cachedMessageUpdateStacks = new MessageUpdateStacks(stacks, this.getUsedSlots());
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
        this.itemStackHandler.setSize(this.maxSlots);
        TileEntityUtil.updateTile(this.world, this.pos);
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
    public void addDepositOrder(GlobalBlockPos linkedMachinePosition, ItemStackComparator comparator, int amount) {
        //check if the item is available in the desired amount, otherwise kill the order.
        ItemStack stack = this.getItemStack(comparator, amount, true);
        if (!stack.isEmpty()) {
            UUID spiritUUID = this.depositOrderSpirits.get(linkedMachinePosition);
            Entity entity = spiritUUID != null ? this.world.getMinecraftServer().getEntityFromUuid(spiritUUID) : null;
            if (entity instanceof EntitySpirit) {
                EntitySpirit spirit = (EntitySpirit) entity;
                if (spirit.getJob() instanceof SpiritJobManageMachine) {
                    SpiritJobManageMachine job = (SpiritJobManageMachine) spirit.getJob();
                    job.addDepsitOrder(new DepositOrder(comparator, amount));
                }
                else {
                    //if the entity does not have the proper job, remove it from the list for now. it will re-register itself on spawn
                    this.removeDepositOrderSpirit(linkedMachinePosition);
                }
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
            return BLOCK_BLACKLIST.contains(itemBlock.getBlock());
        }
        return false;
    }

    @Override
    public int insertStack(ItemStack stack, boolean simulate) {
        if (this.isBlacklisted(stack))
            return stack.getCount();

        if (ItemHandlerHelper.insertItem(this.itemStackHandler, stack, true).getCount() < stack.getCount()) {
            stack = ItemHandlerHelper.insertItem(this.itemStackHandler, stack, simulate);
            if (!simulate) //invalidate our packet cache to re-create it on next get
                this.cachedMessageUpdateStacks = null;
        }

        return stack.getCount();
    }

    @Override
    public ItemStack getItemStack(IItemStackComparator comparator, int requestedSize, boolean simulate) {
        if (requestedSize <= 0 || comparator == null) {
            return ItemStack.EMPTY;
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

                if (!comparator.matches(stack)) {
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
            if (!simulate) //invalidate our packet cache to re-create it on next get
                this.cachedMessageUpdateStacks = null;
        }

        return firstMatchedStack;
    }

    public int getAvailableAmount(IItemStackComparator comparator) {
        if (comparator == null) {
            return 0;
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
    public void readFromNBT(CompoundNBT compound) {
        compound.removeTag("linkedMachines"); //linked machines are not saved, they self-register.
        super.readFromNBT(compound);

        //read stored items
        if (compound.hasKey("items")) {
            this.itemStackHandler.deserializeNBT((CompoundNBT) compound.getTag("items"));
            this.cachedMessageUpdateStacks = null;
        }
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        compound = super.writeToNBT(compound);
        compound.removeTag("linkedMachines"); //linked machines are not saved, they self-register.
        compound.setTag("items", this.itemStackHandler.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNetworkNBT(CompoundNBT compound) {
        super.readFromNetworkNBT(compound);
        this.setSortDirection(SortDirection.get(compound.getInteger("sortDirection")));
        this.setSortType(SortType.get(compound.getInteger("sortType")));

        if (compound.hasKey("maxSlots")) {
            this.setMaxSlots(compound.getInteger("maxSlots"));
        }

        //read stored crafting matrix
        this.matrix = new HashMap<Integer, ItemStack>();
        if (compound.hasKey("matrix")) {
            ListNBT matrixNbt = compound.getTagList("matrix", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < matrixNbt.tagCount(); i++) {
                CompoundNBT stackTag = matrixNbt.getCompoundTagAt(i);
                int slot = stackTag.getByte("slot");
                ItemStack s = new ItemStack(stackTag);
                this.matrix.put(slot, s);
            }
        }

        if (compound.hasKey("orderStack"))
            this.orderStack = new ItemStack(compound.getCompoundTag("orderStack"));

        //read the linked machines
        this.linkedMachines = new HashMap<>();
        if (compound.hasKey("linkedMachines")) {
            ListNBT machinesNbt = compound.getTagList("linkedMachines", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < machinesNbt.tagCount(); i++) {
                MachineReference reference = MachineReference.fromNbt(machinesNbt.getCompoundTagAt(i));
                this.linkedMachines.put(reference.globalPos, reference);
            }
        }
    }

    @Override
    public CompoundNBT writeToNetworkNBT(CompoundNBT compound) {
        compound.setInteger("sortDirection", this.getSortDirection().getValue());
        compound.setInteger("sortType", this.getSortType().getValue());
        compound.setInteger("maxSlots", this.maxSlots);

        //write stored crafting matrix
        ListNBT matrixNbt = new ListNBT();
        for (int i = 0; i < 9; i++) {
            if (this.matrix.get(i) != null && !this.matrix.get(i).isEmpty()) {
                CompoundNBT stackTag = new CompoundNBT();
                stackTag.setByte("slot", (byte) i);
                this.matrix.get(i).writeToNBT(stackTag);
                matrixNbt.appendTag(stackTag);
            }
        }
        compound.setTag("matrix", matrixNbt);

        if (!this.orderStack.isEmpty())
            compound.setTag("orderStack", this.orderStack.writeToNBT(new CompoundNBT()));

        //write linked machines
        ListNBT machinesNbt = new ListNBT();
        for (Map.Entry<GlobalBlockPos, MachineReference> entry : this.linkedMachines.entrySet()) {
            machinesNbt.appendTag(entry.getValue().writeToNBT(new CompoundNBT()));
        }
        compound.setTag("linkedMachines", machinesNbt);

        return super.writeToNetworkNBT(compound);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!this.world.isRemote)
            this.updateStabilizers();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemStackHandler);
        }
        return super.getCapability(capability, facing);
    }
    //endregion Overrides

    //region Methods
    public void updateStabilizers() {
        int additionalSlots = 0;
        List<BlockPos> stabilizerLocations = this.findValidStabilizers();
        for (BlockPos pos : stabilizerLocations) {
            additionalSlots += this.getSlotsForStabilizer(this.world.getBlockState(pos));
        }

        this.setMaxSlots(OccultismConfig.storage.controllerBaseSlots + additionalSlots);
    }

    public List<BlockPos> findValidStabilizers() {
        ArrayList<BlockPos> validStabilizers = new ArrayList<>();

        BlockPos up = this.pos.up();
        Vec3d traceStartPosition = Math3DUtil.getBlockCenter(up);

        for (Direction face : Direction.values()) {
            RayTraceResult result = this.world.rayTraceBlocks(traceStartPosition,
                    Math3DUtil.getBlockCenter(up.offset(face, MAX_STABILIZER_DISTANCE)));
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockState hitBlockState = this.world.getBlockState(result.getBlockPos());
                if (hitBlockState.getBlock() instanceof BlockStorageStabilizer &&
                    hitBlockState.getValue(DirectionalBlock.FACING) == face.getOpposite()) {
                    validStabilizers.add(result.getBlockPos());
                }
            }
        }

        return validStabilizers;
    }

    protected int getSlotsForStabilizer(BlockState state) {
        Block block = state.getBlock();
        if (block == BlockRegistry.STORAGE_STABILIZER_TIER1)
            return OccultismConfig.storage.stabilizerTier1Slots;
        if (block == BlockRegistry.STORAGE_STABILIZER_TIER2)
            return OccultismConfig.storage.stabilizerTier2Slots;
        if (block == BlockRegistry.STORAGE_STABILIZER_TIER3)
            return OccultismConfig.storage.stabilizerTier3Slots;
        if (block == BlockRegistry.STORAGE_STABILIZER_TIER4)
            return OccultismConfig.storage.stabilizerTier4Slots;
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
        this.linkedMachines.entrySet().removeIf(entry -> entry.getValue().getTileEntity(this.world) == null);
    }
    //endregion Methods

}
