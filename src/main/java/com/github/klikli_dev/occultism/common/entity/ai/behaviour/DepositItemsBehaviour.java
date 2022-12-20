package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.util.StorageUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemHandlerHelper;
import com.klikli_dev.forgebrainlib.api.core.behaviour.ExtendedBehaviour;
import com.klikli_dev.forgebrainlib.util.BrainUtils;

import java.util.List;

public class DepositItemsBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double DEPOSIT_ITEM_RANGE_SQUARE = Math.pow(2.5, 2); //we're comparing to square distance

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(OccultismMemoryTypes.DEPOSIT_POSITION.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.DEPOSIT_FACING.get(), MemoryStatus.VALUE_PRESENT)
    );

    public DepositItemsBehaviour() {
        super();

        this.runtimeProvider = (entity) -> {
            return 10;
        };
    }

    protected boolean shouldKeepRunning(E entity) {
        return true;
    }

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
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(depositPos));

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

            } else {
                //if deposit is bogus, that is a player issue not ai.
            }
        } else {
            //if deposit is bogus, that is a player issue not ai.
        }
    }

    @Override
    protected void stop(E entity) {
        //after inserting, close container
        //we use stop with a runtimeprovider to create the delay.
        var depositPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.DEPOSIT_POSITION.get());

        var blockEntity = entity.level.getBlockEntity(depositPos);
        if (blockEntity != null) {
            this.toggleContainer(blockEntity, false);
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
