package com.klikli_dev.occultism.common.entity.ai.goal;

import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class OwnerHurtTargetGoal extends TargetGoal {
    private final FamiliarEntity tameAnimal;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public OwnerHurtTargetGoal(FamiliarEntity tameAnimal) {
        super(tameAnimal, false);
        this.tameAnimal = tameAnimal;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.tameAnimal.getOwner() != null && !this.tameAnimal.isSitting()) {
            LivingEntity livingentity = this.tameAnimal.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp
                        && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT)
                        && this.tameAnimal.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}

