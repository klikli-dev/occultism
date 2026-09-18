package com.klikli_dev.occultism.common.advancement;

import com.klikli_dev.occultism.common.advancement.FamiliarTrigger.TriggerInstance;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public class FamiliarTrigger extends SimpleCriterionTrigger<TriggerInstance> {

    public static Criterion<TriggerInstance> of(Type type) {
        return OccultismAdvancements.FAMILIAR.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(type)));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Type type) {
        this.trigger(player, instance -> instance.matches(type));
    }

    public void trigger(LivingEntity entity, Type type) {
        if (entity instanceof ServerPlayer)
            this.trigger((ServerPlayer) entity, type);
    }

    public enum Type implements StringRepresentable {
        DEER_POOP, CTHULHU_SAD, BAT_EAT, DEVIL_FIRE, GREEDY_ITEM, RARE_VARIANT, PARTY, CAPTURE, DRAGON_NUGGET,
        DRAGON_RIDE, DRAGON_PET, DRAGON_FETCH, BLACKSMITH_UPGRADE, GUARDIAN_ULTIMATE_SACRIFICE, HEADLESS_CTHULHU_HEAD,
        HEADLESS_REBUILT, CHIMERA_RIDE, GOAT_DETACH, SHUB_NIGGURATH_SUMMON, SHUB_CTHULHU_FRIENDS, SHUB_NIGGURATH_SPAWN,
        BEHOLDER_RAY, BEHOLDER_EAT, FAIRY_SAVE, MUMMY_DODGE, BEAVER_WOODCHOP;

        private final String name;

        Type() {
            this.name = this.name().toLowerCase();
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player,
                                  Optional<Type> type) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                                StringRepresentable.fromEnum(Type::values).optionalFieldOf("type")
                                        .forGetter(TriggerInstance::type)
                        )
                        .apply(instance, TriggerInstance::new)
        );

        public boolean matches(Type type) {
            return this.type.isPresent() && this.type.get() == type;
        }
    }
}
