package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class OccultismEntityTypeTagProvider extends EntityTypeTagsProvider {
    public OccultismEntityTypeTagProvider(PackOutput p_256095_, CompletableFuture<Provider> p_256572_) {
        super(p_256095_, p_256572_, Occultism.MODID);
    }

    @Override
    protected void addTags(@NotNull Provider pProvider) {
        this.addCommonTags();
        this.addVanillaTags();
        this.addOccultismTags();
        this.addPerViamInvenireTags();
    }

    private void addPerViamInvenireTags() {
        this.tag(OccultismTags.makeEntityTypeTag(Identifier.fromNamespaceAndPath("per_viam_invenire", "replace_vanilla_navigator")))
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

    private void addOccultismTags() {
        this.tag(Entities.AFRIT_ALLIES)
                .add(EntityType.BLAZE)
                .replace(false);
        this.tag(Entities.CUBEMOB)
                .add(EntityType.SLIME)
                .add(EntityType.MAGMA_CUBE)
                .replace(false);
        this.tag(Entities.CREEPER)
                .add(EntityType.CREEPER)
                .add(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get())
                .replace(false);
        this.tag(Entities.FLYING_PASSIVE)
                .add(EntityType.BAT)
                .add(EntityType.PARROT)
                .add(EntityType.BEE)
                .add(EntityType.ALLAY)
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .add(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .replace(false);
        this.tag(Entities.HEALED_BY_DEMONS_DREAM_FRUIT)
                //Spirit
                .add(OccultismEntities.FOLIOT_TYPE.get())
                .add(OccultismEntities.DJINNI_TYPE.get())
                .add(OccultismEntities.AFRIT_TYPE.get())
                .add(OccultismEntities.AFRIT_UNBOUND_TYPE.get())
                .add(OccultismEntities.MARID_TYPE.get())
                .add(OccultismEntities.MARID_UNBOUND_TYPE.get())
                .add(OccultismEntities.DEMONIC_WIFE.get())
                .add(OccultismEntities.DEMONIC_HUSBAND.get())
                .add(OccultismEntities.WONDERING_TRADER_TYPE.get())
                //Possessed
                .add(OccultismEntities.GOAT_OF_MERCY_TYPE.get())
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BLAZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .add(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .add(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get())
                .add(OccultismEntities.POSSESSED_PHANTOM_TYPE.get())
                .add(OccultismEntities.POSSESSED_SHULKER_TYPE.get())
                .add(OccultismEntities.POSSESSED_SKELETON_TYPE.get())
                .add(OccultismEntities.POSSESSED_WARDEN_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get())
                .add(OccultismEntities.POSSESSED_WITCH_TYPE.get())
                .add(OccultismEntities.POSSESSED_ZOMBIFIED_PIGLIN_TYPE.get())
                //Horde
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_EVOKER_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
                .add(OccultismEntities.WILD_BOGGED_TYPE.get())
                .add(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_SILVERFISH_TYPE.get())
                .add(OccultismEntities.WILD_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_SLIME_TYPE.get())
                .add(OccultismEntities.WILD_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_STRAY_TYPE.get())
                .add(OccultismEntities.WILD_ZOMBIE_TYPE.get())
                //Familiar
                .add(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DRAGON_FAMILIAR_TYPE.get())
                .add(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GOAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GREEDY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get())
                .add(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get())
                .add(OccultismEntities.MUMMY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .add(OccultismEntities.SHUB_NIGGURATH_SPAWN_TYPE.get())
                .add(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get())
                .replace(false);

        this.tag(Entities.HUMANS)
                .addTag(Entities.VILLAGERS)
                .add(EntityType.PLAYER)
                .replace(false);

        this.tag(Entities.WILD_HUNT)
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get())
                .replace(false);

        this.tag(Entities.WILD_DESERT)
                .add(OccultismEntities.WILD_HORDE_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get())
                .replace(false);

        this.tag(Entities.WILD_TRIAL)
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

        this.tag(Entities.RANDOM_ANIMALS_COMMON)
                .add(EntityType.CHICKEN)
                .add(EntityType.COW)
                .add(EntityType.PIG)
                .add(EntityType.SHEEP)
                .add(EntityType.SQUID)
                .add(EntityType.WOLF);

        this.tag(Entities.RANDOM_ANIMALS_WATER)
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
                .add(EntityType.TURTLE)
                .add(EntityType.NAUTILUS)
                .add(EntityType.ZOMBIE_NAUTILUS);

        this.tag(Entities.RANDOM_ANIMALS_SMALL)
                .add(EntityType.ALLAY)
                .add(EntityType.BAT)
                .add(EntityType.BEE)
                .add(EntityType.CAT)
                .add(EntityType.FOX)
                .add(EntityType.OCELOT)
                .add(EntityType.PARROT)
                .add(EntityType.RABBIT);

        this.tag(Entities.RANDOM_ANIMALS_SPECIAL)
                .add(EntityType.ARMADILLO)
                .add(EntityType.IRON_GOLEM)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.PANDA)
                .add(EntityType.POLAR_BEAR)
                .add(EntityType.GOAT)
                .add(EntityType.SNIFFER)
                .add(EntityType.COPPER_GOLEM);

        this.tag(Entities.RANDOM_ANIMALS_RIDEABLE)
                .add(EntityType.PIG)
                .add(EntityType.CAMEL)
                .add(EntityType.DONKEY)
                .add(EntityType.HORSE)
                .add(EntityType.SKELETON_HORSE)
                .add(EntityType.ZOMBIE_HORSE)
                .add(EntityType.LLAMA)
                .add(EntityType.TRADER_LLAMA)
                .add(EntityType.MULE)
                .add(EntityType.STRIDER)
                .add(EntityType.HAPPY_GHAST)
                .add(EntityType.NAUTILUS)
                .add(EntityType.ZOMBIE_NAUTILUS)
                .add(EntityType.CAMEL_HUSK);

        this.tag(Entities.FORCE_KILL_SIMULATION)
                .add(EntityType.WITHER);
    }

    private void addCommonTags() {
        this.tag(Entities.FRAGILE_SOUL_GEM_DENY_LIST).add(OccultismEntities.IESNIUM_GOLEM_TYPE.get()).addOptionalTag(EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(EntityTypes.BOSSES);
        this.tag(Entities.SOUL_GEM_DENY_LIST).add(OccultismEntities.IESNIUM_GOLEM_TYPE.get()).addOptionalTag(EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(EntityTypes.BOSSES);

        this.tag(Entities.SNOW_GOLEM).add(EntityType.SNOW_GOLEM).replace(false);
        this.tag(Entities.COPPER_GOLEM).add(EntityType.COPPER_GOLEM).replace(false);
        this.tag(Entities.IRON_GOLEM).add(EntityType.IRON_GOLEM).replace(false);
        this.tag(Entities.AXOLOTL).add(EntityType.AXOLOTL).replace(false);
        this.tag(Entities.BATS).add(EntityType.BAT).add(OccultismEntities.BAT_FAMILIAR_TYPE.get()).replace(false);
        this.tag(Entities.BEES).add(EntityType.BEE).add(OccultismEntities.POSSESSED_BEE_TYPE.get()).replace(false);
        this.tag(Entities.CHICKEN).add(EntityType.CHICKEN).replace(false);
        this.tag(Entities.COWS).add(EntityType.COW).add(EntityType.MOOSHROOM).replace(false);
        this.tag(Entities.DONKEYS).add(EntityType.DONKEY).replace(false);
        this.tag(Entities.FISH).add(EntityType.COD).add(EntityType.SALMON).add(EntityType.TROPICAL_FISH).add(EntityType.PUFFERFISH).replace(false);
        this.tag(Entities.GOATS).add(EntityType.GOAT).add(OccultismEntities.GOAT_OF_MERCY_TYPE.get()).add(OccultismEntities.GOAT_FAMILIAR_TYPE.get()).replace(false);
        this.tag(Entities.HOGLINS).add(EntityType.HOGLIN).add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get()).replace(false);
        this.tag(Entities.HORSES).add(EntityType.HORSE).replace(false);
        this.tag(Entities.LLAMAS).add(EntityType.LLAMA).add(EntityType.TRADER_LLAMA).replace(false);
        this.tag(Entities.MULES).add(EntityType.MULE).replace(false);
        this.tag(Entities.PANDAS).add(EntityType.PANDA).replace(false);
        this.tag(Entities.PARROTS).add(EntityType.PARROT).replace(false);
        this.tag(Entities.PIGS).add(EntityType.PIG).replace(false);
        this.tag(Entities.SHEEP).add(EntityType.SHEEP).replace(false);
        this.tag(Entities.SPIDERS).add(EntityType.SPIDER).add(EntityType.CAVE_SPIDER).replace(false);
        this.tag(Entities.SQUID).add(EntityType.SQUID).add(EntityType.GLOW_SQUID).replace(false);
        this.tag(Entities.VILLAGERS).add(EntityType.VILLAGER).add(EntityType.WANDERING_TRADER).add(OccultismEntities.WONDERING_TRADER_TYPE.get()).replace(false);
        this.tag(Entities.CAMEL).add(EntityType.CAMEL).replace(false);
        this.tag(Entities.DOLPHIN).add(EntityType.DOLPHIN).replace(false);
        this.tag(Entities.WOLFS).add(EntityType.WOLF).replace(false);
        this.tag(Entities.OCELOT).add(EntityType.OCELOT).replace(false);
        this.tag(Entities.CATS).add(EntityType.CAT).add(EntityType.OCELOT).replace(false);
        this.tag(Entities.VEX).add(EntityType.VEX).replace(false);
        this.tag(Entities.TADPOLES).add(EntityType.TADPOLE).replace(false);
        this.tag(Entities.ALLAY).add(EntityType.ALLAY).replace(false);
        this.tag(Entities.ARMADILLOS).add(EntityType.ARMADILLO).replace(false);
        this.tag(Entities.WARDEN).add(EntityType.WARDEN).add(OccultismEntities.POSSESSED_WARDEN_TYPE.get()).replace(false);
        this.tag(Entities.RAVAGER).add(EntityType.RAVAGER).replace(false);
        this.tag(Entities.ENDERMEN).add(EntityType.ENDERMAN).add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get()).replace(false);
        this.tag(Entities.SHULKER).add(EntityType.SHULKER).add(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get()).add(OccultismEntities.POSSESSED_SHULKER_TYPE.get()).replace(false);
    }

    private void addVanillaTags() {
        //Follow parity with https://minecraft.wiki/w/Entity_type_tag_(Java_Edition)
        this.tag(EntityTypeTags.AQUATIC)
                .add(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get());
        this.tag(EntityTypeTags.ARTHROPOD)
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .add(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get())
                .add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_SILVERFISH.get())
                .add(OccultismEntities.WILD_SILVERFISH.get())
                .add(OccultismEntities.WILD_SPIDER_TYPE.get());
        this.tag(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES)
                .add(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get())
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get());
        this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
                .add(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get());
        this.tag(EntityTypeTags.CAN_FLOAT_WHILE_RIDDEN)
                .add(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get());
        this.tag(EntityTypeTags.CAN_TURN_IN_BOATS)
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get());
        this.tag(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS)
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get());
        this.tag(EntityTypeTags.DEFLECTS_PROJECTILES)
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get());
        this.tag(EntityTypeTags.DISMOUNTS_UNDERWATER)
                .add(OccultismEntities.WILD_SPIDER_TYPE.get());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BLAZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_GHAST_TYPE.get())
                .add(OccultismEntities.IESNIUM_GOLEM_TYPE.get())
                .add(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .add(OccultismEntities.POSSESSED_PHANTOM_TYPE.get())
                .add(OccultismEntities.POSSESSED_SHULKER_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get());
        this.tag(EntityTypeTags.FOLLOWABLE_FRIENDLY_MOBS)
                .add(OccultismEntities.BAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEAVER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get())
                .add(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEER_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DEVIL_FAMILIAR_TYPE.get())
                .add(OccultismEntities.DRAGON_FAMILIAR_TYPE.get())
                .add(OccultismEntities.FAIRY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GOAT_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GREEDY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get())
                .add(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get())
                .add(OccultismEntities.MUMMY_FAMILIAR_TYPE.get())
                .add(OccultismEntities.OTHERWORLD_BIRD_TYPE.get())
                .add(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get())
                .add(OccultismEntities.POSSESSED_BEE_TYPE.get());
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                .add(OccultismEntities.WILD_STRAY_TYPE.get());
        this.tag(EntityTypeTags.FROG_FOOD)
                .add(OccultismEntities.WILD_SLIME_TYPE.get());
        this.tag(EntityTypeTags.ILLAGER)
                .add(OccultismEntities.POSSESSED_EVOKER_TYPE.get());
        this.tag(EntityTypeTags.IMMUNE_TO_INFESTED)
                .add(OccultismEntities.WILD_SILVERFISH_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get());
        this.tag(EntityTypeTags.IMMUNE_TO_OOZING)
                .add(OccultismEntities.WILD_SLIME_TYPE.get());
        this.tag(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE)
                .add(OccultismEntities.WILD_BOGGED_TYPE.get())
                .add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_BREEZE_TYPE.get())
                .add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get())
                .add(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_SKELETON_TYPE.get())
                .add(OccultismEntities.POSSESSED_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_SLIME_TYPE.get())
                .add(OccultismEntities.WILD_SPIDER_TYPE.get())
                .add(OccultismEntities.WILD_STRAY_TYPE.get())
                .add(OccultismEntities.WILD_ZOMBIE_TYPE.get());
        this.tag(EntityTypeTags.NON_CONTROLLING_RIDER)
                .add(OccultismEntities.WILD_SLIME_TYPE.get());
        this.tag(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH)
                .add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get())
                .add(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get());
        this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
                .add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_SILVERFISH.get())
                .add(OccultismEntities.WILD_SILVERFISH.get());
        this.tag(EntityTypeTags.SKELETONS)
                .add(OccultismEntities.WILD_BOGGED_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get())
                .add(OccultismEntities.POSSESSED_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_SKELETON_TYPE.get())
                .add(OccultismEntities.WILD_STRAY_TYPE.get())
                .add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get());
        this.tag(EntityTypeTags.UNDEAD)
                .add(OccultismEntities.POSSESSED_PHANTOM_TYPE.get());
        this.tag(EntityTypeTags.ZOMBIES)
                .add(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get())
                .add(OccultismEntities.WILD_HORDE_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_HUSK_TYPE.get())
                .add(OccultismEntities.WILD_ZOMBIE_TYPE.get())
                .add(OccultismEntities.POSSESSED_ZOMBIFIED_PIGLIN_TYPE.get());

    }
}
