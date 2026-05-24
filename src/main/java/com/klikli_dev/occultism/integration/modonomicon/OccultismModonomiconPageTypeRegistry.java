package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.modonomicon.registry.BookPageTypeRegistry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePage;

public final class OccultismModonomiconPageTypeRegistry {

    public static final BookPageType<BookSpiritFireRecipePage> SPIRIT_FIRE_RECIPE = BookPageTypeRegistry.register(BookSpiritFireRecipePage.ID, BookSpiritFireRecipePage.CODEC, BookSpiritFireRecipePage.STREAM_CODEC);
    public static final BookPageType<BookSpiritTradeRecipePage> SPIRIT_TRADE_RECIPE = BookPageTypeRegistry.register(BookSpiritTradeRecipePage.ID, BookSpiritTradeRecipePage.CODEC, BookSpiritTradeRecipePage.STREAM_CODEC);
    public static final BookPageType<BookRitualRecipePage> RITUAL_RECIPE = BookPageTypeRegistry.register(BookRitualRecipePage.ID, BookRitualRecipePage.CODEC, BookRitualRecipePage.STREAM_CODEC);
    public static final BookPageType<BookBindingCraftingRecipePage> BOOK_BINDING_RECIPE = BookPageTypeRegistry.register(BookBindingCraftingRecipePage.ID, BookBindingCraftingRecipePage.CODEC, BookBindingCraftingRecipePage.STREAM_CODEC);

    private OccultismModonomiconPageTypeRegistry() {
    }

    public static void bootstrap() {
    }
}
