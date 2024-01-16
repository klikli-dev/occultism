package com.klikli_dev.occultism.client.keybindings;

import com.klikli_dev.occultism.client.gui.storage.SatchelScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class BackpackKeyConflictContext implements IKeyConflictContext {

    public static final BackpackKeyConflictContext INSTANCE = new BackpackKeyConflictContext();

    @Override
    public boolean isActive() {
        return !KeyConflictContext.GUI.isActive() || Minecraft.getInstance().screen instanceof SatchelScreen;
    }

    @Override
    public boolean conflicts(IKeyConflictContext other) {
        return this == other;
    }

}
