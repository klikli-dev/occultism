/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.BlacksmithFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.BlacksmithFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.BlacksmithFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class BlacksmithFamiliarRenderer extends MobRenderer<BlacksmithFamiliarEntity, BlacksmithFamiliarRenderState, BlacksmithFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/blacksmith_familiar.png");

    private static final ContextKey<Byte> BARS = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "blacksmith_bars"));

    private final ItemModelResolver itemModelResolver;

    public BlacksmithFamiliarRenderer(Context context) {
        super(context, new BlacksmithFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_BLACKSMITH)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new IngotsLayer(this, this.itemModelResolver));
    }

    @Override
    public void extractRenderState(BlacksmithFamiliarEntity entity, BlacksmithFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isSitting = entity.isSitting();
        reusedState.isPartying = entity.isPartying();
        reusedState.hasSquareHair = entity.hasSquareHair();
        reusedState.hasMarioMoustache = entity.hasMarioMoustache();
        reusedState.hasEarring = entity.hasEarring();
        reusedState.isChristmas = FamiliarUtil.isChristmas();
        reusedState.setRenderData(BARS, entity.getBars());
    }

    @Override
    public BlacksmithFamiliarRenderState createRenderState() {
        return new BlacksmithFamiliarRenderState();
    }

    @Override
    public Identifier getTextureLocation(BlacksmithFamiliarRenderState state) {
        return TEXTURES;
    }

    @Override
    protected void setupRotations(BlacksmithFamiliarRenderState state, PoseStack poseStack, float bob, float scale) {
        if (!state.isSitting) {
            super.setupRotations(state, poseStack, bob, scale);
        }
    }

    private static class IngotsLayer extends RenderLayer<BlacksmithFamiliarRenderState, BlacksmithFamiliarModel> {

        private final ItemModelResolver itemModelResolver;

        public IngotsLayer(RenderLayerParent<BlacksmithFamiliarRenderState, BlacksmithFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, BlacksmithFamiliarRenderState state, float yRot, float xRot) {
            Byte barsData = state.getRenderData(BlacksmithFamiliarRenderer.BARS);
            int bars = barsData != null ? barsData : 0;
            if (bars <= 0) return;

            pMatrixStack.pushPose();
            float scale = 0.5f;
            pMatrixStack.scale(scale, scale, scale);

            ItemStack ingotStack = new ItemStack(Items.IRON_INGOT);
            for (int i = 0; i < bars; i++) {
                pMatrixStack.pushPose();
                pMatrixStack.translate(i % 2 == 0 ? -0.3 : 0.3, 2.03 - i / 2 * 0.03, -0.15);
                pMatrixStack.mulPose(new Quaternionf().rotateXYZ(-90 * ((float) Math.PI / 180F), 0, i * ((float) Math.PI / 180F)));

                ItemStackRenderState stackState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(stackState, ingotStack, ItemDisplayContext.GROUND, null, null, 0);
                stackState.submit(pMatrixStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

                pMatrixStack.popPose();
            }
            pMatrixStack.popPose();
        }
    }
}
