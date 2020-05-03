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


package com.github.klikli_dev.occultism.client.gui.controls;

import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.github.klikli_dev.occultism.util.TextUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.awt.*;

public class ItemSlotWidget {
    //region Fields
    protected int x;
    protected int y;
    protected int stackSize;
    protected int guiLeft;
    protected int guiTop;
    protected boolean showStackSize;
    protected Minecraft minecraft;
    protected IStorageControllerGuiContainer parent;
    protected ItemStack stack;
    protected FontRenderer fontRenderer;
    protected int slotHighlightColor;
    //endregion Fields

    //region Initialization
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
    //endregion Initialization

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

    //region Methods
    public boolean isMouseOverSlot(int mouseX, int mouseY) {
        return this.parent.isPointInRegionController(this.x - this.guiLeft, this.y - this.guiTop, 16, 16, mouseX, mouseY);
    }

    public void drawSlot(int mx, int my) {
        RenderSystem.pushMatrix();
        if (!this.getStack().isEmpty()) {
            //RenderHelper.enableGUIStandardItemLighting();

            if (this.showStackSize) {
                //get amount to show
                String amount = Screen.hasShiftDown() ? Integer.toString(this.stackSize) : TextUtil.formatLargeNumber(
                        this.stackSize);

                //render item overlay
                RenderSystem.pushMatrix();
                RenderSystem.scalef(.5f, .5f, .5f);
                this.minecraft.getItemRenderer().zLevel = -0.1F;
                this.minecraft.getItemRenderer()
                        .renderItemOverlayIntoGUI(this.fontRenderer, this.stack, this.x * 2 + 16, this.y * 2 + 16,
                                amount);
                RenderSystem.popMatrix();
            }

            this.minecraft.getItemRenderer().zLevel = -100F;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(this.getStack(), this.x, this.y);

            if (this.isMouseOverSlot(mx, my)) {
                RenderSystem.colorMask(true, true, true, false);
                this.parent.drawGradientRect(this.x, this.y, this.x + 16, this.y + 16, this.slotHighlightColor,
                        this.slotHighlightColor);
                RenderSystem.colorMask(true, true, true, true);
            }
        }

        RenderSystem.popMatrix();
    }

    public void drawTooltip(int mx, int my) {
        if (this.isMouseOverSlot(mx, my) && !this.getStack().isEmpty()) {
            this.parent.renderToolTip(this.getStack(), mx, my);
        }
    }
    //endregion Methods
}
