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

package com.github.klikli_dev.occultism.common.advancement;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RitualTrigger extends AbstractCriterionTrigger<RitualTrigger.Instance> {
    // region Fields
    private static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "ritual");
    // endregion Fields

    // region Initialization
    public RitualTrigger() {
    }
    // endregion Initialization

    // region Overrides
    public ResourceLocation getId() {
        return ID;
    }

    public RitualTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate,
            ConditionArrayParser conditionsParser) {
        return new RitualTrigger.Instance(deserializeRitualPredicate(json));
    }

    private RitualPredicate deserializeRitualPredicate(JsonObject json) {
        if (json.has("ritual_id"))
            return new RitualPredicate(new ResourceLocation(JSONUtils.getString(json, "ritual_id")), null);
        return RitualPredicate.deserialize(json.get("ritual_predicate"));
    }
    // endregion Overrides

    // region Methods
    public void trigger(ServerPlayerEntity player, Ritual ritual) {
        this.triggerListeners(player, (instance) -> instance.test(player, ritual));
    }
    // endregion Methods

    public static class Instance extends CriterionInstance {

        // region Fields
        RitualPredicate ritualPredicate;
        // endregion Fields

        // region Initialization
        public Instance(RitualPredicate ritualPredicate) {
            super(RitualTrigger.ID, EntityPredicate.AndPredicate.ANY_AND);
            this.ritualPredicate = ritualPredicate;
        }
        // endregion Initialization

        // region Overrides
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            jsonobject.add("ritual_predicate", this.ritualPredicate.serialize());
            return jsonobject;
        }
        // endregion Overrides

        // region Methods
        public boolean test(ServerPlayerEntity player, Ritual ritual) {
            return this.ritualPredicate.test(ritual);
        }
        // endregion Methods
    }

    public static class RitualPredicate {

        public static final RitualPredicate ANY = new RitualPredicate(null, null);

        private ResourceLocation ritualId;
        private ResourceLocation ritualFactoryId;

        public RitualPredicate(ResourceLocation ritualId, ResourceLocation ritualFactoryId) {
            this.ritualId = ritualId;
            this.ritualFactoryId = ritualFactoryId;
        }

        public boolean test(Ritual ritual) {
            if (this == ANY)
                return true;
            else if (ritualId != null && !ritualId.equals(ritual.getRecipe().getId()))
                return false;
            else if (ritualFactoryId != null && !ritualFactoryId.equals(ritual.getFactoryID()))
                return false;
            return true;
        }

        public static RitualPredicate deserialize(JsonElement element) {
            if (element == null || element.isJsonNull())
                return ANY;

            ResourceLocation ritualId = null;
            ResourceLocation ritualFactoryId = null;
            JsonObject json = JSONUtils.getJsonObject(element, "ritual_predicate");
            if (json.has("ritual_id"))
                ritualId = new ResourceLocation(JSONUtils.getString(json, "ritual_id"));
            if (json.has("ritual_factory_id"))
                ritualFactoryId = new ResourceLocation(JSONUtils.getString(json, "ritual_factory_id"));
            return new RitualPredicate(ritualId, ritualFactoryId);
        }

        public JsonElement serialize() {
            if (this == ANY)
                return JsonNull.INSTANCE;
            JsonObject json = new JsonObject();
            if (ritualId != null)
                json.addProperty("ritual_id", ritualId.toString());
            if (ritualFactoryId != null)
                json.addProperty("ritual_factory_id", ritualFactoryId.toString());
            return json;
        }
    }

}
