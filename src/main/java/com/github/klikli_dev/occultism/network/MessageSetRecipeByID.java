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
package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.util.StorageUtil;
import com.google.common.base.Preconditions;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */
public class MessageSetRecipeByID extends MessageBase {

    //region Fields
    private ResourceLocation id;
    //endregion Fields

    //region Initialization
    public MessageSetRecipeByID(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetRecipeByID(ResourceLocation id) {
        this.id = id;
    }
    //endregion Initialization

    //region Overrides


    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {
        if (!(player.containerMenu instanceof IStorageControllerContainer)) {
            return;
        }
        IStorageControllerContainer container = (IStorageControllerContainer) player.containerMenu;
        IStorageController storageController = container.getStorageController();
        if (storageController == null) {
            return;
        }

        Recipe<?> recipe = player.getLevel().getRecipeManager().byKey(this.id).orElse(null);
        Preconditions.checkArgument(recipe != null); //should not happen

        StorageUtil.clearOpenCraftingMatrix(player, false);
        CraftingContainer craftMatrix = container.getCraftMatrix();
        NonNullList<Ingredient> ingredients = this.getIngredientsForRecipe(recipe);

        for (int slot = 0; slot < 9; slot++) {
            Ingredient ingredient = ingredients.get(slot);
            ItemStack extractedStack = StorageUtil.extractItem(new PlayerMainInvWrapper(player.getInventory()), ingredient,
                    1, true);

            if (extractedStack != null && !extractedStack.isEmpty() && craftMatrix.getItem(slot).isEmpty()) {
                //if we found the desired stack, extract it for real and place it in the matrix
                StorageUtil.extractItem(new PlayerMainInvWrapper(player.getInventory()), ingredient, 1, false);
                craftMatrix.setItem(slot, extractedStack);
                continue;
            }

            //if we did not find anything in the player inventory, get it from the network now
            extractedStack = storageController.getItemStack(ingredient, 1, false);
            if (!extractedStack.isEmpty() && craftMatrix.getItem(slot).isEmpty()) {
                //if extraction was successful, place it in the matrix
                craftMatrix.setItem(slot, extractedStack);
                continue;
            }
            //endregion fill in recipe
        }
        //sync to client
        container.updateCraftingSlots(true);
        //finally update controller content for client
        OccultismPackets.sendTo(player, storageController.getMessageUpdateStacks());
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.id = buf.readResourceLocation();
    }
    //endregion Overrides

    //region Methods
    private NonNullList<Ingredient> getIngredientsForRecipe(Recipe<?> recipe) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        NonNullList<Ingredient> ingredientsMatrixGrid = NonNullList.withSize(9, Ingredient.EMPTY);

        Preconditions.checkArgument(ingredients.size() <= 9);


        if (recipe instanceof IShapedRecipe) {
            IShapedRecipe<?> shapedRecipe = (IShapedRecipe<?>) recipe;
            int width = shapedRecipe.getRecipeWidth();
            int height = shapedRecipe.getRecipeHeight();
            Preconditions.checkArgument(width <= 3 && height <= 3);

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    int source = w + h * width;
                    int target = w + h * 3;
                    Ingredient i = ingredients.get(source);
                    ingredientsMatrixGrid.set(target, i);
                }
            }
        } else {
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientsMatrixGrid.set(i, ingredients.get(i));
            }
        }

        return ingredientsMatrixGrid;
    }
    //endregion Methods
}
