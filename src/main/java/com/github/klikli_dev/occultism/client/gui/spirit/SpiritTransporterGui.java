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
import com.github.klikli_dev.occultism.network.MessageSetFilterMode;
import com.github.klikli_dev.occultism.network.MessageSetTagFilterText;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.util.InputUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpiritTransporterGui extends SpiritGui<SpiritTransporterContainer> {
    //region Fields
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/gui/inventory_spirit_transporter_tagfilter.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";
    protected final List<ITextComponent> tooltip = new ArrayList<>();
    protected SpiritTransporterContainer container;

    protected Button filterModeButton;
    protected TextFieldWidget tagFilterTextField;

    protected String tagFilter;
    //endregion Fields

    //region Initialization
    public SpiritTransporterGui(SpiritTransporterContainer container,
                                PlayerInventory playerInventory,
                                ITextComponent titleIn) {
        super(container, playerInventory, titleIn);

        this.container = container;

        this.imageWidth = 176; //photoshop says 176
        this.imageHeight = 202; //photoshop says 188
    }
    //endregion Initialization

    //region Getter / Setter
    public boolean isBlacklist() {
        return this.spirit.isFilterBlacklist();
    }

    public String getTagFilterText() {
        return this.tagFilter;
    }

    public void setTagFilterText(String tagFilterText) {
        this.tagFilter = tagFilterText;
    }

    public void setIsBlacklist(boolean isBlacklist) {
        this.spirit.setFilterBlacklist(isBlacklist);
        OccultismPackets.sendToServer(new MessageSetFilterMode(isBlacklist, this.spirit.getId()));
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public void init() {
        super.init();

        this.setTagFilterText(this.spirit.getTagFilter());

        int isBlacklistButtonTop = 83;
        int isBlacklistButtonLeft = 151;
        int buttonSize = 18;

        int isBlacklistOffset = (this.isBlacklist() ? 0 : 1) * 20;
        this.filterModeButton = new SizedImageButton(this.leftPos + isBlacklistButtonLeft,
                this.topPos + isBlacklistButtonTop, buttonSize, buttonSize, 177, isBlacklistOffset,
                20, 20, 20, 256, 256, TEXTURE,
                (button) -> {
                    this.setIsBlacklist(!this.isBlacklist());
                    this.init();
                });
        this.addButton(this.filterModeButton);

        int tagFilterLeft = 8;
        int tagFilterTop = 105;
        int tagFilterWidth = 124;
        this.tagFilterTextField = new TextFieldWidget(this.font, this.leftPos + tagFilterLeft,
                this.topPos + tagFilterTop, tagFilterWidth, this.font.lineHeight,
                new StringTextComponent("forge:ores;*logs*"));
        this.tagFilterTextField.setMaxLength(90);

        this.tagFilterTextField.setBordered(false);
        this.tagFilterTextField.setVisible(true);
        this.tagFilterTextField.setTextColor(Color.WHITE.getRGB());
        this.tagFilterTextField.setFocus(false);

        if (!StringUtils.isBlank(this.getTagFilterText())) {
            this.tagFilterTextField.setValue(this.getTagFilterText());
        }
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        this.minecraft.getTextureManager().bind(TEXTURE);

        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        RenderSystem.pushMatrix();
        int scale = 30;
        drawEntityToGui(this.leftPos + 35, this.topPos + 65, scale, this.leftPos + 51 - x,
                this.topPos + 75 - 50 - y, this.spirit);
        RenderSystem.popMatrix();
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (this.filterModeButton.isHovered()) {
            this.tooltip.add(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".filter_mode"));
            this.tooltip.add(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".filter_mode."
                    + (this.isBlacklist() ? "blacklist" : "whitelist"))
                    .withStyle(TextFormatting.GRAY));
        }

        //can't use isHovered here, as it also checks for focus
        if (this.isPointInSearchbar(mouseX, mouseY)) {
            this.tooltip.add(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".tag_filter"));
        }

        if (!this.tooltip.isEmpty()) {
            GuiUtils.drawHoveringText(matrixStack, this.tooltip, mouseX - this.leftPos,
                    mouseY - this.topPos, this.width, this.height, -1,
                    Minecraft.getInstance().font);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.tagFilterTextField.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

        if (this.isPointInSearchbar(mouseX, mouseY)) {
            if (mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                this.tagFilterTextField.setValue("");
                this.setTagFilterText("");
            }
        }

        if (this.tagFilterTextField.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        //prevent exiting without saving
        this.setTagFilterText(this.tagFilterTextField.getValue());

        if (this.tagFilterTextField.isFocused() &&
                this.tagFilterTextField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }

        //Handle inventory key down in search bar:
        if (this.tagFilterTextField.isFocused()) {
            InputMappings.Input mouseKey = InputMappings.getKey(keyCode, scanCode);
            if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void removed() {
        super.removed();
        this.tagFilterTextField.setFocus(false);

        //we used to check for blank tag filter here, but that prevents emptying the filter
        this.spirit.setTagFilter(this.tagFilter);
        OccultismPackets.sendToServer(new MessageSetTagFilterText(this.tagFilter, this.spirit.getId()));
    }

    @Override
    public void tick() {
        this.tagFilterTextField.tick();
        super.tick();
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.tagFilterTextField.isFocused() && this.tagFilterTextField.charTyped(codePoint, modifiers)) {
            this.setTagFilterText(this.tagFilterTextField.getValue());
        }

        return false;
    }
    //endregion Overrides

    //region Methods
    protected boolean isPointInSearchbar(double mouseX, double mouseY) {
        return this.isHovering(this.tagFilterTextField.x - this.leftPos, this.tagFilterTextField.y - this.topPos,
                this.tagFilterTextField.getWidth() - 5, this.font.lineHeight + 6, mouseX, mouseY);
    }
    //endregion Methods
}
