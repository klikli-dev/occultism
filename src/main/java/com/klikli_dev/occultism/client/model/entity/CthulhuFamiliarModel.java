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

package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.common.entity.familiar.CthulhuFamiliarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class CthulhuFamiliarModel extends EntityModel<CthulhuFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart head;
    public ModelPart leftLeg;
    public ModelPart leftArm;
    public ModelPart tail;
    public ModelPart leftWing;
    public ModelPart rightLeg;
    public ModelPart rightArm;
    public ModelPart rightWing;
    public ModelPart hair;
    public ModelPart leftEye;
    public ModelPart rightEye;
    public ModelPart leftEar;
    public ModelPart tentacle1;
    public ModelPart tentacle2;
    public ModelPart tentacle3;
    public ModelPart rightEar;
    public ModelPart hat1;
    public ModelPart trunk1;
    public ModelPart hat2;
    public ModelPart trunk2;
    public ModelPart trunk3;
    public ModelPart lantern1;
    public ModelPart lantern2;
    public ModelPart lantern3;
    public DragonFamiliarModel.ColorModelPartProxy lantern4;

    public CthulhuFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.head = this.body.getChild("head");
        this.leftLeg = this.body.getChild("leftLeg");
        this.leftArm = this.body.getChild("leftArm");
        this.tail = this.body.getChild("tail");
        this.leftWing = this.body.getChild("leftWing");
        this.rightLeg = this.body.getChild("rightLeg");
        this.rightArm = this.body.getChild("rightArm");
        this.rightWing = this.body.getChild("rightWing");
        this.hair = this.head.getChild("hair");
        this.leftEye = this.head.getChild("leftEye");
        this.rightEye = this.head.getChild("rightEye");
        this.leftEar = this.head.getChild("leftEar");
        this.tentacle1 = this.head.getChild("tentacle1");
        this.tentacle2 = this.head.getChild("tentacle2");
        this.tentacle3 = this.head.getChild("tentacle3");
        this.rightEar = this.head.getChild("rightEar");
        this.hat1 = this.head.getChild("hat1");
        this.trunk1 = this.head.getChild("trunk1");
        this.hat2 = this.hat1.getChild("hat2");
        this.trunk2 = this.trunk1.getChild("trunk2");
        this.trunk3 = this.trunk2.getChild("trunk3");
        this.lantern1 = this.leftArm.getChild("lantern1");
        this.lantern2 = this.lantern1.getChild("lantern2");
        this.lantern3 = this.lantern2.getChild("lantern3");
        this.lantern4 = new DragonFamiliarModel.ColorModelPartProxy(this.lantern3.getChild("lantern4"));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -1.5F, 7.0F, 7.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 17.0F, 0.0F, 0.43912483713861217F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 0).addBox(-3.0F, -5.0F, -3.2F, 6.0F, 5.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, -5.8F, -0.3F, -0.26284659267403904F, 0.0F, 0.0F));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, 0.5F, 0.0F, -0.39426988068868013F, 0.0F, 0.0F));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(3.2F, -4.5F, 0.0F, -1.6891296287748234F, 0.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -1.0F, 0.0F, 0.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 1.0F, 0.628493034348386F, 0.0F, 0.0F));
        PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(20, 21).addBox(0.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, false), PartPose.offsetAndRotation(2.4F, -5.0F, 1.4F, -0.13142329633701952F, -0.4291764657285667F, 0.5256931853480781F));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-2.0F, 0.5F, 0.0F, -0.39426988068868013F, 0.0F, 0.0F));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(52, 0).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-3.2F, -4.5F, 0.0F, -0.43807764890847944F, 0.0F, 0.0F));
        PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(20, 21).addBox(-8.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, true), PartPose.offsetAndRotation(-2.4F, -5.0F, 1.4F, -0.13142329633701952F, 0.4291764657285667F, -0.5256931853480781F));
        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -7.0F, -3.2F, 0.0F, 7.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0, 0, 0));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(26, 12).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.3F, -3.0F, -3.7F, 0, 0, 0));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(26, 12).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-1.3F, -3.0F, -3.7F, 0, 0, 0));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(3.0F, -4.5F, -1.0F, 0, 0, 0));
        PartDefinition tentacle1 = head.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(-0.1F, -1.1F, -2.8F, -0.19338247978939116F, 0.0F, 0.5759586531581287F));
        PartDefinition tentacle2 = head.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.4F, -2.8F, -0.19338247978939116F, 0.0F, 0.0F));
        PartDefinition tentacle3 = head.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.1F, -1.1F, -2.8F, -0.19338247978939116F, 0.0F, -0.5759586531581287F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, true), PartPose.offsetAndRotation(-3.0F, -4.5F, -1.0F, 0, 0, 0));
        PartDefinition hat1 = head.addOrReplaceChild("hat1", CubeListBuilder.create().texOffs(20, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F, false), PartPose.offsetAndRotation(2.5F, -5.5F, -3.0F, 0.27366763203903305F, -0.03909537541112055F, 0.23457224414434488F));
        PartDefinition trunk1 = head.addOrReplaceChild("trunk1", CubeListBuilder.create().texOffs(44, 7).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.1F, -2.2F, -0.8600982340775168F, 0.0F, 0.0F));
        PartDefinition hat2 = hat1.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(36, 16).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.5F, -2.0F, 0.5F, 0, 0, 0));
        PartDefinition trunk2 = trunk1.addOrReplaceChild("trunk2", CubeListBuilder.create().texOffs(44, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, -0.46914448828868976F, 0.0F, 0.0F));
        PartDefinition trunk3 = trunk2.addOrReplaceChild("trunk3", CubeListBuilder.create().texOffs(54, 7).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.3909537457888271F, 0.0F, 0.0F));
        PartDefinition lantern1 = leftArm.addOrReplaceChild("lantern1", CubeListBuilder.create().texOffs(58, 29).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, false), PartPose.offsetAndRotation(1.0F, 3.5F, -0.5F, 1.2901473511162753F, 0.0F, 0.0F));
        PartDefinition lantern2 = lantern1.addOrReplaceChild("lantern2", CubeListBuilder.create().texOffs(42, 23).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition lantern3 = lantern2.addOrReplaceChild("lantern3", CubeListBuilder.create().texOffs(34, 27).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 4.99F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition lantern4 = lantern3.addOrReplaceChild("lantern4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -3.3F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, red, green, blue, alpha);
        this.lantern4.proxyRender(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, red, green, blue, alpha);
    }


    @Override
    public void setupAnim(CthulhuFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float partialTicks = Minecraft.getInstance().getFrameTime();

        entityIn.riderLimbSwing = limbSwing;
        entityIn.riderLimbSwingAmount = limbSwingAmount;

        this.showModels(entityIn);
        this.lantern4.setColor(1, 1, 1, (Mth.cos(ageInTicks * 0.2f) + 1) * 0.5f);

        this.head.yRot = netHeadYaw * (PI / 180f) * 0.7f;
        this.head.xRot = headPitch * (PI / 180f) * 0.7f - 0.26f;

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
            this.rightArm.xRot = Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f - 0.44f;
            this.leftArm.xRot = Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f - 0.44f;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount * 0.2f - 0.39f;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount * 0.2f - 0.39f;
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

        this.trunk1.xRot = -0.86f + Mth.cos(ageInTicks / 10) * 0.15f;
        this.trunk2.xRot = -0.47f + Mth.cos(ageInTicks / 10) * 0.15f;
        this.trunk3.xRot = 0.39f + Mth.cos(ageInTicks / 10) * 0.15f;
        this.tentacle1.zRot = 0.58f + Mth.cos(ageInTicks / 10) * 0.07f;
        this.tentacle3.zRot = -0.58f - Mth.cos(ageInTicks / 10) * 0.07f;

        if (entityIn.hasBlacksmithUpgrade()) {
            if (entityIn.isSitting()) {
                this.lantern1.xRot = this.toRads(0);
                this.lantern1.y = -1.6f;
                this.lantern1.x = 3f;
                this.lantern1.z = -5f;
            } else {
                this.leftArm.xRot = Mth.cos(limbSwing * 0.4f + PI) * limbSwingAmount * 0.2f + this.toRads(-100);
                this.lantern1.yRot = Mth.cos(ageInTicks * 0.2f) * this.toRads(5);
                this.lantern1.zRot = -Mth.cos(ageInTicks * 0.2f) * this.toRads(5);
                this.lantern1.xRot = this.toRads(74);
                this.lantern1.y = 3.5f;
                this.lantern1.x = 1f;
                this.lantern1.z = -0.5f;
            }
        }

        if (entityIn.isVehicle()) {
            float animHeight = entityIn.getAnimationHeight(partialTicks);
            this.rightArm.xRot = toRads(40 - animHeight * 15);
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
}
