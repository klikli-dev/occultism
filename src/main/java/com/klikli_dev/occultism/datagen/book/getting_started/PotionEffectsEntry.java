package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class PotionEffectsEntry extends EntryProvider {

    public static final String ENTRY_ID = "effects";

    public PotionEffectsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("third_eye", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/third_eye.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Third Eye");
        this.pageText("""
                        Allows you to see and interact with basic materials from the Otherworld.
                        """
        );

        //Familiar Effects
        this.page("beaver_harvest", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/beaver_harvest.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Beaver Harvest");
        this.pageText("""
                        Increases log breaking speed.
                        """
        );

        this.page("step_height", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/step_height.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Step Height");
        this.pageText("""
                        Each level slightly increases step height. Apply {0} when sneaking.
                        """,
                    this.entryLink("Step Blocked", "getting_started", "effects@step_blocked")
        );

        this.page("dragon_greed", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/dragon_greed.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Dragon's Greed");
        this.pageText("""
                        Increases the amount of experience dropped.
                        """
        );

        this.page("mummy_dodge", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/mummy_dodge.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Dodge");
        this.pageText("""
                        Chance to dodge attacks.
                        """
        );

        this.page("double_jump", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/double_jump.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Multi Jump");
        this.pageText("""
                        Allows you to perform extra jumps in mid-air.
                        """
        );

        //Upgraded Familiars Effects
        this.page("bat_lifesteal", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/bat_lifesteal.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Life-Steal");
        this.pageText("""
                        Restores health when defeating enemies.
                        """
        );

        this.page("greedy_harvest", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/greedy_harvest.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Greedy Harvest");
        this.pageText("""
                        Blocks that can be mined with a pickaxe are broken at a fixed speed.
                        """
        );

        this.page("pumpkin_head", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/pumpkin_head.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Pumpkin Head");
        this.pageText("""
                        Endermen won't become hostile when you look them in the eyes.
                        """
        );

        this.page("fire_wing", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/fire_wing.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Fire Wings");
        this.pageText("""
                        Enables elytra glide.
                        """
        );

        //Iesnium Familiar Effects
        this.page("bat_flight", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/bat_flight.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Bat-Flight");
        this.pageText("""
                        Enables creative flight.
                        """
        );

        this.page("fairy_bless", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/fairy_bless.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Fairy Blessing");
        this.pageText("""
                        Converts part of your damage into magic damage and increases the effectiveness of healing received.
                        """
        );

        this.page("nether_emperor", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/nether_emperor.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Emperor of the Nether");
        this.pageText("""
                        Makes undead and nether creatures friendly toward you.
                        """
        );

        this.page("herald_aberrations", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/herald_aberrations.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Herald of Aberrations");
        this.pageText("""
                        Makes warden, enderman, shulker and vex friendly toward you.
                        """
        );

        this.page("aquatic_lord", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/aquatic_lord.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Lord of the Aquatic Depths");
        this.pageText("""
                        Makes aquatic creatures friendly toward you.
                        """
        );

        this.page("forest_whisperer", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/forest_whisperer.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Whisperer of the Forest");
        this.pageText("""
                        Makes illager, arthropod and creeper friendly toward you.
                        """
        );

        //Helpers Effects
        this.page("step_blocked", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/step_blocked.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Step Blocked");
        this.pageText("""
                        Sneaking prevents you from falling off blocks, even with increased step height.
                        """
        );

        this.page("undying_cooldown", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/mob_effect/undying_cooldown.png"))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Occult Undying Cooldown");
        this.pageText("""
                        Cooldown between Occultism revivals.
                        """
        );
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.BREWING_STAND);
    }

    @Override
    protected String entryName() {
        return "Occultism Effects";
    }

    @Override
    protected String entryDescription() {
        return "Learn about occultism's brewery";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
