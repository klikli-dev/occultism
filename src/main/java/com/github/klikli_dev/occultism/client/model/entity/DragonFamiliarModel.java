package com.github.klikli_dev.occultism.client.model.entity;

import com.github.klikli_dev.occultism.common.entity.DragonFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class DragonFamiliarModel extends EntityModel<DragonFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body;
    public ModelRenderer neck1;
    public ModelRenderer leftLeg1;
    public ModelRenderer tail1;
    public ModelRenderer leftWing1;
    public ModelRenderer rightWing1;
    public ModelRenderer rightLeg1;
    public ModelRenderer leftArm1;
    public ModelRenderer rightArm1;
    public ModelRenderer neck2;
    public ModelRenderer head;
    public ModelRenderer jaw;
    public ColorModelRenderer leftEye;
    public ColorModelRenderer rightEye;
    public ModelRenderer fez1;
    public ModelRenderer leftHorn1;
    public ModelRenderer leftEar;
    public ModelRenderer rightEar;
    public ModelRenderer rightHorn1;
    public ModelRenderer tooth1;
    public ModelRenderer tooth2;
    public ModelRenderer tooth4;
    public ModelRenderer leftNose;
    public ModelRenderer rightNose;
    public ModelRenderer fez2;
    public ModelRenderer leftHorn2;
    public ModelRenderer rightHorn2;
    public ModelRenderer leftLeg2;
    public ModelRenderer leftLeg3;
    public ModelRenderer tail2;
    public ModelRenderer spike1;
    public ModelRenderer tail3;
    public ModelRenderer spike2;
    public ModelRenderer spike3;
    public ModelRenderer leftWing2;
    public ModelRenderer rightWing2;
    public ModelRenderer rightLeg2;
    public ModelRenderer rightLeg3;
    public ModelRenderer leftArm2;
    public ModelRenderer leftArm3;
    public ModelRenderer rightArm2;
    public ModelRenderer rightArm3;

    public DragonFamiliarModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leftHorn1 = new ModelRenderer(this, 0, 14);
        this.leftHorn1.setRotationPoint(1.7F, -0.5F, -0.5F);
        this.leftHorn1.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn1, 0.22689280275926282F, 0.0F, 0.0F);
        this.fez2 = new ModelRenderer(this, 24, 13);
        this.fez2.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.fez2.addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm3 = new ModelRenderer(this, 0, 8);
        this.rightArm3.setRotationPoint(0.1F, 2.1F, 0.0F);
        this.rightArm3.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm3, 0.0F, 0.0F, -0.7853981633974483F);
        this.rightNose = new ModelRenderer(this, 45, 1);
        this.rightNose.setRotationPoint(0.8F, -1.4F, -2.6F);
        this.rightNose.addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg3 = new ModelRenderer(this, 28, 7);
        this.leftLeg3.setRotationPoint(0.0F, 2.2F, 0.8F);
        this.leftLeg3.addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg3, 0.3909537457888271F, 0.0F, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 44, 5);
        this.leftLeg1.setRotationPoint(1.8F, 0.5F, 2.0F);
        this.leftLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, 0.4300491170387584F, 0.0F, 0.0F);
        this.rightArm2 = new ModelRenderer(this, 0, 8);
        this.rightArm2.setRotationPoint(-0.1F, 2.1F, 0.0F);
        this.rightArm2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm2, 0.0F, 0.0F, 0.7853981633974483F);
        this.rightEar = new ModelRenderer(this, 12, 14);
        this.rightEar.mirror = true;
        this.rightEar.setRotationPoint(-1.7F, -0.6F, -0.6F);
        this.rightEar.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, -0.13613568498450906F, -1.1798425477165557F, 0.5899212738582779F);
        this.rightArm1 = new ModelRenderer(this, 0, 5);
        this.rightArm1.setRotationPoint(-1.6F, 1.1F, -4.0F);
        this.rightArm1.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 30, 0);
        this.head.setRotationPoint(0.0F, -0.4F, -2.4F);
        this.head.addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.8213519699569813F, 0.0F, 0.0F);
        this.leftEye = new ColorModelRenderer(this, 56, 3);
        this.leftEye.setRotationPoint(1.5F, -0.1F, -2.4F);
        this.leftEye.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.body.addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.06981317007977318F, 0.0F, 0.0F);
        this.leftArm3 = new ModelRenderer(this, 0, 8);
        this.leftArm3.setRotationPoint(0.1F, 2.1F, 0.0F);
        this.leftArm3.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm3, 0.0F, 0.0F, -0.7853981633974483F);
        this.tail2 = new ModelRenderer(this, 50, 10);
        this.tail2.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.tail2.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail2, -0.23457224414434488F, 0.0F, 0.0F);
        this.spike1 = new ModelRenderer(this, 0, 25);
        this.spike1.setRotationPoint(0.0F, -4.5F, 0.0F);
        this.spike1.addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.tail3 = new ModelRenderer(this, 18, 6);
        this.tail3.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.tail3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail3, 0.19547687289441354F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 46, 0);
        this.jaw.setRotationPoint(0.0F, 0.2F, -4.0F);
        this.jaw.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn1 = new ModelRenderer(this, 0, 14);
        this.rightHorn1.mirror = true;
        this.rightHorn1.setRotationPoint(-1.7F, -0.5F, -0.5F);
        this.rightHorn1.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn1, 0.22689280275926282F, 0.0F, 0.0F);
        this.rightLeg2 = new ModelRenderer(this, 50, 5);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setRotationPoint(-0.01F, 2.4F, 0.5F);
        this.rightLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, -0.7428121536172364F, 0.0F, 0.0F);
        this.fez1 = new ModelRenderer(this, 18, 14);
        this.fez1.setRotationPoint(0.0F, -1.5F, -2.0F);
        this.fez1.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.neck1 = new ModelRenderer(this, 18, 0);
        this.neck1.setRotationPoint(0.0F, 0.2F, -4.1F);
        this.neck1.addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck1, -0.5473352640780661F, 0.0F, 0.0F);
        this.spike2 = new ModelRenderer(this, 0, 25);
        this.spike2.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.spike2.addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar = new ModelRenderer(this, 12, 14);
        this.leftEar.setRotationPoint(1.7F, -0.6F, -0.6F);
        this.leftEar.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, -0.13613568498450906F, 1.1798425477165557F, -0.5899212738582779F);
        this.neck2 = new ModelRenderer(this, 0, 0);
        this.neck2.setRotationPoint(0.0F, 0.0F, -2.4F);
        this.neck2.addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck2, -0.1563815016444822F, 0.0F, 0.0F);
        this.leftWing2 = new ModelRenderer(this, 0, 14);
        this.leftWing2.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.leftWing2.addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing2, 0.0F, 0.0F, 0.8600982340775168F);
        this.leftWing1 = new ModelRenderer(this, 0, 9);
        this.leftWing1.setRotationPoint(2.0F, 0.0F, -2.0F);
        this.leftWing1.addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing1, 0.0F, 0.0F, 0.7504915783575618F);
        this.leftArm2 = new ModelRenderer(this, 0, 8);
        this.leftArm2.setRotationPoint(-0.1F, 2.1F, 0.0F);
        this.leftArm2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm2, 0.0F, 0.0F, 0.7853981633974483F);
        this.tooth1 = new ModelRenderer(this, 59, 0);
        this.tooth1.setRotationPoint(0.7F, -0.4F, -1.5F);
        this.tooth1.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightWing1 = new ModelRenderer(this, 0, 9);
        this.rightWing1.mirror = true;
        this.rightWing1.setRotationPoint(-2.0F, 0.0F, -2.0F);
        this.rightWing1.addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing1, 0.0F, 0.0F, -0.7428121536172364F);
        this.rightWing2 = new ModelRenderer(this, 0, 14);
        this.rightWing2.mirror = true;
        this.rightWing2.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.rightWing2.addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing2, 0.0F, 0.0F, -0.8600982340775168F);
        this.tail1 = new ModelRenderer(this, 36, 10);
        this.tail1.setRotationPoint(0.0F, 0.0F, 4.5F);
        this.tail1.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail1, -0.23457224414434488F, 0.0F, 0.0F);
        this.rightLeg3 = new ModelRenderer(this, 28, 7);
        this.rightLeg3.mirror = true;
        this.rightLeg3.setRotationPoint(0.0F, 2.2F, 0.8F);
        this.rightLeg3.addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg3, 0.3909537457888271F, 0.0F, 0.0F);
        this.rightLeg1 = new ModelRenderer(this, 44, 5);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setRotationPoint(-1.8F, 0.5F, 2.0F);
        this.rightLeg1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, 0.4300491170387584F, 0.0F, 0.0F);
        this.leftNose = new ModelRenderer(this, 45, 1);
        this.leftNose.setRotationPoint(-0.8F, -1.4F, -2.6F);
        this.leftNose.addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike3 = new ModelRenderer(this, 8, 26);
        this.spike3.setRotationPoint(0.01F, -3.5F, 0.0F);
        this.spike3.addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightEye = new ColorModelRenderer(this, 56, 3);
        this.rightEye.mirror = true;
        this.rightEye.setRotationPoint(-2.5F, -0.1F, -2.4F);
        this.rightEye.addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.tooth4 = new ModelRenderer(this, 59, 0);
        this.tooth4.setRotationPoint(-1.7F, -0.4F, -1.6F);
        this.tooth4.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tooth2 = new ModelRenderer(this, 59, 0);
        this.tooth2.setRotationPoint(0.0F, -0.3F, -3.2F);
        this.tooth2.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm1 = new ModelRenderer(this, 0, 5);
        this.leftArm1.setRotationPoint(1.6F, 1.1F, -4.0F);
        this.leftArm1.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn2 = new ModelRenderer(this, 6, 14);
        this.leftHorn2.setRotationPoint(0.01F, 0.0F, 1.4F);
        this.leftHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn2, 0.3490658503988659F, 0.0F, 0.0F);
        this.rightHorn2 = new ModelRenderer(this, 6, 14);
        this.rightHorn2.mirror = true;
        this.rightHorn2.setRotationPoint(-0.01F, 0.0F, 1.4F);
        this.rightHorn2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn2, 0.3490658503988659F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 50, 5);
        this.leftLeg2.setRotationPoint(0.01F, 2.4F, 0.5F);
        this.leftLeg2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, -0.7428121536172364F, 0.0F, 0.0F);
        this.head.addChild(this.leftHorn1);
        this.fez1.addChild(this.fez2);
        this.rightArm1.addChild(this.rightArm3);
        this.jaw.addChild(this.rightNose);
        this.leftLeg2.addChild(this.leftLeg3);
        this.body.addChild(this.leftLeg1);
        this.rightArm1.addChild(this.rightArm2);
        this.head.addChild(this.rightEar);
        this.body.addChild(this.rightArm1);
        this.neck2.addChild(this.head);
        this.head.addChild(this.leftEye);
        this.leftArm1.addChild(this.leftArm3);
        this.tail1.addChild(this.tail2);
        this.tail1.addChild(this.spike1);
        this.tail2.addChild(this.tail3);
        this.head.addChild(this.jaw);
        this.head.addChild(this.rightHorn1);
        this.rightLeg1.addChild(this.rightLeg2);
        this.head.addChild(this.fez1);
        this.body.addChild(this.neck1);
        this.tail2.addChild(this.spike2);
        this.head.addChild(this.leftEar);
        this.neck1.addChild(this.neck2);
        this.leftWing1.addChild(this.leftWing2);
        this.body.addChild(this.leftWing1);
        this.leftArm1.addChild(this.leftArm2);
        this.jaw.addChild(this.tooth1);
        this.body.addChild(this.rightWing1);
        this.rightWing1.addChild(this.rightWing2);
        this.body.addChild(this.tail1);
        this.rightLeg2.addChild(this.rightLeg3);
        this.body.addChild(this.rightLeg1);
        this.jaw.addChild(this.leftNose);
        this.tail3.addChild(this.spike3);
        this.head.addChild(this.rightEye);
        this.jaw.addChild(this.tooth4);
        this.jaw.addChild(this.tooth2);
        this.body.addChild(this.leftArm1);
        this.leftHorn1.addChild(this.leftHorn2);
        this.rightHorn1.addChild(this.rightHorn2);
        this.leftLeg1.addChild(this.leftLeg2);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(DragonFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isPartying()) {
            this.head.rotateAngleX = toRads(50) + MathHelper.sin(ageInTicks) * toRads(20);
            this.head.rotateAngleY = MathHelper.sin(ageInTicks) * toRads(5);
            this.head.rotateAngleZ = MathHelper.sin(ageInTicks) * toRads(5);

        } else {
            this.head.rotateAngleX = toRads(50) + 0.03f + headPitch * (PI / 180f) * 0.7f;
            this.head.rotateAngleY = netHeadYaw * (PI / 180f) * 0.5f;
            this.head.rotateAngleZ = netHeadYaw * (PI / 180f) * 0.5f;
        }

    }

    @Override
    public void setLivingAnimations(DragonFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
        setEyeColor(entityIn.getEyeColorR(partialTick), entityIn.getEyeColorG(partialTick),
                entityIn.getEyeColorB(partialTick));
        showModels(entityIn);

        float ageInTicks = entityIn.ticksExisted + partialTick;

        if (entityIn.isPartying()) {
            this.body.rotateAngleZ = 0;
            this.tail1.rotateAngleZ = MathHelper.sin(ageInTicks) * toRads(30);
            this.tail2.rotateAngleZ = -MathHelper.sin(ageInTicks) * toRads(60);
            this.tail3.rotateAngleZ = MathHelper.sin(ageInTicks) * toRads(90);

            this.leftWing1.rotateAngleY = MathHelper.sin(ageInTicks) * toRads(20);
            this.rightWing1.rotateAngleY = -MathHelper.sin(ageInTicks) * toRads(20);
        } else {
            this.body.rotateAngleZ = 0;
            this.tail1.rotateAngleZ = 0;
            this.tail2.rotateAngleZ = 0;
            this.tail3.rotateAngleZ = 0;

            this.leftWing1.rotateAngleY = 0;
            this.rightWing1.rotateAngleY = 0;
        }

        if (entityIn.isSwingInProgress) {
            float attackProgress = entityIn.getAttackProgress(partialTick);
            this.tail1.rotateAngleY = MathHelper.sin(attackProgress * PI * 4) * toRads(30);
            this.tail2.rotateAngleY = MathHelper.sin(attackProgress * PI * 4) * toRads(30);
            this.tail3.rotateAngleY = MathHelper.sin(attackProgress * PI * 4) * toRads(30);
        } else {
            this.tail1.rotateAngleY = 0;
            this.tail2.rotateAngleY = 0;
            this.tail3.rotateAngleY = 0;
        }

        if (!entityIn.isSitting()) {
            this.leftLeg1.rotateAngleX = toRads(25) + MathHelper.cos(limbSwing * 0.7f + PI) * limbSwingAmount * 0.5f;
            this.rightLeg1.rotateAngleX = toRads(25) + MathHelper.cos(limbSwing * 0.7f) * limbSwingAmount * 0.5f;
            this.leftLeg3.rotateAngleX = toRads(23);
            this.rightLeg3.rotateAngleX = toRads(23);

            float flyingTimer = entityIn.getFlyingTimer(partialTick);
            float wingspan = entityIn.getWingspan(partialTick);
            float flyingWingRot = flyingTimer * 1.15f;
            this.leftWing1.rotateAngleZ = toRads(65)
                    + MathHelper.cos(limbSwing * 0.7f + flyingWingRot) * (limbSwingAmount * 0.2f + toRads(wingspan));
            this.leftWing2.rotateAngleZ = toRads(50) + MathHelper.cos(limbSwing * 0.7f + flyingWingRot)
                    * (limbSwingAmount * 0.2f + toRads(wingspan) * 0.5f);
            this.rightWing1.rotateAngleZ = -toRads(65)
                    - MathHelper.cos(limbSwing * 0.7f + flyingWingRot) * (limbSwingAmount * 0.2f + toRads(wingspan));
            this.rightWing2.rotateAngleZ = -toRads(50) - MathHelper.cos(limbSwing * 0.7f + flyingWingRot)
                    * (limbSwingAmount * 0.2f + toRads(wingspan) * 0.5f);

            this.tail1.rotateAngleX = MathHelper.cos(ageInTicks / 20) * toRads(10);
            this.tail2.rotateAngleX = MathHelper.cos(ageInTicks / 20) * toRads(10);
            this.tail3.rotateAngleX = MathHelper.cos(ageInTicks / 20) * toRads(10);

            this.body.rotateAngleX = toRads(-4);
            this.neck1.rotateAngleX = toRads(-30);
            this.neck2.rotateAngleX = toRads(-9);
        } else {
            this.leftLeg1.rotateAngleX = toRads(15);
            this.rightLeg1.rotateAngleX = toRads(15);
            this.leftLeg3.rotateAngleX = toRads(26);
            this.rightLeg3.rotateAngleX = toRads(26);

            this.leftWing1.rotateAngleZ = toRads(150);
            this.leftWing2.rotateAngleZ = toRads(20);
            this.rightWing1.rotateAngleZ = -toRads(150);
            this.rightWing2.rotateAngleZ = -toRads(20);

            this.tail1.rotateAngleX = toRads(30);
            this.tail2.rotateAngleX = toRads(30);
            this.tail3.rotateAngleX = toRads(30);

            this.body.rotateAngleX = toRads(-50);
            this.neck1.rotateAngleX = toRads(10);
            this.neck2.rotateAngleX = toRads(5);
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(DragonFamiliarEntity entityIn) {
        boolean hasEars = entityIn.hasEars();
        boolean hasArms = entityIn.hasArms();

        this.fez1.showModel = entityIn.hasFez();
        this.leftEar.showModel = hasEars;
        this.rightEar.showModel = hasEars;
        this.leftHorn1.showModel = !hasEars;
        this.rightHorn1.showModel = !hasEars;
        this.leftArm1.showModel = hasArms;
        this.rightArm1.showModel = hasArms;
    }

    private void setEyeColor(float r, float g, float b) {
        this.leftEye.setColor(r, g, b);
        this.rightEye.setColor(r, g, b);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private static class ColorModelRenderer extends ModelRenderer {

        float r, g, b;

        public ColorModelRenderer(Model model, int texOffX, int texOffY) {
            super(model, texOffX, texOffY);
        }

        private void setColor(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
                float red, float green, float blue, float alpha) {
            super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, r, g, b, alpha);
        }

    }
}
