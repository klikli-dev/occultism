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

import com.github.klikli_dev.occultism.common.block.BlockSpiritAttunedCrystal;
import com.github.klikli_dev.occultism.common.tile.TileEntitySacrificialBowl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class TESRSacrificialBowl extends TileEntityRenderer<TileEntitySacrificialBowl> {

    //region Fields
    protected Minecraft mc;
    //endregion Fields

    //region Initialization
    public TESRSacrificialBowl() {
        this.mc = Minecraft.getMinecraft();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void render(TileEntitySacrificialBowl tileEntity, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
        ItemStack stack = tileEntity.itemStackHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            long time = tileEntity.getWorld().getTotalWorldTime();

            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();

            //make item bob up and down with Math.sin(time/frequency) / amplitude
            double offset = Math.sin((time - tileEntity.lastChangeTime + partialTicks) / 16) / 16.0;
            GlStateManager.translate(x + 0.5, y + 0.25 + offset, z + 0.5);

            //rotate item slowly
            GlStateManager.rotate((time + partialTicks) * 1, 0, 1, 0);
            float scale = getScale(stack);
            GlStateManager.scale(scale, scale, scale);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem()
                                        .getItemModelWithOverrides(stack, tileEntity.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    public static float getScale(ItemStack stack){
        if(stack.getItem() instanceof BlockItem){
            BlockItem itemBlock = (BlockItem) stack.getItem();
            if(itemBlock.getBlock() instanceof BlockSpiritAttunedCrystal)
                return 3.0f;
        }
        return 1.0f;
    }
    //endregion Overrides
}
