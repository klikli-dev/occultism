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

import com.klikli_dev.occultism.common.entity.familiar.BatFamiliarEntity;
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
public class BatFamiliarModel extends EntityModel<BatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body1;
    public ModelPart head;
    public ModelPart body2;
    public ModelPart leftEye;
    public ModelPart leftEar1;
    public ModelPart mouth;
    public ModelPart hair;
    public ModelPart ribbon;
    public ModelPart rightEye;
    public ModelPart rightEar1;
    public ModelPart leftEar2;
    public ModelPart leftEarBack1;
    public ModelPart leftEarBack2;
    public ModelPart nose;
    public ModelPart tooth;
    public ModelPart rightEar2;
    public ModelPart rightEarBack1;
    public ModelPart rightEarBack2;
    public ModelPart leftLeg;
    public ModelPart leftWing1;
    public ModelPart rightLeg;
    public ModelPart tail;
    public ModelPart rightWing1;
    public ModelPart stick;
    public ModelPart goblet1;
    public ModelPart goblet2;
    public ModelPart goblet3;
    public ModelPart goblet4;
    public ModelPart goblet5;
    public ModelPart goblet6;
    public ModelPart goblet7;
    public ModelPart leftWing2;
    public ModelPart leftWingBack1;
    public ModelPart leftWingBack2;
    public ModelPart rightWing2;
    public ModelPart rightWingBack1;
    public ModelPart rightWingBack2;
    public ModelPart leftChain1;
    public ModelPart rightChain1;
    public ModelPart leftChain2;
    public ModelPart leftChain3;
    public ModelPart rightChain2;
    public ModelPart rightChain3;

    public BatFamiliarModel(ModelPart part) {
        this.body1 = part.getChild("body1");
        this.head = this.body1.getChild("head");
        this.body2 = this.body1.getChild("body2");
        this.leftEye = this.head.getChild("leftEye");
        this.leftEar1 = this.head.getChild("leftEar1");
        this.mouth = this.head.getChild("mouth");
        this.hair = this.head.getChild("hair");
        this.ribbon = this.head.getChild("ribbon");
        this.rightEye = this.head.getChild("rightEye");
        this.rightEar1 = this.head.getChild("rightEar1");
        this.leftEar2 = this.leftEar1.getChild("leftEar2");
        this.leftEarBack1 = this.leftEar1.getChild("leftEarBack1");
        this.leftEarBack2 = this.leftEar2.getChild("leftEarBack2");
        this.nose = this.mouth.getChild("nose");
        this.tooth = this.mouth.getChild("tooth");
        this.rightEar2 = this.rightEar1.getChild("rightEar2");
        this.rightEarBack1 = this.rightEar1.getChild("rightEarBack1");
        this.rightEarBack2 = this.rightEar2.getChild("rightEarBack2");
        this.leftLeg = this.body2.getChild("leftLeg");
        this.leftWing1 = this.body2.getChild("leftWing1");
        this.rightLeg = this.body2.getChild("rightLeg");
        this.tail = this.body2.getChild("tail");
        this.rightWing1 = this.body2.getChild("rightWing1");
        this.stick = this.body2.getChild("stick");
        this.goblet1 = this.leftLeg.getChild("goblet1");
        this.goblet2 = this.goblet1.getChild("goblet2");
        this.goblet3 = this.goblet1.getChild("goblet3");
        this.goblet4 = this.goblet3.getChild("goblet4");
        this.goblet5 = this.goblet3.getChild("goblet5");
        this.goblet6 = this.goblet3.getChild("goblet6");
        this.goblet7 = this.goblet3.getChild("goblet7");
        this.leftWing2 = this.leftWing1.getChild("leftWing2");
        this.leftWingBack1 = this.leftWing1.getChild("leftWingBack1");
        this.leftWingBack2 = this.leftWing2.getChild("leftWingBack2");
        this.rightWing2 = this.rightWing1.getChild("rightWing2");
        this.rightWingBack1 = this.rightWing1.getChild("rightWingBack1");
        this.rightWingBack2 = this.rightWing2.getChild("rightWingBack2");
        this.leftChain1 = this.stick.getChild("leftChain1");
        this.rightChain1 = this.stick.getChild("rightChain1");
        this.leftChain2 = this.leftChain1.getChild("leftChain2");
        this.leftChain3 = this.leftChain2.getChild("leftChain3");
        this.rightChain2 = this.rightChain1.getChild("rightChain2");
        this.rightChain3 = this.rightChain2.getChild("rightChain3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body1 = parts.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(8, 10).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 0).addBox(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(23, 0).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 7.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0, 0, 0));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(1.4F, -3.2F, -3.1F, 0.0F, 0.0F, 0.1972222088043106F));
        PartDefinition leftEar1 = head.addOrReplaceChild("leftEar1", CubeListBuilder.create().texOffs(0, 27).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(2.0F, -4.5F, 0.5F, 0.0F, -0.23649211697418301F, 0.3549999725188077F));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -0.3F, -3.0F, 0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(0.5F, -4.8F, -1.5F, -0.03909537541112055F, 0.0F, -0.23457224414434488F));
        PartDefinition ribbon = head.addOrReplaceChild("ribbon", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, -2.51F, -0.17453292519943295F, 0.17453292519943295F, 0.7853981633974483F));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(-1.4F, -3.2F, -3.1F, 0.0F, 0.0F, -0.1972222088043106F));
        PartDefinition rightEar1 = head.addOrReplaceChild("rightEar1", CubeListBuilder.create().texOffs(0, 27).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, true), PartPose.offsetAndRotation(-2.0F, -4.5F, 0.5F, 0.0F, 0.23649211697418301F, -0.3549999725188077F));
        PartDefinition leftEar2 = leftEar1.addOrReplaceChild("leftEar2", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.4363323129985824F, 0.0F, 0.0F));
        PartDefinition leftEarBack1 = leftEar1.addOrReplaceChild("leftEarBack1", CubeListBuilder.create().texOffs(8, 20).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition leftEarBack2 = leftEar2.addOrReplaceChild("leftEarBack2", CubeListBuilder.create().texOffs(8, 18).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition nose = mouth.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -0.4F, -0.4F, 0, 0, 0));
        PartDefinition tooth = mouth.addOrReplaceChild("tooth", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(0.5F, 0.9F, -0.5F, 0, 0, 0));
        PartDefinition rightEar2 = rightEar1.addOrReplaceChild("rightEar2", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.4363323129985824F, 0.0F, 0.0F));
        PartDefinition rightEarBack1 = rightEar1.addOrReplaceChild("rightEarBack1", CubeListBuilder.create().texOffs(8, 20).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition rightEarBack2 = rightEar2.addOrReplaceChild("rightEarBack2", CubeListBuilder.create().texOffs(8, 18).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition leftLeg = body2.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(24, 20).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(1.1F, 6.3F, 0.0F, 0.23666665389412408F, 0.0F, 0.017453292519943295F));
        PartDefinition leftWing1 = body2.addOrReplaceChild("leftWing1", CubeListBuilder.create().texOffs(44, 26).addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, false), PartPose.offsetAndRotation(1.8F, 4.0F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition rightLeg = body2.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(24, 20).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, true), PartPose.offsetAndRotation(-1.1F, 6.3F, 0.0F, 0.23666665389412408F, 0.0F, -0.017453292519943295F));
        PartDefinition tail = body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(34, 11).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 4.8F, 1.5F, 0.23666665389412408F, 0.0F, 0.0F));
        PartDefinition rightWing1 = body2.addOrReplaceChild("rightWing1", CubeListBuilder.create().texOffs(44, 26).addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, true), PartPose.offsetAndRotation(-1.8F, 4.0F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition stick = body2.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(10, 14).addBox(-7.0F, 0.0F, 0.0F, 14.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0, 0, 0));
        PartDefinition goblet1 = leftLeg.addOrReplaceChild("goblet1", CubeListBuilder.create().texOffs(10, 30).addBox(0.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-3.5F, 3.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition goblet2 = goblet1.addOrReplaceChild("goblet2", CubeListBuilder.create().texOffs(10, 26).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition goblet3 = goblet1.addOrReplaceChild("goblet3", CubeListBuilder.create().texOffs(22, 26).addBox(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(5.01F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition goblet4 = goblet3.addOrReplaceChild("goblet4", CubeListBuilder.create().texOffs(31, 27).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.5F, -1.5F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition goblet5 = goblet3.addOrReplaceChild("goblet5", CubeListBuilder.create().texOffs(35, 27).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.5F, 1.5F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition goblet6 = goblet3.addOrReplaceChild("goblet6", CubeListBuilder.create().texOffs(34, 29).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.5F, 0.0F, 1.5F, 0.0F, 0.0F, 0.7853981633974483F));
        PartDefinition goblet7 = goblet3.addOrReplaceChild("goblet7", CubeListBuilder.create().texOffs(38, 29).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.5F, 0.0F, -1.5F, 0.0F, 0.0F, 0.7853981633974483F));
        PartDefinition leftWing2 = leftWing1.addOrReplaceChild("leftWing2", CubeListBuilder.create().texOffs(44, 20).addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0, 0, 0));
        PartDefinition leftWingBack1 = leftWing1.addOrReplaceChild("leftWingBack1", CubeListBuilder.create().texOffs(44, 14).addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition leftWingBack2 = leftWing2.addOrReplaceChild("leftWingBack2", CubeListBuilder.create().texOffs(44, 8).addBox(0.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition rightWing2 = rightWing1.addOrReplaceChild("rightWing2", CubeListBuilder.create().texOffs(44, 20).addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0, 0, 0));
        PartDefinition rightWingBack1 = rightWing1.addOrReplaceChild("rightWingBack1", CubeListBuilder.create().texOffs(44, 14).addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition rightWingBack2 = rightWing2.addOrReplaceChild("rightWingBack2", CubeListBuilder.create().texOffs(44, 8).addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.03F, 0, 0, 0));
        PartDefinition leftChain1 = stick.addOrReplaceChild("leftChain1", CubeListBuilder.create().texOffs(41, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(7.0F, 1.0F, 0.5F, 0.0F, -0.5235987755982988F, 0.0F));
        PartDefinition rightChain1 = stick.addOrReplaceChild("rightChain1", CubeListBuilder.create().texOffs(41, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(-7.0F, 1.0F, 0.5F, 0.0F, -0.5235987755982988F, 0.0F));
        PartDefinition leftChain2 = leftChain1.addOrReplaceChild("leftChain2", CubeListBuilder.create().texOffs(47, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 1.0471975511965976F, 0.0F));
        PartDefinition leftChain3 = leftChain2.addOrReplaceChild("leftChain3", CubeListBuilder.create().texOffs(53, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition rightChain2 = rightChain1.addOrReplaceChild("rightChain2", CubeListBuilder.create().texOffs(47, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 1.0471975511965976F, 0.0F));
        PartDefinition rightChain3 = rightChain2.addOrReplaceChild("rightChain3", CubeListBuilder.create().texOffs(53, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body1.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(BatFamiliarEntity pEntity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.showModels(pEntity);

        float partialTicks = Minecraft.getInstance().getFrameTime();

        this.body1.yRot = 0;
        this.body1.zRot = 0;
        this.head.xRot = this.toRads(headPitch);
        this.head.yRot = this.toRads(netHeadYaw);
        this.head.zRot = 0;
        this.leftEar1.xRot = 0;
        this.rightEar1.xRot = 0;

        float animationHeight = pEntity.getAnimationHeight(partialTicks);
        this.leftWing1.xRot = animationHeight * this.toRads(20) - 0.15f;
        this.rightWing1.xRot = animationHeight * this.toRads(20) - 0.15f;
        this.leftWing1.yRot = animationHeight * this.toRads(20) - 0.15f;
        this.rightWing1.yRot = -animationHeight * this.toRads(20) + 0.15f;
        this.leftWing2.xRot = animationHeight * this.toRads(20) + this.toRads(15);
        this.rightWing2.xRot = animationHeight * this.toRads(20) + this.toRads(15);
        this.body1.xRot = this.toRads(20) + limbSwingAmount * this.toRads(30);
        this.leftLeg.xRot = 0.24f + Mth.cos(ageInTicks * 0.1f) * this.toRads(20);
        this.rightLeg.xRot = 0.24f + Mth.cos(ageInTicks * 0.1f) * this.toRads(20);

        if (pEntity.isPartying()) {
            float headRot = Mth.sin(ageInTicks / 3) * this.toRads(10);
            float wingRot = Mth.sin(ageInTicks / 3) * this.toRads(40);
            this.head.xRot = headRot;
            this.head.yRot = headRot;
            this.head.zRot = headRot;
            this.leftWing1.xRot = wingRot;
            this.rightWing1.xRot = wingRot;
            this.leftWing1.yRot = wingRot;
            this.rightWing1.yRot = wingRot;
            this.leftWing2.xRot = wingRot;
            this.rightWing2.xRot = wingRot;
            this.body1.xRot = this.toRads(20) + limbSwingAmount * this.toRads(70);
            this.leftEar1.xRot = Mth.cos(ageInTicks / 3 + PI) * this.toRads(25);
            this.rightEar1.xRot = Mth.cos(ageInTicks / 3) * this.toRads(25);
        } else if (pEntity.isSitting()) {
            this.leftWing1.xRot = this.toRads(0);
            this.rightWing1.xRot = this.toRads(0);
            this.leftWing1.yRot = this.toRads(80);
            this.rightWing1.yRot = -this.toRads(80);
            this.leftWing2.xRot = this.toRads(15);
            this.rightWing2.xRot = this.toRads(15);
            this.head.xRot = 0.2f;
            this.body1.xRot = this.toRads(180);
            this.body1.yRot = this.toRads(180);
            this.leftLeg.xRot = 0.24f;
            this.rightLeg.xRot = 0.24f;
        }
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

    private void showModels(BatFamiliarEntity entityIn) {
        boolean isSitting = entityIn.isSitting();
        boolean isPartying = entityIn.isPartying();

        this.stick.visible = isSitting && !isPartying;
        this.goblet1.visible = entityIn.hasBlacksmithUpgrade() && (!isSitting || isPartying);
        this.hair.visible = entityIn.hasHair();
        this.ribbon.visible = entityIn.hasRibbon();
        this.tail.visible = entityIn.hasTail();
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
