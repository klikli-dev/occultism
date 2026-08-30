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

package com.klikli_dev.occultism.common.entity.familiar;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.common.data.FamiliarEffects;
import com.klikli_dev.occultism.common.item.familiar.FamiliarCurio;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class OtherworldBirdEntity extends Parrot implements IFamiliar {
    // region Fields
    final List<FamiliarEffects.FamiliarEffectDefinition> effectDefinitionList = FamiliarEffects.effectMap().get(this.getType());
    private static final Identifier HEALTH_BONUS = Identifier.fromNamespaceAndPath(Occultism.MODID, "upgraded_health_bonus");
    private static final Identifier HEALTH_BONUS_IESNIUM = Identifier.fromNamespaceAndPath(Occultism.MODID, "iesnium_health_bonus");
    private static final float MAX_BOOST_DISTANCE = 8f;
    private static final int FEATHER_COOLDOWN = 5 * 60 * 20;
    private static final int FEATHER_COOLDOWN_UPGRADED = 2 * 60 * 20;
    private static final int FEATHER_COOLDOWN_IESNIUM = 30 * 20;
    private static final EntityDataAccessor<Boolean> BLACKSMITH_UPGRADE = SynchedEntityData.defineId(OtherworldBirdEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IESNIUM_UPGRADE = SynchedEntityData.defineId(OtherworldBirdEntity.class, EntityDataSerializers.BOOLEAN);
    public SitWhenOrderedToGoal sitGoal;
    // endregion Fields

    // region Initialization
    public OtherworldBirdEntity(EntityType<? extends Parrot> type, Level worldIn) {
        super(type, worldIn);
    }
    // endregion Initialization

    // region Static Methods
    public static AttributeSupplier.Builder createAttributes() {
        return Parrot.createAttributes()
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 15.0)
                .add(Attributes.ARMOR_TOUGHNESS, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.FOLLOW_RANGE, 30);
    }
    // endregion Static Methods

    // region Data Methods
    @Override
    protected void defineSynchedData(@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BLACKSMITH_UPGRADE, false);
        builder.define(IESNIUM_UPGRADE, false);
    }

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setBlacksmithUpgrade(input.getBooleanOr("hasBlacksmithUpgrade", false));
        this.setIesniumUpgrade(input.getBooleanOr("hasIesniumUpgrade", false));
    }

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("hasBlacksmithUpgrade", this.hasBlacksmithUpgrade());
        output.putBoolean("hasIesniumUpgrade", this.hasIesniumUpgrade());
    }
    // endregion Data Methods

    // region Vanilla Overrides
    @Override
    protected void registerGoals() {
        // same as parrot, except we don't land on shoulders.
        this.sitGoal = new SitWhenOrderedToGoal(this);
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
    }

    @Override
    public void aiStep() {
        if (this.level().getGameTime() % 1024 == 0)
            this.heal(1F);

        // Every 10 ticks, attempt to refresh the owner buff
        if (!this.level().isClientSide() && this.level().getGameTime() % 10 == 0 && this.isTame()) {
            LivingEntity owner = this.getOwner();
            if (owner != null && this.distanceTo(owner) < MAX_BOOST_DISTANCE) {
                // close enough to boost
                for (MobEffectInstance effect : this.getFamiliarEffects())
                    owner.addEffect(effect);
            }
        }

        super.aiStep();
    }

    @Override
    public @NonNull InteractionResult mobInteract(Player playerIn, @NonNull InteractionHand hand) {
        if (this.getOwner() == null)
            this.setFamiliarOwner(playerIn);

        ItemStack stack = playerIn.getItemInHand(hand);
        if (stack.getItem() instanceof FamiliarCurio) {
            return stack.interactLivingEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            if (playerIn.isShiftKeyDown() && !this.level().isClientSide()) {
                if (this.hasBlacksmithUpgrade()) {
                    if (this.hasIesniumUpgrade()) {
                        this.setBlacksmithUpgrade(false);
                        this.setIesniumUpgrade(false);
                    } else {
                        this.iesniumUpgrade();
                    }
                } else {
                    this.blacksmithUpgrade();
                }
            } else {
                this.setFamiliarOwner(playerIn);
                this.setTame(true, true);
            }
            return this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        if (this.isOwnedBy(playerIn) && playerIn.isShiftKeyDown()) {
            if (this.age >= 0) {
                ItemStack feather = OccultismItems.AWAKENED_FEATHER.toStack();
                Optional<HolderSet.Named<Item>> a = this.registryAccess().get(Tags.Items.FEATHERS);
                if (a.isPresent() && this.getRandom().nextFloat() > 0.1F) {
                    feather = a.get().get(this.getRandom().nextInt(a.get().size())).value().getDefaultInstance();
                }
                playerIn.addItem(feather);
                int cooldown = this.hasIesniumUpgrade() ? FEATHER_COOLDOWN_IESNIUM :
                        this.hasBlacksmithUpgrade() ? FEATHER_COOLDOWN_UPGRADED : FEATHER_COOLDOWN;
                this.setAge(-cooldown);
            } else {
                playerIn.sendSystemMessage(Component.translatable("dialog.occultism.otherworldbird.feather_on_cooldown_" + this.getRandom().nextInt(3)));
            }
            return this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }

        return super.mobInteract(playerIn, hand);
    }

    @Override
    public boolean canAgeUp() {
        return this.getAge() < 0;
    }
    // endregion Vanilla Overrides

    // region IFamiliar Overrides
    @Override
    public LivingEntity getFamiliarOwner() {
        return this.getOwner();
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        this.setOwner(owner);
        if (owner instanceof Player player)
            this.tame(player);
    }

    @Override
    public @NonNull Entity getFamiliarEntity() {
        return this;
    }

    @Override
    public @NonNull Iterable<MobEffectInstance> getFamiliarEffects() {
        if (this.effectDefinitionList == null || this.effectDefinitionList.isEmpty() || this.getOwner() == null)
            return List.of();

        FamiliarSettingsData data = this.getOwner().getExistingDataOrNull(OccultismDataStorage.FAMILIAR_SETTINGS.get());
        List<MobEffectInstance> effects = new ArrayList<>(this.effectDefinitionList.size());
        for (var effect : this.effectDefinitionList) {
            int amp = effect.getValue(this);
            amp = Math.min(amp, data.getEffectAmplifier(this.getFamiliarEntity().getType(), effect.effect()));
            if (amp >= 0) {
                effects.add(new MobEffectInstance(effect.effect(), 300, amp, false, false));
            }
        }
        return effects;
    }

    @Override
    public boolean hasBlacksmithUpgrade() {
        return this.entityData.get(BLACKSMITH_UPGRADE);
    }

    @Override
    public boolean hasIesniumUpgrade() {
        return this.entityData.get(IESNIUM_UPGRADE);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    public boolean canIesniumUpgrade() {
        return this.hasBlacksmithUpgrade() && !this.hasIesniumUpgrade();
    }

    @Override
    public void blacksmithUpgrade() {
        if (this.getOwner() instanceof Player player)
            player.sendSystemMessage(Component.translatable(String.format("message.%s.familiar.upgraded", Occultism.MODID), this.getName()));
        if (!this.hasIesniumUpgrade() && this.hasCustomName())
            this.setCustomName(Component.empty().append(this.getName()).append(" ⛤"));

        AttributeModifier health = new AttributeModifier(HEALTH_BONUS, 20, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance instanceHp = this.getAttribute(Attributes.MAX_HEALTH);
        if (instanceHp != null && !instanceHp.hasModifier(HEALTH_BONUS))
            instanceHp.addPermanentModifier(health);

        this.setBlacksmithUpgrade(true);
    }

    @Override
    public void iesniumUpgrade() {
        if (this.getOwner() instanceof Player player)
            player.sendSystemMessage(Component.translatable(String.format("message.%s.familiar.iesnium_upgraded", Occultism.MODID), this.getName()));
        if (!this.hasIesniumUpgrade() && this.hasCustomName())
            this.setCustomName(Component.empty().append("⛤ ").append(this.getName()));

        AttributeModifier health = new AttributeModifier(HEALTH_BONUS_IESNIUM, 50, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance instanceHp = this.getAttribute(Attributes.MAX_HEALTH);
        if (instanceHp != null && !instanceHp.hasModifier(HEALTH_BONUS_IESNIUM))
            instanceHp.addPermanentModifier(health);

        this.setIesniumUpgrade(true);
    }
    // endregion IFamiliar Overrides

    // region privates
    private void setBlacksmithUpgrade(boolean b) {
        this.entityData.set(BLACKSMITH_UPGRADE, b);
    }

    private void setIesniumUpgrade(boolean b) {
        this.entityData.set(IESNIUM_UPGRADE, b);
    }
    // endregion privates
}

