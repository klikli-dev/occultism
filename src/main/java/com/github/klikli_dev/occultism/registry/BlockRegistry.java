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
import com.github.klikli_dev.occultism.common.block.*;
import com.github.klikli_dev.occultism.common.block.crops.BlockOccultismCrop;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.*;

public class BlockRegistry {

    //region Fields
    public static final Map<Block, String[]> ORE_DICTIONARY = new HashMap<>();
    public static final List<Class<?>> BLOCKS_WITHOUT_ITEMS = Arrays.asList(BlockChalkGlyph.class,
            BlockStableWormhole.class, BlockOccultismCrop.class, BlockSpiritFire.class, BlockOtherworldLog.class,
            BlockOtherworldLogNatural.class, BlockOtherworldSaplingNatural.class, BlockOtherworldSapling.class);
    public static final List<Item> blockItems = new ArrayList<>();

    //Helper blocks and blocks without item
    public static final Block CHALK_GLYPH = new BlockChalkGlyph();
    public static final BlockSpiritFire SPIRIT_FIRE = new BlockSpiritFire();

    //Resources
    public static final Block OTHERSTONE = new BlockOtherstone();
    public static final Block OTHERSTONE_ORE = new BlockOtherstoneOre();
    public static final Block OTHERWORLD_LOG_NATURAL = new BlockOtherworldLogNatural();
    public static final Block OTHERWORLD_LOG = new BlockOtherworldLog();
    public static final Block OTHERWORLD_LEAVES_NATURAL = new BlockOtherworldLeavesNatural();
    public static final Block OTHERWORLD_LEAVES = new BlockOtherworldLeaves();
    public static final Block OTHERWORLD_SAPLING_NATURAL = new BlockOtherworldSaplingNatural();
    public static final Block OTHERWORLD_SAPLING = new BlockOtherworldSapling();

    //Components
    public static final Block SPIRIT_ATTUNED_CRYSTAL = new BlockSpiritAttunedCrystal();
    public static final Block CANDLE_WHITE = new BlockCandle("candle_white");

    //Crops
    public static final BlockOccultismCrop CROP_DATURA = new BlockOccultismCrop("crop_datura");

    //Machines
    public static final Block SACRIFICIAL_BOWL = new BlockSacrificialBowl();
    public static final Block GOLDEN_SACRIFICIAL_BOWL = new BlockGoldenSacrificialBowl();

    public static final Block STORAGE_CONTROLLER = new BlockStorageController();
    public static final Block STORAGE_STABILIZER_TIER1 = new BlockStorageStabilizer("storage_stabilizer_tier1");
    public static final Block STORAGE_STABILIZER_TIER2 = new BlockStorageStabilizer("storage_stabilizer_tier2");
    public static final Block STORAGE_STABILIZER_TIER3 = new BlockStorageStabilizer("storage_stabilizer_tier3");
    public static final Block STORAGE_STABILIZER_TIER4 = new BlockStorageStabilizer("storage_stabilizer_tier4");

    public static final Block STABLE_WORMHOLE = new BlockStableWormhole();
    //endregion Fields

    //region Static Methods
    public static <T extends Block> void registerBlock(T block, String name, Material material, SoundType sound,
                                                       float hardness, float resistance, String tool, int level,
                                                       String... oreDictionaryNames) {
        ResourceLocation location = new ResourceLocation(Occultism.MODID, name);
        block.setRegistryName(location);
        block.setTranslationKey(location.toString().replace(":", "."));
        block.setCreativeTab(Occultism.CREATIVE_TAB);
        ObfuscationReflectionHelper.setPrivateValue(Block.class, block, sound, "blockSoundType", "field_149762_H");
        //set the sound type via reflection

        block.setHardness(hardness);
        block.setResistance(resistance);
        block.setHarvestLevel(tool, level);

        //Set fire and ice settings
        if (material == Material.CARPET)
            Blocks.FIRE.setFireInfo(block, 60, 20);
        if (material == Material.CLOTH || material == Material.LEAVES)
            Blocks.FIRE.setFireInfo(block, 30, 60);
        if (material == Material.PLANTS)
            Blocks.FIRE.setFireInfo(block, 60, 100);
        if (material == Material.TNT || material == Material.VINE)
            Blocks.FIRE.setFireInfo(block, 15, 100);
        if (material == Material.WOOD)
            Blocks.FIRE.setFireInfo(block, 5, 20);
        if (material == Material.ICE)
            block.setDefaultSlipperiness(0.98f);

        if (!isBlockWithoutItem(block)) {
            Item item = new ItemBlock(block).setRegistryName(location).setTranslationKey(block.getTranslationKey());
            blockItems.add(item);
        }

        //prepare ore dictionary. Registration is after block registration event.
        ORE_DICTIONARY.put(block, oreDictionaryNames);
    }

    public static <T extends Block> void registerBlock(T block, String name, Material material, SoundType sound,
                                                       float hardness, float resistance, String... oreDictionaryNames) {
        registerBlock(block, name, material, sound, hardness, resistance, "", 0, oreDictionaryNames);
    }

    /**
     * Returns true if the specified block does not have an item (e.g. a chalk glyph)
     *
     * @param block
     * @return
     */
    public static boolean isBlockWithoutItem(Block block) {
        for (Class<?> blockWithoutItem : BLOCKS_WITHOUT_ITEMS) {
            if (blockWithoutItem.isInstance(block)) {
                return true;
            }
        }
        return false;
    }
    //endregion Static Methods
}
