// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.filter;

import com.klikli_dev.occultism.common.container.filter.AttributeFilterMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AttributeFilterScreen extends com.klikli_dev.codedefinedgui.gui.filter.AttributeFilterScreen<AttributeFilterMenu> {
    private static final int BLACK = 0xFF000000;
    private static final int TOP_SECTION_HEIGHT = 15;
    private static final int MIDDLE_SECTION_HEIGHT = 75;
    private static final int SECOND_BACKGROUND_X_OFFSET = 3;
    private static final int SECOND_BACKGROUND_Y_OFFSET = TOP_SECTION_HEIGHT - 3;
    private static final int SECOND_BACKGROUND_WIDTH_OFFSET = 6;
    private static final int HORIZONTAL_SEPARATOR_Y = 48;
    private static final int VERTICAL_SEPARATOR_X = 202;

    public AttributeFilterScreen(AttributeFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void addBackgroundWidgets() {
        this.root.addChild(new TintedSpriteWidget(this.leftPos + SECOND_BACKGROUND_X_OFFSET, this.topPos + SECOND_BACKGROUND_Y_OFFSET, this.imageWidth - SECOND_BACKGROUND_WIDTH_OFFSET, MIDDLE_SECTION_HEIGHT, OccultismFilterScreenStyle.GUI_BACKGROUND_SPRITE, OccultismFilterScreenStyle.BACKGROUND_TINT));
        this.root.addChild(new TintedSpriteWidget(this.leftPos, this.topPos, this.imageWidth, TOP_SECTION_HEIGHT, OccultismFilterScreenStyle.GUI_BACKGROUND_SPRITE, OccultismFilterScreenStyle.TOP_BAR_TINT));
    }

    @Override
    protected void addScreenWidgets() {
        super.addScreenWidgets();

        int secondBackgroundX = this.leftPos + SECOND_BACKGROUND_X_OFFSET;
        int secondBackgroundY = this.topPos + SECOND_BACKGROUND_Y_OFFSET;
        int secondBackgroundWidth = this.imageWidth - SECOND_BACKGROUND_WIDTH_OFFSET;
        int secondBackgroundBottom = secondBackgroundY + MIDDLE_SECTION_HEIGHT;
        int horizontalSeparatorY = this.topPos + HORIZONTAL_SEPARATOR_Y;

        this.root.addChild(new SeparatorWidget(secondBackgroundX, horizontalSeparatorY, secondBackgroundWidth, 1, BLACK));
        this.root.addChild(new SeparatorWidget(this.leftPos + VERTICAL_SEPARATOR_X, horizontalSeparatorY, 1, secondBackgroundBottom - horizontalSeparatorY, BLACK));
    }
}
