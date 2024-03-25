package com.klikli_dev.occultism.datagen.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.builders.CrushingRecipeBuilder;
import com.klikli_dev.occultism.datagen.builders.MinerRecipeBuilder;
import com.klikli_dev.occultism.datagen.builders.SpiritFireRecipeBuilder;
import com.klikli_dev.occultism.datagen.builders.SpiritTradeRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class OccultismRecipeProvider extends RecipeProvider {
    public OccultismRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        crushingRecipes(pRecipeOutput);
        miningRecipes(pRecipeOutput);
        spiritFireRecipes(pRecipeOutput);
        spiritTradeRecipes(pRecipeOutput);

    }

    private void spiritTradeRecipes(RecipeOutput pRecipeOutput) {
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get()),new ItemStack(OccultismBlocks.OTHERWORLD_SAPLING.get()))
                .unlockedBy("has_otherworld_sapling_natural", has(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_trade/otherworld_sapling"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.STONE),new ItemStack(OccultismBlocks.OTHERSTONE.get(),2))
                .unlockedBy("has_stone", has(Tags.Items.STONE))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_trade/stone_to_otherstone"));
        SpiritTradeRecipeBuilder.spiritTradeRecipe(Ingredient.of(Tags.Items.STONE),new ItemStack(OccultismBlocks.OTHERSTONE.get(),4))
                .unlockedBy("has_stone", has(Tags.Items.STONE))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_trade/test"));

    }

    private void spiritFireRecipes(RecipeOutput pRecipeOutput) {
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Tags.Items.FEATHERS),new ItemStack(OccultismItems.AWAKENED_FEATHER.get()))
                .unlockedBy("has_feather", has(Tags.Items.FEATHERS))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/awakened_feather"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.WRITABLE_BOOK),new ItemStack(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .unlockedBy("has_writable_book", has(Items.WRITABLE_BOOK))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/book_of_binding_empty"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_GOLD_IMPURE.get()),new ItemStack(OccultismItems.CHALK_GOLD.get()))
                .unlockedBy("has_chalk_gold_impure", has(OccultismItems.CHALK_GOLD_IMPURE.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/chalk_gold"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_PURPLE_IMPURE.get()),new ItemStack(OccultismItems.CHALK_PURPLE.get()))
                .unlockedBy("has_chalk_purple_impure", has(OccultismItems.CHALK_PURPLE.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/chalk_purple"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_RED_IMPURE.get()),new ItemStack(OccultismItems.CHALK_RED.get()))
                .unlockedBy("has_chalk_red_impure", has(OccultismItems.CHALK_RED.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/chalk_red"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.CHALK_WHITE_IMPURE.get()),new ItemStack(OccultismItems.CHALK_WHITE.get()))
                .unlockedBy("has_chalk_white_impure", has(OccultismItems.CHALK_WHITE.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/chalk_white"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Blocks.ANDESITE),new ItemStack(OccultismBlocks.OTHERSTONE.get()))
                .unlockedBy("has_andesite", has(Blocks.ANDESITE))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/otherstone"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismBlocks.OTHERWORLD_LOG.get()),new ItemStack(OccultismItems.OTHERWORLD_ASHES.get()))
                .unlockedBy("has_otherworld_log", has(OccultismBlocks.OTHERWORLD_LOG.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/otherworld_ashes"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()),new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .unlockedBy("has_demons_dream_essence", has(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/otherworld_essence"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.OAK_SAPLING),new ItemStack(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get()))
                .unlockedBy("has_oak_sapling", has(Items.OAK_SAPLING))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/otherworld_sapling_natural"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.BLACK_DYE),new ItemStack(OccultismItems.PURIFIED_INK.get()))
                .unlockedBy("has_black_dye", has(Items.BLACK_DYE))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/purified_ink"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Tags.Items.GEMS_DIAMOND),new ItemStack(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/spirit_attuned_gem"));
        SpiritFireRecipeBuilder.spiritFireRecipe(Ingredient.of(Items.BOOK),new ItemStack(OccultismItems.TABOO_BOOK.get()))
                .unlockedBy("has_book", has(Items.BOOK))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "spirit_fire/taboo_book"));
    }

    private void miningRecipes(RecipeOutput pRecipeOutput) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.MASTER),Ingredient.of(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/stella_arcanum"))),100)
                .unlockedBy("has_stella_arcanum_ore", has(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/stella_arcanum"))))
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "miner/master/stella_arcanum"));
        MinerRecipes.basic_resources(pRecipeOutput);
        MinerRecipes.deeps(pRecipeOutput);
        MinerRecipes.master_resources(pRecipeOutput);
        MinerRecipes.ores(pRecipeOutput);
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismItems.DEBUG_WAND.get()),Ingredient.of(OccultismBlocks.OTHERSTONE.get().asItem()),200)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "miner/debug_wand"));
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
