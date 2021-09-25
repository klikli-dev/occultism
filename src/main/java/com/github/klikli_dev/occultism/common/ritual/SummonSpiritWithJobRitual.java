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

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SummonSpiritWithJobRitual extends SummonRitual {

    public SummonSpiritWithJobRitual(RitualRecipe recipe) {
        super(recipe, true);
    }

    @Override
    public void initSummoned(LivingEntity living, World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                             PlayerEntity castingPlayer) {
        super.initSummoned(living, world, goldenBowlPosition, tileEntity, castingPlayer);

        if (living instanceof SpiritEntity) {
            SpiritEntity spirit = (SpiritEntity) living;
            SpiritJob job = OccultismSpiritJobs.REGISTRY.getValue(this.recipe.getSpiritJobType()).create(spirit);
            job.init();
            spirit.setJob(job);
        }
    }
}
