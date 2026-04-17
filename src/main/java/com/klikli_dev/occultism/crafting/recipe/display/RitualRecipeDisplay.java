/*
 * MIT License
 *
 * Copyright 2024 klikli-dev
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

package com.klikli_dev.occultism.crafting.recipe.display;

import com.klikli_dev.occultism.util.OccultismExtraStreamCodecs;
import com.klikli_dev.occultism.registry.OccultismRecipeDisplays;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay.ItemStackSlotDisplay;

import java.util.List;
import java.util.Optional;

public record RitualRecipeDisplay(
        List<Ingredient> ingredients,
        ItemStackTemplate output,
        ItemStackTemplate ritualDummy,
        SlotDisplay craftingStation,
        SlotDisplay activationItem,
        Optional<SlotDisplay> itemToUse,
        Optional<SlotDisplay> summonEntityDrops,
        Optional<SlotDisplay> randomEntityDrops,
        Identifier pentacleId,
        Optional<Component> summonText,
        Optional<Component> jobText,
        Optional<Component> sacrificeText,
        Optional<Component> conditionText
) implements RecipeDisplay {

    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<SlotDisplay>> OPTIONAL_SLOT_DISPLAY_STREAM_CODEC = ByteBufCodecs.optional(SlotDisplay.STREAM_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<Component>> OPTIONAL_COMPONENT_STREAM_CODEC = ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC);

    public static final MapCodec<RitualRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(RitualRecipeDisplay::ingredients),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(RitualRecipeDisplay::output),
            ItemStackTemplate.CODEC.fieldOf("ritual_dummy").forGetter(RitualRecipeDisplay::ritualDummy),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(RitualRecipeDisplay::craftingStation),
            SlotDisplay.CODEC.fieldOf("activation_item").forGetter(RitualRecipeDisplay::activationItem),
            SlotDisplay.CODEC.optionalFieldOf("item_to_use").forGetter(RitualRecipeDisplay::itemToUse),
            SlotDisplay.CODEC.optionalFieldOf("summon_entity_drops").forGetter(RitualRecipeDisplay::summonEntityDrops),
            SlotDisplay.CODEC.optionalFieldOf("random_entity_drops").forGetter(RitualRecipeDisplay::randomEntityDrops),
            Identifier.CODEC.fieldOf("pentacle_id").forGetter(RitualRecipeDisplay::pentacleId),
            ComponentSerialization.CODEC.optionalFieldOf("summon_text").forGetter(RitualRecipeDisplay::summonText),
            ComponentSerialization.CODEC.optionalFieldOf("job_text").forGetter(RitualRecipeDisplay::jobText),
            ComponentSerialization.CODEC.optionalFieldOf("sacrifice_text").forGetter(RitualRecipeDisplay::sacrificeText),
            ComponentSerialization.CODEC.optionalFieldOf("condition_text").forGetter(RitualRecipeDisplay::conditionText)
    ).apply(instance, RitualRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipeDisplay> STREAM_CODEC = OccultismExtraStreamCodecs.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
            RitualRecipeDisplay::ingredients,
            ItemStackTemplate.STREAM_CODEC,
            RitualRecipeDisplay::output,
            ItemStackTemplate.STREAM_CODEC,
            RitualRecipeDisplay::ritualDummy,
            SlotDisplay.STREAM_CODEC,
            RitualRecipeDisplay::craftingStation,
            SlotDisplay.STREAM_CODEC,
            RitualRecipeDisplay::activationItem,
            OPTIONAL_SLOT_DISPLAY_STREAM_CODEC,
            RitualRecipeDisplay::itemToUse,
            OPTIONAL_SLOT_DISPLAY_STREAM_CODEC,
            RitualRecipeDisplay::summonEntityDrops,
            OPTIONAL_SLOT_DISPLAY_STREAM_CODEC,
            RitualRecipeDisplay::randomEntityDrops,
            Identifier.STREAM_CODEC,
            RitualRecipeDisplay::pentacleId,
            OPTIONAL_COMPONENT_STREAM_CODEC,
            RitualRecipeDisplay::summonText,
            OPTIONAL_COMPONENT_STREAM_CODEC,
            RitualRecipeDisplay::jobText,
            OPTIONAL_COMPONENT_STREAM_CODEC,
            RitualRecipeDisplay::sacrificeText,
            OPTIONAL_COMPONENT_STREAM_CODEC,
            RitualRecipeDisplay::conditionText,
            RitualRecipeDisplay::new
    );

    @Override
    public Type<? extends RecipeDisplay> type() {
        return OccultismRecipeDisplays.RITUAL.get();
    }

    @Override
    public SlotDisplay result() {
        return new ItemStackSlotDisplay(this.output);
    }
}
