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

import com.github.klikli_dev.occultism.common.entity.familiar.ShubNiggurathSpawnEntity;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
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
public class ShubNiggurathSpawnModel extends EntityModel<ShubNiggurathSpawnEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart head;
    public ModelPart rightHorn1;
    public ModelPart mouth;
    public ModelPart leftHorn1;
    public ModelPart tentacleTop1;
    public ModelPart tentacleMiddle1;
    public ModelPart tentacleBottom1;
    public ModelPart eye2;
    public ModelPart eye3;
    public ModelPart eye4;
    public ModelPart christmasPresent1;
    public ModelPart rightHorn2;
    public ModelPart jaw;
    public ModelPart upperTeeth;
    public ModelPart eye1;
    public ModelPart lowerTeeth;
    public ModelPart leftHorn2;
    public ModelPart tentacleTop2;
    public ModelPart tentacleTop3;
    public ModelPart tentacleMiddle2;
    public ModelPart tentacleMiddle3;
    public ModelPart tentacleBottom2;
    public ModelPart tentacleBottom3;
    public ModelPart christmasPresent2;
    public ModelPart christmasPresent3;

    public ShubNiggurathSpawnModel(ModelPart part) {
        this.head = part.getChild("head");
        this.rightHorn1 = head.getChild("rightHorn1");
        this.mouth = head.getChild("mouth");
        this.leftHorn1 = head.getChild("leftHorn1");
        this.tentacleTop1 = head.getChild("tentacleTop1");
        this.tentacleMiddle1 = head.getChild("tentacleMiddle1");
        this.tentacleBottom1 = head.getChild("tentacleBottom1");
        this.eye2 = head.getChild("eye2");
        this.eye3 = head.getChild("eye3");
        this.eye4 = head.getChild("eye4");
        this.christmasPresent1 = head.getChild("christmasPresent1");
        this.rightHorn2 = rightHorn1.getChild("rightHorn2");
        this.jaw = mouth.getChild("jaw");
        this.upperTeeth = mouth.getChild("upperTeeth");
        this.eye1 = mouth.getChild("eye1");
        this.lowerTeeth = jaw.getChild("lowerTeeth");
        this.leftHorn2 = leftHorn1.getChild("leftHorn2");
        this.tentacleTop2 = tentacleTop1.getChild("tentacleTop2");
        this.tentacleTop3 = tentacleTop2.getChild("tentacleTop3");
        this.tentacleMiddle2 = tentacleMiddle1.getChild("tentacleMiddle2");
        this.tentacleMiddle3 = tentacleMiddle2.getChild("tentacleMiddle3");
        this.tentacleBottom2 = tentacleBottom1.getChild("tentacleBottom2");
        this.tentacleBottom3 = tentacleBottom2.getChild("tentacleBottom3");
        this.christmasPresent2 = christmasPresent1.getChild("christmasPresent2");
        this.christmasPresent3 = christmasPresent1.getChild("christmasPresent3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(-1.5F, 22.5F, -0.8F, 1.1798425477165557F, 0.0F, -1.5707963267948966F));
        PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(12, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(-0.9F, -0.6F, 0.2F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(12, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.9F, -0.6F, 0.2F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition tentacleTop1 = head.addOrReplaceChild("tentacleTop1", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.7F, 1.4F, -0.7F, -1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition tentacleMiddle1 = head.addOrReplaceChild("tentacleMiddle1", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.3F, 1.4F, -2.2F, -1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition tentacleBottom1 = head.addOrReplaceChild("tentacleBottom1", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.8F, 1.4F, -0.7F, -1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition eye2 = head.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(11, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-0.2F, -0.8F, -1.8F, 0.0F, 0.4300491170387584F, 0.0F));
        PartDefinition eye3 = head.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(11, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(0.8F, 0.0F, -2.1F, 0.0F, 0.27366763203903305F, 1.5707963267948966F));
        PartDefinition eye4 = head.addOrReplaceChild("eye4", CubeListBuilder.create().texOffs(11, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.8F, 0.0F, -0.8F, 0.0F, 0.0781907508222411F, 1.5707963267948966F));
        PartDefinition christmasPresent1 = head.addOrReplaceChild("christmasPresent1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -3.5F, -3.5F, 5.0F, 7.0F, 7.0F, false), PartPose.offsetAndRotation(-0.01F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, true), PartPose.offsetAndRotation(0.01F, 0.2F, 1.0F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition jaw = mouth.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(9, 4).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.1F, 0.0F, 0.3483677027191016F, 0.0F, 0.0F));
        PartDefinition upperTeeth = mouth.addOrReplaceChild("upperTeeth", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0, 0, 0));
        PartDefinition eye1 = mouth.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(11, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(0.0F, -0.8F, -1.7F, 0.0F, -1.0159561416069327F, 0.0F));
        PartDefinition lowerTeeth = jaw.addOrReplaceChild("lowerTeeth", CubeListBuilder.create().texOffs(16, 5).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, false), PartPose.offsetAndRotation(0.01F, -1.0F, 0.0F, 0, 0, 0));
        PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(-0.01F, 0.2F, 1.0F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition tentacleTop2 = tentacleTop1.addOrReplaceChild("tentacleTop2", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0.35185837453889574F, 0.3127630032889644F, 0.0F));
        PartDefinition tentacleTop3 = tentacleTop2.addOrReplaceChild("tentacleTop3", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0.27366763203903305F, 0.23457224414434488F, 0.0F));
        PartDefinition tentacleMiddle2 = tentacleMiddle1.addOrReplaceChild("tentacleMiddle2", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition tentacleMiddle3 = tentacleMiddle2.addOrReplaceChild("tentacleMiddle3", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition tentacleBottom2 = tentacleBottom1.addOrReplaceChild("tentacleBottom2", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition tentacleBottom3 = tentacleBottom2.addOrReplaceChild("tentacleBottom3", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9F, 0, 0, 0));
        PartDefinition christmasPresent2 = christmasPresent1.addOrReplaceChild("christmasPresent2", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(3.5F, 0.0F, 0.0F, 0.7044148967575558F, 0.5473352640780661F, -0.5085889333785032F));
        PartDefinition christmasPresent3 = christmasPresent1.addOrReplaceChild("christmasPresent3", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(3.5F, 0.0F, 0.0F, -0.5082398928281348F, 0.6651449885876833F, -2.4635321326635524F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.head.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(ShubNiggurathSpawnEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        this.showModels(pEntity);

        this.jaw.xRot = 0.35f + Mth.cos(pAgeInTicks * 0.3f) * this.toRads(15);
        this.head.y = 22.5f - Math.abs(Mth.cos(pAgeInTicks * 0.15f)) * 7;
        this.head.yRot = Mth.cos(pAgeInTicks * 0.15f) * this.toRads(20);
        this.head.zRot = Mth.cos(pAgeInTicks * 0.30f) * this.toRads(20) - 1.57f;

        ShubNiggurathFamiliarModel.rotateTentacles(ImmutableList.of(this.tentacleBottom1, this.tentacleBottom2, this.tentacleBottom3), pAgeInTicks * 2.25f, 0);
        ShubNiggurathFamiliarModel.rotateTentacles(ImmutableList.of(this.tentacleMiddle1, this.tentacleMiddle2, this.tentacleMiddle3), pAgeInTicks * 2.25f, 0.5f);
        ShubNiggurathFamiliarModel.rotateTentacles(ImmutableList.of(this.tentacleTop1, this.tentacleTop2, this.tentacleTop3), pAgeInTicks * 2.25f, 1);
        this.tentacleBottom1.xRot -= this.toRads(90);
        this.tentacleMiddle1.xRot -= this.toRads(90);
        this.tentacleTop1.xRot -= this.toRads(90);
        this.tentacleBottom1.zRot = 0;
        this.tentacleMiddle1.zRot = 0;
        this.tentacleTop1.zRot = 0;
    }
    
    private void showModels(ShubNiggurathSpawnEntity entityIn) {
        boolean isChristmas = FamiliarUtil.isChristmas();
        this.tentacleBottom1.visible = !isChristmas;
        this.tentacleMiddle1.visible = !isChristmas;
        this.tentacleTop1.visible = !isChristmas;
        this.mouth.visible = !isChristmas;
        this.christmasPresent1.visible = isChristmas;
    }

    private float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }
}
