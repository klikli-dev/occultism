package com.klikli_dev.occultism.integration.emi.render;

import com.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EntityType;

import java.lang.ref.WeakReference;


public class SpiritRenderable<T extends SpiritEntity> implements EmiRenderable {
    WeakReference<T> spiritEntity;
    EntityType<T> spiritType;

    public SpiritRenderable(EntityType<T> spiritType) {
        this.spiritType = spiritType;
    }

    @Override
    public void render(GuiGraphics draw, int x, int y, float delta) {
        if (this.spiritEntity == null || this.spiritEntity.get() == null)
            this.spiritEntity = new WeakReference<>(this.spiritType.create(Minecraft.getInstance().level));


        SpiritGui.drawEntityToGui(draw, x + 8, (int) (y + (this.spiritEntity.get().getEyeHeight() * 15) + 5), 15, 1, 1, this.spiritEntity.get());
    }
}
