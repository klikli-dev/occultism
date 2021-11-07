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

import com.github.klikli_dev.occultism.common.entity.CthulhuFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.ShubNiggurathFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import java.util.List;

/**
 * Created using Tabula 8.0.0
 */
public class ShubNiggurathFamiliarModel extends EntityModel<ShubNiggurathFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart neck;
    public ModelPart leftLeg1;
    public ModelPart tentacleTop1;
    public ModelPart tentacleMiddle1;
    public ModelPart tentacleBottom1;
    public ModelPart leftArm1;
    public ModelPart rightLeg1;
    public ModelPart rightArm1;
    public ModelPart tail;
    public ModelPart head;
    public ModelPart bell1;
    public ModelPart mouth;
    public ModelPart leftHorn1;
    public ModelPart leftSideHorn1;
    public ModelPart rightHorn1;
    public ModelPart rightSideHorn1;
    public ModelPart leftEye;
    public ModelPart rightEye;
    public ModelPart beard;
    public ModelPart ring;
    public ModelPart leftHorn2;
    public ModelPart leftHorn3;
    public ModelPart leftSideHorn2;
    public ModelPart leftSideHorn3;
    public ModelPart rightHorn2;
    public ModelPart rightHorn3;
    public ModelPart rightSideHorn2;
    public ModelPart rightSideHorn3;
    public ModelPart leftPupil;
    public ModelPart rightPupil;
    public ModelPart bell2;
    public ModelPart bell3;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart leftLeg4;
    public ModelPart tentacleTop2;
    public ModelPart tentacleTop3;
    public ModelPart tentacleMiddle2;
    public ModelPart tentacleMiddle3;
    public ModelPart tentacleBottom2;
    public ModelPart tentacleBottom3;
    public ModelPart leftArm2;
    public ModelPart leftArm4;
    public ModelPart leftArm3;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;
    public ModelPart rightLeg4;
    public ModelPart rightArm2;
    public ModelPart rightArm4;
    public ModelPart rightArm3;

    public ShubNiggurathFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.neck = this.body.getChild("neck");
        this.leftLeg1 = this.body.getChild("leftLeg1");
        this.tentacleTop1 = this.body.getChild("tentacleTop1");
        this.tentacleMiddle1 = this.body.getChild("tentacleMiddle1");
        this.tentacleBottom1 = this.body.getChild("tentacleBottom1");
        this.leftArm1 = this.body.getChild("leftArm1");
        this.rightLeg1 = this.body.getChild("rightLeg1");
        this.rightArm1 = this.body.getChild("rightArm1");
        this.tail = this.body.getChild("tail");
        this.head = this.neck.getChild("head");
        this.bell1 = this.neck.getChild("bell1");
        this.mouth = this.head.getChild("mouth");
        this.leftHorn1 = this.head.getChild("leftHorn1");
        this.leftSideHorn1 = this.head.getChild("leftSideHorn1");
        this.rightHorn1 = this.head.getChild("rightHorn1");
        this.rightSideHorn1 = this.head.getChild("rightSideHorn1");
        this.leftEye = this.head.getChild("leftEye");
        this.rightEye = this.head.getChild("rightEye");
        this.beard = this.mouth.getChild("beard");
        this.ring = this.mouth.getChild("ring");
        this.leftHorn2 = this.leftHorn1.getChild("leftHorn2");
        this.leftHorn3 = this.leftHorn2.getChild("leftHorn3");
        this.leftSideHorn2 = this.leftSideHorn1.getChild("leftSideHorn2");
        this.leftSideHorn3 = this.leftSideHorn2.getChild("leftSideHorn3");
        this.rightHorn2 = this.rightHorn1.getChild("rightHorn2");
        this.rightHorn3 = this.rightHorn2.getChild("rightHorn3");
        this.rightSideHorn2 = this.rightSideHorn1.getChild("rightSideHorn2");
        this.rightSideHorn3 = this.rightSideHorn2.getChild("rightSideHorn3");
        this.leftPupil = this.leftEye.getChild("leftPupil");
        this.rightPupil = this.rightEye.getChild("rightPupil");
        this.bell2 = this.bell1.getChild("bell2");
        this.bell3 = this.bell2.getChild("bell3");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.leftLeg3 = this.leftLeg2.getChild("leftLeg3");
        this.leftLeg4 = this.leftLeg2.getChild("leftLeg4");
        this.tentacleTop2 = this.tentacleTop1.getChild("tentacleTop2");
        this.tentacleTop3 = this.tentacleTop2.getChild("tentacleTop3");
        this.tentacleMiddle2 = this.tentacleMiddle1.getChild("tentacleMiddle2");
        this.tentacleMiddle3 = this.tentacleMiddle2.getChild("tentacleMiddle3");
        this.tentacleBottom2 = this.tentacleBottom1.getChild("tentacleBottom2");
        this.tentacleBottom3 = this.tentacleBottom2.getChild("tentacleBottom3");
        this.leftArm2 = this.leftArm1.getChild("leftArm2");
        this.leftArm4 = this.leftArm2.getChild("leftArm4");
        this.leftArm3 = this.leftArm2.getChild("leftArm3");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.rightLeg3 = this.rightLeg2.getChild("rightLeg3");
        this.rightLeg4 = this.rightLeg2.getChild("rightLeg4");
        this.rightArm2 = this.rightArm1.getChild("rightArm2");
        this.rightArm4 = this.rightArm2.getChild("rightArm4");
        this.rightArm3 = this.rightArm2.getChild("rightArm3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.5F, -1.5F, 5.0F, 8.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 18.6F, 0.0F, 0.5864306020384839F, 0.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.2F, 0.0F, -0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(32, 10).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, 4.4F, 0.2F, -2.5258405147914824F, 0.0F, 0.0F));
        PartDefinition tentacleTop1 = body.addOrReplaceChild("tentacleTop1", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-1.5F, -1.5F, 1.3F, 0.23457224414434488F, 0.23457224414434488F, 0.0F));
        PartDefinition tentacleMiddle1 = body.addOrReplaceChild("tentacleMiddle1", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(1.2F, 0.3F, 1.3F, 0, 0, 0));
        PartDefinition tentacleBottom1 = body.addOrReplaceChild("tentacleBottom1", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.9F, 2.5F, 1.3F, 0, 0, 0));
        PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(32, 10).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -2.1F, -0.1F, -1.2117821049859852F, 0.0F, 0.0F));
        PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(32, 10).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, 4.4F, 0.2F, -2.5258405147914824F, 0.0F, 0.0F));
        PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(32, 10).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(-2.5F, -2.1F, -0.1F, -1.2117821049859852F, 0.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 3.3F, 0.5F, 0.4845034043483675F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.3F, -0.3F, 0, 0, 0));
        PartDefinition bell1 = neck.addOrReplaceChild("bell1", CubeListBuilder.create().texOffs(8, 14).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.2F, 1.4F, -1.291194639214949F, 0.0F, 0.0F));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.5F, -1.0F, 0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(56, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.8F, -2.8F, 0.9F, 0.9773843811168246F, 0.17453292519943295F, 0.0F));
        PartDefinition leftSideHorn1 = head.addOrReplaceChild("leftSideHorn1", CubeListBuilder.create().texOffs(56, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.0F, -1.1F, 1.2F, 0.6981317007977318F, 0.0F, 0.8991936386169619F));
        PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(56, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-0.8F, -2.8F, 0.9F, 0.9773843811168246F, -0.17453292519943295F, 0.0F));
        PartDefinition rightSideHorn1 = head.addOrReplaceChild("rightSideHorn1", CubeListBuilder.create().texOffs(56, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-1.0F, -1.1F, 1.2F, 0.6981317007977318F, 0.0F, -0.8991936386169619F));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(16, 8).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.3F, -1.7F, -0.2F, 0, 0, 0));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(16, 8).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-1.3F, -1.7F, -0.2F, 0, 0, 0));
        PartDefinition beard = mouth.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(46, 0).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0, 0, 0));
        PartDefinition ring = mouth.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(0, 29).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 0.0F, 0.9F, -0.25272366769929566F, 0.0F, 0.0F));
        PartDefinition leftHorn3 = leftHorn2.addOrReplaceChild("leftHorn3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.0F, -0.33091441019915835F, 0.0F, 0.0F));
        PartDefinition leftSideHorn2 = leftSideHorn1.addOrReplaceChild("leftSideHorn2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, -0.2F, 0.9F, -0.44820055723846597F, 0.0F, 0.0F));
        PartDefinition leftSideHorn3 = leftSideHorn2.addOrReplaceChild("leftSideHorn3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.0F, 0.45099308137849586F, 0.0F, 0.0F));
        PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 0.0F, 0.9F, -0.25272366769929566F, 0.0F, 0.0F));
        PartDefinition rightHorn3 = rightHorn2.addOrReplaceChild("rightHorn3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.0F, -0.33091441019915835F, 0.0F, 0.0F));
        PartDefinition rightSideHorn2 = rightSideHorn1.addOrReplaceChild("rightSideHorn2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, -0.2F, 0.9F, -0.44820055723846597F, 0.0F, 0.0F));
        PartDefinition rightSideHorn3 = rightSideHorn2.addOrReplaceChild("rightSideHorn3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.0F, 0.45099308137849586F, 0.0F, 0.0F));
        PartDefinition leftPupil = leftEye.addOrReplaceChild("leftPupil", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition rightPupil = rightEye.addOrReplaceChild("rightPupil", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-0.2F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition bell2 = bell1.addOrReplaceChild("bell2", CubeListBuilder.create().texOffs(8, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 4.0F, -0.1F, 0.8210028961170991F, 0.0F, 0.0F));
        PartDefinition bell3 = bell2.addOrReplaceChild("bell3", CubeListBuilder.create().texOffs(8, 24).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(28, 9).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(-0.01F, 3.0F, -0.6F, 1.993864100194067F, 0.0F, 0.0F));
        PartDefinition leftLeg3 = leftLeg2.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.2F, 2.5F, 0.0F, -0.0781907508222411F, 0.27366763203903305F, 0.0F));
        PartDefinition leftLeg4 = leftLeg2.addOrReplaceChild("leftLeg4", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.2F, 2.5F, 0.0F, -0.0781907508222411F, -0.27366763203903305F, 0.0F));
        PartDefinition tentacleTop2 = tentacleTop1.addOrReplaceChild("tentacleTop2", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0.35185837453889574F, 0.3127630032889644F, 0.0F));
        PartDefinition tentacleTop3 = tentacleTop2.addOrReplaceChild("tentacleTop3", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0.27366763203903305F, 0.23457224414434488F, 0.0F));
        PartDefinition tentacleMiddle2 = tentacleMiddle1.addOrReplaceChild("tentacleMiddle2", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition tentacleMiddle3 = tentacleMiddle2.addOrReplaceChild("tentacleMiddle3", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition tentacleBottom2 = tentacleBottom1.addOrReplaceChild("tentacleBottom2", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition tentacleBottom3 = tentacleBottom2.addOrReplaceChild("tentacleBottom3", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(59, 6).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-0.01F, 3.6F, 0.5F, -0.0781907508222411F, 0.0F, 0.0F));
        PartDefinition leftArm4 = leftArm2.addOrReplaceChild("leftArm4", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.2F, 1.3F, -0.6F, 1.1728612040769677F, -0.23457224414434488F, -0.3490658503988659F));
        PartDefinition leftArm3 = leftArm2.addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.2F, 1.3F, -0.6F, 1.1728612040769677F, 0.23457224414434488F, 0.3490658503988659F));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(28, 9).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, true), PartPose.offsetAndRotation(0.01F, 3.0F, -0.6F, 1.993864100194067F, 0.0F, 0.0F));
        PartDefinition rightLeg3 = rightLeg2.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.2F, 2.5F, 0.0F, -0.0781907508222411F, 0.27366763203903305F, 0.0F));
        PartDefinition rightLeg4 = rightLeg2.addOrReplaceChild("rightLeg4", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.2F, 2.5F, 0.0F, -0.0781907508222411F, -0.27366763203903305F, 0.0F));
        PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(59, 6).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, 3.6F, 0.5F, -0.0781907508222411F, 0.0F, 0.0F));
        PartDefinition rightArm4 = rightArm2.addOrReplaceChild("rightArm4", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.2F, 1.3F, -0.6F, 1.1728612040769677F, -0.23457224414434488F, -0.3490658503988659F));
        PartDefinition rightArm3 = rightArm2.addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.2F, 1.3F, -0.6F, 1.1728612040769677F, 0.23457224414434488F, 0.3490658503988659F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    public static void rotateTentacles(List<ModelPart> tentacles, float ageInTicks, float offset) {
        float rot = Mth.cos(ageInTicks * 0.1f + offset) * (float) Math.toRadians(12);
        for (ModelPart tentacle : tentacles) {
            tentacle.xRot = rot;
            tentacle.yRot = rot;
        }
        tentacles.get(0).zRot = ageInTicks * 0.01f + offset;
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(ShubNiggurathFamiliarEntity pEntity, float limbSwing, float limbSwingAmount,
                          float pAgeInTicks, float netHeadYaw, float headPitch) {
        float partialTicks = Minecraft.getInstance().getFrameTime();
        this.showModels(pEntity);
        CthulhuFamiliarEntity friend = pEntity.getCthulhuFriend();

        this.body.xRot = 0.59f;
        this.body.yRot = 0;
        this.body.zRot = 0;
        this.leftLeg2.xRot = 1.99f;
        this.rightLeg2.xRot = 1.99f;

        if (friend != null) {
            limbSwing = friend.riderLimbSwing;
            limbSwingAmount = friend.riderLimbSwingAmount;
        }

        this.head.yRot = this.toRads(netHeadYaw) * 0.7f;
        this.head.xRot = this.toRads(headPitch) * 0.7f;

        rotateTentacles(ImmutableList.of(this.tentacleBottom1, this.tentacleBottom2, this.tentacleBottom3), pAgeInTicks, 0);
        rotateTentacles(ImmutableList.of(this.tentacleMiddle1, this.tentacleMiddle2, this.tentacleMiddle3), pAgeInTicks, 0.5f);
        rotateTentacles(ImmutableList.of(this.tentacleTop1, this.tentacleTop2, this.tentacleTop3), pAgeInTicks, 1);

        this.rightArm1.xRot = Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.5f - 1.21f;
        this.leftArm1.xRot = Mth.cos(limbSwing * 0.5f) * limbSwingAmount * 0.5f - 1.21f;
        this.leftLeg1.xRot = Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.5f - 2.53f;
        this.rightLeg1.xRot = Mth.cos(limbSwing * 0.5f) * limbSwingAmount * 0.5f - 2.53f;
        this.body.y = 18.6f - Math.abs(Mth.cos(limbSwing * 0.5f + PI)) * limbSwingAmount * 1.5f;

        if (friend != null) {
            this.leftArm1.xRot = this.toRads(
                    -143 - 5 * friend.getAnimationHeight(partialTicks) - 10 * (this.body.y - 18.6f));
        }

        if (pEntity.isPartying()) {
            this.body.xRot = this.toRads(-90) + Mth.cos(pAgeInTicks * 0.3f) * this.toRads(5);
            this.body.yRot = Mth.cos(pAgeInTicks * 0.3f + PI * 1.5f) * this.toRads(5);
            this.body.zRot = Mth.cos(pAgeInTicks * 0.3f + PI) * this.toRads(5);
            this.leftArm1.xRot = Mth.cos(pAgeInTicks * 0.3f) * this.toRads(30) - this.toRads(90);
            this.rightArm1.xRot = Mth.cos(pAgeInTicks * 0.3f + PI) * this.toRads(30) - this.toRads(90);
            this.leftLeg1.xRot = Mth.cos(pAgeInTicks * 0.3f + PI) * this.toRads(30) - this.toRads(90);
            this.rightLeg1.xRot = Mth.cos(pAgeInTicks * 0.3f + PI) * this.toRads(30) - this.toRads(90);
        } else if (pEntity.isSitting()) {
            this.body.xRot = this.toRads(100);
            this.leftLeg1.xRot = -this.toRads(60);
            this.rightLeg1.xRot = -this.toRads(60);
            this.leftLeg2.xRot = this.toRads(75);
            this.rightLeg2.xRot = this.toRads(75);
            this.rightArm1.xRot = -this.toRads(170);
            this.leftArm1.xRot = -this.toRads(170);
            this.head.yRot = this.toRads(115);
        }
    }

    private void showModels(ShubNiggurathFamiliarEntity entityIn) {
        this.ring.visible = entityIn.hasRing();
        this.beard.visible = entityIn.hasBeard();
        this.bell1.visible = entityIn.hasBlacksmithUpgrade();
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }
}
