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

package com.klikli_dev.occultism.client.gui.storage;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyle;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.api.common.data.*;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiSprites;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.controls.ItemSlotWidget;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.client.gui.controls.MachineSlotWidget;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.klikli_dev.occultism.integration.jei.JeiSettings;
import com.klikli_dev.occultism.integration.jei.OccultismJeiIntegration;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.*;
import com.klikli_dev.occultism.util.InputUtil;
import com.klikli_dev.occultism.util.TextUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class StorageControllerGuiBase<T extends StorageControllerContainerBase> extends AbstractContainerScreen<T> implements IStorageControllerGui, IStorageControllerGuiContainer, ContainerListener, GuiHost {

    public static final int ORDER_AREA_OFFSET = 48;
    public static final int ORDER_INPUT_SLOT_INDEX = 10;
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".storage_controller";
    protected static final int GUI_WIDTH = 260;
    protected static final int VISIBLE_COLUMNS = 11;
    protected static final int TOP_BAR_HEIGHT = 21;
    protected static final int MAIN_PANEL_TOP = 12;
    protected static final int ITEM_AREA_LEFT = 32;
    protected static final int ITEM_AREA_TOP = TOP_BAR_HEIGHT + 3;
    protected static final int SEARCH_BAR_LEFT = ITEM_AREA_LEFT + 1;
    protected static final int SEARCH_BAR_TOP = 7;
    protected static final int SEARCH_FIELD_LEFT = SEARCH_BAR_LEFT - 3;
    protected static final int SEARCH_FIELD_TOP = SEARCH_BAR_TOP - 3;
    protected static final int STORAGE_INFO_LABEL_LEFT = 186;
    protected static final int CONTROL_BUTTON_TOP = SEARCH_BAR_TOP - 2;
    protected static final int CONTROL_BUTTON_SIZE = 12;
    protected static final int CONTROL_BUTTON_LEFT = SEARCH_BAR_LEFT + 98;
    protected static final int ORDER_PANEL_LEFT = 0;
    protected static final int ORDER_PANEL_TOP_OFFSET = 5;
    public static final int CRAFTING_GRID_TOP = 4;
    public static final int CRAFTING_OUTPUT_TOP = CRAFTING_GRID_TOP + 18;
    protected static final int CRAFTING_ARROW_LEFT = 103 + ORDER_AREA_OFFSET;
    protected static final int CRAFTING_ARROW_TOP = CRAFTING_OUTPUT_TOP + 1;
    protected static final int INVENTORY_PANEL_TOP_OFFSET = 66;
    protected static final int INVENTORY_PANEL_LEFT = 43;
    protected static final int INVENTORY_PANEL_WIDTH = 176;
    protected static final int INVENTORY_PANEL_HEIGHT = 90;
    protected static final int INVENTORY_LABEL_X = 51;
    protected static final int INVENTORY_LABEL_TOP_OFFSET = 73;
    protected static final int TAB_TOP_OFFSET = 0;
    protected static final int TAB_WIDTH = 34;
    protected static final int TAB_HEIGHT = 29;
    protected static final int TAB_HIDDEN_OVERLAP = 3;
    protected static final int TAB_LEFT_SHIFT = 5;
    protected static final int TAB_ICON_OFFSET_X = -3;
    protected static final int MAIN_PANEL_TINT_FALLBACK = 0xFF4B5563;
    protected static final int STORAGE_BUTTON_TINT = 0xFF5D6878;
    protected static final int STORAGE_BUTTON_HOVER_TINT = 0xFF707C8D;
    protected static final int TOP_CONTROL_TOOLTIP_OFFSET_Y = 18;
    public static final int ORDER_INPUT_SLOT_LEFT = -10;
    public static final int ORDER_INPUT_SLOT_TOP = -61;
    protected static final float SEARCH_BAR_SCALE = 0.75F;
    protected static final int JEI_ACTIVE_COLOR = 0xFF20A020;
    protected static final int JEI_INACTIVE_COLOR = 0xFFC03030;

    public int lastStacksCount;
    public ClientStorageCache clientStorageCache;
    public List<MachineReference> linkedMachines;
    public IStorageControllerContainer storageControllerContainer;
    public StorageControllerGuiMode guiMode = StorageControllerGuiMode.INVENTORY;
    protected int maxItemTypes;
    protected int usedItemTypes;
    protected long maxTotalItemCount;
    protected long usedTotalItemCount;
    protected ItemStack stackUnderMouse = ItemStack.EMPTY;
    protected EditBox searchBar;
    protected List<ItemSlotWidget> itemSlots = new ArrayList<>();
    protected List<MachineSlotWidget> machineSlots = new ArrayList<>();
    protected AbstractWidget clearTextButton;
    protected AbstractWidget clearRecipeButton;
    protected AbstractWidget sortTypeButton;
    protected AbstractWidget sortDirectionButton;
    protected AbstractWidget jeiSyncButton;
    protected AbstractWidget autocraftingModeButton;
    protected AbstractWidget inventoryModeButton;
    protected LabelWidget storageSpaceLabel;
    protected LabelWidget storageTypesLabel;
    protected LabelWidget rowLabel;
    protected LabelWidget filledLabel;
    protected LabelWidget typesLabel;
    protected final GuiRootWidget root;
    protected int rows;
    protected int columns;

    protected int previousPage;
    protected int currentPage;
    protected int totalPages;

    protected boolean forceFocus;
    protected long lastClick;
    protected int realTopPos;
    private int lastCachedStacksToDisplayCount;
    private List<ItemStack> cachedStacksToDisplay;
    private String cachedSearchString;

    public StorageControllerGuiBase(T container, Inventory playerInventory, Component name) {
        super(container, playerInventory, name, GUI_WIDTH, 256);
        this.storageControllerContainer = container;
        // SimpleContainer.addListener was removed in 26.1 - using containerChanged polling instead
        this.root = new GuiRootWidget(this);

        this.rows = Occultism.CLIENT_CONFIG.misc.storageRows.getAsInt();
        this.columns = VISIBLE_COLUMNS;

        this.currentPage = 1;
        this.totalPages = 1;

        this.clientStorageCache = new ClientStorageCache();
        this.storageControllerContainer.setClientStorageCache(this.clientStorageCache);

        this.linkedMachines = new ArrayList<>();

        this.lastClick = System.currentTimeMillis();

        this.resetDisplayCaches();

        Networking.sendToServer(new MessageRequestStacks());
    }

    public static void onScreenMouseClickedPre(Pre event) {
        //JEI correctly consumes the mouseClicked event if we click in their search bar
        //That leads to our search bar never getting unfocused
        //so we use the pre-event to unfocus -> if the click was in the search bar then the mouseClicked of our gui will handle it
        if (event.getScreen() instanceof StorageControllerGuiBase<?> gui) {
            gui.searchBar.setFocused(false);
        }
    }

    //region Getter / Setter
    protected abstract boolean isGuiValid();

    protected abstract BlockPos getEntityPosition();

    public abstract SortDirection getSortDirection();

    public abstract void setSortDirection(SortDirection sortDirection);

    public abstract SortType getSortType();

    //endregion Getter / Setter

    public abstract void setSortType(SortType sortType);

    @Override
    public Font getFontRenderer() {
        return this.font;
    }

    @Override
    public void drawGradientRect(GuiGraphicsExtractor guiGraphics, int left, int top, int right, int bottom, int startColor,
                                 int endColor) {
        guiGraphics.fillGradient(left, top, right, bottom, startColor, endColor);
    }

    @Override
    public boolean isPointInRegionController(int rectX, int rectY, int rectWidth, int rectHeight, double pointX,
                                             double pointY) {
        return this.isHovering(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    public void renderToolTip(GuiGraphicsExtractor guiGraphics, ItemStack stack, int x, int y) {
        guiGraphics.setTooltipForNextFrame(this.font, this.getTooltipFromContainerItem(stack), stack.getTooltipImage(), x, y);
    }

    @Override
    public void renderToolTip(GuiGraphicsExtractor guiGraphics, MachineReference machine, int x, int y) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(machine.getInsertItemStack().getDisplayName());
        if (!StringUtils.isBlank(machine.customName)) {
            tooltip.add(Component.literal(ChatFormatting.GRAY.toString() +
                    ChatFormatting.BOLD + machine.customName +
                    ChatFormatting.RESET));
        }

        if (this.minecraft.player.level().dimension() != machine.insertGlobalPos.getDimensionKey())
            tooltip.add(Component.translatable(ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC +
                    machine.insertGlobalPos.getDimensionKey().identifier() +
                    ChatFormatting.RESET));

        guiGraphics.setComponentTooltipForNextFrame(this.font, tooltip, x, y);
    }

    @Override
    public void setStacks(List<ItemStack> stacks) {
        this.clientStorageCache.update(stacks);
        this.resetDisplayCaches();
    }

    @Override
    public ClientStorageCache getClientStorageCache() {
        return this.clientStorageCache;
    }

    @Override
    public void setMaxStorageSize(int maxItemTypes, long maxTotalItemCount) {
        this.maxItemTypes = maxItemTypes;
        this.maxTotalItemCount = maxTotalItemCount;
    }

    @Override
    public void setUsedStorageSize(int usedItemTypes, long usedTotalItemCount) {
        this.usedItemTypes = usedItemTypes;
        this.usedTotalItemCount = usedTotalItemCount;
    }

    @Override
    public void markDirty() {
        this.init();
    }

    @Override
    public void setLinkedMachines(List<MachineReference> machines) {
        this.linkedMachines = machines;
    }

    @Override
    public void init() {
        super.init();
        this.resetDisplayCaches();
        this.rows = this.visibleRows();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.realTopPos = Math.max(0, (this.height - this.totalGuiHeight()) / 2);
        this.topPos = this.realTopPos + ITEM_AREA_TOP + 18 * this.rows;

        this.clearWidgets();

        this.initRootWidgets();

        boolean focus = false;

        String searchBarText = "";
        if (this.searchBar != null) {
            searchBarText = this.searchBar.getValue();
            if (this.searchBar.isFocused()) {
                focus = true;
            }
        }


        int searchBarRenderedWidth = 90;
        int searchBarRenderedHeight = Math.max(9, this.font.lineHeight);
        this.searchBar = new ScaledEditBox(this.font, this.leftPos + SEARCH_BAR_LEFT,
                this.realTopPos + SEARCH_BAR_TOP, searchBarRenderedWidth, searchBarRenderedHeight,
                Component.literal("search"), SEARCH_BAR_SCALE);
        this.searchBar.setMaxLength(30);

        this.searchBar.setBordered(false);
        this.searchBar.setVisible(true);
        this.searchBar.setTextColor(0xFFFFFFFF);
        this.searchBar.setFocused(focus);

        this.searchBar.setValue(searchBarText);
        // OccultismEmiIntegration excluded from build - EMI sync disabled
        if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
            this.searchBar.setValue(OccultismJeiIntegration.get().getFilterText());
        }
        this.addRenderableWidget(this.searchBar);

        int storageSpaceInfoLabelTop = this.topPos + 7;
        this.storageSpaceLabel =
                new LabelWidget(this.leftPos + STORAGE_INFO_LABEL_LEFT, storageSpaceInfoLabelTop, true,
                        -1, 2, 0x404040);
        this.storageSpaceLabel
                .addLine(I18n.get(TRANSLATION_KEY_BASE + ".space_info_label_new",
                        String.format("%.2f", (double) this.usedTotalItemCount / (double) this.maxTotalItemCount * 100)

                ), false);
        this.addRenderableWidget(this.storageSpaceLabel);

        this.storageTypesLabel =
                new LabelWidget(this.leftPos + STORAGE_INFO_LABEL_LEFT - 7, storageSpaceInfoLabelTop + 40, true,
                        -1, 2, 0x404040);
        this.storageTypesLabel
                .addLine(I18n.get(TRANSLATION_KEY_BASE + ".space_info_label_types", String.format("%.0f", (double) this.usedItemTypes / (double) this.maxItemTypes * 100)), false);
        this.addRenderableWidget(this.storageTypesLabel);

        this.initButtons();

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks); //called by super
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);

        // Poll order slot to detect autocrafting mode change (replaces removed Container.addListener)
        this.containerChanged(this.storageControllerContainer.getOrderSlot());

        this.extractTooltip(guiGraphics, mouseX, mouseY);
        if (!this.isGuiValid()) {
            this.minecraft.player.closeContainer();
            return;
        }
        try {
            this.drawTooltips(guiGraphics, mouseX, mouseY);
        } catch (Throwable e) {
            Occultism.LOGGER.error("Error drawing tooltip.", e);
        }

        //previous content of drawGuiForegroundLayer
        if (!this.isGuiValid()) {
            return;
        }
        if (this.forceFocus) {
            this.searchBar.setFocused(true);
            if (this.searchBar.isFocused()) {
                this.forceFocus = false;
            }
        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY,
                                float partialTicks) {
        if (!this.isGuiValid()) {
            return;
        }

        super.extractContents(guiGraphics, mouseX, mouseY, partialTicks);

        switch (this.guiMode) {
            case INVENTORY:
                this.drawItems(guiGraphics, partialTicks, mouseX, mouseY);
                break;
            case AUTOCRAFTING:
                this.drawMachines(guiGraphics, partialTicks, mouseX, mouseY);
                break;
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int mouseButton = event.button();
        super.mouseClicked(event, doubleClick);

        this.searchBar.setFocused(false);

        //right mouse button clears search bar
        if (this.isPointInSearchbar(mouseX, mouseY)) {
            this.searchBar.setFocused(true);

            if (mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                this.clearSearch();
            }
        } else if (this.guiMode == StorageControllerGuiMode.INVENTORY) {
            ItemStack stackCarriedByMouse = this.minecraft.player.containerMenu.getCarried();
            if (!this.stackUnderMouse.isEmpty() &&
                    (mouseButton == InputUtil.MOUSE_BUTTON_LEFT || mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) &&
                    stackCarriedByMouse.isEmpty() && this.canClick()) {
                //take item out of storage
                Networking.sendToServer(
                        new MessageTakeItem(this.stackUnderMouse, mouseButton, Minecraft.getInstance().hasShiftDown(),
                                Minecraft.getInstance().hasControlDown()));
                this.lastClick = System.currentTimeMillis();
            } else if (!stackCarriedByMouse.isEmpty() && this.isPointInItemArea(mouseX, mouseY) && this.canClick()) {
                //put item into storage
                Networking.sendToServer(new MessageInsertMouseHeldItem(mouseButton));
                this.lastClick = System.currentTimeMillis();
            }
        } else if (this.guiMode == StorageControllerGuiMode.AUTOCRAFTING) {
            for (MachineSlotWidget slot : this.machineSlots) {
                if (slot.isMouseOverSlot(mouseX, mouseY)) {
                    if (mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                        ItemStack orderStack = this.storageControllerContainer.getOrderSlot().getItem(0);
                        if (Minecraft.getInstance().hasShiftDown()) {
                            long time = System.currentTimeMillis() + 5000;
                            Occultism.SELECTED_BLOCK_RENDERER.selectBlock(slot.getMachine().insertGlobalPos.getPos(), time, Color.GREEN);
                            Occultism.SELECTED_BLOCK_RENDERER.selectBlock(slot.getMachine().extractGlobalPos.getPos(), time, Color.YELLOW);
                        } else if (!orderStack.isEmpty()) {
                            //this message both clears the order slot and creates the order
                            GlobalBlockPos storageControllerPos = this.storageControllerContainer.getStorageControllerGlobalBlockPos();
                            if (storageControllerPos != null) {
                                Networking.sendToServer(new MessageRequestOrder(
                                        storageControllerPos,
                                        slot.getMachine().insertGlobalPos, orderStack));
                            } else {
                                Occultism.LOGGER.warn("Linked Storage Controller Position null.");
                            }

                            //now switch back gui mode.
                            this.guiMode = StorageControllerGuiMode.INVENTORY;
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == InputConstants.KEY_ESCAPE) {
            this.minecraft.player.closeContainer();
        }

        var nothandled = !this.searchBar.keyPressed(event) && !this.searchBar.canConsumeInput();
        if (nothandled)
            return super.keyPressed(event);

        // OccultismEmiIntegration excluded from build - EMI sync disabled
        if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
            OccultismJeiIntegration.get().setFilterText(this.searchBar.getValue());
        }
        return true;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    // SimpleContainer.addListener was removed in 26.1; poll the order slot each frame instead
    public void containerChanged(Container inventory) {
        if (inventory == this.storageControllerContainer.getOrderSlot() && !inventory.getItem(0).isEmpty()
                && this.guiMode != StorageControllerGuiMode.AUTOCRAFTING) {
            this.guiMode = StorageControllerGuiMode.AUTOCRAFTING;
            this.init();
        }
    }

    @Override
    public void slotChanged(AbstractContainerMenu menu, int slotIndex, ItemStack itemStack) {
        // No slot change handling needed
    }

    @Override
    public void dataChanged(AbstractContainerMenu menu, int dataSlotIndex, int value) {
        // No data slots to track
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);

        //check if mouse is over item area, then handle scrolling
        if (this.isPointInItemArea(pMouseX, pMouseY)) {
            if (pScrollY > 0 && this.currentPage > 1) {
                this.currentPage--;
            }
            if (pScrollY < 0 && this.currentPage < this.totalPages) {
                this.currentPage++;
            }
        }
        return true;
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (this.searchBar.isFocused() && this.searchBar.charTyped(event)) {
            Networking.sendToServer(new MessageRequestStacks());
            // OccultismEmiIntegration excluded from build - EMI sync disabled
            if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
                OccultismJeiIntegration.get().setFilterText(this.searchBar.getValue());
            }
        }

        return false;
    }

    public void initButtons() {
        int clearRecipeButtonTop = this.topPos + CRAFTING_GRID_TOP - 1;
        this.clearRecipeButton = new SpriteButtonWidget(this.leftPos + 93 + ORDER_AREA_OFFSET,
                clearRecipeButtonTop,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE,
                this.storageButtonBackgroundSprites(),
                Component.translatable(TRANSLATION_KEY_BASE + ".crafting.clear"), () -> {
            Networking.sendToServer(new MessageClearCraftingMatrix());
            Networking.sendToServer(new MessageRequestStacks());
            this.init();
        }, SpriteButtonWidget.offsetText("X", 0.5F, -0.5F));
        this.addRenderableWidget(this.clearRecipeButton);

        this.clearTextButton = new SpriteButtonWidget(this.leftPos + CONTROL_BUTTON_LEFT,
                this.realTopPos + CONTROL_BUTTON_TOP,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE,
                this.storageButtonBackgroundSprites(),
                Component.translatable(TRANSLATION_KEY_BASE + ".search.clear"), () -> {
            this.clearSearch();
            this.forceFocus = true;
            this.init();
        }, SpriteButtonWidget.offsetText("X", 0.5F, -0.5F));
        this.addRenderableWidget(this.clearTextButton);

        this.sortTypeButton = new SpriteButtonWidget(this.leftPos + CONTROL_BUTTON_LEFT + CONTROL_BUTTON_SIZE + 3,
                this.realTopPos + CONTROL_BUTTON_TOP,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE,
                this.storageButtonBackgroundSprites(),
                Component.translatable(TRANSLATION_KEY_BASE + ".sort_type"), () -> {
            this.setSortType(this.getSortType().next());
            Networking.sendToServer(
                    new MessageSortItems(this.getEntityPosition(), this.getSortDirection(), this.getSortType()));
            this.init();
        }, this.sortTypeRenderer());
        this.addRenderableWidget(this.sortTypeButton);

        this.sortDirectionButton = new SpriteButtonWidget(
                this.leftPos + CONTROL_BUTTON_LEFT + CONTROL_BUTTON_SIZE * 2 + 6,
                this.realTopPos + CONTROL_BUTTON_TOP,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE,
                this.storageButtonBackgroundSprites(),
                Component.translatable(TRANSLATION_KEY_BASE + ".sort_direction"), () -> {
            this.setSortDirection(this.getSortDirection().next());
            Networking.sendToServer(
                    new MessageSortItems(this.getEntityPosition(), this.getSortDirection(), this.getSortType()));
            this.init();
        }, SpriteButtonWidget.arrow(this.getSortDirection().isDown()));
        this.addRenderableWidget(this.sortDirectionButton);

        // OccultismEmiIntegration excluded from build - EMI sync disabled; show button if JEI is loaded
        if (OccultismJeiIntegration.get().isLoaded()) {
            this.jeiSyncButton = new SpriteButtonWidget(
                    this.leftPos + CONTROL_BUTTON_LEFT + CONTROL_BUTTON_SIZE * 3 + 9,
                    this.realTopPos + CONTROL_BUTTON_TOP,
                    CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE,
                    this.storageButtonBackgroundSprites(),
                    Component.translatable(TRANSLATION_KEY_BASE + ".search.jei"), () -> {
                JeiSettings.setJeiSearchSync(!JeiSettings.isJeiSearchSynced());
                this.init();
            }, this.jeiSyncRenderer());

            this.addRenderableWidget(this.jeiSyncButton);
        }

        switch (this.guiMode) {
            case INVENTORY:
                this.inventoryModeButton = this.createTabButton(true, true, 0);
                this.autocraftingModeButton = this.createTabButton(false, false, 1);
                break;
            case AUTOCRAFTING:
                this.inventoryModeButton = this.createTabButton(true, false, 0);
                this.autocraftingModeButton = this.createTabButton(false, true, 1);
                break;
        }
        this.addRenderableWidget(this.inventoryModeButton);
        this.addRenderableWidget(this.autocraftingModeButton);
    }

    protected void drawItems(GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
        List<ItemStack> stacksToDisplay = this.applySearchToItems();

        var changedPage = this.previousPage != this.currentPage;
        this.previousPage = this.currentPage;

        var changedStacksToDisplay = this.lastCachedStacksToDisplayCount != stacksToDisplay.size();
        this.lastCachedStacksToDisplayCount = stacksToDisplay.size();

        var changedStacks = this.lastStacksCount != this.getClientStorageCache().stacks().size();
        this.lastStacksCount = this.getClientStorageCache().stacks().size();

        if (changedPage || changedStacksToDisplay || changedStacks) {
            this.sortItemStacks(stacksToDisplay);
            this.buildPage(stacksToDisplay);
            this.buildItemSlots(stacksToDisplay);
        }

        this.drawItemSlots(guiGraphics, mouseX, mouseY);
    }

    protected void drawMachines(GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
        List<MachineReference> machinesToDisplay = this.applySearchToMachines();
        this.sortMachines(machinesToDisplay);
        this.buildPage(machinesToDisplay);
        this.buildMachineSlots(machinesToDisplay);
        this.drawMachineSlots(guiGraphics, mouseX, mouseY);
    }

    protected boolean canClick() {
        return System.currentTimeMillis() > this.lastClick + 100L;
    }

    protected boolean isPointInSearchbar(double mouseX, double mouseY) {
        return this.searchBar != null && this.searchBar.isMouseOver(mouseX, mouseY);
    }

    protected boolean isPointInItemArea(double mouseX, double mouseY) {
        int itemAreaHeight = 4 + 18 * this.rows;
        int itemAreaWidth = this.columns * 18 - 2;
        int itemAreaTop = ITEM_AREA_TOP;
        int itemAreaLeft = ITEM_AREA_LEFT;
        return mouseX > (this.leftPos + itemAreaLeft) && mouseX < (this.leftPos + itemAreaWidth + itemAreaLeft) &&
                mouseY > (this.realTopPos + itemAreaTop) && mouseY < (this.realTopPos + itemAreaTop + itemAreaHeight);
    }

    protected boolean isPointInOrderSlotArea(double mouseX, double mouseY) {
        int slotX = this.leftPos + ORDER_INPUT_SLOT_LEFT - 5;
        int slotY = this.menuTop() + ORDER_INPUT_SLOT_TOP - 5;
        return mouseX >= slotX && mouseX < slotX + 28 && mouseY >= slotY && mouseY < slotY + 28;
    }

    protected boolean isPointInSpaceText(double mouseX, double mouseY) {
        return this.isHovering(this.storageSpaceLabel.getX() - this.leftPos - 32, this.storageSpaceLabel.getY() - this.topPos - 2,
                64, this.font.lineHeight + 2, mouseX, mouseY);
    }

    protected boolean isPointInTypesText(double mouseX, double mouseY) {
        return this.isHovering(this.storageTypesLabel.getX() - this.leftPos - 32, this.storageTypesLabel.getY() - this.topPos - 2,
                64, this.font.lineHeight + 2, mouseX, mouseY);
    }

    protected int topControlTooltipY(int mouseY) {
        return Math.min(this.height - 8, mouseY + TOP_CONTROL_TOOLTIP_OFFSET_Y);
    }

    protected void drawTooltips(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        switch (this.guiMode) {
            case INVENTORY:
                for (ItemSlotWidget s : this.itemSlots) {
                    if (s != null && s.isMouseOverSlot(mouseX, mouseY)) {
                        s.drawTooltip(guiGraphics, mouseX, mouseY);
                    }
                }
                break;
            case AUTOCRAFTING:
                for (MachineSlotWidget s : this.machineSlots) {
                    if (s != null && s.isMouseOverSlot(mouseX, mouseY)) {
                        s.drawTooltip(guiGraphics, mouseX, mouseY);
                    }
                }
                break;
        }

        if (this.isPointInSearchbar(mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            if (!Minecraft.getInstance().hasShiftDown()) {
                tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".shift"));
            } else {
                switch (this.guiMode) {
                    case INVENTORY:
                        tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".search.tooltip@"));
                        tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".search.tooltip#"));
                        tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".search.tooltip$"));
                        break;
                    case AUTOCRAFTING:
                        tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".search.machines.tooltip@"));
                        break;
                }
                tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".search.tooltip_rightclick"));
            }
            guiGraphics.setComponentTooltipForNextFrame(this.font, tooltip, mouseX, this.topControlTooltipY(mouseY));
        }
        if (this.clearTextButton != null && this.clearTextButton.isMouseOver(mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font, Lists.newArrayList(Component.translatable(TRANSLATION_KEY_BASE + ".search.tooltip_clear")),
                    mouseX, this.topControlTooltipY(mouseY));
        }
        if (this.clearRecipeButton != null && this.clearRecipeButton.isMouseOver(mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font,
                    Lists.newArrayList(Component.translatable(TRANSLATION_KEY_BASE + ".crafting.tooltip_clear")),
                    mouseX, mouseY);
        }
        if (this.sortTypeButton != null && this.sortTypeButton.isMouseOver(mouseX, mouseY)) {
            String translationKey = "";
            switch (this.guiMode) {
                case INVENTORY:
                    translationKey =
                            TRANSLATION_KEY_BASE + ".search.tooltip_sort_type_" + this.getSortType().getSerializedName();
                    break;
                case AUTOCRAFTING:
                    translationKey =
                            TRANSLATION_KEY_BASE + ".search.machines.tooltip_sort_type_" +
                                    this.getSortType().getSerializedName();
                    break;
            }
            guiGraphics.setTooltipForNextFrame(this.font, Component.translatable(translationKey), mouseX, this.topControlTooltipY(mouseY));
        }
        if (this.sortDirectionButton != null && this.sortDirectionButton.isMouseOver(mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(this.font, Component.translatable(
                            TRANSLATION_KEY_BASE + ".search.tooltip_sort_direction_" + this.getSortDirection().getSerializedName()),
                    mouseX, this.topControlTooltipY(mouseY));
        }
        if (this.jeiSyncButton != null && this.jeiSyncButton.isMouseOver(mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(this.font, Component.translatable(
                            TRANSLATION_KEY_BASE + ".search.tooltip_jei_" +
                                    (JeiSettings.isJeiSearchSynced() ? "on" : "off")),
                    mouseX, this.topControlTooltipY(mouseY));
        }
        if (this.isPointInOrderSlotArea(mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font,
                    List.of(Component.translatable(TRANSLATION_KEY_BASE + ".order_slot.tooltip")), mouseX, mouseY);
        }
        if (this.inventoryModeButton != null && this.inventoryModeButton.isMouseOver(mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font,
                    List.of(Component.translatable(TRANSLATION_KEY_BASE + ".mode.inventory.tooltip")), mouseX, mouseY);
        }
        if (this.autocraftingModeButton != null && this.autocraftingModeButton.isMouseOver(mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font,
                    List.of(Component.translatable(TRANSLATION_KEY_BASE + ".mode.autocrafting.tooltip")), mouseX, mouseY);
        }
        if (this.isPointInSpaceText(mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(this.font, Component.literal(
                    this.usedTotalItemCount + " / " + this.maxTotalItemCount), mouseX, mouseY);
        }
        if (this.isPointInTypesText(mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(this.font, Component.literal(
                    this.usedItemTypes + " / " + this.maxItemTypes), mouseX, mouseY);
        }
    }

    protected void drawItemSlots(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.stackUnderMouse = ItemStack.EMPTY;
        for (ItemSlotWidget slot : this.itemSlots) {
            slot.drawSlot(guiGraphics, mouseX, mouseY);
            if (slot.isMouseOverSlot(mouseX, mouseY)) {
                this.stackUnderMouse = slot.getStack();
                //        break;
            }
        }
    }

    protected void buildItemSlots(List<ItemStack> stacksToDisplay) {
        this.itemSlots = new ArrayList<>();
        int index = (this.currentPage - 1) * (this.columns);
        for (int row = 0; row < this.rows; row++) {
            if (index >= stacksToDisplay.size()) {
                break;
            }
            for (int col = 0; col < this.columns; col++) {
                if (index >= stacksToDisplay.size()) {
                    break;
                }
                this.itemSlots
                        .add(new ItemSlotWidget(this, stacksToDisplay.get(index),
                                this.leftPos + ITEM_AREA_LEFT + col * 18,
                                this.realTopPos + ITEM_AREA_TOP + row * 18, stacksToDisplay.get(index).getCount(),
                                this.leftPos, this.topPos, true));
                index++;
            }
        }
    }

    protected void buildPage(List<?> objectsToDisplay) {
        this.totalPages = objectsToDisplay.size() / this.columns;
        if (objectsToDisplay.size() % this.columns != 0) {
            this.totalPages++;
        }
        this.totalPages -= (this.rows - 1);
        if (this.totalPages < 1) {
            this.totalPages = 1;
        }
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }
        if (this.currentPage > this.totalPages) {
            this.currentPage = this.totalPages;
        }
    }

    protected void sortItemStacks(List<ItemStack> stacksToDisplay) {
        stacksToDisplay.sort(new Comparator<ItemStack>() {

            final int direction = StorageControllerGuiBase.this.getSortDirection().isDown() ? -1 : 1;

            @Override
            public int compare(ItemStack a, ItemStack b) {
                switch (StorageControllerGuiBase.this.getSortType()) {
                    case AMOUNT:
                        return Integer.compare(b.getCount(), a.getCount()) * this.direction;
                    case NAME:
                        return a.getDisplayName().getString()
                                .compareToIgnoreCase(b.getDisplayName().getString()) *
                                this.direction;
                    case MOD:
                        return TextUtil.getModNameForGameObject(a.getItem())
                                .compareToIgnoreCase(TextUtil.getModNameForGameObject(b.getItem())) *
                                this.direction;
                }
                return 0;
            }

        });
    }

    protected void resetDisplayCaches() {
        this.lastStacksCount = 0;
        this.cachedStacksToDisplay = null;
        this.previousPage = -1;
    }

    protected List<ItemStack> applySearchToItems() {
        String searchText = this.searchBar.getValue();

        if (!searchText.equals("")) {
            if (this.cachedStacksToDisplay != null && this.cachedSearchString != null && this.cachedSearchString.equals(searchText))
                return this.cachedStacksToDisplay;

            List<ItemStack> stacksToDisplay = new ArrayList<>();
            for (ItemStack stack : this.getClientStorageCache().stacks()) {
                if (this.itemMatchesSearch(stack))
                    stacksToDisplay.add(stack);
            }

            this.cachedStacksToDisplay = stacksToDisplay;
            this.cachedSearchString = searchText;

            return stacksToDisplay;
        }
        return new ArrayList<>(this.getClientStorageCache().stacks());
    }

    protected List<MachineReference> applySearchToMachines() {
        String searchText = this.searchBar.getValue();

        if (!searchText.equals("")) {
            List<MachineReference> machinesToDisplay = new ArrayList<>();
            for (MachineReference machine : this.linkedMachines) {
                if (this.machineMatchesSearch(machine))
                    machinesToDisplay.add(machine);
            }
            return machinesToDisplay;
        }

        return new ArrayList<>(this.linkedMachines);
    }

    protected boolean itemMatchesSearch(ItemStack stack) {
        String searchText = this.searchBar.getValue();
        if (searchText.startsWith("@")) {
            String name = TextUtil.getModNameForGameObject(stack.getItem());
            return name.toLowerCase().contains(searchText.toLowerCase().substring(1));
        } else if (searchText.startsWith("#")) {
            List<String> tooltip = stack.getTooltipLines(TooltipContext.of(this.minecraft.level), this.minecraft.player, Default.NORMAL).stream()
                    .map(Component::getString).collect(
                            Collectors.toList());
            String tooltipString = Joiner.on(' ').join(tooltip).toLowerCase().trim();
            return tooltipString.toLowerCase().contains(searchText.toLowerCase().substring(1));
        } else if (searchText.startsWith("$")) {
            StringBuilder tagStringBuilder = new StringBuilder();
            stack.getItem().builtInRegistryHolder().tags().forEach(
                    tag -> tagStringBuilder.append(tag.location()).append(" ")
            );
            return tagStringBuilder.toString().toLowerCase().contains(searchText.toLowerCase().substring(1));
        } else {
            //Note: If search stops working, forge may have re-implemented .getUnformattedComponentText() for translated text components
            return stack.getDisplayName().getString().toLowerCase()
                    .contains(searchText.toLowerCase());
        }
    }

    protected boolean machineMatchesSearch(MachineReference machine) {
        String searchText = this.searchBar.getValue();
        if (searchText.startsWith("@")) {
            String name = TextUtil.getModNameForGameObject(machine.getInsertItem());
            return name.toLowerCase().contains(searchText.toLowerCase().substring(1));
        } else {
            String customName = StringUtils.isBlank(machine.customName) ? "" : machine.customName.toLowerCase();
            return machine.getInsertItemStack().getDisplayName().getString().toLowerCase()
                    .contains(searchText.toLowerCase()) ||
                    customName.contains(searchText.toLowerCase().substring(1));
        }
    }

    protected void sortMachines(List<MachineReference> machinesToDisplay) {
        BlockPos entityPosition = this.getEntityPosition();
        ResourceKey<Level> dimensionKey = this.minecraft.player.level().dimension();
        machinesToDisplay.sort(new Comparator<MachineReference>() {

            final int direction = StorageControllerGuiBase.this.getSortDirection().isDown() ? -1 : 1;

            @Override
            public int compare(MachineReference a, MachineReference b) {
                switch (StorageControllerGuiBase.this.getSortType()) {
                    case AMOUNT: //use distance in this case
                        double distanceA =
                                a.insertGlobalPos.getDimensionKey() == dimensionKey ? a.insertGlobalPos.getPos().distSqr(
                                        entityPosition) : Double.MAX_VALUE;
                        double distanceB =
                                b.insertGlobalPos.getDimensionKey() == dimensionKey ? b.insertGlobalPos.getPos().distSqr(
                                        entityPosition) : Double.MAX_VALUE;
                        return Double.compare(distanceB, distanceA) * this.direction;
                    case NAME:
                        return a.getInsertItemStack().getDisplayName().getString()
                                .compareToIgnoreCase(
                                        b.getInsertItemStack().getDisplayName().getString()) *
                                this.direction;
                    case MOD:
                        return TextUtil.getModNameForGameObject(a.getInsertItem())
                                .compareToIgnoreCase(TextUtil.getModNameForGameObject(b.getInsertItem())) *
                                this.direction;
                }
                return 0;
            }

        });
    }

    protected void buildMachineSlots(List<MachineReference> machinesToDisplay) {
        this.machineSlots = new ArrayList<>();
        int index = (this.currentPage - 1) * (this.columns);
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                if (index >= machinesToDisplay.size()) {
                    break;
                }
                this.machineSlots.add(new MachineSlotWidget(this, machinesToDisplay.get(index),
                        this.leftPos + ITEM_AREA_LEFT + col * 18, this.realTopPos + ITEM_AREA_TOP + row * 18, this.leftPos,
                        this.topPos));
                index++;
            }
        }
    }

    protected void drawMachineSlots(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        for (MachineSlotWidget slot : this.machineSlots) {
            slot.drawSlot(guiGraphics, mouseX, mouseY);
        }
    }

    protected void clearSearch() {
        this.searchBar.setValue("");
        // OccultismEmiIntegration excluded from build - EMI sync disabled
        if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
            OccultismJeiIntegration.get().setFilterText("");
        }
    }

    protected void initRootWidgets() {
        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this, this.tabLeft(), this.topPos + TAB_TOP_OFFSET,
                TAB_WIDTH, TAB_HEIGHT, this.tabBackgroundSprite(this.guiMode == StorageControllerGuiMode.INVENTORY)));
        this.root.addChild(new GuiBackgroundWidget(this, this.tabLeft(), this.topPos + TAB_TOP_OFFSET + TAB_HEIGHT,
                TAB_WIDTH, TAB_HEIGHT, this.tabBackgroundSprite(this.guiMode == StorageControllerGuiMode.AUTOCRAFTING)));
        this.root.addChild(new GuiBackgroundWidget(this, this.mainPanelLeft(), this.guiTop() + MAIN_PANEL_TOP,
                this.mainPanelWidth(), this.mainPanelHeight(), this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL,
                GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos + INVENTORY_PANEL_LEFT,
                this.menuTop() + INVENTORY_PANEL_TOP_OFFSET, INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT,
                this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_INVENTORY_PANEL, GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiSpriteWidget(this.leftPos + CRAFTING_ARROW_LEFT - 5, this.menuTop() + CRAFTING_ARROW_TOP,
                GuiSprites.CRAFTING_ARROW));

        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos + ITEM_AREA_LEFT - 1,
                this.guiTop() + ITEM_AREA_TOP - 1, this.itemAreaBackgroundWidth(), this.itemAreaBackgroundHeight(),
                OccultismGuiSprites.STORAGE_CONTROLLER_ITEM_AREA_BACKGROUND));

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            if (i == ORDER_INPUT_SLOT_INDEX) {
                int slotX = this.leftPos + slot.x + this.menuSlotOffsetX(i);
                int slotY = this.menuTop() + slot.y + this.menuSlotOffsetY(i);
                this.root.addChild(new GuiBackgroundWidget(this, slotX - 5, slotY - 5, 28, 28,
                        this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL, GuiSprites.GUI_BACKGROUND)));
                this.root.addChild(new GuiSpriteWidget(slotX, slotY, this.orderInputSlotSprite()));
                this.root.addChild(new GuiSpriteWidget(slotX + 2, slotY + 2,
                        OccultismGuiSprites.STORAGE_CONTROLLER_ANVIL_IMPACT.tinted(0x80FFFFFF).sized(14, 14)));
                continue;
            }
            this.root.addChild(new GuiSpriteWidget(this.leftPos + slot.x + this.menuSlotOffsetX(i),
                    this.menuTop() + slot.y + this.menuSlotOffsetY(i), this.menuSlotSprite(i)));
        }

        LabelWidget titleLabel = new LabelWidget(this.leftPos + this.imageWidth / 2, this.guiTop() + 5, true,
                -1, 2, 2, 0x303030);
        titleLabel.addLine(this.topBarTitleText());
        this.addRenderableWidget(titleLabel);

        LabelWidget inventoryLabel = new LabelWidget(this.leftPos + INVENTORY_LABEL_X,
                this.menuTop() + INVENTORY_LABEL_TOP_OFFSET, false, -1, 2, 0x303030);
        inventoryLabel.addLine(this.playerInventoryTitle);
        this.addRenderableWidget(inventoryLabel);

        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos + this.topBarLeft(), this.guiTop(),
                this.topBarWidth(), TOP_BAR_HEIGHT, this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_TOP_BAR,
                GuiSprites.GUI_BACKGROUND)));
        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos + SEARCH_FIELD_LEFT, this.guiTop() + SEARCH_FIELD_TOP + 1,
                96, CONTROL_BUTTON_SIZE, GuiSprites.FILTER_BUTTON.tinted(STORAGE_BUTTON_TINT)));

        this.root.syncWithHost();
    }

    protected int mainPanelHeight() {
        return this.menuTop() + INVENTORY_PANEL_TOP_OFFSET - this.guiTop() - MAIN_PANEL_TOP - 3;
    }

    protected int mainPanelLeft() {
        return this.leftPos + (this.imageWidth - this.mainPanelWidth()) / 2;
    }

    protected int mainPanelWidth() {
        return this.columns * 18 + 14;
    }

    protected int itemAreaBackgroundWidth() {
        return this.columns * 18;
    }

    protected int itemAreaBackgroundHeight() {
        return this.rows * 18;
    }

    protected int topBarLeft() {
        return this.mainPanelLeft() - this.leftPos - 3;
    }

    protected int topBarWidth() {
        return this.mainPanelWidth() + 6;
    }

    protected int totalGuiHeight() {
        return ITEM_AREA_TOP + this.rows * 18 + INVENTORY_PANEL_TOP_OFFSET + INVENTORY_PANEL_HEIGHT;
    }

    protected int visibleRows() {
        return Math.max(1, Occultism.CLIENT_CONFIG.misc.storageRows.getAsInt());
    }

    protected int guiTop() {
        return this.realTopPos;
    }

    protected int menuTop() {
        return this.topPos;
    }

    protected String topBarTitleText() {
        String titleText = this.title.getString();
        if (titleText.length() >= 2 && titleText.startsWith("[") && titleText.endsWith("]")) {
            return titleText.substring(1, titleText.length() - 1);
        }

        return titleText;
    }

    protected GuiStyle style() {
        return GuiStyleRegistry.get(OccultismGuiStyles.STORAGE_CONTROLLER);
    }

    protected GuiSprite partSprite(com.klikli_dev.codedefinedgui.gui.style.GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    protected int partColor(com.klikli_dev.codedefinedgui.gui.style.GuiPartKey part, int fallback) {
        return this.style().get(part, GuiStyleProperties.COLOR, fallback);
    }

    protected java.util.function.BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> sortTypeRenderer() {
        return switch (this.getSortType()) {
            case AMOUNT -> SpriteButtonWidget.scaledText("123", 0.52F, 0.25F, 0.5F);
            case NAME -> SpriteButtonWidget.scaledText("A-Z", 0.52F, 0.25F, 0.5F);
            case MOD -> SpriteButtonWidget.offsetText("M", 0.5F, -0.5F);
        };
    }

    protected java.util.function.BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> jeiSyncRenderer() {
        return SpriteButtonWidget.coloredText("J", JeiSettings.isJeiSearchSynced() ? JEI_ACTIVE_COLOR : JEI_INACTIVE_COLOR,
                0.0F, -0.5F);
    }

    protected IconButtonBackgroundSprites storageButtonBackgroundSprites() {
        return new IconButtonBackgroundSprites(
                GuiSprites.FILTER_BUTTON.tinted(STORAGE_BUTTON_TINT),
                GuiSprites.FILTER_BUTTON_DOWN.tinted(STORAGE_BUTTON_TINT),
                GuiSprites.FILTER_BUTTON_HOVER.tinted(STORAGE_BUTTON_HOVER_TINT)
        );
    }

    protected AbstractWidget createTabButton(boolean inventoryTab, boolean active, int row) {
        Component tooltip = Component.translatable(TRANSLATION_KEY_BASE + (inventoryTab ? ".mode.inventory" : ".mode.autocrafting"));
        Runnable onPress = () -> {
            this.guiMode = inventoryTab ? StorageControllerGuiMode.INVENTORY : StorageControllerGuiMode.AUTOCRAFTING;
            this.init();
        };
        ItemStack icon = new ItemStack((inventoryTab ? Blocks.CHEST : Blocks.FURNACE).asItem());
        return new SpriteButtonWidget(this.tabLeft(), this.topPos + TAB_TOP_OFFSET + row * TAB_HEIGHT, TAB_WIDTH, TAB_HEIGHT,
                tooltip, onPress, (button, graphics) -> {
                }, this.tabIconRenderer(icon));
    }

    protected GuiSprite tabBackgroundSprite(boolean active) {
        int mainPanelTint = this.partColor(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL, MAIN_PANEL_TINT_FALLBACK);
        return GuiSprites.GUI_BACKGROUND.tinted(active ? mainPanelTint : this.darkenColor(mainPanelTint, 24));
    }

    protected java.util.function.BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> tabIconRenderer(ItemStack icon) {
        return (button, graphics) -> {
            int x = button.getX() + (button.getWidth() - 16) / 2 + TAB_ICON_OFFSET_X;
            int y = button.getY() + (button.getHeight() - 16) / 2;
            graphics.fakeItem(icon, x, y);
        };
    }

    protected int tabLeft() {
        return this.mainPanelLeft() - (TAB_WIDTH - TAB_HIDDEN_OVERLAP) + TAB_LEFT_SHIFT;
    }

    protected int darkenColor(int color, int amount) {
        int alpha = color & 0xFF000000;
        int red = Math.max(0, ((color >> 16) & 0xFF) - amount);
        int green = Math.max(0, ((color >> 8) & 0xFF) - amount);
        int blue = Math.max(0, (color & 0xFF) - amount);
        return alpha | red << 16 | green << 8 | blue;
    }

    protected GuiSprite menuSlotSprite(int slotIndex) {
        if (slotIndex == 0) {
            return GuiSprites.CRAFTING_RESULT_SLOT.tinted(STORAGE_BUTTON_TINT);
        }
        if (slotIndex >= 1 && slotIndex <= 9) {
            return this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_CRAFTING_SLOT, GuiSprites.INVENTORY_SLOT);
        }
        if (slotIndex == ORDER_INPUT_SLOT_INDEX) {
            return this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_ORDER_SLOT, GuiSprites.INVENTORY_SLOT);
        }

        return this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_PLAYER_SLOT, GuiSprites.INVENTORY_SLOT);
    }

    protected GuiSprite orderInputSlotSprite() {
        return this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_CRAFTING_SLOT, GuiSprites.INVENTORY_SLOT);
    }

    protected int menuSlotOffsetX(int slotIndex) {
        return slotIndex == 0 ? -5 : -1;
    }

    protected int menuSlotOffsetY(int slotIndex) {
        return slotIndex == 0 ? -5 : -1;
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
        return this.guiTop();
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
        return this.menuTop() + INVENTORY_PANEL_TOP_OFFSET + INVENTORY_PANEL_HEIGHT - this.guiTop();
    }

    protected static class ScaledEditBox extends EditBox {

        private static final int CURSOR_HEIGHT = 10;
        private static final float TEXT_OFFSET_Y = 2.0F;
        private final float renderScale;
        private final int baseTextHeight;

        protected ScaledEditBox(Font font, int x, int y, int width, int height, Component message, float renderScale) {
            super(font, x, y, width, height, message);
            this.renderScale = renderScale;
            this.baseTextHeight = Math.max(9, height);
            this.setHeight(Math.max(1, Math.round(this.baseTextHeight * this.renderScale)));
        }

        @Override
        public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(this.getX(), this.getY());
            guiGraphics.pose().scale(this.renderScale, this.renderScale);
            guiGraphics.pose().translate(-this.getX(), -this.getY());
            guiGraphics.pose().translate(0.0F, TEXT_OFFSET_Y);
            super.extractWidgetRenderState(guiGraphics, this.scaleMouseX(mouseX), this.scaleMouseY(mouseY), partialTick);
            guiGraphics.pose().popMatrix();
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            return super.mouseClicked(this.scaleMouseEvent(event), doubleClick);
        }

        @Override
        public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
            return super.mouseDragged(this.scaleMouseEvent(event), dx / this.renderScale, dy / this.renderScale);
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return this.isActive() && mouseX >= this.getX() && mouseX < this.getX() + this.getWidth()
                    && mouseY >= this.getY() && mouseY < this.getY() + this.getHeight();
        }

        @Override
        public int getInnerWidth() {
            return Math.max(1, Math.round(super.getInnerWidth() / this.renderScale));
        }

        private MouseButtonEvent scaleMouseEvent(MouseButtonEvent event) {
            return new MouseButtonEvent(this.scaleMouseX(event.x()), this.scaleMouseY(event.y()),
                    new MouseButtonInfo(event.button(), event.modifiers()));
        }

        private int scaleMouseX(double mouseX) {
            return Mth.floor(this.getX() + (mouseX - this.getX()) / this.renderScale);
        }

        private int scaleMouseY(double mouseY) {
            double scaledHeight = this.baseTextHeight * this.renderScale;
            double centeredOffset = (scaledHeight - CURSOR_HEIGHT) / 2.0D;
            return Mth.floor(this.getY() + (mouseY - this.getY() - centeredOffset) / this.renderScale
                    + (this.baseTextHeight - CURSOR_HEIGHT) / 2.0D);
        }
    }
}
