// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe;

import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

public final class OccultismRecipeManagerClient {
    private OccultismRecipeManagerClient() {
    }

    public static void onRecipesReceived(RecipesReceivedEvent event) {
        var manager = OccultismRecipeManager.get();
        manager.clearClientCache();

        for (var type : event.getRecipeTypes()) {
            manager.storeClientRecipesUnchecked(event.getRecipeMap(), type);
        }
    }

    public static void onClientLogout(LoggingOut event) {
        OccultismRecipeManager.get().clearClientCache();
    }
}