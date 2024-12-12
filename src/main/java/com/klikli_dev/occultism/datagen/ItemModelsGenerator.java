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

package com.klikli_dev.occultism.datagen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemModelsGenerator extends ItemModelProvider {
    public ItemModelsGenerator(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BuiltInRegistries.ITEM.forEach(item -> {
            var key = BuiltInRegistries.ITEM.getKey(item);
            if (key.getPath().startsWith("ritual_dummy/")) {
                this.registerRitualDummy("item/" + key.getPath());
            } else if (key.getPath().startsWith("spawn_egg/")) {
                this.registerSpawnEgg("item/" + key.getPath());
            }
        });

        this.registerAdvancementItem();
        this.registerItemCommon();
        this.registerItemHandheld();
        this.registerItemFromBlock();
        this.registerItemMiners();

        this.registerItemGenerated(this.name(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()),"book_of_calling_manage_machine");
        this.registerItemGenerated(this.name(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get()),"book_of_calling_cleaner");
        this.registerItemGenerated(this.name(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()),"book_of_calling_lumberjack");
        this.registerItemGenerated(this.name(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()),"book_of_calling_transport_items");

        this.registerItemGenerated(this.name(OccultismItems.PENTACLE_SUMMON.get()),"ritual_dummy_summon");
        this.registerItemGenerated(this.name(OccultismItems.PENTACLE_POSSESS.get()),"ritual_dummy_possess");
        this.registerItemGenerated(this.name(OccultismItems.PENTACLE_CRAFT.get()),"ritual_dummy_craft");
        this.registerItemGenerated(this.name(OccultismItems.PENTACLE_MISC.get()),"ritual_dummy_misc");

    }

    protected String name(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private void registerItemGenerated(String name, String texture) {
        this.getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/" + texture));
    }

    private void registerRitualDummy(String name) {
        if (name.contains("misc") || name.contains("contact") || name.contains("wild") || name.contains("resurrect")) {
            this.getBuilder(name).parent(new ModelFile.UncheckedModelFile("occultism:item/pentacle_misc"));
        } else if (name.contains("craft") || name.contains("repair")) {
            this.getBuilder(name).parent(new ModelFile.UncheckedModelFile("occultism:item/pentacle_craft"));
        } else if (name.contains("invoke") || name.contains("possess") || name.contains("familiar")) {
            this.getBuilder(name).parent(new ModelFile.UncheckedModelFile("occultism:item/pentacle_possess"));
        } else if (name.contains("summon")) {
            this.getBuilder(name).parent(new ModelFile.UncheckedModelFile("occultism:item/pentacle_summon"));
        }
    }

    private void registerSpawnEgg(String name) {
        this.getBuilder(name).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    private void registerAdvancementItem() {
        String[] textures = {"cthulhu_icon", "bat_icon", "deer_icon", "devil_icon", "greedy_icon", "hat_icon",
                "dragon_icon", "blacksmith_icon", "guardian_icon", "headless_icon", "chimera_icon",
                "shub_niggurath_icon", "shub_niggurath_spawn_icon", "beholder_icon", "fairy_icon", "mummy_icon", "beaver_icon"};

        List<ItemModelBuilder> icons = new ArrayList<>();
        for (String texture : textures)
            icons.add(this.withExistingParent("item/advancement/" + texture, this.mcLoc("item/generated"))
                    .texture("layer0", this.modLoc("item/advancement/" + texture)));

        ItemModelBuilder builder = this.withExistingParent("item/advancement_icon", this.mcLoc("item/generated"));
        for (int i = 0; i < icons.size(); i++)
            builder.override().predicate(this.mcLoc("custom_model_data"), i).model(icons.get(i)).end();
    }

    private void registerItemCommon() {
        String[] items = {
                this.name(OccultismItems.AFRIT_ESSENCE.get()),
                this.name(OccultismItems.AMETHYST_DUST.get()),
                this.name(OccultismItems.AWAKENED_FEATHER.get()),
                this.name(OccultismItems.BEAVER_NUGGET.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_AFRIT.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_DJINNI.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_EMPTY.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()),
                this.name(OccultismItems.BOOK_OF_BINDING_MARID.get()),
                this.name(OccultismItems.BRUSH.get()),
                this.name(OccultismItems.BURNT_OTHERSTONE.get()),
                this.name(OccultismItems.CHALK_BLACK.get()),
                this.name(OccultismItems.CHALK_BLACK_IMPURE.get()),
                this.name(OccultismItems.CHALK_BLUE.get()),
                this.name(OccultismItems.CHALK_BLUE_IMPURE.get()),
                this.name(OccultismItems.CHALK_BROWN.get()),
                this.name(OccultismItems.CHALK_BROWN_IMPURE.get()),
                this.name(OccultismItems.CHALK_CYAN.get()),
                this.name(OccultismItems.CHALK_CYAN_IMPURE.get()),
                this.name(OccultismItems.CHALK_YELLOW.get()),
                this.name(OccultismItems.CHALK_YELLOW_IMPURE.get()),
                this.name(OccultismItems.CHALK_GRAY.get()),
                this.name(OccultismItems.CHALK_GRAY_IMPURE.get()),
                this.name(OccultismItems.CHALK_GREEN.get()),
                this.name(OccultismItems.CHALK_GREEN_IMPURE.get()),
                this.name(OccultismItems.CHALK_LIGHT_BLUE.get()),
                this.name(OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get()),
                this.name(OccultismItems.CHALK_LIGHT_GRAY.get()),
                this.name(OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get()),
                this.name(OccultismItems.CHALK_LIME.get()),
                this.name(OccultismItems.CHALK_LIME_IMPURE.get()),
                this.name(OccultismItems.CHALK_MAGENTA.get()),
                this.name(OccultismItems.CHALK_MAGENTA_IMPURE.get()),
                this.name(OccultismItems.CHALK_ORANGE.get()),
                this.name(OccultismItems.CHALK_ORANGE_IMPURE.get()),
                this.name(OccultismItems.CHALK_PINK.get()),
                this.name(OccultismItems.CHALK_PINK_IMPURE.get()),
                this.name(OccultismItems.CHALK_PURPLE.get()),
                this.name(OccultismItems.CHALK_PURPLE_IMPURE.get()),
                this.name(OccultismItems.CHALK_RED.get()),
                this.name(OccultismItems.CHALK_RED_IMPURE.get()),
                this.name(OccultismItems.CHALK_WHITE.get()),
                this.name(OccultismItems.CHALK_WHITE_IMPURE.get()),
                this.name(OccultismItems.CHALK_RAINBOW.get()),
                this.name(OccultismItems.CHALK_VOID.get()),
                this.name(OccultismItems.COPPER_DUST.get()),
                this.name(OccultismItems.CRUELTY_ESSENCE.get()),
                this.name(OccultismItems.CRUSHED_BLACKSTONE.get()),
                this.name(OccultismItems.CRUSHED_BLUE_ICE.get()),
                this.name(OccultismItems.CRUSHED_CALCITE.get()),
                this.name(OccultismItems.CRUSHED_END_STONE.get()),
                this.name(OccultismItems.CRUSHED_ICE.get()),
                this.name(OccultismItems.CRUSHED_PACKED_ICE.get()),
                this.name(OccultismItems.CURSED_HONEY.get()),
                this.name(OccultismItems.DATURA.get()),
                this.name(OccultismItems.DATURA_SEEDS.get()),
                this.name(OccultismItems.DEBUG_WAND.get()),
                this.name(OccultismItems.DEMONIC_MEAT.get()),
                this.name(OccultismItems.DEMONS_DREAM_ESSENCE.get()),
                this.name(OccultismItems.DICTIONARY_OF_SPIRITS.get()),
                this.name(OccultismItems.DRAGONYST_DUST.get()),
                this.name(OccultismItems.ECHO_DUST.get()),
                this.name(OccultismItems.EMERALD_DUST.get()),
                this.name(OccultismItems.FAMILIAR_RING.get()),
                this.name(OccultismItems.GOLD_DUST.get()),
                this.name(OccultismItems.GRAY_PASTE.get()),
                this.name(OccultismItems.IESNIUM_DUST.get()),
                this.name(OccultismItems.IESNIUM_INGOT.get()),
                this.name(OccultismItems.IESNIUM_NUGGET.get()),
                this.name(OccultismItems.INFUSED_LENSES.get()),
                this.name(OccultismItems.IRON_DUST.get()),
                this.name(OccultismItems.LAPIS_DUST.get()),
                this.name(OccultismItems.LENS_FRAME.get()),
                this.name(OccultismItems.LENSES.get()),
                this.name(OccultismItems.MAGIC_LAMP_EMPTY.get()),
                this.name(OccultismItems.MARID_ESSENCE.get()),
                this.name(OccultismItems.MINING_DIMENSION_CORE_PIECE.get()),
                this.name(OccultismItems.MYSTERIOUS_EGG_ICON.get()),
                this.name(OccultismItems.NATURE_PASTE.get()),
                this.name(OccultismItems.NETHERITE_DUST.get()),
                this.name(OccultismItems.NETHERITE_SCRAP_DUST.get()),
                this.name(OccultismItems.OBSIDIAN_DUST.get()),
                this.name(OccultismItems.OTHERSTONE_FRAME.get()),
                this.name(OccultismItems.OTHERSTONE_TABLET.get()),
                this.name(OccultismItems.OTHERWORLD_ASHES.get()),
                this.name(OccultismItems.OTHERWORLD_ESSENCE.get()),
                this.name(OccultismItems.PURIFIED_INK.get()),
                this.name(OccultismItems.REPAIR_ICON.get()),
                this.name(OccultismItems.RESEARCH_FRAGMENT_DUST.get()),
                this.name(OccultismItems.RESURRECT_ICON.get()),
                this.name(OccultismItems.RITUAL_SATCHEL_T1.get()),
                this.name(OccultismItems.RITUAL_SATCHEL_T2.get()),
                this.name(OccultismItems.RAW_IESNIUM.get()),
                this.name(OccultismItems.RAW_SILVER.get()),
                this.name(OccultismItems.SATCHEL.get()),
                this.name(OccultismItems.SILVER_DUST.get()),
                this.name(OccultismItems.SILVER_INGOT.get()),
                this.name(OccultismItems.SILVER_NUGGET.get()),
                this.name(OccultismItems.SOUL_SHARD_ITEM.get()),
                this.name(OccultismItems.SPIRIT_ATTUNED_GEM.get()),
                this.name(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD.get()),
                this.name(OccultismBlocks.SPIRIT_CAMPFIRE.asItem()),
                this.name(OccultismBlocks.SPIRIT_LANTERN.asItem()),
                this.name(OccultismItems.STORAGE_REMOTE_INERT.get()),
                this.name(OccultismItems.TABOO_BOOK.get()),
                this.name(OccultismItems.TALLOW.get()),
                this.name(OccultismItems.WITHERITE_DUST.get())
        };
        for (String item : items){
            this.registerItemGenerated(item, item);
        }
    }
    private void registerItemHandheld() {
        String[] items = {
                this.name(OccultismItems.BUTCHER_KNIFE.get()),
                this.name(OccultismItems.IESNIUM_PICKAXE.get()),
                this.name(OccultismItems.INFUSED_PICKAXE.get())
        };
        for (String item : items){
            this.getBuilder(item)
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", this.modLoc("item/" + item));
        }
    }
    private void registerItemFromBlock() {
        String[] items = {
                this.name(OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.asItem()),
                this.name(OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.asItem()),
                this.name(OccultismBlocks.COPPER_SACRIFICIAL_BOWL.asItem()),
                this.name(OccultismBlocks.DIMENSIONAL_MINESHAFT.asItem()),
                this.name(OccultismBlocks.ELDRITCH_CHALICE.asItem()),
                this.name(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.asItem()),
                this.name(OccultismBlocks.IESNIUM_BLOCK.asItem()),
                this.name(OccultismBlocks.IESNIUM_ORE.asItem()),
                this.name(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_WHITE.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_GRAY.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_BLACK.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_BROWN.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_RED.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_ORANGE.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_YELLOW.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_LIME.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_GREEN.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_CYAN.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_BLUE.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_PINK.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_MAGENTA.asItem()),
                this.name(OccultismBlocks.LARGE_CANDLE_PURPLE.asItem()),
                this.name(OccultismBlocks.OTHERCOBBLESTONE.asItem()),
                this.name(OccultismBlocks.OTHERCOBBLESTONE_SLAB.asItem()),
                this.name(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.asItem()),
                this.name(OccultismBlocks.OTHERGLASS_NATURAL.asItem()),
                this.name(OccultismBlocks.OTHERPLANKS.asItem()),
                this.name(OccultismBlocks.OTHERPLANKS_FENCE_GATE.asItem()),
                this.name(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.asItem()),
                this.name(OccultismBlocks.OTHERPLANKS_SLAB.asItem()),
                this.name(OccultismBlocks.OTHERPLANKS_STAIRS.asItem()),
                this.name(OccultismBlocks.OTHERSTONE.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_BRICKS.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_PEDESTAL.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_PEDESTAL_SILVER.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_SLAB.asItem()),
                this.name(OccultismBlocks.OTHERSTONE_STAIRS.asItem()),
                this.name(OccultismBlocks.OTHERWORLD_LEAVES.asItem()),
                this.name(OccultismBlocks.OTHERWORLD_LOG.asItem()),
                this.name(OccultismBlocks.OTHERWORLD_WOOD.asItem()),
                this.name(OccultismBlocks.POLISHED_OTHERSTONE.asItem()),
                this.name(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.asItem()),
                this.name(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.asItem()),
                this.name(OccultismBlocks.RAW_IESNIUM_BLOCK.asItem()),
                this.name(OccultismBlocks.RAW_SILVER_BLOCK.asItem()),
                this.name(OccultismBlocks.SACRIFICIAL_BOWL.asItem()),
                this.name(OccultismBlocks.SILVER_BLOCK.asItem()),
                this.name(OccultismBlocks.SILVER_ORE.asItem()),
                this.name(OccultismBlocks.SILVER_ORE_DEEPSLATE.asItem()),
                this.name(OccultismBlocks.SILVER_SACRIFICIAL_BOWL.asItem()),
                this.name(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()),
                this.name(OccultismBlocks.STORAGE_STABILIZER_TIER0.asItem()),
                this.name(OccultismBlocks.STORAGE_STABILIZER_TIER1.asItem()),
                this.name(OccultismBlocks.STORAGE_STABILIZER_TIER2.asItem()),
                this.name(OccultismBlocks.STORAGE_STABILIZER_TIER3.asItem()),
                this.name(OccultismBlocks.STORAGE_STABILIZER_TIER4.asItem()),
                this.name(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.asItem()),
                this.name(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.asItem()),
                this.name(OccultismBlocks.TALLOW_BLOCK.asItem())
        };
        for (String item : items){
            this.getBuilder(item)
                    .parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + item)));
        }
    }
    private void registerItemMiners() {
        String[] items = {
                this.name(OccultismItems.MINER_AFRIT_DEEPS.get()),
                this.name(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get()),
                this.name(OccultismItems.MINER_DJINNI_ORES.get()),
                this.name(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                this.name(OccultismItems.MINER_MARID_MASTER.get()),
                this.name(OccultismItems.MINER_ANCIENT_ELDRITCH.get())
        };
        for (String item : items){
            this.getBuilder(item)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", this.modLoc("item/magic_lamp"));
        }
    }

}
