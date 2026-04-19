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

package com.klikli_dev.occultism.client.render.blockentity;

import com.klikli_dev.occultism.client.render.blockentity.state.EntityWormholeRenderState;
import com.klikli_dev.occultism.common.block.EntityWormholeBlock;
import com.klikli_dev.occultism.common.blockentity.EntityWormholeBlockEntity;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class EntityWormholeRenderer implements BlockEntityRenderer<EntityWormholeBlockEntity, EntityWormholeRenderState> {
    private final ItemModelResolver itemModelResolver;

    public EntityWormholeRenderer(Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public EntityWormholeRenderState createRenderState() {
        return new EntityWormholeRenderState();
    }

    @Override
    public void extractRenderState(EntityWormholeBlockEntity blockEntity, EntityWormholeRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumbling);

        ItemStack portalStack = blockEntity.itemStackHandler.getResource(0).isEmpty() ? ItemStack.EMPTY : OccultismItems.WORMHOLE_PORTAL.toStack();
        ItemStack nuggetStack = OccultismItems.IESNIUM_NUGGET.toStack();
        BlockState state = blockEntity.getBlockState();

        renderState.portalStack = portalStack;
        renderState.nuggetStack = nuggetStack;
        renderState.facing = state.hasProperty(BlockStateProperties.FACING) ? state.getValue(BlockStateProperties.FACING) : Direction.UP;
        renderState.exitRotationY = state.hasProperty(EntityWormholeBlock.EXIT_ROTATION_Y) ? state.getValue(EntityWormholeBlock.EXIT_ROTATION_Y) : 0;
        renderState.exitRotationX = state.hasProperty(EntityWormholeBlock.EXIT_ROTATION_X) ? state.getValue(EntityWormholeBlock.EXIT_ROTATION_X) : 0;
        renderState.angleY = state.getBlock() instanceof EntityWormholeBlock wormholeBlock ? (int) wormholeBlock.getExitRotY(state) : 0;

        ItemStack blockStack = switch (renderState.exitRotationX) {
            case 2 -> new ItemStack(Items.IRON_BLOCK);
            case 3 -> new ItemStack(Items.REDSTONE_BLOCK);
            case 4 -> new ItemStack(Items.DIAMOND_BLOCK);
            case 5 -> new ItemStack(Items.GOLD_BLOCK);
            default -> new ItemStack(Items.EMERALD_BLOCK);
        };
        renderState.blockStack = blockStack;

        int seed = (int) (blockEntity.getBlockPos().asLong() & 0xFFFFFFFFL);
        if (!portalStack.isEmpty()) {
            this.itemModelResolver.updateForTopItem(renderState.portalRenderState, portalStack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed);
        }
        this.itemModelResolver.updateForTopItem(renderState.nuggetRenderState, nuggetStack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed + 1);
        if (renderState.exitRotationX != 0) {
            this.itemModelResolver.updateForTopItem(renderState.blockRenderState, blockStack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed + 2);
        }
    }

    @Override
    public void submit(EntityWormholeRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitCollector, CameraRenderState cameraRenderState) {
        if (renderState.portalStack.isEmpty()) {
            return;
        }

        Direction facing = renderState.facing;
        int exitY = renderState.exitRotationY;
        int exitX = renderState.exitRotationX;
        int angleY = renderState.angleY;

        poseStack.pushPose();
        poseStack.pushPose();

        double xOffset = facing.getAxis() == Direction.Axis.X ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? 0.03 : 0.97) : 0.5;
        double yOffset = facing.getAxis() == Direction.Axis.Y ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? 0.03 : 0.97) : 0.5;
        double zOffset = facing.getAxis() == Direction.Axis.Z ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? 0.03 : 0.97) : 0.5;

        poseStack.translate(xOffset, yOffset, zOffset);

        float angle = facing.getAxis() == Direction.Axis.X ? 90 : 0;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        float angle2 = facing.getAxis() == Direction.Axis.Y ? 90 : 0;
        poseStack.mulPose(Axis.XP.rotationDegrees(angle2));

        poseStack.scale(0.1F, 0.1F, 0.1F);

        if (exitY != 0) {
            Vec3 vec3;
            if (facing.getAxis() == Direction.Axis.Y) {
                vec3 = Vec3.directionFromRotation(0, (angleY + 180) * 0.002F);
                poseStack.mulPose(Axis.ZP.rotationDegrees((angleY + 180)));
            } else {
                vec3 = Vec3.directionFromRotation(0, angleY * 0.002F);
                poseStack.mulPose(Axis.ZN.rotationDegrees(angleY));
            }
            float nuggetOffset = facing.getAxis() == Direction.Axis.Y ?
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? -0.3F : 0.3F :
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.3F : -0.3F;
            poseStack.translate(-4 * vec3.x, -4 * vec3.z, nuggetOffset);
            renderState.nuggetRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            poseStack.translate(4 * vec3.x, 4 * vec3.z, -nuggetOffset);
        }

        if (exitX != 0) {
            poseStack.scale(0.25F, 0.25F, 0.25F);
            float blockOffset = facing.getAxis() == Direction.Axis.Y ?
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? -0.7F : 0.7F :
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.7F : -0.7F;
            poseStack.translate(0, 0, blockOffset);
            renderState.blockRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            poseStack.mulPose(Axis.ZP.rotationDegrees(45));
            renderState.blockRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            poseStack.translate(0, 0, -blockOffset);
            poseStack.scale(4F, 4F, 4F);
        }

        long systemTime = System.currentTimeMillis();
        float angle3 = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.ZP.rotationDegrees(angle3));
        poseStack.scale(7F, 7F, 7F);
        renderState.portalRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        poseStack.popPose();
        poseStack.mulPose(facing.getRotation());
        poseStack.popPose();
    }
}
