/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.crafting.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.occultism.common.ritual.Ritual;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismRituals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Iterator;
import java.util.function.Supplier;

public class RitualRecipe implements Recipe<Container> {
    public static Serializer SERIALIZER = new Serializer();

    private final ResourceLocation pentacleId;
    private final ResourceLocation ritualType;
    private final ResourceLocation spiritJobType;
    private final Supplier<Ritual> ritual;
    private final ItemStack ritualDummy;
    private final Ingredient activationItem;
    private final TagKey<EntityType<?>> entityToSacrifice;
    private final EntityType<?> entityToSummon;

    private final CompoundTag entityNbt;
    private final Ingredient itemToUse;
    private final int duration;
    private final int spiritMaxAge;
    private final float durationPerIngredient;
    private final String entityToSacrificeDisplayName;
    private final String command;

    private final ResourceLocation id;

    final ItemStack result;
    final NonNullList<Ingredient> ingredients;

    public RitualRecipe(ResourceLocation id, ResourceLocation pentacleId, ResourceLocation ritualType, ItemStack ritualDummy,
                        ItemStack result, EntityType<?> entityToSummon, CompoundTag entityNbt, Ingredient activationItem, NonNullList<Ingredient> ingredients, int duration, int spiritMaxAge, ResourceLocation spiritJobType,
                        TagKey<EntityType<?>> entityToSacrifice, String entityToSacrificeDisplayName, Ingredient itemToUse, String command) {
        this.id = id;
        this.result = result;
        this.ingredients = ingredients;
        this.entityToSummon = entityToSummon;
        this.entityNbt = entityNbt;
        this.pentacleId = pentacleId;
        this.ritualType = ritualType;
        this.ritual = () -> OccultismRituals.REGISTRY.get().getValue(this.ritualType).create(this);
        this.ritualDummy = ritualDummy;
        this.activationItem = activationItem;
        this.duration = duration;
        this.spiritMaxAge = spiritMaxAge;
        this.spiritJobType = spiritJobType;
        this.durationPerIngredient = this.duration / (float) (this.getIngredients().size() + 1);
        this.entityToSacrifice = entityToSacrifice;
        this.entityToSacrificeDisplayName = entityToSacrificeDisplayName;
        this.itemToUse = itemToUse;
        this.command = command;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public String getCommand() {
        return this.command;
    }

    public CompoundTag getEntityNbt() {
        return this.entityNbt;
    }

    public ResourceLocation getPentacleId() {
        return this.pentacleId;
    }

    public Multiblock getPentacle() {
        return ModonomiconAPI.get().getMultiblock(this.pentacleId);
    }

    public ItemStack getRitualDummy() {
        return this.ritualDummy;
    }

    public Ingredient getActivationItem() {
        return this.activationItem;
    }

    public int getDuration() {
        return this.duration;
    }

    public float getDurationPerIngredient() {
        return this.durationPerIngredient;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(Container pInv, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pInv, RegistryAccess access) {
        //as we don't have an inventory this is ignored.
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    /**
     * Custom matches method for ritual recipes
     *
     * @param level              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param activationItem     the item used to start the ritual.
     * @return true if the ritual matches, false otherwise.
     */
    public boolean matches(Level level, BlockPos goldenBowlPosition, ItemStack activationItem) {
        return this.ritual.get().identify(level, goldenBowlPosition, activationItem);
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.RITUAL_TYPE.get();
    }

    public TagKey<EntityType<?>> getEntityToSacrifice() {
        return this.entityToSacrifice;
    }

    public boolean requiresSacrifice() {
        return this.entityToSacrifice != null;
    }

    public Ingredient getItemToUse() {
        return this.itemToUse;
    }

    public boolean requiresItemUse() {
        return this.itemToUse != Ingredient.EMPTY;
    }

    public EntityType<?> getEntityToSummon() {
        return this.entityToSummon;
    }

    public ResourceLocation getRitualType() {
        return this.ritualType;
    }

    public Ritual getRitual() {
        return this.ritual.get();
    }

    public String getEntityToSacrificeDisplayName() {
        return this.entityToSacrificeDisplayName;
    }

    public ResourceLocation getSpiritJobType() {
        return this.spiritJobType;
    }

    public int getSpiritMaxAge() {
        return this.spiritMaxAge;
    }

    public static class Serializer implements RecipeSerializer<RitualRecipe> {
        private static final ShapelessRecipe.Serializer serializer = new ShapelessRecipe.Serializer();

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> ingredients = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            ResourceLocation ritualType = new ResourceLocation(json.get("ritual_type").getAsString());

            EntityType<?> entityToSummon = null;
            if (json.has("entity_to_summon")) {
                entityToSummon = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(GsonHelper.getAsString(json, "entity_to_summon")));
            }

            CompoundTag entityNbt = null;
            if (json.has("entity_nbt")) {
                entityNbt = CraftingHelper.getNBT(json.get("entity_nbt"));
            }

            JsonElement activationItemElement =
                    GsonHelper.isArrayNode(json, "activation_item") ? GsonHelper.getAsJsonArray(json,
                            "activation_item") : GsonHelper.getAsJsonObject(json, "activation_item");
            Ingredient activationItem = Ingredient.fromJson(activationItemElement);

            ResourceLocation pentacleId = new ResourceLocation(json.get("pentacle_id").getAsString());

            int duration = GsonHelper.getAsInt(json, "duration", 30);
            int spiritMaxAge = GsonHelper.getAsInt(json, "spirit_max_age", -1);

            ResourceLocation spiritJobType = null;
            if (json.has("spirit_job_type")) {
                spiritJobType = new ResourceLocation(json.get("spirit_job_type").getAsString());
            }

            ItemStack ritualDummy = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "ritual_dummy"), true);

            TagKey<EntityType<?>> entityToSacrifice = null;
            String entityToSacrificeDisplayName = "";
            if (json.has("entity_to_sacrifice")) {
                var tagRL = new ResourceLocation(GsonHelper.getAsString(json.getAsJsonObject("entity_to_sacrifice"), "tag"));
                entityToSacrifice = TagKey.create(Registries.ENTITY_TYPE, tagRL);

                entityToSacrificeDisplayName = json.getAsJsonObject("entity_to_sacrifice").get("display_name").getAsString();
            }

            Ingredient itemToUse = Ingredient.EMPTY;
            if (json.has("item_to_use")) {
                JsonElement itemToUseElement =
                        GsonHelper.isArrayNode(json, "item_to_use") ? GsonHelper.getAsJsonArray(json,
                                "item_to_use") : GsonHelper.getAsJsonObject(json, "item_to_use");
                itemToUse = Ingredient.fromJson(itemToUseElement);

            }

            var command = GsonHelper.getAsString(json, "command", null);

            return new RitualRecipe(recipeId, pentacleId, ritualType, ritualDummy,
                    result, entityToSummon, entityNbt, activationItem, ingredients, duration,
                    spiritMaxAge, spiritJobType, entityToSacrifice, entityToSacrificeDisplayName, itemToUse, command);
        }

        @Override
        public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int ingredientCount = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromNetwork(buffer));
            }

            ItemStack result = buffer.readItem();

            ResourceLocation ritualType = buffer.readResourceLocation();

            EntityType<?> entityToSummon = null;
            if (buffer.readBoolean()) {
                entityToSummon = buffer.readRegistryId();
            }

            CompoundTag entityNbt = null;
            if (buffer.readBoolean()) {
                entityNbt = buffer.readNbt();
            }

            ResourceLocation pentacleId = buffer.readResourceLocation();
            int duration = buffer.readVarInt();
            int spiritMaxAge = buffer.readVarInt();

            ResourceLocation spiritJobType = null;
            if (buffer.readBoolean()) {
                spiritJobType = buffer.readResourceLocation();
            }

            ItemStack ritualDummy = buffer.readItem();
            Ingredient activationItem = Ingredient.fromNetwork(buffer);

            TagKey<EntityType<?>> entityToSacrifice = null;
            String entityToSacrificeDisplayName = "";
            if (buffer.readBoolean()) {
                var tagRL = buffer.readResourceLocation();
                entityToSacrifice = TagKey.create(Registries.ENTITY_TYPE, tagRL);
                entityToSacrificeDisplayName = buffer.readUtf();
            }

            Ingredient itemToUse = Ingredient.EMPTY;
            if (buffer.readBoolean()) {
                itemToUse = Ingredient.fromNetwork(buffer);
            }

            String command = buffer.readBoolean() ? buffer.readUtf() : null;

            return new RitualRecipe(recipeId, pentacleId, ritualType, ritualDummy, result, entityToSummon, entityNbt,
                    activationItem, ingredients, duration, spiritMaxAge, spiritJobType, entityToSacrifice, entityToSacrificeDisplayName, itemToUse, command);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());

            for(var ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.result);

            buffer.writeResourceLocation(recipe.ritualType);

            buffer.writeBoolean(recipe.entityToSummon != null);
            if (recipe.entityToSummon != null)
                buffer.writeRegistryId(ForgeRegistries.ENTITY_TYPES, recipe.entityToSummon);

            buffer.writeBoolean(recipe.entityNbt != null);
            if (recipe.entityNbt != null)
                buffer.writeNbt(recipe.entityNbt);

            buffer.writeResourceLocation(recipe.pentacleId);
            buffer.writeVarInt(recipe.duration);
            buffer.writeVarInt(recipe.spiritMaxAge);
            buffer.writeBoolean(recipe.spiritJobType != null);
            if (recipe.spiritJobType != null) {
                buffer.writeResourceLocation(recipe.spiritJobType);
            }
            buffer.writeItem(recipe.ritualDummy);
            recipe.activationItem.toNetwork(buffer);
            buffer.writeBoolean(recipe.entityToSacrifice != null);
            if (recipe.entityToSacrifice != null) {

                buffer.writeResourceLocation(recipe.entityToSacrifice.location());
                buffer.writeUtf(recipe.entityToSacrificeDisplayName);
            }
            buffer.writeBoolean(recipe.itemToUse != Ingredient.EMPTY);
            if (recipe.itemToUse != Ingredient.EMPTY)
                recipe.itemToUse.toNetwork(buffer);

            buffer.writeBoolean(recipe.command != null);
            if (recipe.command != null)
                buffer.writeUtf(recipe.command);
        }
    }
}
