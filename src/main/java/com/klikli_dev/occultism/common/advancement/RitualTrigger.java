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

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.ritual.Ritual;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class RitualTrigger extends SimpleCriterionTrigger<RitualTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "ritual");

    public RitualTrigger() {
    }

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new RitualTrigger.Instance(this.deserializeRitualPredicate(json));
    }

    private RitualPredicate deserializeRitualPredicate(JsonObject json) {
        if (json.has("ritual_id"))
            return new RitualPredicate(new ResourceLocation(GsonHelper.getAsString(json, "ritual_id")), null);
        return RitualPredicate.deserialize(json.get("ritual_predicate"));
    }

    public void trigger(ServerPlayer player, Ritual ritual) {
        this.trigger(player, (instance) -> instance.test(player, ritual));
    }


    public static class Instance extends AbstractCriterionTriggerInstance {

        RitualPredicate ritualPredicate;

        public Instance(RitualPredicate ritualPredicate) {
            super(RitualTrigger.ID, ContextAwarePredicate.ANY);
            this.ritualPredicate = ritualPredicate;
        }

        public boolean test(ServerPlayer player, Ritual ritual) {
            return this.ritualPredicate.test(ritual);
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("ritual_predicate", this.ritualPredicate.serialize());
            return jsonobject;
        }

    }

    public static class RitualPredicate {

        public static final RitualPredicate ANY = new RitualPredicate(null, null);

        private final ResourceLocation ritualId;
        private final ResourceLocation ritualFactoryId;

        public RitualPredicate(ResourceLocation ritualId, ResourceLocation ritualFactoryId) {
            this.ritualId = ritualId;
            this.ritualFactoryId = ritualFactoryId;
        }

        public static RitualPredicate deserialize(JsonElement element) {
            if (element == null || element.isJsonNull())
                return ANY;

            ResourceLocation ritualId = null;
            ResourceLocation ritualFactoryId = null;
            JsonObject json = GsonHelper.convertToJsonObject(element, "ritual_predicate");
            if (json.has("ritual_id"))
                ritualId = new ResourceLocation(GsonHelper.getAsString(json, "ritual_id"));
            if (json.has("ritual_factory_id"))
                ritualFactoryId = new ResourceLocation(GsonHelper.getAsString(json, "ritual_factory_id"));
            return new RitualPredicate(ritualId, ritualFactoryId);
        }

        public boolean test(Ritual ritual) {
            if (this == ANY)
                return true;
            else if (this.ritualId != null && !this.ritualId.equals(ritual.getRecipe().getId()))
                return false;
            else return this.ritualFactoryId == null || this.ritualFactoryId.equals(ritual.getFactoryID());
        }

        public JsonElement serialize() {
            if (this == ANY)
                return JsonNull.INSTANCE;
            JsonObject json = new JsonObject();
            if (this.ritualId != null)
                json.addProperty("ritual_id", this.ritualId.toString());
            if (this.ritualFactoryId != null)
                json.addProperty("ritual_factory_id", this.ritualFactoryId.toString());
            return json;
        }
    }

}
