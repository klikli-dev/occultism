/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
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

    public static void registerResolvers(LayoutResolverRegistry registry, GuiSprite arrowSprite,
                                        java.util.function.IntFunction<GuiSprite> slotSpriteResolver) {
        registry.resolve("frame.menu.crafting_arrow", ctx -> ctx.addWidget(new GuiSpriteWidget(
                ctx.node().x(),
                ctx.node().y(),
                arrowSprite
        )));

        registry.add("frame.menu.crafting.output", 25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                ctx.node().x() - 5,
                ctx.node().y() - 5,
                slotSpriteResolver.apply(0)
        )));

        for (int slot = 0; slot < 9; slot++) {
            int slotIndex = slot;
            registry.add("frame.menu.crafting.grid.slot_" + slot, 25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    ctx.node().x() - 1,
                    ctx.node().y() - 1,
                    slotSpriteResolver.apply(slotIndex + 1)
            )));
        }
    }
}
