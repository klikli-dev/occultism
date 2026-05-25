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
                """, this.entryLink("Wither Skeleton Skull", "possession_rituals", "wither_skull"), this.entryLink("Horde Husk", "possession_rituals", "horde_desert"), this.entryLink("Horde Drowned", "possession_rituals", "horde_drowned"), this.entryLink("Horde Creeper", "possession_rituals", "horde_creeper"), this.entryLink("Horde Silverfish", "possession_rituals", "horde_silverfish"), this.entryLink("Trial Key", "possession_rituals", "possess_weak_breeze"), this.entryLink("Ominous Trial Key", "possession_rituals", "possess_breeze"), this.entryLink("Heavy Core", "possession_rituals", "possess_strong_breeze"), this.entryLink("Wild Illager Invasion", "possession_rituals", "horde_illager"), this.entryLink("Group of Random Animal", "possession_rituals", "wild_random_animal"));
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
                """, this.entryLink("Wild Armor Trim Smithing Template", "crafting_rituals", "craft_wild_trim"), this.entryLink("Budding Amethyst", "crafting_rituals", "craft_budding_amethyst"), this.entryLink("Reinforced Deepslate", "crafting_rituals", "craft_reinforced_deepslate"), this.entryLink("Bee Nest", "crafting_rituals", "bee_nest"), this.entryLink("Bell", "crafting_rituals", "bell"), this.entryLink("Horse Armors", "crafting_rituals", "animal_armor"));
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
    protected GuiSprite entryBackground() {
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
