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
import com.github.klikli_dev.occultism.common.entity.possessed.PossessedEndermiteEntity;
import com.github.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismEntities {
    //region Fields
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES,
            Occultism.MODID);

    //TODO: remove once spawn eggs take a supplier.
    public static final NonNullLazy<EntityType<FoliotEntity>> FOLIOT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.create(FoliotEntity::new, EntityClassification.CREATURE)
                                         .size(0.6f, 0.9f)
                                         .build(modLoc("foliot").toString()));
    public static final NonNullLazy<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.create(PossessedEndermiteEntity::new, EntityClassification.MONSTER)
                                         .size(0.4F, 0.3F)
                                         .build(modLoc("possessed_endermite").toString()));

    public static final RegistryObject<EntityType<FoliotEntity>> FOLIOT = ENTITIES.register("foliot", FOLIOT_TYPE::get);
    public static final RegistryObject<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE =
            ENTITIES.register("possessed_endermite", POSSESSED_ENDERMITE_TYPE::get);


    //endregion Fields
}
