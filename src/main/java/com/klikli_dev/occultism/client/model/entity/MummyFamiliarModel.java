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

package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.common.entity.familiar.MummyFamiliarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class MummyFamiliarModel extends EntityModel<MummyFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart head;
    public ModelPart leftArm1;
    public ModelPart leftLeg1;
    public ModelPart skeleton;
    public ModelPart spine;
    public ModelPart rightArm1;
    public ModelPart rightLeg1;
    public ModelPart nose;
    public ModelPart eyeSockets;
    public ModelPart leftEye;
    public ModelPart rightEye;
    public ModelPart crown;
    public ModelPart tooth;
    public ModelPart leftArm2;
    public ModelPart leftArmBandage;
    public ModelPart heka;
    public ModelPart leftGlove;
    public ModelPart leftLeg2;
    public ModelPart leftLegBandage;
    public ModelPart rightArm2;
    public ModelPart rightGlove;
    public ModelPart rightArmBandage;
    public ModelPart rightLeg2;
    public ModelPart leftLegBandage_1;

    public MummyFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.head = this.body.getChild("head");
        this.leftArm1 = this.body.getChild("leftArm1");
        this.leftLeg1 = this.body.getChild("leftLeg1");
        this.skeleton = this.body.getChild("skeleton");
        this.spine = this.body.getChild("spine");
        this.rightArm1 = this.body.getChild("rightArm1");
        this.rightLeg1 = this.body.getChild("rightLeg1");
        this.nose = this.head.getChild("nose");
        this.eyeSockets = this.head.getChild("eyeSockets");
        this.leftEye = this.head.getChild("leftEye");
        this.rightEye = this.head.getChild("rightEye");
        this.crown = this.head.getChild("crown");
        this.tooth = this.nose.getChild("tooth");
        this.leftArm2 = this.leftArm1.getChild("leftArm2");
        this.leftArmBandage = this.leftArm1.getChild("leftArmBandage");
        this.heka = this.leftArm2.getChild("heka");
        this.leftGlove = this.leftArm2.getChild("leftGlove");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.leftLegBandage = this.leftLeg1.getChild("leftLegBandage");
        this.rightArm2 = this.rightArm1.getChild("rightArm2");
        this.rightGlove = this.rightArm2.getChild("rightGlove");
        this.rightArmBandage = this.rightArm2.getChild("rightArmBandage");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.leftLegBandage_1 = this.rightLeg2.getChild("leftLegBandage_1");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, -1.5F, 5.0F, 7.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0, 0, 0));
        PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(44, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.51F, -7.0F, 0.0F, -0.27366763203903305F, 0.0F, 0.0F));
        PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(40, 6).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(1.49F, -0.5F, 0.0F, 0, 0, 0));
        PartDefinition skeleton = body.addOrReplaceChild("skeleton", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -7.0F, -1.0F, 4.0F, 7.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.01F, 0, 0, 0));
        PartDefinition spine = body.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 7.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.49F, -7.0F, 0.0F, -0.27366763203903305F, 0.0F, 0.0F));
        PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(40, 6).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, true), PartPose.offsetAndRotation(-1.51F, -0.5F, 0.0F, 0, 0, 0));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(34, 0).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 0, 0, 0));
        PartDefinition eyeSockets = head.addOrReplaceChild("eyeSockets", CubeListBuilder.create().texOffs(52, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0, 0, 0));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, -3.0F, -2.0F, 0, 0, 0));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-1.0F, -3.0F, -2.0F, 0, 0, 0));
        PartDefinition crown = head.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(4, 22).addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, -1.5F, -0.0781907508222411F, 0.0F, 0.0F));
        PartDefinition tooth = nose.addOrReplaceChild("tooth", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.8F, -0.5F, 0, 0, 0));
        PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(48, 6).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.01F, 3.5F, 0.01F, -0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition leftArmBandage = leftArm1.addOrReplaceChild("leftArmBandage", CubeListBuilder.create().texOffs(44, 28).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.5F, 1.6F, 1.7F, 0.0F, 1.7453292129831807E-4F, 2.9717721919392552F));
        PartDefinition heka = leftArm2.addOrReplaceChild("heka", CubeListBuilder.create().texOffs(24, 22).addBox(0.0F, -2.0F, -6.0F, 0.0F, 4.0F, 6.0F, false), PartPose.offsetAndRotation(1.0F, 3.5F, -1.0F, 0, 0, 0));
        PartDefinition leftGlove = leftArm2.addOrReplaceChild("leftGlove", CubeListBuilder.create().texOffs(36, 26).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(1.0F, 3.7F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(40, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.01F, 2.5F, 0.01F, 0, 0, 0));
        PartDefinition leftLegBandage = leftLeg1.addOrReplaceChild("leftLegBandage", CubeListBuilder.create().texOffs(44, 28).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.5F, 1.5F, 0.0F, 0.27366763203903305F, 0.1563815016444822F));
        PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(48, 6).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 3.5F, 0.01F, -0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition rightGlove = rightArm2.addOrReplaceChild("rightGlove", CubeListBuilder.create().texOffs(36, 26).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, true), PartPose.offsetAndRotation(-1.0F, 3.7F, 0.0F, 0, 0, 0));
        PartDefinition rightArmBandage = rightArm2.addOrReplaceChild("rightArmBandage", CubeListBuilder.create().texOffs(44, 28).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-1.0F, 2.6F, 1.7F, 0.03909537541112055F, 0.23474678106428595F, 2.72637894171943F));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(40, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 2.5F, 0.01F, 0, 0, 0));
        PartDefinition leftLegBandage_1 = rightLeg2.addOrReplaceChild("leftLegBandage_1", CubeListBuilder.create().texOffs(44, 28).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-0.5F, 1.9F, 1.5F, 0.0781907508222411F, -0.03909537541112055F, -1.9957839730239049F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
    }

    @Override
    public void setupAnim(MummyFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        this.showModels(pEntity);

        this.setRotateAngle(this.body, 0, 0, 0);
        this.setRotateAngle(this.head, 0, 0, 0);
        this.setRotateAngle(this.rightArm1, -0.274f, 0, 0);
        this.setRotateAngle(this.rightArm2, -0.43f, 0, 0);
        this.setRotateAngle(this.leftArm1, -0.274f, 0, 0);
        this.setRotateAngle(this.leftArm2, -0.43f, 0, 0);
        this.setRotateAngle(this.rightLeg1, 0, 0, 0);
        this.setRotateAngle(this.rightLeg2, 0, 0, 0);
        this.setRotateAngle(this.leftLeg1, 0, 0, 0);
        this.setRotateAngle(this.leftLeg2, 0, 0, 0);
        this.body.z = 0;

        this.head.xRot = this.toRads(pHeadPitch);
        this.head.yRot = this.toRads(pNetHeadYaw);
        this.rightLeg1.xRot = Mth.cos(pLimbSwing * 0.5f + PI) * this.toRads(40) * pLimbSwingAmount;
        this.leftLeg1.xRot = Mth.cos(pLimbSwing * 0.5f) * this.toRads(40) * pLimbSwingAmount;
        this.rightLeg2.xRot = Math.abs(Mth.cos(pLimbSwing * 0.5f + PI)) * this.toRads(40) * pLimbSwingAmount;
        this.leftLeg2.xRot = Math.abs(Mth.cos(pLimbSwing * 0.5f) * this.toRads(40)) * pLimbSwingAmount;
        this.rightArm1.xRot = Mth.cos(pLimbSwing * 0.5f) * this.toRads(40) * pLimbSwingAmount;
        this.leftArm1.xRot = Mth.cos(pLimbSwing * 0.5f + PI) * this.toRads(40) * pLimbSwingAmount;
        this.rightArm2.xRot = this.toRads(-30) + Mth.cos(pLimbSwing * 0.5f) * this.toRads(20) * pLimbSwingAmount;
        this.leftArm2.xRot = this.toRads(-30) + Mth.cos(pLimbSwing * 0.5f + PI) * this.toRads(20) * pLimbSwingAmount;

        int fightPose = pEntity.getFightPose();

        if (fightPose == 0) {
            this.body.yRot = this.toRads(-60);
            this.body.xRot = this.toRads(-40);
            this.body.zRot = this.toRads(40);
            this.head.yRot = this.toRads(60);
            this.head.xRot = this.toRads(20);
            this.rightArm1.xRot = this.toRads(20);
            this.rightArm1.zRot = this.toRads(60);
            this.rightArm2.xRot = this.toRads(-20);
            this.leftArm1.xRot = this.toRads(-30);
            this.leftArm1.yRot = this.toRads(40);
            this.leftArm1.zRot = this.toRads(-40);
            this.leftArm2.xRot = this.toRads(-70);
            this.rightLeg1.zRot = this.toRads(70);
            this.leftLeg1.xRot = this.toRads(-20);
            this.leftLeg1.zRot = this.toRads(-60);
            this.leftLeg2.zRot = this.toRads(110);
        } else if (fightPose == 1) {
            this.body.yRot = this.toRads(40);
            this.head.yRot = this.toRads(-25);
            this.leftArm1.xRot = this.toRads(-60);
            this.leftArm1.zRot = this.toRads(-70);
            this.leftArm2.xRot = this.toRads(-10);
            this.rightArm1.yRot = this.toRads(-20);
            this.rightArm1.xRot = this.toRads(30);
            this.rightArm2.xRot = this.toRads(-90);
            this.leftLeg1.yRot = this.toRads(-40);
            this.leftLeg1.xRot = this.toRads(-50);
            this.leftLeg2.xRot = this.toRads(50);
            this.rightLeg1.yRot = this.toRads(60);
            this.rightLeg1.xRot = this.toRads(-40);
            this.rightLeg2.xRot = this.toRads(25);
        } else if (fightPose == 2) {
            this.body.yRot = this.toRads(-60);
            this.head.yRot = this.toRads(60);
            this.head.xRot = this.toRads(-15);
            this.rightArm1.yRot = this.toRads(60);
            this.rightArm1.xRot = this.toRads(-140);
            this.rightArm2.xRot = this.toRads(-35);
            this.leftArm1.yRot = this.toRads(40);
            this.leftArm1.xRot = this.toRads(40);
            this.leftArm2.xRot = this.toRads(-70);
            this.rightLeg1.yRot = this.toRads(60);
            this.rightLeg1.xRot = this.toRads(-40);
            this.rightLeg2.xRot = this.toRads(55);
            this.leftLeg1.yRot = this.toRads(60);
            this.leftLeg1.xRot = this.toRads(30);
        }

        if (pEntity.isPartying()) {
            this.setRotateAngle(this.head, 0, 0, 0);
            float bodyRot = pAgeInTicks * 10f % 360;
            this.body.z = Mth.sin(this.toRads(bodyRot)) * 5;
            this.body.yRot = bodyRot > 90 && bodyRot < 270 ? 0 : PI;
            this.leftArm1.xRot = this.toRads(90);
            this.leftArm2.xRot = this.toRads(-90) + Mth.cos(pAgeInTicks * 0.5f) * this.toRads(20);
            this.rightArm1.xRot = this.toRads(-90);
            this.rightArm2.xRot = this.toRads(-90) + Mth.cos(pAgeInTicks * 0.5f) * this.toRads(20);
            this.leftLeg1.xRot = this.toRads(-20) + Mth.cos(pAgeInTicks * 0.5f) * this.toRads(20);
            this.leftLeg2.xRot = this.toRads(20) + Mth.cos(pAgeInTicks * 0.5f) * this.toRads(-10);
            this.rightLeg1.xRot = this.toRads(-20) - Mth.cos(pAgeInTicks * 0.5f) * this.toRads(20);
            this.rightLeg2.xRot = this.toRads(20) - Mth.cos(pAgeInTicks * 0.5f) * this.toRads(-10);
        } else if (pEntity.isSitting()) {
            this.head.xRot = this.toRads(40);
            this.head.yRot = this.toRads(-20);
            this.body.xRot = this.toRads(35);
            this.leftArm1.xRot = this.toRads(-50);
            this.leftArm2.xRot = this.toRads(10);
            this.rightArm1.xRot = this.toRads(-50);
            this.rightArm2.xRot = this.toRads(10);
            this.leftLeg1.xRot = this.toRads(-35);
            this.rightLeg1.xRot = this.toRads(-35);
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
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
