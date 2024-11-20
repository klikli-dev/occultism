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

package com.klikli_dev.occultism.integration.jei.impl;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.klikli_dev.occultism.integration.BoundBookRecipeMaker;
import com.klikli_dev.occultism.integration.jei.impl.recipes.*;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    protected static IJeiRuntime runtime;

    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "jei");
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
        ClientLevel level = Minecraft.getInstance().level;
        RecipeManager recipeManager = level.getRecipeManager();

        registration.addRecipes(RecipeTypes.CRAFTING, BoundBookRecipeMaker.createRecipes());

        var spiritFireRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get());
        registration.addRecipes(JeiRecipeTypes.SPIRIT_FIRE, spiritFireRecipes);

        var crushingRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.CRUSHING_TYPE.get());
        registration.addRecipes(JeiRecipeTypes.CRUSHING, crushingRecipes);

        var minerRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.MINER_TYPE.get());
        registration.addRecipes(JeiRecipeTypes.MINER, minerRecipes);

        var ritualRecipes = recipeManager.getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get());
        registration.addRecipes(JeiRecipeTypes.RITUAL, ritualRecipes);

        this.registerIngredientInfo(registration, OccultismItems.TALLOW.get());
        this.registerIngredientInfo(registration, OccultismBlocks.OTHERSTONE.get());
        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_LOG.get());
        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_LEAVES.get());
        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_SAPLING.get());
        this.registerIngredientInfo(registration, OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get());
        this.registerIngredientInfo(registration, OccultismBlocks.IESNIUM_ORE.get());
        this.registerIngredientInfo(registration, OccultismBlocks.SPIRIT_FIRE.get());
        this.registerIngredientInfo(registration, OccultismItems.DATURA.get());
        this.registerIngredientInfo(registration, OccultismItems.SPAWN_EGG_GOAT_FAMILIAR.get());
        this.registerIngredientInfo(registration, OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR.get());
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
        IRecipeTransferHandlerHelper handlerHelper = registration.getTransferHelper();

        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(StorageControllerContainer.class, handlerHelper), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(StorageRemoteContainer.class, handlerHelper), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(StableWormholeContainer.class, handlerHelper), RecipeTypes.CRAFTING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.SPIRIT_FIRE.get()),
                JeiRecipeTypes.SPIRIT_FIRE);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()),
                JeiRecipeTypes.MINER);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.ELDRITCH_CHALICE.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_marid_crusher"))),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_afrit_crusher"))),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_crusher"))),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_crusher"))),
                JeiRecipeTypes.CRUSHING);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiPlugin.runtime = jeiRuntime;
    }

    public void registerIngredientInfo(IRecipeRegistration registration, ItemLike ingredient) {
        registration.addIngredientInfo(new ItemStack(ingredient.asItem()), VanillaTypes.ITEM_STACK,
                Component.translatable("jei." + Occultism.MODID + ".ingredient." + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath().replace("/", ".") + ".description"));
    }
}
