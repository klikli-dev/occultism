package com.klikli_dev.occultism.datagen.recipes;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class MinerRecipes extends RecipeProvider {
    public MinerRecipes(PackOutput pOutput) {
        super(pOutput);
    }

    public static void ores(Consumer<FinishedRecipe> consumer) {
        makeOreRecipe("agate",200,consumer);
        makeOreRecipe("aluminum",422,consumer);
        makeOreRecipe("amber",184,consumer);
        makeOreRecipe("amethyst",200,consumer);
        makeOreRecipe("aquamarine",200,consumer);
        makeOreRecipe("ardite",159,consumer);
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),Ingredient.of(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+"arcane_crystal"))),200)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/"+"arcane_crystal"));
        makeOreRecipe("beryl",200,consumer);
        makeOreRecipe("boron",199,consumer);
        makeOreRecipe("certus_quartz",187,consumer);
        makeOreRecipe("cinnabar",190,consumer);
        makeVanillaItemRecipe(Items.CLAY,300,consumer);
        makeVanillaOreRecipe("coal",1000,consumer);
        makeOreRecipe("cobalt",163,consumer);
        makeVanillaOreRecipe("copper",584,consumer);
        makeVanillaOreRecipe("diamond",218,consumer);
//        makeOreRecipe("dimensional_shard",127,consumer);
        makeModOreItemRecipe(new ResourceLocation("rftools","dimensional_shard_ore"),127,consumer);
        makeModOreItemRecipe(new ResourceLocation("draconicevolution","draconium_ore"),142,consumer);
        makeOreRecipe("electrotine",155,consumer);
        makeVanillaOreRecipe("emerald",156,consumer);
        makeOreRecipe("garnet",200,consumer);
        makeVanillaItemRecipe(Items.GLOWSTONE,234,consumer);
        makeVanillaOreRecipe("gold",311,consumer);
        makeVanillaItemRecipe(Items.GRAVEL,300,consumer);
        makeOreRecipe("heliodor",200,consumer);
        makeOreRecipe("indicolite",200,consumer);
        makeOreRecipe("inferium",190,consumer);
        makeOreRecipe("iolite",200,consumer);
        makeVanillaOreRecipe("iron",750,consumer);
        makeVanillaOreRecipe("lapis",343,consumer);
        makeOreRecipe("lead",500,consumer);
        makeOreRecipe("lithium",201,consumer);
        makeVanillaItemRecipe(Items.MAGMA_BLOCK,300,consumer);
        makeOreRecipe("magnesium",233,consumer);
        makeOreRecipe("malachite",200,consumer);
        makeOreRecipe("mithril",169,consumer);
        makeOreRecipe("morganite",200,consumer);
        makeVanillaItemRecipe(Items.NETHER_GOLD_ORE,373,consumer);
        makeVanillaItemRecipe(Items.NETHER_QUARTZ_ORE,560,consumer);
        makeOreRecipe("nickel",232,consumer);
        makeOreRecipe("niter",244,consumer);
        makeVanillaItemRecipe(Items.OBSIDIAN,300,consumer);
        makeOreRecipe("onyx",200,consumer);
        makeOreRecipe("opal",200,consumer);
        makeOreRecipe("osmium",203,consumer);
        makeVanillaItemRecipe(OccultismBlocks.OTHERSTONE.get().asItem(),50,consumer);
        makeOreRecipe("peridot",200,consumer);
        makeOreRecipe("platinum",150,consumer);
        makeOreRecipe("prosperity",155,consumer);
        makeVanillaItemRecipe(Items.REDSTONE_ORE,515,consumer);
        makeOreRecipe("ruby",200,consumer);
        makeOreRecipe("salt",160,consumer);
        makeOreRecipe("sapphire",200,consumer);
        makeOreRecipe("silver",381,consumer);
        makeModOreItemRecipe(new ResourceLocation("ae2","sky_stone_block"),50,consumer);
        makeOreRecipe("sulfur",222,consumer);
        makeOreRecipe("tanzanite",200,consumer);
        makeOreRecipe("thorium",222,consumer);
        makeOreRecipe("tin",602,consumer);
        makeOreRecipe("topaz",200,consumer);
        makeOreRecipe("tungsten",192,consumer);
        makeOreRecipe("uranium",140,consumer);
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),Ingredient.of(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+"xpetrified_ore"))),200)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/"+"xpetrified_ore"));
        makeOreRecipe("zinc",186,consumer);
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),"forbidden_arcanus:runic_stones",200)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/runic_stone"));


    }
    public static void makeModOreItemRecipe(ResourceLocation item,int weight, Consumer<FinishedRecipe> consumer) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),item,weight)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .itemExists()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/"+item.getPath()));
    }
    public static void makeVanillaItemRecipe(Item type, int weight, Consumer<FinishedRecipe> consumer) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),Ingredient.of(type),weight)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/"+getItemName(type)));
    }
    public static void makeVanillaOreRecipe(String type,int weight,Consumer<FinishedRecipe> consumer) {
        MinerRecipeBuilder.minerRecipe(Ingredient.of(OccultismTags.Items.Miners.ORES),Ingredient.of(OccultismTags.makeItemTag(new ResourceLocation("forge","ores/"+type))),weight)
                .unlockedBy("has_miner",has(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .allowEmpty()
                .save(consumer, new ResourceLocation(Occultism.MODID, "miner/ores/"+type+"_ore"));
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
