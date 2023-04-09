package com.github.klikli_dev.occultism.integration.theurgy;

import com.github.klikli_dev.occultism.Occultism;
import com.klikli_dev.theurgy.entity.FollowProjectile;
import com.klikli_dev.theurgy.item.DivinationRodItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

public class TheurgyIntegration {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("theurgy");
    }

    public static void spawnDivinationResultParticle(BlockPos result, Level level, LivingEntity entity) {
        if (isLoaded()) {
            TheurgyHelper.spawnDivinationResultParticle(result, level, entity);
        } else {
            Occultism.LOGGER.warn("Attempted to spawn Theurgy Divination Result Particle without Theurgy installed");
        }
    }

    public static class TheurgyHelper {
        /**
         * Copied from Theurgy DivinationRodItem
         * Guards against server side classloads by using Theurgy's DistHelper
         */
        protected static void spawnDivinationResultParticle(BlockPos result, Level level, LivingEntity entity) {
            final var visualizationRange = 10.0f;
            var from = new Vec3(entity.getX(), entity.getEyeY() - (double) 0.1F, entity.getZ());
            var resultVec = Vec3.atCenterOf(result);
            var dist = resultVec.subtract(from);
            var dir = dist.normalize();
            var to = dist.length() <= visualizationRange ? resultVec : from.add(dir.scale(visualizationRange));

            if (level.isLoaded(BlockPos.containing(to)) && level.isLoaded(BlockPos.containing(from)) && level.isClientSide) {
                FollowProjectile aoeProjectile = new FollowProjectile(level, from, to);
                DivinationRodItem.DistHelper.spawnEntityClientSide(level, aoeProjectile);
            }
        }
    }
}
