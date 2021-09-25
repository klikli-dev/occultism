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
        this.texWidth = 64;
        this.texHeight = 64;
        this.rightEye = new ModelRenderer(this, 49, 0);
        this.rightEye.setPos(-2.0F, -3.3F, -2.3F);
        this.rightEye.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightEye, 0.3490658503988659F, 0.0F, 0.0F);
        this.rightHornBig3 = new ModelRenderer(this, 0, 29);
        this.rightHornBig3.setPos(0.0F, -1.7F, 0.0F);
        this.rightHornBig3.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightHornBig3, 0.5864306020384839F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 12, 40);
        this.body.setPos(0.0F, -7.0F, 0.0F);
        this.body.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.body, -0.08726646259971647F, 0.0F, 0.0F);
        this.lowerJaw = new ModelRenderer(this, 24, 18);
        this.lowerJaw.setPos(0.0F, -1.1F, -2.8F);
        this.lowerJaw.addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.lowerJaw, 0.3305653696487898F, 0.0F, 0.0F);
        this.leftHornBig2 = new ModelRenderer(this, 0, 29);
        this.leftHornBig2.setPos(0.3F, -1.5F, 0.0F);
        this.leftHornBig2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftHornBig2, 0.0F, 0.0F, 1.1344640137963142F);
        this.leftHornBig3 = new ModelRenderer(this, 0, 29);
        this.leftHornBig3.setPos(0.0F, -1.7F, 0.0F);
        this.leftHornBig3.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftHornBig3, 0.5864306020384839F, 0.0F, 0.0F);
        this.rightHornBig2 = new ModelRenderer(this, 0, 29);
        this.rightHornBig2.setPos(-0.3F, -1.5F, 0.0F);
        this.rightHornBig2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightHornBig2, 0.0F, 0.0F, -1.1344640137963142F);
        this.rightHorn = new ModelRenderer(this, 0, 2);
        this.rightHorn.setPos(-1.0F, -4.5F, -1.8F);
        this.rightHorn.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightHorn, 0.540353920438478F, -0.1350884801096195F, -0.3152064535891121F);
        this.tooth = new ModelRenderer(this, 0, 0);
        this.tooth.setPos(0.0F, 1.0F, -4.7F);
        this.tooth.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftEye = new ModelRenderer(this, 49, 0);
        this.leftEye.setPos(2.0F, -3.3F, -2.3F);
        this.leftEye.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftEye, 0.3490658503988659F, 0.0F, 0.0F);
        this.upperJaw = new ModelRenderer(this, 39, 23);
        this.upperJaw.setPos(0.0F, -3.0F, -3.0F);
        this.upperJaw.addBox(-2.0F, -1.5F, -5.0F, 4.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 30);
        this.rightArm.setPos(-2.0F, -11.5F, 0.0F);
        this.rightArm.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightArm, 0.35185837453889574F, 0.19547687289441354F, 0.0F);
        this.leftArm = new ModelRenderer(this, 0, 30);
        this.leftArm.setPos(2.0F, -11.5F, 0.0F);
        this.leftArm.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftArm, 0.35185837453889574F, -0.17453292519943295F, 0.0F);
        this.jawHorn2 = new ModelRenderer(this, 0, 29);
        this.jawHorn2.setPos(0.0F, -2.7F, 0.0F);
        this.jawHorn2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.jawHorn2, -0.1563815016444822F, 0.0F, 0.0F);
        this.rightWing = new ModelRenderer(this, 0, 40);
        this.rightWing.mirror = true;
        this.rightWing.setPos(-1.5F, -6.0F, 3.0F);
        this.rightWing.addBox(-6.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightWing, 0.35185837453889574F, 0.5082398928281348F, -0.11728612207217244F);
        this.neck1 = new ModelRenderer(this, 37, 0);
        this.neck1.setPos(0.0F, -5.5F, 0.0F);
        this.neck1.addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.neck1, 0.07400195628981794F, 0.0F, 0.0F);
        this.lollipop = new ModelRenderer(this, 0, 13);
        this.lollipop.setPos(0.0F, -1.5F, -6.0F);
        this.lollipop.addBox(0.0F, -6.0F, -2.5F, 0.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 30);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(-2.0F, -1.5F, -2.0F);
        this.rightLeg.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightLeg, 0.0F, 0.19547687289441354F, 0.0F);
        this.jawHorn1 = new ModelRenderer(this, 0, 24);
        this.jawHorn1.setPos(0.0F, -1.2F, -3.6F);
        this.jawHorn1.addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.jawHorn1, -0.1563815016444822F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 53, 4);
        this.rightEar.setPos(-1.3F, -4.7F, 0.8F);
        this.rightEar.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightEar, 0.2565633967142151F, -0.162315623764424F, -0.2925171866289913F);
        this.leftWing = new ModelRenderer(this, 0, 40);
        this.leftWing.setPos(1.5F, -6.0F, 3.0F);
        this.leftWing.addBox(0.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftWing, 0.35185837453889574F, -0.5082398928281348F, 0.11728612207217244F);
        this.head = new ModelRenderer(this, 41, 13);
        this.head.setPos(0.0F, -3.5F, 1.0F);
        this.head.addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.head, 0.03490658503988659F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 53, 4);
        this.leftEar.setPos(1.3F, -4.7F, 0.8F);
        this.leftEar.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftEar, 0.2565633967142151F, 0.162315623764424F, 0.2925171866289913F);
        this.nose = new ModelRenderer(this, 0, 54);
        this.nose.setPos(0.0F, -2.2F, -4.6F);
        this.nose.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.headScales = new ModelRenderer(this, 0, 40);
        this.headScales.setPos(0.0F, -8.0F, -2.0F);
        this.headScales.addBox(0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.headScales, 0.03490658503988659F, 0.0F, 0.0F);
        this.leftHornBig1 = new ModelRenderer(this, 0, 29);
        this.leftHornBig1.setPos(1.0F, -4.8F, -1.0F);
        this.leftHornBig1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftHornBig1, 0.0F, 0.0F, 0.3127630032889644F);
        this.leftHorn = new ModelRenderer(this, 0, 2);
        this.leftHorn.setPos(1.0F, -4.5F, -1.8F);
        this.leftHorn.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftHorn, 0.540353920438478F, 0.1350884801096195F, 0.3152064535891121F);
        this.egg = new ModelRenderer(this, 0, 0);
        this.egg.setPos(0.0F, 24.0F, 2.0F);
        this.egg.addBox(-7.0F, -9.0F, -4.5F, 14.0F, 9.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 30);
        this.leftLeg.setPos(2.0F, -1.5F, -2.0F);
        this.leftLeg.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftLeg, 0.0F, -0.19547687289441354F, 0.0F);
        this.rightHornBig1 = new ModelRenderer(this, 0, 29);
        this.rightHornBig1.setPos(-1.0F, -4.8F, -1.0F);
        this.rightHornBig1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightHornBig1, 0.0F, 0.0F, -0.3127630032889644F);
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
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
                               float red, float green, float blue, float alpha) {
        ImmutableList.of(this.egg).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(DevilFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.showModels(entityIn);
        this.head.yRot = netHeadYaw * (PI / 180f) * 0.7f;
        this.head.xRot = 0.03f + headPitch * (PI / 180f) * 0.7f;
        this.egg.yRot = 0;
        this.egg.zRot = 0;

        if (ageInTicks % 300 < 60) {
            float progress = ageInTicks % 300 % 60;
            float armHeight = MathHelper.sin(progress * PI / 60) * 0.4f;
            this.leftArm.xRot = 0.35f - armHeight + MathHelper.sin(progress * PI / 10) * 0.45f;
            this.rightArm.xRot = 0.35f - armHeight + MathHelper.sin(progress * PI / 10 + PI) * 0.45f;
        } else {
            this.leftArm.xRot = 0.35f;
            this.rightArm.xRot = 0.35f;
        }

        if (entityIn.isPartying()) {
            this.egg.yRot = ageInTicks / 4;
            this.egg.zRot = MathHelper.cos(ageInTicks / 4) * this.toRads(10);
            this.leftLeg.xRot = this.toRads(30) + MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.4f;
            this.rightLeg.xRot = this.toRads(30) + MathHelper.cos(limbSwing * 0.5f) * limbSwingAmount * 0.4f;
            this.leftArm.xRot = MathHelper.cos(ageInTicks / 4) * this.toRads(10);
            this.rightArm.xRot = MathHelper.cos(ageInTicks / 4 + PI) * this.toRads(10);
        } else if (entityIn.isSitting()) {
            this.leftLeg.xRot = 0;
            this.rightLeg.xRot = 0;
        } else {
            this.leftLeg.xRot = this.toRads(30) + MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.4f;
            this.rightLeg.xRot = this.toRads(30) + MathHelper.cos(limbSwing * 0.5f) * limbSwingAmount * 0.4f;
        }
    }

    @Override
    public void prepareMobModel(DevilFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                                float partialTick) {
        float animationHeight = entityIn.getAnimationHeight(partialTick);
        this.leftWing.yRot = animationHeight * this.toRads(20) - 0.43f;
        this.rightWing.yRot = -animationHeight * this.toRads(20) + 0.43f;

        if (entityIn.isSitting() && !entityIn.isPartying()) {
            this.leftWing.yRot = -0.43f;
            this.rightWing.yRot = 0.43f;
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(DevilFamiliarEntity entityIn) {
        boolean hasNose = entityIn.hasNose();
        boolean hasEars = entityIn.hasEars();

        this.lollipop.visible = entityIn.hasLollipop();
        this.nose.visible = hasNose;
        this.jawHorn1.visible = !hasNose;
        this.leftEar.visible = hasEars;
        this.rightEar.visible = hasEars;
        this.leftHorn.visible = hasEars;
        this.rightHorn.visible = hasEars;
        this.leftHornBig1.visible = !hasEars;
        this.rightHornBig1.visible = !hasEars;
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
