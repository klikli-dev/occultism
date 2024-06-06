package com.klikli_dev.occultism.datafixer;

import com.klikli_dev.occultism.common.misc.StorageControllerItemStackHandler;
import com.klikli_dev.occultism.common.misc.StorageControllerMapItemStackHandler;
import net.minecraft.nbt.CompoundTag;

public class StorageControllerMapItemStackHandlerDataFixer {

    public static boolean needsFixing(CompoundTag compound) {
        return compound.contains("Items"); //Items indicates it is an old itemstackhandler
    }

    public static CompoundTag fix(CompoundTag compound) {
        var oldHandler = new StorageControllerItemStackHandler(null, 0, 0, true);
        var newHandler = new StorageControllerMapItemStackHandler(null, -1, -1);

        oldHandler.deserializeNBT(compound);

        for (int i = 0; i < oldHandler.getSlots(); i++) {
            var stack = oldHandler.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            newHandler.insertItem(stack, false);
        }

        return newHandler.serializeNBT();
    }
}
