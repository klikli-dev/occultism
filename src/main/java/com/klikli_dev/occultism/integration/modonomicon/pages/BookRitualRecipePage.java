/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconPageTypeRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class BookRitualRecipePage extends BookRecipePage<RitualRecipe> {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual_recipe");
    public static final MapCodec<BookRitualRecipePage> CODEC = codec(BookRitualRecipePage::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, BookRitualRecipePage> STREAM_CODEC = streamCodec(BookRitualRecipePage::new);

    public BookRitualRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookRitualRecipePage(NetworkDataHolder data) {
        super(data);
    }

    @Override
    public BookPageType<?> type() {
        return OccultismModonomiconPageTypeRegistry.RITUAL_RECIPE;
    }
}
