package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.RitualRecipeBuilder;
import com.klikli_dev.occultism.registry.*;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class RitualRecipes extends RecipeProvider {

    // Ritual Types
    private static final ResourceLocation RITUAL_SUMMON = OccultismRituals.SUMMON.getId();
    private static final ResourceLocation RITUAL_SUMMON_WILD = OccultismRituals.SUMMON_WILD.getId();
    private static final ResourceLocation RITUAL_SUMMON_JOB = OccultismRituals.SUMMON_SPIRIT_WITH_JOB.getId();
    private static final ResourceLocation RITUAL_FAMILIAR = OccultismRituals.SUMMON_TAMED.getId();
    private static final ResourceLocation RITUAL_CRAFT_WITH_SPIRIT_NAME = OccultismRituals.CRAFT_WITH_SPIRIT_NAME.getId();
    private static final ResourceLocation RITUAL_CRAFT = OccultismRituals.CRAFT.getId();
    private static final ResourceLocation RITUAL_CRAFT_MINER_SPIRIT = OccultismRituals.CRAFT_MINER_SPIRIT.getId();
    private static final ResourceLocation RITUAL_REPAIR = OccultismRituals.REPAIR.getId();
    // Pentacle IDs
    private static final ResourceLocation PENTACLE_SUMMON_FOLIOT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_foliot");
    private static final ResourceLocation PENTACLE_SUMMON_DJINNI = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_djinni");
    private static final ResourceLocation PENTACLE_SUMMON_UNBOUND_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_unbound_afrit");
    private static final ResourceLocation PENTACLE_SUMMON_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_afrit");
    private static final ResourceLocation PENTACLE_SUMMON_UNBOUND_MARID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_unbound_marid");
    private static final ResourceLocation PENTACLE_SUMMON_MARID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "summon_marid");
    private static final ResourceLocation PENTACLE_POSSESS_FOLIOT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_foliot");
    private static final ResourceLocation PENTACLE_POSSESS_DJINNI = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_djinni");
    private static final ResourceLocation PENTACLE_POSSESS_UNBOUND_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_unbound_afrit");
    private static final ResourceLocation PENTACLE_POSSESS_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_afrit");
    private static final ResourceLocation PENTACLE_POSSESS_MARID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possess_marid");
    private static final ResourceLocation PENTACLE_CRAFT_FOLIOT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_foliot");
    private static final ResourceLocation PENTACLE_CRAFT_DJINNI = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_djinni");
    private static final ResourceLocation PENTACLE_CRAFT_AFRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_afrit");
    private static final ResourceLocation PENTACLE_CRAFT_MARID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "craft_marid");
    private static final ResourceLocation PENTACLE_RESURRECT_SPIRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "resurrect_spirit");
    private static final ResourceLocation PENTACLE_CONTACT_WILD_SPIRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "contact_wild_spirit");
    private static final ResourceLocation PENTACLE_CONTACT_ELDRITCH_SPIRIT = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "contact_eldritch_spirit");


    public RitualRecipes(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    private static ItemStack makeLoreSpawnEgg(Item item, String key) {
        ItemStack output = new ItemStack(item);
        output.set(DataComponents.LORE, new ItemLore(List.of(Component.translatable(key + ".tooltip"))));
        output.set(DataComponents.ITEM_NAME, Component.translatable(key));
        return output;
    }

    private static ItemStack makeRitualDummy(ItemLike item) {
        return new ItemStack(item);
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

    public static void ritualRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        summonRituals(recipeOutput, registries);
        possessRituals(recipeOutput);
        familiarRituals(recipeOutput);
        craftingRituals(recipeOutput);
        stabilizerRecipes(recipeOutput);
        minerRecipes(recipeOutput);
        resurrectRituals(recipeOutput);
        repairRituals(recipeOutput);
        randomRituals(recipeOutput);
        contactRituals(recipeOutput);
    }

    private static void summonRituals(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        //Duration 60 * tier (half if time or weather job)
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER.get()),
                        180,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(OccultismTags.Items.IESNIUM_DUST),
                        Ingredient.of(OccultismTags.Items.EMERALD_DUST),
                        Ingredient.of(OccultismTags.Items.LAPIS_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.OBSIDIAN_DUST))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier3"))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_crusher"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.get()),
                        180,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.RODS_BLAZE),
                        Ingredient.of(Tags.Items.BUCKETS_LAVA),
                        Ingredient.of(Items.MAGMA_BLOCK),
                        Ingredient.of(Items.RED_NETHER_BRICKS),
                        Ingredient.of(Items.SOUL_CAMPFIRE))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelt_tier3"))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_smelter"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_rain_weather"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_RAIN_WEATHER.get()),
                        90,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.SANDS),
                        Ingredient.of(Items.DRIED_KELP),
                        Ingredient.of(Items.CACTUS),
                        Ingredient.of(Items.DEAD_BUSH))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(15)
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "rain_weather"))
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_rain_weather"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_thunder_weather"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_THUNDER_WEATHER.get()),
                        90,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT,
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(15)
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "thunder_weather"))
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_thunder_weather"));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND.get(), "item.occultism.ritual_dummy.summon_demonic_husband"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get()),
                        120,
                        RITUAL_FAMILIAR,
                        PENTACLE_SUMMON_DJINNI,
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_EMERALD),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(ItemTags.SWORDS),
                        Ingredient.of(Items.GLASS_BOTTLE))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEMONIC_HUSBAND.get())
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_demonic_husband"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEMONIC_WIFE.get(), "item.occultism.ritual_dummy.summon_demonic_wife"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get()),
                        120,
                        RITUAL_FAMILIAR,
                        PENTACLE_SUMMON_DJINNI,
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(ItemTags.SWORDS),
                        Ingredient.of(Items.GLASS_BOTTLE))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEMONIC_WIFE.get())
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_demonic_wife"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER.get()),
                        120,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI,
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
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.get()),
                        120,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI,
                        Ingredient.of(Items.FIRE_CHARGE),
                        Ingredient.of(Items.BLAST_FURNACE),
                        Ingredient.of(Items.SMOKER),
                        Ingredient.of(Tags.Items.TOOLS_IGNITER))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelt_tier2"))
                .entityToSummon(OccultismEntities.DJINNI_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_smelter"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_MANAGE_MACHINE.get()),
                        120,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI,
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
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_clear_weather"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CLEAR_WEATHER.get()),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI,
                        Ingredient.of(Tags.Items.CROPS_BEETROOT),
                        Ingredient.of(Tags.Items.CROPS_CARROT),
                        Ingredient.of(Tags.Items.CROPS_POTATO),
                        Ingredient.of(Tags.Items.CROPS_WHEAT))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(15)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "clear_weather"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_clear_weather"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_day_time"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_DAY_TIME.get()),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI,
                        Ingredient.of(Items.TORCH),
                        Ingredient.of(ItemTags.SAPLINGS),
                        Ingredient.of(Items.WHEAT),
                        Ingredient.of(Tags.Items.DYES_YELLOW))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(15)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "day_time"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_day_time"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_night_time"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_NIGHT_TIME.get()),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI,
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.ROTTEN_FLESH),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Tags.Items.DYES_BLACK))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(15)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "night_time"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_night_time"));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CLEANER.get()),
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
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER.get()),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(Tags.Items.INGOTS_IRON),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_COPPER),
                        Ingredient.of(OccultismTags.Items.INGOTS_SILVER))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
//                .condition(
//                        new OrCondition(
//                                List.of(
//                                        new IsInDimensionTypeCondition(registries.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(BuiltinDimensionTypes.NETHER)),
//                                        new IsInBiomeWithTagCondition(BiomeTags.HAS_NETHER_FORTRESS)
//                                )
//                        ))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier1"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_crusher"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.get()),
                        60,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT,
                        Ingredient.of(ItemTags.COALS),
                        Ingredient.of(Items.FURNACE),
                        Ingredient.of(Items.CAMPFIRE))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelt_tier1"))
                .entityToSummon(OccultismEntities.FOLIOT_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_smelter"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_LUMBERJACK.get()),
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
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_otherstone_trader"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER.get()),
                        60,
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
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_sapling_trader"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER.get()),
                        60,
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
                        new ItemStack(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_TRANSPORT_ITEMS.get()),
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

        //Marid
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MARID.get(), "item.occultism.ritual_dummy.summon_marid_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRUSHER.get()),
                        240,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_MARID,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_DIAMOND),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_EMERALD),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "crush_tier4"))
                .entityToSummon(OccultismEntities.MARID.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_marid_crusher"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MARID.get(), "item.occultism.ritual_dummy.summon_marid_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.get()),
                        240,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_MARID,
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.CRYING_OBSIDIAN),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COAL),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(OccultismBlocks.SPIRIT_CAMPFIRE.asItem()))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .spiritMaxAge(-1)
                .spiritJobType(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "smelt_tier4"))
                .entityToSummon(OccultismEntities.MARID_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_marid_smelter"));

        //Unbound, duration - 30
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.AFRIT_ESSENCE.get(), "item.occultism.ritual_dummy.summon_unbound_afrit"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_AFRIT.get()),
                        150,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_UNBOUND_AFRIT,
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(OccultismTags.Items.IESNIUM_INGOT),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        Ingredient.of(Tags.Items.GUNPOWDERS))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.AFRIT_WILD.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_unbound_afrit"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.MARID_ESSENCE.get(), "item.occultism.ritual_dummy.summon_unbound_marid"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_MARID.get()),
                        210,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_UNBOUND_MARID,
                        Ingredient.of(Items.CONDUIT),
                        Ingredient.of(Tags.Items.GEMS_PRISMARINE),
                        Ingredient.of(Items.PRISMARINE_SHARD),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSummon(OccultismEntities.MARID_UNBOUND.get())
                .itemToUse(Ingredient.of(Items.TRIDENT))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_unbound_marid"));
    }

    private static void possessRituals(RecipeOutput recipeOutput) {
        //Duration 30 * tier
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.HEART_OF_THE_SEA, "item.occultism.ritual_dummy.possess_elder_guardian"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ELDER_GUARDIAN.get()),
                        90,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Items.PRISMARINE_BRICKS),
                        Ingredient.of(Items.DARK_PRISMARINE),
                        Ingredient.of(Items.PRISMARINE_BRICKS),
                        Ingredient.of(Items.DARK_PRISMARINE),
                        Ingredient.of(Items.SEA_LANTERN),
                        Ingredient.of(Tags.Items.BUCKETS_WATER),
                        Ingredient.of(Tags.Items.GEMS_EMERALD))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.fish")
                .entityToSacrifice(OccultismTags.Entities.FISH)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_elder_guardian"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, "item.occultism.ritual_dummy.possess_hoglin"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_HOGLIN.get()),
                        90,
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.SHULKER_SHELL, "item.occultism.ritual_dummy.possess_shulker"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_SHULKER.get()),
                        90,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.PURPLE_GLAZED_TERRACOTTA),
                        Ingredient.of(Tags.Items.END_STONES),
                        Ingredient.of(Items.PURPLE_GLAZED_TERRACOTTA)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_SHULKER_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cubemob")
                .entityToSacrifice(OccultismTags.Entities.CUBEMOB)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_shulker"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(Items.ECHO_SHARD, "item.occultism.ritual_dummy.possess_warden"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_WARDEN.get()),
                        90,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT,
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSummon(OccultismEntities.POSSESSED_WARDEN_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_warden"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.DEMONIC_MEAT.get(), "item.occultism.ritual_dummy.possess_zombie_piglin"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ZOMBIE_PIGLIN.get()),
                        90,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_UNBOUND_AFRIT,
                        Ingredient.of(Items.GILDED_BLACKSTONE),
                        Ingredient.of(Items.WARPED_FUNGUS),
                        Ingredient.of(Items.CRIMSON_FUNGUS),
                        Ingredient.of(Items.QUARTZ))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_zombie_piglin"));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD.get(), "item.occultism.ritual_dummy.possess_unbound_otherworld_bird"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_OTHERWORLD_BIRD.get()),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(Items.EGG))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .entityToSacrifice(OccultismTags.Entities.PARROTS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_unbound_otherworld_bird"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI),
                        makeLoreSpawnEgg(Items.ENDER_PEARL, "item.occultism.ritual_dummy.possess_enderman"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMAN.get()),
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(Items.GHAST_TEAR, "item.occultism.ritual_dummy.possess_ghast"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_GHAST.get()),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_ghast"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(Items.CHORUS_FRUIT, "item.occultism.ritual_dummy.possess_weak_shulker"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_WEAK_SHULKER.get()),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Items.PURPLE_CONCRETE),
                        Ingredient.of(Tags.Items.END_STONES),
                        Ingredient.of(Items.PURPLE_CONCRETE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSacrifice(OccultismTags.Entities.CUBEMOB)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cubemob")
                .entityToSummon(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_weak_shulker"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.CURSED_HONEY.get(), "item.occultism.ritual_dummy.possess_bee"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_BEE.get()),
                        60,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.HONEYCOMB),
                        Ingredient.of(Items.HONEY_BLOCK),
                        Ingredient.of(Items.HONEY_BOTTLE),
                        Ingredient.of(Items.HONEYCOMB_BLOCK))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_bee"));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.PARROT_SPAWN_EGG, "item.occultism.ritual_dummy.possess_unbound_parrot"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_PARROT),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.DYES_GREEN),
                        Ingredient.of(Tags.Items.DYES_YELLOW),
                        Ingredient.of(Tags.Items.DYES_RED),
                        Ingredient.of(Tags.Items.DYES_BLUE))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.PARROT)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_unbound_parrot"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.END_STONE, "item.occultism.ritual_dummy.possess_endermite"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMITE.get()),
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.PHANTOM_MEMBRANE, "item.occultism.ritual_dummy.possess_phantom"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_PHANTOM.get()),
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.EXPERIENCE_BOTTLE, "item.occultism.ritual_dummy.possess_witch"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_WITCH.get()),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Items.GLASS_BOTTLE),
                        Ingredient.of(Tags.Items.DUSTS_REDSTONE),
                        Ingredient.of(Items.BROWN_MUSHROOM),
                        Ingredient.of(Items.RED_MUSHROOM))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_WITCH_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_witch"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.SKELETON_SKULL, "item.occultism.ritual_dummy.possess_skeleton"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_SKELETON.get()),
                        15, //half because need a lot in pentacles
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

        //Marid
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.CRUELTY_ESSENCE.get(), "item.occultism.ritual_dummy.possess_goat"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_GOAT.get()),
                        240,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_MARID,
                        Ingredient.of(Items.POINTED_DRIPSTONE),
                        Ingredient.of(Items.POINTED_DRIPSTONE),
                        Ingredient.of(Items.RABBIT_FOOT),
                        Ingredient.of(Items.RABBIT_FOOT),
                        Ingredient.of(Items.RABBIT_FOOT),
                        Ingredient.of(Items.RABBIT_FOOT),
                        Ingredient.of(Items.ARMADILLO_SCUTE),
                        Ingredient.of(Items.ARMADILLO_SCUTE),
                        Ingredient.of(Items.ARMADILLO_SCUTE),
                        Ingredient.of(Items.ARMADILLO_SCUTE),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSummon(OccultismEntities.GOAT_OF_MERCY_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_goat"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_IESNIUM_GOLEM.get(), "item.occultism.ritual_dummy.possess_iesnium_golem"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_IESNIUM_GOLEM.get()),
                        240,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_MARID,
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.MARID_ESSENCE),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()),
                        Ingredient.of(Tags.Items.NETHER_STARS),
                        Ingredient.of(OccultismItems.SOUL_GEM_ITEM))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSummon(OccultismEntities.IESNIUM_GOLEM_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.iron_golem")
                .entityToSacrifice(OccultismTags.Entities.IRON_GOLEM)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_iesnium_golem"));
    }

    private static void familiarRituals(RecipeOutput recipeOutput) {
        //Duration 45 * tier
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_guardian"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_GUARDIAN.get()),
                        135,
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

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BAT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_bat"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BAT.get()),
                        90,
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beholder"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEHOLDER.get()),
                        90,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.SPIDERS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.spiders")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_beholder"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_cthulhu"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_CTHULHU.get()),
                        90,
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_CHIMERA.get()),
                        90,
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
                        Ingredient.of(Items.CHICKEN))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.SHEEP)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.sheep")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_chimera"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_devil"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEVIL.get()),
                        90,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(OccultismTags.Items.MAGMA),
                        Ingredient.of(Tags.Items.BONES),
                        Ingredient.of(Items.LAVA_BUCKET))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_devil"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_dragon"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_DRAGON.get()),
                        90,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        Ingredient.of(ItemTags.COALS),
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_FAIRY.get()),
                        90,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Tags.Items.BUCKETS_MILK))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_fairy"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_headless"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_HEADLESS.get()),
                        90,
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_MUMMY.get()),
                        90,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.SLIME_BALLS),
                        Ingredient.of(Tags.Items.SLIME_BALLS),
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_OTHERWORLD_BIRD.get()),
                        90,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.EGGS),
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(Tags.Items.STRINGS))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.PARROTS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_otherworld_bird"));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beaver"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEAVER),
                        45,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .entityToSummon(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_beaver"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_blacksmith"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BLACKSMITH.get()),
                        45,
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_deer"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEER.get()),
                        45,
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_greedy"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_GREEDY.get()),
                        45,
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
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_parrot"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_PARROT.get()),
                        45,
                        OccultismRituals.SUMMON_WITH_CHANCE_OF_CHICKEN_TAMED.getId(),
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
        //Duration 30 * tier^2 + 30
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismItems.RITUAL_SATCHEL_T2.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T2.get()),
                        300,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(Items.HOPPER),
                        Ingredient.of(Items.DISPENSER),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.ENDER_PEARLS))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_ritual_satchel_t2"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_SACRIFICIAL_BOWL.get()),
                        300,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.asItem()),
                        Ingredient.of(OccultismItems.RESEARCH_FRAGMENT_DUST),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_iesnium_sacrificial_bowl"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismItems.WITHERITE_DUST.get(), 3),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_WITHERITE_DUST.get()),
                        150,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismTags.Items.NETHERITE_DUST),
                        Ingredient.of(Items.WITHER_SKELETON_SKULL),
                        Ingredient.of(OccultismTags.Items.BLACKSTONE_DUST),
                        Ingredient.of(Items.WITHER_ROSE)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_witherite_dust"));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.DIMENSIONAL_MATRIX.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MATRIX.get()),
                        150,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Tags.Items.ENDER_PEARLS))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_matrix"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MINESHAFT.get()),
                        150,
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_FAMILIAR_RING.get()),
                        150,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_familiar_ring"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.INFUSED_PICKAXE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_PICKAXE.get()),
                        150,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get()),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_infused_pickaxe"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.RITUAL_SATCHEL_T1.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T1.get()),
                        150,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(Items.HOPPER),
                        Ingredient.of(Items.DISPENSER),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_ritual_satchel_t1"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.SOUL_GEM_ITEM.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_SOUL_GEM.get()),
                        150,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.INGOTS_COPPER),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(OccultismItems.FRAGILE_SOUL_GEM_ITEM),
                        Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                ).unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_soul_gem"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.STORAGE_REMOTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_REMOTE.get()),
                        150,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.STORAGE_REMOTE_INERT.get()),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_remote"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.GRAY_PASTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_GRAY_PASTE.get()),
                        150,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.CLAY_BALL),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Tags.Items.DYES_GRAY))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_gray_paste"));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.INFUSED_LENSES.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_LENSES.get()),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.LENSES.get()),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(Tags.Items.INGOTS_GOLD)
                ).unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_infused_lenses"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.SATCHEL.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_SATCHEL.get()),
                        60,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(Tags.Items.CHESTS_WOODEN),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.LEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_satchel"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STABLE_WORMHOLE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE.get()),
                        60,
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE.get()),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL.get()),
                        Ingredient.of(Tags.Items.INGOTS_COPPER),
                        Ingredient.of(Tags.Items.INGOTS_COPPER),
                        Ingredient.of(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_controller_base"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.RESEARCH_FRAGMENT_DUST.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_RESEARCH_FRAGMENT_DUST.get()),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismTags.Items.EMERALD_DUST),
                        Ingredient.of(Items.EXPERIENCE_BOTTLE),
                        Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_research_fragment_dust"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.NATURE_PASTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_NATURE_PASTE.get()),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(ItemTags.SAPLINGS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(ItemTags.SAPLINGS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(ItemTags.LEAVES),
                        Ingredient.of(ItemTags.SAPLINGS),
                        Ingredient.of(Tags.Items.SEEDS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_nature_paste"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_FRAGILE_SOUL_GEM.get()),
                        30, //this item break after one use, half the time
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(Tags.Items.INGOTS_IRON),
                        Ingredient.of(Tags.Items.EGGS),
                        Ingredient.of(Tags.Items.GLASS_BLOCKS)
                ).unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_fragile_soul_gem"));

        //Marid
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismItems.DRAGONYST_DUST.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DRAGONYST_DUST.get()),
                        510,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.END_STONE_DUST),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.DRAGON_BREATH))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dragonyst_dust"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismBlocks.IESNIUM_ANVIL.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_ANVIL.get()),
                        510,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(Items.ANVIL),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_iesnium_anvil"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismItems.TRUE_SIGHT_STAFF.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_TRUE_SIGHT_STAFF.get()),
                        510,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(Tags.Items.INGOTS_NETHERITE),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE),
                        Ingredient.of(OccultismTags.Items.OTHERWORLD_GOGGLES),
                        Ingredient.of(OccultismItems.DIVINATION_ROD),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.MARID_ESSENCE))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_true_sight_staff"));
    }

    private static void stabilizerRecipes(RecipeOutput recipeOutput) {
        //Duration 30 * tier^2 + 30
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER1.get()),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER0.get()),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(OccultismTags.Items.BLAZE_DUST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier1"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER2.get()),
                        150,
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
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER3.get()),
                        300,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Items.TOTEM_OF_UNDYING),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier3"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER4.get()),
                        510,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.BEACON),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier4"));
    }

    private static void minerRecipes(RecipeOutput recipeOutput) {
        //Duration 30 * tier^2 + 30
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_FOLIOT_UNSPECIALIZED.get()),
                        60,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(Tags.Items.INGOTS_IRON),
                        Ingredient.of(Tags.Items.GRAVELS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_foliot_unspecialized"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.MINER_DJINNI_ORES.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_DJINNI_ORES.get()),
                        150,
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

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_AFRIT_DEEPS.get()),
                        300,
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

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismItems.MINER_MARID_MASTER.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_MARID_MASTER.get()),
                        510,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(Items.NETHERITE_PICKAXE),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.TOTEM_OF_UNDYING),
                        Ingredient.of(Items.NETHER_STAR),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_marid_master"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.MINING_DIMENSION_CORE_PIECE.get()),
                        new ItemStack(OccultismItems.MINER_ANCIENT_ELDRITCH.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_MINER_ANCIENT_ELDRITCH.get()),
                        780,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT,
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()))
                .unlockedBy("has_mining_dimension_core", has(OccultismItems.MINING_DIMENSION_CORE_PIECE.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_miner_ancient_eldritch"));
    }

    private static void resurrectRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SOUL_SHARD_ITEM.get()),
                        makeLoreSpawnEgg(OccultismItems.RESURRECT_ICON.get(), "item.occultism.ritual_dummy.resurrect_familiar"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_FAMILIAR.get()),
                        15,
                        OccultismRituals.RESURRECT_FAMILIAR.getId(),
                        PENTACLE_RESURRECT_SPIRIT,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .unlockedBy("has_otherworld_essence", has(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_familiar"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.SUGAR),
                        makeLoreSpawnEgg(Items.ALLAY_SPAWN_EGG, "item.occultism.ritual_dummy.resurrect_allay"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_ALLAY.get()),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_RESURRECT_SPIRIT,
                        Ingredient.of(Tags.Items.DUSTS_REDSTONE),
                        Ingredient.of(Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(OccultismTags.Items.SILVER_DUST),
                        Ingredient.of(OccultismTags.Items.GOLD_DUST))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.ALLAY)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.vex")
                .entityToSacrifice(OccultismTags.Entities.VEX)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_allay"));
    }

    private static void repairRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismTags.Items.TOOLS_CHALK),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_chalks"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_CHALKS.get()),
                        5,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_white_chalk", has(OccultismItems.CHALK_WHITE))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_chalks"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Tags.Items.TOOLS),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_tools"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_TOOLS.get()),
                        60,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_tools"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Tags.Items.ARMORS),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_armors"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_ARMORS.get()),
                        60,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_armors"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismTags.Items.Miners.MINERS),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_miners"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_MINERS.get()),
                        60,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_miners"));
    }

    private static void contactRituals(RecipeOutput recipeOutput) {
        //Wild duration 90
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.SKELETON_SKULL),
                        makeLoreSpawnEgg(Items.WITHER_SKELETON_SKULL, "item.occultism.ritual_dummy.wild_hunt"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_HUNT.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Tags.Items.NETHERRACKS),
                        Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HUNT_WITHER_SKELETON.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_hunt"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(ItemTags.PICKAXES),
                        makeLoreSpawnEgg(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, "item.occultism.ritual_dummy.wild_husk"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_HUSK.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.SANDSTONE),
                        Ingredient.of(Items.DEAD_BUSH),
                        Ingredient.of(Items.ROTTEN_FLESH),
                        Ingredient.of(Items.GOLD_INGOT))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_HUSK.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.camel")
                .entityToSacrifice(OccultismTags.Entities.CAMEL)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_husk"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.FISHING_ROD),
                        makeLoreSpawnEgg(Items.SNIFFER_EGG, "item.occultism.ritual_dummy.wild_drowned"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_DROWNED.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.PUFFERFISH),
                        Ingredient.of(Items.GRAVEL),
                        Ingredient.of(Items.DRIED_KELP_BLOCK),
                        Ingredient.of(Items.GRAVEL)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_DROWNED.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.fish")
                .entityToSacrifice(OccultismTags.Entities.FISH)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_drowned"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.FLINT_AND_STEEL),
                        makeLoreSpawnEgg(Items.MUSIC_DISC_CAT, "item.occultism.ritual_dummy.wild_creeper"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_CREEPER.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.MOSS_BLOCK),
                        Ingredient.of(Items.TNT),
                        Ingredient.of(Items.MOSS_BLOCK),
                        Ingredient.of(ItemTags.LEAVES)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_CREEPER.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_creeper"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.BRUSH),
                        makeLoreSpawnEgg(Items.MUSIC_DISC_RELIC, "item.occultism.ritual_dummy.wild_silverfish"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_SILVERFISH.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.SAND),
                        Ingredient.of(ItemTags.TERRACOTTA),
                        Ingredient.of(Items.GRAVEL),
                        Ingredient.of(ItemTags.TERRACOTTA)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_SILVERFISH.get())
                .summonNumber(5)
                .itemToUse(Ingredient.of(Items.EGG))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_silverfish"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.HONEYCOMB),
                        makeLoreSpawnEgg(Items.TRIAL_KEY, "item.occultism.ritual_dummy.wild_weak_breeze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_WEAK_BREEZE.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_WEAK_BREEZE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_weak_breeze"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.TRIAL_KEY),
                        makeLoreSpawnEgg(Items.OMINOUS_TRIAL_KEY, "item.occultism.ritual_dummy.wild_breeze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_BREEZE.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_BREEZE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_breeze"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.OMINOUS_TRIAL_KEY),
                        makeLoreSpawnEgg(Items.HEAVY_CORE, "item.occultism.ritual_dummy.wild_strong_breeze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_STRONG_BREEZE.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.COPPER_BULB),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.BREEZE_ROD),
                        Ingredient.of(Items.BREEZE_ROD),
                        Ingredient.of(Items.OMINOUS_BOTTLE))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_STRONG_BREEZE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_strong_breeze"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.GOLDEN_APPLE),
                        makeLoreSpawnEgg(Items.TOTEM_OF_UNDYING, "item.occultism.ritual_dummy.wild_horde_illager"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_ILLAGER.get()),
                        90,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.DARK_OAK_LOG),
                        Ingredient.of(Items.DARK_OAK_LOG),
                        Ingredient.of(Items.DARK_OAK_LOG),
                        Ingredient.of(OccultismTags.Items.EMERALD_DUST),
                        Ingredient.of(OccultismTags.Items.EMERALD_DUST),
                        Ingredient.of(OccultismTags.Items.EMERALD_DUST))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_EVOKER.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_horde_illager"));
        //Forge
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.DIAMOND_BLOCK),
                        new ItemStack(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_WILD_TRIM.get()),
                        180,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.MOSSY_COBBLESTONE),
                        Ingredient.of(Items.JUNGLE_SAPLING),
                        Ingredient.of(Items.BAMBOO),
                        Ingredient.of(Items.GLISTERING_MELON_SLICE),
                        Ingredient.of(Items.MOSSY_COBBLESTONE),
                        Ingredient.of(Items.JUNGLE_SAPLING),
                        Ingredient.of(Items.BAMBOO),
                        Ingredient.of(Items.GLISTERING_MELON_SLICE),
                        Ingredient.of(Items.MOSSY_COBBLESTONE),
                        Ingredient.of(Items.JUNGLE_SAPLING),
                        Ingredient.of(Items.BAMBOO),
                        Ingredient.of(Items.GLISTERING_MELON_SLICE))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrifice(OccultismTags.Entities.LLAMAS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.llamas")
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_wild_trim"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.AMETHYST_BLOCK),
                        new ItemStack(Items.BUDDING_AMETHYST),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BUDDING_AMETHYST.get()),
                        180,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST),
                        Ingredient.of(OccultismTags.Items.AMETHYST_DUST)
                )
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_budding_amethyst"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.DEEPSLATE),
                        new ItemStack(Items.REINFORCED_DEEPSLATE),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_REINFORCED_DEEPSLATE.get()),
                        180,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Tags.Items.OBSIDIANS),
                        Ingredient.of(Tags.Items.OBSIDIANS),
                        Ingredient.of(Tags.Items.OBSIDIANS),
                        Ingredient.of(Tags.Items.OBSIDIANS),
                        Ingredient.of(OccultismTags.Items.IESNIUM_INGOT))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.warden")
                .entityToSacrifice(OccultismTags.Entities.WARDEN)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_reinforced_deepslate"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.BEEHIVE),
                        new ItemStack(Items.BEE_NEST),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BEE_NEST.get()),
                        180,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.HONEYCOMB_BLOCK),
                        Ingredient.of(Items.HONEYCOMB_BLOCK),
                        Ingredient.of(Items.HONEYCOMB_BLOCK),
                        Ingredient.of(Items.HONEYCOMB_BLOCK))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.bees")
                .entityToSacrifice(OccultismTags.Entities.BEES)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_bee_nest"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        new ItemStack(Items.BELL),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BELL.get()),
                        180,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Tags.Items.NUGGETS_GOLD),
                        Ingredient.of(Items.CHAIN),
                        Ingredient.of(Tags.Items.STONES),
                        Ingredient.of(Tags.Items.STONES),
                        Ingredient.of(ItemTags.LOGS))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.goats")
                .entityToSacrifice(OccultismTags.Entities.GOATS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_bell"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem()),
                        new ItemStack(OccultismBlocks.ELDRITCH_CHALICE.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_ELDRITCH_CHALICE.get()),
                        780,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT,
                        Ingredient.of(Items.HEAVY_CORE),
                        Ingredient.of(Items.BELL),
                        Ingredient.of(Items.SOUL_LANTERN),
                        Ingredient.of(Items.CHORUS_FLOWER),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        Ingredient.of(Items.SPONGE),
                        Ingredient.of(Items.REINFORCED_DEEPSLATE),
                        Ingredient.of(Items.RESPAWN_ANCHOR),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.END_STONE_BRICKS),
                        Ingredient.of(Items.SCULK_CATALYST),
                        Ingredient.of(Items.BUDDING_AMETHYST))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.ravager")
                .entityToSacrifice(OccultismTags.Entities.RAVAGER)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_eldritch_chalice"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.DIMENSIONAL_MATRIX),
                        new ItemStack(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_STABILIZED_STORAGE.get()),
                        780,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                        Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE),
                        Ingredient.of(OccultismTags.Items.ECHO_DUST),
                        Ingredient.of(OccultismTags.Items.ECHO_DUST),
                        Ingredient.of(OccultismTags.Items.ECHO_DUST))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_stabilized_storage"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BRUSH),
                        new ItemStack(OccultismItems.CHALK_RAINBOW.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_RAINBOW.get()),
                        780,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT,
                        Ingredient.of(OccultismItems.CHALK_RED),
                        Ingredient.of(OccultismItems.CHALK_BROWN),
                        Ingredient.of(OccultismItems.CHALK_ORANGE),
                        Ingredient.of(OccultismItems.CHALK_YELLOW),
                        Ingredient.of(OccultismItems.CHALK_LIME),
                        Ingredient.of(OccultismItems.CHALK_GREEN),
                        Ingredient.of(OccultismItems.CHALK_CYAN),
                        Ingredient.of(OccultismItems.CHALK_BLUE),
                        Ingredient.of(OccultismItems.CHALK_LIGHT_BLUE),
                        Ingredient.of(OccultismItems.CHALK_PINK),
                        Ingredient.of(OccultismItems.CHALK_MAGENTA),
                        Ingredient.of(OccultismItems.CHALK_PURPLE))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.sheep")
                .entityToSacrifice(OccultismTags.Entities.SHEEP)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_chalk_rainbow"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.CHALK_RAINBOW),
                        new ItemStack(OccultismItems.CHALK_VOID.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_VOID.get()),
                        780,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT,
                        Ingredient.of(OccultismItems.CHALK_WHITE),
                        Ingredient.of(OccultismItems.CHALK_LIGHT_GRAY),
                        Ingredient.of(OccultismItems.CHALK_GRAY),
                        Ingredient.of(OccultismItems.CHALK_BLACK))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_chalk_void"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SOUL_GEM_ITEM),
                        new ItemStack(OccultismItems.TRINITY_GEM_ITEM.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_TRINITY_GEM.get()),
                        780,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT,
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE),
                        Ingredient.of(OccultismItems.MARID_ESSENCE),
                        Ingredient.of(OccultismItems.CRUELTY_ESSENCE),
                        Ingredient.of(OccultismTags.Items.ECHO_DUST),
                        Ingredient.of(OccultismTags.Items.DRAGONYST_DUST),
                        Ingredient.of(OccultismTags.Items.WITHERITE_DUST),
                        Ingredient.of(OccultismTags.Items.IESNIUM_DUST),
                        Ingredient.of(OccultismTags.Items.IESNIUM_DUST),
                        Ingredient.of(OccultismTags.Items.IESNIUM_DUST))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(OccultismTags.Entities.HUMANS)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_trinity_gem"));
    }

    private static void randomRituals(RecipeOutput recipeOutput) {
        //Individual
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_common"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_COMMON),
                        15,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.CROPS),
                        Ingredient.of(Tags.Items.CROPS),
                        Ingredient.of(Tags.Items.CROPS),
                        Ingredient.of(Tags.Items.CROPS))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_COMMON)
                .itemToUse(Ingredient.of(Items.EGG))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_common"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_water"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_WATER),
                        15,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.CLAY),
                        Ingredient.of(Items.CLAY))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_WATER)
                .itemToUse(Ingredient.of(Items.SNOWBALL))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_water"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_small"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SMALL),
                        15,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.NUGGETS_IRON),
                        Ingredient.of(Tags.Items.NUGGETS_IRON),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(Items.SUGAR))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_SMALL)
                .itemToUse(Ingredient.of(Items.EGG))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_small"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_rideable"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_RIDEABLE),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_WHEAT),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_WHEAT),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.CARROT),
                        Ingredient.of(Items.GOLDEN_CARROT),
                        Ingredient.of(Tags.Items.CROPS_CACTUS),
                        Ingredient.of(Items.WARPED_FUNGUS))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_RIDEABLE)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_rideable"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_villager"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_VILLAGER),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(ItemTags.BEDS),
                        Ingredient.of(Items.CAMPFIRE),
                        Ingredient.of(Tags.Items.FOODS_PIE))
                .entityTagToSummon(OccultismTags.Entities.VILLAGERS)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_villager"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_special"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SPECIAL),
                        30,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(OccultismTags.Items.MUSHROOM_BLOCKS),
                        Ingredient.of(Items.MOSS_BLOCK),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON),
                        Ingredient.of(Items.PACKED_ICE),
                        Ingredient.of(Items.TERRACOTTA),
                        Ingredient.of(Items.BAMBOO_BLOCK))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_SPECIAL)
                .itemToUse(Ingredient.of(Items.WIND_CHARGE))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_special"));
        //Group
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_common"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_COMMON),
                        45,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.SEEDS),
                        Ingredient.of(Tags.Items.CROPS),
                        Ingredient.of(Tags.Items.CROPS),
                        Ingredient.of(Tags.Items.CROPS),
                        Ingredient.of(Tags.Items.CROPS))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_COMMON)
                .itemToUse(Ingredient.of(Items.EGG))
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(7)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_common"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_water"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_WATER),
                        45,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.CLAY),
                        Ingredient.of(Items.CLAY))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_WATER)
                .itemToUse(Ingredient.of(Items.SNOWBALL))
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(7)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_water"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_small"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SMALL),
                        45,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.FEATHERS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.STRINGS),
                        Ingredient.of(Tags.Items.NUGGETS_IRON),
                        Ingredient.of(Tags.Items.NUGGETS_IRON),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(Items.SUGAR))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_SMALL)
                .itemToUse(Ingredient.of(Items.EGG))
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(7)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_small"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_rideable"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_RIDEABLE),
                        45,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_WHEAT),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_WHEAT),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.CARROT),
                        Ingredient.of(Items.GOLDEN_CARROT),
                        Ingredient.of(Tags.Items.CROPS_CACTUS),
                        Ingredient.of(Items.WARPED_FUNGUS))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_RIDEABLE)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(3)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_rideable"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_villager"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_VILLAGER),
                        45,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(ItemTags.BEDS),
                        Ingredient.of(Items.CAMPFIRE),
                        Ingredient.of(Tags.Items.FOODS_PIE))
                .entityTagToSummon(OccultismTags.Entities.VILLAGERS)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(3)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_villager"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_special"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SPECIAL),
                        45,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT,
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(OccultismTags.Items.MUSHROOM_BLOCKS),
                        Ingredient.of(Items.MOSS_BLOCK),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON),
                        Ingredient.of(Items.PACKED_ICE),
                        Ingredient.of(Items.TERRACOTTA),
                        Ingredient.of(Items.BAMBOO_BLOCK))
                .entityTagToSummon(OccultismTags.Entities.RANDOM_ANIMALS_SPECIAL)
                .itemToUse(Ingredient.of(Items.WIND_CHARGE))
                .unlockedBy("has_spirit_attuned_gem", has(OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(3)
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_special"));
    }
}
