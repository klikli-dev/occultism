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
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public abstract class MinerRecipes extends RecipeProvider {


    public MinerRecipes(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    public static void ores(RecipeOutput recipeOutput) {
        makeOreRecipe("uraninite_poor", 750, recipeOutput);
        makeOreRecipe("uraninite_regular", 500, recipeOutput);
        makeOreRecipe("uraninite_dense", 200, recipeOutput);
        makeOreRecipe("sal_ammoniac", 750, recipeOutput);
        makeOreRecipe("dark_gem", 200, recipeOutput);
        makeOreRecipe("agate", 200, recipeOutput);
        makeOreRecipe("aluminum", 422, recipeOutput);
        makeOreRecipe("amber", 184, recipeOutput);
        makeOreRecipe("amethyst", 200, recipeOutput);
        makeOreRecipe("antimony", 80, recipeOutput);
        makeOreRecipe("aquamarine", 200, recipeOutput);
        makeOreRecipe("ardite", 159, recipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + "arcane_crystal")), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + "arcane_crystal"));
        makeOreRecipe("bauxite", 168, recipeOutput);
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
        makeOreRecipe("fluorite", 133, recipeOutput);
        makeOreRecipe("garnet", 200, recipeOutput);
        makeVanillaItemRecipe(Items.GLOWSTONE, 234, recipeOutput);
        makeVanillaOreRecipe("gold", 311, recipeOutput);
        makeVanillaItemRecipe(Items.GRAVEL, 300, recipeOutput);
        makeOreRecipe("heliodor", 200, recipeOutput);
        makeOreRecipe("indicolite", 200, recipeOutput);
        makeOreRecipe("inferium", 190, recipeOutput);
        makeOreRecipe("iolite", 200, recipeOutput);
        makeVanillaOreRecipe("iron", 750, recipeOutput);
        makeVanillaOreRecipe("iridium", 40, recipeOutput);
        makeVanillaOreRecipe("lapis", 343, recipeOutput);
        makeOreRecipe("lead", 500, recipeOutput);
        makeOreRecipe("lignite_coal", 212, recipeOutput);
        makeOreRecipe("lithium", 201, recipeOutput);
        makeVanillaItemRecipe(Items.MAGMA_BLOCK, 300, recipeOutput);
        makeOreRecipe("magnesium", 233, recipeOutput);
        makeOreRecipe("malachite", 200, recipeOutput);
        makeOreRecipe("mithril", 169, recipeOutput);
        makeOreRecipe("morganite", 200, recipeOutput);
        makeOreRecipe("monazite", 48, recipeOutput);
        makeVanillaItemRecipe(Items.GILDED_BLACKSTONE, 373, recipeOutput);
        makeVanillaItemRecipe(Items.NETHER_GOLD_ORE, 373, recipeOutput);
        makeVanillaItemRecipe(Items.NETHER_QUARTZ_ORE, 560, recipeOutput);
        makeOreRecipe("nickel", 232, recipeOutput);
        makeOreRecipe("niter", 244, recipeOutput);
        makeVanillaItemRecipe(Items.OBSIDIAN, 300, recipeOutput);
        makeVanillaItemRecipe(Items.CRYING_OBSIDIAN, 50, recipeOutput);
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
        makeOreRecipe("titanium", 10, recipeOutput);
        makeOreRecipe("topaz", 200, recipeOutput);
        makeOreRecipe("tungsten", 192, recipeOutput);
        makeOreRecipe("uranium", 140, recipeOutput);
        makeOreRecipe("uraninite", 140, recipeOutput);
        makeOreRecipe("dimensional_shard", 127, recipeOutput);
        makeOreRecipe("draconium", 142, recipeOutput);

        makeOreRecipe("bone_fragments", 302, recipeOutput);
        makeOreRecipe("baronyte", 260, recipeOutput);
        makeOreRecipe("blazium", 218, recipeOutput);
        makeOreRecipe("bloodstone", 160, recipeOutput);
        makeOreRecipe("blue_gemstone", 300, recipeOutput);
        makeOreRecipe("charged_runium", 142, recipeOutput);
        makeOreRecipe("crystallite", 500, recipeOutput);
        makeOreRecipe("elecanium", 200, recipeOutput);
        makeOreRecipe("emberstone", 302, recipeOutput);
        makeOreRecipe("gemenyte", 400, recipeOutput);
        makeOreRecipe("ghastly", 200, recipeOutput);
        makeOreRecipe("ghoulish", 180, recipeOutput);
        makeOreRecipe("green_gemstone", 300, recipeOutput);
        makeOreRecipe("jade", 200, recipeOutput);
        makeOreRecipe("jewelyte", 370, recipeOutput);
        makeOreRecipe("limonite", 400, recipeOutput);
        makeOreRecipe("lyon", 160, recipeOutput);
        makeOreRecipe("mystite", 280, recipeOutput);
        makeOreRecipe("ornamyte", 280, recipeOutput);
        makeOreRecipe("purple_gemstone", 300, recipeOutput);
        makeOreRecipe("red_gemstone", 300, recipeOutput);
        makeOreRecipe("runium", 300, recipeOutput);
        makeOreRecipe("shyregem", 302, recipeOutput);
        makeOreRecipe("shyrestone", 302, recipeOutput);
        makeOreRecipe("varsium", 200, recipeOutput);
        makeOreRecipe("white_gemstone", 300, recipeOutput);
        makeOreRecipe("yellow_gemstone", 300, recipeOutput);
        makeOreRecipe("black_quartz", 360, recipeOutput);

        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + "xpetrified_ore")), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + "xpetrified_ore"));
        makeOreRecipe("zinc", 186, recipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, tag("forbidden_arcanus:runic_stones"), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/runic_stone"));

    }

    public static void eldritch(RecipeOutput recipeOutput) {
        //Raw
        makeStorageRecipe("raw_aethersent", 90, recipeOutput);
        makeStorageRecipe("raw_allthemodium", 30, recipeOutput);
        makeStorageRecipe("raw_aluminum", 90, recipeOutput);
        makeStorageRecipe("raw_azure_silver", 90, recipeOutput);
        makeStorageRecipe("raw_antimony", 90, recipeOutput);
        makeStorageRecipe("raw_cloggrum", 90, recipeOutput);
        makeStorageRecipe("raw_cobalt", 90, recipeOutput);
        makeStorageRecipe("raw_copper", 90, recipeOutput);
        makeStorageRecipe("raw_crimson_iron", 90, recipeOutput);
        makeStorageRecipe("raw_froststeel", 90, recipeOutput);
        makeStorageRecipe("raw_gold", 90, recipeOutput);
        makeStorageRecipe("raw_iesnium", 90, recipeOutput);
        makeStorageRecipe("raw_iridium", 90, recipeOutput);
        makeStorageRecipe("raw_iron", 90, recipeOutput);
        makeStorageRecipe("raw_lead", 90, recipeOutput);
        makeStorageRecipe("raw_nickel", 90, recipeOutput);
        makeStorageRecipe("raw_osmium", 90, recipeOutput);
        makeStorageRecipe("raw_platinum", 90, recipeOutput);
        makeStorageRecipe("raw_silver", 90, recipeOutput);
        makeStorageRecipe("raw_tin", 90, recipeOutput);
        makeStorageRecipe("raw_tungsten", 90, recipeOutput);
        makeStorageRecipe("raw_titanium", 90, recipeOutput);
        makeStorageRecipe("raw_unobtainium", 10, recipeOutput);
        makeStorageRecipe("raw_uranium", 90, recipeOutput);
        makeStorageRecipe("raw_vibranium", 20, recipeOutput);
        makeStorageRecipe("raw_yellorium", 90, recipeOutput);
        makeStorageRecipe("raw_zinc", 90, recipeOutput);
        //Others
        makeStorageRecipe("arcane_crystal", 90, recipeOutput);
        makeStorageRecipe("bauxite", 90, recipeOutput);
        makeStorageRecipe("coal", 90, recipeOutput);
        makeStorageRecipe("dark_gem", 90, recipeOutput);
        makeStorageRecipe("diamond", 90, recipeOutput);
        makeStorageRecipe("emerald", 90, recipeOutput);
        makeStorageRecipe("fluorite", 90, recipeOutput);
        makeStorageRecipe("inferium_essence", 90, recipeOutput);
        makeStorageRecipe("jade", 90, recipeOutput);
        makeStorageRecipe("lapis", 90, recipeOutput);
        makeStorageRecipe("lignite_coal", 90, recipeOutput);
        makeStorageRecipe("monazite", 90, recipeOutput);
        makeStorageRecipe("onyx", 90, recipeOutput);
        makeStorageRecipe("peridot", 90, recipeOutput);
        makeStorageRecipe("prosperity_shard", 90, recipeOutput);
        makeStorageRecipe("redstone", 90, recipeOutput);
        makeStorageRecipe("ruby", 90, recipeOutput);
        makeStorageRecipe("rune", 90, recipeOutput);
        makeStorageRecipe("salt", 90, recipeOutput);
        makeStorageRecipe("sapphire", 90, recipeOutput);
        makeStorageRecipe("sulfur", 90, recipeOutput);
        makeStorageRecipe("soulium_dust", 90, recipeOutput);
        makeStorageRecipe("stellarite", 90, recipeOutput);
        makeStorageRecipe("uraninite", 90, recipeOutput);
        //Don't found in 1.21
        /*
        makeStorageRecipe("agate", 90, recipeOutput);
        makeStorageRecipe("amber", 90, recipeOutput);
        makeStorageRecipe("aquamarine", 90, recipeOutput);
        makeStorageRecipe("ardite", 90, recipeOutput);
        makeStorageRecipe("beryl", 90, recipeOutput);
        makeStorageRecipe("boron", 90, recipeOutput);
        makeStorageRecipe("cinnabar", 90, recipeOutput);
        makeStorageRecipe("dimensional_shard",90,recipeOutput);
        makeStorageRecipe("electrotine", 90, recipeOutput);
        makeStorageRecipe("garnet", 90, recipeOutput);
        makeStorageRecipe("heliodor", 90, recipeOutput);
        makeStorageRecipe("indicolite", 90, recipeOutput);
        makeStorageRecipe("iolite", 90, recipeOutput);
        makeStorageRecipe("lithium", 90, recipeOutput);
        makeStorageRecipe("magnesium", 90, recipeOutput);
        makeStorageRecipe("malachite", 90, recipeOutput);
        makeStorageRecipe("mithril", 90, recipeOutput);
        makeStorageRecipe("morganite", 90, recipeOutput);
        makeStorageRecipe("niter", 90, recipeOutput);
        makeStorageRecipe("opal", 90, recipeOutput);
        makeStorageRecipe("tanzanite", 90, recipeOutput);
        makeStorageRecipe("thorium", 90, recipeOutput);
        makeStorageRecipe("topaz", 90, recipeOutput);
        makeStorageRecipe("dimensional_shard", 90, recipeOutput);
        makeStorageRecipe("draconium", 90, recipeOutput);
        makeStorageRecipe("bone_fragments", 90, recipeOutput);
        makeStorageRecipe("baronyte", 90, recipeOutput);
        makeStorageRecipe("blazium", 90, recipeOutput);
        makeStorageRecipe("bloodstone", 90, recipeOutput);
        makeStorageRecipe("blue_gemstone", 90, recipeOutput);
        makeStorageRecipe("charged_runium", 90, recipeOutput);
        makeStorageRecipe("crystallite", 90, recipeOutput);
        makeStorageRecipe("elecanium", 90, recipeOutput);
        makeStorageRecipe("emberstone", 90, recipeOutput);
        makeStorageRecipe("gemenyte", 90, recipeOutput);
        makeStorageRecipe("ghastly", 90, recipeOutput);
        makeStorageRecipe("ghoulish", 90, recipeOutput);
        makeStorageRecipe("green_gemstone", 90, recipeOutput);
        makeStorageRecipe("jewelyte", 90, recipeOutput);
        makeStorageRecipe("limonite", 90, recipeOutput);
        makeStorageRecipe("lyon", 90, recipeOutput);
        makeStorageRecipe("mystite", 90, recipeOutput);
        makeStorageRecipe("ornamyte", 90, recipeOutput);
        makeStorageRecipe("purple_gemstone", 90, recipeOutput);
        makeStorageRecipe("red_gemstone", 90, recipeOutput);
        makeStorageRecipe("runium", 90, recipeOutput);
        makeStorageRecipe("shyregem", 90, recipeOutput);
        makeStorageRecipe("shyrestone", 90, recipeOutput);
        makeStorageRecipe("varsium", 90, recipeOutput);
        makeStorageRecipe("white_gemstone", 90, recipeOutput);
        makeStorageRecipe("yellow_gemstone", 90, recipeOutput);
         */
        makeGemEldritchOutputRecipe("sal_ammoniac", 90, 9, recipeOutput);
        makeGemEldritchOutputRecipe("amethyst", 90, 9, recipeOutput);
        makeGemEldritchOutputRecipe("quartz", 90, 9, recipeOutput);
        makeGemEldritchOutputRecipe("black_quartz", 90, 9, recipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, Tags.Items.ORES_NETHERITE_SCRAP, 90, 9)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/ancient_debris"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, Tags.Items.DUSTS_GLOWSTONE, 90, 9)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/glowstone_dust"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismTags.Items.CLAY, 90, 9)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/clay"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismItems.MINING_DIMENSION_CORE_PIECE, 1)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/mining_dim_core"));
    }

    //  Item-based recipes where the item is not available in our dev env are not great because they would need a separate handling
    //  instead use a tag - we can pre-fill it with an optional content!
//    public static void makeModOreItemRecipe(ResourceLocation item, int weight, RecipeOutput consumer) {
//        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES), item, weight)
//                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
//                .itemExists()
//                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + item.getPath()));
//    }

    public static void makeVanillaItemRecipe(Item type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, type, weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + getItemName(type)));
    }

    public static void makeVanillaOreRecipe(String type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + type)), weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + type + "_ore"));
    }

    public static void makeOreRecipe(String type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + type)), weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + type + "_ore"));
    }

    public static void makeStorageRecipe(String type, int weight, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/" + type)), weight)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/" + type));
    }
    public static void makeGemEldritchOutputRecipe(String type, int weight, int amount, RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "gems/" + type)), weight, amount)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/" + type));
    }

    public static void deeps(RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_COAL_ORE, 1000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_coal_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_COPPER_ORE, 584)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_copper_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_DIAMOND_ORE, 218)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_diamond_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_EMERALD_ORE, 156)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_emerald_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_GOLD_ORE, 311)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_gold_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_IRON_ORE, 750)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_iron_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_LAPIS_ORE, 343)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_lapis_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_REDSTONE_ORE, 515)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_redstone_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), 381)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_silver_ore"));


    }

    public static void master_resources(RecipeOutput consumer) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, Items.ANCIENT_DEBRIS, 100)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/master/ancient_debris"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismBlocks.IESNIUM_ORE.get(), 100)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/master/iesnium_ore"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismItems.MINING_DIMENSION_CORE_PIECE, 1)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/master/mining_dim_core"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/stellarite")), 50)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/master/stellarite"));
    }

    public static void basic_resources(RecipeOutput consumer) {

        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.STONE, 10000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/stone"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.ANDESITE, 7000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/andesite"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.DIORITE, 7000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/diorite"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.GRANITE, 7000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/granite"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.DEEPSLATE, 5000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/deepslate"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.MOSSY_COBBLESTONE, 3000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/mossy_cobblestone"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.MOSSY_STONE_BRICKS, 3000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/mossy_stone_bricks"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.NETHERRACK, 1000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/netherrack"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.BASALT, 1000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/basalt"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.BLACKSTONE, 1000)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/blackstone"));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.END_STONE, 30)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/end_stone"));
    }

    public static TagKey<Item> tag(String tag) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(tag));
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

    }

}