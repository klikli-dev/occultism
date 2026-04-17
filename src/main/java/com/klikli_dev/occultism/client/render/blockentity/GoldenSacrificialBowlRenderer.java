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

import com.klikli_dev.occultism.client.render.blockentity.state.GoldenSacrificialBowlRenderState;
import com.klikli_dev.occultism.common.block.SpiritAttunedCrystalBlock;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class GoldenSacrificialBowlRenderer implements BlockEntityRenderer<SacrificialBowlBlockEntity, GoldenSacrificialBowlRenderState> {

    private final ItemModelResolver itemModelResolver;

    public GoldenSacrificialBowlRenderer(BlockEntityRendererProvider.Context context) {
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
    public GoldenSacrificialBowlRenderState createRenderState() {
        return new GoldenSacrificialBowlRenderState();
    }

    @Override
    public void extractRenderState(SacrificialBowlBlockEntity blockEntity, GoldenSacrificialBowlRenderState renderState, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
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
            this.itemModelResolver.updateForTopItem(renderState.itemStackRenderState, stack, ItemDisplayContext.GROUND, blockEntity.getLevel(), null, seed);
        }

        // GoldenSacrificialBowl-specific data
        if (blockEntity instanceof GoldenSacrificialBowlBlockEntity goldenBowl) {
            renderState.isGoldenBowl = true;

            var recipe = goldenBowl.getCurrentRitualRecipe();
            if (recipe != null) {
                renderState.itemUseFulfilled = goldenBowl.itemUseFulfilled();
                renderState.sacrificeFulfilled = goldenBowl.sacrificeFulfilled();
                renderState.recipeId = recipe.id().toString();
                renderState.requiresItemUse = recipe.value().requiresItemUse();
                renderState.requiresSacrifice = recipe.value().requiresSacrifice();

                // Get item to use for cycling animation
                if (renderState.requiresItemUse) {
                    var items = recipe.value().getItemToUse().items().toList();
                    if (!items.isEmpty()) {
                        renderState.itemToUseStacks = items.stream()
                                .map(holder -> new ItemStack(holder.value()))
                                .toArray(ItemStack[]::new);
                    }
                }
            } else {
                renderState.itemUseFulfilled = true;
                renderState.sacrificeFulfilled = true;
                renderState.requiresItemUse = false;
                renderState.requiresSacrifice = false;
            }
        } else {
            renderState.isGoldenBowl = false;
        }
    }

    @Override
    public void submit(GoldenSacrificialBowlRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitCollector, CameraRenderState cameraRenderState) {
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

        double xOffset = facing.getAxis() == Direction.Axis.X ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double yOffset = facing.getAxis() == Direction.Axis.Y ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double zOffset = facing.getAxis() == Direction.Axis.Z ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;

        poseStack.translate(0.5 + xOffset, 0.5 + yOffset, 0.5 + zOffset);

        // Rotate item around Y axis using system time
        long systemTime = System.currentTimeMillis();
        float angle = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        // Scale
        float scale = getScale(stack) * 0.5f;
        poseStack.scale(scale, scale, scale);

        // Render the main item using the pre-created ItemStackRenderState
        stackRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        // Render item-to-use indicator for GoldenSacrificialBowl
        if (renderState.isGoldenBowl && !stack.isEmpty() && renderState.itemToUseStacks != null && renderState.itemToUseStacks.length > 0) {
            poseStack.pushPose();
            poseStack.translate(0, 3.2 - (0.5 + yOffset) / scale, 0);
            if (!renderState.itemUseFulfilled && renderState.requiresItemUse) {
                int index = renderState.itemToUseStacks.length == 1 ? 0 : (int) ((System.currentTimeMillis() / 2880) % renderState.itemToUseStacks.length);
                ItemStack itemStack = renderState.itemToUseStacks[index];
                if (!itemStack.isEmpty()) {
                    // Create a temporary ItemStackRenderState for this item
                    ItemStackRenderState itemStackState = new ItemStackRenderState();
                    int seed = index + 1; // Different seed than main item
                    this.itemModelResolver.updateForTopItem(itemStackState, itemStack, ItemDisplayContext.GROUND, null, null, seed);
                    float scaleUse = getScale(itemStack) * 0.5F;
                    poseStack.scale(scaleUse, scaleUse, scaleUse);
                    itemStackState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
                }
            }
            poseStack.popPose();
        }

        poseStack.popPose();

        poseStack.mulPose(facing.getRotation());

        poseStack.popPose();
    }
}
