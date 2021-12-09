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

import com.github.klikli_dev.occultism.common.entity.BatFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BatFamiliarModel extends EntityModel<BatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body1;
    public ModelRenderer head;
    public ModelRenderer body2;
    public ModelRenderer leftEye;
    public ModelRenderer leftEar1;
    public ModelRenderer mouth;
    public ModelRenderer hair;
    public ModelRenderer ribbon;
    public ModelRenderer rightEye;
    public ModelRenderer rightEar1;
    public ModelRenderer leftEar2;
    public ModelRenderer leftEarBack1;
    public ModelRenderer leftEarBack2;
    public ModelRenderer nose;
    public ModelRenderer tooth;
    public ModelRenderer rightEar2;
    public ModelRenderer rightEarBack1;
    public ModelRenderer rightEarBack2;
    public ModelRenderer leftLeg;
    public ModelRenderer leftWing1;
    public ModelRenderer rightLeg;
    public ModelRenderer tail;
    public ModelRenderer rightWing1;
    public ModelRenderer stick;
    public ModelRenderer goblet1;
    public ModelRenderer goblet2;
    public ModelRenderer goblet3;
    public ModelRenderer goblet4;
    public ModelRenderer goblet5;
    public ModelRenderer goblet6;
    public ModelRenderer goblet7;
    public ModelRenderer leftWing2;
    public ModelRenderer leftWingBack1;
    public ModelRenderer leftWingBack2;
    public ModelRenderer rightWing2;
    public ModelRenderer rightWingBack1;
    public ModelRenderer rightWingBack2;
    public ModelRenderer leftChain1;
    public ModelRenderer rightChain1;
    public ModelRenderer leftChain2;
    public ModelRenderer leftChain3;
    public ModelRenderer rightChain2;
    public ModelRenderer rightChain3;

    public BatFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.leftEarBack1 = new ModelRenderer(this, 8, 20);
        this.leftEarBack1.setPos(0.0F, 0.0F, 0.03F);
        this.leftEarBack1.addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 24, 20);
        this.leftLeg.setPos(1.1F, 6.3F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, 0.23666665389412408F, 0.0F, 0.017453292519943295F);
        this.leftChain3 = new ModelRenderer(this, 53, 0);
        this.leftChain3.setPos(0.0F, 3.0F, 0.0F);
        this.leftChain3.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain3, 0.0F, -1.5707963267948966F, 0.0F);
        this.stick = new ModelRenderer(this, 10, 14);
        this.stick.setPos(0.0F, 9.0F, 0.0F);
        this.stick.addBox(-7.0F, 0.0F, 0.0F, 14.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body2 = new ModelRenderer(this, 23, 0);
        this.body2.setPos(0.0F, 1.0F, 0.0F);
        this.body2.addBox(-2.5F, 0.0F, -2.0F, 5.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar1 = new ModelRenderer(this, 0, 27);
        this.rightEar1.mirror = true;
        this.rightEar1.setPos(-2.0F, -4.5F, 0.5F);
        this.rightEar1.addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar1, 0.0F, 0.23649211697418301F, -0.3549999725188077F);
        this.rightLeg = new ModelRenderer(this, 24, 20);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(-1.1F, 6.3F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, 0.23666665389412408F, 0.0F, -0.017453292519943295F);
        this.head = new ModelRenderer(this, 1, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftWingBack2 = new ModelRenderer(this, 44, 8);
        this.leftWingBack2.setPos(0.0F, 0.0F, 0.03F);
        this.leftWingBack2.addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftChain1 = new ModelRenderer(this, 41, 0);
        this.leftChain1.setPos(7.0F, 1.0F, 0.5F);
        this.leftChain1.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain1, 0.0F, -0.5235987755982988F, 0.0F);
        this.goblet5 = new ModelRenderer(this, 35, 27);
        this.goblet5.setPos(1.5F, 1.5F, 0.0F);
        this.goblet5.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goblet5, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightEar2 = new ModelRenderer(this, 0, 25);
        this.rightEar2.mirror = true;
        this.rightEar2.setPos(0.0F, -5.0F, 0.0F);
        this.rightEar2.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar2, 0.4363323129985824F, 0.0F, 0.0F);
        this.goblet1 = new ModelRenderer(this, 10, 30);
        this.goblet1.setPos(-3.5F, 3.0F, 0.0F);
        this.goblet1.addBox(0.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goblet1, 0.7853981633974483F, 0.0F, 0.0F);
        this.rightEye = new ModelRenderer(this, 0, 0);
        this.rightEye.setPos(-1.4F, -3.2F, -3.1F);
        this.rightEye.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEye, 0.0F, 0.0F, -0.1972222088043106F);
        this.rightEarBack1 = new ModelRenderer(this, 8, 20);
        this.rightEarBack1.mirror = true;
        this.rightEarBack1.setPos(0.0F, 0.0F, 0.03F);
        this.rightEarBack1.addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightWing1 = new ModelRenderer(this, 44, 26);
        this.rightWing1.mirror = true;
        this.rightWing1.setPos(-1.8F, 4.0F, 0.0F);
        this.rightWing1.addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing1, 0.0F, 0.0F, -0.17453292519943295F);
        this.rightWingBack2 = new ModelRenderer(this, 44, 8);
        this.rightWingBack2.mirror = true;
        this.rightWingBack2.setPos(0.0F, 0.0F, 0.03F);
        this.rightWingBack2.addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftEarBack2 = new ModelRenderer(this, 8, 18);
        this.leftEarBack2.setPos(0.0F, 0.0F, 0.03F);
        this.leftEarBack2.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 0, 12);
        this.mouth.setPos(0.0F, -0.3F, -3.0F);
        this.mouth.addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.17453292519943295F, 0.0F, 0.0F);
        this.tooth = new ModelRenderer(this, 0, 4);
        this.tooth.setPos(0.5F, 0.9F, -0.5F);
        this.tooth.addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightEarBack2 = new ModelRenderer(this, 8, 18);
        this.rightEarBack2.mirror = true;
        this.rightEarBack2.setPos(0.0F, 0.0F, 0.03F);
        this.rightEarBack2.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body1 = new ModelRenderer(this, 8, 10);
        this.body1.setPos(0.0F, 14.0F, 0.0F);
        this.body1.addBox(-2.0F, 0.0F, -1.5F, 4.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.goblet6 = new ModelRenderer(this, 34, 29);
        this.goblet6.setPos(1.5F, 0.0F, 1.5F);
        this.goblet6.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goblet6, 0.0F, 0.0F, 0.7853981633974483F);
        this.leftChain2 = new ModelRenderer(this, 47, 0);
        this.leftChain2.setPos(0.0F, 3.0F, 0.0F);
        this.leftChain2.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftChain2, 0.0F, 1.0471975511965976F, 0.0F);
        this.goblet3 = new ModelRenderer(this, 22, 26);
        this.goblet3.setPos(5.01F, 0.0F, 0.0F);
        this.goblet3.addBox(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.goblet7 = new ModelRenderer(this, 38, 29);
        this.goblet7.setPos(1.5F, 0.0F, -1.5F);
        this.goblet7.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goblet7, 0.0F, 0.0F, 0.7853981633974483F);
        this.leftWingBack1 = new ModelRenderer(this, 44, 14);
        this.leftWingBack1.setPos(0.0F, 0.0F, 0.03F);
        this.leftWingBack1.addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightChain3 = new ModelRenderer(this, 53, 0);
        this.rightChain3.setPos(0.0F, 3.0F, 0.0F);
        this.rightChain3.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain3, 0.0F, -1.5707963267948966F, 0.0F);
        this.leftWing1 = new ModelRenderer(this, 44, 26);
        this.leftWing1.setPos(1.8F, 4.0F, 0.0F);
        this.leftWing1.addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing1, 0.0F, 0.0F, 0.17453292519943295F);
        this.goblet2 = new ModelRenderer(this, 10, 26);
        this.goblet2.setPos(0.0F, 0.0F, 0.0F);
        this.goblet2.addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightWing2 = new ModelRenderer(this, 44, 20);
        this.rightWing2.mirror = true;
        this.rightWing2.setPos(0.0F, -6.0F, 0.0F);
        this.rightWing2.addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftEye = new ModelRenderer(this, 0, 0);
        this.leftEye.setPos(1.4F, -3.2F, -3.1F);
        this.leftEye.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEye, 0.0F, 0.0F, 0.1972222088043106F);
        this.tail = new ModelRenderer(this, 34, 11);
        this.tail.setPos(0.0F, 4.8F, 1.5F);
        this.tail.addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.23666665389412408F, 0.0F, 0.0F);
        this.rightChain2 = new ModelRenderer(this, 47, 0);
        this.rightChain2.setPos(0.0F, 3.0F, 0.0F);
        this.rightChain2.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain2, 0.0F, 1.0471975511965976F, 0.0F);
        this.hair = new ModelRenderer(this, 0, 18);
        this.hair.setPos(0.5F, -4.8F, -1.5F);
        this.hair.addBox(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(hair, -0.03909537541112055F, 0.0F, -0.23457224414434488F);
        this.nose = new ModelRenderer(this, 0, 16);
        this.nose.setPos(0.0F, -0.4F, -0.4F);
        this.nose.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ribbon = new ModelRenderer(this, 0, 20);
        this.ribbon.setPos(0.0F, -5.0F, -2.51F);
        this.ribbon.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ribbon, -0.17453292519943295F, 0.17453292519943295F, 0.7853981633974483F);
        this.goblet4 = new ModelRenderer(this, 31, 27);
        this.goblet4.setPos(1.5F, -1.5F, 0.0F);
        this.goblet4.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(goblet4, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightChain1 = new ModelRenderer(this, 41, 0);
        this.rightChain1.setPos(-7.0F, 1.0F, 0.5F);
        this.rightChain1.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightChain1, 0.0F, -0.5235987755982988F, 0.0F);
        this.leftEar2 = new ModelRenderer(this, 0, 25);
        this.leftEar2.setPos(0.0F, -5.0F, 0.0F);
        this.leftEar2.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar2, 0.4363323129985824F, 0.0F, 0.0F);
        this.leftEar1 = new ModelRenderer(this, 0, 27);
        this.leftEar1.setPos(2.0F, -4.5F, 0.5F);
        this.leftEar1.addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar1, 0.0F, -0.23649211697418301F, 0.3549999725188077F);
        this.rightWingBack1 = new ModelRenderer(this, 44, 14);
        this.rightWingBack1.mirror = true;
        this.rightWingBack1.setPos(0.0F, 0.0F, 0.03F);
        this.rightWingBack1.addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftWing2 = new ModelRenderer(this, 44, 20);
        this.leftWing2.setPos(0.0F, -6.0F, 0.0F);
        this.leftWing2.addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar1.addChild(this.leftEarBack1);
        this.body2.addChild(this.leftLeg);
        this.leftChain2.addChild(this.leftChain3);
        this.body2.addChild(this.stick);
        this.body1.addChild(this.body2);
        this.head.addChild(this.rightEar1);
        this.body2.addChild(this.rightLeg);
        this.body1.addChild(this.head);
        this.leftWing2.addChild(this.leftWingBack2);
        this.stick.addChild(this.leftChain1);
        this.goblet3.addChild(this.goblet5);
        this.rightEar1.addChild(this.rightEar2);
        this.leftLeg.addChild(this.goblet1);
        this.head.addChild(this.rightEye);
        this.rightEar1.addChild(this.rightEarBack1);
        this.body2.addChild(this.rightWing1);
        this.rightWing2.addChild(this.rightWingBack2);
        this.leftEar2.addChild(this.leftEarBack2);
        this.head.addChild(this.mouth);
        this.mouth.addChild(this.tooth);
        this.rightEar2.addChild(this.rightEarBack2);
        this.goblet3.addChild(this.goblet6);
        this.leftChain1.addChild(this.leftChain2);
        this.goblet1.addChild(this.goblet3);
        this.goblet3.addChild(this.goblet7);
        this.leftWing1.addChild(this.leftWingBack1);
        this.rightChain2.addChild(this.rightChain3);
        this.body2.addChild(this.leftWing1);
        this.goblet1.addChild(this.goblet2);
        this.rightWing1.addChild(this.rightWing2);
        this.head.addChild(this.leftEye);
        this.body2.addChild(this.tail);
        this.rightChain1.addChild(this.rightChain2);
        this.head.addChild(this.hair);
        this.mouth.addChild(this.nose);
        this.head.addChild(this.ribbon);
        this.goblet3.addChild(this.goblet4);
        this.stick.addChild(this.rightChain1);
        this.leftEar1.addChild(this.leftEar2);
        this.head.addChild(this.leftEar1);
        this.rightWing1.addChild(this.rightWingBack1);
        this.leftWing1.addChild(this.leftWing2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body1).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(BatFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        showModels(pEntity);

        float partialTicks = Minecraft.getInstance().getFrameTime();

        this.body1.yRot = 0;
        this.body1.zRot = 0;
        this.head.xRot = this.toRads(headPitch);
        this.head.yRot = this.toRads(netHeadYaw);
        this.head.zRot = 0;
        this.leftEar1.xRot = 0;
        this.rightEar1.xRot = 0;

        float animationHeight = pEntity.getAnimationHeight(partialTicks);
        this.leftWing1.xRot = animationHeight * this.toRads(20) - 0.15f;
        this.rightWing1.xRot = animationHeight * this.toRads(20) - 0.15f;
        this.leftWing1.yRot = animationHeight * this.toRads(20) - 0.15f;
        this.rightWing1.yRot = -animationHeight * this.toRads(20) + 0.15f;
        this.leftWing2.xRot = animationHeight * this.toRads(20) + this.toRads(15);
        this.rightWing2.xRot = animationHeight * this.toRads(20) + this.toRads(15);
        this.body1.xRot = this.toRads(20) + limbSwingAmount * this.toRads(30);
        this.leftLeg.xRot = 0.24f + MathHelper.cos(ageInTicks * 0.1f) * toRads(20);
        this.rightLeg.xRot = 0.24f + MathHelper.cos(ageInTicks * 0.1f) * toRads(20);

        if (pEntity.isPartying()) {
            float headRot = MathHelper.sin(ageInTicks / 3) * this.toRads(10);
            float wingRot = MathHelper.sin(ageInTicks / 3) * this.toRads(40);
            this.head.xRot = headRot;
            this.head.yRot = headRot;
            this.head.zRot = headRot;
            this.leftWing1.xRot = wingRot;
            this.rightWing1.xRot = wingRot;
            this.leftWing1.yRot = wingRot;
            this.rightWing1.yRot = wingRot;
            this.leftWing2.xRot = wingRot;
            this.rightWing2.xRot = wingRot;
            this.body1.xRot = this.toRads(20) + limbSwingAmount * this.toRads(70);
            this.leftEar1.xRot = MathHelper.cos(ageInTicks / 3 + PI) * toRads(25);
            this.rightEar1.xRot = MathHelper.cos(ageInTicks / 3) * toRads(25);
        } else if (pEntity.isSitting()) {
            this.leftWing1.xRot = this.toRads(0);
            this.rightWing1.xRot = this.toRads(0);
            this.leftWing1.yRot = this.toRads(80);
            this.rightWing1.yRot = -this.toRads(80);
            this.leftWing2.xRot = this.toRads(15);
            this.rightWing2.xRot = this.toRads(15);
            this.head.xRot = 0.2f;
            this.body1.xRot = this.toRads(180);
            this.body1.yRot = this.toRads(180);
            this.leftLeg.xRot = 0.24f;
            this.rightLeg.xRot = 0.24f;
        }
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

    private void showModels(BatFamiliarEntity entityIn) {
        boolean isSitting = entityIn.isSitting();
        boolean isPartying = entityIn.isPartying();

        this.stick.visible = isSitting && !isPartying;
        this.goblet1.visible = entityIn.hasBlacksmithUpgrade() && (!isSitting || isPartying);
        this.hair.visible = entityIn.hasHair();
        this.ribbon.visible = entityIn.hasRibbon();
        this.tail.visible = entityIn.hasTail();
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
