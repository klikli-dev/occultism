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
import com.github.klikli_dev.occultism.common.ritual.RitualFactory;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.registry.*;
import com.github.klikli_dev.occultism.util.loot.AppendLootTable;
import com.github.klikli_dev.occultism.util.loot.MatchBlockCondition;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
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
        new RegistryBuilder<RitualFactory>().setName(new ResourceLocation(Occultism.MODID, "ritual_factory"))
                .setType(RitualFactory.class).create();
        new RegistryBuilder<SpiritJobFactory>().setName(new ResourceLocation(Occultism.MODID, "spirit_job_factory"))
                .setType(SpiritJobFactory.class).create();

        OccultismSpiritJobs.JOBS.register(modEventBus);
        OccultismRituals.RITUAL_FACTORIES.register(modEventBus);
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
        registerSpawnEgg(registry, OccultismEntities.MARID_TYPE.get(), "marid", 0x4d4d4d, 0x1608d1);
        registerSpawnEgg(registry, OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(), "possessed_endermite", 0x161616,
                0x6E6E6E);
        registerSpawnEgg(registry, OccultismEntities.POSSESSED_SKELETON_TYPE.get(), "possessed_skeleton", 0xC1C1C1,
                0x494949);
        registerSpawnEgg(registry, OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(), "possessed_enderman", 0x161616,
                0x0);
        registerSpawnEgg(registry, OccultismEntities.WILD_HUNT_SKELETON_TYPE.get(), "wild_hunt_skeleton", 12698049,
                4802889);
        registerSpawnEgg(registry, OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get(), "wild_hunt_wither_skeleton",
                1315860, 4672845);
        registerSpawnEgg(registry, OccultismEntities.OTHERWORLD_BIRD_TYPE.get(), "otherworld_bird", 0x221269, 0x6b56c4);
        registerSpawnEgg(registry, OccultismEntities.GREEDY_FAMILIAR_TYPE.get(), "familiar_greedy", 0x54990f, 0x725025);
        registerSpawnEgg(registry, OccultismEntities.BAT_FAMILIAR_TYPE.get(), "familiar_bat", 0x434343, 0xd1865f);
        registerSpawnEgg(registry, OccultismEntities.DEER_FAMILIAR_TYPE.get(), "familiar_deer", 0xc9833e, 0xfffdf2);
        registerSpawnEgg(registry, OccultismEntities.CTHULHU_FAMILIAR_TYPE.get(), "familiar_cthulhu", 0x00cdc2, 0x4ae7c0);
        registerSpawnEgg(registry, OccultismEntities.DEVIL_FAMILIAR_TYPE.get(), "familiar_devil", 0xf2f0d7, 0xa01d1d);

        Occultism.LOGGER.info("Registered SpawnEggItems");

        //Register compostable items
        ComposterBlock.CHANCES.put(OccultismItems.DATURA_SEEDS.get(), 0.3f);
        ComposterBlock.CHANCES.put(OccultismBlocks.OTHERWORLD_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.CHANCES.put(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().asItem(), 0.3f);
        ComposterBlock.CHANCES.put(OccultismBlocks.OTHERWORLD_SAPLING.get().asItem(), 0.3f);
        ComposterBlock.CHANCES.put(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get().asItem(), 0.3f);

        ComposterBlock.CHANCES.put(OccultismItems.DATURA.get(), 0.65f);
        Occultism.LOGGER.info("Registered compostable Items");
    }

    public static void registerSpawnEgg(IForgeRegistry<Item> registry, EntityType<?> entityType,
                                        String name, int primaryColor, int secondaryColor) {
        SpawnEggItem spawnEggItem = new SpawnEggItem(entityType, primaryColor, secondaryColor,
                new Item.Properties().group(Occultism.ITEM_GROUP));
        spawnEggItem.setRegistryName(modLoc("spawn_egg/" + name));
        registry.register(spawnEggItem);
    }

    @SubscribeEvent
    public static void onRegisterGlobalLootModifierSerializer(
            RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {

        //Register the serializer to append to loot tables (see https://github.com/gigaherz/Survivalist/blob/87b2e6b547152262544020587f5a31a778f72899/src/main/java/gigaherz/survivalist/SurvivalistMod.java#L153)
        MatchBlockCondition.BLOCK_TAG_CONDITION = Registry.register(
                Registry.LOOT_CONDITION_TYPE,
                modLoc("match_block"),
                new LootConditionType(new MatchBlockCondition.Serializer()));
        event.getRegistry().register(new AppendLootTable.Serializer().setRegistryName(modLoc("append_loot")));
    }
    //endregion Static Methods
}
