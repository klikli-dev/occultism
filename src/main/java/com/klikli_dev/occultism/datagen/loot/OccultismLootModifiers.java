package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.loot.AddItemModifier;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeItemTagsProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;

public class OccultismLootModifiers extends GlobalLootModifierProvider {

    public OccultismLootModifiers(PackOutput output) {
        super(output, Occultism.MODID);
    }

    private EntityEquipmentPredicate mainHand(ItemPredicate.Builder itemPredicate) {
        EntityEquipmentPredicate.Builder builder  = EntityEquipmentPredicate.Builder.equipment();
        builder.mainhand(itemPredicate);
        return builder.build();
    }

    private AddItemModifier tallow(EntityType<?> entityType, int count ) {
            return new AddItemModifier(
                    new LootItemCondition[]{
                            LootItemEntityPropertyCondition
                                    .hasProperties(LootContext.EntityTarget.KILLER,
                                            EntityPredicate.Builder.entity()
                                                    .equipment(mainHand(ItemPredicate.Builder.item().of(OccultismTags.makeItemTag(new ResourceLocation(Occultism.MODID,"tools/knives")))))).build(),
                            LootItemEntityPropertyCondition
                                    .hasProperties(LootContext.EntityTarget.THIS,EntityPredicate.Builder.entity().of(entityType)).build()
                    },OccultismItems.TALLOW.get(),count);
    }

    @Override
    protected void start() {
        this.add("datura_seed_from_grass", new AddItemModifier(new LootItemCondition[]{
                new LootItemRandomChanceCondition(0.2f),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                new InvertedLootItemCondition(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS)).build()
                )
        }, OccultismItems.DATURA_SEEDS.get(),1));

        this.add("datura_seed_from_tall_grass",new AddItemModifier(new LootItemCondition[]{
                new LootItemRandomChanceCondition(0.2f),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                new InvertedLootItemCondition(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS)).build()
                )
        }, OccultismItems.DATURA_SEEDS.get(),1));
        this.add("tallow_from_cows",tallow(EntityType.COW,4));
        this.add("tallow_from_donkeys",tallow(EntityType.DONKEY,3));
        this.add("tallow_from_goats",tallow(EntityType.GOAT,2));
        this.add("tallow_from_hoglins",tallow(EntityType.HOGLIN,4));
        this.add("tallow_from_horses",tallow(EntityType.HORSE,3));
        this.add("tallow_from_llamas",tallow(EntityType.LLAMA,3));
        this.add("tallow_from_mules",tallow(EntityType.MULE,3));
        this.add("tallow_from_pandas",tallow(EntityType.PANDA,3));
        this.add("tallow_from_pigs",tallow(EntityType.PIG,2));
        this.add("tallow_from_sheep",tallow(EntityType.SHEEP,2));
    }
}
