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
import net.minecraft.client.model.geom.builders.*;


public class FoliotModel extends HumanoidModel<FoliotEntity> {

    private final ModelPart leftHorn;
    private final ModelPart rightHorn;

    public FoliotModel(ModelPart part) {
        super(part);
        this.leftHorn = this.head.getChild("left_horn");
        this.rightHorn = this.head.getChild("right_horn");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create()
                        .addBox("", -4.0F, -8.0F, -4.0F, 8, 8, 8, CubeDeformation.NONE, 0, 0),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));

        PartDefinition hat = parts.addOrReplaceChild("hat", CubeListBuilder.create()
                        .addBox("", -5.0F, -10.0F, -5.0F, 10, 10, 10, CubeDeformation.NONE, 24, 44),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));

        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create()
                        .addBox("", -4.0F, 0.0F, -3.0F, 8, 12, 6, CubeDeformation.NONE, 0, 16),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));

        PartDefinition rightArm = parts.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .addBox("", -1.0F, -1.6816F, -1.2683F, 3, 11, 3, CubeDeformation.NONE, 12, 34),
                PartPose.offsetAndRotation(5.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition leftArm = parts.addOrReplaceChild("left_arm", CubeListBuilder.create().
                        addBox("", -2.0F, -2.0F, -2.0F, 3, 11, 3, CubeDeformation.NONE, 0, 34),
                PartPose.offsetAndRotation(-5.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition rightLeg = parts.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .addBox("", -2.0F, 0.0F, -2.0F, 4, 12, 4, CubeDeformation.NONE, 28, 28),
                PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = parts.addOrReplaceChild("left_leg", CubeListBuilder.create().
                        addBox("", -2.0F, 0.0F, -2.0F, 4, 12, 4, CubeDeformation.NONE, 28, 12),
                PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0, 0, 0));

        PartDefinition leftHorn = head.addOrReplaceChild("left_horn", CubeListBuilder.create()
                        .addBox("", 0.5F, -0.5F, -2.5F, 1, 1, 6, CubeDeformation.NONE, 24, 0)
                        .addBox("", 0.5F, 4.5F, -1.5F, 1, 1, 5, CubeDeformation.NONE, 32, 0)
                        .addBox("", 0.5F, 5.5F, -0.5F, 1, 1, 3, CubeDeformation.NONE, 0, 0)
                        .addBox("", 0.5F, 0.5F, 1.5F, 1, 1, 3, CubeDeformation.NONE, 39, 0)
                        .addBox("", 0.5F, 1.5F, 2.5F, 1, 1, 3, CubeDeformation.NONE, 37, 6)
                        .addBox("", 0.5F, 2.5F, 2.5F, 1, 1, 3, CubeDeformation.NONE, 32, 7)
                        .addBox("", 0.5F, 3.5F, 1.5F, 1, 1, 3, CubeDeformation.NONE, 0, 4)
                        .addBox("", 0.5F, 0.5F, -2.5F, 1, 1, 2, CubeDeformation.NONE, 0, 16)
                        .addBox("", 0.5F, 3.5F, -1.5F, 1, 1, 1, CubeDeformation.NONE, 22, 20)
                        .addBox("", 0.5F, 1.5F, -2.5F, 1, 1, 1, CubeDeformation.NONE, 0, 19),
                PartPose.offsetAndRotation(3.5F, -8.5F, -1.5F, 0, 0, 0));

        PartDefinition rightHorn = head.addOrReplaceChild("right_horn", CubeListBuilder.create()
                        .addBox("", 0.5F, -0.5F, -2.5F, 1, 1, 6, CubeDeformation.NONE, 24, 0)
                        .addBox("", 0.5F, 4.5F, -1.5F, 1, 1, 5, CubeDeformation.NONE, 32, 0)
                        .addBox("", 0.5F, 5.5F, -0.5F, 1, 1, 3, CubeDeformation.NONE, 0, 0)
                        .addBox("", 0.5F, 0.5F, 1.5F, 1, 1, 3, CubeDeformation.NONE, 39, 0)
                        .addBox("", 0.5F, 1.5F, 2.5F, 1, 1, 3, CubeDeformation.NONE, 37, 6)
                        .addBox("", 0.5F, 2.5F, 2.5F, 1, 1, 3, CubeDeformation.NONE, 32, 7)
                        .addBox("", 0.5F, 3.5F, 1.5F, 1, 1, 3, CubeDeformation.NONE, 0, 4)
                        .addBox("", 0.5F, 0.5F, -2.5F, 1, 1, 2, CubeDeformation.NONE, 0, 16)
                        .addBox("", 0.5F, 3.5F, -1.5F, 1, 1, 1, CubeDeformation.NONE, 22, 20)
                        .addBox("", 0.5F, 1.5F, -2.5F, 1, 1, 1, CubeDeformation.NONE, 0, 19),
                PartPose.offsetAndRotation(-5.5F, -8.5F, -1.5F, 0, 0, 0));


        return LayerDefinition.create(mesh, 64, 64);
    }

}
