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
import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class SpiritGui<T extends SpiritContainer> extends AbstractContainerScreen<T> {

    protected static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    private static final int ENTITY_RENDER_WIDTH = 70;
    private static final int ENTITY_RENDER_HEIGHT = 70;
    private static final int ENTITY_BASE_SCALE = 30;
    private static final int ENTITY_FIT_PADDING = 14;
    private static final int ENTITY_RENDER_Y_OFFSET = ENTITY_RENDER_HEIGHT / 16;
    protected IFilterConfigurable spirit;
    protected T container;

    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        super(container, playerInventory, titleIn, 175, 165);
        this.container = container;
        this.spirit = this.container.spirit;
    }

    public SpiritGui(T container, Inventory playerInventory, Component titleIn, int imageWidth, int imageHeight) {
        super(container, playerInventory, titleIn, imageWidth, imageHeight);
        this.container = container;
        this.spirit = this.container.spirit;
    }

    public static void drawEntityToGui(GuiGraphicsExtractor guiGraphics, int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity entity) {
        int x0 = posX - ENTITY_RENDER_WIDTH / 2;
        int y0 = posY - ENTITY_RENDER_HEIGHT + 10;
        int x1 = posX + ENTITY_RENDER_WIDTH / 2;
        int y1 = posY + 10;
        extractEntityInInventoryFollowsMouseWithoutName(guiGraphics, x0, y0, x1, y1, scale, 0.0625F, mouseX, mouseY,
                entity);
    }

    protected static float getEntityMouseX(int posX) {
        return posX;
    }

    protected static float getEntityMouseY(int posY) {
        return posY - ENTITY_RENDER_HEIGHT / 2.0F + 10;
    }

    protected static int getEntityScale(LivingEntity entity) {
        float entityScale = Math.max(entity.getScale(), 0.0001F);
        float renderWidth = entity.getBbWidth() / entityScale;
        float renderHeight = entity.getBbHeight() / entityScale;
        if (renderWidth <= 0 || renderHeight <= 0) {
            return ENTITY_BASE_SCALE;
        }

        int fitWidth = ENTITY_RENDER_WIDTH - ENTITY_FIT_PADDING * 2;
        int fitHeight = ENTITY_RENDER_HEIGHT - ENTITY_FIT_PADDING * 2;
        int maxScaleForWidth = (int) Math.floor(fitWidth / renderWidth);
        int maxScaleForHeight = (int) Math.floor(fitHeight / renderHeight);
        return Math.max(1, Math.min(ENTITY_BASE_SCALE, Math.min(maxScaleForWidth, maxScaleForHeight)));
    }

    protected static void extractEntityInInventoryFollowsMouseWithoutName(GuiGraphicsExtractor guiGraphics, int x0,
                                                                          int y0, int x1, int y1, int size,
                                                                          float offsetY, float mouseX, float mouseY,
                                                                          LivingEntity entity) {
        float centerX = (x0 + x1) / 2.0F;
        float centerY = (y0 + y1) / 2.0F;
        float xAngle = (float) Math.atan((centerX - mouseX) / 40.0F);
        float yAngle = (float) Math.atan((centerY - mouseY) / 40.0F);
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf xRotation = new Quaternionf().rotateX(yAngle * 20.0F * (float) (Math.PI / 180.0));
        rotation.mul(xRotation);

        EntityRenderState renderState = createEntityRenderStateWithoutName(entity);
        if (renderState instanceof LivingEntityRenderState livingRenderState) {
            livingRenderState.bodyRot = 180.0F + xAngle * 20.0F;
            livingRenderState.yRot = xAngle * 20.0F;
            if (livingRenderState.pose != Pose.FALL_FLYING) {
                livingRenderState.xRot = -yAngle * 20.0F;
            } else {
                livingRenderState.xRot = 0.0F;
            }

            livingRenderState.boundingBoxWidth = livingRenderState.boundingBoxWidth / livingRenderState.scale;
            livingRenderState.boundingBoxHeight = livingRenderState.boundingBoxHeight / livingRenderState.scale;
            livingRenderState.scale = 1.0F;
        }

        Vector3f translation = new Vector3f(0.0F, renderState.boundingBoxHeight / 2.0F + offsetY, 0.0F);
        guiGraphics.entity(renderState, size, translation, rotation, xRotation, x0, y0, x1, y1);
    }

    protected static EntityRenderState createEntityRenderStateWithoutName(LivingEntity entity) {
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> renderer = entityRenderDispatcher.getRenderer(entity);
        EntityRenderState renderState = renderer.createRenderState(entity, 1.0F);
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;
        renderState.lightCoords = 15728880;
        renderState.nameTag = null;
        renderState.nameTagAttachment = null;
        renderState.scoreText = null;
        return renderState;
    }

    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void init() {
        super.init();
        this.clearWidgets();

        int labelHeight = 9;
        LabelWidget nameLabel = new LabelWidget(this.leftPos + 65, this.topPos + 17, false, -1, 2, 0x404040);
        nameLabel.addLine(TextUtil.formatDemonName(this.spirit.getEntity().getName().getString()));
        this.addRenderableWidget(nameLabel);

        if (this.spirit instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0) {
            int agePercent = (int) Math.floor(spiritEntity.getSpiritAge() / (float) spiritEntity.getSpiritMaxAge() * 100);
            LabelWidget ageLabel = new LabelWidget(this.leftPos + 65, this.topPos + 17 + labelHeight + 5, false, -1, 2, 0x404040);
            ageLabel.addLine(I18n.get(TRANSLATION_KEY_BASE + ".age", agePercent));
            this.addRenderableWidget(ageLabel);
        }

        String jobID = this.spirit instanceof SpiritEntity spiritEntity ? spiritEntity.getJobID() : "";
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
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTicks) {
//        this.renderBackground(guiGraphics); //called by super

        this.extractBackground(guiGraphics);

        guiGraphics.pose().pushMatrix();
        int entityX = this.leftPos + 35;
        int entityY = this.topPos + 65 + ENTITY_RENDER_Y_OFFSET;
        int scale = getEntityScale(this.spirit.getEntity());
        drawEntityToGui(guiGraphics, entityX, entityY, scale, getEntityMouseX(entityX), getEntityMouseY(entityY),
                this.spirit.getEntity());
        guiGraphics.pose().popMatrix();

        super.extractContents(guiGraphics, x, y, partialTicks);
    }

    protected void extractBackground(GuiGraphicsExtractor guiGraphics) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.getTexture(), this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }

    protected Identifier getTexture() {
        return TEXTURE;
    }
}
