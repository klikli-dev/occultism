/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconPageTypeRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class BookSpiritTradeRecipePage extends BookProcessingRecipePage<SpiritTradeRecipe> {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_trade_recipe");
    public static final MapCodec<BookSpiritTradeRecipePage> CODEC = codec(BookSpiritTradeRecipePage::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, BookSpiritTradeRecipePage> STREAM_CODEC = streamCodec(BookSpiritTradeRecipePage::new);

    public BookSpiritTradeRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookSpiritTradeRecipePage(NetworkDataHolder data) {
        super(data);
    }

    @Override
    public BookPageType<?> type() {
        return OccultismModonomiconPageTypeRegistry.SPIRIT_TRADE_RECIPE;
    }
}
