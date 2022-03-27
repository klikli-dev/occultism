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
import com.github.klikli_dev.occultism.loot.AddItemModifier;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEventHandler {

    @SubscribeEvent
    public static void onRegisterRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        //We're not actually registering recipe serializers, but we call the recipe types so they get registered before recipe serialization
        OccultismRecipes.SPIRIT_TRADE_TYPE.get();
        OccultismRecipes.SPIRIT_FIRE_TYPE.get();
        OccultismRecipes.CRUSHING_TYPE.get();
        OccultismRecipes.MINER_TYPE.get();
        OccultismRecipes.RITUAL_TYPE.get();
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        // Register BlockItems for blocks without custom items
        OccultismBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> OccultismBlocks.BLOCK_DATA_GEN_SETTINGS
                        .get(block.getRegistryName()).generateDefaultBlockItem).forEach(block -> {
                    BlockItem blockItem = new BlockItem(block, new Item.Properties().tab(Occultism.ITEM_GROUP));
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
        registerSpawnEgg(registry, OccultismEntities.BAT_FAMILIAR_TYPE.get(), "familiar_bat", 0x434343, 0xda95de);
        registerSpawnEgg(registry, OccultismEntities.DEER_FAMILIAR_TYPE.get(), "familiar_deer", 0xc9833e, 0xfffdf2);
        registerSpawnEgg(registry, OccultismEntities.CTHULHU_FAMILIAR_TYPE.get(), "familiar_cthulhu", 0x00cdc2, 0x4ae7c0);
        registerSpawnEgg(registry, OccultismEntities.DEVIL_FAMILIAR_TYPE.get(), "familiar_devil", 0xf2f0d7, 0xa01d1d);
        registerSpawnEgg(registry, OccultismEntities.DRAGON_FAMILIAR_TYPE.get(), "familiar_dragon", 0x18780f, 0x76c47b);
        registerSpawnEgg(registry, OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get(), "familiar_blacksmith", 0x06bc64, 0x2b2b2b);
        registerSpawnEgg(registry, OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get(), "familiar_guardian", 0x787878, 0x515151);
        registerSpawnEgg(registry, OccultismEntities.HEADLESS_FAMILIAR_TYPE.get(), "familiar_headless", 0x0c0606, 0xde7900);
        registerSpawnEgg(registry, OccultismEntities.CHIMERA_FAMILIAR_TYPE.get(), "familiar_chimera", 0xcf8441, 0x3e7922);
        registerSpawnEgg(registry, OccultismEntities.GOAT_FAMILIAR_TYPE.get(), "familiar_goat", 0xe2e2e2, 0x0f0f0e);
        registerSpawnEgg(registry, OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get(), "familiar_shub_niggurath", 0x362836, 0x594a3a);
        registerSpawnEgg(registry, OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get(), "familiar_beholder", 0x340a09, 0xfffbff);
        registerSpawnEgg(registry, OccultismEntities.FAIRY_FAMILIAR_TYPE.get(), "familiar_fairy", 0xbd674c, 0xcca896);
        registerSpawnEgg(registry, OccultismEntities.MUMMY_FAMILIAR_TYPE.get(), "familiar_mummy", 0xcbb76a, 0xe0d4a3);
        registerSpawnEgg(registry, OccultismEntities.BEAVER_FAMILIAR_TYPE.get(), "familiar_beaver", 0x824a2b, 0xdd9973);

        Occultism.LOGGER.info("Registered SpawnEggItems");

        //Register compostable items
        ComposterBlock.COMPOSTABLES.put(OccultismItems.DATURA_SEEDS.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_SAPLING.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get().asItem(), 0.3f);

        ComposterBlock.COMPOSTABLES.put(OccultismItems.DATURA.get(), 0.65f);
        Occultism.LOGGER.info("Registered compostable Items");
    }

    public static void registerSpawnEgg(IForgeRegistry<Item> registry, EntityType<? extends Mob> entityType,
                                        String name, int primaryColor, int secondaryColor) {
        SpawnEggItem spawnEggItem = new ForgeSpawnEggItem(() -> entityType, primaryColor, secondaryColor,
                new Item.Properties().tab(Occultism.ITEM_GROUP));
        spawnEggItem.setRegistryName(modLoc("spawn_egg/" + name));
        registry.register(spawnEggItem);
    }

    @SubscribeEvent
    public static void onRegisterGlobalLootModifierSerializer(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new AddItemModifier.Serializer().setRegistryName(modLoc("add_item")));
    }
}
