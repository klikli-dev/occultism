/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.datagen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ItemModelsGenerator extends ItemModelProvider {
    public ItemModelsGenerator(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ForgeRegistries.ITEMS.forEach(item -> {
            var key = ForgeRegistries.ITEMS.getKey(item);
            if (key.getPath().startsWith("ritual_dummy/")) {
                this.registerRitualDummy("item/" + key.getPath());
            }
        });
        this.registerAdvancementItem();

        this.registerSpawnEgg(OccultismItems.SPAWN_EGG_DEMONIC_WIFE);
        this.registerSpawnEgg(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND);

        this.registerItemGenerated(this.name(OccultismItems.SOUL_SHARD_ITEM.get()));
        this.registerItemGenerated(this.name(OccultismItems.DEMONS_DREAM_ESSENCE.get()));
        this.registerItemGenerated(this.name(OccultismItems.OTHERWORLD_ESSENCE.get()));
    }

    protected String name(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private ItemModelBuilder registerItemGenerated(String name) {
        return this.registerItemGenerated(name, name);
    }

    private ItemModelBuilder registerItemGenerated(String name, String texture) {
        return this.getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/" + texture));
    }


    private void registerRitualDummy(String name) {
        this.getBuilder(name).parent(new ModelFile.UncheckedModelFile("occultism:item/ritual_dummy"));
    }

    private void registerSpawnEgg(RegistryObject<Item> spawnEgg) {
        this.getBuilder("item/" + spawnEgg.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    private void registerAdvancementItem() {
        String[] textures = {"cthulhu_icon", "bat_icon", "deer_icon", "devil_icon", "greedy_icon", "hat_icon",
                "dragon_icon", "blacksmith_icon", "guardian_icon", "headless_icon", "chimera_icon",
                "shub_niggurath_icon", "shub_niggurath_spawn_icon", "beholder_icon", "fairy_icon", "mummy_icon", "beaver_icon"};

        List<ItemModelBuilder> icons = new ArrayList<>();
        for (String texture : textures)
            icons.add(this.withExistingParent("item/advancement/" + texture, this.mcLoc("item/generated"))
                    .texture("layer0", this.modLoc("item/advancement/" + texture)));

        ItemModelBuilder builder = this.withExistingParent("item/advancement_icon", this.mcLoc("item/generated"));
        for (int i = 0; i < icons.size(); i++)
            builder.override().predicate(this.mcLoc("custom_model_data"), i).model(icons.get(i)).end();
    }
}
