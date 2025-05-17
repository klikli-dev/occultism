package com.klikli_dev.occultism.integration.emi.impl;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.integration.BoundBookRecipeMaker;
import com.klikli_dev.occultism.integration.emi.impl.recipes.CrushingRecipeCategory;
import com.klikli_dev.occultism.integration.emi.impl.recipes.MinerRecipeCategory;
import com.klikli_dev.occultism.integration.emi.impl.recipes.RitualRecipeCategory;
import com.klikli_dev.occultism.integration.emi.impl.recipes.SpiritFireRecipeCategory;
import com.klikli_dev.occultism.integration.emi.impl.render.SpiritRenderable;
import com.klikli_dev.occultism.registry.*;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Objects;

@EmiEntrypoint
public class OccultismEmiPlugin implements EmiPlugin {
    public static final EmiStack SPIRIT_FIRE = EmiStack.of(OccultismItems.SPIRIT_FIRE.get());
    public static final EmiStack DIMENSIONAL_MINESHAFT = EmiStack.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get());
    public static final EmiStack GOLDEN_SACRIFICIAL_BOWL = EmiStack.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get());
    public static final EmiStack IESNIUM_SACRIFICIAL_BOWL = EmiStack.of(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get());
    public static final EmiStack ELDRITCH_CHALICE = EmiStack.of(OccultismBlocks.ELDRITCH_CHALICE.get());
    public static final ResourceLocation EMI_WIDGETS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/gui/emi/widgets.png");
    public static final EmiRecipeCategory SPIRIT_FIRE_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire"),SPIRIT_FIRE, new EmiTexture(EMI_WIDGETS, 0, 0, 16, 16));
    public static final EmiRecipeCategory CRUSHING_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing"), new SpiritRenderable<FoliotEntity>(OccultismEntities.FOLIOT.get()), new EmiTexture(EMI_WIDGETS, 32, 0, 16, 16));
    public static final EmiRecipeCategory MINER_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner"), DIMENSIONAL_MINESHAFT, new EmiTexture(EMI_WIDGETS, 48, 0, 16, 16));
    public static final EmiRecipeCategory RITUAL_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual"),GOLDEN_SACRIFICIAL_BOWL , new EmiTexture(EMI_WIDGETS, 64, 0, 16, 16));

    @Override
    public void initialize(EmiInitRegistry registry) {
        EmiPlugin.super.initialize(registry);
    }

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addRecipeHandler(OccultismContainers.STORAGE_CONTROLLER.get(), new StorageControllerEMIRecipeHandler<>(StorageControllerContainer.class));
        emiRegistry.addRecipeHandler(OccultismContainers.STORAGE_REMOTE.get(), new StorageControllerEMIRecipeHandler<>(StorageRemoteContainer.class));
        emiRegistry.addRecipeHandler(OccultismContainers.STABLE_WORMHOLE.get(), new StorageControllerEMIRecipeHandler<>(StableWormholeContainer.class));

        emiRegistry.addCategory(SPIRIT_FIRE_CATEGORY);
        emiRegistry.addWorkstation(SPIRIT_FIRE_CATEGORY, SPIRIT_FIRE);
        emiRegistry.addCategory(CRUSHING_CATEGORY);
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER.getId())))));
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER.getId())))));
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER.getId())))));
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRUSHER.getId())))));

        emiRegistry.addCategory(MINER_CATEGORY);
        emiRegistry.addWorkstation(MINER_CATEGORY, EmiStack.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()));

        emiRegistry.addCategory(RITUAL_CATEGORY);
        emiRegistry.addWorkstation(RITUAL_CATEGORY, GOLDEN_SACRIFICIAL_BOWL);
        emiRegistry.addWorkstation(RITUAL_CATEGORY, IESNIUM_SACRIFICIAL_BOWL);
        emiRegistry.addWorkstation(RITUAL_CATEGORY, ELDRITCH_CHALICE);

        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.BLASTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.BLASTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.BLASTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.BLASTING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.CAMPFIRE_COOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.CAMPFIRE_COOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.CAMPFIRE_COOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.getId())))));
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.CAMPFIRE_COOKING, EmiStack.of(new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.getId())))));

        RecipeManager manager=emiRegistry.getRecipeManager();
        for(RecipeHolder<SpiritFireRecipe> recipe: manager.getAllRecipesFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get())) {
            emiRegistry.addRecipe(new SpiritFireRecipeCategory(recipe));
        }
        for(RecipeHolder<CrushingRecipe> recipe:manager.getAllRecipesFor(OccultismRecipes.CRUSHING_TYPE.get())){
            emiRegistry.addRecipe(new CrushingRecipeCategory(recipe));
        }

        for(RecipeHolder<MinerRecipe> recipe:manager.getAllRecipesFor(OccultismRecipes.MINER_TYPE.get())){
            if(recipe.value().getIngredients().get(0).getValues().length==1) {
                if (recipe.value().getIngredients().get(0).getValues()[0] instanceof Ingredient.TagValue) {
                    var tag = ((Ingredient.TagValue) recipe.value().getIngredients().get(0).getValues()[0]).tag();
                    if(!MinerRecipeCategory.totalWeights.containsKey(tag))
                        MinerRecipeCategory.totalWeights.put(tag,0L);
                    MinerRecipeCategory.totalWeights.put(tag,MinerRecipeCategory.totalWeights.get(tag)+recipe.value().getWeightedResult().getWeight().asInt());


                }
            }
        }
        for(RecipeHolder<MinerRecipe> recipe:manager.getAllRecipesFor(OccultismRecipes.MINER_TYPE.get())){
            emiRegistry.addRecipe(new MinerRecipeCategory(recipe));
        }
        for(RecipeHolder<RitualRecipe> recipe:manager.getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get())){
            emiRegistry.addRecipe(new RitualRecipeCategory(recipe));
        }

        for(RecipeHolder<CraftingRecipe> recipe: BoundBookRecipeMaker.createRecipes()){
            var ingredients = recipe.value().getIngredients().stream().map(EmiIngredient::of).toList();
            emiRegistry.addRecipe(new EmiCraftingRecipe(ingredients, EmiStack.of(recipe.value().getResultItem(RegistryAccess.EMPTY)), recipe.id(), true));
        }
    }
}