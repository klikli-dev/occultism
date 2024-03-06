package com.klikli_dev.occultism.common.item.spirit.calling;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;

import java.util.HashMap;
import java.util.Map;

public class ItemModes {
    public static ItemMode SET_DEPOSIT;
    public static ItemMode SET_EXTRACT;
    public static ItemMode SET_BASE;
    public static ItemMode SET_STORAGE_CONTROLLER;
    public static ItemMode SET_MANAGED_MACHINE;

    private static final Map<Integer, ItemMode> lookup = new HashMap<Integer, ItemMode>();


    static {
        addItemMode(SET_DEPOSIT = new DepositItemMode());
        addItemMode(SET_EXTRACT = new ExtractItemMode());
        addItemMode(SET_BASE = new SetBaseItemMode());
        addItemMode(SET_STORAGE_CONTROLLER = new SetStorageLocationMode());
        addItemMode(SET_MANAGED_MACHINE = new SetManagedMachineMode());
    }

    public static void addItemMode(ItemMode itemMode) {
        itemMode.setValue(getSize());
        lookup.put(getSize(), itemMode);
    }

//    public static ItemMode get(int value) {
//        return lookup.get(value);
//    }

    public static int getSize() {
        return lookup.size();
    }

}
