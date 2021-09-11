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

import com.github.klikli_dev.occultism.common.entity.DeerFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Created using Tabula 8.0.0
 */
public class DeerFamiliarModel extends EntityModel<DeerFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart leftFrontLeg1;
    public ModelPart tail;
    public ModelPart neck;
    public ModelPart rightFrontLeg1;
    public ModelPart leftBackLeg1;
    public ModelPart rightBackLeg1;
    public ModelPart leftFrontLeg2;
    public ModelPart head;
    public ModelPart nose;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart leftHorn1;
    public ModelPart rightHorn1;
    public ModelPart leftHorn2;
    public ModelPart leftHorn3;
    public ModelPart rightHorn2;
    public ModelPart rightHorn3;
    public ModelPart rightFrontLeg2;
    public ModelPart leftBackLeg2;
    public ModelPart rightBackLeg2;

    public DeerFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.nose = this.head.getChild("nose");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEar = this.head.getChild("rightEar");
        this.leftHorn1 = this.head.getChild("leftHorn1");
        this.leftHorn2 = this.leftHorn1.getChild("leftHorn2");
        this.leftHorn3 = this.leftHorn2.getChild("leftHorn3");
        this.rightHorn1 = this.head.getChild("rightHorn1");
        this.rightHorn2 = this.rightHorn1.getChild("rightHorn2");
        this.rightHorn3 = this.rightHorn2.getChild("rightHorn3");

        this.tail = this.body.getChild("tail");

        this.rightFrontLeg1 = this.body.getChild("rightFrontLeg1");
        this.rightFrontLeg2 = this.rightFrontLeg1.getChild("rightFrontLeg2");
        this.rightBackLeg1 = this.body.getChild("rightBackLeg1");
        this.rightBackLeg2 = this.rightBackLeg1.getChild("rightBackLeg2");

        this.leftFrontLeg1 = this.body.getChild("leftFrontLeg1");
        this.leftFrontLeg2 = this.leftFrontLeg1.getChild("leftFrontLeg2");
        this.leftBackLeg1 = this.body.getChild("leftBackLeg1");
        this.leftBackLeg2 = this.leftBackLeg1.getChild("leftBackLeg2");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition mesh = meshdefinition.getRoot();

        PartDefinition body = mesh.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.0F, -5.0F, -5.0F, 4.0F, 5.0F, 10.0F,
                                CubeDeformation.NONE),
                PartPose.offset(0.0F, 16.4F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(30, 0)
                        .addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -4.0F, -4.0F, 0.3909537457888271F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(38, 0)
                        .addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F,
                                CubeDeformation.NONE),
                PartPose.offset(0.0F, -2.5F, -0.3F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(50, 0)
                        .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.0F, -1.5F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(50, 0)
                        .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, -2.5F, 0.5F, -0.3909537457888271F, 0.0F, 0.7819074915776542F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(22, 3)
                        .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(-1.0F, -2.5F, 0.5F, -0.3909537457888271F, 0.0F, -0.7819074915776542F));

        PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(50, 4)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(0.7F, -2.5F, 0.3F, 0.0F, 0.0F, 0.3127630032889644F));
        PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(58, 0)
                        .addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(0.3F, -2.5F, 0.0F, 0.0F, 0.0F, 1.1344640137963142F));
        PartDefinition leftHorn3 = leftHorn2.addOrReplaceChild("leftHorn3", CubeListBuilder.create().texOffs(18, 5)
                        .addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(0.0F, -2.7F, 0.0F, 0.5864306020384839F, 0.0F, 0.0F));

        PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(50, 4)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(-0.7F, -2.5F, 0.3F, 0.0F, 0.0F, -0.3127630032889644F));
        PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(58, 0)
                        .addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(-0.3F, -2.5F, 0.0F, 0.0F, 0.0F, -1.1344640137963142F));
        PartDefinition rightHorn3 = rightHorn2.addOrReplaceChild("rightHorn3", CubeListBuilder.create().texOffs(18, 5)
                        .addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(0.0F, -2.7F, 0.0F, 0.5864306020384839F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 0)
                        .addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -4.0F, 4.5F, -0.4363323129985824F, 0.0F, 0.0F));

        PartDefinition leftFrontLeg1 = body.addOrReplaceChild("leftFrontLeg1", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.4F, -0.2F, -3.9F, -0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition leftFrontLeg2 = leftFrontLeg1.addOrReplaceChild("leftFrontLeg2", CubeListBuilder.create().texOffs(18, 0)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-0.01F, 3.8F, 0.0F, 0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition leftBackLeg1 = body.addOrReplaceChild("leftBackLeg1", CubeListBuilder.create().texOffs(28, 9)
                        .addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.4F, -0.2F, 3.9F, 0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition leftBackLeg2 = leftBackLeg1.addOrReplaceChild("leftBackLeg2", CubeListBuilder.create().texOffs(34, 10)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-0.01F, 3.8F, 0.0F, -0.11728612207217244F, 0.0F, 0.0F));


        PartDefinition rightFrontLeg1 = body.addOrReplaceChild("rightFrontLeg1", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.4F, -0.2F, -3.9F, -0.11728612207217244F, 0.0F, 0.0F));

        PartDefinition rightFrontLeg2 = rightFrontLeg1.addOrReplaceChild("rightFrontLeg2", CubeListBuilder.create().texOffs(18, 0)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F,
                                CubeDeformation.NONE).mirror(),
                PartPose.offsetAndRotation(0.01F, 3.8F, 0.0F, 0.11728612207217244F, 0.0F, 0.0F));

        PartDefinition rightBackLeg1 = body.addOrReplaceChild("rightBackLeg1", CubeListBuilder.create().texOffs(28, 9)
                        .addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.4F, -0.2F, 3.9F, 0.11728612207217244F, 0.0F, 0.0F));
        PartDefinition rightBackLeg2 = rightBackLeg1.addOrReplaceChild("rightBackLeg2", CubeListBuilder.create().texOffs(34, 10)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.01F, 3.8F, 0.0F, -0.11728612207217244F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 16);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((ModelPart) -> {
            ModelPart.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, red, green, blue, alpha);
        });
    }

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    @Override
    public void prepareMobModel(DeerFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                                float partialTick) {
        this.neck.xRot = entityIn.getNeckRot(partialTick);
    }

    @Override
    public void setupAnim(DeerFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        if (entityIn.isEating()) {
            this.head.xRot = Mth.cos(ageInTicks * 0.8f) * 0.2f;
            this.head.yRot = 0;
        } else {
            this.head.yRot = netHeadYaw * (PI / 180f) * 0.5f;
            this.head.xRot = headPitch * (PI / 180f) * 0.5f;
        }

        this.tail.xRot = Mth.cos(limbSwing * 0.7f) * 0.4f * limbSwingAmount - 0.3f;
        this.body.xRot = 0;

        if (entityIn.isPartying()) {
            this.body.xRot = this.toRad(-20);
            this.setRotateAngle(this.rightBackLeg1, Mth.cos(ageInTicks / 2 + PI) * this.toRad(5) + this.toRad(20), 0, 0);
            this.setRotateAngle(this.leftBackLeg1, Mth.cos(ageInTicks / 2) * this.toRad(5) + this.toRad(20), 0, 0);
            this.setRotateAngle(this.rightFrontLeg1, Mth.cos(ageInTicks / 2) * this.toRad(30) - this.toRad(40), 0, 0);
            this.setRotateAngle(this.leftFrontLeg1, Mth.cos(ageInTicks / 2 + PI) * this.toRad(30) - this.toRad(40), 0, 0);

            this.rightBackLeg2.z = 0;
            this.leftBackLeg2.z = 0;
            this.rightFrontLeg2.z = 0;
            this.leftFrontLeg2.z = 0;

            this.rightBackLeg2.xRot = -0.1f;
            this.leftBackLeg2.xRot = -0.1f;
            this.rightFrontLeg2.xRot = 0.1f;
            this.leftFrontLeg2.xRot = 0.1f;
        } else if (entityIn.isSitting()) {
            this.setRotateAngle(this.rightBackLeg1, this.toRad(80), this.toRad(10), -this.toRad(40));
            this.setRotateAngle(this.leftBackLeg1, this.toRad(80), this.toRad(10), -this.toRad(40));
            this.setRotateAngle(this.rightFrontLeg1, -this.toRad(80), -this.toRad(10), -this.toRad(40));
            this.setRotateAngle(this.leftFrontLeg1, -this.toRad(80), -this.toRad(10), -this.toRad(40));

            this.rightBackLeg2.z = -0.5f;
            this.leftBackLeg2.z = -0.5f;
            this.rightFrontLeg2.z = 0.5f;
            this.leftFrontLeg2.z = 0.5f;

            this.rightBackLeg2.xRot = -this.toRad(150);
            this.leftBackLeg2.xRot = -this.toRad(150);
            this.rightFrontLeg2.xRot = this.toRad(150);
            this.leftFrontLeg2.xRot = this.toRad(150);
        } else {
            boolean fast = entityIn.getAttributeValue(Attributes.MOVEMENT_SPEED) > 0.4;
            this.setRotateAngle(this.rightBackLeg1,
                    Mth.cos(limbSwing * 0.7f + (fast ? PI : 0)) * 1.4f * limbSwingAmount, 0, 0);
            this.setRotateAngle(this.leftBackLeg1, Mth.cos(limbSwing * 0.7f + PI) * 1.4f * limbSwingAmount, 0,
                    0);
            this.setRotateAngle(this.rightFrontLeg1,
                    Mth.cos(limbSwing * 0.7f + (fast ? 0 : PI)) * 1.4f * limbSwingAmount, 0, 0);
            this.setRotateAngle(this.leftFrontLeg1, Mth.cos(limbSwing * 0.7f) * 1.4f * limbSwingAmount, 0, 0);

            this.rightBackLeg2.z = 0;
            this.leftBackLeg2.z = 0;
            this.rightFrontLeg2.z = 0;
            this.leftFrontLeg2.z = 0;

            this.rightBackLeg2.xRot = -0.1f;
            this.leftBackLeg2.xRot = -0.1f;
            this.rightFrontLeg2.xRot = 0.1f;
            this.leftFrontLeg2.xRot = 0.1f;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart ModelPart, float x, float y, float z) {
        ModelPart.xRot = x;
        ModelPart.yRot = y;
        ModelPart.zRot = z;
    }
}
