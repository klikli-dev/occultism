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

package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuideBookItem extends ModonomiconItem {

    public static final ResourceLocation DICTIONARY_OF_SPIRITS = new ResourceLocation(Occultism.MODID, "dictionary_of_spirits");

    public GuideBookItem(Properties properties) {
        super(properties);
        this.craftingRemainingItem = this;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //Copied from parent but statically gets DICTIONARY_OF_SPIRITS instead of from nbt
        var itemInHand = pPlayer.getItemInHand(pUsedHand);

        if (pLevel.isClientSide) {

            if (itemInHand.hasTag()) {
                var book = BookDataManager.get().getBook(DICTIONARY_OF_SPIRITS);
                BookGuiManager.get().openBook(book.getId());
            } else {
                Modonomicon.LOGGER.error("ModonomiconItem: ItemStack has no tag!");
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide);
    }

    @Override
    public Component getName(ItemStack pStack) {
        Book book = BookDataManager.get().getBook(DICTIONARY_OF_SPIRITS);
        if (book != null) {
            return Component.translatable(book.getName());
        }

        return super.getName(pStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //super.appendHoverText(stack, worldIn, tooltip, flagIn);

        Book book = BookDataManager.get().getBook(DICTIONARY_OF_SPIRITS);
        if (book != null) {
            if (flagIn.isAdvanced()) {
                tooltip.add(Component.literal("Book ID: ").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.literal(book.getId().toString()).withStyle(ChatFormatting.RED)));
            }

            if (!book.getTooltip().isBlank()) {
                tooltip.add(Component.translatable(book.getTooltip()).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltip.add(Component.translatable(ModonomiconConstants.I18n.Tooltips.ITEM_NO_BOOK_FOUND_FOR_STACK,
                            !stack.hasTag() ? Component.literal("{}") : NbtUtils.toPrettyComponent(stack.getTag()))
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }


    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack.copy();
    }


    public ItemStack getCreativeModeTabDisplayStack() {
        ItemStack stack = new ItemStack(this);

        CompoundTag cmp = new CompoundTag();
        cmp.putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, DICTIONARY_OF_SPIRITS.toString());
        stack.setTag(cmp);

        return stack;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.getOrCreateTag().putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, DICTIONARY_OF_SPIRITS.toString());
        return super.initCapabilities(stack, nbt);
    }
}
