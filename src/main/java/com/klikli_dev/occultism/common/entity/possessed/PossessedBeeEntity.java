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

package com.klikli_dev.occultism.common.entity.possessed;

import com.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicInteger;

public class PossessedBeeEntity extends Bee {

    private static final int MAX_BEES_PER_TIME = 10; // Maximum bees allowed
    private static final long TIME_WINDOW_TICKS = 20 * 60; // Time window in ticks (60 ticks = 1 minute in Minecraft)
    private static final AtomicInteger beeSpawnCounter = new AtomicInteger(0);
    private static long lastResetGameTime = 0;

    public PossessedBeeEntity(EntityType<? extends Bee> type,
                              Level worldIn) {
        super(type, worldIn);
    }

    //region Static Methods
    public static AttributeSupplier.Builder createAttributes() {
        return Bee.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.06)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.ATTACK_SPEED, 3);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        DamageSource damagesource = this.damageSources().sting(this);
        boolean flag = entity.hurt(damagesource, (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            if (this.level() instanceof ServerLevel serverlevel) {
                EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }

            if (entity instanceof LivingEntity livingentity) {
                livingentity.setStingerCount(livingentity.getStingerCount() + 1);
                int i = 10;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 20;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 30;
                }
                livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
            }

            this.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
        }

        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            LivingEntity livingentity = this.getTarget();
            if (livingentity != null
                    && (double) this.random.nextFloat() < this.getAttributeValue(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {

                long currentGameTime = this.level().getGameTime();

                // Reset the counter if the time window has passed
                synchronized (PossessedBeeEntity.class) {
                    if (currentGameTime - lastResetGameTime > TIME_WINDOW_TICKS) {
                        beeSpawnCounter.set(0);
                        lastResetGameTime = currentGameTime;
                    }
                }

                // Check if the spawn limit has been reached
                if (beeSpawnCounter.get() < MAX_BEES_PER_TIME) {
                    Bee bee = OccultismEntities.POSSESSED_BEE.get().create(this.level());
                    double offsetX = this.level().getRandom().nextGaussian() * (1 + this.level().getRandom().nextInt(4));
                    double offsetZ = this.level().getRandom().nextGaussian() * (1 + this.level().getRandom().nextInt(4));
                    bee.absMoveTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ, this.level().getRandom().nextInt(360), 0);
                    this.level().addFreshEntity(bee);

                    // Increment the counter
                    beeSpawnCounter.incrementAndGet();
                }
            }
            return super.hurt(source, amount);
        }
    }
}
