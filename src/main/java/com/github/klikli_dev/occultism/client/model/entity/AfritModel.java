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

import com.github.klikli_dev.occultism.common.entity.spirit.AfritEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class AfritModel extends HumanoidModel<AfritEntity> {

    public static final String NOSE = "nose";
    public static final String EAR_LEFT = "earLeft";
    public static final String EAR_RIGHT = "earRight";
    public static final String WINGED_WINGS = "wingedWings";
    public static final String WINGED_WINGS_LEFT = "wingedWingsLeft";
    public static final String WINGED_WINGS_RIGHT = "wingedWingsRight";
    public static ModelLayerLocation AFRIT_LAYER = new ModelLayerLocation(new ResourceLocation("occultism:afrit"), "afrit");
    //region Fields
    public ModelPart nose;
    public ModelPart earLeft;
    public ModelPart earRight;
    public ModelPart wingedWings;
    public ModelPart wingedWingsLeft;
    public ModelPart wingedWingsRight;

    //endregion Fields

    //region Initialization
    public AfritModel(ModelPart modelPart) {
        super(modelPart); //modelsize 1.0 was used here
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;

        this.nose = this.head.getChild(NOSE);
        this.earLeft = this.head.getChild(EAR_LEFT);
        this.earRight = this.head.getChild(EAR_RIGHT);
        this.wingedWings = this.body.getChild(WINGED_WINGS);
        this.wingedWingsLeft = this.wingedWings.getChild(WINGED_WINGS_LEFT);
        this.wingedWingsRight = this.wingedWings.getChild(WINGED_WINGS_RIGHT);

        //This no longer works!
        //TODO: Update model construction to 1.17 way
        //      search for "addbox" in 117 channel
        //      or just open https://discordapp.com/channels/313125603924639766/867851603468615740/868187284661469184

        this.earLeft = new ModelPart(this, 0, 0);
        this.earLeft.setPos(4.0F, -11.3F, 3.5F);
        this.earLeft.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(this.earLeft, -0.5009094953223726F, 0.0F, 0.0F);

        this.earRight = new ModelPart(this, 0, 0);
        this.earRight.setPos(-4.0F, -11.3F, 3.5F);
        this.earRight.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(this.earRight, -0.5009094953223726F, 0.0F, 0.0F);



        this.bipedLeftArm = new ModelPart(this, 44, 22);
        this.bipedLeftArm.setPos(5.0F, 3.0F, -1.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 11, 4, 0.0F);
        this.setRotateAngle(this.bipedLeftArm, -0.7499679795819634F, 0.0F, 0.0F);

        this.bipedRightArm = new ModelPart(this, 44, 22);
        this.bipedRightArm.setPos(-5.0F, 3.0F, -1.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(this.bipedRightArm, -0.7499679795819634F, 0.0F, 0.0F);

        this.bipedBody = new ModelPart(this, 16, 20);
        this.bipedBody.setPos(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);

        this.bipedLeftLeg = new ModelPart(this, 0, 22);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setPos(2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

        this.bipedRightLeg = new ModelPart(this, 0, 22);
        this.bipedRightLeg.setPos(-2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

        this.head.addChild(this.earRight);
        this.head.addChild(this.earLeft);
        this.head.addChild(this.nose);

        this.wingedWings = new ModelPart(this);
        this.wingedWings.setPos(0.0F, 24.0F, 0.0F);
        this.bipedBody.addChild(this.wingedWings);

        this.wingedWingsLeft = new ModelPart(this);
        this.wingedWingsLeft.setPos(1.5F, -21.5F, 3.0F);
        this.setRotateAngle(this.wingedWingsLeft, 0.4363F, 0.8727F, 0.0F);
        this.wingedWings.addChild(this.wingedWingsLeft);
        this.wingedWingsLeft.setTextureOffset(40, 38);
        this.wingedWingsLeft.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 11);
        this.wingedWingsLeft.setTextureOffset(32, 0);
        this.wingedWingsLeft.addBox(-0.1F, 0.0F, -0.4226F, 0, 9, 11);

        this.wingedWingsRight = new ModelPart(this);
        this.wingedWingsRight.setPos(-0.5F, -21.5F, 3.0F);
        this.setRotateAngle(this.wingedWingsRight, 0.4363F, -0.8727F, 0.0F);
        this.wingedWings.addChild(this.wingedWingsRight);
        this.wingedWingsRight.setTextureOffset(40, 38);
        this.wingedWingsRight.addBox( -1.0F, -1.0F, 0.0F, 1, 1, 11);
        this.wingedWingsRight.setTextureOffset(32, 0);
        this.wingedWingsRight.addBox(-0.1F, 0.0F, -0.4226F, 0, 9, 11);

    }
    //endregion Initialization

    //region Methods
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // new ModelPart(this, texOffsX, texOffsY) -> texOffs()
        // setPos -> PartPose.offset
        // addBox -> addBox
        // addBox(..., delta) -> addBox(..., CubeDeformation)
        //      delta is also weirdly used as model size in vanilla (+ part delta)
        // x.addChild -> partDefintion.addorReplaceChild()
        //      also needs setup in constructor:    this.nose = this.head.getChild("nose");
        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, CubeDeformation.NONE), PartPose.ZERO);
        partdefinition.addOrReplaceChild("hat",
                CubeListBuilder.create().texOffs(1, 45)
                        .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5f)), PartPose.offset(0, -3, 0));


        return LayerDefinition.create(meshdefinition, 64, 64);;
    }
    //endregion Methods
}
