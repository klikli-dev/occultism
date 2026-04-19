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

import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import com.klikli_dev.occultism.client.model.entity.WonderingTraderModel;
import com.klikli_dev.occultism.client.render.entity.glowlayer.ConditionalGlowingGeoLayer;
import com.klikli_dev.occultism.common.entity.spirit.wonderingtrader.WonderingTraderEntity;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import com.geckolib.renderer.layer.GeoRenderLayer;

public class WonderingTraderRenderer extends OccultismGeoLivingEntityRenderer<WonderingTraderEntity> {

    public WonderingTraderRenderer(Context renderManager) {
        super(renderManager, new WonderingTraderModel());

        GeoRenderLayer<WonderingTraderEntity, Void, OccultismGeoLivingEntityRenderState> glowLayer = new ConditionalGlowingGeoLayer<>(this);
        this.withRenderLayer(glowLayer);
    }

    @Override
    public void submit(OccultismGeoLivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        Player player = Minecraft.getInstance().player;
        if (player != null && (player.hasEffect(OccultismEffects.THIRD_EYE) || CuriosUtil.hasGoggles(player) || CuriosUtil.hasStaff(player))) {
            poseStack.scale(1.2F, 1.2F, 1.2F);
        }
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<OccultismGeoLivingEntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        float headYaw = renderPassInfo.renderState().yRot * Mth.DEG_TO_RAD;
        float headPitch = -renderPassInfo.renderState().xRot * Mth.DEG_TO_RAD;

        snapshots.ifPresent("head", bone -> bone.setRotY(headYaw).setRotX(headPitch));
        snapshots.ifPresent("head3", bone -> bone.setRotY(headYaw).setRotX(headPitch));

        Player player = Minecraft.getInstance().player;
        boolean showOtherForm = player != null && (player.hasEffect(OccultismEffects.THIRD_EYE) || CuriosUtil.hasGoggles(player) || CuriosUtil.hasStaff(player));

        this.hideChildrenRecursive(renderPassInfo, snapshots, "common", showOtherForm);
        this.hideChildrenRecursive(renderPassInfo, snapshots, "other", !showOtherForm);
    }

    private void hideChildrenRecursive(RenderPassInfo<OccultismGeoLivingEntityRenderState> renderPassInfo, BoneSnapshots snapshots, String rootBoneName, boolean hide) {
        renderPassInfo.model().getBone(rootBoneName).ifPresent(rootBone -> this.hideChildrenRecursive(rootBone, snapshots, hide));
    }

    private void hideChildrenRecursive(GeoBone bone, BoneSnapshots snapshots, boolean hide) {
        for (GeoBone child : bone.children()) {
            snapshots.get(child).skipRender(hide);
            this.hideChildrenRecursive(child, snapshots, hide);
        }
    }
}
