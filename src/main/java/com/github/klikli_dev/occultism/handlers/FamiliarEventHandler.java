/*
 * MIT License
 *
 * Copyright 2021 vemerion
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
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.common.entity.*;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FamiliarEventHandler {

    @SubscribeEvent
    public static void beaverFindTree(SaplingGrowTreeEvent event) {
        LevelAccessor world = event.getWorld();
        BlockPos pos = event.getPos();
        List<BeaverFamiliarEntity> beavers = event.getWorld().getEntitiesOfClass(BeaverFamiliarEntity.class,
                new AABB(pos).inflate(30), b -> !b.isSitting() && b.isEffectEnabled(b.getFamiliarOwner()));

        if(!beavers.isEmpty()) {
            BeaverFamiliarEntity beaver = beavers.get(world.getRandom().nextInt(beavers.size()));

            beaver.setTreeTarget(pos);
        }
    }

    @SubscribeEvent
    public static void beaverHarvest(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();

        if (!event.getState().is(BlockTags.LOGS))
            return;

        if (!player.hasEffect(OccultismEffects.BEAVER_HARVEST.get()))
            return;

        int level = player.getEffect(OccultismEffects.BEAVER_HARVEST.get()).getAmplifier();

        event.setNewSpeed(event.getNewSpeed() * (level + 3));
    }


    @SubscribeEvent
    public static void dodge(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!entity.hasEffect(OccultismEffects.MUMMY_DODGE.get()))
            return;

        DamageSource source = event.getSource();

        if (!(source instanceof EntityDamageSource) || source.isExplosion() || source.isBypassInvul())
            return;

        int level = entity.getEffect(OccultismEffects.MUMMY_DODGE.get()).getAmplifier();
        boolean dodge = entity.getRandom().nextDouble() < (level + 1) * 0.1f;
        event.setCanceled(dodge);

        if (dodge)
            OccultismAdvancements.FAMILIAR.trigger(entity, FamiliarTrigger.Type.MUMMY_DODGE);
    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        guardianUltimateSacrifice(event);
        headlessStealHead(event);
        fairySave(event);
        lifesteal(event);
    }

    private static void lifesteal(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity))
            return;

        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

        if (!attacker.hasEffect(OccultismEffects.BAT_LIFESTEAL.get()))
            return;

        attacker.heal(1 + attacker.getEffect(OccultismEffects.BAT_LIFESTEAL.get()).getAmplifier());
    }

    private static void fairySave(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (event.getSource().isBypassInvul() || !(entity instanceof IFamiliar)
                || entity.getType() == OccultismEntities.GUARDIAN_FAMILIAR.get()
                || entity.getType() == OccultismEntities.FAIRY_FAMILIAR.get())
            return;

        IFamiliar familiar = (IFamiliar) entity;

        LivingEntity owner = familiar.getFamiliarOwner();

        if (owner == null || !FamiliarUtil.isFamiliarEnabled(owner, OccultismEntities.FAIRY_FAMILIAR.get()))
            return;

        FairyFamiliarEntity fairy = FamiliarUtil.getFamiliar(owner, OccultismEntities.FAIRY_FAMILIAR.get());

        if (fairy == null || !fairy.saveFamiliar(familiar))
            return;

        event.setCanceled(true);
        entity.setHealth(2);
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 5, 2));

        if (!owner.level.isClientSide)
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.FAIRY_SAVE);
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player))
            return;
        Player player = (Player) event.getSource().getEntity();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        EntityType<?> headType = event.getEntityLiving().getType();

        if (!FamiliarUtil.hasFamiliar(player, OccultismEntities.HEADLESS_FAMILIAR.get(),
                h -> h.getHeadType() == headType))
            return;

        event.setAmount(event.getAmount() * 1.3f);
    }

    private static void headlessStealHead(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player))
            return;
        Player player = (Player) event.getSource().getEntity();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        List<HeadlessFamiliarEntity> headlesses = FamiliarUtil.getAllFamiliars(player,
                OccultismEntities.HEADLESS_FAMILIAR.get());

        if (!headlesses.isEmpty() && event.getEntityLiving().getType() == OccultismEntities.CTHULHU_FAMILIAR.get())
            OccultismAdvancements.FAMILIAR.trigger(player, FamiliarTrigger.Type.HEADLESS_CTHULHU_HEAD);

        headlesses.forEach(h -> h.setHeadType(event.getEntityLiving().getType()));
    }

    private static void guardianUltimateSacrifice(LivingDeathEvent event) {
        if (event.getSource().isBypassInvul() || !(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.GUARDIAN_FAMILIAR.get()))
            return;

        GuardianFamiliarEntity guardian = FamiliarUtil.getFamiliar(player, OccultismEntities.GUARDIAN_FAMILIAR.get());
        if (guardian == null)
            return;

        if (!guardian.sacrifice())
            return;

        event.setCanceled(true);
        player.setHealth(1);
        player.removeAllEffects();
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 10, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 5, 1));
    }

    @SubscribeEvent
    public static void beholderBlindnessImmune(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getEffect() != MobEffects.BLINDNESS)
            return;

        LivingEntity entity = event.getEntityLiving();
        EntityType<BeholderFamiliarEntity> beholder = OccultismEntities.BEHOLDER_FAMILIAR.get();

        if (!FamiliarUtil.hasFamiliar(entity, beholder, FamiliarEntity::hasBlacksmithUpgrade))
            return;

        event.setResult(Result.DENY);
    }
}
