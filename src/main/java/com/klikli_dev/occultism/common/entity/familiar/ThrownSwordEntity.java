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

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class ThrownSwordEntity extends ThrowableItemProjectile {

    private static final int MAX_DURATION = 20 * 5;

    private int duration;
    private float damage;

    public ThrownSwordEntity(EntityType<? extends ThrownSwordEntity> type, Level level) {
        super(type, level);
        this.damage = 6F;
    }

    public void setDamage(float dmg) {
        this.damage = dmg;
        if (dmg > 10F)
            this.setItem(new ItemStack(Items.DIAMOND_SWORD));
    }

    @Override
    public void tick() {
        super.tick();
        this.duration++;

        if (!this.level().isClientSide() && this.duration > MAX_DURATION)
            this.remove(RemovalReason.DISCARDED);
    }


    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return Items.IRON_SWORD;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("duration", this.duration);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.duration = input.getIntOr("duration", 0);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (pResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) pResult;
            if (entityHitResult.getEntity().invulnerableTime > 0)
                return;
        }
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity target = pResult.getEntity();
        if (this.friendlyFire(target))
            return;

        if (!this.level().isClientSide()) {
            target.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private boolean friendlyFire(Entity target) {
        Entity owner = this.getOwner();
        if (owner == null)
            return false;

        return target == owner || (target instanceof IFamiliar && ((IFamiliar) target).getFamiliarOwner() == owner);
    }
}