package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.util.StorageUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemHandlerHelper;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class DepositItemsBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double DEPOSIT_ITEM_RANGE_SQUARE = Math.pow(2.5, 2); //we're comparing to square distance

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(OccultismMemoryTypes.DEPOSIT_POSITION.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.DEPOSIT_FACING.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        var depositPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.DEPOSIT_POSITION.get());
        var dist = entity.distanceToSqr(Vec3.atCenterOf(depositPos));
        return StorageUtil.getFirstFilledSlot(entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new)) != -1
                && dist <= DepositItemsBehaviour.DEPOSIT_ITEM_RANGE_SQUARE;
    }

    protected void start(E entity) {

        var depositPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.DEPOSIT_POSITION.get());
        var depositFacing = BrainUtils.getMemory(entity, OccultismMemoryTypes.DEPOSIT_FACING.get());

        var blockEntity = entity.level.getBlockEntity(depositPos);
        if (blockEntity != null) {
            var depositItemHandlerCap = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, depositFacing);

            this.toggleContainer(blockEntity, true);

            if (depositItemHandlerCap.isPresent()) {
                var depositItemHandler = depositItemHandlerCap.orElseThrow(ItemHandlerMissingException::new);
                var entityItemHandler = entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
                var firstFilledSlot = StorageUtil.getFirstFilledSlot(entityItemHandler);
                ItemStack duplicate = entityItemHandler.getStackInSlot(firstFilledSlot).copy();

                //simulate insertion
                ItemStack toInsert = ItemHandlerHelper.insertItem(depositItemHandler, duplicate, true);
                //if anything was inserted go for real
                if (toInsert.getCount() != duplicate.getCount()) {
                    ItemStack leftover = ItemHandlerHelper.insertItem(depositItemHandler, duplicate, false);
                    //if we inserted everything
                    entityItemHandler.setStackInSlot(firstFilledSlot, leftover);
                }

                //after inserting, close container
                this.toggleContainer(blockEntity, false);

            } else {
                //if deposit is bogus, that is a player issue not ai.
            }
        } else {
            //if deposit is bogus, that is a player issue not ai.
        }
    }

    /**
     * Opens or closes a container
     *
     * @param target the target
     * @param open   true to open the container, false to close it.
     */
    public void toggleContainer(BlockEntity target, boolean open) {
        if (open) {
            //event id: opener counter changed, event param: new count
            target.getLevel().blockEvent(target.getBlockPos(), target.getBlockState().getBlock(), 1, 1);
        } else {
            //event id: opener counter changed, event param: new count
            target.getLevel().blockEvent(target.getBlockPos(), target.getBlockState().getBlock(), 1, 0);
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
