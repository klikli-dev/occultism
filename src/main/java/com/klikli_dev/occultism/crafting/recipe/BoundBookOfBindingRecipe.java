package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BoundBookOfBindingRecipe extends CustomRecipe {
    public static RecipeSerializer<BoundBookOfBindingRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(BoundBookOfBindingRecipe::new);

    public BoundBookOfBindingRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer input, @NotNull Level level) {
        int i = 0;
        ItemStack dictionaryOfSpirits = ItemStack.EMPTY;
        ItemStack bookOfBinding = ItemStack.EMPTY;

        for (int j = 0; j < input.getContainerSize(); j++) {
            ItemStack inputStack = input.getItem(j);
            if (!inputStack.isEmpty()) {
                if (inputStack.is(OccultismItems.DICTIONARY_OF_SPIRITS.get())) {
                    if (!dictionaryOfSpirits.isEmpty()) {
                        return false;
                    }

                    dictionaryOfSpirits = inputStack;
                } else {
                    if (!inputStack.is(OccultismTags.Items.BOOKS_OF_BINDING)) {
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
    public @NotNull ItemStack assemble(CraftingContainer input, @NotNull RegistryAccess registries) {
        int i = 0;
        ItemStack dictionaryOfSpirits = ItemStack.EMPTY;
        ItemStack bookOfBinding = ItemStack.EMPTY;

        for (int j = 0; j < input.getContainerSize(); j++) {
            ItemStack inputStack = input.getItem(j);
            if (!inputStack.isEmpty()) {
                if (inputStack.is(OccultismItems.DICTIONARY_OF_SPIRITS.get())) {
                    if (!dictionaryOfSpirits.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    dictionaryOfSpirits = inputStack;
                } else {
                    if (!inputStack.is(OccultismTags.Items.BOOKS_OF_BINDING)) {
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

        if (dictionaryOfSpirits.hasCustomHoverName()) {
            var customName = dictionaryOfSpirits.getHoverName();
            ItemNBTUtil.setBoundSpiritName(boundBook, customName.getString());
        } else {
            ItemNBTUtil.generateBoundSpiritName(boundBook);
        }

        return boundBook;
    }

    public static ItemStack getBoundBookFromBook(ItemStack book) {
        if (book.is(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get());

        if (book.is(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get());

        if (book.is(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get());

        if (book.is(OccultismItems.BOOK_OF_BINDING_MARID.get()))
            return new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get());

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OccultismRecipes.BOOK_BINDING.get();
    }
}
