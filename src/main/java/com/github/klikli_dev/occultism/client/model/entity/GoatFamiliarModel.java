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

import com.github.klikli_dev.occultism.common.entity.GoatFamiliarEntity;
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
public class GoatFamiliarModel extends EntityModel<GoatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart leftBackLeg1;
    public ModelPart rightBackLeg1;
    public ModelPart neck;
    public ModelPart tail;
    public ModelPart leftFrontLeg1;
    public ModelPart rightFrontLeg1;
    public ModelPart leftBackLeg2;
    public ModelPart leftBackLeg3;
    public ModelPart rightBackLeg2;
    public ModelPart rightBackLeg3;
    public ModelPart head;
    public ModelPart bell1;
    public ModelPart rightHorn1;
    public ModelPart mouth;
    public ModelPart leftHorn1;
    public ModelPart leftEyeEvil;
    public ModelPart rightEyeEvil;
    public ModelPart leftSideHornEvil1;
    public ModelPart rightSideHornEvil1;
    public ModelPart rightHorn2;
    public ModelPart beard;
    public ModelPart ring;
    public ModelPart leftHorn2;
    public ModelPart leftPupilEvil;
    public ModelPart rightPupilEvil;
    public ModelPart leftSideHornEvil2;
    public ModelPart leftSideHornEvil3;
    public ModelPart rightSideHornEvil2;
    public ModelPart rightSideHornEvil3;
    public ModelPart bell2;
    public ModelPart bell3;
    public ModelPart leftFrontLeg2;
    public ModelPart leftFrontLeg3;
    public ModelPart rightFrontLeg2;
    public ModelPart rightFrontLeg3;

    public GoatFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.leftBackLeg1 = this.body.getChild("leftBackLeg1");
        this.rightBackLeg1 = this.body.getChild("rightBackLeg1");
        this.neck = this.body.getChild("neck");
        this.tail = this.body.getChild("tail");
        this.leftFrontLeg1 = this.body.getChild("leftFrontLeg1");
        this.rightFrontLeg1 = this.body.getChild("rightFrontLeg1");
        this.leftBackLeg2 = this.leftBackLeg1.getChild("leftBackLeg2");
        this.leftBackLeg3 = this.leftBackLeg2.getChild("leftBackLeg3");
        this.rightBackLeg2 = this.rightBackLeg1.getChild("rightBackLeg2");
        this.rightBackLeg3 = this.rightBackLeg2.getChild("rightBackLeg3");
        this.head = this.neck.getChild("head");
        this.bell1 = this.neck.getChild("bell1");
        this.rightHorn1 = this.head.getChild("rightHorn1");
        this.mouth = this.head.getChild("mouth");
        this.leftHorn1 = this.head.getChild("leftHorn1");
        this.leftEyeEvil = this.head.getChild("leftEyeEvil");
        this.rightEyeEvil = this.head.getChild("rightEyeEvil");
        this.leftSideHornEvil1 = this.head.getChild("leftSideHornEvil1");
        this.rightSideHornEvil1 = this.head.getChild("rightSideHornEvil1");
        this.rightHorn2 = this.rightHorn1.getChild("rightHorn2");
        this.beard = this.mouth.getChild("beard");
        this.ring = this.mouth.getChild("ring");
        this.leftHorn2 = this.leftHorn1.getChild("leftHorn2");
        this.leftPupilEvil = this.leftEyeEvil.getChild("leftPupilEvil");
        this.rightPupilEvil = this.rightEyeEvil.getChild("rightPupilEvil");
        this.leftSideHornEvil2 = this.leftSideHornEvil1.getChild("leftSideHornEvil2");
        this.leftSideHornEvil3 = this.leftSideHornEvil2.getChild("leftSideHornEvil3");
        this.rightSideHornEvil2 = this.rightSideHornEvil1.getChild("rightSideHornEvil2");
        this.rightSideHornEvil3 = this.rightSideHornEvil2.getChild("rightSideHornEvil3");
        this.bell2 = this.bell1.getChild("bell2");
        this.bell3 = this.bell2.getChild("bell3");
        this.leftFrontLeg2 = this.leftFrontLeg1.getChild("leftFrontLeg2");
        this.leftFrontLeg3 = this.leftFrontLeg2.getChild("leftFrontLeg3");
        this.rightFrontLeg2 = this.rightFrontLeg1.getChild("rightFrontLeg2");
        this.rightFrontLeg3 = this.rightFrontLeg2.getChild("rightFrontLeg3");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -5.0F, 4.0F, 5.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 17.0F, 1.0F, 0, 0, 0));
        PartDefinition leftBackLeg1 = body.addOrReplaceChild("leftBackLeg1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(1.8F, -0.1F, 3.9F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition rightBackLeg1 = body.addOrReplaceChild("rightBackLeg1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-1.8F, -0.1F, 3.9F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.5F, -4.0F, 2.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -1.8F, -3.6F, -0.8726646259971648F, 0.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(55, 6).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.6F, 4.3F, 0.19547687289441354F, 0.0F, 0.0F));
        PartDefinition leftFrontLeg1 = body.addOrReplaceChild("leftFrontLeg1", CubeListBuilder.create().texOffs(37, 7).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(1.8F, -0.1F, -3.1F, -0.12217304763960307F, 0.0F, 0.0F));
        PartDefinition rightFrontLeg1 = body.addOrReplaceChild("rightFrontLeg1", CubeListBuilder.create().texOffs(37, 7).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-1.8F, -0.1F, -3.1F, -0.12217304763960307F, 0.0F, 0.0F));
        PartDefinition leftBackLeg2 = leftBackLeg1.addOrReplaceChild("leftBackLeg2", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 3.3F, -0.1F, 0.8600982340775168F, 0.0F, 0.0F));
        PartDefinition leftBackLeg3 = leftBackLeg2.addOrReplaceChild("leftBackLeg3", CubeListBuilder.create().texOffs(6, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, 2.2F, 0.5F, -1.2119566751954398F, 0.0F, 0.0F));
        PartDefinition rightBackLeg2 = rightBackLeg1.addOrReplaceChild("rightBackLeg2", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 3.3F, -0.1F, 0.8600982340775168F, 0.0F, 0.0F));
        PartDefinition rightBackLeg3 = rightBackLeg2.addOrReplaceChild("rightBackLeg3", CubeListBuilder.create().texOffs(6, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, true), PartPose.offsetAndRotation(0.01F, 2.2F, 0.5F, -1.2119566751954398F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -0.8F, -3.1F, 0.9641199080964395F, 0.0F, 0.0F));
        PartDefinition bell1 = neck.addOrReplaceChild("bell1", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.6F, -2.1F, 0.1563815016444822F, 0.0F, 0.0F));
        PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(18, 4).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-0.9F, -0.6F, 0.2F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(27, 4).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(18, 4).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.9F, -0.6F, 0.2F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition leftEyeEvil = head.addOrReplaceChild("leftEyeEvil", CubeListBuilder.create().texOffs(24, 15).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.3F, -0.2F, -1.7F, 0, 0, 0));
        PartDefinition rightEyeEvil = head.addOrReplaceChild("rightEyeEvil", CubeListBuilder.create().texOffs(24, 15).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-1.3F, -0.2F, -1.7F, 0, 0, 0));
        PartDefinition leftSideHornEvil1 = head.addOrReplaceChild("leftSideHornEvil1", CubeListBuilder.create().texOffs(27, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.0F, 0.4F, -0.3F, 0.6981317007977318F, 0.0F, 0.8991936386169619F));
        PartDefinition rightSideHornEvil1 = head.addOrReplaceChild("rightSideHornEvil1", CubeListBuilder.create().texOffs(27, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-1.0F, 0.4F, -0.3F, 0.6981317007977318F, 0.0F, -0.8991936386169619F));
        PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(57, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(0.01F, 0.2F, 1.0F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition beard = mouth.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(45, 6).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0, 0, 0));
        PartDefinition ring = mouth.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(0, 29).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(57, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 0.2F, 1.0F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition leftPupilEvil = leftEyeEvil.addOrReplaceChild("leftPupilEvil", CubeListBuilder.create().texOffs(22, 15).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition rightPupilEvil = rightEyeEvil.addOrReplaceChild("rightPupilEvil", CubeListBuilder.create().texOffs(22, 15).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-0.2F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition leftSideHornEvil2 = leftSideHornEvil1.addOrReplaceChild("leftSideHornEvil2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, -0.2F, 0.9F, -0.44820055723846597F, 0.0F, 0.0F));
        PartDefinition leftSideHornEvil3 = leftSideHornEvil2.addOrReplaceChild("leftSideHornEvil3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.0F, 0.45099308137849586F, 0.0F, 0.0F));
        PartDefinition rightSideHornEvil2 = rightSideHornEvil1.addOrReplaceChild("rightSideHornEvil2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, -0.2F, 0.9F, -0.44820055723846597F, 0.0F, 0.0F));
        PartDefinition rightSideHornEvil3 = rightSideHornEvil2.addOrReplaceChild("rightSideHornEvil3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.0F, 0.45099308137849586F, 0.0F, 0.0F));
        PartDefinition bell2 = bell1.addOrReplaceChild("bell2", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 4.0F, -0.1F, 0.6646214111173737F, 0.0F, 0.0F));
        PartDefinition bell3 = bell2.addOrReplaceChild("bell3", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0, 0, 0));
        PartDefinition leftFrontLeg2 = leftFrontLeg1.addOrReplaceChild("leftFrontLeg2", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-0.01F, 4.6F, 0.0F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition leftFrontLeg3 = leftFrontLeg2.addOrReplaceChild("leftFrontLeg3", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, 1.5F, 0.0F, -0.19495327877934715F, 0.0F, 0.0F));
        PartDefinition rightFrontLeg2 = rightFrontLeg1.addOrReplaceChild("rightFrontLeg2", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, true), PartPose.offsetAndRotation(-0.01F, 4.6F, 0.0F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition rightFrontLeg3 = rightFrontLeg2.addOrReplaceChild("rightFrontLeg3", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-0.01F, 1.5F, -0.1F, -0.19495327877934715F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(GoatFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
                          float netHeadYaw, float headPitch) {
        this.showModels(pEntity);

        this.body.zRot = 0;
        this.neck.xRot = -0.87f;

        this.head.yRot = this.toRads(netHeadYaw) * 0.5f;
        this.head.zRot = this.toRads(netHeadYaw) * 0.5f;
        this.head.xRot = this.toRads(headPitch) * 0.5f + 0.96f;

        this.rightBackLeg1.xRot = Mth.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount - 0.23f;
        this.leftBackLeg1.xRot = Mth.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount - 0.23f;
        this.rightFrontLeg1.xRot = Mth.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount - 0.12f;
        this.leftFrontLeg1.xRot = Mth.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount - 0.12f;

        if (pEntity.isSitting()) {
            this.body.zRot = this.toRads(90);
            this.neck.xRot = this.toRads(10);
        }

        if (pEntity.isPartying()) {
            this.body.zRot = this.toRads(180);
            this.neck.xRot = this.toRads(10);
            this.rightBackLeg1.xRot = Mth.cos(pAgeInTicks * 0.5f) * this.toRads(25) - 0.23f;
            this.leftBackLeg1.xRot = Mth.cos(pAgeInTicks * 0.5f + PI) * this.toRads(25) - 0.23f;
            this.rightFrontLeg1.xRot = Mth.cos(pAgeInTicks * 0.5f + PI) * this.toRads(25) - 0.12f;
            this.leftFrontLeg1.xRot = Mth.cos(pAgeInTicks * 0.5f) * this.toRads(25) - 0.12f;
        }

        this.bell2.zRot = Mth.cos(limbSwing * 0.7f) * 0.5f * limbSwingAmount;
    }

    @Override
    public void prepareMobModel(GoatFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
                                float pPartialTick) {
        this.neck.yRot = pEntity.getNeckYRot(pPartialTick);
    }

    private void showModels(GoatFamiliarEntity entityIn) {
        boolean hasRedEyes = entityIn.hasRedEyes();
        boolean hasEvilHorns = entityIn.hasEvilHorns();

        this.ring.visible = entityIn.hasRing();
        this.beard.visible = entityIn.hasBeard();
        this.bell1.visible = entityIn.hasBlacksmithUpgrade();
        this.leftEyeEvil.visible = hasRedEyes;
        this.rightEyeEvil.visible = hasRedEyes;
        this.leftHorn1.visible = !hasEvilHorns;
        this.rightHorn1.visible = !hasEvilHorns;
        this.leftSideHornEvil1.visible = hasEvilHorns;
        this.rightSideHornEvil1.visible = hasEvilHorns;
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }
}
