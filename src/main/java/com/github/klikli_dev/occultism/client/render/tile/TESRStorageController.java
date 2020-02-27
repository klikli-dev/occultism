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

import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TESRStorageController extends TileEntitySpecialRenderer<TileEntityStorageController> {

    //region Fields
    protected Minecraft mc;
    protected ItemStack stack;
    //endregion Fields

    //region Initialization
    public TESRStorageController() {
        this.mc = Minecraft.getMinecraft();
    }
    //endregion Initialization


    //region Overrides
    @Override
    public void render(TileEntityStorageController te, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
        if (this.stack == null)
            this.stack = new ItemStack(ItemRegistry.DIMENSIONAL_MATRIX);

        long time = te.getWorld().getTotalWorldTime();
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.pushMatrix();

        //bob item up and down
        double offset = Math.sin((time + partialTicks) / 8) / 16.0;
        GlStateManager.translate(x + 0.5, y + 0.75 + offset, z + 0.5);
        //rotate item slowly around y axis
        GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);
        //Math.sin(time/frequency)*amplitude
        double scale = 4 + Math.sin((te.getWorld().getTotalWorldTime() + partialTicks) / 8) * 0.25;
        GlStateManager.scale(scale, scale, scale);


        IBakedModel model = Minecraft.getMinecraft().getRenderItem()
                                    .getItemModelWithOverrides(this.stack, te.getWorld(), null);
        model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        //get colors from hue over time
        long colorScale = 100L - Math.abs(time / 2 % 160L - 80L);
        float saturation = (float) Math.sin((te.getWorld().getTotalWorldTime() + partialTicks) / 8) * 0.5f + 0.5f;
        int color = Color.getHSBColor(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale).getRGB();

        //region copied from renderModel in RenderItem
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
        EnumFacing[] var6 = EnumFacing.values();
        int var7 = var6.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            EnumFacing enumfacing = var6[var8];
            this.mc.getRenderItem().renderQuads(bufferbuilder, model.getQuads(null, enumfacing, 0L), color, this.stack);
        }

        this.mc.getRenderItem().renderQuads(bufferbuilder, model.getQuads(null, null, 0L), color, this.stack);
        tessellator.draw();
        GlStateManager.popMatrix();
        //endregion copied from renderModel in RenderItem

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
    }
    //endregion Overrides


}
