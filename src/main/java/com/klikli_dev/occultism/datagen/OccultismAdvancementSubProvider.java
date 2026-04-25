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

package com.klikli_dev.occultism.datagen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger.Type;
import com.klikli_dev.occultism.common.advancement.RitualTrigger;
import com.klikli_dev.occultism.common.advancement.RitualTrigger.TriggerInstance;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRituals;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.data.advancements.AdvancementSubProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class OccultismAdvancementSubProvider implements AdvancementSubProvider {

    protected Consumer<AdvancementHolder> saver;
    protected Provider registries;

    private static MutableComponent text(String name, String type) {
        return Component.translatable("advancements." + Occultism.MODID + "." + name + "." + type);
    }

    public static MutableComponent title(String name) {
        return text(name, "title");
    }

    public static MutableComponent descr(String name) {
        return text(name, "description");
    }

    private static MutableComponent familiarText(String name, String type) {
        return Component.translatable("advancements." + Occultism.MODID + ".familiar." + name + "." + type);
    }

    public static MutableComponent familiarTitle(String name) {
        return familiarText(name, "title");
    }

    public static MutableComponent familiarDescr(String name) {
        return familiarText(name, "description");
    }

    @Override
    public void generate(Provider registries, Consumer<AdvancementHolder> saver) {
        this.registries = registries;
        this.saver = saver;
        this.start();
    }

    private void start() {
        var root = this.add(Builder.advancement()
//                .display(
//                        new ItemStackTemplate(OccultismItems.JEI_DUMMY_NONE.get().builtInRegistryHolder()),
//                        title("root"),
//                        descr("root"),
//                        Identifier.fromNamespaceAndPath(Occultism.MODID,"block/otherstone"),
//                        AdvancementType.TASK,
//                        true,
//                        true,
//                        true
//                )
                .addCriterion("occultism_present", PlayerTrigger.TriggerInstance.tick())
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/root")));

        var familiarsRoot = this.add(Builder.advancement()
                .display(OccultismItems.PENTACLE_POSSESS.get(),
                        title("familiars"),
                        descr("familiars"),
                        Identifier.fromNamespaceAndPath(Occultism.MODID,"block/otherplanks"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("summon_familiar",
                        TriggerInstance.ritualFactory(OccultismRituals.FAMILIAR.getId()))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/root")));

        //Familiar advancements
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(2), familiarTitle("deer"), familiarDescr("deer"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("deer_poop", FamiliarTrigger.of(Type.DEER_POOP))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/deer")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(0), familiarTitle("cthulhu"), familiarDescr("cthulhu"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("cthulhu_sad", FamiliarTrigger.of(Type.CTHULHU_SAD))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/cthulhu")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(1), familiarTitle("bat"), familiarDescr("bat"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("bat_eat", FamiliarTrigger.of(Type.BAT_EAT))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/bat")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(3), familiarTitle("devil"), familiarDescr("devil"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("devil_fire", FamiliarTrigger.of(Type.DEVIL_FIRE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/devil")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(4), familiarTitle("greedy"), familiarDescr("greedy"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("greedy_item", FamiliarTrigger.of(Type.GREEDY_ITEM))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/greedy")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(5), familiarTitle("rare"), familiarDescr("rare"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("rare_variant", FamiliarTrigger.of(Type.RARE_VARIANT))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/rare")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(Items.JUKEBOX, familiarTitle("party"), familiarDescr("party"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("party", FamiliarTrigger.of(Type.PARTY))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/party")));
        var familiarRingTemplate = new ItemStackTemplate(
                OccultismItems.FAMILIAR_RING.get().builtInRegistryHolder(), 1,
                DataComponentPatch.builder()
                        .set(OccultismDataComponents.SPIRIT_NAME.get(), "Gardelldor")
                        .build());
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(new DisplayInfo(
                        familiarRingTemplate, familiarTitle("capture"), familiarDescr("capture"), Optional.empty(), AdvancementType.TASK, true, true, false)
                )
                .addCriterion("capture", FamiliarTrigger.of(Type.CAPTURE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/capture")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(Items.GOLD_NUGGET, familiarTitle("dragon_nugget"), familiarDescr("dragon_nugget"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("dragon_nugget", FamiliarTrigger.of(Type.DRAGON_NUGGET))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/dragon_nugget")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(6), familiarTitle("dragon_ride"), familiarDescr("dragon_ride"), null, AdvancementType.TASK, true, true,
                        false)
                .addCriterion("dragon_ride", FamiliarTrigger.of(Type.DRAGON_RIDE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/dragon_ride")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(Items.STICK, familiarTitle("mans_best_friend"), familiarDescr("mans_best_friend"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("dragon_pet", FamiliarTrigger.of(Type.DRAGON_PET))
                .addCriterion("dragon_fetch", FamiliarTrigger.of(Type.DRAGON_FETCH))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/mans_best_friend")));
        this.add(Builder.advancement().parent(root)
                .display(this.icon(7), familiarTitle("blacksmith_upgrade"), familiarDescr("blacksmith_upgrade"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("blacksmith_upgrade", FamiliarTrigger.of(Type.BLACKSMITH_UPGRADE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/blacksmith_upgrade")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(8), familiarTitle("guardian_ultimate_sacrifice"), familiarDescr("guardian_ultimate_sacrifice"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("guardian_ultimate_sacrifice", FamiliarTrigger.of(Type.GUARDIAN_ULTIMATE_SACRIFICE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/guardian_ultimate_sacrifice")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(9), familiarTitle("headless_cthulhu_head"), familiarDescr("headless_cthulhu_head"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("headless_cthulhu_head", FamiliarTrigger.of(Type.HEADLESS_CTHULHU_HEAD))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/headless_cthulhu_head")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(Items.HAY_BLOCK, familiarTitle("headless_rebuilt"), familiarDescr("headless_rebuilt"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("headless_rebuilt", FamiliarTrigger.of(Type.HEADLESS_REBUILT))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/headless_rebuilt")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(10), familiarTitle("chimera_ride"), familiarDescr("chimera_ride"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("chimera_ride", FamiliarTrigger.of(Type.CHIMERA_RIDE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/chimera_ride")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(Items.GOLDEN_APPLE, familiarTitle("goat_detach"), familiarDescr("goat_detach"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("goat_detach", FamiliarTrigger.of(Type.GOAT_DETACH))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/goat_detach")));
        var summonShub = this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(11), familiarTitle("shub_niggurath_summon"), familiarDescr("shub_niggurath_summon"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("shub_niggurath_summon", FamiliarTrigger.of(Type.SHUB_NIGGURATH_SUMMON))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/shub_niggurath_summon")));
        this.add(Builder.advancement().parent(summonShub)
                .display(Items.POPPY, familiarTitle("shub_cthulhu_friends"), familiarDescr("shub_cthulhu_friends"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("shub_cthulhu_friends", FamiliarTrigger.of(Type.SHUB_CTHULHU_FRIENDS))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/shub_cthulhu_friends")));
        this.add(Builder.advancement().parent(summonShub)
                .display(this.icon(12), familiarTitle("shub_niggurath_spawn"), familiarDescr("shub_niggurath_spawn"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("shub_niggurath_spawn", FamiliarTrigger.of(Type.SHUB_NIGGURATH_SPAWN))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/shub_niggurath_spawn")));

        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(13), familiarTitle("beholder_ray"), familiarDescr("beholder_ray"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("beholder_ray", FamiliarTrigger.of(Type.BEHOLDER_RAY))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/beholder_ray")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(Items.PUMPKIN_PIE, familiarTitle("beholder_eat"), familiarDescr("beholder_eat"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("beholder_eat", FamiliarTrigger.of(Type.BEHOLDER_EAT))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/beholder_eat")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(14), familiarTitle("fairy_save"), familiarDescr("fairy_save"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("fairy_save", FamiliarTrigger.of(Type.FAIRY_SAVE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/fairy_save")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(15), familiarTitle("mummy_dodge"), familiarDescr("mummy_dodge"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("mummy_dodge", FamiliarTrigger.of(Type.MUMMY_DODGE))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/mummy_dodge")));
        this.add(Builder.advancement().parent(familiarsRoot)
                .display(this.icon(16), familiarTitle("beaver_woodchop"), familiarDescr("beaver_woodchop"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("beaver_woodchop", FamiliarTrigger.of(Type.BEAVER_WOODCHOP))
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/beaver_woodchop")));

        //Never forget add hidden advancement to new ritual
        BuiltInRegistries.ITEM.forEach(item -> {
            var key = BuiltInRegistries.ITEM.getKey(item);
            if (key.getPath().startsWith("ritual_dummy/") && !key.getPath().startsWith("ritual_dummy/custom")) {
                this.addRitualHidden(root, key.getPath().substring(13));
            }
        });
    }

    private AdvancementHolder addRitualHidden(AdvancementHolder parent, String id) {
        return this.add(Builder.advancement().parent(parent)
                .display(OccultismItems.JEI_DUMMY_NONE.get(), title(id), descr(id), null, AdvancementType.TASK,
                        false, false, true)
                .addCriterion(id,
                        OccultismAdvancements.RITUAL.get().createCriterion(new TriggerInstance(
                                Optional.empty(),
                                Optional.of(Identifier.fromNamespaceAndPath("occultism", "ritual/" + id)),
                                Optional.empty()
                        ))
                )
                .build(Identifier.fromNamespaceAndPath(Occultism.MODID, "occultism/" + id)));
    }

    private AdvancementHolder add(AdvancementHolder advancement) {
        this.saver.accept(advancement);
        return advancement;
    }

    private ItemStackTemplate icon(int data) {
        var patch = DataComponentPatch.builder()
                .set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of(), List.of(), List.of(), List.of(data)))
                .build();
        return new ItemStackTemplate(OccultismItems.ADVANCEMENT_ICON.get().builtInRegistryHolder(), 1, patch);
    }

}
