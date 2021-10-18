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

import com.github.klikli_dev.occultism.common.entity.HeadlessFamiliarEntity;
import com.google.common.collect.ImmutableList;
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
public class HeadlessFamiliarModel extends EntityModel<HeadlessFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart ratBody1;
    public ModelPart ratBody2;
    public ModelPart ratBackLeftLeg1;
    public ModelPart ratTail1;
    public ModelPart body;
    public ModelPart ratHair1;
    public ModelPart ratHair2;
    public ModelPart ratBackRightLeg1;
    public ModelPart ratHead;
    public ModelPart ratFrontLeftLeg1;
    public ModelPart ratHair3;
    public ModelPart ratFrontRightLeg1;
    public ModelPart ratNose;
    public ModelPart ratLeftEar;
    public ModelPart ratRightEar;
    public ModelPart ratTeeth;
    public ModelPart ratGlasses;
    public ModelPart ratFrontLeftLeg2;
    public ModelPart ratFrontLeftLeg3;
    public ModelPart ratFrontRightLeg2;
    public ModelPart ratFrontRightLeg3;
    public ModelPart ratBackLeftLeg2;
    public ModelPart ratBackLeftLeg3;
    public ModelPart ratTail2;
    public ModelPart leftLeg;
    public ModelPart leftArm;
    public ModelPart mantle;
    public ModelPart collar;
    public ModelPart rightArm;
    public ModelPart rightLeg;
    public ModelPart helmet;
    public ModelPart pumpkin1;
    public ModelPart pumpkin2;
    public ModelPart pumpkin3;
    public ModelPart pumpkin4;
    public ModelPart ratBackRightLeg2;
    public ModelPart ratBackRightLeg3;

    public HeadlessFamiliarModel(ModelPart part) {
        this.ratBody1 = part.getChild("ratBody1");
        this.ratBody2 = this.ratBody1.getChild("ratBody2");
        this.ratBackLeftLeg1 = this.ratBody1.getChild("ratBackLeftLeg1");
        this.ratTail1 = this.ratBody1.getChild("ratTail1");
        this.body = this.ratBody1.getChild("body");
        this.ratHair1 = this.ratBody1.getChild("ratHair1");
        this.ratHair2 = this.ratBody1.getChild("ratHair2");
        this.ratBackRightLeg1 = this.ratBody1.getChild("ratBackRightLeg1");
        this.ratHead = this.ratBody2.getChild("ratHead");
        this.ratFrontLeftLeg1 = this.ratBody2.getChild("ratFrontLeftLeg1");
        this.ratHair3 = this.ratBody2.getChild("ratHair3");
        this.ratFrontRightLeg1 = this.ratBody2.getChild("ratFrontRightLeg1");
        this.ratNose = this.ratHead.getChild("ratNose");
        this.ratLeftEar = this.ratHead.getChild("ratLeftEar");
        this.ratRightEar = this.ratHead.getChild("ratRightEar");
        this.ratTeeth = this.ratNose.getChild("ratTeeth");
        this.ratGlasses = this.ratNose.getChild("ratGlasses");
        this.ratFrontLeftLeg2 = this.ratFrontLeftLeg1.getChild("ratFrontLeftLeg2");
        this.ratFrontLeftLeg3 = this.ratFrontLeftLeg2.getChild("ratFrontLeftLeg3");
        this.ratFrontRightLeg2 = this.ratFrontRightLeg1.getChild("ratFrontRightLeg2");
        this.ratFrontRightLeg3 = this.ratFrontRightLeg2.getChild("ratFrontRightLeg3");
        this.ratBackLeftLeg2 = this.ratBackLeftLeg1.getChild("ratBackLeftLeg2");
        this.ratBackLeftLeg3 = this.ratBackLeftLeg2.getChild("ratBackLeftLeg3");
        this.ratTail2 = this.ratTail1.getChild("ratTail2");
        this.leftLeg = this.body.getChild("leftLeg");
        this.leftArm = this.body.getChild("leftArm");
        this.mantle = this.body.getChild("mantle");
        this.collar = this.body.getChild("collar");
        this.rightArm = this.body.getChild("rightArm");
        this.rightLeg = this.body.getChild("rightLeg");
        this.helmet = this.body.getChild("helmet");
        this.pumpkin1 = this.leftArm.getChild("pumpkin1");
        this.pumpkin2 = this.pumpkin1.getChild("pumpkin2");
        this.pumpkin3 = this.pumpkin1.getChild("pumpkin3");
        this.pumpkin4 = this.pumpkin1.getChild("pumpkin4");
        this.ratBackRightLeg2 = this.ratBackRightLeg1.getChild("ratBackRightLeg2");
        this.ratBackRightLeg3 = this.ratBackRightLeg2.getChild("ratBackRightLeg3");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition ratBody1 = parts.addOrReplaceChild("ratBody1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 4.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 19.4F, 3.0F, -0.0781907508222411F, 0.0F, 0.0F));
        PartDefinition ratBody2 = ratBody1.addOrReplaceChild("ratBody2", CubeListBuilder.create().texOffs(24, 0).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 4.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -0.1F, -5.0F, 0.1563815016444822F, 0.0F, 0.0F));
        PartDefinition ratBackLeftLeg1 = ratBody1.addOrReplaceChild("ratBackLeftLeg1", CubeListBuilder.create().texOffs(56, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(3.0F, 0.5F, 0.5F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition ratTail1 = ratBody1.addOrReplaceChild("ratTail1", CubeListBuilder.create().texOffs(48, 6).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -0.5F, 2.5F, -0.35185837453889574F, 0.0F, 0.0F));
        PartDefinition body = ratBody1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(30, 9).addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, 0, 0, 0));
        PartDefinition ratHair1 = ratBody1.addOrReplaceChild("ratHair1", CubeListBuilder.create().texOffs(4, 26).addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, false), PartPose.offsetAndRotation(2.5F, -2.0F, 0.0F, 0.23457224414434488F, 0.11728612207217244F, 0.5082398928281348F));
        PartDefinition ratHair2 = ratBody1.addOrReplaceChild("ratHair2", CubeListBuilder.create().texOffs(4, 26).addBox(-3.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, true), PartPose.offsetAndRotation(-2.5F, -1.3F, 0.0F, 0.4300491170387584F, 0.11728612207217244F, -0.11746065899211351F));
        PartDefinition ratBackRightLeg1 = ratBody1.addOrReplaceChild("ratBackRightLeg1", CubeListBuilder.create().texOffs(56, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-3.0F, 0.5F, 0.5F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition ratHead = ratBody2.addOrReplaceChild("ratHead", CubeListBuilder.create().texOffs(44, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -0.5F, -2.3F, 0.0781907508222411F, 0.0F, 0.0F));
        PartDefinition ratFrontLeftLeg1 = ratBody2.addOrReplaceChild("ratFrontLeftLeg1", CubeListBuilder.create().texOffs(56, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, 0.8F, -0.5F, -0.19547687289441354F, 0.0F, 0.0F));
        PartDefinition ratHair3 = ratBody2.addOrReplaceChild("ratHair3", CubeListBuilder.create().texOffs(4, 26).addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, false), PartPose.offsetAndRotation(0.2F, -1.9F, -0.2F, 0.5082398928281348F, 0.0F, -1.4213961854347594F));
        PartDefinition ratFrontRightLeg1 = ratBody2.addOrReplaceChild("ratFrontRightLeg1", CubeListBuilder.create().texOffs(56, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-2.5F, 0.8F, -0.5F, -0.19547687289441354F, 0.0F, 0.0F));
        PartDefinition ratNose = ratHead.addOrReplaceChild("ratNose", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.2F, -3.0F, 0, 0, 0));
        PartDefinition ratLeftEar = ratHead.addOrReplaceChild("ratLeftEar", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(1.5F, -1.5F, -1.5F, 0.0F, 0.0F, 0.5082398928281348F));
        PartDefinition ratRightEar = ratHead.addOrReplaceChild("ratRightEar", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, true), PartPose.offsetAndRotation(-1.5F, -1.5F, -1.5F, 0.0F, 0.0F, -0.5082398928281348F));
        PartDefinition ratTeeth = ratNose.addOrReplaceChild("ratTeeth", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.5F, 0.5F, -1.8F, 0, 0, 0));
        PartDefinition ratGlasses = ratNose.addOrReplaceChild("ratGlasses", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -2.8F, -0.2F, 0, 0, 0));
        PartDefinition ratFrontLeftLeg2 = ratFrontLeftLeg1.addOrReplaceChild("ratFrontLeftLeg2", CubeListBuilder.create().texOffs(43, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.2F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition ratFrontLeftLeg3 = ratFrontLeftLeg2.addOrReplaceChild("ratFrontLeftLeg3", CubeListBuilder.create().texOffs(44, 6).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 1.7F, 0.4F, 0.35185837453889574F, 0.0F, 0.0F));
        PartDefinition ratFrontRightLeg2 = ratFrontRightLeg1.addOrReplaceChild("ratFrontRightLeg2", CubeListBuilder.create().texOffs(43, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, true), PartPose.offsetAndRotation(0.0F, 1.5F, 0.2F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition ratFrontRightLeg3 = ratFrontRightLeg2.addOrReplaceChild("ratFrontRightLeg3", CubeListBuilder.create().texOffs(44, 6).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(0.0F, 1.7F, 0.4F, 0.35185837453889574F, 0.0F, 0.0F));
        PartDefinition ratBackLeftLeg2 = ratBackLeftLeg1.addOrReplaceChild("ratBackLeftLeg2", CubeListBuilder.create().texOffs(43, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.4F, -0.5473352640780661F, 0.0F, 0.0F));
        PartDefinition ratBackLeftLeg3 = ratBackLeftLeg2.addOrReplaceChild("ratBackLeftLeg3", CubeListBuilder.create().texOffs(44, 6).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 1.7F, 0.4F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition ratTail2 = ratTail1.addOrReplaceChild("ratTail2", CubeListBuilder.create().texOffs(20, 9).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.19547687289441354F, 0.0F, 0.0F));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, -1.4144148417951712F, -0.35185837453889574F, 0.0F));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(8, 10).addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(2.3F, -5.01F, 0.0F, -1.2901473511162753F, -0.3909537457888271F, 0.0F));
        PartDefinition mantle = body.addOrReplaceChild("mantle", CubeListBuilder.create().texOffs(16, 25).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 7.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -6.0F, 1.5F, 0.46914448828868976F, 0.0F, 0.0F));
        PartDefinition collar = body.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(13, 14).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0, 0, 0));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(8, 10).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-2.3F, -5.01F, 0.0F, -1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, -1.4144148417951712F, 0.35185837453889574F, 0.0F));
        PartDefinition helmet = body.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0, 0, 0));
        PartDefinition pumpkin1 = leftArm.addOrReplaceChild("pumpkin1", CubeListBuilder.create().texOffs(44, 22).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(1.0F, 5.0F, -3.51F, 1.586853323955311F, 0.0F, 0.27366763203903305F));
        PartDefinition pumpkin2 = pumpkin1.addOrReplaceChild("pumpkin2", CubeListBuilder.create().texOffs(56, 7).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.23457224414434488F, 0.5082398928281348F, 0.27366763203903305F));
        PartDefinition pumpkin3 = pumpkin1.addOrReplaceChild("pumpkin3", CubeListBuilder.create().texOffs(34, 27).addBox(0.0F, -2.5F, 0.0F, 2.0F, 4.0F, 1.0F, false), PartPose.offsetAndRotation(-2.49F, 0.99F, -2.5F, 0, 0, 0));
        PartDefinition pumpkin4 = pumpkin1.addOrReplaceChild("pumpkin4", CubeListBuilder.create().texOffs(34, 27).addBox(0.0F, -2.5F, 0.0F, 2.0F, 4.0F, 1.0F, true), PartPose.offsetAndRotation(0.49F, 0.99F, -2.5F, 0, 0, 0));
        PartDefinition ratBackRightLeg2 = ratBackRightLeg1.addOrReplaceChild("ratBackRightLeg2", CubeListBuilder.create().texOffs(43, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, true), PartPose.offsetAndRotation(0.0F, 1.5F, 0.4F, -0.5473352640780661F, 0.0F, 0.0F));
        PartDefinition ratBackRightLeg3 = ratBackRightLeg2.addOrReplaceChild("ratBackRightLeg3", CubeListBuilder.create().texOffs(44, 6).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 1.7F, 0.4F, 0.3127630032889644F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }


    @Override
    public void setupAnim(HeadlessFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        this.showModels(pEntity);

        this.ratBody1.xRot = -0.078f;
        this.ratTail1.xRot = -0.195f;
        this.body.xRot = 0;
        this.ratHead.zRot = 0;
        this.ratTail1.zRot = 0;
        this.pumpkin1.z = -3.51f;
        this.pumpkin1.yRot = 0;

        this.ratHead.xRot = this.toRads(pHeadPitch) * 0.4f;
        this.ratHead.yRot = this.toRads(pNetHeadYaw) * 0.4f;

        this.ratTail1.yRot = Mth.sin(pAgeInTicks * 0.2f) * this.toRads(15);
        this.ratTail2.yRot = Mth.sin(pAgeInTicks * 0.2f) * this.toRads(15);

        this.ratBackLeftLeg1.xRot = 0.31f + Mth.cos(limbSwing * 0.7f + PI) * limbSwingAmount * 0.5f;
        this.ratBackRightLeg1.xRot = 0.31f + Mth.cos(limbSwing * 0.7f) * limbSwingAmount * 0.5f;
        this.ratFrontLeftLeg1.xRot = -0.20f + Mth.cos(limbSwing * 0.7f) * limbSwingAmount * 0.5f;
        this.ratFrontRightLeg1.xRot = -0.20f + Mth.cos(limbSwing * 0.7f + PI) * limbSwingAmount * 0.5f;
        this.rightArm.xRot = -1.57f + Mth.cos(limbSwing * 0.4f) * limbSwingAmount * 0.2f;

        if (this.attackTime > 0)
            this.rightArm.xRot = -1.57f + Mth.sin(this.attackTime * this.toRads(180)) * this.toRads(90);

        if (pEntity.isSitting()) {
            this.ratBody1.xRot = -this.toRads(40);
            this.ratHead.xRot += this.toRads(20);
            this.ratBackLeftLeg1.xRot = -this.toRads(10);
            this.ratBackRightLeg1.xRot = -this.toRads(10);
            this.ratFrontLeftLeg1.xRot = this.toRads(25);
            this.ratFrontRightLeg1.xRot = this.toRads(25);
            this.ratTail1.xRot = this.toRads(35);
            this.body.xRot = this.toRads(20);
        }

        if (pEntity.isPartying()) {
            this.ratHead.zRot = Mth.cos(pAgeInTicks * 0.5f) * this.toRads(40);
            this.ratTail1.zRot = Mth.cos(pAgeInTicks * 0.5f) * this.toRads(40);
            this.pumpkin1.z = -7f + Mth.cos(pAgeInTicks * 0.25f) * 2;
            this.pumpkin1.yRot = pAgeInTicks * this.toRads(10);
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(HeadlessFamiliarEntity entityIn) {
        boolean isHairy = entityIn.isHairy();

        this.pumpkin1.visible = !entityIn.hasHead();
        this.ratGlasses.visible = entityIn.hasGlasses();
        this.helmet.visible = entityIn.hasBlacksmithUpgrade();
        this.body.visible = !entityIn.isHeadlessDead();
        this.ratHair1.visible = isHairy;
        this.ratHair2.visible = isHairy;
        this.ratHair3.visible = isHairy;
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        ImmutableList.of(this.ratBody1).forEach((modelRenderer) -> {
            modelRenderer.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        });
    }
}
