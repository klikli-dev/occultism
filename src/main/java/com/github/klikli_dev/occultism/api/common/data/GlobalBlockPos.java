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

package com.github.klikli_dev.occultism.api.common.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;
import java.util.StringJoiner;

public class GlobalBlockPos implements INBTSerializable<CompoundNBT> {
    //region Fields
    protected BlockPos pos;
    protected RegistryKey<World> dimensionKey;
    //endregion Fields

    //region Initialization
    public GlobalBlockPos() {
    }

    public GlobalBlockPos(BlockPos pos, RegistryKey<World> dimensionKey) {
        this.pos = pos;
        this.dimensionKey = dimensionKey;
    }

    public GlobalBlockPos(BlockPos pos, World world) {
        this.pos = pos;
        this.dimensionKey = world.dimension();
    }
    //endregion Initialization

    //region Getter / Setter
    public RegistryKey<World> getDimensionKey() {
        return this.dimensionKey;
    }

    public BlockPos getPos() {
        return this.pos;
    }
    //endregion Getter / Setter

    //region Overrides
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
        return new StringJoiner(", ", "[", "]").add(this.dimensionKey.location().toString())
                       .add("x=" + this.pos.getX()).add("y=" + this.pos.getY())
                       .add("z=" + this.pos.getZ()).toString();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.read(nbt);
    }
    //endregion Overrides

    //region Static Methods
    public static GlobalBlockPos from(CompoundNBT compound) {
        GlobalBlockPos globalBlockPos = new GlobalBlockPos();
        globalBlockPos.deserializeNBT(compound);
        return globalBlockPos;
    }

    public static GlobalBlockPos from(PacketBuffer buf) {
        GlobalBlockPos globalBlockPos = new GlobalBlockPos();
        globalBlockPos.decode(buf);
        return globalBlockPos;
    }

    public static GlobalBlockPos from(TileEntity tileEntity) {
        return new GlobalBlockPos(tileEntity.getBlockPos(), tileEntity.getLevel());
    }
    //endregion Static Methods

    //region Methods
    public CompoundNBT write(CompoundNBT compound) {
        compound.putLong("pos", this.getPos().asLong());
        compound.putString("dimension", this.dimensionKey.location().toString());
        return compound;
    }

    public void read(CompoundNBT compound) {
        this.pos = BlockPos.of(compound.getLong("pos"));
        this.dimensionKey =
                RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("dimension")));
    }

    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(this.pos);
        buf.writeResourceLocation(this.dimensionKey.location());
    }

    public void decode(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.dimensionKey = RegistryKey.create(Registry.DIMENSION_REGISTRY, buf.readResourceLocation());
    }
    //endregion Methods
}
