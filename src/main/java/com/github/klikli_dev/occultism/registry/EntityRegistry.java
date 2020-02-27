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
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class EntityRegistry {

    //region Static Methods
    public static <T extends Entity> void registerEntity(RegistryEvent.Register<EntityEntry> event,
                                                         Class<T> entityClass, String name, int id, int eggMainColor,
                                                         int eggSubColor) {
        event.getRegistry()
                .register(setupEntityBuilder(event, entityClass, name, id).egg(eggMainColor, eggSubColor).build());
    }

    public static <T extends Entity> void registerEntity(RegistryEvent.Register<EntityEntry> event,
                                                         Class<? extends Entity> entityClass, String name, int id) {
        event.getRegistry().register(setupEntityBuilder(event, entityClass, name, id).build());
    }

    private static <T extends Entity> EntityEntryBuilder<T> setupEntityBuilder(
            RegistryEvent.Register<EntityEntry> event, Class<T> entityClass, String name, int id) {
        return EntityEntryBuilder.<T>create().entity(entityClass).id(new ResourceLocation(Occultism.MODID, name), id)
                       .name(name).tracker(64, 1, true);
    }
    //endregion Static Methods
}
