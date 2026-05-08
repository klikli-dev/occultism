/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public record StorageCraftingAreaWidget(
        AbstractWidget clearRecipeButton,
        GuiSprite arrowSprite,
        int arrowX,
        int arrowY) {

    public static StorageCraftingAreaWidget create(
            int controlSize,
            IconButtonBackgroundSprites buttonSprites,
            int clearRecipeButtonX,
            int clearRecipeButtonY,
            Runnable onClearRecipe,
            String translationKeyBase,
            int arrowX,
            int arrowY,
            GuiSprite arrowSprite) {
        AbstractWidget clearRecipeButton = new SpriteButtonWidget(clearRecipeButtonX, clearRecipeButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable(translationKeyBase + ".crafting.clear"),
                onClearRecipe,
                SpriteButtonWidget.offsetText("X", 0.5F, -0.5F));

        return new StorageCraftingAreaWidget(clearRecipeButton, arrowSprite, arrowX, arrowY);
    }

    public void addTo(Consumer<AbstractWidget> adder) {
        adder.accept(this.clearRecipeButton);
    }
}
