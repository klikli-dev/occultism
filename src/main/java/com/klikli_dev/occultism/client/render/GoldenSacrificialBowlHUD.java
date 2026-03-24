package com.klikli_dev.occultism.client.render;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.client.misc.ClientPentacleManager;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
// import com.klikli_dev.occultism.integration.waila.WailaIntegration; // WAILA integration disabled
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class GoldenSacrificialBowlHUD {
    private static final GoldenSacrificialBowlHUD instance = new GoldenSacrificialBowlHUD();

    public static GoldenSacrificialBowlHUD get() {
        return instance;
    }

    public void render(@NotNull GuiGraphics pGuiGraphics, @NotNull DeltaTracker pDeltaTracker) {
        var mc = Minecraft.getInstance();

        if (!(mc.hitResult instanceof BlockHitResult blockHitResult)) {
            ClientPentacleManager.reset();
            return;
        }

        var pos = blockHitResult.getBlockPos();
        if (mc.level != null && !(mc.level.getBlockEntity(pos) instanceof GoldenSacrificialBowlBlockEntity)) {
            ClientPentacleManager.reset();
            return;
        }

        ClientPentacleManager.rebuild(pos);
        // WailaIntegration.displayPentacles() check removed — WAILA integration is disabled
        if(mc.level.getBlockEntity(pos) instanceof GoldenSacrificialBowlBlockEntity bowl) {
            Font font = mc.font;

            int x = pGuiGraphics.guiWidth() / 2;
            int y = pGuiGraphics.guiHeight() / 2 + 9;

            PoseStack pose = pGuiGraphics.pose();
            pose.pushPose();

            if (bowl.ritualActive) {
                String ritualID = I18n.get("item.occultism.ritual_dummy." + bowl.getCurrentRitualRecipe().id().getPath().substring(7));
                String ritualName = Component.translatable(ritualID).getString();
                int i = ritualName.indexOf(":");
                pGuiGraphics.drawCenteredString(font, Component.translatable("occultism.waila.current_ritual",
                        Component.literal(ritualName.substring(i+2))), x, y,
                        bowl.getSignal() == 8 ? ChatFormatting.GREEN.getColor() : ChatFormatting.GRAY.getColor());
                y += 9;
                if (!bowl.sacrificeFulfilled()) {
                    pGuiGraphics.drawCenteredString(font, Component.translatable("occultism.waila.no_sacrifice"), x, y, ChatFormatting.RED.getColor());
                    y += 9;
                    pGuiGraphics.drawCenteredString(font, Component.translatable(bowl.currentRitualRecipe.value().getEntityToSacrificeDisplayName()), x, y, -1);
                }
                if (!bowl.itemUseFulfilled()) {
                    pGuiGraphics.drawCenteredString(font, Component.translatable("ritual.occultism.use_item"), x, y, -1);
                    ItemStack[] stacks = bowl.currentRitualRecipe.value().getItemToUse().getItems();
                    if (stacks.length > 0) {
                        y += 9;
                        int index = stacks.length == 1 ? 0 : (int) (System.currentTimeMillis() / 1000) % stacks.length;
                        pGuiGraphics.drawCenteredString(font, Component.translatable(stacks[index].getDescriptionId()), x, y, -1);
                    }
                }
            } else {
                if (!ClientPentacleManager.lastPentacles.isEmpty()) {
                    pGuiGraphics.drawCenteredString(font, Component.translatable(TranslationKeys.HUD_PENTACLE_FOUND), x, y, ChatFormatting.GOLD.getColor());
                    y += 9;
                    for (var text : ClientPentacleManager.lastPentacles) {
                        pGuiGraphics.drawCenteredString(font, text, x, y, -1);
                        y += 9;
                    }
                } else {
                    pGuiGraphics.drawCenteredString(font, ClientPentacleManager.noPentacleFound.withStyle(ChatFormatting.YELLOW), x, y, -1);
                }
            }

            pose.popPose();
        }
    }
}
