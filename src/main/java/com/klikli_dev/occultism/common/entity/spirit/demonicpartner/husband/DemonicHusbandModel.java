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

package com.klikli_dev.occultism.common.entity.spirit.demonicpartner.husband;

import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class DemonicHusbandModel extends DefaultedGeoModel<DemonicHusband> {

    public DemonicHusbandModel() {
        super(DemonicHusband.ID);
    }

    @Override
    protected String subtype() {
        return "entity";
    }

    @Override
    public void setCustomAnimations(DemonicHusband entity, long instanceId, AnimationState<DemonicHusband> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);

        GeoBone head = getAnimationProcessor().getBone("Head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
        }
    }

//
//    @Override
//    public RenderType getRenderType(AfritEntity animatable, ResourceLocation texture) {
//        return RenderType.entityTranslucent(this.getTextureResource(animatable));
//    }
}
