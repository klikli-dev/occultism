/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.widget;

import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.widget.AbstractScrollSelectionWidget;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class BookOfCallingSelectionWidget<T> extends AbstractScrollSelectionWidget<T> {
    private static final int HEADER_COLOR = 0xFFB0F4D6;
    private static final int TEXT_COLOR = 0xFFF7F1E8;

    private final Function<T, Component> labelFactory;
    private final Component emptyText;
    private final Component scrollHint;

    public BookOfCallingSelectionWidget(int x, int y, int width, int height, GuiSprite sprite,
                                        Supplier<List<T>> entries, IntSupplier selectedIndex,
                                        IntConsumer onChange, Function<T, Component> labelFactory,
                                        Component emptyText, Component scrollHint) {
        super(x, y, width, height, sprite, entries, selectedIndex, onChange);
        this.labelFactory = labelFactory;
        this.emptyText = emptyText;
        this.scrollHint = scrollHint;
    }

    @Override
    public BookOfCallingSelectionWidget<T> withTitle(Component title) {
        super.withTitle(title);
        return this;
    }

    @Override
    protected Component entryLabel(T entry) {
        return this.labelFactory.apply(entry);
    }

    @Override
    protected Component emptyEntriesText() {
        return this.emptyText;
    }

    @Override
    protected Component scrollHintText() {
        return this.scrollHint;
    }

    @Override
    protected int entryTextColor() {
        return TEXT_COLOR;
    }

    @Override
    protected int headerColor() {
        return HEADER_COLOR;
    }
}
