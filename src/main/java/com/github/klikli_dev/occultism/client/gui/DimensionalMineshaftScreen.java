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
import com.github.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.github.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DimensionalMineshaftScreen extends AbstractContainerScreen<DimensionalMineshaftContainer> {
    //region Fields
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(Occultism.MODID, "textures/gui/otherworld_miner.png");

    public DimensionalMineshaftBlockEntity otherworldMiner;
    //endregion Fields

    //region Initialization
    public DimensionalMineshaftScreen(DimensionalMineshaftContainer screenContainer, Inventory inv,
                                      Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.otherworldMiner = screenContainer.otherworldMiner;
    }
    //endregion Initialization

    //region Overrides
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.width, this.height);
        int miningTime = this.otherworldMiner.miningTime;
        int progress = (int) (18 * (1.0F - (float) miningTime / this.otherworldMiner.maxMiningTime));
        if (progress > 0 && miningTime > 0) {
            this.blit(stack, this.leftPos + 61, this.topPos + 41, 176, 0, progress + 1, 4);
        }
    }
    //endregion Overrides
}
