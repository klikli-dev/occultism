package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OccultismEntityTypeTagProvider extends EntityTypeTagsProvider {
    public OccultismEntityTypeTagProvider(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        addForgeTags();


    }

    private void addForgeTags() {
        this.tag(OccultismTags.Entities.SNOW_GOLEM).add(EntityType.SNOW_GOLEM).replace(false);
        this.tag(OccultismTags.Entities.AXOLOTL).add(EntityType.AXOLOTL).replace(false);
        this.tag(OccultismTags.Entities.BATS).add(EntityType.BAT).replace(false);
        this.tag(OccultismTags.Entities.CHICKEN).add(EntityType.CHICKEN).replace(false);
        this.tag(OccultismTags.Entities.COWS).add(EntityType.COW).replace(false);
        this.tag(OccultismTags.Entities.DONKEYS).add(EntityType.DONKEY).replace(false);
        this.tag(OccultismTags.Entities.FISH).add(EntityType.COD).add(EntityType.SALMON).add(EntityType.TROPICAL_FISH).add(EntityType.PUFFERFISH).replace(false);
        this.tag(OccultismTags.Entities.GOATS).add(EntityType.GOAT).replace(false);
        this.tag(OccultismTags.Entities.HOGLINS).add(EntityType.HOGLIN).replace(false);
        this.tag(OccultismTags.Entities.HORSES).add(EntityType.HORSE).replace(false);
        this.tag(OccultismTags.Entities.LLAMAS).add(EntityType.LLAMA).add(EntityType.TRADER_LLAMA).replace(false);
        this.tag(OccultismTags.Entities.MULES).add(EntityType.MULE).replace(false);
        this.tag(OccultismTags.Entities.PANDAS).add(EntityType.PANDA).replace(false);
        this.tag(OccultismTags.Entities.PARROTS).add(EntityType.PARROT).replace(false);
        this.tag(OccultismTags.Entities.PIGS).add(EntityType.PIG).replace(false);
        this.tag(OccultismTags.Entities.SHEEP).add(EntityType.SHEEP).replace(false);
        this.tag(OccultismTags.Entities.SPIDERS).add(EntityType.SPIDER).add(EntityType.CAVE_SPIDER).replace(false);
        this.tag(OccultismTags.Entities.SQUID).add(EntityType.SQUID).add(EntityType.GLOW_SQUID).replace(false);
        this.tag(OccultismTags.Entities.VILLAGERS).add(EntityType.VILLAGER).add(EntityType.WANDERING_TRADER).replace(false);
        this.tag(OccultismTags.Entities.ZOMBIES).add(EntityType.ZOMBIE).add(EntityType.ZOMBIE_VILLAGER).add(EntityType.HUSK).add(EntityType.DROWNED).replace(false);
    }
}
