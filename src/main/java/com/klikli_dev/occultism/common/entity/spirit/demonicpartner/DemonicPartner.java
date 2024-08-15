package com.klikli_dev.occultism.common.entity.spirit.demonicpartner;

import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DemonicPartner extends TamableAnimal {

    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(DemonicPartner.class, EntityDataSerializers.BOOLEAN);
    protected Optional<SmokingRecipe> lastRecipe = Optional.empty();

    protected DemonicPartner(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.ATTACK_DAMAGE, 9.0D);
    }

    @Override
    protected void dropFromLootTable(DamageSource pDamageSource, boolean pAttackedRecently) {
        super.dropFromLootTable(pDamageSource, pAttackedRecently);

        var owner = this.getOwner();

        var shard = new ItemStack(OccultismItems.SOUL_SHARD_ITEM.get());

        var health = this.getHealth();
        this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
        shard.getOrCreateTag().put("entityData", this.serializeNBT());
        this.setHealth(health);

        if(owner instanceof Player player){
            ItemHandlerHelper.giveItemToPlayer(player, shard);
        }
        else {
            ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), shard);
            entityitem.setPickUpDelay(5);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));

            this.level().addFreshEntity(entityitem);
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LYING, false);
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
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        //Demons eat meat
        Item item = pStack.getItem();
        return item.isEdible() && pStack.getFoodProperties(this).isMeat();
    }

    public Optional<SmokingRecipe> getRecipe(ItemStack pStack) {
        return this.level().getRecipeManager().getAllRecipesFor(RecipeType.SMOKING).stream().filter(r ->
                r.ingredient.test(pStack)
        ).findFirst();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();

        if (this.level().isClientSide && this.swinging) {
            Vec3 direction = Vec3.directionFromRotation(this.getRotationVector()).scale(0.6);
            for (int i = 0; i < 5; i++) {
                Vec3 pos = this.position().add(direction.x + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                        1.5 + (this.getRandom().nextFloat() - 0.5f) * 0.7, direction.z + (this.getRandom().nextFloat() - 0.5f) * 0.7);
                this.level().addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.25, 0, direction.z * 0.25);
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (this.level().isClientSide) {
            boolean willInteract = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.DIAMOND) && !this.isTame();
            return willInteract ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        if (this.isTame()) {
            var effects = PotionUtils.getMobEffects(itemstack);
            if (!effects.isEmpty()) {
                for (var instance : effects) {
                    if (instance.getEffect().isInstantenous()) {
                        instance.getEffect().applyInstantenousEffect(this, this, pPlayer, instance.getAmplifier(), 1.0D);
                    } else {
                        pPlayer.addEffect(new MobEffectInstance(instance.getEffect(), instance.getDuration() * 5, instance.getAmplifier(), instance.isAmbient(), instance.isVisible()));
                    }
                }

                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(Items.GLASS_BOTTLE));
                }

                return InteractionResult.SUCCESS;
            }

            //cook raw food
            var recipe = this.lastRecipe.isPresent() ? this.lastRecipe.get().ingredient.test(itemstack) ? this.lastRecipe : this.getRecipe(itemstack) : this.getRecipe(itemstack);
            if (recipe.isPresent()) {
                this.lastRecipe = recipe;
                var result = recipe.get().getResultItem(this.level().registryAccess());
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                ItemHandlerHelper.giveItemToPlayer(pPlayer, result);

                for (int i = 0; i < 2; i++) {
                    Vec3 pos = this.position().add((this.getRandom().nextFloat() - 0.5f) * 0.7,
                            1.5 + (this.getRandom().nextFloat() - 0.5f) * 0.7, (this.getRandom().nextFloat() - 0.5f) * 0.7);
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
                }


                return InteractionResult.SUCCESS;
            }

            //heal with food
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }

            //sit/stand
            InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
            if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(pPlayer)) {
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
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
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
    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, pEntity);
        }

        pEntity.setSecondsOnFire(2);

        return flag;
    }
}
