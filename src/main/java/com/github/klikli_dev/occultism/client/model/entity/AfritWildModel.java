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

import com.github.klikli_dev.occultism.common.entity.spirit.AfritWildEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;


public class AfritWildModel extends HumanoidModel<AfritWildEntity> {

    public static final String NOSE = "nose";
    public static final String EAR_LEFT = "earLeft";
    public static final String EAR_RIGHT = "earRight";
    public static final String WINGED_WINGS = "wingedWings";
    public static final String WINGED_WINGS_LEFT = "wingedWingsLeft";
    public static final String WINGED_WINGS_RIGHT = "wingedWingsRight";

    //region Fields
    public ModelPart nose;
    public ModelPart earLeft;
    public ModelPart earRight;
    public ModelPart wingedWings;
    public ModelPart wingedWingsLeft;
    public ModelPart wingedWingsRight;

    //endregion Fields

    //region Initialization
    public AfritWildModel(ModelPart modelPart) {
        super(modelPart); //modelsize 1.0 was used here
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;

        this.nose = this.head.getChild(NOSE);
        this.earLeft = this.head.getChild(EAR_LEFT);
        this.earRight = this.head.getChild(EAR_RIGHT);
        this.wingedWings = this.body.getChild(WINGED_WINGS);
        this.wingedWingsLeft = this.wingedWings.getChild(WINGED_WINGS_LEFT);
        this.wingedWingsRight = this.wingedWings.getChild(WINGED_WINGS_RIGHT);
    }
    //endregion Initialization

    //region Methods

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
        PartDefinition mesh = meshdefinition.getRoot();

        PartDefinition head = mesh.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, CubeDeformation.NONE), PartPose.ZERO);
        mesh.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(1, 45).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5f)), PartPose.offset(0, -3, 0));

        head.addOrReplaceChild(NOSE, CubeListBuilder.create().texOffs(24, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -5.0F, -4.4F, -0.4553564018453205F, 0.0F, 0.0F));

        head.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(4.0F, -11.3F, 3.5F, -0.5009094953223726F, 0.0F, 0.0F));

        head.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-4.0F, -11.3F, 3.5F, -0.5009094953223726F, 0.0F, 0.0F));

        mesh.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 22)
                        .addBox(-1.0F, -2.0F, -2.0F, 4, 11, 4,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(5.0F, 3.0F, -1.0F, -0.7499679795819634F, 0.0F, 0.0F));

        mesh.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 22)
                        .addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-5.0F, 3.0F, -1.0F, -0.7499679795819634F, 0.0F, 0.0F));

        PartDefinition body = mesh.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20)
                        .addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6,
                                CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        mesh.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4,
                                CubeDeformation.NONE).mirror(),
                PartPose.offset(2.0F, 12.0F, 0.0F));

        mesh.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4,
                                CubeDeformation.NONE),
                PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition wingedWings = body.addOrReplaceChild(WINGED_WINGS, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        wingedWings.addOrReplaceChild(WINGED_WINGS_LEFT, CubeListBuilder.create()
                        .texOffs(40, 38)
                        .addBox(-1.0F, -1.0F, 0.0F, 1, 1, 11,
                                CubeDeformation.NONE)
                        .texOffs(32, 0)
                        .addBox(-0.1F, 0.0F, -0.4226F, 0, 9, 11,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.5F, -21.5F, 3.0F, 0.4363F, 0.8727F, 0.0F));

        wingedWings.addOrReplaceChild(WINGED_WINGS_RIGHT, CubeListBuilder.create()
                        .texOffs(40, 38)
                        .addBox(-1.0F, -1.0F, 0.0F, 1, 1, 11,
                                CubeDeformation.NONE)
                        .texOffs(32, 0)
                        .addBox(-0.1F, 0.0F, -0.4226F, 0, 9, 11,
                                CubeDeformation.NONE),
                PartPose.offsetAndRotation(-0.5F, -21.5F, 3.0F, 0.4363F, -0.8727F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    //endregion Methods
}
