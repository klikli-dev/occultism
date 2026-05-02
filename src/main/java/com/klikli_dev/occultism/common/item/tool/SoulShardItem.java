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

package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class SoulShardItem extends Item {

    public SoulShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pTooltipFlag);
        if (pStack.has(DataComponents.ENTITY_DATA)) {
            // Get entity type directly from TypedEntityData - the "id" field is stripped from the NBT
            // by TypedEntityData.of() since the type is already stored in the TypedEntityData itself
            EntityType<?> type = pStack.get(DataComponents.ENTITY_DATA).type();
            pTooltipAdder.accept(Component.translatable(this.getDescriptionId() + ".tooltip_filled", type.getDescription()));
        } else {
            pTooltipAdder.accept(Component.translatable(this.getDescriptionId() + ".tooltip_empty"));
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.has(DataComponents.ENTITY_DATA) || !stack.has(OccultismDataComponents.SOUL_VALUE))
            return InteractionResult.PASS;

        if (level instanceof ServerLevel serverLevel) {
            // Get entity type directly from TypedEntityData - the "id" field is stripped from the NBT
            // by TypedEntityData.of() since the type is already stored in the TypedEntityData itself
            var typedEntityData = Objects.requireNonNull(stack.get(DataComponents.ENTITY_DATA));
            CompoundTag entityData = typedEntityData.copyTagWithoutId();
            EntityType<?> entityType = typedEntityData.type();
            Entity tempEntity = entityType.create(level, EntitySpawnReason.MOB_SUMMONED);
            LivingEntity mob = tempEntity instanceof LivingEntity living ? living : null;

            if (mob != null) {
                LootTable lootTable = mob.getLootTable()
                        .map(key -> level.getServer().reloadableRegistries().getLootTable(key))
                        .orElse(LootTable.EMPTY);
                LootParams lootParams = new Builder(serverLevel)
                        .withParameter(LootContextParams.THIS_ENTITY, mob)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(player.blockPosition()))
                        .withParameter(LootContextParams.DAMAGE_SOURCE, player.damageSources().generic())
                        .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                        .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, player)
                        .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, player)
                        .create(LootContextParamSets.ENTITY);

                player.getCooldowns().addCooldown(stack, 10);
                int pLuck = (int) player.getLuck();
                int extraRolls = pLuck > 99 ? 19 + level.getRandom().nextInt(pLuck / 33) :
                        pLuck > 9 ? 9 + (pLuck / 10) : pLuck < 1 ? 0 : pLuck;
                for (int i = 0; i < 1 + extraRolls; i++)
                    lootTable.getRandomItems(lootParams, player.getLootTableSeed(), stack2 -> player.spawnAtLocation(serverLevel, stack2));
                if (!player.hasInfiniteMaterials())
                    stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

}
