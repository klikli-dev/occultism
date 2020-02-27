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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirits.EntityFoliot;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobTrader;
import com.github.klikli_dev.occultism.common.tile.TileEntityGoldenSacrificialBowl;
import com.github.klikli_dev.occultism.network.MessageParticle;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.registry.RitualRegistry;
import com.github.klikli_dev.occultism.registry.SpiritJobFactoryRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualSummonFoliotSaplingTrader extends RitualSummonSpirit {

    //region Initialization
    public RitualSummonFoliotSaplingTrader() {
        super(null, "summon_foliot_sapling_trader", RitualRegistry.PENTACLE_SUMMON_FOLIOT_BASIC,
                Ingredient.fromItem(ItemRegistry.BOOK_OF_BINDING_ACTIVE_FOLIOT), "summon_foliot_sapling_trader", 5);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finish(World world, BlockPos goldenBowlPosition, TileEntityGoldenSacrificialBowl tileEntity,
                       EntityPlayer castingPlayer, ItemStack activationItem) {
        super.finish(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem);

        //prepare active book of calling
        ItemStack activationItemCopy = activationItem.copy();
        activationItem.shrink(1); //remove original activation item.

        Occultism.network.sendToDimension(
                new MessageParticle(EnumParticleTypes.SMOKE_LARGE, goldenBowlPosition.getX() + 0.5,
                        goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5),
                world.provider.getDimension());

        //set up the foliot entity
        EntitySpirit spirit = new EntityFoliot(world);
        this.prepareSpiritForSpawn(spirit, world, goldenBowlPosition, castingPlayer,
                ItemNBTUtil.getBoundSpiritName(activationItemCopy));

        //set up the job
        SpiritJobTrader exchange = (SpiritJobTrader) SpiritJobFactoryRegistry.JOB_TRADE_OTHERWORLD_SAPLINGS
                                                             .create(spirit);
        exchange.setTradeRecipeId(new ResourceLocation(Occultism.MODID, "spirit_trade/otherworld_sapling"));
        exchange.init();
        spirit.setJob(exchange);
        spirit.setSpiritMaxAge(60);

        //notify players nearby and spawn
        this.spawnSpirit(spirit, world);
    }
    //endregion Overrides
}
