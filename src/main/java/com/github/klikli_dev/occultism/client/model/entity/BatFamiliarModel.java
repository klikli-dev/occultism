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

import com.github.klikli_dev.occultism.common.entity.BatFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

/**
 * Created using Tabula 8.0.0
 */
public class BatFamiliarModel extends EntityModel<BatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart stick;
    public ModelPart head;
    public ModelPart leftWing1;
    public ModelPart rightWing1;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart nose;
    public ModelPart leftLeg;
    public ModelPart leftWing2;
    public ModelPart rightLeg;
    public ModelPart rightWing2;
    public ModelPart leftChain1;
    public ModelPart rightChain1;
    public ModelPart leftChain2;
    public ModelPart leftChain3;
    public ModelPart rightChain2;
    public ModelPart rightChain3;

    public BatFamiliarModel(ModelPart part) {
        super(RenderType::entityTranslucent);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leftChain1 = new ModelPart(this, 26, 2);
        this.leftChain1.setPos(7.0F, 0.0F, 0.5F);
        this.leftChain1.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain1, 0.0F, -0.5235987755982988F, 0.0F);
        this.rightEar = new ModelPart(this, 16, 0);
        this.rightEar.mirror = true;
        this.rightEar.setPos(-2.0F, -4.0F, 0.0F);
        this.rightEar.addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, -0.0781907508222411F, 0.3127630032889644F, -0.5082398928281348F);
        this.rightChain2 = new ModelPart(this, 32, 2);
        this.rightChain2.setPos(0.0F, -3.0F, 0.0F);
        this.rightChain2.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain2, 0.0F, 1.0471975511965976F, 0.0F);
        this.head = new ModelPart(this, 11, 6);
        this.head.setPos(0.0F, -5.0F, -1.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.19547687289441354F, 0.0F, 0.0F);
        this.nose = new ModelPart(this, 0, 16);
        this.nose.setPos(0.0F, -2.0F, -2.0F);
        this.nose.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightChain3 = new ModelPart(this, 38, 2);
        this.rightChain3.setPos(0.0F, -3.0F, 0.0F);
        this.rightChain3.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain3, 0.0F, -1.5707963267948966F, 0.0F);
        this.leftWing2 = new ModelPart(this, 16, 21);
        this.leftWing2.setPos(6.0F, 0.0F, 0.0F);
        this.leftWing2.addBox(0.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing2, 0.0F, -0.1563815016444822F, 0.0F);
        this.rightWing1 = new ModelPart(this, 0, 21);
        this.rightWing1.mirror = true;
        this.rightWing1.setPos(-2.5F, 0.0F, 0.0F);
        this.rightWing1.addBox(-6.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing1, 0.0F, 0.1563815016444822F, 0.0F);
        this.leftEar = new ModelPart(this, 16, 0);
        this.leftEar.setPos(2.0F, -4.0F, 0.0F);
        this.leftEar.addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, -0.0781907508222411F, -0.3127630032889644F, 0.5082398928281348F);
        this.rightLeg = new ModelPart(this, 0, 11);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(0.0F, 5.0F, 0.0F);
        this.rightLeg.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, -0.35185837453889574F, 0.0F, 0.0F);
        this.stick = new ModelPart(this, 24, 0);
        this.stick.setPos(0.0F, 8.0F, 0.0F);
        this.stick.addBox(-7.0F, 0.0F, 0.0F, 14.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightChain1 = new ModelPart(this, 26, 2);
        this.rightChain1.setPos(-7.0F, 0.0F, 0.5F);
        this.rightChain1.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain1, 0.0F, -0.5235987755982988F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 16.5F, 0.0F);
        this.body.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 3.141592653589793F, 0.0F, 0.0F);
        this.rightWing2 = new ModelPart(this, 16, 21);
        this.rightWing2.mirror = true;
        this.rightWing2.setPos(-6.0F, 0.0F, 0.0F);
        this.rightWing2.addBox(-8.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing2, 0.0F, 0.1563815016444822F, 0.0F);
        this.leftWing1 = new ModelPart(this, 0, 21);
        this.leftWing1.setPos(2.5F, 0.0F, 0.0F);
        this.leftWing1.addBox(-2.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing1, 0.0F, -0.1563815016444822F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 11);
        this.leftLeg.setPos(0.0F, 5.0F, 0.0F);
        this.leftLeg.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, -0.35185837453889574F, 0.0F, 0.0F);
        this.leftChain2 = new ModelPart(this, 32, 2);
        this.leftChain2.setPos(0.0F, -3.0F, 0.0F);
        this.leftChain2.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain2, 0.0F, 1.0471975511965976F, 0.0F);
        this.leftChain3 = new ModelPart(this, 38, 2);
        this.leftChain3.setPos(0.0F, -3.0F, 0.0F);
        this.leftChain3.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain3, 0.0F, -1.5707963267948966F, 0.0F);
        this.stick.addChild(this.leftChain1);
        this.head.addChild(this.rightEar);
        this.rightChain1.addChild(this.rightChain2);
        this.body.addChild(this.head);
        this.head.addChild(this.nose);
        this.rightChain2.addChild(this.rightChain3);
        this.leftWing1.addChild(this.leftWing2);
        this.body.addChild(this.rightWing1);
        this.head.addChild(this.leftEar);
        this.rightWing1.addChild(this.rightLeg);
        this.stick.addChild(this.rightChain1);
        this.rightWing1.addChild(this.rightWing2);
        this.body.addChild(this.leftWing1);
        this.leftWing1.addChild(this.leftLeg);
        this.leftChain1.addChild(this.leftChain2);
        this.leftChain2.addChild(this.leftChain3);
    }

    @Override
    public void render(PoseStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body, this.stick).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setLivingAnimations(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
        if (entityIn.isSitting()) {
            leftWing1.rotateAngleY = toRads(60);
            leftWing2.rotateAngleY = toRads(60);
            rightWing1.rotateAngleY = -toRads(60);
            rightWing2.rotateAngleY = -toRads(60);
            body.rotateAngleX = toRads(180);
            stick.showModel = true;
        } else {
            float animationHeight = entityIn.getAnimationHeight(partialTick);
            leftWing1.rotateAngleY = animationHeight * toRads(20);
            leftWing2.rotateAngleY = animationHeight * toRads(20);
            rightWing1.rotateAngleY = -animationHeight * toRads(20);
            rightWing2.rotateAngleY = -animationHeight * toRads(20);
            body.rotateAngleX = toRads(0);
            stick.showModel = false;
        }
    }

    @Override
    public void setRotationAngles(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {

        if (entityIn.isSitting()) {
            head.rotateAngleX = 0.2f;
            head.rotateAngleY = 0;
            head.rotateAngleZ = 0;
            body.rotateAngleX = (float) Math.toRadians(180);
            body.rotateAngleY = (float) Math.toRadians(180);
        } else {
            head.rotateAngleY = toRads(netHeadYaw) * 0.35f;
            head.rotateAngleZ = toRads(headPitch) * 0.35f;

            body.rotateAngleX = toRads(20) + limbSwingAmount * toRads(70);
            body.rotateAngleY = 0;
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
