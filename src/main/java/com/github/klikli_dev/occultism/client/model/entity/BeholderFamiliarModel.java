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

import com.github.klikli_dev.occultism.common.entity.BeholderFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

import java.util.List;

/**
 * Created using Tabula 8.0.0
 */
public class BeholderFamiliarModel extends EntityModel<BeholderFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart head;
    public ModelPart eye11;
    public ModelPart mouth;
    public ModelPart bigEye;
    public ModelPart spike1;
    public ModelPart spike2;
    public ModelPart upperTeeth1;
    public ModelPart upperTeeth2;
    public ModelPart spike3;
    public ModelPart spike4;
    public ModelPart spike5;
    public ModelPart spike6;
    public ModelPart upperTeeth3;
    public ModelPart eye21;
    public ModelPart eye31;
    public ModelPart eye41;
    public ModelPart glasses;
    public ModelPart eye12;
    public ModelPart eye13;
    public ModelPart eye14;
    public ModelPart eye15;
    public ModelPart beard;
    public ModelPart lowerTeeth1;
    public ModelPart lowerTeeth2;
    public ModelPart lowerTeeth3;
    public ModelPart tongue;
    public ModelPart bigPupil;
    public ModelPart eye22;
    public ModelPart eye23;
    public ModelPart eye24;
    public ModelPart eye25;
    public ModelPart eye32;
    public ModelPart eye33;
    public ModelPart eye34;
    public ModelPart eye35;
    public ModelPart eye42;
    public ModelPart eye43;
    public ModelPart eye44;
    public ModelPart eye45;

    public BeholderFamiliarModel(ModelPart part) {
        this.head = part.getChild("head");
        this.eye11 = this.head.getChild("eye11");
        this.mouth = this.head.getChild("mouth");
        this.bigEye = this.head.getChild("bigEye");
        this.spike1 = this.head.getChild("spike1");
        this.spike2 = this.head.getChild("spike2");
        this.upperTeeth1 = this.head.getChild("upperTeeth1");
        this.upperTeeth2 = this.head.getChild("upperTeeth2");
        this.spike3 = this.head.getChild("spike3");
        this.spike4 = this.head.getChild("spike4");
        this.spike5 = this.head.getChild("spike5");
        this.spike6 = this.head.getChild("spike6");
        this.upperTeeth3 = this.head.getChild("upperTeeth3");
        this.eye21 = this.head.getChild("eye21");
        this.eye31 = this.head.getChild("eye31");
        this.eye41 = this.head.getChild("eye41");
        this.glasses = this.head.getChild("glasses");
        this.eye12 = this.eye11.getChild("eye12");
        this.eye13 = this.eye12.getChild("eye13");
        this.eye14 = this.eye13.getChild("eye14");
        this.eye15 = this.eye14.getChild("eye15");
        this.beard = this.mouth.getChild("beard");
        this.lowerTeeth1 = this.mouth.getChild("lowerTeeth1");
        this.lowerTeeth2 = this.mouth.getChild("lowerTeeth2");
        this.lowerTeeth3 = this.mouth.getChild("lowerTeeth3");
        this.tongue = this.mouth.getChild("tongue");
        this.bigPupil = this.bigEye.getChild("bigPupil");
        this.eye22 = this.eye21.getChild("eye22");
        this.eye23 = this.eye22.getChild("eye23");
        this.eye24 = this.eye23.getChild("eye24");
        this.eye25 = this.eye24.getChild("eye25");
        this.eye32 = this.eye31.getChild("eye32");
        this.eye33 = this.eye32.getChild("eye33");
        this.eye34 = this.eye33.getChild("eye34");
        this.eye35 = this.eye34.getChild("eye35");
        this.eye42 = this.eye41.getChild("eye42");
        this.eye43 = this.eye42.getChild("eye43");
        this.eye44 = this.eye43.getChild("eye44");
        this.eye45 = this.eye44.getChild("eye45");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 7.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0, 0, 0));
        PartDefinition eye11 = head.addOrReplaceChild("eye11", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-2.0F, -3.9F, 2.0F, 0, 0, 0));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, 0.0F, -7.0F, 7.0F, 3.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 3.5F, 0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition bigEye = head.addOrReplaceChild("bigEye", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 5.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -4.7F, 0, 0, 0));
        PartDefinition spike1 = head.addOrReplaceChild("spike1", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(4.0F, -1.6F, 1.8F, 0.0F, 0.0F, -0.7853981633974483F));
        PartDefinition spike2 = head.addOrReplaceChild("spike2", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(4.0F, 0.2F, -1.5F, 0.27366763203903305F, -0.3127630032889644F, 0.7853981633974483F));
        PartDefinition upperTeeth1 = head.addOrReplaceChild("upperTeeth1", CubeListBuilder.create().texOffs(0, 31).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 1.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0, 0, 0));
        PartDefinition upperTeeth2 = head.addOrReplaceChild("upperTeeth2", CubeListBuilder.create().texOffs(0, 40).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 1.0F, 7.0F, false), PartPose.offsetAndRotation(0.01F, 3.0F, 0.01F, 0, 0, 0));
        PartDefinition spike3 = head.addOrReplaceChild("spike3", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(1.0F, -1.6F, 4.0F, -0.7853981633974483F, 0.0F, 0.4363323129985824F));
        PartDefinition spike4 = head.addOrReplaceChild("spike4", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-2.0F, 0.4F, 4.0F, -0.7853981633974483F, 0.0F, -0.5235987755982988F));
        PartDefinition spike5 = head.addOrReplaceChild("spike5", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-4.0F, 0.2F, -1.5F, 0.27366763203903305F, -0.3127630032889644F, 0.7853981633974483F));
        PartDefinition spike6 = head.addOrReplaceChild("spike6", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-4.0F, -1.6F, 1.8F, 0.0F, 0.0F, -0.7853981633974483F));
        PartDefinition upperTeeth3 = head.addOrReplaceChild("upperTeeth3", CubeListBuilder.create().texOffs(28, 41).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 1.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0, 0, 0));
        PartDefinition eye21 = head.addOrReplaceChild("eye21", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(2.4F, -3.9F, 2.3F, 0, 0, 0));
        PartDefinition eye31 = head.addOrReplaceChild("eye31", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(2.8F, -3.9F, -2.3F, 0, 0, 0));
        PartDefinition eye41 = head.addOrReplaceChild("eye41", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(-1.5F, -3.9F, -2.7F, 0, 0, 0));
        PartDefinition glasses = head.addOrReplaceChild("glasses", CubeListBuilder.create().texOffs(28, 23).addBox(-4.5F, -2.0F, -4.5F, 9.0F, 4.0F, 9.0F, false), PartPose.offsetAndRotation(0.0F, -1.2F, -1.5F, 0, 0, 0));
        PartDefinition eye12 = eye11.addOrReplaceChild("eye12", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye13 = eye12.addOrReplaceChild("eye13", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye14 = eye13.addOrReplaceChild("eye14", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye15 = eye14.addOrReplaceChild("eye15", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition beard = mouth.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(28, 15).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, -3.5F, 0, 0, 0));
        PartDefinition lowerTeeth1 = mouth.addOrReplaceChild("lowerTeeth1", CubeListBuilder.create().texOffs(0, 48).addBox(-3.5F, 0.0F, -7.0F, 7.0F, 1.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0, 0, 0));
        PartDefinition lowerTeeth2 = mouth.addOrReplaceChild("lowerTeeth2", CubeListBuilder.create().texOffs(0, 56).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 1.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -0.5F, 0, 0, 0));
        PartDefinition lowerTeeth3 = mouth.addOrReplaceChild("lowerTeeth3", CubeListBuilder.create().texOffs(24, 57).addBox(-2.5F, 0.0F, -5.0F, 5.0F, 1.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0, 0, 0));
        PartDefinition tongue = mouth.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -5.0F, 0, 0, 0));
        PartDefinition bigPupil = bigEye.addOrReplaceChild("bigPupil", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -0.7F, 0, 0, 0));
        PartDefinition eye22 = eye21.addOrReplaceChild("eye22", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye23 = eye22.addOrReplaceChild("eye23", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye24 = eye23.addOrReplaceChild("eye24", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye25 = eye24.addOrReplaceChild("eye25", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye32 = eye31.addOrReplaceChild("eye32", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye33 = eye32.addOrReplaceChild("eye33", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye34 = eye33.addOrReplaceChild("eye34", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye35 = eye34.addOrReplaceChild("eye35", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye42 = eye41.addOrReplaceChild("eye42", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.01F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye43 = eye42.addOrReplaceChild("eye43", CubeListBuilder.create().texOffs(8, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye44 = eye43.addOrReplaceChild("eye44", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        PartDefinition eye45 = eye44.addOrReplaceChild("eye45", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0, 0, 0));
        return LayerDefinition.create(mesh, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.head.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }


    @Override
    public void setupAnim(BeholderFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        this.showModels(pEntity);

        float partialTicks = Minecraft.getInstance().getFrameTime();

        this.setRotateAngle(this.head, 0, 0, 0);

        this.setEyeRot(pEntity, partialTicks, ImmutableList.of(this.eye11, this.eye12, this.eye13, this.eye14), 0);
        this.setEyeRot(pEntity, partialTicks, ImmutableList.of(this.eye21, this.eye22, this.eye23, this.eye24), 1);
        this.setEyeRot(pEntity, partialTicks, ImmutableList.of(this.eye31, this.eye32, this.eye33, this.eye34), 2);
        this.setEyeRot(pEntity, partialTicks, ImmutableList.of(this.eye41, this.eye42, this.eye43, this.eye44), 3);

        Vec2 bigEyePos = pEntity.getBigEyePos(partialTicks);
        this.bigPupil.x = bigEyePos.x;
        this.bigPupil.y = bigEyePos.y - 0.5f;

        this.mouth.xRot = pEntity.getMouthRot(partialTicks);

        if (pEntity.isPartying()) {
            float eyeRot = -Mth.cos(pAgeInTicks * 0.2f) * this.toRads(15);
            ImmutableList.of(this.eye11, this.eye12, this.eye13, this.eye14, this.eye21, this.eye22, this.eye23, this.eye24, this.eye31, this.eye32, this.eye33, this.eye34, this.eye41,
                    this.eye42, this.eye43, this.eye44).forEach(e -> {
                e.yRot = 0;
                e.xRot = eyeRot;
            });

            this.head.xRot = Mth.cos(pAgeInTicks * 0.2f) * this.toRads(20);
            this.head.yRot = Mth.cos(pAgeInTicks * 0.2f + PI) * this.toRads(20);
            this.head.zRot = Mth.cos(pAgeInTicks * 0.1f) * this.toRads(30);
        } else if (pEntity.isSitting()) {
            this.head.zRot = this.toRads(90);
            this.mouth.xRot = this.toRads(20);

            ImmutableList.of(this.eye11, this.eye12, this.eye13, this.eye14, this.eye21, this.eye22, this.eye23, this.eye24, this.eye31, this.eye32, this.eye33, this.eye34, this.eye41,
                    this.eye42, this.eye43, this.eye44).forEach(e -> {
                e.yRot = 0;
                e.xRot = 0;
            });
            this.eye11.xRot = this.toRads(25);
            this.eye12.xRot = this.toRads(25);
            this.eye13.xRot = this.toRads(25);
            this.eye14.xRot = this.toRads(25);
            this.eye11.zRot = this.toRads(-40);
            this.eye12.zRot = this.toRads(-40);
            this.eye13.zRot = this.toRads(-40);
            this.eye14.zRot = this.toRads(-40);

            this.eye41.xRot = this.toRads(-10);
            this.eye42.xRot = this.toRads(-10);
            this.eye43.xRot = this.toRads(-10);
            this.eye44.xRot = this.toRads(-10);
            this.eye41.zRot = this.toRads(-40);
            this.eye42.zRot = this.toRads(-40);
            this.eye43.zRot = this.toRads(-40);
            this.eye44.zRot = this.toRads(-40);

            this.eye21.xRot = this.toRads(-20);
            this.eye22.xRot = this.toRads(-15);
            this.eye23.xRot = this.toRads(-10);
            this.eye24.xRot = this.toRads(-5);
            this.eye21.zRot = this.toRads(2);
            this.eye22.zRot = this.toRads(2);
            this.eye23.zRot = this.toRads(2);
            this.eye24.zRot = this.toRads(2);

            this.eye31.xRot = this.toRads(20);
            this.eye32.xRot = this.toRads(15);
            this.eye33.xRot = this.toRads(10);
            this.eye34.xRot = this.toRads(5);
            this.eye31.zRot = this.toRads(2);
            this.eye32.zRot = this.toRads(2);
            this.eye33.zRot = this.toRads(2);
            this.eye34.zRot = this.toRads(2);
        }
    }

    private void setEyeRot(BeholderFamiliarEntity entity, float partialTicks, List<ModelPart> models, int index) {
        Vec2 rotation = entity.getEyeRot(partialTicks, index);
        for (int i = 0; i < models.size(); i++) {
            models.get(i).xRot = rotation.x;
            models.get(i).zRot = 0;
            models.get(i).yRot = 0;
        }

        models.get(0).xRot = 0;
        models.get(0).yRot = rotation.y;
    }

    private void showModels(BeholderFamiliarEntity entityIn) {
        boolean hasSpikes = entityIn.hasSpikes();

        this.tongue.visible = entityIn.hasTongue();
        this.beard.visible = entityIn.hasBeard();
        this.spike1.visible = hasSpikes;
        this.spike2.visible = hasSpikes;
        this.spike3.visible = hasSpikes;
        this.spike4.visible = hasSpikes;
        this.spike5.visible = hasSpikes;
        this.spike6.visible = hasSpikes;
        this.bigPupil.visible = !entityIn.isSitting();
        this.glasses.visible = entityIn.hasBlacksmithUpgrade();
    }

    private float toRads(float deg) {
        return PI / 180f * deg;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
