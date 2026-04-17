package com.klikli_dev.occultism.integration.emi.impl.render;

import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetTooltipHolder;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

import java.util.List;
import java.util.function.BiFunction;

public class ItemWidget extends Widget  implements WidgetTooltipHolder<ItemWidget> {
    private final int x;
    private final int y;
    private final EmiStack stack;
    private BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier = (mouseX, mouseY) -> List.of();
    public ItemWidget(EmiStack stack,int x, int y) {
        super();
        this.x=x;
        this.y=y;
        this.stack = stack;
    }



    @Override
    public Bounds getBounds() {
        return new Bounds(this.x, this.y,16,16);
    }

    @Override
    public void render(GuiGraphicsExtractor draw, int mouseX, int mouseY, float delta) {
        this.stack.render(draw, this.x, this.y, 16);
    }

    @Override
    public ItemWidget tooltip(BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier) {
        this.tooltipSupplier= tooltipSupplier;
        return this;
    }

    @Override
    public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
        return this.tooltipSupplier.apply(mouseX, mouseY);
    }
}