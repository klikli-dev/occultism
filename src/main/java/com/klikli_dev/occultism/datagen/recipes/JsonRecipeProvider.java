package com.klikli_dev.occultism.datagen.recipes;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class JsonRecipeProvider implements DataProvider {

    protected final PackOutput.PathProvider recipePathProvider;
    protected String modid;

    protected BiConsumer<ResourceLocation, JsonObject> recipeConsumer;

    public JsonRecipeProvider(PackOutput packOutput, String modid) {
        this(packOutput, modid, "");
    }

    /**
     * Creates a new recipe provider with the given sub path.
     *
     * @param packOutput
     * @param recipeSubPath e.g. "calcination"
     */
    public JsonRecipeProvider(PackOutput packOutput, String modid, String recipeSubPath) {
        this.recipePathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipes/" + recipeSubPath);
        this.modid = modid;
    }

    protected String name(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    protected String name(TagKey<Item> tag) {
        return tag.location().getPath().replace('/', '_');
    }

    protected String name(List<TagKey<Item>> tags) {
        return tags.stream().distinct().map(this::name).reduce("", (a, b) -> a + "_and_" + b).replaceFirst("_and_", "");
    }

    public ResourceLocation locFor(TagKey<Item> tag) {
        return tag.location();
    }

    public ResourceLocation locFor(ItemLike itemLike) {
        return ForgeRegistries.ITEMS.getKey(itemLike.asItem());
    }

    public ResourceLocation locFor(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }

    public TagKey<Item> tag(String tag) {
        return this.tag(new ResourceLocation(tag));
    }

    public TagKey<Item> tag(ResourceLocation tag) {
        return TagKey.create(Registries.ITEM, tag);
    }

    public ResourceLocation modLoc(String name) {
        return new ResourceLocation(this.modid, name);
    }

    public ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }

    public ResourceLocation forgeLoc(String name) {
        return new ResourceLocation("forge", name);
    }

    public JsonObject makeFluidIngredient(ResourceLocation fluid) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("fluid", fluid.toString());
        return jsonobject;
    }

    public JsonObject makeFluidTagIngredient(ResourceLocation tag) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("tag", tag.toString());
        return jsonobject;
    }

    public JsonObject makeTagIngredient(ResourceLocation tag) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("tag", tag.toString());
        return jsonobject;
    }

    public JsonObject makeTagIngredient(ResourceLocation tag, int count) {
        var jsonobject = this.makeTagIngredient(tag);
        jsonobject.addProperty("count", count);
        return jsonobject;
    }

    public JsonObject makeItemIngredient(ResourceLocation item) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("item", item.toString());
        return jsonobject;
    }

    public JsonObject makeItemIngredient(ResourceLocation item, int count) {
        var jsonobject = this.makeItemIngredient(item);
        jsonobject.addProperty("count", count);
        return jsonobject;
    }


    public JsonObject makeItemResult(ResourceLocation item) {
        return this.makeItemResult(item, 1);
    }

    public JsonObject makeItemResult(ResourceLocation item, int count) {
        return this.makeItemResult(item, count, (JsonObject) null);
    }

    public JsonObject makeItemResult(ResourceLocation item, int count, @Nullable CompoundTag nbt) {
        return this.makeItemResult(item, count, nbt == null ? null : (JsonObject) NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt));
    }

    public JsonObject makeItemResult(ResourceLocation item, int count, @Nullable JsonObject nbt) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("item", item.toString());
        jsonobject.addProperty("count", count);
        if (nbt != null) {
            jsonobject.add("nbt", nbt);
        }
        return jsonobject;
    }

    public JsonObject makeItemStackCodecResult(ResourceLocation item) {
        return this.makeItemStackCodecResult(item, 1);
    }

    public JsonObject makeItemStackCodecResult(ResourceLocation item, int count) {
        return this.makeItemStackCodecResult(item, count, (JsonObject) null);
    }

    public JsonObject makeItemStackCodecResult(ResourceLocation item, int count, @Nullable CompoundTag nbt) {
        return this.makeItemStackCodecResult(item, count, nbt == null ? null : (JsonObject) NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt));
    }

    public JsonObject makeItemStackCodecResult(ResourceLocation item, int count, @Nullable JsonObject nbt) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("id", item.toString());
        jsonobject.addProperty("Count", count);
        if (nbt != null) {
            jsonobject.add("tag", nbt);
        }
        return jsonobject;
    }

    public JsonObject makeTagResult(ResourceLocation item) {
        return this.makeTagResult(item, 1);
    }

    public JsonObject makeTagResult(ResourceLocation item, int count) {
        return this.makeTagResult(item, count, (JsonObject) null);
    }

    public JsonObject makeTagResult(ResourceLocation tag, int count, @Nullable CompoundTag nbt) {
        return this.makeTagResult(tag, count, nbt == null ? null : (JsonObject) NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt));
    }

    public JsonObject makeTagResult(ResourceLocation tag, int count, @Nullable JsonObject nbt) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("tag", tag.toString());
        jsonobject.addProperty("count", count);
        if (nbt != null) {
            jsonobject.add("nbt", nbt);
        }
        return jsonobject;
    }

    public JsonObject makeFluidResult(FluidStack fluidStack) {
        return FluidStack.CODEC.encodeStart(JsonOps.INSTANCE, fluidStack).getOrThrow(false, (s -> {
            throw new IllegalStateException("Failed to encode fluid stack: " + s);
        })).getAsJsonObject();
    }

    public JsonObject makeTagNotEmptyCondition(String tag) {
        var condition = new JsonObject();
        condition.addProperty("type", "forge:not");
        var value = new JsonObject();
        value.addProperty("type", "forge:tag_empty");
        value.addProperty("tag", tag);
        condition.add("value", value);
        return condition;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        Set<ResourceLocation> set = Sets.newHashSet();
        List<CompletableFuture<?>> futures = new ArrayList<>();
        this.recipeConsumer = (id, recipe) -> {
            if (!set.add(id)) {
                throw new IllegalStateException("Duplicate recipe " + id);
            } else {
                futures.add(DataProvider.saveStable(pOutput, recipe, this.recipePathProvider.json(id)));
            }
        };
        this.buildRecipes(this.recipeConsumer);
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    public abstract void buildRecipes(BiConsumer<ResourceLocation, JsonObject> recipeConsumer);
}