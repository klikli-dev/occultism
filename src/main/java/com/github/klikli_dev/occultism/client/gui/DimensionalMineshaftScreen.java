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

package com.github.klikli_dev.occultism.client.gui;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import com.github.klikli_dev.occultism.common.tile.DimensionalMineshaftTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DimensionalMineshaftScreen extends ContainerScreen<DimensionalMineshaftContainer> {
    //region Fields
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(Occultism.MODID, "textures/gui/otherworld_miner.png");

    public DimensionalMineshaftTileEntity otherworldMiner;
    //endregion Fields

    //region Initialization
    public DimensionalMineshaftScreen(DimensionalMineshaftContainer screenContainer, PlayerInventory inv,
                                      ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.otherworldMiner = screenContainer.otherworldMiner;
    }
    //endregion Initialization

    //region Overrides
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        //do not call super as it renders the inventory name which we do not want
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(stack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int miningTime = this.otherworldMiner.miningTime;
        int progress = (int) (18 * (1.0F - (float) miningTime / this.otherworldMiner.maxMiningTime));
        if (progress > 0 && miningTime > 0) {
            this.blit(stack, this.guiLeft + 61, this.guiTop + 41, 176, 0, progress+1, 4);
        }
    }
    //endregion Overrides
}
