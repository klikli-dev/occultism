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

/**
 * Created using Tabula 8.0.0
 */
public class BlacksmithFamiliarModel extends EntityModel<BlacksmithFamiliarEntity> {
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
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.hammer2 = new ModelRenderer(this, 14, 6);
        this.hammer2.mirror = true;
        this.hammer2.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.hammer2.addBox(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.mouth1 = new ModelRenderer(this, 8, 26);
        this.mouth1.setRotationPoint(0.0F, -1.1F, -3.2F);
        this.mouth1.addBox(-3.5F, -2.0F, 0.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.anvil6 = new ModelRenderer(this, 16, 19);
        this.anvil6.setRotationPoint(2.5F, -0.51F, 0.5F);
        this.anvil6.addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(anvil6, 0.0F, 0.2617993877991494F, 0.0F);
        this.hammer1 = new ModelRenderer(this, 16, 0);
        this.hammer1.mirror = true;
        this.hammer1.setRotationPoint(-1.0F, 5.0F, 0.0F);
        this.hammer1.addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(hammer1, 0.6255260065779288F, 1.7983872772339238F, 0.0F);
        this.wheel2 = new ModelRenderer(this, 34, 14);
        this.wheel2.setRotationPoint(3.5F, 0.5F, -2.5F);
        this.wheel2.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.anvil5 = new ModelRenderer(this, 36, 18);
        this.anvil5.setRotationPoint(-4.0F, -0.5F, 0.0F);
        this.anvil5.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 24, 0);
        this.leftArm.setRotationPoint(2.5F, -5.0F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wheel4 = new ModelRenderer(this, 34, 14);
        this.wheel4.mirror = true;
        this.wheel4.setRotationPoint(-3.5F, 0.5F, 2.5F);
        this.wheel4.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 40, 0);
        this.leftLeg.setRotationPoint(1.5F, 0.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 0, 26);
        this.leftEar.setRotationPoint(2.4F, -3.0F, 0.0F);
        this.leftEar.addBox(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, 0.22689280275926282F, -0.8726646259971648F, -0.5235987755982988F);
        this.earring = new ModelRenderer(this, 0, 29);
        this.earring.setRotationPoint(1.4F, 1.0F, 0.0F);
        this.earring.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earring, 0.0F, 2.3917990897539414F, 0.0F);
        this.hair2 = new ModelRenderer(this, 42, 21);
        this.hair2.setRotationPoint(0.0F, -3.0F, 1.0F);
        this.hair2.addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.anvil4 = new ModelRenderer(this, 0, 19);
        this.anvil4.setRotationPoint(0.0F, -3.6F, 0.0F);
        this.anvil4.addBox(-3.0F, -1.5F, -2.0F, 6.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.anvil1 = new ModelRenderer(this, 42, 12);
        this.anvil1.mirror = true;
        this.anvil1.setRotationPoint(-2.0F, -1.0F, 0.0F);
        this.anvil1.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.mouth2 = new ModelRenderer(this, 22, 26);
        this.mouth2.setRotationPoint(0.0F, -1.1F, -3.2F);
        this.mouth2.addBox(-3.5F, -2.0F, 0.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 24, 0);
        this.rightArm.mirror = true;
        this.rightArm.setRotationPoint(-2.5F, -5.0F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wagon = new ModelRenderer(this, 14, 12);
        this.wagon.setRotationPoint(0.0F, 3.5F, -5.0F);
        this.wagon.addBox(-3.5F, 0.0F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 43, 2);
        this.head.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.wheel3 = new ModelRenderer(this, 34, 14);
        this.wheel3.mirror = true;
        this.wheel3.setRotationPoint(-3.5F, 0.5F, -2.5F);
        this.wheel3.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 19.0F, 3.0F);
        this.body.addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.hair1 = new ModelRenderer(this, 0, 8);
        this.hair1.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.hair1.addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.anvil7 = new ModelRenderer(this, 16, 19);
        this.anvil7.setRotationPoint(2.5F, -0.5F, -0.5F);
        this.anvil7.addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(anvil7, 0.0F, -0.27366763203903305F, 0.0F);
        this.wheel1 = new ModelRenderer(this, 34, 14);
        this.wheel1.setRotationPoint(3.5F, 0.5F, 2.5F);
        this.wheel1.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 40, 0);
        this.rightLeg.mirror = true;
        this.rightLeg.setRotationPoint(-1.5F, 0.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.anvil3 = new ModelRenderer(this, 51, 15);
        this.anvil3.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.anvil3.addBox(-1.0F, -2.0F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 0, 26);
        this.rightEar.mirror = true;
        this.rightEar.setRotationPoint(-2.4F, -3.0F, 0.0F);
        this.rightEar.addBox(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, 0.22689280275926282F, 0.8726646259971648F, 0.5235987755982988F);
        this.nose = new ModelRenderer(this, 30, 7);
        this.nose.setRotationPoint(0.0F, -3.2F, -1.5F);
        this.nose.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(nose, -0.23457224414434488F, 0.0F, 0.0F);
        this.anvil2 = new ModelRenderer(this, 42, 12);
        this.anvil2.setRotationPoint(2.0F, -1.0F, 0.0F);
        this.anvil2.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.hammer1.addChild(this.hammer2);
        this.head.addChild(this.mouth1);
        this.anvil4.addChild(this.anvil6);
        this.rightArm.addChild(this.hammer1);
        this.wagon.addChild(this.wheel2);
        this.anvil4.addChild(this.anvil5);
        this.body.addChild(this.leftArm);
        this.wagon.addChild(this.wheel4);
        this.body.addChild(this.leftLeg);
        this.head.addChild(this.leftEar);
        this.leftEar.addChild(this.earring);
        this.head.addChild(this.hair2);
        this.anvil3.addChild(this.anvil4);
        this.wagon.addChild(this.anvil1);
        this.head.addChild(this.mouth2);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.wagon);
        this.body.addChild(this.head);
        this.wagon.addChild(this.wheel3);
        this.head.addChild(this.hair1);
        this.anvil4.addChild(this.anvil7);
        this.wagon.addChild(this.wheel1);
        this.body.addChild(this.rightLeg);
        this.wagon.addChild(this.anvil3);
        this.head.addChild(this.rightEar);
        this.head.addChild(this.nose);
        this.wagon.addChild(this.anvil2);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(BlacksmithFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void setLivingAnimations(BlacksmithFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
        showModels(entityIn);
    }

    private void showModels(BlacksmithFamiliarEntity entityIn) {
        boolean hasSquarehair = entityIn.hasSquareHair();
        boolean hasMarioMoustache = entityIn.hasMarioMoustache();

        this.earring.showModel = entityIn.hasEarring();
        this.mouth1.showModel = !hasMarioMoustache;
        this.mouth2.showModel = hasMarioMoustache;
        this.hair1.showModel = hasSquarehair;
        this.hair2.showModel = !hasSquarehair;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
