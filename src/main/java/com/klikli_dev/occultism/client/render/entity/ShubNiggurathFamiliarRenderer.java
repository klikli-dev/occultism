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
import com.klikli_dev.occultism.client.model.entity.ShubNiggurathFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.ShubNiggurathFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.ShubNiggurathFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class ShubNiggurathFamiliarRenderer
        extends MobRenderer<ShubNiggurathFamiliarEntity, ShubNiggurathFamiliarRenderState, ShubNiggurathFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/shub_niggurath_familiar.png");

    public ShubNiggurathFamiliarRenderer(Context context) {
        super(context, new ShubNiggurathFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH)), 0.3f);
    }

    @Override
    public void extractRenderState(ShubNiggurathFamiliarEntity entity, ShubNiggurathFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isSitting = entity.isSitting();
        reusedState.isPartying = entity.isPartying();
        reusedState.hasRing = entity.hasRing();
        reusedState.hasBeard = entity.hasBeard();
        reusedState.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        if (entity.getCthulhuFriend() != null) {
            reusedState.riderLimbSwing = entity.getCthulhuFriend().riderLimbSwing;
            reusedState.riderLimbSwingAmount = entity.getCthulhuFriend().riderLimbSwingAmount;
            reusedState.friendAnimationHeight = entity.getCthulhuFriend().getAnimationHeight(partialTick);
        }
    }

    @Override
    public ShubNiggurathFamiliarRenderState createRenderState() {
        return new ShubNiggurathFamiliarRenderState();
    }

    @Override
    public Identifier getTextureLocation(ShubNiggurathFamiliarRenderState state) {
        return TEXTURES;
    }

    @Override
    protected void setupRotations(ShubNiggurathFamiliarRenderState state, PoseStack poseStack, float bob, float scale) {
        if (state.isPartying) {
            poseStack.translate(0, 0.07, 0);
        } else if (state.isSitting) {
            poseStack.translate(0, -0.19, 0);
        }
        super.setupRotations(state, poseStack, bob, scale);
    }
}
