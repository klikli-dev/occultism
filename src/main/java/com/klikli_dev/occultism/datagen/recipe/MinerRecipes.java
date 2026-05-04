package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.MinerRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Items.Miners;
import net.minecraft.advancements.criterion.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

// This class is now a utility class - no constructor needed
public class MinerRecipes {

    public static void minerRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        // Using miner tags to limit ores based on tool tier
        makeOreRecipe("alexandrite", 25, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("allthemodium", 10, pRecipeOutput, registries, Miners.NETHERITE); //weight = netherite*emerald/diamond
        makeOreRecipe("aluminum", 4470, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("ammolite", 31, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("anglesite", 108, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("antimony", 2024, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("aquamarine", 45, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("arcane_crystal", 141, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("azure_silver", 352, pRecipeOutput, registries, Miners.NETHERITE);
        makeOreRecipe("bauxite", 447, pRecipeOutput, registries, Miners.BASIC); //10% of aluminum
        makeOreRecipe("benitoite", 490, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("black_diamond", 23, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("black_quartz", 1006, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("blazing_quartz", 397, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("blue_xychorium", 275, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("bort", 227, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("brilliant", 94, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("carnelian", 25, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("chaos", 150, pRecipeOutput, registries, Miners.DIAMOND);
        makeOreRecipe("cinnabar", 230, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("citrine", 20, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("coal", 15072, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("copper", 9002, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("crimson_iron", 476, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("cthonic_gold", 15, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("dark_gem", 384, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("dark_xychorium", 268, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("diamond", 946, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("dimensional_shard", 1216, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("draconium", 348, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("emerald", 181, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("firestone", 1, pRecipeOutput, registries, Miners.DIAMOND);
        makeOreRecipe("fluorite", 2388, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("garnet", 48, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("gold", 3494, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("green_xychorium", 252, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("heliodor", 39, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("iesnium", 100, pRecipeOutput, registries, Miners.DIAMOND); //double netherite weight
        makeOreRecipe("inferium", 3283, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("iolite", 26, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("iridium", 72, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("iron", 5986, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("kyanite", 33, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("lapis", 973, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("lead", 5168, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("light_xychorium", 288, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("lignite_coal", 5660, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("mithril", 79, pRecipeOutput, registries, Miners.NETHERITE);
        makeOreRecipe("moldavite", 24, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("monazite", 38, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("naquadah", 291, pRecipeOutput, registries, Miners.NETHERITE);
        makeOreRecipe("netherite_scrap", 50, pRecipeOutput, registries, Miners.DIAMOND);
        makeOreRecipe("quartz", 5360, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("nickel", 2028, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("opal", 50, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("osmium", 2979, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("pearl", 34, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("peridot", 149, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("platinum", 335, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("prosperity", 2289, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("red_xychorium", 257, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("redstone", 1477, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("replica", 245, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("rose_quartz", 30, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("ruby", 127, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("runic", 107, pRecipeOutput, registries, Miners.DIAMOND);
        makeOreRecipe("sal_ammoniac", 2395, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("salt", 730, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("sapphire", 103, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("silver", 2320, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("soulium", 694, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("soulstone", 276, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("stellarite", 12, pRecipeOutput, registries, Miners.DIAMOND);
        makeOreRecipe("sulfur", 185, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("tanzanite", 18, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("tin", 5008, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("titanium", 40, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("topaz", 52, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("tungsten", 575, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("turquoise", 35, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("unobtainium", 1, pRecipeOutput, registries, Miners.ELDRITCH); //min weight possible
        makeOreRecipe("uraninite", 1328, pRecipeOutput, registries, Miners.IRON); //merged poor, regular and dense
        makeOreRecipe("uranium", 1371, pRecipeOutput, registries, Miners.BASIC);
        makeOreRecipe("vibranium", 6, pRecipeOutput, registries, Miners.ELDRITCH); //half allthemodium+unobtainium
        makeOreRecipe("white_diamond", 29, pRecipeOutput, registries, Miners.IRON);
        makeOreRecipe("zinc", 3134, pRecipeOutput, registries, Miners.BASIC);

        makeItemRecipe(Items.GLOWSTONE, 5360, pRecipeOutput, registries, Miners.BASIC); //copy quartz weight
        makeItemRecipe(OccultismItems.MINING_DIMENSION_CORE_PIECE.asItem(), 1, pRecipeOutput, registries, Miners.NETHERITE); //min weight possible

        MinerRecipeBuilder.minerRecipe(OccultismItems.DEBUG_WAND.get(), OccultismBlocks.OTHERSTONE.get(), 200, registries)
                .unlockedBy("has_miner", TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/debug/otherstone")));
        MinerRecipeBuilder.minerRecipe(OccultismItems.DEBUG_WAND.get(), OccultismBlocks.OTHERROCK.get(), 200, registries)
                .unlockedBy("has_miner", TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/debug/otherrock")));
    }

    public static void makeOreRecipe(String type, int weight, RecipeOutput consumer, Provider registries, TagKey<Item> tag) {
        MinerRecipeBuilder.minerRecipe(tag, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/" + type)), weight, registries)
            .unlockedBy("has_miner", TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
            .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.parse(tag.location() + "/" + type)));
    }

    public static void makeItemRecipe(Item type, int weight, RecipeOutput consumer, Provider registries, TagKey<Item> tag) {
        MinerRecipeBuilder.minerRecipe(tag, type, weight, registries)
                .unlockedBy("has_miner", TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, ResourceKey.create(Registries.RECIPE, Identifier.parse(tag.location() + "/" + getItemName(type))));
    }

    // Helper method to get item name for recipes
    private static String getItemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    //  Item-based recipes where the item is not available in our dev env are not great because they would need a separate handling
    //  instead use a tag - we can pre-fill it with an optional content!
//    public static void makeModOreItemRecipe(Identifier item, int weight, RecipeOutput consumer) {
//        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.IRON), item, weight)
//                .unlockedBy("has_miner", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.MAGIC_LAMP_EMPTY.get()))
//                .itemExists()
//                .save(consumer, Identifier.fromNamespaceAndPath(Occultism.MODID, "miner/ores/" + item.getPath()));
//    }
}