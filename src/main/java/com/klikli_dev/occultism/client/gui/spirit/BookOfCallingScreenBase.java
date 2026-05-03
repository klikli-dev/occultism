/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyle;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.HorizontalSeparatorWidget;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class BookOfCallingScreenBase extends Screen implements GuiHost {
    protected static final int GUI_WIDTH = 182;
    protected static final int GUI_HEIGHT = 140;
    protected static final int TOP_BAR_HEIGHT = 18;
    protected static final int MAIN_LEFT = 3;
    protected static final int MAIN_TOP = 15;
    protected static final int MAIN_WIDTH = 176;
    protected static final int MAIN_HEIGHT = 122;
    protected static final int TITLE_Y = 6;
    protected static final int CONTENT_LEFT = 14;
    protected static final int CONTENT_TOP = 30;
    protected static final int LABEL_RIGHT_X = 58;
    protected static final int BUTTON_LEFT = 66;
    protected static final int BUTTON_WIDTH = 102;
    protected static final int ROW_HEIGHT = 25;
    protected static final int BUTTON_HEIGHT = 20;
    protected static final int DIVIDER_Y = 84;
    protected static final int EXIT_BUTTON_SIZE = 20;

    protected final GuiRootWidget root = new GuiRootWidget(this);
    protected int leftPos;
    protected int topPos;

    protected BookOfCallingScreenBase() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth()) / 2;
        this.topPos = (this.height - this.imageHeight()) / 2;
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(MAIN_LEFT), this.guiY(MAIN_TOP), MAIN_WIDTH,
                MAIN_HEIGHT, this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_PANEL, GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(0), this.imageWidth(),
                TOP_BAR_HEIGHT, this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_TOP_BAR, GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new HorizontalSeparatorWidget(this.guiX(MAIN_LEFT), this.guiY(DIVIDER_Y), MAIN_WIDTH,
                this.partColor(OccultismGuiParts.BOOK_OF_CALLING_HORIZONTAL_SEPARATOR, 0xFF000000)));
        this.addBackgroundChildren();
        this.root.syncWithHost();

        LabelWidget titleLabel = new LabelWidget(this.guiX(this.imageWidth() / 2), this.guiY(TITLE_Y - 1), true,
                -1, 2, 2, this.partTextColor(OccultismGuiParts.BOOK_OF_CALLING_TITLE, 0x303030));
        titleLabel.addLine(this.title());
        this.addRenderableWidget(titleLabel);

        this.initContents();
    }

    protected void addBackgroundChildren() {
    }

    protected abstract void initContents();

    protected abstract Component title();

    protected void addLabelRow(int y, String translationKey) {
        LabelWidget label = new LabelWidget(this.guiX(LABEL_RIGHT_X), this.guiY(y), false, -1, 2, 2, 0xFFFFFFFF)
                .alignRight(true);
        label.addLine(Component.translatable(translationKey).copy().withStyle(ChatFormatting.WHITE));
        this.addRenderableWidget(label);
    }

    protected GuiStyle style() {
        return GuiStyleRegistry.get(OccultismGuiStyles.BOOK_OF_CALLING);
    }

    protected GuiSprite partSprite(GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    protected int partColor(GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.COLOR, fallback);
    }

    protected int partTextColor(GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.TEXT_COLOR, fallback);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public <W extends AbstractWidget> W addGuiWidget(W widget) {
        return this.addRenderableWidget(widget);
    }

    @Override
    public void removeGuiWidget(AbstractWidget widget) {
        this.removeWidget(widget);
    }

    @Override
    public int leftPos() {
        return this.leftPos;
    }

    @Override
    public int topPos() {
        return this.topPos;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public int imageWidth() {
        return GUI_WIDTH;
    }

    @Override
    public int imageHeight() {
        return GUI_HEIGHT;
    }
}
