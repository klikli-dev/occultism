/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import com.klikli_dev.occultism.common.container.storage.layout.StorageMenuLayout;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public record StorageCraftingAreaWidget(
        AbstractWidget clearRecipeButton) {

    public static StorageCraftingAreaWidget create(
            int controlSize,
            IconButtonBackgroundSprites buttonSprites,
            int clearRecipeButtonX,
            int clearRecipeButtonY,
            Runnable onClearRecipe,
            String translationKeyBase) {
        AbstractWidget clearRecipeButton = new SpriteButtonWidget(clearRecipeButtonX, clearRecipeButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable(translationKeyBase + ".crafting.clear"),
                onClearRecipe,
                SpriteButtonWidget.offsetText("X", 0.5F, -0.5F));

        return new StorageCraftingAreaWidget(clearRecipeButton);
    }

    public void addTo(Consumer<AbstractWidget> adder) {
        adder.accept(this.clearRecipeButton);
    }

    public static void registerResolvers(LayoutResolverRegistry registry, GuiHost host, GuiSprite arrowSprite,
                                         java.util.function.IntFunction<GuiSprite> slotSpriteResolver,
                                         GuiSprite orderSlotBackgroundSprite,
                                         GuiSprite orderSlotSprite,
                                         GuiSprite orderSlotOverlaySprite) {
        registry.resolve("frame.menu.crafting_arrow", ctx -> ctx.addWidget(new GuiSpriteWidget(
                ctx.node().x(),
                ctx.node().y(),
                arrowSprite
        )));

        registry.add("frame.menu.crafting.output", 25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                ctx.node().x() + StorageMenuLayout.OUTPUT_SLOT_VISUAL_OFFSET,
                ctx.node().y() + StorageMenuLayout.OUTPUT_SLOT_VISUAL_OFFSET,
                slotSpriteResolver.apply(0)
        )));

        for (int slot = 0; slot < 9; slot++) {
            int slotIndex = slot;
            registry.add("frame.menu.crafting.grid.slot_" + slot, 25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    ctx.node().x() + StorageMenuLayout.CRAFTING_GRID_SLOT_VISUAL_OFFSET,
                    ctx.node().y() + StorageMenuLayout.CRAFTING_GRID_SLOT_VISUAL_OFFSET,
                    slotSpriteResolver.apply(slotIndex + 1)
            )));
        }

        registry.resolve("frame.menu.order.slot_background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                host,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                orderSlotBackgroundSprite
        )));
        registry.add("frame.menu.order.slot", 25, ctx -> {
            ctx.addWidget(new GuiSpriteWidget(ctx.node().x(), ctx.node().y(), orderSlotSprite));
            ctx.addWidget(new GuiSpriteWidget(
                    ctx.node().x() + StorageMenuLayout.ORDER_SLOT_OVERLAY_OFFSET,
                    ctx.node().y() + StorageMenuLayout.ORDER_SLOT_OVERLAY_OFFSET,
                    orderSlotOverlaySprite
            ));
        });
    }
}
