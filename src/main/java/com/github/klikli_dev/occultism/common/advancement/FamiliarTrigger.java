package com.github.klikli_dev.occultism.common.advancement;

import com.github.klikli_dev.occultism.Occultism;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class FamiliarTrigger extends AbstractCriterionTrigger<FamiliarTrigger.Instance> {

    public enum Type {
        DEER_POOP, CTHULHU_SAD, BAT_EAT, DEVIL_FIRE, GREEDY_ITEM, RARE_VARIANT, PARTY, CAPTURE, DRAGON_NUGGET,
        DRAGON_RIDE, DRAGON_PET, DRAGON_FETCH
    };

    private static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "familiar");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance deserializeTrigger(JsonObject json, AndPredicate entityPredicate,
            ConditionArrayParser conditionsParser) {
        return new Instance(entityPredicate, Type.valueOf(JSONUtils.getString(json, "type")));
    }

    public void trigger(ServerPlayerEntity player, Type type) {
        this.triggerListeners(player, instance -> instance.test(type));
    }

    public void trigger(LivingEntity entity, Type type) {
        if (entity instanceof ServerPlayerEntity)
            this.trigger((ServerPlayerEntity) entity, type);
    }

    public static Instance of(Type type) {
        return new Instance(EntityPredicate.AndPredicate.ANY_AND, type);
    }

    public static class Instance extends CriterionInstance {

        private Type type;

        public Instance(AndPredicate playerCondition, Type type) {
            super(FamiliarTrigger.ID, playerCondition);
            this.type = type;
        }

        public boolean test(Type type) {
            return this.type == type;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject json = super.serialize(conditions);
            json.addProperty("type", type.name());
            return json;
        }

    }
}
