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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.common.advancement.RitualTrigger;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.TickTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class OccultismAdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private final DataGenerator generator;
    private final Map<ResourceLocation, Advancement> advancements;

    public OccultismAdvancementProvider(DataGenerator generator) {
        this.generator = generator;
        this.advancements = new HashMap<>();
    }

    private static TranslationTextComponent text(String name, String type) {
        return new TranslationTextComponent("advancements." + Occultism.MODID + "." + name + "." + type);
    }

    public static TranslationTextComponent title(String name) {
        return text(name, "title");
    }

    public static TranslationTextComponent descr(String name) {
        return text(name, "description");
    }

    private static TranslationTextComponent familiarText(String name, String type) {
        return new TranslationTextComponent("advancements." + Occultism.MODID + ".familiar." + name + "." + type);
    }

    public static TranslationTextComponent familiarTitle(String name) {
        return familiarText(name, "title");
    }

    public static TranslationTextComponent familiarDescr(String name) {
        return familiarText(name, "description");
    }

        private static Path getPath (Path path, Advancement advancement){
            ResourceLocation id = advancement.getId();
            return path.resolve("data/" + id.getNamespace() + "/advancements/" + id.getPath() + ".json");
        }

        @Override
        public void run (DirectoryCache cache) throws IOException {
            Path folder = this.generator.getOutputFolder();
            this.start();

            for (Advancement advancement : this.advancements.values()) {
                Path path = getPath(folder, advancement);
                try {
                    IDataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path);
                } catch (IOException exception) {
                    LOGGER.error("Couldn't save advancement {}", path, exception);
                }
            }
        }

        private void start () {
            Advancement root = this.add(Advancement.Builder.advancement()
//                    .display(OccultismItems.JEI_DUMMY_NONE.get(),
//                            title("root"),
//                            descr("root"),
//                            new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, true,
//                            true, true)
                    .addCriterion("occultism_present",
                            new TickTrigger.Instance(EntityPredicate.AndPredicate.ANY))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/root")));

            Advancement familiarsRoot = this.add(Advancement.Builder.advancement()
                    .display(OccultismItems.JEI_DUMMY_NONE.get(),
                            title("familiars"),
                            descr("familiars"),
                            new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, true,
                            true, false)
                    .addCriterion("summon_familiar",
                            new RitualTrigger.Instance(new RitualTrigger.RitualPredicate(null, OccultismRituals.FAMILIAR_RITUAL.getId())))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/root")));

            //Familiar advancements
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(2), familiarTitle("deer"), familiarDescr("deer"), null, FrameType.TASK, true, true, false)
                    .addCriterion("deer_poop", FamiliarTrigger.of(FamiliarTrigger.Type.DEER_POOP))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/deer")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(0), familiarTitle("cthulhu"), familiarDescr("cthulhu"), null, FrameType.TASK, true, true, false)
                    .addCriterion("cthulhu_sad", FamiliarTrigger.of(FamiliarTrigger.Type.CTHULHU_SAD))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/cthulhu")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(1), familiarTitle("bat"), familiarDescr("bat"), null, FrameType.TASK, true, true, false)
                    .addCriterion("bat_eat", FamiliarTrigger.of(FamiliarTrigger.Type.BAT_EAT))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/bat")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(3), familiarTitle("devil"), familiarDescr("devil"), null, FrameType.TASK, true, true, false)
                    .addCriterion("devil_fire", FamiliarTrigger.of(FamiliarTrigger.Type.DEVIL_FIRE))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/devil")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(4), familiarTitle("greedy"), familiarDescr("greedy"), null, FrameType.TASK, true, true, false)
                    .addCriterion("greedy_item", FamiliarTrigger.of(FamiliarTrigger.Type.GREEDY_ITEM))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/greedy")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(5), familiarTitle("rare"), familiarDescr("rare"), null, FrameType.TASK, true, true, false)
                    .addCriterion("rare_variant", FamiliarTrigger.of(FamiliarTrigger.Type.RARE_VARIANT))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/rare")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(Items.JUKEBOX, familiarTitle("party"), familiarDescr("party"), null, FrameType.TASK, true, true, false)
                    .addCriterion("party", FamiliarTrigger.of(FamiliarTrigger.Type.PARTY))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/party")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(OccultismItems.FAMILIAR_RING.get(), familiarTitle("capture"), familiarDescr("capture"), null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("capture", FamiliarTrigger.of(FamiliarTrigger.Type.CAPTURE))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/capture")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(Items.GOLD_NUGGET, familiarTitle("dragon_nugget"), familiarDescr("dragon_nugget"), null, FrameType.TASK,
                            true, true, false)
                    .addCriterion("dragon_nugget", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_NUGGET))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/dragon_nugget")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(this.icon(6), familiarTitle("dragon_ride"), familiarDescr("dragon_ride"), null, FrameType.TASK, true, true,
                            false)
                    .addCriterion("dragon_ride", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_RIDE))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/dragon_ride")));
            this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                    .display(Items.STICK, familiarTitle("mans_best_friend"), familiarDescr("mans_best_friend"), null, FrameType.TASK,
                            true, true, false)
                    .addCriterion("dragon_pet", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_PET))
                    .addCriterion("dragon_fetch", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_FETCH))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/mans_best_friend")));
            this.add(Advancement.Builder.advancement().parent(root)
                    .display(this.icon(7), familiarTitle("blacksmith_upgrade"), familiarDescr("blacksmith_upgrade"), null, FrameType.TASK,
                            true, true, false)
                    .addCriterion("blacksmith_upgrade", FamiliarTrigger.of(FamiliarTrigger.Type.BLACKSMITH_UPGRADE))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/familiar/blacksmith_upgrade")));

            this.addRitualHidden(root, "craft_dimensional_matrix");
            this.addRitualHidden(root, "craft_dimensional_mineshaft");
            this.addRitualHidden(root, "craft_familiar_ring");
            this.addRitualHidden(root, "craft_infused_lenses");
            this.addRitualHidden(root, "craft_infused_pickaxe");
            this.addRitualHidden(root, "craft_miner_djinni_ores");
            this.addRitualHidden(root, "craft_miner_foliot_unspecialized");
            this.addRitualHidden(root, "craft_satchel");
            this.addRitualHidden(root, "craft_soul_gem");
            this.addRitualHidden(root, "craft_stabilizer_tier1");
            this.addRitualHidden(root, "craft_stabilizer_tier2");
            this.addRitualHidden(root, "craft_stabilizer_tier3");
            this.addRitualHidden(root, "craft_stabilizer_tier4");
            this.addRitualHidden(root, "craft_stable_wormhole");
            this.addRitualHidden(root, "craft_storage_controller_base");
            this.addRitualHidden(root, "craft_storage_remote");
            this.addRitualHidden(root, "familiar_bat");
            this.addRitualHidden(root, "familiar_cthulhu");
            this.addRitualHidden(root, "familiar_deer");
            this.addRitualHidden(root, "familiar_devil");
            this.addRitualHidden(root, "familiar_dragon");
            this.addRitualHidden(root, "familiar_greedy");
            this.addRitualHidden(root, "familiar_otherworld_bird");
            this.addRitualHidden(root, "familiar_parrot");
            this.addRitualHidden(root, "possess_enderman");
            this.addRitualHidden(root, "possess_endermite");
            this.addRitualHidden(root, "possess_skeleton");
            this.addRitualHidden(root, "summon_afrit_crusher");
            this.addRitualHidden(root, "summon_afrit_rain_weather");
            this.addRitualHidden(root, "summon_afrit_thunder_weather");
            this.addRitualHidden(root, "summon_djinni_clear_weather");
            this.addRitualHidden(root, "summon_djinni_crusher");
            this.addRitualHidden(root, "summon_djinni_day_time");
            this.addRitualHidden(root, "summon_djinni_manage_machine");
            this.addRitualHidden(root, "summon_djinni_night_time");
            this.addRitualHidden(root, "summon_foliot_cleaner");
            this.addRitualHidden(root, "summon_foliot_crusher");
            this.addRitualHidden(root, "summon_foliot_lumberjack");
            this.addRitualHidden(root, "summon_foliot_otherstone_trader");
            this.addRitualHidden(root, "summon_foliot_sapling_trader");
            this.addRitualHidden(root, "summon_foliot_transport_items");
            this.addRitualHidden(root, "summon_marid_crusher");
            this.addRitualHidden(root, "summon_wild_afrit");
            this.addRitualHidden(root, "summon_wild_hunt");
        }

        private Advancement addRitualHidden(Advancement parent, String id){
            return this.add(Advancement.Builder.advancement().parent(parent)
                    .display(OccultismItems.JEI_DUMMY_NONE.get(), title(id), descr(id), null, FrameType.TASK,
                            false, false, true)
                    .addCriterion(id, new RitualTrigger.Instance(new RitualTrigger.RitualPredicate(
                            new ResourceLocation("occultism", "ritual/" + id), null)))
                    .build(new ResourceLocation(Occultism.MODID, "occultism/"+ id)));
        }

        private ItemStack icon ( int data){
            ItemStack icon = OccultismItems.ADVANCEMENT_ICON.get().getDefaultInstance();
            icon.addTagElement("CustomModelData", IntNBT.valueOf(data));
            return icon;
        }

        private Advancement add (Advancement advancement){
            if (this.advancements.containsKey(advancement.getId()))
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            this.advancements.put(advancement.getId(), advancement);
            return advancement;
        }

        @Override
        public String getName () {
            return "Advancements: " + Occultism.MODID;
        }

    }
