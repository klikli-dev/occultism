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

package com.github.klikli_dev.occultism.common.item.spirit;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.TranslationKeys;
import com.github.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.github.klikli_dev.occultism.api.common.item.IHandleItemMode;
import com.github.klikli_dev.occultism.api.common.item.IIngredientCopyNBT;
import com.github.klikli_dev.occultism.client.gui.GuiHelper;
import com.github.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.util.BlockEntityUtil;
import com.github.klikli_dev.occultism.util.EntityUtil;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class BookOfCallingItem extends Item implements IIngredientCopyNBT, IHandleItemMode {

    //region Fields
    public static Map<UUID, Long> spiritDeathRegister = new HashMap<>();
    public String translationKeyBase;
    public Predicate<SpiritEntity> targetSpirit;
    //endregion Fields

    //region Initialization
    public BookOfCallingItem(Properties properties, String translationKeyBase, Predicate<SpiritEntity> targetSpirit) {
        super(properties);
        this.translationKeyBase = translationKeyBase;
        this.targetSpirit = targetSpirit;
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * @return returns the base item translation including the spirit name, but excluding the job.
     */
    public String getTranslationKeyBase() {
        return this.translationKeyBase;
    }

    //endregion Getter / Setter
    //region Overrides
    @Override
    public boolean shouldCopyNBT(ItemStack itemStack, Recipe recipe, CraftingContainer inventory) {
        return recipe.getResultItem().getItem() instanceof BookOfBindingBoundItem;
    }

    @Override
    public CompoundTag overrideNBT(ItemStack itemStack, CompoundTag nbt, Recipe recipe, CraftingContainer inventory) {
        CompoundTag result = new CompoundTag();
        //copy over only the spirit name
        if (nbt.contains(ItemNBTUtil.SPIRIT_NAME_TAG))
            result.putString(ItemNBTUtil.SPIRIT_NAME_TAG, nbt.getString(ItemNBTUtil.SPIRIT_NAME_TAG));
        return result;
    }

    @Override
    public int getItemMode(ItemStack stack) {
        return ItemNBTUtil.getItemMode(stack);
    }

    @Override
    public void setItemMode(ItemStack stack, int mode) {
        ItemNBTUtil.setItemMode(stack, mode);
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
                IItemModeSubset<?> subset = this.getItemModeSubset(itemStack);
                WorkAreaSize workAreaSize = ItemNBTUtil.getWorkAreaSize(itemStack);
                GuiHelper.openBookOfCallingGui(subset, workAreaSize);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target,
                                                  InteractionHand hand) {
        if (target.level.isClientSide)
            return InteractionResult.PASS;

        //Ignore anything that is not a spirit
        if (!(target instanceof SpiritEntity))
            return InteractionResult.PASS;

        SpiritEntity targetSpirit = (SpiritEntity) target;

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
                    if (ItemMode.get(this.getItemMode(stack)) == ItemMode.SET_DEPOSIT) {
                        if (targetSpirit.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                            UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
                            if (boundSpiritId != null) {
                                Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(target.level.getServer(), boundSpiritId)
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
    //endregion Overrides

    //region Methods
    public IItemModeSubset<?> getItemModeSubset(ItemStack stack) {
        return ItemMode.get(this.getItemMode(stack));
    }

    public boolean useWorkAreaSize() {
        return true;
    }

    public boolean setSpiritManagedMachineExtractLocation(Player player, Level world, BlockPos pos, ItemStack stack,
                                                          Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);
            BlockEntity extractBlockEntity = world.getBlockEntity(pos);
            if (boundSpirit.isPresent() && extractBlockEntity != null) {
                if (boundSpirit.get().getJob().isPresent() &&
                        boundSpirit.get().getJob().get() instanceof ManageMachineJob manageMachine) {

                    if (manageMachine.getManagedMachine() != null) {

                    } else {
                        player.displayClientMessage(
                                Component.translatable(
                                        TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_managed_machine_extract_location",
                                        TextUtil.formatDemonName(boundSpirit.get().getName().getString())), true);
                        return true;
                    }

                    //get the existing insert reference here, or if null reuse extract
                    var oldManagedMachineBlockEntity = manageMachine.getManagedMachineBlockEntity();
                    MachineReference newReference = MachineReference.from(extractBlockEntity, oldManagedMachineBlockEntity != null ? oldManagedMachineBlockEntity : extractBlockEntity);
                    newReference.extractFacing = face;
                    if (manageMachine.getManagedMachine() == null ||
                            !manageMachine.getManagedMachine().extractGlobalPos.equals(newReference.extractGlobalPos)) {
                        //if we are setting a completely new extract entity, just overwrite the reference.
                        manageMachine.setManagedMachine(newReference);
                    } else {
                        //otherwise just update the registry name in case the extract block type was switched out
                        manageMachine.getManagedMachine().extractRegistryName = newReference.extractRegistryName;
                        manageMachine.getManagedMachine().extractFacing = newReference.extractFacing;
                    }

                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                    String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                    player.displayClientMessage(
                            Component.translatable(
                                    TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_managed_machine_extract_location",
                                    TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                    Component.translatable(blockName), face.getSerializedName()), true);
                    return true;
                }
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    public boolean setSpiritManagedMachine(Player player, Level world, BlockPos pos, ItemStack stack,
                                           Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);
            BlockEntity managedMachineBlockEntity = world.getBlockEntity(pos);
            if (boundSpirit.isPresent() && managedMachineBlockEntity != null) {

                if (boundSpirit.get().getJob().isPresent() &&
                        boundSpirit.get().getJob().get() instanceof ManageMachineJob manageMachine) {

                    //Old code:
                    //  //get the existing extract reference here, or if null reuse insert
                    //  var oldExtractBlockEntity = manageMachine.getExtractBlockEntity();
                    //now we just use the new machine as extract target, because otherwise we would insert into the new machine, but extract from the old
                    MachineReference newReference = MachineReference.from(managedMachineBlockEntity, managedMachineBlockEntity);
                    if (manageMachine.getManagedMachine() == null ||
                            !manageMachine.getManagedMachine().insertGlobalPos.equals(newReference.insertGlobalPos)) {
                        //if we are setting a completely new machine, just overwrite the reference.
                        manageMachine.setManagedMachine(newReference);
                    } else {
                        //otherwise just update the registry name in case the block type was switched out
                        manageMachine.getManagedMachine().insertRegistryName = newReference.insertRegistryName;
                    }

                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                    player.displayClientMessage(
                            Component.translatable(
                                    TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_managed_machine",
                                    TextUtil.formatDemonName(boundSpirit.get().getName().getString())), true);
                    return true;
                }
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    public boolean setSpiritStorageController(Player player, Level world, BlockPos pos, ItemStack stack,
                                              Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);

            if (boundSpirit.isPresent() && boundSpirit.get().getJob().isPresent()) {
                if (boundSpirit.get().getJob().get() instanceof ManageMachineJob) {
                    ManageMachineJob job = (ManageMachineJob) boundSpirit.get().getJob().get();
                    job.setStorageControllerPosition(new GlobalBlockPos(pos, world));
                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                    String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                    player.displayClientMessage(Component.translatable(
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_storage_controller",
                            TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                            Component.translatable(blockName)), true);
                    return true;
                }
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    public boolean setSpiritDepositLocation(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);

            if (boundSpirit.isPresent()) {
                //update properties on entity
                boundSpirit.get().setDepositPosition(pos);
                boundSpirit.get().setDepositFacing(face);
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                player.displayClientMessage(
                        Component.translatable(TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_deposit",
                                TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                Component.translatable(blockName), face.getSerializedName()), true);
                return true;
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    public boolean setSpiritExtractLocation(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);

            if (boundSpirit.isPresent()) {
                //update properties on entity
                boundSpirit.get().setExtractPosition(pos);
                boundSpirit.get().setExtractFacing(face);
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                player.displayClientMessage(
                        Component.translatable(TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_extract",
                                TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                Component.translatable(blockName), face.getSerializedName()), true);
                return true;
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    public boolean setSpiritBaseLocation(Player player, Level world, BlockPos pos, ItemStack stack,
                                         Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);
            if (boundSpirit.isPresent()) {
                //update properties on entity
                boundSpirit.get().setWorkAreaPosition(pos);
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                player.displayClientMessage(
                        Component.translatable(TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_base",
                                TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                Component.translatable(blockName)), true);
                return true;
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    public InteractionResult handleItemMode(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction facing) {
        ItemMode itemMode = ItemMode.get(this.getItemMode(stack));
        BlockEntity blockEntity = world.getBlockEntity(pos);

        //handle the serverside item modes
        if (!world.isClientSide) {
            switch (itemMode) {
                case SET_DEPOSIT:
                    if (blockEntity != null &&
                            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, facing).isPresent()) {
                        return this.setSpiritDepositLocation(player, world, pos, stack,
                                facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                    }
                    break;
                case SET_EXTRACT:
                    if (this instanceof BookOfCallingManageMachineItem) {
                        if (blockEntity != null &&
                                blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, facing).isPresent()) {
                            return this.setSpiritManagedMachineExtractLocation(player, world, pos, stack,
                                    facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                        }
                    } else if (blockEntity != null &&
                            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, facing).isPresent()) {
                        return this.setSpiritExtractLocation(player, world, pos, stack,
                                facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                    }
                    break;
                case SET_BASE:
                    return this.setSpiritBaseLocation(player, world, pos, stack,
                            facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                case SET_STORAGE_CONTROLLER:
                    if (blockEntity instanceof IStorageController) {
                        return this.setSpiritStorageController(player, world, pos, stack,
                                facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                    }
                    break;
                case SET_MANAGED_MACHINE:
                    if (blockEntity != null && BlockEntityUtil.hasCapabilityOnAnySide(blockEntity,
                            ForgeCapabilities.ITEM_HANDLER)) {
                        this.setSpiritManagedMachine(player, world, pos, stack, facing);
                        return InteractionResult.SUCCESS;
                    }
                    break;
            }
        } else {
            switch (itemMode) {
                case SET_MANAGED_MACHINE:
                    if (blockEntity != null && BlockEntityUtil.hasCapabilityOnAnySide(blockEntity,
                            ForgeCapabilities.ITEM_HANDLER)) {
                        MachineReference machine = ItemNBTUtil.getManagedMachine(stack);
                        if (machine != null) {
                            GuiHelper.openBookOfCallingManagedMachineGui(machine.insertFacing, machine.extractFacing,
                                    machine.customName);
                        }
                    }
                    break;
            }
        }


        return InteractionResult.PASS;
    }

    //endregion Methods

    public enum ItemMode implements IItemModeSubset<ItemMode> {

        SET_DEPOSIT(0, "set_deposit"),
        SET_EXTRACT(1, "set_extract"),
        SET_BASE(2, "set_base"),
        SET_STORAGE_CONTROLLER(3, "set_storage_controller"),
        SET_MANAGED_MACHINE(4, "set_managed_machine");

        //region Fields
        private static final Map<Integer, ItemMode> lookup = new HashMap<Integer, ItemMode>();
        private static final String TRANSLATION_KEY_BASE =
                "enum." + Occultism.MODID + ".book_of_calling.item_mode";

        static {
            for (ItemMode itemMode : ItemMode.values()) {
                lookup.put(itemMode.getValue(), itemMode);
            }
        }

        private final int value;
        private final String translationKey;
        //endregion Fields

        //region Initialization
        ItemMode(int value, String translationKey) {
            this.value = value;
            this.translationKey = TRANSLATION_KEY_BASE + "." + translationKey;
        }
        //endregion Initialization

        //region Static Methods
        public static ItemMode get(int value) {
            return lookup.get(value);
        }

        //region Getter / Setter
        public int getValue() {
            return this.value;
        }
        //endregion Getter / Setter

        public String getDescriptionId() {
            return this.translationKey;
        }

        //region Overrides
        @Override
        public ItemMode getItemMode() {
            return this;
        }
        //endregion Overrides

        public ItemMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
        //endregion Static Methods

        //region Methods
        public boolean equals(int value) {
            return this.value == value;
        }
        //endregion Methods
    }

    public interface IItemModeSubset<T extends IItemModeSubset<T>> {
        //region Getter / Setter
        ItemMode getItemMode();
        //endregion Getter / Setter

        //region Methods
        T next();
        //endregion Methods
    }
}
