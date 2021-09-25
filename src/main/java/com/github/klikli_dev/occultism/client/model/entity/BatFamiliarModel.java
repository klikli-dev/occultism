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
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BatFamiliarModel extends EntityModel<BatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer stick;
    public ModelRenderer head;
    public ModelRenderer leftWing1;
    public ModelRenderer rightWing1;
    public ModelRenderer leftEar;
    public ModelRenderer rightEar;
    public ModelRenderer nose;
    public ModelRenderer leftLeg;
    public ModelRenderer leftWing2;
    public ModelRenderer rightLeg;
    public ModelRenderer rightWing2;
    public ModelRenderer leftChain1;
    public ModelRenderer rightChain1;
    public ModelRenderer leftChain2;
    public ModelRenderer leftChain3;
    public ModelRenderer rightChain2;
    public ModelRenderer rightChain3;

    public BatFamiliarModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 64;
        this.texHeight = 32;
        this.leftChain1 = new ModelRenderer(this, 26, 2);
        this.leftChain1.setPos(7.0F, 0.0F, 0.5F);
        this.leftChain1.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain1, 0.0F, -0.5235987755982988F, 0.0F);
        this.rightEar = new ModelRenderer(this, 16, 0);
        this.rightEar.mirror = true;
        this.rightEar.setPos(-2.0F, -4.0F, 0.0F);
        this.rightEar.addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, -0.0781907508222411F, 0.3127630032889644F, -0.5082398928281348F);
        this.rightChain2 = new ModelRenderer(this, 32, 2);
        this.rightChain2.setPos(0.0F, -3.0F, 0.0F);
        this.rightChain2.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain2, 0.0F, 1.0471975511965976F, 0.0F);
        this.head = new ModelRenderer(this, 11, 6);
        this.head.setPos(0.0F, -5.0F, -1.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.19547687289441354F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 16);
        this.nose.setPos(0.0F, -2.0F, -2.0F);
        this.nose.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightChain3 = new ModelRenderer(this, 38, 2);
        this.rightChain3.setPos(0.0F, -3.0F, 0.0F);
        this.rightChain3.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain3, 0.0F, -1.5707963267948966F, 0.0F);
        this.leftWing2 = new ModelRenderer(this, 16, 21);
        this.leftWing2.setPos(6.0F, 0.0F, 0.0F);
        this.leftWing2.addBox(0.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing2, 0.0F, -0.1563815016444822F, 0.0F);
        this.rightWing1 = new ModelRenderer(this, 0, 21);
        this.rightWing1.mirror = true;
        this.rightWing1.setPos(-2.5F, 0.0F, 0.0F);
        this.rightWing1.addBox(-6.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing1, 0.0F, 0.1563815016444822F, 0.0F);
        this.leftEar = new ModelRenderer(this, 16, 0);
        this.leftEar.setPos(2.0F, -4.0F, 0.0F);
        this.leftEar.addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, -0.0781907508222411F, -0.3127630032889644F, 0.5082398928281348F);
        this.rightLeg = new ModelRenderer(this, 0, 11);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(0.0F, 5.0F, 0.0F);
        this.rightLeg.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, -0.35185837453889574F, 0.0F, 0.0F);
        this.stick = new ModelRenderer(this, 24, 0);
        this.stick.setPos(0.0F, 8.0F, 0.0F);
        this.stick.addBox(-7.0F, 0.0F, 0.0F, 14.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightChain1 = new ModelRenderer(this, 26, 2);
        this.rightChain1.setPos(-7.0F, 0.0F, 0.5F);
        this.rightChain1.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain1, 0.0F, -0.5235987755982988F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 16.5F, 0.0F);
        this.body.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 3.141592653589793F, 0.0F, 0.0F);
        this.rightWing2 = new ModelRenderer(this, 16, 21);
        this.rightWing2.mirror = true;
        this.rightWing2.setPos(-6.0F, 0.0F, 0.0F);
        this.rightWing2.addBox(-8.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing2, 0.0F, 0.1563815016444822F, 0.0F);
        this.leftWing1 = new ModelRenderer(this, 0, 21);
        this.leftWing1.setPos(2.5F, 0.0F, 0.0F);
        this.leftWing1.addBox(-2.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing1, 0.0F, -0.1563815016444822F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 11);
        this.leftLeg.setPos(0.0F, 5.0F, 0.0F);
        this.leftLeg.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, -0.35185837453889574F, 0.0F, 0.0F);
        this.leftChain2 = new ModelRenderer(this, 32, 2);
        this.leftChain2.setPos(0.0F, -3.0F, 0.0F);
        this.leftChain2.addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain2, 0.0F, 1.0471975511965976F, 0.0F);
        this.leftChain3 = new ModelRenderer(this, 38, 2);
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
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body, this.stick).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void prepareMobModel(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {

        if (entityIn.isPartying()) {
            stick.visible = false;
            body.xRot = toRads(0);
        } else if (entityIn.isSitting()) {
            leftWing1.yRot = toRads(60);
            leftWing2.yRot = toRads(60);
            rightWing1.yRot = -toRads(60);
            rightWing2.yRot = -toRads(60);
            body.xRot = toRads(180);
            stick.visible = true;
        } else {
            float animationHeight = entityIn.getAnimationHeight(partialTick);
            leftWing1.yRot = animationHeight * toRads(20);
            leftWing2.yRot = animationHeight * toRads(20);
            rightWing1.yRot = -animationHeight * toRads(20);
            rightWing2.yRot = -animationHeight * toRads(20);
            body.xRot = toRads(0);
            stick.visible = false;
        }
    }

    @Override
    public void setupAnim(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {

        if (entityIn.isPartying()) {
            head.xRot = MathHelper.sin(ageInTicks / 3) * toRads(10);
            head.yRot = MathHelper.cos(ageInTicks / 3) * toRads(10);
            head.zRot = MathHelper.sin(ageInTicks / 3) * toRads(10);
            leftWing1.yRot = MathHelper.sin(ageInTicks / 3) * toRads(60);
            leftWing2.yRot = MathHelper.sin(ageInTicks / 3) * toRads(60);
            rightWing1.yRot = MathHelper.sin(ageInTicks / 3) * toRads(60);
            rightWing2.yRot = MathHelper.sin(ageInTicks / 3) * toRads(60);
            body.xRot = toRads(20) + limbSwingAmount * toRads(70);
            body.yRot = 0;
        } else if (entityIn.isSitting()) {
            head.xRot = 0.2f;
            head.yRot = 0;
            head.zRot = 0;
            body.xRot = (float) Math.toRadians(180);
            body.yRot = (float) Math.toRadians(180);
        } else {
            head.xRot = 0;
            head.yRot = toRads(netHeadYaw) * 0.35f;
            head.zRot = toRads(headPitch) * 0.35f;

            body.xRot = toRads(20) + limbSwingAmount * toRads(70);
            body.yRot = 0;
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
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
