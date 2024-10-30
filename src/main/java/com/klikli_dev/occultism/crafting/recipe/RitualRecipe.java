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

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.occultism.common.ritual.Ritual;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismRituals;
import com.klikli_dev.occultism.util.OccultismExtraStreamCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class RitualRecipe implements Recipe<SingleRecipeInput> {

    public static final int DEFAULT_DURATION = 30;

    public static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("ritual_type").forGetter((r) -> r.ritualType),
            RitualRequirementSettings.CODEC.forGetter((r) -> r.ritualRequirementSettings),
            RitualStartSettings.CODEC.forGetter((r) -> r.ritualStartSettings),
            EntityToSummonSettings.CODEC.forGetter((r) -> r.entityToSummonSettings),
            ItemStack.STRICT_CODEC.fieldOf("ritual_dummy").forGetter((r) -> r.ritualDummy),
            ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter((r) -> r.result),
            Codec.STRING.optionalFieldOf("command").forGetter(r -> Optional.ofNullable(r.command))
            ).apply(instance, (ritualType, ritualRequirementSettings, ritualStartSettings, entityToSummonSettings, ritualDummy, result, command) ->
                    new RitualRecipe(ritualType, ritualRequirementSettings, ritualStartSettings, entityToSummonSettings, ritualDummy, result, command.orElse(null))
            )
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = OccultismExtraStreamCodecs.composite(
            ResourceLocation.STREAM_CODEC,
            (r) -> r.ritualType,
            RitualRequirementSettings.STREAM_CODEC,
            (r) -> r.ritualRequirementSettings,
            RitualStartSettings.STREAM_CODEC,
            (r) -> r.ritualStartSettings,
            EntityToSummonSettings.STREAM_CODEC,
            (r) -> r.entityToSummonSettings,
            ItemStack.STREAM_CODEC,
            (r) -> r.ritualDummy,
            ItemStack.OPTIONAL_STREAM_CODEC,
            (r) -> r.result,
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8),
            (r) -> Optional.ofNullable(r.command),
            (ritualType, ritualRequirementSettings, ritualStartSettings, entityToSummonSettings, ritualDummy, result, command) ->
                    new RitualRecipe(ritualType, ritualRequirementSettings, ritualStartSettings, entityToSummonSettings, ritualDummy, result, command.orElse(null))
    );

    public static Serializer SERIALIZER = new Serializer();
    private final ResourceLocation ritualType;
    private final RitualRequirementSettings ritualRequirementSettings;
    private final RitualStartSettings ritualStartSettings;
    @Nullable
    private final EntityToSummonSettings entityToSummonSettings;
    private final ItemStack ritualDummy;
    private final ItemStack result;
    @Nullable
    private final Supplier<Ritual> ritual;
    @Nullable
    private final String command;

    public RitualRecipe(ResourceLocation ritualType, RitualRequirementSettings ritualRequirementSettings, RitualStartSettings ritualStartSettings, @Nullable EntityToSummonSettings entityToSummonSettings, ItemStack ritualDummy, ItemStack result, String command) {
        this.ritualType = ritualType;
        this.ritualRequirementSettings = ritualRequirementSettings;
        this.ritualStartSettings = ritualStartSettings;
        this.entityToSummonSettings = entityToSummonSettings != null ?
                entityToSummonSettings : new EntityToSummonSettings(null, null, null, null, -1, 1);
        this.ritualDummy = ritualDummy;
        this.result = result;
        this.ritual = () -> OccultismRituals.REGISTRY.get(this.ritualType).create(this);
        this.command = command;
    }

    public RitualRecipe(ResourceLocation pentacleId, ResourceLocation ritualType, ItemStack ritualDummy,
                        ItemStack result, @Nullable EntityType<?> entityToSummon, @Nullable TagKey<EntityType<?>> entityTagToSummon, @Nullable CompoundTag entityNbt, Ingredient activationItem, NonNullList<Ingredient> ingredients, int duration, int spiritMaxAge, int summonNumber, @Nullable ResourceLocation spiritJobType, @Nullable EntityToSacrifice entityToSacrifice, @Nullable Ingredient itemToUse, @Nullable String command) {
        this(ritualType,
                new RitualRequirementSettings(pentacleId, ingredients, activationItem, duration, duration / (float) (ingredients.size() + 1)),
                new RitualStartSettings(entityToSacrifice, itemToUse, null),
                entityTagToSummon == null && entityToSummon == null ? null :
                        new EntityToSummonSettings(entityToSummon, entityTagToSummon, entityNbt, spiritJobType, spiritMaxAge, summonNumber),
                ritualDummy, result, command);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public @Nullable ICondition getCondition() {
        return this.ritualStartSettings.condition();
    }

    public @Nullable String getCommand() {
        return this.command;
    }

    public @Nullable CompoundTag getEntityNbt() {
        return this.entityToSummonSettings.entityNbt();
    }

    public ResourceLocation getPentacleId() {
        return this.ritualRequirementSettings.pentacleId();
    }

    public Multiblock getPentacle() {
        return ModonomiconAPI.get().getMultiblock(this.ritualRequirementSettings.pentacleId());
    }

    public ItemStack getRitualDummy() {
        return this.ritualDummy;
    }

    public Ingredient getActivationItem() {
        return this.ritualRequirementSettings.activationItem();
    }

    public ItemStack[] getActivationItemStack() {
        return this.ritualRequirementSettings.activationItem.getItems();
    }

    public int getDuration() {
        return this.ritualRequirementSettings.duration();
    }

    public float getDurationPerIngredient() {
        return this.ritualRequirementSettings.durationPerIngredient();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput pInv, @NotNull Level pLevel) {
        return false;
    }


    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput pCraftingContainer, HolderLookup.@NotNull Provider pRegistries) {
        //as we don't have an inventory this is ignored.
        return ItemStack.EMPTY;
    }


    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
        return this.result;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ritualRequirementSettings.ingredients();
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
    public @NotNull RecipeType<?> getType() {
        return OccultismRecipes.RITUAL_TYPE.get();
    }

    public TagKey<EntityType<?>> getEntityToSacrifice() {
        return this.ritualStartSettings.entityToSacrifice().tag();
    }

    public boolean requiresSacrifice() {
        return this.ritualStartSettings.requiresSacrifice();
    }

    public @Nullable Ingredient getItemToUse() {
        return this.ritualStartSettings.itemToUse();
    }

    public boolean requiresItemUse() {
        return this.ritualStartSettings.requiresItemUse();
    }

    public @Nullable EntityType<?> getEntityToSummon() {
        return this.entityToSummonSettings.entityToSummon();
    }

    public @Nullable TagKey<EntityType<?>> getEntityTagToSummon() {
        return this.entityToSummonSettings.entityTagToSummon();
    }

    public ResourceLocation getRitualType() {
        return this.ritualType;
    }

    public Ritual getRitual() {
        return this.ritual.get();
    }

    public String getEntityToSacrificeDisplayName() {
        return this.ritualStartSettings.getEntityToSacrificeDisplayName();
    }

    public @Nullable ResourceLocation getSpiritJobType() {
        return this.entityToSummonSettings.spiritJobType();
    }

    public int getSpiritMaxAge() {
        return this.entityToSummonSettings.spiritMaxAge();
    }

    public int getSummonNumber() {
        return this.entityToSummonSettings.summonNumber();
    }

    public record EntityToSummonSettings(
            @Nullable EntityType<?> entityToSummon,
            @Nullable TagKey<EntityType<?>> entityTagToSummon,
            @Nullable CompoundTag entityNbt,
            @Nullable ResourceLocation spiritJobType,
            int spiritMaxAge,
            int summonNumber
    ) {
        public static MapCodec<EntityToSummonSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        BuiltInRegistries.ENTITY_TYPE.byNameCodec().optionalFieldOf("entity_to_summon").forGetter(r -> Optional.ofNullable(r.entityToSummon)),
                        TagKey.codec(Registries.ENTITY_TYPE).optionalFieldOf("entity_tag_to_summon").forGetter(r -> Optional.ofNullable(r.entityTagToSummon)),
                        CompoundTag.CODEC.optionalFieldOf("entity_nbt").forGetter(r -> Optional.ofNullable(r.entityNbt)),
                        ResourceLocation.CODEC.optionalFieldOf("spirit_job_type").forGetter(r -> Optional.ofNullable(r.spiritJobType)),
                        Codec.INT.optionalFieldOf("spirit_max_age", -1).forGetter(r -> r.spiritMaxAge),
                        Codec.INT.optionalFieldOf("summon_number", 1).forGetter(r -> r.summonNumber)
                ).apply(instance, (entityToSummon, entityTagToSummon, entityNbt, spiritJobType, spiritMaxAge, summonNumber) -> new EntityToSummonSettings(entityToSummon.orElse(null), entityTagToSummon.orElse(null), entityNbt.orElse(null), spiritJobType.orElse(null), spiritMaxAge, summonNumber))
        );

        public static StreamCodec<RegistryFriendlyByteBuf, EntityToSummonSettings> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ENTITY_TYPE)),
                (r) -> Optional.ofNullable(r.entityToSummon),
                ByteBufCodecs.optional(OccultismExtraStreamCodecs.tagKey(Registries.ENTITY_TYPE)),
                (r) -> Optional.ofNullable(r.entityTagToSummon),
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG),
                (r) -> Optional.ofNullable(r.entityNbt),
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
                (r) -> Optional.ofNullable(r.spiritJobType),
                ByteBufCodecs.INT,
                (r) -> r.spiritMaxAge,
                ByteBufCodecs.INT,
                (r) -> r.summonNumber,
                (entityToSummon, entityTagToSummon, entityNbt, spiritJobType, spiritMaxAge, summonNumber) -> new EntityToSummonSettings(entityToSummon.orElse(null), entityTagToSummon.orElse(null), entityNbt.orElse(null), spiritJobType.orElse(null), spiritMaxAge, summonNumber)
        );
    }

    public record RitualStartSettings(
            @Nullable EntityToSacrifice entityToSacrifice,
            @Nullable Ingredient itemToUse,
            @Nullable ICondition condition
    ) {
        public static MapCodec<RitualStartSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        EntityToSacrifice.CODEC.codec().optionalFieldOf("entity_to_sacrifice").forGetter(r -> Optional.ofNullable(r.entityToSacrifice)),
                        Ingredient.CODEC.optionalFieldOf("item_to_use").forGetter(r -> Optional.ofNullable(r.itemToUse)),
                        ICondition.CODEC.optionalFieldOf("condition").forGetter(r -> Optional.ofNullable(r.condition))
                ).apply(instance, (entityToSacrifice, itemToUse, condition) -> new RitualStartSettings(entityToSacrifice.orElse(null), itemToUse.orElse(null), condition.orElse(null)))
        );

        public static StreamCodec<RegistryFriendlyByteBuf, RitualStartSettings> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(EntityToSacrifice.STREAM_CODEC),
                (r) -> Optional.ofNullable(r.entityToSacrifice),
                ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC),
                (r) -> Optional.ofNullable(r.itemToUse),
                //we need conditions on the client for the description visitor, and stream codecs are finnicky with it, so we use an nbt based one.
                ByteBufCodecs.optional(ByteBufCodecs.fromCodecWithRegistries(ICondition.CODEC)),
                (r) -> Optional.ofNullable(r.condition),
                (entityToSacrifice, itemToUse, condition) -> new RitualStartSettings(entityToSacrifice.orElse(null), itemToUse.orElse(null), condition.orElse(null))
        );

        public String getEntityToSacrificeDisplayName() {
            return this.entityToSacrifice != null ? this.entityToSacrifice.displayName() : "";
        }

        public boolean requiresItemUse() {
            return this.itemToUse != null && !this.itemToUse.isEmpty();
        }

        public boolean requiresSacrifice() {
            return this.entityToSacrifice != null;
        }
    }

    public record RitualRequirementSettings(
            ResourceLocation pentacleId,
            NonNullList<Ingredient> ingredients,
            Ingredient activationItem,
            int duration,
            float durationPerIngredient
    ) {
        public static MapCodec<RitualRequirementSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("pentacle_id").forGetter(r -> r.pentacleId),
                        Ingredient.LIST_CODEC.fieldOf("ingredients").forGetter(r -> r.ingredients),
                        Ingredient.CODEC.fieldOf("activation_item").forGetter(r -> r.activationItem),
                        Codec.INT.optionalFieldOf("duration", DEFAULT_DURATION).forGetter(r -> r.duration)
                ).apply(instance, (pentacleId, ingredients, activationItem, duration) -> new RitualRequirementSettings(pentacleId, NonNullList.copyOf(ingredients), activationItem, duration, -1))
        );

        public static StreamCodec<RegistryFriendlyByteBuf, RitualRequirementSettings> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC,
                (r) -> r.pentacleId,
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                (r) -> r.ingredients,
                Ingredient.CONTENTS_STREAM_CODEC,
                (r) -> r.activationItem,
                ByteBufCodecs.INT,
                (r) -> r.duration,
                ByteBufCodecs.FLOAT,
                (r) -> r.durationPerIngredient,
                (pentacleId, ingredients, activationItem, duration, durationPerIngredient) -> new RitualRequirementSettings(pentacleId, NonNullList.copyOf(ingredients), activationItem, duration, durationPerIngredient)
        );

        public RitualRequirementSettings(ResourceLocation pentacleId, NonNullList<Ingredient> ingredients, Ingredient activationItem, int duration, float durationPerIngredient) {
            this.pentacleId = pentacleId;
            this.ingredients = ingredients;
            this.activationItem = activationItem;
            this.duration = duration;
            this.durationPerIngredient = durationPerIngredient == -1 ? duration / (float) (ingredients.size() + 1) : durationPerIngredient;
        }
    }

    public record EntityToSacrifice(TagKey<EntityType<?>> tag, String displayName) {
        public static MapCodec<EntityToSacrifice> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                TagKey.codec(Registries.ENTITY_TYPE).fieldOf("tag").forGetter(EntityToSacrifice::tag),
                Codec.STRING.fieldOf("display_name").forGetter(EntityToSacrifice::displayName)
        ).apply(instance, EntityToSacrifice::new));

        public static StreamCodec<RegistryFriendlyByteBuf, EntityToSacrifice> STREAM_CODEC = StreamCodec.composite(
                OccultismExtraStreamCodecs.tagKey(Registries.ENTITY_TYPE),
                (r) -> r.tag,
                ByteBufCodecs.STRING_UTF8,
                (r) -> r.displayName,
                EntityToSacrifice::new
        );
    }

    public static class Serializer implements RecipeSerializer<RitualRecipe> {

        @Override
        public @NotNull MapCodec<RitualRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
