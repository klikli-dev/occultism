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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.BlacksmithFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.IFamiliar;
import com.github.klikli_dev.occultism.common.item.tool.ButcherKnifeItem;
import com.github.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootEventHandler {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        //Add butcher knife drops dynamically.
        //TODO: Consider doing a global loot table for that
        if (event.isRecentlyHit() && event.getSource().getDirectEntity() instanceof LivingEntity) {
            LivingEntity trueSource = (LivingEntity) event.getSource().getDirectEntity();
            ItemStack knifeItem = trueSource.getItemInHand(InteractionHand.MAIN_HAND);
            if (knifeItem.getItem() == OccultismItems.BUTCHER_KNIFE.get()) {
                List<ItemStack> loot = ButcherKnifeItem.getLoot(event.getEntityLiving(), knifeItem, trueSource);
                Random rand = event.getEntityLiving().getRandom();

                if (!loot.isEmpty()) {
                    for (ItemStack stack : loot) {
                        ItemStack copy = stack.copy();
                        copy.setCount(rand.nextInt(stack.getCount() + 1) + rand.nextInt(event.getLootingLevel() + 1));
                        Vec3 center = Math3DUtil.center(event.getEntityLiving().blockPosition());
                        event.getDrops()
                                .add(new ItemEntity(event.getEntityLiving().level, center.x, center.y, center.z, copy));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent event) {
        if (event.getDroppedExperience() == 0)
            return;

        Player attackingPlayer = event.getAttackingPlayer();
        if (attackingPlayer != null) {
            MobEffectInstance greed = attackingPlayer.getEffect(OccultismEffects.DRAGON_GREED.get());
            if (greed == null)
                return;
            event.setDroppedExperience(event.getDroppedExperience() + greed.getAmplifier() + 1);
        }
    }

    @SubscribeEvent
    public static void giveStoneToBlacksmith(EntityItemPickupEvent event) {
        ItemEntity entity = event.getItem();
        ItemStack stack = entity.getItem();
        Item item = stack.getItem();

        if (!(Tags.Items.COBBLESTONE.contains(item) || Tags.Items.STONE.contains(item)))
            return;

        Player player = event.getPlayer();

        if (!isBlacksmithEnabled(player) || !hasBlacksmith(player))
            return;

        if (player.getRandom().nextDouble() < 0.01 * stack.getCount())
            repairEquipment(player);

        event.setCanceled(true);
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

    private static boolean isBlacksmithEnabled(Player player) {
        return player.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS)
                .lazyMap(c -> c.isFamiliarEnabled(OccultismEntities.BLACKSMITH_FAMILIAR.get())).orElse(false);
    }

    private static boolean hasBlacksmith(Player player) {
        return hasEquippedBlacksmith(player) || hasNearbyBlacksmith(player);
    }

    private static boolean hasNearbyBlacksmith(Player player) {
        return !player.level.getEntitiesOfClass(BlacksmithFamiliarEntity.class, player.getBoundingBox().inflate(10),
                e -> e.getFamiliarOwner() == player).isEmpty();
    }

    private static boolean hasEquippedBlacksmith(Player player) {
        return CuriosApi.getCuriosHelper().getEquippedCurios(player).map(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) {
                IFamiliar familiar = FamiliarRingItem.getFamiliar(handler.getStackInSlot(i), player.level);
                if (familiar instanceof BlacksmithFamiliarEntity)
                    return true;
            }
            return false;
        }).orElse(false);
    }
}
