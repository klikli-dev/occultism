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

import com.github.klikli_dev.occultism.common.entity.DevilFamiliarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class DevilFamiliarModel extends EntityModel<DevilFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart egg;
    public ModelPart body;
    public ModelPart leftLeg;
    public ModelPart leftArm;
    public ModelPart rightLeg;
    public ModelPart rightArm;
    public ModelPart neck1;
    public ModelPart leftWing;
    public ModelPart rightWing;
    public ModelPart head;
    public ModelPart lowerJaw;
    public ModelPart upperJaw;
    public ModelPart leftEye;
    public ModelPart headScales;
    public ModelPart leftEar;
    public ModelPart leftHorn;
    public ModelPart rightEye;
    public ModelPart rightHorn;
    public ModelPart rightEar;
    public ModelPart leftHornBig1;
    public ModelPart rightHornBig1;
    public ModelPart nose;
    public ModelPart tooth;
    public ModelPart jawHorn1;
    public ModelPart jawHorn2;
    public ModelPart leftHornBig2;
    public ModelPart leftHornBig3;
    public ModelPart rightHornBig2;
    public ModelPart rightHornBig3;
    public ModelPart lollipop;

    public DevilFamiliarModel(ModelPart part) {
        this.egg = part.getChild("egg");
        this.body = this.egg.getChild("body");
        this.leftLeg = this.egg.getChild("leftLeg");
        this.leftArm = this.egg.getChild("leftArm");
        this.rightLeg = this.egg.getChild("rightLeg");
        this.rightArm = this.egg.getChild("rightArm");
        this.neck1 = this.body.getChild("neck1");
        this.leftWing = this.body.getChild("leftWing");
        this.rightWing = this.body.getChild("rightWing");
        this.head = this.neck1.getChild("head");
        this.lowerJaw = this.head.getChild("lowerJaw");
        this.upperJaw = this.head.getChild("upperJaw");
        this.leftEye = this.head.getChild("leftEye");
        this.headScales = this.head.getChild("headScales");
        this.leftEar = this.head.getChild("leftEar");
        this.leftHorn = this.head.getChild("leftHorn");
        this.rightEye = this.head.getChild("rightEye");
        this.rightHorn = this.head.getChild("rightHorn");
        this.rightEar = this.head.getChild("rightEar");
        this.leftHornBig1 = this.head.getChild("leftHornBig1");
        this.rightHornBig1 = this.head.getChild("rightHornBig1");
        this.nose = this.upperJaw.getChild("nose");
        this.tooth = this.upperJaw.getChild("tooth");
        this.jawHorn1 = this.upperJaw.getChild("jawHorn1");
        this.jawHorn2 = this.jawHorn1.getChild("jawHorn2");
        this.leftHornBig2 = this.leftHornBig1.getChild("leftHornBig2");
        this.leftHornBig3 = this.leftHornBig1.getChild("leftHornBig3");
        this.rightHornBig2 = this.rightHornBig1.getChild("rightHornBig2");
        this.rightHornBig3 = this.rightHornBig1.getChild("rightHornBig3");
        this.lollipop = this.rightArm.getChild("lollipop");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition egg = parts.addOrReplaceChild("egg", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -9.0F, -4.5F, 14.0F, 9.0F, 9.0F, false), PartPose.offsetAndRotation(0.0F, 24.0F, 2.0F, 0, 0, 0));
        PartDefinition body = egg.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 40).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 12.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, -0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition leftLeg = egg.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, false), PartPose.offsetAndRotation(2.0F, -1.5F, -2.0F, 0.0F, -0.19547687289441354F, 0.0F));
        PartDefinition leftArm = egg.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, false), PartPose.offsetAndRotation(2.0F, -11.5F, 0.0F, 0.35185837453889574F, -0.17453292519943295F, 0.0F));
        PartDefinition rightLeg = egg.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, true), PartPose.offsetAndRotation(-2.0F, -1.5F, -2.0F, 0.0F, 0.19547687289441354F, 0.0F));
        PartDefinition rightArm = egg.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, false), PartPose.offsetAndRotation(-2.0F, -11.5F, 0.0F, 0.35185837453889574F, 0.19547687289441354F, 0.0F));
        PartDefinition neck1 = body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(37, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, 0.07400195628981794F, 0.0F, 0.0F));
        PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, false), PartPose.offsetAndRotation(1.5F, -6.0F, 3.0F, 0.35185837453889574F, -0.5082398928281348F, 0.11728612207217244F));
        PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, true), PartPose.offsetAndRotation(-1.5F, -6.0F, 3.0F, 0.35185837453889574F, 0.5082398928281348F, -0.11728612207217244F));
        PartDefinition head = neck1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(41, 13).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -3.5F, 1.0F, 0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition lowerJaw = head.addOrReplaceChild("lowerJaw", CubeListBuilder.create().texOffs(24, 18).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -1.1F, -2.8F, 0.3305653696487898F, 0.0F, 0.0F));
        PartDefinition upperJaw = head.addOrReplaceChild("upperJaw", CubeListBuilder.create().texOffs(39, 23).addBox(-2.0F, -1.5F, -5.0F, 4.0F, 3.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0, 0, 0));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(49, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, -3.3F, -2.3F, 0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition headScales = head.addOrReplaceChild("headScales", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, -8.0F, -2.0F, 0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(53, 4).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(1.3F, -4.7F, 0.8F, 0.2565633967142151F, 0.162315623764424F, 0.2925171866289913F));
        PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, -4.5F, -1.8F, 0.540353920438478F, 0.1350884801096195F, 0.3152064535891121F));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-2.0F, -3.3F, -2.3F, 0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-1.0F, -4.5F, -1.8F, 0.540353920438478F, -0.1350884801096195F, -0.3152064535891121F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(53, 4).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(-1.3F, -4.7F, 0.8F, 0.2565633967142151F, -0.162315623764424F, -0.2925171866289913F));
        PartDefinition leftHornBig1 = head.addOrReplaceChild("leftHornBig1", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, -4.8F, -1.0F, 0.0F, 0.0F, 0.3127630032889644F));
        PartDefinition rightHornBig1 = head.addOrReplaceChild("rightHornBig1", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-1.0F, -4.8F, -1.0F, 0.0F, 0.0F, -0.3127630032889644F));
        PartDefinition nose = upperJaw.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 54).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -2.2F, -4.6F, 0, 0, 0));
        PartDefinition tooth = upperJaw.addOrReplaceChild("tooth", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 1.0F, -4.7F, 0, 0, 0));
        PartDefinition jawHorn1 = upperJaw.addOrReplaceChild("jawHorn1", CubeListBuilder.create().texOffs(0, 24).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.2F, -3.6F, -0.1563815016444822F, 0.0F, 0.0F));
        PartDefinition jawHorn2 = jawHorn1.addOrReplaceChild("jawHorn2", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -2.7F, 0.0F, -0.1563815016444822F, 0.0F, 0.0F));
        PartDefinition leftHornBig2 = leftHornBig1.addOrReplaceChild("leftHornBig2", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.3F, -1.5F, 0.0F, 0.0F, 0.0F, 1.1344640137963142F));
        PartDefinition leftHornBig3 = leftHornBig1.addOrReplaceChild("leftHornBig3", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.7F, 0.0F, 0.5864306020384839F, 0.0F, 0.0F));
        PartDefinition rightHornBig2 = rightHornBig1.addOrReplaceChild("rightHornBig2", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-0.3F, -1.5F, 0.0F, 0.0F, 0.0F, -1.1344640137963142F));
        PartDefinition rightHornBig3 = rightHornBig1.addOrReplaceChild("rightHornBig3", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.7F, 0.0F, 0.5864306020384839F, 0.0F, 0.0F));
        PartDefinition lollipop = rightArm.addOrReplaceChild("lollipop", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -6.0F, -2.5F, 0.0F, 6.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -1.5F, -6.0F, 0, 0, 0));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float red, float green, float blue, float alpha) {
        this.egg.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, red, green, blue, alpha);
    }


    @Override
    public void setupAnim(DevilFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.showModels(entityIn);
        this.head.yRot = netHeadYaw * (PI / 180f) * 0.7f;
        this.head.xRot = 0.03f + headPitch * (PI / 180f) * 0.7f;
        this.egg.yRot = 0;
        this.egg.zRot = 0;


        if (entityIn.isPartying()) {
            this.egg.yRot = ageInTicks / 4;
            this.egg.zRot = Mth.cos(ageInTicks / 4) * this.toRads(10);
            this.leftLeg.xRot = this.toRads(30) + Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.4f;
            this.rightLeg.xRot = this.toRads(30) + Mth.cos(limbSwing * 0.5f) * limbSwingAmount * 0.4f;
            this.leftArm.xRot = Mth.cos(ageInTicks / 4) * this.toRads(10);
            this.rightArm.xRot = Mth.cos(ageInTicks / 4 + PI) * this.toRads(10);
        } else if (entityIn.isSitting()) {
            this.leftLeg.xRot = 0;
            this.rightLeg.xRot = 0;
        } else {
            this.leftLeg.xRot = this.toRads(30) + Mth.cos(limbSwing * 0.5f) * limbSwingAmount * 0.4f;
        }

        if (ageInTicks % 300 < 60) {
            float progress = ageInTicks % 300 % 60;
            float armHeight = Mth.sin(progress * PI / 60) * 0.4f;
            this.leftArm.xRot = 0.35f - armHeight + Mth.sin(progress * PI / 10) * 0.45f;
            this.rightArm.xRot = 0.35f - armHeight + Mth.sin(progress * PI / 10 + PI) * 0.45f;
        } else {
            this.leftArm.xRot = 0.35f;
            this.rightArm.xRot = 0.35f;
        }
    }

    @Override
    public void prepareMobModel(DevilFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                                float partialTick) {
        float animationHeight = entityIn.getAnimationHeight(partialTick);
        this.leftWing.yRot = animationHeight * this.toRads(20) - 0.43f;
        this.rightWing.yRot = -animationHeight * this.toRads(20) + 0.43f;

        if (entityIn.isSitting() && !entityIn.isPartying()) {
            this.leftWing.yRot = -0.43f;
            this.rightWing.yRot = 0.43f;
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private void showModels(DevilFamiliarEntity entityIn) {
        boolean hasNose = entityIn.hasNose();
        boolean hasEars = entityIn.hasEars();

        this.lollipop.visible = entityIn.hasLollipop();
        this.nose.visible = hasNose;
        this.jawHorn1.visible = !hasNose;
        this.leftEar.visible = hasEars;
        this.rightEar.visible = hasEars;
        this.leftHorn.visible = hasEars;
        this.rightHorn.visible = hasEars;
        this.leftHornBig1.visible = !hasEars;
        this.rightHornBig1.visible = !hasEars;
    }
}
