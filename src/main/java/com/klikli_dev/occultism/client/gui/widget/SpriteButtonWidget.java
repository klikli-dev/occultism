// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.Objects;
import java.util.function.BiConsumer;

public class SpriteButtonWidget extends AbstractWidget {
    private final int foregroundInsetY;

    private final Runnable onPress;
    private final BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> foregroundRenderer;
    private final IconButtonBackgroundSprites backgroundSprites;

    public SpriteButtonWidget(int x, int y, Component message, Runnable onPress,
                              BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> foregroundRenderer) {
        this(x, y, 12, 12, IconButtonBackgroundSprites.DEFAULT, message, onPress, foregroundRenderer);
    }

    public SpriteButtonWidget(int x, int y, int width, int height, Component message, Runnable onPress,
                              BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> foregroundRenderer) {
        this(x, y, width, height, IconButtonBackgroundSprites.DEFAULT, message, onPress, foregroundRenderer);
    }

    public SpriteButtonWidget(int x, int y, IconButtonBackgroundSprites backgroundSprites, Component message,
                              Runnable onPress, BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> foregroundRenderer) {
        this(x, y, backgroundSprites.normal().width(), backgroundSprites.normal().height(), backgroundSprites, message,
                onPress, foregroundRenderer);
    }

    public SpriteButtonWidget(int x, int y, int width, int height, IconButtonBackgroundSprites backgroundSprites, Component message,
                              Runnable onPress, BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> foregroundRenderer) {
        super(x, y, width, height, message);
        this.backgroundSprites = Objects.requireNonNull(backgroundSprites);
        this.onPress = Objects.requireNonNull(onPress);
        this.foregroundRenderer = Objects.requireNonNull(foregroundRenderer);
        this.foregroundInsetY = height < 18 ? 0 : 1;
    }

    public static BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> text(String text) {
        return (button, graphics) -> {
            Minecraft minecraft = Minecraft.getInstance();
            int x = button.getX() + (button.getWidth() - minecraft.font.width(text)) / 2;
            int y = button.getY() + (button.getHeight() - minecraft.font.lineHeight) / 2 + button.foregroundInsetY;
            graphics.text(minecraft.font, text, x, y, 0xFF000000, false);
        };
    }

    public static BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> sprite(GuiSprite sprite) {
        return (button, graphics) -> {
            int x = button.getX() + (button.getWidth() - sprite.width()) / 2;
            int y = button.getY() + (button.getHeight() - sprite.height()) / 2;
            sprite.extractRenderState(graphics, x, y, sprite.width(), sprite.height());
        };
    }

    public static BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> arrow(boolean down) {
        return (button, graphics) -> {
            graphics.pose().pushMatrix();
            float centerX = button.getX() + button.getWidth() / 2.0F;
            float centerY = button.getY() + button.getHeight() / 2.0F;
            graphics.pose().translate(centerX, centerY);
            graphics.pose().rotate(down ? (float) (Math.PI / 2.0) : (float) (-Math.PI / 2.0));
            graphics.pose().scale(button.getWidth() / 18.0F * 0.6F, button.getHeight() / 18.0F * 0.6F);
            GuiSprites.CRAFTING_ARROW.extractRenderState(graphics,
                    Math.round(-GuiSprites.CRAFTING_ARROW.width() / 2.0F),
                    Math.round(-GuiSprites.CRAFTING_ARROW.height() / 2.0F),
                    GuiSprites.CRAFTING_ARROW.width(), GuiSprites.CRAFTING_ARROW.height());
            graphics.pose().popMatrix();
        };
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.currentSprite(mouseX, mouseY).extractRenderState(guiGraphics, this.getX(), this.getY(), this.getWidth(),
                this.getHeight());
        this.foregroundRenderer.accept(this, guiGraphics);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        if (!this.active) {
            return;
        }

        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.onPress.run();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    private GuiSprite currentSprite(int mouseX, int mouseY) {
        if (!this.active) {
            return this.backgroundSprites.pressed();
        }

        return this.isMouseOver(mouseX, mouseY) ? this.backgroundSprites.hovered() : this.backgroundSprites.normal();
    }
}
