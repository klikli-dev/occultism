package com.klikli_dev.occultism.common.entity.spirit.demonicpartner;

import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DemonicPartner extends TamableAnimal {

    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(DemonicPartner.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Long> HEART_TIME = SynchedEntityData.defineId(DemonicPartner.class, EntityDataSerializers.LONG);
    protected Optional<RecipeHolder<SmokingRecipe>> lastRecipe = Optional.empty();
    protected static final int HEART_INTERVAL = 20 * 60 * 10;
    protected long lastHeartTime;

    protected DemonicPartner(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.ATTACK_DAMAGE, 9.0D);
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, @NotNull DamageSource pDamageSource, boolean pAttackedRecently) {
        super.dropFromLootTable(level, pDamageSource, pAttackedRecently);

        var owner = this.getOwner();

        var shard = new ItemStack(OccultismItems.SOUL_SHARD_ITEM.get());

        var health = this.getHealth();
        this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
        this.resetFallDistance();
        this.removeAllEffects();

        var entityData = new net.minecraft.nbt.CompoundTag();
                var id = this.getEncodeId();
        if(id != null)
            entityData.putString("id", id);
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, this.registryAccess());
        this.saveWithoutId(output);
        entityData.merge(output.buildResult());

        shard.set(DataComponents.ENTITY_DATA, TypedEntityData.of(this.getType(), entityData));
        this.setHealth(health);

        if(owner instanceof Player player){
            ItemTransferUtil.giveItemToPlayer(player, shard);
        }
        else {
            ItemEntity entityItem = new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), shard);
            entityItem.setPickUpDelay(5);
            entityItem.setDeltaMovement(entityItem.getDeltaMovement().multiply(0, 1, 0));

            this.level().addFreshEntity(entityItem);
        }
    }

    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_LYING, false).define(HEART_TIME, (long) 0);
    }
    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(HEART_TIME, input.getLongOr("heartLastTime", 0L));
    }
    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putLong("heartLastTime", this.getHeartTime());
    }

    private void setHeartTime(long b) {
        this.entityData.set(HEART_TIME, b);
    }

    private long getHeartTime() {
        return this.entityData.get(HEART_TIME);
    }

    public boolean isLying() {
        return this.entityData.get(IS_LYING);
    }

    public void setLying(boolean pLying) {
        this.entityData.set(IS_LYING, pLying);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new DemonicPartnerLieNextToPartnerGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.7D, true));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(OccultismTags.Items.DEMONIC_PARTNER_FOOD);
    }

    public Optional<RecipeHolder<SmokingRecipe>> getRecipe(ItemStack pStack) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return Optional.empty();
        return serverLevel.recipeAccess().getRecipeFor(RecipeType.SMOKING,
                new SingleRecipeInput(pStack), serverLevel);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();

        if (this.level().isClientSide() && this.swinging) {
            Vec3 direction = Vec3.directionFromRotation(this.getRotationVector()).scale(0.6);
            for (int i = 0; i < 5; i++) {
                Vec3 pos = this.position().add(direction.x + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                        1.5 + (this.getRandom().nextFloat() - 0.5f) * 0.7, direction.z + (this.getRandom().nextFloat() - 0.5f) * 0.7);
                this.level().addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.25, 0, direction.z * 0.25);
            }
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (this.level().isClientSide()) {
            boolean willInteract = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.DIAMOND) && !this.isTame();
            return willInteract ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        if (this.isTame() && this.isOwnedBy(pPlayer)) {

            if (itemstack.is(OccultismItems.CURSED_HONEY.asItem())) {
                long time =  this.getHeartTime() + HEART_INTERVAL - this.level().getGameTime();
                if (time < 0) {
                    this.setHeartTime(this.level().getGameTime());
                    this.lastHeartTime = this.level().getGameTime();
                    itemstack.shrink(1);
                    ItemTransferUtil.giveItemToPlayer(pPlayer, new ItemStack(OccultismItems.SWEET_HONEY_HEART.asItem()));
                } else {
                    pPlayer.sendOverlayMessage(Component.translatable("dialog.occultism.partner.heart_on_cooldown", time));
                }
                return InteractionResult.SUCCESS;
            }

            var effects = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (effects.hasEffects()) {
                for (var instance : effects.getAllEffects()) {
                    if (instance.getEffect().value().isInstantenous()) {
                        instance.getEffect().value().applyInstantenousEffect((ServerLevel) this.level(), this, this, pPlayer, instance.getAmplifier() + 2, 1.0D);
                    } else {
                        pPlayer.addEffect(new MobEffectInstance(instance.getEffect(), instance.getDuration() * 5, instance.getAmplifier(), instance.isAmbient(), instance.isVisible()));
                    }
                }

                if (!pPlayer.isCreative()) {
                    itemstack.shrink(1);
                    var remainder = itemstack.getItem().getCraftingRemainder();
                    if (remainder == null) {
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(Items.GLASS_BOTTLE));
                    } else {
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, remainder.create());
                    }
                }

                return InteractionResult.SUCCESS;
            }

            var effectsStew = itemstack.getOrDefault(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);
            if (!effectsStew.effects().isEmpty()) {
                //Spoiler: int buff = hasCrown() ? 1 : 0;
                for (var instance : effectsStew.effects()) {
                    if (instance.effect().value().isInstantenous()) {
                        instance.effect().value().applyInstantenousEffect((ServerLevel) this.level(), this, this, pPlayer, 1 /*+ buff*/, 1.0D);
                    } else {
                        pPlayer.addEffect(new MobEffectInstance(instance.effect(), instance.duration() * (50 /*+ 25*buff*/), 0 /*buff*/, false, false));
                    }
                }
                //Double nutrition
                pPlayer.getFoodData().eat(Foods.SUSPICIOUS_STEW);
                pPlayer.getFoodData().eat(Foods.SUSPICIOUS_STEW);

                if (!pPlayer.isCreative()) {
                    itemstack.shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(Items.BOWL));
                }

                return InteractionResult.SUCCESS;
            }

            //cook raw food
            var recipe = this.lastRecipe.isPresent() ? this.lastRecipe.get().value().input().test(itemstack) ? this.lastRecipe : this.getRecipe(itemstack) : this.getRecipe(itemstack);
            if (recipe.isPresent()) {
                this.lastRecipe = recipe;
                var result = recipe.get().value().assemble(new SingleRecipeInput(itemstack));

                if (pPlayer.isShiftKeyDown()) 
                {
                    var multiResult = result.copy();
                    multiResult.setCount(result.getCount() * itemstack.getCount());

                    if (!pPlayer.isCreative()) {
                        itemstack.shrink(itemstack.getCount());
                    }
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, multiResult);
                }
                else
                {
                    if (!pPlayer.isCreative()) {
                        itemstack.shrink(1);
                    }
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, result);
                }

                for (int i = 0; i < 2; i++) {
                    Vec3 pos = this.position().add((this.getRandom().nextFloat() - 0.5f) * 0.7,
                            1.5 + (this.getRandom().nextFloat() - 0.5f) * 0.7, (this.getRandom().nextFloat() - 0.5f) * 0.7);
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
                }


                return InteractionResult.SUCCESS;
            }

            //heal with food
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                FoodProperties foodProperties = itemstack.get(DataComponents.FOOD);
                this.heal(foodProperties != null ? (float)foodProperties.nutrition() : 1.0F);
                if (!pPlayer.isCreative()) {
                    itemstack.shrink(1);
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }

            //sit/stand
            InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
            if ((!interactionresult.consumesAction() || this.isBaby()) && itemstack.isEmpty()) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return InteractionResult.SUCCESS;
            } else {
                return interactionresult;
            }
        } else if (itemstack.is(Items.DIAMOND)) {
            //tame with a diamond
            if (!pPlayer.isCreative()) {
                itemstack.shrink(1);
            }

            if (!net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte) 7);
            } else {
                this.level().broadcastEntityEvent(this, (byte) 6);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, @NotNull Entity pEntity) {
        boolean flag = super.doHurtTarget(level, pEntity);

        pEntity.setRemainingFireTicks(2 * 20);

        this.heal(1);

        return flag;
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, @NotNull DamageSource source) {
        return super.isInvulnerableTo(level, source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FLY_INTO_WALL);
    }
}
