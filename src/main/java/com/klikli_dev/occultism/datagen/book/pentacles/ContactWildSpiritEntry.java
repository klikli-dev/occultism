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

public class ContactWildSpiritEntry extends EntryProvider {

    public static final String ENTRY_ID = "contact_wild_spirit";


    public ContactWildSpiritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Osorins Unbound Calling");
        this.pageText("""
                **Purpose:** Contact {0}\\
                \\
                **Osorins Unbound Calling** has a unique form, mixing different aspects obtained in each chalk
                 and none of the common stabilizing paraphernalia. Therefore, the pentacle offers no protection
                  to the occultist, but acts as an irresistible contact with the {1}.
                """,
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE),
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Wither Skeleton Skull](entry://possession_rituals/wither_skull)
                - [Horde Husk](entry://possession_rituals/horde_husk)
                - [Horde Drowned](entry://possession_rituals/horde_drowned)
                - [Horde Creeper](entry://possession_rituals/horde_creeper)
                - [Horde Silverfish](entry://possession_rituals/horde_silverfish)
                - [Trial Key](entry://possession_rituals/possess_weak_breeze)
                - [Ominous Trial Key](entry://possession_rituals/possess_breeze)
                - [Heavy Core](entry://possession_rituals/possess_strong_breeze)
                - [Wild Illager Invasion](entry://possession_rituals/horde_illager)
                - [Group of Random Animal](entry://possession_rituals/wild_random_animal)
                """
        );
        this.page("uses2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Wild Armor Trim Smithing Template](entry://crafting_rituals/craft_wild_trim)
                - [Budding Amethyst](entry://crafting_rituals/craft_budding_amethyst)
                - [Reinforced Deepslate](entry://crafting_rituals/craft_reinforced_deepslate)
                - [Bee Nest](entry://crafting_rituals/bee_nest)
                - [Bell](entry://crafting_rituals/bell)
                """
        );
    }

    @Override
    protected String entryName() {
        return "Osorin's Unbound Calling";
    }

    @Override
    protected String entryDescription() {
        return "Contact Wild Spirits";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_MISC.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
