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

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.level.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class GoldenSacrificialBowlBlock extends Block {
    //region Fields
    private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 2.3, 12);
    //endregion Fields

    //region Initialization
    public GoldenSacrificialBowlBlock(Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.register(this);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
        level.getPendingBlockTicks().scheduleTick(pos, this, 0);
    }

    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile != null){
                GoldenSacrificialBowlBlockEntity bowl = (GoldenSacrificialBowlBlockEntity)tile;
                bowl.stopRitual(false); //if block changed/was destroyed, interrupt the ritual.
                StorageUtil.dropInventoryItems(bowl);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult onBlockActivated(BlockState state, Level level, BlockPos pos, Player player,
                                             InteractionHand hand, BlockRayTraceResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (BlockEntity instanceof GoldenSacrificialBowlBlockEntity) {
            GoldenSacrificialBowlBlockEntity goldenSacrificialBowlBlockEntity = (GoldenSacrificialBowlBlockEntity) BlockEntity;
            return goldenSacrificialBowlBlockEntity.activate(level, pos, player, hand,
                    hit.getFace()) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return super.onBlockActivated(state, level, pos, player, hand, hit);
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, IBlockReader level) {
        return OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get().create();
    }
    //endregion Overrides

    //region Methods
    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        if (!player.level.isClientSide) {
            BlockPos pos = player.getPosition();
            int range = Ritual.ITEM_USE_DETECTION_RANGE;
            for (BlockPos positionToCheck : BlockPos.getAllInBoxMutable(pos.add(-range, -range, -range),
                    pos.add(range, range, range))) {
                BlockEntity blockEntity = player.level.getBlockEntity(positionToCheck);
                if (BlockEntity instanceof GoldenSacrificialBowlBlockEntity) {
                    GoldenSacrificialBowlBlockEntity bowl = (GoldenSacrificialBowlBlockEntity) BlockEntity;
                    if (bowl.currentRitual != null && bowl.currentRitual.isValidItemUse(event)) {
                        bowl.notifyItemUse(event);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void livingDeath(LivingDeathEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!entityLivingBase.level.isClientSide) {
            //Limit to player kills
            if(event.getSource().getTrueSource() instanceof Player){
                BlockPos pos = entityLivingBase.getPosition();
                int range = Ritual.SACRIFICE_DETECTION_RANGE;
                for (BlockPos positionToCheck : BlockPos.getAllInBoxMutable(pos.add(-range, -range, -range),
                        pos.add(range, range, range))) {
                    BlockEntity blockEntity = entityLivingBase.level.getBlockEntity(positionToCheck);
                    if (BlockEntity instanceof GoldenSacrificialBowlBlockEntity) {
                        GoldenSacrificialBowlBlockEntity bowl = (GoldenSacrificialBowlBlockEntity) BlockEntity;
                        if (bowl.currentRitual != null && bowl.currentRitual.isValidSacrifice(entityLivingBase)) {
                            bowl.notifySacrifice(entityLivingBase);
                        }
                    }
                }
            }
        }
    }
    //endregion Methods
}
