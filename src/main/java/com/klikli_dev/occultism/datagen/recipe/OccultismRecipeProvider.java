package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.datagen.recipe.builders.CrushingRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.MinerRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.SpiritFireRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.SpiritTradeRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class OccultismRecipeProvider extends RecipeProvider {
    public OccultismRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    private static void metalRecipes(RecipeOutput pRecipeOutput) {
        // Iesnium metal
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.IESNIUM_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_iesnium_ingot", has(OccultismTags.Items.IESNIUM_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_IESNIUM)
                .unlockedBy("has_iesnium_block", has(OccultismTags.Items.STORAGE_BLOCK_IESNIUM))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_ingot_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.IESNIUM_NUGGET)
                .unlockedBy("has_iesnium_nugget", has(OccultismTags.Items.IESNIUM_NUGGET))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.IESNIUM_NUGGET.get(), 9)
                .requires(OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_iesnium_ingot", has(OccultismTags.Items.IESNIUM_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_nugget"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismBlocks.RAW_IESNIUM_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.RAW_IESNIUM)
                .unlockedBy("has_raw_iesnium", has(OccultismTags.Items.RAW_IESNIUM))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_iesnium_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.RAW_IESNIUM.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM)
                .unlockedBy("has_raw_iesnium_block", has(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_iesnium_ingot_from_block"));

        // Silver metal
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.SILVER_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_SILVER)
                .unlockedBy("has_silver_block", has(OccultismTags.Items.STORAGE_BLOCK_SILVER))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_ingot_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.SILVER_NUGGET)
                .unlockedBy("has_silver_nugget", has(OccultismTags.Items.SILVER_NUGGET))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.SILVER_NUGGET.get(), 9)
                .requires(OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_nugget"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismBlocks.RAW_SILVER_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.RAW_SILVER)
                .unlockedBy("has_raw_silver", has(OccultismTags.Items.RAW_SILVER))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_silver_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.RAW_SILVER.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER)
                .unlockedBy("has_raw_silver_block", has(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_silver_ingot_from_block"));
    }

    private static void blastingRecipes(RecipeOutput pRecipeOutput) {
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 100)
                .unlockedBy("has_copper_dust", has(OccultismTags.Items.COPPER_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/copper_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.GOLD_DUST), RecipeCategory.MISC, Items.GOLD_INGOT, 0.7f, 100)
                .unlockedBy("has_gold_dust", has(OccultismTags.Items.GOLD_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/gold_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.IESNIUM_ORE), RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_iesnium_ore", has(OccultismTags.Items.IESNIUM_ORE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/iesnium_ingot"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.IESNIUM_DUST), RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_iesnium_dust", has(OccultismTags.Items.IESNIUM_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/iesnium_ingot_from_dust"));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.RAW_IESNIUM), RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_raw_iesnium", has(OccultismTags.Items.RAW_IESNIUM))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/iesnium_ingot_from_raw"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.IRON_DUST), RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 100)
                .unlockedBy("has_iron_dust", has(OccultismTags.Items.IRON_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/iron_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.SILVER_ORE), RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_silver_ore", has(OccultismTags.Items.SILVER_ORE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/silver_ingot"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.SILVER_DUST), RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_silver_dust", has(OccultismTags.Items.SILVER_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/silver_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.RAW_SILVER), RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_raw_silver", has(OccultismTags.Items.RAW_SILVER))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blasting/silver_ingot_from_raw"));
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        blastingRecipes(pRecipeOutput);
        this.craftingRecipes(pRecipeOutput);
        this.smeltingRecipes(pRecipeOutput);
        this.crushingRecipes(pRecipeOutput);
        this.miningRecipes(pRecipeOutput);
        this.spiritFireRecipes(pRecipeOutput);
        this.spiritTradeRecipes(pRecipeOutput);
        this.ritualRecipes(pRecipeOutput);

    }

    private void ritualRecipes(RecipeOutput recipeOutput) {
        RitualRecipes.ritualRecipes(recipeOutput);
    }

    private void spiritTradeRecipes(RecipeOutput pRecipeOutput) {
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get()), new ItemStack(OccultismBlocks.OTHERWORLD_SAPLING.get()))
                .unlockedBy("has_otherworld_sapling_natural", has(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/otherworld_sapling"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.STONES), new ItemStack(OccultismBlocks.OTHERSTONE.get(), 2))
                .unlockedBy("has_stone", has(Tags.Items.STONES))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/stone_to_otherstone"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.STONES), new ItemStack(OccultismBlocks.OTHERSTONE.get(), 4))
                .unlockedBy("has_stone", has(Tags.Items.STONES))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/test"));

    }

    private void spiritFireRecipes(RecipeOutput pRecipeOutput) {
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Tags.Items.FEATHERS), new ItemStack(OccultismItems.AWAKENED_FEATHER.get()))
                .unlockedBy("has_feather", has(Tags.Items.FEATHERS))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/awakened_feather"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.WRITABLE_BOOK), new ItemStack(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .unlockedBy("has_writable_book", has(Items.WRITABLE_BOOK))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/book_of_binding_empty"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_GOLD_IMPURE.get()), new ItemStack(OccultismItems.CHALK_GOLD.get()))
                .unlockedBy("has_chalk_gold_impure", has(OccultismItems.CHALK_GOLD_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_gold"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_PURPLE_IMPURE.get()), new ItemStack(OccultismItems.CHALK_PURPLE.get()))
                .unlockedBy("has_chalk_purple_impure", has(OccultismItems.CHALK_PURPLE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_purple"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_RED_IMPURE.get()), new ItemStack(OccultismItems.CHALK_RED.get()))
                .unlockedBy("has_chalk_red_impure", has(OccultismItems.CHALK_RED_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_red"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_WHITE_IMPURE.get()), new ItemStack(OccultismItems.CHALK_WHITE.get()))
                .unlockedBy("has_chalk_white_impure", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_white"));

        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get()), new ItemStack(OccultismItems.CHALK_LIGHT_GRAY.get()))
                .unlockedBy("has_chalk_light_gray_impure", has(OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_light_gray"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_GRAY_IMPURE.get()), new ItemStack(OccultismItems.CHALK_GRAY.get()))
                .unlockedBy("has_chalk_gray_impure", has(OccultismItems.CHALK_GRAY_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_gray"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_BLACK_IMPURE.get()), new ItemStack(OccultismItems.CHALK_BLACK.get()))
                .unlockedBy("has_chalk_black_impure", has(OccultismItems.CHALK_BLACK_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_black"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_BROWN_IMPURE.get()), new ItemStack(OccultismItems.CHALK_BROWN.get()))
                .unlockedBy("has_chalk_brown_impure", has(OccultismItems.CHALK_BROWN_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_brown"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_ORANGE_IMPURE.get()), new ItemStack(OccultismItems.CHALK_ORANGE.get()))
                .unlockedBy("has_chalk_orange_impure", has(OccultismItems.CHALK_ORANGE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_orange"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_LIME_IMPURE.get()), new ItemStack(OccultismItems.CHALK_LIME.get()))
                .unlockedBy("has_chalk_lime_impure", has(OccultismItems.CHALK_LIME_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_lime"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_GREEN_IMPURE.get()), new ItemStack(OccultismItems.CHALK_GREEN.get()))
                .unlockedBy("has_chalk_green_impure", has(OccultismItems.CHALK_GREEN_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_green"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_CYAN_IMPURE.get()), new ItemStack(OccultismItems.CHALK_CYAN.get()))
                .unlockedBy("has_chalk_cyan_impure", has(OccultismItems.CHALK_CYAN_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_cyan"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get()), new ItemStack(OccultismItems.CHALK_LIGHT_BLUE.get()))
                .unlockedBy("has_chalk_light_blue_impure", has(OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_light_blue"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_BLUE_IMPURE.get()), new ItemStack(OccultismItems.CHALK_BLUE.get()))
                .unlockedBy("has_chalk_blue_impure", has(OccultismItems.CHALK_BLUE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_blue"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_MAGENTA_IMPURE.get()), new ItemStack(OccultismItems.CHALK_MAGENTA.get()))
                .unlockedBy("has_chalk_magenta_impure", has(OccultismItems.CHALK_MAGENTA_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_magenta"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_PINK_IMPURE.get()), new ItemStack(OccultismItems.CHALK_PINK.get()))
                .unlockedBy("has_chalk_pink_impure", has(OccultismItems.CHALK_PINK_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/chalk_pink"));

        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Blocks.ANDESITE), new ItemStack(OccultismBlocks.OTHERSTONE.get()))
                .unlockedBy("has_andesite", has(Blocks.ANDESITE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/otherstone"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismBlocks.OTHERWORLD_LOG.get()), new ItemStack(OccultismItems.OTHERWORLD_ASHES.get()))
                .unlockedBy("has_otherworld_log", has(OccultismBlocks.OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/otherworld_ashes"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()), new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .unlockedBy("has_demons_dream_essence", has(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/otherworld_essence"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.OAK_SAPLING), new ItemStack(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get()))
                .unlockedBy("has_oak_sapling", has(Items.OAK_SAPLING))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/otherworld_sapling_natural"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.BLACK_DYE), new ItemStack(OccultismItems.PURIFIED_INK.get()))
                .unlockedBy("has_black_dye", has(Items.BLACK_DYE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/purified_ink"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Tags.Items.GEMS_DIAMOND), new ItemStack(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/spirit_attuned_gem"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.BOOK), new ItemStack(OccultismItems.TABOO_BOOK.get()))
                .unlockedBy("has_book", has(Items.BOOK))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/taboo_book"));
    }

    private void miningRecipes(RecipeOutput pRecipeOutput) {
        MinerRecipeBuilder.minerRecipe(OccultismTags.Items.Miners.MASTER, OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/stella_arcanum")), 100)
                .unlockedBy("has_stella_arcanum_ore", has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/stella_arcanum"))))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/master/stella_arcanum"));
        MinerRecipes.basic_resources(pRecipeOutput);
        MinerRecipes.deeps(pRecipeOutput);
        MinerRecipes.master_resources(pRecipeOutput);
        MinerRecipes.ores(pRecipeOutput);
        MinerRecipeBuilder.minerRecipe(OccultismItems.DEBUG_WAND.get(), OccultismBlocks.OTHERSTONE.get(), 200)
                .unlockedBy("has_miner", has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miner/debug_wand"));
    }

    private void crushingRecipes(RecipeOutput pRecipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.Items.DATURA_CROP, OccultismTags.Items.DATURA_SEEDS, 200)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .setAllowEmpty(false)
                .setResultAmount(2)
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/datura"));
        this.crushingMetalRecipe("allthemodium", pRecipeOutput);
        this.crushingMetalRecipe("iesnium", pRecipeOutput);
        this.crushingMetalRecipe("aluminum", pRecipeOutput);
        this.crushingMetalRecipe("signalum", pRecipeOutput);
        this.crushingMetalRecipe("uranium", pRecipeOutput);
        this.crushingMetalRecipe("graphite", pRecipeOutput);
        this.crushingMetalRecipe("azure_silver", pRecipeOutput);
        this.crushingMetalRecipe("zinc", pRecipeOutput);
        this.crushingMetalRecipe("lumium", pRecipeOutput);
        this.crushingMetalRecipe("osmium", pRecipeOutput);
        this.crushingMetalRecipe("nickel", pRecipeOutput);
        this.crushingMetalRecipe("lead", pRecipeOutput);
        this.crushingMetalRecipe("bronze", pRecipeOutput);
        this.crushingMetalRecipe("cobalt", pRecipeOutput);
        this.crushingMetalRecipe("unobtainium", pRecipeOutput);
        this.crushingMetalRecipe("tungsten", pRecipeOutput);
        this.crushingMetalRecipe("iridium", pRecipeOutput);
        this.crushingMetalRecipe("steel", pRecipeOutput);
        this.crushingMetalRecipe("enderium", pRecipeOutput);
        this.crushingMetalRecipe("electrum", pRecipeOutput);
        this.crushingMetalRecipe("constantan", pRecipeOutput);
        this.crushingMetalRecipe("tin", pRecipeOutput);
        this.crushingMetalRecipe("netherite", pRecipeOutput);
        this.crushingMetalRecipe("brass", pRecipeOutput);
        this.crushingMetalRecipe("crimson_iron", pRecipeOutput);
        this.crushingMetalRecipe("platinum", pRecipeOutput);
        this.crushingMetalRecipe("invar", pRecipeOutput);
        this.crushingMetalRecipe("vibranium", pRecipeOutput);
        this.crushingMetalRecipe("silver", pRecipeOutput);
        this.crushingMetalRecipe("copper", pRecipeOutput);
        this.crushingMetalRecipe("pewter", pRecipeOutput);
        this.crushingMetalRecipe("mithril", pRecipeOutput);
        this.crushingMetalRecipe("gold", pRecipeOutput);
        this.crushingMetalRecipe("quicksilver", pRecipeOutput);
        this.crushingMetalRecipe("iron", pRecipeOutput);

        this.crushingGemRecipe("dark_gem", pRecipeOutput);
        this.crushingGemRecipe("diamond", pRecipeOutput);
        this.crushingGemRecipe("emerald", pRecipeOutput);
        this.crushingGemRecipe("lapis", pRecipeOutput);
        this.crushingGemRecipe("quartz", pRecipeOutput);
        this.crushingGemRecipe("coal", pRecipeOutput);
        this.crushingGemRecipe("redstone", pRecipeOutput);
        this.crushingGemRecipe("apatite", pRecipeOutput);
        this.crushingGemRecipe("sulfur", pRecipeOutput);
        this.crushingGemRecipe("fluorite", pRecipeOutput);
        this.crushingGemRecipe("cinnabar", pRecipeOutput);
        this.crushingGemRecipe("amber", pRecipeOutput);
        this.crushingGemRecipe("certus_quartz", pRecipeOutput);
        this.crushingGemRecipe("charged_certus_quartz", pRecipeOutput);
        this.crushingGemRecipe("peridot", pRecipeOutput);
        this.crushingGemRecipe("ruby", pRecipeOutput);
        this.crushingGemRecipe("sapphire", pRecipeOutput);
        this.crushingGemRecipe("topaz", pRecipeOutput);
        this.crushingGemRecipe("arcane_crystal", pRecipeOutput);

        CrushingRecipeBuilder.crushingRecipe(Tags.Items.RODS_BLAZE, Items.BLAZE_POWDER, 200)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_blaze_rod", has(Tags.Items.RODS_BLAZE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/blaze_powder_from_rod"));

        CrushingRecipeBuilder.crushingRecipe(Tags.Items.OBSIDIANS, OccultismTags.Items.OBSIDIAN_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIANS))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/obsidian_dust"));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.END_STONES, OccultismTags.Items.END_STONE_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_end_stone", has(Tags.Items.END_STONES))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/end_stone_dust"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.Items.OTHERSTONE, OccultismTags.Items.OTHERCOBBLESTONE, 200)
                .unlockedBy("has_otherstone", has(OccultismTags.Items.OTHERSTONE))
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/othercobblestone"));

    }

    private void crushingGemRecipe(String gemName, RecipeOutput recipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + gemName)), OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + gemName)), 200)
                .unlockedBy("has_" + gemName, has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + gemName))))
                .setResultAmount(4)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/" + gemName + "_dust"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "gems/" + gemName)), OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + gemName)), 200)
                .unlockedBy("has_" + gemName + "_gem", has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "gems/" + gemName))))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/" + gemName + "_dust_from_gem"));

    }

    private void crushingMetalRecipe(String metalName, RecipeOutput recipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + metalName)), OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + metalName)), 200)
                .unlockedBy("has_" + metalName, has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/" + metalName))))
                .setResultAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/" + metalName)), OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + metalName)), 200)
                .unlockedBy("has_raw_" + metalName, has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/" + metalName))))
                .setResultAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_raw"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_" + metalName)), OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + metalName)), 1600)
                .unlockedBy("has_raw_" + metalName + "_block", has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_" + metalName))))
                .setResultAmount(18)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_raw_block"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ingots/" + metalName)), OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + metalName)), 200)
                .unlockedBy("has_" + metalName + "_ingot", has(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ingots/" + metalName))))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_ingot"));


    }

    private void craftingRecipes(RecipeOutput pRecipeOutput) {
        SpecialRecipeBuilder.special(BoundBookOfBindingRecipe::new).save(pRecipeOutput,
                ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/bound_book_of_binding"));

        metalRecipes(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .requires(OccultismItems.AWAKENED_FEATHER.get())
                .requires(OccultismItems.PURIFIED_INK.get())
                .requires(OccultismItems.TABOO_BOOK.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_empty"));

        // Afrit
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .pattern("cpf")
                .pattern("pbp")
                .pattern(" p ")
                .define('p', Tags.Items.DYES_YELLOW)
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_afrit"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .pattern(" p ")
                .pattern("pbp")
                .pattern(" p ")
                .define('p', Tags.Items.DYES_YELLOW)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_afrit_from_empty"));
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_afrit", has(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
//                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_afrit"));

        // Djinni
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .pattern("cgf")
                .pattern("gbg")
                .pattern(" g ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('g', Tags.Items.DYES_PURPLE)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_djinni"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .define('g', Tags.Items.DYES_PURPLE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_djinni_from_empty"));

//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_djinni", has(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
//                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_djinni"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .unlockedBy("has_book_of_calling_djinni", has(OccultismTags.Items.BOOK_OF_CALLING_DJINNI))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_djinni_from_calling"));

        // Foliot
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .pattern("cwf")
                .pattern("wbw")
                .pattern(" w ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('w', Tags.Items.DYES_BLUE)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_foliot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .pattern(" w ")
                .pattern("wbw")
                .pattern(" w ")
                .define('w', Tags.Items.DYES_BLUE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_foliot_from_empty"));

//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_foliot", has(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
//                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_foliot"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .unlockedBy("has_book_of_calling_foliot", has(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_foliot_from_calling"));

        // Marid
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_MARID.get())
                .pattern("cof")
                .pattern("pbp")
                .pattern(" o ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('o', Tags.Items.DYES_GREEN)
                .define('p', Tags.Items.DYES_GREEN)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_marid"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_MARID.get())
                .pattern(" o ")
                .pattern("pbp")
                .pattern(" o ")
                .define('o', Tags.Items.DYES_GREEN)
                .define('p', Tags.Items.DYES_GREEN)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_marid_from_empty"));

//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_MARID.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_marid", has(OccultismItems.BOOK_OF_BINDING_MARID.get()))
//                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_marid"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(Items.FURNACE)
                .unlockedBy("has_book_of_binding_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_djinni_manage_machine"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismItems.BRUSH.get())
                .unlockedBy("has_book_of_binding_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_cleaner"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismTags.Items.METAL_AXES)
                .unlockedBy("has_book_of_binding_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_lumberjack"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(Tags.Items.CHESTS)
                .unlockedBy("has_book_of_binding_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_transport_items"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BRUSH.get())
                .pattern("ppp")
                .pattern("wws")
                .define('p', ItemTags.PLANKS)
                .define('w', ItemTags.WOOL)
                .define('s', Tags.Items.STRINGS)
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/brush"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, OccultismItems.BUTCHER_KNIFE.get())
                .pattern(" is")
                .pattern("is ")
                .pattern("s  ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/butcher_knife"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE.get())
                .pattern("s")
                .pattern("t")
                .define('s', Tags.Items.STRINGS)
                .define('t', OccultismTags.Items.TALLOW)
                .unlockedBy("has_tallow", has(OccultismTags.Items.TALLOW))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_WHITE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_WHITE)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_white"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_LIGHT_GRAY)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_light_gray"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_GRAY.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_GRAY)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_gray"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_BLACK.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_BLACK)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_black"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_BROWN.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_BROWN)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_brown"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_RED.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_red"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_ORANGE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_ORANGE)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_orange"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_YELLOW.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_yellow"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_LIME.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_LIME)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_lime"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_GREEN.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_GREEN)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_green"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_CYAN.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_CYAN)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_cyan"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_BLUE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_blue"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_light_blue"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_PINK.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_PINK)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_pink"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_MAGENTA.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_MAGENTA)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_magenta"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_PURPLE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_PURPLE)
                .unlockedBy("has_large_candle", has(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_purple"));

        // Chalks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_GOLD_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(OccultismTags.Items.GOLD_DUST)
                .requires(OccultismTags.Items.GOLD_DUST)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_gold_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_PURPLE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.END_STONE_DUST)
                .requires(OccultismTags.Items.OBSIDIAN_DUST)
                .requires(OccultismTags.Items.OBSIDIAN_DUST)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_purple_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_RED_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.AFRIT_ESSENCE.get())
                .requires(Items.BLAZE_POWDER)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_red_impure"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.SILVER_DUST)
                .requires(OccultismTags.Items.IRON_DUST)
                .requires(Items.CALCITE)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_light_gray_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_GRAY_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Tags.Items.GUNPOWDERS)
                .requires(Items.WIND_CHARGE)
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_gray_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_BLACK_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.NETHERITE_INGOT)
                .requires(Items.WITHER_ROSE)
                .requires(Items.BLACKSTONE)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_black_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_BROWN_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.POINTED_DRIPSTONE)
                .requires(Items.ARMADILLO_SCUTE)
                .requires(Items.RABBIT_FOOT)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_brown_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_ORANGE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.TORCHFLOWER)
                .requires(Items.GLOW_BERRIES)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_orange_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_LIME_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Tags.Items.SLIME_BALLS)
                .requires(Items.EMERALD)
                .requires(Items.EXPERIENCE_BOTTLE)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_lime_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_GREEN_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Tags.Items.SEEDS)
                .requires(ItemTags.SAPLINGS)
                .requires(ItemTags.LEAVES)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_green_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_CYAN_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.IESNIUM_DUST)
                .requires(Items.GLOW_INK_SAC)
                .requires(Items.ECHO_SHARD)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_cyan_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.ICE)
                .requires(Items.PACKED_ICE)
                .requires(Items.BLUE_ICE)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_light_blue_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_BLUE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.HEART_OF_THE_SEA)
                .requires(Tags.Items.GEMS_LAPIS)
                .requires(Items.TUBE_CORAL)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_blue_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_MAGENTA_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.DRAGON_BREATH)
                .requires(Tags.Items.GEMS_AMETHYST)
                .requires(Items.END_CRYSTAL)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_magenta_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.CHALK_PINK_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Items.PINK_PETALS)
                .requires(Items.PORKCHOP)
                .requires(Items.CHERRY_LEAVES)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_pink_impure"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.CHALK_WHITE_IMPURE.get())
                .pattern("xy")
                .pattern("xy")
                .pattern("xy")
                .define('x', OccultismItems.BURNT_OTHERSTONE.get())
                .define('y', OccultismItems.OTHERWORLD_ASHES.get())
                .unlockedBy("has_ashes", has(OccultismItems.OTHERWORLD_ASHES.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_white_impure"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, OccultismItems.DEMONS_DREAM_ESSENCE.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p',
                        Ingredient.fromValues(Stream.of(
                                        new Ingredient.TagValue(OccultismTags.Items.DATURA_CROP),
                                        new Ingredient.TagValue(OccultismTags.Items.DATURA_SEEDS)
                                )
                        )
                )
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/demons_dream_essence_from_fruit_or_seed"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .requires(OccultismTags.Items.DATURA_SEEDS)
                .requires(OccultismTags.Items.BOOKS)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_SEEDS))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/dictionary_of_spirits"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, OccultismItems.DIVINATION_ROD.get())
                .pattern("xyx")
                .pattern("x x")
                .pattern(" x ")
                .define('x', Tags.Items.RODS_WOODEN)
                .define('y', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/divination_rod"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, OccultismItems.OTHERWORLD_GOGGLES.get())
                .pattern(" l ")
                .pattern("lil")
                .pattern(" f ")
                .define('l', Tags.Items.LEATHERS)
                .define('i', OccultismItems.INFUSED_LENSES.get())
                .define('f', OccultismItems.LENS_FRAME.get())
                .unlockedBy("has_infused_lenses", has(OccultismItems.INFUSED_LENSES.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/goggles"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .pattern("ggg")
                .pattern("gbg")
                .pattern("ggg")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('b', OccultismBlocks.SACRIFICIAL_BOWL.get())
                .unlockedBy("has_sacrificial_bowl", has(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/golden_sacrificial_bowl"));


        // Iesnium tool
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, OccultismItems.IESNIUM_PICKAXE.get())
                .pattern("iii")
                .pattern(" s ")
                .pattern(" s ")
                .define('i', OccultismTags.Items.IESNIUM_INGOT)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iesnium_ingot", has(OccultismTags.Items.IESNIUM_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.LENS_FRAME.get())
                .pattern("ooo")
                .pattern("s s")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .define('s', OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/lens_frame"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.LENSES.get())
                .pattern("ppp")
                .pattern("pgp")
                .pattern("ppp")
                .define('p', Tags.Items.GLASS_PANES)
                .define('g', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/lenses"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.MAGIC_LAMP_EMPTY.get())
                .pattern(" s ")
                .pattern("sis")
                .pattern(" ss")
                .define('s', OccultismTags.Items.SILVER_INGOT)
                .define('i', OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/magic_lamp_empty"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.OTHERSTONE_FRAME.get())
                .pattern("ooo")
                .pattern("o o")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_frame"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_PEDESTAL.get())
                .pattern("s s")
                .pattern(" o ")
                .pattern("sss")
                .define('s', OccultismBlocks.OTHERSTONE_SLAB.get())
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_pedestal"));

        stairBuilder(OccultismBlocks.OTHERSTONE_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERSTONE.asItem()))
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_SLAB.get())
                .pattern("ooo").define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get())
                .pattern("oo").define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_pressure_plate"));
        buttonBuilder(OccultismBlocks.OTHERSTONE_BUTTON.get(), Ingredient.of(OccultismBlocks.OTHERSTONE.asItem()))
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_button"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_WALL.get())
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_wall"));

        stairBuilder(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERCOBBLESTONE.asItem()))
                .unlockedBy("has_othercobblestone", has(OccultismBlocks.OTHERCOBBLESTONE.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/othercobblestone_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERCOBBLESTONE_SLAB.get())
                .pattern("ooo").define('o', OccultismBlocks.OTHERCOBBLESTONE.get())
                .unlockedBy("has_othercobblestone", has(OccultismBlocks.OTHERCOBBLESTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblestone_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERCOBBLESTONE_WALL.get())
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERCOBBLESTONE.get())
                .unlockedBy("has_othercobblestone", has(OccultismBlocks.OTHERCOBBLESTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblestone_wall"));

        stairBuilder(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get(), Ingredient.of(OccultismBlocks.POLISHED_OTHERSTONE.asItem()))
                .unlockedBy("has_polished_otherstone", has(OccultismBlocks.POLISHED_OTHERSTONE.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/polished_otherstone_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get())
                .pattern("ooo").define('o', OccultismBlocks.POLISHED_OTHERSTONE.get())
                .unlockedBy("has_polished_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherstone_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.POLISHED_OTHERSTONE_WALL.get())
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.POLISHED_OTHERSTONE.get())
                .unlockedBy("has_polished_otherstone", has(OccultismBlocks.POLISHED_OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherstone_wall"));

        stairBuilder(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERSTONE_BRICKS.asItem()))
                .unlockedBy("has_otherstone_bricks", has(OccultismBlocks.OTHERSTONE_BRICKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherstone_bricks_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get())
                .pattern("ooo").define('o', OccultismBlocks.OTHERSTONE_BRICKS.get())
                .unlockedBy("has_otherstone_bricks", has(OccultismBlocks.OTHERSTONE_BRICKS.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_BRICKS_WALL.get())
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERSTONE_BRICKS.get())
                .unlockedBy("has_otherstone_bricks", has(OccultismBlocks.OTHERSTONE_BRICKS.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks_wall"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_BRICKS.get())
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get())
                .pattern("o")
                .pattern("o")
                .define('o', OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get())
                .unlockedBy("has_otherstone_bricks_slab", has(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/chiseled_otherstone_bricks"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, OccultismItems.OTHERSTONE_TABLET.get())
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .define('s', OccultismBlocks.OTHERSTONE_SLAB.get())
                .unlockedBy("has_otherstone_slab", has(OccultismBlocks.OTHERSTONE_SLAB.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_tablet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.SACRIFICIAL_BOWL.get())
                .pattern("o o")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/sacrificial_bowl"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get())
                .pattern("gg")
                .pattern("gg")
                .define('g', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_attuned_crystal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get())
                .pattern("ggg")
                .define('g', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_attuned_pickaxe_head"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismBlocks.SPIRIT_CAMPFIRE.get())
                .pattern(" S ")
                .pattern("S#S")
                .pattern("LLL")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LOGS)
                .define('#', OccultismTags.Items.DATURA_CROP)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_campfire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OccultismBlocks.SPIRIT_LANTERN.get())
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .define('X', Tags.Items.NUGGETS_IRON)
                .define('#', OccultismBlocks.SPIRIT_TORCH.get())
                .unlockedBy("has_spirit_torch", has(OccultismBlocks.SPIRIT_TORCH.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_lantern"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OccultismBlocks.SPIRIT_TORCH.get())
                .pattern("X")
                .pattern("#")
                .pattern("S")
                .define('X', ItemTags.COALS)
                .define('#', OccultismTags.Items.DATURA_CROP)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_torch"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismBlocks.STORAGE_CONTROLLER.get())
                .pattern("d")
                .pattern("b")
                .define('d', OccultismItems.DIMENSIONAL_MATRIX.get())
                .define('b', OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .unlockedBy("has_dimensional_matrix", has(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_controller"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, OccultismItems.STORAGE_REMOTE_INERT.get())
                .pattern("gtg")
                .pattern("bgb")
                .pattern("bbb")
                .define('t', OccultismItems.OTHERSTONE_TABLET.get())
                .define('b', ItemTags.STONE_BUTTONS)
                .define('g', Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_otherstone_tablet", has(OccultismItems.OTHERSTONE_TABLET.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_remote_inert"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.WORMHOLE_FRAME.get())
                .pattern(" g ")
                .pattern("gog")
                .pattern(" g ")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('o', OccultismItems.OTHERSTONE_FRAME.get())
                .unlockedBy("has_otherstone_frame", has(OccultismItems.OTHERSTONE_FRAME.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/wormhole_frame"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERWORLD_WOOD.get(), 3)
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.OTHERWORLD_LOG.get())
                .unlockedBy("has_otherworld_log", has(OccultismBlocks.OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherworld_wood"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get(), 3)
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get())
                .unlockedBy("has_otherworld_log", has(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/stripped_otherworld_wood"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERPLANKS.get(), 4)
                .requires(OccultismTags.Items.OTHERWORLD_LOGS)
                .unlockedBy("has_otherworld_log", has(OccultismBlocks.OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks"));

        stairBuilder(OccultismBlocks.OTHERPLANKS_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERPLANKS_SLAB.get())
                .pattern("ooo").define('o', OccultismBlocks.OTHERPLANKS.get())
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get())
                .pattern("oo").define('o', OccultismBlocks.OTHERPLANKS.get())
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_pressure_plate"));
        buttonBuilder(OccultismBlocks.OTHERPLANKS_BUTTON.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_button"));
        fenceBuilder(OccultismBlocks.OTHERPLANKS_FENCE.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_fence"));
        fenceGateBuilder(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_fence_gate"));
        doorBuilder(OccultismBlocks.OTHERPLANKS_DOOR.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_door"));
        trapdoorBuilder(OccultismBlocks.OTHERPLANKS_TRAPDOOR.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_trapdoor"));
        signBuilder(OccultismItems.OTHERPLANKS_SIGN, Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", has(OccultismBlocks.OTHERPLANKS.asItem())).save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"crafting/otherplans_sign"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OccultismItems.OTHERPLANKS_HANGING_SIGN, 6)
                .group("hanging_sign")
                .define('#', OccultismBlocks.STRIPPED_OTHERWORLD_LOG.asItem())
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_otherworld_log", has(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_hanging_sign"));
    }

    private void smeltingRecipes(RecipeOutput pRecipeOutput) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(OccultismBlocks.OTHERSTONE.get()), RecipeCategory.MISC, OccultismItems.BURNT_OTHERSTONE.get(), 0.15f, 200)
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/burnt_otherstone"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200)
                .unlockedBy("has_copper_dust", has(OccultismTags.Items.COPPER_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/copper_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.GOLD_DUST), RecipeCategory.MISC, Items.GOLD_INGOT, 0.7f, 200)
                .unlockedBy("has_gold_dust", has(OccultismTags.Items.GOLD_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/gold_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.IESNIUM_ORE), RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_iesnium_ore", has(OccultismTags.Items.IESNIUM_ORE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/iesnium_ingot"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.IESNIUM_DUST), RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_iesnium_dust", has(OccultismTags.Items.IESNIUM_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/iesnium_ingot_from_dust"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.RAW_IESNIUM), RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_raw_iesnium", has(OccultismTags.Items.RAW_IESNIUM))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/iesnium_ingot_from_raw"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.IRON_DUST), RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200)
                .unlockedBy("has_iron_dust", has(OccultismTags.Items.IRON_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/iron_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.SILVER_ORE), RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_silver_ore", has(OccultismTags.Items.SILVER_ORE))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/silver_ingot"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.SILVER_DUST), RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_silver_dust", has(OccultismTags.Items.SILVER_DUST))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/silver_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.RAW_SILVER), RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_raw_silver", has(OccultismTags.Items.RAW_SILVER))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/silver_ingot_from_raw"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismBlocks.OTHERCOBBLESTONE.asItem()), RecipeCategory.MISC, OccultismBlocks.POLISHED_OTHERSTONE.asItem(), 0.5f, 200)
                .unlockedBy("has_othercobblestone", has(OccultismBlocks.OTHERCOBBLESTONE.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/polished_otherstone"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismBlocks.OTHERSTONE_BRICKS.asItem()), RecipeCategory.MISC, OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.asItem(), 0.3f, 200)
                .unlockedBy("has_otherstone_bricks", has(OccultismBlocks.OTHERSTONE_BRICKS.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelting/cracked_otherstone_bricks"));

    }
}
