package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypeIds;
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
                .add(this.key(OccultismEntities.FOLIOT_TYPE.get()))
                .add(this.key(OccultismEntities.DJINNI_TYPE.get()))
                .add(this.key(OccultismEntities.AFRIT_TYPE.get()))
                .add(this.key(OccultismEntities.MARID_TYPE.get()))
                .add(this.key(OccultismEntities.GREEDY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DEER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DEVIL_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GOAT_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.MUMMY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BEAVER_FAMILIAR_TYPE.get()));
    }

    private void addOccultismTags() {
        this.tag(Entities.AFRIT_ALLIES)
                .add(EntityTypeIds.BLAZE)
                ;
        this.tag(Entities.CUBEMOB)
                .add(EntityTypeIds.SLIME)
                .add(EntityTypeIds.MAGMA_CUBE)
                ;
        this.tag(Entities.CREEPER)
                .add(EntityTypeIds.CREEPER)
                .add(this.key(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get()))
                ;
        this.tag(Entities.FLYING_PASSIVE)
                .add(EntityTypeIds.BAT)
                .add(EntityTypeIds.PARROT)
                .add(EntityTypeIds.BEE)
                .add(EntityTypeIds.ALLAY)
                .add(this.key(OccultismEntities.POSSESSED_BEE_TYPE.get()))
                .add(this.key(OccultismEntities.BAT_FAMILIAR_TYPE.get()))
                ;
        this.tag(Entities.HEALED_BY_OTHERWORLD_FRUIT)
                //Spirit
                .add(this.key(OccultismEntities.FOLIOT_TYPE.get()))
                .add(this.key(OccultismEntities.DJINNI_TYPE.get()))
                .add(this.key(OccultismEntities.AFRIT_TYPE.get()))
                .add(this.key(OccultismEntities.AFRIT_UNBOUND_TYPE.get()))
                .add(this.key(OccultismEntities.MARID_TYPE.get()))
                .add(this.key(OccultismEntities.MARID_UNBOUND_TYPE.get()))
                .add(this.key(OccultismEntities.DEMONIC_WIFE.get()))
                .add(this.key(OccultismEntities.DEMONIC_HUSBAND.get()))
                .add(this.key(OccultismEntities.WONDERING_TRADER_TYPE.get()))
                //Possessed
                .add(this.key(OccultismEntities.GOAT_OF_MERCY_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BEE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BLAZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_GHAST_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_HOGLIN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_PHANTOM_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_SHULKER_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WARDEN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WITCH_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ZOMBIFIED_PIGLIN_TYPE.get()))
                //Horde
                .add(this.key(OccultismEntities.POSSESSED_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_EVOKER_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_BOGGED_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SILVERFISH_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SLIME_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_STRAY_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_ZOMBIE_TYPE.get()))
                //Familiar
                .add(this.key(OccultismEntities.BAT_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BEAVER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DEER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DEVIL_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DRAGON_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.FAIRY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GOAT_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GREEDY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.MUMMY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.OTHERWORLD_BIRD_TYPE.get()))
                .add(this.key(OccultismEntities.SHUB_NIGGURATH_SPAWN_TYPE.get()))
                .add(this.key(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get()))
                ;

        this.tag(Entities.HUMANS)
                .addTag(Entities.VILLAGERS)
                .add(EntityTypeIds.PLAYER)
                ;

        this.tag(Entities.WILD_HUNT)
                .add(this.key(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get()))
                ;

        this.tag(Entities.WILD_DESERT)
                .add(this.key(OccultismEntities.WILD_HORDE_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get()))
                ;

        this.tag(Entities.WILD_TRIAL)
                .add(this.key(OccultismEntities.WILD_BOGGED_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SILVERFISH_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SLIME_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_STRAY_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_ZOMBIE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()))
                ;

        this.tag(Entities.RANDOM_ANIMALS_COMMON)
                .add(EntityTypeIds.CHICKEN)
                .add(EntityTypeIds.COW)
                .add(EntityTypeIds.PIG)
                .add(EntityTypeIds.SHEEP)
                .add(EntityTypeIds.SQUID)
                .add(EntityTypeIds.WOLF);

        this.tag(Entities.RANDOM_ANIMALS_WATER)
                .add(EntityTypeIds.AXOLOTL)
                .add(EntityTypeIds.FROG)
                .add(EntityTypeIds.DOLPHIN)
                .add(EntityTypeIds.SALMON)
                .add(EntityTypeIds.COD)
                .add(EntityTypeIds.TROPICAL_FISH)
                .add(EntityTypeIds.PUFFERFISH)
                .add(EntityTypeIds.SQUID)
                .add(EntityTypeIds.SNOW_GOLEM)
                .add(EntityTypeIds.GLOW_SQUID)
                .add(EntityTypeIds.TADPOLE)
                .add(EntityTypeIds.TURTLE)
                .add(EntityTypeIds.NAUTILUS)
                .add(EntityTypeIds.ZOMBIE_NAUTILUS);

        this.tag(Entities.RANDOM_ANIMALS_SMALL)
                .add(EntityTypeIds.ALLAY)
                .add(EntityTypeIds.BAT)
                .add(EntityTypeIds.BEE)
                .add(EntityTypeIds.CAT)
                .add(EntityTypeIds.FOX)
                .add(EntityTypeIds.OCELOT)
                .add(EntityTypeIds.PARROT)
                .add(EntityTypeIds.RABBIT);

        this.tag(Entities.RANDOM_ANIMALS_SPECIAL)
                .add(EntityTypeIds.ARMADILLO)
                .add(EntityTypeIds.IRON_GOLEM)
                .add(EntityTypeIds.MOOSHROOM)
                .add(EntityTypeIds.PANDA)
                .add(EntityTypeIds.POLAR_BEAR)
                .add(EntityTypeIds.GOAT)
                .add(EntityTypeIds.SNIFFER)
                .add(EntityTypeIds.COPPER_GOLEM);

        this.tag(Entities.RANDOM_ANIMALS_RIDEABLE)
                .add(EntityTypeIds.PIG)
                .add(EntityTypeIds.CAMEL)
                .add(EntityTypeIds.DONKEY)
                .add(EntityTypeIds.HORSE)
                .add(EntityTypeIds.SKELETON_HORSE)
                .add(EntityTypeIds.ZOMBIE_HORSE)
                .add(EntityTypeIds.LLAMA)
                .add(EntityTypeIds.TRADER_LLAMA)
                .add(EntityTypeIds.MULE)
                .add(EntityTypeIds.STRIDER)
                .add(EntityTypeIds.HAPPY_GHAST)
                .add(EntityTypeIds.NAUTILUS)
                .add(EntityTypeIds.ZOMBIE_NAUTILUS)
                .add(EntityTypeIds.CAMEL_HUSK);
    }

    private void addCommonTags() {
        this.tag(Entities.FRAGILE_SOUL_GEM_DENY_LIST).add(this.key(OccultismEntities.IESNIUM_GOLEM_TYPE.get())).addOptionalTag(EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(EntityTypes.BOSSES);
        this.tag(Entities.SOUL_GEM_DENY_LIST).add(this.key(OccultismEntities.IESNIUM_GOLEM_TYPE.get())).addOptionalTag(EntityTypes.CAPTURING_NOT_SUPPORTED).addTag(EntityTypes.BOSSES);

        this.tag(Entities.SNOW_GOLEM).add(EntityTypeIds.SNOW_GOLEM);
        this.tag(Entities.COPPER_GOLEM).add(EntityTypeIds.COPPER_GOLEM);
        this.tag(Entities.IRON_GOLEM).add(EntityTypeIds.IRON_GOLEM);
        this.tag(Entities.AXOLOTL).add(EntityTypeIds.AXOLOTL);
        this.tag(Entities.BATS).add(EntityTypeIds.BAT).add(this.key(OccultismEntities.BAT_FAMILIAR_TYPE.get()));
        this.tag(Entities.BEES).add(EntityTypeIds.BEE).add(this.key(OccultismEntities.POSSESSED_BEE_TYPE.get()));
        this.tag(Entities.CHICKEN).add(EntityTypeIds.CHICKEN);
        this.tag(Entities.COWS).add(EntityTypeIds.COW).add(EntityTypeIds.MOOSHROOM);
        this.tag(Entities.DONKEYS).add(EntityTypeIds.DONKEY);
        this.tag(Entities.FISH).add(EntityTypeIds.COD).add(EntityTypeIds.SALMON).add(EntityTypeIds.TROPICAL_FISH).add(EntityTypeIds.PUFFERFISH);
        this.tag(Entities.GOATS).add(EntityTypeIds.GOAT).add(this.key(OccultismEntities.GOAT_OF_MERCY_TYPE.get())).add(this.key(OccultismEntities.GOAT_FAMILIAR_TYPE.get()));
        this.tag(Entities.HOGLINS).add(EntityTypeIds.HOGLIN).add(this.key(OccultismEntities.POSSESSED_HOGLIN_TYPE.get()));
        this.tag(Entities.HORSES).add(EntityTypeIds.HORSE);
        this.tag(Entities.LLAMAS).add(EntityTypeIds.LLAMA).add(EntityTypeIds.TRADER_LLAMA);
        this.tag(Entities.MULES).add(EntityTypeIds.MULE);
        this.tag(Entities.PANDAS).add(EntityTypeIds.PANDA);
        this.tag(Entities.PARROTS).add(EntityTypeIds.PARROT);
        this.tag(Entities.PIGS).add(EntityTypeIds.PIG);
        this.tag(Entities.SHEEP).add(EntityTypeIds.SHEEP);
        this.tag(Entities.SPIDERS).add(EntityTypeIds.SPIDER).add(EntityTypeIds.CAVE_SPIDER);
        this.tag(Entities.SQUID).add(EntityTypeIds.SQUID).add(EntityTypeIds.GLOW_SQUID);
        this.tag(Entities.VILLAGERS).add(EntityTypeIds.VILLAGER).add(EntityTypeIds.WANDERING_TRADER).add(this.key(OccultismEntities.WONDERING_TRADER_TYPE.get()));
        this.tag(Entities.CAMEL).add(EntityTypeIds.CAMEL);
        this.tag(Entities.DOLPHIN).add(EntityTypeIds.DOLPHIN);
        this.tag(Entities.WOLFS).add(EntityTypeIds.WOLF);
        this.tag(Entities.OCELOT).add(EntityTypeIds.OCELOT);
        this.tag(Entities.CATS).add(EntityTypeIds.CAT).add(EntityTypeIds.OCELOT);
        this.tag(Entities.VEX).add(EntityTypeIds.VEX);
        this.tag(Entities.TADPOLES).add(EntityTypeIds.TADPOLE);
        this.tag(Entities.ALLAY).add(EntityTypeIds.ALLAY);
        this.tag(Entities.ARMADILLOS).add(EntityTypeIds.ARMADILLO);
        this.tag(Entities.WARDEN).add(EntityTypeIds.WARDEN).add(this.key(OccultismEntities.POSSESSED_WARDEN_TYPE.get()));
        this.tag(Entities.RAVAGER).add(EntityTypeIds.RAVAGER);
        this.tag(Entities.ENDERMEN).add(EntityTypeIds.ENDERMAN).add(this.key(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get()));
        this.tag(Entities.SHULKER).add(EntityTypeIds.SHULKER).add(this.key(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get())).add(this.key(OccultismEntities.POSSESSED_SHULKER_TYPE.get()));
        this.tag(Entities.WITCH).add(EntityTypeIds.WITCH).add(this.key(OccultismEntities.POSSESSED_WITCH_TYPE.get()));
        this.tag(Entities.EVOKER).add(EntityTypeIds.EVOKER).add(this.key(OccultismEntities.POSSESSED_EVOKER_TYPE.get()));

    }

    private void addVanillaTags() {
        //Follow parity with https://minecraft.wiki/w/Entity_type_tag_(Java_Edition)
        this.tag(EntityTypeTags.AQUATIC)
                .add(this.key(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()));
        this.tag(EntityTypeTags.ARTHROPOD)
                .add(this.key(OccultismEntities.POSSESSED_BEE_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_SILVERFISH.get()))
                .add(this.key(OccultismEntities.WILD_SILVERFISH.get()))
                .add(this.key(OccultismEntities.WILD_SPIDER_TYPE.get()));
        this.tag(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES)
                .add(this.key(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get()));
        this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
                .add(this.key(OccultismEntities.BEAVER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get()));
        this.tag(EntityTypeTags.CAN_FLOAT_WHILE_RIDDEN)
                .add(this.key(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get()));
        this.tag(EntityTypeTags.CAN_TURN_IN_BOATS)
                .add(this.key(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()));
        this.tag(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS)
                .add(this.key(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()));
        this.tag(EntityTypeTags.DEFLECTS_PROJECTILES)
                .add(this.key(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()));
        this.tag(EntityTypeTags.DISMOUNTS_UNDERWATER)
                .add(this.key(OccultismEntities.WILD_SPIDER_TYPE.get()));
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(this.key(OccultismEntities.BAT_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BEE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BLAZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_GHAST_TYPE.get()))
                .add(this.key(OccultismEntities.IESNIUM_GOLEM_TYPE.get()))
                .add(this.key(OccultismEntities.OTHERWORLD_BIRD_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_PHANTOM_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_SHULKER_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get()));
        this.tag(EntityTypeTags.FOLLOWABLE_FRIENDLY_MOBS)
                .add(this.key(OccultismEntities.BAT_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BEAVER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DEER_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DEVIL_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.DRAGON_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.FAIRY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GOAT_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GREEDY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.MUMMY_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.OTHERWORLD_BIRD_TYPE.get()))
                .add(this.key(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BEE_TYPE.get()));
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                .add(this.key(OccultismEntities.WILD_STRAY_TYPE.get()));
        this.tag(EntityTypeTags.FROG_FOOD)
                .add(this.key(OccultismEntities.WILD_SLIME_TYPE.get()));
        this.tag(EntityTypeTags.ILLAGER)
                .add(this.key(OccultismEntities.POSSESSED_EVOKER_TYPE.get()));
        this.tag(EntityTypeTags.IMMUNE_TO_INFESTED)
                .add(this.key(OccultismEntities.WILD_SILVERFISH_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get()));
        this.tag(EntityTypeTags.IMMUNE_TO_OOZING)
                .add(this.key(OccultismEntities.WILD_SLIME_TYPE.get()));
        this.tag(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE)
                .add(this.key(OccultismEntities.WILD_BOGGED_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SLIME_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SPIDER_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_STRAY_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_ZOMBIE_TYPE.get()));
        this.tag(EntityTypeTags.NON_CONTROLLING_RIDER)
                .add(this.key(OccultismEntities.WILD_SLIME_TYPE.get()));
        this.tag(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH)
                .add(this.key(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get()));
        this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
                .add(this.key(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_SILVERFISH.get()))
                .add(this.key(OccultismEntities.WILD_SILVERFISH.get()));
        this.tag(EntityTypeTags.SKELETONS)
                .add(this.key(OccultismEntities.WILD_BOGGED_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_SKELETON_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_STRAY_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get()));
        this.tag(EntityTypeTags.UNDEAD)
                .add(this.key(OccultismEntities.POSSESSED_PHANTOM_TYPE.get()));
        this.tag(EntityTypeTags.ZOMBIES)
                .add(this.key(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HORDE_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_HUSK_TYPE.get()))
                .add(this.key(OccultismEntities.WILD_ZOMBIE_TYPE.get()))
                .add(this.key(OccultismEntities.POSSESSED_ZOMBIFIED_PIGLIN_TYPE.get()));
        this.tag(EntityTypeTags.BOAT)
                .add(this.key(OccultismEntities.OTHERPLANKS_BOAT_TYPE.get()));
        this.tag(EntityTypes.BOATS)
                .add(this.key(OccultismEntities.OTHERPLANKS_BOAT_TYPE.get()))
                .add(this.key(OccultismEntities.OTHERPLANKS_BOAT_CHEST_TYPE.get()));
    }

    private ResourceKey<EntityType<?>> key(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).orElseThrow();
    }
}
