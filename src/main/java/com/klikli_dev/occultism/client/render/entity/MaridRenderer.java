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

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.MaridModel;
import com.klikli_dev.occultism.common.entity.spirit.MaridEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MaridRenderer extends BipedSpiritRenderer<MaridEntity, MaridModel> {

    private static final ResourceLocation[] TEXTURES = {new ResourceLocation(Occultism.MODID,
            "textures/entity/marid.png")};

    public MaridRenderer(EntityRendererProvider.Context context) {
        super(context, new MaridModel(context.bakeLayer(OccultismModelLayers.MARID)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MaridEntity entity) {
        return TEXTURES[entity.getEntityData().get(entity.getDataParameterSkin())];
    }

    @Override
    protected void scale(MaridEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        super.scale(entity, matrixStackIn, partialTickTime);
        matrixStackIn.scale(1.2f, 1.2f, 1.2f);
    }

}
