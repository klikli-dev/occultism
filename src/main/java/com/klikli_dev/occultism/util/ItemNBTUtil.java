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
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Optional;
import java.util.UUID;

public class ItemNBTUtil {

    public static void updateItemNBTFromEntity(ItemStack stack, SpiritEntity entity) {
        setWorkAreaPosition(stack, entity.getWorkAreaPosition());
        setDepositPosition(stack, entity.getDepositPosition());
        setDepositEntityUUID(stack, entity.getDepositEntityUUID());
        setDepositFacing(stack, entity.getDepositFacing());
        setExtractPosition(stack, entity.getExtractPosition());
        setExtractFacing(stack, entity.getExtractFacing());
        setWorkAreaSize(stack, entity.getWorkAreaSize());

        entity.getJob().filter(ManageMachineJob.class::isInstance).map(ManageMachineJob.class::cast).ifPresent(job -> {
            if (job.getStorageControllerPosition() != null)
                setStorageControllerPosition(stack, job.getStorageControllerPosition());
            if (job.getManagedMachine() != null)
                setManagedMachine(stack, job.getManagedMachine());
        });

    }

    public static void generateBoundSpiritNameIfNone(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.SPIRIT_NAME) ||
                stack.get(OccultismDataComponents.SPIRIT_NAME).isEmpty() ||
                stack.get(OccultismDataComponents.SPIRIT_NAME).equals(TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)
        ) {
            generateBoundSpiritName(stack);
        }
    }

    public static void generateBoundSpiritName(ItemStack stack) {
        setBoundSpiritName(stack, TextUtil.generateName());
    }

    public static void setBoundSpiritName(ItemStack stack, String name) {
        stack.set(OccultismDataComponents.SPIRIT_NAME, name);
    }

    public static String getBoundSpiritName(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.SPIRIT_NAME))
            generateBoundSpiritName(stack);
        return stack.get(OccultismDataComponents.SPIRIT_NAME);
    }

    public static int getItemMode(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.ITEM_MODE))
            setItemMode(stack, 0);

        return stack.get(OccultismDataComponents.ITEM_MODE);
    }

    public static void setItemMode(ItemStack stack, int mode) {
        stack.set(OccultismDataComponents.ITEM_MODE, mode);
    }

    public static GlobalBlockPos getStorageControllerPosition(ItemStack stack) {
        return stack.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);
    }

    public static void setStorageControllerPosition(ItemStack stack, GlobalBlockPos position) {
        if (position != null)
            stack.set(OccultismDataComponents.LINKED_STORAGE_CONTROLLER, position);
    }

    public static MachineReference getManagedMachine(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.MANAGED_MACHINE))
            return null;

        return stack.get(OccultismDataComponents.MANAGED_MACHINE);
    }

    public static void setManagedMachine(ItemStack stack, MachineReference position) {
        if (position != null)
            stack.set(OccultismDataComponents.MANAGED_MACHINE, position);
    }

    public static BlockPos getDepositPosition(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.DEPOSIT_POSITION))
            return null;

        return stack.get(OccultismDataComponents.DEPOSIT_POSITION);
    }

    public static void setDepositPosition(ItemStack stack, Optional<BlockPos> position) {
        if (position.isPresent()) {
            stack.set(OccultismDataComponents.DEPOSIT_POSITION, position.get());
        } else if (stack.has(OccultismDataComponents.DEPOSIT_POSITION)) {
            stack.remove(OccultismDataComponents.DEPOSIT_POSITION);
        }
    }

    public static UUID getDepositEntityUUID(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.DEPOSIT_ENTITY_UUID))
            return null;

        return stack.get(OccultismDataComponents.DEPOSIT_ENTITY_UUID);
    }

    public static void setDepositEntityUUID(ItemStack stack, Optional<UUID> uuid) {
        if (uuid.isPresent()) {
            stack.set(OccultismDataComponents.DEPOSIT_ENTITY_UUID, uuid.get());
        } else if (stack.has(OccultismDataComponents.DEPOSIT_ENTITY_UUID)) {
            stack.remove(OccultismDataComponents.DEPOSIT_ENTITY_UUID);
        }
    }

    public static void setDepositEntityName(ItemStack stack, String string) {
        stack.set(OccultismDataComponents.DEPOSIT_ENTITY_NAME, string);
    }

    public static String getDepositEntityName(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.DEPOSIT_ENTITY_NAME))
            return null;

        return stack.get(OccultismDataComponents.DEPOSIT_ENTITY_NAME);
    }

    public static Direction getDepositFacing(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.DEPOSIT_FACING))
            return null;

        return stack.get(OccultismDataComponents.DEPOSIT_FACING);
    }

    public static void setDepositFacing(ItemStack stack, Direction facing) {
        stack.set(OccultismDataComponents.DEPOSIT_FACING, facing);
    }

    public static BlockPos getExtractPosition(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.EXTRACT_POS))
            return null;

        return stack.get(OccultismDataComponents.EXTRACT_POS);
    }

    public static void setExtractPosition(ItemStack stack, Optional<BlockPos> position) {
        if (position.isPresent()) {
            stack.set(OccultismDataComponents.EXTRACT_POS, position.get());
        } else if (stack.has(OccultismDataComponents.EXTRACT_POS)) {
            stack.remove(OccultismDataComponents.EXTRACT_POS);
        }
    }

    public static Direction getExtractFacing(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.EXTRACT_FACING))
            return null;

        return stack.get(OccultismDataComponents.EXTRACT_FACING);
    }

    public static void setExtractFacing(ItemStack stack, Direction facing) {
        stack.set(OccultismDataComponents.EXTRACT_FACING, facing);
    }

    public static BlockPos getWorkAreaPosition(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.WORK_AREA_POS))
            return null;

        return stack.get(OccultismDataComponents.WORK_AREA_POS);
    }

    public static void setWorkAreaPosition(ItemStack stack, Optional<BlockPos> position) {
        if (position.isPresent()) {
            stack.set(OccultismDataComponents.WORK_AREA_POS, position.get());
        } else if (stack.has(OccultismDataComponents.WORK_AREA_POS)) {
            stack.remove(OccultismDataComponents.WORK_AREA_POS);
        }
    }

    public static WorkAreaSize getWorkAreaSize(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.WORK_AREA_SIZE))
            setWorkAreaSize(stack, WorkAreaSize.SMALL);

        return stack.get(OccultismDataComponents.WORK_AREA_SIZE);
    }

    public static void setWorkAreaSize(ItemStack stack, WorkAreaSize workAreaSize) {
        stack.set(OccultismDataComponents.WORK_AREA_SIZE, workAreaSize);
    }

    public static UUID getSpiritEntityUUID(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.SPIRIT_ENTITY_UUID))
            return null;

        return stack.get(OccultismDataComponents.SPIRIT_ENTITY_UUID);
    }

    public static void setSpiritEntityUUID(ItemStack stack, UUID id) {
        stack.set(OccultismDataComponents.SPIRIT_ENTITY_UUID, id);
    }

    public static boolean getSpiritDead(ItemStack stack) {
        return stack.getOrDefault(OccultismDataComponents.SPIRIT_DEAD, false);
    }

    public static CompoundTag getSpiritEntityData(ItemStack stack) {
        if (!stack.has(OccultismDataComponents.SPIRIT_ENTITY_DATA))
            return null;

        return stack.get(OccultismDataComponents.SPIRIT_ENTITY_DATA).getUnsafe();
    }

    public static void setSpiritEntityData(ItemStack stack, CompoundTag entityData) {
        stack.set(OccultismDataComponents.SPIRIT_ENTITY_DATA, CustomData.of(entityData));
    }

    public static Optional<SpiritEntity> getSpiritEntity(ItemStack itemStack) {
        return EntityUtil.getEntityByUuiDGlobal(getSpiritEntityUUID(itemStack)).map(e -> (SpiritEntity) e);
    }
}
