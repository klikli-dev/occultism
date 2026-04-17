/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, MrRiegel, Sam Bassett
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

package com.klikli_dev.occultism.integration.jei.impl;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetRecipe;
import com.klikli_dev.occultism.network.messages.MessageSetRecipeByID;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */

public class StorageControllerRecipeTransferHandler<T extends AbstractContainerMenu & IStorageControllerContainer> implements IRecipeTransferHandler<T, RecipeHolder<CraftingRecipe>> {

    protected final Class<T> containerClass;
    protected final IRecipeTransferHandlerHelper handlerHelper;

    public StorageControllerRecipeTransferHandler(Class<T> containerClass, IRecipeTransferHandlerHelper handlerHelper) {
        this.handlerHelper = handlerHelper;
        this.containerClass = containerClass;
    }

    public CompoundTag recipeToNbt(AbstractContainerMenu container, IRecipeSlotsView recipeSlots) {
        CompoundTag nbt = new CompoundTag();
        var slotsViewList = recipeSlots.getSlotViews();

        for (Slot slot : container.slots) {
            if (slot.container instanceof CraftingContainer) {

                //get slot view corresponding to slot
                var slotView = slotsViewList.get(slot.getSlotIndex() + 1);
                if (slotView == null) {
                    continue;
                }

                //gets all items matching ingredients.
                List<ItemStack> possibleItems = slotView.getIngredients(VanillaTypes.ITEM_STACK).collect(Collectors.toList());
                if (possibleItems.isEmpty()) {
                    continue;
                }

                ListTag invList = new ListTag();
                for (int i = 0; i < possibleItems.size(); i++) {
                    if (i >= 5) {
                        break; //cap possible items at 5 to avoid mega-messages that hit network cap
                    }

                    //if stack is not empty, write to result
                    ItemStack itemStack = possibleItems.get(i);
                    if (!itemStack.isEmpty()) {
                        invList.add(ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, itemStack).getOrThrow().copy());
                    }
                }
                nbt.put("s" + (slot.getSlotIndex()), invList);
            }
        }
        return nbt;
    }

    @Override
    public Class<T> getContainerClass() {
        return this.containerClass;
    }

    @Override
    public Optional<MenuType<T>> getMenuType() {
        return Optional.empty();
    }

    @Override
    public IRecipeType<RecipeHolder<CraftingRecipe>> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    public IRecipeTransferError transferRecipe(T container, RecipeHolder<CraftingRecipe> recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {

        if (recipe.id() == null) {
            return this.handlerHelper.createUserErrorWithTooltip(Component.translatable("jei." + Occultism.MODID + "error.missing_id"));
        }

        //sort out any modded recipes that don't fit 3x3
        if (recipe.value().placementInfo().isImpossibleToPlace() || recipe.value().placementInfo().ingredients().size() > 9) {
            return this.handlerHelper.createUserErrorWithTooltip(Component.translatable("jei." + Occultism.MODID + "error.recipe_too_large"));
        }

        // can only send shaped/shapeless recipes to storage controller
        //  disabled this -> not a good idea for custom recipes that fit in 3x3 such as botania
        //  not needed either -> the 3x3 check handles anything that is invalid and still registers as crafting.
//        if (!(recipe instanceof ShapedRecipe) && !(recipe instanceof ShapelessRecipe)) {
//            return this.handlerHelper.createUserErrorWithTooltip(Component.translatable("jei." + Occultism.MODID + "error.invalid_type"));
//        }

        if (container instanceof StorageControllerContainerBase menu) {
            List<IRecipeSlotView> missing = new ArrayList<>();
            List<IRecipeSlotView> views = recipeSlots.getSlotViews();
            var reservedPlayerInventory = new int[player.getInventory().getNonEquipmentItems().size()];
            var reservedGridAmounts = new Object2IntOpenHashMap<>();

            for (IRecipeSlotView view : views) {
                if (view.getRole() == RecipeIngredientRole.INPUT || view.getRole() == RecipeIngredientRole.CRAFTING_STATION) {
                    List<ItemStack> possibleStacks = view.getItemStacks().toList();
                    if (possibleStacks.isEmpty()) {
                        continue;
                    }

                    boolean found = false;
                    for (ItemStack stack : possibleStacks) {
                        if (this.hasMatchingPlayerInventoryStack(player, stack, reservedPlayerInventory)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        for (ItemStack stack : possibleStacks) {
                            if (this.hasMatchingStorageStack(menu, stack, reservedGridAmounts)) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if (!found) {
                        missing.add(view);
                    }

                }
            }

            //if recipe is in recipe manager send by id, otherwise fallback to ingredient list
            if (doTransfer) {
                if (JeiPlugin.getSyncedRecipes().byKey(recipe.id()) != null) {
                    Networking.sendToServer(new MessageSetRecipeByID(recipe.id().identifier()));
                } else {
                    Networking.sendToServer(new MessageSetRecipe(this.recipeToNbt(container, recipeSlots)));
                }
            }
            if (!missing.isEmpty()) {
                return new TransferWarning(this.handlerHelper.createUserErrorForMissingSlots(Component.translatable("jei." + Occultism.MODID + ".error.recipe_no_items"), missing));
            }
        }
        return null;
    }

    private boolean hasMatchingPlayerInventoryStack(Player player, ItemStack desiredStack, int[] reservedPlayerInventory) {
        if (desiredStack == null || desiredStack.isEmpty()) {
            return false;
        }

        var mainInventory = player.getInventory().getNonEquipmentItems();
        for (int i = 0; i < mainInventory.size(); i++) {
            var playerStack = mainInventory.get(i);
            if (!playerStack.isEmpty()
                    && ItemStack.isSameItemSameComponents(playerStack, desiredStack)
                    && playerStack.getCount() > reservedPlayerInventory[i]) {
                reservedPlayerInventory[i]++;
                return true;
            }
        }

        return false;
    }

    private boolean hasMatchingStorageStack(StorageControllerContainerBase menu, ItemStack desiredStack,
                                            Object2IntOpenHashMap<Object> reservedAmounts) {
        if (desiredStack == null || desiredStack.isEmpty()) {
            return false;
        }

        for (int i = 1; i < 10; i++) {
            var slot = menu.getSlot(i);
            var stackInSlot = slot.getItem();
            if (!stackInSlot.isEmpty() && ItemStack.isSameItemSameComponents(stackInSlot, desiredStack)) {
                var reservedAmount = reservedAmounts.getOrDefault(slot, 0);
                if (stackInSlot.getCount() > reservedAmount) {
                    reservedAmounts.merge(slot, 1, Integer::sum);
                    return true;
                }
            }
        }

        var clientCache = menu.getClientStorageCache();
        if (clientCache == null) {
            return false;
        }

        for (var stack : clientCache.stacks()) {
            if (ItemStack.isSameItemSameComponents(stack, desiredStack)) {
                var reservedAmount = reservedAmounts.getOrDefault(stack, 0);
                if (stack.getCount() - reservedAmount >= 1) {
                    reservedAmounts.merge(stack, 1, Integer::sum);
                    return true;
                }
            }
        }

        return false;
    }

    private record TransferWarning(IRecipeTransferError parent) implements IRecipeTransferError {

        @Override
            public @NotNull Type getType() {
                return Type.COSMETIC;
            }

            @Override
            public void showError(@NotNull GuiGraphicsExtractor matrixStack, int mouseX, int mouseY, @NotNull IRecipeSlotsView recipeLayout, int recipeX,
                                  int recipeY) {
                this.parent.showError(matrixStack, mouseX, mouseY, recipeLayout, recipeX, recipeY);
            }
        }
}
