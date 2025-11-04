package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.common.entity.ai.sensor.NearestCropSensor;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HarvestCropBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double HARVEST_CROP_RANGE_SQUARE = Math.pow(2.5, 2); //we're comparing to square distance

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(OccultismMemoryTypes.NEAREST_CROP.get(), MemoryStatus.VALUE_PRESENT));

    public HarvestCropBehaviour() {
        super();

        this.runtimeProvider = (entity) -> 20;
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull E entity) {
        var cropPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());
        var dist = cropPos != null ? entity.distanceToSqr(Vec3.atCenterOf(cropPos)) : 0;
        return dist <= HarvestCropBehaviour.HARVEST_CROP_RANGE_SQUARE;
    }


    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    protected boolean shouldKeepRunning(E entity) {
        return BrainUtils.hasMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());
    }

    @Override
    protected void tick(E entity) {
        var cropPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());
        if (cropPos == null)
            return;
        if (NearestCropSensor.isGrowthCrop(entity.level(), cropPos)) {
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(cropPos));
            entity.swing(InteractionHand.MAIN_HAND, true);
            for (int x = -2 ; x <= 2 ; x++) {
                for (int z = -2 ; z <= 2 ; z++) {
                    BlockState state = entity.level().getBlockState(cropPos.offset(x,0,z));
                    if (state.getBlock() instanceof CropBlock cropBlock
                            && cropBlock.isMaxAge(state)) {
                        entity.playSound(SoundEvents.CROP_BREAK, 1, 1);
                        entity.level().destroyBlock(cropPos.offset(x,0,z), true);
                        entity.level().setBlock(cropPos.offset(x,0,z), cropBlock.defaultBlockState(), 1);
                    }
                }
            }
            this.stop((ServerLevel) entity.level(), entity, entity.level().getGameTime());
            //we stop here (even though the above condition would save us) because sensor might reset last harvested crop meanwhile
        } else {
            //if the crop is gone, just stop and reset.
            this.stop((ServerLevel) entity.level(), entity, entity.level().getGameTime());
        }
    }

    protected void stop(E entity) {
        BrainUtils.clearMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());
    }

}
