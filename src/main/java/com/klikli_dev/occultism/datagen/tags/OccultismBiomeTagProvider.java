package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;

import java.util.concurrent.CompletableFuture;

public class OccultismBiomeTagProvider extends BiomeTagsProvider {
    public OccultismBiomeTagProvider(PackOutput p_255800_, CompletableFuture<Provider> p_256205_) {
        super(p_255800_, p_256205_, Occultism.MODID);
    }

    @Override
    protected void addTags(Provider pProvider) {
        this.tag(OccultismTags.ALLOWS_SHUB_NIGGURRATH_TRANSFORMATION).addTag(BiomeTags.IS_FOREST).replace(false);
        this.tag(OccultismTags.ALLOWS_WINGNIS_TRANSFORMATION).addTag(BiomeTags.IS_NETHER).replace(false);
    }
}
