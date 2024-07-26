package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OccultismEntityTypeTagProvider extends EntityTypeTagsProvider {
    public OccultismEntityTypeTagProvider(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.addCommonTags();
        this.addOccultismTags(pProvider);
        this.addPerViamInvenireTags(pProvider);
    }

    private void addPerViamInvenireTags(HolderLookup.Provider provider) {
        this.tag(OccultismTags.makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("per_viam_invenire", "replace_vanilla_navigator")))
                .add(OccultismEntities.FOLIOT_TYPE.get())
                .add(OccultismEntities.DJINNI_TYPE.get())
                .add(OccultismEntities.AFRIT_TYPE.get())
                .add(OccultismEntities.MARID_TYPE.get())
                .add(OccultismEntities.GREEDY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get())
                .add(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GOAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.MUMMY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEAVER_FAMILIAR_TYPE.get()).replace(false);
    }

    private void addOccultismTags(HolderLookup.Provider pProvider) {
        this.tag(OccultismTags.Entities.AFRIT_ALLIES)
                .add(EntityType.BLAZE)
                .replace(false);
        this.tag(OccultismTags.Entities.CUBEMOB)
                .add(EntityType.SLIME)
                .add(EntityType.MAGMA_CUBE)
                .replace(false);
        this.tag(OccultismTags.Entities.FLYING_PASSIVE)
                .add(EntityType.BAT)
                .add(EntityType.PARROT)
                .add(EntityType.BEE)
                .add(EntityType.ALLAY)
                .replace(false);
        this.tag(OccultismTags.Entities.HEALED_BY_DEMONS_DREAM_FRUIT)
                .add(OccultismEntities.FOLIOT_TYPE.get())
                .add(OccultismEntities.DJINNI_TYPE.get())
                .add(OccultismEntities.AFRIT_TYPE.get())
                .add(OccultismEntities.AFRIT_WILD_TYPE.get())
                .add(OccultismEntities.MARID_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get())
                .add(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .add(OccultismEntities.GREEDY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .add(EntityType.PARROT)
                .add(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DRAGON_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get())
                .add(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GOAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.SHUB_NIGGURATH_SPAWN_TYPE.get())
                .add(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.MUMMY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEMONIC_WIFE.get())
                .add(OccultismEntities.DEMONIC_HUSBAND.get()).replace(false);

        this.tag(OccultismTags.Entities.HUMANS)
                .addTags(OccultismTags.Entities.VILLAGERS)
                .add(EntityType.PLAYER)
                .replace(false);

        this.tag(OccultismTags.Entities.SOUL_GEM_DENY_LIST)
                .add(EntityType.WITHER, EntityType.ENDER_DRAGON)
                .replace(false);
        this.tag(OccultismTags.Entities.WILD_HUNT)
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get())
                .replace(false);


    }

    private void addCommonTags() {
        this.tag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(OccultismTags.Entities.SOUL_GEM_DENY_LIST);
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
        this.tag(OccultismTags.Entities.CAMEL).add(EntityType.CAMEL).replace(false);
        this.tag(OccultismTags.Entities.DOLPHIN).add(EntityType.DOLPHIN).replace(false);
    }
}
