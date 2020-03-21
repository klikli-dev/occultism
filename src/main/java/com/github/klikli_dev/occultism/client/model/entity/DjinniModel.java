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

package com.github.klikli_dev.occultism.client.model.entity;

import com.github.klikli_dev.occultism.common.entity.spirit.DjinniEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;


public class DjinniModel extends BipedModel<DjinniEntity> {
    //region Fields
    private final ModelRenderer leftHorn;
    private final ModelRenderer rightHorn;
    private final ModelRenderer leftEar;
    private final ModelRenderer leftInnerEar;
    private final ModelRenderer rightEar;
    private final ModelRenderer rightInnerEar;
    private final ModelRenderer wingedWings;
    private final ModelRenderer wingedWingsLeft;
    private final ModelRenderer wingedWingsRight;


    //endregion Fields

    //region Initialization
    public DjinniModel() {
        super(1.0f);
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.bipedHead = new ModelRenderer(this);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox("bipedHead", -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, 0, 14);

        this.bipedHeadwear = new ModelRenderer(this);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.leftHorn = new ModelRenderer(this);
        this.leftHorn.setRotationPoint(3.0F, -8.0F, -2.0F);
        this.setRotationAngle(this.leftHorn, 0.0F, 0.0F, 0.3491F);
        this.bipedHead.addChild(this.leftHorn);
        this.leftHorn.addBox("leftHorn", 0.0F, -2.0F, 0.0F, 1, 2, 1, 0.0F, 16, 43);
        this.leftHorn.addBox("leftHorn", 1.0F, -4.0F, 0.0F, 1, 3, 1, 0.0F, 41, 3);

        this.rightHorn = new ModelRenderer(this);
        this.rightHorn.setRotationPoint(-3.0F, -8.0F, -2.0F);
        this.setRotationAngle(this.rightHorn, 0.0F, 0.0F, -0.3491F);
        this.bipedHead.addChild(this.rightHorn);
        this.rightHorn.addBox("rightHorn", -1.0F, -2.0F, 0.0F, 1, 2, 1, 0.0F, 28, 42);
        this.rightHorn.addBox("rightHorn", -2.0F, -4.0F, 0.0F, 1, 3, 1, 0.0F, 0, 39);

        this.leftEar = new ModelRenderer(this);
        this.leftEar.setRotationPoint(5.0F, -4.0F, 1.0F);
        this.setRotationAngle(this.leftEar, 0.0F, 0.2618F, 0.0F);
        this.bipedHead.addChild(this.leftEar);
        this.leftEar.addBox("leftEar", 0.0F, -2.0F, -1.0F, 1, 2, 1, 0.0F, 40, 25);
        this.leftEar.addBox("leftEar", 0.0F, -3.0F, 0.0F, 1, 1, 1, 0.0F, 38, 31);
        this.leftEar.addBox("leftEar", 0.0F, -4.0F, 1.0F, 1, 1, 3, 0.0F, 12, 39);
        this.leftEar.addBox("leftEar", 0.0F, -3.0F, 4.0F, 1, 1, 1, 0.0F, 34, 31);
        this.leftEar.addBox("leftEar", 0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F, 37, 0);
        this.leftEar.addBox("leftEar", 0.0F, -1.0F, 2.0F, 1, 1, 1, 0.0F, 30, 31);
        this.leftEar.addBox("leftEar", 0.0F, -2.0F, 3.0F, 1, 1, 1, 0.0F, 34, 4);

        this.leftInnerEar = new ModelRenderer(this);
        this.leftInnerEar.setRotationPoint(-1.0F, 0.0F, -3.0F);
        this.leftEar.addChild(this.leftInnerEar);
        this.leftInnerEar.addBox("leftInnerEar", 0.0F, -2.0F, 3.0F, 1, 1, 3, 0.0F, 36, 4);
        this.leftInnerEar.addBox("leftInnerEar", 0.0F, -1.0F, 3.0F, 1, 1, 2, 0.0F, 24, 19);
        this.leftInnerEar.addBox("leftInnerEar", 0.0F, -3.0F, 4.0F, 1, 1, 3, 0.0F, 35, 25);

        this.rightEar = new ModelRenderer(this);
        this.rightEar.setRotationPoint(-5.0F, -4.0F, 1.0F);
        this.setRotationAngle(this.rightEar, 0.0F, -0.2618F, 0.0F);
        this.bipedHead.addChild(this.rightEar);
        this.rightEar.addBox("rightEar", -1.0F, -2.0F, -1.0F, 1, 2, 1, 0.0F, 17, 39);
        this.rightEar.addBox("rightEar", -1.0F, -3.0F, 0.0F, 1, 1, 1, 0.0F, 26, 31);
        this.rightEar.addBox("rightEar", -1.0F, -4.0F, 1.0F, 1, 1, 3, 0.0F, 24, 15);
        this.rightEar.addBox("rightEar", -1.0F, -3.0F, 4.0F, 1, 1, 1, 0.0F, 22, 31);
        this.rightEar.addBox("rightEar", -1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F, 0, 3);
        this.rightEar.addBox("rightEar", -1.0F, -1.0F, 2.0F, 1, 1, 1, 0.0F, 30, 4);
        this.rightEar.addBox("rightEar", -1.0F, -2.0F, 3.0F, 1, 1, 1, 0.0F, 28, 19);

        this.rightInnerEar = new ModelRenderer(this);
        this.rightInnerEar.setRotationPoint(-1.0F, 0.0F, -3.0F);
        this.rightEar.addChild(this.rightInnerEar);
        this.rightInnerEar.addBox("rightInnerEar", 1.0F, -2.0F, 3.0F, 1, 1, 3, 0.0F, 0, 18);
        this.rightInnerEar.addBox("rightInnerEar", 1.0F, -1.0F, 3.0F, 1, 1, 2, 0.0F, 0, 0);
        this.rightInnerEar.addBox("rightInnerEar", 1.0F, -3.0F, 4.0F, 1, 1, 3, 0.0F, 0, 14);

        this.bipedRightArm = new ModelRenderer(this);
        this.bipedRightArm.setRotationPoint(6.0F, 3.0F, -1.0F);
        this.setRotationAngle(this.bipedRightArm, -0.75F, 0.0F, 0.0F);
        this.bipedRightArm.addBox("bipedRightArm", 0.0F, -1.6816F, -1.2683F, 3, 11, 3, 0.0F, 44, 45);

        this.bipedLeftArm = new ModelRenderer(this);
        this.bipedLeftArm.setRotationPoint(-6.0F, 3.0F, -1.0F);
        this.setRotationAngle(this.bipedLeftArm, -0.75F, 0.0F, 0.0F);
        this.bipedLeftArm.addBox("bipedLeftArm", -3.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F, 32, 45);

        this.bipedBody = new ModelRenderer(this);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox("bipedBody", -6.0F, 0.0F, -3.0F, 12, 8, 6, 0.0F, 0, 0);
        this.bipedBody.addBox("bipedBody", -4.0F, 8.0F, -3.0F, 8, 4, 6, 0.0F, 30, 8);

        this.wingedWings = new ModelRenderer(this);
        this.wingedWings.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.bipedBody.addChild(this.wingedWings);

        this.wingedWingsLeft = new ModelRenderer(this);
        this.wingedWingsLeft.setRotationPoint(1.5F, -21.5F, 3.0F);
        this.setRotationAngle(this.wingedWingsLeft, 0.4363F, 0.8727F, 0.0F);
        this.wingedWings.addChild(this.wingedWingsLeft);
        this.wingedWingsLeft.addBox("wingedWingsLeft", -1.0F, -1.0F, 0.0F, 1, 1, 11, 0.0F, 33, 33);
        this.wingedWingsLeft.addBox("wingedWingsLeft", -0.1F, 0.0F, -0.4226F, 0, 9, 11, 0.0F, 22, 22);

        this.wingedWingsRight = new ModelRenderer(this);
        this.wingedWingsRight.setRotationPoint(-0.5F, -21.5F, 3.0F);
        this.setRotationAngle(this.wingedWingsRight, 0.4363F, -0.8727F, 0.0F);
        this.wingedWings.addChild(this.wingedWingsRight);
        this.wingedWingsRight.addBox("wingedWingsRight", -1.0F, -1.0F, 0.0F, 1, 1, 11, 0.0F, 22, 19);
        this.wingedWingsRight.addBox("wingedWingsRight", -0.1F, 0.0F, -0.4226F, 0, 9, 11, 0.0F, 0, 19);

        this.bipedLeftLeg = new ModelRenderer(this);
        this.bipedLeftLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox("bipedLeftLeg", -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, 16, 42);

        this.bipedRightLeg = new ModelRenderer(this);
        this.bipedRightLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox("bipedRightLeg", -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, 0, 39);
    }
    //endregion Initialization

    //region Methods
    public void setRotationAngles(DjinniEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch) {

        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);


        //Copied from BipedModel, removed all code except that which accesses rotationPointX
        this.bipedRightArm.rotationPointX = -6.0F;
        this.bipedLeftArm.rotationPointX = 6.0F;

        if (this.swingProgress > 0.0F) {
            HandSide handside = this.getMainHand(entityIn);
            ModelRenderer modelrenderer = this.getArmForSide(handside);
            float f1 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
            if (handside == HandSide.LEFT) {
                this.bipedBody.rotateAngleY *= -1.0F;
            }

            //original x needs to be used as the multiplier here
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 6.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 6.0F;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    //endregion Methods
}
