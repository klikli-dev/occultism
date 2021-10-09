package com.github.klikli_dev.occultism.common.advancement;

import com.github.klikli_dev.occultism.Occultism;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.LivingEntity;

public class FamiliarTrigger extends SimpleCriterionTrigger<FamiliarTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "familiar");

    public static Instance of(Type type) {
        return new Instance(EntityPredicate.Composite.ANY, type);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        return new Instance(pPlayer, Type.valueOf(GsonHelper.getAsString(pJson, "type")));
    }

    public void trigger(ServerPlayer player, Type type) {
        this.trigger(player, instance -> instance.test(type));
    }

    public void trigger(LivingEntity entity, Type type) {
        if (entity instanceof ServerPlayer)
            this.trigger((ServerPlayer) entity, type);
    }

    public enum Type {
        DEER_POOP, CTHULHU_SAD, BAT_EAT, DEVIL_FIRE, GREEDY_ITEM, RARE_VARIANT, PARTY, CAPTURE, DRAGON_NUGGET,
        DRAGON_RIDE, DRAGON_PET, DRAGON_FETCH, BLACKSMITH_UPGRADE
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        private final Type type;

        public Instance(EntityPredicate.Composite entityPredicate, Type type) {
            super(FamiliarTrigger.ID, entityPredicate);
            this.type = type;
        }

        public boolean test(Type type) {
            return this.type == type;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext serializationContext) {
            JsonObject json = super.serializeToJson(serializationContext);
            json.addProperty("type", this.type.name());
            return json;
        }

    }
}
