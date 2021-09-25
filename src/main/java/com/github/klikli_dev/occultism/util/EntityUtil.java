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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.UUID;

public class EntityUtil {

    //region Static Methods

    /**
     * Gets the player by given uuid
     * Call on server only!
     *
     * @param uuid the uuid of the player
     * @return Optional containing the player.
     */
    public static Optional<ServerPlayerEntity> getPlayerByUuiDGlobal(UUID uuid) {
        for (ServerWorld world : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
            ServerPlayerEntity player = (ServerPlayerEntity) world.getPlayerByUUID(uuid);
            if (player != null)
                return Optional.of(player);
        }
        return Optional.empty();
    }

    /**
     * Gets the entity by given uuid.
     * Call on server only!
     *
     * @param uuid the uuid of the entity
     * @return Optional containing the entity.
     */
    public static Optional<? extends Entity> getEntityByUuiDGlobal(UUID uuid) {
        return getEntityByUuiDGlobal(ServerLifecycleHooks.getCurrentServer(), uuid);
    }

    /**
     * Gets the entity by given uuid.
     * Call on server only!
     *
     * @param uuid the uuid of the entity
     * @return Optional containing the entity.
     */
    public static Optional<? extends Entity> getEntityByUuiDGlobal(MinecraftServer server, UUID uuid) {
        if (uuid != null && server != null) {
            for (ServerWorld world : server.getAllLevels()) {
                Entity entity = world.getEntity(uuid);
                if (entity != null)
                    return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    /**
     * Creates an entity from the given nbt tag
     *
     * @param world          the world to create the entity in.
     * @param nbtTagCompound the tag compound to create the entity from.
     * @return the entity if successful or null otherwise.
     */
    public static Entity entityFromNBT(World world, CompoundNBT nbtTagCompound) {
        ResourceLocation typeId = new ResourceLocation(nbtTagCompound.getString("id"));

        Entity entity = ForgeRegistries.ENTITIES.getValue(typeId).create(world);
        entity.deserializeNBT(nbtTagCompound);
        return entity;
    }

    /**
     * Creates an entity type from the given nbt tag
     *
     * @param nbtTagCompound the tag compound to create the entity from.
     * @return the entity type if successful or null otherwise.
     */
    public static EntityType<?> entityTypeFromNbt(CompoundNBT nbtTagCompound) {
        ResourceLocation typeId = new ResourceLocation(nbtTagCompound.getString("id"));
        return ForgeRegistries.ENTITIES.getValue(typeId);
    }
    //endregion Static Methods

}
