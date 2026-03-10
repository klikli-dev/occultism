package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class OccultismEnchantmentTagProvider extends EnchantmentTagsProvider {
    public OccultismEnchantmentTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(EnchantmentTags.NON_TREASURE).add(OccultismEnchantments.FRACTURE_SOUL);
        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE).add(OccultismEnchantments.FRACTURE_SOUL);
    }
}
