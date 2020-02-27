package com.github.klikli_dev.occultism.common;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

//region Static Methods
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        // Register BlockItems for blocks without custom items
        OccultismBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(block -> !OccultismBlocks.hasCustomItemBlock(block))
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().group(Occultism.ITEM_GROUP);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    blockItem.setRegistryName(block.getRegistryName());
                    registry.register(blockItem);
                });
        Occultism.logger.debug("Registered BlockItems");
    }

//endregion Static Methods

}
