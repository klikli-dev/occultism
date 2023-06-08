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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OccultismSounds {

    //region Fields
    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Occultism.MODID);

    public static final RegistryObject<SoundEvent> CHALK = SOUNDS.register("chalk", () -> loadSoundEvent("chalk"));
    public static final RegistryObject<SoundEvent> BRUSH = SOUNDS.register("brush", () -> loadSoundEvent("brush"));
    public static final RegistryObject<SoundEvent> START_RITUAL = SOUNDS.register("start_ritual", () -> loadSoundEvent("start_ritual"));
    public static final RegistryObject<SoundEvent> TUNING_FORK = SOUNDS.register("tuning_fork", () -> loadSoundEvent("tuning_fork"));
    public static final RegistryObject<SoundEvent> CRUNCHING = SOUNDS.register("crunching", () -> loadSoundEvent("crunching"));
    public static final RegistryObject<SoundEvent> POOF = SOUNDS.register("poof", () -> loadSoundEvent("poof"));

    //endregion Fields

    /**
     * Creates the sound event object for the given sound event name, as specified in sounds.json Automatically appends
     * MODID.
     *
     * @param name the sound event name without domain.
     * @return the sound event.
     */
    private static SoundEvent loadSoundEvent(String name) {
        ResourceLocation location = new ResourceLocation(Occultism.MODID, name);
        return SoundEvent.createVariableRangeEvent(location);
    }
}
