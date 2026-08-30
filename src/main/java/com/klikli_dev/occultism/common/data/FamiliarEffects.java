package com.klikli_dev.occultism.common.data;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class FamiliarEffects {
    public static final byte DISABLED = -1;
    public static final byte ZERO = 0;
    private static final Map<EntityType<?>, ImmutableList<FamiliarEffectDefinition>> EFFECT_MAP = createEffectMap();

    public record FamiliarEffectDefinition(
            Holder<MobEffect> effect,
            byte normalValue,
            byte upgradedValue,
            byte iesniumValue
    ) {
        public int getValue(IFamiliar familiar) {
            return familiar.hasIesniumUpgrade() ? iesniumValue() :
                    familiar.hasBlacksmithUpgrade() ? upgradedValue() : normalValue();
        }
    }


    public static Map<EntityType<?>, ImmutableList<FamiliarEffectDefinition>> effectMap() {
        return EFFECT_MAP;
    }

    private static Map<EntityType<?>, ImmutableList<FamiliarEffectDefinition>> createEffectMap() {
        Map<EntityType<?>, ImmutableList<FamiliarEffectDefinition>> map = new HashMap<>();
        map.put(OccultismEntities.BAT_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(MobEffects.NIGHT_VISION, ZERO, ZERO, ZERO),
                new FamiliarEffectDefinition(OccultismEffects.BAT_LIFESTEAL, DISABLED, ZERO, (byte) 1),
                new FamiliarEffectDefinition(OccultismEffects.BAT_FLIGHT, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.BEAVER_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.BEAVER_HARVEST, ZERO, (byte) 1, (byte) 2)
        ));
        map.put(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.HERALD_ABERRATIONS, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(MobEffects.WATER_BREATHING, ZERO, ZERO, ZERO),
                new FamiliarEffectDefinition(MobEffects.DOLPHINS_GRACE, DISABLED, ZERO, (byte) 1),
                new FamiliarEffectDefinition(MobEffects.CONDUIT_POWER, DISABLED, DISABLED, ZERO),
                new FamiliarEffectDefinition(OccultismEffects.AQUATIC_LORD, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.DEER_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(MobEffects.SPEED, ZERO, (byte) 1, (byte) 2),
                new FamiliarEffectDefinition(OccultismEffects.STEP_HEIGHT, (byte) 3, (byte) 5, (byte) 8)
        ));
        map.put(OccultismEntities.DEVIL_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(MobEffects.FIRE_RESISTANCE, ZERO, ZERO, ZERO),
                new FamiliarEffectDefinition(OccultismEffects.NETHER_EMPEROR, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.DRAGON_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.DRAGON_GREED, ZERO, (byte) 1, (byte) 2),
                new FamiliarEffectDefinition(MobEffects.HERO_OF_THE_VILLAGE, DISABLED, DISABLED, (byte) 11)
        ));
        map.put(OccultismEntities.FAIRY_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(MobEffects.REGENERATION, DISABLED, ZERO, (byte) 1),
                new FamiliarEffectDefinition(OccultismEffects.FAIRY_BLESS, DISABLED, DISABLED, (byte) 4)
        ));
        map.put(OccultismEntities.GREEDY_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.GREEDY_HARVEST, DISABLED, ZERO, (byte) 3)
        ));
        map.put(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(MobEffects.HEALTH_BOOST, DISABLED, ZERO, ZERO),
                new FamiliarEffectDefinition(MobEffects.RESISTANCE, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.PUMPKIN_HEAD, DISABLED, ZERO, ZERO),
                new FamiliarEffectDefinition(MobEffects.STRENGTH, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.MUMMY_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.MUMMY_DODGE, ZERO, (byte) 1, (byte) 3)
        ));
        map.put(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.FOREST_WHISPERER, DISABLED, DISABLED, ZERO)
        ));
        map.put(OccultismEntities.DRIKWING_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.DOUBLE_JUMP, (byte) 4, (byte) 9, (byte) 14),
                new FamiliarEffectDefinition(MobEffects.JUMP_BOOST, (byte) 3, (byte) 6, (byte) 9),
                new FamiliarEffectDefinition(MobEffects.SLOW_FALLING, ZERO, ZERO, ZERO)
        ));
        map.put(OccultismEntities.WINGNIS_FAMILIAR_TYPE.get(), ImmutableList.of(
                new FamiliarEffectDefinition(OccultismEffects.FIRE_WING, DISABLED, ZERO, ZERO),
                new FamiliarEffectDefinition(OccultismEffects.DOUBLE_JUMP, (byte) 7, (byte) 13, (byte) 19),
                new FamiliarEffectDefinition(MobEffects.JUMP_BOOST, (byte) 4, (byte) 8, (byte) 12),
                new FamiliarEffectDefinition(MobEffects.SLOW_FALLING, ZERO, ZERO, ZERO),
                new FamiliarEffectDefinition(MobEffects.GLOWING, ZERO, ZERO, ZERO)
        ));
        return map;
    }
}
