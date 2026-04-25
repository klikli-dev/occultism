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

import com.klikli_dev.occultism.client.render.entity.state.GreedyFamiliarRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class GreedyFamiliarModel extends EntityModel<GreedyFamiliarRenderState> {

    private static final float PI = (float) Math.PI;

    public ModelPart body;
    public ModelPart rightArm;
    public ModelPart chest1;
    public ModelPart leftArm;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart head;
    public ModelPart chest2;
    public ModelPart monster;
    public ModelPart monsterLeftEye;
    public ModelPart monsterRightEye;
    public ModelPart monsterLeftEar;
    public ModelPart monsterRightEar;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart nose;

    public GreedyFamiliarModel(ModelPart part) {
        super(part);
        this.body = part.getChild("body");
        this.rightArm = this.body.getChild("rightArm");
        this.chest1 = this.body.getChild("chest1");
        this.leftArm = this.body.getChild("leftArm");
        this.rightLeg = this.body.getChild("rightLeg");
        this.leftLeg = this.body.getChild("leftLeg");
        this.head = this.body.getChild("head");
        this.chest2 = this.chest1.getChild("chest2");
        this.monster = this.chest1.getChild("monster");
        this.monsterLeftEye = this.monster.getChild("monsterLeftEye");
        this.monsterRightEye = this.monster.getChild("monsterRightEye");
        this.monsterLeftEar = this.monster.getChild("monsterLeftEar");
        this.monsterRightEar = this.monster.getChild("monsterRightEar");
        this.leftEar = this.head.getChild("leftEar");
        this.rightEar = this.head.getChild("rightEar");
        this.nose = this.head.getChild("nose");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 5.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 19.0F, 0.6F, 0, 0, 0));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(16, 21).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition chest1 = body.addOrReplaceChild("chest1", CubeListBuilder.create().texOffs(12, 14).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.5F, 1.5F, 0, 0, 0));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(16, 21).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, true), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, false), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, 0, 0, 0));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0, 0, 0));
        PartDefinition chest2 = chest1.addOrReplaceChild("chest2", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 1.0555751236166873F, 0.0F, 0.0F));
        PartDefinition monster = chest1.addOrReplaceChild("monster", CubeListBuilder.create().texOffs(15, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 1.5F, 0.1563815016444822F, -0.11728612207217244F, 0.0F));
        monster.addOrReplaceChild("monsterLeftEye", CubeListBuilder.create().texOffs(21, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-0.8F, -1.8F, 1.8F, 0, 0, 0));
        monster.addOrReplaceChild("monsterRightEye", CubeListBuilder.create().texOffs(21, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, true), PartPose.offsetAndRotation(0.8F, -1.8F, 1.8F, 0, 0, 0));
        monster.addOrReplaceChild("monsterLeftEar", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(-0.6F, -1.6F, 0.3F, -0.3127630032889644F, 0.0781907508222411F, -0.35185837453889574F));
        monster.addOrReplaceChild("monsterRightEar", CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(0.6F, -1.6F, 0.3F, -0.3127630032889644F, 0.0781907508222411F, 0.35185837453889574F));
        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, false), PartPose.offsetAndRotation(2.0F, -4.5F, 0.0F, 0.0F, 0.0F, 0.5235987755982988F));
        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, true), PartPose.offsetAndRotation(-2.0F, -4.5F, 0.0F, 0.0F, 0.0F, -0.5235987755982988F));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(18, 8).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -3.0F, -2.5F, 0, 0, 0));
        return LayerDefinition.create(mesh, 32, 32);
    }

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    @Override
    public void setupAnim(GreedyFamiliarRenderState state) {
        super.setupAnim(state);
        this.head.yRot = state.yRot * (PI / 180f);
        this.head.xRot = state.xRot * (PI / 180f);
        this.head.zRot = 0;
        this.rightArm.zRot = 0;
        this.leftArm.zRot = 0;
        this.leftEar.zRot = -state.earRotZ;
        this.rightEar.zRot = state.earRotZ;
        this.leftEar.xRot = state.earRotX;
        this.rightEar.xRot = state.earRotX;

        if (!state.isPartying) {
            this.chest2.xRot = state.lidRot;
            this.monster.y = -0.2f - state.lidRot * 3;
            this.monster.yRot = state.monsterRot;
            this.monster.xRot = 0;
        } else {
            this.chest2.xRot = this.toRad(40);
            this.monster.y = -2.5f;
            this.monster.yRot = 0;
            this.monster.xRot = Mth.cos(state.ageInTicks) * this.toRad(15);
        }

        if (state.isPartying) {
            this.rightArm.xRot = Mth.cos(state.ageInTicks + PI) * this.toRad(20) + this.toRad(180);
            this.leftArm.xRot = Mth.cos(state.ageInTicks) * this.toRad(20) + this.toRad(180);
            this.rightArm.zRot = -this.toRad(20);
            this.leftArm.zRot = this.toRad(20);
            this.head.zRot = Mth.sin(state.ageInTicks) * this.toRad(20);
            if (!state.isVehicle) {
                this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.5f) * 1.4f * state.walkAnimationSpeed;
                this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.5f + PI) * 1.4f * state.walkAnimationSpeed;
            } else {
                this.rightLeg.xRot = -PI / 2;
                this.leftLeg.xRot = -PI / 2;
            }
        } else if (state.isSitting || state.isVehicle) {
            this.rightArm.xRot = 0;
            this.leftArm.xRot = 0;
            this.rightLeg.xRot = -PI / 2;
            this.leftLeg.xRot = -PI / 2;
        } else {
            this.rightArm.xRot = Mth.cos(state.walkAnimationPos * 0.5f + PI) * state.walkAnimationSpeed;
            this.leftArm.xRot = Mth.cos(state.walkAnimationPos * 0.5f) * state.walkAnimationSpeed;
            this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.5f) * 1.4f * state.walkAnimationSpeed;
            this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.5f + PI) * 1.4f * state.walkAnimationSpeed;
        }

        this.chest1.zRot = Mth.cos(state.walkAnimationPos * 0.5f + PI) * state.walkAnimationSpeed * 0.2f;

        if (state.hasTargetBlock) {
            this.rightArm.xRot = -this.toRad(100) + Mth.cos(state.walkAnimationPos * 0.5f + PI) * state.walkAnimationSpeed;
        }
    }
}
