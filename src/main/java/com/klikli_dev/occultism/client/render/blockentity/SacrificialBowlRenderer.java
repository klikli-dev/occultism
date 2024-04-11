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
import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class SacrificialBowlRenderer implements BlockEntityRenderer<SacrificialBowlBlockEntity> {

    public SacrificialBowlRenderer(BlockEntityRendererProvider.Context context) {

    }

    //region Static Methods
    public static float getScale(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem itemBlock) {
            if (itemBlock.getBlock() instanceof SpiritAttunedCrystalBlock)
                return 3.0f;
        }
        return 1.0f;
    }

    @Override
    public void render(SacrificialBowlBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        blockEntity.itemStackHandler.ifPresent(handler -> {
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

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            BakedModel model = itemRenderer.getModel(stack, blockEntity.getLevel(), null, 0);
            itemRenderer.render(stack, ItemDisplayContext.FIXED, true, poseStack, buffer,
                    combinedLight, combinedOverlay, model);

            poseStack.popPose();

            poseStack.mulPose(facing.getRotation());

            poseStack.popPose();
        });
    }
    //endregion Static Methods
}
