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

import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnReason;
import net.minecraft.world.entity.passive.AnimalEntity;
import net.minecraft.world.entity.passive.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;

public class FamiliarParrotRitual extends SummonSpiritRitual {

    //region Initialization
    public FamiliarParrotRitual() {
        super(null,
                OccultismRituals.POSSESS_FOLIOT_PENTACLE.get(),
                Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                "familiar_parrot", 30);
        this.sacrificePredicate =
                (entity) -> OccultismTags.CHICKEN.contains(entity.getType());
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity BlockEntity,
                       Player castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, BlockEntity, castingPlayer, activationItem);

        String entityName = ItemNBTUtil.getBoundSpiritName(activationItem);
        activationItem.shrink(1); //remove original activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //1/3 are a parrot, 2/3 are chickens.
        AnimalEntity parrot =
                level.rand.nextInt(3) == 0 ? EntityType.PARROT.create(level) : EntityType.CHICKEN.create(level);
        parrot.onInitialSpawn((ServerLevel) level, level.getDifficultyForLocation(goldenBowlPosition), SpawnReason.MOB_SUMMONED,
                null,
                null);
        parrot
                .absMoveTo(goldenBowlPosition.getX(), goldenBowlPosition.getY(), goldenBowlPosition.getZ(),
                        level.rand.nextInt(360), 0);
        parrot.setCustomName(new TextComponent(entityName));
        if (parrot instanceof TamableAnimal) {
            ((TamableAnimal) parrot).setTamedBy(castingPlayer);
        }

        //notify players nearby and spawn
        this.spawnEntity(parrot, level);
    }
    //endregion Overrides
}
