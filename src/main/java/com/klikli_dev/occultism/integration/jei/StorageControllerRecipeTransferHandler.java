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

package com.klikli_dev.occultism.integration.jei;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.network.MessageSetRecipe;
import com.klikli_dev.occultism.network.MessageSetRecipeByID;
import com.klikli_dev.occultism.network.Networking;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */

public class StorageControllerRecipeTransferHandler<T extends AbstractContainerMenu & IStorageControllerContainer> implements IRecipeTransferHandler<T, CraftingRecipe> {

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
                        invList.add(itemStack.serializeNBT());
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
    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    public IRecipeTransferError transferRecipe(T container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {

        if (recipe.getId() == null) {
            return this.handlerHelper.createUserErrorWithTooltip(Component.translatable("jei." + Occultism.MODID + "error.missing_id"));
        }

        //sort out any modded recipes that don't fit 3x3
        if (!recipe.canCraftInDimensions(3, 3)) {
            return this.handlerHelper.createUserErrorWithTooltip(Component.translatable("jei." + Occultism.MODID + "error.recipe_too_large"));
        }

        // can only send shaped/shapeless recipes to storage controller
        //  disabled this -> not a good idea for custom recipes that fit in 3x3 such as botania
        //  not needed either -> the 3x3 check handles anything that is invalid and still registers as crafting.
//        if (!(recipe instanceof ShapedRecipe) && !(recipe instanceof ShapelessRecipe)) {
//            return this.handlerHelper.createUserErrorWithTooltip(Component.translatable("jei." + Occultism.MODID + "error.invalid_type"));
//        }

        //if recipe is in recipe manager send by id, otherwise fallback to ingredient list
        if (doTransfer) {
            if (player.getCommandSenderWorld().getRecipeManager().byKey(recipe.getId()).isPresent()) {
                Networking.sendToServer(new MessageSetRecipeByID(recipe.getId()));
            } else {
                Networking.sendToServer(new MessageSetRecipe(this.recipeToNbt(container, recipeSlots)));
            }
        }
        return null;
    }
}
