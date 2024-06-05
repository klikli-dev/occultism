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

package com.klikli_dev.occultism.api.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Objects;
import java.util.StringJoiner;

public class GlobalBlockPos implements INBTSerializable<CompoundTag> {

    protected BlockPos pos;
    protected ResourceKey<Level> dimensionKey;

    public static final Codec<GlobalBlockPos> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(GlobalBlockPos::getPos),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(GlobalBlockPos::getDimensionKey)
    ).apply(instance, GlobalBlockPos::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GlobalBlockPos> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            GlobalBlockPos::getPos,
            ResourceKey.streamCodec(Registries.DIMENSION),
            GlobalBlockPos::getDimensionKey,
            GlobalBlockPos::new
    );

    public GlobalBlockPos() {
    }

    public GlobalBlockPos(BlockPos pos, ResourceKey<Level> dimensionKey) {
        this.pos = pos;
        this.dimensionKey = dimensionKey;
    }

    public GlobalBlockPos(BlockPos pos, Level level) {
        this.pos = pos;
        this.dimensionKey = level.dimension();
    }

    public static GlobalBlockPos from(BlockEntity blockEntity) {
        return new GlobalBlockPos(blockEntity.getBlockPos(), blockEntity.getLevel());
    }

    public ResourceKey<Level> getDimensionKey() {
        return this.dimensionKey;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dimensionKey, this.pos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        GlobalBlockPos other = (GlobalBlockPos) obj;
        if (!this.pos.equals(other.pos))
            return false;
        return this.dimensionKey.equals(other.dimensionKey);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]").add(this.dimensionKey.registry().toString())
                .add("x=" + this.pos.getX()).add("y=" + this.pos.getY())
                .add("z=" + this.pos.getZ()).toString();
    }


    public CompoundTag write(CompoundTag compound) {
        compound.putLong("pos", this.getPos().asLong());
        compound.putString("dimension", this.dimensionKey.location().toString());
        return compound;
    }

    public void read(CompoundTag compound) {
        this.pos = BlockPos.of(compound.getLong("pos"));
        this.dimensionKey =
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("dimension")));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeResourceLocation(this.dimensionKey.location());
    }

    public void decode(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.dimensionKey = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.write(new CompoundTag());
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.read(nbt);
    }
}
