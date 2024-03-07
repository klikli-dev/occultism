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

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.api.common.data.MachineReference;
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
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;

import java.util.*;

public class BookOfCallingManageMachineItem extends BookOfCallingItem {

    public BookOfCallingManageMachineItem(Properties properties, String translationKeyBase) {
        super(properties, translationKeyBase, spirit -> spirit.getJob().orElse(null) instanceof ManageMachineJob);
    }




    public InteractionResult handleItemMode(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction facing) {
        ItemMode itemMode = this.getCurrentItemMode(stack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClientSide) {

            if(itemMode==ItemModes.SET_EXTRACT) {
                if (blockEntity != null
                         && world.getCapability(Capabilities.ItemHandler.BLOCK, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, facing) != null) {
                    return this.setSpiritManagedMachineExtractLocation(player, world, pos, stack,
                            facing) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                }
            }
        } else {
            if (Objects.requireNonNull(itemMode) == ItemModes.SET_MANAGED_MACHINE) {
                if (blockEntity != null && BlockEntityUtil.hasCapabilityOnAnySide(blockEntity,
                        Capabilities.ItemHandler.BLOCK)) {
                    MachineReference machine = ItemNBTUtil.getManagedMachine(stack);
                    if (machine != null) {
                        GuiHelper.openBookOfCallingManagedMachineGui(machine.insertFacing, machine.extractFacing,
                                machine.customName);
                    }
                }
            }
        }
        return super.handleItemMode(player, world, pos, stack, facing);
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

    @Override
    public List<ItemMode> getItemModes() {
        return Arrays.asList(ItemModes.SET_MANAGED_MACHINE, ItemModes.SET_EXTRACT, ItemModes.SET_STORAGE_CONTROLLER);
    }
}
