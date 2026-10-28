// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.mojang.authlib.GameProfile;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

import java.util.UUID;

/**
 * Creates mock server players with a bare (embedded) connection but without
 * going through the login flow.
 *
 * <p>Unlike {@link GameTestHelper#makeMockServerPlayer(GameType)} the player
 * has a connection, so server -&gt; client packets (effects, advancements,
 * stats) do not NPE. Unlike
 * {@link GameTestHelper#makeMockServerPlayerInLevel()} the player is never
 * added to the player list, so no login events (and third-party login sync)
 * fire.</p>
 */
public class MockPlayerHelper {

    public static ServerPlayer makeConnectedMockServerPlayer(GameTestHelper helper, GameType gameType) {
        var level = helper.getLevel();
        var player = new ServerPlayer(level.getServer(), level,
                new GameProfile(UUID.randomUUID(), "test-mock-player"), ClientInformation.createDefault()) {
            @Override
            public GameType gameMode() {
                return gameType;
            }

            @Override
            public boolean isClientAuthoritative() {
                return false;
            }
        };
        gameType.updatePlayerAbilities(player.getAbilities());
        var connection = new Connection(PacketFlow.SERVERBOUND);
        new EmbeddedChannel(connection);
        // Pretend a fully negotiated NeoForge connection so validated
        // packets (e.g. advancement-triggered mod syncs) may be sent.
        NetworkRegistry.configureMockConnection(connection);
        player.connection = new ServerGamePacketListenerImpl(level.getServer(), connection, player,
                CommonListenerCookie.createInitial(player.getGameProfile(), false));
        player.setGameMode(gameType);
        return player;
    }
}
