package com.klikli_dev.occultism.client.misc;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientPentacleManager {
    public static BlockPos lastHovered = null;
    public static List<MutableComponent> lastPentacles = List.of();
    public static long lastPentacleQueryTime = 0;

    public static MutableComponent noPentacleFound = Component.translatable(TranslationKeys.HUD_NO_PENTACLE_FOUND);

    public static void reset() {
        lastHovered = null;
        lastPentacles = List.of();
        lastPentacleQueryTime = 0;
    }

    public static void rebuild(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
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
    }
}
