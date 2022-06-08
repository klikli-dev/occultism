package com.github.klikli_dev.occultism.common.level;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.level.modifiers.OreBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OccultismBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Occultism.MODID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> ORE = SERIALIZERS.register("ore", OreBiomeModifier::makeCodec);
}
