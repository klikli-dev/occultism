package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class OccultismDataStorage {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Occultism.MODID);

    public static final Supplier<AttachmentType<Integer>> DOUBLE_JUMP = ATTACHMENT_TYPES.register(
            "double_jump", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<FamiliarSettingsData>> FAMILIAR_SETTINGS = ATTACHMENT_TYPES.register(
            "familiar_settings", () -> AttachmentType.serializable(FamiliarSettingsData::new).build());

    public static void onPlayerClone(final PlayerEvent.Clone event) {
        //only handle respawn after death -> not portal transfers
        if (event.isWasDeath() && event.getOriginal().hasData(FAMILIAR_SETTINGS)) {
            event.getEntity().getData(FAMILIAR_SETTINGS).clone(event.getOriginal().getData(FAMILIAR_SETTINGS));
        }
    }

    public static void onJoinWorld(final EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            FamiliarSettingsData.syncFor(player);
        }
    }

}
