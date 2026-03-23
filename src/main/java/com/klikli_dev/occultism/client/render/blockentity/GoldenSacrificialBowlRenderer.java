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

import com.klikli_dev.occultism.common.block.SpiritAttunedCrystalBlock;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.klikli_dev.occultism.util.EntityUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.CrumblingOverlay;
import net.minecraft.client.renderer.blockentity.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.CameraRenderState;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class GoldenSacrificialBowlRenderer implements BlockEntityRenderer<SacrificialBowlBlockEntity, BlockEntityRenderState> {

    public GoldenSacrificialBowlRenderer(BlockEntityRendererProvider.Context context) {

    }

    public static float getScale(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem itemBlock) {
            if (itemBlock.getBlock() instanceof SpiritAttunedCrystalBlock)
                return 3.0f;
        }
        return 1.0f;
    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void extractRenderState(SacrificialBowlBlockEntity blockEntity, BlockEntityRenderState renderState, float partialTick, Vec3 cameraPos, CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumbling);
    }

    @Override
    public void submit(BlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitCollector, CameraRenderState cameraRenderState) {
        // TODO: Port to 26.1 rendering API
        // Original render logic displayed a floating, rotating item above the bowl with bobbing animation.
        // For GoldenSacrificialBowl, also showed items-to-use and entity-to-sacrifice indicators.
        // This needs to be ported to the new submit/extractRenderState pattern.
    }

    // Legacy render logic preserved as reference:
    public void render(SacrificialBowlBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        var handler = blockEntity.itemStackHandler;
        ItemStack stack = handler.getStackInSlot(0);
        long time = blockEntity.getLevel().getGameTime();

        var facing = blockEntity.getBlockState().hasProperty(BlockStateProperties.FACING) ?
                blockEntity.getBlockState().getValue(BlockStateProperties.FACING) : Direction.UP;

        poseStack.pushPose();

        poseStack.pushPose();

        //slowly bob up and down following a sine
        double offset = Math.sin((time - blockEntity.lastChangeTime + partialTicks) / 16) * 0.5f + 0.5f; // * 0.5f + 0.5f;  move sine between 0.0-1.0
        offset = offset / 4.0f; //reduce amplitude

        // Fixed offset to push the item away from the bowl
        double fixedOffset = 0.2;

        // Adjust the translation based on the facing direction
        double xOffset = facing.getAxis() == Direction.Axis.X ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double yOffset = facing.getAxis() == Direction.Axis.Y ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double zOffset = facing.getAxis() == Direction.Axis.Z ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;

        poseStack.translate(0.5 + xOffset, 0.5 + yOffset, 0.5 + zOffset);

        //use system time to become independent of game time
        long systemTime = System.currentTimeMillis();
        //rotate item slowly around y axis
        float angle = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        //Fixed scale
        float scale = getScale(stack) * 0.5f;
        poseStack.scale(scale, scale, scale);

        // TODO: Port to 26.1 rendering API - BakedModel removed
        /*
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = blockEntity.getLevel();
        BakedModel model = itemRenderer.getModel(stack, level, null, 0);
        itemRenderer.render(stack, ItemDisplayContext.FIXED, true, poseStack, buffer,
                combinedLight, combinedOverlay, model);

        if (!stack.isEmpty() && blockEntity instanceof GoldenSacrificialBowlBlockEntity goldenBowl) {
            poseStack.translate(0, 3.2 -(0.5 + yOffset)/scale, 0);
            if (!goldenBowl.itemUseFulfilled()){
                ItemStack[] itemStackList = goldenBowl.getCurrentRitualRecipe().value().getItemToUse().getItems();
                if (itemStackList.length > 0) {
                    int index = itemStackList.length == 1 ? 0 : (int) ((System.currentTimeMillis() / 2880) % itemStackList.length);
                    ItemStack itemStack = itemStackList[index];
                    float scaleUse = getScale(itemStack) * 0.5F;
                    poseStack.scale(scaleUse, scaleUse, scaleUse);
                    BakedModel modelItem = itemRenderer.getModel(itemStack, level, null, 0);
                    itemRenderer.render(itemStack, ItemDisplayContext.FIXED, false, poseStack, buffer,
                            combinedLight, combinedOverlay, modelItem);
                }
            }
            if (!goldenBowl.sacrificeFulfilled()) {
                LivingEntity pLivingEntity = (LivingEntity) EntityUtil.getEntityInTag(level, goldenBowl.currentRitualRecipe.value().getEntityToSacrifice()).create(level);
                if (pLivingEntity == null)
                    return;
                float maxSize = (float) Math.max(pLivingEntity.getHitbox().getXsize(), Math.max(pLivingEntity.getHitbox().getYsize(), pLivingEntity.getHitbox().getZsize()));
                float eScale = 0.5F/maxSize;
                poseStack.scale(eScale, eScale, eScale);
                EntityUtil.renderEntity(poseStack, pLivingEntity, buffer, partialTicks);
            }
        }
        */

        poseStack.popPose();

        poseStack.mulPose(facing.getRotation());

        poseStack.popPose();
    }
}
