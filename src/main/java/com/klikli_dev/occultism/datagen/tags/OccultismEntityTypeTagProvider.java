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
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .add(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .replace(false);
        this.tag(OccultismTags.Entities.HEALED_BY_DEMONS_DREAM_FRUIT)
                .add(OccultismEntities.FOLIOT_TYPE.get())
                .add(OccultismEntities.DJINNI_TYPE.get())
                .add(OccultismEntities.AFRIT_TYPE.get())
                .add(OccultismEntities.AFRIT_WILD_TYPE.get())
                .add(OccultismEntities.MARID_TYPE.get())
                .add(OccultismEntities.MARID_UNBOUND_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .add(OccultismEntities.POSSESSED_SKELETON_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .add(OccultismEntities.POSSESSED_PHANTOM_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get())
                .add(OccultismEntities.POSSESSED_SHULKER_TYPE.get())
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_WARDEN_TYPE.get())
                .add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get())
                .add(OccultismEntities.POSSESSED_WITCH_TYPE.get())
                .add(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE.get())
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .add(OccultismEntities.GOAT_OF_MERCY_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_BOGGED_TYPE.get())
                .add(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_SILVERFISH_TYPE.get())
                .add(OccultismEntities.WILD_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_SLIME_TYPE.get())
                .add(OccultismEntities.WILD_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_STRAY_TYPE.get())
                .add(OccultismEntities.WILD_ZOMBIE_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
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

        this.tag(OccultismTags.Entities.WILD_HUNT)
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get())
                .replace(false);
        this.tag(OccultismTags.Entities.WILD_TRIAL)
                .add(OccultismEntities.WILD_BOGGED_TYPE.get())
                .add(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_SILVERFISH_TYPE.get())
                .add(OccultismEntities.WILD_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_SLIME_TYPE.get())
                .add(OccultismEntities.WILD_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_STRAY_TYPE.get())
                .add(OccultismEntities.WILD_ZOMBIE_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
                .replace(false);

        this.tag(OccultismTags.Entities.RANDOM_ANIMALS_COMMON)
                .add(EntityType.CHICKEN)
                .add(EntityType.COW)
                .add(EntityType.PIG)
                .add(EntityType.SHEEP)
                .add(EntityType.SQUID)
                .add(EntityType.WOLF);

        this.tag(OccultismTags.Entities.RANDOM_ANIMALS_WATER)
                .add(EntityType.AXOLOTL)
                .add(EntityType.FROG)
                .add(EntityType.DOLPHIN)
                .add(EntityType.SALMON)
                .add(EntityType.COD)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.SQUID)
                .add(EntityType.SNOW_GOLEM)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.TADPOLE)
                .add(EntityType.TURTLE);

        this.tag(OccultismTags.Entities.RANDOM_ANIMALS_SMALL)
                .add(EntityType.ALLAY)
                .add(EntityType.BAT)
                .add(EntityType.BEE)
                .add(EntityType.CAT)
                .add(EntityType.FOX)
                .add(EntityType.OCELOT)
                .add(EntityType.PARROT)
                .add(EntityType.RABBIT);

        this.tag(OccultismTags.Entities.RANDOM_ANIMALS_SPECIAL)
                .add(EntityType.ARMADILLO)
                .add(EntityType.IRON_GOLEM)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.PANDA)
                .add(EntityType.POLAR_BEAR)
                .add(EntityType.GOAT)
                .add(EntityType.SNIFFER);

        this.tag(OccultismTags.Entities.RANDOM_ANIMALS_RIDEABLE)
                .add(EntityType.PIG)
                .add(EntityType.CAMEL)
                .add(EntityType.DONKEY)
                .add(EntityType.HORSE)
                .add(EntityType.SKELETON_HORSE)
                .add(EntityType.ZOMBIE_HORSE)
                .add(EntityType.LLAMA)
                .add(EntityType.TRADER_LLAMA)
                .add(EntityType.MULE)
                .add(EntityType.STRIDER);
    }

    private void addCommonTags() {
        this.tag(OccultismTags.Entities.FRAGILE_SOUL_GEM_DENY_LIST).add(OccultismEntities.IESNIUM_GOLEM_TYPE.get()).addOptionalTag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(Tags.EntityTypes.BOSSES);
        this.tag(OccultismTags.Entities.SOUL_GEM_DENY_LIST).add(OccultismEntities.IESNIUM_GOLEM_TYPE.get()).addOptionalTag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(Tags.EntityTypes.BOSSES);

        this.tag(OccultismTags.Entities.SNOW_GOLEM).add(EntityType.SNOW_GOLEM).replace(false);
        this.tag(OccultismTags.Entities.IRON_GOLEM).add(EntityType.IRON_GOLEM).replace(false);
        this.tag(OccultismTags.Entities.AXOLOTL).add(EntityType.AXOLOTL).replace(false);
        this.tag(OccultismTags.Entities.BATS).add(EntityType.BAT).add(OccultismEntities.BAT_FAMILIAR_TYPE.get()).replace(false);
        this.tag(OccultismTags.Entities.BEES).add(EntityType.BEE).add(OccultismEntities.POSSESSED_BEE_TYPE.get()).replace(false);
        this.tag(OccultismTags.Entities.CHICKEN).add(EntityType.CHICKEN).replace(false);
        this.tag(OccultismTags.Entities.COWS).add(EntityType.COW).replace(false);
        this.tag(OccultismTags.Entities.DONKEYS).add(EntityType.DONKEY).replace(false);
        this.tag(OccultismTags.Entities.FISH).add(EntityType.COD).add(EntityType.SALMON).add(EntityType.TROPICAL_FISH).add(EntityType.PUFFERFISH).replace(false);
        this.tag(OccultismTags.Entities.GOATS).add(EntityType.GOAT).add(OccultismEntities.GOAT_OF_MERCY_TYPE.get()).add(OccultismEntities.GOAT_FAMILIAR_TYPE.get()).replace(false);
        this.tag(OccultismTags.Entities.HOGLINS).add(EntityType.HOGLIN).add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get()).replace(false);
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
        this.tag(OccultismTags.Entities.ZOMBIES).add(EntityType.ZOMBIE).add(EntityType.ZOMBIE_VILLAGER).add(EntityType.HUSK).add(EntityType.DROWNED).add(OccultismEntities.WILD_ZOMBIE_TYPE.get()).add(OccultismEntities.WILD_HUSK_TYPE.get()).add(OccultismEntities.WILD_HORDE_DROWNED.get()).add(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE.get()).replace(false);
        this.tag(OccultismTags.Entities.CAMEL).add(EntityType.CAMEL).replace(false);
        this.tag(OccultismTags.Entities.DOLPHIN).add(EntityType.DOLPHIN).replace(false);
        this.tag(OccultismTags.Entities.WOLFS).add(EntityType.WOLF).replace(false);
        this.tag(OccultismTags.Entities.OCELOT).add(EntityType.OCELOT).replace(false);
        this.tag(OccultismTags.Entities.CATS).add(EntityType.CAT).add(EntityType.OCELOT).replace(false);
        this.tag(OccultismTags.Entities.VEX).add(EntityType.VEX).replace(false);
        this.tag(OccultismTags.Entities.TADPOLES).add(EntityType.TADPOLE).replace(false);
        this.tag(OccultismTags.Entities.ALLAY).add(EntityType.ALLAY).replace(false);
        this.tag(OccultismTags.Entities.WARDEN).add(EntityType.WARDEN).add(OccultismEntities.POSSESSED_WARDEN_TYPE.get()).replace(false);
        this.tag(OccultismTags.Entities.RAVAGER).add(EntityType.RAVAGER).replace(false);
    }
}
