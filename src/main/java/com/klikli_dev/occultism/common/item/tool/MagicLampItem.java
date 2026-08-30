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
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class MagicLampItem extends Item {

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
            if (player != null && !level.isClientSide()) {
                // Get entity type directly from TypedEntityData - the "id" field is stripped from the NBT
                // by TypedEntityData.of() since the type is already stored in the TypedEntityData itself
                var typedEntityData = Objects.requireNonNull(itemStack.get(DataComponents.ENTITY_DATA));
                CompoundTag entityData = typedEntityData.copyTagWithoutId();
                EntityType<?> entityType = typedEntityData.type();
                itemStack.remove(DataComponents.ENTITY_DATA);
                BlockPos spawnPos = pos.immutable();
                if (!level.getBlockState(spawnPos).getCollisionShape(level, spawnPos).isEmpty())
                    spawnPos = spawnPos.relative(facing);
                entityData.remove("Pos");
                Entity entity = entityType.create(level, EntitySpawnReason.LOAD);
                assert entity != null;
                entity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), entityData));
                entity.snapTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
                float yaw = player.getYHeadRot() + 180;
                entity.setYRot(yaw);
                entity.setYBodyRot(yaw);
                entity.setYHeadRot(yaw);
                entity.setYRot(yaw);
                entity.setYRot(yaw);
                level.addFreshEntity(entity);
                ItemNBTUtil.setSpiritJob(itemStack, "");
                ItemNBTUtil.setBoundSpiritName(itemStack, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN);
                level.playSound(null, spawnPos, OccultismSounds.SPIRIT_FIRE.get(), SoundSource.NEUTRAL, 1, 1);
                ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD,
                        spawnPos.getX() + 0.5, spawnPos.getY() + 0.1, spawnPos.getZ() + 0.5,
                        15, 0.0, 0.1, 0.0, 0.01);
                player.swing(context.getHand());
                player.inventoryMenu.broadcastChanges();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND || !target.isAlive() || target.level().isClientSide())
            return InteractionResult.PASS;

        //Only one spirit at a time
        if (stack.has(DataComponents.ENTITY_DATA))
            return InteractionResult.FAIL;

        //Only allow spirit with job owned by the player.
        if (!(target instanceof SpiritEntity spirit && spirit.getJob().isPresent() && spirit.isOwnedBy(player)))
            return InteractionResult.FAIL;

        target.level().playSound(null, target.getOnPos(), OccultismSounds.POOF.get(), SoundSource.NEUTRAL, 1, 1);
        ((ServerLevel) target.level()).sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(),
                target.getX(), target.getY() + target.getHitbox().getYsize()*0.8, target.getZ(),
                15, 0.0, 0.0, 0.0, 0.01);
        var tagOutput = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
        target.saveWithoutId(tagOutput);
        var entityData = tagOutput.buildResult();
        stack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(target.getType(), entityData));
        ItemNBTUtil.setBoundSpiritName(stack, target.getName().getString());
        ItemNBTUtil.setSpiritJob(stack, spirit.getJobID());
        player.swing(hand);
        player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
        target.remove(RemovalReason.DISCARDED);
        player.inventoryMenu.broadcastChanges();
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        String id = stack.has(DataComponents.ENTITY_DATA) ? this.getDescriptionId().replace("empty", "filled") :
                this.getDescriptionId();
        return Component.translatable(id);
    }

    protected EntityType<?> getType(ItemStack pStack) {
        var data = pStack.get(DataComponents.ENTITY_DATA);
        if (data == null) return null;
        return data.type();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext,
                                @NotNull TooltipDisplay pTooltipDisplay, @NotNull Consumer<Component> pTooltipComponents,
                                @NotNull TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipComponents, pTooltipFlag);
        if (pStack.has(DataComponents.ENTITY_DATA)) {
            EntityType<?> type = this.getType(pStack);
            pTooltipComponents.accept(Component.translatable(this.getDescriptionId() + ".tooltip_filled",
                    TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack))));
            pTooltipComponents.accept(
                    type.getDescription().copy().append(": ").append(
                    Component.translatable("job." + ItemNBTUtil.getSpiritJob(pStack).replace(":","."))));
        } else {
            pTooltipComponents.accept(Component.translatable(this.getDescriptionId() + ".tooltip_empty"));
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return pStack.has(DataComponents.ENTITY_DATA);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
        if (stack.has(DataComponents.ENTITY_DATA) && level.getGameTime() % 20 == 0 && level.getRandom().nextInt(100) == 0 && entity instanceof Player player) {
            player.sendSystemMessage(
                    Component.translatable(this.getDescriptionId() + ".spirit_message_" + level.getRandom().nextInt(10),
                            TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
            if (player.isSleeping())
                player.hurtServer(level, player.damageSources().magic(), 1);
        }
    }
}
