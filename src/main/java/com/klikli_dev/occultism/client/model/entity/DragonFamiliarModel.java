package com.klikli_dev.occultism.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import software.bernie.geckolib.util.Color;

import java.util.Collections;
import java.util.stream.Stream;

/**
 * Created using Tabula 8.0.0
 */
public class DragonFamiliarModel extends EntityModel<DragonFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart neck1;
    public ModelPart leftLeg1;
    public ModelPart tail1;
    public ModelPart leftWing1;
    public ModelPart rightWing1;
    public ModelPart rightLeg1;
    public ModelPart leftArm1;
    public ModelPart rightArm1;
    public ModelPart neck2;
    public ModelPart head;
    public ModelPart jaw;
    public ModelPart fez1;
    public ModelPart leftHorn1;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart rightHorn1;
    public ModelPart tooth1;
    public ModelPart tooth2;
    public ModelPart tooth4;
    public ModelPart leftNose;
    public ModelPart rightNose;
    public ModelPart fez2;
    public ModelPart leftHorn2;
    public ModelPart rightHorn2;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart tail2;
    public ModelPart spike1;
    public ModelPart tail3;
    public ModelPart spike2;
    public ModelPart spike3;
    public ModelPart leftWing2;
    public ModelPart rightWing2;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;
    public ModelPart leftArm2;
    public ModelPart leftArm3;
    public ModelPart rightArm2;
    public ModelPart rightArm3;

    public ColorModelPartProxy leftEye;
    public ColorModelPartProxy rightEye;

    public DragonFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.neck1 = this.body.getChild("neck1");
        this.leftLeg1 = this.body.getChild("leftLeg1");
        this.tail1 = this.body.getChild("tail1");
        this.leftWing1 = this.body.getChild("leftWing1");
        this.rightWing1 = this.body.getChild("rightWing1");
        this.rightLeg1 = this.body.getChild("rightLeg1");
        this.leftArm1 = this.body.getChild("leftArm1");
        this.rightArm1 = this.body.getChild("rightArm1");
        this.neck2 = this.neck1.getChild("neck2");
        this.head = this.neck2.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.leftEye = new ColorModelPartProxy(this.head.getChild("leftEye"));
        this.rightEye = new ColorModelPartProxy(this.head.getChild("rightEye"));
        this.fez1 = this.head.getChild("fez1");
        this.leftHorn1 = this.head.getChild("leftHorn1");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEar = this.head.getChild("rightEar");
        this.rightHorn1 = this.head.getChild("rightHorn1");
        this.tooth1 = this.jaw.getChild("tooth1");
        this.tooth2 = this.jaw.getChild("tooth2");
        this.tooth4 = this.jaw.getChild("tooth4");
        this.leftNose = this.jaw.getChild("leftNose");
        this.rightNose = this.jaw.getChild("rightNose");
        this.fez2 = this.fez1.getChild("fez2");
        this.leftHorn2 = this.leftHorn1.getChild("leftHorn2");
        this.rightHorn2 = this.rightHorn1.getChild("rightHorn2");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.leftLeg3 = this.leftLeg2.getChild("leftLeg3");
        this.tail2 = this.tail1.getChild("tail2");
        this.spike1 = this.tail1.getChild("spike1");
        this.tail3 = this.tail2.getChild("tail3");
        this.spike2 = this.tail2.getChild("spike2");
        this.spike3 = this.tail3.getChild("spike3");
        this.leftWing2 = this.leftWing1.getChild("leftWing2");
        this.rightWing2 = this.rightWing1.getChild("rightWing2");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.rightLeg3 = this.rightLeg2.getChild("rightLeg3");
        this.leftArm2 = this.leftArm1.getChild("leftArm2");
        this.leftArm3 = this.leftArm1.getChild("leftArm3");
        this.rightArm2 = this.rightArm1.getChild("rightArm2");
        this.rightArm3 = this.rightArm1.getChild("rightArm3");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, -0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition neck1 = body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(18, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.2F, -4.1F, -0.5473352640780661F, 0.0F, 0.0F));
        PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(44, 5).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(1.8F, 0.5F, 2.0F, 0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(36, 10).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 4.3F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition leftWing1 = body.addOrReplaceChild("leftWing1", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, false), PartPose.offsetAndRotation(2.0F, 0.0F, -2.0F, 0.0F, 0.0F, 1.1414453574359025F));
        PartDefinition rightWing1 = body.addOrReplaceChild("rightWing1", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, true), PartPose.offsetAndRotation(-2.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.8600982340775168F));
        PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(44, 5).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, true), PartPose.offsetAndRotation(-1.8F, 0.5F, 2.0F, 0.4300491170387584F, 0.0F, 0.0F));
        PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(0, 5).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(1.6F, 1.1F, -4.0F, 0, 0, 0));
        PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(0, 5).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-1.6F, 1.1F, -4.0F, 0, 0, 0));
        PartDefinition neck2 = neck1.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.4F, -0.1563815016444822F, 0.0F, 0.0F));
        PartDefinition head = neck2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 0).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -0.4F, -2.4F, 0.8213519699569813F, 0.0F, 0.0F));
        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(46, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.2F, -4.0F, 0, 0, 0));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(56, 3).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.5F, -0.1F, -2.4F, 0, 0, 0));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(56, 3).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, -0.1F, -2.4F, 0, 0, 0));
        PartDefinition fez1 = head.addOrReplaceChild("fez1", CubeListBuilder.create().texOffs(18, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.5F, -2.0F, 0, 0, 0));
        PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.7F, -0.5F, -0.5F, 0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(12, 14).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.7F, -0.6F, -0.6F, -0.13613568498450906F, 1.1798425477165557F, -0.5899212738582779F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(12, 14).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-1.7F, -0.6F, -0.6F, -0.13613568498450906F, -1.1798425477165557F, 0.5899212738582779F));
        PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-1.7F, -0.5F, -0.5F, 0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition tooth1 = jaw.addOrReplaceChild("tooth1", CubeListBuilder.create().texOffs(59, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.7F, -0.4F, -1.5F, 0, 0, 0));
        PartDefinition tooth2 = jaw.addOrReplaceChild("tooth2", CubeListBuilder.create().texOffs(59, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -0.3F, -3.2F, 0, 0, 0));
        PartDefinition tooth4 = jaw.addOrReplaceChild("tooth4", CubeListBuilder.create().texOffs(59, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-1.7F, -0.4F, -1.6F, 0, 0, 0));
        PartDefinition leftNose = jaw.addOrReplaceChild("leftNose", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-0.8F, -1.4F, -2.6F, 0, 0, 0));
        PartDefinition rightNose = jaw.addOrReplaceChild("rightNose", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.8F, -1.4F, -2.6F, 0, 0, 0));
        PartDefinition fez2 = fez1.addOrReplaceChild("fez2", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0, 0, 0));
        PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(6, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.01F, 0.0F, 1.4F, 0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(6, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 0.0F, 1.4F, 0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(50, 5).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.01F, 2.4F, 0.5F, -0.7428121536172364F, 0.0F, 0.0F));
        PartDefinition leftLeg3 = leftLeg2.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(28, 7).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 2.2F, 0.8F, 0.3909537457888271F, 0.0F, 0.0F));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(50, 10).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.3F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition spike1 = tail1.addOrReplaceChild("spike1", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, 0, 0, 0));
        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(18, 6).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.4F, 0.19547687289441354F, 0.0F, 0.0F));
        PartDefinition spike2 = tail2.addOrReplaceChild("spike2", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.01F, -4.0F, 0.0F, 0, 0, 0));
        PartDefinition spike3 = tail3.addOrReplaceChild("spike3", CubeListBuilder.create().texOffs(8, 26).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -3.5F, 0.0F, 0, 0, 0));
        PartDefinition leftWing2 = leftWing1.addOrReplaceChild("leftWing2", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.8600982340775168F));
        PartDefinition rightWing2 = rightWing1.addOrReplaceChild("rightWing2", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -5.0F, -5.0F, 0.0F, 5.0F, 10.0F, true), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.8600982340775168F));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(50, 5).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, true), PartPose.offsetAndRotation(-0.01F, 2.4F, 0.5F, -0.7428121536172364F, 0.0F, 0.0F));
        PartDefinition rightLeg3 = rightLeg2.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(28, 7).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, true), PartPose.offsetAndRotation(0.0F, 2.2F, 0.8F, 0.3909537457888271F, 0.0F, 0.0F));
        PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-0.1F, 2.1F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F));
        PartDefinition leftArm3 = leftArm1.addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.1F, 2.1F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F));
        PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-0.1F, 2.1F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F));
        PartDefinition rightArm3 = rightArm1.addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.1F, 2.1F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F));
        return LayerDefinition.create(mesh, 64, 32);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
        //        this.rightEye.proxyRender(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
//        this.leftEye.proxyRender(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
    }

    @Override
    public void setupAnim(DragonFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        if (entityIn.isPartying()) {
            this.head.xRot = this.toRads(50) + Mth.sin(ageInTicks) * this.toRads(20);
            this.head.yRot = Mth.sin(ageInTicks) * this.toRads(5);
            this.head.zRot = Mth.sin(ageInTicks) * this.toRads(5);

        } else {
            this.head.xRot = this.toRads(50) + 0.03f + headPitch * (PI / 180f) * 0.7f;
            this.head.yRot = netHeadYaw * (PI / 180f) * 0.5f;
            this.head.zRot = netHeadYaw * (PI / 180f) * 0.5f;
        }

    }


    @Override
    public void prepareMobModel(DragonFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                                float partialTick) {
        this.setEyeColor(entityIn.getEyeColorR(partialTick), entityIn.getEyeColorG(partialTick),
                entityIn.getEyeColorB(partialTick));
        this.showModels(entityIn);

        float ageInTicks = entityIn.tickCount + partialTick;

        this.tail1.zRot = 0;
        this.tail2.zRot = 0;
        this.tail3.zRot = 0;
        this.jaw.zRot = 0;

        if (entityIn.isPartying()) {
            this.tail1.zRot = Mth.sin(ageInTicks) * this.toRads(30);
            this.tail2.zRot = -Mth.sin(ageInTicks) * this.toRads(60);
            this.tail3.zRot = Mth.sin(ageInTicks) * this.toRads(90);

            this.leftWing1.yRot = Mth.sin(ageInTicks) * this.toRads(20);
            this.rightWing1.yRot = -Mth.sin(ageInTicks) * this.toRads(20);
        } else {
            this.leftWing1.yRot = 0;
            this.rightWing1.yRot = 0;
        }

        float petTimer = entityIn.getPetTimer() + partialTick;
        float petDuration = DragonFamiliarEntity.MAX_PET_TIMER / 2.0f;
        if (petTimer < petDuration) {
            this.tail1.zRot = Mth.sin(petTimer / petDuration * PI * 6) * this.toRads(20);
            this.tail2.zRot = Mth.sin(petTimer / petDuration * PI * 6) * this.toRads(20);
            this.tail3.zRot = Mth.sin(petTimer / petDuration * PI * 6) * this.toRads(20);
            this.jaw.zRot = -Mth.sin(petTimer / petDuration * PI * 6) * this.toRads(10);
        }

        if (entityIn.swinging) {
            float attackProgress = entityIn.getAttackProgress(partialTick);
            this.tail1.yRot = Mth.sin(attackProgress * PI * 4) * this.toRads(30);
            this.tail2.yRot = Mth.sin(attackProgress * PI * 4) * this.toRads(30);
            this.tail3.yRot = Mth.sin(attackProgress * PI * 4) * this.toRads(30);
        } else {
            this.tail1.yRot = 0;
            this.tail2.yRot = 0;
            this.tail3.yRot = 0;
        }

        if (!entityIn.isSitting()) {
            this.leftLeg1.xRot = this.toRads(25) + Mth.cos(limbSwing * 0.7f + PI) * limbSwingAmount * 0.5f;
            this.rightLeg1.xRot = this.toRads(25) + Mth.cos(limbSwing * 0.7f) * limbSwingAmount * 0.5f;
            this.leftLeg3.xRot = this.toRads(23);
            this.rightLeg3.xRot = this.toRads(23);

            float flyingTimer = entityIn.getFlyingTimer(partialTick);
            float wingspan = entityIn.getWingspan(partialTick);
            float flyingWingRot = flyingTimer * 1.15f;
            this.leftWing1.zRot = this.toRads(65)
                    + Mth.cos(limbSwing * 0.7f + flyingWingRot) * (limbSwingAmount * 0.2f + this.toRads(wingspan));
            this.leftWing2.zRot = this.toRads(50) + Mth.cos(limbSwing * 0.7f + flyingWingRot)
                    * (limbSwingAmount * 0.2f + this.toRads(wingspan) * 0.5f);
            this.rightWing1.zRot = -this.toRads(65)
                    - Mth.cos(limbSwing * 0.7f + flyingWingRot) * (limbSwingAmount * 0.2f + this.toRads(wingspan));
            this.rightWing2.zRot = -this.toRads(50) - Mth.cos(limbSwing * 0.7f + flyingWingRot)
                    * (limbSwingAmount * 0.2f + this.toRads(wingspan) * 0.5f);

            this.tail1.xRot = Mth.cos(ageInTicks / 20) * this.toRads(10);
            this.tail2.xRot = Mth.cos(ageInTicks / 20) * this.toRads(10);
            this.tail3.xRot = Mth.cos(ageInTicks / 20) * this.toRads(10);

            this.body.xRot = this.toRads(-4);
            this.neck1.xRot = this.toRads(-30);
            this.neck2.xRot = this.toRads(-9);
        } else {
            this.leftLeg1.xRot = this.toRads(15);
            this.rightLeg1.xRot = this.toRads(15);
            this.leftLeg3.xRot = this.toRads(26);
            this.rightLeg3.xRot = this.toRads(26);

            this.leftWing1.zRot = this.toRads(150);
            this.leftWing2.zRot = this.toRads(20);
            this.rightWing1.zRot = -this.toRads(150);
            this.rightWing2.zRot = -this.toRads(20);

            this.tail1.xRot = this.toRads(30);
            this.tail2.xRot = this.toRads(30);
            this.tail3.xRot = this.toRads(30);

            this.body.xRot = this.toRads(-50);
            this.neck1.xRot = this.toRads(10);
            this.neck2.xRot = this.toRads(5);
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(DragonFamiliarEntity entityIn) {
        boolean hasEars = entityIn.hasEars();
        boolean hasArms = entityIn.hasArms();

        this.fez1.visible = entityIn.hasFez();
        this.leftEar.visible = hasEars;
        this.rightEar.visible = hasEars;
        this.leftHorn1.visible = !hasEars;
        this.rightHorn1.visible = !hasEars;
        this.leftArm1.visible = hasArms;
        this.rightArm1.visible = hasArms;
    }

    private void setEyeColor(float r, float g, float b) {
        this.leftEye.setColor(r, g, b);
        this.rightEye.setColor(r, g, b);
    }

    public static class ColorModelPartProxy extends ModelPart {

        Color color;
        ModelPart proxied;

        public ColorModelPartProxy(ModelPart modelPart) {
            super(Collections.emptyList(), Collections.emptyMap());
            this.proxied = modelPart;

        }

        public void setColor(float r, float g, float b) {
            this.setColor(r, g, b, 1);
        }

        public void setColor(float r, float g, float b, float a) {
            this.color =        Color.ofRGBA(r, g, b, a);
        }

        @Override
        public void render(PoseStack poseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, int pColor) {
            //prevent actual render
            this.proxied.render(poseStack, pVertexConsumer, pPackedLight, pPackedOverlay, this.color.getColor());
        }

        public void proxyRender(PoseStack poseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, int pColor) {
            this.proxied.render(poseStack, pVertexConsumer, pPackedLight, pPackedOverlay, this.color.getColor());
        }

        @Override
        public PartPose getInitialPose() {
            return this.proxied.getInitialPose();
        }

        @Override
        public void visit(PoseStack pPoseStack, Visitor pVisitor) {
            this.proxied.visit(pPoseStack, pVisitor);
        }

        @Override
        public void offsetPos(Vector3f p_233565_) {
            this.proxied.offsetPos(p_233565_);
        }

        @Override
        public void offsetRotation(Vector3f p_233568_) {
            this.proxied.offsetRotation(p_233568_);
        }

        @Override
        public void offsetScale(Vector3f p_233571_) {
            this.proxied.offsetScale(p_233571_);
        }

        @Override
        public Stream<ModelPart> getAllParts() {
            return this.proxied.getAllParts();
        }

        @Override
        public PartPose storePose() {
            return this.proxied.storePose();
        }

        @Override
        public void setInitialPose(PartPose pInitialPose) {
            this.proxied.setInitialPose(pInitialPose);
        }

        @Override
        public void resetPose() {
            this.proxied.resetPose();
        }

        @Override
        public void loadPose(PartPose pPartPose) {
            this.proxied.loadPose(pPartPose);
        }

        @Override
        public void copyFrom(ModelPart pModelPart) {
            this.proxied.copyFrom(pModelPart);
        }

        @Override
        public boolean hasChild(String p_233563_) {
            return this.proxied.hasChild(p_233563_);
        }

        @Override
        public ModelPart getChild(String pName) {
            return this.proxied.getChild(pName);
        }

        @Override
        public void setPos(float pX, float pY, float pZ) {
            this.proxied.setPos(pX, pY, pZ);
        }

        @Override
        public void setRotation(float pXRot, float pYRot, float pZRot) {
            this.proxied.setRotation(pXRot, pYRot, pZRot);
        }

        @Override
        public void translateAndRotate(PoseStack pPoseStack) {
            this.proxied.translateAndRotate(pPoseStack);
        }

        @Override
        public Cube getRandomCube(RandomSource pRandom) {
            return this.proxied.getRandomCube(pRandom);
        }

        @Override
        public boolean isEmpty() {
            return this.proxied.isEmpty();
        }
    }
}
