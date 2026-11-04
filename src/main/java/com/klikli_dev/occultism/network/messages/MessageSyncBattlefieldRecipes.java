// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.integration.jei.impl.recipes.BattlefieldRecipeJEI;
import com.klikli_dev.occultism.network.IMessage;
import io.netty.handler.codec.DecoderException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This message sends the server-generated battlefield JEI recipes to the client.
 */
public class MessageSyncBattlefieldRecipes implements IMessage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "sync_battlefield_recipes");
    public static final Type<MessageSyncBattlefieldRecipes> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSyncBattlefieldRecipes> STREAM_CODEC = CustomPacketPayload.codec(MessageSyncBattlefieldRecipes::encode, MessageSyncBattlefieldRecipes::new);

    private List<BattlefieldRecipeJEI> recipes;

    public MessageSyncBattlefieldRecipes(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSyncBattlefieldRecipes(List<BattlefieldRecipeJEI> recipes) {
        this.recipes = recipes;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        BattlefieldRecipeJEI.setClientRecipes(this.recipes);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.recipes.size());
        for (BattlefieldRecipeJEI recipe : this.recipes) {
            BattlefieldRecipeJEI.encode(recipe, buf);
        }
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        int count = buf.readInt();
        if (count < 0 || count > buf.readableBytes() / BattlefieldRecipeJEI.MIN_ENCODED_SIZE) {
            throw new DecoderException("Invalid battlefield recipe count: " + count);
        }
        this.recipes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            this.recipes.add(BattlefieldRecipeJEI.decode(buf));
        }
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
