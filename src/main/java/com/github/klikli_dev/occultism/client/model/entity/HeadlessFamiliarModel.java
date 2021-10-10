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

import com.github.klikli_dev.occultism.common.entity.HeadlessFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class HeadlessFamiliarModel extends EntityModel<HeadlessFamiliarEntity> {
    public ModelRenderer ratBody1;
    public ModelRenderer ratBody2;
    public ModelRenderer ratBackLeftLeg1;
    public ModelRenderer ratTail1;
    public ModelRenderer body;
    public ModelRenderer ratHair1;
    public ModelRenderer ratHair2;
    public ModelRenderer ratBackRightLeg1;
    public ModelRenderer ratHead;
    public ModelRenderer ratFrontLeftLeg1;
    public ModelRenderer ratHair3;
    public ModelRenderer ratFrontRightLeg1;
    public ModelRenderer ratNose;
    public ModelRenderer ratLeftEar;
    public ModelRenderer ratRightEar;
    public ModelRenderer ratTeeth;
    public ModelRenderer ratGlasses;
    public ModelRenderer ratFrontLeftLeg2;
    public ModelRenderer ratFrontLeftLeg3;
    public ModelRenderer ratFrontRightLeg2;
    public ModelRenderer ratFrontRightLeg3;
    public ModelRenderer ratBackLeftLeg2;
    public ModelRenderer ratBackLeftLeg3;
    public ModelRenderer ratTail2;
    public ModelRenderer leftLeg;
    public ModelRenderer leftArm;
    public ModelRenderer mantle;
    public ModelRenderer collar;
    public ModelRenderer rightArm;
    public ModelRenderer rightLeg;
    public ModelRenderer pumpkin1;
    public ModelRenderer pumpkin2;
    public ModelRenderer pumpkin3;
    public ModelRenderer pumpkin4;
    public ModelRenderer ratBackRightLeg2;
    public ModelRenderer ratBackRightLeg3;

    public HeadlessFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.ratGlasses = new ModelRenderer(this, 0, 22);
        this.ratGlasses.setPos(0.0F, -2.8F, -0.2F);
        this.ratGlasses.addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.ratBackRightLeg1 = new ModelRenderer(this, 56, 3);
        this.ratBackRightLeg1.mirror = true;
        this.ratBackRightLeg1.setPos(-3.0F, 0.5F, 0.5F);
        this.ratBackRightLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBackRightLeg1, 0.3127630032889644F, 0.0F, 0.0F);
        this.ratFrontLeftLeg3 = new ModelRenderer(this, 44, 6);
        this.ratFrontLeftLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratFrontLeftLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratFrontLeftLeg3, 0.35185837453889574F, 0.0F, 0.0F);
        this.ratNose = new ModelRenderer(this, 18, 0);
        this.ratNose.setPos(0.0F, 0.2F, -3.0F);
        this.ratNose.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.ratLeftEar = new ModelRenderer(this, 0, 24);
        this.ratLeftEar.setPos(1.5F, -1.5F, -1.5F);
        this.ratLeftEar.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratLeftEar, 0.0F, 0.0F, 0.5082398928281348F);
        this.ratFrontRightLeg1 = new ModelRenderer(this, 56, 3);
        this.ratFrontRightLeg1.setPos(-2.5F, 0.8F, -0.5F);
        this.ratFrontRightLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratFrontRightLeg1, -0.19547687289441354F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 8, 10);
        this.rightArm.mirror = true;
        this.rightArm.setPos(-2.3F, -5.01F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm, -1.5707963267948966F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 8, 10);
        this.leftArm.setPos(2.3F, -5.01F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, -1.2901473511162753F, -0.3909537457888271F, 0.0F);
        this.ratBody1 = new ModelRenderer(this, 0, 0);
        this.ratBody1.setPos(0.0F, 19.4F, 3.0F);
        this.ratBody1.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBody1, -0.0781907508222411F, 0.0F, 0.0F);
        this.ratBackRightLeg2 = new ModelRenderer(this, 43, 0);
        this.ratBackRightLeg2.mirror = true;
        this.ratBackRightLeg2.setPos(0.0F, 1.5F, 0.4F);
        this.ratBackRightLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBackRightLeg2, -0.5473352640780661F, 0.0F, 0.0F);
        this.ratRightEar = new ModelRenderer(this, 0, 24);
        this.ratRightEar.mirror = true;
        this.ratRightEar.setPos(-1.5F, -1.5F, -1.5F);
        this.ratRightEar.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratRightEar, 0.0F, 0.0F, -0.5082398928281348F);
        this.ratBackRightLeg3 = new ModelRenderer(this, 44, 6);
        this.ratBackRightLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratBackRightLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBackRightLeg3, 0.3127630032889644F, 0.0F, 0.0F);
        this.ratBody2 = new ModelRenderer(this, 24, 0);
        this.ratBody2.setPos(0.0F, -0.1F, -5.0F);
        this.ratBody2.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBody2, 0.1563815016444822F, 0.0F, 0.0F);
        this.ratBackLeftLeg3 = new ModelRenderer(this, 44, 6);
        this.ratBackLeftLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratBackLeftLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBackLeftLeg3, 0.3127630032889644F, 0.0F, 0.0F);
        this.ratFrontLeftLeg2 = new ModelRenderer(this, 43, 0);
        this.ratFrontLeftLeg2.setPos(0.0F, 1.5F, 0.2F);
        this.ratFrontLeftLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratFrontLeftLeg2, -0.23457224414434488F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 30, 9);
        this.body.setPos(0.0F, -4.0F, -1.0F);
        this.body.addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 10);
        this.rightLeg.setPos(-1.5F, 0.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, -1.4144148417951712F, 0.35185837453889574F, 0.0F);
        this.ratFrontLeftLeg1 = new ModelRenderer(this, 56, 3);
        this.ratFrontLeftLeg1.setPos(2.5F, 0.8F, -0.5F);
        this.ratFrontLeftLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratFrontLeftLeg1, -0.19547687289441354F, 0.0F, 0.0F);
        this.ratBackLeftLeg2 = new ModelRenderer(this, 43, 0);
        this.ratBackLeftLeg2.setPos(0.0F, 1.5F, 0.4F);
        this.ratBackLeftLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBackLeftLeg2, -0.5473352640780661F, 0.0F, 0.0F);
        this.ratFrontRightLeg3 = new ModelRenderer(this, 44, 6);
        this.ratFrontRightLeg3.mirror = true;
        this.ratFrontRightLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratFrontRightLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratFrontRightLeg3, 0.35185837453889574F, 0.0F, 0.0F);
        this.pumpkin2 = new ModelRenderer(this, 56, 7);
        this.pumpkin2.setPos(0.0F, -2.0F, 0.0F);
        this.pumpkin2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pumpkin2, 0.23457224414434488F, 0.5082398928281348F, 0.27366763203903305F);
        this.pumpkin4 = new ModelRenderer(this, 34, 27);
        this.pumpkin4.mirror = true;
        this.pumpkin4.setPos(0.49F, 0.99F, -2.5F);
        this.pumpkin4.addBox(0.0F, -2.5F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ratFrontRightLeg2 = new ModelRenderer(this, 43, 0);
        this.ratFrontRightLeg2.mirror = true;
        this.ratFrontRightLeg2.setPos(0.0F, 1.5F, 0.2F);
        this.ratFrontRightLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratFrontRightLeg2, -0.23457224414434488F, 0.0F, 0.0F);
        this.ratHair3 = new ModelRenderer(this, 4, 26);
        this.ratHair3.setPos(0.2F, -1.9F, -0.2F);
        this.ratHair3.addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratHair3, 0.5082398928281348F, 0.0F, -1.4213961854347594F);
        this.mantle = new ModelRenderer(this, 16, 25);
        this.mantle.setPos(0.0F, -6.0F, 1.5F);
        this.mantle.addBox(-4.5F, 0.0F, 0.0F, 9.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mantle, 0.46914448828868976F, 0.0F, 0.0F);
        this.collar = new ModelRenderer(this, 13, 14);
        this.collar.setPos(0.0F, -6.0F, 0.0F);
        this.collar.addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.pumpkin3 = new ModelRenderer(this, 34, 27);
        this.pumpkin3.setPos(-2.49F, 0.99F, -2.5F);
        this.pumpkin3.addBox(0.0F, -2.5F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ratTail2 = new ModelRenderer(this, 20, 9);
        this.ratTail2.setPos(0.0F, 0.0F, 3.5F);
        this.ratTail2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratTail2, 0.19547687289441354F, 0.0F, 0.0F);
        this.ratHead = new ModelRenderer(this, 44, 0);
        this.ratHead.setPos(0.0F, -0.5F, -2.3F);
        this.ratHead.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratHead, 0.0781907508222411F, 0.0F, 0.0F);
        this.ratHair2 = new ModelRenderer(this, 4, 26);
        this.ratHair2.mirror = true;
        this.ratHair2.setPos(-2.5F, -1.3F, 0.0F);
        this.ratHair2.addBox(-3.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratHair2, 0.4300491170387584F, 0.11728612207217244F, -0.11746065899211351F);
        this.ratBackLeftLeg1 = new ModelRenderer(this, 56, 3);
        this.ratBackLeftLeg1.setPos(3.0F, 0.5F, 0.5F);
        this.ratBackLeftLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratBackLeftLeg1, 0.3127630032889644F, 0.0F, 0.0F);
        this.pumpkin1 = new ModelRenderer(this, 44, 22);
        this.pumpkin1.setPos(1.0F, 5.0F, -3.51F);
        this.pumpkin1.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pumpkin1, 1.586853323955311F, 0.0F, 0.27366763203903305F);
        this.ratTail1 = new ModelRenderer(this, 48, 6);
        this.ratTail1.setPos(0.0F, -0.5F, 2.5F);
        this.ratTail1.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratTail1, -0.35185837453889574F, 0.0F, 0.0F);
        this.ratTeeth = new ModelRenderer(this, 0, 17);
        this.ratTeeth.setPos(0.5F, 0.5F, -1.8F);
        this.ratTeeth.addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ratHair1 = new ModelRenderer(this, 4, 26);
        this.ratHair1.setPos(2.5F, -2.0F, 0.0F);
        this.ratHair1.addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ratHair1, 0.23457224414434488F, 0.11728612207217244F, 0.5082398928281348F);
        this.leftLeg = new ModelRenderer(this, 0, 10);
        this.leftLeg.setPos(1.5F, 0.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, -1.4144148417951712F, -0.35185837453889574F, 0.0F);
        this.ratNose.addChild(this.ratGlasses);
        this.ratBody1.addChild(this.ratBackRightLeg1);
        this.ratFrontLeftLeg2.addChild(this.ratFrontLeftLeg3);
        this.ratHead.addChild(this.ratNose);
        this.ratHead.addChild(this.ratLeftEar);
        this.ratBody2.addChild(this.ratFrontRightLeg1);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.leftArm);
        this.ratBackRightLeg1.addChild(this.ratBackRightLeg2);
        this.ratHead.addChild(this.ratRightEar);
        this.ratBackRightLeg2.addChild(this.ratBackRightLeg3);
        this.ratBody1.addChild(this.ratBody2);
        this.ratBackLeftLeg2.addChild(this.ratBackLeftLeg3);
        this.ratFrontLeftLeg1.addChild(this.ratFrontLeftLeg2);
        this.ratBody1.addChild(this.body);
        this.body.addChild(this.rightLeg);
        this.ratBody2.addChild(this.ratFrontLeftLeg1);
        this.ratBackLeftLeg1.addChild(this.ratBackLeftLeg2);
        this.ratFrontRightLeg2.addChild(this.ratFrontRightLeg3);
        this.pumpkin1.addChild(this.pumpkin2);
        this.pumpkin1.addChild(this.pumpkin4);
        this.ratFrontRightLeg1.addChild(this.ratFrontRightLeg2);
        this.ratBody2.addChild(this.ratHair3);
        this.body.addChild(this.mantle);
        this.body.addChild(this.collar);
        this.pumpkin1.addChild(this.pumpkin3);
        this.ratTail1.addChild(this.ratTail2);
        this.ratBody2.addChild(this.ratHead);
        this.ratBody1.addChild(this.ratHair2);
        this.ratBody1.addChild(this.ratBackLeftLeg1);
        this.leftArm.addChild(this.pumpkin1);
        this.ratBody1.addChild(this.ratTail1);
        this.ratNose.addChild(this.ratTeeth);
        this.ratBody1.addChild(this.ratHair1);
        this.body.addChild(this.leftLeg);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.ratBody1).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(HeadlessFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
            float pNetHeadYaw, float pHeadPitch) {

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
