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

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.client.gui.spirit.BookOfCallingGui;
import com.klikli_dev.occultism.client.gui.spirit.BookOfCallingManagedMachineGui;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class GuiHelper {

    //region Static Methods
    @OnlyIn(Dist.CLIENT)
    public static void openBookOfCallingManagedMachineGui(Direction insertFacing, Direction extractFacing,
                                                          String customName) {
        Minecraft.getInstance().setScreen(
                new BookOfCallingManagedMachineGui(insertFacing, extractFacing, customName));
    }

    @OnlyIn(Dist.CLIENT)
    public static void openBookOfCallingGui(ItemMode itemMode, WorkAreaSize workAreaSize) {
        itemMode.openGUI(workAreaSize);
    }

    public static void openBookOfCallingGui_internal(ItemMode itemMode, WorkAreaSize workAreaSize) {
        Minecraft.getInstance().setScreen(new BookOfCallingGui(itemMode, workAreaSize));
    }
    //endregion Static Methods
}
