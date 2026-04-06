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

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class GuardianFamiliarModel extends EntityModel<EntityRenderState> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart bodyLower;
    public ModelPart head;
    public ModelPart leftArm1;
    public ModelPart rightArm1;
    public ModelPart leftLeg1;
    public ModelPart rightLeg1;
    public ModelPart leftLeg2;
    public ModelPart rightLeg2;
    public ModelPart crystal1;
    public ModelPart crystal2;
    public ModelPart crystal3;
    public ModelPart leftEye;
    public ModelPart rightEye;
    public ModelPart tree1;
    public ModelPart tree2;
    public ModelPart leftArm2;
    public ModelPart birdBody;
    public ModelPart leftArm3;
    public ModelPart birdLeftLeg;
    public ModelPart birdRightLeg;
    public ModelPart birdHead;
    public ModelPart birdLeftWing;
    public ModelPart birdRightWing;
    public ModelPart birdNose;
    public ModelPart rightArm2;
    public ModelPart rightArm3;

    public GuardianFamiliarModel(ModelPart part) {
        super(part);
        this.body = part.getChild("body");
        this.bodyLower = this.body.getChild("bodyLower");
        this.head = this.body.getChild("head");
        this.leftArm1 = this.body.getChild("leftArm1");
        this.rightArm1 = this.body.getChild("rightArm1");
        this.leftLeg1 = this.bodyLower.getChild("leftLeg1");
        this.rightLeg1 = this.bodyLower.getChild("rightLeg1");
        this.leftLeg2 = this.leftLeg1.getChild("leftLeg2");
        this.rightLeg2 = this.rightLeg1.getChild("rightLeg2");
        this.crystal1 = this.head.getChild("crystal1");
        this.crystal2 = this.head.getChild("crystal2");
        this.crystal3 = this.head.getChild("crystal3");
        this.leftEye = this.head.getChild("leftEye");
        this.rightEye = this.head.getChild("rightEye");
        this.tree1 = this.head.getChild("tree1");
        this.tree2 = this.head.getChild("tree2");
        this.leftArm2 = this.leftArm1.getChild("leftArm2");
        this.birdBody = this.leftArm1.getChild("birdBody");
        this.leftArm3 = this.leftArm2.getChild("leftArm3");
        this.birdLeftLeg = this.birdBody.getChild("birdLeftLeg");
        this.birdRightLeg = this.birdBody.getChild("birdRightLeg");
        this.birdHead = this.birdBody.getChild("birdHead");
        this.birdLeftWing = this.birdBody.getChild("birdLeftWing");
        this.birdRightWing = this.birdBody.getChild("birdRightWing");
        this.birdNose = this.birdHead.getChild("birdNose");
        this.rightArm2 = this.rightArm1.getChild("rightArm2");
        this.rightArm3 = this.rightArm2.getChild("rightArm3");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 6.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 9.1F, 0.0F, 0, 0, 0));
        PartDefinition bodyLower = body.addOrReplaceChild("bodyLower", CubeListBuilder.create().texOffs(26, 0).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 9).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, false), PartPose.offsetAndRotation(5.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.19547687289441354F));
        PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, true), PartPose.offsetAndRotation(-5.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.19198621771937624F));
        PartDefinition leftLeg1 = bodyLower.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(46, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, false), PartPose.offsetAndRotation(2.0F, 2.5F, 0.0F, 0.0F, 0.0F, -0.1563815016444822F));
        PartDefinition rightLeg1 = bodyLower.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(46, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, true), PartPose.offsetAndRotation(-2.0F, 2.5F, 0.0F, 0.0F, 0.0F, 0.1563815016444822F));
        PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 5.5F, 0.0F, 0.0F, 0.0F, 0.11728612207217244F));
        PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, true), PartPose.offsetAndRotation(0.0F, 5.5F, 0.0F, 0.0F, 0.0F, -0.11728612207217244F));
        PartDefinition crystal1 = head.addOrReplaceChild("crystal1", CubeListBuilder.create().texOffs(8, 28).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(0.9F, -1.0F, 0.0F, -0.0781907508222411F, -0.35185837453889574F, 0.4300491170387584F));
        PartDefinition crystal2 = head.addOrReplaceChild("crystal2", CubeListBuilder.create().texOffs(4, 28).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(-1.0F, -1.0F, -1.0F, 0.1563815016444822F, 0.23457224414434488F, 0.19547687289441354F));
        PartDefinition crystal3 = head.addOrReplaceChild("crystal3", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, false), PartPose.offsetAndRotation(-1.1F, -1.0F, 1.0F, -0.11728612207217244F, 0.8210028961170991F, -0.23457224414434488F));
        PartDefinition leftEye = head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, false), PartPose.offsetAndRotation(1.2F, -0.5F, -2.4F, 0.0F, -0.12217304763960307F, 0.12217304763960307F));
        PartDefinition rightEye = head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, true), PartPose.offsetAndRotation(-1.2F, -0.5F, -2.4F, 0.0F, 0.12217304763960307F, -0.12217304763960307F));
        PartDefinition tree1 = head.addOrReplaceChild("tree1", CubeListBuilder.create().texOffs(12, 22).addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition tree2 = head.addOrReplaceChild("tree2", CubeListBuilder.create().texOffs(12, 22).addBox(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(35, 16).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0781907508222411F));
        PartDefinition birdBody = leftArm1.addOrReplaceChild("birdBody", CubeListBuilder.create().texOffs(56, 28).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.3F, -2.9F, 0.0F, -0.5473352640780661F, 0.17453292519943295F, 0.0F));
        PartDefinition leftArm3 = leftArm2.addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(52, 13).addBox(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 5.5F, 0.0F, 0.0F, 0.0F, 0.03909537541112055F));
        PartDefinition birdLeftLeg = birdBody.addOrReplaceChild("birdLeftLeg", CubeListBuilder.create().texOffs(48, 30).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.5F, -0.31F, 0.0F, 0.7428121536172364F, -0.5117305313584153F, -0.4300491170387584F));
        PartDefinition birdRightLeg = birdBody.addOrReplaceChild("birdRightLeg", CubeListBuilder.create().texOffs(48, 30).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(-0.4F, -0.21F, 0.0F, 0.7330382858376184F, 0.5235987755982988F, 0.41887902047863906F));
        PartDefinition birdHead = birdBody.addOrReplaceChild("birdHead", CubeListBuilder.create().texOffs(44, 30).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -0.5F, -0.6F, 0.5082398928281348F, 0.0F, 0.0F));
        PartDefinition birdLeftWing = birdBody.addOrReplaceChild("birdLeftWing", CubeListBuilder.create().texOffs(50, 30).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, false), PartPose.offsetAndRotation(0.7F, -0.6F, 0.0F, 0.0F, -0.3909537457888271F, 0.5082398928281348F));
        PartDefinition birdRightWing = birdBody.addOrReplaceChild("birdRightWing", CubeListBuilder.create().texOffs(50, 30).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, true), PartPose.offsetAndRotation(-0.7F, -0.6F, 0.0F, 0.0F, 0.3909537457888271F, -0.5082398928281348F));
        PartDefinition birdNose = birdHead.addOrReplaceChild("birdNose", CubeListBuilder.create().texOffs(41, 31).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -0.3F, -0.7F, 0, 0, 0));
        PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(35, 16).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, true), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.0781907508222411F));
        PartDefinition rightArm3 = rightArm2.addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(52, 13).addBox(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, true), PartPose.offsetAndRotation(1.0F, 5.5F, 0.0F, 0.0F, 0.0F, -0.03909537541112055F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    // TODO: renderToBuffer is final in Model in 26.1 — custom bird rendering logic removed pending custom RenderState

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    @Override
    public void setupAnim(EntityRenderState state) {
        super.setupAnim(state);
        // TODO: needs custom RenderState to restore entity-specific animation
        // this.showModels(pEntity);
        // int lives = pEntity.getLives();
        // this.head.yRot = netHeadYaw * (PI / 180f);

        this.body.zRot = 0;
        this.rightLeg1.zRot = 0.16f;
        this.rightArm1.zRot = 0.19f;

        // TODO: needs custom RenderState
        // if (pEntity.isSitting()) { ... }
        // if (pEntity.isPartying()) { ... }
        // if (lives == GuardianFamiliarEntity.ONE_LEGGED) { ... }

        // Bird
        // TODO: needs custom RenderState
        // this.birdHead.yRot = ...
        // this.birdHead.xRot = ...
    }

    // TODO: needs custom RenderState to restore entity-specific visibility logic
    // private void showModels(GuardianFamiliarEntity entity) {
    //     boolean hasTree = entity.hasTree();
    //     byte lives = entity.getLives();
    //     this.tree1.visible = hasTree;
    //     this.tree2.visible = hasTree;
    //     this.birdBody.visible = entity.hasBird();
    //     this.leftArm1.visible = lives > 4;
    //     this.leftLeg1.visible = lives > 3;
    //     this.rightLeg1.visible = lives > 2;
    //     this.rightArm1.visible = lives > 1;
    // }

}
