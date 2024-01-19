package com.klikli_dev.occultism.client.keybindings;

import com.klikli_dev.occultism.client.gui.storage.StorageControllerGuiBase;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class StorageRemoteKeyConflictContext implements IKeyConflictContext {

    public static final StorageRemoteKeyConflictContext INSTANCE = new StorageRemoteKeyConflictContext();

    @Override
    public boolean isActive() {
        return !KeyConflictContext.GUI.isActive() || Minecraft.getInstance().screen instanceof StorageControllerGuiBase;
    }

    @Override
    public boolean conflicts(IKeyConflictContext other) {
        return this == other;
    }

}
