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

import com.github.klikli_dev.occultism.common.item.tool.GuideBookItem;
import net.minecraft.client.level.ClientWorld;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import javax.annotation.Nullable;
import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public class GuideBookItemPropertyGetter implements IItemPropertyGetter {
    //region Overrides
    @OnlyIn(Dist.CLIENT)
    @Override
    public float call(ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
        Book book = BookRegistry.INSTANCE.books.get(GuideBookItem.GUIDE);
        float progression = 0.0F;
        if (book != null) {
            int totalEntries = 0;
            int unlockedEntries = 0;
            Iterator var8 = book.contents.entries.values().iterator();

            while (var8.hasNext()) {
                BookEntry entry = (BookEntry) var8.next();
                if (!entry.isSecret()) {
                    ++totalEntries;
                    if (!entry.isLocked()) {
                        ++unlockedEntries;
                    }
                }
            }

            progression = (float) unlockedEntries / Math.max(1.0F, (float) totalEntries);
        }

        return progression;
    }
}
