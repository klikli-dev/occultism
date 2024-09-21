package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Occultism.MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OCCULTISM = CREATIVE_MODE_TABS.register("occultism",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.occultism"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> OccultismItems.PENTACLE_SUMMON.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        OccultismItems.ITEMS.getEntries().forEach(i -> {


                            if (OccultismItems.shouldSkipCreativeModTab(i.get()))
                                return;

                            var stack = new ItemStack(i.get());

                            if (OccultismItems.shouldPregenerateSpiritName(i.get())) {
                                stack.set(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)");
                            }

                            output.accept(stack);

                        });

                        output.accept(OccultismItems.DICTIONARY_OF_SPIRITS.get().getCreativeModeTabDisplayStack());
                    }).build());

}