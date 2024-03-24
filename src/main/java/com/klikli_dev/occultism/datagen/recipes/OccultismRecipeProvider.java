package com.klikli_dev.occultism.datagen.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class OccultismRecipeProvider extends RecipeProvider {
    public OccultismRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        crushingRecipes(pRecipeOutput);

    }



    private void crushingRecipes(RecipeOutput pRecipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(OccultismTags.Items.DATURA_CROP),Ingredient.of(OccultismTags.Items.DATURA_SEEDS), 200)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .setAllowEmpty(false)
                .setOutputAmount(2)
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "crushing/datura"));
        crushingMetalRecipe("allthemodium", pRecipeOutput);
        crushingMetalRecipe("iesnium",pRecipeOutput);
        crushingMetalRecipe("aluminum",pRecipeOutput);
        crushingMetalRecipe("signalum",pRecipeOutput);
        crushingMetalRecipe("uranium",pRecipeOutput);
        crushingMetalRecipe("graphite",pRecipeOutput);
        crushingMetalRecipe("azure_silver",pRecipeOutput);
        crushingMetalRecipe("zinc",pRecipeOutput);
        crushingMetalRecipe("lumium",pRecipeOutput);
        crushingMetalRecipe("osmium",pRecipeOutput);
        crushingMetalRecipe("nickel",pRecipeOutput);
        crushingMetalRecipe("lead",pRecipeOutput);
        crushingMetalRecipe("bronze",pRecipeOutput);
        crushingMetalRecipe("cobalt",pRecipeOutput);
        crushingMetalRecipe("unobtainium",pRecipeOutput);
        crushingMetalRecipe("tungsten",pRecipeOutput);
        crushingMetalRecipe("iridium",pRecipeOutput);
        crushingMetalRecipe("steel",pRecipeOutput);
        crushingMetalRecipe("enderium",pRecipeOutput);
        crushingMetalRecipe("electrum",pRecipeOutput);
        crushingMetalRecipe("constantan",pRecipeOutput);
        crushingMetalRecipe("tin",pRecipeOutput);
        crushingMetalRecipe("netherite",pRecipeOutput);
        crushingMetalRecipe("brass",pRecipeOutput);
        crushingMetalRecipe("crimson_iron",pRecipeOutput);
        crushingMetalRecipe("platinum",pRecipeOutput);
        crushingMetalRecipe("invar",pRecipeOutput);
        crushingMetalRecipe("vibranium",pRecipeOutput);
        crushingMetalRecipe("silver",pRecipeOutput);
        crushingMetalRecipe("copper",pRecipeOutput);
        crushingMetalRecipe("pewter",pRecipeOutput);
        crushingMetalRecipe("mithril",pRecipeOutput);
        crushingMetalRecipe("gold",pRecipeOutput);
        crushingMetalRecipe("quicksilver",pRecipeOutput);
        crushingMetalRecipe("iron",pRecipeOutput);

        crushingGemRecipe("diamond",pRecipeOutput);
        crushingGemRecipe("emerald",pRecipeOutput);
        crushingGemRecipe("lapis",pRecipeOutput);
        crushingGemRecipe("quartz",pRecipeOutput);
        crushingGemRecipe("coal",pRecipeOutput);
        crushingGemRecipe("redstone",pRecipeOutput);
        crushingGemRecipe("apatite",pRecipeOutput);
        crushingGemRecipe("sulfur",pRecipeOutput);
        crushingGemRecipe("fluorite",pRecipeOutput);
        crushingGemRecipe("cinnabar",pRecipeOutput);
        crushingGemRecipe("amber",pRecipeOutput);
        crushingGemRecipe("certus_quartz",pRecipeOutput);
        crushingGemRecipe("charged_certus_quartz",pRecipeOutput);
        crushingGemRecipe("peridot",pRecipeOutput);
        crushingGemRecipe("ruby",pRecipeOutput);
        crushingGemRecipe("sapphire",pRecipeOutput);
        crushingGemRecipe("topaz",pRecipeOutput);
        crushingGemRecipe("arcane_crystal",pRecipeOutput);

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Tags.Items.RODS_BLAZE),
                        Ingredient.of(OccultismTags.Items.BLAZE_DUST),200)
                .allowEmpty()
                .unlockedBy("has_blaze_rod", has(Tags.Items.RODS_BLAZE))

                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "crushing/blaze_powder_from_rod"));

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Tags.Items.OBSIDIAN),Ingredient.of(OccultismTags.Items.OBSIDIAN_DUST),200)
                .allowEmpty()
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "crushing/obsidian_dust"));
        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Tags.Items.END_STONES),Ingredient.of(OccultismTags.Items.END_STONE_DUST),200)
                .allowEmpty()
                .unlockedBy("has_end_stone", has(Tags.Items.END_STONES))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "crushing/end_stone_dust"));

    }

    private void crushingGemRecipe(String gemName, RecipeOutput recipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+gemName)),OccultismTags.makeItemTag(new ResourceLocation("forge","dusts/"+gemName)),200)
                .unlockedBy("has_"+gemName, has(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+gemName))))
                .setOutputAmount(4)
                .setAllowEmpty(false)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "crushing/"+gemName+"_dust"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(new ResourceLocation("forge","gems/"+gemName)),OccultismTags.makeItemTag(new ResourceLocation("forge","dusts/"+gemName)),200)
                .unlockedBy("has_"+gemName+"_gem", has(OccultismTags.makeItemTag(new ResourceLocation("forge","gems/"+gemName))))
                .setOutputAmount(1)
                .setAllowEmpty(false)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "crushing/"+gemName+"_dust_from_gem"));

    }

    private void crushingMetalRecipe(String metalName, RecipeOutput recipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+metalName)),OccultismTags.makeItemTag(new ResourceLocation("forge","dusts/"+metalName)),200)
                .unlockedBy("has_"+metalName, has(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+metalName))))
                .setOutputAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "crushing/"+metalName+"_dust"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(new ResourceLocation("forge","raw_materials/"+metalName)),OccultismTags.makeItemTag(new ResourceLocation("forge","dusts/"+metalName)),200)
                .unlockedBy("has_raw_"+metalName, has(OccultismTags.makeItemTag(new ResourceLocation("forge","raw_materials/"+metalName))))
                .setOutputAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "crushing/"+metalName+"_dust_from_raw"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(new ResourceLocation("forge","storage_blocks/raw_"+metalName)),OccultismTags.makeItemTag(new ResourceLocation("forge","dusts/"+metalName)),1600)
                .unlockedBy("has_raw_"+metalName+"_block", has(OccultismTags.makeItemTag(new ResourceLocation("forge","storage_blocks/raw_"+metalName))))
                .setOutputAmount(18)
                .setAllowEmpty(false)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "crushing/"+metalName+"_dust_from_raw_block"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(new ResourceLocation("forge","ingots/"+metalName)),OccultismTags.makeItemTag(new ResourceLocation("forge","dusts/"+metalName)),200)
                .unlockedBy("has_"+metalName+"_ingot", has(OccultismTags.makeItemTag(new ResourceLocation("forge","ingots/"+metalName))))
                .setOutputAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "crushing/"+metalName+"_dust_from_ingot"));


    }
}
