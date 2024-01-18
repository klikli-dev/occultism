/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.common.advancement;

import com.klikli_dev.occultism.common.ritual.Ritual;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public class RitualTrigger extends SimpleCriterionTrigger<RitualTrigger.TriggerInstance> {


    public void trigger(ServerPlayer player, Ritual ritual) {
        this.trigger(player, (instance) -> instance.matches(player, ritual));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
                                  Optional<ResourceLocation> ritualId,
                                  Optional<ResourceLocation> ritualFactoryId) implements SimpleCriterionTrigger.SimpleInstance {

        public static Criterion<RitualTrigger.TriggerInstance> ritualFactory(ResourceLocation ritualFactoryId) {
            return OccultismAdvancements.RITUAL.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.of(ritualFactoryId)));
        }

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player),
                                ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "ritual_id").forGetter(TriggerInstance::ritualId),
                                ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "ritual_factory_id").forGetter(TriggerInstance::ritualFactoryId)
                        )
                        .apply(instance, TriggerInstance::new)
        );

        public boolean matches(ServerPlayer player, Ritual ritual) {
            if (this.ritualId.isPresent() && !this.ritualId.get().equals(ritual.getRecipeHolder(player).id()))
                return false;
            else return this.ritualFactoryId.isEmpty() || this.ritualFactoryId.get().equals(ritual.getFactoryID());
        }

    }
}
