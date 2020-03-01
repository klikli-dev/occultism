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

package com.github.klikli_dev.occultism.common.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.Random;

/**
 * Mixin to support multiple different skins for a creature.
 */
public interface ISkinnedCreatureMixin {
    //region Getter / Setter

    /**
     * @return the entity data manager
     */
    default LivingEntity getEntity() {
        return (LivingEntity) this;
    }

    /**
     * @return the entity data manager
     */
    EntityDataManager getDataManager();

    /**
     * @return the data parameter for the skin type.
     */
    DataParameter<Integer> getDataParameterSkin();

    /**
     * @return the entity rand.
     */
    Random getRNG();

    /**
     * @return the amount of different skin types for this creature
     */
    default int getSkinTypes() {
        return 1;
    }
    //endregion Getter / Setter

    //region Methods
    default void writeSkinToNBT(CompoundNBT tag) {
        EntityDataManager dataManager = this.getDataManager();
        tag.putInt("skin", dataManager.get(this.getDataParameterSkin()));
    }

    default void readSkinFromNBT(CompoundNBT tag) {
        EntityDataManager dataManager = this.getDataManager();
        dataManager.set(this.getDataParameterSkin(), tag.getInt("skin"));
    }

    /**
     * selects a random skin and stores it in the datamanager.
     */
    default void selectRandomSkin() {
        EntityDataManager dataManager = this.getDataManager();
        dataManager.set(this.getDataParameterSkin(), this.getRNG().nextInt(this.getSkinTypes()));
    }

    /**
     * registers the skin data parameter with the data manager.
     */
    default void registerSkinDataParameter() {
        this.getDataManager().register(this.getDataParameterSkin(), 0);
    }
    //endregion Methods
}
