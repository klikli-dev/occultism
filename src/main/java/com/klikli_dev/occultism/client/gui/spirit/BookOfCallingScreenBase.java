/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolveContext;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiTextWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonWidget;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.widget.VerticallyCenteredTextWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class BookOfCallingScreenBase extends Screen implements GuiHost, LayoutScreenView {
    protected static final int GUI_WIDTH = 241;

    protected final GuiRootWidget root = new GuiRootWidget(this);
    private final ScreenLayoutController layoutController;
    private final int imageHeight;
    protected int leftPos;
    protected int topPos;
    private boolean closingHandled;

    protected BookOfCallingScreenBase(Component title, int imageHeight) {
        super(title);
        this.imageHeight = imageHeight;
        this.layoutController = new ScreenLayoutController(this, this, this.root,
                new GuiStyleContext(GuiStyleRegistry.get(OccultismGuiStyles.BOOK_OF_CALLING)));
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth()) / 2;
        this.topPos = (this.height - this.imageHeight()) / 2;
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();
        this.root.syncWithHost();
        this.afterLayoutInit();
        this.refreshWidgetState();
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        registry.resolve("frame.panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_PANEL, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.top_bar.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_TOP_BAR, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.top_bar.title", ctx -> {
            int titleX = ctx.node().x() + (ctx.node().widthOrThrow() - this.font.width(this.title)) / 2;
            ctx.addWidget(new VerticallyCenteredTextWidget(
                    titleX,
                    ctx.node().y(),
                    -0.5F,
                    () -> this.title,
                    () -> ctx.style().textColor(OccultismGuiParts.BOOK_OF_CALLING_TITLE, 0xFF000000),
                    false
            ));
        });
        this.registerContentResolvers(registry.scope("content"));
    }

    protected abstract void registerContentResolvers(LayoutResolverRegistry registry);

    protected void afterLayoutInit() {
    }

    protected void refreshWidgetState() {
    }

    protected void applyChanges() {
    }

    protected final void addLabel(LayoutResolveContext ctx, Component text) {
        int labelX = ctx.node().maxX() - this.font.width(text);
        ctx.addWidget(new GuiTextWidget(
                labelX,
                ctx.node().y(),
                () -> text,
                () -> ctx.style().textColor(OccultismGuiParts.BOOK_OF_CALLING_LABEL, 0xFFFFFFFF),
                false
        ));
    }

    protected <W extends AbstractWidget> W addRootChild(W widget) {
        return this.root.addChild(widget);
    }

    protected final IconButtonWidget addConfirmButton(LayoutResolveContext ctx) {
        return this.addRootChild(new IconButtonWidget(
                ctx.node().x(),
                ctx.node().y(),
                GuiSprites.FILTER_ICON_CONFIRM,
                ctx.style().iconButtonBackgroundSprites(OccultismGuiParts.BOOK_OF_CALLING_CONFIRM_BUTTON, IconButtonBackgroundSprites.DEFAULT),
                Component.translatable("gui.occultism.book_of_calling.confirm"),
                () -> this.closeScreen(true)))
                .withTooltip(Component.translatable("gui.occultism.book_of_calling.confirm.tooltip"));
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
