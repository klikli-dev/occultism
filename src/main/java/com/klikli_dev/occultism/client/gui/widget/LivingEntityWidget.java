/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.widget;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiSyncable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class LivingEntityWidget extends AbstractWidget implements GuiSyncable {
    private static final int DEFAULT_PADDING = 8;

    private final GuiHost host;
    private final int relativeX;
    private final int relativeY;
    private final Supplier<LivingEntity> entitySupplier;
    private final float previewMouseOffsetX;
    private final float previewMouseOffsetY;

    public LivingEntityWidget(GuiHost host, int x, int y, int width, int height, Supplier<LivingEntity> entitySupplier) {
        this(host, x, y, width, height, entitySupplier, 0.0F, 0.0F);
    }

    public LivingEntityWidget(GuiHost host, int x, int y, int width, int height, Supplier<LivingEntity> entitySupplier,
                              float previewMouseOffsetX, float previewMouseOffsetY) {
        super(host.guiX(x), host.guiY(y), width, height, Component.empty());
        this.host = host;
        this.relativeX = x;
        this.relativeY = y;
        this.entitySupplier = entitySupplier;
        this.previewMouseOffsetX = previewMouseOffsetX;
        this.previewMouseOffsetY = previewMouseOffsetY;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        LivingEntity entity = this.entitySupplier.get();
        if (entity == null) {
            return;
        }

        int size = this.getEntityScale(entity);
        float centerX = this.getX() + this.getWidth() / 2.0F;
        float centerY = this.getY() + this.getHeight() / 2.0F;
        float previewMouseX = centerX - this.previewMouseOffsetX;
        float previewMouseY = centerY - this.previewMouseOffsetY;
        this.extractEntity(guiGraphics, this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), size,
                previewMouseX, previewMouseY, partialTick, entity);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Override
    public void syncToHost() {
        this.setX(this.host.guiX(this.relativeX));
        this.setY(this.host.guiY(this.relativeY));
    }

    private int getEntityScale(LivingEntity entity) {
        float entityScale = Math.max(entity.getScale(), 0.0001F);
        float renderWidth = entity.getBbWidth() / entityScale;
        float renderHeight = entity.getBbHeight() / entityScale;
        if (renderWidth <= 0 || renderHeight <= 0) {
            return 24;
        }

        int fitWidth = Math.max(1, this.getWidth() - DEFAULT_PADDING * 2);
        int fitHeight = Math.max(1, this.getHeight() - DEFAULT_PADDING * 2);
        int maxScaleForWidth = (int) Math.floor(fitWidth / renderWidth);
        int maxScaleForHeight = (int) Math.floor(fitHeight / renderHeight);
        return Math.max(1, Math.min(30, Math.min(maxScaleForWidth, maxScaleForHeight)));
    }

    private void extractEntity(GuiGraphicsExtractor guiGraphics, int x0, int y0, int x1, int y1, int size,
                               float mouseX, float mouseY, float partialTick, LivingEntity entity) {
        float centerX = (x0 + x1) / 2.0F;
        float centerY = (y0 + y1) / 2.0F;
        float xAngle = (float) Math.atan((centerX - mouseX) / 40.0F);
        float yAngle = (float) Math.atan((centerY - mouseY) / 40.0F);
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf xRotation = new Quaternionf().rotateX(yAngle * 20.0F * ((float) Math.PI / 180.0F));
        rotation.mul(xRotation);
        EntityRenderState renderState = this.extractRenderState(entity);
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

        Vector3f translation = new Vector3f(0.0F, renderState.boundingBoxHeight / 2.0F + 0.0625F, 0.0F);
        guiGraphics.entity(renderState, size, translation, rotation, xRotation, x0, y0, x1, y1);
    }

    private EntityRenderState extractRenderState(LivingEntity entity) {
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> renderer = entityRenderDispatcher.getRenderer(entity);
        EntityRenderState renderState = renderer.createRenderState(entity, 1.0F);
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;
        renderState.nameTag = null;
        renderState.scoreText = null;
        renderState.nameTagAttachment = null;
        return renderState;
    }
}
