/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.common.container.spirit.FilterableSpiritContainer;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;
import java.util.regex.Pattern;

public class FilterableSpiritGui<T extends FilterableSpiritContainer> extends SpiritGui<T> {
    protected static final GuiSprite FILTER_HINT_SPRITE = new GuiSprite(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/transporter_filter_hint"), 18, 18).tinted(0x4DFFFFFF);
    protected static final int ENTITY_INVENTORY_SLOT_INDEX = 36;
    protected static final int GUI_WIDTH = 182;
    protected static final int GUI_HEIGHT = 179;
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";
    protected static final String INVENTORY_SLOT_TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.inventory_slot";
    protected static final Pattern TIER_SUFFIX = Pattern.compile("_tier\\d+$");

    public FilterableSpiritGui(T container,
                               Inventory playerInventory,
                               Component titleIn) {
        super(container, playerInventory, titleIn, GUI_WIDTH, GUI_HEIGHT, SpiritLayouts.transporter(), OccultismGuiStyles.SPIRIT_TRANSPORTER,
                OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_INVENTORY_BACKGROUND,
                OccultismGuiParts.SPIRIT_AGE_BAR,
                OccultismGuiParts.SPIRIT_TRANSPORTER_TOP_BAR,
                OccultismGuiParts.SPIRIT_TRANSPORTER_PANEL,
                OccultismGuiParts.SPIRIT_TRANSPORTER_VERTICAL_SEPARATOR,
                OccultismGuiParts.SPIRIT_TRANSPORTER_TITLE);
    }

    protected Component topBarTitle() {
        if (this.spirit.getEntity() instanceof SpiritEntity spiritEntity && !spiritEntity.getJobID().isBlank()) {
            return Component.translatable("gui.occultism.spirit.job", Component.translatable("job." + spiritEntity.getJobID().replace(':', '.')));
        }

        return this.spirit.getEntity().getType().getDescription();
    }

    protected void renderFg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (this.isFilterSlotEmpty()) {
            Slot filterSlot = this.filterSlot();
            FILTER_HINT_SPRITE.extractRenderState(guiGraphics, this.guiX(filterSlot.x - 1), this.guiY(filterSlot.y - 1));
        }

        if (!this.spirit.isInventorySlotActive()) {
            Slot inventorySlot = this.entityInventorySlot();
            int slotLeft = inventorySlot.x - 1;
            int slotTop = inventorySlot.y - 1;
            guiGraphics.fillGradient(this.guiX(slotLeft + 1), this.guiY(slotTop + 1),
                    this.guiX(slotLeft + SLOT_SIZE - 1),
                    this.guiY(slotTop + SLOT_SIZE - 1), 0xAA555555, 0xAA555555);
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
        Slot inventorySlot = this.entityInventorySlot();
        return this.isHovering(inventorySlot.x - 1, inventorySlot.y - 1, SLOT_SIZE, SLOT_SIZE, mouseX, mouseY);
    }

    protected boolean isPointInFilterSlot(double mouseX, double mouseY) {
        Slot filterSlot = this.filterSlot();
        return this.isHovering(filterSlot.x - 1, filterSlot.y - 1, SLOT_SIZE, SLOT_SIZE, mouseX, mouseY);
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

    @Override
    protected String slotNodePath(int slotIndex) {
        if (slotIndex == this.menu.getFilterSlotIndex()) {
            return "frame.main.filter_slot";
        }

        return super.slotNodePath(slotIndex);
    }

    @Override
    protected GuiPartKey slotPart(int slotIndex) {
        if (slotIndex == this.menu.getFilterSlotIndex() || slotIndex == ENTITY_INVENTORY_SLOT_INDEX) {
            return OccultismGuiParts.SPIRIT_TRANSPORTER_FILTER_SLOT;
        }

        return OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_SLOT;
    }

    protected Slot filterSlot() {
        return this.menu.getSlot(this.menu.getFilterSlotIndex());
    }
}
