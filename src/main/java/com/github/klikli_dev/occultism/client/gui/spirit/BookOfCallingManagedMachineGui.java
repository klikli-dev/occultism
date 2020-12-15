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

package com.github.klikli_dev.occultism.client.gui.spirit;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.github.klikli_dev.occultism.network.MessageSetManagedMachine;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.util.EnumUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Direction;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class BookOfCallingManagedMachineGui extends Screen {

    //region Fields
    protected final String originalCustomName;
    protected String customName;
    protected Direction insertFacing = Direction.UP;
    protected Direction extractFacing = Direction.DOWN;

    protected TextFieldWidget text;
    //endregion Fields

    //region Initialization
    public BookOfCallingManagedMachineGui(Direction insertFacing, Direction extractFacing, String customName) {
        super(new StringTextComponent(""));

        this.insertFacing = insertFacing;
        this.extractFacing = extractFacing;
        this.originalCustomName = this.customName = customName == null ? "" : customName;

        this.init();
    }

    //endregion Initialization

    //region Overrides
    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        this.text.render(stack, mouseX, mouseY, partialTicks);
        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        super.onClose();
        this.text.setFocused2(false);
        if (!StringUtils.isBlank(this.customName) && !this.customName.equals(this.originalCustomName)) {
            OccultismPackets.sendToServer(new MessageSetManagedMachine(this.makeMachineReference()));
        }
    }

    @Override
    public void init() {
        super.init();
        this.buttons.clear();
        int guiLeft = (this.width) / 2;
        int guiTop = (this.height - 166) / 2;
        int buttonWidth = 150;
        int buttonMargin = 5;
        int buttonHeight = 20;

        int buttonTop = 60;
        //the insert facing button
        this.addButton(new ExtendedButton(guiLeft - buttonWidth / 2, guiTop + buttonTop, buttonWidth,
                buttonHeight, new TranslationTextComponent("enum." + Occultism.MODID + ".facing." + this.insertFacing.getName2()),
                (b) -> {
                    MachineReference reference = this.makeMachineReference();
                    this.insertFacing = reference.insertFacing = EnumUtil.nextFacing(this.insertFacing);
                    OccultismPackets.sendToServer(new MessageSetManagedMachine(reference));
                    this.init();
                }));

        //the extract facing button
        this.addButton(new ExtendedButton(guiLeft - buttonWidth / 2,
                guiTop + buttonTop + buttonHeight + buttonMargin, buttonWidth, buttonHeight,
                new TranslationTextComponent("enum." + Occultism.MODID + ".facing." + this.extractFacing.getName2()), (b) -> {
            MachineReference reference = this.makeMachineReference();
            this.extractFacing = reference.extractFacing = EnumUtil.nextFacing(this.extractFacing);
            OccultismPackets.sendToServer(new MessageSetManagedMachine(reference));
            this.init();
        }));

        int textWidth = buttonWidth - 4;
        this.text = new TextFieldWidget(this.font, guiLeft - textWidth / 2,
                guiTop + buttonTop + buttonHeight * 2 + buttonMargin * 2, textWidth, buttonHeight, new StringTextComponent(""));
        this.text.setMaxStringLength(30);
        this.text.setVisible(true);
        this.text.setTextColor(Color.WHITE.getRGB());
        this.text.setFocused2(true);

        this.text.setText(this.customName);

        //Exit button
        int exitButtonSize = 20;
        this.addButton(new ExtendedButton(guiLeft - exitButtonSize / 2,
                guiTop + buttonTop + buttonHeight * 3 + buttonMargin * 3, exitButtonSize, exitButtonSize, new StringTextComponent("X"), (b) -> {
            this.minecraft.displayGuiScreen(null);
        }));

        buttonTop += 5;
        LabelWidget insertFacingLabel = new LabelWidget(guiLeft - 80, guiTop + buttonTop, false, -1, 2,
                Color.WHITE.getRGB()).alignRight(true);
        insertFacingLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert", true);
        this.addButton(insertFacingLabel);

        LabelWidget extractFacingLabel = new LabelWidget(guiLeft - 80, guiTop + buttonTop + buttonHeight + buttonMargin,
                false, -1, 2, Color.WHITE.getRGB()).alignRight(true);
        extractFacingLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract", true);
        this.addButton(extractFacingLabel);

        LabelWidget customNameLabel = new LabelWidget(guiLeft - 80,
                guiTop + buttonTop + buttonHeight * 2 + buttonMargin * 2+ 1, false, -1, 2, Color.WHITE.getRGB()).alignRight(true);
        customNameLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.manage_machine.custom_name", true);
        this.addButton(customNameLabel);
    }

    @Override
    public void tick() {
        this.text.tick();
        super.tick();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (this.text.mouseClicked(mouseX, mouseY, mouseButton))
            return true;
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int p_keyPressed_3_) {
        if(this.text.keyPressed(keyCode, scanCode, p_keyPressed_3_))
            return true;
        return super.keyPressed(keyCode, scanCode, p_keyPressed_3_);
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        if (this.text.charTyped(typedChar, keyCode)) {
            this.customName = this.text.getText();
            return true;
        }
        else {
            return super.charTyped(typedChar, keyCode);
        }
    }
    //endregion Overrides

    //region Methods
    public MachineReference makeMachineReference() {
        MachineReference reference = new MachineReference(null, null, false);
        reference.insertFacing = this.insertFacing;
        reference.extractFacing = this.extractFacing;
        reference.customName = this.customName;
        return reference;
    }
    //endregion Methods
}
