/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ResolvedLayout;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractStorageTerminalScreen<T extends StorageControllerContainerBase> extends AbstractContainerScreen<T>
        implements GuiHost, LayoutScreenView {

    protected final GuiRootWidget root;
    protected final ScreenLayoutController layoutController;
    protected ResolvedLayout resolvedLayout;

    protected AbstractStorageTerminalScreen(T container, Inventory playerInventory, Component name, int imageWidth, int imageHeight) {
        super(container, playerInventory, name, imageWidth, imageHeight);
        this.root = new GuiRootWidget(this);
        this.layoutController = new ScreenLayoutController(this, this, this.root,
                new GuiStyleContext(GuiStyleRegistry.get(OccultismGuiStyles.STORAGE_CONTROLLER)));
    }

    @Override
    public LayoutSpec layoutSpec() {
        return StorageTerminalLayouts.create(this.visibleRows());
    }

    protected abstract int visibleRows();

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
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
}
