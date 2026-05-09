package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.crafting.recipe.PasteRepairItemRecipe;
import com.klikli_dev.occultism.datagen.recipe.builders.SpiritFireRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.criterion.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.criterion.ItemPredicate.Builder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

public class OccultismRecipeProvider extends RecipeProvider {
    public OccultismRecipeProvider(Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static OccultismRecipeProvider create(Provider registries, RecipeOutput output) {
        return new OccultismRecipeProvider(registries, output);
    }

    private static void smeltingRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        autoSmeltingRecipe(OccultismBlocks.OTHERCOBBLESTONE.asItem(), OccultismBlocks.OTHERSTONE.asItem(), 0.5f, pRecipeOutput, registries);
        autoSmeltingRecipe(OccultismBlocks.OTHERSTONE.asItem(), OccultismBlocks.POLISHED_OTHERSTONE.asItem(), 0.5f, pRecipeOutput, registries);
        autoSmeltingRecipe(OccultismBlocks.POLISHED_OTHERSTONE.asItem(), OccultismItems.BURNT_OTHERSTONE.asItem(), 0.15f, pRecipeOutput, registries);
        autoSmeltingRecipe(OccultismBlocks.POLISHED_OTHERROCK.asItem(), OccultismItems.BURNT_OTHERROCK.asItem(), 0.15f, pRecipeOutput, registries);
        autoSmeltingRecipe(OccultismBlocks.OTHERSTONE_BRICKS.asItem(), OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.asItem(), 0.3f, pRecipeOutput, registries);

        autoSmeltingRecipe(OccultismBlocks.OTHERCOBBLEROCK.asItem(), OccultismBlocks.OTHERROCK.asItem(), 0.5f, pRecipeOutput, registries);
        autoSmeltingRecipe(OccultismBlocks.OTHERROCK.asItem(), OccultismBlocks.POLISHED_OTHERROCK.asItem(), 0.5f, pRecipeOutput, registries);
        autoSmeltingRecipe(OccultismBlocks.OTHERROCK_BRICKS.asItem(), OccultismBlocks.CRACKED_OTHERROCK_BRICKS.asItem(), 0.3f, pRecipeOutput, registries);
    }

    protected static void autoSmeltingRecipe(Item input, Item output, Float exp, RecipeOutput pRecipeOutput, Provider registries) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(input), RecipeCategory.MISC, CookingBookCategory.BLOCKS, output, exp, 200)
                .unlockedBy("has_" + input.toString().replace("occultism:", ""), TriggerInstance.hasItems(input))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "smelting/" + output.toString().replace("occultism:", ""))));
    }

    private static void oresCookingRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        doubleCookingRecipe(OccultismTags.Items.SILVER_ORE, OccultismItems.SILVER_INGOT.get(), pRecipeOutput, registries);
        doubleCookingRecipe(OccultismTags.Items.RAW_SILVER, OccultismItems.SILVER_INGOT.get(), pRecipeOutput, registries);
        doubleCookingRecipe(OccultismTags.Items.IESNIUM_ORE, OccultismItems.IESNIUM_INGOT.get(), pRecipeOutput, registries);
        doubleCookingRecipe(OccultismTags.Items.RAW_IESNIUM, OccultismItems.IESNIUM_INGOT.get(), pRecipeOutput, registries);
    }

    protected static void doubleCookingRecipe(TagKey<Item> tagInput, Item output, RecipeOutput recipeOutput, Provider registries) {
        String outputString = output.toString().replace("minecraft:", "").replace("occultism:", "");
        String simpleInputString = tagInput.toString().contains("c:ores") ? "ore" : "raw";
        String condtionString = "has_" + tagInput.toString().substring(26).replace("materials/", "").replace("/", "_").replace("]", "");
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        var tagHolder = items.getOrThrow(tagInput);

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(tagHolder), RecipeCategory.MISC, CookingBookCategory.BLOCKS, output, 0.7f, 200)
                .unlockedBy(condtionString, TriggerInstance.hasItems(Builder.item().of(items, tagInput).build()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "smelting/" + outputString + "_from_" + simpleInputString)));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(tagHolder), RecipeCategory.MISC, CookingBookCategory.BLOCKS, output, 0.7f, 100)
                .unlockedBy(condtionString, TriggerInstance.hasItems(Builder.item().of(items, tagInput).build()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "blasting/" + outputString + "_from_" + simpleInputString)));
    }

    private static void spiritFireRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        spiritfireTransmute(OccultismItems.CHALK_WHITE_IMPURE.asItem(), OccultismItems.CHALK_WHITE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_LIGHT_GRAY_IMPURE.asItem(), OccultismItems.CHALK_LIGHT_GRAY.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_GRAY_IMPURE.asItem(), OccultismItems.CHALK_GRAY.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_BLACK_IMPURE.asItem(), OccultismItems.CHALK_BLACK.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_YELLOW_IMPURE.asItem(), OccultismItems.CHALK_YELLOW.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_PURPLE_IMPURE.asItem(), OccultismItems.CHALK_PURPLE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_LIME_IMPURE.asItem(), OccultismItems.CHALK_LIME.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_ORANGE_IMPURE.asItem(), OccultismItems.CHALK_ORANGE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_RED_IMPURE.asItem(), OccultismItems.CHALK_RED.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_BLUE_IMPURE.asItem(), OccultismItems.CHALK_BLUE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_GREEN_IMPURE.asItem(), OccultismItems.CHALK_GREEN.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_LIGHT_BLUE_IMPURE.asItem(), OccultismItems.CHALK_LIGHT_BLUE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_PINK_IMPURE.asItem(), OccultismItems.CHALK_PINK.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_CYAN_IMPURE.asItem(), OccultismItems.CHALK_CYAN.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_BROWN_IMPURE.asItem(), OccultismItems.CHALK_BROWN.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.CHALK_MAGENTA_IMPURE.asItem(), OccultismItems.CHALK_MAGENTA.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismItems.DEMONS_DREAM_ESSENCE.asItem(), OccultismItems.OTHERWORLD_ESSENCE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Items.OAK_SAPLING, OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Items.ANDESITE, OccultismBlocks.OTHERSTONE.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Items.DIORITE, OccultismBlocks.OTHERROCK.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Tags.Items.GEMS_DIAMOND, OccultismItems.SPIRIT_ATTUNED_GEM.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismTags.Items.OTHERWORLD_LOGS, OccultismItems.OTHERWORLD_ASHES.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Tags.Items.FEATHERS, OccultismItems.AWAKENED_FEATHER.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Tags.Items.DYES_BLACK, OccultismItems.PURIFIED_INK.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(Items.BOOK, OccultismItems.TABOO_BOOK.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(OccultismTags.Items.BOOKS_FOR_EMPTY, OccultismItems.BOOK_OF_BINDING_EMPTY.asItem(), pRecipeOutput, registries);
        spiritfireTransmute(ItemTags.FLOWERS, OccultismBlocks.OTHERFLOWER.asItem(), pRecipeOutput, registries);
    }

    protected static void spiritfireTransmute(TagKey<Item> input, Item output, RecipeOutput pRecipeOutput, Provider registries) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(items.getOrThrow(input)), new ItemStackTemplate(output))
                .unlockedBy("has_tag_item", TriggerInstance.hasItems(Builder.item().of(items, input).build()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/" + output.toString().replace("occultism:", ""))));
    }

    protected static void spiritfireTransmute(Item input, Item output, RecipeOutput pRecipeOutput, Provider registries) {
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(input), new ItemStackTemplate(output))
                .unlockedBy("has_" + input.toString().replace("minecraft:", "").replace("occultism:", ""), TriggerInstance.hasItems(input))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_fire/" + output.toString().replace("occultism:", ""))));
    }

    private static void stonecutterRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_SLAB, OccultismBlocks.OTHERSTONE, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_STAIRS, OccultismBlocks.OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_WALL, OccultismBlocks.OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS, OccultismBlocks.OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS_SLAB, OccultismBlocks.OTHERSTONE, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS_STAIRS, OccultismBlocks.OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS_WALL, OccultismBlocks.OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERCOBBLESTONE_SLAB, OccultismBlocks.OTHERCOBBLESTONE, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERCOBBLESTONE_STAIRS, OccultismBlocks.OTHERCOBBLESTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERCOBBLESTONE_WALL, OccultismBlocks.OTHERCOBBLESTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.POLISHED_OTHERSTONE_SLAB, OccultismBlocks.POLISHED_OTHERSTONE, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.POLISHED_OTHERSTONE_STAIRS, OccultismBlocks.POLISHED_OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.POLISHED_OTHERSTONE_WALL, OccultismBlocks.POLISHED_OTHERSTONE, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS_SLAB, OccultismBlocks.OTHERSTONE_BRICKS, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS_STAIRS, OccultismBlocks.OTHERSTONE_BRICKS, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERSTONE_BRICKS_WALL, OccultismBlocks.OTHERSTONE_BRICKS, items);

        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_SLAB, OccultismBlocks.OTHERROCK, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_STAIRS, OccultismBlocks.OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_WALL, OccultismBlocks.OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS, OccultismBlocks.OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS_SLAB, OccultismBlocks.OTHERROCK, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS_STAIRS, OccultismBlocks.OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS_WALL, OccultismBlocks.OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERCOBBLEROCK_SLAB, OccultismBlocks.OTHERCOBBLEROCK, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERCOBBLEROCK_STAIRS, OccultismBlocks.OTHERCOBBLEROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERCOBBLEROCK_WALL, OccultismBlocks.OTHERCOBBLEROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.POLISHED_OTHERROCK_SLAB, OccultismBlocks.POLISHED_OTHERROCK, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.POLISHED_OTHERROCK_STAIRS, OccultismBlocks.POLISHED_OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.POLISHED_OTHERROCK_WALL, OccultismBlocks.POLISHED_OTHERROCK, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS_SLAB, OccultismBlocks.OTHERROCK_BRICKS, 2, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS_STAIRS, OccultismBlocks.OTHERROCK_BRICKS, items);
        otherStonecutter(pRecipeOutput, OccultismBlocks.OTHERROCK_BRICKS_WALL, OccultismBlocks.OTHERROCK_BRICKS, items);
    }

    protected static void otherStonecutter(RecipeOutput recipeOutput, ItemLike result, ItemLike material, int resultCount, HolderGetter<Item> items) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, resultCount)
                .unlockedBy(getHasName(material), TriggerInstance.hasItems(material))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "stonecutting/" + getItemName(result) + "_from_" + getItemName(material))));
    }

    protected static void otherStonecutter(RecipeOutput recipeOutput, ItemLike result, ItemLike material, HolderGetter<Item> items) {
        otherStonecutter(recipeOutput, result, material, 1, items);
    }

    private static void otherflowerRecipes(RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        otherflowerDye(Items.WHITE_DYE, Tags.Items.DYES_WHITE, pRecipeOutput, items);
        otherflowerDye(Items.LIGHT_GRAY_DYE, Tags.Items.DYES_LIGHT_GRAY, pRecipeOutput, items);
        otherflowerDye(Items.GRAY_DYE, Tags.Items.DYES_GRAY, pRecipeOutput, items);
        otherflowerDye(Items.BLACK_DYE, Tags.Items.DYES_BLACK, pRecipeOutput, items);
        otherflowerDye(Items.BROWN_DYE, Tags.Items.DYES_BROWN, pRecipeOutput, items);
        otherflowerDye(Items.RED_DYE, Tags.Items.DYES_RED, pRecipeOutput, items);
        otherflowerDye(Items.ORANGE_DYE, Tags.Items.DYES_ORANGE, pRecipeOutput, items);
        otherflowerDye(Items.YELLOW_DYE, Tags.Items.DYES_YELLOW, pRecipeOutput, items);
        otherflowerDye(Items.LIME_DYE, Tags.Items.DYES_LIME, pRecipeOutput, items);
        otherflowerDye(Items.GREEN_DYE, Tags.Items.DYES_GREEN, pRecipeOutput, items);
        otherflowerDye(Items.CYAN_DYE, Tags.Items.DYES_CYAN, pRecipeOutput, items);
        otherflowerDye(Items.BLUE_DYE, Tags.Items.DYES_BLUE, pRecipeOutput, items);
        otherflowerDye(Items.LIGHT_BLUE_DYE, Tags.Items.DYES_LIGHT_BLUE, pRecipeOutput, items);
        otherflowerDye(Items.PINK_DYE, Tags.Items.DYES_PINK, pRecipeOutput, items);
        otherflowerDye(Items.MAGENTA_DYE, Tags.Items.DYES_MAGENTA, pRecipeOutput, items);
        otherflowerDye(Items.PURPLE_DYE, Tags.Items.DYES_PURPLE, pRecipeOutput, items);
    }

    protected static void otherflowerDye(ItemLike result, TagKey<Item> colorTag, RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, result, 3)
                .requires(OccultismBlocks.OTHERFLOWER.asItem())
                .requires(colorTag)
                .unlockedBy("has_otherflower", TriggerInstance.hasItems(OccultismBlocks.OTHERFLOWER.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.parse(
                        "occultism:crafting/otherflower_to_" + colorTag.toString().substring(31).replace("]", "_") + "dye")));
    }

    private static void grayPasteRecipes(RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        grayPasting(OccultismTags.Items.ECHO_DUST, Items.ECHO_SHARD, RecipeCategory.MISC, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.LAPIS_DUST, Items.LAPIS_LAZULI, RecipeCategory.MISC, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.AMETHYST_DUST, Items.AMETHYST_SHARD, RecipeCategory.MISC, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.EMERALD_DUST, Items.EMERALD, RecipeCategory.MISC, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.ICE_DUST, Items.ICE, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.PACKED_ICE_DUST, Items.PACKED_ICE, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.BLUE_ICE_DUST, Items.BLUE_ICE, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.END_STONE_DUST, Items.END_STONE, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.OBSIDIAN_DUST, Items.OBSIDIAN, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.CALCITE_DUST, Items.CALCITE, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.BLACKSTONE_DUST, Items.BLACKSTONE, RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.OTHERSTONE_DUST, OccultismBlocks.OTHERSTONE.asItem(), RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
        grayPasting(OccultismTags.Items.OTHERROCK_DUST, OccultismBlocks.OTHERROCK.asItem(), RecipeCategory.BUILDING_BLOCKS, pRecipeOutput, items);
    }

    protected static void grayPasting(TagKey<Item> input, Item output, RecipeCategory category, RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        ShapelessRecipeBuilder.shapeless(items, category, output)
                .requires(OccultismItems.GRAY_PASTE).requires(input)
                .unlockedBy("has_gray_paste", TriggerInstance.hasItems(OccultismItems.GRAY_PASTE))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/gray_paste/" + output.toString().replace("minecraft:", "").replace("occultism:", ""))));
    }

    @Override
    public void buildRecipes() {
        HolderGetter<Item> items = this.registries.lookupOrThrow(Registries.ITEM);
        this.ritualRecipes(this.output, this.registries);
        this.miningRecipes(this.output, this.registries);
        this.spiritJobRecipes(this.output, this.registries);
        this.craftingRecipes(this.output, items);
        this.woodRecipes(this.output, items);
        smeltingRecipes(this.output, this.registries);
        oresCookingRecipes(this.output, this.registries);
        spiritFireRecipes(this.output, this.registries);
        stonecutterRecipes(this.output, this.registries);
        otherflowerRecipes(this.output, items);
        grayPasteRecipes(this.output, items);
    }

    private void ritualRecipes(RecipeOutput recipeOutput, Provider registries) {
        RitualRecipes.ritualRecipes(recipeOutput, registries);
    }

    private void miningRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        MinerRecipes.minerRecipes(pRecipeOutput, registries);
    }

    private void spiritJobRecipes(RecipeOutput pRecipeOutput, Provider registries) {
        SpiritJobRecipes.spiritJobRecipes(pRecipeOutput, registries);
    }

    private void metalRecipes(RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        // Iesnium metal
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.IESNIUM_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_iesnium_ingot", this.has(OccultismTags.Items.IESNIUM_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_block")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_IESNIUM)
                .unlockedBy("has_iesnium_block", this.has(OccultismTags.Items.STORAGE_BLOCK_IESNIUM))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_ingot_from_block")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.IESNIUM_NUGGET)
                .unlockedBy("has_iesnium_nugget", this.has(OccultismTags.Items.IESNIUM_NUGGET))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_ingot_from_nuggets")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.IESNIUM_NUGGET.get(), 9)
                .requires(OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_iesnium_ingot", this.has(OccultismTags.Items.IESNIUM_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_nugget")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.RAW_IESNIUM_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.RAW_IESNIUM)
                .unlockedBy("has_raw_iesnium", this.has(OccultismTags.Items.RAW_IESNIUM))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_iesnium_block")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.RAW_IESNIUM.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM)
                .unlockedBy("has_raw_iesnium_block", this.has(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_iesnium_ingot_from_block")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.NETHERITE_DUST.get())
                .requires(OccultismTags.Items.NETHERITE_SCRAP_DUST)
                .requires(OccultismTags.Items.NETHERITE_SCRAP_DUST)
                .requires(OccultismTags.Items.NETHERITE_SCRAP_DUST)
                .requires(OccultismTags.Items.NETHERITE_SCRAP_DUST)
                .requires(OccultismTags.Items.GOLD_DUST)
                .requires(OccultismTags.Items.GOLD_DUST)
                .requires(OccultismTags.Items.GOLD_DUST)
                .requires(OccultismTags.Items.GOLD_DUST)
                .unlockedBy("has_netherite_scrap_dust", this.has(OccultismTags.Items.NETHERITE_SCRAP_DUST))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/netherite_dust")));

        // Silver metal
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.SILVER_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", this.has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_block")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_SILVER)
                .unlockedBy("has_silver_block", this.has(OccultismTags.Items.STORAGE_BLOCK_SILVER))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_ingot_from_block")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.SILVER_NUGGET)
                .unlockedBy("has_silver_nugget", this.has(OccultismTags.Items.SILVER_NUGGET))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_ingot_from_nuggets")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.SILVER_NUGGET.get(), 9)
                .requires(OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", this.has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_nugget")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.RAW_SILVER_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.RAW_SILVER)
                .unlockedBy("has_raw_silver", this.has(OccultismTags.Items.RAW_SILVER))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_silver_block")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.RAW_SILVER.get(), 9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER)
                .unlockedBy("has_raw_silver_block", this.has(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/raw_silver_ingot_from_block")));
    }

    private void craftingRecipes(RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        SpecialRecipeBuilder.special(PasteRepairItemRecipe::new).save(pRecipeOutput,
                ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath("minecraft", "repair_item")));
        SpecialRecipeBuilder.special(BoundBookOfBindingRecipe::new).save(pRecipeOutput,
                ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/bound_book_of_binding")));

        this.metalRecipes(pRecipeOutput, items);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .requires(OccultismItems.AWAKENED_FEATHER.get())
                .requires(OccultismItems.PURIFIED_INK.get())
                .requires(OccultismItems.TABOO_BOOK.get())
                .unlockedBy("has_taboo_book", TriggerInstance.hasItems(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_empty")));

        // Afrit
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .pattern("cpf")
                .pattern("pbp")
                .pattern(" p ")
                .define('p', Tags.Items.DYES_YELLOW)
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", TriggerInstance.hasItems(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_afrit")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .pattern(" p ")
                .pattern("pbp")
                .pattern(" p ")
                .define('p', Tags.Items.DYES_YELLOW)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_taboo_book", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_afrit_from_empty")));
//        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_afrit", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
//                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_afrit")));

        // Djinni
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .pattern("cgf")
                .pattern("gbg")
                .pattern(" g ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('g', Tags.Items.DYES_PURPLE)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", TriggerInstance.hasItems(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_djinni")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .define('g', Tags.Items.DYES_PURPLE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_djinni_from_empty")));

//        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_djinni", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
//                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_djinni")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .unlockedBy("has_book_of_calling_djinni", this.has(OccultismTags.Items.BOOK_OF_CALLING_DJINNI))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_djinni_from_calling")));

        // Foliot
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .pattern("cwf")
                .pattern("wbw")
                .pattern(" w ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('w', Tags.Items.DYES_BLUE)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", TriggerInstance.hasItems(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_foliot")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .pattern(" w ")
                .pattern("wbw")
                .pattern(" w ")
                .define('w', Tags.Items.DYES_BLUE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_foliot_from_empty")));

//        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_foliot", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
//                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_foliot")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .unlockedBy("has_book_of_calling_foliot", this.has(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_foliot_from_calling")));

        // Marid
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_MARID.get())
                .pattern("cof")
                .pattern("pbp")
                .pattern(" o ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('o', Tags.Items.DYES_GREEN)
                .define('p', Tags.Items.DYES_GREEN)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", TriggerInstance.hasItems(OccultismItems.TABOO_BOOK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_marid")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_MARID.get())
                .pattern(" o ")
                .pattern("pbp")
                .pattern(" o ")
                .define('o', Tags.Items.DYES_GREEN)
                .define('p', Tags.Items.DYES_GREEN)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_marid_from_empty")));

//        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get())
//                .requires(OccultismItems.BOOK_OF_BINDING_MARID.get())
//                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
//                .unlockedBy("has_book_of_binding_marid", InventoryChangeTrigger.TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_MARID.get()))
//                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_binding_bound_marid")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(Items.FURNACE)
                .unlockedBy("has_book_of_binding_bound_djinni", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_djinni_manage_machine")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismItems.BRUSH.get())
                .unlockedBy("has_book_of_binding_bound_foliot", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_cleaner")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismTags.Items.METAL_AXES)
                .unlockedBy("has_book_of_binding_bound_foliot", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_lumberjack")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .unlockedBy("has_book_of_binding_bound_foliot", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_farmer")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(Tags.Items.CHESTS)
                .unlockedBy("has_book_of_binding_bound_foliot", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/book_of_calling_foliot_transport_items")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.BRUSH.get())
                .pattern("ppp")
                .pattern("wws")
                .define('p', ItemTags.PLANKS)
                .define('w', ItemTags.WOOL)
                .define('s', Tags.Items.STRINGS)
                .unlockedBy("has_wool", this.has(ItemTags.WOOL))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/brush")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, OccultismItems.BUTCHER_KNIFE.get())
                .pattern(" is")
                .pattern("is ")
                .pattern("s  ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iron_ingot", this.has(Tags.Items.INGOTS_IRON))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/butcher_knife")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.TALLOW_BLOCK.get())
                .requires(Ingredient.of(items.getOrThrow(OccultismTags.Items.TALLOW)), 9)
                .unlockedBy("has_tallow", this.has(OccultismTags.Items.TALLOW))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/tallow_block")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.TALLOW, 9)
                .requires(OccultismBlocks.TALLOW_BLOCK.get())
                .unlockedBy("has_tallow_block", TriggerInstance.hasItems(OccultismBlocks.TALLOW_BLOCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/tallow")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE.get())
                .pattern("s")
                .pattern("t")
                .define('s', Tags.Items.STRINGS)
                .define('t', OccultismTags.Items.TALLOW)
                .unlockedBy("has_tallow", this.has(OccultismTags.Items.TALLOW))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_WHITE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_WHITE)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_white")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_LIGHT_GRAY)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_light_gray")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_GRAY.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_GRAY)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_gray")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_BLACK.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_BLACK)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_black")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_BROWN.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_BROWN)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_brown")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_RED.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_red")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_ORANGE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_ORANGE)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_orange")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_YELLOW.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_yellow")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_LIME.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_LIME)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_lime")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_GREEN.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_GREEN)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_green")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_CYAN.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_CYAN)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_cyan")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_BLUE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_blue")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_light_blue")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_PINK.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_PINK)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_pink")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_MAGENTA.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_MAGENTA)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_magenta")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.DECORATIONS, OccultismBlocks.LARGE_CANDLE_PURPLE.get())
                .requires(OccultismBlocks.LARGE_CANDLE.get())
                .requires(Tags.Items.DYES_PURPLE)
                .unlockedBy("has_large_candle", TriggerInstance.hasItems(OccultismBlocks.LARGE_CANDLE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/large_candle_purple")));

        // Chalks
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_YELLOW_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(OccultismTags.Items.GOLD_DUST)
                .requires(OccultismTags.Items.GOLD_DUST)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_yellow_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_PURPLE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.END_STONE_DUST)
                .requires(OccultismTags.Items.OBSIDIAN_DUST)
                .requires(OccultismTags.Items.OBSIDIAN_DUST)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_purple_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_RED_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.AFRIT_ESSENCE.get())
                .requires(Items.TORCHFLOWER)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_red_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.SILVER_DUST)
                .requires(OccultismTags.Items.IRON_DUST)
                .requires(OccultismTags.Items.CALCITE_DUST)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_light_gray_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_GRAY_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.GRAY_PASTE)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_gray_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_BLACK_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.WITHERITE_DUST)
                .requires(OccultismTags.Items.WITHERITE_DUST)
                .requires(OccultismTags.Items.WITHERITE_DUST)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_black_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_BROWN_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.CRUELTY_ESSENCE)
                .requires(Items.COCOA_BEANS)
                .requires(Items.BROWN_MUSHROOM)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_brown_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_ORANGE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.CURSED_HONEY)
                .requires(Items.GLOW_BERRIES)
                .requires(OccultismTags.Items.BLAZE_DUST)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_orange_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_LIME_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.RESEARCH_DUST)
                .requires(OccultismTags.Items.EMERALD_DUST)
                .requires(Tags.Items.SLIME_BALLS)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_lime_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_GREEN_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.NATURE_PASTE)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_green_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_CYAN_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.IESNIUM_DUST)
                .requires(OccultismTags.Items.ECHO_DUST)
                .requires(Items.GLOW_INK_SAC)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_cyan_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.ICE_DUST)
                .requires(OccultismTags.Items.PACKED_ICE_DUST)
                .requires(OccultismTags.Items.BLUE_ICE_DUST)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_light_blue_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_BLUE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.MARID_ESSENCE)
                .requires(OccultismTags.Items.LAPIS_DUST)
                .requires(OccultismTags.Items.TUBE_CORALS)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_blue_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_MAGENTA_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.DRAGONYST_DUST)
                .requires(OccultismTags.Items.AMETHYST_DUST)
                .requires(Items.CHORUS_FRUIT)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_magenta_impure")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.CHALK_PINK_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.DEMONIC_MEAT)
                .requires(OccultismItems.DEMONIC_MEAT)
                .requires(OccultismItems.DEMONIC_MEAT)
                .unlockedBy("has_chalk_white_impure", TriggerInstance.hasItems(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_pink_impure")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.CHALK_WHITE_IMPURE.get())
                .pattern("xy")
                .pattern("xy")
                .pattern("xy")
                .define('x', OccultismTags.Items.CHALK_BASE_DUST)
                .define('y', OccultismTags.Items.OTHERWORLD_WOOD_DUST)
                .unlockedBy("has_ashes", TriggerInstance.hasItems(OccultismItems.OTHERWORLD_ASHES.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_white_impure")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.CHALK_WHITE_IMPURE.get())
                .pattern("xy")
                .pattern("xy")
                .pattern("xy")
                .define('y', OccultismTags.Items.CHALK_BASE_DUST)
                .define('x', OccultismTags.Items.OTHERWORLD_WOOD_DUST)
                .unlockedBy("has_ashes", TriggerInstance.hasItems(OccultismItems.OTHERWORLD_ASHES.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chalk_white_impure2")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, OccultismItems.DEMONS_DREAM_ESSENCE.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', Ingredient.of(items.getOrThrow(OccultismTags.Items.DATURA_CROP)))
                .unlockedBy("has_datura", this.has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/demons_dream_essence_from_fruit_or_seed")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .requires(OccultismTags.Items.DATURA_SEEDS)
                .requires(OccultismTags.Items.BOOKS)
                .unlockedBy("has_datura", this.has(OccultismTags.Items.DATURA_SEEDS))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/dictionary_of_spirits")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.LIST_FILTER.get())
                .pattern(" p ")
                .pattern("sds")
                .pattern(" s ")
                .define('p', Items.PAPER)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('d', Ingredient.of(items.getOrThrow(OccultismTags.Items.DATURA_CROP)))
                .unlockedBy("has_datura", this.has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/list_filter")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.ATTRIBUTE_FILTER.get())
                .pattern(" s ")
                .pattern("pdp")
                .pattern(" s ")
                .define('p', Items.PAPER)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('d', Ingredient.of(items.getOrThrow(OccultismTags.Items.DATURA_CROP)))
                .unlockedBy("has_datura", this.has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/attribute_filter")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.DIVINATION_ROD.get())
                .pattern(" g ")
                .pattern("xyx")
                .pattern(" g ")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('x', Tags.Items.GLASS_BLOCKS)
                .define('y', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", TriggerInstance.hasItems(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/divination_rod")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.OTHERWORLD_GOGGLES.get())
                .pattern(" l ")
                .pattern("lil")
                .pattern(" f ")
                .define('l', Tags.Items.LEATHERS)
                .define('i', OccultismItems.INFUSED_LENSES.get())
                .define('f', OccultismItems.LENS_FRAME.get())
                .unlockedBy("has_infused_lenses", TriggerInstance.hasItems(OccultismItems.INFUSED_LENSES.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/goggles")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .pattern("ggg")
                .pattern("gbg")
                .pattern("ggg")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('b', OccultismBlocks.SACRIFICIAL_BOWL.get())
                .unlockedBy("has_sacrificial_bowl", TriggerInstance.hasItems(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/golden_sacrificial_bowl")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get())
                .pattern("ggg")
                .pattern("gbg")
                .pattern("ggg")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('b', OccultismBlocks.DARK_SACRIFICIAL_BOWL.get())
                .unlockedBy("has_dark_sacrificial_bowl", TriggerInstance.hasItems(OccultismBlocks.DARK_SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/dark_golden_sacrificial_bowl")));


        // Iesnium tool
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.IESNIUM_PICKAXE.get())
                .pattern("iii")
                .pattern(" s ")
                .pattern(" s ")
                .define('i', OccultismTags.Items.IESNIUM_INGOT)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iesnium_ingot", this.has(OccultismTags.Items.IESNIUM_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/iesnium_pickaxe")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.LENS_FRAME.get())
                .pattern("ooo")
                .pattern("s s")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .define('s', OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", this.has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/lens_frame")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.LENS_FRAME.get())
                .pattern("ooo")
                .pattern("sws")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERROCK.get())
                .define('s', OccultismTags.Items.SILVER_INGOT)
                .define('w', Tags.Items.DYES_WHITE)
                .unlockedBy("has_silver_ingot", this.has(OccultismTags.Items.SILVER_INGOT))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/lens_frame_alt")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.LENSES.get())
                .pattern("ppp")
                .pattern("pgp")
                .pattern("ppp")
                .define('p', Tags.Items.GLASS_PANES)
                .define('g', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", TriggerInstance.hasItems(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/lenses")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.MAGIC_LAMP_EMPTY.get())
                .pattern(" s ")
                .pattern("sis")
                .pattern(" ss")
                .define('s', OccultismTags.Items.SILVER_INGOT)
                .define('i', OccultismItems.SPIRIT_ATTUNED_GEM)
                .unlockedBy("has_spirit_attuned_gem", TriggerInstance.hasItems(OccultismItems.SPIRIT_ATTUNED_GEM))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/magic_lamp_empty")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.OTHERSTONE_FRAME.get())
                .pattern("ooo")
                .pattern("o o")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_frame")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.OTHERROCK_FRAME.get())
                .pattern("ooo")
                .pattern("o o")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_frame")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_PEDESTAL.get())
                .pattern("s s")
                .pattern(" o ")
                .pattern("sss")
                .define('s', OccultismBlocks.OTHERSTONE_SLAB.get())
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_pedestal")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERROCK_PEDESTAL.get())
                .pattern("s s")
                .pattern(" o ")
                .pattern("sss")
                .define('s', OccultismBlocks.OTHERROCK_SLAB.get())
                .define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_pedestal")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismBlocks.STORAGE_STABILIZER_TIER0.get())
                .requires(OccultismBlocks.OTHERSTONE_PEDESTAL.get())
                .unlockedBy("has_otherstone_pedestal", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE_PEDESTAL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_stabilizer_tier0")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get())
                .requires(OccultismBlocks.OTHERROCK_PEDESTAL.get())
                .unlockedBy("has_otherrock_pedestal", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK_PEDESTAL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_stabilizer_tier0_dark")));

        this.stairBuilder(OccultismBlocks.OTHERSTONE_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERSTONE.asItem()))
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get())
                .pattern("oo").define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_pressure_plate")));
        this.buttonBuilder(OccultismBlocks.OTHERSTONE_BUTTON.get(), Ingredient.of(OccultismBlocks.OTHERSTONE.asItem()))
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_button")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_wall")));

        this.stairBuilder(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERCOBBLESTONE.asItem()))
                .unlockedBy("has_othercobblestone", TriggerInstance.hasItems(OccultismBlocks.OTHERCOBBLESTONE.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblestone_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERCOBBLESTONE_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.OTHERCOBBLESTONE.get())
                .unlockedBy("has_othercobblestone", TriggerInstance.hasItems(OccultismBlocks.OTHERCOBBLESTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblestone_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERCOBBLESTONE_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERCOBBLESTONE.get())
                .unlockedBy("has_othercobblestone", TriggerInstance.hasItems(OccultismBlocks.OTHERCOBBLESTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblestone_wall")));

        this.stairBuilder(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get(), Ingredient.of(OccultismBlocks.POLISHED_OTHERSTONE.asItem()))
                .unlockedBy("has_polished_otherstone", TriggerInstance.hasItems(OccultismBlocks.POLISHED_OTHERSTONE.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherstone_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.POLISHED_OTHERSTONE.get())
                .unlockedBy("has_polished_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherstone_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.POLISHED_OTHERSTONE_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.POLISHED_OTHERSTONE.get())
                .unlockedBy("has_polished_otherstone", TriggerInstance.hasItems(OccultismBlocks.POLISHED_OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherstone_wall")));

        this.stairBuilder(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERSTONE_BRICKS.asItem()))
                .unlockedBy("has_otherstone_bricks", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE_BRICKS.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.OTHERSTONE_BRICKS.get())
                .unlockedBy("has_otherstone_bricks", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE_BRICKS.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_BRICKS_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERSTONE_BRICKS.get())
                .unlockedBy("has_otherstone_bricks", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE_BRICKS.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks_wall")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERSTONE_BRICKS.get(), 4)
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_bricks")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get())
                .pattern("o")
                .pattern("o")
                .define('o', OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get())
                .unlockedBy("has_otherstone_bricks_slab", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chiseled_otherstone_bricks")));

        this.stairBuilder(OccultismBlocks.OTHERROCK_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERROCK.asItem()))
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERROCK_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get())
                .pattern("oo").define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_pressure_plate")));
        this.buttonBuilder(OccultismBlocks.OTHERROCK_BUTTON.get(), Ingredient.of(OccultismBlocks.OTHERROCK.asItem()))
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_button")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERROCK_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_wall")));

        this.stairBuilder(OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERCOBBLEROCK.asItem()))
                .unlockedBy("has_othercobblerock", TriggerInstance.hasItems(OccultismBlocks.OTHERCOBBLEROCK.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblerock_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERCOBBLEROCK_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.OTHERCOBBLEROCK.get())
                .unlockedBy("has_othercobblerock", TriggerInstance.hasItems(OccultismBlocks.OTHERCOBBLEROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblerock_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERCOBBLEROCK_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERCOBBLEROCK.get())
                .unlockedBy("has_othercobblerock", TriggerInstance.hasItems(OccultismBlocks.OTHERCOBBLEROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/othercobblerock_wall")));

        this.stairBuilder(OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get(), Ingredient.of(OccultismBlocks.POLISHED_OTHERROCK.asItem()))
                .unlockedBy("has_polished_otherrock", TriggerInstance.hasItems(OccultismBlocks.POLISHED_OTHERROCK.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherrock_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.POLISHED_OTHERROCK_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.POLISHED_OTHERROCK.get())
                .unlockedBy("has_polished_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherrock_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.POLISHED_OTHERROCK_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.POLISHED_OTHERROCK.get())
                .unlockedBy("has_polished_otherrock", TriggerInstance.hasItems(OccultismBlocks.POLISHED_OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/polished_otherrock_wall")));

        this.stairBuilder(OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERROCK_BRICKS.asItem()))
                .unlockedBy("has_otherrock_bricks", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK_BRICKS.asItem())).save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_bricks_stairs")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERROCK_BRICKS_SLAB.get(), 6)
                .pattern("ooo").define('o', OccultismBlocks.OTHERROCK_BRICKS.get())
                .unlockedBy("has_otherrock_bricks", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK_BRICKS.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_bricks_slab")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERROCK_BRICKS_WALL.get(), 6)
                .pattern("ooo").pattern("ooo").define('o', OccultismBlocks.OTHERROCK_BRICKS.get())
                .unlockedBy("has_otherrock_bricks", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK_BRICKS.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_bricks_wall")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERROCK_BRICKS.get(), 4)
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherrock_bricks")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get())
                .pattern("o")
                .pattern("o")
                .define('o', OccultismBlocks.OTHERROCK_BRICKS_SLAB.get())
                .unlockedBy("has_otherrock_bricks_slab", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/chiseled_otherrock_bricks")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.OTHERWORLDLY_TABLET.get())
                .pattern("aga")
                .pattern("gsg")
                .pattern("aga")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('s', OccultismBlocks.OTHERSTONE.get())
                .define('a', OccultismTags.Items.OTHERWORLD_WOOD_DUST)
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_tablet")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.OTHERWORLDLY_TABLET.get())
                .pattern("aga")
                .pattern("gsg")
                .pattern("aga")
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('s', OccultismBlocks.OTHERROCK.get())
                .define('a', OccultismTags.Items.OTHERWORLD_WOOD_DUST)
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherstone_tablet2")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.SACRIFICIAL_BOWL.get())
                .pattern("o o")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", TriggerInstance.hasItems(OccultismBlocks.OTHERSTONE.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/sacrificial_bowl")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get())
                .requires(OccultismBlocks.SACRIFICIAL_BOWL.asItem())
                .requires(Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_sacrificial_bowl", TriggerInstance.hasItems(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/copper_sacrificial_bowl")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get())
                .requires(OccultismBlocks.SACRIFICIAL_BOWL.asItem())
                .requires(OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_sacrificial_bowl", TriggerInstance.hasItems(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/silver_sacrificial_bowl")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.DARK_SACRIFICIAL_BOWL.get())
                .pattern("o o")
                .pattern("ooo")
                .define('o', OccultismBlocks.OTHERROCK.get())
                .unlockedBy("has_otherrock", TriggerInstance.hasItems(OccultismBlocks.OTHERROCK.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/dark_sacrificial_bowl")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get())
                .requires(OccultismBlocks.DARK_SACRIFICIAL_BOWL.asItem())
                .requires(Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_dark_sacrificial_bowl", TriggerInstance.hasItems(OccultismBlocks.DARK_SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/dark_copper_sacrificial_bowl")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get())
                .requires(OccultismBlocks.DARK_SACRIFICIAL_BOWL.asItem())
                .requires(OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_dark_sacrificial_bowl", TriggerInstance.hasItems(OccultismBlocks.DARK_SACRIFICIAL_BOWL.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/dark_silver_sacrificial_bowl")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get())
                .pattern("gg")
                .pattern("gg")
                .define('g', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", TriggerInstance.hasItems(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_attuned_crystal")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get())
                .pattern("ggg")
                .define('g', OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", TriggerInstance.hasItems(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_attuned_pickaxe_head")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.SPIRIT_CAMPFIRE.get())
                .pattern(" S ")
                .pattern("S#S")
                .pattern("LLL")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LOGS)
                .define('#', OccultismTags.Items.DATURA_CROP)
                .unlockedBy("has_datura", this.has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_campfire")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, OccultismBlocks.SPIRIT_LANTERN.get())
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .define('X', Tags.Items.NUGGETS_IRON)
                .define('#', OccultismBlocks.SPIRIT_TORCH.get())
                .unlockedBy("has_spirit_torch", TriggerInstance.hasItems(OccultismItems.SPIRIT_TORCH.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_lantern")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, OccultismBlocks.SPIRIT_TORCH.get())
                .pattern("X")
                .pattern("#")
                .pattern("S")
                .define('X', ItemTags.COALS)
                .define('#', OccultismTags.Items.DATURA_CROP)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_datura", this.has(OccultismTags.Items.DATURA_CROP))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/spirit_torch")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.STORAGE_CONTROLLER.get())
                .pattern("d")
                .pattern("b")
                .define('d', OccultismItems.DIMENSIONAL_MATRIX.get())
                .define('b', OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .unlockedBy("has_dimensional_matrix", TriggerInstance.hasItems(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_controller")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.STORAGE_CONTROLLER_DARK.get())
                .pattern("d")
                .pattern("b")
                .define('d', OccultismItems.DIMENSIONAL_MATRIX.get())
                .define('b', OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get())
                .unlockedBy("has_dimensional_matrix", TriggerInstance.hasItems(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_controller_dark")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, OccultismItems.STORAGE_REMOTE_INERT.get())
                .pattern("iai")
                .pattern("iti")
                .pattern("isi")
                .define('t', OccultismItems.OTHERWORLDLY_TABLET.get())
                .define('i', OccultismTags.Items.IESNIUM_NUGGET)
                .define('a', OccultismItems.SPIRIT_ATTUNED_GEM)
                .define('s', OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_otherstone_tablet", TriggerInstance.hasItems(OccultismItems.OTHERWORLDLY_TABLET.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/storage_remote_inert")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, OccultismBlocks.OTHERGLASS_NATURAL.get())
                .pattern("nen")
                .pattern("ege")
                .pattern("nen")
                .define('n', OccultismTags.Items.IESNIUM_NUGGET)
                .define('e', OccultismTags.Items.END_STONE_DUST)
                .define('g', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_iesnium_nugget", TriggerInstance.hasItems(OccultismItems.IESNIUM_NUGGET.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherglass")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, Items.MOSSY_COBBLESTONE)
                .requires(Items.COBBLESTONE)
                .requires(OccultismItems.NATURE_PASTE)
                .unlockedBy("has_nature_paste", TriggerInstance.hasItems(OccultismItems.NATURE_PASTE))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/nature_paste_mossy_cobblestone")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, Items.MOSSY_STONE_BRICKS)
                .requires(Items.STONE_BRICKS)
                .requires(OccultismItems.NATURE_PASTE)
                .unlockedBy("has_nature_paste", TriggerInstance.hasItems(OccultismItems.NATURE_PASTE))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/nature_paste_mossy_stone_bricks")));
    }

    private void woodRecipes(RecipeOutput pRecipeOutput, HolderGetter<Item> items) {
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERPLANKS.get(), 4)
                .requires(OccultismTags.Items.OTHERWORLD_LOGS)
                .unlockedBy("has_otherworld_log", TriggerInstance.hasItems(OccultismBlocks.OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERWORLD_WOOD.get(), 3)
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.OTHERWORLD_LOG.get())
                .unlockedBy("has_otherworld_log", TriggerInstance.hasItems(OccultismBlocks.OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherworld_wood")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get(), 3)
                .pattern("oo")
                .pattern("oo")
                .define('o', OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get())
                .unlockedBy("has_otherworld_log", TriggerInstance.hasItems(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/stripped_otherworld_wood")));
        this.stairBuilder(OccultismBlocks.OTHERPLANKS_STAIRS.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_stairs")));
        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, OccultismBlocks.OTHERPLANKS_SLAB.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_slab")));
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_pressure_plate")));
        this.buttonBuilder(OccultismBlocks.OTHERPLANKS_BUTTON.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_button")));
        this.fenceBuilder(OccultismBlocks.OTHERPLANKS_FENCE.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_fence")));
        this.fenceGateBuilder(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_fence_gate")));
        this.doorBuilder(OccultismBlocks.OTHERPLANKS_DOOR.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_door")));
        this.trapdoorBuilder(OccultismBlocks.OTHERPLANKS_TRAPDOOR.get(), Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_trapdoor")));
        this.signBuilder(OccultismItems.OTHERPLANKS_SIGN, Ingredient.of(OccultismBlocks.OTHERPLANKS.asItem()))
                .unlockedBy("has_otherplanks", TriggerInstance.hasItems(OccultismBlocks.OTHERPLANKS.asItem()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_sign")));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, OccultismItems.OTHERPLANKS_HANGING_SIGN, 6)
                .group("hanging_sign")
                .define('#', OccultismBlocks.STRIPPED_OTHERWORLD_LOG.asItem())
                .define('X', Items.IRON_CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_otherworld_log", TriggerInstance.hasItems(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "crafting/otherplanks_hanging_sign")));
    }
}
