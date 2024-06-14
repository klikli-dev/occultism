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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismSounds {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Occultism.MODID);

    public static final Supplier<SoundEvent> CHALK = SOUNDS.register("chalk", () -> loadSoundEvent("chalk"));
    public static final Supplier<SoundEvent> BRUSH = SOUNDS.register("brush", () -> loadSoundEvent("brush"));
    public static final Supplier<SoundEvent> START_RITUAL = SOUNDS.register("start_ritual", () -> loadSoundEvent("start_ritual"));
    public static final Supplier<SoundEvent> TUNING_FORK = SOUNDS.register("tuning_fork", () -> loadSoundEvent("tuning_fork"));
    public static final Supplier<SoundEvent> CRUNCHING = SOUNDS.register("crunching", () -> loadSoundEvent("crunching"));
    public static final Supplier<SoundEvent> POOF = SOUNDS.register("poof", () -> loadSoundEvent("poof"));

    /**
     * Creates the sound event object for the given sound event name, as specified in sounds.json Automatically appends
     * MODID.
     *
     * @param name the sound event name without domain.
     * @return the sound event.
     */
    private static SoundEvent loadSoundEvent(String name) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, name);
        return SoundEvent.createVariableRangeEvent(location);
    }
}
