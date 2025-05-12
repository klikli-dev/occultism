package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;

public class IesniumGolemEntry extends EntryProvider {

    public static final String ENTRY_ID = "iesnium_golem";


    public IesniumGolemEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("golem", () -> BookEntityPageModel.create()
                .withEntityId("occultism:iesnium_golem")
                .withEntityName(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Iesnium Golem");
        this.pageText("**Provides:** {0}",
                this.color("Immortal area protector", ChatFormatting.DARK_PURPLE));

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_iesnium_golem"))
        );
        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );

        this.pageTitle("Iesnium Golem");
        this.pageText("""
                An Iesnium Golem is a direct upgrade from a regular Iron Golem, this new version is much stronger and invulnerable. \\
                Only a player can dismiss them, by hitting while crouched, returning as a {0}.
                """,
                this.itemLink(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get()));

        this.page("familiar", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Not a Familiar");
        this.pageText("""
                        The Iesnium Golem Chapter is part of the Familiar Category of this book because of the similarities, however a golem is not exactly a familiar since it does not have an owner.
                        \\
                        \\
                        As such, they also cannot be stored in a {0} or in a {1}. You can, however, dispense with them as stated on the previous page.
                        """,
                this.itemLink(OccultismItems.FAMILIAR_RING.get()),
                this.itemLink(OccultismItems.SOUL_GEM_ITEM.get())
        );
    }

    @Override
    protected String entryName() {
        return "Iesnium Golem";
    }

    @Override
    protected String entryDescription() {
        return "Intangible golem";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/iesnium_golem.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
