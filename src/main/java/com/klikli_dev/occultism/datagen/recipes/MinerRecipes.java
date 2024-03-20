package com.klikli_dev.occultism.datagen.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class MinerRecipes extends RecipeProvider {
    public MinerRecipes(PackOutput pOutput) {
        super(pOutput);
    }

    public static void ores(Consumer<FinishedRecipe> consumer) {
        makeOreRecipe("agate",200,consumer);
    }

    public static void makeOreRecipe(String type,int weight,Consumer<FinishedRecipe> consumer) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),Ingredient.of(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+type))),weight)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/"+type+"_ore"));
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

    }

    public static void deeps(Consumer<FinishedRecipe> consumer) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_COAL_ORE),1000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_coal_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_COPPER_ORE),584)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_copper_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_DIAMOND_ORE),218)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_diamond_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_EMERALD_ORE),156)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_emerald_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_GOLD_ORE),311)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_gold_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_IRON_ORE),750)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_iron_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_LAPIS_ORE),343)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_lapis_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(Items.DEEPSLATE_REDSTONE_ORE),515)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_redstone_ore"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.DEEPS),Ingredient.of(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()),381)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/deeps/deepslate_silver_ore"));


    }

    public static void master_resources(Consumer<FinishedRecipe> consumer) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.MASTER),Ingredient.of(Items.ANCIENT_DEBRIS),100)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/master/ancient_debris"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.MASTER),Ingredient.of(OccultismBlocks.IESNIUM_ORE.get()),100)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/master/iesnium_ore"));
    }
    public static void basic_resources(Consumer<FinishedRecipe> consumer) {

        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.ANDESITE),10000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/andesite"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.DIORITE),10000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/diorite"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.END_STONE),30)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/end_stone"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.GRANITE),10000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/granite"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.MOSSY_COBBLESTONE),200)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/mossy_cobblestone"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.MOSSY_STONE_BRICKS),10000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/mossy_stone_bricks"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.NETHERRACK),1000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/netherrack"));
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.BASIC_RESOURCES),Ingredient.of(Items.STONE),10000)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/basic_resources/stone"));
    }

}
