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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Optional;
import java.util.UUID;

public class EntityUtil {

    //region Static Methods

    /**
     * Gets the player by given uuid Call on server only!
     *
     * @param uuid the uuid of the player
     * @return Optional containing the player.
     */
    public static Optional<ServerPlayer> getPlayerByUuiDGlobal(UUID uuid) {
        for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
            ServerPlayer player = (ServerPlayer) level.getPlayerByUUID(uuid);
            if (player != null)
                return Optional.of(player);
        }
        return Optional.empty();
    }

    /**
     * Gets the entity by given uuid. Call on server only!
     *
     * @param uuid the uuid of the entity
     * @return Optional containing the entity.
     */
    public static Optional<? extends Entity> getEntityByUuiDGlobal(UUID uuid) {
        return getEntityByUuiDGlobal(ServerLifecycleHooks.getCurrentServer(), uuid);
    }

    /**
     * Gets the entity by given uuid. Call on server only!
     *
     * @param uuid the uuid of the entity
     * @return Optional containing the entity.
     */
    public static Optional<? extends Entity> getEntityByUuiDGlobal(MinecraftServer server, UUID uuid) {
        if (uuid != null && server != null) {
            for (ServerLevel level : server.getAllLevels()) {
                Entity entity = level.getEntity(uuid);
                if (entity != null)
                    return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    /**
     * Creates an entity from the given nbt tag
     *
     * @param level          the level to create the entity in.
     * @param nbtTagCompound the tag compound to create the entity from.
     * @return the entity if successful or null otherwise.
     */
    public static Entity entityFromNBT(Level level, CompoundTag nbtTagCompound) {
        ResourceLocation typeId = new ResourceLocation(nbtTagCompound.getString("id"));

        Entity entity = ForgeRegistries.ENTITY_TYPES.getValue(typeId).create(level);
        entity.deserializeNBT(nbtTagCompound);
        return entity;
    }

    /**
     * Creates an entity type from the given nbt tag
     *
     * @param nbtTagCompound the tag compound to create the entity from.
     * @return the entity type if successful or null otherwise.
     */
    public static EntityType<?> entityTypeFromNbt(CompoundTag nbtTagCompound) {
        ResourceLocation typeId = new ResourceLocation(nbtTagCompound.getString("id"));
        return ForgeRegistries.ENTITY_TYPES.getValue(typeId);
    }
    //endregion Static Methods

}
