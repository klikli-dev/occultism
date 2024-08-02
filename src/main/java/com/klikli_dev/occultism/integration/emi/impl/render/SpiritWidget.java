package com.klikli_dev.occultism.integration.emi.impl.render;

import com.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EntityType;

public class SpiritWidget<T extends SpiritEntity> extends Widget {

    private int x,y;
    private T spiritEntity;
    private EntityType<T> spiritType;
    public SpiritWidget(int x, int y, EntityType<T> spiritType) {
        super();
        this.x=x;
        this.y=y;
        this.spiritType=spiritType;

    }
    @Override
    public Bounds getBounds() {
        return new Bounds(x, y, 20, 20);
    }

    @Override
    public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
        if(spiritEntity==null)
            spiritEntity= spiritType.create(Minecraft.getInstance().level);
        SpiritGui.drawEntityToGui(draw, (int) (x),(int)(y+(spiritEntity.getEyeHeight()*15)+5),15,1,1,spiritEntity);
    }
}