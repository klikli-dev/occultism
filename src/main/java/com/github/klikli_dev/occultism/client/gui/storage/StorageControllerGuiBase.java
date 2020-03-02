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

package com.github.klikli_dev.occultism.client.gui.storage;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.data.*;
import com.github.klikli_dev.occultism.client.gui.controls.GuiButtonSizedImage;
import com.github.klikli_dev.occultism.client.gui.controls.GuiItemSlot;
import com.github.klikli_dev.occultism.client.gui.controls.GuiLabel;
import com.github.klikli_dev.occultism.client.gui.controls.GuiMachineSlot;
import com.github.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.github.klikli_dev.occultism.integration.jei.JeiPlugin;
import com.github.klikli_dev.occultism.network.*;
import com.github.klikli_dev.occultism.util.InputUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class StorageControllerGuiBase<T extends StorageControllerContainerBase> extends ContainerScreen<T> implements IStorageControllerGui, IStorageControllerGuiContainer, IInventoryChangedListener {
    //region Fields
    public static final int ORDER_AREA_OFFSET = 48;
    protected static final ResourceLocation BACKGROUND = new ResourceLocation(Occultism.MODID,
            "textures/gui/storage_controller_droparea.png");
    protected static final ResourceLocation BUTTONS = new ResourceLocation(Occultism.MODID, "textures/gui/buttons.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".storage_controller";
    public List<ItemStack> stacks;
    public List<MachineReference> linkedMachines;
    public IStorageControllerContainer storageControllerContainer;
    public int usedSlots;
    public StorageControllerGuiMode guiMode = StorageControllerGuiMode.INVENTORY;
    protected ItemStack stackUnderMouse = ItemStack.EMPTY;
    protected TextFieldWidget searchBar;
    protected List<GuiItemSlot> itemSlots = new ArrayList<>();
    protected List<GuiMachineSlot> machineSlots = new ArrayList<>();
    protected Button clearTextButton;
    protected Button clearRecipeButton;
    protected Button sortTypeButton;
    protected Button sortDirectionButton;
    protected Button jeiSyncButton;
    protected Button autocraftingModeButton;
    protected Button inventoryModeButton;
    protected GuiLabel storageSpaceLabel;
    protected int rows;
    protected int columns;

    protected int currentPage;
    protected int totalPages;

    protected boolean forceFocus;
    protected long lastClick;
    //endregion Fields

    //region Initialization
    public StorageControllerGuiBase(T container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        this.storageControllerContainer = (IStorageControllerContainer) container;
        this.storageControllerContainer.getOrderSlot().addListener(this);

        //size of the gui texture
        this.xSize = 223;
        this.ySize = 256;

        this.rows = 4;
        this.columns = 9;

        this.currentPage = 1;
        this.totalPages = 1;

        this.stacks = new ArrayList<>();
        this.lastClick = System.currentTimeMillis();

        OccultismPackets.sendToServer(new MessageRequestStacks());
    }
    //endregion Initialization

    //region Getter / Setter
    protected abstract boolean isGuiValid();

    protected abstract BlockPos getEntityPosition();

    public abstract SortDirection getSortDirection();

    public abstract void setSortDirection(SortDirection sortDirection);

    public abstract SortType getSortType();

    public abstract void setSortType(SortType sortType);

    //endregion Getter / Setter

    //region Overrides
    @Override
    public FontRenderer getFontRenderer() {
        return this.font;
    }

    @Override
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        super.fillGradient(left, top, right, bottom, startColor, endColor);
    }

    @Override
    public void renderToolTip(ItemStack stack, int x, int y) {
        super.renderTooltip(stack, x, y);
    }

    @Override
    public void renderToolTip(MachineReference machine, int x, int y) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(machine.getItemStack().getDisplayName().getFormattedText());
        if (machine.customName != null) {
            tooltip.add(
                    TextFormatting.GRAY.toString() + TextFormatting.BOLD + machine.customName + TextFormatting.RESET);
        }
        if (this.minecraft.player.dimension != machine.globalPos.getDimensionType())
            tooltip.add(TextFormatting.GRAY.toString() + TextFormatting.ITALIC + I18n.format(
                    machine.globalPos.getDimensionType().getRegistryName().toString() + TextFormatting.RESET));
        this.renderTooltip(tooltip, x, y);
    }

    @Override
    public void setStacks(List<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public void setUsedSlots(int slots) {
        this.usedSlots = slots;
        this.init();
    }

    @Override
    public void setLinkedMachines(List<MachineReference> machines) {
        this.linkedMachines = machines;
    }

    @Override
    public void init() {
        super.init();
        this.guiLeft = (this.width - this.xSize) / 2 - ORDER_AREA_OFFSET;
        this.guiTop = (this.height - this.ySize) / 2;

        this.buttons.clear();

        int searchBarLeft = 9 + ORDER_AREA_OFFSET;
        int searchBarTop = 7;

        String searchBarText = "";
        if (this.searchBar != null)
            searchBarText = this.searchBar.getText();

        this.searchBar = new TextFieldWidget(this.font, this.guiLeft + searchBarLeft,
                this.guiTop + searchBarTop, 90, this.font.FONT_HEIGHT, "search");
        this.searchBar.setMaxStringLength(30);

        this.searchBar.setEnableBackgroundDrawing(false);
        this.searchBar.setVisible(true);
        this.searchBar.setTextColor(Color.WHITE.getRGB());
        this.searchBar.setFocused2(true);

        this.searchBar.setText(searchBarText);
        if (JeiPlugin.isJeiLoaded() && JeiPlugin.isJeiSearchSynced()) {
            this.searchBar.setText(JeiPlugin.getFilterText());
        }

        int maxSlots = this.storageControllerContainer.getStorageController() != null ? this.storageControllerContainer
                                                                                                .getStorageController()
                                                                                                .getMaxSlots() : 0;

        int storageSpaceInfoLabelLeft = 186;
        int storageSpaceInfoLabelTop = 115;
        this.storageSpaceLabel = new GuiLabel(this.guiLeft + storageSpaceInfoLabelLeft, this.guiTop + storageSpaceInfoLabelTop, true, -1, 2, 0x404040);
        this.storageSpaceLabel.addLine(I18n.format(TRANSLATION_KEY_BASE + ".space_info_label", this.usedSlots, maxSlots), false);
        this.addButton(this.storageSpaceLabel);
        this.initButtons();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if (!this.isGuiValid()) {
            this.minecraft.player.closeScreen();
            return;
        }
        try {
            this.drawTooltips(mouseX, mouseY);
        } catch (Throwable e) {
            Occultism.LOGGER.error("Error drawing tooltip.", e);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        if (!this.isGuiValid()) {
            return;
        }
        if (this.forceFocus) {
            this.searchBar.setFocused2(true);
            if (this.searchBar.isFocused()) {
                this.forceFocus = false;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (!this.isGuiValid()) {
            return;
        }

        this.renderBackground();
        this.drawBackgroundTexture();

        switch (this.guiMode) {
            case INVENTORY:
                this.drawItems(partialTicks, mouseX, mouseY);
                break;
            case AUTOCRAFTING:
                this.drawMachines(partialTicks, mouseX, mouseY);
                break;
        }
        this.searchBar.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.searchBar.setFocused2(false);

        //right mouse button clears search bar
        if (this.isPointInSearchbar(mouseX, mouseY)) {
            this.searchBar.setFocused2(true);

            if (mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                this.clearSearch();
            }
        }
        else if (this.guiMode == StorageControllerGuiMode.INVENTORY) {
            ItemStack stackCarriedByMouse = this.minecraft.player.inventory.getItemStack();
            if (!this.stackUnderMouse.isEmpty() &&
                (mouseButton == InputUtil.MOUSE_BUTTON_LEFT || mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) &&
                stackCarriedByMouse.isEmpty() && this.canClick()) {
                //take item out of storage
                OccultismPackets.sendToServer(
                        new MessageTakeItem(this.stackUnderMouse, mouseButton, Screen.hasShiftDown(),
                                Screen.hasControlDown()));
                this.lastClick = System.currentTimeMillis();
            }
            else if (!stackCarriedByMouse.isEmpty() && this.isPointInItemArea(mouseX, mouseY) && this.canClick()) {
                //put item into storage
                OccultismPackets.sendToServer(new MessageInsertMouseHeldItem(mouseButton));
                this.lastClick = System.currentTimeMillis();
            }
        }
        else if (this.guiMode == StorageControllerGuiMode.AUTOCRAFTING) {
            for (GuiMachineSlot slot : this.machineSlots) {
                if (slot.isMouseOverSlot(mouseX, mouseY)) {
                    if (mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                        ItemStack orderStack = this.storageControllerContainer.getOrderSlot().getStackInSlot(0);
                        if (Screen.hasShiftDown()) {
                            long time = System.currentTimeMillis() + 5000;
                            //TODO: enable block selection and rendering
                            //Occultism.proxy.getClientData().selectBlock(slot.getMachine().globalPos.getPos(), time);
                        }
                        else if (!orderStack.isEmpty()) {
                            //this message both clears the order slot and creates the order
                            OccultismPackets.sendToServer(new MessageRequestOrder(GlobalBlockPos.from(
                                    (TileEntity) this.storageControllerContainer.getStorageController()),
                                    slot.getMachine().globalPos, orderStack));
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
    public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {
        return super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onInventoryChanged(IInventory inventory) {
        if (inventory == this.storageControllerContainer.getOrderSlot() && !inventory.getStackInSlot(0).isEmpty()) {
            this.guiMode = StorageControllerGuiMode.AUTOCRAFTING;
            this.init();
        }
    }

    @Override
    public boolean mouseScrolled(double x, double y, double mouseButton) {
        super.mouseScrolled(x, y, mouseButton);

        //check if mouse is over item area, then handle scrolling
        if (this.isPointInItemArea(x, y)) {
            if (mouseButton > 0 && this.currentPage > 1) {
                this.currentPage--;
            }
            if (mouseButton < 0 && this.currentPage < this.totalPages) {
                this.currentPage++;
            }
        }
        return true;
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        if (this.searchBar.isFocused() && this.searchBar.charTyped(typedChar, keyCode)) {
            OccultismPackets.sendToServer(new MessageRequestStacks());
            if (JeiPlugin.isJeiLoaded() && JeiPlugin.isJeiSearchSynced()) {
                JeiPlugin.setFilterText(this.searchBar.getText());
            }
        }

        return false;
    }
    //endregion Overrides

    //region Methods
    public void initButtons() {
        int controlButtonSize = 12;

        int clearRecipeButtonLeft = 93 + ORDER_AREA_OFFSET;
        int clearRecipeButtonTop = 112;
        this.clearRecipeButton = new GuiButtonSizedImage(this.guiLeft + clearRecipeButtonLeft,
                this.guiTop + clearRecipeButtonTop, controlButtonSize, controlButtonSize, 0, 196, 28, 28, 28, 256, 256,
                BUTTONS, (button) -> {
            OccultismPackets.sendToServer(new MessageClearCraftingMatrix());
            OccultismPackets.sendToServer(new MessageRequestStacks());
            this.init();
        });
        this.addButton(this.clearRecipeButton);

        int controlButtonTop = 5;

        int clearTextButtonLeft = 99 + ORDER_AREA_OFFSET;
        this.clearTextButton = new GuiButtonSizedImage(this.guiLeft + clearTextButtonLeft,
                this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, 196, 28, 28, 28, 256, 256,
                BUTTONS, (button) -> {
            this.clearSearch();
            this.forceFocus = true;
            this.init();
        });
        this.addButton(this.clearTextButton);


        int sortTypeOffset = this.getSortType().getValue() * 28;
        this.sortTypeButton = new GuiButtonSizedImage(this.guiLeft + clearTextButtonLeft + controlButtonSize + 3,
                this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, sortTypeOffset, 28, 28, 28,
                256, 256, BUTTONS, (button) -> {
            this.setSortType(this.getSortType().next());
            OccultismPackets.sendToServer(
                    new MessageSortItems(this.getEntityPosition(), this.getSortDirection(), this.getSortType()));
            this.init();
        });
        this.addButton(this.sortTypeButton);

        int sortDirectionOffset = 84 + (1 - this.getSortDirection().getValue()) * 28;
        this.sortDirectionButton = new GuiButtonSizedImage(
                this.guiLeft + clearTextButtonLeft + controlButtonSize + 3 + controlButtonSize + 3,
                this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, sortDirectionOffset, 28, 28,
                28, 256, 256, BUTTONS, (button) -> {
            this.setSortDirection(this.getSortDirection().next());
            OccultismPackets.sendToServer(
                    new MessageSortItems(this.getEntityPosition(), this.getSortDirection(), this.getSortType()));
            this.init();
        });
        this.addButton(this.sortDirectionButton);

        int jeiSyncOffset = 140 + (JeiPlugin.isJeiSearchSynced() ? 0 : 1) * 28;
        this.jeiSyncButton = new GuiButtonSizedImage(
                this.guiLeft + clearTextButtonLeft + controlButtonSize + 3 + controlButtonSize + 3 + controlButtonSize +
                3, this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, jeiSyncOffset, 28, 28, 28,
                256, 256, BUTTONS, (button) -> {
            JeiPlugin.setJeiSearchSync(!JeiPlugin.isJeiSearchSynced());
            this.init();
        });

        if (JeiPlugin.isJeiLoaded())
            this.addButton(this.jeiSyncButton);


        int guiModeButtonTop = 112;
        int guiModeButtonLeft = 27;
        int guiModeButtonHeight = 29;
        int guiModeButtonWidth = 24;

        switch (this.guiMode) {
            case INVENTORY:
                //active tab button for inventory
                this.inventoryModeButton = new GuiButtonSizedImage(this.guiLeft + guiModeButtonLeft,
                        this.guiTop + 112, guiModeButtonWidth, guiModeButtonHeight, 160, 0, 0, guiModeButtonWidth * 2,
                        guiModeButtonHeight * 2, 256, 256, BUTTONS, (button) -> {
                    this.guiMode = StorageControllerGuiMode.INVENTORY;
                    this.init();
                });
                //inactive tab button for crafting
                this.autocraftingModeButton = new GuiButtonSizedImage(this.guiLeft + guiModeButtonLeft,
                        this.guiTop + guiModeButtonTop + guiModeButtonHeight, guiModeButtonWidth, guiModeButtonHeight,
                        160, 174, 0, guiModeButtonWidth * 2, guiModeButtonHeight * 2, 256, 256, BUTTONS,
                        (button) -> {
                            this.guiMode = StorageControllerGuiMode.AUTOCRAFTING;
                            this.init();
                        });
                break;
            case AUTOCRAFTING:
                //inactive tab button for inventory
                this.inventoryModeButton = new GuiButtonSizedImage(this.guiLeft + guiModeButtonLeft,
                        this.guiTop + 112, guiModeButtonWidth, guiModeButtonHeight, 160, 58, 0, guiModeButtonWidth * 2,
                        guiModeButtonHeight * 2, 256, 256, BUTTONS, (button) -> {
                    this.guiMode = StorageControllerGuiMode.INVENTORY;
                    this.init();
                });
                //active tab button for crafting
                this.autocraftingModeButton = new GuiButtonSizedImage(this.guiLeft + guiModeButtonLeft,
                        this.guiTop + guiModeButtonTop + guiModeButtonHeight, guiModeButtonWidth, guiModeButtonHeight,
                        160, 116, 0, guiModeButtonWidth * 2, guiModeButtonHeight * 2, 256, 256, BUTTONS,
                        (button) -> {
                            this.guiMode = StorageControllerGuiMode.AUTOCRAFTING;
                            this.init();
                        });
                break;
        }
        this.addButton(this.inventoryModeButton);
        this.addButton(this.autocraftingModeButton);
    }

    protected void drawItems(float partialTicks, int mouseX, int mouseY) {
        List<ItemStack> stacksToDisplay = this.applySearchToItems();
        this.sortItemStacks(stacksToDisplay);
        this.buildPage(stacksToDisplay);
        this.buildItemSlots(stacksToDisplay);
        this.drawItemSlots(mouseX, mouseY);
    }

    protected void drawMachines(float partialTicks, int mouseX, int mouseY) {
        List<MachineReference> machinesToDisplay = this.applySearchToMachines();
        this.sortMachines(machinesToDisplay);
        this.buildPage(machinesToDisplay);
        this.buildMachineSlots(machinesToDisplay);
        this.drawMachineSlots(mouseX, mouseY);
    }

    protected boolean canClick() {
        return System.currentTimeMillis() > this.lastClick + 100L;
    }

    protected boolean isPointInSearchbar(double mouseX, double mouseY) {
        return this.isPointInRegion(this.searchBar.x - this.guiLeft + 14, this.searchBar.y - this.guiTop,
                this.searchBar.getWidth(), this.font.FONT_HEIGHT + 6, mouseX, mouseY);
    }

    protected boolean isPointInItemArea(double mouseX, double mouseY) {
        int itemAreaHeight = 82;
        int itemAreaTop = 24;
        int itemAreaLeft = 8 + ORDER_AREA_OFFSET;
        return mouseX > (this.guiLeft + itemAreaLeft) && mouseX < (this.guiLeft + this.xSize - itemAreaLeft) &&
               mouseY > (this.guiTop + itemAreaTop) && mouseY < (this.guiTop + itemAreaTop + itemAreaHeight);
    }

    protected void drawTooltips(int mouseX, int mouseY) {
        switch (this.guiMode) {
            case INVENTORY:
                for (GuiItemSlot s : this.itemSlots) {
                    if (s != null && s.isMouseOverSlot(mouseX, mouseY)) {
                        s.drawTooltip(mouseX, mouseY);
                    }
                }
                break;
            case AUTOCRAFTING:
                for (GuiMachineSlot s : this.machineSlots) {
                    if (s != null && s.isMouseOverSlot(mouseX, mouseY)) {
                        s.drawTooltip(mouseX, mouseY);
                    }
                }
                break;
        }

        if (this.isPointInSearchbar(mouseX, mouseY)) {
            List<String> tooltip = new ArrayList<>();
            if (!Screen.hasShiftDown()) {
                tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".shift"));
            }
            else {
                switch (this.guiMode) {
                    case INVENTORY:
                        tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".search.tooltip@"));
                        tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".search.tooltip#"));
                        tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".search.tooltip$"));
                        break;
                    case AUTOCRAFTING:
                        tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".search.machines.tooltip@"));
                        break;
                }
                tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".search.tooltip_rightclick"));
            }
            this.renderTooltip(tooltip, mouseX, mouseY);
        }
        if (this.clearTextButton != null && this.clearTextButton.isMouseOver(mouseX, mouseY)) {
            this.renderTooltip(Lists.newArrayList(I18n.format(TRANSLATION_KEY_BASE + ".search.tooltip_clear")),
                    mouseX, mouseY);
        }
        if (this.sortTypeButton != null && this.sortTypeButton.isMouseOver(mouseX, mouseY)) {
            String translationKey = "";
            switch (this.guiMode) {
                case INVENTORY:
                    translationKey = TRANSLATION_KEY_BASE + ".search.tooltip_sort_type_" + this.getSortType().getName();
                    break;
                case AUTOCRAFTING:
                    translationKey =
                            TRANSLATION_KEY_BASE + ".search.machines.tooltip_sort_type_" + this.getSortType().getName();
                    break;
            }
            this.renderTooltip(Lists.newArrayList(I18n.format(translationKey)), mouseX, mouseY);
        }
        if (this.sortDirectionButton != null && this.sortDirectionButton.isMouseOver(mouseX, mouseY)) {
            this.renderTooltip(Lists.newArrayList(I18n.format(
                    TRANSLATION_KEY_BASE + ".search.tooltip_sort_direction_" + this.getSortDirection().getName())),
                    mouseX, mouseY);
        }
        if (this.jeiSyncButton != null && this.jeiSyncButton.isMouseOver(mouseX, mouseY)) {
            String s = I18n.format(
                    TRANSLATION_KEY_BASE + ".search.tooltip_jei_" + (JeiPlugin.isJeiSearchSynced() ? "on" : "off"));
            this.renderTooltip(Lists.newArrayList(s), mouseX, mouseY);
        }
    }

    protected void drawBackgroundTexture() {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int xCenter = (this.width - this.xSize) / 2;
        int yCenter = (this.height - this.ySize) / 2;
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    protected void drawItemSlots(int mouseX, int mouseY) {
        this.stackUnderMouse = ItemStack.EMPTY;
        for (GuiItemSlot slot : this.itemSlots) {
            slot.drawSlot(mouseX, mouseY);
            if (slot.isMouseOverSlot(mouseX, mouseY)) {
                this.stackUnderMouse = slot.getStack();
                //        break;
            }
        }
    }

    protected void buildItemSlots(List<ItemStack> stacksToDisplay) {

        int itemAreaLeft = 8 + ORDER_AREA_OFFSET;
        int itemAreaTop = 24;

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
                        .add(new GuiItemSlot(this, stacksToDisplay.get(index), this.guiLeft + itemAreaLeft + col * 18,
                                this.guiTop + itemAreaTop + row * 18, stacksToDisplay.get(index).getCount(),
                                this.guiLeft, this.guiTop, true));
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

            //region Fields
            int direction = StorageControllerGuiBase.this.getSortDirection().isDown() ? -1 : 1;
            //endregion Fields

            //region Overrides
            @Override
            public int compare(ItemStack a, ItemStack b) {
                switch (StorageControllerGuiBase.this.getSortType()) {
                    case AMOUNT:
                        return Integer.compare(b.getCount(), a.getCount()) * this.direction;
                    case NAME:
                        return a.getDisplayName().getUnformattedComponentText()
                                       .compareToIgnoreCase(b.getDisplayName().getUnformattedComponentText()) *
                               this.direction;
                    case MOD:
                        return TextUtil.getModNameForGameObject(a.getItem())
                                       .compareToIgnoreCase(TextUtil.getModNameForGameObject(b.getItem())) *
                               this.direction;
                }
                return 0;
            }
            //endregion Overrides
        });
    }

    protected List<ItemStack> applySearchToItems() {
        String searchText = this.searchBar.getText();

        if (!searchText.equals("")) {
            List<ItemStack> stacksToDisplay = new ArrayList<>();
            for (ItemStack stack : this.stacks) {
                if (this.itemMatchesSearch(stack))
                    stacksToDisplay.add(stack);
            }
            return stacksToDisplay;
        }
        return new ArrayList<>(this.stacks);
    }

    protected List<MachineReference> applySearchToMachines() {
        String searchText = this.searchBar.getText();

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
        String searchText = this.searchBar.getText();
        if (searchText.startsWith("@")) {
            String name = TextUtil.getModNameForGameObject(stack.getItem());
            return name.toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else if (searchText.startsWith("#")) {
            String tooltipString;
            List<String> tooltip = stack.getTooltip(this.minecraft.player, ITooltipFlag.TooltipFlags.NORMAL).stream()
                                           .map(ITextComponent::getUnformattedComponentText).collect(
                            Collectors.toList());
            tooltipString = Joiner.on(' ').join(tooltip).toLowerCase();
            return tooltipString.toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else if (searchText.startsWith("$")) {
            StringBuilder tagStringBuilder = new StringBuilder();
            for (ResourceLocation tag : stack.getItem().getTags()) {
                tagStringBuilder.append(tag.toString()).append(' ');
            }
            return tagStringBuilder.toString().toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else {
            return stack.getDisplayName().getUnformattedComponentText().toLowerCase()
                           .contains(searchText.toLowerCase());
        }
    }

    protected boolean machineMatchesSearch(MachineReference machine) {
        String searchText = this.searchBar.getText();
        if (searchText.startsWith("@")) {
            String name = TextUtil.getModNameForGameObject(machine.getItem());
            return name.toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else {
            String customName = machine.customName == null ? "" : machine.customName.toLowerCase();
            return machine.getItemStack().getDisplayName().getUnformattedComponentText().toLowerCase()
                           .contains(searchText.toLowerCase()) ||
                   customName.contains(searchText.toLowerCase().substring(1));
        }
    }

    protected void sortMachines(List<MachineReference> machinesToDisplay) {
        BlockPos entityPosition = this.getEntityPosition();
        DimensionType dimensionType = this.minecraft.player.dimension;
        machinesToDisplay.sort(new Comparator<MachineReference>() {

            //region Fields
            int direction = StorageControllerGuiBase.this.getSortDirection().isDown() ? -1 : 1;
            //endregion Fields

            //region Overrides
            @Override
            public int compare(MachineReference a, MachineReference b) {
                switch (StorageControllerGuiBase.this.getSortType()) {
                    case AMOUNT: //use distance in this case
                        double distanceA =
                                a.globalPos.getDimensionType() == dimensionType ? a.globalPos.getPos().distanceSq(
                                        entityPosition) : Double.MAX_VALUE;
                        double distanceB =
                                b.globalPos.getDimensionType() == dimensionType ? b.globalPos.getPos().distanceSq(
                                        entityPosition) : Double.MAX_VALUE;
                        return Double.compare(distanceB, distanceA) * this.direction;
                    case NAME:
                        return a.getItemStack().getDisplayName().getUnformattedComponentText()
                                       .compareToIgnoreCase(
                                               b.getItemStack().getDisplayName().getUnformattedComponentText()) *
                               this.direction;
                    case MOD:
                        return TextUtil.getModNameForGameObject(a.getItem())
                                       .compareToIgnoreCase(TextUtil.getModNameForGameObject(b.getItem())) *
                               this.direction;
                }
                return 0;
            }
            //endregion Overrides
        });
    }

    protected void buildMachineSlots(List<MachineReference> machinesToDisplay) {

        int itemAreaLeft = 8 + ORDER_AREA_OFFSET;
        int itemAreaTop = 24;

        this.machineSlots = new ArrayList<>();
        int index = (this.currentPage - 1) * (this.columns);
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                if (index >= machinesToDisplay.size()) {
                    break;
                }
                this.machineSlots.add(new GuiMachineSlot(this, machinesToDisplay.get(index),
                        this.guiLeft + itemAreaLeft + col * 18, this.guiTop + itemAreaTop + row * 18, this.guiLeft,
                        this.guiTop));
                index++;
            }
        }
    }

    protected void drawMachineSlots(int mouseX, int mouseY) {
        for (GuiMachineSlot slot : this.machineSlots) {
            slot.drawSlot(mouseX, mouseY);
        }
    }

    protected void clearSearch() {
        this.searchBar.setText("");
        if (JeiPlugin.isJeiSearchSynced()) {
            JeiPlugin.setFilterText("");
        }
    }

    //endregion Methods
}
