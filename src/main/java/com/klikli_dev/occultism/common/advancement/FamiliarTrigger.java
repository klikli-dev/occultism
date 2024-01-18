package com.klikli_dev.occultism.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class FamiliarTrigger extends SimpleCriterionTrigger<FamiliarTrigger.TriggerInstance> {

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
            return name;
        }
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
                                  Optional<Type> type) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player),
                                ExtraCodecs.strictOptionalField(StringRepresentable.fromEnum(Type::values), "type")
                                        .forGetter(TriggerInstance::type)
                        )
                        .apply(instance, TriggerInstance::new)
        );

        public static Criterion<BeeNestDestroyedTrigger.TriggerInstance> destroyedBeeNest(
                Block pBlock, ItemPredicate.Builder pItem, MinMaxBounds.Ints pNumBees
        ) {
            return CriteriaTriggers.BEE_NEST_DESTROYED
                    .createCriterion(
                            new BeeNestDestroyedTrigger.TriggerInstance(
                                    Optional.empty(), Optional.of(pBlock.builtInRegistryHolder()), Optional.of(pItem.build()), pNumBees
                            )
                    );
        }

        public boolean matches(Type type) {
            return this.type.isPresent() && this.type.get() == type;
        }
    }
}
