/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.ChatFormatting;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class BookRitualRecipePage extends BookRecipePage<RitualRecipe> {
    private static final AtomicBoolean LOGGED_SERVER_BUILD_DIAGNOSTICS = new AtomicBoolean(false);
    @Nullable
    private RitualRecipe ritualRecipe;

    public BookRitualRecipePage(JsonDataHolder data) {
        this(data, null);
    }

    public BookRitualRecipePage(NetworkDataHolder data) {
        this(data, null);
    }

    public BookRitualRecipePage(JsonDataHolder data, @Nullable RitualRecipe ritualRecipe) {
        super(data);
        this.ritualRecipe = ritualRecipe;
    }

    public BookRitualRecipePage(NetworkDataHolder data, @Nullable RitualRecipe ritualRecipe) {
        super(data);
        this.ritualRecipe = ritualRecipe;
    }

    public static BookRitualRecipePage fromJson(Identifier entryId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(entryId, json, provider);
        return new BookRitualRecipePage(common, null);
    }

    public static BookRitualRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        var ritualRecipe = buffer.readBoolean() ? RitualRecipe.STREAM_CODEC.decode(buffer) : null;
        return new BookRitualRecipePage(common, ritualRecipe);
    }

    @Nullable
    public RitualRecipe getRitualRecipe() {
        return this.ritualRecipe;
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeBoolean(this.ritualRecipe != null);
        if (this.ritualRecipe != null) {
            RitualRecipe.STREAM_CODEC.encode(buffer, this.ritualRecipe);
        }
    }

    @Override
    public void build(Level level, BookContentEntry parentEntry, int pageNum) {
        boolean autoTitle = this.title1.isEmpty();
        boolean emptyText = this.text.isEmpty();
        super.build(level, parentEntry, pageNum);

        if (level instanceof ServerLevel serverLevel) {
            var holder = this.recipeKey1 != null ? serverLevel.recipeAccess().byKey(this.recipeKey1).orElse(null) : null;
            this.ritualRecipe = holder != null && holder.value() instanceof RitualRecipe ritualRecipe ? ritualRecipe : null;
            this.logServerDiagnostics(serverLevel, holder);
        }

        this.populateTitleFromRecipeIfNeeded(autoTitle);
        this.populateFallbackErrorTextIfMissing(emptyText);
    }

    private void populateTitleFromRecipeIfNeeded(boolean autoTitle) {
        if (autoTitle && this.recipeDisplayEntry1 == null && this.ritualRecipe != null) {
            ItemStack titleStack = this.ritualRecipe.getResult();
            if (titleStack.isEmpty()) {
                titleStack = this.ritualRecipe.getRitualDummy();
            }

            this.title1 = new BookTextHolder(titleStack.getHoverName().copy()
                    .withStyle(Style.EMPTY
                            .withBold(true)
                            .withColor(this.getParentEntry().getBook().getDefaultTitleColor())));
        }
    }

    private void populateFallbackErrorTextIfMissing(boolean emptyText) {
        if (emptyText && this.recipeDisplayEntry1 == null && this.ritualRecipe == null) {
            this.text = new BookTextHolder(Component.literal("Ritual recipe and display were not synchronized to the client. Check the log for occultism Modonomicon diagnostics.")
                    .withStyle(ChatFormatting.RED));
        }
    }

    private void logServerDiagnostics(ServerLevel level, @Nullable net.minecraft.world.item.crafting.RecipeHolder<?> holder) {
        if (!LOGGED_SERVER_BUILD_DIAGNOSTICS.compareAndSet(false, true)) {
            return;
        }

        var ritualRecipes = level.recipeAccess().getRecipes().stream()
                .filter(recipeHolder -> recipeHolder.value() instanceof RitualRecipe)
                .map(recipeHolder -> recipeHolder.id().identifier().toString())
                .sorted()
                .toList();
        var requested = this.recipeKey1 != null ? this.recipeKey1.identifier().toString() : "<null>";
        var display = this.recipeDisplayEntry1 != null ? this.recipeDisplayEntry1.display().type().toString() : "<null>";
        var rawType = holder != null ? holder.value().getClass().getName() : "<null>";

        Occultism.LOGGER.info("[Modonomicon Ritual Diagnostics][Server] requestedRecipe={}, rawRecipePresent={}, rawRecipeType={}, pageDisplayEntryPresent={}, pageDisplayType={}, syncedRecipePresent={}, ritualRecipeCount={}, ritualRecipes={}",
                requested,
                holder != null,
                rawType,
                this.recipeDisplayEntry1 != null,
                display,
                this.ritualRecipe != null,
                ritualRecipes.size(),
                ritualRecipes);
    }

    @Override
    public Identifier getType() {
        return OccultismModonomiconConstants.Page.RITUAL_RECIPE;
    }
}
