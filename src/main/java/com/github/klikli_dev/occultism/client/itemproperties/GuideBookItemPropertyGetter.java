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

package com.github.klikli_dev.occultism.client.itemproperties;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class GuideBookItemPropertyGetter implements ItemPropertyFunction {
    //region Overrides
    @OnlyIn(Dist.CLIENT)
    @Override
    public float call(ItemStack stack, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn, int i) {
        //TODO: Patchouli
//        Book book = BookRegistry.INSTANCE.books.get(GuideBookItem.GUIDE);
//        float progression = 0.0F;
//        if (book != null) {
//            int totalEntries = 0;
//            int unlockedEntries = 0;
//            Iterator var8 = book.contents.entries.values().iterator();
//
//            while (var8.hasNext()) {
//                BookEntry entry = (BookEntry) var8.next();
//                if (!entry.isSecret()) {
//                    ++totalEntries;
//                    if (!entry.isLocked()) {
//                        ++unlockedEntries;
//                    }
//                }
//            }
//
//            progression = (float) unlockedEntries / Math.max(1.0F, (float) totalEntries);
//        }
//
//        return progression;
        return 0.0f;
    }
}
