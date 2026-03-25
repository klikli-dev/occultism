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

import com.klikli_dev.occultism.client.model.entity.DjinniModel;
import com.klikli_dev.occultism.client.render.entity.glowlayer.ConditionalGlowingGeoLayer;
import com.klikli_dev.occultism.common.entity.spirit.DjinniEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import com.geckolib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DjinniRenderer extends GeoEntityRenderer<DjinniEntity, EntityRenderState> {

    public DjinniRenderer(EntityRendererProvider.Context context) {
        super(context, new DjinniModel());

        @SuppressWarnings({"unchecked", "rawtypes"})
        GeoRenderLayer glowLayer = new ConditionalGlowingGeoLayer(this);
        this.withRenderLayer(glowLayer);

        @SuppressWarnings({"unchecked", "rawtypes"})
        GeoRenderLayer itemLayer = new BlockAndItemGeoLayer(context, this) {
            protected List getRelevantBones(GeoAnimatable animatable, Object relatedObject, GeoRenderState renderState, float partialTick) {
                DjinniEntity entity = (DjinniEntity) animatable;
                String jobId = entity.getJobID();
                ItemStack mainHandStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
                if (mainHandStack.isEmpty()) return Collections.emptyList();

                String boneName;
                if (Objects.equals(jobId, OccultismSpiritJobs.MANAGE_MACHINE.getId().toString())) {
                    boneName = "RARM";
                } else if (Objects.equals(jobId, OccultismSpiritJobs.TRADE_GAMBLER.getId().toString())) {
                    boneName = "LARM";
                } else {
                    boneName = "bone2";
                }
                ItemStackRenderState stackState = RenderUtil.createRenderStateForItem(mainHandStack, this.itemModelResolver, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, entity);
                return Collections.singletonList(RenderData.item(boneName, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, stackState));
            }

            public void addRenderData(GeoAnimatable animatable, Object relatedObject, GeoRenderState renderState, float partialTick) {
                List bones = this.getRelevantBones(animatable, relatedObject, renderState, partialTick);
                if (!bones.isEmpty()) {
                    renderState.addGeckolibData(CONTENTS, bones);
                }
            }

            protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStackRenderState stackState, ItemDisplayContext displayContext, Object renderState, SubmitNodeCollector renderTasks, int packedLight) {
                poseStack.pushPose();
                poseStack.translate(0, -0.4, 0);
                // TODO: get jobId from renderState data if needed; using best-effort approach
                // Djinni MANAGE_MACHINE: scale + rotate for machine part
                // Djinni TRADE_GAMBLER: scale + translate for gambling item
                // For now apply the general translate; job-specific transforms require render state data
                super.submitItemStackRender(poseStack, bone, stackState, displayContext, renderState, renderTasks, packedLight);
                poseStack.popPose();
            }
        };
        this.withRenderLayer(itemLayer);
    }
}
