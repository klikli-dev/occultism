package com.klikli_dev.occultism.datagen.loot;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;
import java.util.Set;

public class OccultismLootTableProvider extends LootTableProvider {
    public OccultismLootTableProvider(Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders) {
        super(requiredTables, subProviders);
    }
}
