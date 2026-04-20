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
import com.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SoulShardItem extends Item {

    public SoulShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        if (pStack.has(DataComponents.ENTITY_DATA)) {
            EntityType<?> type = EntityUtil.entityTypeFromNbt(pStack.get(DataComponents.ENTITY_DATA).getUnsafe());
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip_filled", type.getDescription()));
        } else {
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip_empty"));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.has(DataComponents.ENTITY_DATA) || !stack.has(OccultismDataComponents.SOUL_VALUE))
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);

        if (level instanceof ServerLevel serverLevel) {
            CompoundTag entityData = Objects.requireNonNull(stack.get(DataComponents.ENTITY_DATA)).copyTag();
            Entity tempEntity = EntityUtil.entityTypeFromNbt(entityData).create(level);
            LivingEntity mob = tempEntity instanceof LivingEntity living ? living : null;

            if (mob != null) {
                LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(mob.getLootTable());
                LootParams lootParams = new LootParams.Builder(serverLevel)
                        .withParameter(LootContextParams.THIS_ENTITY, mob)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(player.blockPosition()))
                        .withParameter(LootContextParams.DAMAGE_SOURCE, player.damageSources().generic())
                        .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                        .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, player)
                        .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, player)
                        .create(LootContextParamSets.ENTITY);

                player.getCooldowns().addCooldown(stack.getItem(), 10);
                int luck = player.getLuck() > 99 ? 19 + RandomSource.create().nextInt((int) player.getLuck()/33) :
                        player.getLuck() > 9 ? 9 + (int) (player.getLuck()/10) : (int) player.getLuck();
                for (int i = 0; i < 1 + Math.max(0, luck); i++)
                    lootTable.getRandomItems(lootParams, player.getLootTableSeed(), player::spawnAtLocation);
                if (!player.hasInfiniteMaterials())
                    stack.shrink(1);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

}
