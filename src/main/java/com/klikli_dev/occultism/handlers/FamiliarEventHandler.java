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

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger.Type;
import com.klikli_dev.occultism.common.entity.familiar.*;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;

import java.util.List;

@EventBusSubscriber(modid = Occultism.MODID)
public class FamiliarEventHandler {

    @SubscribeEvent
    public static void beaverFindTree(BlockGrowFeatureEvent event) {
        LevelAccessor world = event.getLevel();
        BlockPos pos = event.getPos();
        List<BeaverFamiliarEntity> beavers = event.getLevel().getEntitiesOfClass(BeaverFamiliarEntity.class,
                new AABB(pos).inflate(30), b -> !b.isSitting() && b.isEffectEnabled(b.getFamiliarOwner()));

        if (!beavers.isEmpty()) {
            BeaverFamiliarEntity beaver = beavers.get(world.getRandom().nextInt(beavers.size()));

            beaver.setTreeTarget(pos);
        }
    }

    @SubscribeEvent
    public static void beaverHarvest(BreakSpeed event) {
        Player player = event.getEntity();

        if (!event.getState().is(BlockTags.LOGS))
            return;

        if (!player.hasEffect(OccultismEffects.BEAVER_HARVEST))
            return;

        int level = player.getEffect(OccultismEffects.BEAVER_HARVEST).getAmplifier();

        event.setNewSpeed(event.getNewSpeed() * (level + 3));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void greedyHarvest(BreakSpeed event) {
        if (!event.getState().is(BlockTags.MINEABLE_WITH_PICKAXE))
            return;

        Player player = event.getEntity();
        if (player.hasEffect(OccultismEffects.GREEDY_HARVEST)) {
            int lvl = 1 + player.getEffect(OccultismEffects.GREEDY_HARVEST).getAmplifier();
            float hard = event.getPosition().isPresent() ? event.getState().getDestroySpeed(player.level(), event.getPosition().get()) : 1;
            event.setNewSpeed(2 * lvl * lvl * hard);
        }
    }

    @SubscribeEvent
    public static void keepFarmland_FAMILIAR(BlockEvent.FarmlandTrampleEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;

        if (entity.hasControllingPassenger())
            entity = entity.getControllingPassenger();

        if (!(entity instanceof Player))
            return;

        if (FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.DRIKWING_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.DRIKWING_FAMILIAR_TYPE.get(), DrikwingEntity::hasBlacksmithUpgrade))
            event.setCanceled(true);

        if (FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.WINGNIS_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.WINGNIS_FAMILIAR_TYPE.get()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void headlessEndermanEvent(EnderManAngerEvent event) {
        if (event.getPlayer().hasEffect(OccultismEffects.PUMPKIN_HEAD))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void familiarFriends(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity target = event.getOriginalAboutToBeSetTarget();
        if (target == null || event.isCanceled())
            return;

        if (entity.is(OccultismTags.Entities.FRIENDS_AQUATIC) && target.hasEffect(OccultismEffects.AQUATIC_LORD))
            event.setCanceled(true);

        if (entity.is(OccultismTags.Entities.FRIENDS_FOREST) && target.hasEffect(OccultismEffects.FOREST_WHISPERER))
            event.setCanceled(true);

        if (entity.is(OccultismTags.Entities.FRIENDS_ABERRATIONS) && target.hasEffect(OccultismEffects.HERALD_ABERRATIONS))
            event.setCanceled(true);

        if (entity.is(OccultismTags.Entities.FRIENDS_NETHER) && target.hasEffect(OccultismEffects.NETHER_EMPEROR))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void familiarEffectsImmunity(Applicable event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> effect = event.getEffectInstance().getEffect();
        if (effect == MobEffects.BLINDNESS && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.BEHOLDER_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.BEHOLDER_FAMILIAR.get(), FamiliarEntity::hasBlacksmithUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.DARKNESS && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.BEHOLDER_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.BEHOLDER_FAMILIAR.get(), FamiliarEntity::hasIesniumUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.LEVITATION && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.WINGNIS_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.WINGNIS_FAMILIAR.get(), OtherworldBirdEntity::hasBlacksmithUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.MINING_FATIGUE && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.GREEDY_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.GREEDY_FAMILIAR.get(), FamiliarEntity::hasIesniumUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.SLOWNESS && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.DEER_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.DEER_FAMILIAR.get(), FamiliarEntity::hasBlacksmithUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.WEAKNESS && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.HEADLESS_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.HEADLESS_FAMILIAR.get(), FamiliarEntity::hasIesniumUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.BAD_OMEN && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.DRAGON_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.DRAGON_FAMILIAR.get(), FamiliarEntity::hasIesniumUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.RAID_OMEN && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.DRAGON_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.DRAGON_FAMILIAR.get(), FamiliarEntity::hasIesniumUpgrade))
            event.setResult(Result.DO_NOT_APPLY);

        if (effect == MobEffects.TRIAL_OMEN && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.DRAGON_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(entity, OccultismEntities.DRAGON_FAMILIAR.get(), FamiliarEntity::hasIesniumUpgrade))
            event.setResult(Result.DO_NOT_APPLY);
    }

    @SubscribeEvent
    public static void fairyExtraHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(OccultismEffects.FAIRY_BLESS)) {
            float x = 0.2F * entity.getEffect(OccultismEffects.FAIRY_BLESS).getAmplifier();
            event.setAmount(event.getAmount() * (1+x));
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        safeFall(event);
        dodge(event);
        headlessExtraDamage(event);
        fairyDamageConversion(event);
    }

    public static void safeFall(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        DamageSource source = event.getSource();
        if (!source.is(DamageTypeTags.IS_FALL) && !source.is(DamageTypes.FLY_INTO_WALL))
            return;

        if (FamiliarUtil.hasFamiliar(entity, OccultismEntities.DRIKWING_FAMILIAR_TYPE.get(), DrikwingEntity::hasBlacksmithUpgrade)
                && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.DRIKWING_FAMILIAR.get()))
            event.setCanceled(true);

        if (FamiliarUtil.hasFamiliar(entity, OccultismEntities.WINGNIS_FAMILIAR_TYPE.get())
                && FamiliarUtil.isFamiliarEnabled(entity, OccultismEntities.WINGNIS_FAMILIAR.get()))
            event.setCanceled(true);
    }

    public static void dodge(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (!entity.hasEffect(OccultismEffects.MUMMY_DODGE))
            return;

        DamageSource source = event.getSource();

        if (source.getEntity() == null | source.is(DamageTypeTags.IS_EXPLOSION) || source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return;

        int level = entity.getEffect(OccultismEffects.MUMMY_DODGE).getAmplifier();
        boolean dodge = entity.getRandom().nextDouble() < (level + 1) * 0.1f;
        event.setCanceled(dodge);

        if (dodge)
            OccultismAdvancements.FAMILIAR.get().trigger(entity, Type.MUMMY_DODGE);
    }

    public static void headlessExtraDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player))
            return;

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        EntityType<?> headType = event.getEntity().getType();

        if (!FamiliarUtil.hasFamiliar(player, OccultismEntities.HEADLESS_FAMILIAR.get(),
                h -> h.getHeadType() == headType))
            return;

        event.setAmount(event.getAmount() * 2f);
    }

    public static void fairyDamageConversion(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity sourceEntity)
                || !(sourceEntity.hasEffect(OccultismEffects.FAIRY_BLESS))
                || event.getSource().is(DamageTypes.MAGIC)
                || event.isCanceled())
            return;

        LivingEntity target = event.getEntity();
        if (!(target.level() instanceof ServerLevel serverLevel))
            return;

        float x = 0.1F * sourceEntity.getEffect(OccultismEffects.FAIRY_BLESS).getAmplifier();
        float dmg = event.getAmount();
        event.setAmount(dmg * Math.clamp(1-x, 0, 1));
        target.hurtServer(serverLevel, sourceEntity.damageSources().magic(), dmg * x);
    }

    @SubscribeEvent
    public static void beaverCreaking(AttackEntityEvent event){
        if (event.getTarget() instanceof Creaking creaking && creaking.isAlive()
                && event.getEntity() instanceof Player player && player.level() instanceof ServerLevel level
                && FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.BEAVER_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(player, OccultismEntities.BEAVER_FAMILIAR_TYPE.get(), FamiliarEntity::hasIesniumUpgrade))
            creaking.hurtServer(level, player.damageSources().genericKill(), Integer.MAX_VALUE);
    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        lifeSteal(event);
        fairySave(event);
        headlessStealHead(event);
        guardianUltimateSacrifice(event);
    }

    private static void lifeSteal(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)
                || event.isCanceled())
            return;

        if (attacker.hasEffect(OccultismEffects.BAT_LIFESTEAL)) {
            float maxHp = event.getEntity().getMaxHealth();
            float x = 0.1F * (1 + attacker.getEffect(OccultismEffects.BAT_LIFESTEAL).getAmplifier());
            attacker.heal(maxHp * x);
        }
    }

    private static void fairySave(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !(entity instanceof IFamiliar familiar)
                || entity.getType() == OccultismEntities.GUARDIAN_FAMILIAR.get()
                || entity.getType() == OccultismEntities.FAIRY_FAMILIAR.get())
            return;

        LivingEntity owner = familiar.getFamiliarOwner();

        if (owner == null || !FamiliarUtil.isFamiliarEnabled(owner, OccultismEntities.FAIRY_FAMILIAR.get()))
            return;

        FairyFamiliarEntity fairy = FamiliarUtil.getFamiliar(owner, OccultismEntities.FAIRY_FAMILIAR.get());

        if (fairy == null || !fairy.saveFamiliar(familiar))
            return;

        if (!fairy.isAddedToLevel()) {
            if (entity.hasEffect(OccultismEffects.OCCULT_UNDYING_COOLDOWN)) {
                return;
            } else {
                int i = fairy.hasBlacksmithUpgrade() ? 1 : 2;
                entity.addEffect(new MobEffectInstance(OccultismEffects.OCCULT_UNDYING_COOLDOWN, i * 20 * 20, 0));
            }
        }

        event.setCanceled(true);
        entity.setHealth(2);
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 5, 2));

        if (!owner.level().isClientSide())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, Type.FAIRY_SAVE);
    }

    private static void headlessStealHead(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player))
            return;

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        HeadlessFamiliarEntity headlesses = FamiliarUtil.getNearbyFamiliar(player,
                OccultismEntities.HEADLESS_FAMILIAR.get(), pred -> true);

        if (headlesses == null)
            return;

        if (event.getEntity().getType() == OccultismEntities.CTHULHU_FAMILIAR.get())
            OccultismAdvancements.FAMILIAR.get().trigger(player, Type.HEADLESS_CTHULHU_HEAD);

        headlesses.setHeadType(event.getEntity().getType());
    }

    private static void guardianUltimateSacrifice(LivingDeathEvent event) {
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !(event.getEntity() instanceof Player player))
            return;

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.GUARDIAN_FAMILIAR.get()))
            return;

        GuardianFamiliarEntity guardian = FamiliarUtil.getFamiliar(player, OccultismEntities.GUARDIAN_FAMILIAR.get());
        if (guardian == null)
            return;

        if (!guardian.isAddedToLevel() && player.hasEffect(OccultismEffects.OCCULT_UNDYING_COOLDOWN)) {
            return;
        }

        if (!guardian.sacrifice())
            return;

        event.setCanceled(true);
        player.setHealth(1);
        player.removeAllEffects();
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 10, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 5, 1));
        if (!guardian.isAddedToLevel()) {
            int i = guardian.hasBlacksmithUpgrade() ? 6 : 7;
            i -= guardian.getLives();
            player.addEffect(new MobEffectInstance(OccultismEffects.OCCULT_UNDYING_COOLDOWN, i * 60 * 20, 0, true, true));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    private static void wingnisPower(LivingDeathEvent event) {
        if (event.isCanceled() && event.getEntity() instanceof Player player
                && FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.WINGNIS_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(player, OccultismEntities.WINGNIS_FAMILIAR.get(), OtherworldBirdEntity::hasIesniumUpgrade))
            playerAvoidDeath(player);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    private static void wingnisPower(LivingUseTotemEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof Player player
                && FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.WINGNIS_FAMILIAR.get())
                && FamiliarUtil.hasFamiliar(player, OccultismEntities.WINGNIS_FAMILIAR.get(), OtherworldBirdEntity::hasIesniumUpgrade))
            playerAvoidDeath(player);
    }

    @SubscribeEvent
    public static void birdTransform(LivingUseTotemEvent event) {
        if (!event.isCanceled() && event.getSource().is(DamageTypeTags.IS_FIRE)
                && event.getEntity() instanceof DrikwingEntity bird && bird.canTransform())
            bird.transform();
    }

    private static void playerAvoidDeath(Player player) {
        player.setHealth(player.getMaxHealth());
        player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 60 * 20, 4));
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 60 * 20, 1));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60 * 20, 0));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 5 * 20, 4));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 35 * 20, 9));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 9));
    }
}
