/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.api.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.widget.BookOfCallingSelectionWidget;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetManagedMachine;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BookOfCallingManagedMachineGui extends BookOfCallingScreenBase {
    private static final int GUI_HEIGHT = 112;

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
    public LayoutSpec layoutSpec() {
        return BookOfCallingLayouts.managedMachine(this.imageHeight());
    }

    @Override
    protected void registerContentResolvers(LayoutResolverRegistry registry) {
        registry.resolve("insert.label", ctx -> this.addLabel(ctx, Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert")));
        registry.resolve("insert.selection", ctx -> this.insertSelectionWidget = this.addRootChild(new BookOfCallingSelectionWidget<>(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                () -> this.directions,
                this::selectedInsertIndex,
                this::changeInsertFacing,
                this::facingLabel,
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.unavailable"),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.scroll_to_select")
        ).withTitle(Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.insert"))));

        registry.resolve("divider_horizontal_top", ctx -> this.addRootChild(new HorizontalSeparatorWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.style().color(OccultismGuiParts.BOOK_OF_CALLING_HORIZONTAL_SEPARATOR, 0xFF000000)
        )));
        registry.resolve("extract.label", ctx -> this.addLabel(ctx, Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract")));
        registry.resolve("extract.selection", ctx -> this.extractSelectionWidget = this.addRootChild(new BookOfCallingSelectionWidget<>(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                () -> this.directions,
                this::selectedExtractIndex,
                this::changeExtractFacing,
                this::facingLabel,
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.unavailable"),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.scroll_to_select")
        ).withTitle(Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.extract"))));

        registry.resolve("divider_horizontal_bottom", ctx -> this.addRootChild(new HorizontalSeparatorWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.style().color(OccultismGuiParts.BOOK_OF_CALLING_HORIZONTAL_SEPARATOR, 0xFF000000)
        )));
        registry.resolve("divider_vertical", ctx -> this.addRootChild(new VerticalSeparatorWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().heightOrThrow(),
                ctx.style().color(OccultismGuiParts.BOOK_OF_CALLING_VERTICAL_SEPARATOR, 0xFF000000)
        )));

        registry.resolve("name.label", ctx -> this.addLabel(ctx, Component.translatable("gui." + Occultism.MODID + ".book_of_calling.manage_machine.custom_name")));
        registry.resolve("name.field_background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_FIELD, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("name.field", ctx -> {
            this.text = new EditBox(this.font, ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.node().heightOrThrow(), Component.empty());
            this.text.setMaxLength(30);
            this.text.setBordered(false);
            this.text.setTextColor(0xFFFFFFFF);
            this.text.setFocused(true);
            this.text.setValue(this.customName);
            ctx.addWidget(this.text);
        });

        registry.resolve("confirm_button", ctx -> this.addConfirmButton(ctx));
    }

    @Override
    protected void afterLayoutInit() {
        if (this.text != null) {
            this.setInitialFocus(this.text);
        }
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
