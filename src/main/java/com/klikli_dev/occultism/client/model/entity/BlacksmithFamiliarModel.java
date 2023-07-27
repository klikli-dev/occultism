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

import com.klikli_dev.occultism.common.entity.familiar.BlacksmithFamiliarEntity;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.google.common.collect.ImmutableList;
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
public class BlacksmithFamiliarModel extends EntityModel<BlacksmithFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart wagon;
    public ModelPart rightArm;
    public ModelPart leftArm;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart head;
    public ModelPart hammer1;
    public ModelPart hammer2;
    public ModelPart nose;
    public ModelPart mouth1;
    public ModelPart leftEar;
    public ModelPart hair1;
    public ModelPart rightEar;
    public ModelPart hair2;
    public ModelPart mouth2;
    public ModelPart christmasBeard;
    public ModelPart earring;
    public ModelPart anvil1;
    public ModelPart anvil2;
    public ModelPart anvil3;
    public ModelPart wheel1;
    public ModelPart wheel2;
    public ModelPart wheel3;
    public ModelPart wheel4;
    public ModelPart anvil4;
    public ModelPart anvil5;
    public ModelPart anvil6;
    public ModelPart anvil7;

    public BlacksmithFamiliarModel(ModelPart part) {
        this.body = part.getChild("body");
        this.wagon = part.getChild("wagon");
        this.rightArm = this.body.getChild("rightArm");
        this.leftArm = this.body.getChild("leftArm");
        this.rightLeg = this.body.getChild("rightLeg");
        this.leftLeg = this.body.getChild("leftLeg");
        this.head = this.body.getChild("head");
        this.hammer1 = this.rightArm.getChild("hammer1");
        this.hammer2 = this.hammer1.getChild("hammer2");
        this.nose = this.head.getChild("nose");
        this.mouth1 = this.head.getChild("mouth1");
        this.leftEar = this.head.getChild("leftEar");
        this.hair1 = this.head.getChild("hair1");
        this.rightEar = this.head.getChild("rightEar");
        this.hair2 = this.head.getChild("hair2");
        this.mouth2 = this.head.getChild("mouth2");
        this.christmasBeard = this.head.getChild("christmasBeard");
        this.earring = this.leftEar.getChild("earring");
        this.anvil1 = this.wagon.getChild("anvil1");
        this.anvil2 = this.wagon.getChild("anvil2");
        this.anvil3 = this.wagon.getChild("anvil3");
        this.wheel1 = this.wagon.getChild("wheel1");
        this.wheel2 = this.wagon.getChild("wheel2");
        this.wheel3 = this.wagon.getChild("wheel3");
        this.wheel4 = this.wagon.getChild("wheel4");
        this.anvil4 = this.anvil3.getChild("anvil4");
        this.anvil5 = this.anvil4.getChild("anvil5");
        this.anvil6 = this.anvil4.getChild("anvil6");
        this.anvil7 = this.anvil4.getChild("anvil7");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 19.0F, 3.0F, 0, 0, 0));
        PartDefinition wagon = parts.addOrReplaceChild("wagon", CubeListBuilder.create().texOffs(14, 12).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 1.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 22.5F, -2.0F, 0, 0, 0));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(43, 2).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition hammer1 = rightArm.addOrReplaceChild("hammer1", CubeListBuilder.create().texOffs(16, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, true), PartPose.offsetAndRotation(-1.0F, 5.0F, 0.0F, 0.6255260065779288F, 1.7983872772339238F, 0.0F));
        PartDefinition hammer2 = hammer1.addOrReplaceChild("hammer2", CubeListBuilder.create().texOffs(14, 6).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0, 0, 0));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(30, 7).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.2F, -1.5F, -0.23457224414434488F, 0.0F, 0.0F));
        PartDefinition mouth1 = head.addOrReplaceChild("mouth1", CubeListBuilder.create().texOffs(8, 26).addBox(-3.5F, -2.0F, 0.0F, 7.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -1.1F, -3.2F, 0, 0, 0));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, false), PartPose.offsetAndRotation(2.4F, -3.0F, 0.0F, 0.22689280275926282F, -0.8726646259971648F, -0.5235987755982988F));
        PartDefinition hair1 = head.addOrReplaceChild("hair1", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, true), PartPose.offsetAndRotation(-2.4F, -3.0F, 0.0F, 0.22689280275926282F, 0.8726646259971648F, 0.5235987755982988F));
        PartDefinition hair2 = head.addOrReplaceChild("hair2", CubeListBuilder.create().texOffs(42, 21).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, 0, 0, 0));
        PartDefinition mouth2 = head.addOrReplaceChild("mouth2", CubeListBuilder.create().texOffs(22, 26).addBox(-3.5F, -2.0F, 0.0F, 7.0F, 5.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -1.1F, -3.2F, 0, 0, 0));
        PartDefinition christmasBeard = head.addOrReplaceChild("christmasBeard", CubeListBuilder.create().texOffs(0, 32).addBox(-3.5F, -2.0F, 0.0F, 7.0F, 7.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.7F, -0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition earring = leftEar.addOrReplaceChild("earring", CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, false), PartPose.offsetAndRotation(1.4F, 1.0F, 0.0F, 0.0F, 2.3917990897539414F, 0.0F));
        PartDefinition anvil1 = wagon.addOrReplaceChild("anvil1", CubeListBuilder.create().texOffs(42, 12).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, true), PartPose.offsetAndRotation(-2.0F, -1.0F, 0.0F, 0, 0, 0));
        PartDefinition anvil2 = wagon.addOrReplaceChild("anvil2", CubeListBuilder.create().texOffs(42, 12).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, false), PartPose.offsetAndRotation(2.0F, -1.0F, 0.0F, 0, 0, 0));
        PartDefinition anvil3 = wagon.addOrReplaceChild("anvil3", CubeListBuilder.create().texOffs(51, 15).addBox(-1.0F, -2.0F, -1.5F, 2.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0, 0, 0));
        PartDefinition wheel1 = wagon.addOrReplaceChild("wheel1", CubeListBuilder.create().texOffs(34, 14).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(3.5F, 0.5F, 2.5F, 0, 0, 0));
        PartDefinition wheel2 = wagon.addOrReplaceChild("wheel2", CubeListBuilder.create().texOffs(34, 14).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(3.5F, 0.5F, -2.5F, 0, 0, 0));
        PartDefinition wheel3 = wagon.addOrReplaceChild("wheel3", CubeListBuilder.create().texOffs(34, 14).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-3.5F, 0.5F, -2.5F, 0, 0, 0));
        PartDefinition wheel4 = wagon.addOrReplaceChild("wheel4", CubeListBuilder.create().texOffs(34, 14).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-3.5F, 0.5F, 2.5F, 0, 0, 0));
        PartDefinition anvil4 = anvil3.addOrReplaceChild("anvil4", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, -1.5F, -2.0F, 6.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -3.5F, 0.0F, 0, 0, 0));
        PartDefinition anvil5 = anvil4.addOrReplaceChild("anvil5", CubeListBuilder.create().texOffs(36, 18).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, false), PartPose.offsetAndRotation(-4.0F, -0.5F, 0.0F, 0, 0, 0));
        PartDefinition anvil6 = anvil4.addOrReplaceChild("anvil6", CubeListBuilder.create().texOffs(16, 19).addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -0.51F, 0.5F, 0.0F, 0.2617993877991494F, 0.0F));
        PartDefinition anvil7 = anvil4.addOrReplaceChild("anvil7", CubeListBuilder.create().texOffs(16, 19).addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -0.5F, -0.5F, 0.0F, -0.27366763203903305F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        ImmutableList.of(this.body, this.wagon).forEach((modelRenderer) -> {
            modelRenderer.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        });
    }

    @Override
    public void setupAnim(BlacksmithFamiliarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * (PI / 180f);
        this.head.xRot = headPitch * (PI / 180f);

        this.wheel1.xRot = limbSwing;
        this.wheel2.xRot = limbSwing;
        this.wheel3.xRot = limbSwing;
        this.wheel4.xRot = limbSwing;

        this.rightLeg.xRot = Mth.cos(limbSwing * 0.5f) * limbSwingAmount * 0.6f;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.6f;

        this.leftArm.xRot = this.toRad(-30) + Mth.cos(limbSwing * 0.5f + PI) * limbSwingAmount * 0.2f;
        this.leftArm.yRot = this.toRad(-15);
        this.rightArm.xRot = this.toRad(-75);
        this.rightArm.yRot = this.toRad(25);
        this.hammer1.yRot = 0;
        this.hammer1.zRot = this.toRad(90);
        this.hammer1.xRot = 0;

        this.body.yRot = 0;
        this.body.y = 19;
        this.body.xRot = 0;
        this.body.z = 3;
        this.leftArm.zRot = 0;
        this.rightArm.zRot = 0;

        if (entityIn.isPartying()) {
            this.head.xRot = Mth.cos(ageInTicks * 0.8f) * this.toRad(25);
            this.head.yRot = 0;
            this.leftArm.xRot = this.toRad(-90) + Mth.cos(ageInTicks * 1.2f) * this.toRad(25);
            this.leftArm.yRot = this.toRad(-5);
            this.leftArm.zRot = this.toRad(20);
            this.rightArm.xRot = this.toRad(-90) + Mth.cos(ageInTicks * 1.2f + PI) * this.toRad(25);
            this.rightArm.yRot = this.toRad(-5);
            this.rightArm.zRot = this.toRad(-20);
        } else if (entityIn.isSitting()) {
            this.body.yRot = this.toRad(180);
            this.leftLeg.xRot = this.toRad(-70);
            this.rightLeg.xRot = this.toRad(-70);
            this.body.y = 23f;
            this.body.z = 4;
            this.body.xRot = this.toRad(-20);
            this.head.xRot = this.toRad(20);
            this.head.yRot = 0;
            this.leftArm.xRot = this.toRad(-10);
            this.rightArm.xRot = this.toRad(-10);
        }

    }

    @Override
    public void prepareMobModel(BlacksmithFamiliarEntity entityIn, float limbSwing, float limbSwingAmount,
                                float partialTick) {
        this.showModels(entityIn);
    }

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    private void showModels(BlacksmithFamiliarEntity entityIn) {
        boolean hasSquarehair = entityIn.hasSquareHair();
        boolean hasMarioMoustache = entityIn.hasMarioMoustache();
        boolean isChristmas = FamiliarUtil.isChristmas();

        this.earring.visible = entityIn.hasEarring();
        this.mouth1.visible = !hasMarioMoustache && !isChristmas;
        this.mouth2.visible = hasMarioMoustache && !isChristmas;
        this.hair1.visible = hasSquarehair;
        this.hair2.visible = !hasSquarehair;
        
        this.christmasBeard.visible = isChristmas;
    }
}
