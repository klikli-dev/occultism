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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityUtil {

    //region Static Methods

    /**
     * Gets the player by given uuid
     *
     * @param uuid the uuid of the player
     * @return the player or null if not found.
     */
    public static EntityPlayer getPlayerByUUID(UUID uuid) {
        for (WorldServer ws : DimensionManager.getWorlds()) {
            EntityPlayer player = ws.getPlayerEntityByUUID(uuid);
            if (player != null)
                return player;
        }
        return null;
    }

    /**
     * Generates an NBT tag that contains the entity.
     *
     * @param entity         the entity to get the nbt for
     * @param nbtTagCompound the compound to store the entity in. Null to create a new.
     * @return
     */
    public static NBTTagCompound entityToNBT(EntityLivingBase entity, @Nullable NBTTagCompound nbtTagCompound) {
        NBTTagCompound data = (nbtTagCompound != null) ? nbtTagCompound : new NBTTagCompound();
        data.setString("entityId", entity.getUniqueID().toString());
        if (entity.hasCustomName())
            data.setString("customName", entity.getCustomNameTag());
        data.setTag("entity", entity.serializeNBT());
        return data;
    }

    /**
     * Creates an entity from the given nbt tag
     *
     * @param world          the world to create the entity in.
     * @param nbtTagCompound the tag compound to create the entity from.
     * @return the entity if successful or null otherwise.
     */
    public static Entity entityFromNBT(World world, NBTTagCompound nbtTagCompound) {
        Entity entity = EntityList.createEntityFromNBT(nbtTagCompound.getCompoundTag("entity"), world);
        entity.setUniqueId(UUID.fromString(nbtTagCompound.getString("entityId")));
        if (nbtTagCompound.hasKey("customName"))
            entity.setCustomNameTag(nbtTagCompound.getString("customName"));
        return entity;
    }
    //endregion Static Methods

}
