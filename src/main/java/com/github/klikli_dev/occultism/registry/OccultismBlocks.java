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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.CandleBlock;
import com.github.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.github.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.github.klikli_dev.occultism.common.block.storage.StorageControllerBlock;
import com.github.klikli_dev.occultism.common.block.storage.StorageStabilizerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OccultismBlocks {

    //region Fields
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
            Occultism.MODID);
    public static final Map<ResourceLocation, BlockDataGenSettings> BLOCK_DATA_GEN_SETTINGS = new HashMap<>();

    //Blocks without item
    public static final Block.Properties GLYPH_PROPERTIES = Block.Properties.create(Material.MISCELLANEOUS)
                                                                    .sound(SoundType.CLOTH).doesNotBlockMovement()
                                                                    .hardnessAndResistance(5f, 30).notSolid();
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_WHITE = register("chalk_glyph_white",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0xffffff), false, LootTableType.EMPTY);
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_GOLD = register("chalk_glyph_gold",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0xf0d700), false, LootTableType.EMPTY);
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_PURPLE = register("chalk_glyph_purple",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0x9c0393), false, LootTableType.EMPTY);
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_RED = register("chalk_glyph_red",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0xcc0101), false, LootTableType.EMPTY);

    //Decorative and Ritual Blocks
    public static final RegistryObject<CandleBlock> CANDLE_WHITE = register("candle_white", () -> new CandleBlock(
            Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.CLOTH).doesNotBlockMovement()
                    .hardnessAndResistance(0.1f, 0).lightValue(12)));

    //Machines
    public static final RegistryObject<StorageControllerBlock> STORAGE_CONTROLLER = register("storage_controller",
            () -> new StorageControllerBlock(
                    Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
                            .hardnessAndResistance(5f, 100).notSolid()), true, LootTableType.CUSTOM);
    public static final RegistryObject<StableWormholeBlock> STABLE_WORMHOLE = register("stable_wormhole",
            () -> new StableWormholeBlock(
                    Block.Properties.create(Material.ROCK).sound(SoundType.STONE).doesNotBlockMovement()
                            .hardnessAndResistance(2f, 2).notSolid()), false, LootTableType.CUSTOM);
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER1 = register(
            "storage_stabilizer_tier1", () -> new StorageStabilizerBlock(
                    Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30)
                            .notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER2 = register(
            "storage_stabilizer_tier2", () -> new StorageStabilizerBlock(
                    Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30)
                            .notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER3 = register(
            "storage_stabilizer_tier3", () -> new StorageStabilizerBlock(
                    Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30)
                            .notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER4 = register(
            "storage_stabilizer_tier4", () -> new StorageStabilizerBlock(
                    Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30)
                            .notSolid()));

    //endregion Fields

    //region Static Methods
    public static <I extends Block> RegistryObject<I> register(final String name, final Supplier<? extends I> sup) {
        return register(name, sup, true);
    }

    public static <I extends Block> RegistryObject<I> register(final String name, final Supplier<? extends I> sup,
                                                               boolean generateDefaultBlockItem) {
        return register(name, sup, generateDefaultBlockItem, LootTableType.DROP_SELF);
    }

    public static <I extends Block> RegistryObject<I> register(final String name, final Supplier<? extends I> sup,
                                                               boolean generateDefaultBlockItem,
                                                               LootTableType lootTableType) {
        RegistryObject<I> object = BLOCKS.register(name, sup);
        BLOCK_DATA_GEN_SETTINGS.put(object.getId(), new BlockDataGenSettings(generateDefaultBlockItem, lootTableType));
        return object;
    }
    //endregion Static Methods

    public enum LootTableType {
        EMPTY,
        DROP_SELF,
        CUSTOM
    }

    public static class BlockDataGenSettings {
        //region Fields
        public boolean generateDefaultBlockItem;
        public LootTableType lootTableType;
        //endregion Fields

        //region Initialization
        public BlockDataGenSettings(boolean generateDefaultBlockItem,
                                    LootTableType lootTableType) {
            this.generateDefaultBlockItem = generateDefaultBlockItem;
            this.lootTableType = lootTableType;
        }
        //endregion Initialization
    }
}
