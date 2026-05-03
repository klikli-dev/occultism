/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetManagedMachine;
import com.klikli_dev.occultism.util.EnumUtil;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.StringUtils;

public class BookOfCallingManagedMachineGui extends BookOfCallingScreenBase {
    protected final String originalCustomName;
    protected String customName;
    protected Direction insertFacing;
    protected Direction extractFacing;

    protected EditBox text;

    public BookOfCallingManagedMachineGui(Direction insertFacing, Direction extractFacing, String customName) {
        this.insertFacing = insertFacing;
        this.extractFacing = extractFacing;
        this.originalCustomName = this.customName = customName == null ? "" : customName;
    }

    @Override
    public void onClose() {
        super.onClose();
        if (this.text != null) {
            this.customName = this.text.getValue();
            this.text.setFocused(false);
        }

        if (!StringUtils.isBlank(this.customName) && !this.customName.equals(this.originalCustomName)) {
            Networking.sendToServer(new MessageSetManagedMachine(this.makeMachineReference()));
        }
    }

    @Override
    protected void addBackgroundChildren() {
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(BUTTON_LEFT - 2), this.guiY(CONTENT_TOP + ROW_HEIGHT * 2 - 2),
                BUTTON_WIDTH + 4, BUTTON_HEIGHT + 4, this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_FIELD,
                this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_PANEL, com.klikli_dev.codedefinedgui.gui.texture.GuiSprites.GUI_BACKGROUND))));
    }

    @Override
    protected void initContents() {
        this.addLabelRow(CONTENT_TOP + 7, "gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert");
        this.addRenderableWidget(new ExtendedButton(this.guiX(BUTTON_LEFT), this.guiY(CONTENT_TOP), BUTTON_WIDTH,
                BUTTON_HEIGHT, this.facingLabel(this.insertFacing), (button) -> {
            MachineReference reference = this.makeMachineReference();
            this.insertFacing = reference.insertFacing = EnumUtil.nextFacing(this.insertFacing);
            Networking.sendToServer(new MessageSetManagedMachine(reference));
            this.init();
        }));

        int extractTop = CONTENT_TOP + ROW_HEIGHT;
        this.addLabelRow(extractTop + 7, "gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract");
        this.addRenderableWidget(new ExtendedButton(this.guiX(BUTTON_LEFT), this.guiY(extractTop), BUTTON_WIDTH,
                BUTTON_HEIGHT, this.facingLabel(this.extractFacing), (button) -> {
            MachineReference reference = this.makeMachineReference();
            this.extractFacing = reference.extractFacing = EnumUtil.nextFacing(this.extractFacing);
            Networking.sendToServer(new MessageSetManagedMachine(reference));
            this.init();
        }));

        int textTop = CONTENT_TOP + ROW_HEIGHT * 2;
        this.addLabelRow(textTop + 7, "gui." + Occultism.MODID + ".book_of_calling.manage_machine.custom_name");
        this.text = new EditBox(this.font, this.guiX(BUTTON_LEFT), this.guiY(textTop), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.empty());
        this.text.setMaxLength(30);
        this.text.setBordered(false);
        this.text.setTextColor(0xFFFFFFFF);
        this.text.setFocused(true);
        this.text.setValue(this.customName);
        this.addRenderableWidget(this.text);
        this.setInitialFocus(this.text);

        int exitTop = CONTENT_TOP + ROW_HEIGHT * 3 + 6;
        this.addRenderableWidget(new ExtendedButton(this.guiX((this.imageWidth() - EXIT_BUTTON_SIZE) / 2),
                this.guiY(exitTop), EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE, Component.literal("X"),
                (button) -> this.onClose()));
    }

    private Component facingLabel(Direction direction) {
        return Component.translatable("enum." + Occultism.MODID + ".facing." + direction.getSerializedName());
    }

    @Override
    protected Component title() {
        return Component.translatable("job." + Occultism.MODID + ".manage_machine");
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (this.text != null && this.text.mouseClicked(event, doubleClick)) {
            return true;
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (this.text != null && this.text.keyPressed(event)) {
            this.customName = this.text.getValue();
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (this.text != null && this.text.charTyped(event)) {
            this.customName = this.text.getValue();
            return true;
        }

        return super.charTyped(event);
    }

    public MachineReference makeMachineReference() {
        return new MachineReference((GlobalBlockPos) null, (Identifier) null, false, this.extractFacing, (GlobalBlockPos) null, (Identifier) null,
                false, this.insertFacing, this.customName);
    }
}
