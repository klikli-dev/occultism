package com.klikli_dev.occultism.datagen.book.storage_system;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class ControllerEntry extends EntryProvider {

    public static final String ENTRY_ID = "storage_controller";

    public ControllerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_CONTROLLER);
    }

    @Override
    protected String entryName() {
        return "Storage Actuator";
    }

    @Override
    protected String entryDescription() {
        return "The controller";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(OccultismBlocks.STORAGE_CONTROLLER)
                .withText(this.context().pageText()));
        this.pageTitle("Storage Actuator");
        this.pageText("""
                   The {0} consists of a {1} inhabited by a {2} that creates and manages a storage dimension,
                    and a {3} infused with a {4} that moves items in and out of the storage dimension.
                    """,
                this.itemLink(OccultismBlocks.STORAGE_CONTROLLER),
                this.entryLink("Dimensional Matrix", "crafting_rituals", "craft_dimensional_matrix"),
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.entryLink("Base", "crafting_rituals", "craft_storage_controller_base"),
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("""
                   After crafting the {0} (see following pages), place it in the world and {1} it with an empty hand.
                    This will open the GUI of the storage controller, from there on it will work much like a very big {2}.
                    """,
                this.itemLink(OccultismBlocks.STORAGE_CONTROLLER),
                this.color("right-click", ChatFormatting.GREEN),
                this.itemLink(Items.SHULKER_BOX)
        );

        this.page("safety", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Safety first!");
        this.pageText("""
                    Breaking the storage controller will store all contained items in the dropped item, you will not lose anything.
                     The same applies to breaking or replacing Storage Stabilizers (you will learn about these later).
                     \\
                     \\
                     Like in a shulker box, your items are safe!
                    """
        );

        this.page("size", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("So much storage!");
        this.pageText("""
                   The storage controller holds up to **128** different types of items
                    (_You will learn later how to increase that_). Additionally it is limited to 256000 items in total.
                    It does not matter if you have 256000 different items or 256000 of one item, or any mix.
                    """
        );

        this.page("unique_items", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Unique Items");
        this.pageText("""
                   Items with unique properties ("NBT data"), such as damaged or enchanted equipment will take up
                    one item type for each variation. For example two wooden swords with two different damage values
                    take up two item types. Two wooden swords with the same (or no) damage take up one item type.
                    """
        );

        this.page("config", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Configurability");
        this.pageText("""
                   The item type amount and storage size can be configured in the
                    "{0}" config file in the save directory of your world.
                    """,
                this.color("occultism-server.toml", ChatFormatting.DARK_PURPLE)
        );

        this.page("mods", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Interaction with Mods");
        this.pageText("""
                    For other mods the storage controller behaves like a shulker box, anything that can
                     interact with vanilla chests and shulker boxes can interact with the storage controller.
                     Devices that count storage contents may have trouble with the stack sizes.
                    """
        );

        this.page("matrix_ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix")));
        //no text

        this.page("base_ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base")));
        //no text

        this.page("base_ritual", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/storage_controller"))
                .withText(this.context().pageText()));
        this.pageText("""
                   This is the actual block that works as a storage, make sure to craft it!
                    Placing just the {0} from the previous step won't work.
                    """,
                this.itemLink(OccultismBlocks.STORAGE_CONTROLLER_BASE)
        );
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
