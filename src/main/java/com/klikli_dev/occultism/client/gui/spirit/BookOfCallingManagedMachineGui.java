/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.widget.BookOfCallingSelectionWidget;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetManagedMachine;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;

public class BookOfCallingManagedMachineGui extends BookOfCallingScreenBase {
    private static final int GUI_HEIGHT = 112;
    private static final int INSERT_ROW_Y = 22;
    private static final int EXTRACT_ROW_Y = 54;
    private static final int NAME_ROW_Y = 79;
    private static final int FIRST_DIVIDER_Y = 47;
    private static final int SECOND_DIVIDER_Y = 72;
    private static final int CONFIRM_BUTTON_Y = 85;

    private final InteractionHand hand;
    private final List<Direction> directions = Arrays.asList(Direction.values());
    protected final String originalCustomName;
    protected final Direction originalInsertFacing;
    protected final Direction originalExtractFacing;
    protected String customName;
    protected Direction insertFacing;
    protected Direction extractFacing;

    protected EditBox text;
    private BookOfCallingSelectionWidget<Direction> insertSelectionWidget;
    private BookOfCallingSelectionWidget<Direction> extractSelectionWidget;

    public BookOfCallingManagedMachineGui(Direction insertFacing, Direction extractFacing, String customName, InteractionHand hand) {
        super(Component.translatable("job." + Occultism.MODID + ".manage_machine"), GUI_HEIGHT);
        this.hand = hand;
        this.originalInsertFacing = insertFacing;
        this.originalExtractFacing = extractFacing;
        this.insertFacing = insertFacing;
        this.extractFacing = extractFacing;
        this.originalCustomName = this.customName = customName == null ? "" : customName;
    }

    @Override
    protected void addBackgroundChildren() {
        this.addHorizontalSeparator(FIRST_DIVIDER_Y);
        this.addHorizontalSeparator(SECOND_DIVIDER_Y);
        this.addVerticalSeparator(202, SECOND_DIVIDER_Y, 36);
        this.root.addChild(new GuiBackgroundWidget(this, this.guiX(SELECTION_LEFT - 2), this.guiY(NAME_ROW_Y - 2),
                SELECTION_WIDTH + 4, SELECTION_HEIGHT + 4, this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_FIELD,
                this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_PANEL, com.klikli_dev.codedefinedgui.api.texture.GuiSprites.GUI_BACKGROUND))));
    }

    @Override
    protected void initContents() {
        this.addLabelRow(INSERT_ROW_Y + 5, "gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert");
        this.insertSelectionWidget = this.addRootChild(new BookOfCallingSelectionWidget<>(
                this.guiX(SELECTION_LEFT),
                this.guiY(INSERT_ROW_Y),
                SELECTION_WIDTH,
                SELECTION_HEIGHT,
                this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                () -> this.directions,
                this::selectedInsertIndex,
                this::changeInsertFacing,
                this::facingLabel,
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.unavailable"),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.scroll_to_select")
        ).withTitle(Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert")));

        this.addLabelRow(EXTRACT_ROW_Y + 5, "gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract");
        this.extractSelectionWidget = this.addRootChild(new BookOfCallingSelectionWidget<>(
                this.guiX(SELECTION_LEFT),
                this.guiY(EXTRACT_ROW_Y),
                SELECTION_WIDTH,
                SELECTION_HEIGHT,
                this.partSprite(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                () -> this.directions,
                this::selectedExtractIndex,
                this::changeExtractFacing,
                this::facingLabel,
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.unavailable"),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.scroll_to_select")
        ).withTitle(Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract")));

        this.addLabelRow(NAME_ROW_Y + 5, "gui." + Occultism.MODID + ".book_of_calling.manage_machine.custom_name");
        this.text = new EditBox(this.font, this.guiX(SELECTION_LEFT), this.guiY(NAME_ROW_Y), SELECTION_WIDTH, SELECTION_HEIGHT,
                Component.empty());
        this.text.setMaxLength(30);
        this.text.setBordered(false);
        this.text.setTextColor(0xFFFFFFFF);
        this.text.setFocused(true);
        this.text.setValue(this.customName);
        this.addRenderableWidget(this.text);
        this.setInitialFocus(this.text);

        this.addConfirmButton(CONFIRM_BUTTON_Y);
    }

    @Override
    protected void refreshWidgetState() {
        if (this.insertSelectionWidget != null) {
            this.insertSelectionWidget.updateTooltip();
        }
        if (this.extractSelectionWidget != null) {
            this.extractSelectionWidget.updateTooltip();
        }
    }

    @Override
    protected void applyChanges() {
        if (this.text != null) {
            this.customName = this.text.getValue();
            this.text.setFocused(false);
        }

        if (!Objects.equals(this.originalCustomName, this.customName)
                || this.originalInsertFacing != this.insertFacing
                || this.originalExtractFacing != this.extractFacing) {
            Networking.sendToServer(new MessageSetManagedMachine(this.makeMachineReference(), this.hand));
        }
    }

    private Component facingLabel(Direction direction) {
        return Component.translatable("enum." + Occultism.MODID + ".facing." + direction.getSerializedName());
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

    private int selectedInsertIndex() {
        return Math.max(0, this.directions.indexOf(this.insertFacing));
    }

    private int selectedExtractIndex() {
        return Math.max(0, this.directions.indexOf(this.extractFacing));
    }

    private void changeInsertFacing(int nextIndex) {
        this.insertFacing = this.directions.get(Math.max(0, Math.min(nextIndex, this.directions.size() - 1)));
        if (this.insertSelectionWidget != null) {
            this.insertSelectionWidget.updateTooltip();
        }
    }

    private void changeExtractFacing(int nextIndex) {
        this.extractFacing = this.directions.get(Math.max(0, Math.min(nextIndex, this.directions.size() - 1)));
        if (this.extractSelectionWidget != null) {
            this.extractSelectionWidget.updateTooltip();
        }
    }
}
