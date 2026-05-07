/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ResolvedLayout;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiTextWidget;
import com.klikli_dev.codedefinedgui.api.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SpiritGui<T extends SpiritContainer> extends AbstractContainerScreen<T> implements GuiHost, LayoutScreenView {
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected static final String INVENTORY_SLOT_TRANSLATION_KEY_BASE = TRANSLATION_KEY_BASE + ".inventory_slot";
    protected static final String DISABLED_SLOT_TRANSLATION_KEY = "gui." + Occultism.MODID + ".spirit.transporter.inventory_slot.disabled";
    protected static final Pattern TIER_SUFFIX = Pattern.compile("_tier\\d+$");
    protected static final int ENTITY_INVENTORY_SLOT_INDEX = 36;
    protected static final int GUI_WIDTH = 182;
    protected static final int GUI_HEIGHT = 179;
    protected static final int SLOT_SIZE = 18;
    protected static final int AGED_INVENTORY_OFFSET = 13;

    protected final GuiRootWidget root;
    protected final List<Component> tooltip = new ArrayList<>();
    protected final IFilterConfigurable spirit;
    protected final T container;
    private final GuiPartKey inventoryBackgroundPart;
    private final GuiPartKey ageBarPart;
    private final GuiPartKey topBarPart;
    private final GuiPartKey panelPart;
    private final GuiPartKey verticalSeparatorPart;
    private final GuiPartKey titlePart;
    private final LayoutSpec layoutSpec;
    private final ScreenLayoutController layoutController;
    protected ResolvedLayout resolvedLayout;

    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        this(container, playerInventory, titleIn, GUI_WIDTH, GUI_HEIGHT + inventoryOffsetFor(container),
                SpiritLayouts.standard(inventoryOffsetFor(container) > 0), OccultismGuiStyles.SPIRIT,
                OccultismGuiParts.SPIRIT_PLAYER_INVENTORY_BACKGROUND,
                OccultismGuiParts.SPIRIT_AGE_BAR,
                OccultismGuiParts.SPIRIT_TOP_BAR,
                OccultismGuiParts.SPIRIT_PANEL,
                OccultismGuiParts.SPIRIT_VERTICAL_SEPARATOR,
                OccultismGuiParts.SPIRIT_TITLE);
    }

    protected static int inventoryOffsetFor(SpiritContainer container) {
        return container.spirit.getEntity() instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0
                ? AGED_INVENTORY_OFFSET
                : 0;
    }

    protected SpiritGui(T container, Inventory playerInventory, Component titleIn, int imageWidth, int imageHeight,
                        LayoutSpec layoutSpec, com.klikli_dev.codedefinedgui.api.style.GuiStyleKey styleKey,
                        GuiPartKey inventoryBackgroundPart, GuiPartKey ageBarPart, GuiPartKey topBarPart,
                        GuiPartKey panelPart, GuiPartKey verticalSeparatorPart, GuiPartKey titlePart) {
        super(container, playerInventory, titleIn, imageWidth, imageHeight);
        this.container = container;
        this.spirit = this.container.spirit;
        this.root = new GuiRootWidget(this);
        this.inventoryBackgroundPart = inventoryBackgroundPart;
        this.ageBarPart = ageBarPart;
        this.topBarPart = topBarPart;
        this.panelPart = panelPart;
        this.verticalSeparatorPart = verticalSeparatorPart;
        this.titlePart = titlePart;
        this.layoutSpec = layoutSpec;
        this.layoutController = new ScreenLayoutController(this, this, this.root,
                new GuiStyleContext(GuiStyleRegistry.get(styleKey)));
    }

    @Override
    public void init() {
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
        this.renderFg(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        SpiritEntity spiritEntity = this.spiritEntityWithAge();
        if (spiritEntity != null && this.resolvedLayout != null) {
            var ageBar = this.resolvedLayout.node("frame.main.age_bar");
            int agePercent = (int) Math.floor(spiritEntity.getSpiritAge() / (float) spiritEntity.getSpiritMaxAge() * 100);
            String ageText = I18n.get(TRANSLATION_KEY_BASE + ".age", agePercent);
            int ageLabelX = ageBar.x() + (ageBar.widthOrThrow() - this.font.width(ageText)) / 2;
            int ageLabelY = ageBar.y() + (ageBar.heightOrThrow() - this.font.lineHeight) / 2 + 1;
            guiGraphics.text(this.font, ageText, ageLabelX, ageLabelY, 0xFF000000, false);
        }
    }

    protected void renderFg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (!this.spirit.isInventorySlotActive()) {
            Slot inventorySlot = this.entityInventorySlot();
            int slotLeft = inventorySlot.x - 1;
            int slotTop = inventorySlot.y - 1;
            guiGraphics.fillGradient(this.guiX(slotLeft + 1), this.guiY(slotTop + 1),
                    this.guiX(slotLeft + SLOT_SIZE - 1),
                    this.guiY(slotTop + SLOT_SIZE - 1), 0xAA555555, 0xAA555555);
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
        Slot inventorySlot = this.entityInventorySlot();
        return this.isHovering(inventorySlot.x - 1, inventorySlot.y - 1, SLOT_SIZE, SLOT_SIZE, mouseX, mouseY);
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

    protected float entityPreviewMouseOffsetX() {
        return 14.0F;
    }

    protected float entityPreviewMouseOffsetY() {
        return 7.0F;
    }

    protected boolean hasAgeBar() {
        return this.spiritEntityWithAge() != null;
    }

    protected SpiritEntity spiritEntityWithAge() {
        if (this.spirit.getEntity() instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0) {
            return spiritEntity;
        }

        return null;
    }

    protected Component topBarTitle() {
        if (this.spirit instanceof SpiritEntity spiritEntity && !StringUtils.isBlank(spiritEntity.getJobID())) {
            return Component.translatable(TRANSLATION_KEY_BASE + ".job",
                    Component.translatable("job." + spiritEntity.getJobID().replace(':', '.')));
        }

        return this.spirit.getEntity().getType().getDescription();
    }

    @Override
    public LayoutSpec layoutSpec() {
        return this.layoutSpec;
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        registry.resolve("frame.main.panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(this.panelPart, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.top_bar.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(this.topBarPart, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.top_bar.title", ctx -> {
            Component title = this.topBarTitle();
            int titleX = ctx.node().x() + (ctx.node().widthOrThrow() - this.font.width(title)) / 2;
            ctx.addWidget(new GuiTextWidget(
                    titleX,
                    ctx.node().y(),
                    this::topBarTitle,
                    () -> ctx.style().textColor(this.titlePart, 0x303030),
                    false
            ));
        });
        registry.resolve("frame.player_inventory.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(this.inventoryBackgroundPart, ctx.style().sprite(this.panelPart, GuiSprites.GUI_BACKGROUND))
        )));
        registry.resolve("frame.player_inventory.label", ctx -> ctx.addWidget(new GuiTextWidget(
                ctx.node().x(),
                ctx.node().y(),
                () -> this.playerInventoryTitle,
                () -> ctx.style().textColor(BuiltinGuiParts.PLAYER_INVENTORY_LABEL, 0x303030),
                false
        )));
        registry.resolve("frame.main.vertical_separator", ctx -> this.root.addChild(new VerticalSeparatorWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().heightOrThrow(),
                ctx.style().color(this.verticalSeparatorPart, 0xFF000000)
        )));
        registry.resolve("frame.main.entity_preview", ctx -> this.root.addChild(new LivingEntityWidget(
                this,
                ctx.node().x() - this.leftPos,
                ctx.node().y() - this.topPos,
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                () -> this.spirit.getEntity(),
                this.entityPreviewMouseOffsetX(),
                this.entityPreviewMouseOffsetY()
        )));
        registry.resolve("frame.main.name_label", ctx -> ctx.addWidget(new GuiTextWidget(
                ctx.node().x(),
                ctx.node().y(),
                () -> Component.literal(TextUtil.formatDemonName(this.spirit.getEntity().getName().getString())),
                this::infoLabelColor,
                false
        )));
        if (this.hasAgeBar()) {
            registry.resolve("frame.main.age_bar", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                    this,
                    ctx.node().x(),
                    ctx.node().y(),
                    ctx.node().widthOrThrow(),
                    ctx.node().heightOrThrow(),
                    ctx.style().sprite(this.ageBarPart, ctx.style().sprite(this.topBarPart, GuiSprites.GUI_BACKGROUND))
            )));
        }
        this.registerSlotResolvers(registry);
    }

    protected void registerSlotResolvers(LayoutResolverRegistry registry) {
        for (int slotIndex = 0; slotIndex < this.menu.slots.size(); slotIndex++) {
            String nodePath = this.slotNodePath(slotIndex);
            if (nodePath == null) {
                continue;
            }

            int currentSlotIndex = slotIndex;
            registry.add(nodePath, -25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    ctx.node().x() - 1,
                    ctx.node().y() - 1,
                    ctx.style().sprite(this.slotPart(currentSlotIndex), GuiSprites.INVENTORY_SLOT)
            )));
        }
    }

    protected String slotNodePath(int slotIndex) {
        if (slotIndex < 27) {
            return "frame.player_inventory.main.slot_" + slotIndex;
        }

        if (slotIndex < ENTITY_INVENTORY_SLOT_INDEX) {
            return "frame.player_inventory.hotbar.slot_" + (slotIndex - 27);
        }

        if (slotIndex == ENTITY_INVENTORY_SLOT_INDEX) {
            return "frame.main.inventory_slot";
        }

        return null;
    }

    protected GuiPartKey slotPart(int slotIndex) {
        return slotIndex == ENTITY_INVENTORY_SLOT_INDEX
                ? OccultismGuiParts.SPIRIT_INVENTORY_SLOT
                : OccultismGuiParts.SPIRIT_PLAYER_SLOT;
    }

    protected Slot entityInventorySlot() {
        return this.container.getSlot(ENTITY_INVENTORY_SLOT_INDEX);
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
