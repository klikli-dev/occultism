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

package com.klikli_dev.occultism.common.item.spirit;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.api.common.item.IHandleItemMode;
import com.klikli_dev.occultism.client.gui.GuiHelper;
import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemModes;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import com.klikli_dev.occultism.util.EntityUtil;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class BookOfCallingItem extends Item implements IHandleItemMode {

    public static Map<UUID, Long> spiritDeathRegister = new HashMap<>();
    public String translationKeyBase;
    public Predicate<SpiritEntity> targetSpirit;


    public BookOfCallingItem(Properties properties, String translationKeyBase, Predicate<SpiritEntity> targetSpirit) {
        super(properties);
        this.translationKeyBase = translationKeyBase;
        this.targetSpirit = targetSpirit;
    }

    //region Getter / Setter

    /**
     * @return returns the base item translation including the spirit name, but excluding the job.
     */
    public String getTranslationKeyBase() {
        return this.translationKeyBase;
    }

    //endregion Getter / Setter

    public List<ItemMode> getItemModes() { return new ArrayList<>();}

    @Override
    public int getItemMode(ItemStack stack) {
        return ItemNBTUtil.getItemMode(stack);
    }

    @Override
    public void setItemMode(ItemStack stack, int mode) {
        ItemNBTUtil.setItemMode(stack, mode);
    }

    public ItemMode nextItemMode(ItemStack stack) {
        int mode = this.getItemMode(stack);
        mode = (mode + 1) % this.getItemModes().size();
        this.setItemMode(stack, mode);
        return this.getCurrentItemMode(stack);
    }
    public int modeValue(ItemMode mode) {
        return this.getItemModes().indexOf(mode) ;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!pPlayer.isShiftKeyDown() && pLevel.isClientSide) {
            ItemMode curr = this.getCurrentItemMode(itemStack);
            WorkAreaSize workAreaSize = ItemNBTUtil.getWorkAreaSize(itemStack);
            GuiHelper.openBookOfCallingGui(curr, workAreaSize);
        }
        return super.use(pLevel, pPlayer, pUsedHand);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        Level world = context.getLevel();
        Direction facing = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        CompoundTag entityData = ItemNBTUtil.getSpiritEntityData(itemStack);
        if (entityData != null) {
            //whenever we have an entity stored we can do nothing but release it
            if (!world.isClientSide) {
                EntityType type = EntityUtil.entityTypeFromNbt(entityData);

                facing = facing == null ? Direction.UP : facing;

                BlockPos spawnPos = pos.immutable();
                if (!world.getBlockState(spawnPos).getCollisionShape(world, spawnPos).isEmpty()) {
                    spawnPos = spawnPos.relative(facing);
                }

                Component customName = null;
                if (entityData.contains("CustomName")) {
                    customName = Component.Serializer.fromJson(entityData.getString("CustomName"));
                }

                //remove position from tag to allow the entity to spawn where it should be
                entityData.remove("Pos");

                //type.spawn uses the sub-tag EntityTag
                CompoundTag wrapper = new CompoundTag();
                wrapper.put("EntityTag", entityData);

                SpiritEntity entity = (SpiritEntity) type.create(world);
                entity.load(entityData);
                entity.absMoveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
                world.addFreshEntity(entity);

                //old spawn code
                //                SpiritEntity entity = (SpiritEntity) type.spawn((ServerLevel)world, wrapper, customName, null, spawnPos,
                //                        MobSpawnType.MOB_SUMMONED, true, !pos.equals(spawnPos) && facing == Direction.UP);
                //                if (entityData.contains("OwnerUUID") && !entityData.getString("OwnerUUID").isEmpty()) {
                //                    entity.setOwnerId(UUID.fromString(entityData.getString("OwnerUUID")));
                //                }

                //refresh item nbt
                ItemNBTUtil.updateItemNBTFromEntity(itemStack, entity);

                world.addFreshEntity(entity);

                itemStack.getTag().remove(ItemNBTUtil.SPIRIT_DATA_TAG); //delete entity from item
                player.inventoryMenu.broadcastChanges();
            }
        } else {
            //if there are no entities stored, we can either open the ui or perform the action
            if (player.isShiftKeyDown()) {
                //when sneaking, perform action based on mode
                return this.handleItemMode(player, world, pos, itemStack, facing);
            } else if (world.isClientSide) {
                //if not sneaking, open general ui
                ItemMode curr = this.getCurrentItemMode(itemStack);
                WorkAreaSize workAreaSize = ItemNBTUtil.getWorkAreaSize(itemStack);
                GuiHelper.openBookOfCallingGui(curr, workAreaSize);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target,
                                                  InteractionHand hand) {

        //Ignore anything that is not a spirit
        if (!(target instanceof SpiritEntity targetSpirit))
            return InteractionResult.PASS;

        if (target.level().isClientSide)
            return InteractionResult.SUCCESS;

        //books can only control the spirit that is bound to them.
        if (!targetSpirit.getUUID().equals(ItemNBTUtil.getSpiritEntityUUID(stack))) {
            //re-link book
            if (player.isShiftKeyDown()) {
                if (this.targetSpirit.test(targetSpirit)) {
                    ItemNBTUtil.setSpiritEntityUUID(stack, targetSpirit.getUUID());
                    ItemNBTUtil.setBoundSpiritName(stack, targetSpirit.getName().getString());
                    player.displayClientMessage(
                            Component.translatable(
                                    TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_target_linked"),
                            true);
                    player.swing(hand);
                    player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
                    player.inventoryMenu.broadcastChanges();
                    return InteractionResult.SUCCESS;
                } else {
                    //if our mode is "set deposit" then we check if the target is appropriate for depositing
                    //Note: we filter above for spirits -> so for now only spirits are an appropriate target
                    if (this.getCurrentItemMode(stack) == ItemModes.SET_DEPOSIT) {
                        if (targetSpirit.getCapability(Capabilities.ItemHandler.ENTITY) != null) {
                            UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
                            if (boundSpiritId != null) {
                                Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(target.level().getServer(), boundSpiritId)
                                        .map(e -> (SpiritEntity) e);

                                if (boundSpirit.isPresent()) {
                                    boundSpirit.get().setDepositEntityUUID(targetSpirit.getUUID());
                                    //also update control item with latest data
                                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());
                                    ItemNBTUtil.setDepositEntityName(stack, target.getName().getString());


                                    player.displayClientMessage(
                                            Component.translatable(TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_deposit_entity",
                                                    TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                                    TextUtil.formatDemonName(targetSpirit.getName().getString())), true);
                                    player.swing(hand);
                                    player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
                                    player.inventoryMenu.broadcastChanges();
                                    return InteractionResult.SUCCESS;
                                } else {
                                    player.displayClientMessage(
                                            Component.translatable(
                                                    TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                                            true);
                                    return InteractionResult.FAIL;
                                }
                            } else {
                                //if spirit id is null then this was a (failed) link attempt -> and we fail
                                player.displayClientMessage(
                                        Component.translatable(
                                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_target_cannot_link"),
                                        true);
                                return InteractionResult.FAIL;
                            }
                        } else {
                            //if target is not appropriate, we fail
                            player.displayClientMessage(
                                    Component.translatable(
                                            TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_target_entity_no_inventory"),
                                    true);
                            return InteractionResult.FAIL;
                        }
                    }

                    //if mode is not deposit we fail the linking
                    player.displayClientMessage(
                            Component.translatable(
                                    TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_target_cannot_link"),
                            true);
                    return InteractionResult.FAIL;
                }
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_target_uuid_no_match"),
                        true);
                return InteractionResult.FAIL;
            }
        }

        //serialize entity
        ItemNBTUtil.setSpiritEntityData(stack, targetSpirit.serializeNBT());
        ItemNBTUtil.setSpiritEntityUUID(stack, targetSpirit.getUUID());
        ItemNBTUtil.setBoundSpiritName(stack, targetSpirit.getName().getString());
        //show player swing anim
        player.swing(hand);
        player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
        targetSpirit.remove(Entity.RemovalReason.DISCARDED);
        player.inventoryMenu.broadcastChanges();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.getGameTime() % (20 * 60) == 0) {
            UUID spiritID = ItemNBTUtil.getSpiritEntityUUID(stack);
            if (spiritID != null) {
                Long deathTime = spiritDeathRegister.get(spiritID);
                if (deathTime != null && deathTime < worldIn.getGameTime()) {
                    spiritDeathRegister.remove(spiritID);
                    stack.getTag().putBoolean(ItemNBTUtil.SPIRIT_DEAD_TAG, true);
                    stack.getTag().remove(ItemNBTUtil.SPIRIT_UUID_TAG);
                }
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(this.getTranslationKeyBase() +
                        (ItemNBTUtil.getSpiritDead(stack) ? ".tooltip_dead" : ".tooltip"),
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ItemNBTUtil.getSpiritEntityData(stack) != null;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ItemNBTUtil.getSpiritEntityUUID(stack) != null ? Rarity.RARE : Rarity.COMMON;
    }

    public ItemMode getCurrentItemMode(ItemStack stack) {
        int mode= this.getItemMode(stack);

        // sanity check.
        // here to prevent crashes if old system has invalid mode
        if(mode<0 || mode>=getItemModes().size()){
            mode=0;
            setItemMode(stack,mode);
        }
        return getItemModes().get(mode);
    }

    public boolean useWorkAreaSize() {
        return true;
    }

    public InteractionResult handleItemMode(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction facing) {
        ItemMode itemMode = this.getCurrentItemMode(stack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        //handle the serverside item modes
        if (!world.isClientSide) {

                return itemMode.handle(blockEntity,player, world, pos, stack, facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;

        }
        return InteractionResult.PASS;
    }
}
