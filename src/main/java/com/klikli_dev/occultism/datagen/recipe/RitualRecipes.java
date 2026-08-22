package com.klikli_dev.occultism.datagen.recipe;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.recipe.builders.RitualRecipeBuilder;
import com.klikli_dev.occultism.registry.*;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import com.klikli_dev.occultism.registry.OccultismTags.Items.Miners;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.criterion.ItemPredicate.Builder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public abstract class RitualRecipes extends RecipeProvider {

    private static final Identifier RITUAL_SUMMON = OccultismRituals.SUMMON.getId();
    private static final Identifier RITUAL_SUMMON_WILD = OccultismRituals.SUMMON_WILD.getId();
    private static final Identifier RITUAL_SUMMON_JOB = OccultismRituals.SUMMON_SPIRIT_WITH_JOB.getId();
    private static final Identifier RITUAL_FAMILIAR = OccultismRituals.SUMMON_TAMED.getId();
    private static final Identifier RITUAL_CRAFT_WITH_SPIRIT_NAME = OccultismRituals.CRAFT_WITH_SPIRIT_NAME.getId();
    private static final Identifier RITUAL_CRAFT = OccultismRituals.CRAFT.getId();
    private static final Identifier RITUAL_CRAFT_MINER_SPIRIT = OccultismRituals.CRAFT_MINER_SPIRIT.getId();
    private static final Identifier RITUAL_REPAIR = OccultismRituals.REPAIR.getId();
    private static final Identifier RITUAL_UPGRADE = OccultismRituals.UPGRADE.getId();
    private static final Identifier RITUAL_UNBREAKABLE = OccultismRituals.UNBREAKABLE.getId();
    private static final Identifier PENTACLE_SUMMON_FOLIOT = Identifier.fromNamespaceAndPath(Occultism.MODID, "summon_foliot");
    private static final Identifier PENTACLE_SUMMON_DJINNI = Identifier.fromNamespaceAndPath(Occultism.MODID, "summon_djinni");
    private static final Identifier PENTACLE_SUMMON_UNBOUND_AFRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "summon_unbound_afrit");
    private static final Identifier PENTACLE_SUMMON_AFRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "summon_afrit");
    private static final Identifier PENTACLE_SUMMON_UNBOUND_MARID = Identifier.fromNamespaceAndPath(Occultism.MODID, "summon_unbound_marid");
    private static final Identifier PENTACLE_SUMMON_MARID = Identifier.fromNamespaceAndPath(Occultism.MODID, "summon_marid");
    private static final Identifier PENTACLE_POSSESS_FOLIOT = Identifier.fromNamespaceAndPath(Occultism.MODID, "possess_foliot");
    private static final Identifier PENTACLE_POSSESS_DJINNI = Identifier.fromNamespaceAndPath(Occultism.MODID, "possess_djinni");
    private static final Identifier PENTACLE_POSSESS_UNBOUND_AFRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "possess_unbound_afrit");
    private static final Identifier PENTACLE_POSSESS_AFRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "possess_afrit");
    private static final Identifier PENTACLE_POSSESS_MARID = Identifier.fromNamespaceAndPath(Occultism.MODID, "possess_marid");
    private static final Identifier PENTACLE_CRAFT_FOLIOT = Identifier.fromNamespaceAndPath(Occultism.MODID, "craft_foliot");
    private static final Identifier PENTACLE_CRAFT_DJINNI = Identifier.fromNamespaceAndPath(Occultism.MODID, "craft_djinni");
    private static final Identifier PENTACLE_CRAFT_AFRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "craft_afrit");
    private static final Identifier PENTACLE_CRAFT_MARID = Identifier.fromNamespaceAndPath(Occultism.MODID, "craft_marid");
    private static final Identifier PENTACLE_RESURRECT_SPIRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "resurrect_spirit");
    private static final Identifier PENTACLE_CONTACT_WILD_SPIRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "contact_wild_spirit");
    private static final Identifier PENTACLE_CONTACT_ELDRITCH_SPIRIT = Identifier.fromNamespaceAndPath(Occultism.MODID, "contact_eldritch_spirit");

    //Time calculator

    private static final int BASE_TIME = 20; //Furnace time
    private static final int SUMMON_MULT = 2;
    private static final int POSSESS_MULT = 1;
    private static final float INVOKE_MULT = 1.75F;
    private static final float FAMILIAR_MULT = 1.5F;
    private static final int INFUSE_MULT = 3;
    private static final float REPAIR_MULT = 0.25F;
    private static final float FORGE_MULT = 3.75F;
    private static final float HALF_MULT = 0.5F;
    private static final int FOLIOT_TIER = 1;
    private static final int DJINNI_TIER = 2;
    private static final float UNBOUND_AFRIT_TIER = 2.5F;
    private static final int AFRIT_TIER = 3;
    private static final float UNBOUND_MARID_TIER = 3.5F;
    private static final int MARID_TIER = 4;
    private static final float LOW_TIER = 0.5F;
    private static final float WILD_TIER = 2.75F;
    private static final float GREAT_TIER = 5.5F;

    public RitualRecipes(Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    // Need a static create method that returns an instance for recipe generation
    public static RitualRecipes create(Provider registries, RecipeOutput output) {
        return new RitualRecipes(registries, output) {
            @Override
            protected void buildRecipes() {
                // Will be called - but recipes are generated via static method
            }
        };
    }

    // Helper method for has() with registries and TagKey
    protected static Criterion<TriggerInstance> has(Provider registries, TagKey<Item> tag) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        return TriggerInstance.hasItems(Builder.item().of(items, tag).build());
    }

    // Overloaded has() for ItemLike - uses registries parameter even though not strictly needed
    protected static Criterion<TriggerInstance> has(Provider registries, ItemLike item) {
        return TriggerInstance.hasItems(item);
    }

    // Helper method to create Ingredient from TagKey
    protected static Ingredient ofTag(Provider registries, TagKey<Item> tag) {
        return Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag));
    }

    // Helper method to create Ingredient from ItemLike
    protected static Ingredient ofItem(ItemLike item) {
        return Ingredient.of(item);
    }

    private static ItemStackTemplate makeLoreSpawnEgg(Item item, String key) {
        var patch = DataComponentPatch.builder()
                .set(DataComponents.LORE, new ItemLore(List.of(Component.translatable(key + ".tooltip"))))
                .set(DataComponents.ITEM_NAME, Component.translatable(key))
                .build();
        return new ItemStackTemplate(item.builtInRegistryHolder(), 1, patch);
    }

    private static ItemStackTemplate makeRitualDummy(ItemLike item) {
        return new ItemStackTemplate(item.asItem());
    }

    private static ItemStackTemplate makeRitualDummy(Identifier location) {
        return new ItemStackTemplate(BuiltInRegistries.ITEM.get(location).orElseThrow().value());
    }

    private static ItemStackTemplate makeJeiDummy(Identifier location) {
        return new ItemStackTemplate(BuiltInRegistries.ITEM.get(location).orElseThrow().value());
    }

    private static ItemStackTemplate makeJeiNoneDummy() {
        return makeJeiDummy(Identifier.fromNamespaceAndPath("occultism", "jei_dummy/none"));
    }

    public static void ritualRecipes(RecipeOutput recipeOutput, Provider registries) {
        summonRituals(recipeOutput, registries);
        possessRituals(recipeOutput, registries);
        familiarRituals(recipeOutput, registries);
        craftingRituals(recipeOutput, registries);
        stabilizerRecipes(recipeOutput, registries);
        minerRecipes(recipeOutput, registries);
        resurrectRituals(recipeOutput, registries);
        repairRituals(recipeOutput, registries);
        randomRituals(recipeOutput, registries);
        contactRituals(recipeOutput, registries);
        upgradeRituals(recipeOutput, registries);
    }

    private static void summonRituals(RecipeOutput recipeOutput, Provider registries) {
        //Half if time or weather job
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER.get()),
                        BASE_TIME * SUMMON_MULT * AFRIT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT, registries,
                        ofTag(registries, OccultismTags.Items.IESNIUM_DUST),
                        ofTag(registries, OccultismTags.Items.EMERALD_DUST),
                        ofTag(registries, OccultismTags.Items.LAPIS_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.OBSIDIAN_DUST))
                .unlockedBy("has_bound_afrit", TriggerInstance.hasItems(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crush_tier3"))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_crusher")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER.get()),
                        BASE_TIME * SUMMON_MULT * AFRIT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT, registries,
                        ofTag(registries, Tags.Items.RODS_BLAZE),
                        ofTag(registries, Tags.Items.BUCKETS_LAVA),
                        Ingredient.of(Items.MAGMA_BLOCK),
                        Ingredient.of(Items.RED_NETHER_BRICKS),
                        Ingredient.of(Items.SOUL_CAMPFIRE))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "smelt_tier3"))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_smelter")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_crystallizer"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRYSTALLIZER.get()),
                        BASE_TIME * SUMMON_MULT * AFRIT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT, registries,
                        Ingredient.of(OccultismItems.GRAY_PASTE),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_LAPIS),
                        Ingredient.of(Items.AMETHYST_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.DRIPSTONE_BLOCK))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crystal_tier3"))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_crystallizer")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_rain_weather"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_RAIN_WEATHER.get()),
                        BASE_TIME * SUMMON_MULT * AFRIT_TIER * HALF_MULT, //half due weather job
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT, registries,
                        ofTag(registries, Tags.Items.SANDS),
                        Ingredient.of(Items.DRIED_KELP),
                        Ingredient.of(Items.CACTUS),
                        Ingredient.of(Items.DEAD_BUSH))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "rain_weather"))
                .entityToSacrifice(Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_rain_weather")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_AFRIT.get(), "item.occultism.ritual_dummy.summon_afrit_thunder_weather"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_THUNDER_WEATHER.get()),
                        BASE_TIME * SUMMON_MULT * AFRIT_TIER * HALF_MULT, //half due weather job
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_AFRIT, registries,
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.AFRIT_TYPE.get())
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "thunder_weather"))
                .entityToSacrifice(Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_afrit_thunder_weather")));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND.get(), "item.occultism.ritual_dummy.summon_demonic_husband"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.GEMS_EMERALD),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.PORKCHOP),
                        ofTag(registries, ItemTags.SWORDS),
                        Ingredient.of(Items.GLASS_BOTTLE))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEMONIC_HUSBAND.get())
                .entityToSacrifice(Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_demonic_husband")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEMONIC_WIFE.get(), "item.occultism.ritual_dummy.summon_demonic_wife"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.PORKCHOP),
                        ofTag(registries, ItemTags.SWORDS),
                        Ingredient.of(Items.GLASS_BOTTLE))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEMONIC_WIFE.get())
                .entityToSacrifice(Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_demonic_wife")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, OccultismTags.Items.IRON_DUST),
                        ofTag(registries, OccultismTags.Items.GOLD_DUST),
                        ofTag(registries, OccultismTags.Items.COPPER_DUST),
                        ofTag(registries, OccultismTags.Items.SILVER_DUST))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crush_tier2"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_crusher")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        Ingredient.of(Items.FIRE_CHARGE),
                        Ingredient.of(Items.BLAST_FURNACE),
                        Ingredient.of(Items.SMOKER),
                        ofTag(registries, Tags.Items.TOOLS_IGNITER))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "smelt_tier2"))
                .entityToSummon(OccultismEntities.DJINNI_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_smelter")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_crystallizer"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRYSTALLIZER.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        Ingredient.of(OccultismItems.GRAY_PASTE),
                        ofTag(registries, Tags.Items.GEMS_LAPIS),
                        ofTag(registries, Tags.Items.GEMS_AMETHYST),
                        ofTag(registries, Tags.Items.GEMS_EMERALD))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crystal_tier2"))
                .entityToSummon(OccultismEntities.DJINNI_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_crystallizer")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_MANAGE_MACHINE.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COAL),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        Ingredient.of(Blocks.FURNACE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "manage_machine"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_manage_machine")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_gambler"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_GAMBLER.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, Tags.Items.GEMS_EMERALD),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_DIAMOND),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_SILVER))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(1800)
                .spiritJobType(OccultismSpiritJobs.TRADE_GAMBLER.getId())
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_gambler")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_clear_weather"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CLEAR_WEATHER.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER * HALF_MULT, //half due weather job
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, Tags.Items.CROPS_BEETROOT),
                        ofTag(registries, Tags.Items.CROPS_CARROT),
                        ofTag(registries, Tags.Items.CROPS_POTATO),
                        ofTag(registries, Tags.Items.CROPS_WHEAT))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritMaxAge(15)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "clear_weather"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_clear_weather")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_day_time"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_DAY_TIME.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER * HALF_MULT, //half due time job
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        Ingredient.of(Items.TORCH),
                        ofTag(registries, ItemTags.SAPLINGS),
                        Ingredient.of(Items.WHEAT),
                        ofTag(registries, Tags.Items.DYES_YELLOW))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "day_time"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_day_time")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DJINNI.get(), "item.occultism.ritual_dummy.summon_djinni_night_time"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_NIGHT_TIME.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER * HALF_MULT, //half due time job
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_DJINNI, registries,
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.ROTTEN_FLESH),
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, Tags.Items.DYES_BLACK))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "night_time"))
                .entityToSummon(OccultismEntities.DJINNI.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_djinni_night_time")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WONDERING_TRADER.get(), "item.occultism.ritual_dummy.summon_wondering_trader"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_WONDERING_TRADER.get()),
                        BASE_TIME * SUMMON_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_DJINNI, registries,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        ofTag(registries, Tags.Items.GEMS_EMERALD),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        ofTag(registries, Tags.Items.GEMS_EMERALD))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.WONDERING_TRADER.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_wondering_trader")));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CLEANER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(OccultismItems.BRUSH.get()),
                        ofTag(registries, Tags.Items.CHESTS),
                        Ingredient.of(Blocks.DISPENSER),
                        Ingredient.of(Blocks.HOPPER))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "cleaner"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_cleaner")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, OccultismTags.Items.INGOTS_SILVER))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
//                .condition(
//                        new OrCondition(
//                                List.of(
//                                        new IsInDimensionTypeCondition(registries.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(BuiltinDimensionTypes.NETHER)),
//                                        new IsInBiomeWithTagCondition(BiomeTags.HAS_NETHER_FORTRESS)
//                                )
//                        ))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crush_tier1"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_crusher")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        ofTag(registries, ItemTags.COALS),
                        Ingredient.of(Items.FURNACE),
                        Ingredient.of(Items.CAMPFIRE))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "smelt_tier1"))
                .entityToSummon(OccultismEntities.FOLIOT_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_smelter")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_crystallizer"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRYSTALLIZER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.LAPIS_DUST),
                        ofTag(registries, OccultismTags.Items.EMERALD_DUST))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crystal_tier1"))
                .entityToSummon(OccultismEntities.FOLIOT_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_crystallizer")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_LUMBERJACK.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(OccultismBlocks.OTHERWORLD_SAPLING.get()),
                        Ingredient.of(Items.OAK_SAPLING),
                        Ingredient.of(Items.BIRCH_SAPLING),
                        Ingredient.of(Items.SPRUCE_SAPLING),
                        ofTag(registries, ItemTags.AXES))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "lumberjack"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_lumberjack")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_FARMER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(OccultismItems.DATURA),
                        Ingredient.of(Items.WHEAT),
                        Ingredient.of(Items.CARROT),
                        Ingredient.of(Items.POTATO),
                        ofTag(registries, ItemTags.HOES))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "farmer"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_farmer")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_otherstone_trader"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(Blocks.STONE),
                        Ingredient.of(Blocks.GRANITE),
                        Ingredient.of(Blocks.DIORITE),
                        Ingredient.of(Blocks.ANDESITE))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(3600)
                .spiritJobType(OccultismSpiritJobs.TRADE_OTHERSTONE.getId())
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_otherstone_trader")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_otherrock_trader"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERROCK_TRADER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(Blocks.DEEPSLATE),
                        Ingredient.of(Blocks.DRIPSTONE_BLOCK),
                        Ingredient.of(Blocks.CALCITE),
                        Ingredient.of(Blocks.MAGMA_BLOCK))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(3600)
                .spiritJobType(OccultismSpiritJobs.TRADE_OTHERROCK.getId())
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_otherrock_trader")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FOLIOT.get(), "item.occultism.ritual_dummy.summon_foliot_sapling_trader"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(Items.OAK_SAPLING),
                        Ingredient.of(Items.BIRCH_SAPLING),
                        Ingredient.of(Items.SPRUCE_SAPLING),
                        Ingredient.of(Items.JUNGLE_SAPLING))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(3600)
                .spiritJobType(OccultismSpiritJobs.TRADE_OTHERWORLD_SAPLINGS.getId())
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_sapling_trader")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_TRANSPORT_ITEMS.get()),
                        BASE_TIME * SUMMON_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_FOLIOT, registries,
                        Ingredient.of(Items.MINECART),
                        ofTag(registries, Tags.Items.CHESTS),
                        Ingredient.of(Blocks.DISPENSER),
                        Ingredient.of(Blocks.HOPPER))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "transport_items"))
                .entityToSummon(OccultismEntities.FOLIOT.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_foliot_transport_items")));

        //Marid
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MARID.get(), "item.occultism.ritual_dummy.summon_marid_crusher"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRUSHER.get()),
                        BASE_TIME * SUMMON_MULT * MARID_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_MARID, registries,
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_DIAMOND),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_EMERALD),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crush_tier4"))
                .entityToSummon(OccultismEntities.MARID_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_marid_crusher")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MARID.get(), "item.occultism.ritual_dummy.summon_marid_smelter"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER.get()),
                        BASE_TIME * SUMMON_MULT * MARID_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_MARID, registries,
                        Ingredient.of(Items.DRAGON_BREATH),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.CRYING_OBSIDIAN),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COAL),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(OccultismBlocks.SPIRIT_CAMPFIRE.asItem()))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "smelt_tier4"))
                .entityToSummon(OccultismEntities.MARID_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_marid_smelter")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MARID.get(), "item.occultism.ritual_dummy.summon_marid_crystallizer"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRYSTALLIZER.get()),
                        BASE_TIME * SUMMON_MULT * MARID_TIER,
                        RITUAL_SUMMON_JOB,
                        PENTACLE_SUMMON_MARID, registries,
                        Ingredient.of(OccultismItems.GRAY_PASTE),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.BUDDING_AMETHYST),
                        Ingredient.of(Items.SEA_LANTERN),
                        Ingredient.of(Items.SCULK_CATALYST))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .spiritMaxAge(-1)
                .spiritJobType(Identifier.fromNamespaceAndPath(Occultism.MODID, "crystal_tier4"))
                .entityToSummon(OccultismEntities.MARID_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_marid_crystallizer")));

        //Unbound
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.AFRIT_ESSENCE.get(), "item.occultism.ritual_dummy.summon_unbound_afrit"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_AFRIT.get()),
                        BASE_TIME * SUMMON_MULT * UNBOUND_AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_UNBOUND_AFRIT, registries,
                        ofTag(registries, Tags.Items.NETHERRACKS),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        ofTag(registries, Tags.Items.GUNPOWDERS))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.AFRIT_UNBOUND.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(Entities.COWS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_unbound_afrit")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.MARID_ESSENCE.get(), "item.occultism.ritual_dummy.summon_unbound_marid"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_MARID.get()),
                        BASE_TIME * SUMMON_MULT * UNBOUND_MARID_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_SUMMON_UNBOUND_MARID, registries,
                        Ingredient.of(Items.CONDUIT),
                        ofTag(registries, Tags.Items.GEMS_PRISMARINE),
                        Ingredient.of(Items.PRISMARINE_SHARD),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSummon(OccultismEntities.MARID_UNBOUND.get())
                .itemToUse(Ingredient.of(Items.TRIDENT))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/summon_unbound_marid")));
    }

    private static void possessRituals(RecipeOutput recipeOutput, Provider registries) {
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_ELDER_GUARDIAN.get(), "item.occultism.ritual_dummy.possess_elder_guardian"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ELDER_GUARDIAN.get()),
                        BASE_TIME * POSSESS_MULT * AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT, registries,
                        Ingredient.of(Items.PRISMARINE_BRICKS),
                        Ingredient.of(Items.DARK_PRISMARINE),
                        Ingredient.of(Items.PRISMARINE_BRICKS),
                        Ingredient.of(Items.DARK_PRISMARINE),
                        Ingredient.of(Items.SEA_LANTERN),
                        ofTag(registries, Tags.Items.BUCKETS_WATER),
                        ofTag(registries, Tags.Items.GEMS_EMERALD))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.fish")
                .entityToSacrifice(Entities.FISH)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_elder_guardian")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_HOGLIN.get(), "item.occultism.ritual_dummy.possess_hoglin"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_HOGLIN.get()),
                        BASE_TIME * POSSESS_MULT * AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT, registries,
                        Ingredient.of(Items.NETHERITE_SCRAP),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.NETHERRACKS),
                        ofTag(registries, Tags.Items.NETHERRACKS),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_HOGLIN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(Entities.PIGS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_hoglin")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_SHULKER.get(), "item.occultism.ritual_dummy.possess_shulker"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_SHULKER.get()),
                        BASE_TIME * POSSESS_MULT * AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT, registries,
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.PURPLE_GLAZED_TERRACOTTA),
                        ofTag(registries, Tags.Items.END_STONES),
                        Ingredient.of(Items.PURPLE_GLAZED_TERRACOTTA)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_SHULKER_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cubemob")
                .entityToSacrifice(Entities.CUBEMOB)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_shulker")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_WARDEN.get(), "item.occultism.ritual_dummy.possess_warden"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_WARDEN.get()),
                        BASE_TIME * POSSESS_MULT * AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_AFRIT, registries,
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK),
                        Ingredient.of(Items.SCULK))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(Entities.COWS)
                .entityToSummon(OccultismEntities.POSSESSED_WARDEN_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_warden")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_ZOMBIFIED_PIGLIN.get(), "item.occultism.ritual_dummy.possess_zombified_piglin"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ZOMBIFIED_PIGLIN.get()),
                        BASE_TIME * POSSESS_MULT * UNBOUND_AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_UNBOUND_AFRIT, registries,
                        Ingredient.of(Items.GILDED_BLACKSTONE),
                        Ingredient.of(Items.WARPED_FUNGUS),
                        Ingredient.of(Items.CRIMSON_FUNGUS),
                        Ingredient.of(Items.QUARTZ))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ZOMBIFIED_PIGLIN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(Entities.PIGS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_zombified_piglin")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_GUARDIAN.get(), "item.occultism.ritual_dummy.possess_guardian"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_GUARDIAN.get()),
                        BASE_TIME * POSSESS_MULT * UNBOUND_AFRIT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_UNBOUND_AFRIT, registries,
                        Ingredient.of(Items.INK_SAC, Items.GLOW_INK_SAC),
                        Ingredient.of(Items.SEAGRASS),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_LAPIS),
                        Ingredient.of(Items.SEA_LANTERN),
                        ofTag(registries, Tags.Items.BUCKETS_WATER))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.fish")
                .entityToSacrifice(Entities.FISH)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_guardian")));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD.get(), "item.occultism.ritual_dummy.possess_unbound_otherworld_bird"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_OTHERWORLD_BIRD.get()),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, ItemTags.LEAVES),
                        ofTag(registries, Tags.Items.EGGS))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .entityToSacrifice(Entities.PARROTS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_unbound_otherworld_bird")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN.get(), "item.occultism.ritual_dummy.possess_enderman"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMAN.get()),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.END_STONES),
                        Ingredient.of(Items.ROTTEN_FLESH))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(Entities.PIGS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_enderman")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_GHAST.get(), "item.occultism.ritual_dummy.possess_ghast"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_GHAST.get()),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        ofTag(registries, OccultismTags.Items.MAGMA),
                        ofTag(registries, ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        ofTag(registries, OccultismTags.Items.MAGMA),
                        ofTag(registries, Tags.Items.NETHERRACKS),
                        Ingredient.of(Items.LAVA_BUCKET),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .entityToSacrifice(Entities.COWS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_ghast")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_SHULKER.get(), "item.occultism.ritual_dummy.possess_weak_shulker"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_WEAK_SHULKER.get()),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Items.PURPLE_CONCRETE),
                        ofTag(registries, Tags.Items.END_STONES),
                        Ingredient.of(Items.PURPLE_CONCRETE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSacrifice(Entities.CUBEMOB)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cubemob")
                .entityToSummon(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_weak_shulker")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.CURSED_HONEY.get(), "item.occultism.ritual_dummy.possess_bee"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_BEE.get()),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        Ingredient.of(Items.HONEYCOMB),
                        Ingredient.of(Items.HONEY_BLOCK),
                        Ingredient.of(Items.HONEY_BOTTLE),
                        Ingredient.of(Items.HONEYCOMB_BLOCK))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.bees")
                .entityToSacrifice(Entities.BEES)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_bee")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_BLAZE.get(), "item.occultism.ritual_dummy.possess_blaze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_BLAZE.get()),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        Ingredient.of(Items.FIRE_CHARGE),
                        Ingredient.of(Items.FIREWORK_STAR),
                        Ingredient.of(Items.TORCH),
                        ofTag(registries, ItemTags.CANDLES),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_BLAZE_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(Entities.CHICKEN)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_blaze")));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.PARROT_SPAWN_EGG, "item.occultism.ritual_dummy.possess_unbound_parrot"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_PARROT),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.DYES_GREEN),
                        ofTag(registries, Tags.Items.DYES_YELLOW),
                        ofTag(registries, Tags.Items.DYES_RED),
                        ofTag(registries, Tags.Items.DYES_BLUE))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.PARROT)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(Entities.CHICKEN)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_unbound_parrot")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE.get(), "item.occultism.ritual_dummy.possess_endermite"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMITE.get()),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, ItemTags.DIRT),
                        ofTag(registries, Tags.Items.STONES),
                        ofTag(registries, ItemTags.DIRT),
                        ofTag(registries, Tags.Items.STONES))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .itemToUse(ofTag(registries, Tags.Items.EGGS))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_endermite")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_PHANTOM.get(), "item.occultism.ritual_dummy.possess_phantom"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_PHANTOM.get()),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.FEATHERS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_PHANTOM_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(Entities.CHICKEN)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_phantom")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_WITCH.get(), "item.occultism.ritual_dummy.possess_witch"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_WITCH.get()),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        Ingredient.of(Items.GLASS_BOTTLE),
                        ofTag(registries, Tags.Items.DUSTS_REDSTONE),
                        Ingredient.of(Items.BROWN_MUSHROOM),
                        Ingredient.of(Items.RED_MUSHROOM))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_WITCH_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(Entities.CHICKEN)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_witch")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON.get(), "item.occultism.ritual_dummy.possess_skeleton"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_SKELETON.get()),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER * HALF_MULT, //half because need a lot in pentacles
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, Tags.Items.BONES))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_SKELETON_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .entityToSacrifice(Entities.CHICKEN)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_skeleton")));

        //Marid
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.CRUELTY_ESSENCE.get(), "item.occultism.ritual_dummy.possess_goat"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_GOAT.get()),
                        BASE_TIME * POSSESS_MULT * MARID_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_MARID, registries,
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
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSummon(OccultismEntities.GOAT_OF_MERCY_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .entityToSacrifice(Entities.HORSES)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_goat")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_IESNIUM_GOLEM.get(), "item.occultism.ritual_dummy.possess_iesnium_golem"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_IESNIUM_GOLEM.get()),
                        BASE_TIME * POSSESS_MULT * MARID_TIER,
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_MARID, registries,
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.MARID_ESSENCE),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()),
                        ofTag(registries, Tags.Items.NETHER_STARS),
                        Ingredient.of(OccultismItems.SOUL_GEM_ITEM))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSummon(OccultismEntities.IESNIUM_GOLEM_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.iron_golem")
                .entityToSacrifice(Entities.IRON_GOLEM)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_iesnium_golem")));
    }

    private static void familiarRituals(RecipeOutput recipeOutput, Provider registries) {
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_guardian"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_GUARDIAN.get()),
                        BASE_TIME * FAMILIAR_MULT * AFRIT_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_AFRIT, registries,
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.HUMANS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_guardian")));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BAT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_bat"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BAT.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        Ingredient.of(Items.GOLDEN_CARROT),
                        Ingredient.of(Items.SPIDER_EYE),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE),
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.TORCH)
                )
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.BATS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.bats")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_bat")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beholder"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEHOLDER.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        Ingredient.of(Items.SPIDER_EYE),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.SPIDERS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.spiders")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_beholder")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_cthulhu"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_CTHULHU.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES),
                        ofTag(registries, ItemTags.FISHES))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSacrifice(Entities.SQUID)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSummon(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_cthulhu")));
        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_chimera"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_CHIMERA.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, Tags.Items.BONES),
                        Ingredient.of(Items.MUTTON),
                        Ingredient.of(Items.PORKCHOP),
                        Ingredient.of(Items.BEEF),
                        Ingredient.of(Items.CHICKEN))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.SHEEP)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.sheep")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_chimera")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_devil"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEVIL.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, OccultismTags.Items.MAGMA),
                        ofTag(registries, Tags.Items.BONES),
                        ofTag(registries, OccultismTags.Items.MAGMA),
                        ofTag(registries, Tags.Items.BONES),
                        Ingredient.of(Items.LAVA_BUCKET))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_devil")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_dragon"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_DRAGON.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        ofTag(registries, ItemTags.COALS),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.OBSIDIAN),
                        Ingredient.of(Items.OBSIDIAN))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DRAGON_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_dragon")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_fairy"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_FAIRY.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GHAST_TEAR),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        ofTag(registries, Tags.Items.BUCKETS_MILK))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_fairy")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_headless"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_HEADLESS.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.CROPS_WHEAT),
                        ofTag(registries, Tags.Items.CROPS_WHEAT),
                        Ingredient.of(Blocks.HAY_BLOCK),
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        Ingredient.of(Blocks.CARVED_PUMPKIN))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.SNOW_GOLEM)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.snow_golem")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_headless")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_mummy"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_MUMMY.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.SLIME_BALLS),
                        ofTag(registries, Tags.Items.SLIME_BALLS),
                        Ingredient.of(Items.PAPER),
                        Ingredient.of(Items.PAPER),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.MUMMY_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.LLAMAS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.llamas")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_mummy")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD.get(), "item.occultism.ritual_dummy.familiar_otherworld_bird"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_OTHERWORLD_BIRD.get()),
                        BASE_TIME * FAMILIAR_MULT * DJINNI_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.EGGS),
                        ofTag(registries, ItemTags.LEAVES),
                        ofTag(registries, Tags.Items.STRINGS))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .entityToSacrifice(Entities.PARROTS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_otherworld_bird")));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beaver"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEAVER),
                        BASE_TIME * FAMILIAR_MULT * FOLIOT_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, ItemTags.LOGS),
                        ofTag(registries, ItemTags.LOGS),
                        ofTag(registries, ItemTags.LOGS),
                        ofTag(registries, ItemTags.LOGS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(Entities.PIGS)
                .entityToSummon(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_beaver")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_blacksmith"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_BLACKSMITH.get()),
                        BASE_TIME * FAMILIAR_MULT * FOLIOT_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        Ingredient.of(Items.IRON_SHOVEL),
                        Ingredient.of(Items.IRON_PICKAXE),
                        Ingredient.of(Items.IRON_AXE),
                        Ingredient.of(Items.ANVIL),
                        ofTag(registries, Tags.Items.STONES),
                        ofTag(registries, Tags.Items.STONES),
                        ofTag(registries, Tags.Items.STONES),
                        ofTag(registries, Tags.Items.STONES))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .entityToSacrifice(EntityTypeTags.ZOMBIES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.zombies")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_blacksmith")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_deer"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEER.get()),
                        BASE_TIME * FAMILIAR_MULT * FOLIOT_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.STRINGS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .entityToSacrifice(Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_deer")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_greedy"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_GREEDY.get()),
                        BASE_TIME * FAMILIAR_MULT * FOLIOT_TIER,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.CHESTS),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_IRON),
                        Ingredient.of(Items.DISPENSER),
                        Ingredient.of(Items.HOPPER))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.GREEDY_FAMILIAR_TYPE.get())
                .entityToSacrifice(EntityTypeTags.ZOMBIES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.zombies")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_greedy")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(Items.PARROT_SPAWN_EGG, "item.occultism.ritual_dummy.familiar_parrot"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FAMILIAR_PARROT.get()),
                        BASE_TIME * FAMILIAR_MULT * FOLIOT_TIER,
                        OccultismRituals.SUMMON_WITH_CHANCE_OF_CHICKEN_TAMED.getId(),
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.DYES_GREEN),
                        ofTag(registries, Tags.Items.DYES_YELLOW),
                        ofTag(registries, Tags.Items.DYES_RED),
                        ofTag(registries, Tags.Items.DYES_BLUE),
                        ofTag(registries, Tags.Items.STRINGS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.PARROT)
                .entityToSacrifice(Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/familiar_parrot")));
    }

    private static void craftingRituals(RecipeOutput recipeOutput, Provider registries) {
        //Afrit
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismItems.RITUAL_SATCHEL_T2.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T2.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(Items.HOPPER),
                        Ingredient.of(Items.DISPENSER),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_ritual_satchel_t2")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_SACRIFICIAL_BOWL.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.asItem()),
                        Ingredient.of(OccultismItems.RESEARCH_FRAGMENT_DUST),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_iesnium_sacrificial_bowl")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DARK_IESNIUM_SACRIFICIAL_BOWL.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.asItem()),
                        Ingredient.of(OccultismItems.RESEARCH_FRAGMENT_DUST),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dark_iesnium_sacrificial_bowl")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismItems.WITHERITE_DUST.get(), 3),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_WITHERITE_DUST.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        ofTag(registries, OccultismTags.Items.NETHERITE_DUST),
                        Ingredient.of(Items.WITHER_SKELETON_SKULL),
                        ofTag(registries, OccultismTags.Items.BLACKSTONE_DUST),
                        Ingredient.of(Items.WITHER_ROSE)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_witherite_dust")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismItems.IESNIUM_BUTCHER_KNIFE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_BUTCHER_KNIFE.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.BUTCHER_KNIFE),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT),
                        ofTag(registries, OccultismTags.Items.NETHERITE_DUST))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_iesnium_butcher_knife")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_BATTLEFIELD.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        ofTag(registries, Tags.Items.INGOTS_NETHERITE),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_battlefield")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismItems.FLAMING_PASTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_FLAMING_PASTE.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE),
                        ofTag(registries, Tags.Items.BUCKETS_LAVA),
                        ofTag(registries, Tags.Items.RODS_BLAZE),
                        ofTag(registries, Tags.Items.TOOLS_IGNITER),
                        Ingredient.of(Items.MAGMA_CREAM),
                        Ingredient.of(Items.FIRE_CHARGE),
                        Ingredient.of(OccultismItems.DEMONIC_MEAT),
                        ofTag(registries, Tags.Items.CLUMPS_RESIN))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_flaming_paste")));

        //Djinni
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.DIMENSIONAL_MATRIX.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MATRIX.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        Ingredient.of(Items.QUARTZ_BLOCK),
                        ofTag(registries, Tags.Items.ENDER_PEARLS))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_matrix")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.DIMENSIONAL_MINESHAFT.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MINESHAFT.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        Ingredient.of(OccultismBlocks.OTHERSTONE.get(), OccultismBlocks.OTHERROCK.get()),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_mineshaft")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.FAMILIAR_RING.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_FAMILIAR_RING.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_familiar_ring")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.INFUSED_PICKAXE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_PICKAXE.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI, registries,
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        ofTag(registries, Tags.Items.RODS_WOODEN),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get()),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_infused_pickaxe")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.SOUL_GEM_ITEM.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_SOUL_GEM.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_UPGRADE,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.FRAGILE_SOUL_GEM_ITEM),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        ofTag(registries, ItemTags.SOUL_FIRE_BASE_BLOCKS),
                        ofTag(registries, ItemTags.SOUL_FIRE_BASE_BLOCKS)
                ).unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_soul_gem")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.STORAGE_REMOTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_REMOTE.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.STORAGE_REMOTE_INERT.get()),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.GEMS_QUARTZ))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_remote")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.GRAY_PASTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_GRAY_PASTE.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        ofTag(registries, Tags.Items.GUNPOWDERS),
                        Ingredient.of(Items.CLAY_BALL),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        ofTag(registries, Tags.Items.DYES_GRAY))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_gray_paste")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.ENTITY_WORMHOLE.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_ENTITY_WORMHOLE.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.OTHERSTONE_FRAME.get()),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.OBSIDIANS_CRYING),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_entity_wormhole")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.ENTITY_WORMHOLE_DARK.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_ENTITY_WORMHOLE_DARK.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.OTHERROCK_FRAME.get()),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.OBSIDIANS_CRYING),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_entity_wormhole_dark")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.WORMHOLE_TABLET.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_WORMHOLE_TABLET.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.OTHERWORLDLY_TABLET.get()),
                        Ingredient.of(OccultismBlocks.ENTITY_WORMHOLE.get(), OccultismBlocks.ENTITY_WORMHOLE_DARK.get()),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()),
                        ofTag(registries, OccultismTags.Items.OBSIDIAN_DUST),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(Items.ENDER_EYE),
                        ofTag(registries, Tags.Items.DUSTS_REDSTONE),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_wormhole_tablet")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.SPIRIT_GRINDSTONE.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_SPIRIT_GRINDSTONE.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(Items.GRINDSTONE),
                        ofTag(registries, Tags.Items.GLASS_BLOCKS),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL),
                        ofTag(registries, Tags.Items.GEMS_PRISMARINE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_spirit_grindstone")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.ENDER_SATCHEL.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_ENDER_SATCHEL.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI, registries,
                        ofTag(registries, Tags.Items.CHESTS_ENDER),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_ender_satchel")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.DIMENSIONAL_EXTRACTOR.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_EXTRACTOR.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL, OccultismBlocks.OTHERROCK_PEDESTAL),
                        Ingredient.of(Items.HOPPER),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dimensional_extractor")));

        //Foliot
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.RITUAL_SATCHEL_T1.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T1.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(Items.HOPPER),
                        Ingredient.of(Items.DISPENSER),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_ritual_satchel_t1")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.KNOWLEDGE_TABLET.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_KNOWLEDGE_TABLET.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismItems.OTHERWORLDLY_TABLET),
                        ofTag(registries, OccultismTags.Items.RESEARCH_DUST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        ofTag(registries, Tags.Items.ENDER_PEARLS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_knowledge_tablet")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.INFUSED_LENSES.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_LENSES.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismItems.LENSES.get()),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, Tags.Items.INGOTS_GOLD)
                ).unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_infused_lenses")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.SATCHEL.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_SATCHEL.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        ofTag(registries, Tags.Items.CHESTS_WOODEN),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.LEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_satchel")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismBlocks.STABLE_WORMHOLE.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismItems.OTHERSTONE_FRAME.get()),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.GEMS_QUARTZ),
                        ofTag(registries, Tags.Items.GEMS_QUARTZ),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stable_wormhole")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismBlocks.STABLE_WORMHOLE_DARK.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE_DARK.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismItems.OTHERROCK_FRAME.get()),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.GEMS_QUARTZ),
                        ofTag(registries, Tags.Items.GEMS_QUARTZ),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stable_wormhole_dark")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_CONTROLLER_BASE.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL.get()),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_controller_base")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE_DARK.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismBlocks.OTHERROCK_PEDESTAL.get()),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_storage_controller_base_dark")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.RESEARCH_FRAGMENT_DUST.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_RESEARCH_FRAGMENT_DUST.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        ofTag(registries, Tags.Items.DUSTS),
                        Ingredient.of(Items.EXPERIENCE_BOTTLE),
                        Ingredient.of(Items.ENCHANTED_BOOK),
                        Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_research_fragment_dust")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.NATURE_PASTE.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_NATURE_PASTE.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        ofTag(registries, ItemTags.LEAVES),
                        ofTag(registries, ItemTags.SAPLINGS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, ItemTags.LEAVES),
                        ofTag(registries, ItemTags.SAPLINGS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, ItemTags.LEAVES),
                        ofTag(registries, ItemTags.SAPLINGS),
                        ofTag(registries, Tags.Items.SEEDS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_nature_paste")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.VITALITY_COMPASS.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_VITALITY_COMPASS.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        ofTag(registries, Tags.Items.GEMS_AMETHYST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        Ingredient.of(Items.COMPASS),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_vitality_compass")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_FRAGILE_SOUL_GEM.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER * HALF_MULT, //this item break after one use, half the time
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        ofTag(registries, Tags.Items.EGGS),
                        ofTag(registries, Tags.Items.GLASS_BLOCKS)
                ).unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_fragile_soul_gem")));

        //Marid
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStackTemplate(OccultismItems.DRAGONYST_DUST.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_DRAGONYST_DUST.get()),
                        BASE_TIME * INFUSE_MULT * MARID_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID, registries,
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.END_STONE_DUST),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.END_CRYSTAL),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.DRAGON_BREATH))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_dragonyst_dust")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStackTemplate(OccultismBlocks.IESNIUM_ANVIL.get().asItem().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_ANVIL.get()),
                        BASE_TIME * INFUSE_MULT * MARID_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID, registries,
                        Ingredient.of(Items.ANVIL),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_iesnium_anvil")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStackTemplate(OccultismItems.TRUE_SIGHT_STAFF.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_TRUE_SIGHT_STAFF.get()),
                        BASE_TIME * INFUSE_MULT * MARID_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID, registries,
                        ofTag(registries, Tags.Items.INGOTS_NETHERITE),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE),
                        ofTag(registries, OccultismTags.Items.OTHERWORLD_GOGGLES),
                        Ingredient.of(OccultismItems.DIVINATION_ROD),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.MARID_ESSENCE))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_true_sight_staff")));
    }

    private static void stabilizerRecipes(RecipeOutput recipeOutput, Provider registries) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER1.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER1.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER0.get()),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COPPER),
                        ofTag(registries, OccultismTags.Items.BLAZE_DUST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier1")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER1_DARK.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get()),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COPPER),
                        ofTag(registries, OccultismTags.Items.BLAZE_DUST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier1_dark")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER2.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER2.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier2")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER2_DARK.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get()),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier2_dark")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER3.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER3.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Items.TOTEM_OF_UNDYING),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier3")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER3_DARK.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get()),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Items.TOTEM_OF_UNDYING),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier3_dark")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER4.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER4.get()),
                        BASE_TIME * INFUSE_MULT * MARID_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.BEACON),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier4")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER4_DARK.get()),
                        BASE_TIME * INFUSE_MULT * MARID_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get()),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.BEACON),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_stabilizer_tier4_dark")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER5.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER5.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(Items.SCULK_SHRIEKER),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        ofTag(registries, Tags.Items.OBSIDIANS_CRYING),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
                        ofTag(registries, OccultismTags.Items.DRAGONYST_DUST))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.endermen")
                .entityToSacrifice(Entities.ENDERMEN)
                .unlockedBy("has_stabilizer_tier4", has(registries, OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_stabilizer_tier5")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get()),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get().asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER5_DARK.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(Items.SCULK_SHRIEKER),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        ofTag(registries, Tags.Items.OBSIDIANS_CRYING),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
                        ofTag(registries, OccultismTags.Items.DRAGONYST_DUST))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.endermen")
                .entityToSacrifice(Entities.ENDERMEN)
                .unlockedBy("has_stabilizer_tier4", has(registries, OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_stabilizer_tier5_dark")));
    }

    private static void minerRecipes(RecipeOutput recipeOutput, Provider registries) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStackTemplate(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_FOLIOT_UNSPECIALIZED.get()),
                        BASE_TIME * INFUSE_MULT * FOLIOT_TIER,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_FOLIOT, registries,
                        Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        ofTag(registries, Tags.Items.GRAVELS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_foliot_unspecialized")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStackTemplate(OccultismItems.MINER_DJINNI_ORES.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_DJINNI_ORES.get()),
                        BASE_TIME * INFUSE_MULT * DJINNI_TIER,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.GEMS_LAPIS),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_djinni_ores")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_AFRIT_DEEPS.get()),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.MINER_DJINNI_ORES.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()),
                        Ingredient.of(Items.ECHO_SHARD),
                        Ingredient.of(Blocks.CRYING_OBSIDIAN))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_afrit_deeps")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStackTemplate(OccultismItems.MINER_MARID_MASTER.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_MARID_MASTER.get()),
                        BASE_TIME * INFUSE_MULT * MARID_TIER,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_MARID, registries,
                        Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(Items.NETHERITE_PICKAXE),
                        Ingredient.of(Items.DRAGON_BREATH),
                        Ingredient.of(Items.TOTEM_OF_UNDYING),
                        Ingredient.of(Items.NETHER_STAR),
                        Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_miner_marid_master")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.MINING_DIMENSION_CORE_PIECE.get()),
                        new ItemStackTemplate(OccultismItems.MINER_ANCIENT_ELDRITCH.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_MINER_ANCIENT_ELDRITCH.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()),
                        Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()))
                .unlockedBy("has_mining_dimension_core", has(registries, OccultismItems.MINING_DIMENSION_CORE_PIECE.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.creeper")
                .entityToSacrifice(Entities.CREEPER)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_miner_ancient_eldritch")));
    }

    private static void resurrectRituals(RecipeOutput recipeOutput, Provider registries) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SOUL_SHARD_ITEM.get()),
                        makeLoreSpawnEgg(OccultismItems.RESURRECT_ICON.get(), "item.occultism.ritual_dummy.resurrect_familiar"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_FAMILIAR.get()),
                        BASE_TIME * LOW_TIER,
                        OccultismRituals.RESURRECT_FAMILIAR.getId(),
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .unlockedBy("has_otherworld_essence", has(registries, OccultismItems.OTHERWORLD_ESSENCE.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_familiar")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, Tags.Items.BONES),
                        makeLoreSpawnEgg(Items.SKELETON_HORSE_SPAWN_EGG, "item.occultism.ritual_dummy.resurrect_skeleton_horse"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_SKELETON_HORSE.get()),
                        BASE_TIME,
                        RITUAL_SUMMON,
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        ofTag(registries, Tags.Items.BRICKS),
                        ofTag(registries, ItemTags.COALS),
                        ofTag(registries, OccultismTags.Items.OBSIDIAN_DUST),
                        Ingredient.of(Items.FLINT))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.SKELETON_HORSE)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .entityToSacrifice(Entities.HORSES)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_skeleton_horse")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.ROTTEN_FLESH),
                        makeLoreSpawnEgg(Items.ZOMBIE_HORSE_SPAWN_EGG, "item.occultism.ritual_dummy.resurrect_zombie_horse"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_ZOMBIE_HORSE.get()),
                        BASE_TIME,
                        RITUAL_SUMMON,
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        ofTag(registries, Tags.Items.BRICKS),
                        ofTag(registries, ItemTags.COALS),
                        ofTag(registries, OccultismTags.Items.OBSIDIAN_DUST),
                        Ingredient.of(Items.FLINT))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.ZOMBIE_HORSE)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .entityToSacrifice(Entities.HORSES)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_zombie_horse")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.ROTTEN_FLESH),
                        makeLoreSpawnEgg(Items.ZOMBIE_NAUTILUS_SPAWN_EGG, "item.occultism.ritual_dummy.resurrect_zombie_nautilus"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_ZOMBIE_NAUTILUS.get()),
                        BASE_TIME,
                        RITUAL_SUMMON,
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        ofTag(registries, Tags.Items.BRICKS),
                        ofTag(registries, ItemTags.COALS),
                        ofTag(registries, OccultismTags.Items.OBSIDIAN_DUST),
                        Ingredient.of(Items.DRIED_KELP))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.ZOMBIE_NAUTILUS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.nautilus")
                .entityToSacrifice(Entities.NAUTILUS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_zombie_nautilus")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.ROTTEN_FLESH),
                        makeLoreSpawnEgg(Items.CAMEL_HUSK_SPAWN_EGG, "item.occultism.ritual_dummy.resurrect_camel_husk"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_CAMEL_HUSK.get()),
                        BASE_TIME,
                        RITUAL_SUMMON,
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        ofTag(registries, Tags.Items.BRICKS),
                        ofTag(registries, ItemTags.COALS),
                        ofTag(registries, OccultismTags.Items.OBSIDIAN_DUST),
                        ofTag(registries, ItemTags.SAND))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.CAMEL_HUSK)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.camel")
                .entityToSacrifice(Entities.CAMEL)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_camel_husk")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.SUGAR),
                        makeLoreSpawnEgg(Items.ALLAY_SPAWN_EGG, "item.occultism.ritual_dummy.resurrect_allay"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_ALLAY.get()),
                        BASE_TIME,
                        RITUAL_SUMMON,
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        ofTag(registries, Tags.Items.DUSTS_REDSTONE),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE),
                        ofTag(registries, OccultismTags.Items.SILVER_DUST),
                        ofTag(registries, OccultismTags.Items.GOLD_DUST))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.ALLAY)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.vex")
                .entityToSacrifice(Entities.VEX)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_allay")));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SOUL_SHATTERED_ITEM.get()),
                        makeLoreSpawnEgg(OccultismItems.RESURRECT_ICON.get(), "item.occultism.ritual_dummy.resurrect_mob"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_RESURRECT_MOB.get()),
                        BASE_TIME * WILD_TIER,
                        OccultismRituals.RESURRECT_FAMILIAR.getId(),
                        PENTACLE_RESURRECT_SPIRIT, registries,
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()),
                        Ingredient.of(OccultismBlocks.TALLOW_BLOCK.get()),
                        Ingredient.of(Items.GHAST_TEAR))
                .unlockedBy("has_otherworld_essence", has(registries, OccultismItems.OTHERWORLD_ESSENCE.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/resurrect_mob")));
    }

    private static void repairRituals(RecipeOutput recipeOutput, Provider registries) {
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, OccultismTags.Items.TOOLS_CHALK),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_chalks"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_CHALKS.get()),
                        BASE_TIME * REPAIR_MULT * DJINNI_TIER,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_DJINNI, registries,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_white_chalk", has(registries, OccultismItems.CHALK_WHITE))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_chalks")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, Tags.Items.TOOLS),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_tools"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_TOOLS.get()),
                        BASE_TIME * REPAIR_MULT * AFRIT_TIER,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_tools")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, Tags.Items.ARMORS),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_armors"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_ARMORS.get()),
                        BASE_TIME * REPAIR_MULT * AFRIT_TIER,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_armors")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, Miners.MINERS),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.repair_miners"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_REPAIR_MINERS.get()),
                        BASE_TIME * REPAIR_MULT * AFRIT_TIER,
                        RITUAL_REPAIR,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/repair_miners")));
    }

    private static void contactRituals(RecipeOutput recipeOutput, Provider registries) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.SKELETON_SKULL),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON.get(), "item.occultism.ritual_dummy.wild_hunt"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_HUNT.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COPPER),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.NETHERRACKS),
                        ofTag(registries, ItemTags.SOUL_FIRE_BASE_BLOCKS))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HUNT_WITHER_SKELETON.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(Entities.HUMANS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_hunt")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, ItemTags.PICKAXES),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_HORDE_HUSK.get(), "item.occultism.ritual_dummy.wild_husk"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_HUSK.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.SANDSTONE),
                        Ingredient.of(Items.DEAD_BUSH),
                        Ingredient.of(Items.ROTTEN_FLESH),
                        Ingredient.of(Items.GOLD_INGOT))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_HUSK.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.camel")
                .entityToSacrifice(Entities.CAMEL)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_husk")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, ItemTags.PICKAXES),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_HORDE_PARCHED.get(), "item.occultism.ritual_dummy.wild_parched"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_PARCHED.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.SANDSTONE),
                        Ingredient.of(Items.DEAD_BUSH),
                        Ingredient.of(Items.BONE),
                        Ingredient.of(Items.GOLD_INGOT))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_PARCHED.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.camel")
                .entityToSacrifice(Entities.CAMEL)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_parched")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.FISHING_ROD),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_HORDE_DROWNED.get(), "item.occultism.ritual_dummy.wild_drowned"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_DROWNED.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.PUFFERFISH),
                        Ingredient.of(Items.GRAVEL),
                        Ingredient.of(Items.DRIED_KELP_BLOCK),
                        Ingredient.of(Items.GRAVEL)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_DROWNED.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.fish")
                .entityToSacrifice(Entities.FISH)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_drowned")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.FLINT_AND_STEEL),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_HORDE_CREEPER.get(), "item.occultism.ritual_dummy.wild_creeper"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_CREEPER.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.MOSS_BLOCK),
                        Ingredient.of(Items.TNT),
                        Ingredient.of(Items.MOSS_BLOCK),
                        ofTag(registries, ItemTags.LEAVES)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_CREEPER.get())
                .summonNumber(5)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(Entities.PIGS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_creeper")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.BRUSH),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_HORDE_SILVERFISH.get(), "item.occultism.ritual_dummy.wild_silverfish"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_SILVERFISH.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.SAND),
                        ofTag(registries, ItemTags.TERRACOTTA),
                        Ingredient.of(Items.GRAVEL),
                        ofTag(registries, ItemTags.TERRACOTTA)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.WILD_HORDE_SILVERFISH.get())
                .summonNumber(5)
                .itemToUse(ofTag(registries, Tags.Items.EGGS))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_silverfish")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.HONEYCOMB),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_WEAK_BREEZE.get(), "item.occultism.ritual_dummy.wild_weak_breeze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_WEAK_BREEZE.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(Items.POLISHED_TUFF),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(Items.POLISHED_TUFF))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_WEAK_BREEZE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.snow_golem")
                .entityToSacrifice(Entities.SNOW_GOLEM)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_weak_breeze")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.TRIAL_KEY),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_BREEZE.get(), "item.occultism.ritual_dummy.wild_breeze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_BREEZE.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.COPPER_GRATE, Items.WAXED_COPPER_GRATE),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.COPPER_GRATE, Items.WAXED_COPPER_GRATE),
                        Ingredient.of(Items.TUFF_BRICKS),
                        Ingredient.of(Items.WIND_CHARGE),
                        Ingredient.of(Items.GUNPOWDER),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(Items.PHANTOM_MEMBRANE)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_BREEZE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.copper_golem")
                .entityToSacrifice(Entities.COPPER_GOLEM)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_breeze")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.OMINOUS_TRIAL_KEY),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_STRONG_BREEZE.get(), "item.occultism.ritual_dummy.wild_strong_breeze"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_STRONG_BREEZE.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.COPPER_BULB, Items.WAXED_COPPER_BULB),
                        Ingredient.of(Items.CHISELED_TUFF_BRICKS),
                        Ingredient.of(Items.COPPER_BULB, Items.WAXED_COPPER_BULB),
                        Ingredient.of(Items.CHISELED_TUFF_BRICKS),
                        ofTag(registries, Tags.Items.RODS_BREEZE),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.SLIME_BALLS),
                        Ingredient.of(Items.ECHO_SHARD),
                        Ingredient.of(Items.SHULKER_SHELL),
                        Ingredient.of(Items.FERMENTED_SPIDER_EYE),
                        Ingredient.of(Items.OMINOUS_BOTTLE),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_STRONG_BREEZE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.iron_golem")
                .entityToSacrifice(Entities.IRON_GOLEM)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_strong_breeze")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.GOLDEN_APPLE),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_WILD_EVOKER.get(), "item.occultism.ritual_dummy.wild_horde_illager"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_ILLAGER.get()),
                        BASE_TIME * INVOKE_MULT * WILD_TIER,
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.DARK_OAK_LOG),
                        Ingredient.of(Items.DARK_OAK_LOG),
                        Ingredient.of(Items.DARK_OAK_LOG),
                        ofTag(registries, OccultismTags.Items.EMERALD_DUST),
                        ofTag(registries, OccultismTags.Items.EMERALD_DUST),
                        ofTag(registries, OccultismTags.Items.EMERALD_DUST))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_EVOKER.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(Entities.HUMANS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_horde_illager")));
        //Forge
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.DIAMOND_BLOCK),
                        new ItemStackTemplate(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_WILD_TRIM.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
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
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrifice(Entities.LLAMAS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.llamas")
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_wild_trim")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.AMETHYST_BLOCK),
                        new ItemStackTemplate(Items.BUDDING_AMETHYST),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BUDDING_AMETHYST.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST),
                        ofTag(registries, OccultismTags.Items.AMETHYST_DUST)
                )
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(Entities.PIGS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_budding_amethyst")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.DEEPSLATE),
                        new ItemStackTemplate(Items.REINFORCED_DEEPSLATE),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_REINFORCED_DEEPSLATE.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(Items.IRON_BARS),
                        ofTag(registries, Tags.Items.OBSIDIANS),
                        ofTag(registries, Tags.Items.OBSIDIANS),
                        ofTag(registries, Tags.Items.OBSIDIANS),
                        ofTag(registries, Tags.Items.OBSIDIANS),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.warden")
                .entityToSacrifice(Entities.WARDEN)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_reinforced_deepslate")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.BEEHIVE),
                        new ItemStackTemplate(Items.BEE_NEST),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BEE_NEST.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_UPGRADE,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.HONEYCOMB_BLOCK),
                        Ingredient.of(Items.HONEYCOMB_BLOCK),
                        Ingredient.of(Items.HONEYCOMB_BLOCK),
                        Ingredient.of(Items.HONEYCOMB_BLOCK))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.bees")
                .entityToSacrifice(Entities.BEES)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_bee_nest")));
        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, Tags.Items.STORAGE_BLOCKS_GOLD),
                        new ItemStackTemplate(Items.BELL),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BELL.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.NUGGETS_GOLD),
                        Ingredient.of(Items.IRON_CHAIN),
                        ofTag(registries, Tags.Items.STONES),
                        ofTag(registries, Tags.Items.STONES),
                        ofTag(registries, ItemTags.LOGS))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.goats")
                .entityToSacrifice(Entities.GOATS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_bell")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.LEATHER_HORSE_ARMOR),
                        new ItemStackTemplate(Items.COPPER_HORSE_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_COPPER_HORSE_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_COPPER),
                        ofTag(registries, Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.armadillos")
                .entityToSacrifice(Entities.ARMADILLOS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_copper_horse_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.LEATHER_HORSE_ARMOR),
                        new ItemStackTemplate(Items.IRON_HORSE_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_IRON_HORSE_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        ofTag(registries, Tags.Items.INGOTS_IRON),
                        ofTag(registries, Tags.Items.INGOTS_IRON))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.armadillos")
                .entityToSacrifice(Entities.ARMADILLOS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_iron_horse_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.LEATHER_HORSE_ARMOR),
                        new ItemStackTemplate(Items.GOLDEN_HORSE_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_GOLDEN_HORSE_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_GOLD),
                        ofTag(registries, Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.armadillos")
                .entityToSacrifice(Entities.ARMADILLOS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_golden_horse_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.LEATHER_HORSE_ARMOR),
                        new ItemStackTemplate(Items.DIAMOND_HORSE_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_DIAMOND_HORSE_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.armadillos")
                .entityToSacrifice(Entities.ARMADILLOS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_diamond_horse_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.LEATHER_HORSE_ARMOR),
                        new ItemStackTemplate(OccultismItems.SILVER_HORSE_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_SILVER_HORSE_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT),
                        ofTag(registries, OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.armadillos")
                .entityToSacrifice(Entities.ARMADILLOS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_silver_horse_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.COPPER_HORSE_ARMOR),
                        new ItemStackTemplate(Items.COPPER_NAUTILUS_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_COPPER_NAUTILUS_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.NAUTILUS_SHELL, Items.TURTLE_SCUTE),
                        ofTag(registries, Tags.Items.FOODS_RAW_FISH),
                        Ingredient.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.GLOW_INK_SAC, Items.INK_SAC))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSacrifice(Entities.SQUID)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_copper_nautilus_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.IRON_HORSE_ARMOR),
                        new ItemStackTemplate(Items.IRON_NAUTILUS_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_IRON_NAUTILUS_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.NAUTILUS_SHELL, Items.TURTLE_SCUTE),
                        ofTag(registries, Tags.Items.FOODS_RAW_FISH),
                        Ingredient.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.GLOW_INK_SAC, Items.INK_SAC))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSacrifice(Entities.SQUID)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_iron_nautilus_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.GOLDEN_HORSE_ARMOR),
                        new ItemStackTemplate(Items.GOLDEN_NAUTILUS_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_GOLDEN_NAUTILUS_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.NAUTILUS_SHELL, Items.TURTLE_SCUTE),
                        ofTag(registries, Tags.Items.FOODS_RAW_FISH),
                        Ingredient.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.GLOW_INK_SAC, Items.INK_SAC))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSacrifice(Entities.SQUID)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_golden_nautilus_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.DIAMOND_HORSE_ARMOR),
                        new ItemStackTemplate(Items.DIAMOND_NAUTILUS_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_DIAMOND_NAUTILUS_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.NAUTILUS_SHELL, Items.TURTLE_SCUTE),
                        ofTag(registries, Tags.Items.FOODS_RAW_FISH),
                        Ingredient.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.GLOW_INK_SAC, Items.INK_SAC))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSacrifice(Entities.SQUID)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_diamond_nautilus_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SILVER_HORSE_ARMOR),
                        new ItemStackTemplate(OccultismItems.SILVER_NAUTILUS_ARMOR),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_SILVER_NAUTILUS_ARMOR.get()),
                        BASE_TIME * FORGE_MULT * WILD_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.NAUTILUS_SHELL, Items.TURTLE_SCUTE),
                        ofTag(registries, Tags.Items.FOODS_RAW_FISH),
                        Ingredient.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.GLOW_INK_SAC, Items.INK_SAC))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSacrifice(Entities.SQUID)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_silver_nautilus_armor")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.asItem()),
                        new ItemStackTemplate(OccultismBlocks.ELDRITCH_CHALICE.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_ELDRITCH_CHALICE.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(Items.HEAVY_CORE),
                        Ingredient.of(Items.BELL),
                        Ingredient.of(Items.SOUL_LANTERN),
                        Ingredient.of(Items.CHORUS_FLOWER),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        Ingredient.of(Items.SPONGE),
                        Ingredient.of(Items.REINFORCED_DEEPSLATE),
                        Ingredient.of(Items.RESPAWN_ANCHOR),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.END_STONE_BRICKS),
                        Ingredient.of(Items.SCULK_CATALYST),
                        Ingredient.of(Items.BUDDING_AMETHYST))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.ravager")
                .entityToSacrifice(Entities.RAVAGER)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_eldritch_chalice")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem()),
                        new ItemStackTemplate(OccultismBlocks.CELESTIAL_CHALICE.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_CELESTIAL_CHALICE.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(Items.HEAVY_CORE),
                        Ingredient.of(Items.BELL),
                        Ingredient.of(Items.SOUL_LANTERN),
                        Ingredient.of(Items.CHORUS_FLOWER),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        Ingredient.of(Items.SPONGE),
                        Ingredient.of(Items.REINFORCED_DEEPSLATE),
                        Ingredient.of(Items.RESPAWN_ANCHOR),
                        ofTag(registries, OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.END_STONE_BRICKS),
                        Ingredient.of(Items.SCULK_CATALYST),
                        Ingredient.of(Items.BUDDING_AMETHYST))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.ravager")
                .entityToSacrifice(Entities.RAVAGER)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_celestial_chalice")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BRUSH),
                        new ItemStackTemplate(OccultismItems.CHALK_RAINBOW.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_RAINBOW.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
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
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.sheep")
                .entityToSacrifice(Entities.SHEEP)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_chalk_rainbow")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BRUSH),
                        new ItemStackTemplate(OccultismItems.CHALK_VOID.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_VOID.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismItems.CHALK_WHITE),
                        Ingredient.of(OccultismItems.CHALK_LIGHT_GRAY),
                        Ingredient.of(OccultismItems.CHALK_GRAY),
                        Ingredient.of(OccultismItems.CHALK_BLACK))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.squid")
                .entityToSacrifice(Entities.SQUID)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_chalk_void")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SOUL_GEM_ITEM),
                        new ItemStackTemplate(OccultismItems.TRINITY_GEM_ITEM.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_TRINITY_GEM.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE),
                        Ingredient.of(OccultismItems.MARID_ESSENCE),
                        Ingredient.of(OccultismItems.CRUELTY_ESSENCE),
                        ofTag(registries, OccultismTags.Items.ECHO_DUST),
                        ofTag(registries, OccultismTags.Items.DRAGONYST_DUST),
                        ofTag(registries, OccultismTags.Items.WITHERITE_DUST),
                        ofTag(registries, OccultismTags.Items.IESNIUM_DUST),
                        ofTag(registries, OccultismTags.Items.IESNIUM_DUST),
                        ofTag(registries, OccultismTags.Items.IESNIUM_DUST))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.humans")
                .entityToSacrifice(Entities.HUMANS)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_trinity_gem")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        new ItemStackTemplate(OccultismItems.BEDROCK_GEM_CLUSTER.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_BEDROCK_GEM_CLUSTER.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_CRAFT,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismItems.BEDROCK_SCRAP.get()),
                        Ingredient.of(OccultismItems.BEDROCK_SCRAP.get()),
                        Ingredient.of(OccultismItems.BEDROCK_SCRAP.get()),
                        Ingredient.of(OccultismItems.BEDROCK_SCRAP.get()),
                        ofTag(registries, Tags.Items.GEMS_EMERALD),
                        ofTag(registries, Tags.Items.GEMS_DIAMOND),
                        ofTag(registries, Tags.Items.GEMS_LAPIS),
                        ofTag(registries, Tags.Items.GEMS_PRISMARINE),
                        ofTag(registries, Tags.Items.DUSTS_REDSTONE),
                        ofTag(registries, Tags.Items.GEMS_QUARTZ),
                        ofTag(registries, Tags.Items.GEMS_AMETHYST),
                        ofTag(registries, Tags.Items.DUSTS_GLOWSTONE))
                .unlockedBy("has_bedrock_scrap", has(registries, OccultismItems.BEDROCK_SCRAP.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.witch")
                .entityToSacrifice(OccultismTags.Entities.WITCH)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_bedrock_gem_cluster")));

        RitualRecipeBuilder.ritualRecipeBuilder(ofTag(registries, ItemTags.DURABILITY_ENCHANTABLE),
                        makeLoreSpawnEgg(OccultismItems.REPAIR_ICON.get(), "item.occultism.ritual_dummy.misc_unbreakable"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_UNBREAKABLE.get()),
                        BASE_TIME * REPAIR_MULT * GREAT_TIER,
                        RITUAL_UNBREAKABLE,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismItems.BEDROCK_GEM_CLUSTER),
                        ofTag(registries, OccultismTags.Items.IESNIUM_INGOT),
                        ofTag(registries, Tags.Items.NETHER_STARS),
                        ofTag(registries, Tags.Items.INGOTS_NETHERITE)
                )
                .unlockedBy("has_bedrock_gem_cluster", has(registries, OccultismItems.BEDROCK_GEM_CLUSTER))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.evoker")
                .entityToSacrifice(OccultismTags.Entities.EVOKER)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_unbreakable")));
    }

    private static void randomRituals(RecipeOutput recipeOutput, Provider registries) {
        //Individual
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_common"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_COMMON),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.CROPS),
                        ofTag(registries, Tags.Items.CROPS),
                        ofTag(registries, Tags.Items.CROPS),
                        ofTag(registries, Tags.Items.CROPS))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_COMMON)
                .itemToUse(ofTag(registries, Tags.Items.EGGS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_common")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_water"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_WATER),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.CLAY),
                        Ingredient.of(Items.CLAY))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_WATER)
                .itemToUse(Ingredient.of(Items.SNOWBALL))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_water")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_small"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SMALL),
                        BASE_TIME * POSSESS_MULT * FOLIOT_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_FOLIOT, registries,
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.NUGGETS_IRON),
                        ofTag(registries, Tags.Items.NUGGETS_IRON),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(Items.SUGAR))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_SMALL)
                .itemToUse(ofTag(registries, Tags.Items.EGGS))
                .unlockedBy("has_bound_foliot", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_small")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_rideable"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_RIDEABLE),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_WHEAT),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_WHEAT),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.CARROT),
                        Ingredient.of(Items.GOLDEN_CARROT),
                        ofTag(registries, Tags.Items.CROPS_CACTUS),
                        Ingredient.of(Items.WARPED_FUNGUS))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_RIDEABLE)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_rideable")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_villager"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_VILLAGER),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, ItemTags.BEDS),
                        Ingredient.of(Items.CAMPFIRE),
                        ofTag(registries, Tags.Items.FOODS_PIE))
                .entityTagToSummon(Entities.VILLAGERS)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_villager")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.possess_random_animal_special"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SPECIAL),
                        BASE_TIME * POSSESS_MULT * DJINNI_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON,
                        PENTACLE_POSSESS_DJINNI, registries,
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, OccultismTags.Items.MUSHROOM_BLOCKS),
                        Ingredient.of(Items.MOSS_BLOCK),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_IRON),
                        Ingredient.of(Items.PACKED_ICE),
                        Ingredient.of(Items.TERRACOTTA),
                        Ingredient.of(Items.BAMBOO_BLOCK))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_SPECIAL)
                .itemToUse(Ingredient.of(Items.WIND_CHARGE))
                .unlockedBy("has_bound_djinni", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/possess_random_animal_special")));
        //Group
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_common"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_COMMON),
                        BASE_TIME * INVOKE_MULT * WILD_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.SEEDS),
                        ofTag(registries, Tags.Items.CROPS),
                        ofTag(registries, Tags.Items.CROPS),
                        ofTag(registries, Tags.Items.CROPS),
                        ofTag(registries, Tags.Items.CROPS))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_COMMON)
                .itemToUse(ofTag(registries, Tags.Items.EGGS))
                .unlockedBy("has_spirit_attuned_gem", has(registries, OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(7)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_common")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_water"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_WATER),
                        BASE_TIME * INVOKE_MULT * WILD_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.SEAGRASS),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.KELP),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.MUD),
                        Ingredient.of(Items.CLAY),
                        Ingredient.of(Items.CLAY))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_WATER)
                .itemToUse(Ingredient.of(Items.SNOWBALL))
                .unlockedBy("has_spirit_attuned_gem", has(registries, OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(7)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_water")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_small"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SMALL),
                        BASE_TIME * INVOKE_MULT * WILD_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.FEATHERS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.STRINGS),
                        ofTag(registries, Tags.Items.NUGGETS_IRON),
                        ofTag(registries, Tags.Items.NUGGETS_IRON),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(Items.SUGAR))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_SMALL)
                .itemToUse(ofTag(registries, Tags.Items.EGGS))
                .unlockedBy("has_spirit_attuned_gem", has(registries, OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(7)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_small")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_rideable"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_RIDEABLE),
                        BASE_TIME * INVOKE_MULT * WILD_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_WHEAT),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_WHEAT),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.CARROT),
                        Ingredient.of(Items.GOLDEN_CARROT),
                        ofTag(registries, Tags.Items.CROPS_CACTUS),
                        Ingredient.of(Items.WARPED_FUNGUS))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_RIDEABLE)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_spirit_attuned_gem", has(registries, OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(3)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_rideable")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_villager"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_VILLAGER),
                        BASE_TIME * INVOKE_MULT * WILD_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, ItemTags.BEDS),
                        Ingredient.of(Items.CAMPFIRE),
                        ofTag(registries, Tags.Items.FOODS_PIE))
                .entityTagToSummon(Entities.VILLAGERS)
                .itemToUse(Ingredient.of(Items.EXPERIENCE_BOTTLE))
                .unlockedBy("has_spirit_attuned_gem", has(registries, OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(3)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_villager")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM),
                        makeLoreSpawnEgg(OccultismItems.MYSTERIOUS_EGG_ICON.get(), "item.occultism.ritual_dummy.wild_random_animal_special"),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SPECIAL),
                        BASE_TIME * INVOKE_MULT * WILD_TIER * HALF_MULT, //half because is random
                        RITUAL_SUMMON_WILD,
                        PENTACLE_CONTACT_WILD_SPIRIT, registries,
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, ItemTags.WOOL),
                        ofTag(registries, OccultismTags.Items.MUSHROOM_BLOCKS),
                        Ingredient.of(Items.MOSS_BLOCK),
                        ofTag(registries, Tags.Items.STORAGE_BLOCKS_IRON),
                        Ingredient.of(Items.PACKED_ICE),
                        Ingredient.of(Items.TERRACOTTA),
                        Ingredient.of(Items.BAMBOO_BLOCK))
                .entityTagToSummon(Entities.RANDOM_ANIMALS_SPECIAL)
                .itemToUse(Ingredient.of(Items.WIND_CHARGE))
                .unlockedBy("has_spirit_attuned_gem", has(registries, OccultismItems.SPIRIT_ATTUNED_GEM))
                .summonNumber(3)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/wild_random_animal_special")));
    }

    private static void upgradeRituals(RecipeOutput recipeOutput, Provider registries) {
        //Individual
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStackTemplate(OccultismItems.RITUAL_SATCHEL_T2.get()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_CRAFT_UPGRADE_RITUAL_SATCHEL),
                        BASE_TIME * INFUSE_MULT * AFRIT_TIER * HALF_MULT, //optional upgrading, half the time
                        RITUAL_UPGRADE,
                        PENTACLE_CRAFT_AFRIT, registries,
                        Ingredient.of(OccultismItems.RITUAL_SATCHEL_T1),
                        Ingredient.of(OccultismItems.AFRIT_ESSENCE),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS),
                        ofTag(registries, Tags.Items.ENDER_PEARLS))
                .unlockedBy("has_bound_afrit", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/craft_upgrade_ritual_satchel")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.CALIBRATED_SCULK_SENSOR),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_STABILIZED_STORAGE.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_UPGRADE,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        ofTag(registries, OccultismTags.Items.ECHO_DUST),
                        ofTag(registries, OccultismTags.Items.ECHO_DUST))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.shulker")
                .entityToSacrifice(Entities.SHULKER)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_stabilized_storage")));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(Items.CALIBRATED_SCULK_SENSOR),
                        new ItemStackTemplate(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.asItem()),
                        makeRitualDummy(OccultismItems.RITUAL_DUMMY_FORGE_STABILIZED_STORAGE_DARK.get()),
                        BASE_TIME * FORGE_MULT * GREAT_TIER,
                        RITUAL_UPGRADE,
                        PENTACLE_CONTACT_ELDRITCH_SPIRIT, registries,
                        Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5, OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK),
                        ofTag(registries, OccultismTags.Items.ECHO_DUST),
                        ofTag(registries, OccultismTags.Items.ECHO_DUST))
                .unlockedBy("has_bound_marid", has(registries, OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.shulker")
                .entityToSacrifice(Entities.SHULKER)
                .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual/misc_stabilized_storage_dark")));
    }
}
