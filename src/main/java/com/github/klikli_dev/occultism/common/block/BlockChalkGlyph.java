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

import com.github.klikli_dev.occultism.api.common.data.ChalkGlyphType;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.MetaUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockChalkGlyph extends Block {

    //region Fields
    /**
     * The type of glyph (color)
     */
    public static final PropertyEnum<ChalkGlyphType> TYPE = PropertyEnum.create("type", ChalkGlyphType.class);
    /**
     * The glyph sign (the typeface)
     */
    private static final PropertyInteger SIGN = PropertyInteger.create("sign", 0, 12);

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.0025, 1);
    //endregion Fields

    //region Initialization
    public BlockChalkGlyph() {
        super(Material.CIRCUITS);
        BlockRegistry.registerBlock(this, "chalk_glyph", Material.CIRCUITS, SoundType.STONE, 5, 100);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    //region Blockstate and Meta
    @Override
    public IBlockState getStateFromMeta(int meta) {
        ChalkGlyphType type = ChalkGlyphType.get(MetaUtil.getLowerTwoMetaBits(meta));
        EnumFacing facing = EnumFacing.HORIZONTALS[MetaUtil.getHigherTwoMetaBits(meta)];
        return this.getDefaultState().withProperty(TYPE, type).withProperty(BlockHorizontal.FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int type = state.getValue(TYPE).getValue(); //first 2 bits
        int facing = state.getValue(BlockHorizontal.FACING).getHorizontalIndex() << 2; //last 2 bits;
        return type | facing;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        //the glyph sign is based on the position in the world (ignoring height) similar to how witchery glyphs are
        //Glyphs use texture use 0-12 based on horizontal location
        int sign = Math.abs(pos.getX() + pos.getZ() * 2) % 13;
        return state.withProperty(SIGN, sign);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    //endregion

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        //Destroy if block below is no longer appropriate to place on.
        if (world.getBlockState(pos.down()).getBlockFaceShape(world, pos, EnumFacing.UP) != BlockFaceShape.SOLID)
            world.destroyBlock(pos, true);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
        world.scheduleUpdate(to, this, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) &&
               world.getBlockState(pos.down()).getBlockFaceShape(world, pos, EnumFacing.UP) == BlockFaceShape.SOLID;
    }

    //region Render and Navigation Settings
    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, BlockHorizontal.FACING, SIGN);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult result, World world, BlockPos pos,
                                  EntityPlayer player) {
        ChalkGlyphType type = state.getValue(TYPE);
        return new ItemStack(getChalkFromType(type));
    }

    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
        return PathNodeType.OPEN; //should allow passing through
    }
    //endregion Overrides

    //region Static Methods

    /**
     * Gets the chalk item based on the glyph type.
     *
     * @param type the glyph time.
     * @return the chalk item.
     */
    public static Item getChalkFromType(ChalkGlyphType type) {

        if (type == ChalkGlyphType.PURPLE)
            return ItemRegistry.CHALK_PURPLE;
        if (type == ChalkGlyphType.RED)
            return ItemRegistry.CHALK_RED;
        if (type == ChalkGlyphType.GOLD)
            return ItemRegistry.CHALK_GOLD;

        return ItemRegistry.CHALK_WHITE;
    }

    /**
     * Gets the glyph color based on glyph type
     *
     * @param type the glyph type
     * @return the glyph color
     */
    public static int getColorFromType(ChalkGlyphType type) {

        if (type == ChalkGlyphType.PURPLE)
            return 0x9c0393;
        if (type == ChalkGlyphType.RED)
            return 0xcc0101;
        if (type == ChalkGlyphType.GOLD)
            return 0xf0d700;
        //0xf7f7f7
        //0xffffff
        return 0xffffff;
    }
    //endregion Static Methods


}
