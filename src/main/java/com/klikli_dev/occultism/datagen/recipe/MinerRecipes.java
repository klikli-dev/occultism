package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.MinerRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public abstract class MinerRecipes extends RecipeProvider {


    public MinerRecipes(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    public static void ores(RecipeOutput recipeOutput) {
        makeOreRecipe("agate", 200, recipeOutput);
        makeOreRecipe("aluminum", 422, recipeOutput);
        makeOreRecipe("amber", 184, recipeOutput);
        makeOreRecipe("amethyst", 200, recipeOutput);
        makeOreRecipe("aquamarine", 200, recipeOutput);
        makeOreRecipe("ardite", 159, recipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(new ResourceLocation("c", "ores/" + "arcane_crystal")), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "miner/ores/" + "arcane_crystal"));
        makeOreRecipe("beryl", 200, recipeOutput);
        makeOreRecipe("boron", 199, recipeOutput);
        makeOreRecipe("certus_quartz", 187, recipeOutput);
        makeOreRecipe("cinnabar", 190, recipeOutput);
        makeVanillaItemRecipe(Items.CLAY, 300, recipeOutput);
        makeVanillaOreRecipe("coal", 1000, recipeOutput);
        makeOreRecipe("cobalt", 163, recipeOutput);
        makeVanillaOreRecipe("copper", 584, recipeOutput);
        makeVanillaOreRecipe("diamond", 218, recipeOutput);
//        makeOreRecipe("dimensional_shard",127,recipeOutput);
        makeOreRecipe("electrotine", 155, recipeOutput);
        makeVanillaOreRecipe("emerald", 156, recipeOutput);
        makeOreRecipe("garnet", 200, recipeOutput);
        makeVanillaItemRecipe(Items.GLOWSTONE, 234, recipeOutput);
        makeVanillaOreRecipe("gold", 311, recipeOutput);
        makeVanillaItemRecipe(Items.GRAVEL, 300, recipeOutput);
        makeOreRecipe("heliodor", 200, recipeOutput);
        makeOreRecipe("indicolite", 200, recipeOutput);
        makeOreRecipe("inferium", 190, recipeOutput);
        makeOreRecipe("iolite", 200, recipeOutput);
        makeVanillaOreRecipe("iron", 750, recipeOutput);
        makeVanillaOreRecipe("lapis", 343, recipeOutput);
        makeOreRecipe("lead", 500, recipeOutput);
        makeOreRecipe("lithium", 201, recipeOutput);
        makeVanillaItemRecipe(Items.MAGMA_BLOCK, 300, recipeOutput);
        makeOreRecipe("magnesium", 233, recipeOutput);
        makeOreRecipe("malachite", 200, recipeOutput);
        makeOreRecipe("mithril", 169, recipeOutput);
        makeOreRecipe("morganite", 200, recipeOutput);
        makeVanillaItemRecipe(Items.NETHER_GOLD_ORE, 373, recipeOutput);
        makeVanillaItemRecipe(Items.NETHER_QUARTZ_ORE, 560, recipeOutput);
        makeOreRecipe("nickel", 232, recipeOutput);
        makeOreRecipe("niter", 244, recipeOutput);
        makeVanillaItemRecipe(Items.OBSIDIAN, 300, recipeOutput);
        makeOreRecipe("onyx", 200, recipeOutput);
        makeOreRecipe("opal", 200, recipeOutput);
        makeOreRecipe("osmium", 203, recipeOutput);
        makeVanillaItemRecipe(OccultismBlocks.OTHERSTONE.get().asItem(), 50, recipeOutput);
        makeOreRecipe("peridot", 200, recipeOutput);
        makeOreRecipe("platinum", 150, recipeOutput);
        makeOreRecipe("prosperity", 155, recipeOutput);
        makeVanillaItemRecipe(Items.REDSTONE_ORE, 515, recipeOutput);
        makeOreRecipe("ruby", 200, recipeOutput);
        makeOreRecipe("salt", 160, recipeOutput);
        makeOreRecipe("sapphire", 200, recipeOutput);
        makeOreRecipe("silver", 381, recipeOutput);
        makeOreRecipe("sulfur", 222, recipeOutput);
        makeOreRecipe("tanzanite", 200, recipeOutput);
        makeOreRecipe("thorium", 222, recipeOutput);
        makeOreRecipe("tin", 602, recipeOutput);
        makeOreRecipe("topaz", 200, recipeOutput);
        makeOreRecipe("tungsten", 192, recipeOutput);
        makeOreRecipe("uranium", 140, recipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(new ResourceLocation("c", "ores/" + "xpetrified_ore")), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "miner/ores/" + "xpetrified_ore"));
        makeOreRecipe("zinc", 186, recipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, tag("forbidden_arcanus:runic_stones"), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "miner/ores/runic_stone"));


    }

    public static void makeVanillaItemRecipe(Item type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, type, weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/" + getItemName(type)));
    }

    public static void makeVanillaOreRecipe(String type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(new ResourceLocation("c", "ores/" + type)), weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/" + type + "_ore"));
    }

    public static void makeOreRecipe(String type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(new ResourceLocation("c", "ores/" + type)), weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/" + type + "_ore"));
    }

    public static void deeps(RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_COAL_ORE, 1000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_coal_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_COPPER_ORE, 584)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_copper_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_DIAMOND_ORE, 218)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_diamond_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_EMERALD_ORE, 156)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_emerald_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_GOLD_ORE, 311)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_gold_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_IRON_ORE, 750)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_iron_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_LAPIS_ORE, 343)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_lapis_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_REDSTONE_ORE, 515)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_redstone_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), 381)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_silver_ore"));


    }

    public static void master_resources(RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, Items.ANCIENT_DEBRIS, 100)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/master/ancient_debris"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismBlocks.IESNIUM_ORE.get(), 100)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/master/iesnium_ore"));
    }

    public static void basic_resources(RecipeOutput consumer) {

        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.ANDESITE, 10000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/andesite"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.DIORITE, 10000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/diorite"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.END_STONE, 30)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/end_stone"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.GRANITE, 10000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/granite"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.MOSSY_COBBLESTONE, 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/mossy_cobblestone"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.MOSSY_STONE_BRICKS, 10000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/mossy_stone_bricks"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.NETHERRACK, 1000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/netherrack"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.STONE, 10000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/stone"));
    }

    public static TagKey<Item> tag(String tag) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(tag));
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

    }

}