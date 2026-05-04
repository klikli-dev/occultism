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


package com.klikli_dev.occultism.client.gui.controls;

import com.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.awt.*;

public class ItemSlotWidget {

    protected int x;
    protected int y;
    protected int stackSize;
    protected int guiLeft;
    protected int guiTop;
    protected boolean showStackSize;
    protected Minecraft minecraft;
    protected IStorageControllerGuiContainer parent;
    protected ItemStack stack;
    protected Font fontRenderer;
    protected int slotHighlightColor;

    public ItemSlotWidget(IStorageControllerGuiContainer parent, @Nonnull ItemStack stack, int x, int y, int stackSize,
                          int guiLeft, int guiTop, boolean showStackSize) {
        this.x = x;
        this.y = y;
        this.stackSize = stackSize;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        this.showStackSize = showStackSize;
        this.parent = parent;
        this.minecraft = Minecraft.getInstance();
        this.stack = stack;
        this.fontRenderer = this.parent.getFontRenderer();
        this.slotHighlightColor = new Color(255, 255, 255, 128).getRGB();
    }

    //region Getter / Setter
    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public boolean getShowStackSize() {
        return this.showStackSize;
    }

    public void setShowStackSize(boolean showStackSize) {
        this.showStackSize = showStackSize;
    }
    //endregion Getter / Setter

    public boolean isMouseOverSlot(int mouseX, int mouseY) {
        return this.parent.isPointInRegionController(this.x - this.guiLeft - 1, this.y - this.guiTop - 1, 18, 18, mouseX, mouseY);
    }

    public void drawSlot(GuiGraphicsExtractor guiGraphics, int mx, int my) {
        guiGraphics.pose().pushMatrix();
        if (!this.getStack().isEmpty()) {
            //RenderHelper.enableGUIStandardItemLighting();

            if (this.showStackSize) {

                //get amount to show
                String amount = this.minecraft.hasShiftDown() ? Integer.toString(this.stackSize) : TextUtil.formatLargeNumber(
                        this.stackSize);

                //render item overlay
                guiGraphics.pose().pushMatrix();

//                this.minecraft.getItemRenderer()
//                        .blitOffset = 0.1f;
                //we ended up not using any translate and it was fine

                guiGraphics.pose().scale(.5f, .5f);

                guiGraphics.itemDecorations(this.fontRenderer, this.stack, this.x * 2 + 16, this.y * 2 + 16, amount);
                guiGraphics.pose().popMatrix();
            }

            //this.minecraft.getItemRenderer().blitOffset = -100F;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0, 0);
            guiGraphics.fakeItem(this.getStack(), this.x, this.y);
            guiGraphics.pose().popMatrix();

            if (this.isMouseOverSlot(mx, my)) {
                this.parent.drawGradientRect(guiGraphics, this.x, this.y, this.x + 16, this.y + 16, this.slotHighlightColor,
                        this.slotHighlightColor);
            }
        }

        guiGraphics.pose().popMatrix();
    }

    public void drawTooltip(GuiGraphicsExtractor guiGraphics, int mx, int my) {
        if (this.isMouseOverSlot(mx, my) && !this.getStack().isEmpty()) {
            this.parent.renderToolTip(guiGraphics, this.getStack(), mx, my);
        }
    }

}
