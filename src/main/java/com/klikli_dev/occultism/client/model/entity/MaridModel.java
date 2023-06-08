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

package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.common.entity.spirit.MaridEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;


public class MaridModel extends HumanoidModel<MaridEntity> {
    //region Fields
    public ModelPart nose;
    public ModelPart earLeft;
    public ModelPart earRight;

    //endregion Fields

    //region Initialization
    public MaridModel(ModelPart part) {
        super(part);

        this.nose = this.head.getChild("nose");
        this.earLeft = this.head.getChild("left_ear");
        this.earRight = this.head.getChild("right_ear");
    }
    //endregion Initialization


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition hat = parts.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(1, 45).addBox(-4.0F, -8.0F, -4.0F, 8, 10, 8, false), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0, 0, 0));
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition rightArm = parts.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 22).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, false), PartPose.offsetAndRotation(-5.0F, 3.0F, -1.0F, -0.7499679795819634F, 0.0F, 0.0F));
        PartDefinition leftArm = parts.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 22).addBox(-1.0F, -2.0F, -2.0F, 4, 11, 4, false), PartPose.offsetAndRotation(5.0F, 3.0F, -1.0F, -0.7499679795819634F, 0.0F, 0.0F));
        PartDefinition rightLeg = parts.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, false), PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = parts.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, true), PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0, 0, 0));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, false), PartPose.offsetAndRotation(0.0F, -5.0F, -4.4F, -0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition earLeft = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, false), PartPose.offsetAndRotation(4.0F, -11.3F, 3.5F, -0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition earRight = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, false), PartPose.offsetAndRotation(-4.0F, -11.3F, 3.5F, -0.5009094953223726F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

}
