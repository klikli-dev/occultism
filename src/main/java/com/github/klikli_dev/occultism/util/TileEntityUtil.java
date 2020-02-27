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

package com.github.klikli_dev.occultism.util;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityUtil {
    //region Static Methods

    /**
     * Gets the tile entity from the given global position.
     *
     * @param world the world. Returns null if this is a client world and the wrong dimension.
     * @param pos   the global position.
     * @return the tile entity or null.
     */
    public static TileEntity get(World world, GlobalBlockPos pos) {
        if (world.provider.getDimension() == pos.getDimension()) {
            return world.getTileEntity(pos.getPos());
        }
        if (world.isRemote) //can only access other dimensions on the server.
            return null;

        return world.getMinecraftServer().getWorld(pos.getDimension()).getTileEntity(pos.getPos());
    }

    /**
     * Updates the tile entity at the given position (mark dirty & send updates)
     *
     * @param world the world to update
     * @param pos
     */
    public static void updateTile(World world, BlockPos pos) {
        if (world == null || world.isRemote || world.getTileEntity(pos) == null || !world.getChunk(pos).isLoaded()) {
            return;
        }
        WorldServer server = (WorldServer) world;
        for (EntityPlayer p : server.playerEntities) {
            if (p.getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ()) < 32) {
                try {
                    ((EntityPlayerMP) p).connection.sendPacket(world.getTileEntity(pos).getUpdatePacket());
                    world.markChunkDirty(pos, world.getTileEntity(pos));
                } catch (Error e) {
                    Occultism.logger.error("Could not update tile ", e);
                }
            }
        }
    }

    /**
     * Checks all faces of a tile entity for the given capability.
     *
     * @param tileEntity the tile entity to check.
     * @param capability the capability to check for.
     * @return true if the capability is found on any face.
     */
    public static boolean hasCapabilityOnAnySide(TileEntity tileEntity, Capability<?> capability) {
        for (EnumFacing face : EnumFacing.VALUES) {
            if (tileEntity.hasCapability(capability, face))
                return true;
        }
        return false;
    }
    //endregion Static Methods
}
