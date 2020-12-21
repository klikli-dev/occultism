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

package com.github.klikli_dev.occultism.client.gui.spirit;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.gui.controls.SizedImageButton;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.github.klikli_dev.occultism.common.job.TransportItemsJob;
import com.github.klikli_dev.occultism.network.MessageSetFilterMode;
import com.github.klikli_dev.occultism.network.MessageSortItems;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class SpiritTransporterGui extends SpiritGui<SpiritTransporterContainer> {
    //region Fields
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/gui/inventory_spirit_transporter.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";
    protected final List<ITextComponent> tooltip = new ArrayList<>();
    protected SpiritTransporterContainer container;

    protected Button filterModeButton;
    //endregion Fields

    //region Initialization
    public SpiritTransporterGui(SpiritTransporterContainer container,
                                PlayerInventory playerInventory,
                                ITextComponent titleIn) {
        super(container, playerInventory, titleIn);

        this.container = container;

        this.xSize = 176; //photoshop says 176
        this.ySize = 188; //photoshop says 188
    }
    //endregion Initialization

    //region Getter / Setter
    public boolean isBlacklist() {
        return this.spirit.isFilterBlacklist();
    }

    public void setIsBlacklist(boolean isBlacklist) {
        this.spirit.setFilterBlacklist(isBlacklist);
        OccultismPackets.sendToServer(new MessageSetFilterMode(isBlacklist, this.spirit.getEntityId()));
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public void init() {
        super.init();

        int isBlacklistButtonTop = 83;
        int isBlacklistButtonLeft = 151;
        int buttonSize = 18;

        int isBlacklistOffset = (this.isBlacklist() ? 0 : 1) * 20;
        this.filterModeButton = new SizedImageButton(this.guiLeft + isBlacklistButtonLeft,
                this.guiTop + isBlacklistButtonTop, buttonSize, buttonSize, 177, isBlacklistOffset,
                20, 20, 20, 256, 256, TEXTURE,
                (button) -> {
                    this.setIsBlacklist(!this.isBlacklist());
                    this.init();
                });
        this.addButton(this.filterModeButton);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        RenderSystem.pushMatrix();
        int scale = 30;
        drawEntityToGui(this.guiLeft + 35, this.guiTop + 65, scale, this.guiLeft + 51 - x,
                this.guiTop + 75 - 50 - y, this.spirit);
        RenderSystem.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (this.filterModeButton.isHovered()) {
            this.tooltip.add(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".filter_mode"));
            this.tooltip.add(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".filter_mode."
                                                          + (this.isBlacklist() ? "blacklist" : "whitelist"))
                                     .mergeStyle(TextFormatting.GRAY));
        }

        if (!this.tooltip.isEmpty()) {
            GuiUtils.drawHoveringText(matrixStack, this.tooltip, mouseX - this.guiLeft,
                    mouseY - this.guiTop, this.width, this.height, -1,
                    Minecraft.getInstance().fontRenderer);
        }
    }

    //endregion Overrides
}
