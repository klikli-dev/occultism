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
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import javax.annotation.Nonnull;
import java.awt.*;

public class MachineSlotWidget {

    protected int x;
    protected int y;
    protected int guiLeft;
    protected int guiTop;
    protected Minecraft minecraft;
    protected IStorageControllerGuiContainer parent;
    protected MachineReference machine;
    protected Font fontRenderer;
    protected int slotHighlightColor;

    public MachineSlotWidget(IStorageControllerGuiContainer parent, @Nonnull MachineReference machine, int x, int y,
                             int guiLeft, int guiTop) {
        this.x = x;
        this.y = y;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        this.parent = parent;
        this.minecraft = Minecraft.getInstance();
        this.machine = machine;
        this.fontRenderer = this.parent.getFontRenderer();
        this.slotHighlightColor = new Color(255, 255, 255, 128).getRGB();
    }

    //region Getter / Setter
    public MachineReference getMachine() {
        return this.machine;
    }

    public void setMachine(MachineReference machine) {
        this.machine = machine;
    }

    //endregion Getter / Setter

    public boolean isMouseOverSlot(double mouseX, double mouseY) {
        return this.parent.isPointInRegionController(this.x - this.guiLeft, this.y - this.guiTop, 16, 16, mouseX, mouseY);
    }

    public void drawSlot(GuiGraphics guiGraphics, int mx, int my) {
        guiGraphics.pose().pushPose();
        //render item
        //RenderHelper.setupGuiFlatDiffuseLighting();

        var isMouseOverSlot = this.isMouseOverSlot(mx, my);

        if (isMouseOverSlot)
            guiGraphics.renderItem(this.machine.getExtractItemStack(), this.x, this.y);
        else
            guiGraphics.renderItem(this.machine.getInsertItemStack(), this.x, this.y);

        if (isMouseOverSlot) {
            RenderSystem.colorMask(true, true, true, false);
            this.parent.drawGradientRect(guiGraphics, this.x, this.y, this.x + 16, this.y + 16, this.slotHighlightColor,
                    this.slotHighlightColor);
            RenderSystem.colorMask(true, true, true, true);
        }
        guiGraphics.pose().popPose();
    }

    public void drawTooltip(GuiGraphics guiGraphics, int mx, int my) {
        if (this.isMouseOverSlot(mx, my)) {
            this.parent.renderToolTip(guiGraphics, this.machine, mx, my);
        }
    }

}
