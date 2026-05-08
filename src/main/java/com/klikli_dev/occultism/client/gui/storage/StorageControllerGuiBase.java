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
import com.klikli_dev.codedefinedgui.api.layout.BuiltinLayoutSlotRoles;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSlotView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.style.GuiStyle;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory.PlayerInventoryScreenHost;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory.PlayerInventorySection;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.api.common.data.*;
import com.klikli_dev.occultism.client.gui.storage.adapter.StorageScreenBackend;
import com.klikli_dev.occultism.client.gui.storage.component.StorageItemGridWidget;
import com.klikli_dev.occultism.client.gui.storage.component.StorageCraftingAreaWidget;
import com.klikli_dev.occultism.client.gui.storage.component.StorageInfoWidget;
import com.klikli_dev.occultism.client.gui.storage.component.StorageMachineGridWidget;
import com.klikli_dev.occultism.client.gui.storage.component.StorageModeTabsWidget;
import com.klikli_dev.occultism.client.gui.storage.component.StorageTopBarWidget;
import com.klikli_dev.occultism.client.gui.storage.component.StorageTooltipOverlay;
import com.klikli_dev.occultism.client.gui.storage.component.ScaledSearchFieldWidget;
import com.klikli_dev.occultism.client.gui.storage.logic.StorageScreenActions;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiSprites;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.klikli_dev.occultism.integration.jei.JeiSettings;
import com.klikli_dev.occultism.integration.jei.OccultismJeiIntegration;
import com.klikli_dev.occultism.network.messages.*;
import com.klikli_dev.occultism.util.InputUtil;
import com.klikli_dev.occultism.util.TextUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.language.I18n;
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
import net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre;
import org.apache.commons.lang3.StringUtils;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class StorageControllerGuiBase<T extends StorageControllerContainerBase> extends AbstractStorageTerminalScreen<T> implements IStorageControllerGui, IStorageControllerGuiContainer, ContainerListener, PlayerInventoryScreenHost {

    public static final int ORDER_INPUT_SLOT_INDEX = 10;
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".storage_controller";
    protected static final int GUI_WIDTH = 260;
    protected static final int VISIBLE_COLUMNS = 11;
    protected static final int CONTROL_BUTTON_SIZE = 12;
    protected static final int INVENTORY_PANEL_TOP_OFFSET = 66;
    protected static final int INVENTORY_PANEL_HEIGHT = 90;
    protected static final int TAB_WIDTH = 34;
    protected static final int TAB_HEIGHT = 29;
    protected static final int TAB_ICON_OFFSET_X = -3;
    protected static final int MAIN_PANEL_TINT_FALLBACK = 0xFF4B5563;
    protected static final int STORAGE_BUTTON_TINT = 0xFF5D6878;
    protected static final int STORAGE_BUTTON_HOVER_TINT = 0xFF707C8D;
    protected static final int TOP_CONTROL_TOOLTIP_OFFSET_Y = 18;
    protected static final float SEARCH_BAR_SCALE = 0.75F;
    protected static final int JEI_ACTIVE_COLOR = 0xFF20A020;
    protected static final int JEI_INACTIVE_COLOR = 0xFFC03030;
    private static final int PLAYER_SLOT_COUNT = 36;

    public int lastStacksCount;
    public ClientStorageCache clientStorageCache;
    public List<MachineReference> linkedMachines;
    public IStorageControllerContainer storageControllerContainer;
    protected int maxItemTypes;
    protected int usedItemTypes;
    protected long maxTotalItemCount;
    protected long usedTotalItemCount;
    protected ItemStack stackUnderMouse = ItemStack.EMPTY;
    protected ScaledSearchFieldWidget searchBar;
    protected AbstractWidget clearTextButton;
    protected AbstractWidget clearRecipeButton;
    protected AbstractWidget sortTypeButton;
    protected AbstractWidget sortDirectionButton;
    protected AbstractWidget jeiSyncButton;
    protected AbstractWidget autocraftingModeButton;
    protected AbstractWidget inventoryModeButton;
    protected LabelWidget storageSpaceLabel;
    protected LabelWidget storageTypesLabel;
    protected final StorageScreenBackend backend;
    protected final StorageScreenState state;
    protected final StorageDisplayQuery displayQuery;
    protected final StorageScreenActions actions;
    protected final StorageItemGridWidget itemGrid;
    protected final StorageMachineGridWidget machineGrid;
    protected final PlayerInventorySection playerInventorySection;
    protected StorageTopBarWidget topBarWidget;
    protected StorageModeTabsWidget modeTabsWidget;
    protected StorageCraftingAreaWidget craftingAreaWidget;
    protected StorageInfoWidget storageInfoWidget;
    protected final StorageTooltipOverlay tooltipOverlay;
    protected int rows;
    protected int columns;
    protected int realTopPos;
    private int lastCachedStacksToDisplayCount;
    private List<ItemStack> cachedStacksToDisplay;
    private String cachedSearchString;

    public StorageControllerGuiBase(T container, Inventory playerInventory, Component name, StorageScreenBackend backend) {
        super(container, playerInventory, name, GUI_WIDTH, 256);
        this.storageControllerContainer = container;
        this.backend = backend;
        // SimpleContainer.addListener was removed in 26.1 - using containerChanged polling instead
        this.state = new StorageScreenState();
        this.displayQuery = new StorageDisplayQuery();
        this.actions = new StorageScreenActions();
        this.itemGrid = new StorageItemGridWidget(this);
        this.machineGrid = new StorageMachineGridWidget(this);
        this.playerInventorySection = PlayerInventorySection.standard();
        this.tooltipOverlay = new StorageTooltipOverlay(TRANSLATION_KEY_BASE, TOP_CONTROL_TOOLTIP_OFFSET_Y);

        this.rows = Occultism.CLIENT_CONFIG.misc.storageRows.getAsInt();
        this.columns = VISIBLE_COLUMNS;

        this.clientStorageCache = new ClientStorageCache();
        this.storageControllerContainer.setClientStorageCache(this.clientStorageCache);

        this.linkedMachines = new ArrayList<>();

        this.state.markInteraction(System.currentTimeMillis());

        this.resetDisplayCaches();

        this.actions.requestStacks();
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
    //endregion Getter / Setter

    protected boolean isGuiValid() {
        return this.backend.isValid();
    }

    protected BlockPos getEntityPosition() {
        return this.backend.actionPosition();
    }

    public SortDirection getSortDirection() {
        return this.backend.sortDirection();
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.backend.setSortDirection(sortDirection);
    }

    public SortType getSortType() {
        return this.backend.sortType();
    }

    public void setSortType(SortType sortType) {
        this.backend.setSortType(sortType);
    }

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
        this.topPos = this.realTopPos + StorageTerminalLayouts.menuTop(this.rows);
        this.resolveLayout();

        this.clearWidgets();

        this.initRootWidgets();

        this.initTopBar();
        this.initStorageInfo();
        this.initCraftingSection();
        this.initModeTabs();

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
        if (this.state.consumeSearchFocusRequest()) {
            this.searchBar.setFocused(true);
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

        switch (this.state.mode()) {
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
        } else if (this.state.isInventoryMode()) {
            ItemStack stackCarriedByMouse = this.minecraft.player.containerMenu.getCarried();
            if (!this.stackUnderMouse.isEmpty() &&
                    (mouseButton == InputUtil.MOUSE_BUTTON_LEFT || mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) &&
                    stackCarriedByMouse.isEmpty() && this.canClick()) {
                //take item out of storage
                this.actions.takeStack(this.stackUnderMouse, mouseButton,
                        () -> this.state.markInteraction(System.currentTimeMillis()));
            } else if (!stackCarriedByMouse.isEmpty() && this.isPointInItemArea(mouseX, mouseY) && this.canClick()) {
                //put item into storage
                this.actions.insertCarriedItem(mouseButton,
                        () -> this.state.markInteraction(System.currentTimeMillis()));
            }
        } else if (this.state.isAutocraftingMode()) {
            MachineReference hoveredMachine = this.machineGrid.hoveredMachine(mouseX, mouseY);
            if (hoveredMachine != null && mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                ItemStack orderStack = this.storageControllerContainer.getOrderSlot().getItem(0);
                if (Minecraft.getInstance().hasShiftDown()) {
                    this.actions.highlightMachine(hoveredMachine);
                } else if (!orderStack.isEmpty()) {
                    this.actions.requestMachineOrder(
                            this.storageControllerContainer::getStorageControllerGlobalBlockPos,
                            hoveredMachine,
                            () -> this.storageControllerContainer.getOrderSlot().getItem(0),
                            component -> Occultism.LOGGER.warn(component.getString()),
                            () -> this.state.setMode(StorageControllerGuiMode.INVENTORY)
                    );
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
                && !this.state.isAutocraftingMode()) {
            this.state.setMode(StorageControllerGuiMode.AUTOCRAFTING);
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
            if (pScrollY > 0) {
                this.state.scrollUp();
            }
            if (pScrollY < 0) {
                this.state.scrollDown();
            }
        }
        return true;
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (this.searchBar.isFocused() && this.searchBar.charTyped(event)) {
            this.state.setSearchText(this.searchBar.getValue());
            this.actions.requestStacks();
            // OccultismEmiIntegration excluded from build - EMI sync disabled
            if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
                OccultismJeiIntegration.get().setFilterText(this.searchBar.getValue());
            }
        }

        return false;
    }

    protected void initTopBar() {
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
        if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
            searchBarText = OccultismJeiIntegration.get().getFilterText();
        }
        this.topBarWidget = StorageTopBarWidget.create(
                this.font,
                this.topBarSearchBarX(),
                this.topBarSearchBarY(),
                searchBarRenderedWidth,
                searchBarRenderedHeight,
                SEARCH_BAR_SCALE,
                searchBarText,
                focus,
                CONTROL_BUTTON_SIZE,
                this.storageButtonBackgroundSprites(),
                this.topBarControlButtonX(0),
                this.topBarControlButtonY(0),
                () -> {
                    this.clearSearch();
                    this.state.requestSearchFocus();
                    this.init();
                },
                this.topBarControlButtonX(1),
                this.topBarControlButtonY(1),
                () -> {
                    this.setSortType(this.getSortType().next());
                    this.actions.syncSort(this.getEntityPosition(), this.getSortDirection(), this.getSortType());
                    this.init();
                },
                this.sortTypeRenderer(),
                this.topBarControlButtonX(2),
                this.topBarControlButtonY(2),
                () -> {
                    this.setSortDirection(this.getSortDirection().next());
                    this.actions.syncSort(this.getEntityPosition(), this.getSortDirection(), this.getSortType());
                    this.init();
                },
                SpriteButtonWidget.arrow(this.getSortDirection().isDown()),
                OccultismJeiIntegration.get().isLoaded(),
                this.topBarControlButtonX(3),
                this.topBarControlButtonY(3),
                () -> {
                    JeiSettings.setJeiSearchSync(!JeiSettings.isJeiSearchSynced());
                    this.init();
                },
                this.jeiSyncRenderer(),
                TRANSLATION_KEY_BASE
        );
        this.searchBar = this.topBarWidget.searchBar();
        this.clearTextButton = this.topBarWidget.clearSearchButton();
        this.sortTypeButton = this.topBarWidget.sortTypeButton();
        this.sortDirectionButton = this.topBarWidget.sortDirectionButton();
        this.jeiSyncButton = this.topBarWidget.jeiSyncButton();
        this.state.setSearchText(this.searchBar.getValue());
        this.topBarWidget.addTo(this::addRenderableWidget);
    }

    protected void initStorageInfo() {
        this.storageInfoWidget = StorageInfoWidget.create(
                this.storageSpaceLabelPosition().left(),
                this.storageSpaceLabelPosition().top(),
                this.storageSpaceText(),
                this.storageTypesLabelPosition().left(),
                this.storageTypesLabelPosition().top(),
                this.storageTypesText()
        );
        this.storageSpaceLabel = this.storageInfoWidget.storageSpaceLabel();
        this.storageTypesLabel = this.storageInfoWidget.storageTypesLabel();
        this.storageInfoWidget.addTo(this::addRenderableWidget);
    }

    protected void initCraftingSection() {
        this.craftingAreaWidget = StorageCraftingAreaWidget.create(
                CONTROL_BUTTON_SIZE,
                this.storageButtonBackgroundSprites(),
                this.clearRecipeButtonX(),
                this.clearRecipeButtonY(),
                () -> this.actions.clearCraftingMatrixAndRefresh(this::init),
                TRANSLATION_KEY_BASE
        );
        this.clearRecipeButton = this.craftingAreaWidget.clearRecipeButton();
        this.craftingAreaWidget.addTo(this::addRenderableWidget);
    }

    protected void initModeTabs() {
        this.modeTabsWidget = StorageModeTabsWidget.create(
                this.tabPosition(0).left(),
                this.tabPosition(0).top(),
                this.tabPosition(1).left(),
                this.tabPosition(1).top(),
                TAB_WIDTH,
                TAB_HEIGHT,
                TAB_ICON_OFFSET_X,
                () -> {
                    this.state.setMode(StorageControllerGuiMode.INVENTORY);
                    this.init();
                },
                () -> {
                    this.state.setMode(StorageControllerGuiMode.AUTOCRAFTING);
                    this.init();
                },
                Component.translatable(TRANSLATION_KEY_BASE + ".mode.inventory"),
                Component.translatable(TRANSLATION_KEY_BASE + ".mode.autocrafting")
        );
        this.inventoryModeButton = this.modeTabsWidget.inventoryModeButton();
        this.autocraftingModeButton = this.modeTabsWidget.autocraftingModeButton();
        this.modeTabsWidget.addTo(this::addRenderableWidget);
    }

    protected void drawItems(GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
        List<ItemStack> stacksToDisplay = this.applySearchToItems();

        this.state.setMaxFirstVisibleRow(this.displayQuery.maxFirstVisibleRow(stacksToDisplay.size(), this.columns, this.rows));
        boolean changedFirstVisibleRow = this.state.trackFirstVisibleRowChange();

        var changedStacksToDisplay = this.lastCachedStacksToDisplayCount != stacksToDisplay.size();
        this.lastCachedStacksToDisplayCount = stacksToDisplay.size();

        var changedStacks = this.lastStacksCount != this.getClientStorageCache().stacks().size();
        this.lastStacksCount = this.getClientStorageCache().stacks().size();

        if (changedFirstVisibleRow || changedStacksToDisplay || changedStacks) {
            this.sortItemStacks(stacksToDisplay);
            this.buildItemSlots(stacksToDisplay);
        }

        this.drawItemSlots(guiGraphics, mouseX, mouseY);
    }

    protected void drawMachines(GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
        List<MachineReference> machinesToDisplay = this.applySearchToMachines();
        this.state.setMaxFirstVisibleRow(this.displayQuery.maxFirstVisibleRow(machinesToDisplay.size(), this.columns, this.rows));
        this.sortMachines(machinesToDisplay);
        this.buildMachineSlots(machinesToDisplay);
        this.drawMachineSlots(guiGraphics, mouseX, mouseY);
    }

    protected boolean canClick() {
        return this.state.canInteract(System.currentTimeMillis(), 100L);
    }

    protected boolean isPointInSearchbar(double mouseX, double mouseY) {
        return this.searchBar != null && this.searchBar.isMouseOver(mouseX, mouseY);
    }

    protected boolean isPointInItemArea(double mouseX, double mouseY) {
        Bounds itemAreaBounds = this.itemAreaHoverBounds();
        return mouseX > itemAreaBounds.left() && mouseX < itemAreaBounds.right() &&
                mouseY > itemAreaBounds.top() && mouseY < itemAreaBounds.bottom();
    }

    protected boolean isPointInOrderSlotArea(double mouseX, double mouseY) {
        Bounds bounds = this.orderSlotHoverBounds();
        return mouseX >= bounds.left() && mouseX < bounds.right() && mouseY >= bounds.top() && mouseY < bounds.bottom();
    }

    protected boolean isPointInSpaceText(double mouseX, double mouseY) {
        return this.storageInfoWidget != null && this.storageInfoWidget.isStorageSpaceTextHovered(
                this::isHovering,
                this.leftPos,
                this.topPos,
                this.font.lineHeight,
                mouseX,
                mouseY
        );
    }

    protected boolean isPointInTypesText(double mouseX, double mouseY) {
        return this.storageInfoWidget != null && this.storageInfoWidget.isStorageTypesTextHovered(
                this::isHovering,
                this.leftPos,
                this.topPos,
                this.font.lineHeight,
                mouseX,
                mouseY
        );
    }

    protected void drawTooltips(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        switch (this.state.mode()) {
            case INVENTORY:
                this.itemGrid.drawTooltips(guiGraphics, mouseX, mouseY);
                break;
            case AUTOCRAFTING:
                this.machineGrid.drawTooltips(guiGraphics, mouseX, mouseY);
                break;
        }
        this.tooltipOverlay.drawChromeTooltips(
                guiGraphics,
                this.font,
                mouseX,
                mouseY,
                this.height,
                this.state.mode(),
                this.isPointInSearchbar(mouseX, mouseY),
                this.clearTextButton,
                this.clearRecipeButton,
                this.sortTypeButton,
                this.getSortType().getSerializedName(),
                this.sortDirectionButton,
                this.getSortDirection().getSerializedName(),
                this.jeiSyncButton,
                this.isPointInOrderSlotArea(mouseX, mouseY),
                this.inventoryModeButton,
                this.autocraftingModeButton,
                this.isPointInSpaceText(mouseX, mouseY),
                this.usedTotalItemCount,
                this.maxTotalItemCount,
                this.isPointInTypesText(mouseX, mouseY),
                this.usedItemTypes,
                this.maxItemTypes
        );
    }

    protected void drawItemSlots(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.stackUnderMouse = this.itemGrid.drawAndGetHoveredStack(guiGraphics, mouseX, mouseY);
    }

    protected void buildItemSlots(List<ItemStack> stacksToDisplay) {
        this.itemGrid.rebuild(stacksToDisplay,
                this.displayQuery.firstVisibleIndex(this.state.firstVisibleRow(), this.columns),
                this.rows,
                this.columns,
                this::gridCellPoint,
                this.leftPos,
                this.topPos);
    }

    protected void sortItemStacks(List<ItemStack> stacksToDisplay) {
        this.displayQuery.sortItems(stacksToDisplay, this.getSortDirection(), this.getSortType());
    }

    protected void resetDisplayCaches() {
        this.lastStacksCount = 0;
        this.cachedStacksToDisplay = null;
        this.state.resetDisplayTracking();
    }

    protected List<ItemStack> applySearchToItems() {
        String searchText = this.state.searchText();

        if (!searchText.equals("")) {
            if (this.cachedStacksToDisplay != null && this.cachedSearchString != null && this.cachedSearchString.equals(searchText))
                return this.cachedStacksToDisplay;

            List<ItemStack> stacksToDisplay = this.displayQuery.filterItems(this.getClientStorageCache().stacks(), searchText,
                    this::itemMatchesSearch);

            this.cachedStacksToDisplay = stacksToDisplay;
            this.cachedSearchString = searchText;

            return stacksToDisplay;
        }
        return new ArrayList<>(this.getClientStorageCache().stacks());
    }

    protected List<MachineReference> applySearchToMachines() {
        return this.displayQuery.filterMachines(this.linkedMachines, this.state.searchText(), this::machineMatchesSearch);
    }

    protected boolean itemMatchesSearch(ItemStack stack) {
        String searchText = this.state.searchText();
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
        String searchText = this.state.searchText();
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
        this.displayQuery.sortMachines(machinesToDisplay, this.getSortDirection(), this.getSortType(),
                this.getEntityPosition(), this.minecraft.player.level().dimension());
    }

    protected void buildMachineSlots(List<MachineReference> machinesToDisplay) {
        this.machineGrid.rebuild(machinesToDisplay,
                this.displayQuery.firstVisibleIndex(this.state.firstVisibleRow(), this.columns),
                this.rows,
                this.columns,
                this::gridCellPoint,
                this.leftPos,
                this.topPos);
    }

    protected void drawMachineSlots(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.machineGrid.draw(guiGraphics, mouseX, mouseY);
    }

    protected Point gridCellPoint(int column, int row) {
        Position position = this.itemCellPosition(column, row);
        return new Point(position.left(), position.top());
    }

    protected void clearSearch() {
        this.searchBar.setValue("");
        this.state.setSearchText("");
        // OccultismEmiIntegration excluded from build - EMI sync disabled
        if (OccultismJeiIntegration.get().isLoaded() && JeiSettings.isJeiSearchSynced()) {
            OccultismJeiIntegration.get().setFilterText("");
        }
    }

    protected void initRootWidgets() {
        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();

        this.root.syncWithHost();
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        this.registerMainPanelResolvers(registry);
        this.registerTopBarResolvers(registry);
        this.registerPlayerInventoryResolvers(registry);
        this.registerCraftingResolvers(registry);
    }

    protected void registerMainPanelResolvers(LayoutResolverRegistry registry) {
        StorageModeTabsWidget.registerResolvers(
                registry,
                this,
                this.tabBackgroundSprite(this.state.isInventoryMode()),
                this.tabBackgroundSprite(this.state.isAutocraftingMode())
        );
        registry.resolve("frame.main.panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.main.item_area_background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                OccultismGuiSprites.STORAGE_CONTROLLER_ITEM_AREA_BACKGROUND
        )));
    }

    protected void registerTopBarResolvers(LayoutResolverRegistry registry) {
        StorageTopBarWidget.registerResolvers(
                registry,
                this,
                this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_TOP_BAR, GuiSprites.GUI_BACKGROUND),
                GuiSprites.FILTER_BUTTON.tinted(STORAGE_BUTTON_TINT)
        );
    }

    protected void registerPlayerInventoryResolvers(LayoutResolverRegistry registry) {
        this.playerInventorySection.registerResolvers(registry.scope("frame.menu.player_inventory"), this);
        registry.resolve("frame.menu.player_inventory.background", -100, ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x() + 2,
                ctx.node().y() + 2,
                ctx.node().widthOrThrow() - 4,
                ctx.node().heightOrThrow() - 4,
                this.style().get(BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND)
        )));
    }

    protected void registerCraftingResolvers(LayoutResolverRegistry registry) {
        StorageCraftingAreaWidget.registerResolvers(
                registry,
                this,
                GuiSprites.CRAFTING_ARROW,
                this::menuSlotSprite,
                this.partSprite(OccultismGuiParts.STORAGE_CONTROLLER_INVENTORY_PANEL, GuiSprites.GUI_BACKGROUND),
                this.orderInputSlotSprite(),
                OccultismGuiSprites.STORAGE_CONTROLLER_ANVIL_IMPACT.tinted(0x80FFFFFF).sized(14, 14)
        );
    }

    protected Component storageSpaceText() {
        return Component.literal(I18n.get(TRANSLATION_KEY_BASE + ".space_info_label_new",
                String.format("%.2f", (double) this.usedTotalItemCount / (double) this.maxTotalItemCount * 100)));
    }

    protected Component storageTypesText() {
        return Component.literal(I18n.get(TRANSLATION_KEY_BASE + ".space_info_label_types",
                String.format("%.0f", (double) this.usedItemTypes / (double) this.maxItemTypes * 100)));
    }

    protected int totalGuiHeight() {
        return StorageTerminalLayouts.totalGuiHeight(this.rows);
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

    protected GuiStyle style() {
        return GuiStyleRegistry.get(OccultismGuiStyles.STORAGE_CONTROLLER);
    }

    protected GuiSprite partSprite(com.klikli_dev.codedefinedgui.api.style.GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    protected int partColor(com.klikli_dev.codedefinedgui.api.style.GuiPartKey part, int fallback) {
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

    protected GuiSprite tabBackgroundSprite(boolean active) {
        int mainPanelTint = this.partColor(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL, MAIN_PANEL_TINT_FALLBACK);
        return GuiSprites.GUI_BACKGROUND.tinted(active ? mainPanelTint : this.darkenColor(mainPanelTint, 24));
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

    protected Position topBarSearchBarPosition() {
        return this.nodePosition("frame.top_bar.search.input");
    }

    protected int topBarSearchBarX() {
        return this.topBarSearchBarPosition().left();
    }

    protected int topBarSearchBarY() {
        return this.topBarSearchBarPosition().top();
    }

    protected Position topBarControlButtonPosition(int index) {
        return this.nodePosition("frame.top_bar.controls.button_" + index);
    }

    protected int topBarControlButtonX(int index) {
        return this.topBarControlButtonPosition(index).left();
    }

    protected int topBarControlButtonY(int index) {
        return this.topBarControlButtonPosition(index).top();
    }

    protected Position clearRecipeButtonPosition() {
        return this.nodePosition("frame.menu.clear_recipe_button");
    }

    protected int clearRecipeButtonX() {
        return this.clearRecipeButtonPosition().left();
    }

    protected int clearRecipeButtonY() {
        return this.clearRecipeButtonPosition().top();
    }

    protected Position itemCellPosition(int column, int row) {
        return this.nodePosition("frame.main.item_area.slot_" + (row * this.columns + column));
    }

    protected Position tabPosition(int row) {
        return this.nodePosition(row == 0 ? "frame.main.tabs.inventory" : "frame.main.tabs.autocrafting");
    }

    protected Bounds itemAreaHoverBounds() {
        Position itemAreaNode = this.nodePosition("frame.main.item_area.slot_0");
        return new Bounds(itemAreaNode.left(), itemAreaNode.top(),
                itemAreaNode.left() + this.columns * 18 - 2,
                itemAreaNode.top() + 4 + 18 * this.rows);
    }

    protected Bounds orderSlotHoverBounds() {
        var node = this.resolvedLayout.node("frame.menu.order.slot_background");
        Position position = this.nodePosition("frame.menu.order.slot_background");
        return new Bounds(position.left(), position.top(), position.left() + node.widthOrThrow(), position.top() + node.heightOrThrow());
    }

    protected Position nodePosition(String path) {
        var node = this.resolvedLayout.node(path);
        return new Position(this.leftPos + node.x(), this.guiTop() + node.y());
    }

    protected Position storageSpaceLabelPosition() {
        return this.nodePosition("frame.menu.storage_space_label");
    }

    protected Position storageTypesLabelPosition() {
        return this.nodePosition("frame.menu.storage_types_label");
    }

    @Override
    public List<LayoutSlotView> layoutSlots() {
        return this.createPlayerInventorySlots();
    }

    protected List<LayoutSlotView> createPlayerInventorySlots() {
        int playerInventoryHostOffsetY = this.menuTop() - this.guiTop();
        List<LayoutSlotView> layoutSlots = new ArrayList<>(PLAYER_SLOT_COUNT);
        for (int slotIndex = 0; slotIndex < PLAYER_SLOT_COUNT; slotIndex++) {
            Slot slot = this.menu.getSlot(slotIndex + 11);
            Slot renderedSlot = new Slot(slot.container, slot.getSlotIndex(), slot.x, slot.y + playerInventoryHostOffsetY);
            if (slotIndex < 27) {
                layoutSlots.add(new LayoutSlotView(renderedSlot, BuiltinLayoutSlotRoles.PLAYER_MAIN,
                        OccultismGuiParts.STORAGE_CONTROLLER_PLAYER_SLOT, "main.slot_" + slotIndex));
                continue;
            }

            layoutSlots.add(new LayoutSlotView(renderedSlot, BuiltinLayoutSlotRoles.PLAYER_HOTBAR,
                    OccultismGuiParts.STORAGE_CONTROLLER_PLAYER_SLOT, "hotbar.slot_" + (slotIndex - 27)));
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

    protected record Position(int left, int top) {
    }

    protected record Bounds(int left, int top, int right, int bottom) {
    }
}
