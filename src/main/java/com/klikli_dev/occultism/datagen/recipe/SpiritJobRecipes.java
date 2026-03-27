package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.CrushingRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.CrystallizeRecipeBuilder;
import com.klikli_dev.occultism.datagen.recipe.builders.SpiritTradeRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.CookingBookCategory;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

public abstract class SpiritJobRecipes extends RecipeProvider {
    public SpiritJobRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    // Need a static create method that returns an instance for recipe generation
    public static SpiritJobRecipes create(HolderLookup.Provider registries, RecipeOutput output) {
        return new SpiritJobRecipes(registries, output) {
            @Override
            protected void buildRecipes() {
                // Will be called - but recipes are generated via static method
            }
        };
    }

    public static void spiritJobRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        spiritTradeRecipes(pRecipeOutput, registries);
        mobDropCrushing(pRecipeOutput, registries);
        oreProcessRecipes(pRecipeOutput, registries);
        blockProcessRecipes(pRecipeOutput, registries);
    }

    // Helper method to create Ingredient from TagKey using registries
    protected static Ingredient ingredientOf(TagKey<Item> tag, HolderLookup.Provider registries) {
        return Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag));
    }

    // Helper method to create has() criterion for tags (requires HolderLookup.Provider)
    protected static Criterion<InventoryChangeTrigger.TriggerInstance> hasTag(TagKey<Item> tag, HolderLookup.Provider registries) {
        HolderLookup.ItemGetter items = registries.lookupOrThrow(Registries.ITEM);
        return InventoryChangeTrigger.TriggerInstance.hasItems(items, tag);
    }

    // Helper method to create has() criterion for items
    protected static Criterion<InventoryChangeTrigger.TriggerInstance> hasItem(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    private static void spiritTradeRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL, registries),
                        new ItemStack(OccultismBlocks.OTHERWORLD_SAPLING), 1,
                        "occultism:trader_otherworld_saplings", registries)
                .unlockedBy("has_otherworld_sapling_natural", hasItem(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/otherworld_sapling")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.STONES, registries),
                        new ItemStack(OccultismBlocks.OTHERSTONE.get(), 2), 1,
                        "occultism:trader_otherstone", registries)
                .unlockedBy("has_stone", hasTag(Tags.Items.STONES, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/stone_to_otherstone")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.STONES, registries),
                        new ItemStack(OccultismBlocks.OTHERROCK.get(), 2), 1,
                        "occultism:trader_otherrock", registries)
                .unlockedBy("has_stone", hasTag(Tags.Items.STONES, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/stone_to_otherrock")));

        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(OccultismItems.SPIRIT_ATTUNED_GEM.get(), 1), 8,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_spirit")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.DIAMOND, 1), 1,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_diamond")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.EMERALD, 1), 1,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_emerald")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.QUARTZ, 1), 2,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_quartz")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.LAPIS_LAZULI, 1), 2,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_lazuli")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.PRISMARINE_CRYSTALS, 1), 4,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_prismarine")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.AMETHYST_SHARD, 1), 4,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_amethyst")));

        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.IRON_NUGGET, 1), 16,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_iron")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(Items.GOLD_NUGGET, 1), 8,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_gold")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(OccultismItems.SILVER_NUGGET.get(), 1), 8,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_silver")));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(ingredientOf(Tags.Items.GEMS, registries),
                        new ItemStack(OccultismItems.IESNIUM_NUGGET.get(), 1), 1,
                        "occultism:gambler", registries)
                .unlockedBy("has_gems", hasTag(Tags.Items.GEMS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/gambler_iesnium")));
    }

    private static void mobDropCrushing(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.RODS_BLAZE, Items.BLAZE_POWDER, 200, registries)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_blaze_rod", hasTag(Tags.Items.RODS_BLAZE, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/blaze_powder_from_rod")));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.RODS_BREEZE, Items.WIND_CHARGE, 200, registries)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_breeze_rod", hasTag(Tags.Items.RODS_BREEZE, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/wind_charge_from_rod")));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.BONES, Items.BONE_MEAL, 200, registries)
                .allowEmpty()
                .setResultAmount(4)
                .unlockedBy("has_bone", hasTag(Tags.Items.BONES, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/bone_meal_from_bone")));

        CrushingRecipeBuilder.crushingRecipe(Tags.Items.ENDER_PEARLS, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "ender_pearl")), 200, registries)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_ender_pearl", hasTag(Tags.Items.ENDER_PEARLS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/ender_pearl_dust_from_tag")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "ender_pearl")), Tags.Items.ENDER_PEARLS, 200, registries)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setMinTier(2)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_ender_pearl_dust", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "ender_pearl")), registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/ender_pearl")));

        CrushingRecipeBuilder.crushingRecipe(Items.ECHO_SHARD, OccultismTags.Items.ECHO_DUST, 200, registries)
                .allowEmpty()
                .unlockedBy("has_echo_shard", hasItem(Items.ECHO_SHARD))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(4)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/echo_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.ECHO_DUST, Items.ECHO_SHARD, 200, registries)
                .unlockedBy("has_echo_dust", hasTag(OccultismTags.Items.ECHO_DUST, registries))
                .setResultAmount(1)
                .setMinTier(4)
                .setAllowEmpty(false)
                .setIgnoreCrystallizeMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/echo_shard")));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS, 200, registries)
                .unlockedBy("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD))
                .setResultAmount(1)
                .setMinTier(2)
                .setAllowEmpty(false)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/prismarine_crystal")));
    }

    private static void blockProcessRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.Items.OTHERSTONE, OccultismTags.Items.OTHERCOBBLESTONE, 20, registries)
                .unlockedBy("has_otherstone", hasTag(OccultismTags.Items.OTHERSTONE, registries))
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/othercobblestone")));
        CrushingRecipeBuilder.crushingRecipe(OccultismBlocks.OTHERROCK.asItem(), OccultismBlocks.OTHERCOBBLEROCK.asItem(), 20, registries)
                .unlockedBy("has_otherrock", hasItem(OccultismBlocks.OTHERROCK.asItem()))
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/othercobblerock")));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.OBSIDIANS, OccultismTags.Items.OBSIDIAN_DUST, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_obsidian", hasTag(Tags.Items.OBSIDIANS, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/obsidian_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.OBSIDIAN_DUST, Items.OBSIDIAN, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_obsidian_dust", hasTag(OccultismTags.Items.OBSIDIAN_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/obsidian")));
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.END_STONES, OccultismTags.Items.END_STONE_DUST, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_end_stone", hasTag(Tags.Items.END_STONES, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/end_stone_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.END_STONE_DUST, Items.END_STONE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_end_stone_dust", hasTag(OccultismTags.Items.END_STONE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/end_stone")));
        CrushingRecipeBuilder.crushingRecipe(Items.CALCITE, OccultismTags.Items.CALCITE_DUST, 200, registries)
                .allowEmpty()
                .unlockedBy("has_calcite", hasItem(Items.CALCITE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/calcite_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.CALCITE_DUST, Items.CALCITE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_calcite_dust", hasTag(OccultismTags.Items.CALCITE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/calcite")));
        CrushingRecipeBuilder.crushingRecipe(Items.BLACKSTONE, OccultismTags.Items.BLACKSTONE_DUST, 200, registries)
                .allowEmpty()
                .unlockedBy("has_blackstone", hasItem(Items.BLACKSTONE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/blackstone_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.BLACKSTONE_DUST, Items.BLACKSTONE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_blackstone_dust", hasTag(OccultismTags.Items.BLACKSTONE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/blackstone")));
        CrushingRecipeBuilder.crushingRecipe(Items.ICE, OccultismTags.Items.ICE_DUST, 200, registries)
                .allowEmpty()
                .unlockedBy("has_ice", hasItem(Items.ICE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(2)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/ice_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.ICE_DUST, Items.ICE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_ice_dust", hasTag(OccultismTags.Items.ICE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/ice")));
        CrushingRecipeBuilder.crushingRecipe(Items.PACKED_ICE, OccultismTags.Items.PACKED_ICE_DUST, 200, registries)
                .allowEmpty()
                .unlockedBy("has_packed_ice", hasItem(Items.PACKED_ICE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(2)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/packed_ice_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.PACKED_ICE_DUST, Items.PACKED_ICE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_packed_ice_dust", hasTag(OccultismTags.Items.PACKED_ICE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/packed_ice")));
        CrushingRecipeBuilder.crushingRecipe(Items.BLUE_ICE, OccultismTags.Items.BLUE_ICE_DUST, 200, registries)
                .allowEmpty()
                .unlockedBy("has_blue_ice", hasItem(Items.BLUE_ICE))
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .setMinTier(2)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/blue_ice_dust")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.BLUE_ICE_DUST, Items.BLUE_ICE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_blue_ice_dust", hasTag(OccultismTags.Items.BLUE_ICE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/blue_ice")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.OTHERSTONE_DUST, OccultismBlocks.OTHERSTONE, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_otherstone_dust", hasTag(OccultismTags.Items.OTHERSTONE_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/otherstone")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.Items.OTHERROCK_DUST, OccultismBlocks.OTHERROCK, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(3)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_otherrock_dust", hasTag(OccultismTags.Items.OTHERROCK_DUST, registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/otherrock")));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "sky_stones")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/sky_stone")), 200, registries)
                .unlockedBy("has_sky_stone", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "sky_stones")), registries))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/sky_stone_dust")));
    }

    private static void oreProcessRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider registries) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.Items.DATURA_CROP, OccultismTags.Items.DATURA_SEEDS, 200, registries)
                .unlockedBy("has_datura", hasTag(OccultismTags.Items.DATURA_CROP, registries))
                .setAllowEmpty(false)
                .setResultAmount(2)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/datura")));
        //Ores vanilla + occultism
        fullMetalRecipe("copper", Items.COPPER_INGOT, pRecipeOutput, registries);
        fullMetalRecipe("iron", Items.IRON_INGOT, pRecipeOutput, registries);
        fullMetalRecipe("gold", Items.GOLD_INGOT, pRecipeOutput, registries);
        fullMetalRecipe("silver", OccultismItems.SILVER_INGOT.get(), pRecipeOutput, registries);
        fullMetalRecipe("iesnium", OccultismItems.IESNIUM_INGOT.get(), pRecipeOutput, registries);
        tripleCrushSmeltBlastRecipe("netherite", Items.NETHERITE_INGOT, pRecipeOutput, registries);
        CrushingRecipeBuilder.crushingRecipe(Tags.Items.ORES_NETHERITE_SCRAP, OccultismTags.Items.NETHERITE_SCRAP_DUST, 200, registries)
                .unlockedBy("has_ancient_debris", hasTag(Tags.Items.ORES_NETHERITE_SCRAP, registries))
                .setResultAmount(2)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/netherite_scrap_dust_from_ore")));
        CrushingRecipeBuilder.crushingRecipe(Items.NETHERITE_SCRAP, OccultismTags.Items.NETHERITE_SCRAP_DUST, 200, registries)
                .unlockedBy("has_netherite_scrap", hasItem(Items.NETHERITE_SCRAP))
                .setIgnoreCrushingMultiplier(true)
                .setResultAmount(1)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/netherite_scrap_dust_from_scrap")));
        doubleCookingRecipe("netherite_scrap", Items.NETHERITE_SCRAP, pRecipeOutput, registries);
        //Ores common
        crushingMetalRecipe("aluminum", pRecipeOutput, registries);
        crushingMetalRecipe("iridium", pRecipeOutput, registries);
        crushingMetalRecipe("lead", pRecipeOutput, registries);
        crushingMetalRecipe("nickel", pRecipeOutput, registries);
        crushingMetalRecipe("osmium", pRecipeOutput, registries);
        crushingMetalRecipe("platinum", pRecipeOutput, registries);
        crushingMetalRecipe("tin", pRecipeOutput, registries);
        crushingMetalRecipe("uranium", pRecipeOutput, registries);
        crushingMetalRecipe("zinc", pRecipeOutput, registries);
        //Ores specific
        crushingMetalRecipe("antimony", pRecipeOutput, registries);
        crushingMetalRecipe("allthemodium", pRecipeOutput, registries);
        crushingMetalRecipe("unobtainium", pRecipeOutput, registries);
        crushingMetalRecipe("vibranium", pRecipeOutput, registries);
        crushingMetalRecipe("crimson_iron", pRecipeOutput, registries);
        crushingMetalRecipe("azure_silver", pRecipeOutput, registries);
        crushingMetalRecipe("graphite", pRecipeOutput, registries);
        crushingMetalRecipe("cobalt", pRecipeOutput, registries);
        crushingMetalRecipe("titanium", pRecipeOutput, registries);
        crushingMetalRecipe("tungsten", pRecipeOutput, registries);
        crushingMetalRecipe("pewter", pRecipeOutput, registries);
        crushingMetalRecipe("mithril", pRecipeOutput, registries);
        crushingMetalRecipe("quicksilver", pRecipeOutput, registries);
        //Ingots without ores
        crushingIngotRecipe("adamant", pRecipeOutput, registries);
        crushingIngotRecipe("azure_electrum", pRecipeOutput, registries);
        crushingIngotRecipe("biosteel", pRecipeOutput, registries);
        crushingIngotRecipe("blaze_gold", pRecipeOutput, registries);
        crushingIngotRecipe("brass", pRecipeOutput, registries);
        crushingIngotRecipe("bronze", pRecipeOutput, registries);
        crushingIngotRecipe("constantan", pRecipeOutput, registries);
        crushingIngotRecipe("crimson_steel", pRecipeOutput, registries);
        crushingIngotRecipe("duratium", pRecipeOutput, registries);
        crushingIngotRecipe("electrum", pRecipeOutput, registries);
        crushingIngotRecipe("enderium", pRecipeOutput, registries);
        crushingIngotRecipe("energite", pRecipeOutput, registries);
        crushingIngotRecipe("hop_graphite", pRecipeOutput, registries);
        crushingIngotRecipe("invar", pRecipeOutput, registries);
        crushingIngotRecipe("lumium", pRecipeOutput, registries);
        crushingIngotRecipe("refined_obsidian", pRecipeOutput, registries);
        crushingIngotRecipe("steel", pRecipeOutput, registries);
        crushingIngotRecipe("signalum", pRecipeOutput, registries);
        crushingIngotRecipe("tyrian_steel", pRecipeOutput, registries);
        crushingIngotRecipe("unobtainium_allthemodium_alloy", pRecipeOutput, registries);
        crushingIngotRecipe("unobtainium_vibranium_alloy", pRecipeOutput, registries);
        crushingIngotRecipe("vibranium_allthemodium_alloy", pRecipeOutput, registries);
        //ModernIndustrialization Ingots
        crushingIngotRecipe("annealed_copper", pRecipeOutput, registries);
        crushingIngotRecipe("battery_alloy", pRecipeOutput, registries);
        crushingIngotRecipe("beryllium", pRecipeOutput, registries);
        crushingIngotRecipe("cadmium", pRecipeOutput, registries);
        crushingIngotRecipe("chromium", pRecipeOutput, registries);
        crushingIngotRecipe("cupronickel", pRecipeOutput, registries);
        crushingIngotRecipe("he_mox", pRecipeOutput, registries);
        crushingIngotRecipe("he_uranium", pRecipeOutput, registries);
        crushingIngotRecipe("kanthal", pRecipeOutput, registries);
        crushingIngotRecipe("le_mox", pRecipeOutput, registries);
        crushingIngotRecipe("le_uranium", pRecipeOutput, registries);
        crushingIngotRecipe("plutonium", pRecipeOutput, registries);
        crushingIngotRecipe("silicon", pRecipeOutput, registries);
        crushingIngotRecipe("superconductor", pRecipeOutput, registries);
        crushingIngotRecipe("uranium_235", pRecipeOutput, registries);
        crushingIngotRecipe("uranium_238", pRecipeOutput, registries);
        //Gems
        fullGemRecipe("dark_gem", 2, pRecipeOutput, registries);
        fullGemRecipe("diamond", 2, pRecipeOutput, registries);
        fullGemRecipe("emerald", 2, pRecipeOutput, registries);
        fullGemRecipe("lapis", 6, pRecipeOutput, registries);
        fullGemRecipe("quartz", 2, pRecipeOutput, registries);
        fullGemRecipe("apatite", 2, pRecipeOutput, registries);
        fullGemRecipe("sulfur", 2, pRecipeOutput, registries);
        fullGemRecipe("fluorite", 4, pRecipeOutput, registries);
        fullGemRecipe("cinnabar", 2, pRecipeOutput, registries);
        fullGemRecipe("amber", 2, pRecipeOutput, registries);
        fullGemRecipe("peridot", 2, pRecipeOutput, registries);
        fullGemRecipe("ruby", 2, pRecipeOutput, registries);
        fullGemRecipe("sapphire", 2, pRecipeOutput, registries);
        fullGemRecipe("topaz", 2, pRecipeOutput, registries);
        fullGemRecipe("arcane_crystal", 2, pRecipeOutput, registries);
        fullGemRecipe("black_quartz", 2, pRecipeOutput, registries);
        //Only gem
        gemCrushCrystalRecipe("certus_quartz", pRecipeOutput, registries);
        gemCrushCrystalRecipe("fluix", pRecipeOutput, registries);
        gemCrushCrystalRecipe("amethyst", pRecipeOutput, registries);
        gemCrushCrystalRecipe("entro", pRecipeOutput, registries);
        //Only ores
        crushingOreRecipe("coal", 2, pRecipeOutput, registries);
        crushingOreRecipe("redstone", 4, pRecipeOutput, registries);
        crushingOreRecipe("lignite_coal", 2, pRecipeOutput, registries);
        crushingOreRecipe("saltpeter", 2, pRecipeOutput, registries);
        crushingOreRecipe("monazite", 2, pRecipeOutput, registries);
        crushingOreRecipe("bauxite", 2, pRecipeOutput, registries);
        crushingOreRecipe("salt", 2, pRecipeOutput, registries);

        CrushingRecipeBuilder.crushingRecipe(Items.COAL, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "coal")), 200, registries)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_coal", hasItem(Items.COAL))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/coal_dust_from_item")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "coal")), Items.COAL, 200, registries)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_coal_dust", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "coal")), registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/coal")));
        CrushingRecipeBuilder.crushingRecipe(Items.CHARCOAL, OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "charcoal")), 200, registries)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrushingMultiplier(true)
                .unlockedBy("has_charcoal", hasItem(Items.CHARCOAL))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/charcoal_dust_from_item")));
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "charcoal")), Items.CHARCOAL, 200, registries)
                .setAllowEmpty(false)
                .setResultAmount(1)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_charcoal_dust", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + "charcoal")), registries))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/charcoal")));
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "coal_coke")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/coal_coke")), 200, registries)
                .unlockedBy("has_coal_coke", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "coal_coke")), registries))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/coal_coke_dust")));
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "bricks/normal")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/brick")), 200, registries)
                .unlockedBy("has_brick", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "bricks/normal")), registries))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/brick_dust")));
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "plates/carbon")), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/carbon")), 200, registries)
                .unlockedBy("has_carbon_plate", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "plates/carbon")), registries))
                .setResultAmount(1)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(true)
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/carbon_dust_from_plate")));

        CrystallizeRecipeBuilder.crystallizeRecipe(Items.AMETHYST_BLOCK, Items.AMETHYST_SHARD, 200, registries)
                .allowEmpty()
                .setResultAmount(4)
                .setMinTier(2)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_amethyst_block", hasItem(Items.AMETHYST_BLOCK))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/amethyst_from_block")));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.QUARTZ_BLOCK, Items.QUARTZ, 200, registries)
                .allowEmpty()
                .setResultAmount(4)
                .setMinTier(2)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_quartz_block", hasItem(Items.QUARTZ_BLOCK))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/quartz_from_block")));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.AMETHYST_CLUSTER, Items.BUDDING_AMETHYST, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(4)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_amethyst_cluster", hasItem(Items.AMETHYST_CLUSTER))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/budding_amethyst")));
        CrystallizeRecipeBuilder.crystallizeRecipe(Items.OBSIDIAN, Items.CRYING_OBSIDIAN, 200, registries)
                .allowEmpty()
                .setResultAmount(1)
                .setMinTier(4)
                .setIgnoreCrystallizeMultiplier(true)
                .unlockedBy("has_obsidian", hasItem(Items.OBSIDIAN))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/crying_obsidian")));
    }

    protected static void crushingGeneralizedRecipe(String input, Integer amount, String from, Boolean mult, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + input)), 200, registries)
                .unlockedBy("has_" + input + "_" + from, hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input)), registries))
                .setResultAmount(amount)
                .setAllowEmpty(false)
                .setIgnoreCrushingMultiplier(mult)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + input + "_dust_from_" + from)));
    }

    protected static void crushingOreRecipe(String input, Integer amount, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingGeneralizedRecipe(input, amount, "ore", Boolean.FALSE, recipeOutput, registries);
    }

    protected static void crushingIngotRecipe(String input, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingGeneralizedRecipe(input, 1, "ingot", Boolean.TRUE, recipeOutput, registries);
    }

    protected static void crushingGemRecipe(String input, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingGeneralizedRecipe(input, 1, "gem", Boolean.TRUE, recipeOutput, registries);
    }

    protected static void crystallizeGeneralizedRecipe(String input, Integer amount, String from, Boolean mult, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        CrystallizeRecipeBuilder.crystallizeRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "gems/" + input)), 200, registries)
                .unlockedBy("has_" + input + "_" + from, hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", from + "s/" + input)), registries))
                .setResultAmount(amount)
                .setAllowEmpty(false)
                .setIgnoreCrystallizeMultiplier(mult)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crystallize/" + input + "_from_" + from)));
    }

    protected static void crystallizeDustRecipe(String input, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crystallizeGeneralizedRecipe(input, 1, "dust", Boolean.TRUE, recipeOutput, registries);
    }

    protected static void crystallizeOreRecipe(String input, Integer amount, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crystallizeGeneralizedRecipe(input, amount, "ore", Boolean.FALSE, recipeOutput, registries);
    }

    private static void doubleCookingRecipe(String metalName, Item output, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        String outputString = output.toString().replace("minecraft:", "").replace("occultism:", "");
        var dustTag = OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName));
        SimpleCookingRecipeBuilder
                .smelting(ingredientOf(dustTag, registries), RecipeCategory.MISC, CookingBookCategory.BLOCKS, output, 0.7f, 200)
                .unlockedBy("has_" + metalName + "_dust", hasTag(dustTag, registries))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "smelting/" + outputString + "_from_dust")));

        SimpleCookingRecipeBuilder
                .blasting(ingredientOf(dustTag, registries), RecipeCategory.MISC, CookingBookCategory.BLOCKS, output, 0.7f, 100)
                .unlockedBy("has_" + metalName + "_dust", hasTag(dustTag, registries))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "blasting/" + outputString + "_from_dust")));
    }

    private static void gemCrushCrystalRecipe(String gemName, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingGemRecipe(gemName, recipeOutput, registries);
        crystallizeDustRecipe(gemName, recipeOutput, registries);
    }

    private static void fullGemRecipe(String gemName, Integer amount, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingOreRecipe(gemName, (int)(amount*1.5), recipeOutput, registries);
        crystallizeOreRecipe(gemName, amount, recipeOutput, registries);
        crushingGemRecipe(gemName, recipeOutput, registries);
        crystallizeDustRecipe(gemName, recipeOutput, registries);
    }

    private static void crushingMetalRecipe(String metalName, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingIngotRecipe(metalName, recipeOutput, registries);
        crushingOreRecipe(metalName, 2, recipeOutput, registries);

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/" + metalName)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName)), 200, registries)
                .unlockedBy("has_raw_" + metalName, hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/" + metalName)), registries))
                .setResultAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_raw")));

        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_" + metalName)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/" + metalName)), 1600, registries)
                .unlockedBy("has_raw_" + metalName + "_block", hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_" + metalName)), registries))
                .setResultAmount(18)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dust_from_raw_block")));


        CrushingRecipeBuilder.crushingRecipe(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "clumps/" + metalName)), OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "dirty_dusts/" + metalName)), 200, registries)
                .unlockedBy("has_clump_" + metalName, hasTag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath("c", "clumps/" + metalName)), registries))
                .setResultAmount(2)
                .setAllowEmpty(false)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crushing/" + metalName + "_dirty_dust_from_clump")));
    }

    private static void fullMetalRecipe(String metalName, Item ingot, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingMetalRecipe(metalName, recipeOutput, registries);
        doubleCookingRecipe(metalName, ingot, recipeOutput, registries);
    }

    private static void tripleCrushSmeltBlastRecipe(String input, Item output, RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        crushingIngotRecipe(input, recipeOutput, registries);
        doubleCookingRecipe(input, output, recipeOutput, registries);
    }
}