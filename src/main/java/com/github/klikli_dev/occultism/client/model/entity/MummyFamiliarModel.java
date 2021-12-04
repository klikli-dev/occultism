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

import com.github.klikli_dev.occultism.common.entity.MummyFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class MummyFamiliarModel extends EntityModel<MummyFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer leftArm1;
    public ModelRenderer leftLeg1;
    public ModelRenderer skeleton;
    public ModelRenderer spine;
    public ModelRenderer rightArm1;
    public ModelRenderer rightLeg1;
    public ModelRenderer nose;
    public ModelRenderer eyeSockets;
    public ModelRenderer leftEye;
    public ModelRenderer rightEye;
    public ModelRenderer crown;
    public ModelRenderer tooth;
    public ModelRenderer leftArm2;
    public ModelRenderer leftArmBandage;
    public ModelRenderer heka;
    public ModelRenderer leftGlove;
    public ModelRenderer leftLeg2;
    public ModelRenderer leftLegBandage;
    public ModelRenderer rightArm2;
    public ModelRenderer rightGlove;
    public ModelRenderer rightArmBandage;
    public ModelRenderer rightLeg2;
    public ModelRenderer leftLegBandage_1;

    public MummyFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.spine = new ModelRenderer(this, 0, 19);
        this.spine.setPos(0.0F, 0.0F, 0.0F);
        this.spine.addBox(-0.5F, -7.0F, -0.5F, 1.0F, 7.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftLegBandage_1 = new ModelRenderer(this, 44, 28);
        this.leftLegBandage_1.setPos(-0.5F, 1.9F, 1.5F);
        this.leftLegBandage_1.addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLegBandage_1, 0.0781907508222411F, -0.03909537541112055F, -1.9957839730239049F);
        this.rightArm2 = new ModelRenderer(this, 48, 6);
        this.rightArm2.mirror = true;
        this.rightArm2.setPos(-0.01F, 3.5F, 0.01F);
        this.rightArm2.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm2, -0.4300491170387584F, 0.0F, 0.0F);
        this.crown = new ModelRenderer(this, 4, 22);
        this.crown.setPos(0.0F, 2.0F, -1.5F);
        this.crown.addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crown, -0.0781907508222411F, 0.0F, 0.0F);
        this.leftArm2 = new ModelRenderer(this, 48, 6);
        this.leftArm2.setPos(0.01F, 3.5F, 0.01F);
        this.leftArm2.addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm2, -0.4300491170387584F, 0.0F, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 40, 6);
        this.leftLeg1.setPos(1.49F, -0.5F, 0.0F);
        this.leftLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.eyeSockets = new ModelRenderer(this, 52, 0);
        this.eyeSockets.setPos(0.0F, -3.0F, -3.0F);
        this.eyeSockets.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 40, 11);
        this.leftLeg2.setPos(0.01F, 2.5F, 0.01F);
        this.leftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.skeleton = new ModelRenderer(this, 0, 10);
        this.skeleton.setPos(0.0F, 0.0F, -0.01F);
        this.skeleton.addBox(-2.0F, -7.0F, -1.0F, 4.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 34, 0);
        this.nose.setPos(0.0F, -1.0F, -3.0F);
        this.nose.addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightEye = new ModelRenderer(this, 13, 0);
        this.rightEye.mirror = true;
        this.rightEye.setPos(-1.0F, -3.0F, -2.0F);
        this.rightEye.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftLegBandage = new ModelRenderer(this, 44, 28);
        this.leftLegBandage.setPos(0.0F, 0.5F, 1.5F);
        this.leftLegBandage.addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLegBandage, 0.0F, 0.27366763203903305F, 0.1563815016444822F);
        this.head = new ModelRenderer(this, 16, 0);
        this.head.setPos(0.0F, -7.0F, 0.0F);
        this.head.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightGlove = new ModelRenderer(this, 36, 26);
        this.rightGlove.mirror = true;
        this.rightGlove.setPos(-1.0F, 3.7F, 0.0F);
        this.rightGlove.addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg2 = new ModelRenderer(this, 40, 11);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setPos(-0.01F, 2.5F, 0.01F);
        this.rightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm1 = new ModelRenderer(this, 44, 0);
        this.rightArm1.mirror = true;
        this.rightArm1.setPos(-2.49F, -7.0F, 0.0F);
        this.rightArm1.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm1, -0.27366763203903305F, 0.0F, 0.0F);
        this.heka = new ModelRenderer(this, 24, 22);
        this.heka.setPos(1.0F, 3.5F, -1.0F);
        this.heka.addBox(0.0F, -2.0F, -6.0F, 0.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.tooth = new ModelRenderer(this, 0, 31);
        this.tooth.setPos(0.0F, 0.8F, -0.5F);
        this.tooth.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftArmBandage = new ModelRenderer(this, 44, 28);
        this.leftArmBandage.setPos(1.5F, 1.6F, 1.7F);
        this.leftArmBandage.addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArmBandage, 0.0F, 1.7453292129831807E-4F, 2.9717721919392552F);
        this.rightArmBandage = new ModelRenderer(this, 44, 28);
        this.rightArmBandage.setPos(-1.0F, 2.6F, 1.7F);
        this.rightArmBandage.addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArmBandage, 0.03909537541112055F, 0.23474678106428595F, 2.72637894171943F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 19.0F, 0.0F);
        this.body.addBox(-2.5F, -7.0F, -1.5F, 5.0F, 7.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg1 = new ModelRenderer(this, 40, 6);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setPos(-1.51F, -0.5F, 0.0F);
        this.rightLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm1 = new ModelRenderer(this, 44, 0);
        this.leftArm1.setPos(2.51F, -7.0F, 0.0F);
        this.leftArm1.addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm1, -0.27366763203903305F, 0.0F, 0.0F);
        this.leftGlove = new ModelRenderer(this, 36, 26);
        this.leftGlove.setPos(1.0F, 3.7F, 0.0F);
        this.leftGlove.addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftEye = new ModelRenderer(this, 13, 0);
        this.leftEye.setPos(1.0F, -3.0F, -2.0F);
        this.leftEye.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.spine);
        this.rightLeg2.addChild(this.leftLegBandage_1);
        this.rightArm1.addChild(this.rightArm2);
        this.head.addChild(this.crown);
        this.leftArm1.addChild(this.leftArm2);
        this.body.addChild(this.leftLeg1);
        this.head.addChild(this.eyeSockets);
        this.leftLeg1.addChild(this.leftLeg2);
        this.body.addChild(this.skeleton);
        this.head.addChild(this.nose);
        this.head.addChild(this.rightEye);
        this.leftLeg1.addChild(this.leftLegBandage);
        this.body.addChild(this.head);
        this.rightArm2.addChild(this.rightGlove);
        this.rightLeg1.addChild(this.rightLeg2);
        this.body.addChild(this.rightArm1);
        this.leftArm2.addChild(this.heka);
        this.nose.addChild(this.tooth);
        this.leftArm1.addChild(this.leftArmBandage);
        this.rightArm2.addChild(this.rightArmBandage);
        this.body.addChild(this.rightLeg1);
        this.body.addChild(this.leftArm1);
        this.leftArm2.addChild(this.leftGlove);
        this.head.addChild(this.leftEye);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(MummyFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
            float pNetHeadYaw, float pHeadPitch) {
        showModels(pEntity);

        setRotateAngle(body, 0, 0, 0);
        setRotateAngle(head, 0, 0, 0);
        setRotateAngle(rightArm1, -0.274f, 0, 0);
        setRotateAngle(rightArm2, -0.43f, 0, 0);
        setRotateAngle(leftArm1, -0.274f, 0, 0);
        setRotateAngle(leftArm2, -0.43f, 0, 0);
        setRotateAngle(rightLeg1, 0, 0, 0);
        setRotateAngle(rightLeg2, 0, 0, 0);
        setRotateAngle(leftLeg1, 0, 0, 0);
        setRotateAngle(leftLeg2, 0, 0, 0);
        this.body.z = 0;

        this.head.xRot = toRads(pHeadPitch);
        this.head.yRot = toRads(pNetHeadYaw);
        this.rightLeg1.xRot = MathHelper.cos(pLimbSwing * 0.5f + PI) * toRads(40) * pLimbSwingAmount;
        this.leftLeg1.xRot = MathHelper.cos(pLimbSwing * 0.5f) * toRads(40) * pLimbSwingAmount;
        this.rightLeg2.xRot = Math.abs(MathHelper.cos(pLimbSwing * 0.5f + PI)) * toRads(40) * pLimbSwingAmount;
        this.leftLeg2.xRot = Math.abs(MathHelper.cos(pLimbSwing * 0.5f) * toRads(40)) * pLimbSwingAmount;
        this.rightArm1.xRot = MathHelper.cos(pLimbSwing * 0.5f) * toRads(40) * pLimbSwingAmount;
        this.leftArm1.xRot = MathHelper.cos(pLimbSwing * 0.5f + PI) * toRads(40) * pLimbSwingAmount;
        this.rightArm2.xRot = toRads(-30) + MathHelper.cos(pLimbSwing * 0.5f) * toRads(20) * pLimbSwingAmount;
        this.leftArm2.xRot = toRads(-30) + MathHelper.cos(pLimbSwing * 0.5f + PI) * toRads(20) * pLimbSwingAmount;

        int fightPose = pEntity.getFightPose();

        if (fightPose == 0) {
            this.body.yRot = toRads(-60);
            this.body.xRot = toRads(-40);
            this.body.zRot = toRads(40);
            this.head.yRot = toRads(60);
            this.head.xRot = toRads(20);
            this.rightArm1.xRot = toRads(20);
            this.rightArm1.zRot = toRads(60);
            this.rightArm2.xRot = toRads(-20);
            this.leftArm1.xRot = toRads(-30);
            this.leftArm1.yRot = toRads(40);
            this.leftArm1.zRot = toRads(-40);
            this.leftArm2.xRot = toRads(-70);
            this.rightLeg1.zRot = toRads(70);
            this.leftLeg1.xRot = toRads(-20);
            this.leftLeg1.zRot = toRads(-60);
            this.leftLeg2.zRot = toRads(110);
        } else if (fightPose == 1) {
            this.body.yRot = toRads(40);
            this.head.yRot = toRads(-25);
            this.leftArm1.xRot = toRads(-60);
            this.leftArm1.zRot = toRads(-70);
            this.leftArm2.xRot = toRads(-10);
            this.rightArm1.yRot = toRads(-20);
            this.rightArm1.xRot = toRads(30);
            this.rightArm2.xRot = toRads(-90);
            this.leftLeg1.yRot = toRads(-40);
            this.leftLeg1.xRot = toRads(-50);
            this.leftLeg2.xRot = toRads(50);
            this.rightLeg1.yRot = toRads(60);
            this.rightLeg1.xRot = toRads(-40);
            this.rightLeg2.xRot = toRads(25);
        } else if (fightPose == 2) {
            this.body.yRot = toRads(-60);
            this.head.yRot = toRads(60);
            this.head.xRot = toRads(-15);
            this.rightArm1.yRot = toRads(60);
            this.rightArm1.xRot = toRads(-140);
            this.rightArm2.xRot = toRads(-35);
            this.leftArm1.yRot = toRads(40);
            this.leftArm1.xRot = toRads(40);
            this.leftArm2.xRot = toRads(-70);
            this.rightLeg1.yRot = toRads(60);
            this.rightLeg1.xRot = toRads(-40);
            this.rightLeg2.xRot = toRads(55);
            this.leftLeg1.yRot = toRads(60);
            this.leftLeg1.xRot = toRads(30);
        }

        if (pEntity.isPartying()) {
            setRotateAngle(head, 0, 0, 0);
            float bodyRot = pAgeInTicks * 10f % 360;
            this.body.z = MathHelper.sin(toRads(bodyRot)) * 5;
            this.body.yRot = bodyRot > 90 && bodyRot < 270 ? 0 : PI;
            this.leftArm1.xRot = toRads(90);
            this.leftArm2.xRot = toRads(-90) + MathHelper.cos(pAgeInTicks * 0.5f) * toRads(20);
            this.rightArm1.xRot = toRads(-90);
            this.rightArm2.xRot = toRads(-90) + MathHelper.cos(pAgeInTicks * 0.5f) * toRads(20);
            this.leftLeg1.xRot = toRads(-20) + MathHelper.cos(pAgeInTicks * 0.5f) * toRads(20);
            this.leftLeg2.xRot = toRads(20) + MathHelper.cos(pAgeInTicks * 0.5f) * toRads(-10);
            this.rightLeg1.xRot = toRads(-20) - MathHelper.cos(pAgeInTicks * 0.5f) * toRads(20);
            this.rightLeg2.xRot = toRads(20) - MathHelper.cos(pAgeInTicks * 0.5f) * toRads(-10);
        } else if (pEntity.isSitting()) {
            this.head.xRot = toRads(40);
            this.head.yRot = toRads(-20);
            this.body.xRot = toRads(35);
            this.leftArm1.xRot = toRads(-50);
            this.leftArm2.xRot = toRads(10);
            this.rightArm1.xRot = toRads(-50);
            this.rightArm2.xRot = toRads(10);
            this.leftLeg1.xRot = toRads(-35);
            this.rightLeg1.xRot = toRads(-35);
        }
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

    private void showModels(MummyFamiliarEntity entityIn) {
        boolean hasUpgrade = entityIn.hasBlacksmithUpgrade();

        this.leftGlove.visible = hasUpgrade;
        this.rightGlove.visible = hasUpgrade;
        this.crown.visible = entityIn.hasCrown();
        this.heka.visible = entityIn.hasHeka();
        this.tooth.visible = entityIn.hasTooth();
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
