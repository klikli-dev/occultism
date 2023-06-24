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
import com.github.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

public class FamiliarRitual extends SummonRitual {

    public FamiliarRitual(RitualRecipe recipe) {
        super(recipe, true);
    }

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       Player castingPlayer, ItemStack activationItem) {
        //manually call content of Ritual.finish(), because we cannot access it via super
        level.playSound(null, goldenBowlPosition, OccultismSounds.POOF.get(), SoundSource.BLOCKS, 0.7f,
                0.7f);
        castingPlayer.displayClientMessage(Component.translatable(this.getFinishedMessage()), true);
        OccultismAdvancements.RITUAL.trigger((ServerPlayer) castingPlayer, this);

        String entityName = ItemNBTUtil.getBoundSpiritName(activationItem);
        activationItem.shrink(1); //remove original activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        EntityType<?> entityType = this.recipe.getEntityToSummon();
        if (entityType != null) {
            Entity entity = this.createSummonedEntity(entityType, level, goldenBowlPosition, blockEntity, castingPlayer);
            if (entity instanceof FamiliarEntity familiar) {

                if (!ForgeEventFactory.doSpecialSpawn(familiar, level, (float) familiar.getX(), (float) familiar.getY(), (float) familiar.getZ(), null, MobSpawnType.MOB_SUMMONED))
                    familiar.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(goldenBowlPosition), MobSpawnType.MOB_SUMMONED,
                            null, null);

                this.applyEntityNbt(familiar);

                familiar.absMoveTo(goldenBowlPosition.getX(), goldenBowlPosition.getY(), goldenBowlPosition.getZ(),
                        level.random.nextInt(360), 0);
                familiar.setCustomName(Component.literal(entityName));
                familiar.setFamiliarOwner(castingPlayer);

                //notify players nearby and spawn
                this.spawnEntity(familiar, level);
            }
        }
    }
}
