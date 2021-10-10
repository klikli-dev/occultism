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

package com.github.klikli_dev.occultism.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrownSwordEntity extends ThrowableItemProjectile {

    private static final int MAX_DURATION = 20 * 5;

    private int duration;

    public ThrownSwordEntity(EntityType<? extends ThrownSwordEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        duration++;

        if (!level.isClientSide && duration > MAX_DURATION)
            remove();
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("duration", duration);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT pCompound) {
        super.readAdditionalSaveData(pCompound);
        duration = pCompound.getInt("duration");
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_SWORD;
    }

    @Override
    protected void onHit(RayTraceResult pResult) {
        super.onHit(pResult);
        this.remove();

    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity target = result.getEntity();
        if (friendlyFire(target))
            return;

        if (!level.isClientSide) {
            target.hurt(DamageSource.thrown(this, getOwner()), 6);
        }
    }

    private boolean friendlyFire(Entity target) {
        Entity owner = getOwner();
        if (owner == null)
            return false;

        return target == owner || (target instanceof IFamiliar && ((IFamiliar) target).getFamiliarOwner() == owner);
    }

    @Override
    protected float getGravity() {
        return 0;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}