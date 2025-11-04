package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class EntityWormholeEntry extends EntryProvider {

    public static final String ENTRY_ID = "entity_wormhole";

    public EntityWormholeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.ENTITY_WORMHOLE.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                        The [](item://occultism:entity_wormhole) is a mystical teleportation device maintained by a {0},
                         capable of instantly transporting living beings across vast distances. Once attuned,
                         it creates a stable rift that creatures can step through, linking distant points as if
                         they were side by side. The Djinni ensures the wormhole remains open and aligned,
                         making it a reliable—though undeniably arcane—means of travel.
                                                
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_entity_wormhole"))
        );
        //no text

        this.page("destination", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Set destination");
        this.pageText("""
                        To set the destination, you''ll need a compass. Right-click to place it and shift+right-click to remove it.
                        + A standard compass takes you to the world spawn;
                        + A compass attached to a lodestone takes you to the top of it. (After placing it in the wormhole, the lodestone can be broken);
                        """
        );

        this.page("extra_uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Other compasses");
        this.pageText("""
                        + A compass renamed "{0}" teleport for your personal spawn point;
                        + A compass renamed "{1}" acts as a Random Teleport;
                        \\
                        Alternatively, you can use special compasses:
                        + [](item://minecraft:recovery_compass) teleports to the location of your last death, works only for players;
                        + [](item://occultism:vitality_compass) teleports to the linked creature, it needs to be in a loaded chunk;
                        """,
                this.color("HOME", ChatFormatting.DARK_PURPLE),
                this.color("RTP", ChatFormatting.DARK_PURPLE)
        );

        this.page("exit_yaw", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Exit viewing direction");
        this.pageText("""
                        Using a [](item://occultism:spirit_attuned_gem) will define the yaw viewing angle after teleportation.
                        An iesnium nugget will point to the set direction like a compass rose.
                        """
        );

        this.page("exit_pitch", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Exit viewing inclination");
        this.pageText("""
                        If you hold the gem in your off-hand, it will change the pitch viewing angle.
                         A six-pointed star in the center of the portal indicates the current setting:
                        + Emerald -> Forward
                        + Iron -> Tilted Down
                        + Redstone -> Straight Down
                        + Diamond -> Tilted Up
                        + Gold -> Straight Up
                        """
        );

        this.page("fishing", () -> BookSpotlightPageModel.create()
                .withItem(Items.FISHING_ROD)
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Get over here!");
        this.pageText("""
                        If the wormhole contains an {0}, you can use a fishing rod to pull the linked
                         entity into the portal, the hook needs to stop in the portal before you pull.
                        """,
                this.entryLink("Vitality Compass", "crafting_rituals", "vitality_compass")
        );
    }

    @Override
    protected String entryName() {
        return "Entity Wormhole";
    }

    @Override
    protected String entryDescription() {
        return "Fast travel";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.ENTITY_WORMHOLE.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
