package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;

public class PossessUnboundParrotEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_unbound_parrot";

    public PossessUnboundParrotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "minecraft:parrot");
        this.pageText("""
                **Provides**: A tameable Parrot
                """);

        this.ritualPage("ritual", "ritual/possess_unbound_parrot");

        this.textPage("description");
        this.pageText("""
                In this ritual a [#]({0})Foliot[#]() is summoned **as an untamed spirit**.
                \\
                \\
                The slaughter of a [#]({0})Chicken[#]() and the offering of dyes are intended to entice the Foliot to take the shape of a parrot. As [#]({0})Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions ...
                """, COLOR_PURPLE);

        this.textPageNoTitle("description2");
        this.pageText("""
                *This means, if a [#]({0})Chicken[#]() is spawned, that's not a bug, just bad luck!*
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Unbound Parrot";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/parrot.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
