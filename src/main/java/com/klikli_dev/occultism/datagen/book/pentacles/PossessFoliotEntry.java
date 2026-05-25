package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;

public class PossessFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "possess_foliot";


    public PossessFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Hedyrins Lure");
        this.pageText("""
                        **Purpose:** {0} Possession\\
                        \\
                        **Hedyrins Lure** attracts {1} and forces them to possess a nearby Creature. This pentacle can
                         perform basic possessions, bringing back only low-power Spirits and Possessed Creatures.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE),
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - {0}
                - {1}
                - {2}
                - {3}
                - {4}
                - {5}
                - {6}
                - {7}
                - {8}
                - {9}
                - {10}
                """, this.entryLink("Possessed Endermite", "possession_rituals", "possess_endermite"), this.entryLink("Possessed Skeleton", "possession_rituals", "possess_skeleton"), this.entryLink("Possessed Witch", "possession_rituals", "possess_witch"), this.entryLink("Possessed Phantom", "possession_rituals", "possess_phantom"), this.entryLink("Unbound Parrot", "possession_rituals", "possess_unbound_parrot"), this.entryLink("Random Animal (Common, Water, Small)", "possession_rituals", "possess_random_animal"), this.entryLink("Parrot Familiar", "familiar_rituals", "familiar_parrot"), this.entryLink("Greedy Familiar", "familiar_rituals", "familiar_greedy"), this.entryLink("Deer Familiar", "familiar_rituals", "familiar_deer"), this.entryLink("Blacksmith Familiar", "familiar_rituals", "familiar_blacksmith"), this.entryLink("Beaver Familiar", "familiar_rituals", "familiar_beaver"));

    }

    @Override
    protected String entryName() {
        return "Hedyrin's Lure";
    }

    @Override
    protected String entryDescription() {
        return "Foliot Possession";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_POSSESS.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
