package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockItemTags;
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
                .add(this.key(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get()))
                .add(this.key(OccultismBlocks.OTHERWORLD_LOG.get()))
                .add(this.key(OccultismBlocks.OTHERWORLD_WOOD.get()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get()))
                .add(this.key(OccultismBlocks.SPIRIT_CAMPFIRE.get()))
                .add(this.key(OccultismBlocks.TALLOW_BLOCK.get()))
                .add(this.key(OccultismBlocks.OTHERPLANKS_SHELF.get()));
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(this.key(OccultismBlocks.OTHERWORLD_LEAVES.get()), this.key(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()));
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(this.key(OccultismBlocks.OTHERSTONE.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_NATURAL.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get()))
                .add(this.key(OccultismBlocks.OTHERROCK.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_NATURAL.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_PEDESTAL.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_PEDESTAL.get()))
                .add(this.key(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()))
                .add(this.key(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get()))
                .add(this.key(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.CELESTIAL_CHALICE.get()))
                .add(this.key(OccultismBlocks.ELDRITCH_CHALICE.get()))
                .add(this.key(OccultismBlocks.STORAGE_CONTROLLER.get()))
                .add(this.key(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER0.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER5.get()))
                .add(this.key(OccultismBlocks.STABLE_WORMHOLE.get()))
                .add(this.key(OccultismBlocks.ENTITY_WORMHOLE.get()))
                .add(this.key(OccultismBlocks.STORAGE_CONTROLLER_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get()))
                .add(this.key(OccultismBlocks.STABLE_WORMHOLE_DARK.get()))
                .add(this.key(OccultismBlocks.ENTITY_WORMHOLE_DARK.get()))
                .add(this.key(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .add(this.key(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get()))
                .add(this.key(OccultismBlocks.DIMENSIONAL_EXTRACTOR.get()))
                .add(this.key(OccultismBlocks.SILVER_ORE.get()))
                .add(this.key(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()))
                .add(this.key(OccultismBlocks.IESNIUM_ORE.get()))
                .add(this.key(OccultismBlocks.IESNIUM_ORE_NATURAL.get()))
                .add(this.key(OccultismBlocks.SILVER_BLOCK.get()))
                .add(this.key(OccultismBlocks.RAW_SILVER_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CHISELED_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_GRATE_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_STAIRS.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_SLAB.get()))
                .add(this.key(OccultismBlocks.SILVER_BARS_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CHAIN_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_DOOR.get()))
                .add(this.key(OccultismBlocks.SILVER_TRAPDOOR.get()))
                .add(this.key(OccultismBlocks.SILVER_BULB.get()))
                .add(this.key(OccultismBlocks.IESNIUM_BLOCK.get()))
                .add(this.key(OccultismBlocks.RAW_IESNIUM_BLOCK.get()))
                .add(this.key(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .add(this.key(OccultismBlocks.SPIRIT_LANTERN.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERGLASS_NATURAL.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()))
                .add(this.key(OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get()))
                .add(this.key(OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLEROCK.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLEROCK_SLAB.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERROCK.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERROCK_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_BRICKS.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get()))
                .add(this.key(OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get()))
                .add(this.key(OccultismBlocks.CRACKED_OTHERROCK_BRICKS.get()))
                .add(this.key(OccultismBlocks.IESNIUM_ANVIL.get()))
                .add(this.key(OccultismBlocks.SPIRIT_GRINDSTONE.get()));
        this.tag(BlockTags.CAMPFIRES).add(this.key(OccultismBlocks.SPIRIT_CAMPFIRE.get()));
        this.tag(BlockTags.WOODEN_SHELVES).add(this.key(OccultismBlocks.OTHERPLANKS_SHELF.get()));
        this.tag(BlockTags.ANVIL).add(this.key(OccultismBlocks.IESNIUM_ANVIL.get()));
        this.tag(BlockTags.STAIRS)
                .add(this.key(OccultismBlocks.OTHERSTONE_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_STAIRS.get()));
        this.tag(BlockTags.SLABS)
                .add(this.key(OccultismBlocks.OTHERSTONE_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLEROCK_SLAB.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERROCK_SLAB.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_SLAB.get()));
        this.tag(BlockTags.WALLS)
                .add(this.key(OccultismBlocks.OTHERSTONE_WALL.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE_WALL.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE_WALL.get()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS_WALL.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_WALL.get()))
                .add(this.key(OccultismBlocks.OTHERCOBBLEROCK_WALL.get()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERROCK_WALL.get()))
                .add(this.key(OccultismBlocks.OTHERROCK_BRICKS_WALL.get()));
        this.tag(BlockTags.BARS).add(this.key(OccultismBlocks.SILVER_BARS_BLOCK.get()));
        this.tag(BlockTags.CHAINS).add(this.key(OccultismBlocks.SILVER_CHAIN_BLOCK.get()));
        this.tag(BlockTags.DOORS).add(this.key(OccultismBlocks.SILVER_DOOR.get()));
        this.tag(BlockTags.TRAPDOORS).add(this.key(OccultismBlocks.SILVER_TRAPDOOR.get()));
        this.tag(BlockTags.STONE_PRESSURE_PLATES).add(this.key(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get())).add(this.key(OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get()));
        this.tag(BlockTags.STONE_BUTTONS).add(this.key(OccultismBlocks.OTHERSTONE_BUTTON.get())).add(this.key(OccultismBlocks.OTHERROCK_BUTTON.get()));
        this.tag(BlockTags.CANDLES).addTag(OccultismTags.Blocks.OCCULTISM_CANDLES);
        this.tag(BlockTags.CROPS).add(this.key(OccultismBlocks.DATURA.get()));
        this.tag(BlockTags.LEAVES).add(this.key(OccultismBlocks.OTHERWORLD_LEAVES.get()), this.key(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()));
        this.tag(BlockTags.LOGS).addTag(OccultismTags.Blocks.OTHERWORLD_LOGS);
        this.tag(BlockItemTags.LOGS_THAT_BURN.block()).addTag(OccultismTags.Blocks.OTHERWORLD_LOGS);
        this.tag(BlockTags.PLANKS).add(this.key(OccultismBlocks.OTHERPLANKS.get()));
        this.tag(BlockTags.WOODEN_SLABS).add(this.key(OccultismBlocks.OTHERPLANKS_SLAB.get()));
        this.tag(BlockTags.WOODEN_STAIRS).add(this.key(OccultismBlocks.OTHERPLANKS_STAIRS.get()));
        this.tag(BlockTags.WOODEN_FENCES).add(this.key(OccultismBlocks.OTHERPLANKS_FENCE.get()));
        this.tag(BlockTags.FENCE_GATES).add(this.key(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get()));
        this.tag(BlockTags.WOODEN_DOORS).add(this.key(OccultismBlocks.OTHERPLANKS_DOOR.get()));
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(this.key(OccultismBlocks.OTHERPLANKS_TRAPDOOR.get()));
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(this.key(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get()));
        this.tag(BlockTags.WOODEN_BUTTONS).add(this.key(OccultismBlocks.OTHERPLANKS_BUTTON.get()));
        this.tag(BlockTags.SIGNS).add(this.key(OccultismBlocks.OTHERPLANKS_SIGN.get()));
        this.tag(BlockTags.WALL_SIGNS).add(this.key(OccultismBlocks.OTHERPLANKS_WALL_SIGN.get()));
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(this.key(OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get()));
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(this.key(OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get()));
        this.tag(BlockTags.PIGLIN_REPELLENTS).add(this.key(OccultismBlocks.SPIRIT_CAMPFIRE.get()));
        this.tag(BlockItemTags.SAPLINGS.block()).addTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS);
        this.tag(BlockItemTags.SAPLINGS.block()).addTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS_NATURAL);
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(this.key(OccultismBlocks.SILVER_ORE.get()))
                .add(this.key(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()))
                .add(this.key(OccultismBlocks.RAW_SILVER_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CHISELED_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_GRATE_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_STAIRS.get()))
                .add(this.key(OccultismBlocks.SILVER_CUT_SLAB.get()))
                .add(this.key(OccultismBlocks.SILVER_BARS_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_CHAIN_BLOCK.get()))
                .add(this.key(OccultismBlocks.SILVER_DOOR.get()))
                .add(this.key(OccultismBlocks.SILVER_TRAPDOOR.get()))
                .add(this.key(OccultismBlocks.SILVER_BULB.get()));
        this.tag(BlockTags.SMALL_FLOWERS).add(this.key(OccultismBlocks.OTHERFLOWER.get())).add(this.key(OccultismBlocks.OTHERFLOWER_NATURAL.get()));
        this.tag(BlockTags.FLOWER_POTS).add(this.key(OccultismBlocks.POTTED_OTHERFLOWER.get()));
        this.tag(BlockTags.INFINIBURN_OVERWORLD).add(this.key(OccultismBlocks.TALLOW_BLOCK.get()));
        this.tag(BlockTags.ENCHANTMENT_POWER_TRANSMITTER).addTag(OccultismTags.Blocks.CHALK_GLYPHS);
        this.tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(this.key(OccultismBlocks.SILVER_BLOCK.get()))
                .add(this.key(OccultismBlocks.IESNIUM_BLOCK.get()));
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
                .add(this.key(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.CELESTIAL_CHALICE.get()))
                .add(this.key(OccultismBlocks.ELDRITCH_CHALICE.get()))
                .add(this.key(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .addTag(BlockTags.CANDLES)
                .addTag(Tags.Blocks.GLASS_PANES)
                .addTag(OccultismTags.Blocks.ENCHANTING_TABLES)
                .add(this.key(Blocks.BEACON))
                .add(this.key(Blocks.LODESTONE))
                .add(this.key(Blocks.AMETHYST_CLUSTER))
                .add(this.key(Blocks.END_ROD))
                .addTag(BlockTags.LIGHTNING_RODS)
                .addTag(OccultismTags.Blocks.IRON_BARS)
                .addOptionalTag(OccultismTags.Blocks.CHALK_GLYPHS);

        this.tag(OccultismTags.Blocks.OTHERWORLD_COLLECTS)
                .add(this.key(Blocks.REINFORCED_DEEPSLATE));

        this.tag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS)
                .add(this.key(OccultismBlocks.OTHERWORLD_SAPLING.get()), this.key(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()));
        this.tag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS_NATURAL)
                .add(this.key(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()));
        this.tag(OccultismTags.Blocks.OCCULTISM_CANDLES).add(
                this.key(OccultismBlocks.LARGE_CANDLE.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_WHITE.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_GRAY.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_BLACK.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_BROWN.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_RED.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_ORANGE.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_YELLOW.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_LIME.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_GREEN.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_CYAN.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_BLUE.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_PINK.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_MAGENTA.get()),
                this.key(OccultismBlocks.LARGE_CANDLE_PURPLE.get())
        );

        this.tag(OccultismTags.Blocks.CHALK_GLYPHS)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_WHITE.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_YELLOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_PURPLE.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RED.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLACK.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BROWN.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_ORANGE.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIME.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_GREEN.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_CYAN.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLUE.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_MAGENTA.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_PINK.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()));

        this.tag(OccultismTags.Blocks.CENTER_SACRIFICIAL_BOWL)
                .add(this.key(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get()))
                .add(this.key(OccultismBlocks.CELESTIAL_CHALICE.get()))
                .add(this.key(OccultismBlocks.ELDRITCH_CHALICE.get()));
        this.tag(OccultismTags.Blocks.FOUNDATION_GLYPHS_ANY)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_WHITE.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLACK.get()));
        this.tag(OccultismTags.Blocks.FOUNDATION_GLYPHS_NO_WHITE)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLACK.get()));
        this.tag(OccultismTags.Blocks.FOUNDATION_GLYPHS_DARK)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_GRAY.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLACK.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_BLACK)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLACK.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_RED)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RED.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_BROWN)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BROWN.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_ORANGE)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_ORANGE.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_YELLOW)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_YELLOW.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_LIME)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIME.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_GREEN)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_GREEN.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_CYAN)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_CYAN.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_BLUE)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_BLUE.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_LIGHT_BLUE)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_PINK)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_PINK.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_MAGENTA)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_MAGENTA.get()));
        this.tag(OccultismTags.Blocks.GLYPHS_PURPLE)
                .add(this.key(OccultismBlocks.CHALK_GLYPH_RAINBOW.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_VOID.get()))
                .add(this.key(OccultismBlocks.CHALK_GLYPH_PURPLE.get()));

        this.tag(OccultismTags.Blocks.CAVE_WALL_BLOCKS).add(this.key(Blocks.STONE), this.key(Blocks.GRANITE), this.key(Blocks.DIORITE), this.key(Blocks.ANDESITE));
        this.tag(OccultismTags.Blocks.NETHERRACK).add(this.key(Blocks.NETHERRACK));
        this.tag(OccultismTags.Blocks.STORAGE_STABILIZER)
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER0.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER5.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get()))
                .add(this.key(OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get()));
        this.tag(OccultismTags.Blocks.TREE_SOIL).addTag(BlockTags.DIRT);
        this.tag(OccultismTags.Blocks.WORLDGEN_BLACKLIST).add(this.key(Blocks.END_PORTAL), this.key(Blocks.END_PORTAL_FRAME));
        this.tag(OccultismTags.Blocks.OTHERWORLD_LOGS)
                .add(this.key(OccultismBlocks.OTHERWORLD_LOG.get()))
                .add(this.key(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get()))
                .add(this.key(OccultismBlocks.OTHERWORLD_WOOD.get()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get()));

    }

    private void addForgeTags(Provider provider) {
        this.tag(OccultismTags.Blocks.IESNIUM_ORE)
                .add(this.key(OccultismBlocks.IESNIUM_ORE.get()));
        this.tag(Tags.Blocks.ORES).addTag(OccultismTags.Blocks.IESNIUM_ORE);
        this.tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(this.key(OccultismBlocks.IESNIUM_ORE.get()));


        this.tag(OccultismTags.Blocks.SILVER_ORE)
                .add(this.key(OccultismBlocks.SILVER_ORE.get())).add(this.key(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()));
        this.tag(Tags.Blocks.ORES).addTag(OccultismTags.Blocks.SILVER_ORE);
        this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(this.key(OccultismBlocks.SILVER_ORE_DEEPSLATE.get()));
        this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(this.key(OccultismBlocks.SILVER_ORE.get()));


        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM, this.key(OccultismBlocks.IESNIUM_BLOCK.get()));
        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER, this.key(OccultismBlocks.SILVER_BLOCK.get()));
        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM, this.key(OccultismBlocks.RAW_IESNIUM_BLOCK.get()));
        this.addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, this.key(OccultismBlocks.RAW_SILVER_BLOCK.get()));
        this.tag(OccultismTags.Blocks.MUSHROOM_BLOCKS).add(this.key(Blocks.MUSHROOM_STEM)).add(this.key(Blocks.RED_MUSHROOM_BLOCK)).add(this.key(Blocks.BROWN_MUSHROOM_BLOCK));
        this.tag(OccultismTags.Blocks.ENCHANTING_TABLES).add(this.key(Blocks.ENCHANTING_TABLE));
        this.tag(OccultismTags.Blocks.IRON_BARS).add(this.key(Blocks.IRON_BARS));

        this.tag(Tags.Blocks.BARS).add(this.key(OccultismBlocks.SILVER_BARS_BLOCK.get()));
        this.tag(Tags.Blocks.CHAINS).add(this.key(OccultismBlocks.SILVER_CHAIN_BLOCK.get()));
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(this.key(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get()));
    }

    private void addStorageBlock(TagKey<Block> tag, ResourceKey<Block> block) {
        this.tag(tag)
                .add(block);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(tag);
    }


    private ResourceKey<Block> key(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }
}
