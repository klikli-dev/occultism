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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.World;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.StringJoiner;

public class GlobalBlockPos implements INBTSerializable<CompoundNBT> {
    //region Fields
    protected BlockPos pos;
    protected DimensionType dimensionType;
    //endregion Fields

    //region Initialization
    public GlobalBlockPos() {
    }

    public GlobalBlockPos(BlockPos pos, DimensionType dimensionType) {
        this.pos = pos;
        this.dimensionType = dimensionType;
    }

    public GlobalBlockPos(BlockPos pos, World world) {
        this.pos = pos;
        this.dimensionType = world.getDimensionType();
    }
    //endregion Initialization

    //region Getter / Setter
    public DimensionType getDimensionType() {
        return this.dimensionType;
    }

    public BlockPos getPos() {
        return this.pos;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public int hashCode() {
        //multiply first hash code by a prime to avoid hash collisions (see Objects.hash() for reference
        return this.getPos().hashCode() * 31 + this.getDimensionType().getRegistryName().hashCode();
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
        return this.dimensionType == other.dimensionType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]").add(this.dimensionType.getRegistryName().toString())
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
        return new GlobalBlockPos(tileEntity.getPos(), tileEntity.getWorld());
    }
    //endregion Static Methods

    //region Methods
    public CompoundNBT write(CompoundNBT compound) {
        compound.putLong("pos", this.getPos().toLong());
        compound.putString("dimension", this.getDimensionType().getRegistryName().toString());
        return compound;
    }

    public void read(CompoundNBT compound) {
        this.pos = BlockPos.fromLong(compound.getLong("pos"));
        this.dimensionType = DimensionType.byName(new ResourceLocation(compound.getString("dimension")));
    }

    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(this.pos);
        buf.writeResourceLocation(this.dimensionType.getRegistryName());
    }

    public void decode(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.dimensionType = DimensionType.byName(buf.readResourceLocation());
    }
    //endregion Methods
}
