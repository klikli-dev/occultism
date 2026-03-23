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

package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.wonderingtrader.WonderingTraderEntity;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import com.geckolib.animation.AnimationState;
import com.geckolib.cache.object.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.model.DefaultedGeoModel;
import com.geckolib.model.data.EntityModelData;

public class WonderingTraderModel extends DefaultedGeoModel<WonderingTraderEntity> {

    public WonderingTraderModel() {
        super(Identifier.fromNamespaceAndPath(Occultism.MODID, "wondering_trader"));
    }

    @Override
    protected String subtype() {
        return "entity";
    }

    @Override
    public RenderType getRenderType(WonderingTraderEntity animatable, Identifier texture) {
        return RenderType.entityTranslucent(this.getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(WonderingTraderEntity entity, long instanceId, AnimationState<WonderingTraderEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);

        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;

        GeoBone common = getAnimationProcessor().getBone("common");
        if (common == null)
            return;
        GeoBone other = getAnimationProcessor().getBone("other");
        if (other == null)
            return;

        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            head.setRotX(- entityData.headPitch() * Mth.DEG_TO_RAD);
        }
        GeoBone head3 = getAnimationProcessor().getBone("head3");
        if (head3 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head3.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            head3.setRotX(- entityData.headPitch() * Mth.DEG_TO_RAD);
        }


        boolean hidden = player.hasEffect(OccultismEffects.THIRD_EYE) || CuriosUtil.hasGoggles(player) || CuriosUtil.hasStaff(player);
        hideChildrenRecursive(common, hidden);
        hideChildrenRecursive(other, !hidden);
    }

    private void hideChildrenRecursive(GeoBone bone, boolean hide) {
        for (GeoBone child : bone.getChildBones()) {
            child.setHidden(hide);
            hideChildrenRecursive(child, hide);
        }
    }
}

