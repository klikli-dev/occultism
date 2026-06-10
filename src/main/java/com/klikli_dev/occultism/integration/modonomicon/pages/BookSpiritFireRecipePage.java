/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconPageTypeRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class BookSpiritFireRecipePage extends BookProcessingRecipePage<SpiritFireRecipe> {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_fire_recipe");
    public static final MapCodec<BookSpiritFireRecipePage> CODEC = codec(BookSpiritFireRecipePage::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, BookSpiritFireRecipePage> STREAM_CODEC = streamCodec(BookSpiritFireRecipePage::new);

    public BookSpiritFireRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookSpiritFireRecipePage(NetworkDataHolder data) {
        super(data);
    }

    @Override
    public BookPageType<?> type() {
        return OccultismModonomiconPageTypeRegistry.SPIRIT_FIRE_RECIPE;
    }
}
