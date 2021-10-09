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

package com.github.klikli_dev.occultism.crafting.recipe;

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleManager;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RitualRecipe extends ShapelessRecipe {
    public static Serializer SERIALIZER = new Serializer();

    private final ResourceLocation pentacleId;
    private final ResourceLocation ritualType;
    private final ResourceLocation spiritJobType;
    private final Ritual ritual;
    private final ItemStack ritualDummy;
    private final Ingredient activationItem;
    private final Tag<EntityType<?>> entityToSacrifice;
    private final EntityType<?> entityToSummon;
    private final Ingredient itemToUse;
    private final int duration;
    private final int spiritMaxAge;
    private final float durationPerIngredient;
    private final String entityToSacrificeDisplayName;


    public RitualRecipe(ResourceLocation id, String group, ResourceLocation pentacleId, ResourceLocation ritualType, ItemStack ritualDummy,
                        ItemStack result, EntityType<?> entityToSummon, Ingredient activationItem, NonNullList<Ingredient> input, int duration, int spiritMaxAge, ResourceLocation spiritJobType,
                        Tag<EntityType<?>> entityToSacrifice, String entityToSacrificeDisplayName, Ingredient itemToUse) {
        super(id, group, result, input);
        this.entityToSummon = entityToSummon;
        this.pentacleId = pentacleId;
        this.ritualType = ritualType;
        this.ritual = OccultismRituals.RITUAL_FACTORY_REGISTRY.getValue(this.ritualType).create(this);
        this.ritualDummy = ritualDummy;
        this.activationItem = activationItem;
        this.duration = duration;
        this.spiritMaxAge = spiritMaxAge;
        this.spiritJobType = spiritJobType;
        this.durationPerIngredient = this.duration / (float) (this.getIngredients().size() + 1);
        this.entityToSacrifice = entityToSacrifice;
        this.entityToSacrificeDisplayName = entityToSacrificeDisplayName;
        this.itemToUse = itemToUse;
    }

    public ResourceLocation getPentacleId() {
        return this.pentacleId;
    }

    public Pentacle getPentacle() {
        return PentacleManager.get(this.pentacleId);
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

    /**
     * Custom matches method for ritual recipes
     *
     * @param level              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param activationItem     the item used to start the ritual.
     * @return true if the ritual matches, false otherwise.
     */
    public boolean matches(Level level, BlockPos goldenBowlPosition, ItemStack activationItem) {
        return this.ritual.identify(level, goldenBowlPosition, activationItem);
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer pInv) {
        //as we don't have an inventory this is ignored.
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.RITUAL_TYPE.get();
    }

    public Tag<EntityType<?>> getEntityToSacrifice() {
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
        return this.ritual;
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

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RitualRecipe> {
        //region Fields
        private static final ShapelessRecipe.Serializer serializer = new ShapelessRecipe.Serializer();
        //endregion Fields

        //region Overrides
        @Override
        public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ShapelessRecipe recipe = serializer.fromJson(recipeId, json);

            ResourceLocation ritualType = new ResourceLocation(json.get("ritual_type").getAsString());

            EntityType<?> entityToSummon = null;
            if (json.has("entity_to_summon")) {
                entityToSummon = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(GsonHelper.getAsString(json, "entity_to_summon")));
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

            Tag<EntityType<?>> entityToSacrifice = null;
            String entityToSacrificeDisplayName = "";
            if (json.has("entity_to_sacrifice")) {

                entityToSacrifice = SerializationTags.getInstance().getTagOrThrow(Registry.ENTITY_TYPE_REGISTRY,
                        new ResourceLocation(GsonHelper.getAsString(json.getAsJsonObject("entity_to_sacrifice"), "tag")), (rl) -> {
                            return new JsonSyntaxException("Unknown entity tag '" + rl + "'");
                        });

                entityToSacrificeDisplayName = json.getAsJsonObject("entity_to_sacrifice").get("display_name").getAsString();
            }

            Ingredient itemToUse = Ingredient.EMPTY;
            if (json.has("item_to_use")) {
                JsonElement itemToUseElement =
                        GsonHelper.isArrayNode(json, "item_to_use") ? GsonHelper.getAsJsonArray(json,
                                "item_to_use") : GsonHelper.getAsJsonObject(json, "item_to_use");
                itemToUse = Ingredient.fromJson(itemToUseElement);

            }

            return new RitualRecipe(recipe.getId(), recipe.getGroup(), pentacleId, ritualType, ritualDummy,
                    recipe.getResultItem(), entityToSummon, activationItem, recipe.getIngredients(), duration,
                    spiritMaxAge, spiritJobType, entityToSacrifice, entityToSacrificeDisplayName, itemToUse);
        }

        @Override
        public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ShapelessRecipe recipe = serializer.fromNetwork(recipeId, buffer);

            ResourceLocation ritualType = buffer.readResourceLocation();

            EntityType<?> entityToSummon = null;
            if (buffer.readBoolean()) {
                entityToSummon = buffer.readRegistryId();
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

            Tag<EntityType<?>> entityToSacrifice = null;
            String entityToSacrificeDisplayName = "";
            if (buffer.readBoolean()) {
                entityToSacrifice = SerializationTags.getInstance().getTagOrThrow(Registry.ENTITY_TYPE_REGISTRY, buffer.readResourceLocation(), (rl) -> {
                    return new RuntimeException("Unknown entity tag '" + rl + "'");
                });
                entityToSacrificeDisplayName = buffer.readUtf();
            }

            Ingredient itemToUse = Ingredient.EMPTY;
            if (buffer.readBoolean()) {
                itemToUse = Ingredient.fromNetwork(buffer);
            }

            return new RitualRecipe(recipe.getId(), recipe.getGroup(), pentacleId, ritualType, ritualDummy, recipe.getResultItem(), entityToSummon,
                    activationItem, recipe.getIngredients(), duration, spiritMaxAge, spiritJobType, entityToSacrifice, entityToSacrificeDisplayName, itemToUse);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
            serializer.toNetwork(buffer, recipe);

            buffer.writeResourceLocation(recipe.ritualType);

            buffer.writeBoolean(recipe.entityToSummon != null);
            if (recipe.entityToSummon != null)
                buffer.writeRegistryId(recipe.entityToSummon);

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
                buffer.writeResourceLocation(SerializationTags.getInstance().getIdOrThrow(Registry.ENTITY_TYPE_REGISTRY, recipe.entityToSacrifice, () -> {
                    return new RuntimeException("Id not found for tag.");
                }));
                buffer.writeUtf(recipe.entityToSacrificeDisplayName);
            }
            buffer.writeBoolean(recipe.itemToUse != Ingredient.EMPTY);
            if (recipe.itemToUse != Ingredient.EMPTY)
                recipe.itemToUse.toNetwork(buffer);
        }
        //endregion Overrides
    }
}
