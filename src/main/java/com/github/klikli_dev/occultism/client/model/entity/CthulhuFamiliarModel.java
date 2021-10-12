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
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class CthulhuFamiliarModel extends EntityModel<CthulhuFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer leftLeg;
    public ModelRenderer leftArm;
    public ModelRenderer tail;
    public ModelRenderer leftWing;
    public ModelRenderer rightLeg;
    public ModelRenderer rightArm;
    public ModelRenderer rightWing;
    public ModelRenderer hair;
    public ModelRenderer leftEye;
    public ModelRenderer rightEye;
    public ModelRenderer leftEar;
    public ModelRenderer tentacle1;
    public ModelRenderer tentacle2;
    public ModelRenderer tentacle3;
    public ModelRenderer rightEar;
    public ModelRenderer hat1;
    public ModelRenderer trunk1;
    public ModelRenderer hat2;
    public ModelRenderer trunk2;
    public ModelRenderer trunk3;
    public ModelRenderer lantern1;
    public ModelRenderer lantern2;
    public ModelRenderer lantern3;
    public DragonFamiliarModel.ColorModelRenderer lantern4;

    public CthulhuFamiliarModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 64;
        this.texHeight = 32;
        this.leftEye = new ModelRenderer(this, 26, 12);
        this.leftEye.setPos(1.3F, -3.0F, -3.7F);
        this.leftEye.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.tentacle3 = new ModelRenderer(this, 0, 17);
        this.tentacle3.setPos(0.1F, -1.1F, -2.8F);
        this.tentacle3.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.tentacle3, -0.19338247978939116F, 0.0F, -0.5759586531581287F);
        this.rightEar = new ModelRenderer(this, 0, 21);
        this.rightEar.mirror = true;
        this.rightEar.setPos(-3.0F, -4.5F, -1.0F);
        this.rightEar.addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 52, 0);
        this.leftArm.setPos(3.2F, -4.5F, 0.0F);
        this.leftArm.addBox(0.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftArm, -1.6891296287748234F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 22);
        this.tail.setPos(0.0F, -2.0F, 1.0F);
        this.tail.addBox(0.0F, -1.0F, 0.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.tail, 0.628493034348386F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 0, 21);
        this.leftEar.setPos(3.0F, -4.5F, -1.0F);
        this.leftEar.addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.lantern1 = new ModelRenderer(this, 58, 29);
        this.lantern1.setPos(1.0F, 3.5F, -0.5F);
        this.lantern1.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.lantern1, 1.2901473511162753F, 0.0F, 0.0F);
        this.leftWing = new ModelRenderer(this, 20, 21);
        this.leftWing.setPos(2.4F, -5.0F, 1.4F);
        this.leftWing.addBox(0.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftWing, -0.13142329633701952F, -0.4291764657285667F, 0.5256931853480781F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 17.0F, 0.0F);
        this.body.addBox(-3.5F, -6.0F, -1.5F, 7.0F, 7.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.body, 0.43912483713861217F, 0.0F, 0.0F);
        this.hat1 = new ModelRenderer(this, 20, 16);
        this.hat1.setPos(2.5F, -5.5F, -3.0F);
        this.hat1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.hat1, 0.27366763203903305F, -0.03909537541112055F, 0.23457224414434488F);
        this.hair = new ModelRenderer(this, 0, 0);
        this.hair.setPos(0.0F, -2.0F, 0.0F);
        this.hair.addBox(0.0F, -7.0F, -3.2F, 0.0F, 7.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.hat2 = new ModelRenderer(this, 36, 16);
        this.hat2.setPos(0.5F, -2.0F, 0.5F);
        this.hat2.addBox(-2.0F, 0.0F, -2.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.trunk3 = new ModelRenderer(this, 54, 7);
        this.trunk3.setPos(0.0F, 1.5F, 0.0F);
        this.trunk3.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.trunk3, 0.3909537457888271F, 0.0F, 0.0F);
        this.lantern2 = new ModelRenderer(this, 42, 23);
        this.lantern2.setPos(0.0F, 2.5F, 0.0F);
        this.lantern2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.lantern2, 0.0F, 0.7853981633974483F, 0.0F);
        this.tentacle2 = new ModelRenderer(this, 0, 17);
        this.tentacle2.setPos(0.0F, -1.4F, -2.8F);
        this.tentacle2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.tentacle2, -0.19338247978939116F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 52, 0);
        this.rightArm.mirror = true;
        this.rightArm.setPos(-3.2F, -4.5F, 0.0F);
        this.rightArm.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightArm, -0.43807764890847944F, 0.0F, 0.0F);
        this.tentacle1 = new ModelRenderer(this, 0, 17);
        this.tentacle1.setPos(-0.1F, -1.1F, -2.8F);
        this.tentacle1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.tentacle1, -0.19338247978939116F, 0.0F, 0.5759586531581287F);
        this.head = new ModelRenderer(this, 20, 0);
        this.head.setPos(0.0F, -5.8F, -0.3F);
        this.head.addBox(-3.0F, -5.0F, -3.2F, 6.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.head, -0.26284659267403904F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 44, 0);
        this.leftLeg.setPos(2.0F, 0.5F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftLeg, -0.39426988068868013F, 0.0F, 0.0F);
        this.lantern4 = new DragonFamiliarModel.ColorModelRenderer(this, 36, 26);
        this.lantern4.setPos(0.0F, -3.3F, 0.0F);
        this.lantern4.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.lantern4, 0.0F, 0.0F, 0.7853981633974483F);
        this.trunk2 = new ModelRenderer(this, 44, 11);
        this.trunk2.setPos(0.0F, 1.5F, 0.0F);
        this.trunk2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.trunk2, -0.46914448828868976F, 0.0F, 0.0F);
        this.rightWing = new ModelRenderer(this, 20, 21);
        this.rightWing.mirror = true;
        this.rightWing.setPos(-2.4F, -5.0F, 1.4F);
        this.rightWing.addBox(-8.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightWing, -0.13142329633701952F, 0.4291764657285667F, -0.5256931853480781F);
        this.rightEye = new ModelRenderer(this, 26, 12);
        this.rightEye.setPos(-1.3F, -3.0F, -3.7F);
        this.rightEye.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 44, 0);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(-2.0F, 0.5F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightLeg, -0.39426988068868013F, 0.0F, 0.0F);
        this.trunk1 = new ModelRenderer(this, 44, 7);
        this.trunk1.setPos(0.0F, -1.1F, -2.2F);
        this.trunk1.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.trunk1, -0.8600982340775168F, 0.0F, 0.0F);
        this.lantern3 = new ModelRenderer(this, 34, 27);
        this.lantern3.setPos(0.0F, 4.99F, 0.0F);
        this.lantern3.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.lantern3, 0.0F, -0.7853981633974483F, 0.0F);
        this.head.addChild(this.leftEye);
        this.head.addChild(this.tentacle3);
        this.head.addChild(this.rightEar);
        this.body.addChild(this.leftArm);
        this.body.addChild(this.tail);
        this.head.addChild(this.leftEar);
        this.leftArm.addChild(this.lantern1);
        this.body.addChild(this.leftWing);
        this.head.addChild(this.hat1);
        this.head.addChild(this.hair);
        this.hat1.addChild(this.hat2);
        this.trunk2.addChild(this.trunk3);
        this.lantern1.addChild(this.lantern2);
        this.head.addChild(this.tentacle2);
        this.body.addChild(this.rightArm);
        this.head.addChild(this.tentacle1);
        this.body.addChild(this.head);
        this.body.addChild(this.leftLeg);
        this.lantern3.addChild(this.lantern4);
        this.trunk1.addChild(this.trunk2);
        this.body.addChild(this.rightWing);
        this.head.addChild(this.rightEye);
        this.body.addChild(this.rightLeg);
        this.head.addChild(this.trunk1);
        this.lantern2.addChild(this.lantern3);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(CthulhuFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.showModels(entityIn);
        this.lantern4.setColor(1, 1, 1, (MathHelper.cos(ageInTicks * 0.2f) + 1) * 0.5f);

        this.head.yRot = netHeadYaw * (PI / 180f) * 0.7f;
        this.head.xRot = headPitch * (PI / 180f) * 0.7f - 0.26f;
        
        this.lantern1.yRot = 0;
        this.lantern1.zRot = 0;

        if (entityIn.isPartying()) {
            this.body.xRot = -this.toRads(90);
            this.rightLeg.xRot = this.toRads(15);
            this.leftLeg.xRot = this.toRads(15);
            this.head.yRot = 0;
            this.head.xRot = 0;
        } else if (entityIn.isSitting()) {
            this.rightArm.xRot = 0;
            this.leftArm.xRot = 0;
            this.rightLeg.xRot = -PI / 2;
            this.leftLeg.xRot = -PI / 2;
            this.body.xRot = 0;
        } else {
            this.rightArm.xRot = MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f - 0.44f;
            this.leftArm.xRot = MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f - 0.44f;
            this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount * 0.2f - 0.39f;
            this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount * 0.2f - 0.39f;
            this.body.xRot = entityIn.isInWater() ? 1 : 0.44f;
        }

        if (entityIn.isAngry()) {
            this.leftEye.zRot = -this.toRads(45);
            this.rightEye.zRot = -this.toRads(45);
            this.leftEar.zRot = this.toRads(20);
            this.rightEar.zRot = -this.toRads(20);
        } else {
            this.leftEye.zRot = 0;
            this.rightEye.zRot = 0;
            this.leftEar.zRot = 0;
            this.rightEar.zRot = 0;
        }

        if (entityIn.isGiving()) {
            this.leftArm.yRot = this.toRads(40);
            this.rightArm.yRot = -this.toRads(40);
            this.leftArm.xRot -= this.toRads(40);
            this.rightArm.xRot -= this.toRads(40);
        } else {
            this.leftArm.yRot = 0;
            this.rightArm.yRot = 0;
        }

        this.trunk1.xRot = -0.86f + MathHelper.cos(ageInTicks / 10) * 0.15f;
        this.trunk2.xRot = -0.47f + MathHelper.cos(ageInTicks / 10) * 0.15f;
        this.trunk3.xRot = 0.39f + MathHelper.cos(ageInTicks / 10) * 0.15f;
        this.tentacle1.zRot = 0.58f + MathHelper.cos(ageInTicks / 10) * 0.07f;
        this.tentacle3.zRot = -0.58f - MathHelper.cos(ageInTicks / 10) * 0.07f;

        if (entityIn.hasBlacksmithUpgrade()) {
            if (entityIn.isSitting()) {
                this.lantern1.xRot = this.toRads(0);
                this.lantern1.y = -1.6f;
                this.lantern1.x = 3f;
                this.lantern1.z = -5f;
            } else {
                this.leftArm.xRot = MathHelper.cos(limbSwing * 0.4f + PI) * limbSwingAmount * 0.2f + this.toRads(-100);
                this.lantern1.yRot = MathHelper.cos(ageInTicks * 0.2f) * this.toRads(5);
                this.lantern1.zRot = -MathHelper.cos(ageInTicks * 0.2f) * this.toRads(5);
                this.lantern1.xRot = this.toRads(74);
                this.lantern1.y = 3.5f;
                this.lantern1.x = 1f;
                this.lantern1.z = -0.5f;
            }
        }
    }

    @Override
    public void prepareMobModel(CthulhuFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
        if (entityIn.isSitting() && !entityIn.isPartying()) {
            this.leftWing.yRot = -0.43f;
            this.rightWing.yRot = 0.43f;
        } else {
            float animationHeight = entityIn.getAnimationHeight(partialTick);
            this.leftWing.yRot = animationHeight * this.toRads(20) - 0.43f;
            this.rightWing.yRot = -animationHeight * this.toRads(20) + 0.43f;
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(CthulhuFamiliarEntity entity) {
        boolean hasTrunk = entity.hasTrunk();

        this.hat1.visible = entity.hasHat();
        this.lantern1.visible = entity.hasBlacksmithUpgrade();
        this.trunk1.visible = hasTrunk;
        this.tentacle1.visible = !hasTrunk;
        this.tentacle2.visible = !hasTrunk;
        this.tentacle3.visible = !hasTrunk;
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
