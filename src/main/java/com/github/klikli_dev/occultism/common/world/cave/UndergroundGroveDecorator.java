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

package com.github.klikli_dev.occultism.common.world.cave;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.OccultismConfig;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class UndergroundGroveDecorator extends CaveDecorator {

    //region Fields
    private final WorldGenTrees treeGen = new UndergroundGroveTreeGen(false, 4,
            BlockRegistry.OTHERWORLD_LOG_NATURAL.getDefaultState(),
            BlockRegistry.OTHERWORLD_LEAVES_NATURAL.getDefaultState(), true);
    //endregion Fields

    //region Initialization
    public UndergroundGroveDecorator() {
        super(Blocks.GRASS.getDefaultState(), null, BlockRegistry.OTHERSTONE_ORE.getDefaultState());

    }
    //endregion Initialization

    //region Overrides

    @Override
    public void fillFloor(World world, BlockPos pos, IBlockState state) {
        super.fillFloor(world, pos, state);
        //TODO: Remove this logger once we are happy with the world gen
        Occultism.logger.info("Generating underground grove floor at {} {} {}", pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void finalFloorPass(World world, BlockPos pos) {
        if (world.rand.nextDouble() < OccultismConfig.worldGen.undergroundGroveGen.grassChance)
            ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, 14), world, pos);

        if (world.rand.nextDouble() < OccultismConfig.worldGen.undergroundGroveGen.treeChance) {
            //this.shrubGen.generate(world, world.rand, pos.up());
            this.treeGen.generate(world, world.rand, pos.up());
        }
    }

    @Override
    public void finalCeilingPass(World world, BlockPos pos) {
        if (world.rand.nextDouble() < OccultismConfig.worldGen.undergroundGroveGen.ceilingLightChance) {
            world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState());
        }
    }

    @Override
    public void finalWallPass(World world, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos offset = pos.offset(facing);
            BlockPos up = offset.up();
            if (this.isCeiling(world, up, world.getBlockState(up)) &&
                world.rand.nextDouble() < OccultismConfig.worldGen.undergroundGroveGen.vineChance) {
                IBlockState stateAt = world.getBlockState(offset);
                boolean spawnedVine = false;
                while (stateAt.getBlock().isAir(stateAt, world, offset) && offset.getY() > 0) {
                    world.setBlockState(offset, Blocks.VINE.getDefaultState()
                                                        .withProperty(BlockVine.getPropertyFor(facing.getOpposite()),
                                                                true), 2);
                    offset = offset.down();
                    stateAt = world.getBlockState(offset);
                    spawnedVine = true;
                }

                if (spawnedVine)
                    return;
            }
        }
    }
    //endregion Overrides
}
