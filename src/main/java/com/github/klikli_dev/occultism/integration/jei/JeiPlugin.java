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
import com.github.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.github.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.github.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.github.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.github.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
//import com.github.klikli_dev.occultism.integration.jei.recipes.CrushingRecipeCategory;
//import com.github.klikli_dev.occultism.integration.jei.recipes.MinerRecipeCategory;
//import com.github.klikli_dev.occultism.integration.jei.recipes.RitualRecipeCategory;
//import com.github.klikli_dev.occultism.integration.jei.recipes.SpiritFireRecipeCategory;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.constants.VanillaRecipeCategoryUid;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.helpers.IStackHelper;
//import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
//import mezz.jei.api.registration.IRecipeCatalystRegistration;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import mezz.jei.api.registration.IRecipeTransferRegistration;
//import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

import java.util.List;
//TODO: Patchouli
//@mezz.jei.api.JeiPlugin
//public class JeiPlugin implements IModPlugin {
//
//    //region Fields
//
//    protected static IJeiRuntime runtime;
//    //endregion Fields
//
//    //region Getter / Setter
//
//    public static IJeiRuntime getJeiRuntime() {
//        return runtime;
//    }
//
//    //endregion Getter / Setter
//
//    //region Overrides
//    @Override
//    public ResourceLocation getPluginUid() {
//        return new ResourceLocation(Occultism.MODID, "jei");
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registration) {
//        registration.addRecipeCategories(new SpiritFireRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//        registration.addRecipeCategories(new CrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//        registration.addRecipeCategories(new MinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//        registration.addRecipeCategories(new RitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//        ClientLevel level = Minecraft.getInstance().level;
//        RecipeManager recipeManager = level.getRecipeManager();
//
//
//        List<SpiritFireRecipe> spiritFireRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get());
//        registration.addRecipes(spiritFireRecipes, OccultismRecipes.SPIRIT_FIRE.getId());
//
//        List<CrushingRecipe> crushingRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.CRUSHING_TYPE.get());
//        registration.addRecipes(crushingRecipes, OccultismRecipes.CRUSHING.getId());
//
//        List<MinerRecipe> minerRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.MINER_TYPE.get());
//        registration.addRecipes(minerRecipes, OccultismRecipes.MINER.getId());
//
//        List<RitualRecipe> ritualRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get());
//        registration.addRecipes(ritualRecipes, OccultismRecipes.RITUAL.getId());
//
//        this.registerIngredientInfo(registration, OccultismItems.TALLOW.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.OTHERSTONE.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_LOG.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_LEAVES.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_SAPLING.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.IESNIUM_ORE.get());
//        this.registerIngredientInfo(registration, OccultismBlocks.SPIRIT_FIRE.get());
//    }
//
//    public void registerIngredientInfo(IRecipeRegistration registration, ItemLike ingredient) {
//        registration.addIngredientInfo(new ItemStack(ingredient.asItem()), VanillaTypes.ITEM,
//                new TranslatableComponent("jei." + Occultism.MODID + ".ingredient." + ingredient.asItem().getRegistryName().getPath() + ".description"));
//    }
//
//    @Override
//    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
//        //region Fields
//        IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
//        IRecipeTransferHandlerHelper handlerHelper = registration.getTransferHelper();
//        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(
//                        StorageControllerContainer.class, CraftingRecipe.class, handlerHelper),
//                VanillaRecipeCategoryUid.CRAFTING);
//        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(
//                        StorageRemoteContainer.class, CraftingRecipe.class, handlerHelper),
//                VanillaRecipeCategoryUid.CRAFTING);
//        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(
//                        StableWormholeContainer.class, CraftingRecipe.class, handlerHelper),
//                VanillaRecipeCategoryUid.CRAFTING);
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.SPIRIT_FIRE.get()),
//                OccultismRecipes.SPIRIT_FIRE.getId());
//        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()),
//                OccultismRecipes.MINER.getId());
//        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()),
//                OccultismRecipes.RITUAL.getId());
//    }
//
//    @Override
//    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
//        JeiPlugin.runtime = jeiRuntime;
//        JeiSettings.setJeiLoaded(true);
//    }
//    //endregion Overrides
//
//}
