/*
 * MIT License
 *
 * Copyright 2022 vemerion
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
import com.klikli_dev.occultism.client.model.entity.BeaverFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.BeaverFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeaverFamiliarRenderer extends MobRenderer<BeaverFamiliarEntity, BeaverFamiliarModel> {

    private static final ResourceLocation TEXTURES = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/beaver_familiar.png");

    public BeaverFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new BeaverFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_BEAVER)), 0.3f);

    }

    @Override
    public ResourceLocation getTextureLocation(BeaverFamiliarEntity entity) {
        return TEXTURES;
    }
}
