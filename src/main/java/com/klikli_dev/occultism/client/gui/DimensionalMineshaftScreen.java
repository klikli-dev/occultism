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

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class DimensionalMineshaftScreen extends AbstractContainerScreen<DimensionalMineshaftContainer> {

    public static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/gui/otherworld_miner.png");

    public DimensionalMineshaftBlockEntity otherworldMiner;

    public DimensionalMineshaftScreen(DimensionalMineshaftContainer screenContainer, Inventory inv,
                                      Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.otherworldMiner = screenContainer.otherworldMiner;
    }

    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(guiGraphics); //called by super
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, (float) 0, (float) 0, this.imageWidth, this.imageHeight, 256, 256);

        int miningTime = this.otherworldMiner.miningTime;
        int progress = (int) (18 * (1.0F - (float) miningTime / this.otherworldMiner.maxMiningTime));
        if (progress > 0 && miningTime > 0) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos + 61, this.topPos + 41, (float) 176, (float) 0, progress + 1, 4, 256, 256);
        }
        if (this.otherworldMiner.inputHandler.getStackInSlot(0).isEmpty())
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos + 26, this.topPos + 35, (float) 176, (float) 4, 16, 16, 256, 256);

        super.extractContents(guiGraphics, mouseX, mouseY, partialTicks);
    }

}
