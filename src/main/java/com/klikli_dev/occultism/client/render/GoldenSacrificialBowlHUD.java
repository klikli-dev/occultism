package com.klikli_dev.occultism.client.render;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.client.misc.ClientPentacleManager;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.integration.jade.JadeIntegration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

public class GoldenSacrificialBowlHUD implements GuiLayer {
    private static final GoldenSacrificialBowlHUD instance = new GoldenSacrificialBowlHUD();

    public static GoldenSacrificialBowlHUD get() {
        return instance;
    }

    @Override
    public void render(@NotNull GuiGraphicsExtractor pGuiGraphics, @NotNull DeltaTracker pDeltaTracker) {
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
        if (JadeIntegration.displayPentacles() && mc.level.getBlockEntity(pos) instanceof GoldenSacrificialBowlBlockEntity bowl) {
            Font font = mc.font;

            int x = pGuiGraphics.guiWidth() / 2;
            int y = pGuiGraphics.guiHeight() / 2 + 9;

            if (bowl.ritualActive) {
                String ritualName = bowl.getCurrentRitualRecipe().value().getRitualDummy().getHoverName().getString();

                int i = Math.max(ritualName.indexOf(":"), ritualName.indexOf("："));
                pGuiGraphics.centeredText(font, Component.translatable("occultism.jade.current_ritual",
                                Component.literal(ritualName.substring(i + 2))), x, y,
                        bowl.getSignal() == 8 ? 0xFF000000 + TextColor.GREEN.getValue() : 0xFF000000 + TextColor.GRAY.getValue());
                y += 9;
                if (!bowl.sacrificeFulfilled()) {
                    pGuiGraphics.centeredText(font, Component.translatable("occultism.jade.no_sacrifice"), x, y, 0xFF000000 + TextColor.RED.getValue());
                    y += 9;
                    pGuiGraphics.centeredText(font, Component.translatable(bowl.currentRitualRecipe.value().getEntityToSacrificeDisplayName()), x, y, -1);
                }
                if (!bowl.itemUseFulfilled()) {
                    pGuiGraphics.centeredText(font, Component.translatable("occultism.jade.no_item_use"), x, y, 0xFF000000 + TextColor.RED.getValue());
                    var itemToUse = bowl.currentRitualRecipe.value().getItemToUse();
                    ItemStack[] stacks = itemToUse != null ? itemToUse.items().map(holder -> new ItemStack(holder.value())).toArray(ItemStack[]::new) : new ItemStack[0];
                    if (stacks.length > 0) {
                        y += 9;
                        int index = stacks.length == 1 ? 0 : (int) (System.currentTimeMillis() / 1000) % stacks.length;
                        pGuiGraphics.centeredText(font, Component.translatable(stacks[index].getItem().getDescriptionId()), x, y, -1);
                    }
                }
            } else {
                if (!ClientPentacleManager.lastPentacles.isEmpty()) {
                    pGuiGraphics.centeredText(font, Component.translatable(TranslationKeys.HUD_PENTACLE_FOUND), x, y,  0xFF000000 + TextColor.GOLD.getValue());
                    y += 9;
                    for (var text : ClientPentacleManager.lastPentacles) {
                        pGuiGraphics.centeredText(font, text, x, y, -1);
                        y += 9;
                    }
                } else {
                    pGuiGraphics.centeredText(font, ClientPentacleManager.noPentacleFound.withStyle(ChatFormatting.YELLOW), x, y, -1);
                }
            }
        }
    }
}
