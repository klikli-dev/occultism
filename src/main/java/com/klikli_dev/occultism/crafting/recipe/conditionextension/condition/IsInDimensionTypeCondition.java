package com.klikli_dev.occultism.crafting.recipe.conditionextension.condition;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapper;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class IsInDimensionTypeCondition implements ICondition, ConditionWrapper<IsInDimensionTypeCondition> {
    public static MapCodec<IsInDimensionTypeCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    DimensionType.CODEC.fieldOf("dimension_type").forGetter(IsInDimensionTypeCondition::dimensionType)
            )
            .apply(builder, IsInDimensionTypeCondition::new));

    private final Holder<DimensionType> dimensionType;

    public IsInDimensionTypeCondition(Holder<DimensionType> dimensionType) {
        this.dimensionType = dimensionType;
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return false;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public Holder<DimensionType> dimensionType() {
        return this.dimensionType;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, OccultismConditionContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public IsInDimensionTypeCondition condition() {
        return this;
    }
}
