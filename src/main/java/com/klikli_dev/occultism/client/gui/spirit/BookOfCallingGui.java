/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.api.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.widget.BookOfCallingSelectionWidget;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetItemMode;
import com.klikli_dev.occultism.network.messages.MessageSetWorkAreaSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookOfCallingGui extends BookOfCallingScreenBase {
    private static final int GUI_HEIGHT = 86;
    private static final int MODE_ROW_Y = 22;
    private static final int WORK_AREA_ROW_Y = 54;
    private static final int DIVIDER_Y = 47;
    private static final int DIVIDER_HEIGHT = 38;
    private static final int CONFIRM_BUTTON_Y = 54;

    private final InteractionHand hand;
    private final ItemMode initialMode;
    private final WorkAreaSize initialWorkAreaSize;
    private List<ItemMode> modeOptions = List.of();
    private final List<WorkAreaSize> workAreaOptions = Arrays.asList(WorkAreaSize.values());
    private int selectedModeIndex;
    private int selectedWorkAreaIndex;
    private BookOfCallingSelectionWidget<ItemMode> modeSelectionWidget;
    private BookOfCallingSelectionWidget<WorkAreaSize> workAreaSelectionWidget;

    public BookOfCallingGui(ItemMode mode, WorkAreaSize workAreaSize, InteractionHand hand) {
        super(resolveTitle(hand), GUI_HEIGHT);
        this.hand = hand;
        this.initialMode = mode;
        this.initialWorkAreaSize = workAreaSize;
        this.selectedWorkAreaIndex = workAreaSize.ordinal();
    }

    @Override
    public LayoutSpec layoutSpec() {
        return BookOfCallingLayouts.standard(this.imageHeight());
    }

    @Override
    protected void registerContentResolvers(LayoutResolverRegistry registry) {
        this.modeOptions = this.availableModes();
        this.selectedModeIndex = this.indexOfMode(this.initialMode);

        registry.resolve("mode.label", ctx -> this.addLabel(ctx, Component.translatable("gui." + Occultism.MODID + ".book_of_calling.mode")));
        registry.resolve("mode.selection", ctx -> this.modeSelectionWidget = this.addRootChild(new BookOfCallingSelectionWidget<>(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                () -> this.modeOptions,
                () -> this.selectedModeIndex,
                this::changeModeSelection,
                mode -> Component.translatable(mode.translationKey()),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.unavailable"),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.scroll_to_select")
        ).withTitle(Component.translatable("gui." + Occultism.MODID + ".book_of_calling.mode"))));

        registry.resolve("divider_horizontal", ctx -> this.addRootChild(new HorizontalSeparatorWidget(
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

        registry.resolve("work_area.label", ctx -> this.addLabel(ctx, Component.translatable("gui." + Occultism.MODID + ".book_of_calling.work_area")));
        registry.resolve("work_area.selection", ctx -> this.workAreaSelectionWidget = this.addRootChild(new BookOfCallingSelectionWidget<>(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                this::availableWorkAreas,
                () -> this.selectedWorkAreaIndex,
                this::changeWorkAreaSelection,
                size -> Component.translatable(size.getDescriptionId()),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.work_area.not_applicable"),
                Component.translatable("gui." + Occultism.MODID + ".book_of_calling.scroll_to_select")
        ).withTitle(Component.translatable("gui." + Occultism.MODID + ".book_of_calling.work_area"))));

        registry.resolve("confirm_button", ctx -> this.addConfirmButton(ctx));
    }

    @Override
    protected void refreshWidgetState() {
        if (this.workAreaSelectionWidget != null) {
            this.workAreaSelectionWidget.active = this.selectedMode().hasSize();
            this.workAreaSelectionWidget.updateTooltip();
        }
        if (this.modeSelectionWidget != null) {
            this.modeSelectionWidget.updateTooltip();
        }
    }

    @Override
    protected void applyChanges() {
        BookOfCallingItem book = this.currentBook();
        if (book == null) {
            return;
        }

        ItemMode selectedMode = this.selectedMode();
        if (this.indexOfMode(this.initialMode) != this.selectedModeIndex) {
            Networking.sendToServer(new MessageSetItemMode(book.modeValue(selectedMode), this.hand));
        }

        WorkAreaSize selectedWorkArea = this.selectedWorkArea();
        if (selectedMode.hasSize() && selectedWorkArea != this.initialWorkAreaSize) {
            Networking.sendToServer(new MessageSetWorkAreaSize(selectedWorkArea, this.hand));
        }
    }

    private void changeModeSelection(int nextIndex) {
        if (this.modeOptions.isEmpty()) {
            return;
        }

        this.selectedModeIndex = Math.max(0, Math.min(nextIndex, this.modeOptions.size() - 1));
        this.refreshWidgetState();
    }

    private void changeWorkAreaSelection(int nextIndex) {
        List<WorkAreaSize> options = this.availableWorkAreas();
        if (options.isEmpty()) {
            return;
        }

        this.selectedWorkAreaIndex = Math.max(0, Math.min(nextIndex, options.size() - 1));
        if (this.workAreaSelectionWidget != null) {
            this.workAreaSelectionWidget.updateTooltip();
        }
    }

    private List<ItemMode> availableModes() {
        BookOfCallingItem book = this.currentBook();
        return book == null ? List.of(this.initialMode) : book.getItemModes();
    }

    private List<WorkAreaSize> availableWorkAreas() {
        return this.selectedMode().hasSize() ? this.workAreaOptions : Collections.emptyList();
    }

    private ItemMode selectedMode() {
        if (this.modeOptions.isEmpty()) {
            return this.initialMode;
        }

        return this.modeOptions.get(Math.max(0, Math.min(this.selectedModeIndex, this.modeOptions.size() - 1)));
    }

    private WorkAreaSize selectedWorkArea() {
        return this.workAreaOptions.get(Math.max(0, Math.min(this.selectedWorkAreaIndex, this.workAreaOptions.size() - 1)));
    }

    private int indexOfMode(ItemMode mode) {
        int index = this.modeOptions.indexOf(mode);
        return index >= 0 ? index : 0;
    }

    private BookOfCallingItem currentBook() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return null;
        }

        ItemStack stack = player.getItemInHand(this.hand);
        return stack.getItem() instanceof BookOfCallingItem book ? book : null;
    }

    private static Component resolveTitle(InteractionHand hand) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return Component.translatable("item." + Occultism.MODID + ".book_of_calling");
        }

        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {
            return stack.getHoverName();
        }

        return Component.translatable("item." + Occultism.MODID + ".book_of_calling");
    }
}
