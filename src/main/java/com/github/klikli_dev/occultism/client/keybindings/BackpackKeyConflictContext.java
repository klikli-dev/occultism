package com.github.klikli_dev.occultism.client.keybindings;

import com.github.klikli_dev.occultism.client.gui.storage.SatchelScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;

public class BackpackKeyConflictContext implements IKeyConflictContext {

    //region Fields
    public static final BackpackKeyConflictContext INSTANCE = new BackpackKeyConflictContext();
    //endregion Fields

    //region Overrides
    @Override
    public boolean isActive() {
        return !KeyConflictContext.GUI.isActive() || Minecraft.getInstance().screen instanceof SatchelScreen;
    }

    @Override
    public boolean conflicts(IKeyConflictContext other) {
        return this == other;
    }
    //endregion Overrides

}
