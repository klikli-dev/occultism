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

package com.github.klikli_dev.occultism.client.model.entity;

import com.github.klikli_dev.occultism.common.entity.ShubNiggurathSpawnEntity;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class ShubNiggurathSpawnModel extends EntityModel<ShubNiggurathSpawnEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer head;
    public ModelRenderer rightHorn1;
    public ModelRenderer mouth;
    public ModelRenderer leftHorn1;
    public ModelRenderer tentacleTop1;
    public ModelRenderer tentacleMiddle1;
    public ModelRenderer tentacleBottom1;
    public ModelRenderer eye2;
    public ModelRenderer eye3;
    public ModelRenderer eye4;
    public ModelRenderer christmasPresent1;
    public ModelRenderer rightHorn2;
    public ModelRenderer jaw;
    public ModelRenderer upperTeeth;
    public ModelRenderer eye1;
    public ModelRenderer lowerTeeth;
    public ModelRenderer leftHorn2;
    public ModelRenderer tentacleTop2;
    public ModelRenderer tentacleTop3;
    public ModelRenderer tentacleMiddle2;
    public ModelRenderer tentacleMiddle3;
    public ModelRenderer tentacleBottom2;
    public ModelRenderer tentacleBottom3;
    public ModelRenderer christmasPresent2;
    public ModelRenderer christmasPresent3;

    public ShubNiggurathSpawnModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        this.eye2 = new ModelRenderer(this, 11, 15);
        this.eye2.setPos(-0.2F, -0.8F, -1.8F);
        this.eye2.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(eye2, 0.0F, 0.4300491170387584F, 0.0F);
        this.tentacleMiddle2 = new ModelRenderer(this, 4, 11);
        this.tentacleMiddle2.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleMiddle2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.tentacleBottom1 = new ModelRenderer(this, 4, 11);
        this.tentacleBottom1.setPos(-0.8F, 1.4F, -0.7F);
        this.tentacleBottom1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleBottom1, -1.5707963267948966F, 0.0F, 0.0F);
        this.lowerTeeth = new ModelRenderer(this, 16, 5);
        this.lowerTeeth.setPos(0.01F, -1.0F, 0.0F);
        this.lowerTeeth.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.christmasPresent2 = new ModelRenderer(this, 0, 22);
        this.christmasPresent2.setPos(3.5F, 0.0F, 0.0F);
        this.christmasPresent2.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(christmasPresent2, 0.7044148967575558F, 0.5473352640780661F, -0.5085889333785032F);
        this.eye3 = new ModelRenderer(this, 11, 15);
        this.eye3.mirror = true;
        this.eye3.setPos(0.8F, 0.0F, -2.1F);
        this.eye3.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(eye3, 0.0F, 0.27366763203903305F, 1.5707963267948966F);
        this.tentacleTop3 = new ModelRenderer(this, 4, 11);
        this.tentacleTop3.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleTop3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleTop3, 0.27366763203903305F, 0.23457224414434488F, 0.0F);
        this.tentacleMiddle3 = new ModelRenderer(this, 4, 11);
        this.tentacleMiddle3.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleMiddle3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 9, 4);
        this.jaw.setPos(0.0F, 0.1F, 0.0F);
        this.jaw.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(jaw, 0.3483677027191016F, 0.0F, 0.0F);
        this.eye4 = new ModelRenderer(this, 11, 15);
        this.eye4.setPos(0.8F, 0.0F, -0.8F);
        this.eye4.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(eye4, 0.0F, 0.0781907508222411F, 1.5707963267948966F);
        this.leftHorn1 = new ModelRenderer(this, 12, 0);
        this.leftHorn1.setPos(0.9F, -0.6F, 0.2F);
        this.leftHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn1, -0.17453292519943295F, 0.0F, 0.0F);
        this.rightHorn2 = new ModelRenderer(this, 18, 0);
        this.rightHorn2.mirror = true;
        this.rightHorn2.setPos(0.01F, 0.2F, 1.0F);
        this.rightHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn2, -0.17453292519943295F, 0.0F, 0.0F);
        this.upperTeeth = new ModelRenderer(this, 0, 6);
        this.upperTeeth.setPos(0.0F, 0.5F, 0.0F);
        this.upperTeeth.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn1 = new ModelRenderer(this, 12, 0);
        this.rightHorn1.mirror = true;
        this.rightHorn1.setPos(-0.9F, -0.6F, 0.2F);
        this.rightHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn1, -0.17453292519943295F, 0.0F, 0.0F);
        this.tentacleMiddle1 = new ModelRenderer(this, 4, 11);
        this.tentacleMiddle1.setPos(0.3F, 1.4F, -2.2F);
        this.tentacleMiddle1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleMiddle1, -1.5707963267948966F, 0.0F, 0.0F);
        this.tentacleTop2 = new ModelRenderer(this, 4, 11);
        this.tentacleTop2.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleTop2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleTop2, 0.35185837453889574F, 0.3127630032889644F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(-1.5F, 22.5F, -0.8F);
        this.head.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 1.1798425477165557F, 0.0F, -1.5707963267948966F);
        this.eye1 = new ModelRenderer(this, 11, 15);
        this.eye1.mirror = true;
        this.eye1.setPos(0.0F, -0.8F, -1.7F);
        this.eye1.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(eye1, 0.0F, -1.0159561416069327F, 0.0F);
        this.tentacleBottom3 = new ModelRenderer(this, 4, 11);
        this.tentacleBottom3.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleBottom3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.christmasPresent1 = new ModelRenderer(this, 0, 18);
        this.christmasPresent1.setPos(-0.01F, 0.0F, 0.0F);
        this.christmasPresent1.addBox(-1.5F, -3.5F, -3.5F, 5.0F, 7.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn2 = new ModelRenderer(this, 18, 0);
        this.leftHorn2.setPos(-0.01F, 0.2F, 1.0F);
        this.leftHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn2, -0.17453292519943295F, 0.0F, 0.0F);
        this.tentacleBottom2 = new ModelRenderer(this, 4, 11);
        this.tentacleBottom2.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleBottom2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.christmasPresent3 = new ModelRenderer(this, 0, 22);
        this.christmasPresent3.setPos(3.5F, 0.0F, 0.0F);
        this.christmasPresent3.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(christmasPresent3, -0.5082398928281348F, 0.6651449885876833F, -2.4635321326635524F);
        this.tentacleTop1 = new ModelRenderer(this, 4, 11);
        this.tentacleTop1.setPos(0.7F, 1.4F, -0.7F);
        this.tentacleTop1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleTop1, -1.5707963267948966F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 21, 0);
        this.mouth.setPos(0.0F, 0.0F, -2.5F);
        this.mouth.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.19198621771937624F, 0.0F, 0.0F);
        this.head.addChild(this.eye2);
        this.tentacleMiddle1.addChild(this.tentacleMiddle2);
        this.head.addChild(this.tentacleBottom1);
        this.jaw.addChild(this.lowerTeeth);
        this.christmasPresent1.addChild(this.christmasPresent2);
        this.head.addChild(this.eye3);
        this.tentacleTop2.addChild(this.tentacleTop3);
        this.tentacleMiddle2.addChild(this.tentacleMiddle3);
        this.mouth.addChild(this.jaw);
        this.head.addChild(this.eye4);
        this.head.addChild(this.leftHorn1);
        this.rightHorn1.addChild(this.rightHorn2);
        this.mouth.addChild(this.upperTeeth);
        this.head.addChild(this.rightHorn1);
        this.head.addChild(this.tentacleMiddle1);
        this.tentacleTop1.addChild(this.tentacleTop2);
        this.mouth.addChild(this.eye1);
        this.tentacleBottom2.addChild(this.tentacleBottom3);
        this.head.addChild(this.christmasPresent1);
        this.leftHorn1.addChild(this.leftHorn2);
        this.tentacleBottom1.addChild(this.tentacleBottom2);
        this.christmasPresent1.addChild(this.christmasPresent3);
        this.head.addChild(this.tentacleTop1);
        this.head.addChild(this.mouth);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.head).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(ShubNiggurathSpawnEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
            float pNetHeadYaw, float pHeadPitch) {
        showModels(pEntity);

        this.jaw.xRot = 0.35f + MathHelper.cos(pAgeInTicks * 0.3f) * toRads(15);
        this.head.y = 22.5f - Math.abs(MathHelper.cos(pAgeInTicks * 0.15f)) * 7;
        this.head.yRot = MathHelper.cos(pAgeInTicks * 0.15f) * toRads(20);
        this.head.zRot = MathHelper.cos(pAgeInTicks * 0.30f) * toRads(20) - 1.57f;

        ShubNiggurathFamiliarModel.rotateTentacles(ImmutableList.of(tentacleBottom1, tentacleBottom2, tentacleBottom3),
                pAgeInTicks * 2.25f, 0);
        ShubNiggurathFamiliarModel.rotateTentacles(ImmutableList.of(tentacleMiddle1, tentacleMiddle2, tentacleMiddle3),
                pAgeInTicks * 2.25f, 0.5f);
        ShubNiggurathFamiliarModel.rotateTentacles(ImmutableList.of(tentacleTop1, tentacleTop2, tentacleTop3),
                pAgeInTicks * 2.25f, 1);
        tentacleBottom1.xRot -= toRads(90);
        tentacleMiddle1.xRot -= toRads(90);
        tentacleTop1.xRot -= toRads(90);
        tentacleBottom1.zRot = 0;
        tentacleMiddle1.zRot = 0;
        tentacleTop1.zRot = 0;
    }

    private void showModels(ShubNiggurathSpawnEntity entityIn) {
        boolean isChristmas = FamiliarUtil.isChristmas();
        tentacleBottom1.visible = !isChristmas;
        tentacleMiddle1.visible = !isChristmas;
        tentacleTop1.visible = !isChristmas;
        mouth.visible = !isChristmas;
        christmasPresent1.visible = isChristmas;
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
