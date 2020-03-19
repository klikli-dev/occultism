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

package com.github.klikli_dev.occultism.common.item.tool;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;
import vazkii.patchouli.common.network.NetworkHandler;
import vazkii.patchouli.common.network.message.MessageOpenBookGui;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class GuideBookItem extends Item {
    //region Fields
    public static final ResourceLocation GUIDE = new ResourceLocation(Occultism.MODID, "dictionary_of_spirits");
    //endregion Fields

    //region Initialization
    public GuideBookItem(Properties properties) {
        super(properties);

        this.addPropertyOverride(new ResourceLocation("completion"), new IItemPropertyGetter() {
            //region Overrides
            @OnlyIn(Dist.CLIENT)
            public float call(ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                Book book = BookRegistry.INSTANCE.books.get(GUIDE);
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
            //endregion Overrides
        });
    }
    //endregion Initialization

    //region Overrides
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Book book = BookRegistry.INSTANCE.books.get(GUIDE);
        if (book == null) {
            return new ActionResult<>(ActionResultType.FAIL, stack);
        }
        else {
            if (playerIn instanceof ServerPlayerEntity) {
                NetworkHandler.sendToPlayer(new MessageOpenBookGui(book.id), (ServerPlayerEntity) playerIn);
                SoundEvent sfx = PatchouliSounds.getSound(book.openSound, PatchouliSounds.book_open);
                playerIn.playSound(sfx, 1.0F, (float) (0.7D + Math.random() * 0.4D));
            }

            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        Book book = BookRegistry.INSTANCE.books.get(GUIDE);
        if (book != null && book.contents != null) {
            tooltip.add((new StringTextComponent(book.contents.getSubtitle())).applyTextStyle(TextFormatting.GRAY));
        }

    }

    public ITextComponent getDisplayName(ItemStack stack) {
        Book book = BookRegistry.INSTANCE.books.get(GUIDE);
        return (ITextComponent) (
                book != null ? new TranslationTextComponent(book.name, new Object[0]) : super.getDisplayName(stack));
    }
    //endregion Overrides
}
