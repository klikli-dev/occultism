package com.klikli_dev.occultism.client.render;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GoldenSacrificialBowlHUD implements LayeredDraw.Layer {
    private static final GoldenSacrificialBowlHUD instance = new GoldenSacrificialBowlHUD();

    public static BlockPos lastHovered = null;
    public static List<MutableComponent> lastPentacles = List.of();
    public static long lastPentacleQueryTime = 0;

    private static MutableComponent noPentacleFound = Component.translatable(TranslationKeys.HUD_NO_PENTACLE_FOUND);

    public static GoldenSacrificialBowlHUD get() {
        return instance;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker) {
        var mc = Minecraft.getInstance();

        if (!(mc.hitResult instanceof BlockHitResult blockHitResult)) {
            lastHovered = null;
            lastPentacles = List.of();
            lastPentacleQueryTime = 0;
            return;
        }

        var pos = blockHitResult.getBlockPos();
        if (!(mc.level.getBlockEntity(pos) instanceof GoldenSacrificialBowlBlockEntity goldenBowl)) {
            lastHovered = null;
            lastPentacles = List.of();
            lastPentacleQueryTime = 0;
            return;
        }

        if (!pos.equals(lastHovered) || lastPentacles.isEmpty()) {
            //only rebuild after one second
            if(!(lastPentacles.isEmpty() && mc.level.getGameTime() - lastPentacleQueryTime < 20)) {
                lastPentacleQueryTime = mc.level.getGameTime();
                lastHovered = pos;

                var pentacles = mc.level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream()
                        //find the pentacles that are valid for the given golden bowl
                        .filter(r -> r.value().getPentacle().validate(mc.level, pos) != null)
                        //then filter out duplicate pentacles, as some recipes share the same pentacle
                        .collect(Collectors.toMap(
                                r -> r.value().getPentacle().getId(), // Use pentacle ID as the key
                                Function.identity(), // Keep the recipe as the value
                                (existing, replacement) -> existing // In case of key collision, keep the existing value
                        ))
                        .values().stream()
                        //now map to pentacle name
                        .map(r -> Component.translatable(Util.makeDescriptionId("multiblock", r.value().getPentacle().getId())))
                        .collect(Collectors.toList());

                lastPentacles = pentacles;
            }
        }

        Font font = mc.font;

        int x = pGuiGraphics.guiWidth() / 2;
        int y = pGuiGraphics.guiHeight() / 2 + 18;

        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();

        if(!lastPentacles.isEmpty()){
            for (var text : lastPentacles) {
                pGuiGraphics.drawCenteredString(font, Component.translatable(TranslationKeys.HUD_PENTACLE_FOUND, text.withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.WHITE), x, y, -1);
                y += 9;
            }
        } else {
            pGuiGraphics.drawCenteredString(font, noPentacleFound.withStyle(ChatFormatting.YELLOW), x, y, -1);
        }

        pose.popPose();
    }
}
