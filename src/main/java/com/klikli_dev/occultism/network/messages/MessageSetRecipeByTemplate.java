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
import com.klikli_dev.occultism.integration.emi.impl.StorageControllerEMIRecipeHandler;
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

    public MessageSetRecipeByTemplate(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetRecipeByTemplate(@Nullable ResourceLocation recipeId,
                                      NonNullList<ItemStack> ingredientTemplates) {
        this.recipeId = recipeId;
        this.ingredientTemplates = ingredientTemplates;
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

        for (int slot = 0; slot < 9; slot++) {
            var ingredient = ingredients.get(slot);
            if (ingredient.isEmpty()) {
                continue;
            }

            //attempt to get the desired stack from the player inventory
            ItemStack extractedStack = StorageUtil
                    .extractItem(new PlayerMainInvWrapper(player.getInventory()), ingredient,
                            1, true);
            if (!extractedStack.isEmpty() && craftMatrix.getItem(slot).isEmpty()) {
                //if we found the desired stack, extract it for real and place it in the matrix
                StorageUtil.extractItem(new PlayerMainInvWrapper(player.getInventory()), ingredient, 1, false);
                craftMatrix.setItem(slot, extractedStack);
                continue;
            }

            //if we did not find anything in the player inventory, get it from the network now
            var stack = storageController.getOneOfMostCommonItem(ingredient, false);
            if (!stack.isEmpty() && craftMatrix.getItem(slot).isEmpty()) {
                //if extraction was successful, place it in the matrix
                craftMatrix.setItem(slot, stack);
                continue;
            }

        }
        //sync to client
        container.updateCraftingSlots(true);
        //finally update controller content for client
        Networking.sendTo(player, storageController.getMessageUpdateStacks());
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeNullable(this.recipeId, FriendlyByteBuf::writeResourceLocation);

        ItemStack.OPTIONAL_LIST_STREAM_CODEC.encode(buf, this.ingredientTemplates);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.recipeId = buf.readNullable(FriendlyByteBuf::readResourceLocation);
        this.ingredientTemplates = NonNullList.copyOf(ItemStack.OPTIONAL_LIST_STREAM_CODEC.decode(buf));
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
                return StorageControllerEMIRecipeHandler.ensure3by3CraftingMatrix(recipe.value());
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
}
