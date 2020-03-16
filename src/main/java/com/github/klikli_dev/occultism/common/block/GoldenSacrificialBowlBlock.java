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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class GoldenSacrificialBowlBlock extends Block {
    //region Fields
    private static final VoxelShape SHAPE = VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 0.20, 1));
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
        world.getPendingBlockTicks().scheduleTick(pos, this, 0);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            GoldenSacrificialBowlTileEntity bowl = (GoldenSacrificialBowlTileEntity)worldIn.getTileEntity(pos);
            bowl.stopRitual(false); //if block changed/was destroyed, interrupt the ritual.
            StorageUtil.dropInventoryItems(bowl);
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                             Hand hand, BlockRayTraceResult hit) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof GoldenSacrificialBowlTileEntity) {
            GoldenSacrificialBowlTileEntity goldenSacrificialBowlTileEntity = (GoldenSacrificialBowlTileEntity) tileEntity;
            return goldenSacrificialBowlTileEntity.activate(world, pos, player, hand,
                    hit.getFace()) ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
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
        if (!player.world.isRemote) {
            BlockPos pos = player.getPosition();
            int range = Ritual.ITEM_USE_DETECTION_RANGE;
            for (BlockPos positionToCheck : BlockPos.getAllInBoxMutable(pos.add(-range, -range, -range),
                    pos.add(range, range, range))) {
                TileEntity tileEntity = player.world.getTileEntity(positionToCheck);
                if (tileEntity instanceof GoldenSacrificialBowlTileEntity) {
                    GoldenSacrificialBowlTileEntity bowl = (GoldenSacrificialBowlTileEntity) tileEntity;
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
        if (!entityLivingBase.world.isRemote) {
            BlockPos pos = entityLivingBase.getPosition();
            int range = Ritual.SACRIFICE_DETECTION_RANGE;
            for (BlockPos positionToCheck : BlockPos.getAllInBoxMutable(pos.add(-range, -range, -range),
                    pos.add(range, range, range))) {
                TileEntity tileEntity = entityLivingBase.world.getTileEntity(positionToCheck);
                if (tileEntity instanceof GoldenSacrificialBowlTileEntity) {
                    GoldenSacrificialBowlTileEntity bowl = (GoldenSacrificialBowlTileEntity) tileEntity;
                    if (bowl.currentRitual != null && bowl.currentRitual.isValidSacrifice(entityLivingBase)) {
                        bowl.notifySacrifice(entityLivingBase);
                    }
                }
            }
        }
    }
    //endregion Methods
}
