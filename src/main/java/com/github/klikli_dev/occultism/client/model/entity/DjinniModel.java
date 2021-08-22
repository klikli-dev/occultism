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
        this.leftHorn = this.head.getChild("leftHorn");
        this.rightHorn = this.head.getChild("rightHorn");
        this.leftEar = this.head.getChild("leftEar");
        this.leftInnerEar = this.leftEar.getChild("leftInnerEar");
        this.rightEar = this.head.getChild("rightEar");
        this.rightInnerEar = this.rightEar.getChild("rightInnerEar");
        this.wingedWings = this.body.getChild("wingedWings");
        this.wingedWingsLeft = this.wingedWings.getChild("wingedWingsLeft");
        this.wingedWingsRight = this.wingedWings.getChild("wingedWingsRight");

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

    public static LayerDefinition createLayer() {
        //TODO: Manually add missing cubes -> script only does one cube per part
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox("head", -4.0F, -8.0F, -4.0F, 8, 8, 8,CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition hat = parts.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox("body", -4.0F, 8.0F, -3.0F, 8, 4, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition rightArm = parts.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 0).addBox("leftArm", -3.0F, -2.0F, -2.0F, 3, 11, false), PartPose.offsetAndRotation(-6.0F, 3.0F, -1.0F, 0, 0, 0));
        PartDefinition leftArm = parts.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 0).addBox("leftArm", 0.0F, -1.6816F, -1.2683F, 3, 11, false), PartPose.offsetAndRotation(6.0F, 3.0F, -1.0F, 0, 0, 0));
        PartDefinition rightLeg = parts.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 0).addBox("rightLeg", -2.0F, 0.0F, -2.0F, 4, 12, false), PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = parts.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 0).addBox("leftLeg", -2.0F, 0.0F, -2.0F, 4, 12, false), PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(0, 0).addBox("leftHorn", 1.0F, -4.0F, 0.0F, 1, 3, false), PartPose.offsetAndRotation(3.0F, -8.0F, -2.0F, 0, 0, 0));
        PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(0, 0).addBox("rightHorn", -2.0F, -4.0F, 0.0F, 1, 3, false), PartPose.offsetAndRotation(-3.0F, -8.0F, -2.0F, 0, 0, 0));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 0).addBox("leftEar", 0.0F, -2.0F, 3.0F, 1, 1, false), PartPose.offsetAndRotation(5.0F, -4.0F, 1.0F, 0, 0, 0));
        PartDefinition leftInnerEar = leftEar.addOrReplaceChild("leftInnerEar", CubeListBuilder.create().texOffs(0, 0).addBox("leftInnerEar", 0.0F, -3.0F, 4.0F, 1, 1, false), PartPose.offsetAndRotation(-1.0F, 0.0F, -3.0F, 0, 0, 0));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 0).addBox("rightEar", -1.0F, -2.0F, 3.0F, 1, 1, false), PartPose.offsetAndRotation(-5.0F, -4.0F, 1.0F, 0, 0, 0));
        PartDefinition rightInnerEar = rightEar.addOrReplaceChild("rightInnerEar", CubeListBuilder.create().texOffs(0, 0).addBox("rightInnerEar", 1.0F, -3.0F, 4.0F, 1, 1, false), PartPose.offsetAndRotation(-1.0F, 0.0F, -3.0F, 0, 0, 0));
        PartDefinition wingedWings = body.addOrReplaceChild("wingedWings", CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0, false), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0, 0, 0));
        PartDefinition wingedWingsLeft = wingedWings.addOrReplaceChild("wingedWingsLeft", CubeListBuilder.create().texOffs(0, 0).addBox("wingedWingsLeft", -0.1F, 0.0F, -0.4226F, 0, 9, false), PartPose.offsetAndRotation(1.5F, -21.5F, 3.0F, 0, 0, 0));
        PartDefinition wingedWingsRight = wingedWings.addOrReplaceChild("wingedWingsRight", CubeListBuilder.create().texOffs(0, 0).addBox("wingedWingsRight", -0.1F, 0.0F, -0.4226F, 0, 9, false), PartPose.offsetAndRotation(-0.5F, -21.5F, 3.0F, 0, 0, 0));
        return LayerDefinition.create(mesh, 64, 64);
    }

}
