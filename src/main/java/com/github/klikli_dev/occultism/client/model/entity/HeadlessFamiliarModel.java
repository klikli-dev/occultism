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
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class HeadlessFamiliarModel extends EntityModel<HeadlessFamiliarEntity> {

    private static final float PI = (float) Math.PI;

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
    public ModelRenderer helmet;
    public ModelRenderer pumpkin1;
    public ModelRenderer pumpkin2;
    public ModelRenderer pumpkin3;
    public ModelRenderer pumpkin4;
    public ModelRenderer ratBackRightLeg2;
    public ModelRenderer ratBackRightLeg3;

    public HeadlessFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.leftArm = new ModelRenderer(this, 8, 10);
        this.leftArm.setPos(2.3F, -5.01F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftArm, -1.2901473511162753F, -0.3909537457888271F, 0.0F);
        this.ratGlasses = new ModelRenderer(this, 0, 22);
        this.ratGlasses.setPos(0.0F, -2.8F, -0.2F);
        this.ratGlasses.addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.helmet = new ModelRenderer(this, 0, 32);
        this.helmet.setPos(0.0F, -6.0F, 0.0F);
        this.helmet.addBox(-2.5F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.ratTail1 = new ModelRenderer(this, 48, 6);
        this.ratTail1.setPos(0.0F, -0.5F, 2.5F);
        this.ratTail1.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratTail1, -0.35185837453889574F, 0.0F, 0.0F);
        this.ratFrontRightLeg2 = new ModelRenderer(this, 43, 0);
        this.ratFrontRightLeg2.mirror = true;
        this.ratFrontRightLeg2.setPos(0.0F, 1.5F, 0.2F);
        this.ratFrontRightLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratFrontRightLeg2, -0.23457224414434488F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 10);
        this.rightLeg.setPos(-1.5F, 0.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightLeg, -1.4144148417951712F, 0.35185837453889574F, 0.0F);
        this.ratBackRightLeg2 = new ModelRenderer(this, 43, 0);
        this.ratBackRightLeg2.mirror = true;
        this.ratBackRightLeg2.setPos(0.0F, 1.5F, 0.4F);
        this.ratBackRightLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBackRightLeg2, -0.5473352640780661F, 0.0F, 0.0F);
        this.ratHair3 = new ModelRenderer(this, 4, 26);
        this.ratHair3.setPos(0.2F, -1.9F, -0.2F);
        this.ratHair3.addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratHair3, 0.5082398928281348F, 0.0F, -1.4213961854347594F);
        this.ratTeeth = new ModelRenderer(this, 0, 17);
        this.ratTeeth.setPos(0.5F, 0.5F, -1.8F);
        this.ratTeeth.addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 10);
        this.leftLeg.setPos(1.5F, 0.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftLeg, -1.4144148417951712F, -0.35185837453889574F, 0.0F);
        this.ratBackLeftLeg1 = new ModelRenderer(this, 56, 3);
        this.ratBackLeftLeg1.setPos(3.0F, 0.5F, 0.5F);
        this.ratBackLeftLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBackLeftLeg1, 0.3127630032889644F, 0.0F, 0.0F);
        this.ratFrontLeftLeg3 = new ModelRenderer(this, 44, 6);
        this.ratFrontLeftLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratFrontLeftLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratFrontLeftLeg3, 0.35185837453889574F, 0.0F, 0.0F);
        this.ratRightEar = new ModelRenderer(this, 0, 24);
        this.ratRightEar.mirror = true;
        this.ratRightEar.setPos(-1.5F, -1.5F, -1.5F);
        this.ratRightEar.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratRightEar, 0.0F, 0.0F, -0.5082398928281348F);
        this.pumpkin4 = new ModelRenderer(this, 34, 27);
        this.pumpkin4.mirror = true;
        this.pumpkin4.setPos(0.49F, 0.99F, -2.5F);
        this.pumpkin4.addBox(0.0F, -2.5F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 30, 9);
        this.body.setPos(0.0F, -4.0F, -1.0F);
        this.body.addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 8, 10);
        this.rightArm.mirror = true;
        this.rightArm.setPos(-2.3F, -5.01F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightArm, -1.5707963267948966F, 0.0F, 0.0F);
        this.ratHair2 = new ModelRenderer(this, 4, 26);
        this.ratHair2.mirror = true;
        this.ratHair2.setPos(-2.5F, -1.3F, 0.0F);
        this.ratHair2.addBox(-3.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratHair2, 0.4300491170387584F, 0.11728612207217244F, -0.11746065899211351F);
        this.mantle = new ModelRenderer(this, 16, 25);
        this.mantle.setPos(0.0F, -6.0F, 1.5F);
        this.mantle.addBox(-4.5F, 0.0F, 0.0F, 9.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.mantle, 0.46914448828868976F, 0.0F, 0.0F);
        this.ratBackRightLeg1 = new ModelRenderer(this, 56, 3);
        this.ratBackRightLeg1.mirror = true;
        this.ratBackRightLeg1.setPos(-3.0F, 0.5F, 0.5F);
        this.ratBackRightLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBackRightLeg1, 0.3127630032889644F, 0.0F, 0.0F);
        this.ratBackLeftLeg2 = new ModelRenderer(this, 43, 0);
        this.ratBackLeftLeg2.setPos(0.0F, 1.5F, 0.4F);
        this.ratBackLeftLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBackLeftLeg2, -0.5473352640780661F, 0.0F, 0.0F);
        this.ratBody2 = new ModelRenderer(this, 24, 0);
        this.ratBody2.setPos(0.0F, -0.1F, -5.0F);
        this.ratBody2.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBody2, 0.1563815016444822F, 0.0F, 0.0F);
        this.ratFrontRightLeg3 = new ModelRenderer(this, 44, 6);
        this.ratFrontRightLeg3.mirror = true;
        this.ratFrontRightLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratFrontRightLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratFrontRightLeg3, 0.35185837453889574F, 0.0F, 0.0F);
        this.pumpkin1 = new ModelRenderer(this, 44, 22);
        this.pumpkin1.setPos(1.0F, 5.0F, -3.51F);
        this.pumpkin1.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.pumpkin1, 1.586853323955311F, 0.0F, 0.27366763203903305F);
        this.ratBody1 = new ModelRenderer(this, 0, 0);
        this.ratBody1.setPos(0.0F, 19.4F, 3.0F);
        this.ratBody1.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBody1, -0.0781907508222411F, 0.0F, 0.0F);
        this.ratHead = new ModelRenderer(this, 44, 0);
        this.ratHead.setPos(0.0F, -0.5F, -2.3F);
        this.ratHead.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratHead, 0.0781907508222411F, 0.0F, 0.0F);
        this.pumpkin2 = new ModelRenderer(this, 56, 7);
        this.pumpkin2.setPos(0.0F, -2.0F, 0.0F);
        this.pumpkin2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.pumpkin2, 0.23457224414434488F, 0.5082398928281348F, 0.27366763203903305F);
        this.ratFrontLeftLeg2 = new ModelRenderer(this, 43, 0);
        this.ratFrontLeftLeg2.setPos(0.0F, 1.5F, 0.2F);
        this.ratFrontLeftLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratFrontLeftLeg2, -0.23457224414434488F, 0.0F, 0.0F);
        this.ratFrontLeftLeg1 = new ModelRenderer(this, 56, 3);
        this.ratFrontLeftLeg1.setPos(2.5F, 0.8F, -0.5F);
        this.ratFrontLeftLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratFrontLeftLeg1, -0.19547687289441354F, 0.0F, 0.0F);
        this.ratBackLeftLeg3 = new ModelRenderer(this, 44, 6);
        this.ratBackLeftLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratBackLeftLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBackLeftLeg3, 0.3127630032889644F, 0.0F, 0.0F);
        this.ratLeftEar = new ModelRenderer(this, 0, 24);
        this.ratLeftEar.setPos(1.5F, -1.5F, -1.5F);
        this.ratLeftEar.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratLeftEar, 0.0F, 0.0F, 0.5082398928281348F);
        this.ratHair1 = new ModelRenderer(this, 4, 26);
        this.ratHair1.setPos(2.5F, -2.0F, 0.0F);
        this.ratHair1.addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratHair1, 0.23457224414434488F, 0.11728612207217244F, 0.5082398928281348F);
        this.pumpkin3 = new ModelRenderer(this, 34, 27);
        this.pumpkin3.setPos(-2.49F, 0.99F, -2.5F);
        this.pumpkin3.addBox(0.0F, -2.5F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ratTail2 = new ModelRenderer(this, 20, 9);
        this.ratTail2.setPos(0.0F, 0.0F, 3.5F);
        this.ratTail2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratTail2, 0.19547687289441354F, 0.0F, 0.0F);
        this.ratFrontRightLeg1 = new ModelRenderer(this, 56, 3);
        this.ratFrontRightLeg1.setPos(-2.5F, 0.8F, -0.5F);
        this.ratFrontRightLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratFrontRightLeg1, -0.19547687289441354F, 0.0F, 0.0F);
        this.ratNose = new ModelRenderer(this, 18, 0);
        this.ratNose.setPos(0.0F, 0.2F, -3.0F);
        this.ratNose.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.ratBackRightLeg3 = new ModelRenderer(this, 44, 6);
        this.ratBackRightLeg3.setPos(0.0F, 1.7F, 0.4F);
        this.ratBackRightLeg3.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.ratBackRightLeg3, 0.3127630032889644F, 0.0F, 0.0F);
        this.collar = new ModelRenderer(this, 13, 14);
        this.collar.setPos(0.0F, -6.0F, 0.0F);
        this.collar.addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.leftArm);
        this.ratNose.addChild(this.ratGlasses);
        this.body.addChild(this.helmet);
        this.ratBody1.addChild(this.ratTail1);
        this.ratFrontRightLeg1.addChild(this.ratFrontRightLeg2);
        this.body.addChild(this.rightLeg);
        this.ratBackRightLeg1.addChild(this.ratBackRightLeg2);
        this.ratBody2.addChild(this.ratHair3);
        this.ratNose.addChild(this.ratTeeth);
        this.body.addChild(this.leftLeg);
        this.ratBody1.addChild(this.ratBackLeftLeg1);
        this.ratFrontLeftLeg2.addChild(this.ratFrontLeftLeg3);
        this.ratHead.addChild(this.ratRightEar);
        this.pumpkin1.addChild(this.pumpkin4);
        this.ratBody1.addChild(this.body);
        this.body.addChild(this.rightArm);
        this.ratBody1.addChild(this.ratHair2);
        this.body.addChild(this.mantle);
        this.ratBody1.addChild(this.ratBackRightLeg1);
        this.ratBackLeftLeg1.addChild(this.ratBackLeftLeg2);
        this.ratBody1.addChild(this.ratBody2);
        this.ratFrontRightLeg2.addChild(this.ratFrontRightLeg3);
        this.leftArm.addChild(this.pumpkin1);
        this.ratBody2.addChild(this.ratHead);
        this.pumpkin1.addChild(this.pumpkin2);
        this.ratFrontLeftLeg1.addChild(this.ratFrontLeftLeg2);
        this.ratBody2.addChild(this.ratFrontLeftLeg1);
        this.ratBackLeftLeg2.addChild(this.ratBackLeftLeg3);
        this.ratHead.addChild(this.ratLeftEar);
        this.ratBody1.addChild(this.ratHair1);
        this.pumpkin1.addChild(this.pumpkin3);
        this.ratTail1.addChild(this.ratTail2);
        this.ratBody2.addChild(this.ratFrontRightLeg1);
        this.ratHead.addChild(this.ratNose);
        this.ratBackRightLeg2.addChild(this.ratBackRightLeg3);
        this.body.addChild(this.collar);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
                               int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.ratBody1).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(HeadlessFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        this.showModels(pEntity);

        this.ratBody1.xRot = -0.078f;
        this.ratTail1.xRot = -0.195f;
        this.body.xRot = 0;
        this.ratHead.zRot = 0;
        this.ratTail1.zRot = 0;
        this.pumpkin1.z = -3.51f;
        this.pumpkin1.yRot = 0;

        this.ratHead.xRot = this.toRads(pHeadPitch) * 0.4f;
        this.ratHead.yRot = this.toRads(pNetHeadYaw) * 0.4f;

        this.ratTail1.yRot = MathHelper.sin(pAgeInTicks * 0.2f) * this.toRads(15);
        this.ratTail2.yRot = MathHelper.sin(pAgeInTicks * 0.2f) * this.toRads(15);

        this.ratBackLeftLeg1.xRot = 0.31f + MathHelper.cos(limbSwing * 0.7f + PI) * limbSwingAmount * 0.5f;
        this.ratBackRightLeg1.xRot = 0.31f + MathHelper.cos(limbSwing * 0.7f) * limbSwingAmount * 0.5f;
        this.ratFrontLeftLeg1.xRot = -0.20f + MathHelper.cos(limbSwing * 0.7f) * limbSwingAmount * 0.5f;
        this.ratFrontRightLeg1.xRot = -0.20f + MathHelper.cos(limbSwing * 0.7f + PI) * limbSwingAmount * 0.5f;
        this.rightArm.xRot = -1.57f + MathHelper.cos(limbSwing * 0.4f) * limbSwingAmount * 0.2f;

        if (this.attackTime > 0)
            this.rightArm.xRot = -1.57f + MathHelper.sin(this.attackTime * this.toRads(180)) * this.toRads(90);

        if (pEntity.isSitting()) {
            this.ratBody1.xRot = -this.toRads(40);
            this.ratHead.xRot += this.toRads(20);
            this.ratBackLeftLeg1.xRot = -this.toRads(10);
            this.ratBackRightLeg1.xRot = -this.toRads(10);
            this.ratFrontLeftLeg1.xRot = this.toRads(25);
            this.ratFrontRightLeg1.xRot = this.toRads(25);
            this.ratTail1.xRot = this.toRads(35);
            this.body.xRot = this.toRads(20);
        }

        if (pEntity.isPartying()) {
            this.ratHead.zRot = MathHelper.cos(pAgeInTicks * 0.5f) * this.toRads(40);
            this.ratTail1.zRot = MathHelper.cos(pAgeInTicks * 0.5f) * this.toRads(40);
            this.pumpkin1.z = -7f + MathHelper.cos(pAgeInTicks * 0.25f) * 2;
            this.pumpkin1.yRot = pAgeInTicks * this.toRads(10);
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(HeadlessFamiliarEntity entityIn) {
        boolean isHairy = entityIn.isHairy();

        this.pumpkin1.visible = !entityIn.hasHead();
        this.ratGlasses.visible = entityIn.hasGlasses();
        this.helmet.visible = entityIn.hasBlacksmithUpgrade();
        this.body.visible = !entityIn.isHeadlessDead();
        this.ratHair1.visible = isHairy;
        this.ratHair2.visible = isHairy;
        this.ratHair3.visible = isHairy;
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
