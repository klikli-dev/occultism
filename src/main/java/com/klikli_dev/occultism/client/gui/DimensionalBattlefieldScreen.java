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
import com.klikli_dev.occultism.common.blockentity.DimensionalBattlefieldBlockEntity;
import com.klikli_dev.occultism.common.container.DimensionalBattlefieldContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DimensionalBattlefieldScreen extends AbstractContainerScreen<DimensionalBattlefieldContainer> {

    public static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/gui/otherworld_butcher.png");

    public DimensionalBattlefieldBlockEntity otherworldButcher;

    public DimensionalBattlefieldScreen(DimensionalBattlefieldContainer screenContainer, Inventory inv,
                                        Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.otherworldButcher = screenContainer.otherworldButcher;
        this.imageHeight = 192;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(guiGraphics); //called by super
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int mobHealth = this.otherworldButcher.mobHealth;
        int progress = (int) (34 * (1.0F - (float) mobHealth / this.otherworldButcher.maxMobLife));
        if (progress > 0 && mobHealth > 0) {
            guiGraphics.blit(TEXTURE, this.leftPos + 18, this.topPos + 81, 176, 0, progress + 1, 4);
        }
        if (this.otherworldButcher.inputSoulHandler.getStackInSlot(0).isEmpty())
            guiGraphics.blit(TEXTURE, this.leftPos + 27, this.topPos + 37, 176, 4, 16, 16);
        if (this.otherworldButcher.inputWeaponHandler.getStackInSlot(0).isEmpty())
            guiGraphics.blit(TEXTURE, this.leftPos + 14, this.topPos + 59, 176, 20, 16, 16);
        if (this.otherworldButcher.inputFuelHandler.getStackInSlot(0).isEmpty())
            guiGraphics.blit(TEXTURE, this.leftPos + 40, this.topPos + 59, 176, 36, 16, 16);
    }

}
