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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import java.awt.*;

public class BookOfCallingGui extends Screen {

    //region Fields
    public BookOfCallingItem.ItemMode mode;
    public WorkAreaSize workAreaSize;
    //endregion Fields

    //region Initialization
    public BookOfCallingGui(BookOfCallingItem.ItemMode mode, WorkAreaSize workAreaSize) {
        super(new StringTextComponent(""));

        this.mode = mode;
        this.workAreaSize = workAreaSize;
        this.init();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        int i = (this.width) / 2;
        int j = (this.height - 166) / 2;
        int buttonWidth = 150;

        //Item mode button
        this.addButton((new ExtendedButton(i - buttonWidth / 2, j + 60, buttonWidth, 20,
                I18n.format(this.mode.getTranslationKey()), (b) -> {
            this.mode = this.mode.next();
            OccultismPackets.sendToServer(new MessageSetItemMode(this.mode.getValue()));
            this.init();
        })));

        //Work area size button
        this.addButton(new ExtendedButton(i - buttonWidth / 2, j + 85, buttonWidth, 20,
                I18n.format(this.workAreaSize.getTranslationKey()), (b) -> {
            this.workAreaSize = this.workAreaSize.next();
            OccultismPackets.sendToServer(new MessageSetWorkAreaSize(this.workAreaSize.getValue()));
            this.init();
        }));

        //Exit button
        int exitButtonWidth = 20;
        this.addButton(new ExtendedButton(i - exitButtonWidth / 2, j + 110, exitButtonWidth, 20, "X", (b) -> {
            this.minecraft.displayGuiScreen(null);
            this.init();
        }));

        LabelWidget modeLabel = new LabelWidget(i - 80,
                j + 66, false, -1, 2, Color.WHITE.getRGB()).alignRight(true);
        modeLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.mode", true);
        this.addButton(modeLabel);

        LabelWidget workAreaLabel = new LabelWidget(
                i - 80, j + 91, true, -1, 2, Color.WHITE.getRGB()).alignRight(true);
        workAreaLabel.addLine("gui." + Occultism.MODID + ".book_of_calling.work_area", true);
        this.addButton(workAreaLabel);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    //endregion Overrides
}
