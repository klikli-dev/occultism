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

import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import var;
import java.awt.*;
import java.util.List;

/**
 * Use StorageControllerGeoRenderer instead
 */
@Deprecated
public class StorageControllerRenderer implements BlockEntityRenderer<StorageControllerBlockEntity> {

    protected Minecraft minecraft;
    protected ItemStack stack;

    public StorageControllerRenderer(BlockEntityRendererProvider.Context context) {
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(StorageControllerBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (this.stack == null)
            this.stack = new ItemStack(OccultismItems.DIMENSIONAL_MATRIX.get());

        poseStack.pushPose();

        //use system time to become independent of game time
        long systemTime = System.currentTimeMillis();

        double systemTimeRadSin8 = Math.sin(Math.toRadians((float) systemTime / 8));
        double systemTimeRadSin16 = Math.sin(Math.toRadians((float) systemTime / 16));

        double offset = systemTimeRadSin16 / 16.0;
        poseStack.translate(0.5, 1.75 + offset, 0.5);

        //rotate item slowly around y axis
        //do not use system time rad, as rotationDegrees converts for us and we want to clamp it to 360° first
        float angle = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        //Math.sin(time/frequency)*amplitude
        float scale = (float) (1 + systemTimeRadSin8 * 0.025f);
        poseStack.scale(scale, scale, scale);

        //get colors from hue over time
        long colorScale = 100L - Math.abs(systemTime / 16 / 2 % 160L - 80L);
        //make saturation smoothly go from 0.0-1.0
        float saturation = (float) systemTimeRadSin8 * 0.5f + 0.5f;
        int color = Color.getHSBColor(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale).getRGB();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(this.stack, blockEntity.getLevel(), null, 0);

        //from ItemRenderer#renderItem
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        RenderType rendertype = ItemBlockRenderTypes.getRenderType(this.stack, false); //getRenderType(itemstack, isBlock(??)) isBlock = false -> is item entity?
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(buffer, rendertype, true, this.stack.hasFoil());
        //from  ItemRenderer#rendermodel
        var random = RandomSource.create();

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            this.renderQuads(poseStack, ivertexbuilder, model.getQuads(null, direction, random), color, combinedLight,
                    combinedOverlay);
        }

        random.setSeed(42L);
        this.renderQuads(poseStack, ivertexbuilder, model.getQuads(null, null, random), color, combinedLight,
                combinedOverlay);


        poseStack.popPose();
    }

    public void renderQuads(PoseStack matrixStackIn, VertexConsumer bufferIn, List<BakedQuad> quadsIn, int colorIn,
                            int combinedLightIn, int combinedOverlayIn) {
        //from  ItemRenderer#renderQuadList
        PoseStack.Pose pose = matrixStackIn.last();

        for (BakedQuad bakedquad : quadsIn) {
            int i = colorIn;

            float f = (float) (i >> 16 & 255) / 255.0F;
            float f1 = (float) (i >> 8 & 255) / 255.0F;
            float f2 = (float) (i & 255) / 255.0F;
            bufferIn.putBulkData(pose, bakedquad, f, f1, f2, 1.0f, combinedLightIn, combinedOverlayIn, true);
        }

    }

}
