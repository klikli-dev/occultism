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
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@EventBusSubscriber(modid = Occultism.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LootEventHandler {

    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent event) {
        if (event.getDroppedExperience() == 0)
            return;

        Player attackingPlayer = event.getAttackingPlayer();
        if (attackingPlayer != null) {
            MobEffectInstance greed = attackingPlayer.getEffect(OccultismEffects.DRAGON_GREED);
            if (greed == null)
                return;
            event.setDroppedExperience(event.getDroppedExperience() + greed.getAmplifier() + 1);
        }
    }

    @SubscribeEvent
    public static void giveStoneToBlacksmith(ItemEntityPickupEvent.Pre event) {
        ItemEntity entity = event.getItemEntity();
        ItemStack stack = entity.getItem();

        if (!(stack.is(Tags.Items.COBBLESTONES) || stack.is(Tags.Items.STONES)))
            return;

        Player player = event.getPlayer();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.BLACKSMITH_FAMILIAR.get()) || !FamiliarUtil.hasFamiliar(player, OccultismEntities.BLACKSMITH_FAMILIAR.get()))
            return;

        if (player.getRandom().nextDouble() < Occultism.SERVER_CONFIG.spiritJobs.blacksmithFamiliarRepairChance.get() * stack.getCount())
            repairEquipment(player);

        event.setCanPickup(TriState.FALSE);
        entity.remove(Entity.RemovalReason.DISCARDED);
    }

    private static void repairEquipment(Player player) {
        for (ItemStack stack : player.getAllSlots()) {
            if (!stack.isDamaged())
                continue;
            stack.setDamageValue(stack.getDamageValue() - 2);
            return;
        }
    }


}
