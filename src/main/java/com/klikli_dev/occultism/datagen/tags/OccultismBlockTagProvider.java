package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class OccultismBlockTagProvider extends BlockTagsProvider {
    public OccultismBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider, Occultism.MODID);
    }


    public void addMinecraftTags(Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get())
                .add(OccultismBlocks.OTHERWORLD_LOG.get())
                .add(OccultismBlocks.OTHERWORLD_WOOD.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get())
                .add(OccultismBlocks.SPIRIT_CAMPFIRE.get())
                .add(OccultismBlocks.TALLOW_BLOCK.get())
                .add(OccultismBlocks.OTHERPLANKS_SHELF.get())
                .replace(false);
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(OccultismBlocks.OTHERWORLD_LEAVES.get(), OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()).replace(false);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(OccultismBlocks.OTHERSTONE.get())
                .add(OccultismBlocks.OTHERSTONE_NATURAL.get())
                .add(OccultismBlocks.OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get())
                .add(OccultismBlocks.OTHERROCK.get())
                .add(OccultismBlocks.OTHERROCK_NATURAL.get())
                .add(OccultismBlocks.OTHERROCK_STAIRS.get())
                .add(OccultismBlocks.OTHERROCK_SLAB.get())
                .add(OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get())
                .add(OccultismBlocks.OTHERSTONE_PEDESTAL.get())
                .add(OccultismBlocks.OTHERROCK_PEDESTAL.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get())
                .add(OccultismBlocks.SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.CELESTIAL_CHALICE.get())
                .add(OccultismBlocks.ELDRITCH_CHALICE.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER0.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER5.get())
                .add(OccultismBlocks.RITUAL_CATCHER.get())
                .add(OccultismBlocks.STABLE_WORMHOLE.get())
                .add(OccultismBlocks.ENTITY_WORMHOLE.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER_DARK.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get())
                .add(OccultismBlocks.RITUAL_CATCHER_DARK.get())
                .add(OccultismBlocks.STABLE_WORMHOLE_DARK.get())
                .add(OccultismBlocks.ENTITY_WORMHOLE_DARK.get())
                .add(OccultismBlocks.DIMENSIONAL_MINESHAFT.get())
                .add(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get())
                .add(OccultismBlocks.DIMENSIONAL_EXTRACTOR.get())
                .add(OccultismBlocks.SILVER_ORE.get())
                .add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get())
                .add(OccultismBlocks.IESNIUM_ORE.get())
                .add(OccultismBlocks.IESNIUM_ORE_NATURAL.get())
                .add(OccultismBlocks.SILVER_BLOCK.get())
                .add(OccultismBlocks.RAW_SILVER_BLOCK.get())
                .add(OccultismBlocks.SILVER_CHISELED_BLOCK.get())
                .add(OccultismBlocks.SILVER_GRATE_BLOCK.get())
                .add(OccultismBlocks.SILVER_CUT_BLOCK.get())
                .add(OccultismBlocks.SILVER_CUT_STAIRS.get())
                .add(OccultismBlocks.SILVER_CUT_SLAB.get())
                .add(OccultismBlocks.SILVER_BARS_BLOCK.get())
                .add(OccultismBlocks.SILVER_CHAIN_BLOCK.get())
                .add(OccultismBlocks.SILVER_DOOR.get())
                .add(OccultismBlocks.SILVER_TRAPDOOR.get())
                .add(OccultismBlocks.SILVER_BULB.get())
                .add(OccultismBlocks.IESNIUM_BLOCK.get())
                .add(OccultismBlocks.RAW_IESNIUM_BLOCK.get())
                .add(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get())
                .add(OccultismBlocks.SPIRIT_LANTERN.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get())
                .add(OccultismBlocks.OTHERGLASS_NATURAL.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get())
                .add(OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get())
                .add(OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get())
                .add(OccultismBlocks.OTHERCOBBLEROCK.get())
                .add(OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get())
                .add(OccultismBlocks.OTHERCOBBLEROCK_SLAB.get())
                .add(OccultismBlocks.POLISHED_OTHERROCK.get())
                .add(OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get())
                .add(OccultismBlocks.POLISHED_OTHERROCK_SLAB.get())
                .add(OccultismBlocks.OTHERROCK_BRICKS.get())
                .add(OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get())
                .add(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get())
                .add(OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get())
                .add(OccultismBlocks.CRACKED_OTHERROCK_BRICKS.get())
                .add(OccultismBlocks.IESNIUM_ANVIL.get())
                .add(OccultismBlocks.SPIRIT_GRINDSTONE.get());
        this.tag(BlockTags.CAMPFIRES).add(OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
        this.tag(BlockTags.WOODEN_SHELVES).add(OccultismBlocks.OTHERPLANKS_SHELF.get()).replace(false);
        this.tag(BlockTags.ANVIL).add(OccultismBlocks.IESNIUM_ANVIL.get()).replace(false);
        this.tag(BlockTags.STAIRS)
                .add(OccultismBlocks.OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get())
                .add(OccultismBlocks.OTHERROCK_STAIRS.get())
                .add(OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get())
                .add(OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get())
                .add(OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get())
                .add(OccultismBlocks.SILVER_CUT_STAIRS.get())
                .replace(false);
        this.tag(BlockTags.SLABS)
                .add(OccultismBlocks.OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get())
                .add(OccultismBlocks.OTHERROCK_SLAB.get())
                .add(OccultismBlocks.OTHERCOBBLEROCK_SLAB.get())
                .add(OccultismBlocks.POLISHED_OTHERROCK_SLAB.get())
                .add(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get())
                .add(OccultismBlocks.SILVER_CUT_SLAB.get())
                .replace(false);
        this.tag(BlockTags.WALLS)
                .add(OccultismBlocks.OTHERSTONE_WALL.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_WALL.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_WALL.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_WALL.get())
                .add(OccultismBlocks.OTHERROCK_WALL.get())
                .add(OccultismBlocks.OTHERCOBBLEROCK_WALL.get())
                .add(OccultismBlocks.POLISHED_OTHERROCK_WALL.get())
                .add(OccultismBlocks.OTHERROCK_BRICKS_WALL.get()).replace(false);
        this.tag(BlockTags.BARS).add(OccultismBlocks.SILVER_BARS_BLOCK.get());
        this.tag(BlockTags.CHAINS).add(OccultismBlocks.SILVER_CHAIN_BLOCK.get());
        this.tag(BlockTags.DOORS).add(OccultismBlocks.SILVER_DOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(OccultismBlocks.SILVER_TRAPDOOR.get());
        this.tag(BlockTags.STONE_PRESSURE_PLATES).add(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get()).add(OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get()).replace(false);
        this.tag(BlockTags.STONE_BUTTONS).add(OccultismBlocks.OTHERSTONE_BUTTON.get()).add(OccultismBlocks.OTHERROCK_BUTTON.get()).replace(false);
        this.tag(BlockTags.CANDLES).addTags(OccultismTags.Blocks.OCCULTISM_CANDLES).replace(false);
        this.tag(BlockTags.CROPS).add(OccultismBlocks.DATURA.get()).replace(false);
        this.tag(BlockTags.LEAVES).add(OccultismBlocks.OTHERWORLD_LEAVES.get(), OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()).replace(false);
        this.tag(BlockTags.LOGS).addTags(OccultismTags.Blocks.OTHERWORLD_LOGS).replace(false);
        this.tag(BlockTags.LOGS_THAT_BURN).addTags(OccultismTags.Blocks.OTHERWORLD_LOGS).replace(false);
        this.tag(BlockTags.PLANKS).add(OccultismBlocks.OTHERPLANKS.get()).replace(false);
        this.tag(BlockTags.WOODEN_SLABS).add(OccultismBlocks.OTHERPLANKS_SLAB.get()).replace(false);
        this.tag(BlockTags.WOODEN_STAIRS).add(OccultismBlocks.OTHERPLANKS_STAIRS.get()).replace(false);
        this.tag(BlockTags.WOODEN_FENCES).add(OccultismBlocks.OTHERPLANKS_FENCE.get()).replace(false);
        this.tag(BlockTags.FENCE_GATES).add(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get()).replace(false);
        this.tag(BlockTags.WOODEN_DOORS).add(OccultismBlocks.OTHERPLANKS_DOOR.get()).replace(false);
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(OccultismBlocks.OTHERPLANKS_TRAPDOOR.get()).replace(false);
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get()).replace(false);
        this.tag(BlockTags.WOODEN_BUTTONS).add(OccultismBlocks.OTHERPLANKS_BUTTON.get()).replace(false);
        this.tag(BlockTags.SIGNS).add(OccultismBlocks.OTHERPLANKS_SIGN.get()).replace(false);
        this.tag(BlockTags.WALL_SIGNS).add(OccultismBlocks.OTHERPLANKS_WALL_SIGN.get()).replace(false);
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get()).replace(false);
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get()).replace(false);
        this.tag(BlockTags.PIGLIN_REPELLENTS).add(OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
        this.tag(BlockTags.SAPLINGS).addTags(OccultismTags.Blocks.OTHERWORLD_SAPLINGS).replace(false);
        this.tag(BlockTags.SAPLINGS).addTags(OccultismTags.Blocks.OTHERWORLD_SAPLINGS_NATURAL).replace(false);
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(OccultismBlocks.SILVER_ORE.get())
                .add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get())
                .add(OccultismBlocks.RAW_SILVER_BLOCK.get())
                .add(OccultismBlocks.SILVER_BLOCK.get())
                .add(OccultismBlocks.SILVER_CHISELED_BLOCK.get())
                .add(OccultismBlocks.SILVER_GRATE_BLOCK.get())
                .add(OccultismBlocks.SILVER_CUT_BLOCK.get())
                .add(OccultismBlocks.SILVER_CUT_STAIRS.get())
                .add(OccultismBlocks.SILVER_CUT_SLAB.get())
                .add(OccultismBlocks.SILVER_BARS_BLOCK.get())
                .add(OccultismBlocks.SILVER_CHAIN_BLOCK.get())
                .add(OccultismBlocks.SILVER_DOOR.get())
                .add(OccultismBlocks.SILVER_TRAPDOOR.get())
                .add(OccultismBlocks.SILVER_BULB.get())
                .replace(false);
        this.tag(BlockTags.SMALL_FLOWERS).add(OccultismBlocks.OTHERFLOWER.get()).add(OccultismBlocks.OTHERFLOWER_NATURAL.get()).replace(false);
        this.tag(BlockTags.FLOWER_POTS).add(OccultismBlocks.POTTED_OTHERFLOWER.get()).replace(false);
        this.tag(BlockTags.INFINIBURN_OVERWORLD).add(OccultismBlocks.TALLOW_BLOCK.get());
        this.tag(BlockTags.ENCHANTMENT_POWER_TRANSMITTER).addTag(OccultismTags.Blocks.CHALK_GLYPHS);
        this.tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(OccultismBlocks.SILVER_BLOCK.get())
                .add(OccultismBlocks.IESNIUM_BLOCK.get());
    }

    @Override
    protected void addTags(Provider provider) {
        this.addForgeTags(provider);
        this.addMinecraftTags(provider);
        this.addOccultismTags(provider);
    }

    private void addOccultismTags(Provider provider) {
        this.tag(OccultismTags.Blocks.PENTACLE_MATERIALS)
                .addTag(Tags.Blocks.SKULLS)
                .add(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.CELESTIAL_CHALICE.get())
                .add(OccultismBlocks.ELDRITCH_CHALICE.get())
                .add(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get())
                .addTag(BlockTags.CANDLES)
                .addTag(Tags.Blocks.GLASS_PANES)
                .addTag(OccultismTags.Blocks.ENCHANTING_TABLES)
                .add(Blocks.BEACON)
                .add(Blocks.LODESTONE)
                .add(Blocks.AMETHYST_CLUSTER)
                .add(Blocks.END_ROD)
                .addTag(BlockTags.LIGHTNING_RODS)
                .addTag(OccultismTags.Blocks.IRON_BARS)
                .addOptionalTag(OccultismTags.Blocks.CHALK_GLYPHS)
                .replace(false);

        this.tag(OccultismTags.Blocks.OTHERWORLD_COLLECTS)
                .add(Blocks.REINFORCED_DEEPSLATE);

        this.tag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS)
                .add(OccultismBlocks.OTHERWORLD_SAPLING.get(), OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()).replace(false);
        this.tag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS_NATURAL)
                .add(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()).replace(false);
        this.tag(OccultismTags.Blocks.OCCULTISM_CANDLES).add(
                OccultismBlocks.LARGE_CANDLE.get(),
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
                OccultismBlocks.LARGE_CANDLE_PURPLE.get()
        ).replace(false);

        this.tag(OccultismTags.Blocks.CHALK_GLYPHS)
                .add(OccultismBlocks.CHALK_GLYPH_WHITE.get())
                .add(OccultismBlocks.CHALK_GLYPH_YELLOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_PURPLE.get())
                .add(OccultismBlocks.CHALK_GLYPH_RED.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLACK.get())
                .add(OccultismBlocks.CHALK_GLYPH_BROWN.get())
                .add(OccultismBlocks.CHALK_GLYPH_ORANGE.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIME.get())
                .add(OccultismBlocks.CHALK_GLYPH_GREEN.get())
                .add(OccultismBlocks.CHALK_GLYPH_CYAN.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLUE.get())
                .add(OccultismBlocks.CHALK_GLYPH_MAGENTA.get())
                .add(OccultismBlocks.CHALK_GLYPH_PINK.get())
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get()).replace(false);

        this.tag(OccultismTags.Blocks.CENTER_SACRIFICIAL_BOWL)
                .add(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.CELESTIAL_CHALICE.get())
                .add(OccultismBlocks.ELDRITCH_CHALICE.get()).replace(false);
        this.tag(OccultismTags.Blocks.FOUNDATION_GLYPHS_ANY)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_WHITE.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLACK.get()).replace(false);
        this.tag(OccultismTags.Blocks.FOUNDATION_GLYPHS_NO_WHITE)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLACK.get()).replace(false);
        this.tag(OccultismTags.Blocks.FOUNDATION_GLYPHS_DARK)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_GRAY.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLACK.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_BLACK)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLACK.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_RED)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_RED.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_BROWN)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_BROWN.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_ORANGE)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_ORANGE.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_YELLOW)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_YELLOW.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_LIME)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIME.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_GREEN)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_GREEN.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_CYAN)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_CYAN.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_BLUE)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_BLUE.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_LIGHT_BLUE)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_PINK)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_PINK.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_MAGENTA)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_MAGENTA.get()).replace(false);
        this.tag(OccultismTags.Blocks.GLYPHS_PURPLE)
                .add(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())
                .add(OccultismBlocks.CHALK_GLYPH_VOID.get())
                .add(OccultismBlocks.CHALK_GLYPH_PURPLE.get()).replace(false);

        this.tag(OccultismTags.Blocks.CAVE_WALL_BLOCKS).add(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE).replace(false);
        this.tag(OccultismTags.Blocks.NETHERRACK).add(Blocks.NETHERRACK).replace(false);
        this.tag(OccultismTags.Blocks.STORAGE_STABILIZER)
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER0.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER5.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get());
        this.tag(OccultismTags.Blocks.TREE_SOIL).addTags(BlockTags.DIRT).replace(false);
        this.tag(OccultismTags.Blocks.WORLDGEN_BLACKLIST).add(Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME).replace(false);
        this.tag(OccultismTags.Blocks.OTHERWORLD_LOGS)
                .add(OccultismBlocks.OTHERWORLD_LOG.get())
                .add(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get())
                .add(OccultismBlocks.OTHERWORLD_WOOD.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get());

    }

    private void addForgeTags(Provider provider) {
        this.tag(OccultismTags.Blocks.IESNIUM_ORE)
                .add(OccultismBlocks.IESNIUM_ORE.get()).replace(false);
        this.tag(Tags.Blocks.ORES).addTags(OccultismTags.Blocks.IESNIUM_ORE).replace(false);
        this.tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(OccultismBlocks.IESNIUM_ORE.get()).replace(false);


        this.tag(OccultismTags.Blocks.SILVER_ORE)
                .add(OccultismBlocks.SILVER_ORE.get()).add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()).replace(false);
        this.tag(Tags.Blocks.ORES).addTags(OccultismTags.Blocks.SILVER_ORE).replace(false);
        this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()).replace(false);
        this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(OccultismBlocks.SILVER_ORE.get()).replace(false);


        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM, OccultismBlocks.IESNIUM_BLOCK.get());
        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER, OccultismBlocks.SILVER_BLOCK.get());
        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM, OccultismBlocks.RAW_IESNIUM_BLOCK.get());
        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, OccultismBlocks.RAW_SILVER_BLOCK.get());
        this.tag(OccultismTags.Blocks.MUSHROOM_BLOCKS).add(Blocks.MUSHROOM_STEM).add(Blocks.RED_MUSHROOM_BLOCK).add(Blocks.BROWN_MUSHROOM_BLOCK);
        this.tag(OccultismTags.Blocks.ENCHANTING_TABLES).add(Blocks.ENCHANTING_TABLE);
        this.tag(OccultismTags.Blocks.IRON_BARS).add(Blocks.IRON_BARS);

        this.tag(Tags.Blocks.BARS).add(OccultismBlocks.SILVER_BARS_BLOCK.get());
        this.tag(Tags.Blocks.CHAINS).add(OccultismBlocks.SILVER_CHAIN_BLOCK.get());
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get()).replace(false);
    }

    private void addStorageBlock(TagKey<Block> tag, Block block) {
        this.tag(tag)
                .add(block).replace(false);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(tag).replace(false);
    }


}
