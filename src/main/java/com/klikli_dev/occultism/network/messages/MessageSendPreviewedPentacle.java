package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.theurgy.Theurgy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Rotation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

public class MessageSendPreviewedPentacle implements IMessage {

    public static final Type<MessageSendPreviewedPentacle> TYPE = new Type<>(Theurgy.loc("send_previewed_pentacle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSendPreviewedPentacle> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    m -> m.multiblock,
                    BlockPos.STREAM_CODEC,
                    m -> m.anchor,
                    NeoForgeStreamCodecs.enumCodec(Rotation.class),
                    m -> m.facing,
                    BlockPos.STREAM_CODEC,
                    m -> m.target,
                    MessageSendPreviewedPentacle::new
            );

    public ResourceLocation multiblock;
    public BlockPos anchor;
    public Rotation facing;
    public BlockPos target;

    public MessageSendPreviewedPentacle(ResourceLocation multiblock, BlockPos anchor, Rotation facing, BlockPos target) {
        this.multiblock = multiblock;
        this.anchor = anchor;
        this.facing = facing;
        this.target = target;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        OccultismItems.RITUAL_SATCHEL_T2.get().setTargetPentacle(player.getUUID(), this.multiblock, this.anchor, this.facing, this.target, player.level().getGameTime());
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
