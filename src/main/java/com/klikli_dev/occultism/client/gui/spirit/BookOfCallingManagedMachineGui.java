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

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.network.MessageSetManagedMachine;
import com.klikli_dev.occultism.network.OccultismPackets;
import com.klikli_dev.occultism.util.EnumUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.StringUtils;

public class BookOfCallingManagedMachineGui extends Screen {

    protected final String originalCustomName;
    protected String customName;
    protected Direction insertFacing = Direction.UP;
    protected Direction extractFacing = Direction.DOWN;

    protected EditBox text;

    public BookOfCallingManagedMachineGui(Direction insertFacing, Direction extractFacing, String customName) {
        super(Component.literal(""));

        this.insertFacing = insertFacing;
        this.extractFacing = extractFacing;
        this.originalCustomName = this.customName = customName == null ? "" : customName;

        this.init();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
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
        this.text.setFocused(false);
        if (!StringUtils.isBlank(this.customName) && !this.customName.equals(this.originalCustomName)) {
            OccultismPackets.sendToServer(new MessageSetManagedMachine(this.makeMachineReference()));
        }
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();
        int guiLeft = (this.width) / 2;
        int guiTop = (this.height - 166) / 2;
        int buttonWidth = 150;
        int buttonMargin = 5;
        int buttonHeight = 20;

        int buttonTop = 60;
        //the insert facing button
        this.addRenderableWidget(new ExtendedButton(guiLeft - buttonWidth / 2, guiTop + buttonTop, buttonWidth,
                buttonHeight, Component.translatable("enum." + Occultism.MODID + ".facing." + this.insertFacing.getSerializedName()),
                (b) -> {
                    MachineReference reference = this.makeMachineReference();
                    this.insertFacing = reference.insertFacing = EnumUtil.nextFacing(this.insertFacing);
                    OccultismPackets.sendToServer(new MessageSetManagedMachine(reference));
                    this.init();
                }));

        //the extract facing button
        this.addRenderableWidget(new ExtendedButton(guiLeft - buttonWidth / 2,
                guiTop + buttonTop + buttonHeight + buttonMargin, buttonWidth, buttonHeight,
                Component.translatable("enum." + Occultism.MODID + ".facing." + this.extractFacing.getSerializedName()), (b) -> {
            MachineReference reference = this.makeMachineReference();
            this.extractFacing = reference.extractFacing = EnumUtil.nextFacing(this.extractFacing);
            OccultismPackets.sendToServer(new MessageSetManagedMachine(reference));
            this.init();
        }));

        int textWidth = buttonWidth - 4;
        this.text = new EditBox(this.font, guiLeft - textWidth / 2,
                guiTop + buttonTop + buttonHeight * 2 + buttonMargin * 2, textWidth, buttonHeight, Component.literal(""));
        this.text.setMaxLength(30);
        this.text.setVisible(true);
        this.text.setTextColor(OccultismConstants.Color.WHITE);
        this.text.setFocused(true);

        this.text.setValue(this.customName);

        //Exit button
        int exitButtonSize = 20;
        this.addRenderableWidget(new ExtendedButton(guiLeft - exitButtonSize / 2,
                guiTop + buttonTop + buttonHeight * 3 + buttonMargin * 3, exitButtonSize, exitButtonSize, Component.literal("X"), (b) -> {
            this.onClose();
        }));

        buttonTop += 5;
        LabelWidget insertFacingLabel = new LabelWidget(guiLeft - 80, guiTop + buttonTop, false, -1, 2,
                OccultismConstants.Color.WHITE).alignRight(true);
        insertFacingLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert", true);
        this.addRenderableWidget(insertFacingLabel);

        LabelWidget extractFacingLabel = new LabelWidget(guiLeft - 80, guiTop + buttonTop + buttonHeight + buttonMargin,
                false, -1, 2, OccultismConstants.Color.WHITE).alignRight(true);
        extractFacingLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract", true);
        this.addRenderableWidget(extractFacingLabel);

        LabelWidget customNameLabel = new LabelWidget(guiLeft - 80,
                guiTop + buttonTop + buttonHeight * 2 + buttonMargin * 2 + 1, false, -1, 2, OccultismConstants.Color.WHITE).alignRight(true);
        customNameLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.manage_machine.custom_name", true);
        this.addRenderableWidget(customNameLabel);
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
        if (this.text.keyPressed(keyCode, scanCode, p_keyPressed_3_))
            return true;
        return super.keyPressed(keyCode, scanCode, p_keyPressed_3_);
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        if (this.text.charTyped(typedChar, keyCode)) {
            this.customName = this.text.getValue();
            return true;
        } else {
            return super.charTyped(typedChar, keyCode);
        }
    }

    public MachineReference makeMachineReference() {
        MachineReference reference = new MachineReference(null, null, false, null, null, false);
        reference.insertFacing = this.insertFacing;
        reference.extractFacing = this.extractFacing;
        reference.customName = this.customName;
        return reference;
    }

}
