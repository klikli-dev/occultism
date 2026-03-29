/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.datagen.model;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Locale;

public class OccultismItemModelSubProvider {

    protected String name(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private void registerItemGenerated(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void registerItemGenerated(ItemModelGenerators itemModels, Item item, String texture) {
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(
                ModelTemplates.FLAT_ITEM.create(
                        ModelLocationUtils.getModelLocation(item),
                        TextureMapping.layer0(new Material(Identifier.fromNamespaceAndPath(Occultism.MODID, "item/" + texture))),
                        itemModels.modelOutput
                )
        ));
    }

    private void registerItemHandheld(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    private void registerItemFromBlock(ItemModelGenerators itemModels, Block block) {
        Identifier blockModel = Identifier.fromNamespaceAndPath(Occultism.MODID, "block/" + this.name(block.asItem()));
        Identifier itemModel = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/" + this.name(block.asItem()));
        itemModels.modelOutput.accept(itemModel, () -> {
            var json = new com.google.gson.JsonObject();
            json.addProperty("parent", blockModel.toString());
            return json;
        });
        itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModel));
    }

    private void registerRitualDummy(ItemModelGenerators itemModels, Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        String parent;
        if (path.contains("misc") || path.contains("contact") || path.contains("wild") || path.contains("resurrect")) {
            parent = "occultism:item/pentacle_misc";
        } else if (path.contains("craft") || path.contains("repair")) {
            parent = "occultism:item/pentacle_craft";
        } else if (path.contains("invoke") || path.contains("possess") || path.contains("familiar")) {
            parent = "occultism:item/pentacle_possess";
        } else if (path.contains("summon")) {
            parent = "occultism:item/pentacle_summon";
        } else {
            parent = "occultism:item/pentacle_misc";
        }
        Identifier itemModel = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/" + path);
        itemModels.modelOutput.accept(itemModel, () -> {
            var json = new com.google.gson.JsonObject();
            json.addProperty("parent", parent);
            return json;
        });
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(itemModel));
    }

    private void registerSpawnEgg(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    public void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Register ritual dummies and spawn eggs automatically
        BuiltInRegistries.ITEM.forEach(item -> {
            var key = BuiltInRegistries.ITEM.getKey(item);
            if (key.getPath().startsWith("ritual_dummy/")) {
                this.registerRitualDummy(itemModels, item);
            } else if (key.getPath().startsWith("spawn_egg/")) {
                this.registerSpawnEgg(itemModels, item);
            }
        });

        this.registerAdvancementItem(itemModels);
        this.registerItemCommon(itemModels);
        this.registerItemHandheldItems(itemModels);
        this.registerItemFromBlocks(itemModels, blockModels);
        this.registerItemMiners(itemModels);
        this.registerItemChalks(itemModels);
        this.registerItemCandles(itemModels, blockModels);
        this.registerVitalityCompass(itemModels);

        this.registerItemGenerated(itemModels, OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get(), "book_of_calling_manage_machine");
        this.registerItemGenerated(itemModels, OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get(), "book_of_calling_cleaner");
        this.registerItemGenerated(itemModels, OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get(), "book_of_calling_lumberjack");
        this.registerItemGenerated(itemModels, OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get(), "book_of_calling_farmer");
        this.registerItemGenerated(itemModels, OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get(), "book_of_calling_transport_items");

        this.registerItemGenerated(itemModels, OccultismItems.PENTACLE_SUMMON.get(), "ritual_dummy_summon");
        this.registerItemGenerated(itemModels, OccultismItems.PENTACLE_POSSESS.get(), "ritual_dummy_possess");
        this.registerItemGenerated(itemModels, OccultismItems.PENTACLE_CRAFT.get(), "ritual_dummy_craft");
        this.registerItemGenerated(itemModels, OccultismItems.PENTACLE_MISC.get(), "ritual_dummy_misc");
    }

    private void registerAdvancementItem(ItemModelGenerators itemModels) {
        String[] textures = {"cthulhu_icon", "bat_icon", "deer_icon", "devil_icon", "greedy_icon", "hat_icon",
                "dragon_icon", "blacksmith_icon", "guardian_icon", "headless_icon", "chimera_icon",
                "shub_niggurath_icon", "shub_niggurath_spawn_icon", "beholder_icon", "fairy_icon", "mummy_icon", "beaver_icon"};

        // Generate individual icon models
        for (int i = 0; i < textures.length; i++) {
            final String texture = textures[i];
            Identifier iconModel = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement/" + texture);
            itemModels.modelOutput.accept(iconModel, () -> {
                var json = new com.google.gson.JsonObject();
                json.addProperty("parent", "item/generated");
                var texturesJson = new com.google.gson.JsonObject();
                texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement/" + texture).toString());
                json.add("textures", texturesJson);
                return json;
            });
        }

        // Generate the main advancement_icon model with overrides
        Identifier mainModel = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement_icon");
        itemModels.modelOutput.accept(mainModel, () -> {
            var json = new com.google.gson.JsonObject();
            json.addProperty("parent", "item/generated");
            var texturesJson = new com.google.gson.JsonObject();
            texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement/" + textures[0]).toString());
            json.add("textures", texturesJson);

            var overrides = new com.google.gson.JsonArray();
            for (int i = 0; i < textures.length; i++) {
                var override = new com.google.gson.JsonObject();
                var predicate = new com.google.gson.JsonObject();
                predicate.addProperty("minecraft:custom_model_data", i);
                override.add("predicate", predicate);
                override.addProperty("model", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement/" + textures[i]).toString());
                overrides.add(override);
            }
            json.add("overrides", overrides);
            return json;
        });
        itemModels.itemModelOutput.accept(OccultismItems.ADVANCEMENT_ICON.get(), ItemModelUtils.plainModel(mainModel));
    }

    private void registerItemCommon(ItemModelGenerators itemModels) {
        Item[] items = {
                OccultismItems.AFRIT_ESSENCE.get(),
                OccultismItems.AMETHYST_DUST.get(),
                OccultismItems.AWAKENED_FEATHER.get(),
                OccultismItems.BEAVER_NUGGET.get(),
                OccultismItems.BOOK_OF_BINDING_AFRIT.get(),
                OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get(),
                OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get(),
                OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get(),
                OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get(),
                OccultismItems.BOOK_OF_BINDING_DJINNI.get(),
                OccultismItems.BOOK_OF_BINDING_EMPTY.get(),
                OccultismItems.BOOK_OF_BINDING_FOLIOT.get(),
                OccultismItems.BOOK_OF_BINDING_MARID.get(),
                OccultismItems.BRUSH.get(),
                OccultismItems.BURNT_OTHERROCK.get(),
                OccultismItems.BURNT_OTHERSTONE.get(),
                OccultismItems.CHALK_RAINBOW.get(),
                OccultismItems.CHALK_VOID.get(),
                OccultismItems.COPPER_DUST.get(),
                OccultismItems.CRUELTY_ESSENCE.get(),
                OccultismItems.CRUSHED_BLACKSTONE.get(),
                OccultismItems.CRUSHED_BLUE_ICE.get(),
                OccultismItems.CRUSHED_CALCITE.get(),
                OccultismItems.CRUSHED_END_STONE.get(),
                OccultismItems.CRUSHED_ICE.get(),
                OccultismItems.CRUSHED_PACKED_ICE.get(),
                OccultismItems.CURSED_HONEY.get(),
                OccultismItems.DATURA.get(),
                OccultismItems.DATURA_SEEDS.get(),
                OccultismItems.DEBUG_WAND.get(),
                OccultismItems.DEMONIC_MEAT.get(),
                OccultismItems.DEMONS_DREAM_ESSENCE.get(),
                OccultismItems.DRAGONYST_DUST.get(),
                OccultismItems.ENDER_SATCHEL.get(),
                OccultismItems.ECHO_DUST.get(),
                OccultismItems.EMERALD_DUST.get(),
                OccultismItems.FLAME_AUTOMATION.get(),
                OccultismItems.FAMILIAR_RING.get(),
                OccultismItems.GOLD_DUST.get(),
                OccultismItems.GRAY_PASTE.get(),
                OccultismItems.IESNIUM_DUST.get(),
                OccultismItems.IESNIUM_INGOT.get(),
                OccultismItems.IESNIUM_NUGGET.get(),
                OccultismItems.INFUSED_LENSES.get(),
                OccultismItems.IRON_DUST.get(),
                OccultismItems.KNOWLEDGE_TABLET.get(),
                OccultismItems.LAPIS_DUST.get(),
                OccultismItems.LENS_FRAME.get(),
                OccultismItems.LENSES.get(),
                OccultismItems.MAGIC_LAMP_EMPTY.get(),
                OccultismItems.MARID_ESSENCE.get(),
                OccultismItems.MINING_DIMENSION_CORE_PIECE.get(),
                OccultismItems.MYSTERIOUS_EGG_ICON.get(),
                OccultismItems.NATURE_PASTE.get(),
                OccultismItems.NETHERITE_DUST.get(),
                OccultismItems.NETHERITE_SCRAP_DUST.get(),
                OccultismItems.OBSIDIAN_DUST.get(),
                OccultismItems.OTHERROCK_FRAME.get(),
                OccultismItems.OTHERSTONE_FRAME.get(),
                OccultismItems.OTHERWORLDLY_TABLET.get(),
                OccultismItems.OTHERWORLD_ASHES.get(),
                OccultismItems.OTHERWORLD_ESSENCE.get(),
                OccultismItems.PURIFIED_INK.get(),
                OccultismItems.REPAIR_ICON.get(),
                OccultismItems.RESEARCH_FRAGMENT_DUST.get(),
                OccultismItems.RESURRECT_ICON.get(),
                OccultismItems.RITUAL_SATCHEL_T1.get(),
                OccultismItems.RITUAL_SATCHEL_T2.get(),
                OccultismItems.RAW_IESNIUM.get(),
                OccultismItems.RAW_SILVER.get(),
                OccultismItems.SATCHEL.get(),
                OccultismItems.SILVER_DUST.get(),
                OccultismItems.SILVER_INGOT.get(),
                OccultismItems.SILVER_NUGGET.get(),
                OccultismItems.SOUL_SHARD_ITEM.get(),
                OccultismItems.SOUL_SHATTERED_ITEM.get(),
                OccultismItems.SPIRIT_ATTUNED_GEM.get(),
                OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get(),
                OccultismBlocks.SPIRIT_CAMPFIRE.asItem(),
                OccultismBlocks.SPIRIT_LANTERN.asItem(),
                OccultismItems.STORAGE_REMOTE_INERT.get(),
                OccultismItems.SWEET_HONEY_HEART.get(),
                OccultismItems.TABOO_BOOK.get(),
                OccultismItems.TALLOW.get(),
                OccultismItems.WITHERITE_DUST.get(),
                OccultismItems.WORMHOLE_PORTAL.get()
        };
        for (Item item : items) {
            this.registerItemGenerated(itemModels, item);
        }
    }

    private void registerItemHandheldItems(ItemModelGenerators itemModels) {
        Item[] items = {
                OccultismItems.BUTCHER_KNIFE.get(),
                OccultismItems.IESNIUM_BUTCHER_KNIFE.get(),
                OccultismItems.IESNIUM_PICKAXE.get(),
                OccultismItems.INFUSED_PICKAXE.get()
        };
        for (Item item : items) {
            this.registerItemHandheld(itemModels, item);
        }
    }

    private void registerItemFromBlocks(ItemModelGenerators itemModels, BlockModelGenerators blockModels) {
        Block[] blocks = {
                OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.CELESTIAL_CHALICE.get(),
                OccultismBlocks.ELDRITCH_CHALICE.get(),
                OccultismBlocks.DIMENSIONAL_MINESHAFT.get(),
                OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get(),
                OccultismBlocks.DIMENSIONAL_EXTRACTOR.get(),
                OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.IESNIUM_BLOCK.get(),
                OccultismBlocks.IESNIUM_ORE.get(),
                OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.LARGE_CANDLE.get(),
                OccultismBlocks.OTHERCOBBLESTONE.get(),
                OccultismBlocks.OTHERCOBBLESTONE_SLAB.get(),
                OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get(),
                OccultismBlocks.OTHERCOBBLEROCK.get(),
                OccultismBlocks.OTHERCOBBLEROCK_SLAB.get(),
                OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get(),
                OccultismBlocks.OTHERGLASS_NATURAL.get(),
                OccultismBlocks.OTHERPLANKS.get(),
                OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(),
                OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get(),
                OccultismBlocks.OTHERPLANKS_SLAB.get(),
                OccultismBlocks.OTHERPLANKS_STAIRS.get(),
                OccultismBlocks.OTHERSTONE.get(),
                OccultismBlocks.OTHERSTONE_BRICKS.get(),
                OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get(),
                OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get(),
                OccultismBlocks.OTHERSTONE_PEDESTAL.get(),
                OccultismBlocks.OTHERROCK_PEDESTAL.get(),
                OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get(),
                OccultismBlocks.OTHERSTONE_SLAB.get(),
                OccultismBlocks.OTHERSTONE_STAIRS.get(),
                OccultismBlocks.OTHERROCK.get(),
                OccultismBlocks.OTHERROCK_BRICKS.get(),
                OccultismBlocks.OTHERROCK_BRICKS_SLAB.get(),
                OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get(),
                OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get(),
                OccultismBlocks.OTHERROCK_SLAB.get(),
                OccultismBlocks.OTHERROCK_STAIRS.get(),
                OccultismBlocks.OTHERWORLD_LEAVES.get(),
                OccultismBlocks.OTHERWORLD_LOG.get(),
                OccultismBlocks.OTHERWORLD_WOOD.get(),
                OccultismBlocks.POLISHED_OTHERSTONE.get(),
                OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get(),
                OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get(),
                OccultismBlocks.POLISHED_OTHERROCK.get(),
                OccultismBlocks.POLISHED_OTHERROCK_SLAB.get(),
                OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get(),
                OccultismBlocks.RAW_IESNIUM_BLOCK.get(),
                OccultismBlocks.RAW_SILVER_BLOCK.get(),
                OccultismBlocks.SACRIFICIAL_BOWL.get(),
                OccultismBlocks.SILVER_BLOCK.get(),
                OccultismBlocks.SILVER_ORE.get(),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get(),
                OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get(),
                OccultismBlocks.SPIRIT_GRINDSTONE.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER0.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER1.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER2.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER3.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER4.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER5.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get(),
                OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get(),
                OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get(),
                OccultismBlocks.TALLOW_BLOCK.get()
        };
        for (Block block : blocks) {
            this.registerItemFromBlock(itemModels, block);
        }
    }

    private void registerItemMiners(ItemModelGenerators itemModels) {
        Item[] items = {
                OccultismItems.MINER_AFRIT_DEEPS.get(),
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get(),
                OccultismItems.MINER_DJINNI_ORES.get(),
                OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_MARID_MASTER.get(),
                OccultismItems.MINER_ANCIENT_ELDRITCH.get()
        };
        for (Item item : items) {
            // Miners use the magic_lamp texture
            this.registerItemGenerated(itemModels, item, "magic_lamp");
        }
    }

    private void registerItemChalks(ItemModelGenerators itemModels) {
        Item[] chalks = {
                OccultismItems.CHALK_BLACK.get(),
                OccultismItems.CHALK_BLUE.get(),
                OccultismItems.CHALK_BROWN.get(),
                OccultismItems.CHALK_CYAN.get(),
                OccultismItems.CHALK_YELLOW.get(),
                OccultismItems.CHALK_GRAY.get(),
                OccultismItems.CHALK_GREEN.get(),
                OccultismItems.CHALK_LIGHT_BLUE.get(),
                OccultismItems.CHALK_LIGHT_GRAY.get(),
                OccultismItems.CHALK_LIME.get(),
                OccultismItems.CHALK_MAGENTA.get(),
                OccultismItems.CHALK_ORANGE.get(),
                OccultismItems.CHALK_PINK.get(),
                OccultismItems.CHALK_PURPLE.get(),
                OccultismItems.CHALK_RED.get(),
                OccultismItems.CHALK_WHITE.get()
        };
        for (Item item : chalks) {
            this.registerItemGenerated(itemModels, item, "chalk_base");
        }

        Item[] chalksImpure = {
                OccultismItems.CHALK_BLACK_IMPURE.get(),
                OccultismItems.CHALK_BLUE_IMPURE.get(),
                OccultismItems.CHALK_BROWN_IMPURE.get(),
                OccultismItems.CHALK_CYAN_IMPURE.get(),
                OccultismItems.CHALK_YELLOW_IMPURE.get(),
                OccultismItems.CHALK_GRAY_IMPURE.get(),
                OccultismItems.CHALK_GREEN_IMPURE.get(),
                OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get(),
                OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get(),
                OccultismItems.CHALK_LIME_IMPURE.get(),
                OccultismItems.CHALK_MAGENTA_IMPURE.get(),
                OccultismItems.CHALK_ORANGE_IMPURE.get(),
                OccultismItems.CHALK_PINK_IMPURE.get(),
                OccultismItems.CHALK_PURPLE_IMPURE.get(),
                OccultismItems.CHALK_RED_IMPURE.get(),
                OccultismItems.CHALK_WHITE_IMPURE.get()
        };
        for (Item item : chalksImpure) {
            // Impure chalks have two layers
            Identifier modelId = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/" + this.name(item));
            itemModels.modelOutput.accept(modelId, () -> {
                var json = new com.google.gson.JsonObject();
                json.addProperty("parent", "item/generated");
                var texturesJson = new com.google.gson.JsonObject();
                texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/chalk_base").toString());
                texturesJson.addProperty("layer1", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/chalk_base_impure").toString());
                json.add("textures", texturesJson);
                return json;
            });
            itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelId));
        }
    }

    private void registerItemCandles(ItemModelGenerators itemModels, BlockModelGenerators blockModels) {
        Block[] candles = {
                OccultismBlocks.LARGE_CANDLE_WHITE.get(),
                OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get(),
                OccultismBlocks.LARGE_CANDLE_GRAY.get(),
                OccultismBlocks.LARGE_CANDLE_BLACK.get(),
                OccultismBlocks.LARGE_CANDLE_BROWN.get(),
                OccultismBlocks.LARGE_CANDLE_RED.get(),
                OccultismBlocks.LARGE_CANDLE_ORANGE.get(),
                OccultismBlocks.LARGE_CANDLE_YELLOW.get(),
                OccultismBlocks.LARGE_CANDLE_LIME.get(),
                OccultismBlocks.LARGE_CANDLE_GREEN.get(),
                OccultismBlocks.LARGE_CANDLE_CYAN.get(),
                OccultismBlocks.LARGE_CANDLE_BLUE.get(),
                OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get(),
                OccultismBlocks.LARGE_CANDLE_PINK.get(),
                OccultismBlocks.LARGE_CANDLE_MAGENTA.get(),
                OccultismBlocks.LARGE_CANDLE_PURPLE.get(),
        };
        for (Block block : candles) {
            this.registerItemFromBlock(itemModels, block);
        }
    }

    private void registerVitalityCompass(ItemModelGenerators itemModels) {
        for (int i = 0; i < 32; i++) {
            String suffix = String.format(Locale.ROOT, "_%02d", i);
            Identifier modelId = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/vitality_compass/compass" + suffix);
            itemModels.modelOutput.accept(modelId, () -> {
                var json = new com.google.gson.JsonObject();
                json.addProperty("parent", "item/generated");
                var texturesJson = new com.google.gson.JsonObject();
                texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/vitality_compass/compass_base").toString());
                texturesJson.addProperty("layer1", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/vitality_compass/compass" + suffix).toString());
                json.add("textures", texturesJson);
                return json;
            });
        }
    }
}
