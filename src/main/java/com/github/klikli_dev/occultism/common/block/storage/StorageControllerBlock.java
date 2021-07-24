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

package com.github.klikli_dev.occultism.common.block.storage;

import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.inventory.container.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.CollisionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.Shapes;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.stream.Stream;

public class StorageControllerBlock extends Block {

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 4, 16),
            Block.makeCuboidShape(4, 4, 4, 12, 12, 12),
            Block.makeCuboidShape(2, 12, 2, 14, 16, 14)
    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    //region Initialization
    public StorageControllerBlock(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntityUtil.onBlockChangeDropWithNbt(this, state, worldIn, pos, newState);
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public InteractionResult onBlockActivated(BlockState state, Level level, BlockPos pos, Player player,
                                             InteractionHand handIn, BlockRayTraceResult rayTraceResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (BlockEntity instanceof MenuProvider) {
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) BlockEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack getItem(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return BlockEntityUtil.getItemWithNbt(this, worldIn, pos);
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockState state, BlockGetter level) {
        return OccultismTiles.STORAGE_CONTROLLER.get().create();
    }

    //endregion Overrides
}
