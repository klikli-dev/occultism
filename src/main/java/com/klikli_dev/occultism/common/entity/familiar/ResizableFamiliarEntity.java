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

import com.klikli_dev.occultism.Occultism;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class ResizableFamiliarEntity extends FamiliarEntity {

    protected static final byte MAX_SIZE = 100;
    private static final EntityDataAccessor<Byte> SIZE = SynchedEntityData.defineId(ResizableFamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private static final Identifier FAMILIAR_SCALE = Identifier.fromNamespaceAndPath(Occultism.MODID, "familiar_scale");

    public ResizableFamiliarEntity(EntityType<? extends ResizableFamiliarEntity> type, Level level) {
        super(type, level);
    }

    public byte getSize() {
        return this.entityData.get(SIZE);
    }

    public void setSize(byte size) {
        int i = Mth.clamp(size, 0, MAX_SIZE);
        this.entityData.set(SIZE, (byte) i);
        this.getAttribute(Attributes.SCALE).removeModifier(FAMILIAR_SCALE);
        this.getAttribute(Attributes.SCALE).addTransientModifier(
                new AttributeModifier(FAMILIAR_SCALE, i*0.01F - 0.5F, AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SIZE, (byte) 0);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setSize(input.getByteOr("getSize", (byte) 0));
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putByte("getSize", this.getSize());
    }

    public float getFamiliarScale() {
        return 1 + this.getSize() * 1f / MAX_SIZE;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if (SIZE.equals(pKey))
            this.refreshDimensions();
    }
}
