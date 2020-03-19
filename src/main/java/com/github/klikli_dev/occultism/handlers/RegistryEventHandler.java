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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.job.SpiritJobFactory;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEventHandler {

    //region Static Methods

    @SubscribeEvent
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        new RegistryBuilder<Pentacle>().setName(new ResourceLocation(Occultism.MODID, "pentacle"))
                .setType(Pentacle.class).create();
        new RegistryBuilder<Ritual>().setName(new ResourceLocation(Occultism.MODID, "ritual"))
                .setType(Ritual.class).create();
        new RegistryBuilder<SpiritJobFactory>().setName(new ResourceLocation(Occultism.MODID, "spirit_job_factory"))
                .setType(SpiritJobFactory.class).create();

        OccultismRituals.PENTACLES.register(modEventBus);
        OccultismRituals.RITUALS.register(modEventBus);
        OccultismSpiritJobs.JOBS.register(modEventBus);
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        // Register BlockItems for blocks without custom items
        OccultismBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> OccultismBlocks.BLOCK_DATA_GEN_SETTINGS
                                         .get(block.getRegistryName()).generateDefaultBlockItem).forEach(block -> {
            BlockItem blockItem = new BlockItem(block, new Item.Properties().group(Occultism.ITEM_GROUP));
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
        Occultism.LOGGER.info("Registered BlockItems");

        registerSpawnEgg(registry, OccultismEntities.FOLIOT_TYPE.get(), "foliot", 0xaa728d, 0x37222c);
        registerSpawnEgg(registry, OccultismEntities.DJINNI_TYPE.get(), "djinni", 0xaa728d, 0x37222c);
        registerSpawnEgg(registry, OccultismEntities.AFRIT_TYPE.get(), "afrit", 0xaa728d, 0x37222c);
        registerSpawnEgg(registry, OccultismEntities.AFRIT_WILD_TYPE.get(), "afrit_wild", 0xaa728d, 0x37222c);
        registerSpawnEgg(registry, OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(),"possessed_endermite", 0x161616, 0x6E6E6E);
        registerSpawnEgg(registry, OccultismEntities.POSSESSED_SKELETON_TYPE.get(),"possessed_skeleton", 0xC1C1C1, 0x494949);
        registerSpawnEgg(registry, OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(),"possessed_enderman", 0x161616, 0x0);
        registerSpawnEgg(registry, OccultismEntities.WILD_HUNT_SKELETON_TYPE.get(),"wild_hunt_skeleton", 12698049, 4802889);
        registerSpawnEgg(registry, OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get(),"wild_hunt_wither_skeleton", 1315860, 4672845);

        Occultism.LOGGER.info("Registered SpawnEggItems");
    }

    public static void registerSpawnEgg(IForgeRegistry<Item> registry, EntityType<?> entityType,
                                        String name, int primaryColor, int secondaryColor) {
        SpawnEggItem spawnEggItem = new SpawnEggItem(entityType, primaryColor, secondaryColor,
                new Item.Properties().group(Occultism.ITEM_GROUP));
        spawnEggItem.setRegistryName(modLoc("spawn_egg/" + name));
        registry.register(spawnEggItem);
    }
    //endregion Static Methods
}
