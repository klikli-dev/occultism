package com.klikli_dev.occultism.datagen.book.spirits;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class EssenceDecayEntry extends EntryProvider {

    public static final String ENTRY_ID = "essence_decay";

    public EssenceDecayEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Essence Decay");
        this.pageText("""
                When residing in our plane of existence, spirits experience [#]({0})Essence Decay[#](), the slow rot of their "body". The more powerful the spirit, the slower the decay, but only the most powerful can stop it entirely. Once fully decayed they are returned to [#]({0})The Other Place[#]() and can only be re-summoned once fully recovered.
                """, "ad03fc");

        this.page("countermeasures", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Countermeasures");
        this.pageText("""
                The summoner can slow or even stop essence decay by binding the spirit into an object, or summoning it into a living being. Additionally the pentacle used can influence the effects of essence decay to a degree.
                """);

        this.page("affected_spirits", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Affected Spirits");
        this.pageText("""
                Only trader, time and weather spirits are affected by essence decay, by default. All others are immune and will not despawn. Modpacks may modify this behaviour.
                """);
    }

    @Override
    protected String entryName() {
        return "Essence Decay";
    }

    @Override
    protected String entryDescription() {
        return "Even the immortal are not immune to time.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.SQUARE_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.ROTTEN_FLESH);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
