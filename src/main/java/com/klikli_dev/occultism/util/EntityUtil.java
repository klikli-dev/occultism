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

import com.klikli_dev.occultism.Occultism;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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
        //entity data may originate from other mods and does not necessarily contain a valid "id" tag
        if (!nbtTagCompound.contains("id", Tag.TAG_STRING))
            return null;
        ResourceLocation typeId = ResourceLocation.tryParse(nbtTagCompound.getString("id"));
        if (typeId == null || !BuiltInRegistries.ENTITY_TYPE.containsKey(typeId))
            return null;
        return BuiltInRegistries.ENTITY_TYPE.get(typeId);
    }

    /**
     * Entity data written by other mods may lack the mandatory string "id" tag. Vanilla's entity_data
     * component codec requires it, so affected stacks throw on save ("Missing id for entity"), which
     * breaks saving of player data and chunks containing them. This method removes such broken data, so
     * the stack can be saved again. Data that has an id but cannot be resolved to an entity type is left
     * alone, as it does not break saving. Call on server side only.
     *
     * @param stack the stack to clean up.
     * @return true if invalid data was removed.
     */
    public static boolean removeInvalidEntityData(ItemStack stack) {
        CustomData entityData = stack.get(DataComponents.ENTITY_DATA);
        if (entityData != null && !entityData.getUnsafe().contains("id", Tag.TAG_STRING)) {
            Occultism.LOGGER.warn("Removing invalid entity data (missing \"id\" tag) from stack {}, " +
                    "it would otherwise prevent world/player data from saving.", stack);
            stack.remove(DataComponents.ENTITY_DATA);
            return true;
        }
        return false;
    }

    public static EntityType<?> getEntityInTag(Level level, TagKey<EntityType<?>> tag) {
        HolderLookup<EntityType<?>> lookup = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);
        HolderSet<EntityType<?>> set = lookup.getOrThrow(tag);
        List<? extends EntityType<?>> list = set.stream().map(Holder::value)
                .filter(type -> type != EntityType.PLAYER).filter(type -> type.create(level) instanceof LivingEntity).toList();
        return list.get(list.size() == 1 ? 0 : (int) ((System.currentTimeMillis() / 2880) % list.size()));
    }

    public static void renderEntity(PoseStack matrix, LivingEntity pLivingEntity, MultiBufferSource pBuffer, float partialTicks) {
        matrix.pushPose();
        pLivingEntity.setYRot(0);
        pLivingEntity.yBodyRot = pLivingEntity.getYRot();
        pLivingEntity.yHeadRot = pLivingEntity.getYRot();
        pLivingEntity.yHeadRotO = pLivingEntity.getYRot();
        EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
        erd.setRenderShadow(false);
        erd.render(pLivingEntity, 0, 0, 0, 0, partialTicks, matrix, pBuffer, 15728880);
        erd.setRenderShadow(true);
        matrix.popPose();
    }
    //endregion Static Methods

}
