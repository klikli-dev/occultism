package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Items;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BoundBookOfBindingRecipe extends CustomRecipe {
    public static final BoundBookOfBindingRecipe INSTANCE = new BoundBookOfBindingRecipe();
    public static final MapCodec<BoundBookOfBindingRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, BoundBookOfBindingRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<BoundBookOfBindingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public BoundBookOfBindingRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        int i = 0;
        ItemStack dictionaryOfSpirits = ItemStack.EMPTY;
        ItemStack bookOfBinding = ItemStack.EMPTY;

        for (int j = 0; j < input.size(); j++) {
            ItemStack inputStack = input.getItem(j);
            if (!inputStack.isEmpty()) {
                if (inputStack.is(OccultismItems.DICTIONARY_OF_SPIRITS)) {
                    if (!dictionaryOfSpirits.isEmpty()) {
                        return false;
                    }

                    dictionaryOfSpirits = inputStack;
                } else {
                    if (!inputStack.is(Items.BOOKS_OF_BINDING)) {
                        return false;
                    }
                    if (!bookOfBinding.isEmpty()) {
                        return false;
                    }
                    bookOfBinding = inputStack;
                    i++;
                }
            }
        }

        return !dictionaryOfSpirits.isEmpty() && !bookOfBinding.isEmpty() && i > 0;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input) {
        int i = 0;
        ItemStack dictionaryOfSpirits = ItemStack.EMPTY;
        ItemStack bookOfBinding = ItemStack.EMPTY;

        for (int j = 0; j < input.size(); j++) {
            ItemStack inputStack = input.getItem(j);
            if (!inputStack.isEmpty()) {
                if (inputStack.is(OccultismItems.DICTIONARY_OF_SPIRITS)) {
                    if (!dictionaryOfSpirits.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    dictionaryOfSpirits = inputStack;
                } else {
                    if (!inputStack.is(Items.BOOKS_OF_BINDING)) {
                        return ItemStack.EMPTY;
                    }
                    if (!bookOfBinding.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    bookOfBinding = inputStack;
                    i++;
                }
            }
        }

        var boundBook = getBoundBookFromBook(bookOfBinding);
        if (boundBook.isEmpty())
            return ItemStack.EMPTY;

        var customName = dictionaryOfSpirits.get(DataComponents.CUSTOM_NAME);
        var akashicName = dictionaryOfSpirits.getComponents().filter(comp -> comp.toString().contains("akashictome:og_display_name"));
        if (!akashicName.isEmpty()) {
            String s = akashicName.stream().findFirst().get().value().toString();
            if (s.startsWith("translation")){
                ItemNBTUtil.generateBoundSpiritName(boundBook);
            } else {
                ItemNBTUtil.setBoundSpiritName(boundBook, s.substring(8, s.length()-1));
            }
        } else if (customName != null) {
            String s = customName.getContents().toString();
            if (s.equals("translation{key='eccentrictome.name', args=[translation{key='book.occultism.dictionary_of_spirits.name', args=[]}[style={color=green}]]}")) {
                ItemNBTUtil.generateBoundSpiritName(boundBook);
            } else if (s.startsWith("translation{key='eccentrictome.name', args=[literal{") && s.endsWith("}[style={color=green}]]}")) {
                ItemNBTUtil.setBoundSpiritName(boundBook, s.substring(52, s.length() - 24));
            } else {
                ItemNBTUtil.setBoundSpiritName(boundBook, customName.getString());
            }
        } else {
            ItemNBTUtil.generateBoundSpiritName(boundBook);
        }

        return boundBook;
    }

    public static ItemStack getBoundBookFromBook(ItemStack book) {
        if (book.is(OccultismItems.BOOK_OF_BINDING_FOLIOT))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get());

        if (book.is(OccultismItems.BOOK_OF_BINDING_DJINNI))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get());

        if (book.is(OccultismItems.BOOK_OF_BINDING_AFRIT))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get());

        if (book.is(OccultismItems.BOOK_OF_BINDING_MARID))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get());

        return ItemStack.EMPTY;
    }

    public static ItemStack bookshelfCraft(ItemStack book, ItemStack dictionary) {
        var boundBook = getBoundBookFromBook(book);
        var customName = dictionary.get(DataComponents.CUSTOM_NAME);
        var akashicName = dictionary.getComponents().filter(comp -> comp.toString().contains("akashictome:og_display_name"));
        if (!akashicName.isEmpty()) {
            String s = akashicName.stream().findFirst().get().value().toString();
            if (s.startsWith("translation")){
                ItemNBTUtil.generateBoundSpiritName(boundBook);
            } else {
                ItemNBTUtil.setBoundSpiritName(boundBook, s.substring(8, s.length()-1));
            }
        } else if (customName != null) {
            String s = customName.getContents().toString();
            if (s.equals("translation{key='eccentrictome.name', args=[translation{key='book.occultism.dictionary_of_spirits.name', args=[]}[style={color=green}]]}")) {
                ItemNBTUtil.generateBoundSpiritName(boundBook);
            } else if (s.startsWith("translation{key='eccentrictome.name', args=[literal{") && s.endsWith("}[style={color=green}]]}")) {
                ItemNBTUtil.setBoundSpiritName(boundBook, s.substring(52, s.length() - 24));
            } else {
                ItemNBTUtil.setBoundSpiritName(boundBook, customName.getString());
            }
        } else {
            ItemNBTUtil.generateBoundSpiritName(boundBook);
        }
        return boundBook;
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return OccultismRecipes.BOOK_BINDING.get();
    }
}
