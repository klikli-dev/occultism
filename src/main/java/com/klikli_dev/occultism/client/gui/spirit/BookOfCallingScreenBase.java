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
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.gui.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.input.MouseButtonEvent;

public abstract class BookOfCallingScreenBase extends Screen implements GuiHost {
    protected static final int GUI_WIDTH = 241;
    protected static final int TOP_BAR_Y = -1;
    protected static final int TOP_BAR_HEIGHT = 15;
    protected static final int MAIN_LEFT = 3;
    protected static final int MAIN_TOP = 12;
    protected static final int TITLE_Y = 4;
    protected static final int LABEL_RIGHT_X = 66;
    protected static final int SELECTION_LEFT = 72;
    protected static final int SELECTION_WIDTH = 120;
    protected static final int SELECTION_HEIGHT = 18;
    protected static final int CONFIRM_BUTTON_X = 208;

    protected final GuiRootWidget root = new GuiRootWidget(this);
    private final int imageHeight;
    protected int leftPos;
    protected int topPos;
    private boolean closingHandled;

    protected BookOfCallingScreenBase(Component title, int imageHeight) {
        super(title);
        this.imageHeight = imageHeight;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth()) / 2;
        this.topPos = (this.height - this.imageHeight()) / 2;
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(MAIN_LEFT), this.guiY(MAIN_TOP), this.imageWidth() - 6,
                this.imageHeight() - MAIN_TOP, this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_PANEL, GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(TOP_BAR_Y), this.imageWidth(),
                TOP_BAR_HEIGHT, this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_TOP_BAR, GuiSprites.GUI_BACKGROUND)));
        this.addBackgroundChildren();
        this.root.syncWithHost();

        LabelWidget titleLabel = new LabelWidget(this.guiX(this.imageWidth() / 2), this.guiY(TITLE_Y - 1), true,
                -1, 2, 2, this.partTextColor(OccultismGuiParts.BOOK_OF_CALLING_TITLE, 0xFF000000));
        titleLabel.addLine(this.title);
        this.addRenderableWidget(titleLabel);

        this.initContents();
        this.refreshWidgetState();
    }

    protected void addBackgroundChildren() {
    }

    protected abstract void initContents();

    protected void refreshWidgetState() {
    }

    protected void applyChanges() {
    }

    protected void addLabelRow(int y, String translationKey) {
        LabelWidget label = new LabelWidget(this.guiX(LABEL_RIGHT_X), this.guiY(y), false, -1, 2, 2,
                this.partTextColor(OccultismGuiParts.BOOK_OF_CALLING_LABEL, 0xFFFFFFFF))
                .alignRight(true);
        label.addLine(Component.translatable(translationKey).copy().withStyle(ChatFormatting.WHITE));
        this.addRenderableWidget(label);
    }

    protected void addHorizontalSeparator(int y) {
        this.root.addChild(new HorizontalSeparatorWidget(this.guiX(MAIN_LEFT), this.guiY(y), this.imageWidth() - 6,
                this.partColor(OccultismGuiParts.BOOK_OF_CALLING_HORIZONTAL_SEPARATOR, 0xFF000000)));
    }

    protected void addVerticalSeparator(int x, int y, int height) {
        this.root.addChild(new VerticalSeparatorWidget(this.guiX(x), this.guiY(y), height,
                this.partColor(OccultismGuiParts.BOOK_OF_CALLING_VERTICAL_SEPARATOR, 0xFF000000)));
    }

    protected <W extends AbstractWidget> W addRootChild(W widget) {
        return this.root.addChild(widget);
    }

    protected IconButtonWidget addConfirmButton(int y) {
        return this.addRootChild(new IconButtonWidget(this.guiX(CONFIRM_BUTTON_X), this.guiY(y),
                GuiSprites.FILTER_ICON_CONFIRM,
                this.buttonBackgroundSprites(OccultismGuiParts.BOOK_OF_CALLING_CONFIRM_BUTTON),
                Component.translatable("gui.occultism.book_of_calling.confirm"),
                () -> this.closeScreen(true)))
                .withTooltip(Component.translatable("gui.occultism.book_of_calling.confirm.tooltip"));
    }

    protected IconButtonBackgroundSprites buttonBackgroundSprites(GuiPartKey part) {
        return new IconButtonBackgroundSprites(
                this.style().get(part, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON),
                this.style().get(part, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN),
                this.style().get(part, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER)
        );
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
    public void tick() {
        super.tick();
        this.refreshWidgetState();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        for (var listener : this.children()) {
            if (listener.isMouseOver(mouseX, mouseY) && listener.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
                this.setFocused(listener);
                return true;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void onClose() {
        this.closeScreen(false);
    }

    protected final void closeScreen(boolean confirm) {
        if (this.closingHandled) {
            return;
        }

        this.closingHandled = true;
        if (confirm) {
            this.applyChanges();
        }

        super.onClose();
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
        return this.imageHeight;
    }
}
