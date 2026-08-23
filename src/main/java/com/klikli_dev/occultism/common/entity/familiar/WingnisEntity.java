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

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import org.jspecify.annotations.NonNull;

public class WingnisEntity extends OtherworldBirdEntity {

    public WingnisEntity(EntityType<? extends Parrot> type, Level worldIn) {
        super(type, worldIn);
    }

    public WingnisEntity(Level level, DrikwingEntity bird) {
        this(OccultismEntities.WINGNIS_FAMILIAR.get(), level);
        this.setFamiliarOwner(bird.getFamiliarOwner());
        this.setPos(bird.getX(), bird.getY(), bird.getZ());
    }

    @Override
    protected void dropFromLootTable(@NonNull ServerLevel serverLevel, @NonNull DamageSource pDamageSource, boolean pAttackedRecently) {
        super.dropFromLootTable(serverLevel, pDamageSource, pAttackedRecently);

        var owner = this.getFamiliarOwner();

        var shard = new ItemStack(OccultismItems.SOUL_SHARD_ITEM.get());

        var health = this.getHealth();
        this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
        this.resetFallDistance();
        this.removeAllEffects();

        var id = this.getEncodeId();
        var entityData = new CompoundTag();
        if (id != null)
            entityData.putString("id", id);
        var valueOutput = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, this.registryAccess());
        this.saveWithoutId(valueOutput);
        entityData.merge(valueOutput.buildResult());

        shard.set(DataComponents.ENTITY_DATA, TypedEntityData.of(this.getType(), entityData));

        this.setHealth(health);

        if (owner instanceof Player player) {
            ItemTransferUtil.giveItemToPlayer(player, shard);
        } else {
            ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), shard);
            entityitem.setPickUpDelay(5);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));

            this.level().addFreshEntity(entityitem);
        }
    }
}

