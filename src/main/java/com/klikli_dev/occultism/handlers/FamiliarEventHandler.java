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
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.entity.familiar.*;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;

import java.util.List;

@EventBusSubscriber(modid = Occultism.MODID, bus = EventBusSubscriber.Bus.GAME)
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
    public static void beaverHarvest(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();

        if (!event.getState().is(BlockTags.LOGS))
            return;

        if (!player.hasEffect(OccultismEffects.BEAVER_HARVEST))
            return;

        int level = player.getEffect(OccultismEffects.BEAVER_HARVEST).getAmplifier();

        event.setNewSpeed(event.getNewSpeed() * (level + 3));
    }


    @SubscribeEvent
    public static void dodge(LivingHurtEvent event) {
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
            OccultismAdvancements.FAMILIAR.get().trigger(entity, FamiliarTrigger.Type.MUMMY_DODGE);
    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        guardianUltimateSacrifice(event);
        headlessStealHead(event);
        fairySave(event);
        lifesteal(event);
    }

    private static void lifesteal(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker))
            return;

        if (!attacker.hasEffect(OccultismEffects.BAT_LIFESTEAL))
            return;

        attacker.heal(1 + attacker.getEffect(OccultismEffects.BAT_LIFESTEAL).getAmplifier());
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

        event.setCanceled(true);
        entity.setHealth(2);
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 5, 2));

        if (!owner.level().isClientSide)
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.FAIRY_SAVE);
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player))
            return;

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        EntityType<?> headType = event.getEntity().getType();

        if (!FamiliarUtil.hasFamiliar(player, OccultismEntities.HEADLESS_FAMILIAR.get(),
                h -> h.getHeadType() == headType))
            return;

        event.setAmount(event.getAmount() * 1.3f);
    }

    private static void headlessStealHead(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player))
            return;

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        List<HeadlessFamiliarEntity> headlesses = FamiliarUtil.getAllFamiliars(player,
                OccultismEntities.HEADLESS_FAMILIAR.get());

        if (!headlesses.isEmpty() && event.getEntity().getType() == OccultismEntities.CTHULHU_FAMILIAR.get())
            OccultismAdvancements.FAMILIAR.get().trigger(player, FamiliarTrigger.Type.HEADLESS_CTHULHU_HEAD);

        headlesses.forEach(h -> h.setHeadType(event.getEntity().getType()));
    }

    private static void guardianUltimateSacrifice(LivingDeathEvent event) {
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !(event.getEntity() instanceof Player player))
            return;

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
    public static void beholderBlindnessImmune(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().getEffect() != MobEffects.BLINDNESS)
            return;

        LivingEntity entity = event.getEntity();
        EntityType<BeholderFamiliarEntity> beholder = OccultismEntities.BEHOLDER_FAMILIAR.get();

        if (!FamiliarUtil.hasFamiliar(entity, beholder, FamiliarEntity::hasBlacksmithUpgrade))
            return;

        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }
}
