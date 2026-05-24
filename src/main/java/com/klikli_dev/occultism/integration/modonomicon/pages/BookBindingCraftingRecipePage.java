/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconPageTypeRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public class BookBindingCraftingRecipePage extends BookRecipePage<Recipe<?>> {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "book_binding_recipe");
    public static final MapCodec<BookBindingCraftingRecipePage> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            JSON_COMMON_CODEC.forGetter(BookBindingCraftingRecipePage::toJsonDataHolder),
            ItemStackTemplate.CODEC.fieldOf("unbound_book").forGetter(page -> page.unboundBook)
    ).apply(instance, BookBindingCraftingRecipePage::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BookBindingCraftingRecipePage> STREAM_CODEC = StreamCodec.composite(
            NETWORK_COMMON_STREAM_CODEC, BookBindingCraftingRecipePage::toNetworkDataHolder,
            ItemStackTemplate.STREAM_CODEC, page -> page.unboundBook,
            BookBindingCraftingRecipePage::new
    );

    ItemStackTemplate unboundBook;

    public BookBindingCraftingRecipePage(JsonDataHolder data, ItemStackTemplate unboundBook) {
        super(data);
        this.unboundBook = unboundBook;
    }

    public BookBindingCraftingRecipePage(NetworkDataHolder data, ItemStackTemplate unboundBook) {
        super(data);
        this.unboundBook = unboundBook;
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        STREAM_CODEC.encode(buffer, this);
    }

    @Override
    public void build(Level level, BookContentEntry parentEntry, int pageNum) {
        // This page renders its recipe manually from the stored unbound book.
        // In 26.1 Modonomicon resolves recipe pages through RecipeDisplayEntry,
        // but this special crafting recipe has no server-side display entry.
        // Calling the base BookRecipePage#build would therefore log a spurious
        // "Recipe ... not found" warning even though the recipe itself exists.
        this.setParentEntry(parentEntry);
        this.setPageNumber(pageNum);
        this.book = parentEntry.getBook();

        if (this.title1.isEmpty()) {
            var boundBook = BoundBookOfBindingRecipe.getBoundBookFromBook(this.unboundBook.create());
            this.title1 = new BookTextHolder(boundBook.getHoverName().copy()
                    .withStyle(Style.EMPTY
                            .withBold(true)
                            .withColor(this.getParentEntry().getBook().theme().palette().defaultTitleColor())));
        }
    }

    @Override
    public BookPageType<?> type() {
        return OccultismModonomiconPageTypeRegistry.BOOK_BINDING_RECIPE;
    }
}
