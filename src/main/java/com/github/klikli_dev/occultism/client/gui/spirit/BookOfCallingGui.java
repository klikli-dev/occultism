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
import com.github.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.github.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.github.klikli_dev.occultism.network.MessageSetItemMode;
import com.github.klikli_dev.occultism.network.MessageSetWorkAreaSize;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.widget.ExtendedButton;

import java.awt.*;

public class BookOfCallingGui extends Screen {

    //region Fields
    public BookOfCallingItem.IItemModeSubset<?> mode;
    public WorkAreaSize workAreaSize;
    //endregion Fields

    //region Initialization
    public BookOfCallingGui(BookOfCallingItem.IItemModeSubset<?> mode, WorkAreaSize workAreaSize) {
        super(new TextComponent(""));

        this.mode = mode;
        this.workAreaSize = workAreaSize;
        this.init();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
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
                guiTop + 66, false, -1, 2, Color.WHITE.getRGB()).alignRight(true);
        modeLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.mode", true);
        this.addRenderableWidget(modeLabel);

        //Item mode button
        this.addRenderableWidget((new ExtendedButton(guiLeft - buttonWidth / 2, guiTop + 60, buttonWidth, 20,
                new TranslatableComponent(this.mode.getItemMode().getDescriptionId()), (b) -> {
            this.mode = this.mode.next();
            OccultismPackets.sendToServer(new MessageSetItemMode(this.mode.getItemMode().getValue()));
            this.init();
        })));

        boolean showSize = this.mode.getItemMode() == BookOfCallingItem.ItemMode.SET_BASE;
        if (showSize) {
            LabelWidget workAreaLabel = new LabelWidget(
                    guiLeft - 80, guiTop + 91, true, -1, 2, Color.WHITE.getRGB()).alignRight(true);
            workAreaLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.work_area", true);
            this.addRenderableWidget(workAreaLabel);

            //Work area size button
            this.addRenderableWidget(new ExtendedButton(guiLeft - buttonWidth / 2, guiTop + 85, buttonWidth, 20,
                    new TranslatableComponent(this.workAreaSize.getDescriptionId()), (b) -> {
                this.workAreaSize = this.workAreaSize.next();
                OccultismPackets.sendToServer(new MessageSetWorkAreaSize(this.workAreaSize.getValue()));
                this.init();
            }));

        }

        //Exit button
        int exitButtonWidth = 20;
        this.addRenderableWidget(
                new ExtendedButton(guiLeft - exitButtonWidth / 2, guiTop + (showSize ? 110 : 85), exitButtonWidth, 20,
                        new TextComponent("X"), (b) -> {
                    this.minecraft.setScreen(null);
                    this.init();
                }));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    //endregion Overrides
}
