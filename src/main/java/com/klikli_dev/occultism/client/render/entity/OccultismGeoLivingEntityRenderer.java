/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.render.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

public abstract class OccultismGeoLivingEntityRenderer<T extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<T, OccultismGeoLivingEntityRenderState> {
    protected OccultismGeoLivingEntityRenderer(Context context, GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public OccultismGeoLivingEntityRenderState createRenderState(T animatable, @Nullable Void relatedObject) {
        return new OccultismGeoLivingEntityRenderState();
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<OccultismGeoLivingEntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        DefaultAnimations.hardcodedHeadRotation(renderPassInfo, snapshots, "head");
        DefaultAnimations.hardcodedHeadRotation(renderPassInfo, snapshots, "Head");
    }
}
