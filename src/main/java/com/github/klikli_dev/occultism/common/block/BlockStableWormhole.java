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

package com.github.klikli_dev.occultism.common.block;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.tile.TileEntityStableWormhole;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Region;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockStableWormhole extends Block {

    //region Fields
    public static final IProperty<Boolean> LINKED = PropertyBool.create("linked");
    private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0, 0, 0, 0.0625, 1, 1);
    private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.9375, 0, 0, 1, 1, 1);

    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0, 0, 0.9375, 1, 1, 1);
    private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 0.0625);

    private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.0625, 1);
    private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0, 0.9375, 0, 1, 1, 1);

    //endregion Fields
    //region Initialization
    public BlockStableWormhole() {
        super(Material.ANVIL);
        BlockRegistry.registerBlock(this, "stable_wormhole", Material.ROCK, SoundType.METAL, 1.5f, 30);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DirectionalBlock.FACING, Direction.byIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(DirectionalBlock.FACING).getIndex();
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        //getActualState can be called from different threads, in which case a ChunkCache will be provided.
        //for chunk caches we need use a read-only variant of getTileEntity
        TileEntity tileEntity = worldIn instanceof Region ? ((Region) worldIn).getTileEntity(pos,
                Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
        boolean linked = false;
        if (tileEntity instanceof TileEntityStableWormhole)
            linked = ((TileEntityStableWormhole) tileEntity).getLinkedStorageControllerPosition() != null;
        return super.getActualState(state, worldIn, pos).withProperty(LINKED, linked);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        switch (state.getValue(DirectionalBlock.FACING)) {
            case EAST:
                return EAST_AABB;
            case WEST:
                return WEST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case NORTH:
                return NORTH_AABB;
            case DOWN:
                return DOWN_AABB;
            case UP:
            default:
                return UP_AABB;
        }
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityStableWormhole) {
            ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
            itemstack.setTagInfo("BlockEntityTag", tileentity.writeToNBT(new CompoundNBT()));
            spawnAsEntity(worldIn, pos, itemstack);
            worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, BlockState state, float chance, int fortune) {
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY,
                                           float hitZ, int meta, LivingEntity placer) {
        //place on the face the placer looks at, this is more comfortable than using entity facing.
        RayTraceResult result = placer.rayTrace(10, 1.0f);
        return this.getDefaultState().withProperty(DirectionalBlock.FACING, result.sideHit);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer,
                                ItemStack stack) {
        Occultism.proxy.scheduleDelayedTask(worldIn, () -> worldIn.markBlockRangeForRenderUpdate(pos, pos), 1000);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityStableWormhole) {
            CompoundNBT nbttagcompound = tileentity.writeToNBT(new CompoundNBT());
            if (!nbttagcompound.isEmpty()) {
                itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
            }
        }

        return itemstack;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DirectionalBlock.FACING, LINKED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, BlockState state) {
        return new TileEntityStableWormhole();
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return super.canRenderInLayer(state, layer) || layer == BlockRenderLayer.SOLID;
    }
    //endregion Overrides

}


