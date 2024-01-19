package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.common.entity.ai.sensor.NearestTreeSensor;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import java.util.*;

public class FellTreeBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double FELL_TREE_RANGE_SQUARE = Math.pow(2.5, 2); //we're comparing to square distance

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(OccultismMemoryTypes.NEAREST_TREE.get(), MemoryStatus.VALUE_PRESENT));

    protected int breakingTime;
    protected int previousBreakProgress;

    public FellTreeBehaviour() {
        super();

        this.runtimeProvider = (entity) -> {
            return 200;
        };
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        var treePos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
        var dist = entity.distanceToSqr(Vec3.atCenterOf(treePos));
        return dist <= FellTreeBehaviour.FELL_TREE_RANGE_SQUARE;
    }


    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    protected boolean shouldKeepRunning(E entity) {
        return BrainUtils.hasMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
    }

    @Override
    protected void tick(E entity) {
        var treePos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
        if (NearestTreeSensor.isLog(entity.level(), treePos)) {
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(treePos));
            this.breakingTime++;
            entity.swing(InteractionHand.MAIN_HAND);
            int i = (int) ((float) this.breakingTime / 160.0F * 10.0F);
            if (this.breakingTime % 10 == 0) {
                entity.playSound(SoundEvents.WOOD_HIT, 1, 1);
                entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1, 0.5F);
            }
            if (i != this.previousBreakProgress) {
                entity.level().destroyBlockProgress(entity.getId(), treePos, i);
                this.previousBreakProgress = i;
            }
            if (this.breakingTime == 160) {
                entity.playSound(SoundEvents.WOOD_BREAK, 1, 1);
                this.fellTree(entity, treePos);
                BrainUtils.setMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get(), treePos);
                this.stop((ServerLevel) entity.level(), entity, entity.level().getGameTime());
                //we stop here (even though the above condition would save us) because sensor might reset last felled tree meanwhile
            }

        } else {
            //if the tree is gone, just stop and reset.
            this.stop((ServerLevel) entity.level(), entity, entity.level().getGameTime());
        }
    }

    protected void start(E entity) {
        this.breakingTime = 0;
        this.previousBreakProgress = -1;
    }

    protected void stop(E entity) {
        BrainUtils.clearMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
    }

    private void fellTree(E entity, BlockPos treePos) {
        Level level = entity.level();
        BlockPos base = treePos;
        Queue<BlockPos> blocks = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        blocks.add(base);

        while (!blocks.isEmpty()) {

            BlockPos pos = blocks.remove();
            if (!visited.add(pos)) {
                continue;
            }

            if (!NearestTreeSensor.isLog(level, pos)) {
                continue;
            }

            for (Direction facing : Direction.Plane.HORIZONTAL) {
                BlockPos pos2 = pos.relative(facing);
                if (!visited.contains(pos2)) {
                    blocks.add(pos2);
                }
            }

            for (int x = 0; x < 3; x++) {
                for (int z = 0; z < 3; z++) {
                    BlockPos pos2 = pos.offset(-1 + x, 1, -1 + z);
                    if (!visited.contains(pos2)) {
                        blocks.add(pos2);
                    }
                }
            }

            level.destroyBlock(pos, true);
        }

    }

}
