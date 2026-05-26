package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;

public class PossessUnboundOtherworldBirdEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_unbound_otherworld_bird";

    public PossessUnboundOtherworldBirdEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:otherworld_bird");
        this.pageText("""
                **Provides**: A tameable Drikwing
                """);

        this.ritualPage("ritual", "ritual/possess_unbound_otherworld_bird");

        this.textPageNoTitle("description");
        this.pageText("""
                See [Drikwing Familiar](entry://familiar_rituals/familiar_otherworld_bird) for more information.
                """);
    }

    @Override
    protected String entryName() {
        return "Unbound Drikwing";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/otherworld_bird.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
