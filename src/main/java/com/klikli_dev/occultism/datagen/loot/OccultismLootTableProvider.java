package com.klikli_dev.occultism.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class OccultismLootTableProvider extends LootTableProvider {
    public OccultismLootTableProvider(PackOutput pOutput, Set<ResourceKey<LootTable>> pRequiredTables, List<SubProviderEntry> pSubProviders, CompletableFuture<Provider> pRegistries) {
        super(pOutput, pRequiredTables, pSubProviders, pRegistries);
    }
}
