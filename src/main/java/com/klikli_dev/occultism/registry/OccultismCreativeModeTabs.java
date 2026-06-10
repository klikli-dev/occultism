package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.klikli_dev.occultism.common.item.debug.DebugSpawnEggItem;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Occultism.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OCCULTISM = CREATIVE_MODE_TABS.register("occultism",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.occultism"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        //General items and blocks
                        OccultismItems.ITEMS.getEntries().forEach(i -> {
                            if (OccultismItems.shouldSkipCreativeModTab(i.get())
                                    || i.get() instanceof SpawnEggItem
                                    || i.get() instanceof DebugSpawnEggItem
                                    || i.get() instanceof DummyTooltipItem
                                    || i.get() instanceof BlockItem)
                                return;
                            var stack = new ItemStack(i.get());
                            if (OccultismItems.shouldPregenerateSpiritName(i.get())) {
                                stack.set(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN);
                            }
                            output.accept(stack);
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OCCULTISM_BLOCKS = CREATIVE_MODE_TABS.register("occultism_blocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.occultism_blocks"))
                    .withTabsBefore(OccultismCreativeModeTabs.OCCULTISM.getId())
                    .icon(() -> OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        OccultismItems.ITEMS.getEntries().forEach(i -> {
                            if (i.get() instanceof BlockItem && !OccultismItems.shouldSkipCreativeModTab(i.get())) {
                                var stack = new ItemStack(i.get());
                                output.accept(stack);
                            }
                        });

                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OCCULTISM_EGGS = CREATIVE_MODE_TABS.register("occultism_eggs",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.occultism_eggs"))
                    .withTabsBefore(OccultismCreativeModeTabs.OCCULTISM.getId())
                    .icon(() -> OccultismItems.SPAWN_EGG_FOLIOT.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        OccultismItems.ITEMS.getEntries().forEach(i -> {
                            if ((i.get() instanceof SpawnEggItem || i.get() instanceof DebugSpawnEggItem)
                                    && !OccultismItems.shouldSkipCreativeModTab(i.get())) {
                                var stack = new ItemStack(i.get());
                                output.accept(stack);
                            }
                        });

                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OCCULTISM_DUMMY = CREATIVE_MODE_TABS.register("occultism_dummy",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.occultism_dummy"))
                    .withTabsBefore(OccultismCreativeModeTabs.OCCULTISM.getId())
                    .icon(() -> OccultismItems.PENTACLE_SUMMON.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        OccultismItems.ITEMS.getEntries().forEach(i -> {
                            if (i.get() instanceof DummyTooltipItem && !OccultismItems.shouldSkipCreativeModTab(i.get())) {
                                var stack = new ItemStack(i.get());
                                output.accept(stack);
                            }
                        });

                    }).build());

}
