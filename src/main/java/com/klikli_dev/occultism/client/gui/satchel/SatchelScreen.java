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

package com.klikli_dev.occultism.client.gui.satchel;

import com.klikli_dev.occultism.common.container.satchel.AbstractSatchelContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SatchelScreen extends AbstractSatchelScreen<AbstractSatchelContainer> {
    private static final int GUI_WIDTH = 320;
    private static final int GUI_HEIGHT = 248;
    private static final int MAIN_HEIGHT = 138;
    private static final int MAIN_TOP = 11;
    private static final int MAIN_WIDTH = 315;
    private static final int INVENTORY_BACKGROUND_LEFT = 69;
    private static final int INVENTORY_BACKGROUND_TOP = 153;
    private static final int INVENTORY_BACKGROUND_WIDTH = 176;
    private static final int INVENTORY_BACKGROUND_HEIGHT = 90;
    private static final int INVENTORY_LABEL_X = 77;
    private static final int INVENTORY_LABEL_Y = 166;

    public SatchelScreen(AbstractSatchelContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    protected int mainHeight() {
        return MAIN_HEIGHT;
    }

    @Override
    protected int mainTop() {
        return MAIN_TOP;
    }

    @Override
    protected int mainWidth() {
        return MAIN_WIDTH;
    }

    @Override
    protected int inventoryBackgroundTop() {
        return INVENTORY_BACKGROUND_TOP;
    }

    @Override
    protected int inventoryBackgroundLeft() {
        return INVENTORY_BACKGROUND_LEFT;
    }

    @Override
    protected int inventoryBackgroundWidth() {
        return INVENTORY_BACKGROUND_WIDTH;
    }

    @Override
    protected int inventoryBackgroundHeight() {
        return INVENTORY_BACKGROUND_HEIGHT;
    }

    @Override
    protected int inventoryLabelX() {
        return INVENTORY_LABEL_X;
    }

    @Override
    protected int inventoryLabelY() {
        return INVENTORY_LABEL_Y;
    }
}
