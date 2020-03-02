/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.client.gui.spirit;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.render.OccultismRenderType;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;

public class GuiSpirit extends ContainerScreen<SpiritContainer> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected SpiritEntity spirit;
    protected SpiritContainer container;

//region Initialization
    public GuiSpirit(SpiritContainer container, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(container, playerInventory, titleIn);
        this.container = container;
        this.spirit = this.container.spirit;

        this.xSize = 175;
        this.ySize = 165;
    }
//endregion Initialization

//region Overrides
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    public static void drawEntityToGui(int posX, int posY, int scale, float mouseX, float mouseY,
                                       LivingEntity entity) {
    //TODO: if this does not work as intended, view InventoryScreen#drawEntityOnScreen which I think renders the player
        RenderSystem.enableColorMaterial();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(posX, posY, 30);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((-scale), scale, scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        matrixstack.rotate(quaternion);

        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        RenderSystem.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        RenderSystem.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        RenderSystem.rotatef(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
        entity.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
        entity.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;

        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        entityrenderermanager.renderEntityStatic(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, buffer, 15728880);
        buffer.finish();
        entityrenderermanager.setRenderShadow(true);

        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        RenderSystem.popMatrix();
        RenderHelper.disableStandardItemLighting();
        RenderSystem.disableRescaleNormal();
    }
//endregion Overrides
}
