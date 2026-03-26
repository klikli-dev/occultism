package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.MinerRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

// This class is now a utility class - no constructor needed
public abstract class MinerRecipes {

    // Helper method to create has() criterion for tags (requires HolderLookup.Provider)
    protected static Criterion<InventoryChangeTrigger.TriggerInstance> hasTag(TagKey<Item> tag, HolderLookup.Provider registries) {
        var builder = ItemPredicate.Builder.create();
        return InventoryChangeTrigger.TriggerInstance.hasItems(registries, tag);
    }

    public static void minerRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        basic_resources(pRecipeOutput, registries);
        ores(pRecipeOutput, registries);
        deeps(pRecipeOutput, registries);
        master_resources(pRecipeOutput, registries);
        eldritch(pRecipeOutput, registries);
        MinerRecipeBuilder.minerRecipe(OccultismItems.DEBUG_WAND.get(), OccultismBlocks.OTHERSTONE.get(), 200, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/debug_wand")));
        MinerRecipeBuilder.minerRecipe(OccultismItems.DEBUG_WAND.get(), OccultismBlocks.OTHERROCK.get(), 200, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/debug_wand_2")));
    }

    public static void ores(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        makeOreRecipe("uraninite_poor", 750, recipeOutput, registries);
        makeOreRecipe("uraninite_regular", 500, recipeOutput, registries);
        makeOreRecipe("uraninite_dense", 200, recipeOutput, registries);
        makeOreRecipe("sal_ammoniac", 750, recipeOutput, registries);
        makeOreRecipe("dark_gem", 200, recipeOutput, registries);
        makeOreRecipe("agate", 200, recipeOutput, registries);
        makeOreRecipe("aluminum", 422, recipeOutput, registries);
        makeOreRecipe("amber", 184, recipeOutput, registries);
        makeOreRecipe("amethyst", 200, recipeOutput, registries);
        makeOreRecipe("antimony", 80, recipeOutput, registries);
        makeOreRecipe("aquamarine", 200, recipeOutput, registries);
        makeOreRecipe("ardite", 159, recipeOutput, registries);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/" + "arcane_crystal")), 200, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + "arcane_crystal")));
        makeOreRecipe("bauxite", 168, recipeOutput, registries);
        makeOreRecipe("beryl", 200, recipeOutput, registries);
        makeOreRecipe("boron", 199, recipeOutput, registries);
        makeOreRecipe("certus_quartz", 187, recipeOutput, registries);
        makeOreRecipe("cinnabar", 190, recipeOutput, registries);
        makeVanillaItemRecipe(Items.CLAY, 300, recipeOutput, registries);
        makeVanillaOreRecipe("coal", 1000, recipeOutput, registries);
        makeOreRecipe("cobalt", 163, recipeOutput, registries);
        makeVanillaOreRecipe("copper", 584, recipeOutput, registries);
        makeVanillaOreRecipe("diamond", 218, recipeOutput, registries);
//        makeOreRecipe("dimensional_shard",127,recipeOutput, registries);
        makeOreRecipe("electrotine", 155, recipeOutput, registries);
        makeVanillaOreRecipe("emerald", 156, recipeOutput, registries);
        makeOreRecipe("fluorite", 133, recipeOutput, registries);
        makeOreRecipe("garnet", 200, recipeOutput, registries);
        makeVanillaItemRecipe(Items.GLOWSTONE, 234, recipeOutput, registries);
        makeVanillaOreRecipe("gold", 311, recipeOutput, registries);
        makeVanillaItemRecipe(Items.GRAVEL, 300, recipeOutput, registries);
        makeOreRecipe("heliodor", 200, recipeOutput, registries);
        makeOreRecipe("indicolite", 200, recipeOutput, registries);
        makeOreRecipe("inferium", 190, recipeOutput, registries);
        makeOreRecipe("iolite", 200, recipeOutput, registries);
        makeVanillaOreRecipe("iron", 750, recipeOutput, registries);
        makeOreRecipe("iridium", 40, recipeOutput, registries);
        makeVanillaOreRecipe("lapis", 343, recipeOutput, registries);
        makeOreRecipe("lead", 500, recipeOutput, registries);
        makeOreRecipe("lignite_coal", 212, recipeOutput, registries);
        makeOreRecipe("lithium", 201, recipeOutput, registries);
        makeVanillaItemRecipe(Items.MAGMA_BLOCK, 300, recipeOutput, registries);
        makeOreRecipe("magnesium", 233, recipeOutput, registries);
        makeOreRecipe("malachite", 200, recipeOutput, registries);
        makeOreRecipe("mithril", 169, recipeOutput, registries);
        makeOreRecipe("morganite", 200, recipeOutput, registries);
        makeOreRecipe("monazite", 48, recipeOutput, registries);
        makeVanillaItemRecipe(Items.GILDED_BLACKSTONE, 373, recipeOutput, registries);
        makeVanillaItemRecipe(Items.NETHER_GOLD_ORE, 373, recipeOutput, registries);
        makeVanillaItemRecipe(Items.NETHER_QUARTZ_ORE, 560, recipeOutput, registries);
        makeOreRecipe("nickel", 232, recipeOutput, registries);
        makeOreRecipe("niter", 244, recipeOutput, registries);
        makeVanillaItemRecipe(Items.OBSIDIAN, 300, recipeOutput, registries);
        makeVanillaItemRecipe(Items.CRYING_OBSIDIAN, 50, recipeOutput, registries);
        makeOreRecipe("onyx", 200, recipeOutput, registries);
        makeOreRecipe("opal", 200, recipeOutput, registries);
        makeOreRecipe("osmium", 203, recipeOutput, registries);
        makeVanillaItemRecipe(OccultismBlocks.OTHERSTONE.get().asItem(), 50, recipeOutput, registries);
        makeVanillaItemRecipe(OccultismBlocks.OTHERROCK.get().asItem(), 50, recipeOutput, registries);
        makeOreRecipe("peridot", 200, recipeOutput, registries);
        makeOreRecipe("platinum", 150, recipeOutput, registries);
        makeOreRecipe("prosperity", 155, recipeOutput, registries);
        makeVanillaItemRecipe(Items.REDSTONE_ORE, 515, recipeOutput, registries);
        makeOreRecipe("ruby", 200, recipeOutput, registries);
        makeOreRecipe("salt", 160, recipeOutput, registries);
        makeOreRecipe("sapphire", 200, recipeOutput, registries);
        makeOreRecipe("silver", 381, recipeOutput, registries);
        makeOreRecipe("sulfur", 222, recipeOutput, registries);
        makeOreRecipe("tanzanite", 200, recipeOutput, registries);
        makeOreRecipe("thorium", 222, recipeOutput, registries);
        makeOreRecipe("tin", 602, recipeOutput, registries);
        makeOreRecipe("titanium", 10, recipeOutput, registries);
        makeOreRecipe("topaz", 200, recipeOutput, registries);
        makeOreRecipe("tungsten", 192, recipeOutput, registries);
        makeOreRecipe("uranium", 140, recipeOutput, registries);
        makeOreRecipe("uraninite", 140, recipeOutput, registries);
        makeOreRecipe("dimensional_shard", 127, recipeOutput, registries);
        makeOreRecipe("draconium", 142, recipeOutput, registries);

        makeOreRecipe("bone_fragments", 302, recipeOutput, registries);
        makeOreRecipe("baronyte", 260, recipeOutput, registries);
        makeOreRecipe("blazium", 218, recipeOutput, registries);
        makeOreRecipe("bloodstone", 160, recipeOutput, registries);
        makeOreRecipe("blue_gemstone", 300, recipeOutput, registries);
        makeOreRecipe("charged_runium", 142, recipeOutput, registries);
        makeOreRecipe("crystallite", 500, recipeOutput, registries);
        makeOreRecipe("elecanium", 200, recipeOutput, registries);
        makeOreRecipe("emberstone", 302, recipeOutput, registries);
        makeOreRecipe("gemenyte", 400, recipeOutput, registries);
        makeOreRecipe("ghastly", 200, recipeOutput, registries);
        makeOreRecipe("ghoulish", 180, recipeOutput, registries);
        makeOreRecipe("green_gemstone", 300, recipeOutput, registries);
        makeOreRecipe("jade", 200, recipeOutput, registries);
        makeOreRecipe("jewelyte", 370, recipeOutput, registries);
        makeOreRecipe("limonite", 400, recipeOutput, registries);
        makeOreRecipe("lyon", 160, recipeOutput, registries);
        makeOreRecipe("mystite", 280, recipeOutput, registries);
        makeOreRecipe("ornamyte", 280, recipeOutput, registries);
        makeOreRecipe("purple_gemstone", 300, recipeOutput, registries);
        makeOreRecipe("red_gemstone", 300, recipeOutput, registries);
        makeOreRecipe("runium", 300, recipeOutput, registries);
        makeOreRecipe("shyregem", 302, recipeOutput, registries);
        makeOreRecipe("shyrestone", 302, recipeOutput, registries);
        makeOreRecipe("varsium", 200, recipeOutput, registries);
        makeOreRecipe("white_gemstone", 300, recipeOutput, registries);
        makeOreRecipe("yellow_gemstone", 300, recipeOutput, registries);
        makeOreRecipe("black_quartz", 360, recipeOutput, registries);

        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/" + "xpetrified_ore")), 200, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + "xpetrified_ore")));
        makeOreRecipe("zinc", 186, recipeOutput, registries);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, tag("forbidden_arcanus:runic_stones"), 200, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/runic_stone")));

    }

    public static void eldritch(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        //Raw
        makeStorageRecipe("raw_aethersent", 90, recipeOutput, registries);
        makeStorageRecipe("raw_allthemodium", 30, recipeOutput, registries);
        makeStorageRecipe("raw_aluminum", 90, recipeOutput, registries);
        makeStorageRecipe("raw_azure_silver", 90, recipeOutput, registries);
        makeStorageRecipe("raw_antimony", 90, recipeOutput, registries);
        makeStorageRecipe("raw_cloggrum", 90, recipeOutput, registries);
        makeStorageRecipe("raw_cobalt", 90, recipeOutput, registries);
        makeStorageRecipe("raw_copper", 90, recipeOutput, registries);
        makeStorageRecipe("raw_crimson_iron", 90, recipeOutput, registries);
        makeStorageRecipe("raw_froststeel", 90, recipeOutput, registries);
        makeStorageRecipe("raw_gold", 90, recipeOutput, registries);
        makeStorageRecipe("raw_iesnium", 90, recipeOutput, registries);
        makeStorageRecipe("raw_iridium", 90, recipeOutput, registries);
        makeStorageRecipe("raw_iron", 90, recipeOutput, registries);
        makeStorageRecipe("raw_lead", 90, recipeOutput, registries);
        makeStorageRecipe("raw_nickel", 90, recipeOutput, registries);
        makeStorageRecipe("raw_osmium", 90, recipeOutput, registries);
        makeStorageRecipe("raw_platinum", 90, recipeOutput, registries);
        makeStorageRecipe("raw_silver", 90, recipeOutput, registries);
        makeStorageRecipe("raw_tin", 90, recipeOutput, registries);
        makeStorageRecipe("raw_tungsten", 90, recipeOutput, registries);
        makeStorageRecipe("raw_titanium", 90, recipeOutput, registries);
        makeStorageRecipe("raw_unobtainium", 10, recipeOutput, registries);
        makeStorageRecipe("raw_uranium", 90, recipeOutput, registries);
        makeStorageRecipe("raw_vibranium", 20, recipeOutput, registries);
        makeStorageRecipe("raw_yellorium", 90, recipeOutput, registries);
        makeStorageRecipe("raw_zinc", 90, recipeOutput, registries);
        //Others
        makeStorageRecipe("arcane_crystal", 90, recipeOutput, registries);
        makeStorageRecipe("bauxite", 90, recipeOutput, registries);
        makeStorageRecipe("coal", 90, recipeOutput, registries);
        makeStorageRecipe("dark_gem", 90, recipeOutput, registries);
        makeStorageRecipe("diamond", 90, recipeOutput, registries);
        makeStorageRecipe("emerald", 90, recipeOutput, registries);
        makeStorageRecipe("fluorite", 90, recipeOutput, registries);
        makeStorageRecipe("inferium_essence", 90, recipeOutput, registries);
        makeStorageRecipe("jade", 90, recipeOutput, registries);
        makeStorageRecipe("lapis", 90, recipeOutput, registries);
        makeStorageRecipe("lignite_coal", 90, recipeOutput, registries);
        makeStorageRecipe("monazite", 90, recipeOutput, registries);
        makeStorageRecipe("onyx", 90, recipeOutput, registries);
        makeStorageRecipe("peridot", 90, recipeOutput, registries);
        makeStorageRecipe("prosperity_shard", 90, recipeOutput, registries);
        makeStorageRecipe("redstone", 90, recipeOutput, registries);
        makeStorageRecipe("ruby", 90, recipeOutput, registries);
        makeStorageRecipe("rune", 90, recipeOutput, registries);
        makeStorageRecipe("salt", 90, recipeOutput, registries);
        makeStorageRecipe("sapphire", 90, recipeOutput, registries);
        makeStorageRecipe("sulfur", 90, recipeOutput, registries);
        makeStorageRecipe("soulium_dust", 90, recipeOutput, registries);
        makeStorageRecipe("stellarite", 90, recipeOutput, registries);
        makeStorageRecipe("uraninite", 90, recipeOutput, registries);
        //Don't found in 1.21
        /*
        makeStorageRecipe("agate", 90, recipeOutput, registries);
        makeStorageRecipe("amber", 90, recipeOutput, registries);
        makeStorageRecipe("aquamarine", 90, recipeOutput, registries);
        makeStorageRecipe("ardite", 90, recipeOutput, registries);
        makeStorageRecipe("beryl", 90, recipeOutput, registries);
        makeStorageRecipe("boron", 90, recipeOutput, registries);
        makeStorageRecipe("cinnabar", 90, recipeOutput, registries);
        makeStorageRecipe("dimensional_shard",90,recipeOutput, registries);
        makeStorageRecipe("electrotine", 90, recipeOutput, registries);
        makeStorageRecipe("garnet", 90, recipeOutput, registries);
        makeStorageRecipe("heliodor", 90, recipeOutput, registries);
        makeStorageRecipe("indicolite", 90, recipeOutput, registries);
        makeStorageRecipe("iolite", 90, recipeOutput, registries);
        makeStorageRecipe("lithium", 90, recipeOutput, registries);
        makeStorageRecipe("magnesium", 90, recipeOutput, registries);
        makeStorageRecipe("malachite", 90, recipeOutput, registries);
        makeStorageRecipe("mithril", 90, recipeOutput, registries);
        makeStorageRecipe("morganite", 90, recipeOutput, registries);
        makeStorageRecipe("niter", 90, recipeOutput, registries);
        makeStorageRecipe("opal", 90, recipeOutput, registries);
        makeStorageRecipe("tanzanite", 90, recipeOutput, registries);
        makeStorageRecipe("thorium", 90, recipeOutput, registries);
        makeStorageRecipe("topaz", 90, recipeOutput, registries);
        makeStorageRecipe("dimensional_shard", 90, recipeOutput, registries);
        makeStorageRecipe("draconium", 90, recipeOutput, registries);
        makeStorageRecipe("bone_fragments", 90, recipeOutput, registries);
        makeStorageRecipe("baronyte", 90, recipeOutput, registries);
        makeStorageRecipe("blazium", 90, recipeOutput, registries);
        makeStorageRecipe("bloodstone", 90, recipeOutput, registries);
        makeStorageRecipe("blue_gemstone", 90, recipeOutput, registries);
        makeStorageRecipe("charged_runium", 90, recipeOutput, registries);
        makeStorageRecipe("crystallite", 90, recipeOutput, registries);
        makeStorageRecipe("elecanium", 90, recipeOutput, registries);
        makeStorageRecipe("emberstone", 90, recipeOutput, registries);
        makeStorageRecipe("gemenyte", 90, recipeOutput, registries);
        makeStorageRecipe("ghastly", 90, recipeOutput, registries);
        makeStorageRecipe("ghoulish", 90, recipeOutput, registries);
        makeStorageRecipe("green_gemstone", 90, recipeOutput, registries);
        makeStorageRecipe("jewelyte", 90, recipeOutput, registries);
        makeStorageRecipe("limonite", 90, recipeOutput, registries);
        makeStorageRecipe("lyon", 90, recipeOutput, registries);
        makeStorageRecipe("mystite", 90, recipeOutput, registries);
        makeStorageRecipe("ornamyte", 90, recipeOutput, registries);
        makeStorageRecipe("purple_gemstone", 90, recipeOutput, registries);
        makeStorageRecipe("red_gemstone", 90, recipeOutput, registries);
        makeStorageRecipe("runium", 90, recipeOutput, registries);
        makeStorageRecipe("shyregem", 90, recipeOutput, registries);
        makeStorageRecipe("shyrestone", 90, recipeOutput, registries);
        makeStorageRecipe("varsium", 90, recipeOutput, registries);
        makeStorageRecipe("white_gemstone", 90, recipeOutput, registries);
        makeStorageRecipe("yellow_gemstone", 90, recipeOutput, registries);
         */
        makeGemEldritchOutputRecipe("sal_ammoniac", 90, 9, recipeOutput, registries);
        makeGemEldritchOutputRecipe("amethyst", 90, 9, recipeOutput, registries);
        makeGemEldritchOutputRecipe("quartz", 90, 9, recipeOutput, registries);
        makeGemEldritchOutputRecipe("black_quartz", 90, 9, recipeOutput, registries);
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, Tags.Items.ORES_NETHERITE_SCRAP, 90, 9, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/ancient_debris")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, Tags.Items.DUSTS_GLOWSTONE, 90, 9, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/glowstone_dust")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismTags.Items.CLAY, 90, 9, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/clay")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismItems.MINING_DIMENSION_CORE_PIECE, 1, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/mining_dim_core")));
    }

    //  Item-based recipes where the item is not available in our dev env are not great because they would need a separate handling
    //  instead use a tag - we can pre-fill it with an optional content!
//    public static void makeModOreItemRecipe(Identifier item, int weight, RecipeOutput consumer) {
//        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES), item, weight)
//                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
//                .itemExists()
//                .save(consumer, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + item.getPath()));
//    }

    public static void makeVanillaItemRecipe(Item type, int weight, RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, type, weight, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + getItemName(type))));
    }

    public static void makeVanillaOreRecipe(String type, int weight, RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/" + type)), weight, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + type + "_ore")));
    }

    public static void makeOreRecipe(String type, int weight, RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ORES, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/" + type)), weight, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + type + "_ore")));
    }

    public static void makeStorageRecipe(String type, int weight, RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/" + type)), weight, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/" + type)));
    }
    public static void makeGemEldritchOutputRecipe(String type, int weight, int amount, RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.ELDRITCH, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "gems/" + type)), weight, amount, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/eldritch/" + type)));
    }

    public static void deeps(RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_COAL_ORE, 1000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_coal_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_COPPER_ORE, 584, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_copper_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_DIAMOND_ORE, 218, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_diamond_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_EMERALD_ORE, 156, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_emerald_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_GOLD_ORE, 311, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_gold_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_IRON_ORE, 750, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_iron_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_LAPIS_ORE, 343, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_lapis_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, Items.DEEPSLATE_REDSTONE_ORE, 515, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_redstone_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.DEEPS, OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), 381, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/deeps/deepslate_silver_ore")));


    }

    public static void master_resources(RecipeOutput consumer, HolderLookup.Provider registries) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, Items.ANCIENT_DEBRIS, 100, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/master/ancient_debris")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismBlocks.IESNIUM_ORE.get(), 100, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/master/iesnium_ore")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismItems.MINING_DIMENSION_CORE_PIECE, 1, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/master/mining_dim_core")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/stellarite")), 50, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/master/stellarite")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/stella_arcanum")), 100, registries)
                .unlockedBy("has_stella_arcanum_ore", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/stella_arcanum")), registries))
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/master/stella_arcanum")));
    }

    public static void basic_resources(RecipeOutput consumer, HolderLookup.Provider registries) {

        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.STONE, 10000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/stone")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.ANDESITE, 7000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/andesite")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.DIORITE, 7000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/diorite")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.GRANITE, 7000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/granite")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.DEEPSLATE, 5000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/deepslate")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.MOSSY_COBBLESTONE, 3000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/mossy_cobblestone")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.MOSSY_STONE_BRICKS, 3000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/mossy_stone_bricks")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.NETHERRACK, 1000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/netherrack")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.BASALT, 1000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/basalt")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.BLACKSTONE, 1000, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/blackstone")));
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.BASIC_RESOURCES, Items.END_STONE, 30, registries)
                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/basic_resources/end_stone")));
    }

    public static TagKey<Item> tag(String tag) {
        return TagKey.create(Registries.ITEM, Identifier.parse(tag));
    }

    // Helper method to get item name for recipes
    private static String getItemName(Item item) {
        return item.toString();
    }

}