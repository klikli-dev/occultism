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

package com.github.klikli_dev.occultism.client.gui;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.gui.controls.GuiLabelNoShadow;
import com.github.klikli_dev.occultism.common.container.ContainerSpirit;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.plexus.util.StringUtils;

public class GuiSpirit extends GuiContainer {
    //region Fields
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected EntitySpirit spirit;
    protected ContainerSpirit container;
    //endregion Fields

    //region Initialization
    public GuiSpirit(ContainerSpirit container) {
        super(container);
        this.container = container;
        this.spirit = container.spirit;

        this.xSize = 175;
        this.ySize = 165;

        this.mc = Minecraft.getMinecraft();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void initGui() {
        super.initGui();
        this.labelList.clear();

        int labelHeight = 9;
        GuiLabelNoShadow nameLabel = new GuiLabelNoShadow(this.mc.fontRenderer, 0, this.guiLeft + 65, this.guiTop + 17,
                150, labelHeight, 0x404040);
        nameLabel.addLine(TextUtil.formatDemonName(this.spirit.getName()));
        this.labelList.add(nameLabel);

        int agePercent = (int) Math.floor(this.spirit.getSpiritAge() / (float) this.spirit.getSpiritMaxAge() * 100);
        GuiLabelNoShadow ageLabel = new GuiLabelNoShadow(this.mc.fontRenderer, 0, this.guiLeft + 65,
                this.guiTop + 17 + labelHeight + 5, 150, labelHeight, 0x404040);
        ageLabel.addLineTranslated(I18n.format(TRANSLATION_KEY_BASE + ".age", agePercent));
        this.labelList.add(ageLabel);

        String jobID = this.spirit.getJobID();
        if (!StringUtils.isBlank(jobID)) {
            jobID = jobID.replace(":", ".");
            GuiLabelNoShadow jobLabel = new GuiLabelNoShadow(this.mc.fontRenderer, 0, this.guiLeft + 65,
                    this.guiTop + 17 + labelHeight + 5 + labelHeight + 5 + 5, 150, labelHeight, 0x404040);

            String jobText = I18n.format(TRANSLATION_KEY_BASE + ".job", I18n.format("job." + jobID + ".name"));
            String[] lines = WordUtils.wrap(jobText, 15, "\n", true).split("[\\r\\n]+", 2);
            for (String line : lines)
                jobLabel.addLineTranslated(TextFormatting.ITALIC.toString() + line + TextFormatting.RESET.toString());
            this.labelList.add(jobLabel);

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //draw backdrop
        this.drawDefaultBackground();

        //draw gui texture
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        //draw spirit
        GlStateManager.pushMatrix();
        int scale = 30;
        drawEntityToGui(this.guiLeft + 35, this.guiTop + 65, scale, this.guiLeft + 51 - mouseX,
                this.guiTop + 75 - 50 - mouseY, this.spirit);
        GlStateManager.popMatrix();
    }
    //endregion Overrides

    //region Static Methods
    public static void drawEntityToGui(int posX, int posY, int scale, float mouseX, float mouseY,
                                       EntityLivingBase entity) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, 30);
        GlStateManager.scale((-scale), scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
        entity.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
        entity.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    //endregion Static Methods
}
