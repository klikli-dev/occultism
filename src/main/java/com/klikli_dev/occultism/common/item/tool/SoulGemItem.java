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

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class SoulGemItem extends Item {

    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\\\\n|\\R", Pattern.MULTILINE);

    public SoulGemItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        if (context.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        Level level = context.getLevel();
        Direction facing = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        if (itemStack.has(DataComponents.ENTITY_DATA)) {
            //whenever we have an entity stored we can do nothing but release it
            if (!level.isClientSide()) {
                // Get entity type directly from TypedEntityData - the "id" field is stripped from the NBT
                // by TypedEntityData.of() since the type is already stored in the TypedEntityData itself
                var typedEntityData = itemStack.get(DataComponents.ENTITY_DATA);
                CompoundTag entityData = typedEntityData.copyTagWithoutId();
                EntityType<?> entityType = typedEntityData.type();
                itemStack.remove(DataComponents.ENTITY_DATA); //delete entity from item right away to avoid duplicate in case of unexpected error

                facing = facing == null ? Direction.UP : facing;

                BlockPos spawnPos = pos.immutable();
                if (!level.getBlockState(spawnPos).getCollisionShape(level, spawnPos).isEmpty()) {
                    spawnPos = spawnPos.relative(facing);
                }

                //remove position from tag to allow the entity to spawn where it should be
                entityData.remove("Pos");

                Entity entity = entityType.create(level, EntitySpawnReason.MOB_SUMMONED);
                entity.load(TagValueInput.create(ProblemReporter.DISCARDING, entity.registryAccess(), entityData));
                entity.snapTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
                float yaw = player.getYHeadRot() + 180;
                entity.setYRot(yaw);
                entity.setYBodyRot(yaw);
                entity.setYHeadRot(yaw);
                entity.setYRot(yaw);
                entity.setYRot(yaw);
                level.addFreshEntity(entity);

                player.swing(context.getHand());

                if (itemStack.getItem().equals(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get())) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1f,
                            1 + 0.5f * player.getRandom().nextFloat());
                }
                level.playSound(null, spawnPos, OccultismSounds.SPIRIT_FIRE.get(), SoundSource.NEUTRAL, 1, 1);
                ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD,
                        spawnPos.getX() + 0.5, spawnPos.getY() + 0.1, spawnPos.getZ() + 0.5,
                        15, 0.0, 0.1, 0.0, 0.01);
                player.inventoryMenu.broadcastChanges();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target,
                                                  InteractionHand hand) {

        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        if (!target.isAlive())
            return InteractionResult.PASS;

        //This is called from PlayerEventHandler#onPlayerRightClickEntity, because we need to bypass sitting entities processInteraction
        if (target.level().isClientSide())
            return InteractionResult.PASS;

        //Do not allow  players.
        if (target instanceof Player)
            return InteractionResult.FAIL;

        //Already got an entity in there.
        if (stack.has(DataComponents.ENTITY_DATA))
            return InteractionResult.FAIL;

        //do not capture entities on deny lists
        if (target.getType().builtInRegistryHolder().is(Entities.FRAGILE_SOUL_GEM_DENY_LIST) && stack.getItem().equals(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get())) {
            player.sendSystemMessage(
                    Component.translatable(this.getDescriptionId() + ".message.entity_type_denied"));
            return InteractionResult.FAIL;
        }

        if (target.getType().builtInRegistryHolder().is(Entities.SOUL_GEM_DENY_LIST) && stack.getItem().equals(OccultismItems.SOUL_GEM_ITEM.get())) {
            player.sendSystemMessage(
                    Component.translatable(this.getDescriptionId() + ".message.entity_type_denied"));
            return InteractionResult.FAIL;
        }

        if (target.getType().builtInRegistryHolder().is(Entities.TRINITY_GEM_DENY_LIST) && stack.getItem().equals(OccultismItems.TRINITY_GEM_ITEM.get())) {
            player.sendSystemMessage(
                    Component.translatable(this.getDescriptionId() + ".message.entity_type_denied"));
            return InteractionResult.FAIL;
        }

        target.level().playSound(null, target.getOnPos(), OccultismSounds.POOF.get(), SoundSource.NEUTRAL, 1, 1);
        ((ServerLevel) target.level()).sendParticles(stack.getRarity() == Rarity.EPIC ? ParticleTypes.SCULK_SOUL : ParticleTypes.SOUL,
                target.getX(), target.getY() + target.getHitbox().getYsize()*0.8, target.getZ(),
                15, 0.0, 0.0, 0.0, 0.01);
        var output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
        target.saveWithoutId(output);
        var entityData = output.buildResult();

        //serialize entity - TypedEntityData stores the type directly, so we don't need the "id" in the NBT
        stack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(target.getType(), entityData));
        //show player swing anim
        player.swing(hand);
        player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
        target.remove(RemovalReason.DISCARDED);
        player.inventoryMenu.broadcastChanges();
        return InteractionResult.SUCCESS;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(stack.has(DataComponents.ENTITY_DATA) ? this.getDescriptionId() : this.getDescriptionId() + "_empty");
    }

    protected EntityType<?> getType(ItemStack pStack) {
        TypedEntityData<?> typedData = pStack.getOrDefault(DataComponents.ENTITY_DATA, null);
        if (typedData == null) return null;
        return (EntityType<?>) typedData.type();
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pTooltipFlag);

        if (pStack.has(DataComponents.ENTITY_DATA)) {
            EntityType<?> type = this.getType(pStack);
            if (type != null) {
                this.appendMultilineTooltip(pTooltipAdder,
                        Component.translatable(this.getDescriptionId() + ".tooltip_filled", type.getDescription()));
            }
        } else {
            this.appendMultilineTooltip(pTooltipAdder, Component.translatable(this.getDescriptionId() + ".tooltip_empty"));
        }
    }

    protected void appendMultilineTooltip(Consumer<Component> pTooltipAdder, Component pTooltip) {
        for (String line : NEWLINE_PATTERN.split(pTooltip.getString(), -1)) {
            pTooltipAdder.accept(Component.literal(line));
        }
    }
}
