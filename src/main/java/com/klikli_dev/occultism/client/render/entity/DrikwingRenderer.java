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

package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.render.entity.state.DrikwingRenderState;
import com.klikli_dev.occultism.common.entity.familiar.DrikwingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.parrot.ParrotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class DrikwingRenderer extends MobRenderer<DrikwingEntity, DrikwingRenderState, ParrotModel> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/drikwing.png");
    public static final Identifier TEXTURE_RED = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/drikwing_red.png");
    private final ItemModelResolver itemModelResolver;

    public DrikwingRenderer(Context context) {
        super(context, new ParrotModel(context.bakeLayer(ModelLayers.PARROT)), 0.3F);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new TotemLayer(this, this.itemModelResolver));
    }

    public DrikwingRenderState createRenderState() {
        return new DrikwingRenderState();
    }

    public Identifier getTextureLocation(DrikwingRenderState state) {
        return state.flaming ? TEXTURE_RED : TEXTURE;
    }

    public void extractRenderState(DrikwingEntity entity, DrikwingRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.variant = entity.getVariant();
        float flap = Mth.lerp(partialTicks, entity.oFlap, entity.flap);
        float flapSpeed = Mth.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
        state.flapAngle = (Mth.sin(flap) + 1.0F) * flapSpeed;
        state.pose = ParrotModel.getPose(entity);
        state.totem = entity.getItemInHand(InteractionHand.MAIN_HAND);
        state.flaming = entity.isFlaming();
    }

    private static class TotemLayer extends RenderLayer<DrikwingRenderState, ParrotModel> {
        private final ItemModelResolver itemModelResolver;

        public TotemLayer(RenderLayerParent<DrikwingRenderState, ParrotModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, DrikwingRenderState state, float yRot, float xRot) {
            if (state.totem == null || state.totem == ItemStack.EMPTY)
                return;

            poseStack.pushPose();

            poseStack.translate(0.0F, 1.18F, 0.0F);
            poseStack.mulPose(new Quaternionf().rotateXYZ(28 * ((float) Math.PI / 180F),  0, (float) Math.PI));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            ItemStackRenderState stackState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(stackState, Items.TOTEM_OF_UNDYING.getDefaultInstance(), ItemDisplayContext.GROUND, null, null, 0);
            stackState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

            poseStack.popPose();
        }
    }
}
