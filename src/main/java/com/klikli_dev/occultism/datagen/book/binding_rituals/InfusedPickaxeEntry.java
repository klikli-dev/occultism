package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class InfusedPickaxeEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_infused_pickaxe";

    public InfusedPickaxeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.INFUSED_PICKAXE.get());
    }

    @Override
    protected String entryName() {
        return "Infused Pickaxe";
    }

    @Override
    protected String entryDescription() {
        return "When the Third Eye isn't enough";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Otherworld ores usually can only be mined with Otherworld metal tools.
                         The {0} is a makeshift solution to this Chicken-and-Egg problem.
                         Brittle spirit attuned gems house a {1} that allows harvesting the,
                         but the durability is extremely low. A more durable version is the {2}.
                        """,
                this.itemLink(OccultismItems.INFUSED_PICKAXE.get()),
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.entryLink("Iesnium Pickaxe", "getting_started", "iesnium_pickaxe")
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_pickaxe"))
        );
        //no text
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
