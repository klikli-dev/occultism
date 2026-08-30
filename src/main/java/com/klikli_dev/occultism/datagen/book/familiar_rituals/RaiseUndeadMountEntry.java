package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class RaiseUndeadMountEntry extends EntryProvider {

    public static final String ENTRY_ID = "raise_undead_mount";

    public RaiseUndeadMountEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("""
                        Occultists focused on necromancy who pioneered the study of resurrection rituals
                         began with “incomplete” rituals, bringing undead versions of mounts back to life.
                        """
        );

        this.page("rituals", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Rituals");
        this.pageText("""
                        The following pages present different rituals, one for each type of undead mount.
                        \\
                        \\
                        **Note:** Unfortunately, the sacrifice’s attributes do not determine the power of its resurrected version.
                        """
        );

        this.page("zombie_horse", () -> BookEntityPageModel.create()
                .withEntityId("minecraft:zombie_horse")
                .withText(this.context().pageText())
                .withScale(0.6f)
                .withOffset(-0.4f));
        this.pageText("Be careful with sunlight.");

        this.page("ritual_zombie_horse", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/resurrect_zombie_horse")));

        this.page("skeleton_horse", () -> BookEntityPageModel.create()
                .withEntityId("minecraft:skeleton_horse")
                .withText(this.context().pageText())
                .withScale(0.6f)
                .withOffset(-0.4f));
        this.pageText("No traps.");

        this.page("ritual_skeleton_horse", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/resurrect_skeleton_horse")));

        this.page("camel_husk", () -> BookEntityPageModel.create()
                .withEntityId("minecraft:camel_husk")
                .withText(this.context().pageText())
                .withScale(0.8f)
                .withOffset(-0.5f));
        this.pageText("The sandstorms sent news.");

        this.page("ritual_camel_husk", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/resurrect_camel_husk")));

        this.page("zombie_nautilus", () -> BookEntityPageModel.create()
                .withEntityId("minecraft:zombie_nautilus")
                .withText(this.context().pageText())
                .withScale(0.4f)
                .withOffset(-0.3f));
        this.pageText("Keep it hydrated.");

        this.page("ritual_zombie_nautilus", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/resurrect_zombie_nautilus")));
    }

    @Override
    protected String entryName() {
        return "Raise Undead Mounts";
    }

    @Override
    protected String entryDescription() {
        return "Chaotic evil";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/zombie_horse.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
