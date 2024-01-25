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
import com.klikli_dev.occultism.client.model.entity.FoliotModel;
import com.klikli_dev.occultism.common.entity.spirit.DjinniEntity;
import com.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.Objects;

public class FoliotRenderer extends GeoEntityRenderer<FoliotEntity> {

    public FoliotRenderer(EntityRendererProvider.Context context) {
        super(context, new FoliotModel());

        this.withScale(1.5f);

        this.addRenderLayer(new BlockAndItemGeoLayer<>(this, (bone, animatable) -> {
            if (Objects.equals(bone.getName(), "RARM")) //right hand
                return animatable.getItemInHand(InteractionHand.MAIN_HAND);
            return null;
        }, (bone, animatable) -> null) {
            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, FoliotEntity animatable) {
                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, FoliotEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                poseStack.pushPose();

                poseStack.translate(-0.1, -0.3, 0);

                final float scale = 0.4f;
                poseStack.scale(scale, scale, scale);

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
                poseStack.popPose();
            }
        });
    }

}
