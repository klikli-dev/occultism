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

import com.github.klikli_dev.occultism.common.tile.StorageControllerTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class StorageControllerRenderer extends TileEntityRenderer<StorageControllerTileEntity> {

    //region Fields
    protected Minecraft minecraft;
    protected ItemStack stack;
    //endregion Fields

    //region Initialization
    public StorageControllerRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
        this.minecraft = Minecraft.getInstance();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void render(StorageControllerTileEntity tileEntity, float partialTicks, MatrixStack matrixStack,
                       IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (this.stack == null)
            this.stack = new ItemStack(OccultismItems.DIMENSIONAL_MATRIX.get());

        matrixStack.push();

        //use system time to become independent of game time
        long systemTime = System.currentTimeMillis();

        double systemTimeRadSin8 = Math.sin(Math.toRadians((float)systemTime / 8));
        double systemTimeRadSin16 = Math.sin(Math.toRadians((float)systemTime / 16));

        double offset = systemTimeRadSin16 / 16.0;
        matrixStack.translate(0.5, 1.75 + offset, 0.5);

        //rotate item slowly around y axis
        //do not use system time rad, as rotationDegrees converts for us and we want to clamp it to 360° first
        float angle = ((float)systemTime / 16) % 360;
        matrixStack.rotate(Vector3f.YP.rotationDegrees(angle));

        //Math.sin(time/frequency)*amplitude
        float scale = (float) (1 + systemTimeRadSin8 * 0.025f);
        matrixStack.scale(scale, scale, scale);

        //get colors from hue over time
        long colorScale = 100L - Math.abs(systemTime / 16 / 2 % 160L - 80L);
        //make saturation smoothly go from 0.0-1.0
        float saturation = (float) systemTimeRadSin8 * 0.5f + 0.5f;
        int color = Color.getHSBColor(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale).getRGB();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        IBakedModel model = itemRenderer.getItemModelWithOverrides(this.stack, tileEntity.getWorld(), null);

        //from ItemRenderer#renderItem
        matrixStack.translate(-0.5D, -0.5D, -0.5D);
        RenderType rendertype = RenderTypeLookup.func_239219_a_(this.stack, false); //getRenderType(itemstack, isBlock(??)) isBlock = false -> is item entity?
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, rendertype, true, this.stack.hasEffect());
        //from  ItemRenderer#rendermodel
        Random random = new Random();

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            this.renderQuads(matrixStack, ivertexbuilder, model.getQuads(null, direction, random), color, combinedLight,
                    combinedOverlay);
        }

        random.setSeed(42L);
        this.renderQuads(matrixStack, ivertexbuilder, model.getQuads(null, null, random), color, combinedLight,
                combinedOverlay);


        matrixStack.pop();
    }
    //endregion Overrides

    //region Methods
    public void renderQuads(MatrixStack matrixStackIn, IVertexBuilder bufferIn, List<BakedQuad> quadsIn, int colorIn,
                            int combinedLightIn, int combinedOverlayIn) {
        //from  ItemRenderer#renderQuad
        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();

        for (BakedQuad bakedquad : quadsIn) {
            int i = colorIn;

            float f = (float) (i >> 16 & 255) / 255.0F;
            float f1 = (float) (i >> 8 & 255) / 255.0F;
            float f2 = (float) (i & 255) / 255.0F;
            bufferIn.addVertexData(matrixstack$entry, bakedquad, f, f1, f2, combinedLightIn, combinedOverlayIn, true);
        }

    }
    //endregion Methods
}
