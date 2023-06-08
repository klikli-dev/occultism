package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.ResourceLocation;

public class OccultismModonomiconConstants {
    public static class Page {
        public static final ResourceLocation SPIRIT_FIRE_RECIPE = new ResourceLocation(Occultism.MODID, "spirit_fire_recipe");
        public static final ResourceLocation SPIRIT_TRADE_RECIPE = new ResourceLocation(Occultism.MODID, "spirit_trade_recipe");
        public static final ResourceLocation RITUAL_RECIPE = new ResourceLocation(Occultism.MODID, "ritual_recipe");
    }

    public static class I18n{
        public static final String PREFIX = Occultism.MODID + ".";
        public static final String RITUAL_RECIPE_ITEM_USE = PREFIX + "modonomicon.ritual_recipe.item_to_use";
        public static final String RITUAL_RECIPE_SUMMON = PREFIX + "modonomicon.ritual_recipe.summon";
        public static final String RITUAL_RECIPE_JOB = PREFIX + "modonomicon.ritual_recipe.job";
        public static final String RITUAL_RECIPE_SACRIFICE = PREFIX + "modonomicon.ritual_recipe.sacrifice";
        public static final String RITUAL_RECIPE_GO_TO_PENTACLE = PREFIX + "modonomicon.ritual_recipe.go_to_pentacle";

    }
}
