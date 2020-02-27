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
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleCraftDjinni;
import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleDebug;
import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleSummonFoliotBasic;
import net.minecraft.util.ResourceLocation;

public class RitualRegistry {

    //region Fields
    public static Pentacle PENTACLE_DEBUG = new PentacleDebug();
    public static Pentacle PENTACLE_SUMMON_FOLIOT_BASIC = new PentacleSummonFoliotBasic();
    public static Pentacle PENTACLE_CRAFT_DJINNI = new PentacleCraftDjinni();

    public static Ritual RITUAL_DEBUG = new RitualDebug();
    public static Ritual RITUAL_SUMMON_FOLIOT_LUMBERJACK = new RitualSummonFoliotLumberjack();
    public static Ritual RITUAL_SUMMON_FOLIOT_MANAGE_MACHINE = new RitualSummonFoliotManageMachine();
    public static Ritual RITUAL_SUMMON_FOLIOT_SAPLING_TRADER = new RitualSummonFoliotSaplingTrader();
    public static Ritual RITUAL_SUMMON_FOLIOT_OTHERSTONE_TRADER = new RitualSummonFoliotOtherstoneTrader();

    public static Ritual RITUAL_CRAFT_DIMENSIONAL_MATRIX = new RitualCraftDimensionalMatrix();
    //endregion Fields

    //region Static Methods
    public static <T extends Pentacle> T registerPentacle(T pentacle, String name) {
        ResourceLocation location = new ResourceLocation(Occultism.MODID, name);
        pentacle.setRegistryName(location);
        return pentacle;
    }

    public static <T extends Ritual> T registerRitual(T ritual, String name) {
        ResourceLocation location = new ResourceLocation(Occultism.MODID, name);
        ritual.setRegistryName(name);
        return ritual;
    }
    //endregion Static Methods
}
