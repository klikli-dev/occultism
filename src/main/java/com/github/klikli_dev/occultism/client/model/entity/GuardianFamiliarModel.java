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

import com.github.klikli_dev.occultism.common.entity.GuardianFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class GuardianFamiliarModel extends EntityModel<GuardianFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer bodyLower;
    public ModelRenderer head;
    public ModelRenderer leftArm1;
    public ModelRenderer rightArm1;
    public ModelRenderer leftLeg1;
    public ModelRenderer rightLeg1;
    public ModelRenderer leftLeg2;
    public ModelRenderer rightLeg2;
    public ModelRenderer crystal1;
    public ModelRenderer crystal2;
    public ModelRenderer crystal3;
    public ModelRenderer leftEye;
    public ModelRenderer rightEye;
    public ModelRenderer tree1;
    public ModelRenderer tree2;
    public ModelRenderer leftArm2;
    public ModelRenderer birdBody;
    public ModelRenderer leftArm3;
    public ModelRenderer birdLeftLeg;
    public ModelRenderer birdRightLeg;
    public ModelRenderer birdHead;
    public ModelRenderer birdLeftWing;
    public ModelRenderer birdRightWing;
    public ModelRenderer birdNose;
    public ModelRenderer rightArm2;
    public ModelRenderer rightArm3;

    public GuardianFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.rightLeg1 = new ModelRenderer(this, 46, 0);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setPos(-2.0F, 2.5F, 0.0F);
        this.rightLeg1.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, 0.0F, 0.0F, 0.1563815016444822F);
        this.head = new ModelRenderer(this, 22, 9);
        this.head.setPos(0.0F, -5.0F, 0.0F);
        this.head.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 20, 0);
        this.leftLeg2.setPos(0.0F, 5.5F, 0.0F);
        this.leftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, 0.0F, 0.0F, 0.11728612207217244F);
        this.leftLeg1 = new ModelRenderer(this, 46, 0);
        this.leftLeg1.setPos(2.0F, 2.5F, 0.0F);
        this.leftLeg1.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, 0.0F, 0.0F, -0.1563815016444822F);
        this.birdBody = new ModelRenderer(this, 56, 28);
        this.birdBody.setPos(0.3F, -2.9F, 0.0F);
        this.birdBody.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(birdBody, -0.5473352640780661F, 0.17453292519943295F, 0.0F);
        this.birdRightLeg = new ModelRenderer(this, 48, 30);
        this.birdRightLeg.mirror = true;
        this.birdRightLeg.setPos(-0.4F, -0.21F, 0.0F);
        this.birdRightLeg.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(birdRightLeg, 0.7330382858376184F, 0.5235987755982988F, 0.41887902047863906F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 9.1F, 0.0F);
        this.body.addBox(-3.5F, -3.0F, -3.0F, 7.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.birdNose = new ModelRenderer(this, 41, 31);
        this.birdNose.setPos(0.0F, -0.3F, -0.7F);
        this.birdNose.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm2 = new ModelRenderer(this, 35, 16);
        this.leftArm2.setPos(0.0F, 6.0F, 0.0F);
        this.leftArm2.addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm2, 0.0F, 0.0F, 0.0781907508222411F);
        this.crystal1 = new ModelRenderer(this, 8, 28);
        this.crystal1.setPos(0.9F, -1.0F, 0.0F);
        this.crystal1.addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crystal1, -0.0781907508222411F, -0.35185837453889574F, 0.4300491170387584F);
        this.rightArm3 = new ModelRenderer(this, 52, 13);
        this.rightArm3.mirror = true;
        this.rightArm3.setPos(1.0F, 5.5F, 0.0F);
        this.rightArm3.addBox(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm3, 0.0F, 0.0F, -0.03909537541112055F);
        this.birdRightWing = new ModelRenderer(this, 50, 30);
        this.birdRightWing.mirror = true;
        this.birdRightWing.setPos(-0.7F, -0.6F, 0.0F);
        this.birdRightWing.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(birdRightWing, 0.0F, 0.3909537457888271F, -0.5082398928281348F);
        this.rightEye = new ModelRenderer(this, 0, 25);
        this.rightEye.mirror = true;
        this.rightEye.setPos(-1.2F, -0.5F, -2.4F);
        this.rightEye.addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEye, 0.0F, 0.12217304763960307F, -0.12217304763960307F);
        this.crystal2 = new ModelRenderer(this, 4, 28);
        this.crystal2.setPos(-1.0F, -1.0F, -1.0F);
        this.crystal2.addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crystal2, 0.1563815016444822F, 0.23457224414434488F, 0.19547687289441354F);
        this.rightArm1 = new ModelRenderer(this, 0, 12);
        this.rightArm1.mirror = true;
        this.rightArm1.setPos(-5.0F, -3.0F, 0.0F);
        this.rightArm1.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm1, 0.0F, 0.0F, 0.19198621771937624F);
        this.birdHead = new ModelRenderer(this, 44, 30);
        this.birdHead.setPos(0.0F, -0.5F, -0.6F);
        this.birdHead.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(birdHead, 0.5082398928281348F, 0.0F, 0.0F);
        this.crystal3 = new ModelRenderer(this, 0, 28);
        this.crystal3.setPos(-1.1F, -1.0F, 1.0F);
        this.crystal3.addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crystal3, -0.11728612207217244F, 0.8210028961170991F, -0.23457224414434488F);
        this.tree2 = new ModelRenderer(this, 12, 27);
        this.tree2.setPos(0.0F, -2.0F, 0.0F);
        this.tree2.addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tree2, 0.0F, -0.7853981633974483F, 0.0F);
        this.leftArm3 = new ModelRenderer(this, 52, 13);
        this.leftArm3.setPos(0.0F, 5.5F, 0.0F);
        this.leftArm3.addBox(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm3, 0.0F, 0.0F, 0.03909537541112055F);
        this.bodyLower = new ModelRenderer(this, 26, 0);
        this.bodyLower.setPos(0.0F, 5.0F, 0.0F);
        this.bodyLower.addBox(-2.5F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm1 = new ModelRenderer(this, 0, 12);
        this.leftArm1.setPos(5.0F, -3.0F, 0.0F);
        this.leftArm1.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm1, 0.0F, 0.0F, -0.19547687289441354F);
        this.tree1 = new ModelRenderer(this, 12, 27);
        this.tree1.setPos(0.0F, -2.0F, 0.0F);
        this.tree1.addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tree1, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightLeg2 = new ModelRenderer(this, 20, 0);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setPos(0.0F, 5.5F, 0.0F);
        this.rightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, 0.0F, 0.0F, -0.11728612207217244F);
        this.leftEye = new ModelRenderer(this, 0, 25);
        this.leftEye.setPos(1.2F, -0.5F, -2.4F);
        this.leftEye.addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEye, 0.0F, -0.12217304763960307F, 0.12217304763960307F);
        this.birdLeftLeg = new ModelRenderer(this, 48, 30);
        this.birdLeftLeg.setPos(0.5F, -0.31F, 0.0F);
        this.birdLeftLeg.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(birdLeftLeg, 0.7428121536172364F, -0.5117305313584153F, -0.4300491170387584F);
        this.birdLeftWing = new ModelRenderer(this, 50, 30);
        this.birdLeftWing.setPos(0.7F, -0.6F, 0.0F);
        this.birdLeftWing.addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(birdLeftWing, 0.0F, -0.3909537457888271F, 0.5082398928281348F);
        this.rightArm2 = new ModelRenderer(this, 35, 16);
        this.rightArm2.mirror = true;
        this.rightArm2.setPos(0.0F, 6.0F, 0.0F);
        this.rightArm2.addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm2, 0.0F, 0.0F, -0.0781907508222411F);
        this.bodyLower.addChild(this.rightLeg1);
        this.body.addChild(this.head);
        this.leftLeg1.addChild(this.leftLeg2);
        this.bodyLower.addChild(this.leftLeg1);
        this.leftArm1.addChild(this.birdBody);
        this.birdBody.addChild(this.birdRightLeg);
        this.birdHead.addChild(this.birdNose);
        this.leftArm1.addChild(this.leftArm2);
        this.head.addChild(this.crystal1);
        this.rightArm2.addChild(this.rightArm3);
        this.birdBody.addChild(this.birdRightWing);
        this.head.addChild(this.rightEye);
        this.head.addChild(this.crystal2);
        this.body.addChild(this.rightArm1);
        this.birdBody.addChild(this.birdHead);
        this.head.addChild(this.crystal3);
        this.head.addChild(this.tree2);
        this.leftArm2.addChild(this.leftArm3);
        this.body.addChild(this.bodyLower);
        this.body.addChild(this.leftArm1);
        this.head.addChild(this.tree1);
        this.rightLeg1.addChild(this.rightLeg2);
        this.head.addChild(this.leftEye);
        this.birdBody.addChild(this.birdLeftLeg);
        this.birdBody.addChild(this.birdLeftWing);
        this.rightArm1.addChild(this.rightArm2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    @Override
    public void setupAnim(GuardianFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
            float pNetHeadYaw, float pHeadPitch) {
        showModels(pEntity);

        this.birdHead.yRot = pNetHeadYaw * (PI / 180f) * 0.4f;
        this.birdHead.xRot = pHeadPitch * (PI / 180f) * 0.4f + toRads(30);
        this.birdLeftLeg.y = -(MathHelper.sin(pAgeInTicks / 2) + 1) * 0.1f - 0.21f;
        this.birdRightLeg.y = -(MathHelper.sin(pAgeInTicks / 2 + PI) + 1) * 0.1f - 0.21f;
        if (pAgeInTicks % 100 < 20) {
            float wingProgress = pAgeInTicks % 100 % 20;
            this.birdLeftWing.zRot = toRads(20) + MathHelper.sin(wingProgress / 20 * toRads(360) * 2) * toRads(25);
            this.birdRightWing.zRot = toRads(-20) - MathHelper.sin(wingProgress / 20 * toRads(360) * 2) * toRads(25);
        }
    }

    private void showModels(GuardianFamiliarEntity entity) {
        boolean hasTree = entity.hasTree();
        byte lives = entity.getLives();

        this.tree1.visible = hasTree;
        this.tree2.visible = hasTree;
        this.birdBody.visible = entity.hasBird();
        this.leftArm1.visible = lives > 4;
        this.leftLeg1.visible = lives > 3;
        this.rightLeg1.visible = lives > 2;
        this.rightArm1.visible = lives > 1;
    }
}
