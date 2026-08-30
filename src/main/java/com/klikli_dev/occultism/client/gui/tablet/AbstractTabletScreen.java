package com.klikli_dev.occultism.client.gui.tablet;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiTextWidget;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiSprites;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.common.container.tablet.AbstractTabletContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractTabletScreen <T extends AbstractTabletContainer> extends AbstractContainerScreen<T> implements GuiHost, LayoutScreenView {
    private static final int PLAYER_SLOT_COUNT = 36;

    protected final GuiRootWidget root;
    private final ScreenLayoutController layoutController;

    protected AbstractTabletScreen(T menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.root = new GuiRootWidget(this);
        this.layoutController = new ScreenLayoutController(this, this, this.root,
                new GuiStyleContext(GuiStyleRegistry.get(OccultismGuiStyles.TABLET)));
    }

    @Override
    protected void init() {
        super.init();
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
    public void registerResolvers(LayoutResolverRegistry registry) {
        registry.resolve("frame.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.TABLET_BACKGROUND, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.TABLET_PANEL, OccultismGuiSprites.TELEPORT_TABLET_BACKGROUND)
        )));
        registry.resolve("frame.player_inventory.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.TABLET_PLAYER_INVENTORY_BACKGROUND, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.player_inventory.label", ctx -> ctx.addWidget(new GuiTextWidget(
                ctx.node().x(),
                ctx.node().y(),
                () -> this.playerInventoryTitle,
                () -> ctx.style().textColor(BuiltinGuiParts.PLAYER_INVENTORY_LABEL, 0x303030),
                false
        )));
        this.registerSlotResolvers(registry);
    }

    @Override
    public abstract LayoutSpec layoutSpec();

    protected void registerSlotResolvers(LayoutResolverRegistry registry) {
        for (int slotIndex = 0; slotIndex < this.menu.slots.size(); slotIndex++) {
            String nodePath = this.slotNodePath(slotIndex);
            if (nodePath == null) {
                continue;
            }

            int currentSlotIndex = slotIndex;
            registry.add(nodePath, 0, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    ctx.node().x() - 1,
                    ctx.node().y() - 1,
                    ctx.style().sprite(this.slotPart(currentSlotIndex), GuiSprites.INVENTORY_SLOT)
            )));
        }
    }

    protected String slotNodePath(int slotIndex) {
        if (slotIndex < this.tabletSlotCount()) {
            return "content.tablet.slot_" + slotIndex;
        }

        int playerSlotIndex = slotIndex - this.tabletSlotCount();
        if (playerSlotIndex < 27) {
            return "frame.player_inventory.main.slot_" + playerSlotIndex;
        }

        if (playerSlotIndex < PLAYER_SLOT_COUNT) {
            return "frame.player_inventory.hotbar.slot_" + (playerSlotIndex - 27);
        }

        return null;
    }

    @Override
    public <W extends AbstractWidget > W addGuiWidget(W widget) {
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

    protected GuiPartKey slotPart(int slotIndex) {
        return slotIndex < this.tabletSlotCount() ? OccultismGuiParts.TABLET_SLOT : OccultismGuiParts.TABLET_PLAYER_SLOT;
    }

    protected int tabletSlotCount() {
        return this.menu.slots.size() - PLAYER_SLOT_COUNT;
    }
}
