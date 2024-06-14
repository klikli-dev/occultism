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

import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import com.klikli_dev.modonomicon.registry.DataComponentRegistry;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GuideBookItem extends ModonomiconItem {

    public static final ResourceLocation DICTIONARY_OF_SPIRITS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "dictionary_of_spirits");

    public GuideBookItem(Properties properties) {
        super(properties);
        this.craftingRemainingItem = this;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //Copied from parent but statically gets DICTIONARY_OF_SPIRITS instead of from nbt
        var itemInHand = pPlayer.getItemInHand(pUsedHand);
        if (!itemInHand.has(DataComponentRegistry.BOOK_ID))
            itemInHand.set(DataComponentRegistry.BOOK_ID, DICTIONARY_OF_SPIRITS);

        if (pLevel.isClientSide) {
            var book = BookDataManager.get().getBook(DICTIONARY_OF_SPIRITS);
            BookGuiManager.get().openBook(book.getId());
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
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
//        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);

        Book book = BookDataManager.get().getBook(DICTIONARY_OF_SPIRITS);
        if (book != null) {
            if (tooltipFlag.isAdvanced()) {
                list.add(Component.literal("Book ID: ").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.literal(book.getId().toString()).withStyle(ChatFormatting.RED)));
            }

            if (!book.getTooltip().isBlank()) {
                list.add(Component.translatable(book.getTooltip()).withStyle(ChatFormatting.GRAY));
            }
        } else {
            var compound = new CompoundTag();
            for (var entry : itemStack.getComponents()) {
                var tag = entry.encodeValue(NbtOps.INSTANCE).getOrThrow();
                var key = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(entry.type());

                compound.put(key.toString(), tag);
            }

            list.add(Component.translatable(ModonomiconConstants.I18n.Tooltips.ITEM_NO_BOOK_FOUND_FOR_STACK, NbtUtils.toPrettyComponent(compound))
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack.copy();
    }


    public ItemStack getCreativeModeTabDisplayStack() {
        ItemStack stack = new ItemStack(this);

        stack.set(DataComponentRegistry.BOOK_ID, DICTIONARY_OF_SPIRITS);

        return stack;
    }
}
