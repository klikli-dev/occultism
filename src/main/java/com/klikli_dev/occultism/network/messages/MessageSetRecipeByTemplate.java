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

package com.klikli_dev.occultism.network.messages;

import com.google.common.base.Preconditions;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.integration.emi.impl.EmiHelper;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageSetRecipeByTemplate implements IMessage {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "set_recipe_by_template");
    public static final Type<MessageSetRecipeByTemplate> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetRecipeByTemplate> STREAM_CODEC = CustomPacketPayload.codec(MessageSetRecipeByTemplate::encode, MessageSetRecipeByTemplate::new);

    private @Nullable ResourceLocation recipeId;
    private NonNullList<ItemStack> ingredientTemplates;
    private int recipeAmount;

    public MessageSetRecipeByTemplate(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetRecipeByTemplate(@Nullable ResourceLocation recipeId,
                                      NonNullList<ItemStack> ingredientTemplates, int recipeAmount) {
        this.recipeId = recipeId;
        this.ingredientTemplates = ingredientTemplates;
        this.recipeAmount = recipeAmount;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        if (!(player.containerMenu instanceof IStorageControllerContainer container)) {
            return;
        }
        IStorageController storageController = container.getStorageController();
        if (storageController == null) {
            return;
        }
        //clear the current crafting matrix
        StorageUtil.clearOpenCraftingMatrix(player, false);

        CraftingContainer craftMatrix = container.getCraftMatrix();

        var ingredients = this.getDesiredIngredients(player);
        int recipeAmount = sanitizeRecipeAmount(this.recipeAmount);

        for (int i = 0; i < recipeAmount; i++) {
            boolean extractedAny = false;

            for (int slot = 0; slot < 9; slot++) {
                var ingredient = ingredients.get(slot);
                if (ingredient.isEmpty()) {
                    continue;
                }

                if (!canAcceptIngredient(craftMatrix, slot)) {
                    continue;
                }

                ItemStack extractedStack = StorageUtil
                        .extractItem(new PlayerMainInvWrapper(player.getInventory()), ingredient, 1, true);
                if (canAcceptIngredient(craftMatrix, slot, extractedStack)) {
                    extractedStack = StorageUtil.extractItem(new PlayerMainInvWrapper(player.getInventory()), ingredient, 1, false);
                    if (!extractedStack.isEmpty()) {
                        placeExtractedStack(craftMatrix, slot, extractedStack);
                        extractedAny = true;
                        continue;
                    }
                }

                extractedStack = storageController.getItemStack(ingredient, 1, true);
                if (canAcceptIngredient(craftMatrix, slot, extractedStack)) {
                    extractedStack = storageController.getItemStack(ingredient, 1, false);
                    if (!extractedStack.isEmpty()) {
                        placeExtractedStack(craftMatrix, slot, extractedStack);
                        extractedAny = true;
                    }
                }
            }

            if (!extractedAny) {
                break;
            }
        }

        container.updateCraftingSlots(true);
        Networking.sendTo(player, storageController.getMessageUpdateStacks());
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeNullable(this.recipeId, FriendlyByteBuf::writeResourceLocation);

        ItemStack.OPTIONAL_LIST_STREAM_CODEC.encode(buf, this.ingredientTemplates);
        buf.writeInt(this.recipeAmount);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.recipeId = buf.readNullable(FriendlyByteBuf::readResourceLocation);
        this.ingredientTemplates = NonNullList.copyOf(ItemStack.OPTIONAL_LIST_STREAM_CODEC.decode(buf));
        this.recipeAmount = buf.readInt();
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private NonNullList<Ingredient> getDesiredIngredients(Player player) {
        // Try to retrieve the real recipe on the server-side
        if (this.recipeId != null) {
            var recipe = player.level().getRecipeManager().byKey(this.recipeId).orElse(null);
            if (recipe != null) {
                return EmiHelper.ensure3by3CraftingMatrix(recipe.value());
            }
        }

        // If the recipe is unavailable for any reason, use the templates provided by the client
        var ingredients = NonNullList.withSize(9, Ingredient.EMPTY);
        Preconditions.checkArgument(ingredients.size() == this.ingredientTemplates.size(),
                "Got %d ingredient templates from client, expected %d",
                this.ingredientTemplates.size(), ingredients.size());
        for (int i = 0; i < ingredients.size(); i++) {
            var template = this.ingredientTemplates.get(i);
            if (!template.isEmpty()) {
                ingredients.set(i, Ingredient.of(template));
            }
        }

        return ingredients;
    }

    private static int sanitizeRecipeAmount(int recipeAmount) {
        if (recipeAmount <= 0) {
            return 1;
        }

        return recipeAmount;
    }

    private static boolean canAcceptIngredient(CraftingContainer craftMatrix, int slot) {
        ItemStack currentStack = craftMatrix.getItem(slot);
        return currentStack.isEmpty() || currentStack.getCount() < currentStack.getMaxStackSize();
    }

    private static boolean canAcceptIngredient(CraftingContainer craftMatrix, int slot, ItemStack extractedStack) {
        if (extractedStack.isEmpty()) {
            return false;
        }

        ItemStack currentStack = craftMatrix.getItem(slot);
        if (ItemStack.isSameItemSameComponents(currentStack, extractedStack)) {
            int newCount = currentStack.getCount() + extractedStack.getCount();
            return newCount <= currentStack.getMaxStackSize();
        }

        return currentStack.isEmpty();
    }

    private static void placeExtractedStack(CraftingContainer craftMatrix, int slot, ItemStack extractedStack) {
        ItemStack currentStack = craftMatrix.getItem(slot);
        if (ItemStack.isSameItemSameComponents(currentStack, extractedStack)) {
            currentStack.grow(extractedStack.getCount());
        } else {
            craftMatrix.setItem(slot, extractedStack);
        }
    }
}
