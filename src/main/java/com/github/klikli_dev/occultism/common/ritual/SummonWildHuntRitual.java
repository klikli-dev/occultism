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

package com.github.klikli_dev.occultism.common.ritual;

import com.github.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SummonWildHuntRitual extends SummonRitual {

    public SummonWildHuntRitual(RitualRecipe recipe) {
        super(recipe, false);
    }

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       Player castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, blockEntity, castingPlayer, activationItem);

        activationItem.shrink(1); //remove original activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //Spawn the wither skeletons, who will spawn their minions
        EntityType<?> entityType = this.recipe.getEntityToSummon();
        if (entityType != null) {

            for (int i = 0; i < 3; i++) {
                Entity entity = this.createSummonedEntity(entityType, level, goldenBowlPosition, blockEntity, castingPlayer);

                if (entity instanceof LivingEntity living) {
                    double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                    double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));

                    living.absMoveTo(goldenBowlPosition.getX() + offsetX, goldenBowlPosition.getY() + 1.5,
                            goldenBowlPosition.getZ() + offsetZ,
                            level.getRandom().nextInt(360), 0);
                    living.setCustomName(new TextComponent(TextUtil.generateName()));

                    if (living instanceof Mob mob) {
                        mob.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(goldenBowlPosition), MobSpawnType.MOB_SUMMONED, null, null);
                    }

                    this.applyEntityNbt(living);

                    this.spawnEntity(living, level);
                }
            }
        }
        //endregion Overrides
    }
}