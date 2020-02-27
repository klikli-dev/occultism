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

import com.github.klikli_dev.occultism.api.common.block.IOtherOre;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.PotionRegistry;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockOtherworldLeavesNatural extends LeavesBlock implements IOtherOre {

    //region Fields
    public static final int COLOR = 0x760ea2;
    //endregion Fields

    //region Initialization
    public BlockOtherworldLeavesNatural() {
        this.leavesFancy = true;
        BlockRegistry.registerBlock(this, "otherworld_leaves_natural", Material.LEAVES, SoundType.PLANT, 0.2f, 0);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true)
                        .withProperty(UNCOVERED, false));
        this.setTranslationKey("leaves.oak");
    }
    //endregion Initialization

    //region Overrides

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0)
                       .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        int i = 0;

        if (!state.getValue(DECAYABLE)) {
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY)) {
            i |= 8;
        }

        return i;
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return IOtherOre.super.getActualState(state, worldIn, pos);
    }

    @Override
    public int damageDropped(BlockState state) {
        return state.getValue(UNCOVERED) ? 0 : BlockPlanks.EnumType.OAK.getMetadata();
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
                             @Nullable TileEntity te, ItemStack stack) {
        if (!worldIn.isRemote && stack.getItem() instanceof ShearsItem) {
            player.addStat(Stats.getBlockStats(BlockRegistry.OTHERWORLD_LEAVES));
            //Drop handled via onSheared
        }
        else {
            super.harvestBlock(worldIn, player, pos, IOtherOre.super.getHarvestState(player, state), te, stack);
        }
    }

    @Override
    public ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this.getCoveredBlock(), 1);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        return IOtherOre.super.getItem(worldIn, pos, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE, UNCOVERED);
    }

    @Override
    public Block getUncoveredBlock() {
        return BlockRegistry.OTHERWORLD_LEAVES;
    }

    @Override
    public Block getCoveredBlock() {
        return Blocks.LEAVES;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return state.getValue(UNCOVERED) ? Item.getItemFromBlock(
                BlockRegistry.OTHERWORLD_SAPLING_NATURAL) : Item.getItemFromBlock(Blocks.SAPLING);
    }

    @Override
    protected int getSaplingDropChance(BlockState state) {
        return state.getValue(UNCOVERED) ? 5 : 20;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos,
                                            int fortune) {
        if (world instanceof ServerWorld) {
            Vec3d center = Math3DUtil.getBlockCenter(pos);
            PlayerEntity player = ((ServerWorld) world).getClosestPlayer(center.x, center.y, center.z, 10, false);
            if (player != null && player.isPotionActive(PotionRegistry.THIRD_EYE))
                return NonNullList.withSize(1, new ItemStack(BlockRegistry.OTHERWORLD_LEAVES, 1));
        }
        return NonNullList.withSize(1, new ItemStack(Blocks.LEAVES, 1, BlockPlanks.EnumType.OAK.getMetadata()));
    }
    //endregion Overrides
}