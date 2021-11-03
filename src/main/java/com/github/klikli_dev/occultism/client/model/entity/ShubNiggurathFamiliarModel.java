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

import com.github.klikli_dev.occultism.common.entity.ShubNiggurathFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class ShubNiggurathFamiliarModel extends EntityModel<ShubNiggurathFamiliarEntity> {
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer leftLeg1;
    public ModelRenderer tentacleTop1;
    public ModelRenderer tentacleMiddle1;
    public ModelRenderer tentacleBottom1;
    public ModelRenderer leftArm1;
    public ModelRenderer rightLeg1;
    public ModelRenderer rightArm1;
    public ModelRenderer tail;
    public ModelRenderer head;
    public ModelRenderer bell1;
    public ModelRenderer mouth;
    public ModelRenderer leftHorn1;
    public ModelRenderer leftSideHorn1;
    public ModelRenderer rightHorn1;
    public ModelRenderer rightSideHorn1;
    public ModelRenderer leftEye;
    public ModelRenderer rightEye;
    public ModelRenderer beard;
    public ModelRenderer ring;
    public ModelRenderer leftHorn2;
    public ModelRenderer leftHorn3;
    public ModelRenderer leftSideHorn2;
    public ModelRenderer leftSideHorn3;
    public ModelRenderer rightHorn2;
    public ModelRenderer rightHorn3;
    public ModelRenderer rightSideHorn2;
    public ModelRenderer rightSideHorn3;
    public ModelRenderer leftPupil;
    public ModelRenderer rightPupil;
    public ModelRenderer bell2;
    public ModelRenderer bell3;
    public ModelRenderer leftLeg2;
    public ModelRenderer leftLeg3;
    public ModelRenderer leftLeg4;
    public ModelRenderer tentacleTop2;
    public ModelRenderer tentacleTop3;
    public ModelRenderer tentacleMiddle2;
    public ModelRenderer tentacleMiddle3;
    public ModelRenderer tentacleBottom2;
    public ModelRenderer tentacleBottom3;
    public ModelRenderer leftArm2;
    public ModelRenderer leftArm4;
    public ModelRenderer leftArm3;
    public ModelRenderer rightLeg2;
    public ModelRenderer rightLeg3;
    public ModelRenderer rightLeg4;
    public ModelRenderer rightArm2;
    public ModelRenderer rightArm4;
    public ModelRenderer rightArm3;

    public ShubNiggurathFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.rightArm3 = new ModelRenderer(this, 0, 11);
        this.rightArm3.mirror = true;
        this.rightArm3.setPos(-0.2F, 1.3F, -0.6F);
        this.rightArm3.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm3, 1.1728612040769677F, 0.23457224414434488F, 0.3490658503988659F);
        this.tentacleBottom1 = new ModelRenderer(this, 6, 11);
        this.tentacleBottom1.setPos(-0.8F, 2.4F, 1.3F);
        this.tentacleBottom1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn2 = new ModelRenderer(this, 33, 0);
        this.leftHorn2.setPos(-0.01F, 0.0F, 0.9F);
        this.leftHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn2, -0.25272366769929566F, 0.0F, 0.0F);
        this.leftLeg4 = new ModelRenderer(this, 0, 11);
        this.leftLeg4.setPos(0.2F, 2.5F, 0.0F);
        this.leftLeg4.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg4, -0.0781907508222411F, -0.27366763203903305F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 28, 9);
        this.leftLeg2.setPos(-0.01F, 3.0F, -0.6F);
        this.leftLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, 1.993864100194067F, 0.0F, 0.0F);
        this.tentacleMiddle2 = new ModelRenderer(this, 6, 11);
        this.tentacleMiddle2.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleMiddle2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightSideHorn2 = new ModelRenderer(this, 33, 0);
        this.rightSideHorn2.mirror = true;
        this.rightSideHorn2.setPos(-0.01F, -0.2F, 0.9F);
        this.rightSideHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightSideHorn2, -0.44820055723846597F, 0.0F, 0.0F);
        this.tentacleTop3 = new ModelRenderer(this, 6, 11);
        this.tentacleTop3.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleTop3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleTop3, 0.27366763203903305F, 0.23457224414434488F, 0.0F);
        this.bell2 = new ModelRenderer(this, 8, 20);
        this.bell2.setPos(0.0F, 4.0F, -0.1F);
        this.bell2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bell2, 0.8210028961170991F, 0.0F, 0.0F);
        this.ring = new ModelRenderer(this, 0, 29);
        this.ring.setPos(0.0F, -1.0F, -2.0F);
        this.ring.addBox(-2.0F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ring, 0.5759586531581287F, 0.0F, 0.0F);
        this.rightLeg2 = new ModelRenderer(this, 28, 9);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setPos(0.01F, 3.0F, -0.6F);
        this.rightLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, 1.993864100194067F, 0.0F, 0.0F);
        this.leftSideHorn2 = new ModelRenderer(this, 33, 0);
        this.leftSideHorn2.setPos(-0.01F, -0.2F, 0.9F);
        this.leftSideHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftSideHorn2, -0.44820055723846597F, 0.0F, 0.0F);
        this.rightHorn3 = new ModelRenderer(this, 33, 0);
        this.rightHorn3.mirror = true;
        this.rightHorn3.setPos(-0.01F, 0.0F, 1.0F);
        this.rightHorn3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn3, -0.33091441019915835F, 0.0F, 0.0F);
        this.rightLeg3 = new ModelRenderer(this, 0, 11);
        this.rightLeg3.mirror = true;
        this.rightLeg3.setPos(-0.2F, 2.5F, 0.0F);
        this.rightLeg3.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg3, -0.0781907508222411F, 0.27366763203903305F, 0.0F);
        this.rightPupil = new ModelRenderer(this, 13, 0);
        this.rightPupil.mirror = true;
        this.rightPupil.setPos(-0.2F, 0.0F, 0.0F);
        this.rightPupil.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightPupil, 0.7853981633974483F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 14);
        this.tail.setPos(0.0F, 3.3F, 0.5F);
        this.tail.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.4845034043483675F, 0.0F, 0.0F);
        this.tentacleBottom3 = new ModelRenderer(this, 6, 11);
        this.tentacleBottom3.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleBottom3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightSideHorn1 = new ModelRenderer(this, 56, 0);
        this.rightSideHorn1.mirror = true;
        this.rightSideHorn1.setPos(-1.0F, 0.4F, -0.3F);
        this.rightSideHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightSideHorn1, 0.6981317007977318F, 0.0F, -0.8991936386169619F);
        this.leftArm1 = new ModelRenderer(this, 32, 10);
        this.leftArm1.setPos(2.5F, -2.1F, -0.1F);
        this.leftArm1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm1, -1.2117821049859852F, 0.0F, 0.0F);
        this.rightLeg1 = new ModelRenderer(this, 32, 10);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setPos(-2.5F, 4.4F, 0.2F);
        this.rightLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, -2.5258405147914824F, 0.0F, 0.0F);
        this.rightArm1 = new ModelRenderer(this, 32, 10);
        this.rightArm1.setPos(-2.5F, -2.1F, -0.1F);
        this.rightArm1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm1, -1.2117821049859852F, 0.0F, 0.0F);
        this.rightLeg4 = new ModelRenderer(this, 0, 11);
        this.rightLeg4.setPos(0.2F, 2.5F, 0.0F);
        this.rightLeg4.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg4, -0.0781907508222411F, -0.27366763203903305F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 18.6F, 0.0F);
        this.body.addBox(-2.5F, -3.5F, -1.5F, 5.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 0.5864306020384839F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 24, 0);
        this.head.setPos(0.0F, -2.8F, 1.1F);
        this.head.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.0781907508222411F, 0.0F, 0.0F);
        this.beard = new ModelRenderer(this, 46, 0);
        this.beard.setPos(0.0F, 3.0F, 0.0F);
        this.beard.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tentacleBottom2 = new ModelRenderer(this, 6, 11);
        this.tentacleBottom2.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleBottom2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm3 = new ModelRenderer(this, 0, 11);
        this.leftArm3.mirror = true;
        this.leftArm3.setPos(-0.2F, 1.3F, -0.6F);
        this.leftArm3.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm3, 1.1728612040769677F, 0.23457224414434488F, 0.3490658503988659F);
        this.bell1 = new ModelRenderer(this, 8, 14);
        this.bell1.setPos(0.01F, -1.2F, 1.2F);
        this.bell1.addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bell1, -1.291194639214949F, 0.0F, 0.0F);
        this.rightHorn1 = new ModelRenderer(this, 56, 0);
        this.rightHorn1.mirror = true;
        this.rightHorn1.setPos(-0.8F, -1.3F, -0.6F);
        this.rightHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn1, 0.9773843811168246F, -0.17453292519943295F, 0.0F);
        this.leftArm4 = new ModelRenderer(this, 0, 11);
        this.leftArm4.setPos(0.2F, 1.3F, -0.6F);
        this.leftArm4.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm4, 1.1728612040769677F, -0.23457224414434488F, -0.3490658503988659F);
        this.tentacleTop1 = new ModelRenderer(this, 6, 11);
        this.tentacleTop1.setPos(-1.5F, -1.5F, 1.3F);
        this.tentacleTop1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleTop1, 0.23457224414434488F, 0.23457224414434488F, 0.0F);
        this.rightArm2 = new ModelRenderer(this, 59, 6);
        this.rightArm2.setPos(0.01F, 3.6F, 0.5F);
        this.rightArm2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm2, -0.0781907508222411F, 0.0F, 0.0F);
        this.tentacleMiddle3 = new ModelRenderer(this, 6, 11);
        this.tentacleMiddle3.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleMiddle3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightEye = new ModelRenderer(this, 16, 8);
        this.rightEye.mirror = true;
        this.rightEye.setPos(-1.3F, -0.2F, -1.7F);
        this.rightEye.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn3 = new ModelRenderer(this, 33, 0);
        this.leftHorn3.setPos(-0.01F, 0.0F, 1.0F);
        this.leftHorn3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn3, -0.33091441019915835F, 0.0F, 0.0F);
        this.bell3 = new ModelRenderer(this, 8, 24);
        this.bell3.setPos(0.0F, 2.0F, 0.0F);
        this.bell3.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg3 = new ModelRenderer(this, 0, 11);
        this.leftLeg3.mirror = true;
        this.leftLeg3.setPos(-0.2F, 2.5F, 0.0F);
        this.leftLeg3.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg3, -0.0781907508222411F, 0.27366763203903305F, 0.0F);
        this.leftSideHorn3 = new ModelRenderer(this, 33, 0);
        this.leftSideHorn3.setPos(-0.01F, 0.0F, 1.0F);
        this.leftSideHorn3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftSideHorn3, 0.45099308137849586F, 0.0F, 0.0F);
        this.rightArm4 = new ModelRenderer(this, 0, 11);
        this.rightArm4.setPos(0.2F, 1.3F, -0.6F);
        this.rightArm4.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm4, 1.1728612040769677F, -0.23457224414434488F, -0.3490658503988659F);
        this.leftPupil = new ModelRenderer(this, 13, 0);
        this.leftPupil.setPos(0.2F, 0.0F, 0.0F);
        this.leftPupil.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftPupil, 0.7853981633974483F, 0.0F, 0.0F);
        this.rightSideHorn3 = new ModelRenderer(this, 33, 0);
        this.rightSideHorn3.mirror = true;
        this.rightSideHorn3.setPos(-0.01F, 0.0F, 1.0F);
        this.rightSideHorn3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightSideHorn3, 0.45099308137849586F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 36, 0);
        this.mouth.setPos(0.0F, 0.0F, -2.5F);
        this.mouth.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.19198621771937624F, 0.0F, 0.0F);
        this.leftArm2 = new ModelRenderer(this, 59, 6);
        this.leftArm2.setPos(-0.01F, 3.6F, 0.5F);
        this.leftArm2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm2, -0.0781907508222411F, 0.0F, 0.0F);
        this.tentacleMiddle1 = new ModelRenderer(this, 6, 11);
        this.tentacleMiddle1.setPos(0.9F, 0.5F, 1.3F);
        this.tentacleMiddle1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn2 = new ModelRenderer(this, 33, 0);
        this.rightHorn2.mirror = true;
        this.rightHorn2.setPos(-0.01F, 0.0F, 0.9F);
        this.rightHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn2, -0.25272366769929566F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 16, 0);
        this.neck.setPos(0.0F, -3.2F, 0.0F);
        this.neck.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.11728612207217244F, 0.0F, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 32, 10);
        this.leftLeg1.setPos(2.5F, 4.4F, 0.2F);
        this.leftLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, -2.5258405147914824F, 0.0F, 0.0F);
        this.leftEye = new ModelRenderer(this, 16, 8);
        this.leftEye.setPos(1.3F, -0.2F, -1.7F);
        this.leftEye.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftSideHorn1 = new ModelRenderer(this, 56, 0);
        this.leftSideHorn1.setPos(1.0F, 0.4F, -0.3F);
        this.leftSideHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftSideHorn1, 0.6981317007977318F, 0.0F, 0.8991936386169619F);
        this.leftHorn1 = new ModelRenderer(this, 56, 0);
        this.leftHorn1.setPos(0.8F, -1.3F, -0.6F);
        this.leftHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn1, 0.9773843811168246F, 0.17453292519943295F, 0.0F);
        this.tentacleTop2 = new ModelRenderer(this, 6, 11);
        this.tentacleTop2.setPos(0.0F, 0.0F, 1.9F);
        this.tentacleTop2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tentacleTop2, 0.35185837453889574F, 0.3127630032889644F, 0.0F);
        this.rightArm2.addChild(this.rightArm3);
        this.body.addChild(this.tentacleBottom1);
        this.leftHorn1.addChild(this.leftHorn2);
        this.leftLeg2.addChild(this.leftLeg4);
        this.leftLeg1.addChild(this.leftLeg2);
        this.tentacleMiddle1.addChild(this.tentacleMiddle2);
        this.rightSideHorn1.addChild(this.rightSideHorn2);
        this.tentacleTop2.addChild(this.tentacleTop3);
        this.bell1.addChild(this.bell2);
        this.mouth.addChild(this.ring);
        this.rightLeg1.addChild(this.rightLeg2);
        this.leftSideHorn1.addChild(this.leftSideHorn2);
        this.rightHorn2.addChild(this.rightHorn3);
        this.rightLeg2.addChild(this.rightLeg3);
        this.rightEye.addChild(this.rightPupil);
        this.body.addChild(this.tail);
        this.tentacleBottom2.addChild(this.tentacleBottom3);
        this.head.addChild(this.rightSideHorn1);
        this.body.addChild(this.leftArm1);
        this.body.addChild(this.rightLeg1);
        this.body.addChild(this.rightArm1);
        this.rightLeg2.addChild(this.rightLeg4);
        this.neck.addChild(this.head);
        this.mouth.addChild(this.beard);
        this.tentacleBottom1.addChild(this.tentacleBottom2);
        this.leftArm2.addChild(this.leftArm3);
        this.neck.addChild(this.bell1);
        this.head.addChild(this.rightHorn1);
        this.leftArm2.addChild(this.leftArm4);
        this.body.addChild(this.tentacleTop1);
        this.rightArm1.addChild(this.rightArm2);
        this.tentacleMiddle2.addChild(this.tentacleMiddle3);
        this.head.addChild(this.rightEye);
        this.leftHorn2.addChild(this.leftHorn3);
        this.bell2.addChild(this.bell3);
        this.leftLeg2.addChild(this.leftLeg3);
        this.leftSideHorn2.addChild(this.leftSideHorn3);
        this.rightArm2.addChild(this.rightArm4);
        this.leftEye.addChild(this.leftPupil);
        this.rightSideHorn2.addChild(this.rightSideHorn3);
        this.head.addChild(this.mouth);
        this.leftArm1.addChild(this.leftArm2);
        this.body.addChild(this.tentacleMiddle1);
        this.rightHorn1.addChild(this.rightHorn2);
        this.body.addChild(this.neck);
        this.body.addChild(this.leftLeg1);
        this.head.addChild(this.leftEye);
        this.head.addChild(this.leftSideHorn1);
        this.head.addChild(this.leftHorn1);
        this.tentacleTop1.addChild(this.tentacleTop2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(ShubNiggurathFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
            float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.showModels(pEntity);
    }

    private void showModels(ShubNiggurathFamiliarEntity entityIn) {
        this.ring.visible = entityIn.hasRing();
        this.beard.visible = entityIn.hasBeard();
        this.bell1.visible = entityIn.hasBlacksmithUpgrade();
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
