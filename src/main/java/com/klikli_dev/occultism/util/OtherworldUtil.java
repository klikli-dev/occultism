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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.common.item.otherworld.OtherworldBlockItem;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.util.thread.SidedThreadGroups;

public class OtherworldUtil {

    //region Static Methods

    /**
     * Runs on both physical client and server. Returns default translation key for physical server. Returns
     * getClientTranslationKey for physical client.
     */
    public static String getTranslationKeyDistAware(OtherworldBlockItem item, ItemStack stack) {
        if (FMLEnvironment.dist == Dist.CLIENT)
            return getClientTranslationKey(item, stack);
        return item.getOrCreateDescriptionId();
    }

    /**
     * Runs on physical client. Returns default translation for logical server or if third eye is not present. Returns
     * otherworld translation for logical client if third eye is present.
     */
    @OnlyIn(Dist.CLIENT)
    public static String getClientTranslationKey(OtherworldBlockItem item, ItemStack stack) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            return item.getOrCreateDescriptionId();
        boolean thirdEye = Minecraft.getInstance() != null && Minecraft.getInstance().player != null
                && Minecraft.getInstance().player.hasEffect(OccultismEffects.THIRD_EYE);
        return stack.getOrDefault(OccultismDataComponents.IS_INVENTORY_ITEM, false) ||
                thirdEye ? item.getOrCreateDescriptionId() : item.getDescriptionId();
    }
    //endregion Static Methods
}
