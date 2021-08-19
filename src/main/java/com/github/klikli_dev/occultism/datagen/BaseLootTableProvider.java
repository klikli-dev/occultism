/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, McJty
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

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Based on https://github.com/McJty/YouTubeModding14
 */
public abstract class BaseLootTableProvider extends LootTableProvider {

    //region Fields
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // Filled by subclasses
    protected final Map<Block, LootTable.Builder> blockLootTable = new HashMap<>();
    protected final Map<EntityType<?>, LootTable.Builder> entityLootTable = new HashMap<>();

    private final DataGenerator generator;
    //endregion Fields

    //region Initialization
    public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }
    //endregion Initialization

    //region Overrides


    @Override
    public void run(HashCache cache) {
        this.addTables();
        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : this.blockLootTable.entrySet()) {
            tables.put(entry.getKey().getLootTable(),
                    entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }
        for (Map.Entry<EntityType<?>, LootTable.Builder> entry : this.entityLootTable.entrySet()) {
            tables.put(entry.getKey().getDefaultLootTable(),
                    entry.getValue().setParamSet(LootContextParamSets.ENTITY).build());
        }
        this.writeTables(cache, tables);
    }


    @Override
    public String getName() {
        return "Occultism LootTables";
    }
    //endregion Overrides

    //region Methods
    // Subclasses can override this to fill the 'lootTables' map.
    protected abstract void addTables();

    // Actually write out the tables in the output folder
    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
            } catch (IOException e) {
                Occultism.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
    //endregion Methods
}