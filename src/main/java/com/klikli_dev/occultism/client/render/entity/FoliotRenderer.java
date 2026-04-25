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
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import com.geckolib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FoliotRenderer extends OccultismGeoLivingEntityRenderer<FoliotEntity> {

    public FoliotRenderer(Context context) {
        super(context, new FoliotModel());

        GeoRenderLayer<FoliotEntity, Void, OccultismGeoLivingEntityRenderState> glowLayer = new ConditionalGlowingGeoLayer<>(this);
        this.withRenderLayer(glowLayer);

        GeoRenderLayer<FoliotEntity, Void, OccultismGeoLivingEntityRenderState> itemLayer = new BlockAndItemGeoLayer<>(context, this) {
            @Override
            protected List<RenderData> getRelevantBones(FoliotEntity animatable, @Nullable Void relatedObject, OccultismGeoLivingEntityRenderState renderState, float partialTick) {
                String jobId = animatable.getJobID();
                ItemStack mainHandStack = animatable.getItemInHand(InteractionHand.MAIN_HAND);
                if (mainHandStack.isEmpty()) return Collections.emptyList();

                String boneName;
                ItemDisplayContext displayContext;
                if (Objects.equals(jobId, OccultismSpiritJobs.FARMER.getId().toString())
                        || Objects.equals(jobId, OccultismSpiritJobs.LUMBERJACK.getId().toString())
                        || Objects.equals(jobId, OccultismSpiritJobs.CLEANER.getId().toString())) {
                    boneName = "arm_left";
                    displayContext = ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
                } else {
                    boneName = "arm_right";
                    displayContext = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                }
                ItemStackRenderState stackState = RenderUtil.createRenderStateForItem(mainHandStack, this.itemModelResolver, displayContext, animatable);
                return Collections.singletonList(RenderData.item(boneName, displayContext, stackState));
            }

            @Override
            public void addRenderData(FoliotEntity animatable, @Nullable Void relatedObject, OccultismGeoLivingEntityRenderState renderState, float partialTick) {
                renderState.jobID = animatable.getJobID();
                List<RenderData> bones = this.getRelevantBones(animatable, relatedObject, renderState, partialTick);
                if (!bones.isEmpty()) {
                    renderState.addGeckolibData(CONTENTS, bones);
                }
            }

            @Override
            protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStackRenderState stackState, ItemDisplayContext displayContext, OccultismGeoLivingEntityRenderState renderState, SubmitNodeCollector renderTasks, int packedLight) {
                poseStack.pushPose();
                poseStack.translate(0, -0.65, 0);
                if (Objects.equals(renderState.jobID, OccultismSpiritJobs.CLEANER.getId().toString())) {
                    poseStack.translate(-0.3, -0.35, 0.35);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                }
                poseStack.mulPose(Axis.XN.rotationDegrees(90));
                super.submitItemStackRender(poseStack, bone, stackState, displayContext, renderState, renderTasks, packedLight);
                poseStack.popPose();
            }
        };
        this.withRenderLayer(itemLayer);
    }
}
