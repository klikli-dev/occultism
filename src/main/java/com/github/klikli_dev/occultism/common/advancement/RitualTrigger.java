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
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RitualTrigger extends AbstractCriterionTrigger<RitualTrigger.Instance> {
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

    public RitualTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate,
                                                     ConditionArrayParser conditionsParser) {
        return new RitualTrigger.Instance(this.id, new ResourceLocation(JSONUtils.getString(json, "ritual_id")));
    }
    //endregion Overrides

    //region Methods
    public void trigger(ServerPlayer player, Ritual ritual) {
        this.triggerListeners(player, (instance) -> instance.test(player, ritual));
    }
    //endregion Methods

    public static class Instance extends CriterionInstance {

        //region Fields
        private final ResourceLocation ritualId;
        //endregion Fields

        //region Initialization
        public Instance(ResourceLocation criterion, ResourceLocation ritualId) {
            super(criterion, EntityPredicate.AndPredicate.ANY_AND);
            this.ritualId = ritualId;
        }
        //endregion Initialization

        //region Overrides
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            jsonobject.addProperty("ritual_id", this.ritualId.toString());
            return jsonobject;
        }
        //endregion Overrides

        //region Methods
        public boolean test(ServerPlayer player, Ritual ritual) {
            return this.ritualId.equals(ritual.getRegistryName());
        }
        //endregion Methods
    }

}
