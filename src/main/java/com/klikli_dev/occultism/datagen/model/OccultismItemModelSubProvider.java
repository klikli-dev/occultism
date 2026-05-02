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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.itemproperties.DivinationDistanceItemPropertyGetter;
import com.klikli_dev.occultism.client.itemproperties.SoulGemItemPropertyGetter;
import com.klikli_dev.occultism.client.itemproperties.StorageRemoteItemPropertyGetter;
import com.klikli_dev.occultism.client.itemproperties.VitalityCompassItemPropertyGetter;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ConditionalItemModel.Unbaked;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class OccultismItemModelSubProvider {

    private static final Map<String, Integer> CHALK_COLORS = Map.ofEntries(
            Map.entry("chalk_white", 0xFFFFFF),
            Map.entry("chalk_light_gray", 0x9D9D97),
            Map.entry("chalk_gray", 0x474F52),
            Map.entry("chalk_black", 0x1D1D21),
            Map.entry("chalk_brown", 0x835432),
            Map.entry("chalk_red", 0xB02E26),
            Map.entry("chalk_orange", 0xF9801D),
            Map.entry("chalk_gold", 0xFED83D),
            Map.entry("chalk_yellow", 0xFED83D),
            Map.entry("chalk_lime", 0x80C71F),
            Map.entry("chalk_green", 0x5E7C16),
            Map.entry("chalk_cyan", 0x169C9C),
            Map.entry("chalk_light_blue", 0x3AB3DA),
            Map.entry("chalk_blue", 0x3C44AA),
            Map.entry("chalk_purple", 0x8932B8),
            Map.entry("chalk_magenta", 0xC74EBD),
            Map.entry("chalk_pink", 0xF38BAA)
    );

    private static int opaque(int color) {
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    }

    public Stream<Item> getKnownItems() {
        return Stream.of(
                OccultismItems.ADVANCEMENT_ICON.get(),
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
                OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get(),
                OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get(),
                OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get(),
                OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get(),
                OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get(),
                OccultismItems.BRUSH.get(),
                OccultismItems.BURNT_OTHERROCK.get(),
                OccultismItems.BURNT_OTHERSTONE.get(),
                OccultismItems.BUTCHER_KNIFE.get(),
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
                OccultismItems.CHALK_WHITE.get(),
                OccultismItems.CHALK_RAINBOW.get(),
                OccultismItems.CHALK_VOID.get(),
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
                OccultismItems.CHALK_WHITE_IMPURE.get(),
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
                OccultismItems.ECHO_DUST.get(),
                OccultismItems.EMERALD_DUST.get(),
                OccultismItems.ENDER_SATCHEL.get(),
                OccultismItems.FAMILIAR_RING.get(),
                OccultismItems.FLAME_AUTOMATION.get(),
                OccultismItems.GOLD_DUST.get(),
                OccultismItems.GRAY_PASTE.get(),
                OccultismItems.IESNIUM_BUTCHER_KNIFE.get(),
                OccultismItems.IESNIUM_DUST.get(),
                OccultismItems.IESNIUM_INGOT.get(),
                OccultismItems.IESNIUM_NUGGET.get(),
                OccultismItems.IESNIUM_PICKAXE.get(),
                OccultismItems.INFUSED_LENSES.get(),
                OccultismItems.INFUSED_PICKAXE.get(),
                OccultismItems.IRON_DUST.get(),
                OccultismItems.KNOWLEDGE_TABLET.get(),
                OccultismItems.LAPIS_DUST.get(),
                OccultismItems.LENS_FRAME.get(),
                OccultismItems.LENSES.get(),
                OccultismItems.MAGIC_LAMP_EMPTY.get(),
                OccultismItems.MARID_ESSENCE.get(),
                OccultismItems.MINER_AFRIT_DEEPS.get(),
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get(),
                OccultismItems.MINER_DJINNI_ORES.get(),
                OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_MARID_MASTER.get(),
                OccultismItems.MINER_ANCIENT_ELDRITCH.get(),
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
                OccultismItems.PENTACLE_SUMMON.get(),
                OccultismItems.PENTACLE_POSSESS.get(),
                OccultismItems.PENTACLE_CRAFT.get(),
                OccultismItems.PENTACLE_MISC.get(),
                OccultismItems.PURIFIED_INK.get(),
                OccultismItems.RAW_IESNIUM.get(),
                OccultismItems.RAW_SILVER.get(),
                OccultismItems.REPAIR_ICON.get(),
                OccultismItems.RESEARCH_FRAGMENT_DUST.get(),
                OccultismItems.RESURRECT_ICON.get(),
                OccultismItems.RITUAL_SATCHEL_T1.get(),
                OccultismItems.RITUAL_SATCHEL_T2.get(),
                OccultismItems.SATCHEL.get(),
                OccultismItems.SILVER_DUST.get(),
                OccultismItems.SILVER_INGOT.get(),
                OccultismItems.SILVER_NUGGET.get(),
                OccultismItems.SOUL_SHARD_ITEM.get(),
                OccultismItems.SOUL_SHATTERED_ITEM.get(),
                OccultismItems.SPIRIT_ATTUNED_GEM.get(),
                OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get(),
                OccultismItems.STORAGE_REMOTE_INERT.get(),
                OccultismItems.SWEET_HONEY_HEART.get(),
                OccultismItems.TABOO_BOOK.get(),
                OccultismItems.TALLOW.get(),
                OccultismItems.WITHERITE_DUST.get(),
                OccultismItems.WORMHOLE_PORTAL.get(),
                OccultismBlocks.SPIRIT_CAMPFIRE.asItem(),
                OccultismBlocks.SPIRIT_LANTERN.asItem(),
                OccultismBlocks.LARGE_CANDLE_WHITE.asItem(),
                OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.asItem(),
                OccultismBlocks.LARGE_CANDLE_GRAY.asItem(),
                OccultismBlocks.LARGE_CANDLE_BLACK.asItem(),
                OccultismBlocks.LARGE_CANDLE_BROWN.asItem(),
                OccultismBlocks.LARGE_CANDLE_RED.asItem(),
                OccultismBlocks.LARGE_CANDLE_ORANGE.asItem(),
                OccultismBlocks.LARGE_CANDLE_YELLOW.asItem(),
                OccultismBlocks.LARGE_CANDLE_LIME.asItem(),
                OccultismBlocks.LARGE_CANDLE_GREEN.asItem(),
                OccultismBlocks.LARGE_CANDLE_CYAN.asItem(),
                OccultismBlocks.LARGE_CANDLE_BLUE.asItem(),
                OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.asItem(),
                OccultismBlocks.LARGE_CANDLE_PINK.asItem(),
                OccultismBlocks.LARGE_CANDLE_MAGENTA.asItem(),
                OccultismBlocks.LARGE_CANDLE_PURPLE.asItem()
        );
    }

    protected String name(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(Occultism.MODID, path);
    }

    private void registerItemGenerated(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void registerItemGenerated(ItemModelGenerators itemModels, Item item, String texture) {
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(
                ModelTemplates.FLAT_ITEM.create(
                        this.modLoc("item/" + this.name(item)),
                        TextureMapping.layer0(new Material(this.modLoc("item/" + texture))),
                        itemModels.modelOutput
                )
        ));
    }

    private void registerTintedItemGenerated(ItemModelGenerators itemModels, Item item, String texture, int color) {
        Identifier modelId = ModelTemplates.FLAT_ITEM.create(
                this.modLoc("item/" + this.name(item)),
                TextureMapping.layer0(new Material(this.modLoc("item/" + texture))),
                itemModels.modelOutput
        );
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(modelId,
                ItemModelUtils.constantTint(opaque(color))));
    }

    private void registerImpureChalk(ItemModelGenerators itemModels, Item item, int color) {
        Identifier modelId = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/" + this.name(item));
        itemModels.modelOutput.accept(modelId, () -> {
            var json = new JsonObject();
            json.addProperty("parent", "item/generated");
            var texturesJson = new JsonObject();
            texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/chalk_base").toString());
            texturesJson.addProperty("layer1", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/chalk_base_impure").toString());
            json.add("textures", texturesJson);
            return json;
        });
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(modelId,
                ItemModelUtils.constantTint(opaque(color)),
                ItemModelUtils.constantTint(opaque(0xFFFFFF))));
    }

    private void registerItemHandheld(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    private void registerItemFromBlock(ItemModelGenerators itemModels, Block block) {
        itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(this.modLoc("block/" + this.name(block.asItem()))));
    }

    private void registerItemExistingModel(ItemModelGenerators itemModels, Item item) {
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.modLoc("item/" + this.name(item))));
    }

    private void registerConditionalItemDefinition(ItemModelGenerators itemModels, Item item,
                                                   ConditionalItemModelProperty property,
                                                   String onTrueModelPath, String onFalseModelPath) {
        itemModels.itemModelOutput.accept(item, new Unbaked(
                Optional.empty(),
                property,
                ItemModelUtils.plainModel(this.modLoc(onTrueModelPath)),
                ItemModelUtils.plainModel(this.modLoc(onFalseModelPath))
        ));
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
            var json = new JsonObject();
            json.addProperty("parent", parent);
            return json;
        });
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(itemModel));
    }

    private void registerSpawnEgg(ItemModelGenerators itemModels, Item item) {
        var colors = this.getSpawnEggColors(item);
        var modelId = this.modLoc("item/template_spawn_egg");
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(modelId,
                ItemModelUtils.constantTint(opaque(colors.primaryColor())),
                ItemModelUtils.constantTint(opaque(colors.secondaryColor()))));
    }

    private void registerSpawnEggTemplate(ItemModelGenerators itemModels) {
        Identifier modelId = this.modLoc("item/template_spawn_egg");
        itemModels.modelOutput.accept(modelId, () -> {
            var json = new JsonObject();
            json.addProperty("parent", "item/generated");
            var texturesJson = new JsonObject();
            texturesJson.addProperty("layer0", this.modLoc("item/spawn_egg").toString());
            texturesJson.addProperty("layer1", this.modLoc("item/spawn_egg_overlay").toString());
            json.add("textures", texturesJson);
            return json;
        });
    }

    private SpawnEggColors getSpawnEggColors(Item item) {
        return switch (BuiltInRegistries.ITEM.getKey(item).getPath()) {
            case "spawn_egg/foliot" -> new SpawnEggColors(0x8d5454, 0x1f1f1f);
            case "spawn_egg/djinni" -> new SpawnEggColors(0x073f7c, 0xc9d631);
            case "spawn_egg/afrit" -> new SpawnEggColors(0x5d241a, 0x946510);
            case "spawn_egg/afrit_unbound" -> new SpawnEggColors(0x4d140a, 0x744500);
            case "spawn_egg/marid" -> new SpawnEggColors(0x396265, 0x57c786);
            case "spawn_egg/marid_unbound" -> new SpawnEggColors(0x394245, 0x57a766);
            case "spawn_egg/wondering_trader" -> new SpawnEggColors(0x375482, 0xbf886d);
            case "spawn_egg/possessed_endermite" -> new SpawnEggColors(0x161616, 0x6e6e6e);
            case "spawn_egg/possessed_skeleton" -> new SpawnEggColors(0xc1c1c1, 0x494949);
            case "spawn_egg/possessed_enderman" -> new SpawnEggColors(0x161616, 0x000000);
            case "spawn_egg/possessed_ghast" -> new SpawnEggColors(0xe2e2e2, 0xc1c1c1);
            case "spawn_egg/possessed_phantom" -> new SpawnEggColors(0x3f4c81, 0x6ccc00);
            case "spawn_egg/possessed_weak_shulker" -> new SpawnEggColors(0x8c628c, 0x342638);
            case "spawn_egg/possessed_shulker" -> new SpawnEggColors(0x8c628c, 0x342638);
            case "spawn_egg/possessed_elder_guardian" -> new SpawnEggColors(0xb5b3a3, 0x4b4d60);
            case "spawn_egg/possessed_witch" -> new SpawnEggColors(0x280000, 0x346828);
            case "spawn_egg/possessed_blaze" -> new SpawnEggColors(0xe8a700, 0xb4af58);
            case "spawn_egg/possessed_zombie_piglin" -> new SpawnEggColors(0xdb8a8a, 0x6a8c46);
            case "spawn_egg/possessed_bee" -> new SpawnEggColors(0xd6b03c, 0x060606);
            case "spawn_egg/possessed_guardian" -> new SpawnEggColors(0x70978a, 0xff9233);
            case "spawn_egg/possessed_goat" -> new SpawnEggColors(0xa0a0a0, 0x835432);
            case "spawn_egg/wild_hunt_skeleton" -> new SpawnEggColors(0xc1c1c1, 0x494949);
            case "spawn_egg/wild_hunt_wither_skeleton" -> new SpawnEggColors(0x141414, 0x474d4d);
            case "spawn_egg/possessed_warden" -> new SpawnEggColors(0x0f4649, 0x39d6e0);
            case "spawn_egg/possessed_hoglin" -> new SpawnEggColors(0x592a10, 0xf9f3a4);
            case "spawn_egg/wild_horde_husk" -> new SpawnEggColors(0x5f584c, 0x92815e);
            case "spawn_egg/wild_horde_drowned" -> new SpawnEggColors(0x7bcfb9, 0x577148);
            case "spawn_egg/wild_horde_creeper" -> new SpawnEggColors(0x577148, 0x111111);
            case "spawn_egg/wild_horde_silverfish" -> new SpawnEggColors(0x666666, 0x262626);
            case "spawn_egg/wild_weak_breeze" -> new SpawnEggColors(0xa289cf, 0x5d428f);
            case "spawn_egg/wild_breeze" -> new SpawnEggColors(0x9279bf, 0x4d327f);
            case "spawn_egg/wild_strong_breeze" -> new SpawnEggColors(0x8269af, 0x3d226f);
            case "spawn_egg/wild_evoker" -> new SpawnEggColors(0x8e9494, 0xcbc786);
            case "spawn_egg/otherworld_bird" -> new SpawnEggColors(0x221269, 0x6b56c4);
            case "spawn_egg/familiar_greedy" -> new SpawnEggColors(0x54990f, 0x725025);
            case "spawn_egg/familiar_bat" -> new SpawnEggColors(0x434343, 0xda95de);
            case "spawn_egg/familiar_deer" -> new SpawnEggColors(0xc9833e, 0xfffdf2);
            case "spawn_egg/familiar_cthulhu" -> new SpawnEggColors(0x00cdc2, 0x4ae7c0);
            case "spawn_egg/familiar_devil" -> new SpawnEggColors(0xf2f0d7, 0xa01d1d);
            case "spawn_egg/familiar_dragon" -> new SpawnEggColors(0x18780f, 0x76c47b);
            case "spawn_egg/familiar_blacksmith" -> new SpawnEggColors(0x06bc64, 0x2b2b2b);
            case "spawn_egg/familiar_guardian" -> new SpawnEggColors(0x787878, 0x515151);
            case "spawn_egg/familiar_headless" -> new SpawnEggColors(0x0c0606, 0xde7900);
            case "spawn_egg/familiar_chimera" -> new SpawnEggColors(0xcf8441, 0x3e7922);
            case "spawn_egg/familiar_goat" -> new SpawnEggColors(0xe2e2e2, 0x0f0f0e);
            case "spawn_egg/familiar_shub_niggurath" -> new SpawnEggColors(0x362836, 0x594a3a);
            case "spawn_egg/familiar_beholder" -> new SpawnEggColors(0x340a09, 0xfffbff);
            case "spawn_egg/familiar_fairy" -> new SpawnEggColors(0xbd674c, 0xcca896);
            case "spawn_egg/familiar_mummy" -> new SpawnEggColors(0xcbb76a, 0xe0d4a3);
            case "spawn_egg/familiar_beaver" -> new SpawnEggColors(0x824a2b, 0xdd9973);
            case "spawn_egg/demonic_wife" -> new SpawnEggColors(0xf2f0d7, 0xa01d1d);
            case "spawn_egg/demonic_husband" -> new SpawnEggColors(0xf2f0d7, 0xa01d1d);
            case "spawn_egg/iesnium_golem" -> new SpawnEggColors(0x94d4db, 0x345f7c);
            default ->
                    throw new IllegalArgumentException("Missing spawn egg colors for " + BuiltInRegistries.ITEM.getKey(item));
        };
    }

    public void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerSpawnEggTemplate(itemModels);

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
        // Block item models are handled by OccultismBlockModelSubProvider
        this.registerItemMiners(itemModels);
        this.registerItemChalks(itemModels);
        this.registerItemCandles(itemModels, blockModels);
        this.registerVitalityCompass(itemModels);
        this.registerConditionalItemDefinitions(itemModels);
        this.registerDivinationRod(itemModels);
        this.registerTrueSightStaff(itemModels);
        this.registerManualItemModels(itemModels);

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

    private void registerManualItemModels(ItemModelGenerators itemModels) {
        Item[] manualItems = {
                OccultismItems.DICTIONARY_OF_SPIRITS.get(),
                OccultismItems.OTHERWORLD_GOGGLES.get(),
                OccultismItems.DIMENSIONAL_MATRIX.get(),
                OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get(),
                OccultismItems.DEBUG_FOLIOT_LUMBERJACK.get(),
                OccultismItems.DEBUG_FOLIOT_FARMER.get(),
                OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS.get(),
                OccultismItems.DEBUG_FOLIOT_CLEANER.get(),
                OccultismItems.DEBUG_FOLIOT_TRADER_ITEM.get(),
                OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE.get(),
                OccultismItems.DEBUG_DJINNI_TEST.get(),
                OccultismItems.JEI_DUMMY_NONE.get(),
                OccultismItems.JEI_DUMMY_REQUIRE_SACRIFICE.get(),
                OccultismItems.JEI_DUMMY_REQUIRE_ITEM_USE.get(),
                OccultismItems.OTHERPLANKS_SIGN.get(),
                OccultismItems.OTHERPLANKS_HANGING_SIGN.get()
        };
        for (Item item : manualItems) {
            this.registerItemExistingModel(itemModels, item);
        }
    }

    private void registerConditionalItemDefinitions(ItemModelGenerators itemModels) {
        this.registerConditionalItemDefinition(itemModels, OccultismItems.FRAGILE_SOUL_GEM_ITEM.get(),
                new SoulGemItemPropertyGetter(),
                "item/fragile_soul_gem_filled",
                "item/fragile_soul_gem_empty");
        this.registerConditionalItemDefinition(itemModels, OccultismItems.SOUL_GEM_ITEM.get(),
                new SoulGemItemPropertyGetter(),
                "item/soul_gem_filled",
                "item/soul_gem_empty");
        this.registerConditionalItemDefinition(itemModels, OccultismItems.TRINITY_GEM_ITEM.get(),
                new SoulGemItemPropertyGetter(),
                "item/trinity_gem_filled",
                "item/trinity_gem_empty");
        this.registerConditionalItemDefinition(itemModels, OccultismItems.STORAGE_REMOTE.get(),
                new StorageRemoteItemPropertyGetter(),
                "item/storage_remote_linked",
                "item/storage_remote_unlinked");
    }

    private void registerTrueSightStaff(ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.accept(OccultismItems.TRUE_SIGHT_STAFF.get(), ItemModelUtils.rangeSelect(
                new DivinationDistanceItemPropertyGetter(),
                ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_0")),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_7")), 0.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_6")), 1.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_5")), 2.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_4")), 3.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_3")), 4.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_2")), 5.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_1")), 6.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_0")), 7.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/true_sight_staff_searching")), 8.0F)
        ));
    }

    private void registerDivinationRod(ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.accept(OccultismItems.DIVINATION_ROD.get(), ItemModelUtils.rangeSelect(
                new DivinationDistanceItemPropertyGetter(),
                ItemModelUtils.plainModel(this.modLoc("item/divination_rod_0")),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_7")), 0.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_6")), 1.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_5")), 2.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_4")), 3.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_3")), 4.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_2")), 5.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_1")), 6.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_0")), 7.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/divination_rod_searching")), 8.0F)
        ));
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
                var json = new JsonObject();
                json.addProperty("parent", "item/generated");
                var texturesJson = new JsonObject();
                texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement/" + texture).toString());
                json.add("textures", texturesJson);
                return json;
            });
        }

        // Generate the main advancement_icon model with overrides
        Identifier mainModel = Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement_icon");
        itemModels.modelOutput.accept(mainModel, () -> {
            var json = new JsonObject();
            json.addProperty("parent", "item/generated");
            var texturesJson = new JsonObject();
            texturesJson.addProperty("layer0", Identifier.fromNamespaceAndPath(Occultism.MODID, "item/advancement/" + textures[0]).toString());
            json.add("textures", texturesJson);

            var overrides = new JsonArray();
            for (int i = 0; i < textures.length; i++) {
                var override = new JsonObject();
                var predicate = new JsonObject();
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
            this.registerTintedItemGenerated(itemModels, item, "chalk_base", this.getChalkColor(item));
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
            this.registerImpureChalk(itemModels, item, this.getChalkColor(item));
        }
    }

    private int getChalkColor(Item item) {
        String path = this.name(item);
        //TODO: make large candle less saturated
        path = path.replace("_impure", "").replace("large_candle", "chalk");
        Integer color = CHALK_COLORS.get(path);
        if (color == null) {
            throw new IllegalArgumentException("Missing chalk color for " + BuiltInRegistries.ITEM.getKey(item));
        }
        return color;
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
            int color = this.getChalkColor(block.asItem());
            itemModels.itemModelOutput.accept(block.asItem(),
                    ItemModelUtils.tintedModel(this.modLoc("block/" + this.name(block.asItem())),
                            ItemModelUtils.constantTint(opaque(color))));
        }
    }

    private void registerVitalityCompass(ItemModelGenerators itemModels) {
        for (int i = 0; i < 32; i++) {
            String suffix = String.format(Locale.ROOT, "_%02d", i);
            Identifier modelId = this.modLoc("item/vitality_compass/compass" + suffix);
            itemModels.modelOutput.accept(modelId, () -> {
                var json = new JsonObject();
                json.addProperty("parent", "item/generated");
                var texturesJson = new JsonObject();
                texturesJson.addProperty("layer0", this.modLoc("item/vitality_compass/compass_base").toString());
                texturesJson.addProperty("layer1", this.modLoc("item/vitality_compass/compass" + suffix).toString());
                json.add("textures", texturesJson);
                return json;
            });
        }

        itemModels.itemModelOutput.accept(OccultismItems.VITALITY_COMPASS.get(), ItemModelUtils.rangeSelect(
                new VitalityCompassItemPropertyGetter(),
                32.0F,
                ItemModelUtils.plainModel(this.modLoc("item/vitality_compass")),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_16")), 0.0F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_17")), 0.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_18")), 1.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_19")), 2.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_20")), 3.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_21")), 4.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_22")), 5.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_23")), 6.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_24")), 7.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_25")), 8.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_26")), 9.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_27")), 10.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_28")), 11.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_29")), 12.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_30")), 13.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_31")), 14.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_00")), 15.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_01")), 16.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_02")), 17.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_03")), 18.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_04")), 19.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_05")), 20.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_06")), 21.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_07")), 22.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_08")), 23.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_09")), 24.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_10")), 25.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_11")), 26.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_12")), 27.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_13")), 28.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_14")), 29.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_15")), 30.5F),
                ItemModelUtils.override(ItemModelUtils.plainModel(this.modLoc("item/vitality_compass/compass_16")), 31.5F)));
    }

    private record SpawnEggColors(int primaryColor, int secondaryColor) {
    }
}
