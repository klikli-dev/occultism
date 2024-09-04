package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OccultismBlockTagProvider extends BlockTagsProvider {
    public OccultismBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Occultism.MODID, existingFileHelper);
    }


    public void addMinecraftTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get())
                .add(OccultismBlocks.OTHERWORLD_LOG.get())
                .add(OccultismBlocks.OTHERWORLD_WOOD.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get())
                .add(OccultismBlocks.SPIRIT_CAMPFIRE.get())
                .replace(false);
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(OccultismBlocks.OTHERWORLD_LEAVES.get(), OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()).replace(false);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(OccultismBlocks.OTHERSTONE.get())
                .add(OccultismBlocks.OTHERSTONE_NATURAL.get())
                .add(OccultismBlocks.OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get())
                .add(OccultismBlocks.OTHERSTONE_PEDESTAL.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .add(OccultismBlocks.SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .add(OccultismBlocks.STORAGE_CONTROLLER.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
                .add(OccultismBlocks.STABLE_WORMHOLE.get())
                .add(OccultismBlocks.DIMENSIONAL_MINESHAFT.get())
                .add(OccultismBlocks.SILVER_ORE.get())
                .add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get())
                .add(OccultismBlocks.IESNIUM_ORE.get())
                .add(OccultismBlocks.IESNIUM_ORE_NATURAL.get())
                .add(OccultismBlocks.SILVER_BLOCK.get())
                .add(OccultismBlocks.RAW_SILVER_BLOCK.get())
                .add(OccultismBlocks.IESNIUM_BLOCK.get())
                .add(OccultismBlocks.RAW_IESNIUM_BLOCK.get())
                .add(OccultismBlocks.SPIRIT_LANTERN.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get())
                .add(OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get())
                .add(OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get());
        this.tag(BlockTags.CAMPFIRES).add(OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
        this.tag(BlockTags.STAIRS)
                .add(OccultismBlocks.OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get()).replace(false);
        this.tag(BlockTags.SLABS)
                .add(OccultismBlocks.OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()).replace(false);
        this.tag(BlockTags.WALLS)
                .add(OccultismBlocks.OTHERSTONE_WALL.get())
                .add(OccultismBlocks.OTHERCOBBLESTONE_WALL.get())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_WALL.get())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_WALL.get()).replace(false);
        this.tag(BlockTags.STONE_PRESSURE_PLATES).add(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get()).replace(false);
        this.tag(BlockTags.STONE_BUTTONS).add(OccultismBlocks.OTHERSTONE_BUTTON.get()).replace(false);
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
        this.tag(BlockTags.PIGLIN_REPELLENTS).add(OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
        this.tag(BlockTags.SAPLINGS).addTags(OccultismTags.Blocks.OTHERWORLD_SAPLINGS).replace(false);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.addForgeTags(provider);
        this.addMinecraftTags(provider);
        this.addOccultismTags(provider);
    }

    private void addOccultismTags(HolderLookup.Provider provider) {
        this.tag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS)
                .add(OccultismBlocks.OTHERWORLD_SAPLING.get(), OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()).replace(false);
        this.tag(OccultismTags.Blocks.OCCULTISM_CANDLES).add(OccultismBlocks.CANDLE_WHITE.get()).replace(false);
        this.tag(OccultismTags.Blocks.CAVE_WALL_BLOCKS).add(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE).replace(false);
        this.tag(OccultismTags.Blocks.NETHERRACK).add(Blocks.NETHERRACK).replace(false);
        this.tag(OccultismTags.Blocks.STORAGE_STABILIZER)
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
                .add(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()).replace(false);
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

    private void addForgeTags(HolderLookup.Provider provider) {
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

        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(OccultismBlocks.OTHERPLANKS_FENCE_GATE.get()).replace(false);
    }

    private void addStorageBlock(TagKey<Block> tag, Block block) {
        this.tag(tag)
                .add(block).replace(false);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(tag).replace(false);
    }


}
