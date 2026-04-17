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

import com.klikli_dev.occultism.client.render.blockentity.state.SacrificialBowlRenderState;
import com.klikli_dev.occultism.common.block.SpiritAttunedCrystalBlock;
import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class SacrificialBowlRenderer implements BlockEntityRenderer<SacrificialBowlBlockEntity, SacrificialBowlRenderState> {

    private final ItemModelResolver itemModelResolver;

    public SacrificialBowlRenderer(Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    public static float getScale(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem itemBlock) {
            if (itemBlock.getBlock() instanceof SpiritAttunedCrystalBlock)
                return 3.0f;
        }
        return 1.0f;
    }

    @Override
    public SacrificialBowlRenderState createRenderState() {
        return new SacrificialBowlRenderState();
    }

    @Override
    public void extractRenderState(SacrificialBowlBlockEntity blockEntity, SacrificialBowlRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumbling);

        ItemStack stack = blockEntity.itemStackHandler.getResource(0).toStack();
        Direction facing = blockEntity.getBlockState().hasProperty(BlockStateProperties.FACING) ?
                blockEntity.getBlockState().getValue(BlockStateProperties.FACING) : Direction.UP;
        long gameTime = blockEntity.getLevel() != null ? blockEntity.getLevel().getGameTime() : 0L;
        long lastChangeTime = blockEntity.lastChangeTime;

        renderState.itemStack = stack;
        renderState.facing = facing;
        renderState.gameTime = gameTime;
        renderState.lastChangeTime = lastChangeTime;

        // Update the pre-initialized ItemStackRenderState with the current item
        if (!stack.isEmpty()) {
            int seed = (int) (blockEntity.getBlockPos().asLong() & 0xFFFFFFFFL);
            // Pass null for owner since BlockEntity doesn't implement ItemOwner
            this.itemModelResolver.updateForTopItem(renderState.itemStackRenderState, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed);
        }
    }

    @Override
    public void submit(SacrificialBowlRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitCollector, CameraRenderState cameraRenderState) {
        ItemStack stack = renderState.itemStack;
        ItemStackRenderState stackRenderState = renderState.itemStackRenderState;
        Direction facing = renderState.facing;
        long gameTime = renderState.gameTime;
        long lastChangeTime = renderState.lastChangeTime;

        if (stack == null || stack.isEmpty() || facing == null || gameTime == 0) {
            return;
        }

        poseStack.pushPose();
        poseStack.pushPose();

        // Calculate bobbing offset
        double offset = Math.sin((gameTime - lastChangeTime) / 16.0) * 0.5 + 0.5;
        offset = offset / 4.0;

        double fixedOffset = 0.2;

        double xOffset = facing.getAxis() == Direction.Axis.X ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double yOffset = facing.getAxis() == Direction.Axis.Y ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double zOffset = facing.getAxis() == Direction.Axis.Z ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;

        poseStack.translate(0.5 + xOffset, 0.5 + yOffset, 0.5 + zOffset);

        // Rotate item around Y axis using system time
        long systemTime = System.currentTimeMillis();
        float angle = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        // Scale
        float scale = getScale(stack) * 0.5f;
        poseStack.scale(scale, scale, scale);

        // Render the item using the pre-created ItemStackRenderState
        stackRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();

        poseStack.mulPose(facing.getRotation());

        poseStack.popPose();
    }
}
