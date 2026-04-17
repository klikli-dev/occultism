package com.klikli_dev.occultism.integration.emi.impl.render;

import com.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.function.BiFunction;

public class SpiritWidget<T extends SpiritEntity> extends Widget {

    private final int x;
    private final int y;
    private final int s;
    private T spiritEntity;
    private final EntityType<T> spiritType;
    private BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier = (mouseX, mouseY) -> List.of();
    public SpiritWidget(int x, int y, EntityType<T> spiritType, int s) {
        super();
        this.x=x;
        this.y=y;
        this.s=s;
        this.spiritType=spiritType;

    }
    @Override
    public Bounds getBounds() {
        return new Bounds(this.x -15, 0, 30, 30);
    }

    @Override
    public void render(GuiGraphicsExtractor draw, int mouseX, int mouseY, float delta) {
        if(this.spiritEntity ==null)
            this.spiritEntity = this.spiritType.create(Minecraft.getInstance().level);
        SpiritGui.drawEntityToGui(draw, this.x,(int)(this.y +(this.spiritEntity.getEyeHeight()*15)), this.s,1,1, this.spiritEntity);
    }


    public SpiritWidget tooltip(BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier) {
        this.tooltipSupplier = tooltipSupplier;
        return this;
    }
    @Override
    public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
        return this.tooltipSupplier.apply(mouseX, mouseY);
    }

}