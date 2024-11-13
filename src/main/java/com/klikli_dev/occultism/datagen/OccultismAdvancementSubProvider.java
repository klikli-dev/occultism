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
import com.klikli_dev.occultism.common.advancement.RitualTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRituals;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

public class OccultismAdvancementSubProvider implements AdvancementProvider.AdvancementGenerator {

    protected Consumer<AdvancementHolder> saver;
    protected HolderLookup.Provider registries;
    protected ExistingFileHelper existingFileHelper;

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
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        this.registries = registries;
        this.saver = saver;
        this.existingFileHelper = existingFileHelper;
        this.start();
    }

    private void start() {
        var root = this.add(Advancement.Builder.advancement()
//                    .display(OccultismItems.JEI_DUMMY_NONE.get(),
//                            title("root"),
//                            descr("root"),
//                            ResourceLocation.parse("textures/gui/advancements/backgrounds/stone.png"), AdvancementType.TASK, true,
//                            true, true)
                .addCriterion("occultism_present", PlayerTrigger.TriggerInstance.tick())
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/root")));

        var familiarsRoot = this.add(Advancement.Builder.advancement()
                .display(OccultismItems.PENTACLE_POSSESS.get(),
                        title("familiars"),
                        descr("familiars"),
                        ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"textures/block/otherplanks.png"), AdvancementType.TASK, true,
                        true, false)
                .addCriterion("summon_familiar",
                        RitualTrigger.TriggerInstance.ritualFactory(OccultismRituals.FAMILIAR.getId()))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/root")));

        //Familiar advancements
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(2), familiarTitle("deer"), familiarDescr("deer"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("deer_poop", FamiliarTrigger.of(FamiliarTrigger.Type.DEER_POOP))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/deer")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(0), familiarTitle("cthulhu"), familiarDescr("cthulhu"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("cthulhu_sad", FamiliarTrigger.of(FamiliarTrigger.Type.CTHULHU_SAD))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/cthulhu")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(1), familiarTitle("bat"), familiarDescr("bat"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("bat_eat", FamiliarTrigger.of(FamiliarTrigger.Type.BAT_EAT))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/bat")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(3), familiarTitle("devil"), familiarDescr("devil"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("devil_fire", FamiliarTrigger.of(FamiliarTrigger.Type.DEVIL_FIRE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/devil")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(4), familiarTitle("greedy"), familiarDescr("greedy"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("greedy_item", FamiliarTrigger.of(FamiliarTrigger.Type.GREEDY_ITEM))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/greedy")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(5), familiarTitle("rare"), familiarDescr("rare"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("rare_variant", FamiliarTrigger.of(FamiliarTrigger.Type.RARE_VARIANT))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/rare")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(Items.JUKEBOX, familiarTitle("party"), familiarDescr("party"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("party", FamiliarTrigger.of(FamiliarTrigger.Type.PARTY))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/party")));
        var familiarRingStack = new ItemStack(OccultismItems.FAMILIAR_RING.get());
        familiarRingStack.set(OccultismDataComponents.SPIRIT_NAME, "Gardelldor");
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(new DisplayInfo(
                        familiarRingStack, familiarTitle("capture"), familiarDescr("capture"), Optional.ofNullable(null), AdvancementType.TASK, true, true, false)
                )
                .addCriterion("capture", FamiliarTrigger.of(FamiliarTrigger.Type.CAPTURE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/capture")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(Items.GOLD_NUGGET, familiarTitle("dragon_nugget"), familiarDescr("dragon_nugget"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("dragon_nugget", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_NUGGET))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/dragon_nugget")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(6), familiarTitle("dragon_ride"), familiarDescr("dragon_ride"), null, AdvancementType.TASK, true, true,
                        false)
                .addCriterion("dragon_ride", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_RIDE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/dragon_ride")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(Items.STICK, familiarTitle("mans_best_friend"), familiarDescr("mans_best_friend"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("dragon_pet", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_PET))
                .addCriterion("dragon_fetch", FamiliarTrigger.of(FamiliarTrigger.Type.DRAGON_FETCH))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/mans_best_friend")));
        this.add(Advancement.Builder.advancement().parent(root)
                .display(this.icon(7), familiarTitle("blacksmith_upgrade"), familiarDescr("blacksmith_upgrade"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("blacksmith_upgrade", FamiliarTrigger.of(FamiliarTrigger.Type.BLACKSMITH_UPGRADE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/blacksmith_upgrade")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(8), familiarTitle("guardian_ultimate_sacrifice"), familiarDescr("guardian_ultimate_sacrifice"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("guardian_ultimate_sacrifice", FamiliarTrigger.of(FamiliarTrigger.Type.GUARDIAN_ULTIMATE_SACRIFICE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/guardian_ultimate_sacrifice")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(9), familiarTitle("headless_cthulhu_head"), familiarDescr("headless_cthulhu_head"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("headless_cthulhu_head", FamiliarTrigger.of(FamiliarTrigger.Type.HEADLESS_CTHULHU_HEAD))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/headless_cthulhu_head")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(Items.HAY_BLOCK, familiarTitle("headless_rebuilt"), familiarDescr("headless_rebuilt"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("headless_rebuilt", FamiliarTrigger.of(FamiliarTrigger.Type.HEADLESS_REBUILT))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/headless_rebuilt")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(10), familiarTitle("chimera_ride"), familiarDescr("chimera_ride"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("chimera_ride", FamiliarTrigger.of(FamiliarTrigger.Type.CHIMERA_RIDE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/chimera_ride")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(Items.GOLDEN_APPLE, familiarTitle("goat_detach"), familiarDescr("goat_detach"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("goat_detach", FamiliarTrigger.of(FamiliarTrigger.Type.GOAT_DETACH))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/goat_detach")));
        var summonShub = this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(11), familiarTitle("shub_niggurath_summon"), familiarDescr("shub_niggurath_summon"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("shub_niggurath_summon", FamiliarTrigger.of(FamiliarTrigger.Type.SHUB_NIGGURATH_SUMMON))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/shub_niggurath_summon")));
        this.add(Advancement.Builder.advancement().parent(summonShub)
                .display(Items.POPPY, familiarTitle("shub_cthulhu_friends"), familiarDescr("shub_cthulhu_friends"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("shub_cthulhu_friends", FamiliarTrigger.of(FamiliarTrigger.Type.SHUB_CTHULHU_FRIENDS))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/shub_cthulhu_friends")));
        this.add(Advancement.Builder.advancement().parent(summonShub)
                .display(this.icon(12), familiarTitle("shub_niggurath_spawn"), familiarDescr("shub_niggurath_spawn"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("shub_niggurath_spawn", FamiliarTrigger.of(FamiliarTrigger.Type.SHUB_NIGGURATH_SPAWN))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/shub_niggurath_spawn")));

        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(13), familiarTitle("beholder_ray"), familiarDescr("beholder_ray"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("beholder_ray", FamiliarTrigger.of(FamiliarTrigger.Type.BEHOLDER_RAY))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/beholder_ray")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(Items.PUMPKIN_PIE, familiarTitle("beholder_eat"), familiarDescr("beholder_eat"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("beholder_eat", FamiliarTrigger.of(FamiliarTrigger.Type.BEHOLDER_EAT))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/beholder_eat")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(14), familiarTitle("fairy_save"), familiarDescr("fairy_save"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("fairy_save", FamiliarTrigger.of(FamiliarTrigger.Type.FAIRY_SAVE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/fairy_save")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(15), familiarTitle("mummy_dodge"), familiarDescr("mummy_dodge"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("mummy_dodge", FamiliarTrigger.of(FamiliarTrigger.Type.MUMMY_DODGE))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/mummy_dodge")));
        this.add(Advancement.Builder.advancement().parent(familiarsRoot)
                .display(this.icon(16), familiarTitle("beaver_woodchop"), familiarDescr("beaver_woodchop"), null, AdvancementType.TASK,
                        true, true, false)
                .addCriterion("beaver_woodchop", FamiliarTrigger.of(FamiliarTrigger.Type.BEAVER_WOODCHOP))
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/familiar/beaver_woodchop")));

        //Never forget add hidden advancement to new ritual
        BuiltInRegistries.ITEM.forEach(item -> {
            var key = BuiltInRegistries.ITEM.getKey(item);
            if (key.getPath().startsWith("ritual_dummy/") && !key.getPath().startsWith("ritual_dummy/custom")) {
                this.addRitualHidden(root, key.getPath().substring(13));
            }
        });
    }

    private AdvancementHolder addRitualHidden(AdvancementHolder parent, String id) {
        return this.add(Advancement.Builder.advancement().parent(parent)
                .display(OccultismItems.JEI_DUMMY_NONE.get(), title(id), descr(id), null, AdvancementType.TASK,
                        false, false, true)
                .addCriterion(id,
                        OccultismAdvancements.RITUAL.get().createCriterion(new RitualTrigger.TriggerInstance(
                                Optional.empty(),
                                Optional.of(ResourceLocation.fromNamespaceAndPath("occultism", "ritual/" + id)),
                                Optional.empty()
                        ))
                )
                .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "occultism/" + id)));
    }

    private AdvancementHolder add(AdvancementHolder advancement) {
        this.saver.accept(advancement);
        return advancement;
    }

    private ItemStack icon(int data) {
        ItemStack icon = OccultismItems.ADVANCEMENT_ICON.get().getDefaultInstance();
        icon.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(data));
        return icon;
    }

}
