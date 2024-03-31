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
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetItemMode;
import com.klikli_dev.occultism.network.messages.MessageSetWorkAreaSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;

public class BookOfCallingGui extends Screen {

    public ItemMode mode;
    public WorkAreaSize workAreaSize;

    public BookOfCallingGui(ItemMode mode, WorkAreaSize workAreaSize) {
        super(Component.literal(""));

        this.mode = mode;
        this.workAreaSize = workAreaSize;
        this.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        this.renderBackground(guiGraphics); //called by super
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();
        int guiLeft = (this.width) / 2;
        int guiTop = (this.height - 166) / 2;
        int buttonWidth = 150;


        LabelWidget modeLabel = new LabelWidget(guiLeft - 80,
                guiTop + 66, false, -1, 2, OccultismConstants.Color.WHITE).alignRight(true);
        modeLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.mode", true);
        this.addRenderableWidget(modeLabel);

        //Item mode button
        this.addRenderableWidget((new ExtendedButton(guiLeft - buttonWidth / 2, guiTop + 60, buttonWidth, 20,
                Component.translatable(this.mode.translationKey()), (b) -> {
            LocalPlayer player = Minecraft.getInstance().player;
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());

            //go to the next mode, if the selected item somehow changed keep the old mode.
            this.mode = stack.getItem() instanceof BookOfCallingItem bookOfCallingItem ?
                    bookOfCallingItem.nextItemMode(stack) : this.mode;

            Networking.sendToServer(new MessageSetItemMode(((BookOfCallingItem) stack.getItem()).modeValue(this.mode)));
            this.init();
        })));

        boolean showSize = this.mode.hasSize();
        if (showSize) {
            LabelWidget workAreaLabel = new LabelWidget(
                    guiLeft - 80, guiTop + 91, true, -1, 2, OccultismConstants.Color.WHITE).alignRight(true);
            workAreaLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.work_area", true);
            this.addRenderableWidget(workAreaLabel);

            //Work area size button
            this.addRenderableWidget(new ExtendedButton(guiLeft - buttonWidth / 2, guiTop + 85, buttonWidth, 20,
                    Component.translatable(this.workAreaSize.getDescriptionId()), (b) -> {
                this.workAreaSize = this.workAreaSize.next();
                Networking.sendToServer(new MessageSetWorkAreaSize(this.workAreaSize.getValue()));
                this.init();
            }));

        }

        //Exit button
        int exitButtonWidth = 20;
        this.addRenderableWidget(
                new ExtendedButton(guiLeft - exitButtonWidth / 2, guiTop + (showSize ? 110 : 85), exitButtonWidth, 20,
                        Component.literal("X"), (b) -> {
                    this.minecraft.setScreen(null);
                    this.init();
                }));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
