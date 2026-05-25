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

public class FirstRitualEntry extends EntryProvider {

    public static final String ENTRY_ID = "first_ritual";

    public FirstRitualEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("The Ritual (tm)");
        this.pageText("These pages will walk the gentle reader through the process of the [first ritual](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_crusher_t1) step by step.\n\\\nWe **start** by placing the [](item://occultism:golden_sacrificial_bowl) and drawing the appropriate pentacle, [Aviar's Circle](entry://occultism:dictionary_of_spirits/pentacles/summon_foliot) as seen on the left around it.\n");

        this.page("multiblock", () -> BookMultiblockPageModel.create()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .withText(this.context().pageText()));
        this.pageText("Only the color and location of the chalk marks is relevant, not the glyph/sign.\n");

        this.page("bowl_text", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Sacrificial Bowls");
        this.pageText("Next, place *at least* 4 [Sacrificial Bowls](item://occultism:sacrificial_bowl) close to the pentacle.\n\\\n\\\nThey must be placed **anywhere** within 8 blocks of the central [](item://occultism:golden_sacrificial_bowl). **The exact location does not matter.**\n");

        this.page("bowl_placement", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .withText(this.context().pageText()));
        this.pageText("Some possible locations for the sacrificial bowls.\n");

        this.page("ritual_text", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Placing Ingredients");
        this.pageText("Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls. The ingredients will be consumed from the bowls as the ritual progresses.\n");

        this.page("ritual_recipe", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher")));

        this.page("pentacle_link_hint", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("A Note about Ritual Recipes");
        this.pageText("Ritual recipe pages, such as the previous pageshow not only the ingredients, but also the pentacle that you need to draw with chalk in order to use the ritual.\n\\\n\\\n**To show the pentacle, click the blue link** at the center top of the ritual page. You can then even preview it in-world.\n");

        this.page("start_ritual", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Let there be ... spirits!");
        this.pageText("Finally, [#](ad03fc)right-click[#]() the [](item://occultism:golden_sacrificial_bowl) with the **bound** book of binding you created before and wait until the crusher spawns.\n\\\n\\\nNow all that remains is to drop appropriate ores near the crusher and wait for it to turn it into dust.\n");

        this.page("automation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Automatic Rituals");
        this.pageText("Instead of right-clicking the golden ritual bowl with the final ingredient, you can also use a Hopper or any type of pipe to insert the item into the bowl. The ritual will start automatically.\\\nNote that any rituals that summon tamed animals or familiars will summon them untamed instead.\n");

        this.page("upside_down_bowl", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Don't let my items drop");
        this.pageText("If you want to hold crafted items instead of dropping them into the world, place a sacrificial bowl facing down above the golden one.\nThis works up to three blocks higher and can also be used with a copper or silver sacrificial bowl.\n");

        this.page("automation_flame", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.FLAME_AUTOMATION.get()))
                .withText(this.context().pageText()));
        this.pageText("The setup of upside-down bowl, also produce [](item://occultism:flame_of_automation) when the ritual don't has an item as output.\nFor example, this can be used to automate spirit summoning and possessing, as a return to your system (it will come with the NBT of the ritual performed).\n");

        this.page("redstone", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Redstone");
        this.pageText("Depending on the ritual state the golden bowl will emit a different redstone level:\n- **0** if no ritual is active\n- **1** if the ritual is active, but waiting for a sacrifice\n- **2** if the ritual is active, but waiting for an item to be used\n- **8** if the ritual is active and running\n");

        this.page("clone_redstone", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("All sides blocked?");
        this.pageText("You can place another [](item://occultism:golden_sacrificial_bowl) in the third block below the\n original [](item://occultism:golden_sacrificial_bowl). Every time this new bowl receives an\n block update, it clones the actual signal strength of the original bowl.\n");

        this.page("clone_placement", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/redstone_clone.png"))
                .withBorder(true)
                .withText(this.context().pageText()));
        this.pageText("One suggestion is to use any block that interacts with redstone and an observer.\n");
    }

    @Override
    protected String entryName() {
        return "First Ritual";
    }

    @Override
    protected String entryDescription() {
        return "We're actually getting started now!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_SUMMON.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
