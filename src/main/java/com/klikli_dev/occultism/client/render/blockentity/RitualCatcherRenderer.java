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

import com.klikli_dev.occultism.client.render.blockentity.state.RitualCatcherRenderState;
import com.klikli_dev.occultism.common.blockentity.RitualCatcherBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class RitualCatcherRenderer implements BlockEntityRenderer<RitualCatcherBlockEntity, RitualCatcherRenderState> {
    private final ItemModelResolver itemModelResolver;

    public RitualCatcherRenderer(Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NonNull RitualCatcherRenderState createRenderState() {
        return new RitualCatcherRenderState();
    }

    @Override
    public void extractRenderState(@NonNull RitualCatcherBlockEntity blockEntity, @NonNull RitualCatcherRenderState renderState,
                                   float partialTick, @NonNull Vec3 cameraPos, @Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumbling);

        ItemStack storedStack = blockEntity.itemStackHandler.getResource(0).toStack();
        BlockState state = blockEntity.getBlockState();

        renderState.itemStack = storedStack;
        renderState.facing = state.hasProperty(BlockStateProperties.FACING) ? state.getValue(BlockStateProperties.FACING) : Direction.UP;

        if (!storedStack.isEmpty()) {
            int seed = (int) (blockEntity.getBlockPos().asLong() & 0xFFFFFFFFL);
            this.itemModelResolver.updateForTopItem(renderState.itemStackRenderState, storedStack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed);
        }
    }

    @Override
    public void submit(RitualCatcherRenderState renderState, @NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector submitCollector, @NonNull CameraRenderState cameraRenderState) {
        if (renderState.itemStack.isEmpty()) {
            return;
        }

        Direction facing = renderState.facing;

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

        poseStack.scale(0.5F, 0.5F, 0.5F);
        renderState.itemStackRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
        poseStack.mulPose(facing.getRotation());
        poseStack.popPose();
    }
}
