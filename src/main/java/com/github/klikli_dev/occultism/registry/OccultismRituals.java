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
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.ritual.pentacle.DebugPentacle;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import java.util.function.Supplier;

public class OccultismRituals {
    //region Fields
    public static IForgeRegistry<Pentacle> PENTACLE_REGISTRY = RegistryManager.ACTIVE.getRegistry(Pentacle.class);
    public static DeferredRegister<Pentacle> PENTACLES = new DeferredRegister<>(PENTACLE_REGISTRY, Occultism.MODID);

    public static IForgeRegistry<Ritual> RITUAL_REGISTRY = RegistryManager.ACTIVE.getRegistry(Ritual.class);
    public static DeferredRegister<Ritual> RITUALS = new DeferredRegister<>(RITUAL_REGISTRY, Occultism.MODID);

    public static final RegistryObject<DebugPentacle> PENTACLE_DEBUG = PENTACLES.register("debug", () -> new Pentacle.Builder<>(
            DebugPentacle::new).build());

    //endregion Fields
}
