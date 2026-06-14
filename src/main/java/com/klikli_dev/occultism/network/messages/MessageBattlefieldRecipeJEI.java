package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.integration.jei.impl.JeiPlugin;
import com.klikli_dev.occultism.integration.jei.impl.recipes.BattlefieldRecipeJEI;
import com.klikli_dev.occultism.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

// Based on https://github.com/CyclopsMC/EvilCraft-Compat/blob/master-26/src/main/java/org/cyclops/evilcraftcompat/modcompat/jei/JeiModCompatLoader.java
public class MessageBattlefieldRecipeJEI implements IMessage {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "battlefield_jei");
    public static final Type<MessageBattlefieldRecipeJEI> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageBattlefieldRecipeJEI> STREAM_CODEC = CustomPacketPayload.codec(MessageBattlefieldRecipeJEI::encode, MessageBattlefieldRecipeJEI::new);
    public BattlefieldRecipeJEI recipes;
    public int size;


    public MessageBattlefieldRecipeJEI(BattlefieldRecipeJEI recipe, int i) {
        this.recipes = recipe;
        this.size = i;
    }

    public MessageBattlefieldRecipeJEI(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        JeiPlugin.receiveSpiritFurnaceRecipeOnClient(recipes, size);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        BattlefieldRecipeJEI.encode(recipes, buf);
        buf.writeInt(size);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.recipes = BattlefieldRecipeJEI.decode(buf);
        this.size = buf.readInt();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
