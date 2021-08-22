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

import com.github.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;


public class FoliotModel extends HumanoidModel<FoliotEntity> {

    private final ModelPart leftHorn;
    private final ModelPart rightHorn;

    public FoliotModel(ModelPart part) {
        super(part);
        this.leftHorn = head.getChild("leftHorn");
        this.rightHorn = head.getChild("rightHorn");
    }

    public static LayerDefinition createLayer() {
        //TODO: Manually add missing cubes -> script only does one cube per part
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox("head", -4.0F, -8.0F, -4.0F, 8, 8, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition hat = parts.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox("hat", -5.0F, -10.0F, -5.0F, 10, 10, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox("body", -4.0F, 0.0F, -3.0F, 8, 12, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition rightArm = parts.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 0).addBox("rightArm", -1.0F, -1.6816F, -1.2683F, 3, 11, false), PartPose.offsetAndRotation(5.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition leftArm = parts.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 0).addBox("leftArm", -2.0F, -2.0F, -2.0F, 3, 11, false), PartPose.offsetAndRotation(-5.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition rightLeg = parts.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 0).addBox("rightLeg", -2.0F, 0.0F, -2.0F, 4, 12, false), PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = parts.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 0).addBox("leftLeg", -2.0F, 0.0F, -2.0F, 4, 12, false), PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(0, 0).addBox("leftHorn", 0.5F, 1.5F, -2.5F, 1, 1, false), PartPose.offsetAndRotation(3.5F, -8.5F, -1.5F, 0, 0, 0));
        PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(0, 0).addBox("rightHorn", 0.5F, 1.5F, -2.5F, 1, 1, false), PartPose.offsetAndRotation(-5.5F, -8.5F, -1.5F, 0, 0, 0));
        return LayerDefinition.create(mesh, 64, 64);
    }

}
