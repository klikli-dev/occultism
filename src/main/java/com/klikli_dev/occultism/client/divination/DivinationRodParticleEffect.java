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

package com.klikli_dev.occultism.client.divination;

import com.klikli_dev.magicparticleslib.premade.projectile.VisualEntitySpawner;
import com.klikli_dev.magicparticleslib.premade.projectile.glowtrail.GlowTrailProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DivinationRodParticleEffect {
    private static final float VISUALIZATION_RANGE = 10.0f;
    private static final int COLOR = 0xFFFF19B4;
    private static final float SIZE = 0.25f;

    private DivinationRodParticleEffect() {
    }

    public static void spawn(BlockPos result, Level level, LivingEntity entity) {
        Vec3 from = new Vec3(entity.getX(), entity.getEyeY() - 0.1F, entity.getZ());
        Vec3 resultVec = Vec3.atCenterOf(result);
        Vec3 distance = resultVec.subtract(from);
        Vec3 direction = distance.normalize();
        Vec3 to = distance.length() <= VISUALIZATION_RANGE ? resultVec : from.add(direction.scale(VISUALIZATION_RANGE));

        if (!level.isClientSide() || !level.isLoaded(BlockPos.containing(from)) || !level.isLoaded(BlockPos.containing(to))) {
            return;
        }

        GlowTrailProjectile projectile = new GlowTrailProjectile(level, from, to)
                .color(COLOR)
                .size(SIZE);
        VisualEntitySpawner.spawn(level, projectile, true);
    }
}
