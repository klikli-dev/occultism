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

import com.github.klikli_dev.occultism.common.entity.FairyFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class FairyFamiliarModel extends EntityModel<FairyFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer tail1;
    public ModelRenderer leftArm;
    public ModelRenderer leftLeg1;
    public ModelRenderer leftWing;
    public ModelRenderer rightLeg1;
    public ModelRenderer rightArm;
    public ModelRenderer rightWing;
    public ModelRenderer nose;
    public ModelRenderer leftHorn1;
    public ModelRenderer leftEar1;
    public ModelRenderer leftEye;
    public ModelRenderer rightEye;
    public ModelRenderer rightEar1;
    public ModelRenderer rightHorn1;
    public ModelRenderer whisker1;
    public ModelRenderer whisker2;
    public ModelRenderer whisker3;
    public ModelRenderer whisker4;
    public ModelRenderer tooth1;
    public ModelRenderer tooth2;
    public ModelRenderer tooth3;
    public ModelRenderer leftHorn2;
    public ModelRenderer flower;
    public ModelRenderer leftEar2;
    public ModelRenderer rightEar2;
    public ModelRenderer rightHorn2;
    public ModelRenderer tail2;
    public ModelRenderer tail3;
    public ModelRenderer leftWand;
    public ModelRenderer leftLeg2;
    public ModelRenderer leftLeg3;
    public ModelRenderer rightLeg2;
    public ModelRenderer rightLeg3;
    public ModelRenderer rightWand;

    public FairyFamiliarModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 64;
        this.texHeight = 32;
        this.leftHorn2 = new ModelRenderer(this, 13, 0);
        this.leftHorn2.setPos(0.01F, -1.5F, 0.0F);
        this.leftHorn2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn2, -0.35185837453889574F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 53, 8);
        this.rightArm.mirror = true;
        this.rightArm.setPos(-2.5F, -5.0F, 0.0F);
        this.rightArm.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar2 = new ModelRenderer(this, 0, 16);
        this.rightEar2.mirror = true;
        this.rightEar2.setPos(0.0F, 0.0F, 0.0F);
        this.rightEar2.addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar2, -2.1111502472333745F, 0.0F, 0.0F);
        this.tooth2 = new ModelRenderer(this, 31, 0);
        this.tooth2.setPos(-1.4F, 0.5F, -1.7F);
        this.tooth2.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 53, 8);
        this.leftArm.setPos(2.5F, -5.0F, 0.0F);
        this.leftArm.addBox(0.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn1 = new ModelRenderer(this, 48, 0);
        this.rightHorn1.mirror = true;
        this.rightHorn1.setPos(-2.2F, -4.5F, 1.0F);
        this.rightHorn1.addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn1, -0.3909537457888271F, 0.0F, 0.0F);
        this.leftWing = new ModelRenderer(this, 0, 21);
        this.leftWing.setPos(0.0F, -4.0F, 1.0F);
        this.leftWing.addBox(0.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing, 0.0F, -0.5864306020384839F, 0.0F);
        this.rightLeg1 = new ModelRenderer(this, 0, 9);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setPos(-1.2F, -0.8F, 0.0F);
        this.rightLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn1 = new ModelRenderer(this, 48, 0);
        this.leftHorn1.setPos(2.2F, -4.5F, 1.0F);
        this.leftHorn1.addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn1, -0.3909537457888271F, 0.0F, 0.0F);
        this.rightEar1 = new ModelRenderer(this, 0, 16);
        this.rightEar1.mirror = true;
        this.rightEar1.setPos(-2.1F, -3.4F, 1.9F);
        this.rightEar1.addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar1, 0.3127630032889644F, 0.27366763203903305F, 0.4300491170387584F);
        this.rightLeg3 = new ModelRenderer(this, 17, 0);
        this.rightLeg3.setPos(0.3F, 4.01F, -0.3F);
        this.rightLeg3.addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg3, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightWing = new ModelRenderer(this, 0, 21);
        this.rightWing.mirror = true;
        this.rightWing.setPos(0.0F, -4.0F, 1.0F);
        this.rightWing.addBox(-10.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing, 0.0F, 0.5864306020384839F, 0.0F);
        this.whisker1 = new ModelRenderer(this, 0, 17);
        this.whisker1.setPos(1.0F, 0.4F, -1.0F);
        this.whisker1.addBox(0.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(whisker1, 0.0F, 0.3127630032889644F, 0.3909537457888271F);
        this.tail2 = new ModelRenderer(this, 36, 6);
        this.tail2.setPos(0.01F, 0.0F, 2.5F);
        this.tail2.addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail2, 0.27366763203903305F, 0.0F, 0.0F);
        this.tail3 = new ModelRenderer(this, 55, 4);
        this.tail3.setPos(0.0F, 0.0F, 2.5F);
        this.tail3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail3, 0.27366763203903305F, 0.0F, 0.0F);
        this.whisker3 = new ModelRenderer(this, 0, 17);
        this.whisker3.mirror = true;
        this.whisker3.setPos(-1.0F, -0.4F, -2.0F);
        this.whisker3.addBox(-3.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(whisker3, 0.03909537541112055F, -0.3909537457888271F, 0.27366763203903305F);
        this.rightEye = new ModelRenderer(this, 54, 0);
        this.rightEye.mirror = true;
        this.rightEye.setPos(-2.5F, -3.0F, -0.7F);
        this.rightEye.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEye, 0.3127630032889644F, 0.0F, 0.0F);
        this.tooth1 = new ModelRenderer(this, 31, 0);
        this.tooth1.setPos(1.4F, 0.4F, -1.5F);
        this.tooth1.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 16, 0);
        this.head.setPos(0.0F, -6.0F, -0.5F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 36, 0);
        this.nose.setPos(0.0F, -1.5F, -2.5F);
        this.nose.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar1 = new ModelRenderer(this, 0, 16);
        this.leftEar1.setPos(2.1F, -3.4F, 1.9F);
        this.leftEar1.addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar1, 0.3127630032889644F, -0.27366763203903305F, -0.4300491170387584F);
        this.tail1 = new ModelRenderer(this, 45, 4);
        this.tail1.setPos(0.0F, -1.0F, 1.0F);
        this.tail1.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail1, -0.5082398928281348F, 0.0F, 0.0F);
        this.leftEar2 = new ModelRenderer(this, 0, 16);
        this.leftEar2.setPos(0.0F, 0.0F, 0.0F);
        this.leftEar2.addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar2, -2.1111502472333745F, 0.0F, 0.0F);
        this.rightHorn2 = new ModelRenderer(this, 13, 0);
        this.rightHorn2.mirror = true;
        this.rightHorn2.setPos(0.01F, -1.5F, 0.0F);
        this.rightHorn2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn2, -0.35185837453889574F, 0.0F, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 0, 9);
        this.leftLeg1.setPos(1.2F, -0.8F, 0.0F);
        this.leftLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.flower = new ModelRenderer(this, 25, 7);
        this.flower.setPos(0.52F, -1.0F, 0.0F);
        this.flower.addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(flower, 0.782431135626991F, 0.0F, 0.0F);
        this.leftLeg3 = new ModelRenderer(this, 17, 0);
        this.leftLeg3.setPos(0.3F, 4.01F, -0.3F);
        this.leftLeg3.addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg3, 0.0F, 0.7853981633974483F, 0.0F);
        this.leftEye = new ModelRenderer(this, 54, 0);
        this.leftEye.setPos(2.5F, -3.0F, -0.7F);
        this.leftEye.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEye, 0.3127630032889644F, 0.0F, 0.0F);
        this.rightWand = new ModelRenderer(this, 8, 9);
        this.rightWand.mirror = true;
        this.rightWand.setPos(-1.0F, 4.0F, -0.2F);
        this.rightWand.addBox(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg2 = new ModelRenderer(this, 17, 0);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setPos(-0.3F, 4.01F, -0.3F);
        this.rightLeg2.addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, 0.0F, 0.7853981633974483F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 20.0F, 0.0F);
        this.body.addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 17, 0);
        this.leftLeg2.setPos(-0.3F, 4.01F, -0.3F);
        this.leftLeg2.addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, 0.0F, 0.7853981633974483F, 0.0F);
        this.whisker4 = new ModelRenderer(this, 0, 17);
        this.whisker4.mirror = true;
        this.whisker4.setPos(-1.0F, 0.4F, -1.0F);
        this.whisker4.addBox(-3.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(whisker4, 0.0F, -0.3127630032889644F, -0.3909537457888271F);
        this.leftWand = new ModelRenderer(this, 8, 9);
        this.leftWand.setPos(1.0F, 4.0F, -0.2F);
        this.leftWand.addBox(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.tooth3 = new ModelRenderer(this, 31, 0);
        this.tooth3.setPos(0.7F, 0.6F, -2.9F);
        this.tooth3.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.whisker2 = new ModelRenderer(this, 0, 17);
        this.whisker2.setPos(1.0F, -0.4F, -2.0F);
        this.whisker2.addBox(0.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(whisker2, 0.03909537541112055F, 0.3909537457888271F, -0.27366763203903305F);
        this.leftHorn1.addChild(this.leftHorn2);
        this.body.addChild(this.rightArm);
        this.rightEar1.addChild(this.rightEar2);
        this.nose.addChild(this.tooth2);
        this.body.addChild(this.leftArm);
        this.head.addChild(this.rightHorn1);
        this.body.addChild(this.leftWing);
        this.body.addChild(this.rightLeg1);
        this.head.addChild(this.leftHorn1);
        this.head.addChild(this.rightEar1);
        this.rightLeg1.addChild(this.rightLeg3);
        this.body.addChild(this.rightWing);
        this.nose.addChild(this.whisker1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.nose.addChild(this.whisker3);
        this.head.addChild(this.rightEye);
        this.nose.addChild(this.tooth1);
        this.body.addChild(this.head);
        this.head.addChild(this.nose);
        this.head.addChild(this.leftEar1);
        this.body.addChild(this.tail1);
        this.leftEar1.addChild(this.leftEar2);
        this.rightHorn1.addChild(this.rightHorn2);
        this.body.addChild(this.leftLeg1);
        this.leftHorn1.addChild(this.flower);
        this.leftLeg1.addChild(this.leftLeg3);
        this.head.addChild(this.leftEye);
        this.rightArm.addChild(this.rightWand);
        this.rightLeg1.addChild(this.rightLeg2);
        this.leftLeg1.addChild(this.leftLeg2);
        this.nose.addChild(this.whisker4);
        this.leftArm.addChild(this.leftWand);
        this.nose.addChild(this.tooth3);
        this.nose.addChild(this.whisker2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(FairyFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
            float pNetHeadYaw, float pHeadPitch) {
        showModels(pEntity);
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(FairyFamiliarEntity entityIn) {
        boolean hasTeeth = entityIn.hasTeeth();
        boolean isLeftHanded = entityIn.isLeftHanded();

        this.flower.visible = entityIn.hasFlower();
        this.tooth1.visible = hasTeeth;
        this.tooth2.visible = hasTeeth;
        this.tooth3.visible = hasTeeth;
        this.whisker1.visible = !hasTeeth;
        this.whisker2.visible = !hasTeeth;
        this.whisker3.visible = !hasTeeth;
        this.whisker4.visible = !hasTeeth;
        this.leftWand.visible = isLeftHanded;
        this.rightWand.visible = !isLeftHanded;
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
