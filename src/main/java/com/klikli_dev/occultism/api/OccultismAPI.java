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

package com.klikli_dev.occultism.api;

import com.klikli_dev.occultism.common.entity.job.SpiritJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

public class OccultismAPI {

    private static final OccultismAPI instance = new OccultismAPI();

    public static OccultismAPI get() {
        return instance;
    }

    /**
     * Gets the list of items the given entity can pick up, IF that entity is an occultism spirit with a job.
     *
     * @return An empty optional if it is not a spirit entity, or the spirit entity has no job, otherwise the list of items it can pick up.
     */
    public Optional<List<Ingredient>> getItemsToPickUp(Entity entity) {
        if (entity instanceof SpiritEntity spiritEntity) {
            return spiritEntity.getJob().map(SpiritJob::getItemsToPickUp);
        }
        return Optional.empty();
    }

    /**
     * Checks if the given entity can pick up the given item entity, IF that entity is an occultism spirit with a job.
     *
     * @return An empty optional if it is not a spirit entity, or the spirit entity has no job, otherwise a boolean representing whether it can pick up the item.
     */
    public Optional<Boolean> canPickupItem(Entity entity, ItemEntity itemEntity) {
        if (entity instanceof SpiritEntity spiritEntity) {
            return spiritEntity.getJob().map(job -> job.canPickupItem(itemEntity));
        }
        return Optional.empty();
    }
}
