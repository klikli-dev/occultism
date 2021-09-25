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

package com.github.klikli_dev.occultism.client.render.tile;

import com.github.klikli_dev.occultism.common.block.SpiritAttunedCrystalBlock;
import com.github.klikli_dev.occultism.common.tile.SacrificialBowlTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class SacrificialBowlRenderer extends TileEntityRenderer<SacrificialBowlTileEntity> {

    //region Initialization
    public SacrificialBowlRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }
    //endregion Initialization

    //region Overrides

    //region Static Methods
    public static float getScale(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            BlockItem itemBlock = (BlockItem) stack.getItem();
            if (itemBlock.getBlock() instanceof SpiritAttunedCrystalBlock)
                return 3.0f;
        }
        return 1.0f;
    }
    //endregion Overrides

    @Override
    public void render(SacrificialBowlTileEntity tileEntity, float partialTicks, MatrixStack matrixStack,
                       IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        tileEntity.itemStackHandler.ifPresent(handler -> {
            ItemStack stack = handler.getStackInSlot(0);
            long time = tileEntity.getLevel().getGameTime();
            matrixStack.pushPose();

            //slowly bob up and down following a sine
            double offset = Math.sin((time - tileEntity.lastChangeTime + partialTicks) / 16) * 0.5f + 0.5f; // * 0.5f + 0.5f;  move sine between 0.0-1.0
            offset = offset / 4.0f; //reduce amplitude
            matrixStack.translate(0.5, 0.6 + offset, 0.5);

            //use system time to become independent of game time
            long systemTime = System.currentTimeMillis();
            //rotate item slowly around y axis
            float angle = (systemTime / 16) % 360;
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(angle));

            //Fixed scale
            float scale = getScale(stack) * 0.5f;
            matrixStack.scale(scale, scale, scale);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            IBakedModel model = itemRenderer.getModel(stack, tileEntity.getLevel(), null);
            itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer,
                    combinedLight, combinedOverlay, model);

            matrixStack.popPose();
        });
    }
    //endregion Static Methods
}
