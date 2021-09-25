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

import com.github.klikli_dev.occultism.common.entity.GreedyFamiliarEntity;
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
public class GreedyFamiliarModel extends EntityModel<GreedyFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart rightArm;
    public ModelPart chest1;
    public ModelPart leftArm;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart head;
    public ModelPart chest2;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart nose;

    public GreedyFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.rightArm = this.body.getChild("rightArm");
        this.chest1 = this.body.getChild("chest1");
        this.leftArm = this.body.getChild("leftArm");
        this.rightLeg = this.body.getChild("rightLeg");
        this.leftLeg = this.body.getChild("leftLeg");
        this.head = this.body.getChild("head");
        this.chest2 = this.chest1.getChild("chest2");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEar = this.head.getChild("rightEar");
        this.nose = this.head.getChild("nose");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 19.0F, 0.6F, 0, 0, 0));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(16, 21).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition chest1 = body.addOrReplaceChild("chest1", CubeListBuilder.create().texOffs(12, 14).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.5F, 1.5F, 0, 0, 0));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(16, 21).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition chest2 = chest1.addOrReplaceChild("chest2", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, false), PartPose.offsetAndRotation(2.0F, -4.5F, 0.0F, 0.0F, 0.0F, 0.5235987755982988F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, true), PartPose.offsetAndRotation(-2.0F, -4.5F, 0.0F, 0.0F, 0.0F, -0.5235987755982988F));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(18, 8).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, -2.5F, 0, 0, 0));
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, red, green, blue, alpha);
    }

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    @Override
    public void setupAnim(GreedyFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * (PI / 180f);
        this.head.xRot = headPitch * (PI / 180f);
        this.head.zRot = 0;
        this.rightArm.zRot = 0;
        this.leftArm.zRot = 0;
        if (entityIn.isPartying()) {
            this.rightArm.xRot = Mth.cos(ageInTicks + PI) * this.toRad(20) + this.toRad(180);
            this.leftArm.xRot = Mth.cos(ageInTicks) * this.toRad(20) + this.toRad(180);
            this.rightArm.zRot = -this.toRad(20);
            this.leftArm.zRot = this.toRad(20);
            this.head.zRot = Mth.sin(ageInTicks) * this.toRad(20);
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.5f + PI) * 1.4f * limbSwingAmount;
        } else if (entityIn.isSitting()) {
            this.rightArm.xRot = 0;
            this.leftArm.xRot = 0;
            this.rightLeg.xRot = -PI / 2;
            this.leftLeg.xRot = -PI / 2;
        } else {
            this.rightArm.xRot = Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount;
            this.leftArm.xRot = Mth.cos(limbSwing * 0.5f) * limbSwingAmount;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.5f) * 1.4f * limbSwingAmount;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.5f + PI) * 1.4f * limbSwingAmount;
        }
        this.chest2.xRot = Mth.cos(limbSwing * 0.35f + PI) * 0.5f * limbSwingAmount + PI / 12;
    }
}
