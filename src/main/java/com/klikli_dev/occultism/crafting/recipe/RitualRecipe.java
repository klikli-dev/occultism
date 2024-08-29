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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class RitualRecipe implements Recipe<SingleRecipeInput> {

    public static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("pentacle_id").forGetter((r) -> r.pentacleId),
                    ResourceLocation.CODEC.fieldOf("ritual_type").forGetter((r) -> r.ritualType),
                    ItemStack.STRICT_CODEC.fieldOf("ritual_dummy").forGetter((r) -> r.ritualDummy),
                    ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter((r) -> r.result),
                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().optionalFieldOf("entity_to_summon").forGetter(r -> Optional.ofNullable(r.entityToSummon)),
                    TagKey.codec(Registries.ENTITY_TYPE).optionalFieldOf("entity_tag_to_summon").forGetter(r -> Optional.ofNullable(r.entityTagToSummon)),
                    CompoundTag.CODEC.optionalFieldOf("entity_nbt").forGetter(r -> Optional.ofNullable(r.entityNbt)),
                    Ingredient.CODEC.fieldOf("activation_item").forGetter((r) -> r.activationItem),
                    Ingredient.LIST_CODEC.fieldOf("ingredients").forGetter((r) -> r.ingredients),
                    Codec.INT.optionalFieldOf("duration", 30).forGetter((r) -> r.duration),
                    Codec.INT.optionalFieldOf("spirit_max_age", -1).forGetter((r) -> r.spiritMaxAge),
                    Codec.INT.optionalFieldOf("summon_number", 1).forGetter((r) -> r.summonNumber),
                    ResourceLocation.CODEC.optionalFieldOf("spirit_job_type").forGetter(r -> Optional.ofNullable(r.spiritJobType)),
                    EntityToSacrifice.CODEC.optionalFieldOf("entity_to_sacrifice").forGetter(r -> Optional.ofNullable(r.entityToSacrifice)),
                    Ingredient.CODEC.optionalFieldOf("item_to_use").forGetter(r -> Optional.ofNullable(r.itemToUse)),
                    Codec.STRING.optionalFieldOf("command").forGetter(r -> Optional.ofNullable(r.command))
            ).apply(instance, (pentacleId, ritualType, ritualDummy, result, entityToSummon, entityTagToSummon, entityNbt, activationItem, ingredients, duration, spiritMaxAge, summonNumber, spiritJobType, entityToSacrifice, itemToUse, command) -> new RitualRecipe(pentacleId, ritualType, ritualDummy, result, entityToSummon.orElse(null), entityTagToSummon.orElse(null), entityNbt.orElse(null), activationItem,
                    NonNullList.copyOf(ingredients), duration, spiritMaxAge, summonNumber, spiritJobType.orElse(null), entityToSacrifice.orElse(null), itemToUse.orElse(Ingredient.EMPTY), command.orElse(null)))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = OccultismExtraStreamCodecs.composite(
            ResourceLocation.STREAM_CODEC,
            (r) -> r.pentacleId,
            ResourceLocation.STREAM_CODEC,
            (r) -> r.ritualType,
            ItemStack.STREAM_CODEC,
            (r) -> r.ritualDummy,
            ItemStack.OPTIONAL_STREAM_CODEC,
            (r) -> r.result,
            ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ENTITY_TYPE)),
            (r) -> Optional.ofNullable(r.entityToSummon),
            ByteBufCodecs.optional(OccultismExtraStreamCodecs.tagKey(Registries.ENTITY_TYPE)),
            (r) -> Optional.ofNullable(r.entityTagToSummon),
            ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG),
            (r) -> Optional.ofNullable(r.entityNbt),
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.activationItem,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
            (r) -> r.ingredients,
            ByteBufCodecs.INT,
            (r) -> r.duration,
            ByteBufCodecs.INT,
            (r) -> r.spiritMaxAge,
            ByteBufCodecs.INT,
            (r) -> r.summonNumber,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
            (r) -> Optional.ofNullable(r.spiritJobType),
            ByteBufCodecs.optional(EntityToSacrifice.STREAM_CODEC),
            (r) -> Optional.ofNullable(r.entityToSacrifice),
            ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC),
            (r) -> Optional.ofNullable(r.itemToUse),
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8),
            (r) -> Optional.ofNullable(r.command),
            (pentacleId, ritualType, ritualDummy, result, entityToSummon, entityTagToSummon, entityNbt, activationItem, ingredients, duration, spiritMaxAge, summonNumber, spiritJobType, entityToSacrifice, itemToUse, command) ->
                    new RitualRecipe(pentacleId, ritualType, ritualDummy, result, entityToSummon.orElse(null), entityTagToSummon.orElse(null), entityNbt.orElse(null), activationItem,
                            NonNullList.copyOf(ingredients), duration, spiritMaxAge, summonNumber, spiritJobType.orElse(null), entityToSacrifice.orElse(null), itemToUse.orElse(Ingredient.EMPTY), command.orElse(null))
    );

    public static Serializer SERIALIZER = new Serializer();
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;
    private final ResourceLocation pentacleId;
    private final ResourceLocation ritualType;
    @Nullable
    private final ResourceLocation spiritJobType;
    private final Supplier<Ritual> ritual;
    private final ItemStack ritualDummy;
    private final Ingredient activationItem;
    @Nullable
    private final EntityToSacrifice entityToSacrifice;
    @Nullable
    private final EntityType<?> entityToSummon;
    @Nullable
    private final TagKey<EntityType<?>> entityTagToSummon;
    @Nullable
    private final CompoundTag entityNbt;
    @Nullable
    private final Ingredient itemToUse;
    private final int duration;
    private final int spiritMaxAge;
    private final int summonNumber;
    private final float durationPerIngredient;
    @Nullable
    private final String command;

    public RitualRecipe(ResourceLocation pentacleId, ResourceLocation ritualType, ItemStack ritualDummy,
                        ItemStack result, @Nullable EntityType<?> entityToSummon, @Nullable TagKey<EntityType<?>> entityTagToSummon, @Nullable CompoundTag entityNbt, Ingredient activationItem, NonNullList<Ingredient> ingredients, int duration, int spiritMaxAge, int summonNumber, @Nullable ResourceLocation spiritJobType, @Nullable EntityToSacrifice entityToSacrifice, @Nullable Ingredient itemToUse, @Nullable String command) {
        this.result = result;
        this.ingredients = ingredients;
        this.entityToSummon = entityToSummon;
        this.entityTagToSummon = entityTagToSummon;
        this.entityNbt = entityNbt;
        this.pentacleId = pentacleId;
        this.ritualType = ritualType;
        this.ritual = () -> OccultismRituals.REGISTRY.get(this.ritualType).create(this);
        this.ritualDummy = ritualDummy;
        this.activationItem = activationItem;
        this.duration = duration;
        this.spiritMaxAge = spiritMaxAge;
        this.summonNumber = summonNumber;
        this.spiritJobType = spiritJobType;
        this.durationPerIngredient = this.duration / (float) (this.getIngredients().size() + 1);
        this.entityToSacrifice = entityToSacrifice;
        this.itemToUse = itemToUse;
        this.command = command;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public @Nullable String getCommand() {
        return this.command;
    }

    public @Nullable CompoundTag getEntityNbt() {
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
    public @NotNull RecipeType<?> getType() {
        return OccultismRecipes.RITUAL_TYPE.get();
    }

    public TagKey<EntityType<?>> getEntityToSacrifice() {
        return this.entityToSacrifice.tag();
    }

    public boolean requiresSacrifice() {
        return this.entityToSacrifice != null;
    }

    public @Nullable Ingredient getItemToUse() {
        return this.itemToUse;
    }

    public boolean requiresItemUse() {
        return this.itemToUse != null && !this.itemToUse.isEmpty();
    }

    public @Nullable EntityType<?> getEntityToSummon() {
        return this.entityToSummon;
    }

    public @Nullable TagKey<EntityType<?>> getEntityTagToSummon() {
        return this.entityTagToSummon;
    }

    public ResourceLocation getRitualType() {
        return this.ritualType;
    }

    public Ritual getRitual() {
        return this.ritual.get();
    }

    public String getEntityToSacrificeDisplayName() {
        return this.entityToSacrifice != null ? this.entityToSacrifice.displayName() : "";
    }

    public @Nullable ResourceLocation getSpiritJobType() {
        return this.spiritJobType;
    }

    public int getSpiritMaxAge() {
        return this.spiritMaxAge;
    }

    public int getSummonNumber() {
        return this.summonNumber;
    }

    public record EntityToSacrifice(TagKey<EntityType<?>> tag, String displayName) {
        public static Codec<EntityToSacrifice> CODEC = RecordCodecBuilder.create(instance -> instance.group(
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
