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

package com.github.klikli_dev.occultism.integration.jei;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.github.klikli_dev.occultism.crafting.recipe.ItemStackFakeInventory;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.util.RecipeUtil;
import com.google.common.base.Strings;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Collection;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    //region Fields
    protected static boolean jeiLoaded = false;
    protected static boolean jeiSearchSync = true;
    protected static IJeiRuntime runtime;
    //endregion Fields

    //region Getter / Setter
    public static boolean isJeiLoaded() {
        return jeiLoaded;
    }

    public static void setJeiLoaded(boolean loaded) {
        JeiPlugin.jeiLoaded = loaded;
    }

    public static boolean isJeiSearchSynced() {
        return jeiSearchSync;
    }

    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }

    public static String getFilterText() {
        if (runtime != null)
            return runtime.getIngredientFilter().getFilterText();
        return "";
    }

    public static void setFilterText(String filter) {
        if (runtime != null)
            runtime.getIngredientFilter().setFilterText(Strings.nullToEmpty(filter));
    }

    public static void setJeiSearchSync(boolean synced) {
        JeiPlugin.jeiSearchSync = synced;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Occultism.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SpiritFireRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager recipeManager = world.getRecipeManager();

        Collection<IRecipe<ItemStackFakeInventory>> spiritFireRecipes =
                RecipeUtil.getRecipes(recipeManager, OccultismRecipes.SPIRIT_FIRE_TYPE.get()).values();
        registration.addRecipes(spiritFireRecipes, OccultismRecipes.SPIRIT_FIRE.getId());

        Collection<IRecipe<ItemStackFakeInventory>> crushingRecipes =
                RecipeUtil.getRecipes(recipeManager, OccultismRecipes.CRUSHING_TYPE.get()).values();
        registration.addRecipes(crushingRecipes, OccultismRecipes.CRUSHING.getId());

        Collection<IRecipe<RecipeWrapper>> minerRecipes =
                RecipeUtil.getRecipes(recipeManager, OccultismRecipes.MINER_TYPE.get()).values();
        registration.addRecipes(minerRecipes, OccultismRecipes.MINER.getId());

        Collection<IRecipe<RecipeWrapper>> ritualRecipes =
                RecipeUtil.getRecipes(recipeManager, OccultismRecipes.RITUAL_TYPE.get()).values();
        registration.addRecipes(ritualRecipes, OccultismRecipes.RITUAL.getId());
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(StorageControllerContainer.class),
                VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.SPIRIT_FIRE.get()),
                OccultismRecipes.SPIRIT_FIRE.getId());
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()),
                OccultismRecipes.MINER.getId());
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()),
                OccultismRecipes.RITUAL.getId());
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiPlugin.runtime = jeiRuntime;
        JeiPlugin.jeiLoaded = true;
    }
    //endregion Overrides

}
