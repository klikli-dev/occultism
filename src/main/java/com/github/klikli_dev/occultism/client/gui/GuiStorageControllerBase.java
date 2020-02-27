/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the storage system in this file has been based on https://github.com/MrRiegel.
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

package com.github.klikli_dev.occultism.client.gui;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.data.*;
import com.github.klikli_dev.occultism.client.gui.controls.GuiButtonSizedImage;
import com.github.klikli_dev.occultism.client.gui.controls.GuiItemSlot;
import com.github.klikli_dev.occultism.client.gui.controls.GuiLabelNoShadow;
import com.github.klikli_dev.occultism.client.gui.controls.GuiMachineSlot;
import com.github.klikli_dev.occultism.integration.jei.JeiPlugin;
import com.github.klikli_dev.occultism.network.*;
import com.github.klikli_dev.occultism.util.InputUtil;
import com.github.klikli_dev.occultism.util.ModNameUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class GuiStorageControllerBase extends GuiContainer implements IStorageControllerGui, IStorageControllerGuiContainer, IInventoryChangedListener {
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
    protected GuiTextField searchBar;
    protected List<GuiItemSlot> itemSlots = new ArrayList<>();
    protected List<GuiMachineSlot> machineSlots = new ArrayList<>();
    protected GuiButton clearTextButton;
    protected GuiButton clearRecipeButton;
    protected GuiButton sortTypeButton;
    protected GuiButton sortDirectionButton;
    protected GuiButton jeiSyncButton;
    protected GuiButton autocraftingModeButton;
    protected GuiButton inventoryModeButton;
    protected GuiLabelNoShadow storageSpaceInfoLabel;
    protected int rows;
    protected int columns;

    protected int currentPage;
    protected int totalPages;

    protected boolean forceFocus;
    protected long lastClick;
    //endregion Fields

    //region Initialization
    public GuiStorageControllerBase(Container container) {
        super(container);
        this.storageControllerContainer = (IStorageControllerContainer) container;
        this.storageControllerContainer.getOrderSlot().addInventoryChangeListener(this);
        this.mc = Minecraft.getMinecraft();

        //size of the gui texture
        this.xSize = 223;
        this.ySize = 256;

        this.rows = 4;
        this.columns = 9;

        this.currentPage = 1;
        this.totalPages = 1;

        this.stacks = new ArrayList<>();
        this.lastClick = System.currentTimeMillis();

        Occultism.network.sendToServer(new MessageRequestStacks());
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
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        super.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    @Override
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    @Override
    public void renderToolTip(MachineReference machine, int x, int y) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(machine.getItemStack().getDisplayName());
        if (machine.customName != null) {
            tooltip.add(
                    TextFormatting.GRAY.toString() + TextFormatting.BOLD + machine.customName + TextFormatting.RESET);
        }
        if (Minecraft.getMinecraft().player.dimension != machine.globalPos.getDimension())
            tooltip.add(TextFormatting.GRAY.toString() + TextFormatting.ITALIC + I18n.format(
                    DimensionType.getById(machine.globalPos.getDimension()).getName() + TextFormatting.RESET));
        this.drawHoveringText(tooltip, x, y);
    }

    @Override
    public void setStacks(List<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public void setUsedSlots(int slots) {
        this.usedSlots = slots;
        this.initGui();
    }

    @Override
    public void setLinkedMachines(List<MachineReference> machines) {
        this.linkedMachines = machines;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2 - ORDER_AREA_OFFSET;
        this.guiTop = (this.height - this.ySize) / 2;

        this.buttonList.clear();
        this.labelList.clear();

        Keyboard.enableRepeatEvents(true);

        int searchBarLeft = 9 + ORDER_AREA_OFFSET;
        int searchBarTop = 7;

        String searchBarText = "";
        if (this.searchBar != null)
            searchBarText = this.searchBar.getText();

        this.searchBar = new GuiTextField(0, this.fontRenderer, this.guiLeft + searchBarLeft,
                this.guiTop + searchBarTop, 90, this.fontRenderer.FONT_HEIGHT);
        this.searchBar.setMaxStringLength(30);

        this.searchBar.setEnableBackgroundDrawing(false);
        this.searchBar.setVisible(true);
        this.searchBar.setTextColor(Color.WHITE.getRGB());
        this.searchBar.setFocused(true);

        this.searchBar.setText(searchBarText);
        if (JeiPlugin.isJeiLoaded() && JeiPlugin.isJeiSearchSynced()) {
            this.searchBar.setText(JeiPlugin.getFilterText());
        }

        int maxSlots = this.storageControllerContainer.getStorageController() != null ? this.storageControllerContainer
                                                                                                .getStorageController()
                                                                                                .getMaxSlots() : 0;
        String storageSpaceText = I18n.format(TRANSLATION_KEY_BASE + ".space_info_label", this.usedSlots, maxSlots);
        int storageSpaceLabelWidth = this.mc.fontRenderer.getStringWidth(storageSpaceText);

        int storageSpaceInfoLabelLeft = 110 + ORDER_AREA_OFFSET;
        int storageSpaceInfoLabelTop = 115;
        this.storageSpaceInfoLabel = new GuiLabelNoShadow(this.mc.fontRenderer, 0,
                this.guiLeft + storageSpaceInfoLabelLeft, this.guiTop + storageSpaceInfoLabelTop,
                storageSpaceLabelWidth, this.mc.fontRenderer.FONT_HEIGHT, 0x404040); //light gray text
        this.storageSpaceInfoLabel.addLineTranslated(storageSpaceText);
        this.labelList.add(this.storageSpaceInfoLabel);

        int controlButtonSize = 12;

        int clearRecipeButtonLeft = 93 + ORDER_AREA_OFFSET;
        int clearRecipeButtonTop = 112;

        this.clearRecipeButton = new GuiButtonSizedImage(1, this.guiLeft + clearRecipeButtonLeft,
                this.guiTop + clearRecipeButtonTop, controlButtonSize, controlButtonSize, 0, 196, 28, 28, 28, 256, 256,
                BUTTONS);
        this.addButton(this.clearRecipeButton);

        int controlButtonTop = 5;

        int clearTextButtonLeft = 99 + ORDER_AREA_OFFSET;
        this.clearTextButton = new GuiButtonSizedImage(0, this.guiLeft + clearTextButtonLeft,
                this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, 196, 28, 28, 28, 256, 256,
                BUTTONS);
        this.addButton(this.clearTextButton);


        int sortTypeOffset = this.getSortType().getValue() * 28;
        this.sortTypeButton = new GuiButtonSizedImage(2, this.guiLeft + clearTextButtonLeft + controlButtonSize + 3,
                this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, sortTypeOffset, 28, 28, 28,
                256, 256, BUTTONS);
        this.addButton(this.sortTypeButton);

        int sortDirectionOffset = 84 + (1 - this.getSortDirection().getValue()) * 28;
        this.sortDirectionButton = new GuiButtonSizedImage(3,
                this.guiLeft + clearTextButtonLeft + controlButtonSize + 3 + controlButtonSize + 3,
                this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, sortDirectionOffset, 28, 28,
                28, 256, 256, BUTTONS);
        this.addButton(this.sortDirectionButton);

        int jeiSyncOffset = 140 + (JeiPlugin.isJeiSearchSynced() ? 0 : 1) * 28;
        this.jeiSyncButton = new GuiButtonSizedImage(4,
                this.guiLeft + clearTextButtonLeft + controlButtonSize + 3 + controlButtonSize + 3 + controlButtonSize +
                3, this.guiTop + controlButtonTop, controlButtonSize, controlButtonSize, 0, jeiSyncOffset, 28, 28, 28,
                256, 256, BUTTONS);

        if (JeiPlugin.isJeiLoaded())
            this.addButton(this.jeiSyncButton);


        int guiModeButtonTop = 112;
        int guiModeButtonLeft = 27;
        int guiModeButtonHeight = 29;
        int guiModeButtonWidth = 24;

        switch (this.guiMode) {
            case INVENTORY:
                //active tab button for inventory
                this.inventoryModeButton = new GuiButtonSizedImage(5, this.guiLeft + guiModeButtonLeft,
                        this.guiTop + 112, guiModeButtonWidth, guiModeButtonHeight, 160, 0, 0, guiModeButtonWidth * 2,
                        guiModeButtonHeight * 2, 256, 256, BUTTONS);
                //inactive tab button for crafting
                this.autocraftingModeButton = new GuiButtonSizedImage(6, this.guiLeft + guiModeButtonLeft,
                        this.guiTop + guiModeButtonTop + guiModeButtonHeight, guiModeButtonWidth, guiModeButtonHeight,
                        160, 174, 0, guiModeButtonWidth * 2, guiModeButtonHeight * 2, 256, 256, BUTTONS);
                break;
            case AUTOCRAFTING:
                //inactive tab button for inventory
                this.inventoryModeButton = new GuiButtonSizedImage(5, this.guiLeft + guiModeButtonLeft,
                        this.guiTop + 112, guiModeButtonWidth, guiModeButtonHeight, 160, 58, 0, guiModeButtonWidth * 2,
                        guiModeButtonHeight * 2, 256, 256, BUTTONS);
                //active tab button for crafting
                this.autocraftingModeButton = new GuiButtonSizedImage(6, this.guiLeft + guiModeButtonLeft,
                        this.guiTop + guiModeButtonTop + guiModeButtonHeight, guiModeButtonWidth, guiModeButtonHeight,
                        160, 116, 0, guiModeButtonWidth * 2, guiModeButtonHeight * 2, 256, 256, BUTTONS);
                break;
        }
        this.addButton(this.inventoryModeButton);
        this.addButton(this.autocraftingModeButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
        if (!this.isGuiValid()) {
            this.mc.player.closeScreen();
            return;
        }
        try {
            this.drawTooltips(mouseX, mouseY);
        } catch (Throwable e) {
            Occultism.logger.error("Error drawing tooltip.", e);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (!this.isGuiValid()) {
            return;
        }
        this.drawDefaultBackground(); //Draw vanilla backdrop
        this.drawBackgroundTexture();

        switch (this.guiMode) {
            case INVENTORY:
                this.drawItems(partialTicks, mouseX, mouseY);
                break;
            case AUTOCRAFTING:
                this.drawMachines(partialTicks, mouseX, mouseY);
                break;
        }
        this.searchBar.drawTextBox();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.searchBar.setFocused(false);
        int clearButtonX = 90;
        int clearButtonY = 110;

        //right mouse button clears search bar
        if (this.isPointInSearchbar(mouseX, mouseY)) {
            this.searchBar.setFocused(true);

            if (mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                this.clearSearch();
            }
        }
        else if (this.guiMode == StorageControllerGuiMode.INVENTORY) {
            ItemStack stackCarriedByMouse = this.mc.player.inventory.getItemStack();
            if (mouseButton == InputUtil.MOUSE_BUTTON_MIDDLE && !stackCarriedByMouse.isEmpty()) {
                //ignore middle clicks while dragging an item
                return;
            }

            if (!this.stackUnderMouse.isEmpty() &&
                (mouseButton == InputUtil.MOUSE_BUTTON_LEFT || mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) &&
                stackCarriedByMouse.isEmpty() && this.canClick()) {
                //take item out of storage
                Occultism.network.sendToServer(
                        new MessageTakeItem(this.stackUnderMouse, mouseButton, isShiftKeyDown(), isCtrlKeyDown()));
                this.lastClick = System.currentTimeMillis();
            }
            else if (!stackCarriedByMouse.isEmpty() && this.isPointInItemArea(mouseX, mouseY) && this.canClick()) {
                //put item into storage
                Occultism.network.sendToServer(new MessageInsertMouseHeldItem(mouseButton));
                this.lastClick = System.currentTimeMillis();
            }
        }
        else if (this.guiMode == StorageControllerGuiMode.AUTOCRAFTING) {
            for (GuiMachineSlot slot : this.machineSlots) {
                if (slot.isMouseOverSlot(mouseX, mouseY)) {
                    if (mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                        ItemStack orderStack = this.storageControllerContainer.getOrderSlot().getStackInSlot(0);
                        if (isShiftKeyDown()) {
                            long time = System.currentTimeMillis() + 5000;
                            Occultism.proxy.getClientData().selectBlock(slot.getMachine().globalPos.getPos(), time);
                        }
                        else if (!orderStack.isEmpty()) {
                            //this message both clears the order slot and creates the order
                            Occultism.network.sendToServer(new MessageRequestOrder(GlobalBlockPos.fromTileEntity(
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
    }

    @Override
    public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        return super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!this.checkHotbarKeys(keyCode)) {
            Keyboard.enableRepeatEvents(true);
            if (this.searchBar.isFocused() && this.searchBar.textboxKeyTyped(typedChar, keyCode)) {
                Occultism.network.sendToServer(new MessageRequestStacks());
                if (JeiPlugin.isJeiLoaded() && JeiPlugin.isJeiSearchSynced()) {
                    JeiPlugin.setFilterText(this.searchBar.getText());
                }
            }
            else if (!this.stackUnderMouse.isEmpty()) {
                //                    //TODO: check keybinds with jey
                //                    //JeiPlugin.testJeiKeybind(keyCode, this.stackUnderMouse);
            }
            else {
                super.keyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.searchBar != null) {
            this.searchBar.updateCursorCounter();
        }
    }

    @Override
    public void renderToolTip(ItemStack stack, int x, int y) {
        super.renderToolTip(stack, x, y);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button == null) {
            return;
        }

        boolean sort = true;

        switch (button.id) {
            case 0:
                sort = false;
                this.clearSearch();
                this.forceFocus = true; //force focus
                break;
            case 1:
                Occultism.network.sendToServer(new MessageClearCraftingMatrix());
                Occultism.network.sendToServer(new MessageRequestStacks());
                break;
            case 2:
                this.setSortType(this.getSortType().next());
                break;
            case 3:
                this.setSortDirection(this.getSortDirection().next());
                break;
            case 4:
                sort = false;
                JeiPlugin.setJeiSearchSync(!JeiPlugin.isJeiSearchSynced());
                break;
            case 5:
                this.guiMode = StorageControllerGuiMode.INVENTORY;
                break;
            case 6:
                this.guiMode = StorageControllerGuiMode.AUTOCRAFTING;
                break;
        }

        if (sort)
            Occultism.network.sendToServer(
                    new MessageSortItems(this.getEntityPosition(), this.getSortDirection(), this.getSortType()));
        this.initGui();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        //check if mouse is over item area, then handle scrolling
        int i = Mouse.getX() * this.width / this.mc.displayWidth;
        int j = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
        if (this.isPointInItemArea(i, j)) {
            int mouse = Mouse.getEventDWheel();
            if (mouse > 0 && this.currentPage > 1) {
                this.currentPage--;
            }
            if (mouse < 0 && this.currentPage < this.totalPages) {
                this.currentPage++;
            }
        }
    }

    @Override
    public void onInventoryChanged(IInventory inventory) {
        if (inventory == this.storageControllerContainer.getOrderSlot() && !inventory.getStackInSlot(0).isEmpty()) {
            this.guiMode = StorageControllerGuiMode.AUTOCRAFTING;
            this.initGui();
        }
    }
    //endregion Overrides

    //region Methods
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

    protected boolean isPointInSearchbar(int mouseX, int mouseY) {
        return this.isPointInRegion(this.searchBar.x - this.guiLeft + 14, this.searchBar.y - this.guiTop,
                this.searchBar.width, this.fontRenderer.FONT_HEIGHT + 6, mouseX, mouseY);
    }

    protected boolean isPointInItemArea(int mouseX, int mouseY) {
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
            if (!isShiftKeyDown()) {
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
            this.drawHoveringText(tooltip, mouseX, mouseY);
        }
        if (this.clearTextButton != null && this.clearTextButton.isMouseOver()) {
            this.drawHoveringText(Lists.newArrayList(I18n.format(TRANSLATION_KEY_BASE + ".search.tooltip_clear")),
                    mouseX, mouseY);
        }
        if (this.sortTypeButton != null && this.sortTypeButton.isMouseOver()) {
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
            this.drawHoveringText(Lists.newArrayList(I18n.format(translationKey)), mouseX, mouseY);
        }
        if (this.sortDirectionButton != null && this.sortDirectionButton.isMouseOver()) {
            this.drawHoveringText(Lists.newArrayList(I18n.format(
                    TRANSLATION_KEY_BASE + ".search.tooltip_sort_direction_" + this.getSortDirection().getName())),
                    mouseX, mouseY);
        }
        if (this.jeiSyncButton != null && this.jeiSyncButton.isMouseOver()) {
            String s = I18n.format(
                    TRANSLATION_KEY_BASE + ".search.tooltip_jei_" + (JeiPlugin.isJeiSearchSynced() ? "on" : "off"));
            this.drawHoveringText(Lists.newArrayList(s), mouseX, mouseY);
        }
    }

    protected void drawBackgroundTexture() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
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
        if (this.itemSlots.isEmpty()) {
            this.stackUnderMouse = ItemStack.EMPTY;
        }
    }

    protected void buildItemSlots(List<ItemStack> stacksToDisplay) {

        int itemAreaLeft = 8 + ORDER_AREA_OFFSET;
        int itemAreaTop = 24;

        this.itemSlots = new ArrayList<>();
        int index = (this.currentPage - 1) * (this.columns);
        for (int row = 0; row < this.rows; row++) {
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
            int direction = GuiStorageControllerBase.this.getSortDirection().isDown() ? -1 : 1;
            //endregion Fields

            //region Overrides
            @Override
            public int compare(ItemStack a, ItemStack b) {
                switch (GuiStorageControllerBase.this.getSortType()) {
                    case AMOUNT:
                        return Integer.compare(b.getCount(), a.getCount()) * this.direction;
                    case NAME:
                        return a.getDisplayName().compareToIgnoreCase(b.getDisplayName()) * this.direction;
                    case MOD:
                        return ModNameUtil.getModNameForGameObject(a.getItem())
                                       .compareToIgnoreCase(ModNameUtil.getModNameForGameObject(b.getItem())) *
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
            String name = ModNameUtil.getModNameForGameObject(stack.getItem());
            return name.toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else if (searchText.startsWith("#")) {
            String tooltipString;
            List<String> tooltip = stack.getTooltip(this.mc.player, ITooltipFlag.TooltipFlags.NORMAL);
            tooltipString = Joiner.on(' ').join(tooltip).toLowerCase();
            tooltipString = ChatFormatting.stripFormatting(tooltipString);
            return tooltipString.toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else if (searchText.startsWith("$")) {
            StringBuilder oreDictStringBuilder = new StringBuilder();
            for (int oreId : OreDictionary.getOreIDs(stack)) {
                String oreName = OreDictionary.getOreName(oreId);
                oreDictStringBuilder.append(oreName).append(' ');
            }
            return oreDictStringBuilder.toString().toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else {
            return stack.getDisplayName().toLowerCase().contains(searchText.toLowerCase());
        }
    }

    protected boolean machineMatchesSearch(MachineReference machine) {
        String searchText = this.searchBar.getText();
        if (searchText.startsWith("@")) {
            String name = ModNameUtil.getModNameForGameObject(machine.getItem());
            return name.toLowerCase().contains(searchText.toLowerCase().substring(1));
        }
        else {
            String customName = machine.customName == null ? "" : machine.customName.toLowerCase();
            return machine.getItemStack().getDisplayName().toLowerCase().contains(searchText.toLowerCase()) ||
                   customName.contains(searchText.toLowerCase().substring(1));
        }
    }

    protected void sortMachines(List<MachineReference> machinesToDisplay) {
        BlockPos entityPosition = this.getEntityPosition();
        int dimension = Minecraft.getMinecraft().player.dimension;
        machinesToDisplay.sort(new Comparator<MachineReference>() {

            //region Fields
            int direction = GuiStorageControllerBase.this.getSortDirection().isDown() ? -1 : 1;
            //endregion Fields

            //region Overrides
            @Override
            public int compare(MachineReference a, MachineReference b) {
                switch (GuiStorageControllerBase.this.getSortType()) {
                    case AMOUNT: //use distance in this case
                        double distanceA = a.globalPos.getDimension() == dimension ? a.globalPos.getPos().distanceSq(
                                entityPosition) : Double.MAX_VALUE;
                        double distanceB = b.globalPos.getDimension() == dimension ? b.globalPos.getPos().distanceSq(
                                entityPosition) : Double.MAX_VALUE;
                        return Double.compare(distanceB, distanceA) * this.direction;
                    case NAME:
                        return a.getItemStack().getDisplayName()
                                       .compareToIgnoreCase(b.getItemStack().getDisplayName()) * this.direction;
                    case MOD:
                        return ModNameUtil.getModNameForGameObject(a.getItem())
                                       .compareToIgnoreCase(ModNameUtil.getModNameForGameObject(b.getItem())) *
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
