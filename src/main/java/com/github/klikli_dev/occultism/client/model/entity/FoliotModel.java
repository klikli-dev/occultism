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

import com.github.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FoliotModel extends BipedModel<FoliotEntity> {
    //region Fields
    private final ModelRenderer leftHorn;
    private final ModelRenderer rightHorn;
    //endregion Fields

    //region Initialization
    public FoliotModel() {
        super(1.0f);
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.texWidth = 64;
        this.texHeight = 64;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox("bipedHead", -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, 0, 0);

        this.hat = new ModelRenderer(this);
        this.hat.setPos(0.0F, 0.0F, 0.0F);
        this.hat.addBox("bipedHeadWear", -5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F, 24, 44);

        this.leftHorn = new ModelRenderer(this);
        this.leftHorn.setPos(3.5F, -8.5F, -1.5F);
        this.head.addChild(this.leftHorn);
        this.leftHorn.addBox("leftHorn", 0.5F, -0.5F, -2.5F, 1, 1, 6, 0.0F, 24, 0);
        this.leftHorn.addBox("leftHorn", 0.5F, 4.5F, -1.5F, 1, 1, 5, 0.0F, 32, 0);
        this.leftHorn.addBox("leftHorn", 0.5F, 5.5F, -0.5F, 1, 1, 3, 0.0F, 0, 0);
        this.leftHorn.addBox("leftHorn", 0.5F, 0.5F, 1.5F, 1, 1, 3, 0.0F, 39, 0);
        this.leftHorn.addBox("leftHorn", 0.5F, 1.5F, 2.5F, 1, 1, 3, 0.0F, 37, 6);
        this.leftHorn.addBox("leftHorn", 0.5F, 2.5F, 2.5F, 1, 1, 3, 0.0F, 32, 7);
        this.leftHorn.addBox("leftHorn", 0.5F, 3.5F, 1.5F, 1, 1, 3, 0.0F, 0, 4);
        this.leftHorn.addBox("leftHorn", 0.5F, 0.5F, -2.5F, 1, 1, 2, 0.0F, 0, 16);
        this.leftHorn.addBox("leftHorn", 0.5F, 3.5F, -1.5F, 1, 1, 1, 0.0F, 22, 20);
        this.leftHorn.addBox("leftHorn", 0.5F, 1.5F, -2.5F, 1, 1, 1, 0.0F, 0, 19);

        this.rightHorn = new ModelRenderer(this);
        this.rightHorn.setPos(-5.5F, -8.5F, -1.5F);
        this.head.addChild(this.rightHorn);
        this.rightHorn.addBox("rightHorn", 0.5F, -0.5F, -2.5F, 1, 1, 6, 0.0F, 24, 0);
        this.rightHorn.addBox("rightHorn", 0.5F, 4.5F, -1.5F, 1, 1, 5, 0.0F, 32, 0);
        this.rightHorn.addBox("rightHorn", 0.5F, 5.5F, -0.5F, 1, 1, 3, 0.0F, 0, 0);
        this.rightHorn.addBox("rightHorn", 0.5F, 0.5F, 1.5F, 1, 1, 3, 0.0F, 39, 0);
        this.rightHorn.addBox("rightHorn", 0.5F, 1.5F, 2.5F, 1, 1, 3, 0.0F, 37, 6);
        this.rightHorn.addBox("rightHorn", 0.5F, 2.5F, 2.5F, 1, 1, 3, 0.0F, 32, 7);
        this.rightHorn.addBox("rightHorn", 0.5F, 3.5F, 1.5F, 1, 1, 3, 0.0F, 0, 4);
        this.rightHorn.addBox("rightHorn", 0.5F, 0.5F, -2.5F, 1, 1, 2, 0.0F, 0, 16);
        this.rightHorn.addBox("rightHorn", 0.5F, 3.5F, -1.5F, 1, 1, 1, 0.0F, 22, 20);
        this.rightHorn.addBox("rightHorn", 0.5F, 1.5F, -2.5F, 1, 1, 1, 0.0F, 0, 19);

        this.rightArm = new ModelRenderer(this);
        this.rightArm.setPos(5.0F, 3.0F, -1.0F);
        this.setRotateAngle(this.rightArm, -0.75F, 0.0F, 0.0F);
        this.rightArm.addBox("bipedRightArm", -1.0F, -1.6816F, -1.2683F, 3, 11, 3, 0.0F, 12, 34);

        this.leftArm = new ModelRenderer(this);

        this.leftArm.setPos(-5.0F, 3.0F, -1.0F);
        this.setRotateAngle(this.leftArm, -0.75F, 0.0F, 0.0F);
        this.leftArm.addBox("bipedLeftArm", -2.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F, 0, 34);

        this.leftLeg = new ModelRenderer(this);
        this.leftLeg.setPos(-2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox("bipedLeftLeg", -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, 28, 12);

        this.body = new ModelRenderer(this);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.body.addBox("bipedBody", -4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F, 0, 16);

        this.rightLeg = new ModelRenderer(this);
        this.rightLeg.setPos(2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox("bipedRightLeg", -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, 28, 28);
    }
    //endregion Initialization

    //region Methods
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
    //endregion Methods
}
