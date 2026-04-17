package com.klikli_dev.occultism.integration;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.CraftingRecipe.CraftingBookInfo;
import net.minecraft.world.item.crafting.Recipe.CommonInfo;

import java.util.List;

public class BoundBookRecipeMaker {

    public static List<RecipeHolder<CraftingRecipe>> createRecipes() {
        return List.of(
                makeRecipe(new ItemStack(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())),
                makeRecipe(new ItemStack(OccultismItems.BOOK_OF_BINDING_DJINNI.get())),
                makeRecipe(new ItemStack(OccultismItems.BOOK_OF_BINDING_AFRIT.get())),
                makeRecipe(new ItemStack(OccultismItems.BOOK_OF_BINDING_MARID.get()))
        );
    }

    private static RecipeHolder<CraftingRecipe> makeRecipe(ItemStack bookOfBinding) {
        String group = "occultism.bound_book_of_binding";
        var itemPath = BuiltInRegistries.ITEM.getKey(bookOfBinding.getItem()).getPath();
        var id = Identifier.fromNamespaceAndPath(Occultism.MODID, group + "_" + itemPath);

        // Build a ShapelessRecipe using the new constructor signatures in 26.1
        var commonInfo = new CommonInfo(true);
        var bookInfo = new CraftingBookInfo(CraftingBookCategory.MISC, "");
        var result = ItemStackTemplate.fromNonEmptyStack(BoundBookOfBindingRecipe.getBoundBookFromBook(bookOfBinding));
        var ingredients = List.of(
                Ingredient.of(OccultismItems.DICTIONARY_OF_SPIRITS.get()),
                Ingredient.of(OccultismItems.DICTIONARY_OF_SPIRITS.get()),
                Ingredient.of(bookOfBinding.getItem())
        );

        var recipe = new ShapelessRecipe(commonInfo, bookInfo, result, ingredients);
        return new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, id), recipe);
    }

}
