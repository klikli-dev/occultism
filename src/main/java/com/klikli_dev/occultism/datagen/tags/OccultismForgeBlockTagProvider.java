package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OccultismForgeBlockTagProvider extends BlockTagsProvider {
    public OccultismForgeBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

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
