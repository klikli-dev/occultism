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
import com.github.klikli_dev.occultism.client.model.entity.ModelFoliot;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderFoliot extends RenderSpirit {
    //region Fields
    private static final ResourceLocation[] TEXTURES = {new ResourceLocation(Occultism.MODID,
            "textures/entity/foliot.png")};
    //endregion Fields


    //region Initialization
    public RenderFoliot(EntityRendererManager renderManager) {
        super(renderManager, new ModelFoliot(), 0.25f);
    }
    //endregion Initialization

    //region Overrides
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySpirit entity) {
        return TEXTURES[entity.getDataManager().get(entity.getDataParameterSkin())];
    }

    @Override
    protected void preRenderCallback(EntitySpirit entity, float partialTickTime) {
        super.preRenderCallback(entity, partialTickTime);
        GlStateManager.scale(0.6, 0.6, 0.6);
    }
    //endregion Overrides
}
