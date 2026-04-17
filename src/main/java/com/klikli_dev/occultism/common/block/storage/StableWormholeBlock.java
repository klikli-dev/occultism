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

package com.klikli_dev.occultism.common.block.storage;

import com.klikli_dev.occultism.common.block.OtherstoneFrameBlock;
import com.klikli_dev.occultism.common.blockentity.StableWormholeBlockEntity;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class StableWormholeBlock extends OtherstoneFrameBlock implements EntityBlock {

    public static final Property<Boolean> LINKED = BooleanProperty.create("linked");

    public StableWormholeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LINKED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof StableWormholeBlockEntity wormhole && StorageControllerContainerBase.canOpen(pPlayer, pPos)
                    && pPlayer instanceof ServerPlayer serverPlayer) {
                if (wormhole.getLinkedStorageController() != null) {
                    serverPlayer.openMenu(wormhole, pPos);
                    StorageControllerContainerBase.reserve(pPlayer, pPos);
                } else if (pLevel.getBlockState(pPos).getValue(LINKED) && !wormhole.controllerLoaded()) {
                    serverPlayer.sendSystemMessage(Component.translatable(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.not_loaded"));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
        if (context.getItemInHand().has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER)) {
            state = state.setValue(LINKED, true);
        }
        return state;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader worldIn, BlockPos pos, BlockState state, boolean includeData) {
        return BlockEntityUtil.getItemWithNbt(this, worldIn, pos);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(LINKED);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return OccultismBlockEntities.STABLE_WORMHOLE.get().create(blockPos, blockState);
    }
}


