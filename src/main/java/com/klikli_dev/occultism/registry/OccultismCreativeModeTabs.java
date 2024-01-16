package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryObject;

public class OccultismCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Occultism.MODID);


    public static final RegistryObject<CreativeModeTab> OCCULTISM = CREATIVE_MODE_TABS.register("occultism",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.occultism"))
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .icon(() -> OccultismItems.PENTACLE.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    OccultismItems.ITEMS.getEntries().forEach(i -> {

                    if (!OccultismItems.shouldSkipCreativeModTab(i.get())) {
                        output.accept(i.get());
                    }
                });
                output.accept(OccultismItems.DICTIONARY_OF_SPIRITS.get().getCreativeModeTabDisplayStack());
            }).build());

}