/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
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
import com.klikli_dev.codedefinedgui.gui.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.client.gui.widget.LivingEntityWidget;
import com.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.filter.FilterUiStyles;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpiritTransporterGui extends SpiritGui<SpiritTransporterContainer> implements GuiHost {
    protected static final GuiSprite FILTER_HINT_SPRITE = new GuiSprite(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/transporter_filter_hint"), 18, 18).tinted(0x4DFFFFFF);
    protected static final int ENTITY_INVENTORY_SLOT_INDEX = 36;
    protected static final int TITLE_X = 11;
    protected static final int TITLE_Y = 6;
    protected static final int ENTITY_X = 8;
    protected static final int ENTITY_Y = 21;
    protected static final int ENTITY_WIDTH = 50;
    protected static final int ENTITY_HEIGHT = 50;
    protected static final int GUI_WIDTH = 182;
    protected static final int GUI_HEIGHT = 179;
    protected static final int MAIN_LEFT = 3;
    protected static final int MAIN_TOP = 15;
    protected static final int MAIN_WIDTH = 176;
    protected static final int MAIN_HEIGHT = 58;
    protected static final int NAME_LABEL_X = MAIN_LEFT + 5;
    protected static final int NAME_LABEL_Y = MAIN_TOP + 5;
    protected static final int INVENTORY_BACKGROUND_LEFT = 3;
    protected static final int INVENTORY_BACKGROUND_TOP = 89;
    protected static final int INVENTORY_BACKGROUND_WIDTH = 176;
    protected static final int INVENTORY_BACKGROUND_HEIGHT = 90;
    protected static final int INVENTORY_LABEL_X = 11;
    protected static final int INVENTORY_LABEL_Y = 102;
    protected static final int INVENTORY_SLOT_LEFT = 152;
    protected static final int INVENTORY_SLOT_TOP = 25;
    protected static final int INVENTORY_SLOT_SIZE = 18;
    protected static final int FILTER_SLOT_LEFT = 152;
    protected static final int FILTER_SLOT_TOP = 49;
    protected static final int TOP_BAR_HEIGHT = 18;
    protected static final int VERTICAL_SEPARATOR_X = 140;
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";

    protected final GuiRootWidget root;
    protected final List<Component> tooltip = new ArrayList<>();

    public SpiritTransporterGui(SpiritTransporterContainer container,
                                Inventory playerInventory,
                                Component titleIn) {
        super(container, playerInventory, titleIn, GUI_WIDTH, GUI_HEIGHT);
        this.root = new GuiRootWidget(this);
    }

    @Override
    public void init() {
        super.init();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(MAIN_LEFT), this.guiY(MAIN_TOP), MAIN_WIDTH, MAIN_HEIGHT, this.partSprite(this.panelPart(), GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(0), this.imageWidth, TOP_BAR_HEIGHT, this.partSprite(this.topBarPart(), GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(INVENTORY_BACKGROUND_LEFT), this.guiY(INVENTORY_BACKGROUND_TOP), INVENTORY_BACKGROUND_WIDTH, INVENTORY_BACKGROUND_HEIGHT, this.partSprite(BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND, this.partSprite(this.panelPart(), GuiSprites.GUI_BACKGROUND))));
        this.root.addChild(new VerticalSeparatorWidget(this.guiX(VERTICAL_SEPARATOR_X), this.guiY(MAIN_TOP), MAIN_HEIGHT, this.partColor(this.verticalSeparatorPart(), 0xFF000000)));
        this.root.addChild(new LivingEntityWidget(this, ENTITY_X, ENTITY_Y, ENTITY_WIDTH, ENTITY_HEIGHT, () -> this.spirit.getEntity()));

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            this.root.addChild(new GuiSpriteWidget(this.guiX(slot.x - 1), this.guiY(slot.y - 1), this.slotSprite(i)));
        }
        this.root.syncWithHost();

        LabelWidget titleLabel = new LabelWidget(this.leftPos + TITLE_X, this.topPos + TITLE_Y, false, -1, 2,
                2, this.partColor(this.titlePart(), 0x303030));
        titleLabel.addLine(this.topBarTitle());
        this.addRenderableWidget(titleLabel);
    }

    @Override
    protected void extractBackground(GuiGraphicsExtractor guiGraphics) {
    }

    @Override
    protected void extractSpiritEntity(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTicks) {
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        guiGraphics.text(this.font, TextUtil.formatDemonName(this.spirit.getEntity().getName().getString()), NAME_LABEL_X, NAME_LABEL_Y, 0xFF303030, false);
        guiGraphics.text(this.font, this.playerInventoryTitle, INVENTORY_LABEL_X, INVENTORY_LABEL_Y, 0x303030, false);
    }

    protected Component topBarTitle() {
        if (this.spirit.getEntity() instanceof SpiritEntity spiritEntity && !spiritEntity.getJobID().isBlank()) {
            return Component.translatable("gui.occultism.spirit.job", Component.translatable("job." + spiritEntity.getJobID().replace(':', '.')));
        }

        return this.title;
    }

    @Override
    protected void addSpiritNameWidget() {
    }

    @Override
    protected int infoLabelColor() {
        return 0xFF303030;
    }

    @Override
    protected int infoLabelLeft() {
        return this.leftPos + 68;
    }

    @Override
    protected int infoLabelTop() {
        return this.topPos + 25;
    }

    @Override
    public <T extends AbstractWidget> T addGuiWidget(T widget) {
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

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderFg(guiGraphics, mouseX, mouseY);
    }

    protected void renderFg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (this.isFilterSlotEmpty()) {
            FILTER_HINT_SPRITE.extractRenderState(guiGraphics, this.leftPos + FILTER_SLOT_LEFT, this.topPos + FILTER_SLOT_TOP);
        }

        if (!this.spirit.isInventorySlotActive()) {
            guiGraphics.fillGradient(this.leftPos + INVENTORY_SLOT_LEFT, this.topPos + INVENTORY_SLOT_TOP,
                    this.leftPos + INVENTORY_SLOT_LEFT + INVENTORY_SLOT_SIZE - 2,
                    this.topPos + INVENTORY_SLOT_TOP + INVENTORY_SLOT_SIZE - 2, 0xAA555555, 0xAA555555);
        }

        if (this.isPointInFilterSlot(mouseX, mouseY)) {
            this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".filter_item"));
            if (this.isFilterSlotEmpty()) {
                this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".filter_item.empty")
                        .withStyle(ChatFormatting.GRAY));
            }
        }

        if (this.isPointInInventorySlot(mouseX, mouseY)) {
            if (!this.spirit.isInventorySlotActive()) {
                this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".inventory_slot.disabled")
                        .withStyle(ChatFormatting.GRAY));
            } else if (!this.container.getSlot(ENTITY_INVENTORY_SLOT_INDEX).hasItem()) {
                this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".inventory_slot.block_only")
                        .withStyle(ChatFormatting.GRAY));
            }
        }

        if (!this.tooltip.isEmpty()) {
            guiGraphics.setTooltipForNextFrame(this.font, this.tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    protected boolean isPointInInventorySlot(double mouseX, double mouseY) {
        return this.isHovering(INVENTORY_SLOT_LEFT, INVENTORY_SLOT_TOP, INVENTORY_SLOT_SIZE, INVENTORY_SLOT_SIZE,
                mouseX, mouseY);
    }

    protected boolean isPointInFilterSlot(double mouseX, double mouseY) {
        return this.isHovering(FILTER_SLOT_LEFT, FILTER_SLOT_TOP, INVENTORY_SLOT_SIZE, INVENTORY_SLOT_SIZE,
                mouseX, mouseY);
    }

    protected boolean isFilterSlotEmpty() {
        return !this.menu.getSlot(this.menu.getFilterSlotIndex()).hasItem();
    }

    protected GuiStyle style() {
        return GuiStyleRegistry.get(this.spirit.getFilterItem().is(OccultismItems.ATTRIBUTE_FILTER.get())
                ? FilterUiStyles.OCCULTISM_ATTRIBUTE
                : FilterUiStyles.OCCULTISM_LIST);
    }

    protected GuiSprite partSprite(GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    protected int partColor(GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.COLOR, fallback);
    }

    protected GuiPartKey topBarPart() {
        return this.spirit.getFilterItem().is(OccultismItems.ATTRIBUTE_FILTER.get())
                ? BuiltinFilterParts.ATTRIBUTE_TOP_BAR
                : BuiltinFilterParts.LIST_TOP_BAR;
    }

    protected GuiPartKey panelPart() {
        return this.spirit.getFilterItem().is(OccultismItems.ATTRIBUTE_FILTER.get())
                ? BuiltinFilterParts.ATTRIBUTE_PANEL
                : BuiltinFilterParts.LIST_PANEL;
    }

    protected GuiPartKey verticalSeparatorPart() {
        return this.spirit.getFilterItem().is(OccultismItems.ATTRIBUTE_FILTER.get())
                ? BuiltinFilterParts.ATTRIBUTE_VERTICAL_SEPARATOR
                : BuiltinFilterParts.LIST_VERTICAL_SEPARATOR;
    }

    protected GuiPartKey titlePart() {
        return this.spirit.getFilterItem().is(OccultismItems.ATTRIBUTE_FILTER.get())
                ? BuiltinFilterParts.ATTRIBUTE_TITLE
                : BuiltinFilterParts.LIST_TITLE;
    }

    protected GuiSprite slotSprite(int slotIndex) {
        if (slotIndex == this.menu.getFilterSlotIndex() || slotIndex == ENTITY_INVENTORY_SLOT_INDEX) {
            return this.partSprite(BuiltinFilterParts.FILTER_SLOT, GuiSprites.INVENTORY_SLOT);
        }

        return this.partSprite(BuiltinFilterParts.PLAYER_SLOT, GuiSprites.INVENTORY_SLOT);
    }
}
