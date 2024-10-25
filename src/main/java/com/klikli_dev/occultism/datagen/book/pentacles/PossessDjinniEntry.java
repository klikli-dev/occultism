package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
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
                - [Possessed Enderman](entry://possession_rituals/possess_enderman)
                - [Possessed Ghast](entry://possession_rituals/possess_ghast)
                - [Possessed Weak Shulker](entry://possession_rituals/possess_weak_shulker)
                - [Possessed Warden](entry://possession_rituals/possess_warden)
                - [Possessed Bee](entry://possession_rituals/possess_bee)
                - [Unbound Drikwing](entry://possession_rituals/possess_unbound_otherworld_bird)
                - [Drikwing Familiar](entry://familiar_rituals/familiar_otherworld_bird)
                - [Bat Familiar](entry://familiar_rituals/familiar_bat)
                - [Cthulhu Familiar](entry://familiar_rituals/familiar_cthulhu)
                - [Devil Familiar](entry://familiar_rituals/familiar_devil)
                - [Dragon Familiar](entry://familiar_rituals/familiar_dragon)
                """
        );

        this.page("uses2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Headless Ratman Familiar](entry://familiar_rituals/familiar_headless)
                - [Beholder Familiar](entry://familiar_rituals/familiar_beholder)
                - [Fairy Familiar](entry://familiar_rituals/familiar_fairy)
                - [Chimera Familiar](entry://familiar_rituals/familiar_chimera)
                - [Mummy Familiar](entry://familiar_rituals/familiar_mummy)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Ihagan's Enthrallment";
    }

    @Override
    protected String entryDescription() {
        return "Possessing Djinni";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
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
