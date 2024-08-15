package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.loot.AddItemModifier;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class OccultismLootModifiers extends GlobalLootModifierProvider {
    public OccultismLootModifiers(PackOutput output) {
        super(output, Occultism.MODID);
    }

    private EntityEquipmentPredicate mainHand(ItemPredicate itemPredicate) {
        EntityEquipmentPredicate.Builder builder  = EntityEquipmentPredicate.Builder.equipment();
        builder.mainhand(itemPredicate);
        return builder.build();
    }

    private AddItemModifier tallow(String entityType, int count ) {
        return new AddItemModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition
                                .hasProperties(LootContext.EntityTarget.KILLER,
                                        EntityPredicate.Builder.entity()
                                                .equipment(mainHand(ItemPredicate.Builder.item().of(OccultismTags.makeItemTag(new ResourceLocation(Occultism.MODID,"tools/knives"))).build()))).build(),
                        LootItemEntityPropertyCondition
                                .hasProperties(LootContext.EntityTarget.THIS,EntityPredicate.Builder.entity().of(OccultismTags.makeEntityTypeTag(new ResourceLocation("forge",entityType)))).build()
                }, OccultismItems.TALLOW.get(),count);
    }
    @Override
    protected void start() {
        this.add("datura_seed_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))
                ).build()
        }, OccultismItems.DATURA_SEEDS.get(),1));

        this.add("datura_seed_from_tall_grass",new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))
                ).build()
        }, OccultismItems.DATURA_SEEDS.get(),1));
        this.add("tallow_from_cows",tallow("cows",4));
        this.add("tallow_from_donkeys",tallow("donkeys",3));
        this.add("tallow_from_goats",tallow("goats",2));
        this.add("tallow_from_hoglins",tallow("hoglins",4));
        this.add("tallow_from_horses",tallow("horses",3));
        this.add("tallow_from_llamas",tallow("llamas",3));
        this.add("tallow_from_mules",tallow("mules",3));
        this.add("tallow_from_pandas",tallow("pandas",3));
        this.add("tallow_from_pigs",tallow("pigs",2));
        this.add("tallow_from_sheep",tallow("sheep",2));
    }
}
