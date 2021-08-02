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

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class RitualTrigger extends SimpleCriterionTrigger<RitualTrigger.Instance> {
    //region Fields
    private final ResourceLocation id;
    //endregion Fields

    //region Initialization
    public RitualTrigger(ResourceLocation id) {
        this.id = id;
    }
    //endregion Initialization

    //region Overrides
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return new RitualTrigger.Instance(this.id, new ResourceLocation(GsonHelper.getAsString(json, "ritual_id")));
    }
    //endregion Overrides

    //region Methods
    public void trigger(ServerPlayer player, Ritual ritual) {
        this.trigger(player, (instance) -> instance.test(player, ritual));
    }

    //endregion Methods

    public static class Instance extends AbstractCriterionTriggerInstance {

        //region Fields
        private final ResourceLocation ritualId;
        //endregion Fields

        //region Initialization
        public Instance(ResourceLocation criterion, ResourceLocation ritualId) {
            super(ritualId, EntityPredicate.Composite.ANY);
            this.ritualId = ritualId;
        }
        //endregion Initialization


        //region Methods
        public boolean test(ServerPlayer player, Ritual ritual) {
            return this.ritualId.equals(ritual.getRegistryName());
        }

        @Override
        public ResourceLocation getCriterion() {
            return ritualId;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext serializationContext) {
            JsonObject jsonobject = super.serializeToJson(serializationContext);
            jsonobject.addProperty("ritual_id", this.ritualId.toString());
            return jsonobject;
        }
        //endregion Methods
    }

}
