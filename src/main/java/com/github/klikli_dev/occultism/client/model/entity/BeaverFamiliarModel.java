/*
 * MIT License
 *
 * Copyright 2022 vemerion
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

import com.github.klikli_dev.occultism.common.entity.BeaverFamiliarEntity;
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
public class BeaverFamiliarModel extends EntityModel<BeaverFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart tail;
    public ModelPart leftLeg1;
    public ModelPart head;
    public ModelPart leftArm1;
    public ModelPart tail2;
    public ModelPart rightLeg1;
    public ModelPart rightArm1;
    public ModelPart leftLeg2;
    public ModelPart mouth;
    public ModelPart leftEye;
    public ModelPart leftEar;
    public ModelPart rightEye;
    public ModelPart rightEar;
    public ModelPart nose;
    public ModelPart teeth;
    public ModelPart whiskers1;
    public ModelPart whiskers2;
    public ModelPart leftArm2;
    public ModelPart rightLeg2;
    public ModelPart rightArm2;

    public BeaverFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.tail = this.body.getChild("tail");
        this.leftLeg1 = this.body.getChild("leftLeg1");
        this.head = this.body.getChild("head");
        this.leftArm1 = this.body.getChild("leftArm1");
        this.tail2 = this.body.getChild("tail2");
        this.rightLeg1 = this.body.getChild("rightLeg1");
        this.rightArm1 = this.body.getChild("rightArm1");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.mouth = this.head.getChild("mouth");
        this.leftEye = this.head.getChild("leftEye");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEye = this.head.getChild("rightEye");
        this.rightEar = this.head.getChild("rightEar");
        this.nose = this.mouth.getChild("nose");
        this.teeth = this.mouth.getChild("teeth");
        this.whiskers1 = this.mouth.getChild("whiskers1");
        this.whiskers2 = this.mouth.getChild("whiskers2");
        this.leftArm2 = this.leftArm1.getChild("leftArm2");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.rightArm2 = this.rightArm1.getChild("rightArm2");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 19.5F, 0.0F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(-10, 22).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 5.0F, 0.5082398928281348F, 0.0F, 0.0F));
        PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, 0.0F, -2.0F, 2.0F, 6.0F, 4.0F, false), PartPose.offsetAndRotation(2.0F, -1.5F, 2.5F, -0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(43, 0).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, -0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(32, 9).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(1.7F, 0.0F, -3.0F, -0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition tail2 = body.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(16, 22).addBox(-3.5F, -0.5F, 0.0F, 7.0F, 1.0F, 9.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 5.0F, 0.5082398928281348F, 0.0F, 0.0F));
        PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(22, 0).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 6.0F, 4.0F, true), PartPose.offsetAndRotation(-2.0F, -1.5F, 2.5F, -0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(32, 9).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-1.7F, 0.0F, -3.0F, -0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(34, 0).addBox(-1.5F, -0.5F, -4.0F, 3.0F, 1.0F, 4.0F, false), PartPose.offsetAndRotation(1.48F, 5.7F, 0.5F, 0, 0, 0));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.3F, -5.0F, 0, 0, 0));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(34, 5).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, -0.5F, -3.0F, 0, 0, 0));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(1.5F, -2.1F, -2.1F, -0.23457224414434488F, -0.35185837453889574F, 0.5082398928281348F));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(34, 5).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-2.0F, -0.5F, -3.0F, 0, 0, 0));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, true), PartPose.offsetAndRotation(-1.5F, -2.1F, -2.1F, -0.23457224414434488F, 0.35185837453889574F, -0.5082398928281348F));
        PartDefinition nose = mouth.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -0.2F, -1.7F, 0, 0, 0));
        PartDefinition teeth = mouth.addOrReplaceChild("teeth", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 1.0F, -0.8F, 0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition whiskers1 = mouth.addOrReplaceChild("whiskers1", CubeListBuilder.create().texOffs(4, 20).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.3F, -1.7F, 0.3909537457888271F, 0.039269908169872414F, 0.017453292519943295F));
        PartDefinition whiskers2 = mouth.addOrReplaceChild("whiskers2", CubeListBuilder.create().texOffs(4, 20).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -0.8F, -1.3F, 0.3909537457888271F, 0.039269908169872414F, -0.017453292519943295F));
        PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(40, 10).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(1.02F, 3.7F, 0.02F, 0, 0, 0));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(34, 0).addBox(-1.5F, -0.5F, -4.0F, 3.0F, 1.0F, 4.0F, true), PartPose.offsetAndRotation(-1.48F, 5.7F, 0.5F, 0, 0, 0));
        PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(40, 10).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 3.0F, true), PartPose.offsetAndRotation(-1.02F, 3.7F, 0.02F, 0, 0, 0));
        return LayerDefinition.create(mesh, 64, 32);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }


    @Override
    public void setupAnim(BeaverFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
                          float netHeadYaw, float headPitch) {
        this.showModels(pEntity);

        this.rightLeg1.zRot = 0;
        this.leftLeg1.zRot = 0;
        this.rightArm1.zRot = 0;
        this.leftArm1.zRot = 0;
        this.body.xRot = 0.09f;
        this.body.yRot = 0;
        this.body.y = 19.5f;
        this.leftLeg2.xRot = 0;
        this.rightLeg2.xRot = 0;
        this.leftArm2.xRot = 0;
        this.rightArm2.xRot = 0;


        this.head.xRot = this.toRads(headPitch);
        this.head.yRot = this.toRads(netHeadYaw);

        this.rightLeg1.xRot = -0.07f + Mth.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount;
        this.leftLeg1.xRot = -0.07f + Mth.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount;
        this.rightArm1.xRot = -0.07f + Mth.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount;
        this.leftArm1.xRot = -0.07f + Mth.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount;

        this.tail.xRot = 0.51f + Mth.cos(pAgeInTicks * 0.1f) * this.toRads(20);
        this.tail2.xRot = 0.51f + Mth.cos(pAgeInTicks * 0.1f) * this.toRads(20);

        if (!pEntity.isSitting() && pEntity.isInWater()) {
            this.rightLeg1.zRot = this.toRads(40);
            this.leftLeg1.zRot = -this.toRads(40);
            this.rightArm1.zRot = this.toRads(40);
            this.leftArm1.zRot = -this.toRads(40);

            this.rightLeg1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.3f) * 0.4f;
            this.leftLeg1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.3f) * 0.4f;
            this.rightArm1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.3f + PI) * 0.4f;
            this.leftArm1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.3f + PI) * 0.4f;
        }

        if (pEntity.isSitting()) {
            this.body.xRot = this.toRads(-40);
            this.head.xRot = this.toRads(25);
            this.head.yRot = 0;
            this.tail.xRot = this.toRads(70);
            this.tail2.xRot = this.toRads(70);

            this.leftLeg1.xRot = this.toRads(-20);
            this.leftLeg2.xRot = this.toRads(50);
            this.rightLeg1.xRot = this.toRads(-20);
            this.rightLeg2.xRot = this.toRads(50);

            this.leftArm1.xRot = this.toRads(10);
            this.leftArm2.xRot = this.toRads(40);
            this.rightArm1.xRot = this.toRads(10);
            this.rightArm2.xRot = this.toRads(40);
        }

        if (pEntity.isPartying()) {
            this.body.xRot = this.toRads(90);
            this.body.yRot = pAgeInTicks * 0.5f;
            this.body.y = 12.5f;
            this.head.xRot = 0;
            this.head.yRot = 0;

            this.tail.xRot = Mth.cos(pAgeInTicks * 0.8f) * this.toRads(50);
            this.tail2.xRot = Mth.cos(pAgeInTicks * 0.8f) * this.toRads(50);

            this.rightLeg1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.7f) * this.toRads(40);
            this.leftLeg1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.7f + PI) * this.toRads(40);
            this.rightArm1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.7f + PI) * this.toRads(40);
            this.leftArm1.xRot = -0.07f + Mth.cos(pAgeInTicks * 0.7f) * this.toRads(40);
        }
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

    private void showModels(BeaverFamiliarEntity entityIn) {
        boolean hasEars = entityIn.hasEars();
        boolean hasWhiskers = entityIn.hasWhiskers();
        boolean hasBigTail = entityIn.hasBigTail();

        this.leftEar.visible = hasEars;
        this.rightEar.visible = hasEars;
        this.whiskers1.visible = hasWhiskers;
        this.whiskers2.visible = hasWhiskers;
        this.tail.visible = !hasBigTail;
        this.tail2.visible = hasBigTail;
    }
}
