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

import com.klikli_dev.occultism.client.model.entity.AfritModel;
import com.klikli_dev.occultism.client.render.entity.glowlayer.ConditionalGlowingGeoLayer;
import com.klikli_dev.occultism.common.entity.spirit.AfritEntity;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import com.geckolib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class AfritRenderer extends GeoEntityRenderer<AfritEntity, EntityRenderState> {

    public AfritRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AfritModel());

        @SuppressWarnings({"unchecked", "rawtypes"})
        GeoRenderLayer glowLayer = new ConditionalGlowingGeoLayer(this);
        this.withRenderLayer(glowLayer);

        @SuppressWarnings({"unchecked", "rawtypes"})
        GeoRenderLayer itemLayer = new BlockAndItemGeoLayer(renderManager, this) {
            protected List getRelevantBones(Object animatable, Object relatedObject, Object renderState, float partialTick) {
                AfritEntity entity = (AfritEntity) animatable;
                ItemStack mainHandStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
                if (!mainHandStack.isEmpty()) {
                    ItemStackRenderState stackState = RenderUtil.createRenderStateForItem(mainHandStack, this.itemModelResolver, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, entity);
                    return Collections.singletonList(RenderData.item("bone", ItemDisplayContext.THIRD_PERSON_LEFT_HAND, stackState));
                }
                return Collections.emptyList();
            }

            public void addRenderData(Object animatable, Object relatedObject, Object renderState, float partialTick) {
                List bones = this.getRelevantBones(animatable, relatedObject, renderState, partialTick);
                if (!bones.isEmpty()) {
                    ((com.geckolib.renderer.base.GeoRenderState)(Object)renderState).addGeckolibData(CONTENTS, bones);
                }
            }

            protected void submitItemStackRender(PoseStack poseStack, com.geckolib.cache.model.GeoBone bone, ItemStackRenderState stackState, ItemDisplayContext displayContext, Object renderState, net.minecraft.client.renderer.SubmitNodeCollector renderTasks, int packedLight) {
                poseStack.pushPose();
                poseStack.translate(0, -0.4, 0);
                super.submitItemStackRender(poseStack, bone, stackState, displayContext, renderState, renderTasks, packedLight);
                poseStack.popPose();
            }
        };
        this.withRenderLayer(itemLayer);
    }
}
