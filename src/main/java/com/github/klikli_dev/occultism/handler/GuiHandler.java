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

package com.github.klikli_dev.occultism.handler;

import com.github.klikli_dev.occultism.client.gui.GuiSpirit;
import com.github.klikli_dev.occultism.client.gui.GuiStorageController;
import com.github.klikli_dev.occultism.client.gui.GuiStorageRemote;
import com.github.klikli_dev.occultism.common.container.ContainerSpirit;
import com.github.klikli_dev.occultism.common.container.ContainerStorageController;
import com.github.klikli_dev.occultism.common.container.ContainerStorageRemote;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    //region Overrides
    @Override
    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        GuiID guiID = GuiID.values()[ID];

        BlockPos pos = new BlockPos(x, y, z);
        switch (guiID) {
            case STORAGE_CONTROLLER:
                TileEntityUtil.updateTile(world, pos);
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TileEntityStorageController) {
                    return new ContainerStorageController(player.inventory, (TileEntityStorageController) tileEntity);
                }
                break;
            case STORAGE_REMOTE:
                //hand is encoded in X
                Hand hand = Hand.values()[x];
                return new ContainerStorageRemote(player.inventory, hand);
            case SPIRIT:
                //entity id is encoded in x
                Entity entity = world.getEntityByID(x);
                return new ContainerSpirit((EntitySpirit) entity, player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        GuiID guiID = GuiID.values()[ID];

        BlockPos pos = new BlockPos(x, y, z);
        switch (guiID) {
            case STORAGE_CONTROLLER:
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TileEntityStorageController) {
                    return new GuiStorageController(
                            (ContainerStorageController) this.getServerGuiElement(ID, player, world, x, y, z));
                }
                break;
            case STORAGE_REMOTE:
                //hand is encoded in X
                Hand hand = Hand.values()[x];
                return new GuiStorageRemote(
                        (ContainerStorageRemote) this.getServerGuiElement(ID, player, world, x, y, z));
            case SPIRIT:
                return new GuiSpirit((ContainerSpirit) this.getServerGuiElement(ID, player, world, x, y, z));
        }

        return null;
    }
    //endregion Overrides

    public enum GuiID {
        STORAGE_CONTROLLER,
        STORAGE_REMOTE,
        SPIRIT,
    }
}
