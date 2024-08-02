package com.klikli_dev.occultism.integration.emi.impl.render;

import com.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EntityType;


public class SpiritRenderable<T extends SpiritEntity> implements EmiRenderable{
    T spiritEntity;
    EntityType<T> spiritType;
    public SpiritRenderable(EntityType<T> spiritType) {
        this.spiritType=spiritType;
    }

    @Override
    public void render(GuiGraphics draw, int x, int y, float delta) {
        if(spiritEntity==null)
            spiritEntity= (T) spiritType.create(Minecraft.getInstance().level);
        SpiritGui.drawEntityToGui(draw, (int) (x+8),(int)(y+(spiritEntity.getEyeHeight()*15)+5),15,1,1,spiritEntity);
    }
}