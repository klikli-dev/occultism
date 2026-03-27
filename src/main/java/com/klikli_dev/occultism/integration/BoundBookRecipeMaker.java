package com.klikli_dev.occultism.integration;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.*;

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
        var id = net.minecraft.resources.Identifier.fromNamespaceAndPath(Occultism.MODID, group + "_" + itemPath);

        // Build a ShapelessRecipe using the new constructor signatures in 26.1
        var commonInfo = new net.minecraft.world.item.crafting.Recipe.CommonInfo(true);
        var bookInfo = new net.minecraft.world.item.crafting.CraftingRecipe.CraftingBookInfo(net.minecraft.world.item.crafting.CraftingBookCategory.MISC, "");
        var result = net.minecraft.world.item.ItemStackTemplate.fromNonEmptyStack(BoundBookOfBindingRecipe.getBoundBookFromBook(bookOfBinding));
        var ingredients = java.util.List.of(
                net.minecraft.world.item.crafting.Ingredient.of(OccultismItems.DICTIONARY_OF_SPIRITS.get()),
                net.minecraft.world.item.crafting.Ingredient.of(OccultismItems.DICTIONARY_OF_SPIRITS.get()),
                net.minecraft.world.item.crafting.Ingredient.of(bookOfBinding.getItem())
        );

        var recipe = new net.minecraft.world.item.crafting.ShapelessRecipe(commonInfo, bookInfo, result, ingredients);
        return new net.minecraft.world.item.crafting.RecipeHolder<>(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.RECIPE, id), recipe);
    }

}
