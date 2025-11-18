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

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.EntityUtil;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MagicLampItem extends Item {

    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");

    public MagicLampItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getHand() != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        Level level = context.getLevel();
        Direction facing = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        if (itemStack.has(DataComponents.ENTITY_DATA)) {
            if (player != null && !level.isClientSide) {
                CompoundTag entityData = Objects.requireNonNull(itemStack.get(DataComponents.ENTITY_DATA)).copyTag();
                itemStack.remove(DataComponents.ENTITY_DATA);
                EntityType<?> type = EntityUtil.entityTypeFromNbt(entityData);
                BlockPos spawnPos = pos.immutable();
                if (!level.getBlockState(spawnPos).getCollisionShape(level, spawnPos).isEmpty())
                    spawnPos = spawnPos.relative(facing);
                entityData.remove("Pos");
                CompoundTag wrapper = new CompoundTag();
                wrapper.put("EntityTag", entityData);
                Entity entity = type.create(level);
                assert entity != null;
                entity.load(entityData);
                entity.absMoveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
                float yaw = player.getYHeadRot() + 180;
                entity.setYRot(yaw);
                entity.setYBodyRot(yaw);
                entity.setYHeadRot(yaw);
                entity.setYRot(yaw);
                entity.setYRot(yaw);
                level.addFreshEntity(entity);
                ItemNBTUtil.setSpiritJob(itemStack, "");
                ItemNBTUtil.setBoundSpiritName(itemStack, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN);
                player.swing(context.getHand());
                player.inventoryMenu.broadcastChanges();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND || !target.isAlive() || target.level().isClientSide)
            return InteractionResult.PASS;

        //Only allow spirit with job owned by the player.
        if (!(target instanceof SpiritEntity spirit && spirit.getJob().isPresent() && spirit.isOwnedBy(player)))
            return InteractionResult.FAIL;

        var entityData = new CompoundTag();
        var id = target.getEncodeId();
        if(id != null)
            entityData.putString("id", id);
        entityData = target.saveWithoutId(entityData);
        stack.set(DataComponents.ENTITY_DATA, CustomData.of(entityData));
        ItemNBTUtil.setBoundSpiritName(stack, target.getName().getString());
        ItemNBTUtil.setSpiritJob(stack, spirit.getJobID());
        player.swing(hand);
        player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
        target.remove(Entity.RemovalReason.DISCARDED);
        player.inventoryMenu.broadcastChanges();
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull String getDescriptionId(ItemStack stack) {
        return stack.has(DataComponents.ENTITY_DATA) ? this.getDescriptionId().replace("empty","filled"):
                this.getDescriptionId();
    }

    protected EntityType<?> getType(ItemStack pStack) {
        CustomData customdata = pStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        return customdata.read(ENTITY_TYPE_FIELD_CODEC).getOrThrow();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        if (pStack.has(DataComponents.ENTITY_DATA)) {
            EntityType<?> type = this.getType(pStack);
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip_filled",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack)), type.getDescription(),
                Component.translatable("job.occultism."+ItemNBTUtil.getSpiritJob(pStack).split(":",2)[1])));
        } else {
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip_empty"));
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return pStack.has(DataComponents.ENTITY_DATA);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (stack.has(DataComponents.ENTITY_DATA) && level.getGameTime() % 20 == 0 && level.getRandom().nextInt(100) == 0 && entity instanceof Player player) {
            player.displayClientMessage(
                    Component.translatable(this.getDescriptionId() + ".spirit_message_" + level.getRandom().nextInt(10),
                            TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))), false);
            if (player.isSleeping())
                player.hurt(player.damageSources().magic(), 1);
        }
    }
}
