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

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class BatFamiliarModel extends EntityModel<BatFamiliarEntity> {

    public ModelRenderer body;
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

    public BatFamiliarModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 16.5F, 0.0F);
        this.body.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 0.46914448828868976F, 0.0F, 0.0F);
        this.rightWing1 = new ModelRenderer(this, 0, 21);
        this.rightWing1.mirror = true;
        this.rightWing1.setRotationPoint(-2.5F, 0.0F, 0.0F);
        this.rightWing1.addBox(-6.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing1, 0.0F, 0.1563815016444822F, 0.0F);
        this.rightWing2 = new ModelRenderer(this, 16, 21);
        this.rightWing2.mirror = true;
        this.rightWing2.setRotationPoint(-6.0F, 0.0F, 0.0F);
        this.rightWing2.addBox(-8.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing2, 0.0F, 0.1563815016444822F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 11);
        this.leftLeg.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.leftLeg.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, -0.35185837453889574F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 16);
        this.nose.setRotationPoint(0.0F, -2.0F, -2.0F);
        this.nose.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 16, 0);
        this.rightEar.mirror = true;
        this.rightEar.setRotationPoint(-2.0F, -4.0F, 0.0F);
        this.rightEar.addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, -0.0781907508222411F, 0.3127630032889644F, -0.5082398928281348F);
        this.rightLeg = new ModelRenderer(this, 0, 11);
        this.rightLeg.mirror = true;
        this.rightLeg.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.rightLeg.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, -0.35185837453889574F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 16, 0);
        this.leftEar.setRotationPoint(2.0F, -4.0F, 0.0F);
        this.leftEar.addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, -0.0781907508222411F, -0.3127630032889644F, 0.5082398928281348F);
        this.head = new ModelRenderer(this, 11, 6);
        this.head.setRotationPoint(0.0F, -5.0F, -1.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.19547687289441354F, 0.0F, 0.0F);
        this.leftWing2 = new ModelRenderer(this, 16, 21);
        this.leftWing2.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.leftWing2.addBox(0.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing2, 0.0F, -0.1563815016444822F, 0.0F);
        this.leftWing1 = new ModelRenderer(this, 0, 21);
        this.leftWing1.setRotationPoint(2.5F, 0.0F, 0.0F);
        this.leftWing1.addBox(-2.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing1, 0.0F, -0.1563815016444822F, 0.0F);
        this.body.addChild(this.rightWing1);
        this.rightWing1.addChild(this.rightWing2);
        this.leftWing1.addChild(this.leftLeg);
        this.head.addChild(this.nose);
        this.head.addChild(this.rightEar);
        this.rightWing1.addChild(this.rightLeg);
        this.head.addChild(this.leftEar);
        this.body.addChild(this.head);
        this.leftWing1.addChild(this.leftWing2);
        this.body.addChild(this.leftWing1);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
