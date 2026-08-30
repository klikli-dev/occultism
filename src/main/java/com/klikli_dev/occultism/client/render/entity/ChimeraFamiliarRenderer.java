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
import com.klikli_dev.occultism.client.model.entity.ChimeraFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.ChimeraFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.ChimeraFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class ChimeraFamiliarRenderer extends MobRenderer<ChimeraFamiliarEntity, ChimeraFamiliarRenderState, ChimeraFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/chimera_familiar.png");

    public ChimeraFamiliarRenderer(Context context) {
        super(context, new ChimeraFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_CHIMERA)), 0.3f);
    }

    @Override
    public void extractRenderState(ChimeraFamiliarEntity entity, ChimeraFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isSitting = entity.isSitting();
        reusedState.isPartying = entity.isPartying();
        reusedState.isSnakeAttacking = entity.getAttackProgress(0) > 0
                && entity.getAttacker() == ChimeraFamiliarEntity.SNAKE_ATTACKER;
        reusedState.noseGoatRot = entity.getNoseGoatRot(partialTick);
        reusedState.attackProgress = entity.getAttackProgress(partialTick);
        reusedState.attacker = entity.getAttacker();
        reusedState.hasFlaps = entity.hasFlaps();
        reusedState.hasRing = entity.hasRing();
        reusedState.hasHat = entity.hasHat();
        reusedState.hasGoat = entity.hasGoat();
        reusedState.hasBeard = entity.hasBeard();
        reusedState.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        reusedState.scale = 0.5F + (float) entity.getSize() / 100;
    }

    @Override
    public ChimeraFamiliarRenderState createRenderState() {
        return new ChimeraFamiliarRenderState();
    }

    @Override
    public void submit(ChimeraFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        if (state.isSitting)
            poseStack.translate(0, -0.14 * state.scale, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(ChimeraFamiliarRenderState state) {
        return TEXTURES;
    }
}
