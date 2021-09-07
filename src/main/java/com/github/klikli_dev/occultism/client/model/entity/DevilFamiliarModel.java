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

import com.github.klikli_dev.occultism.common.entity.DevilFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class DevilFamiliarModel extends EntityModel<DevilFamiliarEntity> {
    
    private static final float PI = (float) Math.PI;
    
    public ModelRenderer egg;
    public ModelRenderer body;
    public ModelRenderer leftLeg;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer rightArm;
    public ModelRenderer neck1;
    public ModelRenderer leftWing;
    public ModelRenderer rightWing;
    public ModelRenderer head;
    public ModelRenderer lowerJaw;
    public ModelRenderer upperJaw;
    public ModelRenderer leftEye;
    public ModelRenderer headScales;
    public ModelRenderer leftEar;
    public ModelRenderer leftHorn;
    public ModelRenderer rightEye;
    public ModelRenderer rightHorn;
    public ModelRenderer rightEar;
    public ModelRenderer leftHornBig1;
    public ModelRenderer rightHornBig1;
    public ModelRenderer nose;
    public ModelRenderer tooth;
    public ModelRenderer jawHorn1;
    public ModelRenderer jawHorn2;
    public ModelRenderer leftHornBig2;
    public ModelRenderer leftHornBig3;
    public ModelRenderer rightHornBig2;
    public ModelRenderer rightHornBig3;
    public ModelRenderer lollipop;

    public DevilFamiliarModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.rightEye = new ModelRenderer(this, 49, 0);
        this.rightEye.setRotationPoint(-2.0F, -3.3F, -2.3F);
        this.rightEye.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEye, 0.3490658503988659F, 0.0F, 0.0F);
        this.rightHornBig3 = new ModelRenderer(this, 0, 29);
        this.rightHornBig3.setRotationPoint(0.0F, -1.7F, 0.0F);
        this.rightHornBig3.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHornBig3, 0.5864306020384839F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 12, 40);
        this.body.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.body.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.08726646259971647F, 0.0F, 0.0F);
        this.lowerJaw = new ModelRenderer(this, 24, 18);
        this.lowerJaw.setRotationPoint(0.0F, -1.1F, -2.8F);
        this.lowerJaw.addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerJaw, 0.3305653696487898F, 0.0F, 0.0F);
        this.leftHornBig2 = new ModelRenderer(this, 0, 29);
        this.leftHornBig2.setRotationPoint(0.3F, -1.5F, 0.0F);
        this.leftHornBig2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHornBig2, 0.0F, 0.0F, 1.1344640137963142F);
        this.leftHornBig3 = new ModelRenderer(this, 0, 29);
        this.leftHornBig3.setRotationPoint(0.0F, -1.7F, 0.0F);
        this.leftHornBig3.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHornBig3, 0.5864306020384839F, 0.0F, 0.0F);
        this.rightHornBig2 = new ModelRenderer(this, 0, 29);
        this.rightHornBig2.setRotationPoint(-0.3F, -1.5F, 0.0F);
        this.rightHornBig2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHornBig2, 0.0F, 0.0F, -1.1344640137963142F);
        this.rightHorn = new ModelRenderer(this, 0, 2);
        this.rightHorn.setRotationPoint(-1.0F, -4.5F, -1.8F);
        this.rightHorn.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn, 0.540353920438478F, -0.1350884801096195F, -0.3152064535891121F);
        this.tooth = new ModelRenderer(this, 0, 0);
        this.tooth.setRotationPoint(0.0F, 1.0F, -4.7F);
        this.tooth.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftEye = new ModelRenderer(this, 49, 0);
        this.leftEye.setRotationPoint(2.0F, -3.3F, -2.3F);
        this.leftEye.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEye, 0.3490658503988659F, 0.0F, 0.0F);
        this.upperJaw = new ModelRenderer(this, 39, 23);
        this.upperJaw.setRotationPoint(0.0F, -3.0F, -3.0F);
        this.upperJaw.addBox(-2.0F, -1.5F, -5.0F, 4.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 30);
        this.rightArm.setRotationPoint(-2.0F, -11.5F, 0.0F);
        this.rightArm.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm, 0.35185837453889574F, 0.19547687289441354F, 0.0F);
        this.leftArm = new ModelRenderer(this, 0, 30);
        this.leftArm.setRotationPoint(2.0F, -11.5F, 0.0F);
        this.leftArm.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, 0.35185837453889574F, -0.17453292519943295F, 0.0F);
        this.jawHorn2 = new ModelRenderer(this, 0, 29);
        this.jawHorn2.setRotationPoint(0.0F, -2.7F, 0.0F);
        this.jawHorn2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(jawHorn2, -0.1563815016444822F, 0.0F, 0.0F);
        this.rightWing = new ModelRenderer(this, 0, 40);
        this.rightWing.mirror = true;
        this.rightWing.setRotationPoint(-1.5F, -6.0F, 3.0F);
        this.rightWing.addBox(-6.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing, 0.35185837453889574F, 0.5082398928281348F, -0.11728612207217244F);
        this.neck1 = new ModelRenderer(this, 37, 0);
        this.neck1.setRotationPoint(0.0F, -5.5F, 0.0F);
        this.neck1.addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck1, 0.07400195628981794F, 0.0F, 0.0F);
        this.lollipop = new ModelRenderer(this, 0, 13);
        this.lollipop.setRotationPoint(0.0F, -1.5F, -6.0F);
        this.lollipop.addBox(0.0F, -6.0F, -2.5F, 0.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 30);
        this.rightLeg.mirror = true;
        this.rightLeg.setRotationPoint(-2.0F, -1.5F, -2.0F);
        this.rightLeg.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, 0.0F, 0.19547687289441354F, 0.0F);
        this.jawHorn1 = new ModelRenderer(this, 0, 24);
        this.jawHorn1.setRotationPoint(0.0F, -1.2F, -3.6F);
        this.jawHorn1.addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(jawHorn1, -0.1563815016444822F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 53, 4);
        this.rightEar.setRotationPoint(-1.3F, -4.7F, 0.8F);
        this.rightEar.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, 0.2565633967142151F, -0.162315623764424F, -0.2925171866289913F);
        this.leftWing = new ModelRenderer(this, 0, 40);
        this.leftWing.setRotationPoint(1.5F, -6.0F, 3.0F);
        this.leftWing.addBox(0.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing, 0.35185837453889574F, -0.5082398928281348F, 0.11728612207217244F);
        this.head = new ModelRenderer(this, 41, 13);
        this.head.setRotationPoint(0.0F, -3.5F, 1.0F);
        this.head.addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.03490658503988659F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 53, 4);
        this.leftEar.setRotationPoint(1.3F, -4.7F, 0.8F);
        this.leftEar.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, 0.2565633967142151F, 0.162315623764424F, 0.2925171866289913F);
        this.nose = new ModelRenderer(this, 0, 54);
        this.nose.setRotationPoint(0.0F, -2.2F, -4.6F);
        this.nose.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.headScales = new ModelRenderer(this, 0, 40);
        this.headScales.setRotationPoint(0.0F, -8.0F, -2.0F);
        this.headScales.addBox(0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(headScales, 0.03490658503988659F, 0.0F, 0.0F);
        this.leftHornBig1 = new ModelRenderer(this, 0, 29);
        this.leftHornBig1.setRotationPoint(1.0F, -4.8F, -1.0F);
        this.leftHornBig1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHornBig1, 0.0F, 0.0F, 0.3127630032889644F);
        this.leftHorn = new ModelRenderer(this, 0, 2);
        this.leftHorn.setRotationPoint(1.0F, -4.5F, -1.8F);
        this.leftHorn.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn, 0.540353920438478F, 0.1350884801096195F, 0.3152064535891121F);
        this.egg = new ModelRenderer(this, 0, 0);
        this.egg.setRotationPoint(0.0F, 24.0F, 2.0F);
        this.egg.addBox(-7.0F, -9.0F, -4.5F, 14.0F, 9.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 30);
        this.leftLeg.setRotationPoint(2.0F, -1.5F, -2.0F);
        this.leftLeg.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, 0.0F, -0.19547687289441354F, 0.0F);
        this.rightHornBig1 = new ModelRenderer(this, 0, 29);
        this.rightHornBig1.setRotationPoint(-1.0F, -4.8F, -1.0F);
        this.rightHornBig1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHornBig1, 0.0F, 0.0F, -0.3127630032889644F);
        this.head.addChild(this.rightEye);
        this.rightHornBig1.addChild(this.rightHornBig3);
        this.egg.addChild(this.body);
        this.head.addChild(this.lowerJaw);
        this.leftHornBig1.addChild(this.leftHornBig2);
        this.leftHornBig1.addChild(this.leftHornBig3);
        this.rightHornBig1.addChild(this.rightHornBig2);
        this.head.addChild(this.rightHorn);
        this.upperJaw.addChild(this.tooth);
        this.head.addChild(this.leftEye);
        this.head.addChild(this.upperJaw);
        this.egg.addChild(this.rightArm);
        this.egg.addChild(this.leftArm);
        this.jawHorn1.addChild(this.jawHorn2);
        this.body.addChild(this.rightWing);
        this.body.addChild(this.neck1);
        this.rightArm.addChild(this.lollipop);
        this.egg.addChild(this.rightLeg);
        this.upperJaw.addChild(this.jawHorn1);
        this.head.addChild(this.rightEar);
        this.body.addChild(this.leftWing);
        this.neck1.addChild(this.head);
        this.head.addChild(this.leftEar);
        this.upperJaw.addChild(this.nose);
        this.head.addChild(this.headScales);
        this.head.addChild(this.leftHornBig1);
        this.head.addChild(this.leftHorn);
        this.egg.addChild(this.leftLeg);
        this.head.addChild(this.rightHornBig1);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.egg).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(DevilFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.showModels(entityIn);
        this.head.rotateAngleY = netHeadYaw * (PI / 180f) * 0.7f;
        this.head.rotateAngleX = 0.03f + headPitch * (PI / 180f) * 0.7f;
        
        if (entityIn.isSitting()) {
            this.leftLeg.rotateAngleX = 0;
            this.rightLeg.rotateAngleX = 0;
        } else {
            this.leftLeg.rotateAngleX = toRads(30) + MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.4f;
            this.rightLeg.rotateAngleX = toRads(30) + MathHelper.cos(limbSwing * 0.5f) * limbSwingAmount * 0.4f;
        }
        
        if (ageInTicks % 300 < 60) {
            float progress = ageInTicks % 300 % 60;
            float armHeight = MathHelper.sin(progress * PI / 60) * 0.4f;
            this.leftArm.rotateAngleX = 0.35f - armHeight + MathHelper.sin(progress * PI / 10) * 0.45f;
            this.rightArm.rotateAngleX = 0.35f - armHeight + MathHelper.sin(progress * PI / 10 + PI) * 0.45f;
        } else {
            this.leftArm.rotateAngleX = 0.35f;
            this.rightArm.rotateAngleX = 0.35f;
        }
    }

    @Override
    public void setLivingAnimations(DevilFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
        if (entityIn.isSitting()) {
            leftWing.rotateAngleY = -0.43f;
            rightWing.rotateAngleY = 0.43f;
        } else {
            float animationHeight = entityIn.getAnimationHeight(partialTick);
            leftWing.rotateAngleY = animationHeight * toRads(20) - 0.43f;
            rightWing.rotateAngleY = -animationHeight * toRads(20) + 0.43f;
        }
    }
    
    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(DevilFamiliarEntity entityIn) {
        boolean hasNose = entityIn.hasNose();
        boolean hasEars = entityIn.hasEars();

        this.lollipop.showModel = entityIn.hasLollipop();
        this.nose.showModel = hasNose;
        this.jawHorn1.showModel = !hasNose;
        this.leftEar.showModel = hasEars;
        this.rightEar.showModel = hasEars;
        this.leftHorn.showModel = hasEars;
        this.rightHorn.showModel = hasEars;
        this.leftHornBig1.showModel = !hasEars;
        this.rightHornBig1.showModel = !hasEars;
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
