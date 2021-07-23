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

import com.github.klikli_dev.occultism.common.entity.spirit.WildHuntWitherSkeletonEntity;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.level.Level;
import net.minecraft.level.server.ServerWorld;

public class SummonWildHuntRitual extends SummonSpiritRitual {
    //region Fields
    public static final ResourceLocation villagerTag = new ResourceLocation("forge", "villagers");
    //endregion Fields

    //region Initialization
    public SummonWildHuntRitual() {
        super(null,
                OccultismRituals.SUMMON_WILD_GREATER_SPIRIT_PENTACLE.get(),
                Ingredient.fromItems(Blocks.SKELETON_SKULL),
                "summon_wild_hunt", 30);
        this.sacrificePredicate = (entity) -> entity instanceof Player ||
                                              OccultismTags.VILLAGERS.contains(entity.getType());
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity BlockEntity,
                       Player castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, BlockEntity, castingPlayer, activationItem);

        activationItem.shrink(1); //remove original activation item.

        ((ServerWorld) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //Spawn the wither skeletons, who will spawn their minions
        for (int i = 0; i < 3; i++) {
            WildHuntWitherSkeletonEntity skeleton = OccultismEntities.WILD_HUNT_WITHER_SKELETON.get().create(level);

            double offsetX = (level.getRandom().nextGaussian() - 1.0) * (1 + level.getRandom().nextInt(4));
            double offsetZ = (level.getRandom().nextGaussian() - 1.0) * (1 + level.getRandom().nextInt(4));

            skeleton.setPositionAndRotation(goldenBowlPosition.getX() + offsetX, goldenBowlPosition.getY() + 1.5,
                    goldenBowlPosition.getZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            skeleton.setCustomName(new StringTextComponent(TextUtil.generateName()));

            skeleton.onInitialSpawn((ServerWorld) level, level.getDifficultyForLocation(goldenBowlPosition), SpawnReason.MOB_SUMMONED,
                    null,
                    null);
            this.spawnEntity(skeleton, level);
        }
    }
    //endregion Overrides
}
