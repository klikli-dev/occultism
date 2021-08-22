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

import com.github.klikli_dev.occultism.common.entity.BatFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

/**
 * Created using Tabula 8.0.0
 */
public class BatFamiliarModel extends EntityModel<BatFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart stick;
    public ModelPart head;
    public ModelPart leftWing1;
    public ModelPart rightWing1;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart nose;
    public ModelPart leftLeg;
    public ModelPart leftWing2;
    public ModelPart rightLeg;
    public ModelPart rightWing2;
    public ModelPart leftChain1;
    public ModelPart rightChain1;
    public ModelPart leftChain2;
    public ModelPart leftChain3;
    public ModelPart rightChain2;
    public ModelPart rightChain3;

    public BatFamiliarModel(ModelPart part) {
        super(RenderType::entityTranslucent);

        this.stick = part.getChild("stick");
        this.leftChain1 = this.stick.getChild("leftChain1");
        this.leftChain2 = this.leftChain1.getChild("leftChain2");
        this.leftChain3 = this.leftChain2.getChild("leftChain3");
        this.rightChain1 = this.stick.getChild("rightChain1");
        this.rightChain2 = this.rightChain1.getChild("rightChain2");
        this.rightChain3 = this.rightChain2.getChild("rightChain3");

        this.body = part.getChild("body");
        this.head = this.body.getChild("head");
        this.nose = this.head.getChild("nose");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEar = this.head.getChild("rightEar");

        this.leftWing1 = this.body.getChild("leftWing1");
        this.leftWing2 = this.leftWing1.getChild("leftWing2");
        this.leftLeg = this.leftWing1.getChild("leftLeg");

        this.rightWing1 = this.body.getChild("rightWing1");
        this.rightWing2 = this.rightWing1.getChild("rightWing2");
        this.rightLeg = this.rightWing1.getChild("rightLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition mesh = meshdefinition.getRoot();


        PartDefinition stick = mesh.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(24, 0)
                        .addBox(-7.0F, 0.0F, 0.0F, 14.0F, 1.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition leftChain1 = stick.addOrReplaceChild("leftChain1", CubeListBuilder.create().texOffs(26, 2)
                        .addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(7.0F, 0.0F, 0.5F, 0.0F, -0.5235987755982988F, 0.0F));
        PartDefinition leftChain2 = leftChain1.addOrReplaceChild("leftChain2", CubeListBuilder.create().texOffs(32, 2)
                        .addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 1.0471975511965976F, 0.0F));
        PartDefinition leftChain3 = leftChain2.addOrReplaceChild("leftChain3", CubeListBuilder.create().texOffs(38, 2)
                        .addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));


        PartDefinition rightChain1 = stick.addOrReplaceChild("rightChain1", CubeListBuilder.create().texOffs(26, 2)
                        .addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-7.0F, 0.0F, 0.5F, 0.0F, -0.5235987755982988F, 0.0F));
        PartDefinition rightChain2 = rightChain1.addOrReplaceChild("rightChain2", CubeListBuilder.create().texOffs(32, 2)
                        .addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 1.0471975511965976F, 0.0F));
        PartDefinition rightChain3 = rightChain2.addOrReplaceChild("rightChain3", CubeListBuilder.create().texOffs(38, 2)
                        .addBox(-1.5F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));


        PartDefinition body = mesh.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(2.5F, -5.0F, -2.5F, 5.0F, 8.0F, 3.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 16.5F, 0.0F, 3.141592653589793F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(11, 6)
                        .addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -5.0F, -1.0F, -0.19547687289441354F, 0.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 6)
                        .addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F,
                                CubeDeformation.NONE),
                PartPose.offset(0.0F, -2.0F, -2.0F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(16, 0)
                        .addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(2.0F, -4.0F, 0.0F, -0.0781907508222411F, -0.3127630032889644F, 0.5082398928281348F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(16, 0)
                        .addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, -4.0F, 0.0F, -0.0781907508222411F, 0.3127630032889644F, -0.5082398928281348F));

        PartDefinition leftWing1 = body.addOrReplaceChild("leftWing1", CubeListBuilder.create().texOffs(0, 21)
                        .addBox(-2.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, -0.1563815016444822F, 0.0F));
        PartDefinition leftWing2 = leftWing1.addOrReplaceChild("leftWing2", CubeListBuilder.create().texOffs(16, 21)
                        .addBox(0.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, -0.1563815016444822F, 0.0F));
        PartDefinition leftLeg = leftWing1.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 11)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.35185837453889574F, 0.0F, 0.0F));


        PartDefinition rightWing1 = body.addOrReplaceChild("rightWing1", CubeListBuilder.create().texOffs(0, 21)
                        .addBox(-6.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.1563815016444822F, 0.0F));
        PartDefinition rightWing2 = rightWing1.addOrReplaceChild("rightWing2", CubeListBuilder.create().texOffs(16, 21)
                        .addBox(-8.0F, -5.0F, 0.0F, 8.0F, 10.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, 0.1563815016444822F, 0.0F));
        PartDefinition rightLeg = rightWing1.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 11)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.35185837453889574F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body, this.stick).forEach((modelRenderer) -> {
            modelRenderer.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, red, green, blue, alpha);
        });
    }

    @Override
    public void prepareMobModel(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                                float partialTick) {
        if (entityIn.isSitting()) {
            this.leftWing1.yRot = this.toRads(60);
            this.leftWing2.yRot = this.toRads(60);
            this.rightWing1.yRot = -this.toRads(60);
            this.rightWing2.yRot = -this.toRads(60);
            this.body.xRot = this.toRads(180);
            this.stick.visible = true;
        } else {
            float animationHeight = entityIn.getAnimationHeight(partialTick);
            this.leftWing1.yRot = animationHeight * this.toRads(20);
            this.leftWing2.yRot = animationHeight * this.toRads(20);
            this.rightWing1.yRot = -animationHeight * this.toRads(20);
            this.rightWing2.yRot = -animationHeight * this.toRads(20);
            this.body.xRot = this.toRads(0);
            this.stick.visible = false;
        }
    }

    @Override
    public void setupAnim(BatFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {

        if (entityIn.isSitting()) {
            this.head.xRot = 0.2f;
            this.head.yRot = 0;
            this.head.zRot = 0;
            this.body.xRot = (float) Math.toRadians(180);
            this.body.yRot = (float) Math.toRadians(180);
        } else {
            this.head.yRot = this.toRads(netHeadYaw) * 0.35f;
            this.head.zRot = this.toRads(headPitch) * 0.35f;

            this.body.xRot = this.toRads(20) + limbSwingAmount * this.toRads(70);
            this.body.yRot = 0;
        }
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }
}
