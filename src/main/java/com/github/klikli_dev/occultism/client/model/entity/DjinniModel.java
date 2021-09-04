/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

import com.github.klikli_dev.occultism.common.entity.spirit.DjinniEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;


public class DjinniModel extends HumanoidModel<DjinniEntity> {

    private final ModelPart leftHorn;
    private final ModelPart rightHorn;
    private final ModelPart leftEar;
    private final ModelPart leftInnerEar;
    private final ModelPart rightEar;
    private final ModelPart rightInnerEar;
    private final ModelPart wingedWings;
    private final ModelPart wingedWingsLeft;
    private final ModelPart wingedWingsRight;

    public DjinniModel(ModelPart part) {
        super(part);
        this.leftHorn = this.head.getChild("left_horn");
        this.rightHorn = this.head.getChild("right_horn");
        this.leftEar = this.head.getChild("left_ear");
        this.leftInnerEar = this.leftEar.getChild("left_inner_ear");
        this.rightEar = this.head.getChild("right_ear");
        this.rightInnerEar = this.rightEar.getChild("right_inner_ear");
        this.wingedWings = this.body.getChild("winged_wings");
        this.wingedWingsLeft = this.wingedWings.getChild("winged_wings_left");
        this.wingedWingsRight = this.wingedWings.getChild("winged_wings_right");

    }

    @Override
    public void setupAnim(DjinniEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {

        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        //Copied from HumanoidModel, removed all code except that which accesses rotationPointX
        this.rightArm.x = -6.0F;
        this.leftArm.x = 6.0F;

        if (this.attackTime > 0.0F) {
            //original x needs to be used as the multiplier here
            this.rightArm.x = -Mth.cos(this.body.yRot) * 6.0F;
            this.leftArm.x = Mth.cos(this.body.yRot) * 6.0F;
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition hat = parts.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -3.0F, 12, 8, 6, CubeDeformation.NONE)
                        .texOffs(30, 8)
                        .addBox(-4.0F, 8.0F, -3.0F, 8, 4, 6, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));

        PartDefinition rightArm = parts.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 45)
                        .addBox(-3.0F, -2.0F, -2.0F, 3, 11, 3, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-6.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition leftArm = parts.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(0.0F, -1.6816F, -1.2683F, 3, 11, 3, CubeDeformation.NONE),
                PartPose.offsetAndRotation(6.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition rightLeg = parts.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 39)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, CubeDeformation.NONE),
                PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = parts.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0, 0, 0));


        PartDefinition leftHorn = head.addOrReplaceChild("left_horn", CubeListBuilder.create()
                        .texOffs(16, 43)
                        .addBox(0.0F, -2.0F, 0.0F, 1, 2, 1, CubeDeformation.NONE)
                        .texOffs(41, 3)
                        .addBox(1.0F, -4.0F, 0.0F, 1, 3, 1, CubeDeformation.NONE),
                PartPose.offsetAndRotation(3.0F, -8.0F, -2.0F, 0, 0, 0.3491F));
        PartDefinition rightHorn = head.addOrReplaceChild("right_horn", CubeListBuilder.create()
                        .texOffs(28, 42)
                        .addBox(-1.0F, -2.0F, 0.0F, 1, 2, 1, CubeDeformation.NONE)
                        .texOffs(0, 39)
                        .addBox(-2.0F, -4.0F, 0.0F, 1, 3, 1, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-3.0F, -8.0F, -2.0F, 0, 0, -0.3491F));

        PartDefinition leftEar = head.addOrReplaceChild("left_ear", CubeListBuilder.create()
                        .addBox(0.0F, -2.0F, -1.0F, 1, 2, 1, CubeDeformation.NONE, 40, 25)
                        .addBox(0.0F, -3.0F, 0.0F, 1, 1, 1, CubeDeformation.NONE, 38, 31)
                        .addBox(0.0F, -4.0F, 1.0F, 1, 1, 3, CubeDeformation.NONE, 12, 39)
                        .addBox(0.0F, -3.0F, 4.0F, 1, 1, 1, CubeDeformation.NONE, 34, 31)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, CubeDeformation.NONE, 37, 0)
                        .addBox(0.0F, -1.0F, 2.0F, 1, 1, 1, CubeDeformation.NONE, 30, 31)
                        .addBox(0.0F, -2.0F, 3.0F, 1, 1, 1, CubeDeformation.NONE, 34, 4),
                PartPose.offsetAndRotation(5.0F, -4.0F, 1.0F, 0, 0.2618F, 0));
        PartDefinition leftInnerEar = leftEar.addOrReplaceChild("left_inner_ear", CubeListBuilder.create()
                        .addBox(0.0F, -2.0F, 3.0F, 1, 1, 3, CubeDeformation.NONE, 36, 4)
                        .addBox(0.0F, -1.0F, 3.0F, 1, 1, 2, CubeDeformation.NONE, 24, 19)
                        .addBox(0.0F, -3.0F, 4.0F, 1, 1, 3, CubeDeformation.NONE, 35, 25),
                PartPose.offsetAndRotation(-1.0F, 0.0F, -3.0F, 0, 0, 0));


        PartDefinition rightEar = head.addOrReplaceChild("right_ear", CubeListBuilder.create()
                        .addBox(-1.0F, -2.0F, -1.0F, 1, 2, 1, CubeDeformation.NONE, 17, 39)
                        .addBox(-1.0F, -3.0F, 0.0F, 1, 1, 1, CubeDeformation.NONE, 26, 31)
                        .addBox(-1.0F, -4.0F, 1.0F, 1, 1, 3, CubeDeformation.NONE, 24, 15)
                        .addBox(-1.0F, -3.0F, 4.0F, 1, 1, 1, CubeDeformation.NONE, 22, 31)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, CubeDeformation.NONE, 0, 3)
                        .addBox(-1.0F, -1.0F, 2.0F, 1, 1, 1, CubeDeformation.NONE, 30, 4)
                        .addBox(-1.0F, -2.0F, 3.0F, 1, 1, 1, CubeDeformation.NONE, 28, 19),
                PartPose.offsetAndRotation(-5.0F, -4.0F, 1.0F, 0, -0.2618F, 0));
        PartDefinition rightInnerEar = rightEar.addOrReplaceChild("right_inner_ear", CubeListBuilder.create()
                        .addBox(1.0F, -2.0F, 3.0F, 1, 1, 3, CubeDeformation.NONE, 0, 18)
                        .addBox(1.0F, -1.0F, 3.0F, 1, 1, 2, CubeDeformation.NONE, 0, 0)
                        .addBox(1.0F, -3.0F, 4.0F, 1, 1, 3, CubeDeformation.NONE, 0, 14),
                PartPose.offsetAndRotation(-1.0F, 0.0F, -3.0F, 0, 0, 0));

        PartDefinition wingedWings = body.addOrReplaceChild("winged_wings", CubeListBuilder.create().texOffs(0, 0),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0, 0, 0));


        PartDefinition wingedWingsLeft = wingedWings.addOrReplaceChild("winged_wings_left", CubeListBuilder.create()
                        .addBox(-1.0F, -1.0F, 0.0F, 1, 1, 11, CubeDeformation.NONE, 33, 33)
                        .addBox(-0.1F, 0.0F, -0.4226F, 0, 9, 11, CubeDeformation.NONE, 22, 22),
                PartPose.offsetAndRotation(1.5F, -21.5F, 3.0F, 0.4363F, 0.8727F, 0.0F));


        PartDefinition wingedWingsRight = wingedWings.addOrReplaceChild("winged_wings_right", CubeListBuilder.create()
                        .addBox(-1.0F, -1.0F, 0.0F, 1, 1, 11, CubeDeformation.NONE, 22, 19)
                        .addBox(-0.1F, 0.0F, -0.4226F, 0, 9, 11, CubeDeformation.NONE, 0, 19),
                PartPose.offsetAndRotation(-0.5F, -21.5F, 3.0F, 0.4363F, -0.8727F, 0.0F));


        return LayerDefinition.create(mesh, 64, 64);
    }

}
