/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.common.advancement.RitualTrigger;
import com.github.klikli_dev.occultism.common.advancement.RitualTrigger.RitualPredicate;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class OccultismAdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private final DataGenerator generator;
    private Map<ResourceLocation, Advancement> advancements;

    public OccultismAdvancementProvider(DataGenerator generator) {
        this.generator = generator;
        this.advancements = new HashMap<>();
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Path folder = this.generator.getOutputFolder();
        start();

        for (Advancement advancement : advancements.values()) {
            Path path = getPath(folder, advancement);
            try {
                IDataProvider.save(GSON, cache, advancement.copy().serialize(), path);
            } catch (IOException exception) {
                LOGGER.error("Couldn't save advancement {}", path, exception);
            }
        }
    }

    private void start() {
        Advancement root = add(Advancement.Builder.builder()
                .withDisplay(OccultismItems.JEI_DUMMY_NONE.get(), title("root"), descr("root"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, true,
                        true, false)
                .withCriterion("summon_familiar",
                        new RitualTrigger.Instance(new RitualPredicate(null, OccultismRituals.FAMILIAR_RITUAL.getId())))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/root")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(icon(2), title("deer"), descr("deer"), null, FrameType.TASK, true, true, false)
                .withCriterion("deer_poop", FamiliarTrigger.of(FamiliarTrigger.Type.DEER_POOP))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/deer")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(icon(0), title("cthulhu"), descr("cthulhu"), null, FrameType.TASK, true, true, false)
                .withCriterion("cthulhu_sad", FamiliarTrigger.of(FamiliarTrigger.Type.CTHULHU_SAD))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/cthulhu")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(icon(1), title("bat"), descr("bat"), null, FrameType.TASK, true, true, false)
                .withCriterion("bat_eat", FamiliarTrigger.of(FamiliarTrigger.Type.BAT_EAT))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/bat")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(icon(3), title("devil"), descr("devil"), null, FrameType.TASK, true, true, false)
                .withCriterion("devil_fire", FamiliarTrigger.of(FamiliarTrigger.Type.DEVIL_FIRE))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/devil")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(icon(4), title("greedy"), descr("greedy"), null, FrameType.TASK, true, true, false)
                .withCriterion("greedy_item", FamiliarTrigger.of(FamiliarTrigger.Type.GREEDY_ITEM))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/greedy")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(icon(5), title("rare"), descr("rare"), null, FrameType.TASK, true, true, false)
                .withCriterion("rare_variant", FamiliarTrigger.of(FamiliarTrigger.Type.RARE_VARIANT))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/rare")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(Items.JUKEBOX, title("party"), descr("party"), null, FrameType.TASK, true, true, false)
                .withCriterion("party", FamiliarTrigger.of(FamiliarTrigger.Type.PARTY))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/party")));
        add(Advancement.Builder.builder().withParent(root)
                .withDisplay(OccultismItems.FAMILIAR_RING.get(), title("capture"), descr("capture"), null,
                        FrameType.TASK, true, true, false)
                .withCriterion("capture", FamiliarTrigger.of(FamiliarTrigger.Type.CAPTURE))
                .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/capture")));
    }

    private static TranslationTextComponent text(String name, String type) {
        return new TranslationTextComponent("advancements." + Occultism.MODID + ".familiar." + name + "." + type);
    }

    public static TranslationTextComponent title(String name) {
        return text(name, "title");
    }

    public static TranslationTextComponent descr(String name) {
        return text(name, "description");
    }

    private ItemStack icon(int data) {
        ItemStack icon = OccultismItems.ADVANCEMENT_ICON.get().getDefaultInstance();
        icon.setTagInfo("CustomModelData", IntNBT.valueOf(data));
        return icon;
    }

    private Advancement add(Advancement advancement) {
        if (this.advancements.containsKey(advancement.getId()))
            throw new IllegalStateException("Duplicate advancement " + advancement.getId());
        this.advancements.put(advancement.getId(), advancement);
        return advancement;
    }

    private static Path getPath(Path path, Advancement advancement) {
        ResourceLocation id = advancement.getId();
        return path.resolve("data/" + id.getNamespace() + "/advancements/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Advancements: " + Occultism.MODID;
    }

}
