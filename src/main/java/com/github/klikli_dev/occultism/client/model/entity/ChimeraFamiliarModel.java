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

import com.github.klikli_dev.occultism.common.entity.ChimeraFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class ChimeraFamiliarModel extends EntityModel<ChimeraFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer leftBackLeg1;
    public ModelRenderer snake1;
    public ModelRenderer leftLeg1;
    public ModelRenderer goatNeck;
    public ModelRenderer rightBackLeg1;
    public ModelRenderer rightLeg1;
    public ModelRenderer mouth;
    public ModelRenderer leftEye;
    public ModelRenderer leftEar;
    public ModelRenderer mane1;
    public ModelRenderer mane2;
    public ModelRenderer mane3;
    public ModelRenderer mane4;
    public ModelRenderer mane7;
    public ModelRenderer rightEar;
    public ModelRenderer rightEye;
    public ModelRenderer mane5;
    public ModelRenderer mane6;
    public ModelRenderer mane8;
    public ModelRenderer jaw;
    public ModelRenderer nose;
    public ModelRenderer leftBackLeg2;
    public ModelRenderer leftBackLeg3;
    public ModelRenderer snake2;
    public ModelRenderer snake3;
    public ModelRenderer snake4;
    public ModelRenderer snakeFlap1;
    public ModelRenderer snakeFlap2;
    public ModelRenderer snake5;
    public ModelRenderer snakeTooth;
    public ModelRenderer snakeHat1;
    public ModelRenderer snakeHat2;
    public ModelRenderer leftLeg2;
    public ModelRenderer leftLeg3;
    public ModelRenderer goatHead;
    public ModelRenderer goatLeftHorn1;
    public ModelRenderer goatMouth;
    public ModelRenderer goatRightHorn1;
    public ModelRenderer goatLeftHorn2;
    public ModelRenderer goatBeard;
    public ModelRenderer goatRing;
    public ModelRenderer goatRightHorn2;
    public ModelRenderer rightBackLeg2;
    public ModelRenderer rightBackLeg3;
    public ModelRenderer rightLeg2;
    public ModelRenderer rightLeg3;

    public ChimeraFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.rightBackLeg2 = new ModelRenderer(this, 57, 3);
        this.rightBackLeg2.mirror = true;
        this.rightBackLeg2.setPos(-0.01F, 3.3F, -0.1F);
        this.rightBackLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightBackLeg2, 0.8600982340775168F, 0.0F, 0.0F);
        this.mane1 = new ModelRenderer(this, 3, 28);
        this.mane1.setPos(0.0F, 0.5F, -3.0F);
        this.mane1.addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane1, 0.6108652381980153F, 0.0F, 0.0F);
        this.goatNeck = new ModelRenderer(this, 0, 15);
        this.goatNeck.setPos(-1.5F, -1.0F, 0.0F);
        this.goatNeck.addBox(-4.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatNeck, 0.03909537541112055F, -0.23457224414434488F, 0.8990190684075072F);
        this.rightLeg2 = new ModelRenderer(this, 54, 13);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setPos(0.01F, 3.4F, 0.5F);
        this.rightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, -0.7428121536172364F, 0.0F, 0.0F);
        this.rightLeg3 = new ModelRenderer(this, 27, 14);
        this.rightLeg3.mirror = true;
        this.rightLeg3.setPos(0.01F, 3.2F, 0.8F);
        this.rightLeg3.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg3, 0.3127630032889644F, 0.0F, 0.0F);
        this.leftLeg3 = new ModelRenderer(this, 27, 14);
        this.leftLeg3.setPos(0.01F, 3.2F, 0.8F);
        this.leftLeg3.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg3, 0.3127630032889644F, 0.0F, 0.0F);
        this.mane3 = new ModelRenderer(this, 3, 28);
        this.mane3.mirror = true;
        this.mane3.setPos(2.0F, -1.0F, -2.9F);
        this.mane3.addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane3, 0.6108652381980153F, 0.0F, -1.5707963267948966F);
        this.snakeHat1 = new ModelRenderer(this, 45, 28);
        this.snakeHat1.setPos(0.0F, -1.5F, 1.01F);
        this.snakeHat1.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.mane2 = new ModelRenderer(this, 3, 28);
        this.mane2.setPos(-2.0F, -1.0F, -2.9F);
        this.mane2.addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane2, 0.6981317007977318F, 0.0F, 1.5707963267948966F);
        this.snake2 = new ModelRenderer(this, 45, 4);
        this.snake2.setPos(0.0F, -3.5F, 0.0F);
        this.snake2.addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snake2, 0.46914448828868976F, -0.46914448828868976F, 0.0F);
        this.goatRightHorn1 = new ModelRenderer(this, 34, 12);
        this.goatRightHorn1.mirror = true;
        this.goatRightHorn1.setPos(-0.3F, -0.7F, 0.8F);
        this.goatRightHorn1.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatRightHorn1, -0.17453292519943295F, 1.5707963267948966F, 0.0F);
        this.leftEye = new ModelRenderer(this, 44, 0);
        this.leftEye.setPos(1.7F, -2.4F, -3.9F);
        this.leftEye.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEye, 0.0F, 0.0F, -0.4363323129985824F);
        this.snakeTooth = new ModelRenderer(this, 0, 31);
        this.snakeTooth.setPos(-0.4F, -2.8F, 0.0F);
        this.snakeTooth.addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.snake4 = new ModelRenderer(this, 0, 6);
        this.snake4.setPos(0.0F, -4.0F, 0.3F);
        this.snake4.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snake4, 0.5864306020384839F, 0.0F, 0.0F);
        this.leftBackLeg1 = new ModelRenderer(this, 4, 0);
        this.leftBackLeg1.setPos(2.3F, -0.1F, 3.9F);
        this.leftBackLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftBackLeg1, -0.23457224414434488F, 0.0F, 0.0F);
        this.mane7 = new ModelRenderer(this, 17, 27);
        this.mane7.setPos(2.0F, -1.0F, -1.0F);
        this.mane7.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane7, 0.6981317007977318F, 0.0F, -1.5707963267948966F);
        this.mane5 = new ModelRenderer(this, 17, 27);
        this.mane5.setPos(0.0F, 0.5F, -1.0F);
        this.mane5.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane5, 0.6981317007977318F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 47, 0);
        this.jaw.setPos(0.0F, 2.0F, 0.3F);
        this.jaw.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(jaw, 0.11728612207217244F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 35, 0);
        this.mouth.setPos(0.0F, -1.2F, -3.3F);
        this.mouth.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.4300491170387584F, 0.0F, 0.0F);
        this.rightBackLeg3 = new ModelRenderer(this, 20, 0);
        this.rightBackLeg3.mirror = true;
        this.rightBackLeg3.setPos(0.01F, 2.2F, 0.5F);
        this.rightBackLeg3.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightBackLeg3, -1.2119566751954398F, 0.0F, 0.0F);
        this.leftBackLeg2 = new ModelRenderer(this, 57, 3);
        this.leftBackLeg2.setPos(-0.01F, 3.3F, -0.1F);
        this.leftBackLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftBackLeg2, 0.8600982340775168F, 0.0F, 0.0F);
        this.mane8 = new ModelRenderer(this, 17, 27);
        this.mane8.setPos(0.0F, -3.0F, -1.0F);
        this.mane8.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane8, 0.6981317007977318F, 0.0F, 3.141592653589793F);
        this.snake3 = new ModelRenderer(this, 45, 4);
        this.snake3.setPos(0.0F, -3.5F, 0.0F);
        this.snake3.addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snake3, 0.46914448828868976F, -0.46914448828868976F, 0.0F);
        this.head = new ModelRenderer(this, 20, 0);
        this.head.setPos(0.0F, 0.0F, -6.0F);
        this.head.addBox(-2.5F, -4.0F, -4.0F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 46, 11);
        this.leftLeg1.setPos(2.0F, -0.2F, -3.0F);
        this.leftLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, 0.4300491170387584F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 0);
        this.nose.setPos(0.0F, -0.4F, -2.4F);
        this.nose.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 56, 0);
        this.rightEar.setPos(-2.0F, -2.9F, -2.0F);
        this.rightEar.addBox(0.0F, -2.0F, -1.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, -0.11728612207217244F, -0.11728612207217244F, -1.1644836982359053F);
        this.rightLeg1 = new ModelRenderer(this, 46, 11);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setPos(-2.0F, -0.2F, -3.0F);
        this.rightLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, 0.4300491170387584F, 0.0F, 0.0F);
        this.snake5 = new ModelRenderer(this, 30, 10);
        this.snake5.setPos(0.01F, 0.0F, 0.0F);
        this.snake5.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snake5, 0.5082398928281348F, 0.0F, 0.0F);
        this.goatLeftHorn1 = new ModelRenderer(this, 34, 12);
        this.goatLeftHorn1.setPos(-0.3F, -0.7F, -0.8F);
        this.goatLeftHorn1.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatLeftHorn1, -0.17453292519943295F, 1.5707963267948966F, 0.0F);
        this.goatLeftHorn2 = new ModelRenderer(this, 12, 15);
        this.goatLeftHorn2.setPos(0.01F, 0.2F, 1.4F);
        this.goatLeftHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatLeftHorn2, -0.2617993877991494F, 0.0F, 0.0F);
        this.snake1 = new ModelRenderer(this, 45, 4);
        this.snake1.setPos(0.0F, 0.0F, 4.5F);
        this.snake1.addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snake1, -0.6646214111173737F, 0.0F, 0.0F);
        this.goatMouth = new ModelRenderer(this, 16, 16);
        this.goatMouth.setPos(-2.5F, 0.0F, 0.0F);
        this.goatMouth.addBox(-3.0F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatMouth, 0.0F, 0.0F, -0.19547687289441354F);
        this.leftEar = new ModelRenderer(this, 56, 0);
        this.leftEar.setPos(2.0F, -2.9F, -2.0F);
        this.leftEar.addBox(0.0F, -2.0F, -1.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, -0.11728612207217244F, -0.11728612207217244F, -0.35185837453889574F);
        this.snakeFlap1 = new ModelRenderer(this, 33, 28);
        this.snakeFlap1.setPos(1.0F, -2.0F, 0.0F);
        this.snakeFlap1.addBox(0.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snakeFlap1, 0.0F, 0.17453292519943295F, 0.0F);
        this.snakeFlap2 = new ModelRenderer(this, 33, 28);
        this.snakeFlap2.mirror = true;
        this.snakeFlap2.setPos(-1.0F, -2.0F, 0.0F);
        this.snakeFlap2.addBox(-3.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snakeFlap2, 0.0F, -0.17453292519943295F, 0.0F);
        this.snakeHat2 = new ModelRenderer(this, 53, 28);
        this.snakeHat2.setPos(0.0F, 0.0F, 0.0F);
        this.snakeHat2.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.goatRightHorn2 = new ModelRenderer(this, 12, 15);
        this.goatRightHorn2.mirror = true;
        this.goatRightHorn2.setPos(0.01F, 0.2F, 1.4F);
        this.goatRightHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatRightHorn2, -0.2617993877991494F, 0.0F, 0.0F);
        this.goatRing = new ModelRenderer(this, 35, 28);
        this.goatRing.setPos(-2.0F, -1.0F, 0.0F);
        this.goatRing.addBox(-3.0F, 0.0F, -2.0F, 3.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatRing, 0.0F, 0.0F, -0.5864306020384839F);
        this.mane4 = new ModelRenderer(this, 3, 28);
        this.mane4.setPos(0.0F, -3.0F, -3.0F);
        this.mane4.addBox(-3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane4, 0.6108652381980153F, 0.0F, 3.141592653589793F);
        this.leftBackLeg3 = new ModelRenderer(this, 20, 0);
        this.leftBackLeg3.setPos(0.01F, 2.2F, 0.5F);
        this.leftBackLeg3.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftBackLeg3, -1.2119566751954398F, 0.0F, 0.0F);
        this.mane6 = new ModelRenderer(this, 17, 27);
        this.mane6.setPos(-2.0F, -1.0F, -1.0F);
        this.mane6.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mane6, 0.6981317007977318F, 0.0F, 1.5707963267948966F);
        this.goatHead = new ModelRenderer(this, 37, 14);
        this.goatHead.setPos(-3.0F, -1.5F, 0.2F);
        this.goatHead.addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goatHead, 0.0F, -0.1972222088043106F, -0.9382889765773795F);
        this.leftLeg2 = new ModelRenderer(this, 54, 13);
        this.leftLeg2.setPos(0.01F, 3.4F, 0.5F);
        this.leftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, -0.7428121536172364F, 0.0F, 0.0F);
        this.rightEye = new ModelRenderer(this, 44, 0);
        this.rightEye.mirror = true;
        this.rightEye.setPos(-1.7F, -2.4F, -3.9F);
        this.rightEye.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEye, 0.0F, 0.0F, 0.4363323129985824F);
        this.goatBeard = new ModelRenderer(this, 26, 18);
        this.goatBeard.setPos(0.0F, 3.0F, 0.0F);
        this.goatBeard.addBox(-3.0F, -1.5F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 17.0F, 0.0F);
        this.body.addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg1 = new ModelRenderer(this, 4, 0);
        this.rightBackLeg1.mirror = true;
        this.rightBackLeg1.setPos(-2.3F, -0.1F, 3.9F);
        this.rightBackLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightBackLeg1, -0.23457224414434488F, 0.0F, 0.0F);
        this.rightBackLeg1.addChild(this.rightBackLeg2);
        this.head.addChild(this.mane1);
        this.body.addChild(this.goatNeck);
        this.rightLeg1.addChild(this.rightLeg2);
        this.rightLeg2.addChild(this.rightLeg3);
        this.leftLeg2.addChild(this.leftLeg3);
        this.head.addChild(this.mane3);
        this.snake4.addChild(this.snakeHat1);
        this.head.addChild(this.mane2);
        this.snake1.addChild(this.snake2);
        this.goatHead.addChild(this.goatRightHorn1);
        this.head.addChild(this.leftEye);
        this.snake4.addChild(this.snakeTooth);
        this.snake3.addChild(this.snake4);
        this.body.addChild(this.leftBackLeg1);
        this.head.addChild(this.mane7);
        this.head.addChild(this.mane5);
        this.mouth.addChild(this.jaw);
        this.head.addChild(this.mouth);
        this.rightBackLeg2.addChild(this.rightBackLeg3);
        this.leftBackLeg1.addChild(this.leftBackLeg2);
        this.head.addChild(this.mane8);
        this.snake2.addChild(this.snake3);
        this.body.addChild(this.head);
        this.body.addChild(this.leftLeg1);
        this.mouth.addChild(this.nose);
        this.head.addChild(this.rightEar);
        this.body.addChild(this.rightLeg1);
        this.snake4.addChild(this.snake5);
        this.goatHead.addChild(this.goatLeftHorn1);
        this.goatLeftHorn1.addChild(this.goatLeftHorn2);
        this.body.addChild(this.snake1);
        this.goatHead.addChild(this.goatMouth);
        this.head.addChild(this.leftEar);
        this.snake3.addChild(this.snakeFlap1);
        this.snake3.addChild(this.snakeFlap2);
        this.snakeHat1.addChild(this.snakeHat2);
        this.goatRightHorn1.addChild(this.goatRightHorn2);
        this.goatMouth.addChild(this.goatRing);
        this.head.addChild(this.mane4);
        this.leftBackLeg2.addChild(this.leftBackLeg3);
        this.head.addChild(this.mane6);
        this.goatNeck.addChild(this.goatHead);
        this.leftLeg1.addChild(this.leftLeg2);
        this.head.addChild(this.rightEye);
        this.goatMouth.addChild(this.goatBeard);
        this.body.addChild(this.rightBackLeg1);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void prepareMobModel(ChimeraFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
            float pPartialTick) {
        this.goatMouth.zRot = pEntity.getNoseGoatRot(pPartialTick) - 0.2f;

        float attackProgress = pEntity.getAttackProgress(pPartialTick);
        if (attackProgress > 0) {
            switch (pEntity.getAttacker()) {
            case ChimeraFamiliarEntity.GOAT_ATTACKER:
                this.goatNeck.zRot = -MathHelper.sin(attackProgress * PI * 2) * toRads(30) + 0.9f;
                break;
            case ChimeraFamiliarEntity.SNAKE_ATTACKER:
                this.snake1.xRot = MathHelper.sin(attackProgress * PI) * toRads(25) - 0.66f;
                this.snake2.xRot = MathHelper.sin(attackProgress * PI) * toRads(25) + 0.47f;
                this.snake3.xRot = MathHelper.sin(attackProgress * PI) * toRads(25) + 0.47f;
                this.snake4.xRot = -MathHelper.sin(attackProgress * PI) * toRads(30) + 0.59f;
                break;
            }
        }
    }

    @Override
    public void setupAnim(ChimeraFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
            float netHeadYaw, float headPitch) {
        boolean isSnakeAttacking = pEntity.getAttackProgress(0) > 0
                && pEntity.getAttacker() == ChimeraFamiliarEntity.SNAKE_ATTACKER;

        showModels(pEntity);

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

        this.head.yRot = toRads(netHeadYaw) * 0.7f;
        this.head.xRot = toRads(headPitch) * 0.7f;
        this.snake4.yRot = toRads(netHeadYaw) * 0.3f;
        this.snake4.zRot = -toRads(netHeadYaw) * 0.3f;

        this.snake1.zRot = MathHelper.cos(limbSwing * 0.3f) * 0.1f * limbSwingAmount + toRads(0);
        this.snake2.zRot = MathHelper.cos(limbSwing * 0.3f) * 0.1f * limbSwingAmount + toRads(0);
        this.snake3.zRot = MathHelper.cos(limbSwing * 0.3f) * 0.25f * limbSwingAmount + toRads(0);
        this.snake4.zRot = -MathHelper.cos(limbSwing * 0.3f) * 0.25f * limbSwingAmount;

        this.snake5.xRot = MathHelper.cos(pAgeInTicks * 0.1f) * toRads(15) + toRads(15);

        this.rightBackLeg1.xRot = MathHelper.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount - 0.23f;
        this.leftBackLeg1.xRot = MathHelper.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount - 0.23f;
        this.rightLeg1.xRot = MathHelper.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount + 0.43f;
        this.leftLeg1.xRot = MathHelper.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount + 0.43f;

        if (pEntity.isSitting()) {
            this.leftLeg1.xRot = -toRads(15);
            this.rightLeg1.xRot = -toRads(15);
            this.leftLeg3.xRot = toRads(30);
            this.rightLeg3.xRot = toRads(30);
            this.leftBackLeg1.xRot = -toRads(62);
            this.rightBackLeg1.xRot = -toRads(62);
            this.body.xRot = -toRads(20);
            this.leftLeg1.yRot = -toRads(10);
            this.rightLeg1.yRot = toRads(10);
            this.snake1.zRot = toRads(80);
            this.snake2.zRot = toRads(0);
            this.snake3.zRot = toRads(-20);
            this.snake4.zRot = toRads(-65);
            this.snake2.yRot = -toRads(20);
            this.snake3.yRot = -toRads(20);
            this.snake4.yRot = -toRads(20);
            this.snake2.xRot = toRads(40);
            this.snake3.xRot = toRads(50);
            this.snake4.xRot = toRads(60);
            this.snake5.xRot = toRads(7);
        }

        if (pEntity.isPartying()) {
            this.head.xRot = MathHelper.cos(pAgeInTicks * 0.4f) * toRads(30);
            this.goatHead.zRot = -MathHelper.cos(pAgeInTicks * 0.4f) * toRads(15) - 0.94f;
            this.goatHead.yRot = -0.20f;
            this.snake4.xRot = MathHelper.cos(pAgeInTicks * 0.4f) * toRads(30) + 0.59f;
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

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
