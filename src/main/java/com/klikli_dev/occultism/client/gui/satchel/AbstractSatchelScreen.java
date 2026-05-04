/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.satchel;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyle;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiSpriteWidget;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.common.container.satchel.AbstractSatchelContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public abstract class AbstractSatchelScreen<T extends AbstractSatchelContainer> extends AbstractContainerScreen<T> implements GuiHost {
    private static final int PLAYER_SLOT_COUNT = 36;

    protected final GuiRootWidget root;

    protected AbstractSatchelScreen(T menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.root = new GuiRootWidget(this);
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(this.mainLeft()), this.guiY(this.mainTop()),
                this.mainWidth(), this.mainHeight(), this.partSprite(OccultismGuiParts.SATCHEL_PANEL,
                GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(this.inventoryBackgroundLeft()),
                this.guiY(this.inventoryBackgroundTop()), this.inventoryBackgroundWidth(),
                this.inventoryBackgroundHeight(), this.partSprite(OccultismGuiParts.SATCHEL_PLAYER_INVENTORY_BACKGROUND,
                GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(this.topBarY()), this.imageWidth,
                this.topBarHeight(), this.partSprite(OccultismGuiParts.SATCHEL_TOP_BAR, GuiSprites.GUI_BACKGROUND)));

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            this.root.addChild(new GuiSpriteWidget(this.guiX(slot.x - 1), this.guiY(slot.y - 1), this.slotSprite(i)));
        }
        this.root.syncWithHost();

        LabelWidget titleLabel = new LabelWidget(this.guiX(this.imageWidth / 2), this.guiY(this.titleY() - 1), true,
                -1, 2, 2, this.partTextColor(OccultismGuiParts.SATCHEL_TITLE, 0x303030));
        titleLabel.addLine(this.topBarTitleText());
        this.addRenderableWidget(titleLabel);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        guiGraphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX(), this.inventoryLabelY(), 0x303030,
                false);
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
        return this.imageWidth;
    }

    @Override
    public int imageHeight() {
        return this.imageHeight;
    }

    protected Component topBarTitle() {
        return this.title;
    }

    protected String topBarTitleText() {
        String titleText = this.topBarTitle().getString();
        if (titleText.length() >= 2 && titleText.startsWith("[") && titleText.endsWith("]")) {
            return titleText.substring(1, titleText.length() - 1);
        }

        return titleText;
    }

    protected GuiStyle style() {
        return GuiStyleRegistry.get(OccultismGuiStyles.SATCHEL);
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

    protected GuiSprite slotSprite(int slotIndex) {
        GuiPartKey part = slotIndex < this.satchelSlotCount() ? OccultismGuiParts.SATCHEL_SLOT
                : OccultismGuiParts.SATCHEL_PLAYER_SLOT;
        return this.partSprite(part, GuiSprites.INVENTORY_SLOT);
    }

    protected int satchelSlotCount() {
        return this.menu.slots.size() - PLAYER_SLOT_COUNT;
    }

    protected int titleY() {
        return 9;
    }

    protected int topBarHeight() {
        return 16;
    }

    protected int topBarY() {
        return -1;
    }

    protected int mainLeft() {
        return 3;
    }

    protected int mainTop() {
        return this.topBarHeight() - 3;
    }

    protected int mainWidth() {
        return this.imageWidth - 6;
    }

    protected abstract int mainHeight();

    protected int inventoryBackgroundLeft() {
        return 3;
    }

    protected abstract int inventoryBackgroundTop();

    protected int inventoryBackgroundWidth() {
        return this.imageWidth - 6;
    }

    protected abstract int inventoryBackgroundHeight();

    protected abstract int inventoryLabelX();

    protected abstract int inventoryLabelY();
}
