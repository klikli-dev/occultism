package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.RitualRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class RitualRecipes extends RecipeProvider {


    private static final ResourceLocation RITUAL_SUMMON = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon");
    private static final ResourceLocation RITUAL_SUMMON_JOB = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_spirit_with_job");
    private static final ResourceLocation RITUAL_RESURRECT_FAMILIAR = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "resurrect_familiar");
    private static final ResourceLocation PENTACLE_SUMMON_FOLIOT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_foliot");
    private static final ResourceLocation PENTACLE_SUMMON_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_afrit");
    // Ritual Types
    private static final ResourceLocation RITUAL_FAMILIAR = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "familiar");
    private static final ResourceLocation RITUAL_CRAFT_WITH_SPIRIT_NAME = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_with_spirit_name");
    private static final ResourceLocation RITUAL_CRAFT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft");
    private static final ResourceLocation RITUAL_CRAFT_MINER_SPIRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_miner_spirit");
    // Pentacle IDs
    private static final ResourceLocation PENTACLE_POSSESS_DJINNI = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_djinni");
    private static final ResourceLocation PENTACLE_POSSESS_FOLIOT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_foliot");
    private static final ResourceLocation PENTACLE_POSSESS_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_afrit");
    private static final ResourceLocation PENTACLE_CRAFT_DJINNI = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_djinni");
    private static final ResourceLocation PENTACLE_CRAFT_FOLIOT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_foliot");
    private static final ResourceLocation PENTACLE_CRAFT_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_afrit");
    private static final ResourceLocation PENTACLE_CRAFT_MARID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_marid");


    public RitualRecipes(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    private static ItemStack makeLoreSpawnEgg(Item item, String key) {
        ItemStack output = new ItemStack(item);
        output.set(DataComponents.LORE, new ItemLore(List.of(Component.translatable(key + ".tooltip"))));
        output.set(DataComponents.ITEM_NAME, Component.translatable(key));
        return output;
    }

    private static ItemStack makeRitualDummy(ResourceLocation location) {
        return new ItemStack(BuiltInRegistries.ITEM.get(location));
    }

    private static ItemStack makeJeiDummy(ResourceLocation location) {
        return new ItemStack(BuiltInRegistries.ITEM.get(location));
    }

    private static ItemStack makeJeiNoneDummy() {
        return makeJeiDummy(ResourceLocation.fromNamespaceAndPath("occultism", "jei_dummy/none"));
    }

    public static void ritualRecipes(RecipeOutput recipeOutput) {
        craftingRituals(recipeOutput);
        familiarRituals(recipeOutput);
        possessRituals(recipeOutput);
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SOUL_SHARD_ITEM.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/resurrect_familiar")),
                        15,
                        RITUAL_RESURRECT_FAMILIAR,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .unlockedBy("has_otherworld_essence", has(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_familiar"));
        summonRituals(recipeOutput);

    }

    private static void summonRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_afrit_crusher")),
                        120,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(OccultismTags.Items.IESNIUM_DUST),
                        Ingredient.of(OccultismTags.Items.IESNIUM_DUST),
                        Ingredient.of(Tags.Items.GEMS_EMERALD))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier3"))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_crusher"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_afrit_rain_weather")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.SANDS),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Items.CACTUS),
                        Ingredient.of(Items.DEAD_BUSH))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(120)
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "rain_weather"))
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_rain_weather"));
        //summon_afrit_thunder_weather
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_afrit_thunder_weather")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(240)
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "thunder_weather"))
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_thunder_weather"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get(), "item.occultism.ritual_dummy.summon_demonic_husband"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_demonic_husband")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_EMERALD),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(ItemTags.SWORDS),
                        Ingredient.of(Items.GLASS_BOTTLE)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEMONIC_HUSBAND.get())
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_demonic_husband"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get(), "item.occultism.ritual_dummy.summon_demonic_wife"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_demonic_wife")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(ItemTags.SWORDS),
                        Ingredient.of(Items.GLASS_BOTTLE)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEMONIC_WIFE.get())
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_demonic_wife"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_clear_weather")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.CROPS_BEETROOT),
                        Ingredient.of(Tags.Items.CROPS_CARROT),
                        Ingredient.of(Tags.Items.CROPS_POTATO),
                        Ingredient.of(Tags.Items.CROPS_WHEAT))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(60)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "clear_weather"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_clear_weather"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_crusher")),
                        90,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(OccultismTags.Items.IRON_DUST),
                        Ingredient.of(OccultismTags.Items.GOLD_DUST),
                        Ingredient.of(OccultismTags.Items.COPPER_DUST),
                        Ingredient.of(OccultismTags.Items.SILVER_DUST))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier2"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_crusher"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_day_time")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Items.TORCH),
                        Ingredient.of(ItemTags.SAPLINGS),
                        Ingredient.of(Items.WHEAT),
                        Ingredient.of(Tags.Items.DYES_YELLOW))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(60)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "day_time"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_day_time"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_manage_machine")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COAL),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_IRON),
                        Ingredient.of(Blocks.FURNACE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "manage_machine"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_manage_machine"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_night_time")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(ItemTags.BEDS),
                        Ingredient.of(Items.ROTTEN_FLESH),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.DYES_BLACK))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(60)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "night_time"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_night_time"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_cleaner")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(OccultismItems.BRUSH.get()),
                        Ingredient.of(Tags.Items.CHESTS),
                        Ingredient.of(Blocks.DISPENSER),
                        Ingredient.of(Blocks.HOPPER))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "cleaner"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_cleaner"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_crusher")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(OccultismTags.Items.IRON_DUST),
                        Ingredient.of(OccultismTags.Items.GOLD_DUST),
                        Ingredient.of(OccultismTags.Items.COPPER_DUST),
                        Ingredient.of(OccultismTags.Items.SILVER_DUST))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier1"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_crusher"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_lumberjack")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(OccultismBlocks.OTHERWORLD_SAPLING.get()),
                        Ingredient.of(Items.OAK_SAPLING),
                        Ingredient.of(Items.BIRCH_SAPLING),
                        Ingredient.of(Items.SPRUCE_SAPLING),
                        Ingredient.of(ItemTags.AXES))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "lumberjack"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_lumberjack"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_otherstone_trader")),
                        30,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(Blocks.STONE),
                        Ingredient.of(Blocks.GRANITE),
                        Ingredient.of(Blocks.DIORITE),
                        Ingredient.of(Blocks.ANDESITE))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(3600)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "trade_otherstone_t1"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_otherstone_trader"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_sapling_trader")),
                        30,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(Items.OAK_SAPLING),
                        Ingredient.of(Items.BIRCH_SAPLING),
                        Ingredient.of(Items.SPRUCE_SAPLING),
                        Ingredient.of(Items.JUNGLE_SAPLING))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(3600)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "trade_otherworld_saplings_t1"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_sapling_trader"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_transport_items")),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(Items.MINECART),
                        Ingredient.of(Tags.Items.CHESTS),
                        Ingredient.of(Blocks.DISPENSER),
                        Ingredient.of(Blocks.HOPPER))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "transport_items"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_transport_items"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeJeiNoneDummy(),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_marid_crusher")),
                        150,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_DIAMOND),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_EMERALD))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier4"))
                .entityToSummon(OccultismEntities.MARID.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_marid_crusher"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.AFRIT_ESSENCE.get(), "item.occultism.ritual_dummy.summon_wild_afrit"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_wild_afrit")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        Ingredient.of(Items.GUNPOWDER))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.AFRIT_WILD.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_wild_afrit"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.WITHER_SKELETON_SKULL, "item.occultism.ritual_dummy.summon_wild_hunt"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_wild_hunt")),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Items.SOUL_SAND))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HUNT_WITHER_SKELETON.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_wild_hunt"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD.get(), "item.occultism.ritual_dummy.summon_wild_otherworld_bird"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_wild_otherworld_bird")),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Items.COBWEB),
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(Items.EGG))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .entityToSacrifice(OccultismTags.Entities.PARROTS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_wild_otherworld_bird"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.PARROT_SPAWN_EGG, "item.occultism.ritual_dummy.summon_wild_parrot"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_wild_parrot")),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.DYES_GREEN),
                        Ingredient.of(Tags.Items.DYES_YELLOW),
                        Ingredient.of(Tags.Items.DYES_RED),
                        Ingredient.of(Tags.Items.DYES_BLUE),
                        Ingredient.of(Items.EGG))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.PARROT)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_wild_parrot"));
    }

    private static void possessRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.HEART_OF_THE_SEA, "item.occultism.ritual_dummy.possess_elder_guardian"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_elder_guardian")),
                        90,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Items.OXIDIZED_COPPER),
                        Ingredient.of(Items.PRISMARINE),
                        Ingredient.of(Items.PRISMARINE_BRICKS),
                        Ingredient.of(Items.DARK_PRISMARINE),
                        Ingredient.of(Items.WET_SPONGE),
                        Ingredient.of(Items.SEA_LANTERN),
                        Ingredient.of(Items.WATER_BUCKET),
                        Ingredient.of(Items.WATER_BUCKET),
                        Ingredient.of(Items.WATER_BUCKET),
                        Ingredient.of(Tags.Items.GEMS_EMERALD))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.elder_guardian")
                .entityToSacrifice(OccultismTags.Entities.FISH)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_elder_guardian"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI),
                        makeLoreSpawnEgg(Items.ENDER_PEARL, "item.occultism.ritual_dummy.possess_enderman"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_enderman")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.END_STONES),
                        Ingredient.of(Items.ROTTEN_FLESH))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_enderman"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.END_STONE, "item.occultism.ritual_dummy.possess_endermite"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_endermite")),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(ItemTags.DIRT),
                        Ingredient.of(Tags.Items.STONES),
                        Ingredient.of(ItemTags.DIRT),
                        Ingredient.of(Tags.Items.STONES))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .itemToUse(Ingredient.of(Items.EGG))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_endermite"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(Items.GHAST_TEAR, "item.occultism.ritual_dummy.possess_ghast"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_ghast")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_ghast"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, "item.occultism.ritual_dummy.possess_hoglin"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_hoglin")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Items.NETHERITE_SCRAP),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_HOGLIN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_hoglin"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.PHANTOM_MEMBRANE, "item.occultism.ritual_dummy.possess_phantom"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_phantom")),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_PHANTOM_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.flying_passive")
                .entityToSacrifice(OccultismTags.Entities.FLYING_PASSIVE)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_phantom"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.SHULKER_SHELL, "item.occultism.ritual_dummy.possess_shulker"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_shulker")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Tags.Items.OBSIDIANS),
                        Ingredient.of(Tags.Items.END_STONES),
                        Ingredient.of(Items.PURPLE_GLAZED_TERRACOTTA))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_SHULKER_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cubemob")
                .entityToSacrifice(OccultismTags.Entities.CUBEMOB)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_shulker"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.SKELETON_SKULL, "item.occultism.ritual_dummy.possess_skeleton"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_skeleton")),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.BONES))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_SKELETON_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_skeleton"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(Items.ECHO_SHARD, "item.occultism.ritual_dummy.possess_warden"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_warden")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.SCULK_SHRIEKER),
                        Ingredient.of(Items.SCULK_SENSOR),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK_SHRIEKER),
                        Ingredient.of(Items.SCULK_SENSOR),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK_SHRIEKER),
                        Ingredient.of(Items.SCULK_SENSOR),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.axolotls")
                .entityToSacrifice(OccultismTags.Entities.AXOLOTL)
                .entityToSummon(OccultismEntities.POSSESSED_WARDEN_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_warden"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(Items.CHORUS_FRUIT, "item.occultism.ritual_dummy.possess_weak_shulker"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/possess_weak_shulker")),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Items.PURPLE_CONCRETE),
                        Ingredient.of(Tags.Items.END_STONES),
                        Ingredient.of(Items.MAGENTA_CONCRETE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSacrifice(OccultismTags.Entities.CUBEMOB)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cubemob")
                .entityToSummon(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_weak_shulker"));


    }

    private static void familiarRituals(RecipeOutput recipeOutput) {

        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BAT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_bat"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_bat")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.GOLDEN_CARROT),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.TORCH)
                )
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.BATS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.bats")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_bat"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beaver"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_beaver")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_beaver"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beholder"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_beholder")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE)
                )
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.SPIDERS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.spiders")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_beholder"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_blacksmith"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_blacksmith")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Items.IRON_SHOVEL),
                        Ingredient.of(Items.IRON_PICKAXE),
                        Ingredient.of(Items.IRON_AXE),
                        Ingredient.of(Items.ANVIL),
                        Ingredient.of(Tags.Items.STONES),
                        Ingredient.of(Tags.Items.STONES),
                        Ingredient.of(Tags.Items.STONES),
                        Ingredient.of(Tags.Items.STONES))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.ZOMBIES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.zombies")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_blacksmith"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_cthulhu"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_cthulhu")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES),
                        Ingredient.of(ItemTags.FISHES))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSacrifice(OccultismTags.Entities.SQUID)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSummon(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_cthulhu"));


        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_chimera"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_chimera")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Items.MUTTON),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(Items.BEEF),
                        Ingredient.of(Items.CHICKEN)
                )
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.SHEEP)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.sheep")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_chimera"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_deer"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_deer")),
                        15,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.STRINGS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_deer"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_devil"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_devil")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.BONES))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_devil"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_dragon"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_dragon")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COAL),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.OBSIDIAN),
                        Ingredient.of(Items.OBSIDIAN))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DRAGON_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_dragon"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_fairy"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_fairy")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.DRAGON_BREATH))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_fairy"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_greedy"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_greedy")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.CHESTS),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON),
                        Ingredient.of(Items.DISPENSER),
                        Ingredient.of(Items.HOPPER))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.GREEDY_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.ZOMBIES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.zombies")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_greedy"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_guardian"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_guardian")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_guardian"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_headless"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_headless")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.CROPS_WHEAT),
                        Ingredient.of(Tags.Items.CROPS_WHEAT),
                        Ingredient.of(Blocks.HAY_BLOCK),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Blocks.CARVED_PUMPKIN))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.SNOW_GOLEM)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.snow_golem")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_headless"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_mummy"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_mummy")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.SLIMEBALLS),
                        Ingredient.of(Tags.Items.SLIMEBALLS),
                        Ingredient.of(Items.PAPER),
                        Ingredient.of(Items.PAPER),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.MUMMY_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.LLAMAS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.llamas")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_mummy"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD.get(), "item.occultism.ritual_dummy.familiar_otherworld_bird"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_otherworld_bird")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Blocks.COBWEB),
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(Tags.Items.STRINGS))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.PARROTS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_otherworld_bird"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_parrot"),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/familiar_parrot")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.DYES_GREEN),
                        Ingredient.of(Tags.Items.DYES_YELLOW),
                        Ingredient.of(Tags.Items.DYES_RED),
                        Ingredient.of(Tags.Items.DYES_BLUE),
                        Ingredient.of(Tags.Items.STRINGS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.PARROT)
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_parrot"));


    }

    private static void craftingRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.DIMENSIONAL_MATRIX.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_dimensional_matrix")),
                        240,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Tags.Items.ENDER_PEARLS)
                )
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_matrix"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_dimensional_mineshaft")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_mineshaft"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.FAMILIAR_RING.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_familiar_ring")),
                        90,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_familiar_ring"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.INFUSED_LENSES.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_infused_lenses")),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.LENSES.get()),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(Tags.Items.INGOTS_GOLD)
                ).unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_infused_lenses"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.INFUSED_PICKAXE.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_dimensional_matrix")),
                        60,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get()),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT)
                )
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_infused_pickaxe"));

        minerRecipes(recipeOutput);

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.SATCHEL.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_satchel")),
                        240,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(Tags.Items.CHESTS_WOODEN),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_satchel"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.SOUL_GEM_ITEM.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_soul_gem")),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.INGOTS_COPPER),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Blocks.SOUL_SAND),
                        Ingredient.of(Blocks.SOUL_SAND),
                        Ingredient.of(Blocks.SOUL_SAND),
                        Ingredient.of(Blocks.SOUL_SAND)
                ).unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_soul_gem"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_stabilizer_tier1")),
                        120,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL.get()),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(OccultismTags.Items.BLAZE_DUST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier1"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_stabilizer_tier2")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier2"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_stabilizer_tier3")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Items.NETHER_STAR),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier3"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_stabilizer_tier4")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.DRAGON_HEAD),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier4"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STABLE_WORMHOLE.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_stable_wormhole")),
                        120,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.WORMHOLE_FRAME.get()),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stable_wormhole"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_storage_controller_base")),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_controller_base"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.STORAGE_REMOTE.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_storage_remote")),
                        120,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.STORAGE_REMOTE_INERT.get()),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_remote"));

    }

    private static void minerRecipes(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_miner_afrit_deeps")),
                        120,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismItems.MINER_DJINNI_ORES.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()),
                        Ingredient.of(Items.ECHO_SHARD),
                        Ingredient.of(Blocks.CRYING_OBSIDIAN))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_afrit_deeps"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.MINER_DJINNI_ORES.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_miner_djinni_ores")),
                        60,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_LAPIS),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_djinni_ores"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_miner_foliot_unspecialized")),
                        60,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(Tags.Items.RAW_MATERIALS_IRON),
                        Ingredient.of(Tags.Items.GRAVELS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_foliot_unspecialized"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismItems.MINER_MARID_MASTER.get()),
                        makeRitualDummy(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/craft_miner_marid_master")),
                        120,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(Items.NETHERITE_PICKAXE),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.TOTEM_OF_UNDYING),
                        Ingredient.of(Items.NETHER_STAR))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_marid_master"));
    }
}
