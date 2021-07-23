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

package com.github.klikli_dev.occultism.common.item.tool;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SoulGemItem extends Item {
    //region Initialization
    public SoulGemItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public InteractionResult onItemUse(ItemUseContext context) {
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItem();
        Level level = context.getLevel();
        Direction facing = context.getFace();
        BlockPos pos = context.getPos();
        if (itemStack.getOrCreateTag().contains("entityData")) {
            //whenever we have an entity stored we can do nothing but release it
            if (!level.isClientSide) {
                CompoundTag entityData = itemStack.getTag().getCompound("entityData");
                itemStack.getTag()
                        .remove("entityData"); //delete entity from item right away to avoid duplicate in case of unexpected error

                EntityType type = EntityUtil.entityTypeFromNbt(entityData);

                facing = facing == null ? Direction.UP : facing;

                BlockPos spawnPos = pos.toImmutable();
                if (!level.getBlockState(spawnPos).getCollisionShape(level, spawnPos).isEmpty()) {
                    spawnPos = spawnPos.offset(facing);
                }

                ITextComponent customName = null;
                if (entityData.contains("CustomName")) {
                    customName = ITextComponent.Serializer.getComponentFromJson(entityData.getString("CustomName"));
                }

                //remove position from tag to allow the entity to spawn where it should be
                entityData.remove("Pos");

                //type.spawn uses the sub-tag EntityTag
                CompoundTag wrapper = new CompoundTag();
                wrapper.put("EntityTag", entityData);

                Entity entity = type.create(level);
                entity.read(entityData);
                entity.setPositionAndRotation(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
                level.addEntity(entity);

                // old spawn cde:
                //                Entity entity = type.spawn((ServerWorld) level, wrapper, customName, null, spawnPos,
                //                        SpawnReason.MOB_SUMMONED, true, !pos.equals(spawnPos) && facing == Direction.UP);
                //                if (entity instanceof TamableAnimal && entityData.contains("OwnerUUID") &&
                //                    !entityData.getString("OwnerUUID").isEmpty()) {
                //                    TamableAnimal tameableEntity = (TamableAnimal) entity;
                //                    try {
                //                        tameableEntity.setOwnerId(UUID.fromString(entityData.getString("OwnerUUID")));
                //                    } catch (IllegalArgumentException e) {
                //                        //catch invalid uuid exception
                //                    }
                //                }

                player.swingArm(context.getHand());
                player.container.detectAndSendChanges();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult itemInteractionForEntity(ItemStack stack, Player player, LivingEntity target,
                                                     InteractionHand hand) {
        //This is called from PlayerEventHandler#onPlayerRightClickEntity, because we need to bypass sitting entities processInteraction
        if (target.level.isClientSide)
            return InteractionResult.PASS;

        //Do not allow bosses or players.
        //canChangeDimension used to be isNonBoss
        if (!target.canChangeDimension() || target instanceof Player)
            return InteractionResult.FAIL;

        //Already got an entity in there.
        if (stack.getOrCreateTag().contains("entityData"))
            return InteractionResult.FAIL;

        //do not capture entities on deny lists
        if (Occultism.SERVER_CONFIG.itemSettings.soulgemEntityTypeDenyList.get().contains(target.getEntityString())) {
            player.sendMessage(
                    new TranslationTextComponent(this.getDescriptionId() + ".message.entity_type_denied"),
                    Util.DUMMY_UUID);
            return InteractionResult.FAIL;
        }

        //serialize entity
        stack.getTag().put("entityData", target.serializeNBT());
        //show player swing anim
        player.swingArm(hand);
        player.setHeldItem(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
        target.remove(true);
        player.container.detectAndSendChanges();
        return InteractionResult.SUCCESS;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return stack.getOrCreateTag().contains("entityData") ? this.getDescriptionId() :
                       this.getDescriptionId() + "_empty";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        if (stack.getOrCreateTag().contains("entityData")) {
            EntityType<?> type = EntityUtil.entityTypeFromNbt(stack.getTag().getCompound("entityData"));
            tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".tooltip_filled", type.getName()));
        }
        else {
            tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".tooltip_empty"));
        }
    }
    //endregion Overrides
}
