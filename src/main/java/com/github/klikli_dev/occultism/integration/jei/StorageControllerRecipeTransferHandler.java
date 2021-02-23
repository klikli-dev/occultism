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

package com.github.klikli_dev.occultism.integration.jei;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.network.MessageSetRecipe;
import com.github.klikli_dev.occultism.network.MessageSetRecipeByID;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredient;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */
public class StorageControllerRecipeTransferHandler<T extends Container & IStorageControllerContainer> implements IRecipeTransferHandler<T> {

    //region Fields
    protected final Class<T> containerClass;
    protected final IRecipeTransferHandlerHelper helper;
    //endregion Fields

    //region Initialization
    public StorageControllerRecipeTransferHandler(Class<T> containerClass, IRecipeTransferHandlerHelper helper) {
        this.containerClass = containerClass;
        this.helper = helper;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Class<T> getContainerClass() {
        return this.containerClass;
    }

    @Nullable
    @Override
    public IRecipeTransferError transferRecipe(T container, Object recipeObject, IRecipeLayout recipeLayout,
                                               PlayerEntity player, boolean maxTransfer, boolean doTransfer) {


        IRecipe<?> recipe = (IRecipe<?>) recipeObject;

        if (recipe.getId() == null) {
            return this.helper.createUserErrorWithTooltip(I18n.format("jei." + Occultism.MODID + "error.missing_id"));
        }

        //sort out any modded recipes that don't fit 3x3
        if (!recipe.canFit(3, 3)) {
            return this.helper.createUserErrorWithTooltip(I18n.format("jei." + Occultism.MODID + "error.recipe_too_large"));
        }

        // can only send shaped/shapeless recipes to storage controller
        //  disabled this -> not a good idea for custom recipes that fit in 3x3 such as botania
        //  not needed either -> the 3x3 check handles anything that is invalid and still registers as crafting.
//        if (!(recipe instanceof ShapedRecipe) && !(recipe instanceof ShapelessRecipe)) {
//            return this.helper.createUserErrorWithTooltip(I18n.format("jei." + Occultism.MODID + "error.invalid_type"));
//        }

        //if recipe is in recipe manager send by id, otherwise fallback to ingredient list
        if (player.getEntityWorld().getRecipeManager().getRecipe(recipe.getId()).isPresent()) {
            OccultismPackets.sendToServer(new MessageSetRecipeByID(recipe.getId()));
        }
        else {
            OccultismPackets.sendToServer(new MessageSetRecipe(this.recipeToTag(container, recipeLayout)));
        }

        return null;
    }
    //endregion Overrides


    //region Methods
    public CompoundNBT recipeToTag(Container container, IRecipeLayout recipeLayout) {
        CompoundNBT nbt = new CompoundNBT();
        Map<Integer, ? extends IGuiIngredient<ItemStack>> inputs = recipeLayout.getItemStacks().getGuiIngredients();

        for (Slot slot : container.inventorySlots) {
            if (slot.inventory instanceof CraftingInventory) {

                //get ingredient from recipe layout
                IGuiIngredient<ItemStack> ingredient = inputs.get(slot.getSlotIndex() + 1);
                if (ingredient == null) {
                    continue;
                }

                //gets all items matching ingredients.
                List<ItemStack> possibleItems = ingredient.getAllIngredients();
                if (possibleItems.isEmpty()) {
                    continue;
                }

                ListNBT invList = new ListNBT();
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
    //endregion Methods
}
