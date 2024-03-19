package com.klikli_dev.occultism.datagen.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class OccultismRecipeProvider extends RecipeProvider {

    public OccultismRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        blastingRecipes(consumer);
        craftingRecipes(consumer);
        smeltingRecipes(consumer);
    }



    private void craftingRecipes(Consumer<FinishedRecipe> consumer) {
        metalRecipes(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .requires(OccultismItems.AWAKENED_FEATHER.get())
                .requires(OccultismItems.PURIFIED_INK.get())
                .requires(OccultismItems.TABOO_BOOK.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_empty"));

        // Afrit
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .pattern("cpf")
                .pattern("pbp")
                .pattern(" p ")
                .define('p', Tags.Items.DYES_PURPLE)
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_afrit"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .pattern(" p ")
                .pattern("pbp")
                .pattern(" p ")
                .define('p', Tags.Items.DYES_PURPLE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_afrit_from_empty"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get())
                .requires(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .unlockedBy("has_book_of_binding_afrit", has(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_bound_afrit"));

        // Djinni
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .pattern("cgf")
                .pattern("gbg")
                .pattern(" g ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('g', Tags.Items.DYES_GREEN)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_djinni"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .define('g', Tags.Items.DYES_GREEN)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_djinni_from_empty"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .unlockedBy("has_book_of_binding_djinni", has(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_bound_djinni"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .unlockedBy("has_book_of_calling_djinni", has(OccultismTags.Items.BOOK_OF_CALLING_DJINNI))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_bound_djinni_from_calling"));

        // Foliot
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .pattern("cwf")
                .pattern("wbw")
                .pattern(" w ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('w', Tags.Items.DYES_WHITE)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_foliot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .pattern(" w ")
                .pattern("wbw")
                .pattern(" w ")
                .define('w', Tags.Items.DYES_WHITE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_foliot_from_empty"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .unlockedBy("has_book_of_binding_foliot", has(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_bound_foliot"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .unlockedBy("has_book_of_calling_foliot", has(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_bound_foliot_from_calling"));

        // Marid
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_MARID.get())
                .pattern("cof")
                .pattern("pbp")
                .pattern(" o ")
                .define('c', OccultismItems.PURIFIED_INK.get())
                .define('b', OccultismItems.TABOO_BOOK.get())
                .define('o', Tags.Items.DYES_ORANGE)
                .define('p', Tags.Items.DYES_PURPLE)
                .define('f', OccultismItems.AWAKENED_FEATHER.get())
                .unlockedBy("has_taboo_book", has(OccultismItems.TABOO_BOOK.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_marid"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_MARID.get())
                .pattern(" o ")
                .pattern("pbp")
                .pattern(" o ")
                .define('o', Tags.Items.DYES_ORANGE)
                .define('p', Tags.Items.DYES_PURPLE)
                .define('b', OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .unlockedBy("has_empty_binding", has(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_marid_from_empty"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get())
                .requires(OccultismItems.BOOK_OF_BINDING_MARID.get())
                .requires(OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .unlockedBy("has_book_of_binding_marid", has(OccultismItems.BOOK_OF_BINDING_MARID.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_binding_bound_marid"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .requires(Items.FURNACE)
                .unlockedBy("has_book_of_binding_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_calling_djinni_manage_machine"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismItems.BRUSH.get())
                .unlockedBy("has_book_of_binding_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_calling_foliot_cleaner"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(OccultismTags.Items.METAL_AXES)
                .unlockedBy("has_book_of_binding_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_calling_foliot_lumberjack"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get())
                .requires(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .requires(Tags.Items.CHESTS)
                .unlockedBy("has_book_of_binding_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/book_of_calling_foliot_transport_items"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.BRUSH.get())
                .pattern("ppp")
                .pattern("wws")
                .define('p', ItemTags.PLANKS)
                .define('w', ItemTags.WOOL)
                .define('s', Tags.Items.STRING)
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/brush"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,OccultismItems.BUTCHER_KNIFE.get())
                .pattern(" is")
                .pattern("is ")
                .pattern("s  ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/butcher_knife"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OccultismBlocks.CANDLE_WHITE.get())
                .pattern("s")
                .pattern("t")
                .define('s', Tags.Items.STRING)
                .define('t',OccultismTags.Items.TALLOW)
                .unlockedBy("has_tallow", has(OccultismTags.Items.TALLOW))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/candle"));

        // Chalks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.CHALK_GOLD_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.GOLD_DUST)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/chalk_gold_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.CHALK_PURPLE_IMPURE.get())
                .requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismTags.Items.END_STONE_DUST)
                .requires(OccultismTags.Items.OBSIDIAN_DUST)
                .requires(OccultismTags.Items.OBSIDIAN_DUST)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/chalk_purple_impure"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.CHALK_RED_IMPURE.get())
.requires(OccultismItems.CHALK_WHITE_IMPURE.get())
                .requires(OccultismItems.AFRIT_ESSENCE.get())
                .requires(Items.BLAZE_POWDER)
                .unlockedBy("has_chalk_white", has(OccultismItems.CHALK_WHITE_IMPURE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/chalk_red_impure"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.CHALK_WHITE_IMPURE.get())
                .pattern("xy")
                .pattern("xy")
                .pattern("xy")
                .define('x', OccultismItems.BURNT_OTHERSTONE.get())
                .define('y', OccultismItems.OTHERWORLD_ASHES.get())
                .unlockedBy("has_ashes", has(OccultismItems.OTHERWORLD_ASHES.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/chalk_white_impure"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD,OccultismItems.DEMONS_DREAM_ESSENCE.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p',OccultismTags.Items.DATURA_CROP)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/demons_dream_essence_from_fruit"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD,OccultismItems.DEMONS_DREAM_ESSENCE.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p',OccultismTags.Items.DATURA_SEEDS)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_SEEDS))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/demons_dream_essence_from_seeds"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .requires(OccultismTags.Items.DATURA_SEEDS)
                .requires(OccultismTags.Items.BOOKS)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_SEEDS))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/dictionary_of_spirits"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,OccultismItems.DIVINATION_ROD.get())
                .pattern("xyx")
                .pattern("x x")
                .pattern(" x ")
                .define('x',Tags.Items.RODS_WOODEN)
                .define('y',OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/divination_rod"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,OccultismItems.OTHERWORLD_GOGGLES.get())
                .pattern(" l ")
                .pattern("lil")
                .pattern(" f ")
                .define('l',Tags.Items.LEATHER)
                .define('i',OccultismItems.INFUSED_LENSES.get())
                .define('f',OccultismItems.LENS_FRAME.get())
                .unlockedBy("has_infused_lenses", has(OccultismItems.INFUSED_LENSES.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/goggles"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .pattern("ggg")
                .pattern("gbg")
                .pattern("ggg")
                .define('g',Tags.Items.INGOTS_GOLD)
                .define('b',OccultismBlocks.SACRIFICIAL_BOWL.get())
                .unlockedBy("has_sacrificial_bowl", has(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/golden_sacrificial_bowl"));



        // Iesnium tool
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,OccultismItems.IESNIUM_PICKAXE.get())
                .pattern("iii")
                .pattern(" s ")
                .pattern(" s ")
                .define('i',OccultismTags.Items.IESNIUM_INGOT)
                .define('s',Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iesnium_ingot", has(OccultismTags.Items.IESNIUM_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/iesnium_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.LENS_FRAME.get())
                .pattern("ooo")
                .pattern("s s")
                .pattern("ooo")
                .define('o',OccultismBlocks.OTHERSTONE.get())
                .define('s',OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/lens_frame"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.LENSES.get())
                .pattern("ppp")
                .pattern("pgp")
                .pattern("ppp")
                .define('p',Tags.Items.GLASS_PANES)
                .define('g',OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/lenses"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.MAGIC_LAMP_EMPTY.get())
                .pattern(" s ")
                .pattern("sis")
                .pattern(" ss")
                .define('s',OccultismTags.Items.SILVER_INGOT)
                .define('i',OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/magic_lamp_empty"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.OTHERSTONE_FRAME.get())
                .pattern("ooo")
                .pattern("o o")
                .pattern("ooo")
                .define('o',OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/otherstone_frame"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,OccultismBlocks.OTHERSTONE_PEDESTAL.get())
                .pattern("s s")
                .pattern(" o ")
                .pattern("sss")
                .define('s',OccultismBlocks.OTHERSTONE_SLAB.get())
                .define('o', OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/otherstone_pedestal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,OccultismBlocks.OTHERSTONE_SLAB.get(),6)
                .pattern("ooo")
                .define('o',OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/otherstone_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,OccultismItems.OTHERSTONE_TABLET.get())
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .define('s',OccultismBlocks.OTHERSTONE_SLAB.get())
                .unlockedBy("has_otherstone_slab", has(OccultismBlocks.OTHERSTONE_SLAB.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/otherstone_tablet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,OccultismBlocks.SACRIFICIAL_BOWL.get())
                .pattern("o o")
                .pattern("ooo")
                .define('o',OccultismBlocks.OTHERSTONE.get())
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/sacrificial_bowl"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get())
                .pattern("gg")
                .pattern("gg")
                .define('g',OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/spirit_attuned_crystal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get())
                .pattern("ggg")
                .define('g',OccultismItems.SPIRIT_ATTUNED_GEM.get())
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/spirit_attuned_pickaxe_head"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismBlocks.SPIRIT_CAMPFIRE.get())
                .pattern(" S ")
                .pattern("S#S")
                .pattern("LLL")
                .define('S',Tags.Items.RODS_WOODEN)
                .define('L',ItemTags.LOGS)
                .define('#',OccultismTags.Items.DATURA_CROP)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/spirit_campfire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,OccultismBlocks.SPIRIT_LANTERN.get())
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .define('X',Tags.Items.NUGGETS_IRON)
                .define('#',OccultismBlocks.SPIRIT_TORCH.get())
                .unlockedBy("has_spirit_torch", has(OccultismBlocks.SPIRIT_TORCH.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/spirit_lantern"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,OccultismBlocks.SPIRIT_TORCH.get())
                .pattern("X")
                .pattern("#")
                .pattern("S")
                .define('X',ItemTags.COALS)
                .define('#',OccultismTags.Items.DATURA_CROP)
                .define('S',Tags.Items.RODS_WOODEN)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/spirit_torch"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismBlocks.STORAGE_CONTROLLER.get())
                .pattern("d")
                .pattern("b")
                .define('d',OccultismItems.DIMENSIONAL_MATRIX.get())
                .define('b',OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .unlockedBy("has_dimensional_matrix", has(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/storage_controller"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,OccultismItems.STORAGE_REMOTE_INERT.get())
                .pattern("gtg")
                .pattern("bgb")
                .pattern("bbb")
                .define('t',OccultismItems.OTHERSTONE_TABLET.get())
                .define('b',ItemTags.STONE_BUTTONS)
                .define('g',Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_otherstone_tablet", has(OccultismItems.OTHERSTONE_TABLET.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/storage_remote_inert"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.WORMHOLE_FRAME.get())
                .pattern(" g ")
                .pattern("gog")
                .pattern(" g ")
                .define('g',Tags.Items.INGOTS_GOLD)
                .define('o',OccultismItems.OTHERSTONE_FRAME.get())
                .unlockedBy("has_otherstone_frame", has(OccultismItems.OTHERSTONE_FRAME.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/wormhole_frame"));

    }

    private static void metalRecipes(Consumer<FinishedRecipe> consumer) {
        // Iesnium metal
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,OccultismBlocks.IESNIUM_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_iesnium_ingot", has(OccultismTags.Items.IESNIUM_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/iesnium_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.IESNIUM_INGOT.get(),9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_IESNIUM)
                .unlockedBy("has_iesnium_block", has(OccultismTags.Items.STORAGE_BLOCK_IESNIUM))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/iesnium_ingot_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.IESNIUM_INGOT.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.IESNIUM_NUGGET)
                .unlockedBy("has_iesnium_nugget", has(OccultismTags.Items.IESNIUM_NUGGET))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/iesnium_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.IESNIUM_NUGGET.get(),9)
                .requires(OccultismTags.Items.IESNIUM_INGOT)
                .unlockedBy("has_iesnium_ingot", has(OccultismTags.Items.IESNIUM_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/iesnium_nugget"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismBlocks.RAW_IESNIUM_BLOCK.get())
                        .pattern("ppp")
                        .pattern("ppp")
                        .pattern("ppp")
                        .define('p', OccultismTags.Items.RAW_IESNIUM)
                        .unlockedBy("has_raw_iesnium", has(OccultismTags.Items.RAW_IESNIUM))
                        .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/raw_iesnium_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.RAW_IESNIUM.get(),9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM)
                .unlockedBy("has_raw_iesnium_block", has(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/raw_iesnium_ingot_from_block"));

        // Silver metal
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,OccultismBlocks.SILVER_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/silver_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.SILVER_INGOT.get(),9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_SILVER)
                .unlockedBy("has_silver_block", has(OccultismTags.Items.STORAGE_BLOCK_SILVER))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/silver_ingot_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismItems.SILVER_INGOT.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.SILVER_NUGGET)
                .unlockedBy("has_silver_nugget", has(OccultismTags.Items.SILVER_NUGGET))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/silver_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.SILVER_NUGGET.get(),9)
                .requires(OccultismTags.Items.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(OccultismTags.Items.SILVER_INGOT))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/silver_nugget"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,OccultismBlocks.RAW_SILVER_BLOCK.get())
                .pattern("ppp")
                .pattern("ppp")
                .pattern("ppp")
                .define('p', OccultismTags.Items.RAW_SILVER)
                .unlockedBy("has_raw_silver", has(OccultismTags.Items.RAW_SILVER))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/raw_silver_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,OccultismItems.RAW_SILVER.get(),9)
                .requires(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER)
                .unlockedBy("has_raw_silver_block", has(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER))
                .save(consumer, new ResourceLocation(Occultism.MODID, "crafting/raw_silver_ingot_from_block"));
    }


    private void smeltingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(OccultismBlocks.OTHERSTONE.get()),RecipeCategory.MISC,OccultismItems.BURNT_OTHERSTONE.get(), 0.15f,200)
                .unlockedBy("has_otherstone", has(OccultismBlocks.OTHERSTONE.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/burnt_otherstone"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT,0.7f,200)
                .unlockedBy("has_copper_dust", has(OccultismTags.Items.COPPER_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/copper_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.GOLD_DUST), RecipeCategory.MISC, Items.GOLD_INGOT,0.7f,200)
                .unlockedBy("has_gold_dust", has(OccultismTags.Items.GOLD_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/gold_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.IESNIUM_ORE),RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(),0.7f,200)
                .unlockedBy("has_iesnium_ore", has(OccultismTags.Items.IESNIUM_ORE))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/iesnium_ingot"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.IESNIUM_DUST),RecipeCategory.MISC,OccultismItems.IESNIUM_INGOT.get(),0.7f,200)
                .unlockedBy("has_iesnium_dust", has(OccultismTags.Items.IESNIUM_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/iesnium_ingot_from_dust"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.RAW_IESNIUM),RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(),0.7f,200)
                .unlockedBy("has_raw_iesnium", has(OccultismTags.Items.RAW_IESNIUM))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/iesnium_ingot_from_raw"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.IRON_DUST),RecipeCategory.MISC, Items.IRON_INGOT,0.7f,200)
                .unlockedBy("has_iron_dust", has(OccultismTags.Items.IRON_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/iron_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.SILVER_ORE),RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(),0.7f,200)
                .unlockedBy("has_silver_ore", has(OccultismTags.Items.SILVER_ORE))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/silver_ingot"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.SILVER_DUST),RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(),0.7f,200)
                .unlockedBy("has_silver_dust", has(OccultismTags.Items.SILVER_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/silver_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(OccultismTags.Items.RAW_SILVER),RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(),0.7f,200)
                .unlockedBy("has_raw_silver", has(OccultismTags.Items.RAW_SILVER))
                .save(consumer, new ResourceLocation(Occultism.MODID,"smelting/silver_ingot_from_raw"));

    }

    private static void blastingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT,0.7f,100)
                .unlockedBy("has_copper_dust", has(OccultismTags.Items.COPPER_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/copper_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.GOLD_DUST), RecipeCategory.MISC, Items.GOLD_INGOT,0.7f,100)
                .unlockedBy("has_gold_dust", has(OccultismTags.Items.GOLD_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/gold_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.IESNIUM_ORE),RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(),0.7f,100)
                .unlockedBy("has_iesnium_ore", has(OccultismTags.Items.IESNIUM_ORE))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/iesnium_ingot"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.IESNIUM_DUST),RecipeCategory.MISC,OccultismItems.IESNIUM_INGOT.get(),0.7f,100)
                .unlockedBy("has_iesnium_dust", has(OccultismTags.Items.IESNIUM_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/iesnium_ingot_from_dust"));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.RAW_IESNIUM),RecipeCategory.MISC, OccultismItems.IESNIUM_INGOT.get(),0.7f,100)
                .unlockedBy("has_raw_iesnium", has(OccultismTags.Items.RAW_IESNIUM))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/iesnium_ingot_from_raw"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.IRON_DUST),RecipeCategory.MISC, Items.IRON_INGOT,0.7f,100)
                .unlockedBy("has_iron_dust", has(OccultismTags.Items.IRON_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/iron_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.SILVER_ORE),RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(),0.7f,100)
                .unlockedBy("has_silver_ore", has(OccultismTags.Items.SILVER_ORE))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/silver_ingot"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.SILVER_DUST),RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(),0.7f,100)
                .unlockedBy("has_silver_dust", has(OccultismTags.Items.SILVER_DUST))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/silver_ingot_from_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(OccultismTags.Items.RAW_SILVER),RecipeCategory.MISC, OccultismItems.SILVER_INGOT.get(),0.7f,100)
                .unlockedBy("has_raw_silver", has(OccultismTags.Items.RAW_SILVER))
                .save(consumer, new ResourceLocation(Occultism.MODID,"blasting/silver_ingot_from_raw"));
    }
}
