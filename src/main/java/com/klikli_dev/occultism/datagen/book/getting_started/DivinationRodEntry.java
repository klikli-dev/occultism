package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class DivinationRodEntry extends EntryProvider {

    public static final String ENTRY_ID = "divination_rod";

    public DivinationRodEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Divination");
        this.pageText("To make it easier to get started, the materials obtained by divination now also have crafting recipes.\n**If you want the full experience, skip the following recipe page and move on to the\n[divination instructions](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_rod).**\n");

        this.page("otherstone_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone")));

        this.page("otherworld_sapling_natural_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(this.context().pageText()));
        this.pageText("**Beware**: the tree growing from the sapling will look like a normal oak tree.\nYou need to activate the [Third Eye](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)\nto harvest the Otherworld Logs and Leaves.\n");

        this.page("divination_rod", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DIVINATION_ROD.get()))
                .withText(this.context().pageText()));
        this.pageText("Otherworld materials play an important role in interacting with spirits.\nAs they are rare and not visible to the naked eye, finding them requires special tools.\nThe divination rod allows to find Otherworld materials based on their similarities to materials common to our world.\n");

        this.page("spirit_attuned_gem_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem")));

        this.page("divination_rod_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/divination_rod")));

        this.page("about_divination_rod", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("The divination rod uses a spirit attuned gem attached to a wooden rod.\nThe gem resonates with the chosen material, and this movement is amplified by the wooden rod,\nallowing to detect nearby Otherworld materials.    \n    \n    \nThe rod works by detecting resonance between real world and Otherworld materials.\nAttuned the rod to a real world material, and it will find the corresponding Otherworld block.\n");

        this.page("how_to_use", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Use of the Rod");
        this.pageText("[#](ad03fc)Shift-right-click[#]() a block to attune the rod to the corresponding Otherworld block.\n- [](item://minecraft:andesite): [](item://occultism:otherstone)\n- [](item://minecraft:oak_wood):  [](item://occultism:otherworld_log)\n- [](item://minecraft:oak_leaves): [](item://occultism:otherworld_leaves)\n- [](item://minecraft:netherrack): [](item://occultism:iesnium_ore)\n\nThen [#](ad03fc)right-click[#]() and hold until the rod animation finishes.");

        this.page("how_to_use2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("After the animation finishes, a particle will fly toward the closest found block.\nAdditionally you can watch the crystals for hints: a white crystal indicates no target blocks found,\na fully purple block means the found block is nearby. Mixes between white and purple show\nthat the target is rather far away.");

        this.page("how_to_use3", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("[#](ad03fc)Right-clicking[#]() without holding after a successful search will send another particle toward the last found target block.\n\\\n\\\nIf you prefer the old target highlight, open occultism-client.toml and set useAlternativeDivinationRodRenderer = true.\n");

        this.page("divination_rod_screenshots", () -> BookImagePageModel.create()
                .withImages(
                        this.modLoc("textures/gui/book/rod_far.png"),
                        this.modLoc("textures/gui/book/rod_mid.png"),
                        this.modLoc("textures/gui/book/rod_near.png")
                )
                .withText(this.context().pageText()));
        this.pageText("White means nothing was found.\nThe more purple you see, the closer you are.\n");

        this.page("troubleshooting", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Troubleshooting");
        this.pageText("If the rod does not create visible particles for you, you can try to:\n- Set particles to all or decreased in the video settings\n- Open occultism-client.toml in your instance's /config folder and set useAlternativeDivinationRodRenderer = true to switch back to Occultism's old block outline renderer\n");

        this.page("otherworld_groves", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Otherworld Groves");
        this.pageText("Otherworld Groves are lush, overgrown caves, with [#](ad03fc)Otherworld Trees[#](),\nand walls of [](item://occultism:otherstone), and represent the fastest way to get everything one\nneeds to get set up as a summoner.\nTo find them, attune your divination rod to Otherworld leaves\nor logs, as unlike Otherstone, they only spawn in these groves.\n");

        this.page("otherworld_groves_2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("**Hint:** In the Overworld, look **down**.\n");

        this.page("otherworld_trees", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Otherworld Trees");
        this.pageText("Otherworld trees grow naturally in Otherworld Groves. To the naked eye they appear as oak trees,\nbut to the Third Eye they reveal their true nature.    \n**Important:** Otherworld Saplings can only be obtained by breaking the leaves manually, naturally only oak saplings drop.\n");

        this.page("otherworld_trees_2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("Trees grown from Stable Otherworld Saplings as obtained from spirit traders do not have that limitation.\n");

        this.page("config", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Extra Config");
        this.pageText("An additional function of the Divination Rod is to locate any ore,\n however this is not a default function and needs to be enabled,\n as we recommend using the Greedy familiar or Theurgy mod for this type of divination.\n If you want to enable this feature directly in Occultism Divination Rod, check\n \"Server Configuration > Items\" and set \"Divination c:ores\" to \"on\".\n");
    }

    @Override
    protected String entryName() {
        return "Divination Rod";
    }

    @Override
    protected String entryDescription() {
        return "Obtaining otherworld materials";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DIVINATION_ROD.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
