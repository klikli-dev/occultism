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

import com.klikli_dev.occultism.client.model.entity.WonderingTraderModel;
import com.klikli_dev.occultism.client.render.entity.glowlayer.ConditionalGlowingGeoLayer;
import com.klikli_dev.occultism.common.entity.spirit.wonderingtrader.WonderingTraderEntity;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class WonderingTraderRenderer extends GeoEntityRenderer<WonderingTraderEntity, EntityRenderState> {

    public WonderingTraderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WonderingTraderModel());

        this.addRenderLayer(new ConditionalGlowingGeoLayer<>(this));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, WonderingTraderEntity entity, BakedGeoModel model,
            @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer,
            boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        Player player = Minecraft.getInstance().player;

        if (player != null && (player.hasEffect(OccultismEffects.THIRD_EYE) || CuriosUtil.hasGoggles(player) || CuriosUtil.hasStaff(player))) {
            poseStack.scale(1.2F, 1.2F, 1.2F);
        }

        super.actuallyRender(poseStack, entity, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }


}
