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
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class GoldenSacrificialBowlBlock extends Block {
    //region Fields
    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 2.3, 12);
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
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        super.neighborChanged(state, world, pos, blockIn, fromPos, isMoving);
        world.getBlockTicks().scheduleTick(pos, this, 0);
    }

    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile != null) {
                GoldenSacrificialBowlTileEntity bowl = (GoldenSacrificialBowlTileEntity) tile;
                bowl.stopRitual(false); //if block changed/was destroyed, interrupt the ritual.
                StorageUtil.dropInventoryItems(bowl);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                Hand hand, BlockRayTraceResult hit) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof GoldenSacrificialBowlTileEntity) {
            GoldenSacrificialBowlTileEntity goldenSacrificialBowlTileEntity = (GoldenSacrificialBowlTileEntity) tileEntity;
            return goldenSacrificialBowlTileEntity.activate(world, pos, player, hand,
                    hit.getDirection()) ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get().create();
    }
    //endregion Overrides

    //region Methods
    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        if (!player.level.isClientSide) {
            BlockPos pos = player.blockPosition();
            int range = Ritual.ITEM_USE_DETECTION_RANGE;
            for (BlockPos positionToCheck : BlockPos.betweenClosed(pos.offset(-range, -range, -range),
                    pos.offset(range, range, range))) {
                TileEntity tileEntity = player.level.getBlockEntity(positionToCheck);
                if (tileEntity instanceof GoldenSacrificialBowlTileEntity) {
                    GoldenSacrificialBowlTileEntity bowl = (GoldenSacrificialBowlTileEntity) tileEntity;
                    if (bowl.currentRitualRecipe != null && bowl.currentRitualRecipe.getRitual().isValidItemUse(event)) {
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
            if (event.getSource().getEntity() instanceof PlayerEntity) {
                BlockPos pos = entityLivingBase.blockPosition();
                int range = Ritual.SACRIFICE_DETECTION_RANGE;
                for (BlockPos positionToCheck : BlockPos.betweenClosed(pos.offset(-range, -range, -range),
                        pos.offset(range, range, range))) {
                    TileEntity tileEntity = entityLivingBase.level.getBlockEntity(positionToCheck);
                    if (tileEntity instanceof GoldenSacrificialBowlTileEntity) {
                        GoldenSacrificialBowlTileEntity bowl = (GoldenSacrificialBowlTileEntity) tileEntity;
                        if (bowl.currentRitualRecipe != null && bowl.currentRitualRecipe.getRitual().isValidSacrifice(entityLivingBase)) {
                            bowl.notifySacrifice(entityLivingBase);
                        }
                    }
                }
            }
        }
    }
    //endregion Methods
}
