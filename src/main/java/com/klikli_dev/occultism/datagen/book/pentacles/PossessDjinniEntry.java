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

public class PossessDjinniEntry extends EntryProvider {

    public static final String ENTRY_ID = "possess_djinni";


    public PossessDjinniEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Ihagans Enthrallment");
        this.pageText("""
                        **Purpose:** {0} Possession\\
                        \\
                        **Ihagans Enthrallment** forces {1} to possess a nearby Creature. This pentacle is very versatile
                          for imprisonment, allowing you to summon more powerful Spirits and Creatures.
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
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
                """, this.entryLink("Possessed Enderman", "possession_rituals", "possess_enderman"), this.entryLink("Possessed Ghast", "possession_rituals", "possess_ghast"), this.entryLink("Possessed Weak Shulker", "possession_rituals", "possess_weak_shulker"), this.entryLink("Possessed Bee", "possession_rituals", "possess_bee"), this.entryLink("Possessed Blaze", "possession_rituals", "possess_blaze"), this.entryLink("Random Animal (Rideable, Special, Villager)", "possession_rituals", "possess_random_animal"), this.entryLink("Unbound Drikwing", "possession_rituals", "possess_unbound_otherworld_bird"), this.entryLink("Drikwing Familiar", "familiar_rituals", "familiar_otherworld_bird"));

        this.page("uses2", () -> BookTextPageModel.create()
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
                """, this.entryLink("Bat Familiar", "familiar_rituals", "familiar_bat"), this.entryLink("Cthulhu Familiar", "familiar_rituals", "familiar_cthulhu"), this.entryLink("Devil Familiar", "familiar_rituals", "familiar_devil"), this.entryLink("Dragon Familiar", "familiar_rituals", "familiar_dragon"), this.entryLink("Headless Ratman Familiar", "familiar_rituals", "familiar_headless"), this.entryLink("Beholder Familiar", "familiar_rituals", "familiar_beholder"), this.entryLink("Fairy Familiar", "familiar_rituals", "familiar_fairy"), this.entryLink("Chimera Familiar", "familiar_rituals", "familiar_chimera"), this.entryLink("Mummy Familiar", "familiar_rituals", "familiar_mummy"));

    }

    @Override
    protected String entryName() {
        return "Ihagan's Enthrallment";
    }

    @Override
    protected String entryDescription() {
        return "Djinni Possession";
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
