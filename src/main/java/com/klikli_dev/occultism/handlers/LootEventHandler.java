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
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.registry.*;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Occultism.MODID)
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

        if (player.getRandom().nextDouble() < Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarRepairChance.get() * stack.getCount())
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

    @SubscribeEvent
    public static void breakSpecialBlocks(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player.isCreative())
            return;

        event.getState().getTags().forEach(blockTagKey -> {
            if (blockTagKey.equals(OccultismTags.Blocks.OTHERWORLD_COLLECTS)) {
                if (player.getItemInHand(player.getUsedItemHand()).is(OccultismItems.IESNIUM_PICKAXE)
                        || player.getItemInHand(player.getUsedItemHand()).is(OccultismItems.INFUSED_PICKAXE)
                        || CuriosUtil.hasStaff(player)) {
                    Level level = (Level) event.getLevel();
                    BlockPos pos = event.getPos();
                    ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(),
                            new ItemStack(event.getState().getBlock()));
                    level.addFreshEntity(itementity);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
                    event.setCanceled(true);
                }
            }
        });
    }


    @SubscribeEvent
    public static void playerHead(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player
                && event.getSource().getWeaponItem() != null
                && event.getSource().getWeaponItem().is(OccultismTags.Items.TOOLS_KNIFE_IESNIUM)
                && !event.isCanceled()) {

            ItemStack head = new ItemStack(Items.PLAYER_HEAD);
            ResolvableProfile resolvable = new ResolvableProfile(player.getGameProfile());
            head.set(DataComponents.PROFILE, resolvable);
            ItemEntity itemEntity = new ItemEntity(
                    player.level(),
                    player.getX(), player.getY(), player.getZ(),
                    head
            );
            player.level().addFreshEntity(itemEntity);
        }
    }

    @SubscribeEvent
    public static void handleCapturing(LivingDropsEvent event) {
        Entity killer = event.getSource().getEntity();
        LivingEntity killed = event.getEntity();

        if (killer instanceof LivingEntity living) {
            int level = living.getWeaponItem().getEnchantmentLevel(killed.level().holderOrThrow(OccultismEnchantments.FRACTURE_SOUL));
            if (level == 0 || killed.getType().is(OccultismTags.Entities.SOUL_SHATTERED_DENY_LIST) || killed instanceof IFamiliar || killed instanceof Player) {
                return;
            }

            if (killed.level().random.nextFloat() < (float) (level * Occultism.SERVER_CONFIG.itemSettings.shatteredSoulChance.get())) {
                var shard = new ItemStack(OccultismItems.SOUL_SHATTERED_ITEM.get());
                var health = killed.getHealth();
                killed.setHealth(killed.getMaxHealth()); //simulate a healthy mob to avoid death on respawn
                killed.resetFallDistance();
                killed.removeAllEffects();
                var entityData = new CompoundTag();
                var id = killed.getEncodeId();
                if(id != null)
                    entityData.putString("id", id);
                entityData = killed.saveWithoutId(entityData);
                shard.set(DataComponents.ENTITY_DATA, CustomData.of(entityData));
                killed.setHealth(health); //stop healthy simulation to mob die
                event.getDrops().add(new ItemEntity(killed.level(), killed.getX(), killed.getY(), killed.getZ(), shard));
            }
        }
    }
}
