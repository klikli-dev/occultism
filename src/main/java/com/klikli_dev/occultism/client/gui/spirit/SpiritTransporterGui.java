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
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.client.gui.controls.SizedImageButton;
import com.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.klikli_dev.occultism.network.messages.MessageSetFilterMode;
import com.klikli_dev.occultism.network.messages.MessageSetTagFilterText;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.util.InputUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpiritTransporterGui extends SpiritGui<SpiritTransporterContainer> {

    protected static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/gui/inventory_spirit_transporter_tagfilter.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";
    protected final List<Component> tooltip = new ArrayList<>();
    protected SpiritTransporterContainer container;

    protected SizedImageButton filterModeButton;
    protected EditBox tagFilterTextField;

    protected String tagFilter;

    public SpiritTransporterGui(SpiritTransporterContainer container,
                                Inventory playerInventory,
                                Component titleIn) {
        super(container, playerInventory, titleIn);

        this.container = container;

        this.imageWidth = 176;
        this.imageHeight = 220;
    }

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
        Networking.sendToServer(new MessageSetFilterMode(isBlacklist, this.spirit.getId()));
    }
    //endregion Getter / Setter

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
        this.addRenderableWidget(this.filterModeButton);

        int tagFilterLeft = 8;
        int tagFilterTop = 123;
        int tagFilterWidth = 124;
        this.tagFilterTextField = new EditBox(this.font, this.leftPos + tagFilterLeft,
                this.topPos + tagFilterTop, tagFilterWidth, this.font.lineHeight,
                Component.literal("c:ores;*logs*;item:minecraft:chest"));
        this.tagFilterTextField.setMaxLength(90);

        this.tagFilterTextField.setBordered(false);
        this.tagFilterTextField.setVisible(true);
        this.tagFilterTextField.setTextColor(OccultismConstants.Color.WHITE);
        this.tagFilterTextField.setFocused(false);

        if (!StringUtils.isBlank(this.getTagFilterText())) {
            this.tagFilterTextField.setValue(this.getTagFilterText());
        }
        this.addWidget(this.tagFilterTextField);
        this.setInitialFocus(this.tagFilterTextField);
    }



    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        guiGraphics.pose().pushPose();
        int scale = 30;
        drawEntityToGui(guiGraphics, this.leftPos + 35, this.topPos + 65, scale, this.leftPos + 51 - x,
                this.topPos + 75 - 50 - y, this.spirit);
        guiGraphics.pose().popPose();
    }

    protected void renderFg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (this.filterModeButton.isHoveredOrFocused()) {
            this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".filter_mode"));
            this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".filter_mode."
                            + (this.isBlacklist() ? "blacklist" : "whitelist"))
                    .withStyle(ChatFormatting.GRAY));
        }

        //can't use isHovered here, as it also checks for focus
        if (this.isPointInSearchbar(mouseX, mouseY)) {
            this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".tag_filter"));
        }

        if (!this.tooltip.isEmpty())
            guiGraphics.renderTooltip(this.font, this.tooltip, Optional.empty(), mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.tagFilterTextField.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderFg(guiGraphics, mouseX, mouseY);
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
            InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
            if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        this.tagFilterTextField.setFocused(false);

        //we used to check for blank tag filter here, but that prevents emptying the filter
        this.spirit.setTagFilter(this.tagFilter);
        Networking.sendToServer(new MessageSetTagFilterText(this.tagFilter, this.spirit.getId()));
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.tagFilterTextField.isFocused() && this.tagFilterTextField.charTyped(codePoint, modifiers)) {
            this.setTagFilterText(this.tagFilterTextField.getValue());
        }

        return false;
    }

    protected boolean isPointInSearchbar(double mouseX, double mouseY) {
        return this.isHovering(this.tagFilterTextField.getX() - this.leftPos, this.tagFilterTextField.getY() - this.topPos,
                this.tagFilterTextField.getWidth() - 5, this.font.lineHeight + 6, mouseX, mouseY);
    }

}
