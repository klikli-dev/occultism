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
import com.klikli_dev.occultism.crafting.recipe.*;
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
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    protected static IJeiRuntime runtime;
    private static RecipeMap syncedRecipes = RecipeMap.EMPTY;

    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(Occultism.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SpiritFireRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystallizeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SpiritTradeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.CRAFTING, BoundBookRecipeMaker.createRecipes());

        registration.addRecipes(JeiRecipeTypes.SPIRIT_FIRE, this.getRecipes(syncedRecipes, OccultismRecipes.SPIRIT_FIRE_TYPE.get()));
        registration.addRecipes(JeiRecipeTypes.CRUSHING, this.getRecipes(syncedRecipes, OccultismRecipes.CRUSHING_TYPE.get()));
        registration.addRecipes(JeiRecipeTypes.CRYSTALLIZE, this.getRecipes(syncedRecipes, OccultismRecipes.CRYSTALLIZE_TYPE.get()));
        registration.addRecipes(JeiRecipeTypes.SPIRIT_TRADE, this.getRecipes(syncedRecipes, OccultismRecipes.SPIRIT_TRADE_TYPE.get()));
        registration.addRecipes(JeiRecipeTypes.MINER, this.getRecipes(syncedRecipes, OccultismRecipes.MINER_TYPE.get()));
        registration.addRecipes(JeiRecipeTypes.RITUAL, this.getRecipes(syncedRecipes, OccultismRecipes.RITUAL_TYPE.get()));

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <I extends RecipeInput, T extends net.minecraft.world.item.crafting.Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        return (List) recipeMap.byType(type);
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
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.CELESTIAL_CHALICE.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismBlocks.ELDRITCH_CHALICE.get()),
                JeiRecipeTypes.RITUAL);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER.get()),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER.get()),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER.get()),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRUSHER.get()),
                JeiRecipeTypes.CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRYSTALLIZER.get()),
                JeiRecipeTypes.CRYSTALLIZE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRYSTALLIZER.get()),
                JeiRecipeTypes.CRYSTALLIZE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRYSTALLIZER.get()),
                JeiRecipeTypes.CRYSTALLIZE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRYSTALLIZER.get()),
                JeiRecipeTypes.CRYSTALLIZE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER.get()),
                JeiRecipeTypes.SPIRIT_TRADE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER.get()),
                JeiRecipeTypes.SPIRIT_TRADE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERROCK_TRADER.get()),
                JeiRecipeTypes.SPIRIT_TRADE);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_GAMBLER.get()),
                JeiRecipeTypes.SPIRIT_TRADE);

        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.get()),
                RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.get()),
                RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.get()),
                RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.get()),
                RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.get()),
                RecipeTypes.SMOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.get()),
                RecipeTypes.SMOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.get()),
                RecipeTypes.SMOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.get()),
                RecipeTypes.SMOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.get()),
                RecipeTypes.BLASTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.get()),
                RecipeTypes.BLASTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.get()),
                RecipeTypes.BLASTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.get()),
                RecipeTypes.BLASTING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.get()),
                RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.get()),
                RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.get()),
                RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.get()),
                RecipeTypes.CAMPFIRE_COOKING);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiPlugin.runtime = jeiRuntime;
    }

    @EventBusSubscriber(modid = Occultism.MODID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(
                    OccultismRecipes.SPIRIT_FIRE_TYPE.get(),
                    OccultismRecipes.CRUSHING_TYPE.get(),
                    OccultismRecipes.CRYSTALLIZE_TYPE.get(),
                    OccultismRecipes.SPIRIT_TRADE_TYPE.get(),
                    OccultismRecipes.MINER_TYPE.get(),
                    OccultismRecipes.RITUAL_TYPE.get()
            );
        }
    }

    @EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipesReceived(RecipesReceivedEvent event) {
            syncedRecipes = event.getRecipeMap();
        }
    }

    public void registerIngredientInfo(IRecipeRegistration registration, ItemLike ingredient) {
        registration.addIngredientInfo(new ItemStack(ingredient.asItem()), VanillaTypes.ITEM_STACK,
                Component.translatable("jei." + Occultism.MODID + ".ingredient." + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath().replace("/", ".") + ".description"));
    }
}
