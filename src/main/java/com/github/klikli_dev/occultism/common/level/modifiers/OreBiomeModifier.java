package com.github.klikli_dev.occultism.common.level.modifiers;

import com.github.klikli_dev.occultism.Occultism;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OreBiomeModifier implements BiomeModifier {

    public static final ResourceLocation RL = new ResourceLocation(Occultism.MODID, "ore");
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(RL, ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Occultism.MODID);

    private HolderSet<PlacedFeature> features;

    public OreBiomeModifier(HolderSet<PlacedFeature> features) {
        this.features = features;
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
        if (phase == Phase.ADD)
        {
            var generation = builder.getGenerationSettings();
            this.features.forEach(holder -> generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, holder));
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec()
    {
        return SERIALIZER.get();
    }

    public static Codec<OreBiomeModifier > makeCodec()
    {
        return RecordCodecBuilder.create(builder -> builder.group(
                PlacedFeature.LIST_CODEC.fieldOf("features").forGetter((m) -> m.features)
        ).apply(builder, OreBiomeModifier::new));
    }

}
