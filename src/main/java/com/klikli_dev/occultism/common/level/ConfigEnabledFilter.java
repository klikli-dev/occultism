/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.level;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.config.OccultismServerConfig.WorldGenSettings;
import com.klikli_dev.occultism.registry.OccultismFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;

import java.util.function.Function;

/**
 * Only places a feature if it is enabled in the world generation section of the server config.
 * The config is checked during chunk generation, so changes apply to newly generated chunks without a restart.
 */
public class ConfigEnabledFilter extends PlacementFilter {

    public static final MapCodec<ConfigEnabledFilter> CODEC = Setting.CODEC.fieldOf("setting")
            .xmap(ConfigEnabledFilter::new, filter -> filter.setting);

    private final Setting setting;

    private ConfigEnabledFilter(Setting setting) {
        this.setting = setting;
    }

    public static ConfigEnabledFilter of(Setting setting) {
        return new ConfigEnabledFilter(setting);
    }

    public Setting getSetting() {
        return this.setting;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        //the server config is loaded before chunks generate, but guard against it being unavailable (e.g. during datagen)
        if (!Occultism.SERVER_CONFIG.spec.isLoaded())
            return true;
        return this.setting.getValue(Occultism.SERVER_CONFIG.worldGen).get();
    }

    @Override
    public PlacementModifierType<?> type() {
        return OccultismFeatures.CONFIG_ENABLED_FILTER.get();
    }

    public enum Setting implements StringRepresentable {
        SILVER_ORE("silver_ore", settings -> settings.enableSilverOre),
        DEEPSLATE_SILVER_ORE("deepslate_silver_ore", settings -> settings.enableDeepslateSilverOre),
        IESNIUM_ORE("iesnium_ore", settings -> settings.enableIesniumOre),
        UNDERGROUND_GROVE("underground_grove", settings -> settings.enableUndergroundGrove),
        OTHERWORLD_TREES("otherworld_trees", settings -> settings.enableOtherworldTrees);

        public static final Codec<Setting> CODEC = StringRepresentable.fromEnum(Setting::values);

        private final String name;
        private final Function<WorldGenSettings, BooleanValue> value;

        Setting(String name, Function<WorldGenSettings, BooleanValue> value) {
            this.name = name;
            this.value = value;
        }

        public BooleanValue getValue(WorldGenSettings settings) {
            return this.value.apply(settings);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
