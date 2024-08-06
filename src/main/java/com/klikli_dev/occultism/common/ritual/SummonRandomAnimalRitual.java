package com.klikli_dev.occultism.common.ritual;

import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.stream.StreamSupport;

public class SummonRandomAnimalRitual extends SummonRitual{
    public SummonRandomAnimalRitual(RitualRecipe recipe) {
        super(recipe, false);
    }

    @Override
    protected EntityType<?> getEntityToSummon(Level level) {
        var options = StreamSupport.stream(BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(OccultismTags.Entities.RANDOM_ANIMALS_TO_SUMMON_LIST).spliterator(), false).toList();

        if (options.isEmpty()) {
            return null;
        }

        int index = level.random.nextInt(options.size());
        return options.get(index).value();
    }
}
