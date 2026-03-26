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

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.wonderingtrader.WonderingTraderEntity;
import com.klikli_dev.occultism.registry.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;


@EventBusSubscriber(modid = Occultism.MODID)
public class ForgeEventHandler {

    //region Static Methods
    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, OccultismItems.OTHERWORLD_ESSENCE.asItem(), OccultismPotions.THIRD_EYE_POTION);
        builder.addMix(OccultismPotions.THIRD_EYE_POTION, Items.REDSTONE, OccultismPotions.LONG_THIRD_EYE_POTION);

        builder.addMix(Potions.NIGHT_VISION, OccultismItems.DATURA.asItem(), OccultismPotions.THIRD_EYE_POTION);
        builder.addMix(Potions.LONG_NIGHT_VISION, OccultismItems.DATURA.asItem(), OccultismPotions.LONG_THIRD_EYE_POTION);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        OccultismCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onTraderSpawn(FinalizeSpawnEvent event) {
        if (event.isSpawnCancelled() || event.isCanceled())
            return;
        if (event.getSpawnType() != EntitySpawnReason.EVENT)
            return;
        if (!(event.getEntity() instanceof WanderingTrader trader) || (event.getEntity() instanceof WonderingTraderEntity))
            return;
        if (RandomSource.create().nextInt(100) >= Occultism.SERVER_CONFIG.spiritJobs.traderWonderingChance.getAsInt())
            return;
        Level level = trader.level();
        if (level.isClientSide())
            return;
        if (trader.getPersistentData().getBoolean("replaced"))
            return;
        trader.getPersistentData().putBoolean("replaced", true);

        level.playSound(null, trader.blockPosition(), OccultismSounds.START_RITUAL.get(), SoundSource.AMBIENT, 2, 3);
        WonderingTraderEntity wondering = OccultismEntities.WONDERING_TRADER.get().spawn((ServerLevel) level, trader.blockPosition(), EntitySpawnReason.EVENT);
        if (wondering == null)
            return;
        wondering.setDespawnDelay(48000);
        wondering.setReplacedTrader(trader);
        wondering.setPersistenceRequired();
    }
    //endregion Static Methods
}
