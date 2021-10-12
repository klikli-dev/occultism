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

import com.github.klikli_dev.occultism.common.entity.BlacksmithFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BlacksmithFamiliarModel extends EntityModel<BlacksmithFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer head;
    public ModelRenderer wagon;
    public ModelRenderer hammer1;
    public ModelRenderer hammer2;
    public ModelRenderer nose;
    public ModelRenderer mouth1;
    public ModelRenderer leftEar;
    public ModelRenderer hair1;
    public ModelRenderer rightEar;
    public ModelRenderer hair2;
    public ModelRenderer mouth2;
    public ModelRenderer earring;
    public ModelRenderer anvil1;
    public ModelRenderer anvil2;
    public ModelRenderer anvil3;
    public ModelRenderer wheel1;
    public ModelRenderer wheel2;
    public ModelRenderer wheel3;
    public ModelRenderer wheel4;
    public ModelRenderer anvil4;
    public ModelRenderer anvil5;
    public ModelRenderer anvil6;
    public ModelRenderer anvil7;

    public BlacksmithFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.anvil6 = new ModelRenderer(this, 16, 19);
        this.anvil6.setPos(2.5F, -0.51F, 0.5F);
        this.anvil6.addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.anvil6, 0.0F, 0.2617993877991494F, 0.0F);
        this.anvil1 = new ModelRenderer(this, 42, 12);
        this.anvil1.mirror = true;
        this.anvil1.setPos(-2.0F, -1.0F, 0.0F);
        this.anvil1.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 24, 0);
        this.leftArm.setPos(2.5F, -5.0F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.hair2 = new ModelRenderer(this, 42, 21);
        this.hair2.setPos(0.0F, -3.0F, 1.0F);
        this.hair2.addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.wheel2 = new ModelRenderer(this, 34, 14);
        this.wheel2.setPos(3.5F, 0.5F, -2.5F);
        this.wheel2.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.hammer2 = new ModelRenderer(this, 14, 6);
        this.hammer2.mirror = true;
        this.hammer2.setPos(0.0F, 0.0F, -4.0F);
        this.hammer2.addBox(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wheel3 = new ModelRenderer(this, 34, 14);
        this.wheel3.mirror = true;
        this.wheel3.setPos(-3.5F, 0.5F, -2.5F);
        this.wheel3.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 19.0F, 3.0F);
        this.body.addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 30, 7);
        this.nose.setPos(0.0F, -3.2F, -1.5F);
        this.nose.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.nose, -0.23457224414434488F, 0.0F, 0.0F);
        this.mouth1 = new ModelRenderer(this, 8, 26);
        this.mouth1.setPos(0.0F, -1.1F, -3.2F);
        this.mouth1.addBox(-3.5F, -2.0F, 0.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 24, 0);
        this.rightArm.mirror = true;
        this.rightArm.setPos(-2.5F, -5.0F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.anvil2 = new ModelRenderer(this, 42, 12);
        this.anvil2.setPos(2.0F, -1.0F, 0.0F);
        this.anvil2.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.anvil5 = new ModelRenderer(this, 36, 18);
        this.anvil5.setPos(-4.0F, -0.5F, 0.0F);
        this.anvil5.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 40, 0);
        this.leftLeg.setPos(1.5F, 0.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.anvil4 = new ModelRenderer(this, 0, 19);
        this.anvil4.setPos(0.0F, -3.5F, 0.0F);
        this.anvil4.addBox(-3.0F, -1.5F, -2.0F, 6.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.anvil7 = new ModelRenderer(this, 16, 19);
        this.anvil7.setPos(2.5F, -0.5F, -0.5F);
        this.anvil7.addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.anvil7, 0.0F, -0.27366763203903305F, 0.0F);
        this.wheel4 = new ModelRenderer(this, 34, 14);
        this.wheel4.mirror = true;
        this.wheel4.setPos(-3.5F, 0.5F, 2.5F);
        this.wheel4.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.hair1 = new ModelRenderer(this, 0, 8);
        this.hair1.setPos(0.0F, -5.0F, 0.0F);
        this.hair1.addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 0, 26);
        this.leftEar.setPos(2.4F, -3.0F, 0.0F);
        this.leftEar.addBox(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.leftEar, 0.22689280275926282F, -0.8726646259971648F, -0.5235987755982988F);
        this.wagon = new ModelRenderer(this, 14, 12);
        this.wagon.setPos(0.0F, 22.5F, -2.0F);
        this.wagon.addBox(-3.5F, 0.0F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 40, 0);
        this.rightLeg.mirror = true;
        this.rightLeg.setPos(-1.5F, 0.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 43, 2);
        this.head.setPos(0.0F, -5.0F, 0.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.earring = new ModelRenderer(this, 0, 29);
        this.earring.setPos(1.4F, 1.0F, 0.0F);
        this.earring.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.earring, 0.0F, 2.3917990897539414F, 0.0F);
        this.hammer1 = new ModelRenderer(this, 16, 0);
        this.hammer1.mirror = true;
        this.hammer1.setPos(-1.0F, 5.0F, 0.0F);
        this.hammer1.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.hammer1, 0.6255260065779288F, 1.7983872772339238F, 0.0F);
        this.rightEar = new ModelRenderer(this, 0, 26);
        this.rightEar.mirror = true;
        this.rightEar.setPos(-2.4F, -3.0F, 0.0F);
        this.rightEar.addBox(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(this.rightEar, 0.22689280275926282F, 0.8726646259971648F, 0.5235987755982988F);
        this.wheel1 = new ModelRenderer(this, 34, 14);
        this.wheel1.setPos(3.5F, 0.5F, 2.5F);
        this.wheel1.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.anvil3 = new ModelRenderer(this, 51, 15);
        this.anvil3.setPos(0.0F, -1.0F, 0.0F);
        this.anvil3.addBox(-1.0F, -2.0F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.mouth2 = new ModelRenderer(this, 22, 26);
        this.mouth2.setPos(0.0F, -1.1F, -3.2F);
        this.mouth2.addBox(-3.5F, -2.0F, 0.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.anvil4.addChild(this.anvil6);
        this.wagon.addChild(this.anvil1);
        this.body.addChild(this.leftArm);
        this.head.addChild(this.hair2);
        this.wagon.addChild(this.wheel2);
        this.hammer1.addChild(this.hammer2);
        this.wagon.addChild(this.wheel3);
        this.head.addChild(this.nose);
        this.head.addChild(this.mouth1);
        this.body.addChild(this.rightArm);
        this.wagon.addChild(this.anvil2);
        this.anvil4.addChild(this.anvil5);
        this.body.addChild(this.leftLeg);
        this.anvil3.addChild(this.anvil4);
        this.anvil4.addChild(this.anvil7);
        this.wagon.addChild(this.wheel4);
        this.head.addChild(this.hair1);
        this.head.addChild(this.leftEar);
        this.body.addChild(this.rightLeg);
        this.body.addChild(this.head);
        this.leftEar.addChild(this.earring);
        this.rightArm.addChild(this.hammer1);
        this.head.addChild(this.rightEar);
        this.wagon.addChild(this.wheel1);
        this.wagon.addChild(this.anvil3);
        this.head.addChild(this.mouth2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body, this.wagon).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(BlacksmithFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * (PI / 180f);
        this.head.xRot = headPitch * (PI / 180f);

        this.wheel1.xRot = limbSwing;
        this.wheel2.xRot = limbSwing;
        this.wheel3.xRot = limbSwing;
        this.wheel4.xRot = limbSwing;

        this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.5f) * limbSwingAmount * 0.6f;
        this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.6f;

        this.leftArm.xRot = this.toRad(-30) + MathHelper.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f;
        this.leftArm.yRot = this.toRad(-15);
        this.rightArm.xRot = this.toRad(-75);
        this.rightArm.yRot = this.toRad(25);
        this.hammer1.yRot = 0;
        this.hammer1.zRot = this.toRad(90);
        this.hammer1.xRot = 0;
        
        this.body.yRot = 0;
        this.body.y = 19;
        this.body.xRot = 0;
        this.body.z = 3;
        this.leftArm.zRot = 0;
        this.rightArm.zRot = 0;

        if (entityIn.isPartying()) {
            this.head.xRot = MathHelper.cos(ageInTicks * 0.8f) * this.toRad(25);
            this.head.yRot = 0;
            this.leftArm.xRot = this.toRad(-90) + MathHelper.cos(ageInTicks * 1.2f) * this.toRad(25);
            this.leftArm.yRot = this.toRad(-5);
            this.leftArm.zRot = this.toRad(20);
            this.rightArm.xRot = this.toRad(-90) + MathHelper.cos(ageInTicks * 1.2f + PI) * this.toRad(25);
            this.rightArm.yRot = this.toRad(-5);
            this.rightArm.zRot = this.toRad(-20);
        } else if (entityIn.isSitting()) {
            this.body.yRot = this.toRad(180);
            this.leftLeg.xRot = this.toRad(-70);
            this.rightLeg.xRot = this.toRad(-70);
            this.body.y = 23f;
            this.body.z = 4;
            this.body.xRot = this.toRad(-20);
            this.head.xRot = this.toRad(20);
            this.head.yRot = 0;
            this.leftArm.xRot = this.toRad(-10);
            this.rightArm.xRot = this.toRad(-10);
        }

    }

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    @Override
    public void prepareMobModel(BlacksmithFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
        this.showModels(entityIn);
    }

    private void showModels(BlacksmithFamiliarEntity entityIn) {
        boolean hasSquarehair = entityIn.hasSquareHair();
        boolean hasMarioMoustache = entityIn.hasMarioMoustache();

        this.earring.visible = entityIn.hasEarring();
        this.mouth1.visible = !hasMarioMoustache;
        this.mouth2.visible = hasMarioMoustache;
        this.hair1.visible = hasSquarehair;
        this.hair2.visible = !hasSquarehair;
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
