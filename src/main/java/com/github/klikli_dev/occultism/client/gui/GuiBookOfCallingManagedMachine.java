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

package com.github.klikli_dev.occultism.client.gui;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.network.MessageSetManagedMachine;
import com.github.klikli_dev.occultism.util.EnumUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.codehaus.plexus.util.StringUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class GuiBookOfCallingManagedMachine extends GuiScreen {

    //region Fields
    protected final String originalCustomName;
    protected String customName;
    protected EnumFacing insertFacing = EnumFacing.UP;
    protected EnumFacing extractFacing = EnumFacing.DOWN;

    protected GuiTextField text;
    //endregion Fields

    //region Initialization
    public GuiBookOfCallingManagedMachine(EnumFacing insertFacing, EnumFacing extractFacing, String customName) {
        super();
        this.insertFacing = insertFacing;
        this.extractFacing = extractFacing;
        this.originalCustomName = this.customName = customName == null ? "" : customName;

        this.mc = Minecraft.getMinecraft();
        this.initGui();
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.text.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        Keyboard.enableRepeatEvents(true);
        if (this.text.textboxKeyTyped(typedChar, keyCode)) {
            this.customName = this.text.getText();
        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.text.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        MachineReference reference = new MachineReference(null, null, false);
        reference.insertFacing = this.insertFacing;
        reference.extractFacing = this.extractFacing;
        reference.customName = this.customName;

        switch (button.id) {
            case 0: // insert facing button
                this.insertFacing = reference.insertFacing = EnumUtil.nextFacing(this.insertFacing);
                Occultism.network.sendToServer(new MessageSetManagedMachine(reference));
                break;
            case 1: //extract facing button
                this.extractFacing = reference.extractFacing = EnumUtil.nextFacing(this.extractFacing);
                Occultism.network.sendToServer(new MessageSetManagedMachine(reference));
                break;
            case 2: //exit button
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
        }

        this.initGui();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.labelList.clear();
        int guiLeft = (this.width) / 2;
        int guiTop = (this.height - 166) / 2;
        int buttonWidth = 150;
        int buttonMargin = 5;
        int buttonHeight = 20;
        int buttonId = 0;

        int buttonTop = 60;
        //the insert facing button
        this.buttonList.add(new GuiButtonExt(buttonId++, guiLeft - buttonWidth / 2, guiTop + buttonTop, buttonWidth,
                buttonHeight, I18n.format("enum." + Occultism.MODID + ".facing." + this.insertFacing.getName())));

        //the extract facing button
        this.buttonList.add(new GuiButtonExt(buttonId++, guiLeft - buttonWidth / 2,
                guiTop + buttonTop + buttonHeight + buttonMargin, buttonWidth, buttonHeight,
                I18n.format("enum." + Occultism.MODID + ".facing." + this.extractFacing.getName())));

        int textWidth = buttonWidth - 4;
        this.text = new GuiTextField(0, this.mc.fontRenderer, guiLeft - textWidth / 2,
                guiTop + buttonTop + buttonHeight * 2 + buttonMargin * 2, textWidth, buttonHeight);
        this.text.setMaxStringLength(30);
        //this.text.setEnableBackgroundDrawing(false);
        this.text.setVisible(true);
        this.text.setTextColor(Color.WHITE.getRGB());
        this.text.setFocused(true);

        this.text.setText(this.customName);

        int exitButtonSize = 20;
        this.buttonList.add(new GuiButtonExt(buttonId++, guiLeft - exitButtonSize / 2,
                guiTop + buttonTop + buttonHeight * 3 + buttonMargin * 3, exitButtonSize, exitButtonSize, "X"));

        int labelId = 0;

        String insertFacingString = I18n.format("gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert");
        int insertFacingStringWidth = this.mc.fontRenderer.getStringWidth(insertFacingString);
        GuiLabel insertFacingLabel = new GuiLabel(this.mc.fontRenderer, labelId++,
                guiLeft - buttonWidth / 2 - insertFacingStringWidth - 10, guiTop + buttonTop, insertFacingStringWidth,
                20, Color.WHITE.getRGB());
        insertFacingLabel.addLine(insertFacingString);
        this.labelList.add(insertFacingLabel);

        String extractFacingString = I18n.format("gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract");
        int extractFacingStringWidth = this.mc.fontRenderer.getStringWidth(extractFacingString);
        GuiLabel extractFacingLabel = new GuiLabel(this.mc.fontRenderer, labelId++,
                guiLeft - buttonWidth / 2 - extractFacingStringWidth - 10,
                guiTop + buttonTop + buttonHeight + buttonMargin, extractFacingStringWidth, 20, Color.WHITE.getRGB());
        extractFacingLabel.addLine(extractFacingString);
        this.labelList.add(extractFacingLabel);

        String customNameString = I18n.format("gui." + Occultism.MODID + ".book_of_calling.manage_machine.custom_name");
        int customNameStringWidth = this.mc.fontRenderer.getStringWidth(customNameString);
        GuiLabel customNameLabel = new GuiLabel(this.mc.fontRenderer, labelId++,
                guiLeft - buttonWidth / 2 - customNameStringWidth - 10,
                guiTop + buttonTop + buttonHeight * 2 + buttonMargin * 2, customNameStringWidth, 20,
                Color.WHITE.getRGB());
        customNameLabel.addLine(customNameString);
        this.labelList.add(customNameLabel);
    }

    @Override
    public void updateScreen() {
        this.text.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        this.text.setFocused(false);
        //if custom name is not empty or unchanged, send packet
        if (!StringUtils.isBlank(this.customName) && !this.customName.equals(this.originalCustomName)) {
            MachineReference reference = new MachineReference(null, null, false);
            reference.insertFacing = this.insertFacing;
            reference.extractFacing = this.extractFacing;
            reference.customName = this.customName;
            Occultism.network.sendToServer(new MessageSetManagedMachine(reference));
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
    //endregion Overrides

}
