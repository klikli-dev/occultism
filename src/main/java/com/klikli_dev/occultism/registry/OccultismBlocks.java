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
import com.klikli_dev.occultism.common.block.*;
import com.klikli_dev.occultism.common.block.crops.ReplantableCropsBlock;
import com.klikli_dev.occultism.common.block.custom.OtherHangingSignBlock;
import com.klikli_dev.occultism.common.block.custom.OtherStandingSignBlock;
import com.klikli_dev.occultism.common.block.custom.OtherWallHangingSignBlock;
import com.klikli_dev.occultism.common.block.custom.OtherWallSignBlock;
import com.klikli_dev.occultism.common.block.otherworld.*;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.common.block.storage.StorageControllerBlock;
import com.klikli_dev.occultism.common.block.storage.StorageStabilizerBlock;
import com.klikli_dev.occultism.common.entity.familiar.CthulhuFamiliarEntity;
import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import com.klikli_dev.occultism.util.OtherWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;
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
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class OccultismBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Occultism.MODID);
    public static final Map<Identifier, BlockDataGenSettings> BLOCK_DATA_GEN_SETTINGS = new HashMap<>();

    //Blocks without item
    public static final DeferredBlock<SpiritFireBlock> SPIRIT_FIRE = register("spirit_fire",
            SpiritFireBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .noCollision()
                    .instabreak()
                    .lightLevel((state) -> 12)
                    .sound(SoundType.WOOL)
                    .noLootTable()
            , false, LootTableType.EMPTY);

    public static final DeferredBlock<Block> LIGHTED_AIR = register("lighted_air",
            (p) -> new AirBlock(p
            ) {
                @Override
                public void tick(@NonNull BlockState pState, @NonNull ServerLevel pLevel, @NonNull BlockPos pPos, @NonNull RandomSource pRandom) {
                    if (pLevel.getEntitiesOfClass(CthulhuFamiliarEntity.class, new AABB(pPos),
                            FamiliarEntity::hasBlacksmithUpgrade).isEmpty())
                        pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
                }
            }, () -> Block.Properties.of().noCollision().air().noLootTable().lightLevel(s -> 15).randomTicks(), false, LootTableType.EMPTY);

    public static final Supplier<Block.Properties> GLYPH_PROPERTIES = () -> Block.Properties.of()
            .sound(SoundType.WOOL)
            .pushReaction(PushReaction.DESTROY)
            .replaceable()
            .noCollision()
            .noLootTable()
            .strength(5f, 30);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_WHITE = register("chalk_glyph_white",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor, OccultismItems.CHALK_WHITE),
            GLYPH_PROPERTIES,
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_YELLOW = register("chalk_glyph_gold",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.yellowChalkGlyphColor, OccultismItems.CHALK_YELLOW),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_PURPLE = register("chalk_glyph_purple",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.purpleChalkGlyphColor, OccultismItems.CHALK_PURPLE),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_RED = register("chalk_glyph_red",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.redChalkGlyphColor, OccultismItems.CHALK_RED),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_LIGHT_GRAY = register("chalk_glyph_light_gray",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.lightGrayChalkGlyphColor, OccultismItems.CHALK_LIGHT_GRAY),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_GRAY = register("chalk_glyph_gray",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.grayChalkGlyphColor, OccultismItems.CHALK_GRAY),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_BLACK = register("chalk_glyph_black",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.blackChalkGlyphColor, OccultismItems.CHALK_BLACK),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_BROWN = register("chalk_glyph_brown",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.brownChalkGlyphColor, OccultismItems.CHALK_BROWN),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_ORANGE = register("chalk_glyph_orange",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.orangeChalkGlyphColor, OccultismItems.CHALK_ORANGE),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_LIME = register("chalk_glyph_lime",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.limeChalkGlyphColor, OccultismItems.CHALK_LIME),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_GREEN = register("chalk_glyph_green",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.greenChalkGlyphColor, OccultismItems.CHALK_GREEN),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_CYAN = register("chalk_glyph_cyan",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.cyanChalkGlyphColor, OccultismItems.CHALK_CYAN),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_LIGHT_BLUE = register("chalk_glyph_light_blue",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.lightBlueChalkGlyphColor, OccultismItems.CHALK_LIGHT_BLUE),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_BLUE = register("chalk_glyph_blue",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.blueChalkGlyphColor, OccultismItems.CHALK_BLUE),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_MAGENTA = register("chalk_glyph_magenta",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.magentaChalkGlyphColor, OccultismItems.CHALK_MAGENTA),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_PINK = register("chalk_glyph_pink",
            (p) -> new ChalkGlyphBlock(p, Occultism.CLIENT_CONFIG.visuals.pinkChalkGlyphColor, OccultismItems.CHALK_PINK),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);

    public static final DeferredBlock<RainbowGlyphBlock> CHALK_GLYPH_RAINBOW = register("chalk_glyph_rainbow",
            (p) -> new RainbowGlyphBlock(p, true, OccultismItems.CHALK_RAINBOW),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);
    public static final DeferredBlock<VoidGlyphBlock> CHALK_GLYPH_VOID = register("chalk_glyph_void",
            (p) -> new VoidGlyphBlock(p, true, OccultismItems.CHALK_VOID),
            GLYPH_PROPERTIES, false, LootTableType.EMPTY);

    //Resources
    public static final DeferredBlock<Block> OTHERSTONE = register("otherstone", Block::new,
            () -> Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30).requiresCorrectToolForDrops(),
            true, LootTableType.CUSTOM);
    public static final DeferredBlock<StairBlock> OTHERSTONE_STAIRS = register("otherstone_stairs",
            (p) -> new StairBlock(OccultismBlocks.OTHERSTONE.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> OTHERSTONE_SLAB = register("otherstone_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<PressurePlateBlock> OTHERSTONE_PRESSURE_PLATE = register("otherstone_pressure_plate",
            (p) -> new PressurePlateBlock(BlockSetType.STONE, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE));
    public static final DeferredBlock<ButtonBlock> OTHERSTONE_BUTTON = register("otherstone_button",
            (p) -> new ButtonBlock(BlockSetType.STONE, 30, p),
            () -> BlockBehaviour.Properties.of().noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<WallBlock> OTHERSTONE_WALL = register("otherstone_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> OTHERCOBBLESTONE = register("othercobblestone", Block::new,
            () -> Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30).requiresCorrectToolForDrops());
    public static final DeferredBlock<StairBlock> OTHERCOBBLESTONE_STAIRS = register("othercobblestone_stairs",
            (p) -> new StairBlock(OccultismBlocks.OTHERSTONE.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> OTHERCOBBLESTONE_SLAB = register("othercobblestone_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> OTHERCOBBLESTONE_WALL = register("othercobblestone_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> POLISHED_OTHERSTONE = register("polished_otherstone", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()));
    public static final DeferredBlock<StairBlock> POLISHED_OTHERSTONE_STAIRS = register("polished_otherstone_stairs",
            (p) -> new StairBlock(OccultismBlocks.POLISHED_OTHERSTONE.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> POLISHED_OTHERSTONE_SLAB = register("polished_otherstone_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(POLISHED_OTHERSTONE.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> POLISHED_OTHERSTONE_WALL = register("polished_otherstone_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> OTHERSTONE_BRICKS = register("otherstone_bricks", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()));
    public static final DeferredBlock<StairBlock> OTHERSTONE_BRICKS_STAIRS = register("otherstone_bricks_stairs",
            (p) -> new StairBlock(OccultismBlocks.OTHERSTONE_BRICKS.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> OTHERSTONE_BRICKS_SLAB = register("otherstone_bricks_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE_BRICKS.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> OTHERSTONE_BRICKS_WALL = register("otherstone_bricks_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> CHISELED_OTHERSTONE_BRICKS = register("chiseled_otherstone_bricks", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()));
    public static final DeferredBlock<Block> CRACKED_OTHERSTONE_BRICKS = register("cracked_otherstone_bricks", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()));

    public static final DeferredBlock<Block> OTHERROCK = register("otherrock", Block::new,
            () -> Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30).requiresCorrectToolForDrops(), true, LootTableType.CUSTOM);
    public static final DeferredBlock<StairBlock> OTHERROCK_STAIRS = register("otherrock_stairs",
            (p) -> new StairBlock(OccultismBlocks.OTHERROCK.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> OTHERROCK_SLAB = register("otherrock_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<PressurePlateBlock> OTHERROCK_PRESSURE_PLATE = register("otherrock_pressure_plate",
            (p) -> new PressurePlateBlock(BlockSetType.STONE, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE));
    public static final DeferredBlock<ButtonBlock> OTHERROCK_BUTTON = register("otherrock_button",
            (p) -> new ButtonBlock(BlockSetType.STONE, 30, p),
            () -> BlockBehaviour.Properties.of().noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<WallBlock> OTHERROCK_WALL = register("otherrock_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> OTHERCOBBLEROCK = register("othercobblerock", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()));
    public static final DeferredBlock<StairBlock> OTHERCOBBLEROCK_STAIRS = register("othercobblerock_stairs",
            (p) -> new StairBlock(OccultismBlocks.OTHERROCK.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> OTHERCOBBLEROCK_SLAB = register("othercobblerock_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> OTHERCOBBLEROCK_WALL = register("othercobblerock_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> POLISHED_OTHERROCK = register("polished_otherrock", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()));
    public static final DeferredBlock<StairBlock> POLISHED_OTHERROCK_STAIRS = register("polished_otherrock_stairs",
            (p) -> new StairBlock(OccultismBlocks.POLISHED_OTHERROCK.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> POLISHED_OTHERROCK_SLAB = register("polished_otherrock_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(POLISHED_OTHERROCK.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> POLISHED_OTHERROCK_WALL = register("polished_otherrock_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> OTHERROCK_BRICKS = register("otherrock_bricks", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()));
    public static final DeferredBlock<StairBlock> OTHERROCK_BRICKS_STAIRS = register("otherrock_bricks_stairs",
            (p) -> new StairBlock(OccultismBlocks.OTHERROCK_BRICKS.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<SlabBlock> OTHERROCK_BRICKS_SLAB = register("otherrock_bricks_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK_BRICKS.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<WallBlock> OTHERROCK_BRICKS_WALL = register("otherrock_bricks_wall",
            WallBlock::new,
            () -> BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> CHISELED_OTHERROCK_BRICKS = register("chiseled_otherrock_bricks", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()));
    public static final DeferredBlock<Block> CRACKED_OTHERROCK_BRICKS = register("cracked_otherrock_bricks", Block::new,
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()));

    //Components
    public static final DeferredBlock<OtherstoneNaturalBlock> OTHERSTONE_NATURAL =
            register("otherstone_natural", OtherstoneNaturalBlock::new,
                    () -> Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)
                            .overrideDescription("block.minecraft.andesite"),
                    true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<OtherrockNaturalBlock> OTHERROCK_NATURAL =
            register("otherrock_natural", OtherrockNaturalBlock::new,
                    () -> Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)
                            .overrideDescription("block.minecraft.diorite"),
                    true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<OtherglassNaturalBlock> OTHERGLASS_NATURAL =
            register("otherglass_natural", OtherglassNaturalBlock::new,
                    () -> Block.Properties.of()
                            .instrument(NoteBlockInstrument.HAT)
                            .sound(SoundType.GLASS)
                            .noOcclusion()
                            .noTerrainParticles()
                            .strength(5f, 50)
                            .overrideDescription("block.occultism.otherglass"),
                    true, LootTableType.CUSTOM);
    //For otherglass natural
    public static final DeferredBlock<Block> OTHERGLASS = register("otherglass", Block::new,
            () -> Block.Properties.of().mapColor(MapColor.SAND).noOcclusion().noTerrainParticles().sound(SoundType.GLASS).strength(2.5f, 50), false);

    //Flower
    public static final DeferredBlock<FlowerBlock> OTHERFLOWER =
            register("otherflower", (p) -> new FlowerBlock(OccultismEffects.THIRD_EYE, 11, p),
                    () -> Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).noOcclusion().noCollision());
    public static final DeferredBlock<OtherflowerNaturalBlock> OTHERFLOWER_NATURAL =
            register("otherflower_natural", OtherflowerNaturalBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).noCollision().noOcclusion()
                            .overrideDescription("block.minecraft.poppy"), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<FlowerPotBlock> POTTED_OTHERFLOWER =
            register("potted_otherflower", (p) -> new FlowerPotBlock(OccultismBlocks.OTHERFLOWER.get(), p),
                    () -> Block.Properties.ofFullCopy(Blocks.POTTED_POPPY), false);
    //Wood
    public static final DeferredBlock<OtherworldSaplingBlock> OTHERWORLD_SAPLING =
            register("otherworld_sapling", OtherworldSaplingBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).randomTicks().noCollision());
    public static final DeferredBlock<OtherworldSaplingNaturalBlock> OTHERWORLD_SAPLING_NATURAL =
            register("otherworld_sapling_natural", OtherworldSaplingNaturalBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.GRASS)
                            .strength(0.0f).randomTicks().noCollision()
                            .overrideDescription("block.minecraft.oak_sapling"), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<LeavesBlock> OTHERWORLD_LEAVES =
            register("otherworld_leaves", (p) -> new UntintedParticleLeavesBlock(0.01F, ParticleTypes.CHERRY_LEAVES, p),
                    () -> Block.Properties.of()
                            .mapColor(MapColor.PLANT).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)
                            .strength(0.2F).randomTicks().noOcclusion().ignitedByLava().isValidSpawn(Blocks::ocelotOrParrot)
                            .isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false)
                            .isRedstoneConductor((state, level, pos) -> false)
                    , true, LootTableType.CUSTOM);
    public static final DeferredBlock<OtherworldLeavesNaturalBlock> OTHERWORLD_LEAVES_NATURAL =
            register("otherworld_leaves_natural", OtherworldLeavesNaturalBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.PLANT).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)
                            .strength(0.2F).randomTicks().noOcclusion().ignitedByLava().isValidSpawn(Blocks::ocelotOrParrot)
                            .isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false)
                            .isRedstoneConductor((state, level, pos) -> false)
                            .overrideDescription("block.minecraft.oak_leaves"), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> STRIPPED_OTHERWORLD_LOG_NATURAL =
            register("stripped_otherworld_log_natural", OtherworldStrippedLogNaturalBlock::new,
                    () -> Block.Properties.of()
                            .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE).strength(2.0f)
                            .overrideDescription("block.minecraft.stripped_oak_log"), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> OTHERWORLD_LOG_NATURAL =
            register("otherworld_log_natural", (p) -> new OtherworldLogNaturalBlock(p, OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL),
                    () -> Block.Properties.of()
                            .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE).strength(2.0f)
                            .overrideDescription("block.minecraft.oak_log"), true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> STRIPPED_OTHERWORLD_LOG =
            register("stripped_otherworld_log", RotatedPillarBlock::new,
                    () -> Block.Properties.of()
                            .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                            .strength(2.0F).sound(SoundType.WOOD).strength(2.0f));
    public static final DeferredBlock<Block> OTHERWORLD_LOG =
            register("otherworld_log", (p) -> new OtherworldStrippableBlock(p, OccultismBlocks.STRIPPED_OTHERWORLD_LOG),
                    () -> Block.Properties.of()
                            .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                            .strength(2.0F).sound(SoundType.WOOD).strength(2.0f));
    public static final DeferredBlock<Block> STRIPPED_OTHERWORLD_WOOD =
            register("stripped_otherworld_wood", RotatedPillarBlock::new,
                    () -> Block.Properties.of()
                            .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                            .strength(2.0F).sound(SoundType.WOOD).strength(2.0f));
    public static final DeferredBlock<Block> OTHERWORLD_WOOD =
            register("otherworld_wood", (p) -> new OtherworldStrippableBlock(p, OccultismBlocks.STRIPPED_OTHERWORLD_WOOD),
                    () -> Block.Properties.of()
                            .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                            .strength(2.0F).sound(SoundType.WOOD).strength(2.0f));
    public static final DeferredBlock<Block> OTHERPLANKS =
            register("otherplanks", Block::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD).strength(2.0f));
    public static final DeferredBlock<StairBlock> OTHERPLANKS_STAIRS = register("otherplanks_stairs",
            (p) -> new StairBlock(OTHERPLANKS.get().defaultBlockState(), p),
            () -> Block.Properties.ofFullCopy(OTHERPLANKS.get()));
    public static final DeferredBlock<SlabBlock> OTHERPLANKS_SLAB = register("otherplanks_slab",
            SlabBlock::new,
            () -> Block.Properties.ofFullCopy(OTHERPLANKS.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<FenceBlock> OTHERPLANKS_FENCE = register("otherplanks_fence",
            FenceBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> OTHERPLANKS_FENCE_GATE = register("otherplanks_fence_gate",
            (p) -> new FenceGateBlock(WoodType.OAK, p),
            () -> BlockBehaviour.Properties.ofFullCopy(OccultismBlocks.OTHERPLANKS.get()));
    public static final DeferredBlock<DoorBlock> OTHERPLANKS_DOOR = register("otherplanks_door",
            (p) -> new DoorBlock(BlockSetType.OAK, p),
            () -> BlockBehaviour.Properties.ofFullCopy(OccultismBlocks.OTHERPLANKS.get()), true, LootTableType.CUSTOM);
    public static final DeferredBlock<TrapDoorBlock> OTHERPLANKS_TRAPDOOR = register("otherplanks_trapdoor",
            (p) -> new TrapDoorBlock(BlockSetType.OAK, p),
            () -> BlockBehaviour.Properties.ofFullCopy(OccultismBlocks.OTHERPLANKS.get()));
    public static final DeferredBlock<PressurePlateBlock> OTHERPLANKS_PRESSURE_PLATE = register("otherplanks_pressure_plate",
            (p) -> new PressurePlateBlock(BlockSetType.OAK, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));
    public static final DeferredBlock<ButtonBlock> OTHERPLANKS_BUTTON = register("otherplanks_button",
            (p) -> new ButtonBlock(BlockSetType.OAK, 30, p),
            () -> BlockBehaviour.Properties.of().noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<Block> OTHERPLANKS_SIGN = register("otherplanks_sign",
            (p) -> new OtherStandingSignBlock(OtherWoodType.OTHERPLANKS, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN), false, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> OTHERPLANKS_WALL_SIGN = register("otherplanks_wall_sign",
            (p) -> new OtherWallSignBlock(OtherWoodType.OTHERPLANKS, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN), false, LootTableType.CUSTOM);

    public static final DeferredBlock<OtherHangingSignBlock> OTHERPLANKS_HANGING_SIGN = register("otherplanks_hanging_sign",
            (p) -> new OtherHangingSignBlock(p, OtherWoodType.OTHERPLANKS),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), false, LootTableType.CUSTOM);
    public static final DeferredBlock<OtherWallHangingSignBlock> OTHERPLANKS_WALL_HANGING_SIGN = register("otherplanks_wall_hanging_sign",
            (p) -> new OtherWallHangingSignBlock(p, OtherWoodType.OTHERPLANKS),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), false, LootTableType.CUSTOM);

    //Ores
    public static final DeferredBlock<Block> SILVER_ORE = register("silver_ore", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.IRON_ORE), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> SILVER_ORE_DEEPSLATE = register("silver_ore_deepslate", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.IRON_ORE), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> IESNIUM_ORE = register("iesnium_ore", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.IRON_ORE), true, LootTableType.CUSTOM);
    public static final DeferredBlock<IesniumOreNaturalBlock> IESNIUM_ORE_NATURAL =
            register("iesnium_ore_natural", IesniumOreNaturalBlock::new,
                    () -> Block.Properties.ofFullCopy(Blocks.IRON_ORE)
                            .overrideDescription("block.minecraft.netherrack"),
                    true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> SILVER_BLOCK = register("silver_block", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = register("raw_silver_block", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK));
    public static final DeferredBlock<Block> IESNIUM_BLOCK = register("iesnium_block", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> RAW_IESNIUM_BLOCK = register("raw_iesnium_block", Block::new,
            () -> Block.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK));

    //Decorative and Ritual Blocks
    public static final DeferredBlock<Block> TALLOW_BLOCK = register("tallow_block", Block::new,
            () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.HONEY_BLOCK).speedFactor(0.8F).jumpFactor(0.8F).strength(0.2f, 3));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE = register("large_candle",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.SAND).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_WHITE = register("large_candle_white",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.WOOL).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_LIGHT_GRAY = register("large_candle_light_gray",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_GRAY = register("large_candle_gray",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_BLACK = register("large_candle_black",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_BROWN = register("large_candle_brown",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_RED = register("large_candle_red",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_RED).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_ORANGE = register("large_candle_orange",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_YELLOW = register("large_candle_yellow",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_LIME = register("large_candle_lime",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_GREEN = register("large_candle_green",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_CYAN = register("large_candle_cyan",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_LIGHT_BLUE = register("large_candle_light_blue",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_BLUE = register("large_candle_blue",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_PURPLE = register("large_candle_purple",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_MAGENTA = register("large_candle_magenta",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_MAGENTA).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<LargeCandleBlock> LARGE_CANDLE_PINK = register("large_candle_pink",
            LargeCandleBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.COLOR_PINK).sound(SoundType.CANDLE).noCollision().strength(0.1f, 0)
                    .lightLevel(LargeCandleBlock.LIGHT_EMISSION));

    public static final DeferredBlock<SpiritAttunedCrystalBlock> SPIRIT_ATTUNED_CRYSTAL =
            register("spirit_attuned_crystal", SpiritAttunedCrystalBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).noOcclusion()
                            .strength(1.5f, 30).lightLevel((state) -> 8));

    //Machines
    public static final DeferredBlock<SacrificialBowlBlock> SACRIFICIAL_BOWL =
            register("sacrificial_bowl", SacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<SacrificialBowlBlock> COPPER_SACRIFICIAL_BOWL =
            register("copper_sacrificial_bowl", SacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<SacrificialBowlBlock> SILVER_SACRIFICIAL_BOWL =
            register("silver_sacrificial_bowl", SacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<GoldenSacrificialBowlBlock> GOLDEN_SACRIFICIAL_BOWL =
            register("golden_sacrificial_bowl", GoldenSacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<GoldenSacrificialBowlBlock> IESNIUM_SACRIFICIAL_BOWL =
            register("iesnium_sacrificial_bowl", GoldenSacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion(), Rarity.UNCOMMON);
    public static final DeferredBlock<SacrificialBowlBlock> DARK_SACRIFICIAL_BOWL =
            register("dark_sacrificial_bowl", SacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<SacrificialBowlBlock> DARK_COPPER_SACRIFICIAL_BOWL =
            register("dark_copper_sacrificial_bowl", SacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<SacrificialBowlBlock> DARK_SILVER_SACRIFICIAL_BOWL =
            register("dark_silver_sacrificial_bowl", SacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<GoldenSacrificialBowlBlock> DARK_GOLDEN_SACRIFICIAL_BOWL =
            register("dark_golden_sacrificial_bowl", GoldenSacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<GoldenSacrificialBowlBlock> DARK_IESNIUM_SACRIFICIAL_BOWL =
            register("dark_iesnium_sacrificial_bowl", GoldenSacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion(), Rarity.UNCOMMON);
    public static final DeferredBlock<GoldenSacrificialBowlBlock> CELESTIAL_CHALICE =
            register("celestial_chalice", GoldenSacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(5.1f, 77)
                            .noOcclusion(), Rarity.EPIC);
    public static final DeferredBlock<GoldenSacrificialBowlBlock> ELDRITCH_CHALICE =
            register("eldritch_chalice", GoldenSacrificialBowlBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(5.1f, 77)
                            .noOcclusion(), Rarity.EPIC);

    public static final DeferredBlock<Block> OTHERSTONE_PEDESTAL = register("otherstone_pedestal",
            (p) -> new NonPathfindableBlock(p),
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion());
    public static final DeferredBlock<Block> STORAGE_CONTROLLER_BASE = register("storage_controller_base",
            (p) -> new NonPathfindableBlock(p),
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion());
    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER = register("storage_controller",
            StorageControllerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE)
                    .strength(5f, 100).noOcclusion(), true, LootTableType.CUSTOM);
    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER_STABILIZED = register("storage_controller_stabilized",
            StorageControllerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE)
                    .strength(5f, 100).noOcclusion(), true, Rarity.EPIC, LootTableType.CUSTOM);

    //TODO: change id in next major version, keep actual to avoid bugs
    public static final DeferredBlock<Block> OTHERROCK_PEDESTAL = register("otherstone_pedestal_silver",
            (p) -> new NonPathfindableBlock(p),
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion());
    public static final DeferredBlock<Block> STORAGE_CONTROLLER_BASE_DARK = register("storage_controller_base_dark",
            (p) -> new NonPathfindableBlock(p),
            () -> Block.Properties.ofFullCopy(OTHERROCK.get()).noOcclusion());
    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER_DARK = register("storage_controller_dark",
            StorageControllerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE)
                    .strength(5f, 100).noOcclusion(), true, LootTableType.CUSTOM);
    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER_STABILIZED_DARK = register("storage_controller_stabilized_dark",
            StorageControllerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE)
                    .strength(5f, 100).noOcclusion(), true, Rarity.EPIC, LootTableType.CUSTOM);

    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER0 = register(
            "storage_stabilizer_tier0", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion());
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER1 = register(
            "storage_stabilizer_tier1", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion());
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER2 = register(
            "storage_stabilizer_tier2", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion());
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER3 = register(
            "storage_stabilizer_tier3", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion(), Rarity.UNCOMMON);
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER4 = register(
            "storage_stabilizer_tier4", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion(), Rarity.RARE);
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER5 = register(
            "storage_stabilizer_tier5", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion(), Rarity.EPIC);

    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER0_DARK = register(
            "storage_stabilizer_tier0_dark", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion());
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER1_DARK = register(
            "storage_stabilizer_tier1_dark", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion());
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER2_DARK = register(
            "storage_stabilizer_tier2_dark", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion());
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER3_DARK = register(
            "storage_stabilizer_tier3_dark", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion(), Rarity.UNCOMMON);
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER4_DARK = register(
            "storage_stabilizer_tier4_dark", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion(), Rarity.RARE);
    public static final DeferredBlock<StorageStabilizerBlock> STORAGE_STABILIZER_TIER5_DARK = register(
            "storage_stabilizer_tier5_dark", StorageStabilizerBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).strength(1.5f, 30)
                    .noOcclusion(), Rarity.EPIC);

    public static final DeferredBlock<StableWormholeBlock> STABLE_WORMHOLE = register("stable_wormhole",
            StableWormholeBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).noCollision()
                    .strength(2f, 2).noOcclusion(), false, LootTableType.CUSTOM);

    public static final DeferredBlock<StableWormholeBlock> STABLE_WORMHOLE_DARK = register("stable_wormhole_dark",
            StableWormholeBlock::new,
            () -> Block.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE).noCollision()
                    .strength(2f, 2).noOcclusion(), false, LootTableType.CUSTOM);

    public static final DeferredBlock<EntityWormholeBlock> ENTITY_WORMHOLE =
            register("entity_wormhole", EntityWormholeBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());
    public static final DeferredBlock<EntityWormholeBlock> ENTITY_WORMHOLE_DARK =
            register("entity_wormhole_dark", EntityWormholeBlock::new,
                    () -> Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion());

    public static final DeferredBlock<DimensionalMineshaftBlock> DIMENSIONAL_MINESHAFT =
            register("dimensional_mineshaft", DimensionalMineshaftBlock::new,
                    () -> Block.Properties.of().sound(SoundType.STONE)
                            .strength(1.5f, 30)
                            .noOcclusion()
            );
    public static final DeferredBlock<DimensionalBattlefieldBlock> DIMENSIONAL_BATTLEFIELD =
            register("dimensional_battlefield", DimensionalBattlefieldBlock::new,
                    () -> Block.Properties.of().sound(SoundType.STONE)
                            .strength(1.5f, 30)
                            .noOcclusion()
            );

    public static final DeferredBlock<Block> DIMENSIONAL_EXTRACTOR = register("dimensional_extractor",
            (p) -> new NonPathfindableBlock(p, true),
            () -> Block.Properties.ofFullCopy(OTHERSTONE.get()).noOcclusion());

    public static final DeferredBlock<SpiritGrindstoneBlock> SPIRIT_GRINDSTONE =
            register("spirit_grindstone", SpiritGrindstoneBlock::new,
                    () -> BlockBehaviour.Properties.of()
                            .sound(SoundType.GLASS)
                            .strength(5, 1200)
                            .pushReaction(PushReaction.BLOCK)
            );
    public static final DeferredBlock<IesniumAnvilBlock> IESNIUM_ANVIL =
            register("iesnium_anvil", IesniumAnvilBlock::new,
                    () -> BlockBehaviour.Properties.of()
                            .sound(SoundType.ANVIL)
                            .strength(5, 1200)
                            .pushReaction(PushReaction.BLOCK)
            , Rarity.RARE);

    //Crops
    public static final DeferredBlock<ReplantableCropsBlock> DATURA = register("datura",
            (p) -> new ReplantableCropsBlock(p, () -> OccultismItems.DATURA_SEEDS.asItem(), () -> OccultismItems.DATURA.asItem()),
            () -> Block.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .sound(SoundType.CROP).noCollision().randomTicks()
                    //registry object is wrapped in lambda to account for load order and circular dependencies
                    .strength(0, 0), false, LootTableType.REPLANTABLE_CROP);

    //Dummy
    public static final DeferredBlock<Block> SKELETON_SKULL_DUMMY = register("skeleton_skull_dummy", Block::new,
            () -> Block.Properties.of().strength(1.0F), false);
    public static final DeferredBlock<Block> WITHER_SKELETON_SKULL_DUMMY = register("wither_skeleton_skull_dummy", Block::new,
            () -> Block.Properties.of().strength(1.0F), false);

    //Deco
    public static final DeferredBlock<Block> SPIRIT_LANTERN = register("spirit_lantern",
            LanternBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN)
                    .lightLevel((state) -> 10).noOcclusion());

    //See Occultism#commonSetup for extending the campfire block entity type to accept our spirit campfire
    public static final DeferredBlock<Block> SPIRIT_CAMPFIRE = register("spirit_campfire",
            (p) -> new CampfireBlock(false, 0, p),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .strength(2.0F).sound(SoundType.WOOD).lightLevel(
                            litBlockEmission(10)).noOcclusion());

    public static final DeferredBlock<Block> SPIRIT_TORCH = register("spirit_torch",
            (p) -> new SpiritTorchBlock(
                    OccultismParticles.SPIRIT_FIRE_FLAME, p),
            () -> BlockBehaviour.Properties.of()
                    .noCollision().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD), false);

    public static final DeferredBlock<Block> SPIRIT_WALL_TORCH = register("spirit_wall_torch",
            (p) -> new SpiritWallTorchBlock(
                    OccultismParticles.SPIRIT_FIRE_FLAME, p),
            () -> BlockBehaviour.Properties.of()
                    .noCollision().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD), false);

    public static <I extends Block> DeferredBlock<I> register(final String name, final Function<BlockBehaviour.Properties, ? extends I> func, Supplier<BlockBehaviour.Properties> properties) {
        return register(name, func, properties, true);
    }

    public static <I extends Block> DeferredBlock<I> register(final String name, final Function<BlockBehaviour.Properties, ? extends I> func, Supplier<BlockBehaviour.Properties> properties, Rarity rarity) {
        return register(name, func, properties, true, rarity, LootTableType.DROP_SELF);
    }

    public static <I extends Block> DeferredBlock<I> register(final String name, final Function<BlockBehaviour.Properties, ? extends I> func, Supplier<BlockBehaviour.Properties> properties,
                                                              boolean generateDefaultBlockItem) {
        return register(name, func, properties, generateDefaultBlockItem, LootTableType.DROP_SELF);
    }

    public static <I extends Block> DeferredBlock<I> register(final String name, final Function<BlockBehaviour.Properties, ? extends I> func, Supplier<BlockBehaviour.Properties> properties,
                                                              boolean generateDefaultBlockItem,
                                                              LootTableType lootTableType) {
        return register(name, func, properties, generateDefaultBlockItem, Rarity.COMMON, lootTableType);
    }

    public static <I extends Block> DeferredBlock<I> register(final String name, final Function<BlockBehaviour.Properties, ? extends I> func, Supplier<BlockBehaviour.Properties> properties,
                                                              boolean generateDefaultBlockItem, Rarity rarity,
                                                              LootTableType lootTableType) {
        DeferredBlock<I> object = BLOCKS.registerBlock(name, func, properties);
        BLOCK_DATA_GEN_SETTINGS.put(Identifier.fromNamespaceAndPath(Occultism.MODID, name), new BlockDataGenSettings(generateDefaultBlockItem, lootTableType));

        if (generateDefaultBlockItem) {
            if (name.contains("natural")) {
                OccultismItems.ITEMS.registerItem(name, p -> new OccultismBlockItem(object.get(), p.useBlockDescriptionPrefix()));
            } else {
                if (rarity == Rarity.COMMON) {
                    OccultismItems.ITEMS.registerItem(name, p -> new BlockItem(object.get(), p.useBlockDescriptionPrefix()));
                } else {
                    OccultismItems.ITEMS.registerItem(name, p -> new BlockItem(object.get(), p.useBlockDescriptionPrefix().rarity(rarity).fireResistant()));
                }
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
