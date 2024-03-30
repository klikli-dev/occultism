package com.klikli_dev.occultism.datagen.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.builders.RitualRecipeBuilder;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public abstract class RitualRecipes extends RecipeProvider {


    // Ritual Types
    private static ResourceLocation RITUAL_FAMILIAR =new ResourceLocation(Occultism.MODID, "familiar");
    private static ResourceLocation RITUAL_CRAFT_WITH_SPIRIT_NAME=new ResourceLocation(Occultism.MODID, "craft_with_spirit_name");
    private static ResourceLocation RITUAL_CRAFT=new ResourceLocation(Occultism.MODID, "craft");
    private static ResourceLocation RITUAL_CRAFT_MINER_SPIRIT=new ResourceLocation(Occultism.MODID, "craft_miner_spirit");
    private static final ResourceLocation RITUAL_SUMMON = new ResourceLocation(Occultism.MODID, "summon");

    // Pentacle IDs
    private static ResourceLocation PENTACLE_POSSESS_DJINNI = new ResourceLocation(Occultism.MODID, "possess_djinni");
    private static ResourceLocation PENTACLE_POSSESS_FOLIOT = new ResourceLocation(Occultism.MODID, "possess_foliot");
    private static ResourceLocation PENTACLE_POSSESS_AFRIT = new ResourceLocation(Occultism.MODID, "possess_afrit");
    private static ResourceLocation PENTACLE_CRAFT_DJINNI = new ResourceLocation(Occultism.MODID, "craft_djinni");
    private static ResourceLocation PENTACLE_CRAFT_FOLIOT = new ResourceLocation(Occultism.MODID, "craft_foliot");
    private static ResourceLocation PENTACLE_CRAFT_AFRIT = new ResourceLocation(Occultism.MODID, "craft_afrit");
    private static ResourceLocation PENTACLE_CRAFT_MARID = new ResourceLocation(Occultism.MODID, "craft_marid");


    public RitualRecipes(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }
    private static CompoundTag getDisplayTag(String key) {
        var displayTag = new CompoundTag();
        var Lore = new ListTag();
        Lore.add(StringTag.valueOf("[{\"translate\":\""+key+".tooltip\"}]"));
        displayTag.put("Lore", Lore);
        displayTag.put("Name", StringTag.valueOf("{\"translate\":\""+key+"\"}"));
        return displayTag;
    }
    private static ItemStack makeLoreSpawnEgg(Item item, String key) {
        ItemStack output = new ItemStack(item);
        output.addTagElement("display", getDisplayTag(key));
        return output;
    }
    private static ItemStack makeRitualDummy(ResourceLocation location) {
        return new ItemStack(BuiltInRegistries.ITEM.get(location));
    }
    public static void ritualRecipes(RecipeOutput recipeOutput) {
        craftingRituals(recipeOutput);
        familiarRituals(recipeOutput);
        possessRituals(recipeOutput);

    }

    private static void possessRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                makeLoreSpawnEgg(Items.HEART_OF_THE_SEA, "item.occultism.ritual_dummy.possess_elder_guardian"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/possess_elder_guardian")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/possess_elder_guardian"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI),
                makeLoreSpawnEgg(Items.ENDER_PEARL, "item.occultism.ritual_dummy.possess_enderman"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/possess_enderman")),
                60,
                RITUAL_SUMMON,
                PENTACLE_POSSESS_DJINNI,
                Ingredient.of(Tags.Items.BONES),
                Ingredient.of(Tags.Items.STRING),
                Ingredient.of(Tags.Items.END_STONES),
                Ingredient.of(Items.ROTTEN_FLESH))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get())
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.pigs")
                .entityToSacrifice(OccultismTags.Entities.PIGS)
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/possess_enderman"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                makeLoreSpawnEgg(Items.END_STONE, "item.occultism.ritual_dummy.possess_endermite"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/possess_endermite")),
                30,
                RITUAL_SUMMON,
                PENTACLE_POSSESS_FOLIOT,
                Ingredient.of(ItemTags.DIRT),
                Ingredient.of(Tags.Items.STONE),
                Ingredient.of(ItemTags.DIRT),
                Ingredient.of(Tags.Items.STONE))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .itemToUse(Ingredient.of(Items.EGG))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/possess_endermite"));
    }

    private static void familiarRituals(RecipeOutput recipeOutput) {

        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BAT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_bat"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_bat")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_bat"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beaver"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_beaver")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS),
                        Ingredient.of(ItemTags.LOGS))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_beaver"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_beholder"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_beholder")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_beholder"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_blacksmith"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_blacksmith")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Items.IRON_SHOVEL),
                        Ingredient.of(Items.IRON_PICKAXE),
                        Ingredient.of(Items.IRON_AXE),
                        Ingredient.of(Items.ANVIL),
                        Ingredient.of(Tags.Items.STONE),
                        Ingredient.of(Tags.Items.STONE),
                        Ingredient.of(Tags.Items.STONE),
                        Ingredient.of(Tags.Items.STONE))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.ZOMBIES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.zombies")
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_blacksmith"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR.get(),"item.occultism.ritual_dummy.familiar_cthulhu"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_cthulhu")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_cthulhu"));



        RitualRecipeBuilder.ritualRecipeBuilder(
                        Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_chimera"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_chimera")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Tags.Items.LEATHER),
                        Ingredient.of(Tags.Items.STRING),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_chimera"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEER_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_deer"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_deer")),
                        15,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_FOLIOT,
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.RODS_WOODEN),
                        Ingredient.of(Tags.Items.STRING),
                        Ingredient.of(Tags.Items.STRING))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.COWS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.cows")
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_deer"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_devil"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_devil")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_devil"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_dragon"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_dragon")),
                        60,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.LAVA_BUCKET),
                        Ingredient.of(Items.FLINT_AND_STEEL),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COAL),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Tags.Items.GUNPOWDER),
                        Ingredient.of(Items.OBSIDIAN),
                        Ingredient.of(Items.OBSIDIAN))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.DRAGON_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_dragon"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_fairy"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_fairy")),
                        30,
                        RITUAL_FAMILIAR,
                        PENTACLE_POSSESS_DJINNI,
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(Tags.Items.GUNPOWDER),
                        Ingredient.of(Tags.Items.GUNPOWDER),
                        Ingredient.of(Tags.Items.GUNPOWDER),
                        Ingredient.of(Items.DRAGON_BREATH))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.HORSES)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.horses")
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_fairy"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_greedy"),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_greedy")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_greedy"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_guardian"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_guardian")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_guardian"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_headless"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_headless")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_headless"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_mummy"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_mummy")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_mummy"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD.get(), "item.occultism.ritual_dummy.familiar_otherworld_bird"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_otherworld_bird")),
                30,
                RITUAL_FAMILIAR,
                PENTACLE_POSSESS_DJINNI,
                Ingredient.of(Tags.Items.FEATHERS),
                Ingredient.of(Tags.Items.FEATHERS),
                Ingredient.of(Blocks.COBWEB),
                Ingredient.of(ItemTags.LEAVES),
                Ingredient.of(Tags.Items.STRING))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .entityToSummon(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .entityToSacrifice(OccultismTags.Entities.PARROTS)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.parrots")
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_otherworld_bird"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                makeLoreSpawnEgg(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR.get(), "item.occultism.ritual_dummy.familiar_parrot"),
                makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/familiar_parrot")),
                30,
                RITUAL_FAMILIAR,
                PENTACLE_POSSESS_FOLIOT,
                Ingredient.of(Tags.Items.FEATHERS),
                Ingredient.of(Tags.Items.DYES_GREEN),
                Ingredient.of(Tags.Items.DYES_YELLOW),
                Ingredient.of(Tags.Items.DYES_RED),
                Ingredient.of(Tags.Items.DYES_BLUE),
                Ingredient.of(Tags.Items.STRING))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .entityToSummon(EntityType.PARROT)
                .entityToSacrifice(OccultismTags.Entities.CHICKEN)
                .entityToSacrificeDisplayName("ritual.occultism.sacrifice.chicken")
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/familiar_parrot"));


    }

    private static void craftingRituals(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.DIMENSIONAL_MATRIX.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/craft_dimensional_matrix")),
                        240,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ),
                        Ingredient.of(Tags.Items.ENDER_PEARLS)
                )
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_dimensional_matrix"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/craft_dimensional_mineshaft")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_dimensional_mineshaft"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.FAMILIAR_RING.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/craft_familiar_ring")),
                        90,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_familiar_ring"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.INFUSED_LENSES.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/craft_infused_lenses")),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.LENSES.get()),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT),
                        Ingredient.of(Tags.Items.INGOTS_GOLD)
                ).unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_infused_lenses"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.INFUSED_PICKAXE.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID, "ritual_dummy/craft_dimensional_matrix")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_infused_pickaxe"));

        minerRecipes(recipeOutput);

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.SATCHEL.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_satchel")),
                        240,
                        RITUAL_CRAFT_WITH_SPIRIT_NAME,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(Tags.Items.CHESTS_WOODEN),
                        Ingredient.of(Tags.Items.LEATHER),
                        Ingredient.of(Tags.Items.LEATHER),
                        Ingredient.of(Tags.Items.STRING),
                        Ingredient.of(OccultismTags.Items.SILVER_INGOT))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_satchel"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.SOUL_GEM_ITEM.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_soul_gem")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_soul_gem"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_stabilizer_tier1")),
                        120,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL.get()),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_COPPER),
                        Ingredient.of(OccultismTags.Items.BLAZE_DUST),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_stabilizer_tier1"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_stabilizer_tier2")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_SILVER),
                        Ingredient.of(Items.GHAST_TEAR),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()),
                        Ingredient.of(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_stabilizer_tier2"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_stabilizer_tier3")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_AFRIT,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()),
                        Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD),
                        Ingredient.of(Items.NETHER_STAR),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_afrit", has(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_stabilizer_tier3"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_stabilizer_tier4")),
                        240,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_MARID,
                        Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()),
                        Ingredient.of(OccultismTags.Items.STORAGE_BLOCK_IESNIUM),
                        Ingredient.of(Items.DRAGON_HEAD),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                        Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .unlockedBy("has_bound_marid", has(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_stabilizer_tier4"));


        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STABLE_WORMHOLE.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_stable_wormhole")),
                        120,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.WORMHOLE_FRAME.get()),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_stable_wormhole"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_storage_controller_base")),
                        60,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismBlocks.OTHERSTONE_PEDESTAL.get()),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD),
                        Ingredient.of(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_storage_controller_base"));
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.STORAGE_REMOTE.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_storage_remote")),
                        120,
                        RITUAL_CRAFT,
                        PENTACLE_CRAFT_DJINNI,
                        Ingredient.of(OccultismItems.STORAGE_REMOTE_INERT.get()),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.ENDER_PEARLS),
                        Ingredient.of(Tags.Items.GEMS_QUARTZ))
                .unlockedBy("has_bound_djinni", has(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_storage_remote"));

    }

    private static void minerRecipes(RecipeOutput recipeOutput) {
        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                        new ItemStack(OccultismItems.MINER_AFRIT_DEEPS.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_miner_afrit_deeps")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_miner_afrit_deeps"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                        new ItemStack(OccultismItems.MINER_DJINNI_ORES.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_miner_djinni_ores")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_miner_djinni_ores"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                        new ItemStack(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_miner_foliot_unspecialized")),
                        60,
                        RITUAL_CRAFT_MINER_SPIRIT,
                        PENTACLE_CRAFT_FOLIOT,
                        Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()),
                        Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()),
                        Ingredient.of(Tags.Items.RAW_MATERIALS_IRON),
                        Ingredient.of(Tags.Items.GRAVEL))
                .unlockedBy("has_bound_foliot", has(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_miner_foliot_unspecialized"));

        RitualRecipeBuilder.ritualRecipeBuilder(Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                        new ItemStack(OccultismItems.MINER_MARID_MASTER.get()),
                        makeRitualDummy(new ResourceLocation(Occultism.MODID,"ritual_dummy/craft_miner_marid_master")),
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
                .save(recipeOutput, new ResourceLocation(Occultism.MODID, "ritual/craft_miner_marid_master"));
    }
}
