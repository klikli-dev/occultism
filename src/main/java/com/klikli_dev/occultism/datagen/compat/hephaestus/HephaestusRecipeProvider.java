package com.klikli_dev.occultism.datagen.compat.hephaestus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NeoForgeConditions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class HephaestusRecipeProvider {

    public static final String FOLDER = "compat/" + HephaestusRecipeBuilder.MODID + "/";

    public static final Identifier MOLTEN_IESNIUM = HephaestusRecipeBuilder.id("molten_iesnium");

    private static final int TEMPERATURE = 1200;

    private static final int NUGGET = 10;
    private static final int INGOT = 90;
    private static final int BLOCK = INGOT * 9;
    private static final int BASE_TIME = 100;

    private final ICondition[] conditions = {
            NeoForgeConditions.modLoaded(HephaestusRecipeBuilder.MODID),
            NeoForgeConditions.registered(Registries.FLUID, MOLTEN_IESNIUM)
    };

    private final Provider registries;
    private final BiConsumer<Identifier, JsonObject> jsonOutput;

    protected HephaestusRecipeProvider(Provider registries, BiConsumer<Identifier, JsonObject> jsonOutput) {
        this.registries = registries;
        this.jsonOutput = jsonOutput;
    }

    public void buildRecipes() {
        HolderGetter<Item> items = this.registries.lookupOrThrow(Registries.ITEM);

        this.metalMelting(items);
        this.toolMelting();
        this.ritualBlockMelting();
        this.casting();
    }

    private void metalMelting(HolderGetter<Item> items) {
        this.melting("metal/iesnium/block", items, OccultismTags.Items.STORAGE_BLOCK_IESNIUM, BLOCK, BASE_TIME * 2);
        this.melting("metal/iesnium/raw_block", items, OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM, BLOCK, (int) (BASE_TIME * 2.5));
        this.melting("metal/iesnium/ingot", items, OccultismTags.Items.IESNIUM_INGOT, INGOT, BASE_TIME);
        this.melting("metal/iesnium/raw", items, OccultismTags.Items.RAW_IESNIUM, INGOT, (int) (BASE_TIME * 1.5));
        this.melting("metal/iesnium/nugget", items, OccultismTags.Items.IESNIUM_NUGGET, NUGGET, BASE_TIME / 3);
        this.melting("metal/iesnium/dust", items, OccultismTags.Items.IESNIUM_DUST, INGOT, BASE_TIME);
        this.melting("metal/iesnium/ore", items, OccultismTags.Items.IESNIUM_ORE, INGOT, (int) (BASE_TIME * 1.5));
    }

    private void toolMelting() {
        this.melting("tools/iesnium_pickaxe", OccultismItems.IESNIUM_PICKAXE, INGOT * 3, BASE_TIME * 2, true);
        this.melting("tools/iesnium_butcher_knife", OccultismItems.IESNIUM_BUTCHER_KNIFE, INGOT * 2, (int) (BASE_TIME * 1.5), true);
    }

    private void ritualBlockMelting() {
        this.melting("misc/iesnium_anvil", OccultismBlocks.IESNIUM_ANVIL, BLOCK * 3, BASE_TIME * 4, false);
        this.melting("misc/iesnium_sacrificial_bowl", OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL, BLOCK, BASE_TIME * 2, false);
        this.melting("misc/dark_iesnium_sacrificial_bowl", OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL, BLOCK, BASE_TIME * 2, false);
    }

    private void casting() {
        this.save("smeltery/casting/table/metal/iesnium/ingot_cast", HephaestusRecipeBuilder.castingTable(
                HephaestusRecipeBuilder.INGOT_CAST, MOLTEN_IESNIUM, INGOT, itemId(OccultismItems.IESNIUM_INGOT), BASE_TIME));
        this.save("smeltery/casting/table/metal/iesnium/nugget_cast", HephaestusRecipeBuilder.castingTable(
                HephaestusRecipeBuilder.NUGGET_CAST, MOLTEN_IESNIUM, NUGGET, itemId(OccultismItems.IESNIUM_NUGGET), BASE_TIME / 3));
        this.save("smeltery/casting/basin/metal/iesnium/block_cast", HephaestusRecipeBuilder.castingBasin(
                MOLTEN_IESNIUM, BLOCK, itemId(OccultismBlocks.IESNIUM_BLOCK), BASE_TIME * 2));
    }

    private void melting(String name, HolderGetter<Item> items, TagKey<Item> input, int amount, int time) {
        this.melting(name, ofTag(items, input), amount, time, false);
    }

    private void melting(String name, ItemLike input, int amount, int time, boolean damageable) {
        this.melting(name, Ingredient.of(input), amount, time, damageable);
    }

    private void melting(String name, Ingredient input, int amount, int time, boolean damageable) {
        this.save("smeltery/melting/" + name, HephaestusRecipeBuilder.melting(
                this.toJson(input), MOLTEN_IESNIUM, amount, TEMPERATURE, time, damageable));
    }

    private void save(String path, JsonObject recipe) {
        ICondition.writeConditions(this.registries, recipe, this.conditions);
        this.jsonOutput.accept(Identifier.fromNamespaceAndPath(Occultism.MODID, FOLDER + path), recipe);
    }

    private JsonElement toJson(Ingredient ingredient) {
        return Ingredient.CODEC.encodeStart(this.registries.createSerializationContext(JsonOps.INSTANCE), ingredient).getOrThrow();
    }

    private static Ingredient ofTag(HolderGetter<Item> items, TagKey<Item> tag) {
        return Ingredient.of(items.getOrThrow(tag));
    }

    private static Identifier itemId(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    public static class Runner implements DataProvider {

        private final PackOutput.PathProvider recipePathProvider;
        private final CompletableFuture<Provider> registries;

        public Runner(PackOutput packOutput, CompletableFuture<Provider> registries) {
            this.recipePathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
            this.registries = registries;
        }

        @Override
        public CompletableFuture<?> run(CachedOutput cache) {
            return this.registries.thenCompose(registries -> {
                Set<Identifier> allRecipes = new HashSet<>();
                List<CompletableFuture<?>> tasks = new ArrayList<>();

                new HephaestusRecipeProvider(registries, (id, recipe) -> {
                    if (!allRecipes.add(id))
                        throw new IllegalStateException("Duplicate recipe " + id);
                    tasks.add(DataProvider.saveStable(cache, recipe, this.recipePathProvider.json(id)));
                }).buildRecipes();

                return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
            });
        }

        @Override
        public String getName() {
            return "Occultism Hephaestus Compat Recipe Provider";
        }
    }
}
