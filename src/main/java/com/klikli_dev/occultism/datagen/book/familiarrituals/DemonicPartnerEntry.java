package com.klikli_dev.occultism.datagen.book.familiarrituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class DemonicPartnerEntry extends EntryProvider {

    public static final String ENTRY_ID = "demonic_partner";


    public DemonicPartnerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {


        this.page("description", () -> BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build());
        this.pageTitle("Demonic Partner");
        this.pageText("""
                Magicians practicing the occult are a diverse crowd, coming from all creeds and all corners of the world. However one thing unites them all - they are as lonely as any human without a partner.
                \\
                \\
                Of course being a magician, there is a way to solve this problem once and for all.
                """);

        this.page("description2", () -> BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build());
        this.pageTitle("Demonic Partner");
        this.pageText("""
                As beings of immense powers Demons can have it all ... except love.\\
                No demon will freely admit this, but at times they are impressed by the power and strength that some mortal occultists acquire. And in such cases, the impossible can happen - the demon can fall in love with a mortal.
                """);

        this.page("description3", () -> BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build());
        this.pageTitle("Demonic Partner");
        this.pageText("""
                Demons, of course, deal in contracts, and what better contract than a marriage contract?
                """);

        this.page("about", () -> BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build());
        this.pageTitle("Demonic Partner");
        this.pageText("""
                The Demonic Partner - a Husband or Wife - can fight for you and help with household chores.
                \\
                \\
                Right-Click with any cookable food and they will use their magic to cook it.
                \\
                \\
                Right-Click with a potion to get the effect for a significantly longer time.
                """);

        this.page("wife", () -> BookEntityPageModel.builder()
                .withEntityId("occultism:demonic_wife")
                .withEntityName(this.context().pageTitle())
                .build()
        );
        this.pageTitle("Demonic Wife");


        this.page("ritual1", () -> BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_demonic_wife"))
                .build()
        );

        this.page("husband", () -> BookEntityPageModel.builder()
                .withEntityId("occultism:demonic_husband")
                .withEntityName(this.context().pageTitle())
                .build()
        );
        this.pageTitle("Demonic Husband");

        this.page("ritual2", () -> BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_demonic_husband"))
                .build()
        );
    }

    @Override
    protected String entryName() {
        return "Demonic Partner";
    }

    @Override
    protected String entryDescription() {
        return "A demonic Household?";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/demonic_partner.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
