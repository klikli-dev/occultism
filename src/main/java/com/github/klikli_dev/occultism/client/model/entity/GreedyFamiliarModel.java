package com.github.klikli_dev.occultism.client.model.entity;

import com.github.klikli_dev.occultism.common.entity.GreedyFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class GreedyFamiliarModel extends EntityModel<GreedyFamiliarEntity> {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leftEar;
    public ModelRenderer rightEar;
    public ModelRenderer nose;
    public ModelRenderer rightArm;
    public ModelRenderer chest1;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer chest2;

    public GreedyFamiliarModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 10);
        this.body.setRotationPoint(0.0F, 5.0F, 0.6F);
        this.body.addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 8, 24);
        this.leftLeg.setRotationPoint(1.5F, 0.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 18, 8);
        this.nose.setRotationPoint(0.0F, -3.0F, -2.5F);
        this.nose.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.chest1 = new ModelRenderer(this, 12, 14);
        this.chest1.setRotationPoint(0.0F, 0.5F, 1.5F);
        this.chest1.addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 16, 21);
        this.rightArm.mirror = true;
        this.rightArm.setRotationPoint(-2.5F, -5.0F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 0, 0);
        this.rightEar.mirror = true;
        this.rightEar.setRotationPoint(-2.0F, -4.5F, 0.0F);
        this.rightEar.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, 0.0F, 0.0F, -0.5235987755982988F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.head.addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 8, 24);
        this.rightLeg.mirror = true;
        this.rightLeg.setRotationPoint(-1.5F, 0.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.chest2 = new ModelRenderer(this, 0, 18);
        this.chest2.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.chest2.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(chest2, 0.23457224414434488F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 0, 0);
        this.leftEar.setRotationPoint(2.0F, -4.5F, 0.0F);
        this.leftEar.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, 0.0F, 0.0F, 0.5235987755982988F);
        this.leftArm = new ModelRenderer(this, 16, 21);
        this.leftArm.setRotationPoint(2.5F, -5.0F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head.addChild(this.body);
        this.body.addChild(this.leftLeg);
        this.head.addChild(this.nose);
        this.body.addChild(this.chest1);
        this.body.addChild(this.rightArm);
        this.head.addChild(this.rightEar);
        this.body.addChild(this.rightLeg);
        this.chest1.addChild(this.chest2);
        this.head.addChild(this.leftEar);
        this.body.addChild(this.leftArm);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.head).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(GreedyFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
