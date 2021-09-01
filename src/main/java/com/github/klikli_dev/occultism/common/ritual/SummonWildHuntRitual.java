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

import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SummonWildHuntRitual extends SummonRitual {

    public SummonWildHuntRitual(RitualRecipe recipe) {
        super(recipe, false);
    }

    @Override
    public void finish(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem) {
        super.finish(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem);

        activationItem.shrink(1); //remove original activation item.

        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //Spawn the wither skeletons, who will spawn their minions
        EntityType<?> entityType = this.recipe.getEntityToSummon();
        if (entityType != null) {

            for (int i = 0; i < 3; i++) {
                Entity entity = this.createSummonedEntity(entityType, world, goldenBowlPosition, tileEntity, castingPlayer);

                if (entity instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity) entity;
                    double offsetX = (world.getRandom().nextGaussian()) * (1 + world.getRandom().nextInt(4));
                    double offsetZ = (world.getRandom().nextGaussian()) * (1 + world.getRandom().nextInt(4));

                    living.setPositionAndRotation(goldenBowlPosition.getX() + offsetX, goldenBowlPosition.getY() + 1.5,
                            goldenBowlPosition.getZ() + offsetZ,
                            world.getRandom().nextInt(360), 0);
                    living.setCustomName(new StringTextComponent(TextUtil.generateName()));

                    if (living instanceof MobEntity) {
                        ((MobEntity) living).onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(goldenBowlPosition), SpawnReason.MOB_SUMMONED,
                                null,
                                null);
                    }

                    this.spawnEntity(living, world);
                }
            }
        }
    }
    //endregion Overrides
}
