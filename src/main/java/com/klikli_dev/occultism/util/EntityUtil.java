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

package com.klikli_dev.occultism.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;
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
     * Creates an entity type from the given nbt tag
     *
     * @param nbtTagCompound the tag compound to create the entity from.
     * @return the entity type if successful or null otherwise.
     */
    public static EntityType<?> entityTypeFromNbt(CompoundTag nbtTagCompound) {
        String idString = nbtTagCompound.getString("id").orElse("");
        Identifier typeId = Identifier.parse(idString);
        return BuiltInRegistries.ENTITY_TYPE.getOptional(typeId).orElse(null);
    }

    public static EntityType<?> getEntityInTag(Level level, TagKey<EntityType<?>> tag) {
        HolderLookup<EntityType<?>> lookup = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);
        HolderSet<EntityType<?>> set = lookup.getOrThrow(tag);
        List<? extends EntityType<?>> list = set.stream().map(Holder::value)
                .filter(type -> type != EntityType.PLAYER).toList();
        return list.get(list.size() == 1 ? 0 : (int) ((System.currentTimeMillis() / 2880) % list.size()));
    }

    public static void renderEntity(PoseStack matrix, LivingEntity pLivingEntity, MultiBufferSource pBuffer, float partialTicks) {
        // TODO: Update to new 26.1 rendering API - the old EntityRenderDispatcher.render() method no longer exists
        // New API uses GuiGraphicsExtractor.entity() with EntityRenderState
        // For now, we skip rendering as this is only used in a few places
    }
    //endregion Static Methods

}
