package com.github.klikli_dev.occultism.common;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.item.ItemDebugWand;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class OccultismItems {

    public static DeferredRegister<Item> ITEMS = new DeferredRegister(ForgeRegistries.ITEMS, Occultism.MODID);
    //region Fields
    public static final RegistryObject<Item> PENTACLE = ITEMS.register("pentacle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand", () -> new ItemDebugWand(new Item.Properties().maxStackSize(1).group(Occultism.ITEM_GROUP)));
    //endregion Fields
}
