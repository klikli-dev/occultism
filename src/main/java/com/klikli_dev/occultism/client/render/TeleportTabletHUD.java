package com.klikli_dev.occultism.client.render;

import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

public class TeleportTabletHUD  implements GuiLayer {
    private static final TeleportTabletHUD instance = new TeleportTabletHUD();

    public static TeleportTabletHUD get() {
        return instance;
    }

    @Override
    public void render(@NotNull GuiGraphicsExtractor pGuiGraphics, @NotNull DeltaTracker pDeltaTracker) {
        var mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null)
            return;

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!stack.is(OccultismItems.WORMHOLE_TABLET)) {
            stack = player.getItemInHand(InteractionHand.OFF_HAND);
            if (!stack.is(OccultismItems.WORMHOLE_TABLET))
                return;
        }
        if (!stack.has(DataComponents.CONTAINER))
            return;

        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        if (contents != ItemContainerContents.EMPTY) {
            ItemStack compass = contents.getStackInSlot(0);
            if (!compass.isEmpty())
                pGuiGraphics.centeredText(mc.font, compass.getDisplayName(),
                        pGuiGraphics.guiWidth() / 2,
                        pGuiGraphics.guiHeight() / 2 + 8,
                        -1);
        }
    }
}
