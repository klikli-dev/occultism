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
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;


public class DjinniModel extends HumanoidModel<DjinniEntity> {
    //region Fields
    private final ModelPart leftHorn;
    private final ModelPart rightHorn;
    private final ModelPart leftEar;
    private final ModelPart leftInnerEar;
    private final ModelPart rightEar;
    private final ModelPart rightInnerEar;
    private final ModelPart wingedWings;
    private final ModelPart wingedWingsLeft;
    private final ModelPart wingedWingsRight;


    //endregion Fields

    //region Initialization
    public DjinniModel() {
        super(1.0f);
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.head = new ModelPart(this);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox("head", -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, 0, 14);

        this.hat = new ModelPart(this);
        this.hat.setPos(0.0F, 0.0F, 0.0F);

        this.leftHorn = new ModelPart(this);
        this.leftHorn.setPos(3.0F, -8.0F, -2.0F);
        this.setRotationAngle(this.leftHorn, 0.0F, 0.0F, 0.3491F);
        this.head.addChild(this.leftHorn);
        this.leftHorn.addBox("leftHorn", 0.0F, -2.0F, 0.0F, 1, 2, 1, 0.0F, 16, 43);
        this.leftHorn.addBox("leftHorn", 1.0F, -4.0F, 0.0F, 1, 3, 1, 0.0F, 41, 3);

        this.rightHorn = new ModelPart(this);
        this.rightHorn.setPos(-3.0F, -8.0F, -2.0F);
        this.setRotationAngle(this.rightHorn, 0.0F, 0.0F, -0.3491F);
        this.head.addChild(this.rightHorn);
        this.rightHorn.addBox("rightHorn", -1.0F, -2.0F, 0.0F, 1, 2, 1, 0.0F, 28, 42);
        this.rightHorn.addBox("rightHorn", -2.0F, -4.0F, 0.0F, 1, 3, 1, 0.0F, 0, 39);

        this.leftEar = new ModelPart(this);
        this.leftEar.setPos(5.0F, -4.0F, 1.0F);
        this.setRotationAngle(this.leftEar, 0.0F, 0.2618F, 0.0F);
        this.head.addChild(this.leftEar);
        this.leftEar.addBox("leftEar", 0.0F, -2.0F, -1.0F, 1, 2, 1, 0.0F, 40, 25);
        this.leftEar.addBox("leftEar", 0.0F, -3.0F, 0.0F, 1, 1, 1, 0.0F, 38, 31);
        this.leftEar.addBox("leftEar", 0.0F, -4.0F, 1.0F, 1, 1, 3, 0.0F, 12, 39);
        this.leftEar.addBox("leftEar", 0.0F, -3.0F, 4.0F, 1, 1, 1, 0.0F, 34, 31);
        this.leftEar.addBox("leftEar", 0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F, 37, 0);
        this.leftEar.addBox("leftEar", 0.0F, -1.0F, 2.0F, 1, 1, 1, 0.0F, 30, 31);
        this.leftEar.addBox("leftEar", 0.0F, -2.0F, 3.0F, 1, 1, 1, 0.0F, 34, 4);

        this.leftInnerEar = new ModelPart(this);
        this.leftInnerEar.setPos(-1.0F, 0.0F, -3.0F);
        this.leftEar.addChild(this.leftInnerEar);
        this.leftInnerEar.addBox("leftInnerEar", 0.0F, -2.0F, 3.0F, 1, 1, 3, 0.0F, 36, 4);
        this.leftInnerEar.addBox("leftInnerEar", 0.0F, -1.0F, 3.0F, 1, 1, 2, 0.0F, 24, 19);
        this.leftInnerEar.addBox("leftInnerEar", 0.0F, -3.0F, 4.0F, 1, 1, 3, 0.0F, 35, 25);

        this.rightEar = new ModelPart(this);
        this.rightEar.setPos(-5.0F, -4.0F, 1.0F);
        this.setRotationAngle(this.rightEar, 0.0F, -0.2618F, 0.0F);
        this.head.addChild(this.rightEar);
        this.rightEar.addBox("rightEar", -1.0F, -2.0F, -1.0F, 1, 2, 1, 0.0F, 17, 39);
        this.rightEar.addBox("rightEar", -1.0F, -3.0F, 0.0F, 1, 1, 1, 0.0F, 26, 31);
        this.rightEar.addBox("rightEar", -1.0F, -4.0F, 1.0F, 1, 1, 3, 0.0F, 24, 15);
        this.rightEar.addBox("rightEar", -1.0F, -3.0F, 4.0F, 1, 1, 1, 0.0F, 22, 31);
        this.rightEar.addBox("rightEar", -1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F, 0, 3);
        this.rightEar.addBox("rightEar", -1.0F, -1.0F, 2.0F, 1, 1, 1, 0.0F, 30, 4);
        this.rightEar.addBox("rightEar", -1.0F, -2.0F, 3.0F, 1, 1, 1, 0.0F, 28, 19);

        this.rightInnerEar = new ModelPart(this);
        this.rightInnerEar.setPos(-1.0F, 0.0F, -3.0F);
        this.rightEar.addChild(this.rightInnerEar);
        this.rightInnerEar.addBox("rightInnerEar", 1.0F, -2.0F, 3.0F, 1, 1, 3, 0.0F, 0, 18);
        this.rightInnerEar.addBox("rightInnerEar", 1.0F, -1.0F, 3.0F, 1, 1, 2, 0.0F, 0, 0);
        this.rightInnerEar.addBox("rightInnerEar", 1.0F, -3.0F, 4.0F, 1, 1, 3, 0.0F, 0, 14);

        this.bipedLeftArm = new ModelPart(this);
        this.bipedLeftArm.setPos(6.0F, 3.0F, -1.0F);
        this.setRotationAngle(this.bipedLeftArm, -0.75F, 0.0F, 0.0F);
        this.bipedLeftArm.addBox("bipedLeftArm", 0.0F, -1.6816F, -1.2683F, 3, 11, 3, 0.0F, 44, 45);

        this.bipedRightArm = new ModelPart(this);
        this.bipedRightArm.setPos(-6.0F, 3.0F, -1.0F);
        this.setRotationAngle(this.bipedRightArm, -0.75F, 0.0F, 0.0F);
        this.bipedRightArm.addBox("bipedLeftArm", -3.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F, 32, 45);

        this.bipedBody = new ModelPart(this);
        this.bipedBody.setPos(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox("bipedBody", -6.0F, 0.0F, -3.0F, 12, 8, 6, 0.0F, 0, 0);
        this.bipedBody.addBox("bipedBody", -4.0F, 8.0F, -3.0F, 8, 4, 6, 0.0F, 30, 8);

        this.wingedWings = new ModelPart(this);
        this.wingedWings.setPos(0.0F, 24.0F, 0.0F);
        this.bipedBody.addChild(this.wingedWings);

        this.wingedWingsLeft = new ModelPart(this);
        this.wingedWingsLeft.setPos(1.5F, -21.5F, 3.0F);
        this.setRotationAngle(this.wingedWingsLeft, 0.4363F, 0.8727F, 0.0F);
        this.wingedWings.addChild(this.wingedWingsLeft);
        this.wingedWingsLeft.addBox("wingedWingsLeft", -1.0F, -1.0F, 0.0F, 1, 1, 11, 0.0F, 33, 33);
        this.wingedWingsLeft.addBox("wingedWingsLeft", -0.1F, 0.0F, -0.4226F, 0, 9, 11, 0.0F, 22, 22);

        this.wingedWingsRight = new ModelPart(this);
        this.wingedWingsRight.setPos(-0.5F, -21.5F, 3.0F);
        this.setRotationAngle(this.wingedWingsRight, 0.4363F, -0.8727F, 0.0F);
        this.wingedWings.addChild(this.wingedWingsRight);
        this.wingedWingsRight.addBox("wingedWingsRight", -1.0F, -1.0F, 0.0F, 1, 1, 11, 0.0F, 22, 19);
        this.wingedWingsRight.addBox("wingedWingsRight", -0.1F, 0.0F, -0.4226F, 0, 9, 11, 0.0F, 0, 19);

        this.bipedLeftLeg = new ModelPart(this);
        this.bipedLeftLeg.setPos(-2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox("bipedLeftLeg", -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, 16, 42);

        this.bipedRightLeg = new ModelPart(this);
        this.bipedRightLeg.setPos(2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox("bipedRightLeg", -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, 0, 39);
    }
    //endregion Initialization

    //region Methods
    public void setRotationAngles(DjinniEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch) {

        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);


        //Copied from HumanoidModel, removed all code except that which accesses rotationPointX
        this.bipedRightArm.rotationPointX = -6.0F;
        this.bipedLeftArm.rotationPointX = 6.0F;

        if (this.swingProgress > 0.0F) {
            //original x needs to be used as the multiplier here
            this.bipedRightArm.rotationPointX = -Mth.cos(this.bipedBody.rotateAngleY) * 6.0F;
            this.bipedLeftArm.rotationPointX = Mth.cos(this.bipedBody.rotateAngleY) * 6.0F;
        }
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    //endregion Methods
}
