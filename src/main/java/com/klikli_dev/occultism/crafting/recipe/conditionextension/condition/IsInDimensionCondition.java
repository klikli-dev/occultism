package com.klikli_dev.occultism.crafting.recipe.conditionextension.condition;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapper;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class IsInDimensionCondition implements ICondition, ConditionWrapper<IsInDimensionCondition> {
    public static MapCodec<IsInDimensionCondition> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(IsInDimensionCondition::dimension)
                    )
                    .apply(builder, IsInDimensionCondition::new));

    private final ResourceKey<Level> dimension;

    public IsInDimensionCondition(ResourceKey<Level> dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return false;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public ResourceKey<Level> dimension() {
        return this.dimension;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, OccultismConditionContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public IsInDimensionCondition condition() {
        return this;
    }
}
