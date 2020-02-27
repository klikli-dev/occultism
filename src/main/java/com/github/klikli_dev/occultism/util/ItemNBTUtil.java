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

package com.github.klikli_dev.occultism.util;

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.entity.spirits.WorkAreaSize;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobManageMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class ItemNBTUtil {

    //region Fields
    public static final String SPIRIT_NAME_TAG = "spiritName";
    public static final String SPIRIT_UUID_TAG = "spiritUUID";
    public static final String SPIRIT_DATA_TAG = "spiritData";
    public static final String ITEM_MODE_TAG = "itemMode";
    public static final String WORK_AREA_SIZE_TAG = "workAreaSize";
    public static final String WORK_AREA_POSITION_TAG = "workAreaPosition";
    public static final String DEPOSIT_POSITION_TAG = "depositPosition";
    public static final String DEPOSIT_FACING_TAG = "depositFacing";
    public static final String STORAGE_CONTROLLER_POSITION_TAG = "storageControllerPosition";
    public static final String MANAGED_MACHINE = "managedMachine";

    //endregion Fields

    //region Static Methods

    public static void updateItemNBTFromEntity(ItemStack stack, EntitySpirit entity) {
        ItemNBTUtil.setWorkAreaPosition(stack, entity.getWorkAreaPosition());
        ItemNBTUtil.setDepositPosition(stack, entity.getDepositPosition());
        ItemNBTUtil.setDepostFacing(stack, entity.getDepositFacing());
        ItemNBTUtil.setWorkAreaSize(stack, entity.getWorkAreaSize());
        if (entity.getJob() instanceof SpiritJobManageMachine) {
            SpiritJobManageMachine job = (SpiritJobManageMachine) entity.getJob();
            if (job.getStorageControllerPosition() != null)
                ItemNBTUtil.setStorageControllerPosition(stack, job.getStorageControllerPosition());
            if (job.getManagedMachine() != null)
                ItemNBTUtil.setManagedMachine(stack, job.getManagedMachine());
        }
    }

    public static void generateBoundSpiritName(ItemStack stack) {
        setBoundSpiritName(stack, NameUtil.generateName());
    }

    public static void setBoundSpiritName(ItemStack stack, String name) {
        stack.setTagInfo(SPIRIT_NAME_TAG, new NBTTagString(name));
    }

    public static String getBoundSpiritName(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(SPIRIT_NAME_TAG))
            generateBoundSpiritName(stack);
        return stack.getTagCompound().getString(SPIRIT_NAME_TAG);
    }

    public static int getItemMode(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(ITEM_MODE_TAG))
            setItemMode(stack, 0);

        return stack.getTagCompound().getInteger(ITEM_MODE_TAG);
    }

    public static void setItemMode(ItemStack stack, int mode) {
        stack.setTagInfo(ITEM_MODE_TAG, new NBTTagInt(mode));
    }

    public static GlobalBlockPos getStorageControllerPosition(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(STORAGE_CONTROLLER_POSITION_TAG))
            return null;

        return GlobalBlockPos.fromNbt(stack.getTagCompound().getCompoundTag(STORAGE_CONTROLLER_POSITION_TAG));
    }

    public static void setStorageControllerPosition(ItemStack stack, GlobalBlockPos position) {
        if (position != null)
            stack.setTagInfo(STORAGE_CONTROLLER_POSITION_TAG, position.writeToNBT(new NBTTagCompound()));
    }

    public static MachineReference getManagedMachine(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(MANAGED_MACHINE))
            return null;

        return MachineReference.fromNbt(stack.getTagCompound().getCompoundTag(MANAGED_MACHINE));
    }

    public static void setManagedMachine(ItemStack stack, MachineReference position) {
        if (position != null)
            stack.setTagInfo(MANAGED_MACHINE, position.writeToNBT(new NBTTagCompound()));
    }

    public static GlobalBlockPos getDepositPosition(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(DEPOSIT_POSITION_TAG))
            return null;

        return GlobalBlockPos.fromNbt(stack.getTagCompound().getCompoundTag(DEPOSIT_POSITION_TAG));
    }

    public static void setDepositPosition(ItemStack stack, BlockPos position) {
        if (position != null)
            stack.setTagInfo(DEPOSIT_POSITION_TAG, new NBTTagLong(position.toLong()));
    }

    public static EnumFacing getDepositFacing(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(DEPOSIT_FACING_TAG))
            return null;
        return EnumFacing.values()[compound.getInteger(DEPOSIT_FACING_TAG)];
    }

    public static void setDepostFacing(ItemStack stack, EnumFacing facing) {
        stack.setTagInfo(DEPOSIT_POSITION_TAG, new NBTTagInt(facing.ordinal()));
    }

    public static BlockPos getWorkAreaPosition(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(WORK_AREA_POSITION_TAG))
            return null;

        return BlockPos.fromLong(stack.getTagCompound().getLong(WORK_AREA_POSITION_TAG));
    }

    public static void setWorkAreaPosition(ItemStack stack, BlockPos position) {
        if (position != null)
            stack.setTagInfo(WORK_AREA_POSITION_TAG, new NBTTagLong(position.toLong()));
    }

    public static WorkAreaSize getWorkAreaSize(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(WORK_AREA_SIZE_TAG))
            setWorkAreaSize(stack, WorkAreaSize.SMALL);

        return WorkAreaSize.get(stack.getTagCompound().getInteger(WORK_AREA_SIZE_TAG));
    }

    public static void setWorkAreaSize(ItemStack stack, WorkAreaSize workAreaSize) {
        stack.setTagInfo(WORK_AREA_SIZE_TAG, new NBTTagInt(workAreaSize.getValue()));
    }

    public static UUID getSpiritEntityUUID(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(SPIRIT_UUID_TAG))
            return null;
        return stack.getTagCompound().getCompoundTag(SPIRIT_UUID_TAG).getUniqueId("");
    }

    public static void setSpiritEntityUUID(ItemStack stack, UUID id) {
        NBTTagCompound uuidCompound = new NBTTagCompound();
        uuidCompound.setUniqueId("", id);
        stack.setTagInfo(SPIRIT_UUID_TAG, uuidCompound);
    }

    public static NBTTagCompound getSpiritEntityData(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(SPIRIT_DATA_TAG))
            return null;
        return stack.getTagCompound().getCompoundTag(SPIRIT_DATA_TAG);
    }

    public static void setSpiritEntityData(ItemStack stack, NBTTagCompound entityData) {
        stack.setTagInfo(SPIRIT_DATA_TAG, entityData);
    }

    public static EntitySpirit getSpiritEntity(ItemStack itemStack) {
        UUID entityUUID = getSpiritEntityUUID(itemStack);
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        if (server != null && entityUUID != null) {
            return (EntitySpirit) server.getEntityFromUuid(entityUUID);
        }
        return null;
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(key))
            setBoolean(stack, key, false);
        return stack.getTagCompound().getBoolean(key);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        stack.setTagInfo(key, new NBTTagByte((byte) (value ? 1 : 0)));
    }

    public static int getInteger(ItemStack stack, String key) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(key))
            setInteger(stack, key, 0);
        return stack.getTagCompound().getInteger(key);
    }

    public static void setInteger(ItemStack stack, String key, int value) {
        stack.setTagInfo(key, new NBTTagInt(value));
    }

    public static float getFloat(ItemStack stack, String key) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(key))
            setInteger(stack, key, 0);
        return stack.getTagCompound().getFloat(key);
    }

    public static void setFloat(ItemStack stack, String key, float value) {
        stack.setTagInfo(key, new NBTTagFloat(value));
    }

    public static String getString(ItemStack stack, String key) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null || !compound.hasKey(key))
            setInteger(stack, key, 0);
        return stack.getTagCompound().getString(key);
    }

    public static void setString(ItemStack stack, String key, String value) {
        stack.setTagInfo(key, new NBTTagString(value));
    }

    public static NBTTagCompound getTagCompound(ItemStack stack) {
        return stack.getTagCompound() == null ? new NBTTagCompound() : stack.getTagCompound();
    }

    //endregion Static Methods

}
