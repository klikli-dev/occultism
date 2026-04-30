// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.filter;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.gui.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.common.container.filter.ListFilterMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ListFilterScreen extends OccultismListFilterScreenBase {
    private static final int BLACK = 0xFF000000;
    private static final int TOP_SECTION_HEIGHT = 15;
    private static final int SECOND_BACKGROUND_X_OFFSET = 3;
    private static final int SECOND_BACKGROUND_Y_OFFSET = TOP_SECTION_HEIGHT - 3;
    private static final int SECOND_BACKGROUND_WIDTH_OFFSET = 6;
    private static final int MIDDLE_SECTION_HEIGHT = 87;
    private static final int HORIZONTAL_SEPARATOR_Y = 64;
    private static final int VERTICAL_SEPARATOR_X = 145;

    public ListFilterScreen(ListFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void addBackgroundWidgets() {
        var tintedBackground = GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT);
        var tintedTopBar = GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT);

        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos + SECOND_BACKGROUND_X_OFFSET, this.topPos + SECOND_BACKGROUND_Y_OFFSET, this.imageWidth - SECOND_BACKGROUND_WIDTH_OFFSET, MIDDLE_SECTION_HEIGHT, tintedBackground));
        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos, this.topPos, this.imageWidth, TOP_SECTION_HEIGHT, tintedTopBar));
    }

    @Override
    protected void addScreenWidgets() {
        super.addScreenWidgets();

        int secondBackgroundX = this.leftPos + SECOND_BACKGROUND_X_OFFSET;
        int secondBackgroundY = this.topPos + SECOND_BACKGROUND_Y_OFFSET;
        int secondBackgroundWidth = this.imageWidth - SECOND_BACKGROUND_WIDTH_OFFSET;
        int secondBackgroundBottom = secondBackgroundY + MIDDLE_SECTION_HEIGHT;
        int horizontalSeparatorY = this.topPos + HORIZONTAL_SEPARATOR_Y;

        this.root.addChild(new HorizontalSeparatorWidget(secondBackgroundX, horizontalSeparatorY, secondBackgroundWidth));
        this.root.addChild(new VerticalSeparatorWidget(this.leftPos + VERTICAL_SEPARATOR_X, horizontalSeparatorY, secondBackgroundBottom - horizontalSeparatorY, BLACK));
    }
}
