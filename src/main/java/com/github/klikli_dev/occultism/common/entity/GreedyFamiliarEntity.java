package com.github.klikli_dev.occultism.common.entity;

import java.util.EnumSet;
import java.util.UUID;

import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public class GreedyFamiliarEntity extends CreatureEntity implements IFamiliar {

    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(GreedyFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private UUID owner;

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SITTING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new FindItemGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == OccultismItems.FAMILIAR_RING.get()) {
            return stack.interactWithEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            owner = playerIn.getUniqueID();
            return ActionResultType.func_233537_a_(world.isRemote);
        } else {
            if (!world.isRemote && getFamiliarOwner() == playerIn)
                setSitting(!isSitting());
            return ActionResultType.func_233537_a_(world.isRemote);
        }
    }

    @Override
    public LivingEntity getFamiliarOwner() {
        if (owner == null)
            return null;
        return world.getPlayerByUuid(owner);
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (!(wearer instanceof PlayerEntity))
            return;

        for (ItemEntity e : world.getEntitiesWithinAABB(ItemEntity.class, wearer.getBoundingBox().grow(5))) {
            e.onCollideWithPlayer((PlayerEntity) wearer);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.hasUniqueId("owner"))
            owner = compound.getUniqueId("owner");
        if (compound.contains("isSitting"))
            setSitting(compound.getBoolean("isSitting"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (owner != null)
            compound.putUniqueId("owner", owner);

        compound.putBoolean("isSitting", isSitting());
    }

    private void setSitting(boolean b) {
        dataManager.set(SITTING, b);
    }

    public boolean isSitting() {
        return dataManager.get(SITTING);
    }

    public class FollowOwnerGoal extends Goal {
        private GreedyFamiliarEntity entity;
        private double speed;
        private int cooldown;
        private float maxDist;
        private float minDist;
        private LivingEntity owner;

        public FollowOwnerGoal(GreedyFamiliarEntity entity, double speed, float minDist, float maxDist) {
            this.entity = entity;
            this.speed = speed;
            this.minDist = minDist * minDist;
            this.maxDist = maxDist * maxDist;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        private boolean shouldFollow(double distance) {
            return owner != null && !owner.isSpectator() && entity.getDistanceSq(owner) > distance;
        }

        public boolean shouldExecute() {
            owner = entity.getFamiliarOwner();
            return shouldFollow(minDist);
        }

        public boolean shouldContinueExecuting() {
            return shouldFollow(maxDist);
        }

        public void startExecuting() {
            cooldown = 0;
        }

        public void resetTask() {
            owner = null;
            entity.getNavigator().clearPath();
        }

        public void tick() {
            entity.getLookController().setLookPositionWithEntity(owner, 10, entity.getVerticalFaceSpeed());
            if (--cooldown < 0) {
                cooldown = 10;
                if (!entity.getLeashed() && !entity.isPassenger()) {
                    entity.getNavigator().tryMoveToEntityLiving(owner, speed);
                }
            }
        }
    }

    private class SitGoal extends Goal {
        private final GreedyFamiliarEntity entity;

        public SitGoal(GreedyFamiliarEntity entity) {
            this.entity = entity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            return !entity.isInWaterOrBubbleColumn() && entity.isOnGround() && entity.getFamiliarOwner() != null
                    && entity.isSitting();
        }

        public void startExecuting() {
            entity.getNavigator().clearPath();
        }

        public void resetTask() {
            entity.setSitting(false);
        }
    }

    private static class FindItemGoal extends Goal {

        private static final double RANGE = 12;

        private GreedyFamiliarEntity entity;

        public FindItemGoal(GreedyFamiliarEntity raptor) {
            this.entity = raptor;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return getNearbyItem() != null && entity.getFamiliarOwner() instanceof PlayerEntity;
        }

        @Override
        public void startExecuting() {
            ItemEntity item = getNearbyItem();
            if (item != null)
                entity.getNavigator().tryMoveToEntityLiving(item, 1.2);
        }

        @Override
        public void tick() {
            ItemEntity item = getNearbyItem();
            if (item != null) {
                entity.getNavigator().tryMoveToEntityLiving(item, 1.2);
                LivingEntity owner = entity.getFamiliarOwner();
                if (item.getDistanceSq(entity) < 4 && owner instanceof PlayerEntity)
                    item.onCollideWithPlayer(((PlayerEntity) owner));
            }
        }

        private ItemEntity getNearbyItem() {
            LivingEntity owner = entity.getFamiliarOwner();
            if (!(owner instanceof PlayerEntity))
                return null;

            PlayerEntity player = (PlayerEntity) owner;
            IItemHandler inv = new PlayerMainInvWrapper(player.inventory);

            for (ItemEntity item : entity.world.getEntitiesWithinAABB(ItemEntity.class,
                    entity.getBoundingBox().grow(RANGE))) {
                ItemStack stack = item.getItem();
                if (ItemHandlerHelper.insertItemStacked(inv, stack, true).getCount() != stack.getCount())
                    return item;
            }
            return null;
        }

    }
}
