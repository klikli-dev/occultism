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

import com.github.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

public class MachineReference implements INBTSerializable<CompoundTag> {
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

    public MachineReference(BlockPos pos, ResourceKey<Level> dimensionKey, ResourceLocation registryName,
                            boolean chunkLoaded) {
        this(new GlobalBlockPos(pos, dimensionKey), registryName, chunkLoaded);
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
    public CompoundTag serializeNBT() {
        return this.write(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.read(nbt);
    }
    //endregion Overrides

    //region Static Methods
    public static MachineReference from(BlockEntity blockEntity) {
        GlobalBlockPos pos = GlobalBlockPos.from(blockEntity);
        BlockState state = blockEntity.getLevel().getBlockState(pos.getPos());
        ItemStack item = state.getBlock().getCloneItemStack(blockEntity.getLevel(), pos.getPos(), state);
        boolean isLoaded = blockEntity.getLevel().isLoaded(pos.getPos());
        return new MachineReference(pos, item.getItem().getRegistryName(), isLoaded);
    }

    public static MachineReference from(CompoundTag compound) {
        MachineReference reference = new MachineReference();
        reference.deserializeNBT(compound);
        return reference;
    }

    public static MachineReference from(FriendlyByteBuf buf) {
        MachineReference reference = new MachineReference();
        reference.decode(buf);
        return reference;
    }
    //endregion Static Methods

    //region Methods
    public CompoundTag write(CompoundTag compound) {
        if (this.globalPos != null)
            compound.put("globalPos", this.globalPos.serializeNBT());
        if (this.registryName != null)
            compound.putString("registryName", this.registryName.toString());
        if (!StringUtils.isBlank(this.customName))
            compound.putString("customName", this.customName);

        compound.putBoolean("isChunkLoaded", this.chunkLoaded);
        compound.putByte("insertFacing", (byte) this.insertFacing.get3DDataValue());
        compound.putByte("extractFacing", (byte) this.extractFacing.get3DDataValue());

        return compound;
    }

    public void read(CompoundTag compound) {
        if (compound.contains("globalPos"))
            this.globalPos = GlobalBlockPos.from(compound.getCompound("globalPos"));
        if (compound.contains("registryName"))
            this.registryName = new ResourceLocation(compound.getString("registryName"));
        if (compound.contains("customName"))
            this.customName = compound.getString("customName");

        this.chunkLoaded = compound.getBoolean("isChunkLoaded");
        this.insertFacing = Direction.from3DDataValue(compound.getInt("insertFacing"));
        this.extractFacing = Direction.from3DDataValue(compound.getInt("extractFacing"));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(this.write(new CompoundTag()));
    }

    public void decode(FriendlyByteBuf buf) {
        this.deserializeNBT(buf.readNbt());
    }

    public BlockEntity getBlockEntity(Level level) {
        return BlockEntityUtil.get(level, this.globalPos);
    }
    //endregion Methods


}
