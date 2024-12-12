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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.block.LargeCandleBlock;
import com.klikli_dev.occultism.common.block.*;
import com.klikli_dev.occultism.common.block.crops.ReplantableCropsBlock;
import com.klikli_dev.occultism.common.block.custom.*;
import com.klikli_dev.occultism.common.block.otherworld.*;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.common.block.storage.StorageControllerBlock;
import com.klikli_dev.occultism.common.block.storage.StorageStabilizerBlock;
import com.klikli_dev.occultism.common.entity.familiar.CthulhuFamiliarEntity;
import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import com.klikli_dev.occultism.util.OtherWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class OccultismBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Occultism.MODID);
    public static final Map<ResourceLocation, BlockDataGenSettings> BLOCK_DATA_GEN_SETTINGS = new HashMap<>();

    //Blocks without item
    public static final DeferredBlock<SpiritFireBlock> SPIRIT_FIRE = register("spirit_fire",
            () -> new SpiritFireBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .noCollission()
                            .instabreak()
                            .lightLevel((state) -> 12)
                            .sound(SoundType.WOOL)
                            .noLootTable()
            ), false, LootTableType.EMPTY);

    public static final DeferredBlock<Block> LIGHTED_AIR = register("lighted_air",
            () -> new AirBlock(
            Block.Properties.of().noCollission().air().noLootTable().lightLevel(s -> 15).randomTicks()) {
        @Override
        @SuppressWarnings("deprecation")
        public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
            if (pLevel.getEntitiesOfClass(CthulhuFamiliarEntity.class, new AABB(pPos),
                    FamiliarEntity::hasBlacksmithUpgrade).isEmpty())
                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        }
    }, false, LootTableType.EMPTY);

    public static final Block.Properties GLYPH_PROPERTIES = Block.Properties.of()
            .sound(SoundType.WOOL)
            .pushReaction(PushReaction.DESTROY)
            .replaceable()
            .noCollission()
            .noLootTable()
            .strength(5f, 30);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_WHITE = register("chalk_glyph_white",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor, OccultismItems.CHALK_WHITE),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_YELLOW = register("chalk_glyph_gold",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.yellowChalkGlyphColor, OccultismItems.CHALK_YELLOW),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_PURPLE = register("chalk_glyph_purple",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.purpleChalkGlyphColor, OccultismItems.CHALK_PURPLE),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_RED = register("chalk_glyph_red",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.redChalkGlyphColor, OccultismItems.CHALK_RED),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_LIGHT_GRAY = register("chalk_glyph_light_gray",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.lightGrayChalkGlyphColor, OccultismItems.CHALK_LIGHT_GRAY),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_GRAY = register("chalk_glyph_gray",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.grayChalkGlyphColor, OccultismItems.CHALK_GRAY),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_BLACK = register("chalk_glyph_black",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.blackChalkGlyphColor, OccultismItems.CHALK_BLACK),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_BROWN = register("chalk_glyph_brown",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.brownChalkGlyphColor, OccultismItems.CHALK_BROWN),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_ORANGE = register("chalk_glyph_orange",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.orangeChalkGlyphColor, OccultismItems.CHALK_ORANGE),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_LIME = register("chalk_glyph_lime",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.limeChalkGlyphColor, OccultismItems.CHALK_LIME),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_GREEN = register("chalk_glyph_green",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.greenChalkGlyphColor, OccultismItems.CHALK_GREEN),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_CYAN = register("chalk_glyph_cyan",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.cyanChalkGlyphColor, OccultismItems.CHALK_CYAN),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_LIGHT_BLUE = register("chalk_glyph_light_blue",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.lightBlueChalkGlyphColor, OccultismItems.CHALK_LIGHT_BLUE),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_BLUE = register("chalk_glyph_blue",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.blueChalkGlyphColor, OccultismItems.CHALK_BLUE),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_MAGENTA = register("chalk_glyph_magenta",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.magentaChalkGlyphColor, OccultismItems.CHALK_MAGENTA),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_PINK = register("chalk_glyph_pink",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.pinkChalkGlyphColor, OccultismItems.CHALK_PINK),
            false, LootTableType.EMPTY);

    public static final DeferredBlock<RainbowGlyphBlock> CHALK_GLYPH_RAINBOW = register("chalk_glyph_rainbow",
            () -> new RainbowGlyphBlock(GLYPH_PROPERTIES, true, OccultismItems.CHALK_RAINBOW),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<RainbowGlyphBlock> CHALK_GLYPH_VOID = register("chalk_glyph_void",
            () -> new RainbowGlyphBlock(GLYPH_PROPERTIES, false, OccultismItems.CHALK_VOID),
            false, LootTableType.EMPTY);

    //Resources
    public static final DeferredBlock<Block> OTHERSTONE = register("otherstone", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));
    public static final DeferredBlock<StairBlock> OTHERSTONE_STAIRS = register("otherstone_stairs",
            () -> new StairBlock(OccultismBlocks.OTHERSTONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> OTHERSTONE_SLAB = register("otherstone_slab",
            () -> new SlabBlock(Block.Properties.ofFullCopy(OTHERSTONE.get())), true, LootTableType.CUSTOM);
    public static final DeferredBlock<PressurePlateBlock> OTHERSTONE_PRESSURE_PLATE = register("otherstone_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)));
    public static final DeferredBlock<ButtonBlock> OTHERSTONE_BUTTON = register("otherstone_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<WallBlock> OTHERSTONE_WALL = register("otherstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> OTHERCOBBLESTONE = register("othercobblestone", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));
    public static final DeferredBlock<StairBlock> OTHERCOBBLESTONE_STAIRS = register("othercobblestone_stairs",
            () -> new StairBlock(OccultismBlocks.OTHERSTONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> OTHERCOBBLESTONE_SLAB = register("othercobblestone_slab",
            () -> new SlabBlock(Block.Properties.ofFullCopy(OTHERSTONE.get())), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> OTHERCOBBLESTONE_WALL = register("othercobblestone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> POLISHED_OTHERSTONE = register("polished_otherstone", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));
    public static final DeferredBlock<StairBlock> POLISHED_OTHERSTONE_STAIRS = register("polished_otherstone_stairs",
            () -> new StairBlock(OccultismBlocks.POLISHED_OTHERSTONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> POLISHED_OTHERSTONE_SLAB = register("polished_otherstone_slab",
            () -> new SlabBlock(Block.Properties.ofFullCopy(POLISHED_OTHERSTONE.get())), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> POLISHED_OTHERSTONE_WALL = register("polished_otherstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> OTHERSTONE_BRICKS = register("otherstone_bricks", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));
    public static final DeferredBlock<StairBlock> OTHERSTONE_BRICKS_STAIRS = register("otherstone_bricks_stairs",
            () -> new StairBlock(OccultismBlocks.OTHERSTONE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> OTHERSTONE_BRICKS_SLAB = register("otherstone_bricks_slab",
            () -> new SlabBlock(Block.Properties.ofFullCopy(OTHERSTONE_BRICKS.get())), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> OTHERSTONE_BRICKS_WALL = register("otherstone_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CHISELED_OTHERSTONE_BRICKS = register("chiseled_otherstone_bricks", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));
    public static final DeferredBlock<Block> CRACKED_OTHERSTONE_BRICKS = register("cracked_otherstone_bricks", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));

    //Components
    public static final DeferredBlock<OtherstoneNaturalBlock> OTHERSTONE_NATURAL =
            register("otherstone_natural", () -> new OtherstoneNaturalBlock(
                            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)),
                    true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<OtherglassNaturalBlock> OTHERGLASS_NATURAL =
            register("otherglass_natural", () -> new OtherglassNaturalBlock(
                            Block.Properties.of()
                                    .instrument(NoteBlockInstrument.HAT)
                                    .sound(SoundType.GLASS)
                                    .noOcclusion()
                                    .noTerrainParticles()
                                    .strength(5f, 50)),
                    true, LootTableType.CUSTOM);
    //For otherglass natural
    public static final DeferredBlock<Block> OTHERGLASS = register("otherglass", () -> new Block(
            Block.Properties.of().mapColor(MapColor.SAND).noOcclusion().noTerrainParticles().sound(SoundType.GLASS).strength(2.5f, 50)),false);

    //Flower
    public static final DeferredBlock<FlowerBlock> OTHERFLOWER =
            register("otherflower", () -> new FlowerBlock(
                    OccultismEffects.THIRD_EYE, 11,
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).noOcclusion().noCollission()));
    public static final DeferredBlock<OtherflowerNaturalBlock> OTHERFLOWER_NATURAL =
            register("otherflower_natural", () -> new OtherflowerNaturalBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).noCollission().noOcclusion()), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<FlowerPotBlock> POTTED_OTHERFLOWER =
            register("potted_otherflower", () -> new FlowerPotBlock(
                    OccultismBlocks.OTHERFLOWER.get(), Block.Properties.ofFullCopy(Blocks.POTTED_POPPY)), false);
    //Wood
    public static final DeferredBlock<OtherworldSaplingBlock> OTHERWORLD_SAPLING =
            register("otherworld_sapling", () -> new OtherworldSaplingBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).randomTicks().noCollission()));
    public static final DeferredBlock<OtherworldSaplingNaturalBlock> OTHERWORLD_SAPLING_NATURAL =
            register("otherworld_sapling_natural", () -> new OtherworldSaplingNaturalBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).randomTicks().noCollission()), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<LeavesBlock> OTHERWORLD_LEAVES =
            register("otherworld_leaves", () -> new LeavesBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.2f).randomTicks().noOcclusion()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<OtherworldLeavesNaturalBlock> OTHERWORLD_LEAVES_NATURAL =
            register("otherworld_leaves_natural", () -> new OtherworldLeavesNaturalBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT).sound(SoundType.GRASS)
                            .strength(0.2f).randomTicks().noOcclusion()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> OTHERWORLD_LOG_NATURAL =
            register("otherworld_log_natural", () -> new OtherworldLogNaturalBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE).strength(2.0f), OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> STRIPPED_OTHERWORLD_LOG_NATURAL =
            register("stripped_otherworld_log_natural", () -> new OtherworldStrippedLogNaturalBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE).strength(2.0f)), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> OTHERWORLD_LOG =
            register("otherworld_log", () -> new OtherworldStrippableBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                    .strength(2.0F).sound(SoundType.WOOD).strength(2.0f), OccultismBlocks.STRIPPED_OTHERWORLD_LOG));
    public static final DeferredBlock<Block> OTHERWORLD_WOOD =
            register("otherworld_wood", () -> new OtherworldStrippableBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                    .strength(2.0F).sound(SoundType.WOOD).strength(2.0f), OccultismBlocks.STRIPPED_OTHERWORLD_WOOD));
    public static final DeferredBlock<Block> STRIPPED_OTHERWORLD_LOG =
            register("stripped_otherworld_log", () -> new RotatedPillarBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                    .strength(2.0F).sound(SoundType.WOOD).strength(2.0f)));
    public static final DeferredBlock<Block> STRIPPED_OTHERWORLD_WOOD =
            register("stripped_otherworld_wood", () -> new RotatedPillarBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                    .strength(2.0F).sound(SoundType.WOOD).strength(2.0f)));
    public static final DeferredBlock<Block> OTHERPLANKS =
            register("otherplanks", () -> new Block(Block.Properties.of()
                    .mapColor(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD).strength(2.0f)));
    public static final DeferredBlock<StairBlock> OTHERPLANKS_STAIRS = register("otherplanks_stairs",
            () -> new StairBlock(OTHERPLANKS.get().defaultBlockState(), Block.Properties.ofFullCopy(OTHERPLANKS.get())));
    public static final DeferredBlock<SlabBlock> OTHERPLANKS_SLAB = register("otherplanks_slab",
            () -> new SlabBlock(Block.Properties.ofFullCopy(OTHERPLANKS.get())), true, LootTableType.CUSTOM);
    public static final DeferredBlock<FenceBlock> OTHERPLANKS_FENCE = register("otherplanks_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> OTHERPLANKS_FENCE_GATE = register("otherplanks_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(OccultismBlocks.OTHERPLANKS.get())));
    public static final DeferredBlock<DoorBlock> OTHERPLANKS_DOOR = register("otherplanks_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(OccultismBlocks.OTHERPLANKS.get())), true, LootTableType.CUSTOM);
    public static final DeferredBlock<TrapDoorBlock> OTHERPLANKS_TRAPDOOR = register("otherplanks_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(OccultismBlocks.OTHERPLANKS.get())));
    public static final DeferredBlock<PressurePlateBlock> OTHERPLANKS_PRESSURE_PLATE = register("otherplanks_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<ButtonBlock> OTHERPLANKS_BUTTON = register("otherplanks_button",
            () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> OTHERPLANKS_SIGN = register("otherplanks_sign",
            () -> new OtherStandingSignBlock(OtherWoodType.OTHERPLANKS, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)),false, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> OTHERPLANKS_WALL_SIGN = register("otherplanks_wall_sign",
            () -> new OtherWallSignBlock(OtherWoodType.OTHERPLANKS, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)),false, LootTableType.CUSTOM);

    public static final DeferredBlock<OtherHangingSignBlock> OTHERPLANKS_HANGING_SIGN = register("otherplanks_hanging_sign",
            () -> new OtherHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), OtherWoodType.OTHERPLANKS),false, LootTableType.CUSTOM);
    public static final DeferredBlock<OtherWallHangingSignBlock> OTHERPLANKS_WALL_HANGING_SIGN = register("otherplanks_wall_hanging_sign",
            () -> new OtherWallHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), OtherWoodType.OTHERPLANKS),false, LootTableType.CUSTOM);

    //Ores
    public static final DeferredBlock<Block> SILVER_ORE = register("silver_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE)), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> SILVER_ORE_DEEPSLATE = register("silver_ore_deepslate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE)), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> IESNIUM_ORE = register("iesnium_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE)), true, LootTableType.CUSTOM);
    public static final DeferredBlock<IesniumOreNaturalBlock> IESNIUM_ORE_NATURAL =
            register("iesnium_ore_natural", () -> new IesniumOreNaturalBlock(Block.Properties.ofFullCopy(Blocks.IRON_ORE)),
                    true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> SILVER_BLOCK = register("silver_block", () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = register("raw_silver_block", () -> new Block(Block.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)));
    public static final DeferredBlock<Block> IESNIUM_BLOCK = register("iesnium_block", () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_IESNIUM_BLOCK = register("raw_iesnium_block", () -> new Block(Block.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)));

    //Decorative and Ritual Blocks
    public static final DeferredBlock<Block> TALLOW_BLOCK = register("tallow_block", () -> new Block(
            Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.HONEY_BLOCK).speedFactor(0.8F).jumpFactor(0.8F).strength(0.2f, 3)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE = register("large_candle",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.SAND).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_WHITE = register("large_candle_white",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.WOOL).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_LIGHT_GRAY = register("large_candle_light_gray",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_GRAY = register("large_candle_gray",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_BLACK = register("large_candle_black",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_BROWN = register("large_candle_brown",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_RED = register("large_candle_red",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_RED).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_ORANGE = register("large_candle_orange",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_YELLOW = register("large_candle_yellow",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_LIME = register("large_candle_lime",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_GREEN = register("large_candle_green",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_CYAN = register("large_candle_cyan",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_LIGHT_BLUE = register("large_candle_light_blue",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_BLUE = register("large_candle_blue",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_PURPLE = register("large_candle_purple",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_MAGENTA = register("large_candle_magenta",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_MAGENTA).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_PINK = register("large_candle_pink",
            () -> new LargeCandleBlock(Block.Properties.of()
                    .mapColor(MapColor.COLOR_PINK).sound(SoundType.CANDLE).noCollission().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION)));

    public static final DeferredBlock<SpiritAttunedCrystalBlock> SPIRIT_ATTUNED_CRYSTAL =
            register("spirit_attuned_crystal", () -> new SpiritAttunedCrystalBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).noOcclusion()
                            .strength(1.5f, 30).lightLevel((state) -> 8)));

    //Machines
    public static final DeferredBlock<SacrificialBowlBlock> SACRIFICIAL_BOWL =
            register("sacrificial_bowl", () -> new SacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<SacrificialBowlBlock> COPPER_SACRIFICIAL_BOWL =
            register("copper_sacrificial_bowl", () -> new SacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<SacrificialBowlBlock> SILVER_SACRIFICIAL_BOWL =
            register("silver_sacrificial_bowl", () -> new SacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<GoldenSacrificialBowlBlock> GOLDEN_SACRIFICIAL_BOWL =
            register("golden_sacrificial_bowl", () -> new GoldenSacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<GoldenSacrificialBowlBlock> IESNIUM_SACRIFICIAL_BOWL =
            register("iesnium_sacrificial_bowl", () -> new GoldenSacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<GoldenSacrificialBowlBlock> ELDRITCH_CHALICE =
            register("eldritch_chalice", () -> new GoldenSacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(5.1f, 77)
                            .noOcclusion()));

    public static final DeferredBlock<Block> OTHERSTONE_PEDESTAL = register("otherstone_pedestal",
            () -> new NonPathfindableBlock(Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion()));
    public static final DeferredBlock<Block> STORAGE_CONTROLLER_BASE = register("storage_controller_base",
            () -> new NonPathfindableBlock(Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion()));
    public static final DeferredBlock<Block> OTHERSTONE_PEDESTAL_SILVER = register("otherstone_pedestal_silver",
            () -> new NonPathfindableBlock(Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion()));

    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER = register("storage_controller",
            () -> new StorageControllerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE)
                            .strength(5f, 100).noOcclusion()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER_STABILIZED = register("storage_controller_stabilized",
            () -> new StorageControllerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE)
                            .strength(5f, 100).noOcclusion()), true, LootTableType.CUSTOM);

    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER0 = register(
            "storage_stabilizer_tier0", () -> new StorageStabilizerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER1 = register(
            "storage_stabilizer_tier1", () -> new StorageStabilizerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER2 = register(
            "storage_stabilizer_tier2", () -> new StorageStabilizerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER3 = register(
            "storage_stabilizer_tier3", () -> new StorageStabilizerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER4 = register(
            "storage_stabilizer_tier4", () -> new StorageStabilizerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));

    public static final DeferredBlock<StableWormholeBlock> STABLE_WORMHOLE = register("stable_wormhole",
            () -> new StableWormholeBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).noCollission()
                            .strength(2f, 2).noOcclusion()), false, LootTableType.CUSTOM);

    public static final DeferredBlock<DimensionalMineshaftBlock> DIMENSIONAL_MINESHAFT =
            register("dimensional_mineshaft", () -> new DimensionalMineshaftBlock(
                    Block.Properties.of().sound(SoundType.STONE)
                            .strength(1.5f, 30)
                            .noOcclusion()
            ));

    public static final DeferredBlock<IesniumAnvilBlock> IESNIUM_ANVIL =
            register("iesnium_anvil", () -> new IesniumAnvilBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.ANVIL).strength(5,1200)
            ));

    //Crops
    public static final DeferredBlock<ReplantableCropsBlock> DATURA = register("datura",
            () -> new ReplantableCropsBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.CROP).noCollission().randomTicks()
                            //registry object is wrapped in lambda to account for load order and circular dependencies
                            .strength(0, 0), OccultismItems.DATURA_SEEDS,
                    OccultismItems.DATURA), false, LootTableType.REPLANTABLE_CROP);

    //Dummy
    public static final DeferredBlock<Block> SKELETON_SKULL_DUMMY = register("skeleton_skull_dummy", () -> new Block(
            Block.Properties.of().strength(1.0F)), false);
    public static final DeferredBlock<Block> WITHER_SKELETON_SKULL_DUMMY = register("wither_skeleton_skull_dummy", () -> new Block(
            Block.Properties.of().strength(1.0F)), false);

    //Deco
    public static final DeferredBlock<Block> SPIRIT_LANTERN = register("spirit_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN)
                    .lightLevel((state) -> 10).noOcclusion()));

    //See Occultism#commonSetup for extending the campfire block entity type to accept our spirit campfire
    public static final DeferredBlock<Block> SPIRIT_CAMPFIRE = register("spirit_campfire",
            () -> new CampfireBlock(false, 0, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .strength(2.0F).sound(SoundType.WOOD).lightLevel(
                            litBlockEmission(10)).noOcclusion()));

    public static final DeferredBlock<Block> SPIRIT_TORCH = register("spirit_torch",
            () -> new SpiritTorchBlock(
                    OccultismParticles.SPIRIT_FIRE_FLAME,//particles are not registered at block construct time, hence the supplier
                    BlockBehaviour.Properties.of()
                    .noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD)), false);

    public static final DeferredBlock<Block> SPIRIT_WALL_TORCH = register("spirit_wall_torch",
            () -> new SpiritWallTorchBlock(
                    OccultismParticles.SPIRIT_FIRE_FLAME, //particles are not registered at block construct time, hence the supplier
                    BlockBehaviour.Properties.of()
                    .noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD).lootFrom(SPIRIT_TORCH)), false);

    public static <I extends Block> DeferredBlock<I> register(final String name, final Supplier<? extends I> sup) {
        return register(name, sup, true);
    }

    public static <I extends Block> DeferredBlock<I> register(final String name, final Supplier<? extends I> sup,
                                                              boolean generateDefaultBlockItem) {
        return register(name, sup, generateDefaultBlockItem, LootTableType.DROP_SELF);
    }

    public static <I extends Block> DeferredBlock<I> register(final String name, final Supplier<? extends I> sup,
                                                              boolean generateDefaultBlockItem,
                                                              LootTableType lootTableType) {
        DeferredBlock<I> object = BLOCKS.register(name, sup);
        BLOCK_DATA_GEN_SETTINGS.put(object.getId(), new BlockDataGenSettings(generateDefaultBlockItem, lootTableType));

        if (generateDefaultBlockItem) {
            if (name.contains("natural")) {
                OccultismItems.ITEMS.register(name, () -> new OccultismBlockItem(object.get(), new Item.Properties()));
            } else {
                OccultismItems.ITEMS.register(name, () -> new BlockItem(object.get(), new Item.Properties()));
            }
        }

        return object;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
        };
    }


    public enum LootTableType {
        EMPTY,
        DROP_SELF,
        REPLANTABLE_CROP,
        OTHERWORLD_BLOCK,
        CUSTOM
    }

    public static class BlockDataGenSettings {
        public boolean generateDefaultBlockItem;
        public LootTableType lootTableType;

        public BlockDataGenSettings(boolean generateDefaultBlockItem,
                                    LootTableType lootTableType) {
            this.generateDefaultBlockItem = generateDefaultBlockItem;
            this.lootTableType = lootTableType;
        }
    }
}
