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

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.World;

import java.util.StringJoiner;

public class GlobalBlockPos {
    //region Fields
    protected BlockPos pos;
    protected int dimension;
    //endregion Fields

    //region Initialization
    protected GlobalBlockPos() {
    }

    public GlobalBlockPos(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public GlobalBlockPos(BlockPos pos, World world) {
        this.pos = pos;
        this.dimension = world.provider.getDimension();
    }
    //endregion Initialization

    //region Getter / Setter
    public int getDimension() {
        return this.dimension;
    }

    public BlockPos getPos() {
        return this.pos;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public int hashCode() {
        //multiply first hash code by a prime to avoid hash collisions (see Objects.hash() for reference)
        return this.getPos().hashCode() * 31 + this.getDimension();
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
        if (!this.getPos().equals(other.getPos()))
            return false;
        return this.getDimension() == other.getDimension();
    }

    @Override
    public String toString() {
        String dimension = DimensionType.getById(this.dimension).getName();
        return new StringJoiner(", ", "[", "]").add(dimension).add("x=" + this.pos.getX()).add("y=" + this.pos.getY())
                       .add("z=" + this.pos.getZ()).toString();
    }
    //endregion Overrides

    //region Static Methods
    public static GlobalBlockPos fromNbt(CompoundNBT compound) {
        GlobalBlockPos globalBlockPos = new GlobalBlockPos();
        globalBlockPos.readFromNBT(compound);
        return globalBlockPos;
    }

    public static GlobalBlockPos fromBytes(ByteBuf byteBuf) {
        GlobalBlockPos globalBlockPos = new GlobalBlockPos();
        globalBlockPos.pos = BlockPos.fromLong(byteBuf.readLong());
        globalBlockPos.dimension = byteBuf.readInt();
        return globalBlockPos;
    }


    public static GlobalBlockPos fromTileEntity(TileEntity tileEntity) {
        return new GlobalBlockPos(tileEntity.getPos(), tileEntity.getWorld());
    }
    //endregion Static Methods

    //region Methods
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        compound.setLong("pos", this.getPos().toLong());
        compound.setInteger("dimension", this.getDimension());
        return compound;
    }

    public void readFromNBT(CompoundNBT compound) {
        this.pos = BlockPos.fromLong(compound.getLong("pos"));
        this.dimension = compound.getInteger("dimension");
    }


    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(this.pos.toLong());
        byteBuf.writeInt(this.dimension);
    }
    //endregion Methods
}
