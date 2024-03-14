package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OccultismBlockTagProvider extends BlockTagsProvider {
    public OccultismBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Occultism.MODID, existingFileHelper);
    }


    public void addMinecraftTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(OccultismBlocks.OTHERWORLD_LOG.get(),OccultismBlocks.OTHERWORLD_LOG_NATURAL.get(),OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(OccultismBlocks.OTHERWORLD_LEAVES.get(),OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()).replace(false);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(OccultismBlocks.OTHERSTONE.get())
                .add(OccultismBlocks.OTHERSTONE_NATURAL.get())
                .add(OccultismBlocks.OTHERSTONE_SLAB.get())
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
                .add(OccultismBlocks.SPIRIT_LANTERN.get());
this.tag(BlockTags.CAMPFIRES).add(OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
this.tag(BlockTags.CANDLES).add(OccultismBlocks.CANDLE_WHITE.get()).replace(false);
this.tag(BlockTags.CROPS).add(OccultismBlocks.DATURA.get()).replace(false);
this.tag(BlockTags.LEAVES).add(OccultismBlocks.OTHERWORLD_LEAVES.get(),OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()).replace(false);
this.tag(BlockTags.LOGS).add(OccultismBlocks.OTHERWORLD_LOG.get(),OccultismBlocks.OTHERWORLD_LOG_NATURAL.get()).replace(false);
this.tag(BlockTags.PIGLIN_REPELLENTS).add(OccultismBlocks.SPIRIT_CAMPFIRE.get()).replace(false);
this.tag(BlockTags.SAPLINGS).addTags(OccultismTags.Blocks.OTHERWORLD_SAPLINGS).replace(false);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addForgeTags(provider);
        addMinecraftTags(provider);
        addOccultismTags(provider);
    }

    private void addOccultismTags(HolderLookup.Provider provider) {
        this.tag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS)
                .add(OccultismBlocks.OTHERWORLD_SAPLING.get(),OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()).replace(false);
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


        addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM,OccultismBlocks.IESNIUM_BLOCK.get());
        addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER,OccultismBlocks.SILVER_BLOCK.get());
        addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM,OccultismBlocks.RAW_IESNIUM_BLOCK.get());
        addStorageBlock(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER,OccultismBlocks.RAW_SILVER_BLOCK.get());
    }

    private void addStorageBlock(TagKey<Block> tag, Block block) {
        this.tag(tag)
                .add(block).replace(false);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(tag).replace(false);
    }


}
