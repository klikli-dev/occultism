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
import com.klikli_dev.occultism.common.block.CandleBlock;
import com.klikli_dev.occultism.common.block.*;
import com.klikli_dev.occultism.common.block.crops.ReplantableCropsBlock;
import com.klikli_dev.occultism.common.block.otherworld.*;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.common.block.storage.StorageControllerBlock;
import com.klikli_dev.occultism.common.block.storage.StorageStabilizerBlock;
import com.klikli_dev.occultism.common.entity.familiar.CthulhuFamiliarEntity;
import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
                            .sound(SoundType.WOOL)), false, LootTableType.EMPTY);

    public static final DeferredBlock<Block> LIGHTED_AIR = register("lighted_air", () -> new AirBlock(
            Block.Properties.of().noCollission().air().noLootTable().lightLevel(s -> 15).randomTicks()) {
        @Override
        @SuppressWarnings("deprecation")
        public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
            if (pLevel.getEntitiesOfClass(CthulhuFamiliarEntity.class, new AABB(pPos),
                    FamiliarEntity::hasBlacksmithUpgrade).isEmpty())
                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        }
    });

    public static final Block.Properties GLYPH_PROPERTIES = Block.Properties.of()
            .sound(SoundType.WOOL)
            .pushReaction(PushReaction.DESTROY)
            .replaceable()
            .noCollission()
            .strength(5f, 30);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_WHITE = register("chalk_glyph_white",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor, () -> OccultismItems.CHALK_WHITE.get()),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_GOLD = register("chalk_glyph_gold",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.goldenChalkGlyphColor, () -> OccultismItems.CHALK_GOLD.get()), false,
            LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_PURPLE = register("chalk_glyph_purple",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.purpleChalkGlyphColor, () -> OccultismItems.CHALK_PURPLE.get()),
            false, LootTableType.EMPTY);
    public static final DeferredBlock<ChalkGlyphBlock> CHALK_GLYPH_RED = register("chalk_glyph_red",
            () -> new ChalkGlyphBlock(GLYPH_PROPERTIES, Occultism.CLIENT_CONFIG.visuals.redChalkGlyphColor, () -> OccultismItems.CHALK_RED.get()), false,
            LootTableType.EMPTY);

    //Resources
    public static final DeferredBlock<Block> OTHERSTONE = register("otherstone", () -> new Block(
            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)));
    //Components
    public static final DeferredBlock<SlabBlock> OTHERSTONE_SLAB = register("otherstone_slab", () -> new SlabBlock(Block.Properties.ofLegacyCopy(OTHERSTONE.get())));
    public static final DeferredBlock<Block> OTHERSTONE_PEDESTAL = register("otherstone_pedestal", () -> new Block(Block.Properties.ofLegacyCopy(OTHERSTONE.get())));
    public static final DeferredBlock<Block> STORAGE_CONTROLLER_BASE = register("storage_controller_base",
            () -> new NonPathfindableBlock(Block.Properties.ofLegacyCopy(OTHERSTONE.get()).noOcclusion()));
    public static final DeferredBlock<OtherstoneNaturalBlock> OTHERSTONE_NATURAL =
            register("otherstone_natural", () -> new OtherstoneNaturalBlock(
                            Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 30)),
                    true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> OTHERWORLD_LOG =
            register("otherworld_log", () -> new RotatedPillarBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE)
                    .strength(2.0F).sound(SoundType.WOOD).strength(2.0f)));
    public static final DeferredBlock<Block> OTHERWORLD_LOG_NATURAL =
            register("otherworld_log_natural", () -> new OtherworldLogNaturalBlock(Block.Properties.of()
                    .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_PURPLE).strength(2.0f)), true, LootTableType.OTHERWORLD_BLOCK);
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
                            .strength(0.0f).randomTicks().noCollission()), false, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> SILVER_ORE = register("silver_ore", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.IRON_ORE)), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> SILVER_ORE_DEEPSLATE = register("silver_ore_deepslate", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.IRON_ORE)), true, LootTableType.CUSTOM);
    public static final DeferredBlock<Block> IESNIUM_ORE = register("iesnium_ore", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.IRON_ORE)), true, LootTableType.CUSTOM);
    public static final DeferredBlock<IesniumOreNaturalBlock> IESNIUM_ORE_NATURAL =
            register("iesnium_ore_natural", () -> new IesniumOreNaturalBlock(Block.Properties.ofLegacyCopy(Blocks.IRON_ORE)),
                    true, LootTableType.OTHERWORLD_BLOCK);
    public static final DeferredBlock<Block> SILVER_BLOCK = register("silver_block", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = register("raw_silver_block", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.RAW_IRON_BLOCK)));
    public static final DeferredBlock<Block> IESNIUM_BLOCK = register("iesnium_block", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_IESNIUM_BLOCK = register("raw_iesnium_block", () -> new Block(Block.Properties.ofLegacyCopy(Blocks.RAW_IRON_BLOCK)));

    //Decorative and Ritual Blocks
    public static final DeferredBlock<CandleBlock> CANDLE_WHITE = register("candle_white", () -> new CandleBlock(
            Block.Properties.of()
                    .mapColor(MapColor.WOOL)
                    .sound(SoundType.WOOL).noCollission()
                    .strength(0.1f, 0).lightLevel((state) -> 12)));

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
    public static final DeferredBlock<GoldenSacrificialBowlBlock> GOLDEN_SACRIFICIAL_BOWL =
            register("golden_sacrificial_bowl", () -> new GoldenSacrificialBowlBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE).strength(1.5f, 30)
                            .noOcclusion()));

    public static final DeferredBlock<StorageControllerBlock> STORAGE_CONTROLLER = register("storage_controller",
            () -> new StorageControllerBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.STONE)
                            .sound(SoundType.STONE)
                            .strength(5f, 100).noOcclusion()), false, LootTableType.CUSTOM);
                            
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

    //Crops
    public static final DeferredBlock<ReplantableCropsBlock> DATURA = register("datura",
            () -> new ReplantableCropsBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .sound(SoundType.CROP).noCollission().randomTicks()
                            //registry object is wrapped in lambda to account for load order and circular dependencies
                            .strength(0, 0), () -> OccultismItems.DATURA_SEEDS.get(),
                    () -> OccultismItems.DATURA.get()), false, LootTableType.REPLANTABLE_CROP);

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

    //See Occultism#commonSetup for extending the campfire blockentity type to accept our spirit campfire
    public static final DeferredBlock<Block> SPIRIT_CAMPFIRE = register("spirit_campfire",
            () -> new CampfireBlock(false, 0, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .strength(2.0F).sound(SoundType.WOOD).lightLevel(
                            litBlockEmission(10)).noOcclusion()));

    public static final DeferredBlock<Block> SPIRIT_TORCH = register("spirit_torch",
            () -> new SpiritTorchBlock(
                    () -> OccultismParticles.SPIRIT_FIRE_FLAME.get(),//particles are not registered at block construct time, hence the supplier
                    BlockBehaviour.Properties.of()
                    .noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD)), false);

    public static final DeferredBlock<Block> SPIRIT_WALL_TORCH = register("spirit_wall_torch",
            () -> new SpiritWallTorchBlock(
                    () -> OccultismParticles.SPIRIT_FIRE_FLAME.get(), //particles are not registered at block construct time, hence the supplier
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
            OccultismItems.ITEMS.register(name, () -> new BlockItem(object.get(), new Item.Properties()));
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
