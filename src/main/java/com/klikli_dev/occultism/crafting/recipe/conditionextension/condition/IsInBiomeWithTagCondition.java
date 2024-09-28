package com.klikli_dev.occultism.crafting.recipe.conditionextension.condition;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapper;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class IsInBiomeWithTagCondition implements ICondition, ConditionWrapper<IsInBiomeWithTagCondition> {
    public static MapCodec<IsInBiomeWithTagCondition> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            TagKey.codec(Registries.BIOME).fieldOf("tag").forGetter(IsInBiomeWithTagCondition::tag)
                    )
                    .apply(builder, IsInBiomeWithTagCondition::new));

    private final TagKey<Biome> tag;

    public IsInBiomeWithTagCondition(TagKey<Biome> tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return false;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public TagKey<Biome> tag() {
        return this.tag;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, OccultismConditionContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public IsInBiomeWithTagCondition condition() {
        return this;
    }
}
