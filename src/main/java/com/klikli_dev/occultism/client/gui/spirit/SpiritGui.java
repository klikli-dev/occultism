/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

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
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.client.gui.widget.LivingEntityWidget;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SpiritGui<T extends SpiritContainer> extends AbstractContainerScreen<T> implements GuiHost {
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected static final String INVENTORY_SLOT_TRANSLATION_KEY_BASE = TRANSLATION_KEY_BASE + ".inventory_slot";
    protected static final String DISABLED_SLOT_TRANSLATION_KEY = "gui." + Occultism.MODID + ".spirit.transporter.inventory_slot.disabled";
    protected static final Pattern TIER_SUFFIX = Pattern.compile("_tier\\d+$");
    protected static final int ENTITY_INVENTORY_SLOT_INDEX = 36;
    protected static final int GUI_WIDTH = 182;
    protected static final int GUI_HEIGHT = 179;
    protected static final int TITLE_Y = 6;
    protected static final int TOP_BAR_HEIGHT = 18;
    protected static final int MAIN_LEFT = 3;
    protected static final int MAIN_TOP = 15;
    protected static final int MAIN_WIDTH = 176;
    protected static final int MAIN_HEIGHT = 63;
    protected static final int ENTITY_X = 8;
    protected static final int ENTITY_Y = 29;
    protected static final int ENTITY_WIDTH = 50;
    protected static final int ENTITY_HEIGHT = 50;
    protected static final int DETAILS_X = MAIN_LEFT + 64;
    protected static final int DETAILS_Y = MAIN_TOP + 10;
    protected static final int DETAILS_LINE_SPACING = 14;
    protected static final int INVENTORY_BACKGROUND_LEFT = 3;
    protected static final int INVENTORY_BACKGROUND_TOP = 89;
    protected static final int INVENTORY_BACKGROUND_WIDTH = 176;
    protected static final int INVENTORY_BACKGROUND_HEIGHT = 90;
    protected static final int INVENTORY_LABEL_X = 11;
    protected static final int INVENTORY_LABEL_Y = 102;
    protected static final int VERTICAL_SEPARATOR_X = 140;
    protected static final int INVENTORY_SLOT_LEFT = 152;
    protected static final int INVENTORY_SLOT_TOP = 38;
    protected static final int INVENTORY_SLOT_SIZE = 18;

    protected final GuiRootWidget root;
    protected final List<Component> tooltip = new ArrayList<>();
    protected final IFilterConfigurable spirit;
    protected final T container;

    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        this(container, playerInventory, titleIn, GUI_WIDTH, GUI_HEIGHT);
    }

    public SpiritGui(T container, Inventory playerInventory, Component titleIn, int imageWidth, int imageHeight) {
        super(container, playerInventory, titleIn, imageWidth, imageHeight);
        this.container = container;
        this.spirit = this.container.spirit;
        this.root = new GuiRootWidget(this);
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(this.mainLeft()), this.guiY(this.mainTop()),
                this.mainWidth(), this.mainHeight(), this.partSprite(this.panelPart(), GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(0), this.imageWidth,
                this.topBarHeight(), this.partSprite(this.topBarPart(), GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(this.inventoryBackgroundLeft()),
                this.guiY(this.inventoryBackgroundTop()), this.inventoryBackgroundWidth(), this.inventoryBackgroundHeight(),
                this.partSprite(this.inventoryBackgroundPart(),
                        this.partSprite(this.panelPart(), GuiSprites.GUI_BACKGROUND))));
        this.root.addChild(new VerticalSeparatorWidget(this.guiX(this.verticalSeparatorX()), this.guiY(this.mainTop()),
                this.mainHeight(), this.partColor(this.verticalSeparatorPart(), 0xFF000000)));
        this.root.addChild(new LivingEntityWidget(this, this.entityX(), this.entityY(), this.entityWidth(),
                this.entityHeight(), () -> this.spirit.getEntity(), this.entityPreviewMouseOffsetX(),
                this.entityPreviewMouseOffsetY()));

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            this.root.addChild(new GuiSpriteWidget(this.guiX(slot.x - 1), this.guiY(slot.y - 1), this.slotSprite(i)));
        }
        this.root.syncWithHost();

        LabelWidget titleLabel = new LabelWidget(this.guiX(this.imageWidth / 2), this.guiY(this.titleY() - 1), true,
                -1, 2, 2, this.partTextColor(this.titlePart(), 0x303030));
        titleLabel.addLine(this.topBarTitle());
        this.addRenderableWidget(titleLabel);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderFg(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        int y = this.detailsY();
        int color = this.infoLabelColor();
        guiGraphics.text(this.font, TextUtil.formatDemonName(this.spirit.getEntity().getName().getString()), this.detailsX(),
                y, color, false);
        y += this.detailsLineSpacing();

        if (this.spirit instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0) {
            int agePercent = (int) Math.floor(spiritEntity.getSpiritAge() / (float) spiritEntity.getSpiritMaxAge() * 100);
            guiGraphics.text(this.font, I18n.get(TRANSLATION_KEY_BASE + ".age", agePercent), this.detailsX(), y, color,
                    false);
            y += this.detailsLineSpacing();
        }

        String jobID = this.spirit instanceof SpiritEntity spiritEntity ? spiritEntity.getJobID() : "";
        if (!StringUtils.isBlank(jobID)) {
            String jobText = I18n.get(TRANSLATION_KEY_BASE + ".job", I18n.get("job." + jobID.replace(':', '.')));
            for (String line : WordUtils.wrap(jobText, 18, "\n", true).split("[\\r\\n]+")) {
                guiGraphics.text(this.font, ChatFormatting.ITALIC + line + ChatFormatting.RESET, this.detailsX(), y, color,
                        false);
                y += this.font.lineHeight + 2;
            }
        }

        guiGraphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX(), this.inventoryLabelY(), 0x303030, false);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.extractBackground(guiGraphics);
        this.extractSpiritEntity(guiGraphics, mouseX, mouseY, partialTicks);
        super.extractContents(guiGraphics, mouseX, mouseY, partialTicks);
    }

    protected void extractBackground(GuiGraphicsExtractor guiGraphics) {
    }

    protected void extractSpiritEntity(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
    }

    protected void renderFg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (!this.spirit.isInventorySlotActive()) {
            guiGraphics.fillGradient(this.guiX(INVENTORY_SLOT_LEFT + 1), this.guiY(INVENTORY_SLOT_TOP),
                    this.guiX(INVENTORY_SLOT_LEFT + INVENTORY_SLOT_SIZE - 1),
                    this.guiY(INVENTORY_SLOT_TOP + INVENTORY_SLOT_SIZE - 2), 0xAA555555, 0xAA555555);
        }

        if (this.isPointInInventorySlot(mouseX, mouseY)) {
            if (!this.spirit.isInventorySlotActive()) {
                this.tooltip.add(Component.translatable(DISABLED_SLOT_TRANSLATION_KEY).withStyle(ChatFormatting.GRAY));
            } else if (!this.container.getSlot(ENTITY_INVENTORY_SLOT_INDEX).hasItem()) {
                String translationKey = this.inventorySlotTranslationKey();
                if (translationKey != null) {
                    this.tooltip.add(Component.translatable(translationKey).withStyle(ChatFormatting.GRAY));
                }
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
                    String familyKey = INVENTORY_SLOT_TRANSLATION_KEY_BASE + "." + namespace
                            + tierlessPath.substring(0, pathSeparator);
                    if (I18n.exists(familyKey)) {
                        return familyKey;
                    }
                }
            }
        }

        return null;
    }

    protected int infoLabelColor() {
        return 0xFF303030;
    }

    protected int titleY() {
        return TITLE_Y;
    }

    protected int topBarHeight() {
        return TOP_BAR_HEIGHT;
    }

    protected int mainLeft() {
        return MAIN_LEFT;
    }

    protected int mainTop() {
        return MAIN_TOP;
    }

    protected int mainWidth() {
        return MAIN_WIDTH;
    }

    protected int mainHeight() {
        return MAIN_HEIGHT;
    }

    protected int entityX() {
        return ENTITY_X;
    }

    protected int entityY() {
        return ENTITY_Y;
    }

    protected int entityWidth() {
        return ENTITY_WIDTH;
    }

    protected int entityHeight() {
        return ENTITY_HEIGHT;
    }

    protected float entityPreviewMouseOffsetX() {
        return 14.0F;
    }

    protected float entityPreviewMouseOffsetY() {
        return 7.0F;
    }

    protected int detailsX() {
        return DETAILS_X;
    }

    protected int detailsY() {
        return DETAILS_Y;
    }

    protected int detailsLineSpacing() {
        return DETAILS_LINE_SPACING;
    }

    protected int inventoryBackgroundLeft() {
        return INVENTORY_BACKGROUND_LEFT;
    }

    protected int inventoryBackgroundTop() {
        return INVENTORY_BACKGROUND_TOP;
    }

    protected int inventoryBackgroundWidth() {
        return INVENTORY_BACKGROUND_WIDTH;
    }

    protected int inventoryBackgroundHeight() {
        return INVENTORY_BACKGROUND_HEIGHT;
    }

    protected GuiPartKey inventoryBackgroundPart() {
        return OccultismGuiParts.SPIRIT_PLAYER_INVENTORY_BACKGROUND;
    }

    protected int inventoryLabelX() {
        return INVENTORY_LABEL_X;
    }

    protected int inventoryLabelY() {
        return INVENTORY_LABEL_Y;
    }

    protected int verticalSeparatorX() {
        return VERTICAL_SEPARATOR_X;
    }

    protected Component topBarTitle() {
        return this.spirit.getEntity().getType().getDescription();
    }

    protected GuiStyle style() {
        return GuiStyleRegistry.get(OccultismGuiStyles.SPIRIT);
    }

    protected GuiSprite partSprite(GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    protected int partColor(GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.COLOR, fallback);
    }

    protected int partTextColor(GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.TEXT_COLOR, fallback);
    }

    protected GuiPartKey topBarPart() {
        return OccultismGuiParts.SPIRIT_TOP_BAR;
    }

    protected GuiPartKey panelPart() {
        return OccultismGuiParts.SPIRIT_PANEL;
    }

    protected GuiPartKey verticalSeparatorPart() {
        return OccultismGuiParts.SPIRIT_VERTICAL_SEPARATOR;
    }

    protected GuiPartKey titlePart() {
        return OccultismGuiParts.SPIRIT_TITLE;
    }

    protected GuiSprite slotSprite(int slotIndex) {
        if (slotIndex == ENTITY_INVENTORY_SLOT_INDEX) {
            return this.partSprite(OccultismGuiParts.SPIRIT_INVENTORY_SLOT, GuiSprites.INVENTORY_SLOT);
        }

        return this.partSprite(OccultismGuiParts.SPIRIT_PLAYER_SLOT, GuiSprites.INVENTORY_SLOT);
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
