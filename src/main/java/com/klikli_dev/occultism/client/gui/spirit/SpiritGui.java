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

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class SpiritGui<T extends SpiritContainer> extends AbstractContainerScreen<T> {

    protected static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected IFilterConfigurable spirit;
    protected T container;

    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        super(container, playerInventory, titleIn, 175, 165);
        this.container = container;
        this.spirit = this.container.spirit;
    }

    public SpiritGui(T container, Inventory playerInventory, Component titleIn, int imageWidth, int imageHeight) {
        super(container, playerInventory, titleIn, imageWidth, imageHeight);
        this.container = container;
        this.spirit = this.container.spirit;
    }

    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void init() {
        super.init();
        this.clearWidgets();

        int labelHeight = 9;
        LabelWidget nameLabel = new LabelWidget(this.infoLabelLeft(), this.infoLabelTop(), false, -1, 2, this.infoLabelColor());
        nameLabel.addLine(TextUtil.formatDemonName(this.spirit.getEntity().getName().getString()));
        this.addRenderableWidget(nameLabel);

        if (this.spirit instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0) {
            int agePercent = (int) Math.floor(spiritEntity.getSpiritAge() / (float) spiritEntity.getSpiritMaxAge() * 100);
            LabelWidget ageLabel = new LabelWidget(this.infoLabelLeft(), this.infoLabelTop() + labelHeight + 5, false, -1, 2, this.infoLabelColor());
            ageLabel.addLine(I18n.get(TRANSLATION_KEY_BASE + ".age", agePercent));
            this.addRenderableWidget(ageLabel);
        }

        String jobID = this.spirit instanceof SpiritEntity spiritEntity ? spiritEntity.getJobID() : "";
        if (!StringUtils.isBlank(jobID)) {
            jobID = jobID.replace(":", ".");
            LabelWidget jobLabel = new LabelWidget(this.infoLabelLeft(),
                    this.infoLabelTop() + labelHeight + 5 + labelHeight + 5 + 5, false, -1, 2, this.infoLabelColor());

            String jobText = I18n.get(TRANSLATION_KEY_BASE + ".job", I18n.get("job." + jobID));
            String[] lines = WordUtils.wrap(jobText, 15, "\n", true).split("[\\r\\n]+", 2);
            for (String line : lines)
                jobLabel.addLine(ChatFormatting.ITALIC + line + ChatFormatting.RESET);
            this.addRenderableWidget(jobLabel);

        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTicks) {
//        this.renderBackground(guiGraphics); //called by super

        this.extractBackground(guiGraphics);

        this.extractSpiritEntity(guiGraphics, x, y, partialTicks);

        super.extractContents(guiGraphics, x, y, partialTicks);
    }

    protected void extractSpiritEntity(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTicks) {
    }

    protected int infoLabelLeft() {
        return this.leftPos + 65;
    }

    protected int infoLabelTop() {
        return this.topPos + 17;
    }

    protected int infoLabelColor() {
        return 0x404040;
    }

    protected void extractBackground(GuiGraphicsExtractor guiGraphics) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.getTexture(), this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }

    protected Identifier getTexture() {
        return TEXTURE;
    }
}
