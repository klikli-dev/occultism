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

import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

public class MachineReference implements INBTSerializable<CompoundNBT> {
    //region Fields
    public GlobalBlockPos globalPos;
    public ResourceLocation registryName;
    public boolean chunkLoaded;
    public Direction insertFacing = Direction.UP;
    public Direction extractFacing = Direction.DOWN;
    public String customName = null;

    protected ItemStack cachedItemStack = ItemStack.EMPTY;
    protected Item cachedItem = null;
    protected Block cachedBlock = null;
    //endregion Fields

    //region Initialization
    public MachineReference() {

    }

    public MachineReference(BlockPos pos, DimensionType dimensionType, ResourceLocation registryName,
                            boolean chunkLoaded) {
        this(new GlobalBlockPos(pos, dimensionType), registryName, chunkLoaded);
    }

    public MachineReference(GlobalBlockPos globalPos, ResourceLocation registryName, boolean chunkLoaded) {
        this.globalPos = globalPos;
        this.registryName = registryName;
        this.chunkLoaded = chunkLoaded;
    }
    //endregion Initialization

    //region Getter / Setter
    public Block getBlock() {
        if (this.cachedBlock == null)
            this.cachedBlock = ForgeRegistries.BLOCKS.getValue(this.registryName);
        return this.cachedBlock;
    }

    public Item getItem() {
        if (this.cachedItem == null)
            this.cachedItem = ForgeRegistries.ITEMS.getValue(this.registryName);
        return this.cachedItem;
    }

    public ItemStack getItemStack() {
        if (this.cachedItemStack.isEmpty())
            this.cachedItemStack = new ItemStack(this.getItem());
        return this.cachedItemStack;
    }
    //endregion Getter / Setter

    //region Overrides
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
    public static MachineReference from(TileEntity tileEntity) {
        GlobalBlockPos pos = GlobalBlockPos.from(tileEntity);
        BlockState state = tileEntity.getWorld().getBlockState(pos.getPos());
        ItemStack item = state.getBlock().getItem(tileEntity.getWorld(), pos.getPos(), state);
        boolean isLoaded = tileEntity.getWorld().isBlockLoaded(pos.getPos());
        return new MachineReference(pos, item.getItem().getRegistryName(), isLoaded);
    }

    public static MachineReference from(CompoundNBT compound) {
        MachineReference reference = new MachineReference();
        reference.deserializeNBT(compound);
        return reference;
    }

    public static MachineReference from(PacketBuffer buf) {
        MachineReference reference = new MachineReference();
        reference.decode(buf);
        return reference;
    }
    //endregion Static Methods

    //region Methods
    public CompoundNBT write(CompoundNBT compound) {
        if (this.globalPos != null)
            compound.put("globalPos", this.globalPos.serializeNBT());
        if (this.registryName != null)
            compound.putString("registryName", this.registryName.toString());
        if (!StringUtils.isBlank(this.customName))
            compound.putString("customName", this.customName);

        compound.putBoolean("isChunkLoaded", this.chunkLoaded);
        compound.putByte("insertFacing", (byte) this.insertFacing.getIndex());
        compound.putByte("extractFacing", (byte) this.extractFacing.getIndex());

        return compound;
    }

    public void read(CompoundNBT compound) {
        if (compound.contains("globalPos"))
            this.globalPos = GlobalBlockPos.from(compound.getCompound("globalPos"));
        if (compound.contains("registryName"))
            this.registryName = new ResourceLocation(compound.getString("registryName"));
        if (compound.contains("customName"))
            this.customName = compound.getString("customName");

        this.chunkLoaded = compound.getBoolean("isChunkLoaded");
        this.insertFacing = Direction.byIndex(compound.getInt("insertFacing"));
        this.extractFacing = Direction.byIndex(compound.getInt("extractFacing"));
    }

    public void encode(PacketBuffer buf) {
        buf.writeCompoundTag(this.write(new CompoundNBT()));
    }

    public void decode(PacketBuffer buf) {
        this.deserializeNBT(buf.readCompoundTag());
    }

    public TileEntity getTileEntity(World world) {
        return TileEntityUtil.get(world, this.globalPos);
    }
    //endregion Methods


}
