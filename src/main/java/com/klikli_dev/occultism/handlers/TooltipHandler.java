/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.DimensionalBattlefieldScreen;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT)
public class TooltipHandler {

    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\\\\n|\\R", Pattern.MULTILINE);
    private static final List<String> namespacesToListenFor = new ArrayList<>();

    /**
     * Register a namespace (= mod id) of items to listen for during tooltip handling.
     * Should be called in @{@link net.neoforged.fml.event.lifecycle.FMLClientSetupEvent}
     */
    public static void registerNamespaceToListenTo(String namespace) {
        namespacesToListenFor.add(namespace);
    }

    @SubscribeEvent
    public static void onAddInformation(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof DimensionalBattlefieldScreen && stack.has(OccultismDataComponents.SOUL_VALUE)) {
            event.getToolTip().add(
                Component.translatable("tooltip.occultism.soul_value",
                    stack.getOrDefault(OccultismDataComponents.SOUL_VALUE, 0))
                .withStyle(ChatFormatting.LIGHT_PURPLE));
            event.getToolTip().add(
                    Component.translatable("tooltip.occultism.soul_value_stack",
                                    stack.count() * stack.getOrDefault(OccultismDataComponents.SOUL_VALUE, 0))
                            .withStyle(ChatFormatting.LIGHT_PURPLE));
        }

        if (stack.has(OccultismDataComponents.SPIRIT_NAME)) {
            String translationKey = stack.getItem().getDescriptionId() + ".occultism_spirit_tooltip";

            if (I18n.exists(translationKey))
                event.getToolTip().add(Component.translatable(translationKey,
                        TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
        }

        if (Occultism.CLIENT_CONFIG.visuals.showItemTagsInTooltip.get() && event.getFlags().isAdvanced()) {
            var tooltips = event.getToolTip();
            //Item key is already displayed by the advanced tooltips in the base game.
                // 26.1 removed ItemStack.getItemHolder() and the old tag iteration API
                // Simplified to just show the item key instead of all tags
                //var itemKey = BuiltInRegistries.ITEM.getKey(stack.getItem());
                //tooltips.add(Component.literal(itemKey.toString()).withStyle(ChatFormatting.DARK_GRAY));
            Stream<TagKey<Item>> tagStream = stack.tags();
            tagStream.forEach(tag -> {
                tooltips.add(Component.literal(tag.location().toString()).withStyle(ChatFormatting.DARK_GRAY));
            });
        }

        var namespace = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();

        if (namespacesToListenFor.contains(namespace)) {
            String tooltipKey = stack.getItem().getDescriptionId() + ".auto_tooltip";
            boolean tooltipExists = I18n.exists(tooltipKey);
            if (tooltipExists) {
                var tooltipStyle = Style.EMPTY.withColor(ChatFormatting.GRAY);
                String localizedTooltip = I18n.get(tooltipKey);

                for (String line : NEWLINE_PATTERN.split(localizedTooltip, -1)) {
                    event.getToolTip().add(Component.literal(line).withStyle(tooltipStyle));
                }
            }
        }
    }
}
