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
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BeaverFamiliarModel extends EntityModel<BeaverFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer leftLeg1;
    public ModelRenderer head;
    public ModelRenderer leftArm1;
    public ModelRenderer tail2;
    public ModelRenderer rightLeg1;
    public ModelRenderer rightArm1;
    public ModelRenderer leftLeg2;
    public ModelRenderer mouth;
    public ModelRenderer leftEye;
    public ModelRenderer leftEar;
    public ModelRenderer rightEye;
    public ModelRenderer rightEar;
    public ModelRenderer nose;
    public ModelRenderer teeth;
    public ModelRenderer whiskers1;
    public ModelRenderer whiskers2;
    public ModelRenderer leftArm2;
    public ModelRenderer rightLeg2;
    public ModelRenderer rightArm2;

    public BeaverFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.rightLeg2 = new ModelRenderer(this, 34, 0);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setPos(-1.48F, 5.7F, 0.5F);
        this.rightLeg2.addBox(-1.5F, -0.5F, -4.0F, 3.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftEye = new ModelRenderer(this, 34, 5);
        this.leftEye.setPos(2.0F, -0.5F, -3.0F);
        this.leftEye.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 30, 0);
        this.nose.setPos(0.0F, -0.2F, -1.7F);
        this.nose.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 0, 20);
        this.leftEar.setPos(1.5F, -2.1F, -2.1F);
        this.leftEar.addBox(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, -0.23457224414434488F, -0.35185837453889574F, 0.5082398928281348F);
        this.mouth = new ModelRenderer(this, 0, 0);
        this.mouth.setPos(0.0F, 0.3F, -5.0F);
        this.mouth.addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 19.5F, 0.0F);
        this.body.addBox(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 0.08726646259971647F, 0.0F, 0.0F);
        this.leftArm1 = new ModelRenderer(this, 32, 9);
        this.leftArm1.setPos(1.7F, 0.0F, -3.0F);
        this.leftArm1.addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm1, -0.06981317007977318F, 0.0F, 0.0F);
        this.rightArm1 = new ModelRenderer(this, 32, 9);
        this.rightArm1.mirror = true;
        this.rightArm1.setPos(-1.7F, 0.0F, -3.0F);
        this.rightArm1.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm1, -0.06981317007977318F, 0.0F, 0.0F);
        this.rightArm2 = new ModelRenderer(this, 40, 10);
        this.rightArm2.mirror = true;
        this.rightArm2.setPos(-1.02F, 3.7F, 0.02F);
        this.rightArm2.addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg1 = new ModelRenderer(this, 22, 0);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setPos(-2.0F, -1.5F, 2.5F);
        this.rightLeg1.addBox(-2.0F, 0.0F, -2.0F, 2.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, -0.06981317007977318F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 43, 0);
        this.head.setPos(0.0F, -1.0F, -4.0F);
        this.head.addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.08726646259971647F, 0.0F, 0.0F);
        this.whiskers2 = new ModelRenderer(this, 4, 20);
        this.whiskers2.setPos(0.0F, -0.8F, -1.3F);
        this.whiskers2.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(whiskers2, 0.3909537457888271F, 0.039269908169872414F, -0.017453292519943295F);
        this.whiskers1 = new ModelRenderer(this, 4, 20);
        this.whiskers1.setPos(0.0F, 0.3F, -1.7F);
        this.whiskers1.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(whiskers1, 0.3909537457888271F, 0.039269908169872414F, 0.017453292519943295F);
        this.rightEye = new ModelRenderer(this, 34, 5);
        this.rightEye.mirror = true;
        this.rightEye.setPos(-2.0F, -0.5F, -3.0F);
        this.rightEye.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.teeth = new ModelRenderer(this, 0, 5);
        this.teeth.setPos(0.0F, 1.0F, -0.8F);
        this.teeth.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(teeth, 0.11728612207217244F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 16, 22);
        this.tail2.setPos(0.0F, 2.0F, 5.0F);
        this.tail2.addBox(-3.5F, -0.5F, 0.0F, 7.0F, 1.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail2, 0.5082398928281348F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, -10, 22);
        this.tail.setPos(0.0F, 2.0F, 5.0F);
        this.tail.addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.5082398928281348F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 0, 20);
        this.rightEar.mirror = true;
        this.rightEar.setPos(-1.5F, -2.1F, -2.1F);
        this.rightEar.addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, -0.23457224414434488F, 0.35185837453889574F, -0.5082398928281348F);
        this.leftArm2 = new ModelRenderer(this, 40, 10);
        this.leftArm2.setPos(1.02F, 3.7F, 0.02F);
        this.leftArm2.addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 34, 0);
        this.leftLeg2.setPos(1.48F, 5.7F, 0.5F);
        this.leftLeg2.addBox(-1.5F, -0.5F, -4.0F, 3.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 22, 0);
        this.leftLeg1.setPos(2.0F, -1.5F, 2.5F);
        this.leftLeg1.addBox(0.0F, 0.0F, -2.0F, 2.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, -0.06981317007977318F, 0.0F, 0.0F);
        this.rightLeg1.addChild(this.rightLeg2);
        this.head.addChild(this.leftEye);
        this.mouth.addChild(this.nose);
        this.head.addChild(this.leftEar);
        this.head.addChild(this.mouth);
        this.body.addChild(this.leftArm1);
        this.body.addChild(this.rightArm1);
        this.rightArm1.addChild(this.rightArm2);
        this.body.addChild(this.rightLeg1);
        this.body.addChild(this.head);
        this.mouth.addChild(this.whiskers2);
        this.mouth.addChild(this.whiskers1);
        this.head.addChild(this.rightEye);
        this.mouth.addChild(this.teeth);
        this.body.addChild(this.tail2);
        this.body.addChild(this.tail);
        this.head.addChild(this.rightEar);
        this.leftArm1.addChild(this.leftArm2);
        this.leftLeg1.addChild(this.leftLeg2);
        this.body.addChild(this.leftLeg1);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
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

        
        this.head.xRot = toRads(headPitch);
        this.head.yRot = toRads(netHeadYaw);

        this.rightLeg1.xRot = -0.07f + MathHelper.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount;
        this.leftLeg1.xRot = -0.07f + MathHelper.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount;
        this.rightArm1.xRot = -0.07f + MathHelper.cos(limbSwing * 0.7f + PI) * 0.8f * limbSwingAmount;
        this.leftArm1.xRot = -0.07f + MathHelper.cos(limbSwing * 0.7f) * 0.8f * limbSwingAmount;

        this.tail.xRot = 0.51f + MathHelper.cos(pAgeInTicks * 0.1f) * toRads(20);
        this.tail2.xRot = 0.51f + MathHelper.cos(pAgeInTicks * 0.1f) * toRads(20);
        
        if (!pEntity.isSitting() && pEntity.isInWater()) {
            this.rightLeg1.zRot = toRads(40);
            this.leftLeg1.zRot = -toRads(40);
            this.rightArm1.zRot = toRads(40);
            this.leftArm1.zRot = -toRads(40);
            
            this.rightLeg1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.3f) * 0.4f;
            this.leftLeg1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.3f) * 0.4f;
            this.rightArm1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.3f + PI) * 0.4f;
            this.leftArm1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.3f + PI) * 0.4f;
        }
        
        if (pEntity.isSitting()) {
            this.body.xRot = toRads(-40);
            this.head.xRot = toRads(25);
            this.head.yRot = 0;
            this.tail.xRot = toRads(70);
            this.tail2.xRot = toRads(70);
            
            this.leftLeg1.xRot = toRads(-20);
            this.leftLeg2.xRot = toRads(50);
            this.rightLeg1.xRot = toRads(-20);
            this.rightLeg2.xRot = toRads(50);
            
            this.leftArm1.xRot = toRads(10);
            this.leftArm2.xRot = toRads(40);
            this.rightArm1.xRot = toRads(10);
            this.rightArm2.xRot = toRads(40);
        }
        
        if (pEntity.isPartying()) {
            this.body.xRot = toRads(90);
            this.body.yRot = pAgeInTicks * 0.5f;
            this.body.y = 12.5f;
            this.head.xRot = 0;
            this.head.yRot = 0;
            
            this.tail.xRot = MathHelper.cos(pAgeInTicks * 0.8f) * toRads(50);
            this.tail2.xRot = MathHelper.cos(pAgeInTicks * 0.8f) * toRads(50);
            
            this.rightLeg1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.7f) * toRads(40);
            this.leftLeg1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.7f + PI) * toRads(40);
            this.rightArm1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.7f + PI) * toRads(40);
            this.leftArm1.xRot = -0.07f + MathHelper.cos(pAgeInTicks * 0.7f) * toRads(40);
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

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
