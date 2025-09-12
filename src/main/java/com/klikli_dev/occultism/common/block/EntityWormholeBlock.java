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

package com.klikli_dev.occultism.common.block;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.EntityWormholeBlockEntity;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.StorageUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityWormholeBlock extends OtherstoneFrameBlock implements EntityBlock, Portal {

    public static final MapCodec<EntityWormholeBlock> CODEC = simpleCodec(EntityWormholeBlock::new);
    public static final IntegerProperty EXIT_ROTATION_X = IntegerProperty.create("exit_rotation_x", 0, 5);
    public static final IntegerProperty EXIT_ROTATION_Y = IntegerProperty.create("exit_rotation_y", 0, 8);

    public EntityWormholeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(EXIT_ROTATION_X,0)
                        .setValue(EXIT_ROTATION_Y,0)
        );
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EXIT_ROTATION_X, EXIT_ROTATION_Y);
        super.createBlockStateDefinition(builder);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = worldIn.getBlockEntity(pos);
            if (blockEntity != null) {
                StorageUtil.dropInventoryItems(blockEntity);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            if (pStack.is(OccultismItems.SPIRIT_ATTUNED_GEM.get())) {
                if (pHand.equals(InteractionHand.MAIN_HAND)){
                    pLevel.setBlock(pPos, pState.cycle(EXIT_ROTATION_Y), 10);
                } else {
                    pLevel.setBlock(pPos, pState.cycle(EXIT_ROTATION_X), 10);
                }
                return ItemInteractionResult.CONSUME;
            } else {
                EntityWormholeBlockEntity wormhole = (EntityWormholeBlockEntity) pLevel.getBlockEntity(pPos);
                var handler = wormhole.itemStackHandler;
                ItemStack itemStack = handler.getStackInSlot(0);
                if (pPlayer.isShiftKeyDown() && !itemStack.isEmpty()) {
                    if (heldItem.isEmpty()) {
                        //place it in the hand if possible
                        pPlayer.setItemInHand(pHand, handler.extractItem(0, 64, false));
                    } else {
                        //and if not, just put it in the inventory
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, handler.extractItem(0, 64, false));
                    }
                    pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 1);

                    wormhole.setChanged();
                    return ItemInteractionResult.SUCCESS;
                } else if (itemStack.isEmpty() && pStack.is(ItemTags.COMPASSES)) {
                    //if there is nothing in the bowl, put the hand held item in
                    pPlayer.setItemInHand(pHand, handler.insertItem(0, heldItem, false));
                    pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                    wormhole.setChanged();
                    return ItemInteractionResult.CONSUME;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return OccultismBlockEntities.ENTITY_WORMHOLE.get().create(blockPos, blockState);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity.canUsePortal(false)
                && Shapes.joinIsNotEmpty(
                Shapes.create(entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ())),
                state.getShape(level, pos), BooleanOp.AND)
                && level.getBlockEntity(pos) instanceof EntityWormholeBlockEntity wormholeBlockEntity
                && !wormholeBlockEntity.itemStackHandler.getStackInSlot(0).isEmpty()) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public DimensionTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof EntityWormholeBlockEntity wormhole) {
            ItemStack compass = wormhole.itemStackHandler.getStackInSlot(0);

            ResourceKey<Level> resourcekey = null;
            BlockPos blockpos = null;
            if (compass.has(DataComponents.LODESTONE_TRACKER)
                    && compass.get(DataComponents.LODESTONE_TRACKER).target().isPresent()) {
                resourcekey = compass.get(DataComponents.LODESTONE_TRACKER).target().get().dimension();
                blockpos = compass.get(DataComponents.LODESTONE_TRACKER).target().get().pos().above();
            } else if (compass.is(Items.COMPASS)) {
                resourcekey = Level.OVERWORLD;
                if (compass.has(DataComponents.CUSTOM_NAME)
                        && compass.get(DataComponents.CUSTOM_NAME).getString().equals("RTP")){
                    resourcekey = entity.level().dimension();
                    blockpos = findSafeRTP(blockpos, level, entity);
                }
            } else if (compass.is(Items.RECOVERY_COMPASS)
                    && entity instanceof ServerPlayer serverPlayer
                    && serverPlayer.getLastDeathLocation().isPresent()) {
                resourcekey = serverPlayer.getLastDeathLocation().get().dimension();
                blockpos = serverPlayer.getLastDeathLocation().get().pos();
            } else if (compass.has(OccultismDataComponents.SPIRIT_ENTITY_UUID)) {
                UUID spirit = ItemNBTUtil.getSpiritEntityUUID(compass);
                if (spirit != null && level.getEntity(spirit) != null) {
                    resourcekey = level.getEntity(spirit).level().dimension();
                    blockpos = level.getEntity(spirit).blockPosition();
                }
            }

            //Level setting
            ServerLevel serverlevel = resourcekey == null ? null : level.getServer().getLevel(resourcekey);
            if (serverlevel == null)
                return null;
            //Blockpos check
            if (blockpos == null)
                blockpos = serverlevel.getSharedSpawnPos();

            //State to get exit rotation
            BlockState state = level.getBlockState(pos);

            return new DimensionTransition(
                    serverlevel,
                    blockpos.getBottomCenter(),
                    entity.getDeltaMovement(),
                    state.getValue(EXIT_ROTATION_Y) == 0 ? entity.getYHeadRot() : this.getExitRotY(state),
                    state.getValue(EXIT_ROTATION_X) == 0 ? entity.getXRot() : this.getExitRotX(state),
                    DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
            );

        }
        return null;
    }

    public float getExitRotY(BlockState state) {
        return switch (state.getValue(EXIT_ROTATION_Y)) {
            case 2 -> 45;
            case 3 -> 90;
            case 4 -> 135;
            case 5 -> 180;
            case 6 -> 225;
            case 7 -> 270;
            case 8 -> 315;
            default -> 0;
        };
    }

    public float getExitRotX(BlockState state) {
        return switch (state.getValue(EXIT_ROTATION_X)) {
            case 2 -> 45;
            case 3 -> 90;
            case 4 -> -45;
            case 5 -> -90;
            default -> 0;
        };
    }

    private BlockPos findSafeRTP(BlockPos blockpos, Level level, Entity entity){
        //Respect word border
        int range = Math.min((int) level.getWorldBorder().getDistanceToBorder(entity), Occultism.SERVER_CONFIG.itemSettings.maxDistanceRTP.getAsInt());
        //Random direction
        blockpos = entity.blockPosition().offset(RandomSource.create().nextInt(-range, range), level.getMaxBuildHeight(),RandomSource.create().nextInt(-range,range));
        //Find floor
        while (level.getBlockState(blockpos.below()).is(BlockTags.AIR) && blockpos.getY() > level.getMinBuildHeight()) {
            blockpos = blockpos.below();
        }
        //Pass nether (or other dimension) roof
        if (blockpos.getY() > 10 && level.getBlockState(blockpos.below()).is(Blocks.BEDROCK)) {
            blockpos = blockpos.below(5);
            while (!level.getBlockState(blockpos.below()).is(BlockTags.AIR) && blockpos.getY() > level.getMinBuildHeight()) {
                blockpos = blockpos.below();
            }
            while (level.getBlockState(blockpos.below()).is(BlockTags.AIR) && blockpos.getY() > level.getMinBuildHeight()) {
                blockpos = blockpos.below();
            }
        }
        //Return blockpos if safe, or repeat the process
        return blockpos.getY() == level.getMinBuildHeight()
                || level.getBlockState(blockpos.below()).is(Blocks.WATER)
                || level.getBlockState(blockpos.below()).is(Blocks.LAVA) ?
                findSafeRTP(blockpos, level, entity) : blockpos;
    }
}
