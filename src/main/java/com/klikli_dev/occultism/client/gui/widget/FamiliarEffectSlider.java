package com.klikli_dev.occultism.client.gui.widget;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.IntConsumer;

public class FamiliarEffectSlider extends AbstractWidget {

    private static final int SLIDER_HEIGHT = 20;
    private static final int KNOB_WIDTH = 2;

    private int[] values;
    private final IntConsumer onValueChange;

    private int value;
    private boolean visible;

    public FamiliarEffectSlider(int x, int y, int width, int[] values, int value, IntConsumer onValueChange) {
        super( x, y, width, SLIDER_HEIGHT, Component.empty());

        if (values.length != 3)
            throw new IllegalArgumentException("values must contain exactly 3 values");
        if (values[0] > values[1] || values[1] > values[2])
            throw new IllegalArgumentException("values must be in ascending order");

        this.values = values.clone();
        this.value = Mth.clamp(value, -1, values[2]);
        this.onValueChange = onValueChange;
        this.visible = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = Mth.clamp(value, -1, values[2]);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (!visible)
            return;

        int left = getX();
        int right = getRight();
        int centerY = getY() + SLIDER_HEIGHT / 2;

        // Track
        int trackLeft = left + KNOB_WIDTH / 2;
        int trackRight = right - KNOB_WIDTH / 2;
        graphics.fill(trackLeft - 4, centerY - 3, trackRight + 4, centerY + 3, 0xFF222222);
        graphics.fill(trackLeft - 3, centerY - 2, trackRight + 3, centerY + 2, getTrackColor());
        int maxValue = values[2];
        for (int value = -1; value <= maxValue; value++) {
            int dotX = getValueX(trackLeft, trackRight, value);
            graphics.fill(dotX, centerY, dotX + 1, centerY + 2, 0xFF606060);
        }

        // Knob
        int knobX = getValueX(trackLeft, trackRight);
        int knobLeft = knobX - 3;
        int knobRight = knobX + 3;
        int knobTop = getY() + 2;
        int knobBottom = getY() + SLIDER_HEIGHT - 2;
        graphics.fill(knobLeft, knobTop, knobRight, knobBottom, 0xFF202020);
        graphics.fill(knobLeft + 1, knobTop + 1, knobRight - 1, knobBottom - 1, 0xFF808080);
        graphics.fill(knobLeft + 2, knobTop + 2, knobRight - 2, knobTop + 3, 0xFFC0C0C0);
        graphics.fill(knobLeft + 1, knobTop + 2, knobLeft + 2, knobBottom - 2, 0xFFA8A8A8);
        graphics.fill(knobLeft + 2, knobBottom - 2, knobRight - 2, knobBottom - 1, 0xFF505050);
        graphics.fill(knobRight - 2, knobTop + 2, knobRight - 1, knobBottom - 2, 0xFF505050);
    }

    private int getValueX(int left, int right) {
        int max = values[2];

        if (max <= -1) {
            return left;
        }

        double normalized =
                (double) (value + 1)
                        / (double) (max + 1);

        return left + (int) ((right - left) * normalized);
    }

    private int getValueX(int left, int right, int value) {
        int max = values[2];

        if (max <= -1) {
            return left;
        }

        double normalized =
                (double) (value + 1)
                        / (double) (max + 1);

        return left + (int) ((right - left) * normalized);
    }

    private int getValueFromMouse(double mouseX) {
        int left = getX() + KNOB_WIDTH / 2;
        int right = getRight() - KNOB_WIDTH / 2;

        double normalized =
                Mth.clamp(
                        (mouseX - left) / (double) (right - left),
                        0.0,
                        1.0
                );

        int max = values[2];

        return Mth.clamp(
                (int) Math.round(normalized * (max + 1)) - 1,
                -1,
                max
        );
    }

    private void setValueFromMouse(double mouseX) {
        int newValue = getValueFromMouse(mouseX);

        if (newValue != this.value) {
            this.value = newValue;
            this.onValueChange.accept(newValue);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (!isMouseOver(event.x(), event.y()))
            return false;

        setValueFromMouse(event.x());
        return true;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (!isMouseOver(event.x(), event.y()))
            return false;

        setValueFromMouse(event.x());
        return true;
    }

    private int getTrackColor() {
        if (value == -1)
            return 0xFFFF5555;

        if (value <= values[0]) {
            return 0xFFAAAAAA;
        }

        if (value <= values[1]) {
            return 0xFF00AA00;
        }

        return 0xFF00AAAA;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) {
        narration.add(NarratedElementType.TITLE, Component.literal(Integer.toString(value)));
    }

    public void setValues(int[] values) {
        if (values.length != 3)
            throw new IllegalArgumentException("values must contain exactly 3 values");

        if (values[0] > values[1] || values[1] > values[2])
            throw new IllegalArgumentException("values must be in ascending order");

        this.values = values.clone();
        this.value = Mth.clamp(this.value,-1, this.values[2]);
    }
}
