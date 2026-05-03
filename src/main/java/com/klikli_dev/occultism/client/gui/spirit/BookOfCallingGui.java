/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetItemMode;
import com.klikli_dev.occultism.network.messages.MessageSetWorkAreaSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;

public class BookOfCallingGui extends BookOfCallingScreenBase {
    public ItemMode mode;
    public WorkAreaSize workAreaSize;

    public BookOfCallingGui(ItemMode mode, WorkAreaSize workAreaSize) {
        this.mode = mode;
        this.workAreaSize = workAreaSize;
    }

    @Override
    protected void initContents() {
        this.addLabelRow(CONTENT_TOP + 7, "gui." + Occultism.MODID + ".book_of_calling.mode");
        this.addRenderableWidget(new ExtendedButton(this.guiX(BUTTON_LEFT), this.guiY(CONTENT_TOP), BUTTON_WIDTH,
                BUTTON_HEIGHT, Component.translatable(this.mode.translationKey()), (button) -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof BookOfCallingItem bookOfCallingItem) {
                this.mode = bookOfCallingItem.nextItemMode(stack);
                Networking.sendToServer(new MessageSetItemMode(bookOfCallingItem.modeValue(this.mode)));
                this.init();
            }
        }));

        boolean showSize = this.mode.hasSize();
        if (showSize) {
            int sizeTop = CONTENT_TOP + ROW_HEIGHT;
            this.addLabelRow(sizeTop + 7, "gui." + Occultism.MODID + ".book_of_calling.work_area");
            this.addRenderableWidget(new ExtendedButton(this.guiX(BUTTON_LEFT), this.guiY(sizeTop), BUTTON_WIDTH,
                    BUTTON_HEIGHT, Component.translatable(this.workAreaSize.getDescriptionId()), (button) -> {
                this.workAreaSize = this.workAreaSize.next();
                Networking.sendToServer(new MessageSetWorkAreaSize(this.workAreaSize));
                this.init();
            }));
        }

        int exitTop = showSize ? CONTENT_TOP + ROW_HEIGHT * 2 + 6 : CONTENT_TOP + ROW_HEIGHT + 6;
        this.addRenderableWidget(new ExtendedButton(this.guiX((this.imageWidth() - EXIT_BUTTON_SIZE) / 2),
                this.guiY(exitTop), EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE, Component.literal("X"),
                (button) -> this.onClose()));
    }

    @Override
    protected Component title() {
        return Component.translatable("item." + Occultism.MODID + ".book_of_calling");
    }
}
