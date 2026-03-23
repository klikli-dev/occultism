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

import com.klikli_dev.occultism.client.model.entity.FoliotModel;
import com.klikli_dev.occultism.client.render.entity.glowlayer.ConditionalGlowingGeoLayer;
import com.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import com.geckolib.cache.object.GeoBone;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.Objects;

public class FoliotRenderer extends GeoEntityRenderer<FoliotEntity> {

    public FoliotRenderer(EntityRendererProvider.Context context) {
        super(context, new FoliotModel());

        this.addRenderLayer(new ConditionalGlowingGeoLayer<>(this));
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this, (bone, animatable) -> {
            if (animatable.getEntity() instanceof SpiritEntity spirit) {
                if (Objects.equals(spirit.getJobID(), OccultismSpiritJobs.FARMER.getId().toString())
                        || Objects.equals(spirit.getJobID(), OccultismSpiritJobs.LUMBERJACK.getId().toString())
                        || Objects.equals(spirit.getJobID(), OccultismSpiritJobs.CLEANER.getId().toString())) {
                    if (Objects.equals(bone.getName(), "arm_left")) //left hand
                        return animatable.getItemInHand(InteractionHand.MAIN_HAND);
                } else if (Objects.equals(bone.getName(), "arm_right")) //right hand
                    return animatable.getItemInHand(InteractionHand.MAIN_HAND);
            }
            return null;
        }, (bone, animatable) -> null) {
            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, FoliotEntity animatable) {
                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, FoliotEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                poseStack.pushPose();

                poseStack.translate(0, -0.65, 0);
                if (Objects.equals(animatable.getJobID(), OccultismSpiritJobs.CLEANER.getId().toString())) {
                    poseStack.translate(-0.3, 0.35, 0.85);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                }
                poseStack.mulPose(Axis.XN.rotationDegrees(90));

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
                poseStack.popPose();
            }
        });
    }

}
