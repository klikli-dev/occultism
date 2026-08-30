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

import com.klikli_dev.occultism.common.blockentity.EntityWormholeBlockEntity;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import com.klikli_dev.occultism.util.TeleportUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class EntityWormholeBlock extends OtherstoneFrameBlock implements EntityBlock, Portal {

    public static final IntegerProperty EXIT_ROTATION_X = IntegerProperty.create("exit_rotation_x", 0, 5);
    public static final IntegerProperty EXIT_ROTATION_Y = IntegerProperty.create("exit_rotation_y", 0, 8);
    public static final MapCodec<EntityWormholeBlock> CODEC = simpleCodec(EntityWormholeBlock::new);

    public EntityWormholeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(EXIT_ROTATION_X, 0)
                        .setValue(EXIT_ROTATION_Y, 0)
        );
    }

    private static ItemStack getWormholeStack(EntityWormholeBlockEntity wormhole) {
        return wormhole.itemStackHandler.getResource(0).toStack(wormhole.itemStackHandler.getAmountAsInt(0));
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState pState, @NotNull PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(EXIT_ROTATION_X, EXIT_ROTATION_Y);
        super.createBlockStateDefinition(builder);
    }

    // onRemove logic moved to EntityWormholeBlockEntity.preRemoveSideEffects()

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack pStack, @NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            if (pStack.is(OccultismItems.SPIRIT_ATTUNED_GEM.get())) {
                if (pHand.equals(InteractionHand.MAIN_HAND)) {
                    pLevel.setBlock(pPos, pState.cycle(EXIT_ROTATION_Y), 10);
                } else {
                    pLevel.setBlock(pPos, pState.cycle(EXIT_ROTATION_X), 10);
                }
                return InteractionResult.CONSUME;
            } else {
                EntityWormholeBlockEntity wormhole = (EntityWormholeBlockEntity) pLevel.getBlockEntity(pPos);
                if (wormhole != null) {
                    var handler = wormhole.itemStackHandler;
                    ItemStack itemStack = getWormholeStack(wormhole);
                    if (pPlayer.isShiftKeyDown() && !itemStack.isEmpty()) {
                        if (heldItem.isEmpty()) {
                            //place it in the hand if possible
                            pPlayer.setItemInHand(pHand, ItemTransferUtil.extractItem(handler, 0, 64, false));
                        } else {
                            //and if not, just put it in the inventory
                            ItemTransferUtil.giveItemToPlayer(pPlayer, ItemTransferUtil.extractItem(handler, 0, 64, false));
                        }
                        pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 1);

                        wormhole.setChanged();
                        return InteractionResult.SUCCESS;
                    } else if (itemStack.isEmpty() && pStack.is(ItemTags.COMPASSES)) {
                        //if there is nothing in the bowl, put the hand held item in
                        pPlayer.setItemInHand(pHand, ItemTransferUtil.insertItem(handler, 0, heldItem, false));
                        pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                        wormhole.setChanged();
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return OccultismBlockEntities.ENTITY_WORMHOLE.get().create(blockPos, blockState);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos, Direction direction) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        return (blockentity instanceof EntityWormholeBlockEntity be) ?
                be.itemStackHandler.getResource(0).toStack().isEmpty() ? 0 : 15
                : 0;
    }

    @Override
    protected void entityInside(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (!level.isClientSide() && entity.canUsePortal(false)
                && (Shapes.joinIsNotEmpty(
                Shapes.create(entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ())),
                state.getShape(level, pos), BooleanOp.AND)
                || entity instanceof Projectile)
                && level.getBlockEntity(pos) instanceof EntityWormholeBlockEntity wormholeBlockEntity
                && !getWormholeStack(wormholeBlockEntity).isEmpty()) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public TeleportTransition getPortalDestination(ServerLevel level, @NotNull Entity entity, @NotNull BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof EntityWormholeBlockEntity wormhole) {
            TeleportUtil.TeleportDestination destination = TeleportUtil.findDestination(level, entity, getWormholeStack(wormhole));
            ResourceKey<Level> resourcekey = destination.level();
            BlockPos targetPos = destination.blockPos();
            Vec3 position = destination.position();

            //Level setting
            ServerLevel serverlevel = resourcekey == null ? null : level.getServer().getLevel(resourcekey);
            if (serverlevel == null)
                return null;
            //Resolve to a safe destination around the target block when needed
            if (position == null) {
                if (targetPos == null)
                    targetPos = serverlevel.getRespawnData().pos();
                position = entity instanceof Projectile ? Vec3.atBottomCenterOf(targetPos) : TeleportUtil.findSafeTeleportPosition(entity, serverlevel, targetPos);
            }
            if (position == null)
                return null;

            //State to get exit rotation
            BlockState state = level.getBlockState(pos);

            return new TeleportTransition(
                    serverlevel,
                    position,
                    entity.getDeltaMovement(),
                    state.getValue(EXIT_ROTATION_Y) == 0 ? entity.getYHeadRot() : this.getExitRotY(state),
                    state.getValue(EXIT_ROTATION_X) == 0 ? entity.getXRot() : this.getExitRotX(state),
                    TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
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

    public void pullEntity(ServerLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof EntityWormholeBlockEntity wormhole
                && getWormholeStack(wormhole).has(OccultismDataComponents.SPIRIT_ENTITY_UUID)) {
            UUID spirit = ItemNBTUtil.getSpiritEntityUUID(getWormholeStack(wormhole));
            if (spirit != null) {
                for (ServerLevel allLvl : Objects.requireNonNull(level.getServer()).getAllLevels()) {
                    Entity targetEntity = allLvl.getEntity(spirit);
                    if (targetEntity != null && targetEntity.canUsePortal(false)) {
                        TeleportTransition transition = new TeleportTransition(
                                level,
                                Vec3.atBottomCenterOf(pos),
                                targetEntity.getDeltaMovement(),
                                state.getValue(EXIT_ROTATION_Y) == 0 ? targetEntity.getYHeadRot() : this.getExitRotY(state),
                                state.getValue(EXIT_ROTATION_X) == 0 ? targetEntity.getXRot() : this.getExitRotX(state),
                                TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
                        );
                        targetEntity.teleport(transition);
                        return;
                    }
                }
            }
        }
    }
}
