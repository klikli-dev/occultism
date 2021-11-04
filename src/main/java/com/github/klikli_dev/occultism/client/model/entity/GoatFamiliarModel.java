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
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class GoatFamiliarModel extends EntityModel<GoatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer leftBackLeg1;
    public ModelRenderer rightBackLeg1;
    public ModelRenderer neck;
    public ModelRenderer tail;
    public ModelRenderer leftFrontLeg1;
    public ModelRenderer rightFrontLeg1;
    public ModelRenderer leftBackLeg2;
    public ModelRenderer leftBackLeg3;
    public ModelRenderer rightBackLeg2;
    public ModelRenderer rightBackLeg3;
    public ModelRenderer head;
    public ModelRenderer bell1;
    public ModelRenderer rightHorn1;
    public ModelRenderer mouth;
    public ModelRenderer leftHorn1;
    public ModelRenderer leftEyeEvil;
    public ModelRenderer rightEyeEvil;
    public ModelRenderer leftSideHornEvil1;
    public ModelRenderer rightSideHornEvil1;
    public ModelRenderer rightHorn2;
    public ModelRenderer beard;
    public ModelRenderer ring;
    public ModelRenderer leftHorn2;
    public ModelRenderer leftPupilEvil;
    public ModelRenderer rightPupilEvil;
    public ModelRenderer leftSideHornEvil2;
    public ModelRenderer leftSideHornEvil3;
    public ModelRenderer rightSideHornEvil2;
    public ModelRenderer rightSideHornEvil3;
    public ModelRenderer bell2;
    public ModelRenderer bell3;
    public ModelRenderer leftFrontLeg2;
    public ModelRenderer leftFrontLeg3;
    public ModelRenderer rightFrontLeg2;
    public ModelRenderer rightFrontLeg3;

    public GoatFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.rightFrontLeg2 = new ModelRenderer(this, 0, 6);
        this.rightFrontLeg2.mirror = true;
        this.rightFrontLeg2.setPos(-0.01F, 4.6F, 0.0F);
        this.rightFrontLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFrontLeg2, 0.3127630032889644F, 0.0F, 0.0F);
        this.leftSideHornEvil2 = new ModelRenderer(this, 33, 0);
        this.leftSideHornEvil2.setPos(-0.01F, -0.2F, 0.9F);
        this.leftSideHornEvil2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftSideHornEvil2, -0.44820055723846597F, 0.0F, 0.0F);
        this.leftHorn1 = new ModelRenderer(this, 18, 4);
        this.leftHorn1.setPos(0.9F, -0.6F, 0.2F);
        this.leftHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn1, -0.17453292519943295F, 0.0F, 0.0F);
        this.rightHorn1 = new ModelRenderer(this, 18, 4);
        this.rightHorn1.mirror = true;
        this.rightHorn1.setPos(-0.9F, -0.6F, 0.2F);
        this.rightHorn1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn1, -0.17453292519943295F, 0.0F, 0.0F);
        this.rightSideHornEvil2 = new ModelRenderer(this, 33, 0);
        this.rightSideHornEvil2.mirror = true;
        this.rightSideHornEvil2.setPos(-0.01F, -0.2F, 0.9F);
        this.rightSideHornEvil2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightSideHornEvil2, -0.44820055723846597F, 0.0F, 0.0F);
        this.rightFrontLeg3 = new ModelRenderer(this, 22, 0);
        this.rightFrontLeg3.mirror = true;
        this.rightFrontLeg3.setPos(-0.01F, 1.5F, -0.1F);
        this.rightFrontLeg3.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFrontLeg3, -0.19495327877934715F, 0.0F, 0.0F);
        this.rightBackLeg3 = new ModelRenderer(this, 6, 0);
        this.rightBackLeg3.mirror = true;
        this.rightBackLeg3.setPos(0.01F, 2.2F, 0.5F);
        this.rightBackLeg3.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightBackLeg3, -1.2119566751954398F, 0.0F, 0.0F);
        this.ring = new ModelRenderer(this, 0, 29);
        this.ring.setPos(0.0F, -1.0F, -2.0F);
        this.ring.addBox(-2.0F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ring, 0.5759586531581287F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 36, 0);
        this.neck.setPos(0.0F, -1.8F, -3.6F);
        this.neck.addBox(-1.0F, -1.5F, -4.0F, 2.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.8726646259971648F, 0.0F, 0.0F);
        this.beard = new ModelRenderer(this, 45, 6);
        this.beard.setPos(0.0F, 3.0F, 0.0F);
        this.beard.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 48, 0);
        this.head.setPos(0.0F, -0.8F, -3.1F);
        this.head.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.9641199080964395F, 0.0F, 0.0F);
        this.leftFrontLeg3 = new ModelRenderer(this, 22, 0);
        this.leftFrontLeg3.setPos(0.01F, 1.5F, 0.0F);
        this.leftFrontLeg3.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftFrontLeg3, -0.19495327877934715F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 17.0F, 1.0F);
        this.body.addBox(-2.0F, -3.0F, -5.0F, 4.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.bell3 = new ModelRenderer(this, 0, 25);
        this.bell3.setPos(0.0F, 2.0F, 0.0F);
        this.bell3.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightEyeEvil = new ModelRenderer(this, 24, 15);
        this.rightEyeEvil.mirror = true;
        this.rightEyeEvil.setPos(-1.3F, -0.2F, -1.7F);
        this.rightEyeEvil.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftEyeEvil = new ModelRenderer(this, 24, 15);
        this.leftEyeEvil.setPos(1.3F, -0.2F, -1.7F);
        this.leftEyeEvil.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg1 = new ModelRenderer(this, 0, 0);
        this.rightBackLeg1.mirror = true;
        this.rightBackLeg1.setPos(-1.8F, -0.1F, 3.9F);
        this.rightBackLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightBackLeg1, -0.23457224414434488F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 27, 4);
        this.mouth.setPos(0.0F, 0.0F, -2.5F);
        this.mouth.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.19198621771937624F, 0.0F, 0.0F);
        this.leftHorn2 = new ModelRenderer(this, 57, 0);
        this.leftHorn2.setPos(-0.01F, 0.2F, 1.0F);
        this.leftHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn2, -0.17453292519943295F, 0.0F, 0.0F);
        this.leftSideHornEvil1 = new ModelRenderer(this, 27, 0);
        this.leftSideHornEvil1.setPos(1.0F, 0.4F, -0.3F);
        this.leftSideHornEvil1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftSideHornEvil1, 0.6981317007977318F, 0.0F, 0.8991936386169619F);
        this.rightSideHornEvil1 = new ModelRenderer(this, 27, 0);
        this.rightSideHornEvil1.mirror = true;
        this.rightSideHornEvil1.setPos(-1.0F, 0.4F, -0.3F);
        this.rightSideHornEvil1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightSideHornEvil1, 0.6981317007977318F, 0.0F, -0.8991936386169619F);
        this.leftBackLeg2 = new ModelRenderer(this, 18, 0);
        this.leftBackLeg2.setPos(-0.01F, 3.3F, -0.1F);
        this.leftBackLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftBackLeg2, 0.8600982340775168F, 0.0F, 0.0F);
        this.rightSideHornEvil3 = new ModelRenderer(this, 33, 0);
        this.rightSideHornEvil3.mirror = true;
        this.rightSideHornEvil3.setPos(-0.01F, 0.0F, 1.0F);
        this.rightSideHornEvil3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightSideHornEvil3, 0.45099308137849586F, 0.0F, 0.0F);
        this.rightHorn2 = new ModelRenderer(this, 57, 0);
        this.rightHorn2.mirror = true;
        this.rightHorn2.setPos(0.01F, 0.2F, 1.0F);
        this.rightHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn2, -0.17453292519943295F, 0.0F, 0.0F);
        this.rightPupilEvil = new ModelRenderer(this, 22, 15);
        this.rightPupilEvil.mirror = true;
        this.rightPupilEvil.setPos(-0.2F, 0.0F, 0.0F);
        this.rightPupilEvil.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightPupilEvil, 0.7853981633974483F, 0.0F, 0.0F);
        this.leftFrontLeg2 = new ModelRenderer(this, 0, 6);
        this.leftFrontLeg2.setPos(-0.01F, 4.6F, 0.0F);
        this.leftFrontLeg2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftFrontLeg2, 0.3127630032889644F, 0.0F, 0.0F);
        this.leftBackLeg3 = new ModelRenderer(this, 6, 0);
        this.leftBackLeg3.setPos(0.01F, 2.2F, 0.5F);
        this.leftBackLeg3.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftBackLeg3, -1.2119566751954398F, 0.0F, 0.0F);
        this.leftBackLeg1 = new ModelRenderer(this, 0, 0);
        this.leftBackLeg1.setPos(1.8F, -0.1F, 3.9F);
        this.leftBackLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftBackLeg1, -0.23457224414434488F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 55, 6);
        this.tail.setPos(0.0F, -1.6F, 4.3F);
        this.tail.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.19547687289441354F, 0.0F, 0.0F);
        this.leftPupilEvil = new ModelRenderer(this, 22, 15);
        this.leftPupilEvil.setPos(0.2F, 0.0F, 0.0F);
        this.leftPupilEvil.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftPupilEvil, 0.7853981633974483F, 0.0F, 0.0F);
        this.leftFrontLeg1 = new ModelRenderer(this, 37, 7);
        this.leftFrontLeg1.setPos(1.8F, -0.1F, -3.1F);
        this.leftFrontLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftFrontLeg1, -0.12217304763960307F, 0.0F, 0.0F);
        this.bell1 = new ModelRenderer(this, 0, 15);
        this.bell1.setPos(0.01F, -1.6F, -2.1F);
        this.bell1.addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bell1, 0.1563815016444822F, 0.0F, 0.0F);
        this.leftSideHornEvil3 = new ModelRenderer(this, 33, 0);
        this.leftSideHornEvil3.setPos(-0.01F, 0.0F, 1.0F);
        this.leftSideHornEvil3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftSideHornEvil3, 0.45099308137849586F, 0.0F, 0.0F);
        this.bell2 = new ModelRenderer(this, 0, 21);
        this.bell2.setPos(0.0F, 4.0F, -0.1F);
        this.bell2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bell2, 0.6646214111173737F, 0.0F, 0.0F);
        this.rightFrontLeg1 = new ModelRenderer(this, 37, 7);
        this.rightFrontLeg1.mirror = true;
        this.rightFrontLeg1.setPos(-1.8F, -0.1F, -3.1F);
        this.rightFrontLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFrontLeg1, -0.12217304763960307F, 0.0F, 0.0F);
        this.rightBackLeg2 = new ModelRenderer(this, 18, 0);
        this.rightBackLeg2.mirror = true;
        this.rightBackLeg2.setPos(-0.01F, 3.3F, -0.1F);
        this.rightBackLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightBackLeg2, 0.8600982340775168F, 0.0F, 0.0F);
        this.rightFrontLeg1.addChild(this.rightFrontLeg2);
        this.leftSideHornEvil1.addChild(this.leftSideHornEvil2);
        this.head.addChild(this.leftHorn1);
        this.head.addChild(this.rightHorn1);
        this.rightSideHornEvil1.addChild(this.rightSideHornEvil2);
        this.rightFrontLeg2.addChild(this.rightFrontLeg3);
        this.rightBackLeg2.addChild(this.rightBackLeg3);
        this.mouth.addChild(this.ring);
        this.body.addChild(this.neck);
        this.mouth.addChild(this.beard);
        this.neck.addChild(this.head);
        this.leftFrontLeg2.addChild(this.leftFrontLeg3);
        this.bell2.addChild(this.bell3);
        this.head.addChild(this.rightEyeEvil);
        this.head.addChild(this.leftEyeEvil);
        this.body.addChild(this.rightBackLeg1);
        this.head.addChild(this.mouth);
        this.leftHorn1.addChild(this.leftHorn2);
        this.head.addChild(this.leftSideHornEvil1);
        this.head.addChild(this.rightSideHornEvil1);
        this.leftBackLeg1.addChild(this.leftBackLeg2);
        this.rightSideHornEvil2.addChild(this.rightSideHornEvil3);
        this.rightHorn1.addChild(this.rightHorn2);
        this.rightEyeEvil.addChild(this.rightPupilEvil);
        this.leftFrontLeg1.addChild(this.leftFrontLeg2);
        this.leftBackLeg2.addChild(this.leftBackLeg3);
        this.body.addChild(this.leftBackLeg1);
        this.body.addChild(this.tail);
        this.leftEyeEvil.addChild(this.leftPupilEvil);
        this.body.addChild(this.leftFrontLeg1);
        this.neck.addChild(this.bell1);
        this.leftSideHornEvil2.addChild(this.leftSideHornEvil3);
        this.bell1.addChild(this.bell2);
        this.body.addChild(this.rightFrontLeg1);
        this.rightBackLeg1.addChild(this.rightBackLeg2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void prepareMobModel(GoatFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
            float pPartialTick) {
        this.neck.yRot = pEntity.getNeckYRot(pPartialTick);
    }

    @Override
    public void setupAnim(GoatFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
            float netHeadYaw, float headPitch) {
        showModels(pEntity);

        this.body.zRot = 0;
        this.neck.xRot = -0.87f;

        this.head.yRot = toRads(netHeadYaw) * 0.5f;
        this.head.zRot = toRads(netHeadYaw) * 0.5f;
        this.head.xRot = toRads(headPitch) * 0.5f + 0.96f;

        this.rightBackLeg1.xRot = MathHelper.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount - 0.23f;
        this.leftBackLeg1.xRot = MathHelper.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount - 0.23f;
        this.rightFrontLeg1.xRot = MathHelper.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount - 0.12f;
        this.leftFrontLeg1.xRot = MathHelper.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount - 0.12f;

        if (pEntity.isSitting()) {
            this.body.zRot = toRads(90);
            this.neck.xRot = toRads(10);
        }

        if (pEntity.isPartying()) {
            this.body.zRot = toRads(180);
            this.neck.xRot = toRads(10);
            this.rightBackLeg1.xRot = MathHelper.cos(pAgeInTicks * 0.5f) * toRads(25) - 0.23f;
            this.leftBackLeg1.xRot = MathHelper.cos(pAgeInTicks * 0.5f + PI) * toRads(25) - 0.23f;
            this.rightFrontLeg1.xRot = MathHelper.cos(pAgeInTicks * 0.5f + PI) * toRads(25) - 0.12f;
            this.leftFrontLeg1.xRot = MathHelper.cos(pAgeInTicks * 0.5f) * toRads(25) - 0.12f;
        }

        this.bell2.zRot = MathHelper.cos(limbSwing * 0.7f) * 0.5f * limbSwingAmount;
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

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
