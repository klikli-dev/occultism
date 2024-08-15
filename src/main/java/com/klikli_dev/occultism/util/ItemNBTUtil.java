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

import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class ItemNBTUtil {

    public static final String SPIRIT_NAME_TAG = "spiritName";
    public static final String SPIRIT_UUID_TAG = "spiritUUID";
    public static final String SPIRIT_DEAD_TAG = "spiritDead";
    public static final String SPIRIT_DATA_TAG = "spiritData";
    public static final String ITEM_MODE_TAG = "itemMode";
    public static final String WORK_AREA_SIZE_TAG = "workAreaSize";
    public static final String WORK_AREA_POSITION_TAG = "workAreaPosition";
    public static final String DEPOSIT_POSITION_TAG = "depositPosition";
    public static final String DEPOSIT_ENTITY_UUID_TAG = "depositEntityUUID";
    public static final String DEPOSIT_ENTITY_NAME_TAG = "depositEntityName";
    public static final String DEPOSIT_FACING_TAG = "depositFacing";
    public static final String EXTRACT_POSITION_TAG = "extractPosition";
    public static final String EXTRACT_FACING_TAG = "extractFacing";
    public static final String STORAGE_CONTROLLER_POSITION_TAG = "storageControllerPosition";
    public static final String MANAGED_MACHINE = "managedMachine";

    //region Static Methods

    public static void updateItemNBTFromEntity(ItemStack stack, SpiritEntity entity) {
        ItemNBTUtil.setWorkAreaPosition(stack, entity.getWorkAreaPosition());
        ItemNBTUtil.setDepositPosition(stack, entity.getDepositPosition());
        ItemNBTUtil.setDepositEntityUUID(stack, entity.getDepositEntityUUID());
        ItemNBTUtil.setDepositFacing(stack, entity.getDepositFacing());
        ItemNBTUtil.setExtractPosition(stack, entity.getExtractPosition());
        ItemNBTUtil.setExtractFacing(stack, entity.getExtractFacing());
        ItemNBTUtil.setWorkAreaSize(stack, entity.getWorkAreaSize());

        entity.getJob().filter(ManageMachineJob.class::isInstance).map(ManageMachineJob.class::cast).ifPresent(job -> {
            if (job.getStorageControllerPosition() != null)
                ItemNBTUtil.setStorageControllerPosition(stack, job.getStorageControllerPosition());
            if (job.getManagedMachine() != null)
                ItemNBTUtil.setManagedMachine(stack, job.getManagedMachine());
        });

    }

    public static void generateBoundSpiritName(ItemStack stack) {
        setBoundSpiritName(stack, TextUtil.generateName());
    }

    public static void setBoundSpiritName(ItemStack stack, String name) {
        stack.getOrCreateTag().putString(SPIRIT_NAME_TAG, name);
    }

    public static String getBoundSpiritName(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(SPIRIT_NAME_TAG))
            generateBoundSpiritName(stack);
        return stack.getTag().getString(SPIRIT_NAME_TAG);
    }

    public static int getItemMode(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(ITEM_MODE_TAG))
            setItemMode(stack, 0);

        return stack.getTag().getInt(ITEM_MODE_TAG);
    }

    public static void setItemMode(ItemStack stack, int mode) {
        stack.getOrCreateTag().putInt(ITEM_MODE_TAG, mode);
    }

    public static GlobalBlockPos getStorageControllerPosition(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(STORAGE_CONTROLLER_POSITION_TAG))
            return null;

        return GlobalBlockPos.from(stack.getTag().getCompound(STORAGE_CONTROLLER_POSITION_TAG));
    }

    public static void setStorageControllerPosition(ItemStack stack, GlobalBlockPos position) {
        if (position != null)
            stack.addTagElement(STORAGE_CONTROLLER_POSITION_TAG, position.serializeNBT());
    }

    public static MachineReference getManagedMachine(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(MANAGED_MACHINE))
            return null;

        return MachineReference.from(stack.getTag().getCompound(MANAGED_MACHINE));
    }

    public static void setManagedMachine(ItemStack stack, MachineReference position) {
        if (position != null)
            stack.addTagElement(MANAGED_MACHINE, position.serializeNBT());
    }

    public static BlockPos getDepositPosition(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(DEPOSIT_POSITION_TAG))
            return null;

        return BlockPos.of(stack.getTag().getLong(DEPOSIT_POSITION_TAG));
    }

    public static void setDepositPosition(ItemStack stack, Optional<BlockPos> position) {
        if (position.isPresent()) {
            stack.getOrCreateTag().putLong(DEPOSIT_POSITION_TAG, position.get().asLong());
        } else if (stack.hasTag()) {
            stack.getTag().remove(DEPOSIT_POSITION_TAG);
        }
    }

    public static UUID getDepositEntityUUID(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(DEPOSIT_ENTITY_UUID_TAG))
            return null;

        return stack.getTag().getUUID(DEPOSIT_ENTITY_UUID_TAG);
    }

    public static void setDepositEntityUUID(ItemStack stack, Optional<UUID> uuid) {
        if (uuid.isPresent()) {
            stack.getOrCreateTag().putUUID(DEPOSIT_ENTITY_UUID_TAG, uuid.get());
        } else if (stack.hasTag()) {
            stack.getTag().remove(DEPOSIT_ENTITY_UUID_TAG);
        }
    }

    public static void setDepositEntityName(ItemStack stack, String string) {
        stack.getOrCreateTag().putString(DEPOSIT_ENTITY_NAME_TAG, string);
    }

    public static String getDepositEntityName(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(DEPOSIT_ENTITY_NAME_TAG))
            return null;

        return stack.getTag().getString(DEPOSIT_ENTITY_NAME_TAG);
    }

    public static Direction getDepositFacing(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(DEPOSIT_FACING_TAG))
            return null;
        return Direction.values()[stack.getTag().getInt(DEPOSIT_FACING_TAG)];
    }

    public static void setDepositFacing(ItemStack stack, Direction facing) {
        stack.getOrCreateTag().putInt(DEPOSIT_FACING_TAG, facing.ordinal());
    }

    public static BlockPos getExtractPosition(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(EXTRACT_POSITION_TAG))
            return null;

        return BlockPos.of(stack.getTag().getLong(EXTRACT_POSITION_TAG));
    }

    public static void setExtractPosition(ItemStack stack, Optional<BlockPos> position) {
        position.ifPresent(p -> stack.getOrCreateTag().putLong(EXTRACT_POSITION_TAG, p.asLong()));
    }

    public static Direction getExtractFacing(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(EXTRACT_FACING_TAG))
            return null;
        return Direction.values()[stack.getTag().getInt(EXTRACT_FACING_TAG)];
    }

    public static void setExtractFacing(ItemStack stack, Direction facing) {
        stack.getOrCreateTag().putInt(EXTRACT_FACING_TAG, facing.ordinal());
    }

    public static BlockPos getWorkAreaPosition(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(WORK_AREA_POSITION_TAG))
            return null;

        return BlockPos.of(stack.getTag().getLong(WORK_AREA_POSITION_TAG));
    }

    public static void setWorkAreaPosition(ItemStack stack, Optional<BlockPos> position) {
        position.ifPresent(p -> stack.getOrCreateTag().putLong(WORK_AREA_POSITION_TAG, p.asLong()));
    }

    public static WorkAreaSize getWorkAreaSize(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(WORK_AREA_SIZE_TAG))
            setWorkAreaSize(stack, WorkAreaSize.SMALL);
        return WorkAreaSize.get(stack.getTag().getInt(WORK_AREA_SIZE_TAG));
    }

    public static void setWorkAreaSize(ItemStack stack, WorkAreaSize workAreaSize) {
        stack.getOrCreateTag().putInt(WORK_AREA_SIZE_TAG, workAreaSize.getValue());
    }

    public static UUID getSpiritEntityUUID(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(SPIRIT_UUID_TAG))
            return null;
        return stack.getTag().getCompound(SPIRIT_UUID_TAG).getUUID("");
    }

    public static void setSpiritEntityUUID(ItemStack stack, UUID id) {
        CompoundTag uuidCompound = new CompoundTag();
        uuidCompound.putUUID("", id);
        stack.addTagElement(SPIRIT_UUID_TAG, uuidCompound);
    }

    public static boolean getSpiritDead(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(SPIRIT_DEAD_TAG);
    }

    public static CompoundTag getSpiritEntityData(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(SPIRIT_DATA_TAG))
            return null;
        return stack.getTag().getCompound(SPIRIT_DATA_TAG);
    }

    public static void setSpiritEntityData(ItemStack stack, CompoundTag entityData) {
        stack.getOrCreateTag().put(SPIRIT_DATA_TAG, entityData);
    }

    public static Optional<SpiritEntity> getSpiritEntity(ItemStack itemStack) {
        return EntityUtil.getEntityByUuiDGlobal(getSpiritEntityUUID(itemStack)).map(e -> (SpiritEntity) e);
    }
    //endregion Static Methods

}
