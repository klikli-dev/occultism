package com.klikli_dev.occultism.client.misc;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientPentacleManager {
    public static BlockPos lastHovered = null;
    public static List<MutableComponent> lastPentacles = List.of();
    public static List<MutableComponent> allPentacles = List.of();
    public static long lastPentacleQueryTime = 0;
    public static long lastPageChangeTime = 0;
    public static final int ITEMS_PER_PAGE = Occultism.CLIENT_CONFIG.misc.pentagramInBowlInfoCount.getAsInt(); // Number of items per page
    public static final int PAGE_CHANGE_TICKS = Occultism.CLIENT_CONFIG.misc.pentagramInBowlInfoTicks.getAsInt(); // Time in ticks between page changes

    public static MutableComponent noPentacleFound = Component.translatable(TranslationKeys.HUD_NO_PENTACLE_FOUND);
    private static int currentPage = 0;
    private static int totalPages = 0;
    public static MutableComponent pageIndicator = null;

    public static void reset() {
        lastHovered = null;
        lastPentacles = List.of();
        allPentacles = List.of();
        lastPentacleQueryTime = 0;
        lastPageChangeTime = 0;
        currentPage = 0;
        totalPages = 0;
        pageIndicator = null;
    }

    public static void rebuild(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (!pos.equals(lastHovered) || allPentacles.isEmpty()) {
            if(!(allPentacles.isEmpty() && mc.level.getGameTime() - lastPentacleQueryTime < 20)) {
                lastPentacleQueryTime = mc.level.getGameTime();
                lastHovered = pos;
                allPentacles = mc.level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream()
                        .filter(r -> r.value().getPentacle().validate(mc.level, pos) != null)
                        .collect(Collectors.toMap(
                                r -> r.value().getPentacle().getId(),
                                Function.identity(),
                                (existing, replacement) -> existing
                        ))
                        .values().stream()
                        .map(r -> Component.translatable(TranslationKeys.HUD_PENTACLE_FOUND,Component.translatable(Util.makeDescriptionId("multiblock", r.value().getPentacle().getId())).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.WHITE)))
                        .collect(Collectors.toList());
                currentPage = 0;
                calculateTotalPages();
                updateDisplayedPentacles();
            }
        } else {
            long currentTime = mc.level.getGameTime();
            if (totalPages > 1 && currentTime - lastPageChangeTime >= PAGE_CHANGE_TICKS) {
                lastPageChangeTime = currentTime;
                changePage();
            }
        }
    }

    private static void calculateTotalPages() {
        totalPages = (int) Math.ceil((double) allPentacles.size() / ITEMS_PER_PAGE);
        if (totalPages == 0) totalPages = 1; // At least one page even if empty
    }

    private static void changePage() {
        if (totalPages <= 1) return;

        // Cycle through pages sequentially
        currentPage = (currentPage + 1) % totalPages;
        updateDisplayedPentacles();
    }

    private static void updateDisplayedPentacles() {
        if (allPentacles.isEmpty()) {
            lastPentacles = List.of();
            pageIndicator = null;
            return;
        }
        int startIdx = currentPage * ITEMS_PER_PAGE;
        int endIdx = Math.min(startIdx + ITEMS_PER_PAGE, allPentacles.size());
        if (totalPages > 1) {
            pageIndicator = Component.literal("Page ")
                    .withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(String.valueOf(currentPage + 1))
                            .withStyle(ChatFormatting.YELLOW))
                    .append(Component.literal("/")
                            .withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(String.valueOf(totalPages))
                            .withStyle(ChatFormatting.YELLOW));
        } else {
            pageIndicator = null;
        }

        List<MutableComponent> displayedPentacles = new ArrayList<>();
        if (startIdx < allPentacles.size()) {
            MutableComponent firstItem = allPentacles.get(startIdx).copy();

            displayedPentacles.add(firstItem);
        }
        for (int i = startIdx + 1; i < endIdx; i++) {
            displayedPentacles.add(allPentacles.get(i).copy());
        }

        if (totalPages > 1) {
            if (pageIndicator != null) {
                displayedPentacles.add(pageIndicator);
            }
        }

        lastPentacles = displayedPentacles;
    }
}