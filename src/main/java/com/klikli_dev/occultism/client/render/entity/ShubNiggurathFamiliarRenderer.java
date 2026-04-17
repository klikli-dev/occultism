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
import com.klikli_dev.occultism.common.entity.familiar.ShubNiggurathFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class ShubNiggurathFamiliarRenderer
        extends MobRenderer<ShubNiggurathFamiliarEntity, LivingEntityRenderState, ShubNiggurathFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/shub_niggurath_familiar.png");

    public ShubNiggurathFamiliarRenderer(Context context) {
        super(context, new ShubNiggurathFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH)), 0.3f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    // Old render method preserved for reference - TODO: Port to 26.1 rendering API
    // public void render(ShubNiggurathFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
    //     pMatrixStack.pushPose();
    //     if (pEntity.isPartying())
    //         pMatrixStack.translate(0, 0.07, 0);
    //     else if (pEntity.isSitting())
    //         pMatrixStack.translate(0, -0.19, 0);
    //     super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    //     pMatrixStack.popPose();
    // }
}
