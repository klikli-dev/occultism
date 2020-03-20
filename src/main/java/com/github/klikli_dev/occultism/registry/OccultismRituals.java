/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.ritual.*;
import com.github.klikli_dev.occultism.common.ritual.pentacle.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class OccultismRituals {
    //region Fields
    public static final IForgeRegistry<Pentacle> PENTACLE_REGISTRY = RegistryManager.ACTIVE.getRegistry(Pentacle.class);
    public static final DeferredRegister<Pentacle> PENTACLES = new DeferredRegister<>(PENTACLE_REGISTRY,
            Occultism.MODID);
    public static final IForgeRegistry<Ritual> RITUAL_REGISTRY = RegistryManager.ACTIVE.getRegistry(Ritual.class);
    public static final DeferredRegister<Ritual> RITUALS = new DeferredRegister<>(RITUAL_REGISTRY, Occultism.MODID);

    //Pentacles
    public static final RegistryObject<DebugPentacle> DEBUG_PENTACLE =
            PENTACLES.register("debug", DebugPentacle::new);
    public static final RegistryObject<SummonFoliotPentacle> SUMMON_FOLIOT_PENTACLE =
            PENTACLES.register("summon_foliot", SummonFoliotPentacle::new);
    public static final RegistryObject<SummonDjinniPentacle> SUMMON_DJINNI_PENTACLE =
            PENTACLES.register("summon_djinni", SummonDjinniPentacle::new);
    public static final RegistryObject<SummonAfritPentacle> SUMMON_AFRIT_PENTACLE =
            PENTACLES.register("summon_afrit", SummonAfritPentacle::new);
    public static final RegistryObject<SummonWildAfritPentacle> SUMMON_WILD_AFRIT_PENTACLE =
            PENTACLES.register("summon_wild_afrit", SummonWildAfritPentacle::new);
    public static final RegistryObject<SummonWildGreaterSpiritPentacle> SUMMON_WILD_GREATER_SPIRIT_PENTACLE =
            PENTACLES.register("summon_wild_greater_spirit", SummonWildGreaterSpiritPentacle::new);
    public static final RegistryObject<CraftFoliotPentacle> CRAFT_FOLIOT_PENTACLE =
            PENTACLES.register("craft_foliot", CraftFoliotPentacle::new);
    public static final RegistryObject<CraftDjinniPentacle> CRAFT_DJINNI_PENTACLE =
            PENTACLES.register("craft_djinni", CraftDjinniPentacle::new);
    public static final RegistryObject<CraftAfritPentacle> CRAFT_AFRIT_PENTACLE =
            PENTACLES.register("craft_afrit", CraftAfritPentacle::new);
    public static final RegistryObject<CraftMaridPentacle> CRAFT_MARID_PENTACLE =
            PENTACLES.register("craft_marid", CraftMaridPentacle::new);
    public static final RegistryObject<PossessFoliotPentacle> POSSESS_FOLIOT_PENTACLE =
            PENTACLES.register("possess_foliot", PossessFoliotPentacle::new);
    public static final RegistryObject<PossessDjinniPentacle> POSSESS_DJINNI_PENTACLE =
            PENTACLES.register("possess_djinni", PossessDjinniPentacle::new);

    //Rituals
    public static final RegistryObject<DebugRitual> DEBUG_RITUAL = RITUALS.register("debug", DebugRitual::new);

    //Summonig
    public static final RegistryObject<SummonFoliotLumberjackRitual> SUMMON_FOLIOT_LUMBERJACK_RITUAL =
            RITUALS.register("summon_foliot_lumberjack", SummonFoliotLumberjackRitual::new);
    public static final RegistryObject<SummonFoliotCrusherRitual> SUMMON_FOLIOT_CRUSHER_RITUAL =
            RITUALS.register("summon_foliot_crusher", SummonFoliotCrusherRitual::new);
    public static final RegistryObject<SummonFoliotOtherstoneTraderRitual> SUMMON_FOLIOT_OTHERSTONE_TRADER_RITUAL =
            RITUALS.register("summon_foliot_otherstone_trader", SummonFoliotOtherstoneTraderRitual::new);
    public static final RegistryObject<SummonFoliotSaplingTraderRitual> SUMMON_FOLIOT_SAPLING_TRADER_RITUAL =
            RITUALS.register("summon_foliot_sapling_trader", SummonFoliotSaplingTraderRitual::new);
    public static final RegistryObject<SummonDjinniManageMachineRitual> SUMMON_DJINNI_MANAGE_MACHINE_RITUAL =
            RITUALS.register("summon_djinni_manage_machine", SummonDjinniManageMachineRitual::new);
    public static final RegistryObject<SummonDjinniClearWeatherRitual> SUMMON_DJINNI_CLEAR_WEATHER_RITUAL =
            RITUALS.register("summon_djinni_clear_weather", SummonDjinniClearWeatherRitual::new);
    public static final RegistryObject<SummonAfritRainWeatherRitual> SUMMON_AFRIT_RAIN_WEATHER_RITUAL =
            RITUALS.register("summon_afrit_rain_weather", SummonAfritRainWeatherRitual::new);
    public static final RegistryObject<SummonAfritThunderWeatherRitual> SUMMON_AFRIT_THUNDER_WEATHER_RITUAL =
            RITUALS.register("summon_afrit_thunder_weather", SummonAfritThunderWeatherRitual::new);
    public static final RegistryObject<SummonWildAfritRitual> SUMMON_WILD_AFRIT_RITUAL =
            RITUALS.register("summon_wild_afrit", SummonWildAfritRitual::new);
    public static final RegistryObject<SummonWildAfritRitual> SUMMON_WILD_HUNT_RITUAL =
            RITUALS.register("summon_wild_hunt", SummonWildAfritRitual::new);
    //Crafting
    public static final RegistryObject<CraftStorageControllerBaseRitual> CRAFT_STORAGE_CONTROLLER_BASE_RITUAL =
            RITUALS.register("craft_storage_controller_base", CraftStorageControllerBaseRitual::new);
    public static final RegistryObject<CraftDimensionalMatrixRitual> CRAFT_DIMENSIONAL_MATRIX_RITUAL =
            RITUALS.register("craft_dimensional_matrix", CraftDimensionalMatrixRitual::new);
    public static final RegistryObject<CraftStableWormholeRitual> CRAFT_STABLE_WORMHOLE_RITUAL =
            RITUALS.register("craft_stable_wormhole", CraftStableWormholeRitual::new);
    public static final RegistryObject<CraftStorageRemoteRitual> CRAFT_STORAGE_REMOTE_RITUAL =
            RITUALS.register("craft_storage_remote", CraftStorageRemoteRitual::new);

    public static final RegistryObject<CraftStabilizerTier1Ritual> CRAFT_STABILIZER_TIER1_RITUAL =
            RITUALS.register("craft_stabilizer_tier1", CraftStabilizerTier1Ritual::new);
    public static final RegistryObject<CraftStabilizerTier2Ritual> CRAFT_STABILIZER_TIER2_RITUAL =
            RITUALS.register("craft_stabilizer_tier2", CraftStabilizerTier2Ritual::new);
    public static final RegistryObject<CraftStabilizerTier3Ritual> CRAFT_STABILIZER_TIER3_RITUAL =
            RITUALS.register("craft_stabilizer_tier3", CraftStabilizerTier3Ritual::new);
    public static final RegistryObject<CraftStabilizerTier4Ritual> CRAFT_STABILIZER_TIER4_RITUAL =
            RITUALS.register("craft_stabilizer_tier4", CraftStabilizerTier4Ritual::new);

    public static final RegistryObject<CraftInfusedLensesRitual> CRAFT_INFUSED_LENSES_RITUAL =
            RITUALS.register("craft_infused_lenses", CraftInfusedLensesRitual::new);
    public static final RegistryObject<CraftInfusedPickaxe> CRAFT_INFUSED_PICKAXE_RITUAL =
            RITUALS.register("craft_infused_pickaxe", CraftInfusedPickaxe::new);
    //Possession
    public static final RegistryObject<PossessEndermiteRitual> POSSESS_ENDERMITE_RITUAL =
            RITUALS.register("possess_endermite", PossessEndermiteRitual::new);
    public static final RegistryObject<PossessSkeletonRitual> POSSESS_SKELETON_RITUAL =
            RITUALS.register("possess_skeleton", PossessSkeletonRitual::new);
    public static final RegistryObject<PossessEndermanRitual> POSSESS_ENDERMAN_RITUAL =
            RITUALS.register("possess_enderman", PossessEndermanRitual::new);
    //endregion Fields
}
