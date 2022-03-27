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


        //Register compostable items
        ComposterBlock.COMPOSTABLES.put(OccultismItems.DATURA_SEEDS.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_SAPLING.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get().asItem(), 0.3f);

        ComposterBlock.COMPOSTABLES.put(OccultismItems.DATURA.get(), 0.65f);
        Occultism.LOGGER.info("Registered compostable Items");
    }


    @SubscribeEvent
    public static void onRegisterGlobalLootModifierSerializer(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new AddItemModifier.Serializer().setRegistryName(modLoc("add_item")));
    }
}
