package com.klikli_dev.occultism.client.render;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.client.misc.ClientPentacleManager;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.ModList;
import snownee.jade.Jade;

public class GoldenSacrificialBowlHUD implements LayeredDraw.Layer {
    private static final GoldenSacrificialBowlHUD instance = new GoldenSacrificialBowlHUD();

    public static GoldenSacrificialBowlHUD get() {
        return instance;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker) {
        var mc = Minecraft.getInstance();

        if (!(mc.hitResult instanceof BlockHitResult blockHitResult)) {
            ClientPentacleManager.reset();
            return;
        }

        var pos = blockHitResult.getBlockPos();
        if (!(mc.level.getBlockEntity(pos) instanceof GoldenSacrificialBowlBlockEntity goldenBowl)) {
            ClientPentacleManager.reset();
            return;
        }

        ClientPentacleManager.rebuild(pos);
        if(!ModList.get().isLoaded("jade")) {
            Font font = mc.font;

            int x = pGuiGraphics.guiWidth() / 2;
            int y = pGuiGraphics.guiHeight() / 2 + 18;

            PoseStack pose = pGuiGraphics.pose();
            pose.pushPose();

            if (!ClientPentacleManager.lastPentacles.isEmpty()) {
                for (var text : ClientPentacleManager.lastPentacles) {
                    pGuiGraphics.drawCenteredString(font, text, x, y, -1);
                    y += 9;
                }
            } else {
                pGuiGraphics.drawCenteredString(font, ClientPentacleManager.noPentacleFound.withStyle(ChatFormatting.YELLOW), x, y, -1);
            }

            pose.popPose();
        }
    }
}
