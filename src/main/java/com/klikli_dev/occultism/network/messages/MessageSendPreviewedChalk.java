package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.theurgy.Theurgy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MessageSendPreviewedChalk implements IMessage {

    public static final Type<MessageSendPreviewedChalk> TYPE = new Type<>(Theurgy.loc("send_previewed_chalk"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSendPreviewedChalk> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.holderRegistry(Registries.BLOCK),
                    m -> m.glyphBlock,
                    BlockPos.STREAM_CODEC,
                    m -> m.target,
                    MessageSendPreviewedChalk::new
            );

    public Holder<Block> glyphBlock;
    public BlockPos target;

    public MessageSendPreviewedChalk(Holder<Block> glyphBlock, BlockPos target) {
        this.glyphBlock = glyphBlock;
        this.target = target;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        OccultismItems.RITUAL_SATCHEL_T1.get().setTargetChalk(player.getUUID(), this.glyphBlock, this.target, player.level().getGameTime());
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        //noop -> stream codec handles this
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        //noop -> stream codec handles this
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
