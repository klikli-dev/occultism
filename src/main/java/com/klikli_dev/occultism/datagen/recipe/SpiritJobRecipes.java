package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.CrushingRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.CrystallizeRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.SpiritTradeRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public abstract class SpiritJobRecipes extends RecipeProvider {
    public SpiritJobRecipes(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    public static void spiritJobRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        spiritTradeRecipes(pRecipeOutput, registries);
        mobDropCrushing(pRecipeOutput, registries);
        oreProcessRecipes(pRecipeOutput, registries);
        blockProcessRecipes(pRecipeOutput, registries);
    }

    private static void spiritTradeRecipes(RecipeOutput pRecipeOutput) {
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL),
                        new ItemStack(OccultismBlocks.OTHERWORLD_SAPLING), 1,
                        "occultism:trader_otherworld_saplings")
                .unlockedBy("has_otherworld_sapling_natural", has(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/otherworld_sapling"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.STONES),
                        new ItemStack(OccultismBlocks.OTHERSTONE.get(), 2), 1,
                        "occultism:trader_otherstone")
                .unlockedBy("has_stone", has(Tags.Items.STONES))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/stone_to_otherstone"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.STONES),
                        new ItemStack(OccultismBlocks.OTHERROCK.get(), 2), 1,
                        "occultism:trader_otherrock")
                .unlockedBy("has_stone", has(Tags.Items.STONES))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/stone_to_otherrock"));

        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(OccultismItems.SPIRIT_ATTUNED_GEM.get(), 1), 8,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_spirit"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.DIAMOND, 1), 1,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_diamond"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.EMERALD, 1), 1,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_emerald"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.QUARTZ, 1), 2,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_quartz"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.LAPIS_LAZULI, 1), 2,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_lazuli"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.PRISMARINE_CRYSTALS, 1), 4,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_prismarine"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.AMETHYST_SHARD, 1), 4,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_amethyst"));

        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.IRON_NUGGET, 1), 16,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_iron"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(Items.GOLD_NUGGET, 1), 8,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_gold"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(OccultismItems.SILVER_NUGGET.get(), 1), 8,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_silver"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.GEMS),
                        new ItemStack(OccultismItems.IESNIUM_NUGGET.get(), 1), 1,
                        "occultism:gambler")
                .unlockedBy("has_gems", has(Tags.Items.GEMS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_iesnium"));
    }

    private static void mobDropCrushing(RecipeOutput pRecipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.RODS_BLAZE, Items.BLAZE_POWDER, 200)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_blaze_rod", has(Tags.Items.RODS_BLAZE))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/blaze_powder_from_rod"));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.RODS_BREEZE, Items.WIND_CHARGE, 200)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_breeze_rod", has(Tags.Items.RODS_BREEZE))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/wind_charge_from_rod"));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.BONES, Items.BONE_MEAL, 200)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_bone", has(Tags.Items.BONES))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/bone_meal_from_bone"));

        CrushingRecipeBuilder.crushingRecipe(Tags.Items.ENDER_PEARLS, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "ender_pearl")), 200)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/ender_pearl_dust_from_tag"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "ender_pearl")), Tags.Items.ENDER_PEARLS, 200)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setMinTier(2)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_ender_pearl_dust", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "ender_pearl"))))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/ender_pearl"));

        CrushingRecipeBuilder.crushingRecipe(Items.ECHO_SHARD, OccultismTags.Items.ECHO_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(4)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/echo_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.ECHO_DUST, Items.ECHO_SHARD, 200)
                .unlockedBy("has_echo_dust", has(OccultismTags.Items.ECHO_DUST))
                .setResultAmount(1)
                .setMinTier(4)
                .setAllowEmpty(false)
                .setIgnoreCrystallizeMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/echo_shard"));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS, 200)
                .unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD))
                .setResultAmount(1)
                .setMinTier(2)
                .setAllowEmpty(false)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/prismarine_crystal"));
    }

    private static void blockProcessRecipes(RecipeOutput pRecipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.Items.OTHERSTONE, OccultismTags.Items.OTHERCOBBLESTONE, 20)
                .unlockedBy("has_otherstone", has(OccultismTags.Items.OTHERSTONE))
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/othercobblestone"));
        CrushingRecipeBuilder.crushingRecipe(OccultismBlocks.OTHERROCK.asItem(), OccultismBlocks.OTHERCOBBLEROCK.asItem(), 20)
                .unlockedBy("has_otherrock", has(OccultismBlocks.OTHERROCK.asItem()))
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/othercobblerock"));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.OBSIDIANS, OccultismTags.Items.OBSIDIAN_DUST, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIANS))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/obsidian_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.OBSIDIAN_DUST, Items.OBSIDIAN, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_obsidian_dust", has(OccultismTags.Items.OBSIDIAN_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/obsidian"));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.END_STONES, OccultismTags.Items.END_STONE_DUST, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_end_stone", has(Tags.Items.END_STONES))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/end_stone_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.END_STONE_DUST, Items.END_STONE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_end_stone_dust", has(OccultismTags.Items.END_STONE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/end_stone"));
        CrushingRecipeBuilder.crushingRecipe(Items.CALCITE, OccultismTags.Items.CALCITE_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_calcite", has(Items.CALCITE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/calcite_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.CALCITE_DUST, Items.CALCITE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_calcite_dust", has(OccultismTags.Items.CALCITE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/calcite"));
        CrushingRecipeBuilder.crushingRecipe(Items.BLACKSTONE, OccultismTags.Items.BLACKSTONE_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_blackstone", has(Items.BLACKSTONE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/blackstone_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.BLACKSTONE_DUST, Items.BLACKSTONE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_blackstone_dust", has(OccultismTags.Items.BLACKSTONE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/blackstone"));
        CrushingRecipeBuilder.crushingRecipe(Items.ICE, OccultismTags.Items.ICE_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_ice", has(Items.ICE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(2)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/ice_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.ICE_DUST, Items.ICE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_ice_dust", has(OccultismTags.Items.ICE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/ice"));
        CrushingRecipeBuilder.crushingRecipe(Items.PACKED_ICE, OccultismTags.Items.PACKED_ICE_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_packed_ice", has(Items.PACKED_ICE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(2)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/packed_ice_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.PACKED_ICE_DUST, Items.PACKED_ICE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_packed_ice_dust", has(OccultismTags.Items.PACKED_ICE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/packed_ice"));
        CrushingRecipeBuilder.crushingRecipe(Items.BLUE_ICE, OccultismTags.Items.BLUE_ICE_DUST, 200)
                .allowEmpty()
                .unlockedBy("has_blue_ice", has(Items.BLUE_ICE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(2)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/blue_ice_dust"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.BLUE_ICE_DUST, Items.BLUE_ICE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_blue_ice_dust", has(OccultismTags.Items.BLUE_ICE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/blue_ice"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.OTHERSTONE_DUST, OccultismBlocks.OTHERSTONE, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_otherstone_dust", has(OccultismTags.Items.OTHERSTONE_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/otherstone"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.OTHERROCK_DUST, OccultismBlocks.OTHERROCK, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_otherrock_dust", has(OccultismTags.Items.OTHERROCK_DUST))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/otherrock"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "sky_stones")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/sky_stone")), 200)
                .unlockedBy("has_sky_stone", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "sky_stones"))))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/sky_stone_dust"));
    }

    private static void oreProcessRecipes(RecipeOutput pRecipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.Items.DATURA_CROP, OccultismTags.Items.DATURA_SEEDS, 200)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .setAllowEmpty(false)
                .setResultAmount(2)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/datura"));
        //Ores vanilla + occultism
        fullMetalRecipe("copper", Items.COPPER_INGOT, pRecipeOutput);
        fullMetalRecipe("iron", Items.IRON_INGOT, pRecipeOutput);
        fullMetalRecipe("gold", Items.GOLD_INGOT, pRecipeOutput);
        fullMetalRecipe("silver", OccultismItems.SILVER_INGOT.get(), pRecipeOutput);
        fullMetalRecipe("iesnium", OccultismItems.IESNIUM_INGOT.get(), pRecipeOutput);
        tripleCrushSmeltBlastRecipe("netherite", Items.NETHERITE_INGOT, pRecipeOutput);
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.ORES_NETHERITE_SCRAP, OccultismTags.Items.NETHERITE_SCRAP_DUST, 200)
                .unlockedBy("has_ancient_debris", has(Tags.Items.ORES_NETHERITE_SCRAP))
                .setResultAmount(2)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/netherite_scrap_dust_from_ore"));
        CrushingRecipeBuilder.crushingRecipe(Items.NETHERITE_SCRAP, OccultismTags.Items.NETHERITE_SCRAP_DUST, 200)
                .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
                .setIgnoreCrushingMultiplier(true)
                .setResultAmount(1)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/netherite_scrap_dust_from_scrap"));
        doubleCookingRecipe("netherite_scrap", Items.NETHERITE_SCRAP, pRecipeOutput);
        //Ores common
        crushingMetalRecipe("aluminum", pRecipeOutput);
        crushingMetalRecipe("iridium", pRecipeOutput);
        crushingMetalRecipe("lead", pRecipeOutput);
        crushingMetalRecipe("nickel", pRecipeOutput);
        crushingMetalRecipe("osmium", pRecipeOutput);
        crushingMetalRecipe("platinum", pRecipeOutput);
        crushingMetalRecipe("tin", pRecipeOutput);
        crushingMetalRecipe("uranium", pRecipeOutput);
        crushingMetalRecipe("zinc", pRecipeOutput);
        //Ores specific
        crushingMetalRecipe("antimony", pRecipeOutput);
        crushingMetalRecipe("allthemodium", pRecipeOutput);
        crushingMetalRecipe("unobtainium", pRecipeOutput);
        crushingMetalRecipe("vibranium", pRecipeOutput);
        crushingMetalRecipe("crimson_iron", pRecipeOutput);
        crushingMetalRecipe("azure_silver", pRecipeOutput);
        crushingMetalRecipe("graphite", pRecipeOutput);
        crushingMetalRecipe("cobalt", pRecipeOutput);
        crushingMetalRecipe("titanium", pRecipeOutput);
        crushingMetalRecipe("tungsten", pRecipeOutput);
        crushingMetalRecipe("pewter", pRecipeOutput);
        crushingMetalRecipe("mithril", pRecipeOutput);
        crushingMetalRecipe("quicksilver", pRecipeOutput);
        //Ingots without ores
        crushingIngotRecipe("adamant", pRecipeOutput);
        crushingIngotRecipe("azure_electrum", pRecipeOutput);
        crushingIngotRecipe("biosteel", pRecipeOutput);
        crushingIngotRecipe("blaze_gold", pRecipeOutput);
        crushingIngotRecipe("brass", pRecipeOutput);
        crushingIngotRecipe("bronze", pRecipeOutput);
        crushingIngotRecipe("constantan", pRecipeOutput);
        crushingIngotRecipe("crimson_steel", pRecipeOutput);
        crushingIngotRecipe("duratium", pRecipeOutput);
        crushingIngotRecipe("electrum", pRecipeOutput);
        crushingIngotRecipe("enderium", pRecipeOutput);
        crushingIngotRecipe("energite", pRecipeOutput);
        crushingIngotRecipe("hop_graphite", pRecipeOutput);
        crushingIngotRecipe("invar", pRecipeOutput);
        crushingIngotRecipe("lumium", pRecipeOutput);
        crushingIngotRecipe("refined_obsidian", pRecipeOutput);
        crushingIngotRecipe("steel", pRecipeOutput);
        crushingIngotRecipe("signalum", pRecipeOutput);
        crushingIngotRecipe("tyrian_steel", pRecipeOutput);
        crushingIngotRecipe("unobtainium_allthemodium_alloy", pRecipeOutput);
        crushingIngotRecipe("unobtainium_vibranium_alloy", pRecipeOutput);
        crushingIngotRecipe("vibranium_allthemodium_alloy", pRecipeOutput);
        //ModernIndustrialization Ingots
        crushingIngotRecipe("annealed_copper", pRecipeOutput);
        crushingIngotRecipe("battery_alloy", pRecipeOutput);
        crushingIngotRecipe("beryllium", pRecipeOutput);
        crushingIngotRecipe("cadmium", pRecipeOutput);
        crushingIngotRecipe("chromium", pRecipeOutput);
        crushingIngotRecipe("cupronickel", pRecipeOutput);
        crushingIngotRecipe("he_mox", pRecipeOutput);
        crushingIngotRecipe("he_uranium", pRecipeOutput);
        crushingIngotRecipe("kanthal", pRecipeOutput);
        crushingIngotRecipe("le_mox", pRecipeOutput);
        crushingIngotRecipe("le_uranium", pRecipeOutput);
        crushingIngotRecipe("plutonium", pRecipeOutput);
        crushingIngotRecipe("silicon", pRecipeOutput);
        crushingIngotRecipe("superconductor", pRecipeOutput);
        crushingIngotRecipe("uranium_235", pRecipeOutput);
        crushingIngotRecipe("uranium_238", pRecipeOutput);
        //Gems
        fullGemRecipe("dark_gem", 2, pRecipeOutput);
        fullGemRecipe("diamond", 2, pRecipeOutput);
        fullGemRecipe("emerald", 2, pRecipeOutput);
        fullGemRecipe("lapis", 6, pRecipeOutput);
        fullGemRecipe("quartz", 2, pRecipeOutput);
        fullGemRecipe("apatite", 2, pRecipeOutput);
        fullGemRecipe("sulfur", 2, pRecipeOutput);
        fullGemRecipe("fluorite", 4, pRecipeOutput);
        fullGemRecipe("cinnabar", 2, pRecipeOutput);
        fullGemRecipe("amber", 2, pRecipeOutput);
        fullGemRecipe("peridot", 2, pRecipeOutput);
        fullGemRecipe("ruby", 2, pRecipeOutput);
        fullGemRecipe("sapphire", 2, pRecipeOutput);
        fullGemRecipe("topaz", 2, pRecipeOutput);
        fullGemRecipe("arcane_crystal", 2, pRecipeOutput);
        fullGemRecipe("black_quartz", 2, pRecipeOutput);
        //Only gem
        gemCrushCrystalRecipe("certus_quartz", pRecipeOutput);
        gemCrushCrystalRecipe("fluix", pRecipeOutput);
        gemCrushCrystalRecipe("amethyst", pRecipeOutput);
        gemCrushCrystalRecipe("entro", pRecipeOutput);
        //Only ores
        crushingOreRecipe("coal", 2, pRecipeOutput);
        crushingOreRecipe("redstone", 4, pRecipeOutput);
        crushingOreRecipe("lignite_coal", 2, pRecipeOutput);
        crushingOreRecipe("saltpeter", 2, pRecipeOutput);
        crushingOreRecipe("monazite", 2, pRecipeOutput);
        crushingOreRecipe("bauxite", 2, pRecipeOutput);
        crushingOreRecipe("salt", 2, pRecipeOutput);

        CrushingRecipeBuilder.crushingRecipe(Items.COAL, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "coal")), 200)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_coal", has(Items.COAL))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/coal_dust_from_item"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "coal")), Items.COAL, 200)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_coal_dust", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "coal"))))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/coal"));
        CrushingRecipeBuilder.crushingRecipe(Items.CHARCOAL, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "charcoal")), 200)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/charcoal_dust_from_item"));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "charcoal")), Items.CHARCOAL, 200)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_charcoal_dust", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "charcoal"))))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/charcoal"));
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "coal_coke")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/coal_coke")), 200)
                .unlockedBy("has_coal_coke", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "coal_coke"))))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/coal_coke_dust"));
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "bricks/normal")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/brick")), 200)
                .unlockedBy("has_brick", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "bricks/normal"))))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/brick_dust"));
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "plates/carbon")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/carbon")), 200)
                .unlockedBy("has_carbon_plate", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "plates/carbon"))))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/carbon_dust_from_plate"));

        CrystallizeRecipeBuilder.crystallizeRecipe(Items.AMETHYST_BLOCK, Items.AMETHYST_SHARD, 200)
                .allowEmpty()
                .setResultAmount(4)
                .setMinTier(2)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_amethyst_block", has(Items.AMETHYST_BLOCK))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/amethyst_from_block"));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.QUARTZ_BLOCK, Items.QUARTZ, 200)
                .allowEmpty()
                .setResultAmount(4)
                .setMinTier(2)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_quartz_block", has(Items.QUARTZ_BLOCK))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/quartz_from_block"));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.AMETHYST_CLUSTER, Items.BUDDING_AMETHYST, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(4)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_amethyst_cluster", has(Items.AMETHYST_CLUSTER))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/budding_amethyst"));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.OBSIDIAN, Items.CRYING_OBSIDIAN, 200)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(4)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
                .save(pRecipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/crying_obsidian"));
    }

    protected static void crushingGeneralizedRecipe(String input, Integer amount, String from, Boolean mult, RecipeOutput recipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + input)), 200)
                .unlockedBy("has_" + input + "_" + from, has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input))))
                .setResultAmount(amount)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(mult)
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + input + "_dust_from_" + from));
    }

    protected static void crushingOreRecipe(String input, Integer amount, RecipeOutput recipeOutput) {
        crushingGeneralizedRecipe(input, amount, "ore", Boolean.FALSE, recipeOutput);
    }

    protected static void crushingIngotRecipe(String input, RecipeOutput recipeOutput) {
        crushingGeneralizedRecipe(input, 1, "ingot", Boolean.TRUE, recipeOutput);
    }

    protected static void crushingGemRecipe(String input, RecipeOutput recipeOutput) {
        crushingGeneralizedRecipe(input, 1, "gem", Boolean.TRUE, recipeOutput);
    }

    protected static void crystallizeGeneralizedRecipe(String input, Integer amount, String from, Boolean mult, RecipeOutput recipeOutput) {
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "gems/" + input)), 200)
                .unlockedBy("has_" + input + "_" + from, has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input))))
                .setResultAmount(amount)
                .setAllowEmpty(false)
                .setIgnoreCrystallizeMultiplier(mult)
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/" + input + "_from_" + from));
    }

    protected static void crystallizeDustRecipe(String input, RecipeOutput recipeOutput) {
        crystallizeGeneralizedRecipe(input, 1, "dust", Boolean.TRUE, recipeOutput);
    }

    protected static void crystallizeOreRecipe(String input, Integer amount, RecipeOutput recipeOutput) {
        crystallizeGeneralizedRecipe(input, amount, "ore", Boolean.FALSE, recipeOutput);
    }

    private static void doubleCookingRecipe(String metalName, Item output, RecipeOutput recipeOutput) {
        String outputString = output.toString().replace("minecraft:", "").replace("occultism:", "");
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName))), RecipeCategory.MISC, output, 0.7f, 200)
                .unlockedBy("has_" + metalName + "_dust", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName))))
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "smelting/" + outputString + "_from_dust"));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName))), RecipeCategory.MISC, output, 0.7f, 100)
                .unlockedBy("has_" + metalName + "_dust", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName))))
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "blasting/" + outputString + "_from_dust"));
    }

    private static void gemCrushCrystalRecipe(String gemName, RecipeOutput recipeOutput) {
        crushingGemRecipe(gemName, recipeOutput);
        crystallizeDustRecipe(gemName, recipeOutput);
    }

    private static void fullGemRecipe(String gemName, Integer amount, RecipeOutput recipeOutput) {
        crushingOreRecipe(gemName, (int)(amount*1.5), recipeOutput);
        crystallizeOreRecipe(gemName, amount, recipeOutput);
        crushingGemRecipe(gemName, recipeOutput);
        crystallizeDustRecipe(gemName, recipeOutput);
    }

    private static void crushingMetalRecipe(String metalName, RecipeOutput recipeOutput) {
        crushingIngotRecipe(metalName, recipeOutput);
        crushingOreRecipe(metalName, 2, recipeOutput);

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/" + metalName)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName)), 200)
                .unlockedBy("has_raw_" + metalName, has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/" + metalName))))
                .setResultAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_raw"));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_" + metalName)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName)), 1600)
                .unlockedBy("has_raw_" + metalName + "_block", has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_" + metalName))))
                .setResultAmount(18)
                .setAllowEmpty(false)
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_raw_block"));


        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "clumps/" + metalName)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dirty_dusts/" + metalName)), 200)
                .unlockedBy("has_clump_" + metalName, has(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "clumps/" + metalName))))
                .setResultAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dirty_dust_from_clump"));
    }

    private static void fullMetalRecipe(String metalName, Item ingot, RecipeOutput recipeOutput) {
        crushingMetalRecipe(metalName, recipeOutput);
        doubleCookingRecipe(metalName, ingot, recipeOutput);
    }

    private static void tripleCrushSmeltBlastRecipe(String input, Item output, RecipeOutput recipeOutput) {
        crushingIngotRecipe(input, recipeOutput);
        doubleCookingRecipe(input, output, recipeOutput);
    }
}