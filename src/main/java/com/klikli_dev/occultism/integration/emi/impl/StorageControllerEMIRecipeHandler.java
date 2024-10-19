
/*
 * MIT License
 *
 * Copyright 2024 Creators of AE2
 * Copyright 2024 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.integration.emi.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.klikli_dev.occultism.common.misc.ItemStackKey;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetRecipeByTemplate;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.Widget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * See also https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/integration/modules/emi/EmiUseCraftingRecipeHandler.java
 * @param <T>
 */
public class StorageControllerEMIRecipeHandler<T extends StorageControllerContainerBase> implements StandardRecipeHandler<T> {
    // Colors for the slot highlights
    public static final int BLUE_SLOT_HIGHLIGHT_COLOR = 0x400000ff;
    public static final int RED_SLOT_HIGHLIGHT_COLOR = 0x66ff0000;
    // Colors for the buttons
    public static final int BLUE_PLUS_BUTTON_COLOR = 0x804545FF;
    public static final int ORANGE_PLUS_BUTTON_COLOR = 0x80FFA500;
    protected static final int CRAFTING_GRID_WIDTH = 3;
    protected static final int CRAFTING_GRID_HEIGHT = 3;
    //TODO: Implement
    //      https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/integration/modules/emi/AbstractRecipeHandler.java
    //      https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/integration/modules/emi/EmiUseCraftingRecipeHandler.java
    //      https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/menu/me/items/CraftingTermMenu.java#L199 for get missing ingredients
    //      https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/menu/me/common/MEStorageMenu.java#L696 for get missing ingredients / has ingredient
    //       See also EMI CraftingRecipeHandler and StorageControllerRecipeTransferHandler
    private static final Comparator<ItemStack> ENTRY_COMPARATOR = Comparator
            .comparing(ItemStack::getCount);
    protected final Class<T> containerClass;

    public StorageControllerEMIRecipeHandler(Class<T> containerClass) {
        this.containerClass = containerClass;
    }

    public static Map<Integer, Ingredient> getGuiSlotToIngredientMap(Recipe<?> recipe) {
        var ingredients = recipe.getIngredients();

        // JEI will align non-shaped recipes smaller than 3x3 in the grid. It'll center them horizontally, and
        // some will be aligned to the bottom. (i.e. slab recipes).
        int width;
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            width = shapedRecipe.getWidth();
        } else {
            width = CRAFTING_GRID_WIDTH;
        }

        var result = new HashMap<Integer, Ingredient>(ingredients.size());
        for (int i = 0; i < ingredients.size(); i++) {
            var guiSlot = (i / width) * CRAFTING_GRID_WIDTH + (i % width);
            var ingredient = ingredients.get(i);
            if (!ingredient.isEmpty()) {
                result.put(guiSlot, ingredient);
            }
        }
        return result;
    }

    public static void performTransfer(StorageControllerContainerBase menu, @Nullable ResourceLocation recipeId, Recipe<?> recipe) {

        // We send the items in the recipe in any case to serve as a fallback in case the recipe is transient
        var templateItems = findGoodTemplateItems(recipe, menu);

        // Don't transmit a recipe id to the server in case the recipe is not actually resolvable
        // this is the case for recipes synthetically generated for JEI/EMI
        if (recipeId != null && menu.player.level().getRecipeManager().byKey(recipeId).isEmpty()) {
            Occultism.LOGGER.debug("Cannot send recipe id %s to server because it's transient", recipeId);
            recipeId = null;
        }

        Networking.sendToServer(new MessageSetRecipeByTemplate(recipeId, templateItems));
    }

    private static NonNullList<ItemStack> findGoodTemplateItems(Recipe<?> recipe, StorageControllerContainerBase menu) {
        var ingredientPriorities = getIngredientPriorities(menu, ENTRY_COMPARATOR);

        var templateItems = NonNullList.withSize(9, ItemStack.EMPTY);
        var ingredients = EmiHelper.ensure3by3CraftingMatrix(recipe);
        for (int i = 0; i < ingredients.size(); i++) {
            var ingredient = ingredients.get(i);
            if (!ingredient.isEmpty()) {
                // Try to find the best item. In case the ingredient is a tag, it might contain versions the
                // player doesn't actually have
                var stack = ingredientPriorities.entrySet()
                        .stream()
                        .filter(e -> ingredient.test(e.getKey().stack()))
                        .max(Comparator.comparingInt(Map.Entry::getValue))
                        .map(e -> e.getKey().stack())
                        .orElse(ingredient.getItems()[0]);

                templateItems.set(i, stack);
            }
        }
        return templateItems;
    }

    public static Map<ItemStackKey, Integer> getIngredientPriorities(StorageControllerContainerBase menu,
                                                                     Comparator<ItemStack> comparator) {
        var orderedEntries = menu.getClientStorageCache().stacks()
                .stream()
                .sorted(comparator)
                .toList();

        var result = new HashMap<ItemStackKey, Integer>(orderedEntries.size());
        for (int i = 0; i < orderedEntries.size(); i++) {
            result.put(new ItemStackKey(orderedEntries.get(i)), i);
        }

        // Also consider the player inventory, but only as the last resort
        for (var item : menu.playerInventory.items) {
            // Use -1 as lower priority than the lowest network entry (which start at 0)
            result.putIfAbsent(new ItemStackKey(item), -1);
        }

        return result;
    }



    private static void renderMissingAndCraftableSlotOverlays(Map<Integer, SlotWidget> inputSlots,
                                                              GuiGraphics guiGraphics,
                                                              Set<Integer> missingSlots, Set<Integer> craftableSlots) {
        for (var entry : inputSlots.entrySet()) {
            boolean missing = missingSlots.contains(entry.getKey());
            boolean craftable = craftableSlots.contains(entry.getKey());
            if (missing || craftable) {
                var poseStack = guiGraphics.pose();
                poseStack.pushPose();
                poseStack.translate(0, 0, 400);
                var innerBounds = getInnerBounds(entry.getValue());
                guiGraphics.fill(innerBounds.x(), innerBounds.y(), innerBounds.right(),
                        innerBounds.bottom(), missing ? RED_SLOT_HIGHLIGHT_COLOR : BLUE_SLOT_HIGHLIGHT_COLOR);
                poseStack.popPose();
            }
        }
    }

    private static boolean isInputSlot(SlotWidget slot) {
        return slot.getRecipe() == null;
    }

    private static Bounds getInnerBounds(SlotWidget slot) {
        var bounds = slot.getBounds();
        return new Bounds(
                bounds.x() + 1,
                bounds.y() + 1,
                bounds.width() - 2,
                bounds.height() - 2);
    }

    private static Map<Integer, SlotWidget> getRecipeInputSlots(EmiRecipe recipe, List<Widget> widgets) {
        // Map ingredient indices to their respective slots
        var inputSlots = new HashMap<Integer, SlotWidget>(recipe.getInputs().size());
        for (int i = 0; i < recipe.getInputs().size(); i++) {
            for (var widget : widgets) {
                if (widget instanceof SlotWidget slot && isInputSlot(slot)) {
                    if (slot.getStack() == recipe.getInputs().get(i)) {
                        inputSlots.put(i, slot);
                    }
                }
            }
        }
        return inputSlots;
    }

    public static List<Component> createCraftingTooltip(StorageControllerContainerBase.MissingIngredientSlots missingSlots, boolean withTitle) {
        List<Component> tooltip = new ArrayList<>();
        if (withTitle) {
            tooltip.add(Component.translatable("jei." + Occultism.MODID + ".error.recipe_move_items"));
        }
        if (missingSlots.anyMissing()) {
            tooltip.add(Component.translatable("jei." + Occultism.MODID + ".error.recipe_items_missing").withStyle(ChatFormatting.RED));
        }
        return tooltip;
    }

    @Override
    public List<Slot> getInputSources(T handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(handler.getSlot(i));
        }
        //10 is the order inventory slot which EMI does not need
        int invStart = 11;
        for (int i = invStart; i < invStart + 36; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(T handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public @Nullable Slot getOutputSlot(T handler) {
        return handler.slots.get(0);
    }

    @Override
    public boolean canCraft(EmiRecipe recipe, EmiCraftContext<T> context) {
        if (context.getType() == EmiCraftContext.Type.FILL_BUTTON) {
            return this.transferRecipe(recipe, context, false).canCraft();
        }
        return StandardRecipeHandler.super.canCraft(recipe, context);
    }

    protected Result transferRecipe(T menu, RecipeHolder<?> holder, EmiRecipe emiRecipe, boolean doTransfer) {

        var recipeId = holder != null ? holder.id() : null;
        var recipe = holder != null ? holder.value() : null;

        boolean craftingRecipe = this.isCraftingRecipe(recipe, emiRecipe);
        if (!craftingRecipe) {
            return Result.createNotApplicable();
        }

        if (!this.fitsIn3x3Grid(recipe, emiRecipe)) {
            return Result.createFailed(Component.translatable("jei." + Occultism.MODID + ".error.recipe_too_large"));
        }

        if (recipe == null) {
            recipe = this.createFakeRecipe(emiRecipe);
        }

        // Find missing ingredient
        var slotToIngredientMap = getGuiSlotToIngredientMap(recipe);
        var missingSlots = menu.findMissingIngredients(slotToIngredientMap);

        if (missingSlots.missingSlots().size() == slotToIngredientMap.size()) {
            // All missing, can't do much...
            return Result.createFailed(Component.translatable("jei." + Occultism.MODID + ".error.recipe_no_items"), missingSlots.missingSlots());
        }

        if (!doTransfer) {
            if (missingSlots.anyMissingOrCraftable()) {
                // Highlight the slots with missing ingredients
                return new Result.PartiallyCraftable(missingSlots);
            }
        } else {
            performTransfer(menu, recipeId, recipe);
        }

        // No error
        return Result.createSuccessful();
    }

    private Recipe<?> createFakeRecipe(EmiRecipe display) {
        var ingredients = NonNullList.withSize(CRAFTING_GRID_WIDTH * CRAFTING_GRID_HEIGHT,
                Ingredient.EMPTY);

        for (int i = 0; i < Math.min(display.getInputs().size(), ingredients.size()); i++) {
            var ingredient = Ingredient.of(display.getInputs().get(i).getEmiStacks().stream()
                    .map(EmiStack::getItemStack)
                    .filter(is -> !is.isEmpty()));
            ingredients.set(i, ingredient);
        }

        var pattern = new ShapedRecipePattern(CRAFTING_GRID_WIDTH, CRAFTING_GRID_HEIGHT, ingredients, Optional.empty());
        return new ShapedRecipe("", CraftingBookCategory.MISC, pattern, ItemStack.EMPTY);
    }

    protected final Result transferRecipe(EmiRecipe emiRecipe, EmiCraftContext<T> context, boolean doTransfer) {
        if (!this.containerClass.isInstance(context.getScreenHandler())) {
            return Result.createNotApplicable();
        }

        T menu = this.containerClass.cast(context.getScreenHandler());

        var holder = this.getRecipeHolder(context.getScreenHandler().player.level(), emiRecipe);

        var result = this.transferRecipe(menu, holder, emiRecipe, doTransfer);
        if (result instanceof Result.Success && doTransfer) {
            Minecraft.getInstance().setScreen(context.getScreen());
        }
        return result;
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return true;
    }

    @Override
    public boolean craft(EmiRecipe recipe, EmiCraftContext<T> context) {
        return this.transferRecipe(recipe, context, true).canCraft();
    }

    @Override
    public List<ClientTooltipComponent> getTooltip(EmiRecipe recipe, EmiCraftContext<T> context) {
        var tooltip = this.transferRecipe(recipe, context, false).getTooltip(recipe, context);
        if (tooltip != null) {
            return tooltip.stream()
                    .map(Component::getVisualOrderText)
                    .map(ClientTooltipComponent::create)
                    .toList();
        } else {
            return StandardRecipeHandler.super.getTooltip(recipe, context);
        }
    }

    @Override
    public void render(EmiRecipe recipe, EmiCraftContext<T> context, List<Widget> widgets, GuiGraphics draw) {
        this.transferRecipe(recipe, context, false).render(recipe, context, widgets, draw);
    }

    @Nullable
    private RecipeHolder<?> getRecipeHolder(Level level, EmiRecipe recipe) {
        if (recipe.getBackingRecipe() != null) {
            return recipe.getBackingRecipe();
        }
        if (recipe.getId() != null) {
            // TODO: This can produce false positives...
            return level.getRecipeManager().byKey(recipe.getId()).orElse(null);
        }
        return null;
    }

    protected final boolean isCraftingRecipe(Recipe<?> recipe, EmiRecipe emiRecipe) {
        return (recipe != null && recipe.getType() == RecipeType.CRAFTING)
                || emiRecipe.getCategory().equals(VanillaEmiRecipeCategories.CRAFTING);
    }

    protected final boolean fitsIn3x3Grid(Recipe<?> recipe, EmiRecipe emiRecipe) {
        if (recipe != null) {
            return recipe.canCraftInDimensions(CRAFTING_GRID_WIDTH, CRAFTING_GRID_HEIGHT);
        } else {
            return true;
        }
    }

    protected static sealed abstract class Result {
        static NotApplicable createNotApplicable() {
            return new NotApplicable();
        }

        static Success createSuccessful() {
            return new Success();
        }

        static Error createFailed(Component text) {
            return new Error(text, Set.of());
        }

        static Error createFailed(Component text, Set<Integer> missingSlots) {
            return new Error(text, missingSlots);
        }

        /**
         * @return null doesn't override the default tooltip.
         */
        @Nullable
        List<Component> getTooltip(EmiRecipe recipe, EmiCraftContext<?> context) {
            return null;
        }

        abstract boolean canCraft();

        void render(EmiRecipe recipe, EmiCraftContext<? extends StorageControllerContainerBase> context, List<Widget> widgets,
                    GuiGraphics draw) {
        }

        static final class Success extends Result {
            @Override
            boolean canCraft() {
                return true;
            }
        }

        /**
         * There are missing ingredients, but at least one is present.
         */
        static final class PartiallyCraftable extends Result {
            private final StorageControllerContainerBase.MissingIngredientSlots missingSlots;

            public PartiallyCraftable(StorageControllerContainerBase.MissingIngredientSlots missingSlots) {
                this.missingSlots = missingSlots;
            }

            @Override
            boolean canCraft() {
                return true;
            }

            @Override
            List<Component> getTooltip(EmiRecipe recipe, EmiCraftContext<?> context) {
                // EMI caches this tooltip, we cannot dynamically react to control being held here
                return createCraftingTooltip(this.missingSlots, false);
            }

            @Override
            void render(EmiRecipe recipe, EmiCraftContext<? extends StorageControllerContainerBase> context, List<Widget> widgets,
                        GuiGraphics guiGraphics) {
                renderMissingAndCraftableSlotOverlays(getRecipeInputSlots(recipe, widgets), guiGraphics,
                        this.missingSlots.missingSlots(),
                        this.missingSlots.craftableSlots());
            }
        }

        static final class NotApplicable extends Result {
            @Override
            boolean canCraft() {
                return false;
            }
        }

        static final class Error extends Result {
            private final Component message;
            private final Set<Integer> missingSlots;

            public Error(Component message, Set<Integer> missingSlots) {
                this.message = message;
                this.missingSlots = missingSlots;
            }

            public Component getMessage() {
                return this.message;
            }

            @Override
            boolean canCraft() {
                return false;
            }

            @Override
            void render(EmiRecipe recipe, EmiCraftContext<? extends StorageControllerContainerBase> context, List<Widget> widgets,
                        GuiGraphics guiGraphics) {

                renderMissingAndCraftableSlotOverlays(getRecipeInputSlots(recipe, widgets), guiGraphics, this.missingSlots,
                        Set.of());
            }
        }
    }
}
