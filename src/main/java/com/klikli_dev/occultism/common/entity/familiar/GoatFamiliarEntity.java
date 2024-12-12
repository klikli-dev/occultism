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

package com.klikli_dev.occultism.common.entity.familiar;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

public class GoatFamiliarEntity extends ResizableFamiliarEntity {

    private int shakeHeadTimer;

    public GoatFamiliarEntity(EntityType<? extends GoatFamiliarEntity> type, Level level) {
        super(type, level);
    }

    public GoatFamiliarEntity(Level worldIn, boolean hasRing, boolean hasBeard, byte size, LivingEntity owner) {
        this(OccultismEntities.GOAT_FAMILIAR.get(), worldIn);
        this.setRing(hasRing);
        this.setBeard(hasBeard);
        this.setSize(size);
        this.setFamiliarOwner(owner);
    }

    public static void ringBell(FamiliarEntity entity) {
        LivingEntity owner = entity.getFamiliarOwner();
        if (owner == null || !entity.hasBlacksmithUpgrade())
            return;

        entity.playSound(SoundEvents.BELL_BLOCK, 1, 1);

        for (Mob e : entity.level().getEntitiesOfClass(Mob.class, entity.getBoundingBox().inflate(30),
                e -> e.isAlive() && e.getClassification(false) == MobCategory.MONSTER))
            e.setTarget(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide)
            this.shakeHeadTimer--;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (super.hurt(pSource, pAmount)) {
            if (pSource.getEntity() != null) {
                ringBell(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("variants"))
            this.setRing(compound.getBoolean("hasRing"));
    }

    public boolean hasRing() {
        return this.hasVariant(0);
    }

    private void setRing(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasBeard() {
        return this.hasVariant(1);
    }

    private void setBeard(boolean b) {
        this.setVariant(1, b);
    }

    public boolean isBlack() {
        return this.hasVariant(2);
    }

    private void setBlack(boolean b) {
        this.setVariant(2, b);
    }

    public boolean hasRedEyes() {
        return this.hasVariant(3);
    }

    private void setRedEyes(boolean b) {
        this.setVariant(3, b);
    }

    public boolean hasEvilHorns() {
        return this.hasVariant(4);
    }

    private void setEvilHorns(boolean b) {
        this.setVariant(4, b);
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        boolean isInTransformationBiome = this.isInTransformationBiome(playerIn) || this.isInTransformationBiome(this);
        if (this.isTransformItem(stack) && playerIn == this.getFamiliarOwner()) {
            if (isInTransformationBiome) {

                if (stack.is(Tags.Items.DYES_BLACK))
                    this.setBlack(true);
                else if (stack.getItem() == Items.ENDER_EYE)
                    this.setRedEyes(true);
                else if (stack.getItem() == Items.FLINT)
                    this.setEvilHorns(true);
                if (this.shouldTransform()) {
                    OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.SHUB_NIGGURATH_SUMMON);
                    this.transform();
                }

                if (!this.level().isClientSide) //do this down here because shrink will erase item info and cause checks to fail
                    stack.shrink(1);

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                this.shakeHeadTimer = 20;
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(playerIn, hand);
    }

    private void transform() {
        if (this.level().isClientSide) {
            float scale = this.getScale();
            for (int i = 0; i < 30; i++)
                this.level().addParticle(ParticleTypes.SMOKE, this.getRandomX(scale), this.getRandomY() * scale, this.getRandomZ(scale), 0, 0,
                        0);
        } else {
            ShubNiggurathFamiliarEntity shubNiggurath = new ShubNiggurathFamiliarEntity(this.level(), this);
            shubNiggurath.setCustomName(this.getName());
            this.level().addFreshEntity(shubNiggurath);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    boolean isTransformItem(ItemStack stack) {
        return (stack.is(Tags.Items.DYES_BLACK) && !this.isBlack()) || (stack.getItem() == Items.FLINT && !this.hasEvilHorns())
                || (stack.getItem() == Items.ENDER_EYE && !this.hasRedEyes());
    }

    boolean shouldTransform() {
        return this.isBlack() && this.hasRedEyes() && this.hasEvilHorns();
    }

    @SuppressWarnings("deprecation")
    private boolean isInTransformationBiome(Entity entity) {
        return this.level().getBiome(entity.blockPosition()).is(OccultismTags.ALLOWS_SHUB_NIGGURRATH_TRANSFORMATION);
    }

    public float getNeckYRot(float pPartialTick) {
        if (this.shakeHeadTimer <= 0)
            return 0;
        return Mth.sin((this.shakeHeadTimer - pPartialTick) / 20 * (float) Math.PI * 5) * (float) Math.toRadians(30);
    }
}
