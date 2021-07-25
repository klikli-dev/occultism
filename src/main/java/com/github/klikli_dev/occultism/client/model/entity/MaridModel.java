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

import com.github.klikli_dev.occultism.common.entity.spirit.MaridEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;


public class MaridModel extends HumanoidModel<MaridEntity> {
    //region Fields
    public ModelPart nose;
    public ModelPart earLeft;
    public ModelPart earRight;

    //endregion Fields

    //region Initialization
    public MaridModel() {
        super(1.0f);
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);

        this.hat = new ModelPart(this, 1, 45);
        this.hat.setPos(0.0F, -3.0F, 0.0F);
        this.hat.addBox(-4.0F, -8.0F, -4.0F, 8, 10, 8, 0.5F);

        this.earLeft = new ModelPart(this, 0, 0);
        this.earLeft.setPos(4.0F, -11.3F, 3.5F);
        this.earLeft.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(this.earLeft, -0.5009094953223726F, 0.0F, 0.0F);

        this.earRight = new ModelPart(this, 0, 0);
        this.earRight.setPos(-4.0F, -11.3F, 3.5F);
        this.earRight.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(this.earRight, -0.5009094953223726F, 0.0F, 0.0F);

        this.nose = new ModelPart(this, 24, 0);
        this.nose.setPos(0.0F, -5.0F, -4.4F);
        this.nose.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(this.nose, -0.4553564018453205F, 0.0F, 0.0F);

        this.bipedLeftArm = new ModelPart(this, 44, 22);
        this.bipedLeftArm.setPos(5.0F, 3.0F, -1.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 11, 4, 0.0F);
        this.setRotateAngle(this.bipedLeftArm, -0.7499679795819634F, 0.0F, 0.0F);

        this.bipedRightArm = new ModelPart(this, 44, 22);
        this.bipedRightArm.setPos(-5.0F, 3.0F, -1.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(this.bipedRightArm, -0.7499679795819634F, 0.0F, 0.0F);

        this.bipedBody = new ModelPart(this, 16, 20);
        this.bipedBody.setPos(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);

        this.bipedLeftLeg = new ModelPart(this, 0, 22);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setPos(2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

        this.bipedRightLeg = new ModelPart(this, 0, 22);
        this.bipedRightLeg.setPos(-2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

        this.head.addChild(this.earRight);
        this.head.addChild(this.earLeft);
        this.head.addChild(this.nose);
    }
    //endregion Initialization

    //region Methods
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    //endregion Methods
}
