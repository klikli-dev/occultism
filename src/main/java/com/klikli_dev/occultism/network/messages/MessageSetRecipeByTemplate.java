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
//import com.klikli_dev.occultism.integration.emi.impl.EmiHelper; // TODO: re-enable when EMI is available for 26.1
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageSetRecipeByTemplate implements IMessage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "set_recipe_by_template");
    public static final Type<MessageSetRecipeByTemplate> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetRecipeByTemplate> STREAM_CODEC = CustomPacketPayload.codec(MessageSetRecipeByTemplate::encode, MessageSetRecipeByTemplate::new);

    private @Nullable Identifier recipeId;
    private NonNullList<ItemStack> ingredientTemplates;
    private int recipeAmount;

    public MessageSetRecipeByTemplate(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetRecipeByTemplate(@Nullable Identifier recipeId,
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
        int recipeAmount = this.sanitizeRecipeAmount(this.recipeAmount);

        for (int i = 0; i < recipeAmount; i++) {
            boolean anyExtracted = false;
            for (int slot = 0; slot < 9; slot++) {
                var ingredient = ingredients.get(slot);
                if (ingredient.isEmpty()) {
                    continue;
                }

                if (!this.canAcceptIngredient(craftMatrix, slot)) {
                    continue;
                }

                //attempt to get the desired stack from the player inventory
                ItemStack extractedStack = StorageUtil
                        .extractItem(net.neoforged.neoforge.transfer.item.PlayerInventoryWrapper.of(player).getMainSlots(), ingredient,
                                1, true);
                if (this.canAcceptIngredient(craftMatrix, slot, extractedStack)) {
                    extractedStack = StorageUtil.extractItem(net.neoforged.neoforge.transfer.item.PlayerInventoryWrapper.of(player).getMainSlots(), ingredient, 1, false);
                    if (!extractedStack.isEmpty()) {
                        this.placeExtractedStack(craftMatrix, slot, extractedStack);
                        anyExtracted = true;
                        continue;
                    }
                }

                //if we did not find anything in the player inventory, get it from the network now
                extractedStack = storageController.getItemStack(ingredient, 1, true);
                if (this.canAcceptIngredient(craftMatrix, slot, extractedStack)) {
                    extractedStack = storageController.getItemStack(ingredient, 1, false);
                    if (!extractedStack.isEmpty()) {
                        this.placeExtractedStack(craftMatrix, slot, extractedStack);
                        anyExtracted = true;
                    }
                }
            }

            if (!anyExtracted) {
                break;
            }
        }
        //sync to client
        container.updateCraftingSlots(true);
        //finally update controller content for client
        Networking.sendTo(player, storageController.getMessageUpdateStacks());
    }

    private int sanitizeRecipeAmount(int recipeAmount) {
        return Math.max(1, recipeAmount);
    }

    private boolean canAcceptIngredient(CraftingContainer craftMatrix, int slot) {
        var stackInSlot = craftMatrix.getItem(slot);
        return stackInSlot.isEmpty() || stackInSlot.getCount() < stackInSlot.getMaxStackSize();
    }

    private boolean canAcceptIngredient(CraftingContainer craftMatrix, int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        var stackInSlot = craftMatrix.getItem(slot);
        if (ItemStack.isSameItemSameComponents(stackInSlot, stack)) {
            int newCount = stackInSlot.getCount() + stack.getCount();
            return newCount <= stackInSlot.getMaxStackSize();
        }

        return stackInSlot.isEmpty();
    }

    private void placeExtractedStack(CraftingContainer craftMatrix, int slot, ItemStack stack) {
        ItemStack stackInSlot = craftMatrix.getItem(slot);
        if (ItemStack.isSameItemSameComponents(stackInSlot, stack)) {
            stackInSlot.grow(stack.getCount());
        } else {
            craftMatrix.setItem(slot, stack);
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeNullable(this.recipeId, FriendlyByteBuf::writeIdentifier);

        ItemStack.OPTIONAL_LIST_STREAM_CODEC.encode(buf, this.ingredientTemplates);
        buf.writeInt(this.recipeAmount);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.recipeId = buf.readNullable(FriendlyByteBuf::readIdentifier);
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
            // Access via ServerPlayer's level which has getServer()
            ServerPlayer serverPlayer = (ServerPlayer) player;
            var server = serverPlayer.level().getServer();
            if (server != null) {
                var recipeHolder = server.getRecipeManager().byKey(ResourceKey.create(Registries.RECIPE, this.recipeId));
                var recipe = recipeHolder.map(r -> r.value()).orElse(null);
                if (recipe != null) {
                    return StorageUtil.ensure3by3CraftingMatrix(recipe);
                }
            }
        }

        // If the recipe is unavailable for any reason, use the templates provided by the client
        var ingredients = NonNullList.withSize(9, Ingredient.of());
        Preconditions.checkArgument(ingredients.size() == this.ingredientTemplates.size(),
                "Got %d ingredient templates from client, expected %d",
                this.ingredientTemplates.size(), ingredients.size());
        for (int i = 0; i < ingredients.size(); i++) {
            var template = this.ingredientTemplates.get(i);
            if (!template.isEmpty()) {
                ingredients.set(i, Ingredient.of(template.getItem()));
            }
        }

        return ingredients;
    }
}
