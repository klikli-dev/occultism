/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.api.layout.BuiltinLayoutSlotRoles;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSlotView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ResolvedLayout;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory.PlayerInventoryScreenHost;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory.PlayerInventorySection;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractDimensionalMachineScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T>
        implements GuiHost, LayoutScreenView, PlayerInventoryScreenHost {
    private static final int PLAYER_SLOT_COUNT = 36;
    private static final int PROGRESS_BACKGROUND_TINT = 0xFF697586;

    protected final GuiRootWidget root;
    protected final LayoutSpec layoutSpec;
    protected final ScreenLayoutController layoutController;
    protected final PlayerInventorySection playerInventorySection;
    protected final List<LayoutSlotView> playerInventorySlots;
    protected ResolvedLayout resolvedLayout;

    protected AbstractDimensionalMachineScreen(T menu, Inventory playerInventory, Component title, int imageWidth,
                                               int imageHeight, LayoutSpec layoutSpec) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.root = new GuiRootWidget(this);
        this.layoutSpec = layoutSpec;
        this.playerInventorySection = PlayerInventorySection.standard();
        this.playerInventorySlots = this.createPlayerInventorySlots();
        this.layoutController = new ScreenLayoutController(this, this, this.root,
                new GuiStyleContext(GuiStyleRegistry.get(OccultismGuiStyles.DIMENSIONAL_MACHINE)));
    }

    @Override
    protected void init() {
        super.init();
        this.resolvedLayout = this.layoutSpec.resolve();
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();
        this.root.syncWithHost();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderDynamicContents(guiGraphics, mouseX, mouseY, partialTicks);
        super.extractContents(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public LayoutSpec layoutSpec() {
        return this.layoutSpec;
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        registry.resolve("frame.machine_panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.DIMENSIONAL_MACHINE_PANEL, GuiSprites.GUI_BACKGROUND)
        )));
        this.playerInventorySection.registerResolvers(registry.scope("frame.player_inventory"), this);
        registry.resolve("frame.progress.background", ctx -> ctx.addWidget(new GuiSpriteWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.style().sprite(OccultismGuiParts.DIMENSIONAL_MACHINE_PROGRESS_BACKGROUND,
                                OccultismGuiSprites.CRAFTING_PROGRESS_BAR_BACKGROUND.tinted(PROGRESS_BACKGROUND_TINT))
                        .sized(ctx.node().widthOrThrow(), ctx.node().heightOrThrow())
        )));
        this.registerSlotResolvers(registry);
    }

    protected void registerSlotResolvers(LayoutResolverRegistry registry) {
        for (int slotIndex = 0; slotIndex < this.machineSlotCount(); slotIndex++) {
            String nodePath = this.machineSlotNodePath(slotIndex);
            if (nodePath == null) {
                continue;
            }

            int currentSlotIndex = slotIndex;
            registry.add(nodePath, 25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    ctx.node().x() - 1,
                    ctx.node().y() - 1,
                    ctx.style().sprite(this.slotPart(currentSlotIndex), GuiSprites.INVENTORY_SLOT)
            )));
        }
    }

    protected String slotNodePath(int slotIndex) {
        if (slotIndex < this.machineSlotCount()) {
            return this.machineSlotNodePath(slotIndex);
        }

        int playerSlotIndex = slotIndex - this.machineSlotCount();
        if (playerSlotIndex < 27) {
            return "frame.player_inventory.main.slot_" + playerSlotIndex;
        }

        if (playerSlotIndex < PLAYER_SLOT_COUNT) {
            return "frame.player_inventory.hotbar.slot_" + (playerSlotIndex - 27);
        }

        return null;
    }

    protected com.klikli_dev.codedefinedgui.api.style.GuiPartKey slotPart(int slotIndex) {
        return OccultismGuiParts.DIMENSIONAL_MACHINE_SLOT;
    }

    protected abstract int machineSlotCount();

    protected abstract String machineSlotNodePath(int slotIndex);

    protected abstract void renderDynamicContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY,
                                                  float partialTicks);

    protected void renderSpriteAtNode(GuiGraphicsExtractor guiGraphics, String nodePath, GuiSprite sprite) {
        this.renderSpriteAtNode(guiGraphics, nodePath, sprite, sprite.width(), sprite.height());
    }

    protected void renderSpriteAtNode(GuiGraphicsExtractor guiGraphics, String nodePath, GuiSprite sprite, int width,
                                      int height) {
        if (this.resolvedLayout == null) {
            return;
        }

        var node = this.resolvedLayout.node(nodePath);
        sprite.extractRenderState(guiGraphics, this.guiX(node.x()), this.guiY(node.y()), width, height);
    }

    @Override
    public List<LayoutSlotView> layoutSlots() {
        return this.playerInventorySlots;
    }

    private List<LayoutSlotView> createPlayerInventorySlots() {
        List<LayoutSlotView> layoutSlots = new ArrayList<>(PLAYER_SLOT_COUNT);
        for (int slotIndex = 0; slotIndex < PLAYER_SLOT_COUNT; slotIndex++) {
            Slot slot = this.menu.getSlot(slotIndex + this.machineSlotCount());
            if (slotIndex < 27) {
                layoutSlots.add(new LayoutSlotView(slot, BuiltinLayoutSlotRoles.PLAYER_MAIN,
                        BuiltinGuiParts.PLAYER_SLOT, "main.slot_" + slotIndex));
                continue;
            }

            layoutSlots.add(new LayoutSlotView(slot, BuiltinLayoutSlotRoles.PLAYER_HOTBAR,
                    BuiltinGuiParts.PLAYER_SLOT, "hotbar.slot_" + (slotIndex - 27)));
        }

        return List.copyOf(layoutSlots);
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
