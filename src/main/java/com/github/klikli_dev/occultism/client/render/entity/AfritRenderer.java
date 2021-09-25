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

package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.AfritModel;
import com.github.klikli_dev.occultism.common.entity.spirit.AfritEntity;
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AfritRenderer extends BipedSpiritRenderer<AfritEntity, AfritModel> {
    //region Fields
    private static final ResourceLocation[] TEXTURES = {new ResourceLocation(Occultism.MODID,
            "textures/entity/afrit.png")};
    //endregion Fields


    //region Initialization
    public AfritRenderer(EntityRendererProvider.Context context) {
        super(context, new AfritModel(context.bakeLayer(OccultismModelLayers.AFRIT)), 0.5f);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public ResourceLocation getTextureLocation(AfritEntity entity) {
        return TEXTURES[entity.getEntityData().get(entity.getDataParameterSkin())];
    }

    @Override
    protected void scale(AfritEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        super.scale(entity, matrixStackIn, partialTickTime);
        matrixStackIn.scale(1.2f, 1.2f, 1.2f);
    }
    //endregion Overrides
}
