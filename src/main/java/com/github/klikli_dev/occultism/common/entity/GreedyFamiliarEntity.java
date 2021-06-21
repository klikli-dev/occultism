package com.github.klikli_dev.occultism.common.entity;

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
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GreedyFamiliarEntity extends CreatureEntity implements IFamiliar {

    private UUID owner;

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
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
        }

        return super.getEntityInteractionResult(playerIn, hand);
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
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (owner != null)
            compound.putUniqueId("owner", owner);
    }
}
