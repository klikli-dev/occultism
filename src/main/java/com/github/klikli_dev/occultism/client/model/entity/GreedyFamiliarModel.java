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

import com.github.klikli_dev.occultism.common.entity.GreedyFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class GreedyFamiliarModel extends EntityModel<GreedyFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer chest1;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer head;
    public ModelRenderer chest2;
    public ModelRenderer monster;
    public ModelRenderer monsterLeftEye;
    public ModelRenderer monsterRightEye;
    public ModelRenderer monsterLeftEar;
    public ModelRenderer monsterRightEar;
    public ModelRenderer leftEar;
    public ModelRenderer rightEar;
    public ModelRenderer nose;

    public GreedyFamiliarModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        this.body = new ModelRenderer(this, 0, 10);
        this.body.setPos(0.0F, 19.0F, 0.6F);
        this.body.addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, -5.0F, 0.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 16, 21);
        this.rightArm.mirror = true;
        this.rightArm.setPos(-2.5F, -5.0F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 0, 0);
        this.leftEar.setPos(2.0F, -4.5F, 0.0F);
        this.leftEar.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, 0.0F, 0.0F, 0.5235987755982988F);
        this.chest2 = new ModelRenderer(this, 0, 18);
        this.chest2.setPos(0.0F, -3.0F, 0.0F);
        this.chest2.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(chest2, 1.0555751236166873F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 8, 24);
        this.leftLeg.setPos(1.5F, 0.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.monster = new ModelRenderer(this, 15, 0);
        this.monster.setPos(0.0F, -2.0F, 1.5F);
        this.monster.addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(monster, 0.1563815016444822F, -0.11728612207217244F, 0.0F);
        this.monsterRightEye = new ModelRenderer(this, 21, 0);
        this.monsterRightEye.mirror = true;
        this.monsterRightEye.setPos(0.8F, -1.8F, 1.8F);
        this.monsterRightEye.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 0, 0);
        this.rightEar.mirror = true;
        this.rightEar.setPos(-2.0F, -4.5F, 0.0F);
        this.rightEar.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, 0.0F, 0.0F, -0.5235987755982988F);
        this.chest1 = new ModelRenderer(this, 12, 14);
        this.chest1.setPos(0.0F, 0.5F, 1.5F);
        this.chest1.addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.monsterLeftEar = new ModelRenderer(this, 24, 0);
        this.monsterLeftEar.setPos(-0.6F, -1.6F, 0.3F);
        this.monsterLeftEar.addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(monsterLeftEar, -0.3127630032889644F, 0.0781907508222411F, -0.35185837453889574F);
        this.rightLeg = new ModelRenderer(this, 8, 24);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(-1.5F, 0.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.monsterRightEar = new ModelRenderer(this, 24, 0);
        this.monsterRightEar.setPos(0.6F, -1.6F, 0.3F);
        this.monsterRightEar.addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(monsterRightEar, -0.3127630032889644F, 0.0781907508222411F, 0.35185837453889574F);
        this.nose = new ModelRenderer(this, 18, 8);
        this.nose.setPos(0.0F, -3.0F, -2.5F);
        this.nose.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 16, 21);
        this.leftArm.setPos(2.5F, -5.0F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.monsterLeftEye = new ModelRenderer(this, 21, 0);
        this.monsterLeftEye.setPos(-0.8F, -1.8F, 1.8F);
        this.monsterLeftEye.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.head);
        this.body.addChild(this.rightArm);
        this.head.addChild(this.leftEar);
        this.chest1.addChild(this.chest2);
        this.body.addChild(this.leftLeg);
        this.chest1.addChild(this.monster);
        this.monster.addChild(this.monsterRightEye);
        this.head.addChild(this.rightEar);
        this.body.addChild(this.chest1);
        this.monster.addChild(this.monsterLeftEar);
        this.body.addChild(this.rightLeg);
        this.monster.addChild(this.monsterRightEar);
        this.head.addChild(this.nose);
        this.body.addChild(this.leftArm);
        this.monster.addChild(this.monsterLeftEye);
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

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    @Override
    public void prepareMobModel(GreedyFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
            float pPartialTick) {
        this.leftEar.zRot = -pEntity.getEarRotZ(pPartialTick);
        this.rightEar.zRot = pEntity.getEarRotZ(pPartialTick);
        this.leftEar.xRot = pEntity.getEarRotX(pPartialTick);
        this.rightEar.xRot = pEntity.getEarRotX(pPartialTick);

        float ageInTicks = pEntity.tickCount + pPartialTick;

        if (!pEntity.isPartying()) {
            this.chest2.xRot = pEntity.getLidRot(pPartialTick);
            this.monster.y = -0.2f - pEntity.getLidRot(pPartialTick) * 3;
            this.monster.yRot = pEntity.getMonsterRot(pPartialTick);
            this.monster.xRot = 0;
        } else {
            this.chest2.xRot = toRad(40);
            this.monster.y = -2.5f;
            this.monster.yRot = 0;
            this.monster.xRot = MathHelper.cos(ageInTicks) * this.toRad(15);
        }
    }

    @Override
    public void setupAnim(GreedyFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * (PI / 180f);
        this.head.xRot = headPitch * (PI / 180f);
        this.head.zRot = 0;
        this.rightArm.zRot = 0;
        this.leftArm.zRot = 0;

        if (entityIn.isPartying()) {
            this.rightArm.xRot = MathHelper.cos(ageInTicks + PI) * this.toRad(20) + this.toRad(180);
            this.leftArm.xRot = MathHelper.cos(ageInTicks) * this.toRad(20) + this.toRad(180);
            this.rightArm.zRot = -this.toRad(20);
            this.leftArm.zRot = this.toRad(20);
            this.head.zRot = MathHelper.sin(ageInTicks) * this.toRad(20);
            if (entityIn.getVehicle() == null) {
                this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount;
                this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.5f + PI) * 1.4f * limbSwingAmount;
            } else {
                this.rightLeg.xRot = -PI / 2;
                this.leftLeg.xRot = -PI / 2;
            }
        } else if (entityIn.isSitting() || entityIn.getVehicle() != null) {
            this.rightArm.xRot = 0;
            this.leftArm.xRot = 0;
            this.rightLeg.xRot = -PI / 2;
            this.leftLeg.xRot = -PI / 2;
        } else {
            this.rightArm.xRot = MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount;
            this.leftArm.xRot = MathHelper.cos(limbSwing * 0.5f) * limbSwingAmount;
            this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount;
            this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.5f + PI) * 1.4f * limbSwingAmount;
        }

        this.chest1.zRot = MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f;

        if (entityIn.getTargetBlock().isPresent())
            this.rightArm.xRot = -this.toRad(100) + MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount;
    }
}
