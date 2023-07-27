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

import com.klikli_dev.occultism.common.entity.familiar.ChimeraFamiliarEntity;
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
public class ChimeraFamiliarModel extends EntityModel<ChimeraFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart head;
    public ModelPart leftBackLeg1;
    public ModelPart snake1;
    public ModelPart leftLeg1;
    public ModelPart goatNeck;
    public ModelPart rightBackLeg1;
    public ModelPart rightLeg1;
    public ModelPart mouth;
    public ModelPart leftEye;
    public ModelPart leftEar;
    public ModelPart mane1;
    public ModelPart mane2;
    public ModelPart mane3;
    public ModelPart mane4;
    public ModelPart mane7;
    public ModelPart rightEar;
    public ModelPart rightEye;
    public ModelPart mane5;
    public ModelPart mane6;
    public ModelPart mane8;
    public ModelPart jaw;
    public ModelPart nose;
    public ModelPart leftBackLeg2;
    public ModelPart leftBackLeg3;
    public ModelPart snake2;
    public ModelPart snake3;
    public ModelPart snake4;
    public ModelPart snakeFlap1;
    public ModelPart snakeFlap2;
    public ModelPart snake5;
    public ModelPart snakeTooth;
    public ModelPart snakeHat1;
    public ModelPart snakeHat2;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart goatHead;
    public ModelPart goatLeftHorn1;
    public ModelPart goatMouth;
    public ModelPart goatRightHorn1;
    public ModelPart goatLeftHorn2;
    public ModelPart goatBeard;
    public ModelPart goatRing;
    public ModelPart goatRightHorn2;
    public ModelPart rightBackLeg2;
    public ModelPart rightBackLeg3;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;

    public ChimeraFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.head = this.body.getChild("head");
        this.leftBackLeg1 = this.body.getChild("leftBackLeg1");
        this.snake1 = this.body.getChild("snake1");
        this.leftLeg1 = this.body.getChild("leftLeg1");
        this.goatNeck = this.body.getChild("goatNeck");
        this.rightBackLeg1 = this.body.getChild("rightBackLeg1");
        this.rightLeg1 = this.body.getChild("rightLeg1");
        this.mouth = this.head.getChild("mouth");
        this.leftEye = this.head.getChild("leftEye");
        this.leftEar = this.head.getChild("leftEar");
        this.mane1 = this.head.getChild("mane1");
        this.mane2 = this.head.getChild("mane2");
        this.mane3 = this.head.getChild("mane3");
        this.mane4 = this.head.getChild("mane4");
        this.mane7 = this.head.getChild("mane7");
        this.rightEar = this.head.getChild("rightEar");
        this.rightEye = this.head.getChild("rightEye");
        this.mane5 = this.head.getChild("mane5");
        this.mane6 = this.head.getChild("mane6");
        this.mane8 = this.head.getChild("mane8");
        this.jaw = this.mouth.getChild("jaw");
        this.nose = this.mouth.getChild("nose");
        this.leftBackLeg2 = this.leftBackLeg1.getChild("leftBackLeg2");
        this.leftBackLeg3 = this.leftBackLeg2.getChild("leftBackLeg3");
        this.snake2 = this.snake1.getChild("snake2");
        this.snake3 = this.snake2.getChild("snake3");
        this.snake4 = this.snake3.getChild("snake4");
        this.snakeFlap1 = this.snake3.getChild("snakeFlap1");
        this.snakeFlap2 = this.snake3.getChild("snakeFlap2");
        this.snake5 = this.snake4.getChild("snake5");
        this.snakeTooth = this.snake4.getChild("snakeTooth");
        this.snakeHat1 = this.snake4.getChild("snakeHat1");
        this.snakeHat2 = this.snakeHat1.getChild("snakeHat2");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.leftLeg3 = this.leftLeg2.getChild("leftLeg3");
        this.goatHead = this.goatNeck.getChild("goatHead");
        this.goatLeftHorn1 = this.goatHead.getChild("goatLeftHorn1");
        this.goatMouth = this.goatHead.getChild("goatMouth");
        this.goatRightHorn1 = this.goatHead.getChild("goatRightHorn1");
        this.goatLeftHorn2 = this.goatLeftHorn1.getChild("goatLeftHorn2");
        this.goatBeard = this.goatMouth.getChild("goatBeard");
        this.goatRing = this.goatMouth.getChild("goatRing");
        this.goatRightHorn2 = this.goatRightHorn1.getChild("goatRightHorn2");
        this.rightBackLeg2 = this.rightBackLeg1.getChild("rightBackLeg2");
        this.rightBackLeg3 = this.rightBackLeg2.getChild("rightBackLeg3");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.rightLeg3 = this.rightLeg2.getChild("rightLeg3");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 17.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 0).addBox(-2.5F, -4.0F, -4.0F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 0, 0, 0));
        PartDefinition leftBackLeg1 = body.addOrReplaceChild("leftBackLeg1", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.3F, -0.1F, 3.9F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition snake1 = body.addOrReplaceChild("snake1", CubeListBuilder.create().texOffs(45, 4).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, -0.6646214111173737F, 0.0F, 0.0F));
        PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(46, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, -0.2F, -3.0F, 0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition goatNeck = body.addOrReplaceChild("goatNeck", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.0F, 0.03909537541112055F, -0.23457224414434488F, 0.8990190684075072F));
        PartDefinition rightBackLeg1 = body.addOrReplaceChild("rightBackLeg1", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.3F, -0.1F, 3.9F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(46, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.0F, -0.2F, -3.0F, 0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(35, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.2F, -3.3F, 0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.7F, -2.4F, -3.9F, 0.0F, 0.0F, -0.4363323129985824F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, -2.0F, -1.5F, 2.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(2.0F, -2.9F, -2.0F, -0.11728612207217244F, -0.11728612207217244F, -0.35185837453889574F));
        PartDefinition mane1 = head.addOrReplaceChild("mane1", CubeListBuilder.create().texOffs(3, 28).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.5F, -3.0F, 0.6108652381980153F, 0.0F, 0.0F));
        PartDefinition mane2 = head.addOrReplaceChild("mane2", CubeListBuilder.create().texOffs(3, 28).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(-2.0F, -1.0F, -2.9F, 0.6981317007977318F, 0.0F, 1.5707963267948966F));
        PartDefinition mane3 = head.addOrReplaceChild("mane3", CubeListBuilder.create().texOffs(3, 28).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, true), PartPose.offsetAndRotation(2.0F, -1.0F, -2.9F, 0.6108652381980153F, 0.0F, -1.5707963267948966F));
        PartDefinition mane4 = head.addOrReplaceChild("mane4", CubeListBuilder.create().texOffs(3, 28).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.6108652381980153F, 0.0F, 3.141592653589793F));
        PartDefinition mane7 = head.addOrReplaceChild("mane7", CubeListBuilder.create().texOffs(17, 27).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(2.0F, -1.0F, -1.0F, 0.6981317007977318F, 0.0F, -1.5707963267948966F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, -2.0F, -1.5F, 2.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-2.0F, -2.9F, -2.0F, -0.11728612207217244F, -0.11728612207217244F, -1.1644836982359053F));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-1.7F, -2.4F, -3.9F, 0.0F, 0.0F, 0.4363323129985824F));
        PartDefinition mane5 = head.addOrReplaceChild("mane5", CubeListBuilder.create().texOffs(17, 27).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.5F, -1.0F, 0.6981317007977318F, 0.0F, 0.0F));
        PartDefinition mane6 = head.addOrReplaceChild("mane6", CubeListBuilder.create().texOffs(17, 27).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(-2.0F, -1.0F, -1.0F, 0.6981317007977318F, 0.0F, 1.5707963267948966F));
        PartDefinition mane8 = head.addOrReplaceChild("mane8", CubeListBuilder.create().texOffs(17, 27).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, -1.0F, 0.6981317007977318F, 0.0F, 3.141592653589793F));
        PartDefinition jaw = mouth.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(47, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.3F, 0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition nose = mouth.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -0.4F, -2.4F, 0, 0, 0));
        PartDefinition leftBackLeg2 = leftBackLeg1.addOrReplaceChild("leftBackLeg2", CubeListBuilder.create().texOffs(57, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 3.3F, -0.1F, 0.8600982340775168F, 0.0F, 0.0F));
        PartDefinition leftBackLeg3 = leftBackLeg2.addOrReplaceChild("leftBackLeg3", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, 2.2F, 0.5F, -1.2119566751954398F, 0.0F, 0.0F));
        PartDefinition snake2 = snake1.addOrReplaceChild("snake2", CubeListBuilder.create().texOffs(45, 4).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.5F, 0.0F, 0.46914448828868976F, -0.46914448828868976F, 0.0F));
        PartDefinition snake3 = snake2.addOrReplaceChild("snake3", CubeListBuilder.create().texOffs(45, 4).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.5F, 0.0F, 0.46914448828868976F, -0.46914448828868976F, 0.0F));
        PartDefinition snake4 = snake3.addOrReplaceChild("snake4", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 0.3F, 0.5864306020384839F, 0.0F, 0.0F));
        PartDefinition snakeFlap1 = snake3.addOrReplaceChild("snakeFlap1", CubeListBuilder.create().texOffs(33, 28).addBox(0.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(1.0F, -2.0F, 0.0F, 0.0F, 0.17453292519943295F, 0.0F));
        PartDefinition snakeFlap2 = snake3.addOrReplaceChild("snakeFlap2", CubeListBuilder.create().texOffs(33, 28).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, true), PartPose.offsetAndRotation(-1.0F, -2.0F, 0.0F, 0.0F, -0.17453292519943295F, 0.0F));
        PartDefinition snake5 = snake4.addOrReplaceChild("snake5", CubeListBuilder.create().texOffs(30, 10).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, 0.0F, 0.0F, 0.5082398928281348F, 0.0F, 0.0F));
        PartDefinition snakeTooth = snake4.addOrReplaceChild("snakeTooth", CubeListBuilder.create().texOffs(0, 31).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, false), PartPose.offsetAndRotation(-0.4F, -2.8F, 0.0F, 0, 0, 0));
        PartDefinition snakeHat1 = snake4.addOrReplaceChild("snakeHat1", CubeListBuilder.create().texOffs(45, 28).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -1.5F, 1.01F, 0, 0, 0));
        PartDefinition snakeHat2 = snakeHat1.addOrReplaceChild("snakeHat2", CubeListBuilder.create().texOffs(53, 28).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(54, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.01F, 3.4F, 0.5F, -0.7428121536172364F, 0.0F, 0.0F));
        PartDefinition leftLeg3 = leftLeg2.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(27, 14).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.01F, 3.2F, 0.8F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition goatHead = goatNeck.addOrReplaceChild("goatHead", CubeListBuilder.create().texOffs(37, 14).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(-3.0F, -1.5F, 0.2F, 0.0F, -0.1972222088043106F, -0.9382889765773795F));
        PartDefinition goatLeftHorn1 = goatHead.addOrReplaceChild("goatLeftHorn1", CubeListBuilder.create().texOffs(34, 12).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-0.3F, -0.7F, -0.8F, -0.17453292519943295F, 1.5707963267948966F, 0.0F));
        PartDefinition goatMouth = goatHead.addOrReplaceChild("goatMouth", CubeListBuilder.create().texOffs(16, 16).addBox(-3.0F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.19547687289441354F));
        PartDefinition goatRightHorn1 = goatHead.addOrReplaceChild("goatRightHorn1", CubeListBuilder.create().texOffs(34, 12).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-0.3F, -0.7F, 0.8F, -0.17453292519943295F, 1.5707963267948966F, 0.0F));
        PartDefinition goatLeftHorn2 = goatLeftHorn1.addOrReplaceChild("goatLeftHorn2", CubeListBuilder.create().texOffs(12, 15).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.01F, 0.2F, 1.4F, -0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition goatBeard = goatMouth.addOrReplaceChild("goatBeard", CubeListBuilder.create().texOffs(26, 18).addBox(-3.0F, -1.5F, -1.0F, 3.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0, 0, 0));
        PartDefinition goatRing = goatMouth.addOrReplaceChild("goatRing", CubeListBuilder.create().texOffs(35, 28).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 0.0F, 4.0F, false), PartPose.offsetAndRotation(-2.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.5864306020384839F));
        PartDefinition goatRightHorn2 = goatRightHorn1.addOrReplaceChild("goatRightHorn2", CubeListBuilder.create().texOffs(12, 15).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(0.01F, 0.2F, 1.4F, -0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition rightBackLeg2 = rightBackLeg1.addOrReplaceChild("rightBackLeg2", CubeListBuilder.create().texOffs(57, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 3.3F, -0.1F, 0.8600982340775168F, 0.0F, 0.0F));
        PartDefinition rightBackLeg3 = rightBackLeg2.addOrReplaceChild("rightBackLeg3", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, true), PartPose.offsetAndRotation(0.01F, 2.2F, 0.5F, -1.2119566751954398F, 0.0F, 0.0F));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(54, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(0.01F, 3.4F, 0.5F, -0.7428121536172364F, 0.0F, 0.0F));
        PartDefinition rightLeg3 = rightLeg2.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(27, 14).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, true), PartPose.offsetAndRotation(0.01F, 3.2F, 0.8F, 0.3127630032889644F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(ChimeraFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
                          float netHeadYaw, float headPitch) {
        boolean isSnakeAttacking = pEntity.getAttackProgress(0) > 0
                && pEntity.getAttacker() == ChimeraFamiliarEntity.SNAKE_ATTACKER;

        this.showModels(pEntity);

        this.snake2.yRot = 0;
        this.snake3.yRot = 0;
        this.leftLeg3.xRot = 0.31f;
        this.rightLeg3.xRot = 0.31f;
        this.body.xRot = 0;
        if (!isSnakeAttacking) {
            this.snake1.xRot = -0.66f;
            this.snake2.xRot = 0.47f;
            this.snake3.xRot = 0.47f;
            this.snake4.xRot = 0.59f;
        }
        this.goatHead.zRot = -0.94f;
        this.leftLeg1.yRot = 0;
        this.rightLeg1.yRot = 0;

        this.head.yRot = this.toRads(netHeadYaw) * 0.7f;
        this.head.xRot = this.toRads(headPitch) * 0.7f;
        this.snake4.yRot = this.toRads(netHeadYaw) * 0.3f;
        this.snake4.zRot = -this.toRads(netHeadYaw) * 0.3f;

        this.snake1.zRot = Mth.cos(limbSwing * 0.3f) * 0.1f * limbSwingAmount + this.toRads(0);
        this.snake2.zRot = Mth.cos(limbSwing * 0.3f) * 0.1f * limbSwingAmount + this.toRads(0);
        this.snake3.zRot = Mth.cos(limbSwing * 0.3f) * 0.25f * limbSwingAmount + this.toRads(0);
        this.snake4.zRot = -Mth.cos(limbSwing * 0.3f) * 0.25f * limbSwingAmount;

        this.snake5.xRot = Mth.cos(pAgeInTicks * 0.1f) * this.toRads(15) + this.toRads(15);

        this.rightBackLeg1.xRot = Mth.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount - 0.23f;
        this.leftBackLeg1.xRot = Mth.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount - 0.23f;
        this.rightLeg1.xRot = Mth.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount + 0.43f;
        this.leftLeg1.xRot = Mth.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount + 0.43f;

        if (pEntity.isSitting()) {
            this.leftLeg1.xRot = -this.toRads(15);
            this.rightLeg1.xRot = -this.toRads(15);
            this.leftLeg3.xRot = this.toRads(30);
            this.rightLeg3.xRot = this.toRads(30);
            this.leftBackLeg1.xRot = -this.toRads(62);
            this.rightBackLeg1.xRot = -this.toRads(62);
            this.body.xRot = -this.toRads(20);
            this.leftLeg1.yRot = -this.toRads(10);
            this.rightLeg1.yRot = this.toRads(10);
            this.snake1.zRot = this.toRads(80);
            this.snake2.zRot = this.toRads(0);
            this.snake3.zRot = this.toRads(-20);
            this.snake4.zRot = this.toRads(-65);
            this.snake2.yRot = -this.toRads(20);
            this.snake3.yRot = -this.toRads(20);
            this.snake4.yRot = -this.toRads(20);
            this.snake2.xRot = this.toRads(40);
            this.snake3.xRot = this.toRads(50);
            this.snake4.xRot = this.toRads(60);
            this.snake5.xRot = this.toRads(7);
        }

        if (pEntity.isPartying()) {
            this.head.xRot = Mth.cos(pAgeInTicks * 0.4f) * this.toRads(30);
            this.goatHead.zRot = -Mth.cos(pAgeInTicks * 0.4f) * this.toRads(15) - 0.94f;
            this.goatHead.yRot = -0.20f;
            this.snake4.xRot = Mth.cos(pAgeInTicks * 0.4f) * this.toRads(30) + 0.59f;
        }
    }

    @Override
    public void prepareMobModel(ChimeraFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
                                float pPartialTick) {
        this.goatMouth.zRot = pEntity.getNoseGoatRot(pPartialTick) - 0.2f;

        float attackProgress = pEntity.getAttackProgress(pPartialTick);
        if (attackProgress > 0) {
            switch (pEntity.getAttacker()) {
                case ChimeraFamiliarEntity.GOAT_ATTACKER:
                    this.goatNeck.zRot = -Mth.sin(attackProgress * PI * 2) * this.toRads(30) + 0.9f;
                    break;
                case ChimeraFamiliarEntity.SNAKE_ATTACKER:
                    this.snake1.xRot = Mth.sin(attackProgress * PI) * this.toRads(25) - 0.66f;
                    this.snake2.xRot = Mth.sin(attackProgress * PI) * this.toRads(25) + 0.47f;
                    this.snake3.xRot = Mth.sin(attackProgress * PI) * this.toRads(25) + 0.47f;
                    this.snake4.xRot = -Mth.sin(attackProgress * PI) * this.toRads(30) + 0.59f;
                    break;
            }
        }
    }

    private void showModels(ChimeraFamiliarEntity entityIn) {
        boolean hasFlaps = entityIn.hasFlaps();

        this.snakeFlap1.visible = hasFlaps;
        this.snakeFlap2.visible = hasFlaps;
        this.goatRing.visible = entityIn.hasRing();
        this.snakeHat1.visible = entityIn.hasHat();
        this.goatNeck.visible = entityIn.hasGoat();
        this.goatBeard.visible = entityIn.hasBeard();
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

}
