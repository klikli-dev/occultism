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

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.TextUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class SpiritGui<T extends SpiritContainer> extends AbstractContainerScreen<T> {

    protected static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected SpiritEntity spirit;
    protected T container;

    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        super(container, playerInventory, titleIn);
        this.container = container;
        this.spirit = this.container.spirit;
        this.imageWidth = 175;
        this.imageHeight = 165;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public static void drawEntityToGui(GuiGraphics guiGraphics, int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity entity) {
        var poseStack = guiGraphics.pose();
        //From inventory screen
        float f = (float) Math.atan(mouseX / 40.0F);
        float f1 = (float) Math.atan(mouseY / 40.0F);
        poseStack.pushPose();
        poseStack.translate((float) posX, (float) posY, 1050.0F);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        poseStack.translate(0.0D, 0.0D, 1000.0D);
        poseStack.scale((float) scale, (float) scale, (float) scale);
        var quaternion = Axis.ZP.rotationDegrees(180.0F);
        var quaternion1 = Axis.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        poseStack.mulPose(quaternion);
        float f2 = entity.yBodyRot;
        float f3 = entity.yRotO;
        float f4 = entity.xRotO;
        float f5 = entity.yHeadRotO;
        float f6 = entity.yHeadRot;
        entity.yBodyRot = 180.0F + f * 20.0F;
        entity.yRotO = 180.0F + f * 40.0F;
        entity.xRotO = -f1 * 20.0F;
        entity.yHeadRot = entity.yRotO;
        entity.yHeadRotO = entity.yRotO;
        EntityRenderDispatcher entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conjugate();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        entityrenderermanager.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, bufferSource, 15728880);
        bufferSource.endBatch();
        entityrenderermanager.setRenderShadow(true);
        entity.yBodyRot = f2;
        entity.yRotO = f3;
        entity.xRotO = f4;
        entity.yHeadRotO = f5;
        entity.yHeadRot = f6;
        poseStack.popPose();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void init() {
        super.init();
        this.clearWidgets();

        int labelHeight = 9;
        LabelWidget nameLabel = new LabelWidget(this.leftPos + 65, this.topPos + 17, false, -1, 2, 0x404040);
        nameLabel.addLine(TextUtil.formatDemonName(this.spirit.getName().getString()));
        this.addRenderableWidget(nameLabel);

        if (this.spirit.getSpiritMaxAge() >= 0) {
            int agePercent = (int) Math.floor(this.spirit.getSpiritAge() / (float) this.spirit.getSpiritMaxAge() * 100);
            LabelWidget ageLabel = new LabelWidget(this.leftPos + 65, this.topPos + 17 + labelHeight + 5, false, -1, 2, 0x404040);
            ageLabel.addLine(I18n.get(TRANSLATION_KEY_BASE + ".age", agePercent));
            this.addRenderableWidget(ageLabel);
        }

        String jobID = this.spirit.getJobID();
        if (!StringUtils.isBlank(jobID)) {
            jobID = jobID.replace(":", ".");
            LabelWidget jobLabel = new LabelWidget(this.leftPos + 65,
                    this.topPos + 17 + labelHeight + 5 + labelHeight + 5 + 5, false, -1, 2, 0x404040);

            String jobText = I18n.get(TRANSLATION_KEY_BASE + ".job", I18n.get("job." + jobID));
            String[] lines = WordUtils.wrap(jobText, 15, "\n", true).split("[\\r\\n]+", 2);
            for (String line : lines)
                jobLabel.addLine(ChatFormatting.ITALIC + line + ChatFormatting.RESET);
            this.addRenderableWidget(jobLabel);

        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
//        this.renderBackground(guiGraphics); //called by super

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        guiGraphics.pose().pushPose();
        int scale = 30;
        drawEntityToGui(guiGraphics, this.leftPos + 35, this.topPos + 65, scale, this.leftPos + 51 - x,
                this.topPos + 75 - 50 - y, this.spirit);
        guiGraphics.pose().popPose();
    }
}
