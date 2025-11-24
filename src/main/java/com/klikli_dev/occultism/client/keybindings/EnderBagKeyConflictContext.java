package com.klikli_dev.occultism.client.keybindings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class EnderBagKeyConflictContext implements IKeyConflictContext {

    public static final EnderBagKeyConflictContext INSTANCE = new EnderBagKeyConflictContext();

    @Override
    public boolean isActive() {
        return !KeyConflictContext.GUI.isActive() || Minecraft.getInstance().screen instanceof AbstractContainerScreen<?>;
    }

    @Override
    public boolean conflicts(IKeyConflictContext other) {
        return this == other;
    }

}
