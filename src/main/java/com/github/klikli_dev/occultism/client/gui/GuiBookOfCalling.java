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

package com.github.klikli_dev.occultism.client.gui;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirits.WorkAreaSize;
import com.github.klikli_dev.occultism.common.item.ItemBookOfCallingActive;
import com.github.klikli_dev.occultism.network.MessageSetItemMode;
import com.github.klikli_dev.occultism.network.MessageSetWorkAreaSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.awt.*;

public class GuiBookOfCalling extends Screen {

    //region Fields
    public ItemBookOfCallingActive.ItemMode mode;
    public WorkAreaSize workAreaSize;
    //endregion Fields

    //region Initialization
    public GuiBookOfCalling(ItemBookOfCallingActive.ItemMode mode, WorkAreaSize workAreaSize) {
        super();
        this.mode = mode;
        this.workAreaSize = workAreaSize;
        this.mc = Minecraft.getMinecraft();
        this.initGui();
    }
    //endregion Initialization

    //region Overrides
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(Button button) {

        if (button.enabled) {
            switch (button.id) {
                case 0: // mode button
                    this.mode = this.mode.next();
                    Occultism.network.sendToServer(new MessageSetItemMode(this.mode.getValue()));
                    break;
                case 1: //work area button
                    this.workAreaSize = this.workAreaSize.next();
                    Occultism.network.sendToServer(new MessageSetWorkAreaSize(this.workAreaSize.getValue()));
                    break;
                case 2: //exit button
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
            }
        }

        this.initGui();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.labelList.clear();
        int i = (this.width) / 2;
        int j = (this.height - 166) / 2;
        int buttonWidth = 150;
        int buttonId = 0;
        ItemStack item = Minecraft.getMinecraft().player.getHeldItem(Hand.MAIN_HAND);
        this.buttonList.add(new GuiButtonExt(buttonId++, i - buttonWidth / 2, j + 60, buttonWidth, 20,
                I18n.format(this.mode.getTranslationKey())));
        this.buttonList.add(new GuiButtonExt(buttonId++, i - buttonWidth / 2, j + 85, buttonWidth, 20,
                I18n.format(this.workAreaSize.getTranslationKey())));

        int exitButtonWidth = 20;
        this.buttonList.add(new GuiButtonExt(buttonId++, i - exitButtonWidth / 2, j + 110, exitButtonWidth, 20, "X"));

        int labelId = 0;

        String modeString = I18n.format("gui." + Occultism.MODID + ".book_of_calling.mode");
        int modeStringWidth = this.mc.fontRenderer.getStringWidth(modeString);
        GuiLabel modeLabel = new GuiLabel(this.mc.fontRenderer, labelId++, i - buttonWidth / 2 - modeStringWidth - 10,
                j + 60, modeStringWidth, 20, Color.WHITE.getRGB());
        modeLabel.addLine(modeString);

        String workAreaString = I18n.format("gui." + Occultism.MODID + ".book_of_calling.work_area");
        int workAreaStringWidth = this.mc.fontRenderer.getStringWidth(workAreaString);
        GuiLabel workAreaLabel = new GuiLabel(this.mc.fontRenderer, labelId++,
                i - buttonWidth / 2 - workAreaStringWidth - 10, j + 85, workAreaStringWidth, 20, Color.WHITE.getRGB());
        workAreaLabel.addLine(workAreaString);

        this.labelList.add(modeLabel);
        this.labelList.add(workAreaLabel);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
    //endregion Overrides

}
