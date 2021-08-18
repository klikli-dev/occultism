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

package com.github.klikli_dev.occultism.common.job;

import com.github.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.github.klikli_dev.occultism.common.entity.ai.DepositItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.ExtractItemsGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.Nullable;

public class TransportItemsJob extends SpiritJob implements MenuProvider {
    //region Fields

    protected DepositItemsGoal depositItemsGoal;
    protected ExtractItemsGoal extractItemsGoal;
    protected OpenDoorGoal openDoorGoal;
    //endregion Fields

    //region Initialization


    public TransportItemsJob(SpiritEntity entity) {
        super(entity);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Component getDisplayName() {
        return this.entity.getDisplayName();
    }

    @Override
    public void init() {
        this.entity.getNavigation().getNodeEvaluator().setCanPassDoors(true);
        ((GroundPathNavigation) this.entity.getNavigation()).setCanOpenDoors(true);
        this.entity.goalSelector.addGoal(3, this.depositItemsGoal = new DepositItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(4, this.extractItemsGoal = new ExtractItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(5, this.openDoorGoal = new OpenDoorGoal(this.entity, true));
    }

    @Override
    public void cleanup() {
        this.entity.getNavigation().getNodeEvaluator().setCanPassDoors(false);
        ((GroundPathNavigation) this.entity.getNavigation()).setCanOpenDoors(false);
        this.entity.goalSelector.removeGoal(this.depositItemsGoal);
        this.entity.goalSelector.removeGoal(this.extractItemsGoal);
        this.entity.goalSelector.removeGoal(this.openDoorGoal);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new SpiritTransporterContainer(id, playerInventory, this.entity);
    }
    //endregion Overrides
}
