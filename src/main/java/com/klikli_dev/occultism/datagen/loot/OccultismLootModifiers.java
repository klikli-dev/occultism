package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.loot.AddItemModifier;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class OccultismLootModifiers extends GlobalLootModifierProvider {

    public OccultismLootModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Occultism.MODID);
    }

    private EntityEquipmentPredicate mainHand(ItemPredicate.Builder itemPredicate) {
        EntityEquipmentPredicate.Builder builder = EntityEquipmentPredicate.Builder.equipment();
        builder.mainhand(itemPredicate);
        return builder.build();
    }

    private AddItemModifier tallow(String entityType, int count) {
        return new AddItemModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition
                                .hasProperties(LootContext.EntityTarget.ATTACKER,
                                        EntityPredicate.Builder.entity()
                                                .equipment(this.mainHand(ItemPredicate.Builder.item().of(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tools/knives")))))).build(),
                        LootItemEntityPropertyCondition
                                .hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(OccultismTags.makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", entityType)))).build()
                }, OccultismItems.TALLOW.get(), count);
    }

    @Override
    protected void start() {
        this.add("datura_seed_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                new InvertedLootItemCondition(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEARS)).build()
                )
        }, OccultismItems.DATURA_SEEDS.get(), 1));

        this.add("datura_seed_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                new InvertedLootItemCondition(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEARS)).build()
                )
        }, OccultismItems.DATURA_SEEDS.get(), 1));
        this.add("tallow_from_cows", this.tallow("cows", 4));
        this.add("tallow_from_donkeys", this.tallow("donkeys", 3));
        this.add("tallow_from_goats", this.tallow("goats", 2));
        this.add("tallow_from_hoglins", this.tallow("hoglins", 4));
        this.add("tallow_from_horses", this.tallow("horses", 3));
        this.add("tallow_from_llamas", this.tallow("llamas", 3));
        this.add("tallow_from_mules", this.tallow("mules", 3));
        this.add("tallow_from_pandas", this.tallow("pandas", 3));
        this.add("tallow_from_pigs", this.tallow("pigs", 2));
        this.add("tallow_from_sheep", this.tallow("sheep", 2));
    }
}
