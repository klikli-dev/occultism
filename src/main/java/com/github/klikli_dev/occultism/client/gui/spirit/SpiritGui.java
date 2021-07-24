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
import com.github.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.util.TextUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class SpiritGui<T extends SpiritContainer> extends ContainerScreen<T> {
//region Fields
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected SpiritEntity spirit;
    protected T container;
//endregion Fields

    //region Initialization
    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        super(container, playerInventory, titleIn);
        this.container = container;
        this.spirit = this.container.spirit;

        this.xSize = 175;
        this.ySize = 165;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void init() {
        super.init();
        this.buttons.clear();

        int labelHeight = 9;
        LabelWidget nameLabel = new LabelWidget(this.guiLeft + 65, this.guiTop + 17, false, -1, 2, 0x404040);
        nameLabel.addLine(TextUtil.formatDemonName(this.spirit.getName().getString()));
        this.addButton(nameLabel);

        int agePercent = (int) Math.floor(this.spirit.getSpiritAge() / (float) this.spirit.getSpiritMaxAge() * 100);
        LabelWidget ageLabel = new LabelWidget(this.guiLeft + 65, this.guiTop + 17 + labelHeight + 5, false, -1, 2, 0x404040);
        ageLabel.addLine(I18n.format(TRANSLATION_KEY_BASE + ".age", agePercent));
        this.addButton(ageLabel);

        String jobID = this.spirit.getJobID();
        if (!StringUtils.isBlank(jobID)) {
            jobID = jobID.replace(":", ".");
            LabelWidget jobLabel = new LabelWidget(this.guiLeft + 65,
                    this.guiTop + 17 + labelHeight + 5 + labelHeight + 5 + 5, false, -1, 2, 0x404040);

            String jobText = I18n.format(TRANSLATION_KEY_BASE + ".job", I18n.format("job." + jobID));
            String[] lines = WordUtils.wrap(jobText, 15, "\n", true).split("[\\r\\n]+", 2);
            for (String line : lines)
                jobLabel.addLine(TextFormatting.ITALIC + line + TextFormatting.RESET);
            this.addButton(jobLabel);

        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(PoseStack poseStack, float partialTicks, int x, int y) {
        this.renderBackground(poseStack);

        RenderSystem.color4f(1, 1, 1, 1);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        this.blit(poseStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        RenderSystem.pushMatrix();
        int scale = 30;
        drawEntityToGui(this.guiLeft + 35, this.guiTop + 65, scale, this.guiLeft + 51 - x,
                this.guiTop + 75 - 50 - y, this.spirit);
        RenderSystem.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(PoseStack poseStack, int x, int y) {
        //don't call super to avoid drawing names of inventories
    }
    //endregion Overrides

//region Static Methods
    public static void drawEntityToGui(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity entity) {
        //From inventory screen
        float f = (float)Math.atan((double)(mouseX / 40.0F));
        float f1 = (float)Math.atan((double)(mouseY / 40.0F));
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)posX, (float)posY, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        PoseStack matrixstack = new PoseStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float)scale, (float)scale, (float)scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.multiply(quaternion1);
        matrixstack.rotate(quaternion);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        entity.renderYawOffset = 180.0F + f * 20.0F;
        entity.rotationYaw = 180.0F + f * 40.0F;
        entity.rotationPitch = -f1 * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
        quaternion1.conjugate();
        entityrenderermanager.setCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        entityrenderermanager.renderEntityStatic(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        irendertypebuffer$impl.finish();
        entityrenderermanager.setRenderShadow(true);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        RenderSystem.popMatrix();
    }
//endregion Static Methods
}
