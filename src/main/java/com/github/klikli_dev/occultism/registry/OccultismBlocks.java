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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismBlocks {

    //region Fields
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Occultism.MODID);

    //Blocks without item
    public static final Block.Properties GLYPH_PROPERTIES = Block.Properties.create(Material.MISCELLANEOUS)
                                                                    .sound(SoundType.CLOTH).doesNotBlockMovement()
                                                                    .hardnessAndResistance(5f, 30).notSolid();
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_WHITE = BLOCKS.register("chalk_glyph_white",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0xffffff));
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_GOLD = BLOCKS.register("chalk_glyph_gold",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0xf0d700));
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_PURPLE = BLOCKS.register("chalk_glyph_purple",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0x9c0393));
    public static final RegistryObject<ChalkGlyphBlock> CHALK_GLYPH_RED = BLOCKS.register("chalk_glyph_red",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, 0xcc0101));

    //Decorative and Ritual Blocks
    public static final RegistryObject<CandleBlock> CANDLE_WHITE = BLOCKS.register("candle_white", () -> new CandleBlock(
            Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.CLOTH).doesNotBlockMovement()
                    .hardnessAndResistance(0.1f, 0).lightValue(12)));

    //Machines
    public static final RegistryObject<StorageControllerBlock> STORAGE_CONTROLLER = BLOCKS.register("storage_controller", () -> new StorageControllerBlock(
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
                    .hardnessAndResistance(5f, 100).notSolid()));
    public static final RegistryObject<StableWormholeBlock> STABLE_WORMHOLE = BLOCKS.register("stable_wormhole", () -> new StableWormholeBlock(
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE).doesNotBlockMovement()
                    .hardnessAndResistance(2f, 2).notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER1 = BLOCKS.register("storage_stabilizer_tier1", () -> new StorageStabilizerBlock(
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30).notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER2 = BLOCKS.register("storage_stabilizer_tier2", () -> new StorageStabilizerBlock(
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30).notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER3 = BLOCKS.register("storage_stabilizer_tier3", () -> new StorageStabilizerBlock(
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30).notSolid()));
    public static final RegistryObject<StorageStabilizerBlock> STORAGE_STABILIZER_TIER4 = BLOCKS.register("storage_stabilizer_tier4", () -> new StorageStabilizerBlock(
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 30).notSolid()));

    //endregion Fields

    //region Static Methods
    public static boolean requiresDefaultBlock(Block block) {
        if(block instanceof ChalkGlyphBlock)
            return false;
        if(block instanceof StableWormholeBlock)
            return false;
        if(block instanceof StorageStabilizerBlock)
            return false;
        if(block instanceof StorageControllerBlock)
            return false;
        return true;
    }

    public static boolean requiresEmptyLootTable(Block block) {
        return block instanceof ChalkGlyphBlock;
    }

    public static boolean requiresCustomLootTable(Block block) {
        if(block instanceof StableWormholeBlock)
            return true;
        if(block instanceof StorageControllerBlock)
            return true;
        return false;
    }
    //endregion Static Methods
}
