package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class DimensionalBattlefieldEntry extends EntryProvider {

    public static final String ENTRY_ID = "dimensional_battlefield";


    public DimensionalBattlefieldEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                         The dimensional battlefield houses an {0} that opens a stable gateway
                          to a combat arena, perfectly suited for epic battles. Although the
                          portal is too small to transport humans, the afrit is able to pass
                          through it, carrying a few items to farm mob drops within the dimension.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_battlefield"))
        );
        //no text

        this.page("use", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Using");
        this.pageText("""
                         To activate the Dimensional Battlefield, you must supply:
                          + A mob captured with {0} (or one of its variants).
                          + A weapon for the afrit to wield in combat.
                          + A source of spiritual fuel, such as {1}
                        """,
                this.itemLink(OccultismItems.SOUL_GEM_ITEM),
                this.itemLink(OccultismItems.DATURA)
        );

        this.page("gem", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Capture methods");
        this.pageText("""
                         {0} is the default and produces basic drops when simulating possessed mobs.
                          Using a {1} comes with a chance of failure.
                          Finally, use the {2} improves efficiency, allowing the farming of
                          possessed mobs and bosses, and triples the loot from other mobs.
                          The {3} don't need spiritual fuel, but can be consumed after the process.
                        """,
                this.itemLink(OccultismItems.SOUL_GEM_ITEM),
                this.itemLink(OccultismItems.FRAGILE_SOUL_GEM_ITEM),
                this.itemLink(OccultismItems.TRINITY_GEM_ITEM),
                this.itemLink(OccultismItems.SOUL_SHATTERED_ITEM)
        );

        this.page("fuel", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Spiritual Fuel");
        this.pageText("""
                         Spiritual fuel is used to clone the captured mob,
                          allowing it to be defeated in order to obtain its loot.
                          Some resources can be used: {0}, {1}, {2}, {3}.
                          \\
                          The higher the quality of the resource, the greater its value.
                          A total value equal to the mob’s health is required to initiate the battle.
                        """,
                this.itemLink(OccultismItems.DATURA_SEEDS),
                this.itemLink(OccultismItems.DATURA),
                this.itemLink(OccultismItems.DEMONS_DREAM_ESSENCE),
                this.itemLink(OccultismItems.OTHERWORLD_ESSENCE)
        );

        this.page("capabilities", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Operation");
        this.pageText("""
                         The dimensional battlefield will discard any items it cannot store, so it is important
                          to regularly empty the output, either manually, with hoppers or using a transporter spirit.\\
                          Interactions per side:
                          + Top -> input slots (gem, weapon, fuel);
                          + Bottom -> loot slots;
                          + Other -> all slots;
                        """
        );

        this.page("redstone", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Redstone");
        this.pageText("""
                         The dimensional battlefield has two interactions with redstone:
                          1. The spirit will stop working when receives a redstone signal;
                          2. A comparator can be used to extract a signal based on occupied slots and weapon durability.
                          Tip, if the comparator sends a power of 15, it is better to stop the operations.
                        """
        );

        this.page("enchantment", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Enchantments");
        this.pageText("""
                        As you know, weapons can be enchanted. Applying looting increases the amount of drops obtained.
                        \\
                        Sharpness speeds up the process, though not as effectively as Smite, Bane of
                        Arthropods, or Impaling when the mob is vulnerable to those enchantments.
                      """
        );

        this.page("enchantment2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Durability");
        this.pageText("""
                       The **Unbreaking** and **Mending** enchantments function as vanilla.
                       \\
                        With other methods (mods) you can make the weapon unusable (**unusing**)
                        when it's close to breaking, or even truly unbreakable (**eternal**).
                       """
        );
    }

    @Override
    protected String entryName() {
        return "Dimensional Battlefield";
    }

    @Override
    protected String entryDescription() {
        return "Spiritual mob killing simulator";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
