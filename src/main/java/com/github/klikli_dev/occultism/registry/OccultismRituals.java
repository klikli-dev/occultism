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
    public static final RegistryObject<PentacleSummonFoliotBasic> SUMMON_FOLIOT_BASIC_PENTACLE =
            PENTACLES.register("summon_foliot_basic", PentacleSummonFoliotBasic::new);
    public static final RegistryObject<PentacleCraftFoliot> CRAFT_FOLIOT_PENTACLE =
            PENTACLES.register("craft_foliot", PentacleCraftFoliot::new);
    public static final RegistryObject<PentacleCraftDjinni> CRAFT_DJINNI_PENTACLE =
            PENTACLES.register("craft_djinni", PentacleCraftDjinni::new);

    //Rituals
    public static final RegistryObject<DebugRitual> DEBUG_RITUAL = RITUALS.register("debug", DebugRitual::new);

    //Summonig
    public static final RegistryObject<SummonFoliotLumberjackRitual> SUMMON_FOLIOT_LUMBERJACK_RITUAL =
            RITUALS.register("summon_foliot_lumberjack", SummonFoliotLumberjackRitual::new);
    public static final RegistryObject<SummonFoliotLumberjackRitual> SUMMON_FOLIOT_CRUSHER_RITUAL =
            RITUALS.register("summon_foliot_crusher", SummonFoliotLumberjackRitual::new);
    public static final RegistryObject<SummonFoliotManageMachineRitual> SUMMON_FOLIOT_MANAGE_MACHINE_RITUAL =
            RITUALS.register("summon_foliot_manage_machine", SummonFoliotManageMachineRitual::new);
    public static final RegistryObject<SummonFoliotOtherstoneTraderRitual> SUMMON_FOLIOT_OTHERSTONE_TRADER_RITUAL =
            RITUALS.register("summon_foliot_otherstone_trader", SummonFoliotOtherstoneTraderRitual::new);
    public static final RegistryObject<SummonFoliotSaplingTraderRitual> SUMMON_FOLIOT_SAPLING_TRADER_RITUAL =
            RITUALS.register("summon_foliot_sapling_trader", SummonFoliotSaplingTraderRitual::new);

    //Crafting
    public static final RegistryObject<CraftStorageControllerBaseRitual> CRAFT_STORAGE_CONTROLLER_BASE_RITUAL =
            RITUALS.register("craft_storage_controller_base", CraftStorageControllerBaseRitual::new);
    public static final RegistryObject<CraftDimensionalMatrixRitual> CRAFT_DIMENSIONAL_MATRIX_RITUAL =
            RITUALS.register("craft_dimensional_matrix", CraftDimensionalMatrixRitual::new);
    public static final RegistryObject<CraftStableWormholeRitual> CRAFT_STABLE_WORMHOLE_RITUAL =
            RITUALS.register("craft_stable_wormhole", CraftStableWormholeRitual::new);
    public static final RegistryObject<CraftStorageRemoteRitual> CRAFT_STORAGE_REMOTE_RITUAL =
            RITUALS.register("craft_storage_remote", CraftStorageRemoteRitual::new);
    //endregion Fields
}
