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

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class DrikwingEntity extends OtherworldBirdEntity {
    // region Fields
    private static final EntityDataAccessor<Boolean> FLAMING = SynchedEntityData.defineId(DrikwingEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean transforming;
    // endregion Fields

    // region Initialization
    public DrikwingEntity(EntityType<? extends Parrot> type, Level worldIn) {
        super(type, worldIn);
        this.transforming = false;
    }
    // endregion Initialization

    public boolean canTransform() {
        return this.isFlaming() && this.isInTransformationBiome(this);
    }

    private boolean isInTransformationBiome(Entity entity) {
        return this.level().getBiome(entity.blockPosition()).is(OccultismTags.ALLOWS_WINGNIS_TRANSFORMATION);
    }

    public void transform() {
        if (!this.level().isClientSide())
            this.transforming = true;
    }

    //region Static Methods
    @Override
    protected void defineSynchedData(@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLAMING, false);
    }

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setFlaming(input.getBooleanOr("isFlaming", false));
    }

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("isFlaming", this.isFlaming());
    }

    // endregion Getter / Setter

    // region Overrides

    // region Getter / Setter

    @Override
    public void aiStep() {
        if (this.transforming) {
            WingnisEntity wingnis = new WingnisEntity(this.level(), this);
            if (this.hasCustomName())
                wingnis.setCustomName(this.getName());
            if (this.hasBlacksmithUpgrade())
                wingnis.blacksmithUpgrade();
            if (this.hasIesniumUpgrade())
                wingnis.iesniumUpgrade();
            this.level().addFreshEntity(wingnis);
            this.remove(RemovalReason.DISCARDED);
        }
        super.aiStep();
    }

    public boolean isFlaming() {
        return this.entityData.get(FLAMING);
    }

    public void setFlaming(boolean b) {
        this.entityData.set(FLAMING, b);
    }
    // endregion Overrides

    @Override
    public @NonNull InteractionResult mobInteract(Player playerIn, @NonNull InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (this.isOwnedBy(playerIn) && !playerIn.level().isClientSide()) {
            if (this.getItemInHand(InteractionHand.MAIN_HAND) == ItemStack.EMPTY
                    && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                this.setItemSlot(EquipmentSlot.MAINHAND, Items.TOTEM_OF_UNDYING.getDefaultInstance());
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            if (!this.isFlaming() && stack.is(OccultismItems.FLAMING_PASTE)) {
                return stack.interactLivingEntity(playerIn, this, hand);
            }
        }

        return super.mobInteract(playerIn, hand);
    }

    @Override
    protected void dropFromLootTable(ServerLevel serverLevel, DamageSource pDamageSource, boolean pAttackedRecently) {
        super.dropFromLootTable(serverLevel, pDamageSource, pAttackedRecently);

        var owner = this.getFamiliarOwner();

        var shard = new ItemStack(OccultismItems.SOUL_SHARD_ITEM.get());

        var health = this.getHealth();
        this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
        this.setFlaming(false);
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
//endregion Static Methods
}

