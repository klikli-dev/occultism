/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.render.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
}
