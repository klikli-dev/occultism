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

import com.klikli_dev.occultism.common.entity.familiar.FairyFamiliarEntity;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class FairyFamiliarModel extends EntityModel<FairyFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart head;
    public ModelPart tail1;
    public ModelPart leftArm;
    public ModelPart leftLeg1;
    public ModelPart leftWing;
    public ModelPart rightLeg1;
    public ModelPart rightArm;
    public ModelPart rightWing;
    public ModelPart nose;
    public ModelPart leftHorn1;
    public ModelPart leftEar1;
    public ModelPart leftEye;
    public ModelPart rightEye;
    public ModelPart rightEar1;
    public ModelPart rightHorn1;
    public ModelPart whisker1;
    public ModelPart whisker2;
    public ModelPart whisker3;
    public ModelPart whisker4;
    public ModelPart tooth1;
    public ModelPart tooth2;
    public ModelPart tooth3;
    public ModelPart leftHorn2;
    public ModelPart flower;
    public ModelPart leftEar2;
    public ModelPart rightEar2;
    public ModelPart rightHorn2;
    public ModelPart tail2;
    public ModelPart tail3;
    public ModelPart leftWand;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;
    public ModelPart rightWand;

    public FairyFamiliarModel(ModelPart part) {
        super(RenderType::entityTranslucent);
        this.body = part.getChild("body");
        this.head = this.body.getChild("head");
        this.tail1 = this.body.getChild("tail1");
        this.leftArm = this.body.getChild("leftArm");
        this.leftLeg1 = this.body.getChild("leftLeg1");
        this.leftWing = this.body.getChild("leftWing");
        this.rightLeg1 = this.body.getChild("rightLeg1");
        this.rightArm = this.body.getChild("rightArm");
        this.rightWing = this.body.getChild("rightWing");
        this.nose = this.head.getChild("nose");
        this.leftHorn1 = this.head.getChild("leftHorn1");
        this.leftEar1 = this.head.getChild("leftEar1");
        this.leftEye = this.head.getChild("leftEye");
        this.rightEye = this.head.getChild("rightEye");
        this.rightEar1 = this.head.getChild("rightEar1");
        this.rightHorn1 = this.head.getChild("rightHorn1");
        this.whisker1 = this.nose.getChild("whisker1");
        this.whisker2 = this.nose.getChild("whisker2");
        this.whisker3 = this.nose.getChild("whisker3");
        this.whisker4 = this.nose.getChild("whisker4");
        this.tooth1 = this.nose.getChild("tooth1");
        this.tooth2 = this.nose.getChild("tooth2");
        this.tooth3 = this.nose.getChild("tooth3");
        this.leftHorn2 = this.leftHorn1.getChild("leftHorn2");
        this.flower = this.leftHorn1.getChild("flower");
        this.leftEar2 = this.leftEar1.getChild("leftEar2");
        this.rightEar2 = this.rightEar1.getChild("rightEar2");
        this.rightHorn2 = this.rightHorn1.getChild("rightHorn2");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.leftWand = this.leftArm.getChild("leftWand");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.leftLeg3 = this.leftLeg1.getChild("leftLeg3");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.rightLeg3 = this.rightLeg1.getChild("rightLeg3");
        this.rightWand = this.rightArm.getChild("rightWand");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -6.0F, -1.5F, 5.0F, 6.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -6.0F, -0.5F, 0, 0, 0));
        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(45, 4).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, -0.5082398928281348F, 0.0F, 0.0F));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(53, 8).addBox(0.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(1.2F, -0.8F, 0.0F, 0, 0, 0));
        PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 1.0F, 0.0F, -0.5864306020384839F, 0.0F));
        PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-1.2F, -0.8F, 0.0F, 0, 0, 0));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(53, 8).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(0, 21).addBox(-10.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, -4.0F, 1.0F, 0.0F, 0.5864306020384839F, 0.0F));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(36, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.5F, -2.5F, 0, 0, 0));
        PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(48, 0).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.2F, -4.5F, 1.0F, -0.3909537457888271F, 0.0F, 0.0F));
        PartDefinition leftEar1 = head.addOrReplaceChild("leftEar1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(2.1F, -3.4F, 1.9F, 0.3127630032889644F, -0.27366763203903305F, -0.4300491170387584F));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(54, 0).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -3.0F, -0.7F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(54, 0).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, -3.0F, -0.7F, 0.3127630032889644F, 0.0F, 0.0F));
        PartDefinition rightEar1 = head.addOrReplaceChild("rightEar1", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, true), PartPose.offsetAndRotation(-2.1F, -3.4F, 1.9F, 0.3127630032889644F, 0.27366763203903305F, 0.4300491170387584F));
        PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(48, 0).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-2.2F, -4.5F, 1.0F, -0.3909537457888271F, 0.0F, 0.0F));
        PartDefinition whisker1 = nose.addOrReplaceChild("whisker1", CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(1.0F, 0.4F, -1.0F, 0.0F, 0.3127630032889644F, 0.3909537457888271F));
        PartDefinition whisker2 = nose.addOrReplaceChild("whisker2", CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(1.0F, -0.4F, -2.0F, 0.03909537541112055F, 0.3909537457888271F, -0.27366763203903305F));
        PartDefinition whisker3 = nose.addOrReplaceChild("whisker3", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, true), PartPose.offsetAndRotation(-1.0F, -0.4F, -2.0F, 0.03909537541112055F, -0.3909537457888271F, 0.27366763203903305F));
        PartDefinition whisker4 = nose.addOrReplaceChild("whisker4", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, true), PartPose.offsetAndRotation(-1.0F, 0.4F, -1.0F, 0.0F, -0.3127630032889644F, -0.3909537457888271F));
        PartDefinition tooth1 = nose.addOrReplaceChild("tooth1", CubeListBuilder.create().texOffs(31, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.4F, 0.4F, -1.5F, 0, 0, 0));
        PartDefinition tooth2 = nose.addOrReplaceChild("tooth2", CubeListBuilder.create().texOffs(31, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-1.4F, 0.5F, -1.7F, 0, 0, 0));
        PartDefinition tooth3 = nose.addOrReplaceChild("tooth3", CubeListBuilder.create().texOffs(31, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.7F, 0.6F, -2.9F, 0, 0, 0));
        PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.5F, 0.0F, -0.35185837453889574F, 0.0F, 0.0F));
        PartDefinition flower = leftHorn1.addOrReplaceChild("flower", CubeListBuilder.create().texOffs(25, 7).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.52F, -1.0F, 0.0F, 0.782431135626991F, 0.0F, 0.0F));
        PartDefinition leftEar2 = leftEar1.addOrReplaceChild("leftEar2", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.1111502472333745F, 0.0F, 0.0F));
        PartDefinition rightEar2 = rightEar1.addOrReplaceChild("rightEar2", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.1111502472333745F, 0.0F, 0.0F));
        PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, true), PartPose.offsetAndRotation(0.01F, -1.5F, 0.0F, -0.35185837453889574F, 0.0F, 0.0F));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(36, 6).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.01F, 0.0F, 2.5F, 0.27366763203903305F, 0.0F, 0.0F));
        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(55, 4).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.27366763203903305F, 0.0F, 0.0F));
        PartDefinition leftWand = leftArm.addOrReplaceChild("leftWand", CubeListBuilder.create().texOffs(8, 9).addBox(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 5.0F, false), PartPose.offsetAndRotation(1.0F, 4.0F, -0.2F, 0, 0, 0));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(17, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-0.3F, 4.01F, -0.3F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition leftLeg3 = leftLeg1.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(17, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.3F, 4.01F, -0.3F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(17, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-0.3F, 4.01F, -0.3F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition rightLeg3 = rightLeg1.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(17, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.3F, 4.01F, -0.3F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition rightWand = rightArm.addOrReplaceChild("rightWand", CubeListBuilder.create().texOffs(8, 9).addBox(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 5.0F, true), PartPose.offsetAndRotation(-1.0F, 4.0F, -0.2F, 0, 0, 0));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(FairyFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks,
                          float netHeadYaw, float headPitch) {
        this.showModels(pEntity);

        float partialTicks = Minecraft.getInstance().getFrameTime();

        ModelPart mainArm = this.getMainArm(pEntity);

        this.leftArm.yRot = 0;
        this.rightArm.yRot = 0;
        this.leftWand.xRot = 0;
        this.rightWand.xRot = 0;

        this.head.xRot = 0;
        this.head.yRot = this.toRads(netHeadYaw) * 0.8f;
        this.head.zRot = this.toRads(headPitch) * 0.8f;

        float animationHeight = pEntity.getWingRot(partialTicks);
        this.leftWing.yRot = animationHeight * this.toRads(15) - 0.59f;
        this.rightWing.yRot = -animationHeight * this.toRads(15) + 0.59f;

        float bodyRot = limbSwingAmount * this.toRads(100);
        this.body.xRot = bodyRot;
        this.leftArm.xRot = -bodyRot * 3 + Mth.cos(pAgeInTicks * 0.2f) * this.toRads(10);
        this.rightArm.xRot = -bodyRot * 3 + Mth.cos(pAgeInTicks * 0.2f + PI) * this.toRads(10);
        this.leftLeg1.xRot = this.body.xRot;
        this.rightLeg1.xRot = this.body.xRot;

        this.tail1.xRot = -0.51f + Mth.cos(pAgeInTicks * 0.2f) * this.toRads(10);
        this.tail2.xRot = 0.27f + Mth.cos(pAgeInTicks * 0.2f) * this.toRads(10);
        this.tail3.xRot = 0.27f + Mth.cos(pAgeInTicks * 0.2f) * this.toRads(10);

        if (pEntity.hasMagicTarget()) {
            this.body.xRot = this.toRads(20);
            this.setRotateAngle(this.head, 0, 0, 0);
            this.leftArm.xRot = this.toRads(-80) + Mth.cos(pAgeInTicks * 0.2f) * this.toRads(20);
            this.rightArm.xRot = this.toRads(-80) + Mth.cos(pAgeInTicks * 0.2f) * this.toRads(20);
            this.leftLeg1.xRot = this.toRads(20);
            this.rightLeg1.xRot = this.toRads(20);
        } else {
            if (pEntity.isPartying()) {
                this.body.xRot = 0;
                mainArm.xRot = pEntity.getPartyArmRotX(partialTicks);
                mainArm.yRot = pEntity.getPartyArmRotY(partialTicks);
            } else if (pEntity.isSitting()) {
                this.body.xRot = this.toRads(90);
                this.head.xRot = this.toRads(-10);
                this.head.yRot = 0;
                this.head.zRot = 0;
                this.leftArm.xRot = this.toRads(-80) + Mth.cos(pAgeInTicks * 0.1f) * this.toRads(10);
                this.rightArm.xRot = this.toRads(-80) + Mth.cos(pAgeInTicks * 0.1f + PI) * this.toRads(10);
                this.leftWand.xRot = this.toRads(60);
                this.rightWand.xRot = this.toRads(60);
                this.leftLeg1.xRot = this.toRads(-80) + Mth.cos(pAgeInTicks * 0.1f + PI) * this.toRads(10);
                this.rightLeg1.xRot = this.toRads(-80) + Mth.cos(pAgeInTicks * 0.1f) * this.toRads(10);
                this.tail1.xRot = this.toRads(-130) + Mth.cos(pAgeInTicks * 0.1f) * this.toRads(5);
                this.tail2.xRot = this.toRads(-15) + Mth.cos(pAgeInTicks * 0.05f) * this.toRads(5);
                this.tail3.xRot = this.toRads(-15) + Mth.cos(pAgeInTicks * 0.05f) * this.toRads(5);
            }
        }

        float supportAnim = pEntity.getSupportAnim(partialTicks);
        if (supportAnim != 0) {
            mainArm.xRot = this.toRads(-80) + Mth.sin(supportAnim * PI * 3) * this.toRads(35);
            mainArm.yRot = Mth.cos(supportAnim * PI * 3) * this.toRads(35);
        }

    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    private ModelPart getMainArm(FairyFamiliarEntity pEntity) {
        return pEntity.isLeftHanded() ? this.leftArm : this.rightArm;
    }

    private void showModels(FairyFamiliarEntity entityIn) {
        boolean hasTeeth = entityIn.hasTeeth();
        boolean isLeftHanded = entityIn.isLeftHanded();

        this.flower.visible = entityIn.hasFlower();
        this.tooth1.visible = hasTeeth;
        this.tooth2.visible = hasTeeth;
        this.tooth3.visible = hasTeeth;
        this.whisker1.visible = !hasTeeth;
        this.whisker2.visible = !hasTeeth;
        this.whisker3.visible = !hasTeeth;
        this.whisker4.visible = !hasTeeth;
        this.leftWand.visible = isLeftHanded;
        this.rightWand.visible = !isLeftHanded;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
