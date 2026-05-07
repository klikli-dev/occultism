/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.api.style.GuiStyle;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.common.container.spirit.FilterableSpiritContainer;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import java.util.Optional;
import java.util.regex.Pattern;

public class FilterableSpiritGui<T extends FilterableSpiritContainer> extends SpiritGui<T> {
    protected static final GuiSprite FILTER_HINT_SPRITE = new GuiSprite(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/transporter_filter_hint"), 18, 18).tinted(0x4DFFFFFF);
    protected static final int ENTITY_INVENTORY_SLOT_INDEX = 36;
    protected static final int TITLE_X = 11;
    protected static final int TITLE_Y = 6;
    protected static final int ENTITY_X = 8;
    protected static final int ENTITY_Y = 29;
    protected static final int ENTITY_WIDTH = 50;
    protected static final int ENTITY_HEIGHT = 50;
    protected static final int GUI_WIDTH = 182;
    protected static final int GUI_HEIGHT = 179;
    protected static final int MAIN_LEFT = 3;
    protected static final int MAIN_TOP = 15;
    protected static final int MAIN_WIDTH = 176;
    protected static final int MAIN_HEIGHT = 63;
    protected static final int NAME_LABEL_X = MAIN_LEFT + 8;
    protected static final int NAME_LABEL_Y = MAIN_TOP + 6;
    protected static final int INVENTORY_BACKGROUND_LEFT = 3;
    protected static final int INVENTORY_BACKGROUND_TOP = 89;
    protected static final int INVENTORY_BACKGROUND_WIDTH = 176;
    protected static final int INVENTORY_BACKGROUND_HEIGHT = 90;
    protected static final int INVENTORY_LABEL_X = 11;
    protected static final int INVENTORY_LABEL_Y = 102;
    protected static final int INVENTORY_SLOT_SIZE = 18;
    protected static final int TOP_BAR_HEIGHT = 18;
    protected static final int SLOT_VERTICAL_GAP = 6;
    protected static final int SLOT_STACK_HEIGHT = INVENTORY_SLOT_SIZE * 2 + SLOT_VERTICAL_GAP;
    protected static final int SLOT_STACK_TOP = TOP_BAR_HEIGHT + ((MAIN_TOP + MAIN_HEIGHT - TOP_BAR_HEIGHT) - SLOT_STACK_HEIGHT) / 2;
    protected static final int INVENTORY_SLOT_LEFT = 152;
    protected static final int INVENTORY_SLOT_TOP = SLOT_STACK_TOP;
    protected static final int FILTER_SLOT_LEFT = 152;
    protected static final int FILTER_SLOT_TOP = SLOT_STACK_TOP + INVENTORY_SLOT_SIZE + SLOT_VERTICAL_GAP;
    protected static final int VERTICAL_SEPARATOR_X = 140;
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";
    protected static final String INVENTORY_SLOT_TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.inventory_slot";
    protected static final Pattern TIER_SUFFIX = Pattern.compile("_tier\\d+$");

    public FilterableSpiritGui(T container,
                               Inventory playerInventory,
                               Component titleIn) {
        super(container, playerInventory, titleIn, GUI_WIDTH, GUI_HEIGHT);
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

        return this.spirit.getEntity().getType().getDescription();
    }

    @Override
    protected int mainLeft() {
        return MAIN_LEFT;
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
    protected int mainHeight() {
        return MAIN_HEIGHT;
    }

    @Override
    protected int entityX() {
        return ENTITY_X;
    }

    @Override
    protected int entityY() {
        return ENTITY_Y;
    }

    @Override
    protected int entityWidth() {
        return ENTITY_WIDTH;
    }

    @Override
    protected int entityHeight() {
        return ENTITY_HEIGHT;
    }

    @Override
    protected int inventoryBackgroundLeft() {
        return INVENTORY_BACKGROUND_LEFT;
    }

    @Override
    protected int inventoryBackgroundTop() {
        return INVENTORY_BACKGROUND_TOP;
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
    protected GuiPartKey inventoryBackgroundPart() {
        return OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_INVENTORY_BACKGROUND;
    }

    @Override
    protected int inventoryLabelX() {
        return INVENTORY_LABEL_X;
    }

    @Override
    protected int inventoryLabelY() {
        return INVENTORY_LABEL_Y;
    }

    @Override
    protected int verticalSeparatorX() {
        return VERTICAL_SEPARATOR_X;
    }

    protected void renderFg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (this.isFilterSlotEmpty()) {
            FILTER_HINT_SPRITE.extractRenderState(guiGraphics, this.guiX(FILTER_SLOT_LEFT), this.guiY(FILTER_SLOT_TOP - 1));
        }

        if (!this.spirit.isInventorySlotActive()) {
            guiGraphics.fillGradient(this.guiX(INVENTORY_SLOT_LEFT + 1), this.guiY(INVENTORY_SLOT_TOP),
                    this.guiX(INVENTORY_SLOT_LEFT + INVENTORY_SLOT_SIZE - 1),
                    this.guiY(INVENTORY_SLOT_TOP + INVENTORY_SLOT_SIZE - 2), 0xAA555555, 0xAA555555);
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
                this.tooltip.add(Component.translatable(this.inventorySlotTranslationKey())
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

    protected String inventorySlotTranslationKey() {
        if (this.spirit.getEntity() instanceof SpiritEntity spiritEntity && !spiritEntity.getJobID().isBlank()) {
            String jobKey = spiritEntity.getJobID().replace(':', '.');
            String exactKey = INVENTORY_SLOT_TRANSLATION_KEY_BASE + "." + jobKey;

            if (I18n.exists(exactKey)) {
                return exactKey;
            }

            int namespaceSeparator = jobKey.indexOf('.');
            if (namespaceSeparator >= 0) {
                String namespace = jobKey.substring(0, namespaceSeparator + 1);
                String path = jobKey.substring(namespaceSeparator + 1);
                String tierlessPath = TIER_SUFFIX.matcher(path).replaceFirst("");

                if (!tierlessPath.equals(path)) {
                    String tierlessKey = INVENTORY_SLOT_TRANSLATION_KEY_BASE + "." + namespace + tierlessPath;
                    if (I18n.exists(tierlessKey)) {
                        return tierlessKey;
                    }
                }

                int pathSeparator = tierlessPath.indexOf('_');
                if (pathSeparator >= 0) {
                    String familyKey = INVENTORY_SLOT_TRANSLATION_KEY_BASE + "." + namespace + tierlessPath.substring(0, pathSeparator);
                    if (I18n.exists(familyKey)) {
                        return familyKey;
                    }
                }
            }
        }

        return INVENTORY_SLOT_TRANSLATION_KEY_BASE + "." + Occultism.MODID + ".transport_items";
    }

    protected GuiStyle style() {
        return GuiStyleRegistry.get(OccultismGuiStyles.SPIRIT_TRANSPORTER);
    }

    protected GuiSprite partSprite(GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    protected int partColor(GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.COLOR, fallback);
    }

    protected GuiPartKey topBarPart() {
        return OccultismGuiParts.SPIRIT_TRANSPORTER_TOP_BAR;
    }

    protected GuiPartKey panelPart() {
        return OccultismGuiParts.SPIRIT_TRANSPORTER_PANEL;
    }

    protected GuiPartKey verticalSeparatorPart() {
        return OccultismGuiParts.SPIRIT_TRANSPORTER_VERTICAL_SEPARATOR;
    }

    protected GuiPartKey titlePart() {
        return OccultismGuiParts.SPIRIT_TRANSPORTER_TITLE;
    }

    protected GuiSprite slotSprite(int slotIndex) {
        if (slotIndex == this.menu.getFilterSlotIndex() || slotIndex == ENTITY_INVENTORY_SLOT_INDEX) {
            return this.partSprite(OccultismGuiParts.SPIRIT_TRANSPORTER_FILTER_SLOT, GuiSprites.INVENTORY_SLOT);
        }

        return this.partSprite(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_SLOT, GuiSprites.INVENTORY_SLOT);
    }
}
