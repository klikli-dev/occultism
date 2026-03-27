package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public class OccultismModonomiconConstants {
    public static class Page {
        public static final Identifier SPIRIT_FIRE_RECIPE = new Identifier(Occultism.MODID, "spirit_fire_recipe");
        public static final Identifier SPIRIT_TRADE_RECIPE = new Identifier(Occultism.MODID, "spirit_trade_recipe");
        public static final Identifier RITUAL_RECIPE = new Identifier(Occultism.MODID, "ritual_recipe");
        public static final Identifier BOOK_BINDING_RECIPE = new Identifier(Occultism.MODID, "book_binding_recipe");
    }

    public static class I18n {
        public static final String PREFIX = Occultism.MODID + ".";
        public static final String RITUAL_RECIPE_ITEM_USE = PREFIX + "modonomicon.ritual_recipe.item_to_use";
        public static final String RITUAL_RECIPE_SUMMON = PREFIX + "modonomicon.ritual_recipe.summon";
        public static final String RITUAL_RECIPE_JOB = PREFIX + "modonomicon.ritual_recipe.job";
        public static final String RITUAL_RECIPE_SACRIFICE = PREFIX + "modonomicon.ritual_recipe.sacrifice";
        public static final String RITUAL_RECIPE_GO_TO_PENTACLE = PREFIX + "modonomicon.ritual_recipe.go_to_pentacle";

    }
}
