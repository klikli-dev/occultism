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

import java.util.List;

import com.github.klikli_dev.occultism.common.entity.BeholderFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;

/**
 * Created using Tabula 8.0.0
 */
public class BeholderFamiliarModel extends EntityModel<BeholderFamiliarEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer head;
    public ModelRenderer eye11;
    public ModelRenderer mouth;
    public ModelRenderer bigEye;
    public ModelRenderer spike1;
    public ModelRenderer spike2;
    public ModelRenderer upperTeeth1;
    public ModelRenderer upperTeeth2;
    public ModelRenderer spike3;
    public ModelRenderer spike4;
    public ModelRenderer spike5;
    public ModelRenderer spike6;
    public ModelRenderer upperTeeth3;
    public ModelRenderer eye21;
    public ModelRenderer eye31;
    public ModelRenderer eye41;
    public ModelRenderer glasses;
    public ModelRenderer eye12;
    public ModelRenderer eye13;
    public ModelRenderer eye14;
    public ModelRenderer eye15;
    public ModelRenderer beard;
    public ModelRenderer lowerTeeth1;
    public ModelRenderer lowerTeeth2;
    public ModelRenderer lowerTeeth3;
    public ModelRenderer tongue;
    public ModelRenderer bigPupil;
    public ModelRenderer eye22;
    public ModelRenderer eye23;
    public ModelRenderer eye24;
    public ModelRenderer eye25;
    public ModelRenderer eye32;
    public ModelRenderer eye33;
    public ModelRenderer eye34;
    public ModelRenderer eye35;
    public ModelRenderer eye42;
    public ModelRenderer eye43;
    public ModelRenderer eye44;
    public ModelRenderer eye45;

    public BeholderFamiliarModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.eye41 = new ModelRenderer(this, 8, 4);
        this.eye41.setPos(-1.5F, -3.9F, -2.7F);
        this.eye41.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.lowerTeeth1 = new ModelRenderer(this, 0, 48);
        this.lowerTeeth1.setPos(0.0F, -1.0F, 0.0F);
        this.lowerTeeth1.addBox(-3.5F, 0.0F, -7.0F, 7.0F, 1.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.upperTeeth2 = new ModelRenderer(this, 0, 40);
        this.upperTeeth2.setPos(0.01F, 3.0F, 0.01F);
        this.upperTeeth2.addBox(-3.5F, 0.0F, -3.5F, 7.0F, 1.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.eye44 = new ModelRenderer(this, 0, 0);
        this.eye44.setPos(0.0F, -1.9F, 0.0F);
        this.eye44.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.eye24 = new ModelRenderer(this, 0, 0);
        this.eye24.setPos(0.0F, -1.9F, 0.0F);
        this.eye24.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.eye34 = new ModelRenderer(this, 0, 0);
        this.eye34.setPos(0.0F, -1.9F, 0.0F);
        this.eye34.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.lowerTeeth3 = new ModelRenderer(this, 24, 57);
        this.lowerTeeth3.setPos(0.0F, -1.0F, -1.0F);
        this.lowerTeeth3.addBox(-2.5F, 0.0F, -5.0F, 5.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.tongue = new ModelRenderer(this, 40, 0);
        this.tongue.setPos(0.0F, -1.0F, -5.0F);
        this.tongue.addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.eye21 = new ModelRenderer(this, 8, 4);
        this.eye21.setPos(2.4F, -3.9F, 2.3F);
        this.eye21.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye31 = new ModelRenderer(this, 8, 4);
        this.eye31.setPos(2.8F, -3.9F, -2.3F);
        this.eye31.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike3 = new ModelRenderer(this, 32, 0);
        this.spike3.setPos(1.0F, -1.6F, 4.0F);
        this.spike3.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spike3, -0.7853981633974483F, 0.0F, 0.4363323129985824F);
        this.bigPupil = new ModelRenderer(this, 24, 0);
        this.bigPupil.setPos(0.0F, -1.0F, -0.7F);
        this.bigPupil.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike4 = new ModelRenderer(this, 32, 0);
        this.spike4.setPos(-2.0F, 0.4F, 4.0F);
        this.spike4.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spike4, -0.7853981633974483F, 0.0F, -0.5235987755982988F);
        this.upperTeeth3 = new ModelRenderer(this, 28, 41);
        this.upperTeeth3.setPos(0.0F, 3.0F, 0.0F);
        this.upperTeeth3.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.eye12 = new ModelRenderer(this, 8, 4);
        this.eye12.setPos(0.01F, -1.9F, 0.0F);
        this.eye12.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye14 = new ModelRenderer(this, 0, 0);
        this.eye14.setPos(0.0F, -1.9F, 0.0F);
        this.eye14.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.eye23 = new ModelRenderer(this, 8, 4);
        this.eye23.setPos(0.0F, -1.9F, 0.0F);
        this.eye23.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye42 = new ModelRenderer(this, 8, 4);
        this.eye42.setPos(0.01F, -1.9F, 0.0F);
        this.eye42.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye43 = new ModelRenderer(this, 8, 4);
        this.eye43.setPos(0.0F, -1.9F, 0.0F);
        this.eye43.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike1 = new ModelRenderer(this, 32, 0);
        this.spike1.setPos(4.0F, -1.6F, 1.8F);
        this.spike1.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spike1, 0.0F, 0.0F, -0.7853981633974483F);
        this.eye15 = new ModelRenderer(this, 0, 4);
        this.eye15.setPos(0.0F, -1.9F, 0.0F);
        this.eye15.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye45 = new ModelRenderer(this, 0, 4);
        this.eye45.setPos(0.0F, -1.9F, 0.0F);
        this.eye45.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye13 = new ModelRenderer(this, 8, 4);
        this.eye13.setPos(0.0F, -1.9F, 0.0F);
        this.eye13.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike5 = new ModelRenderer(this, 32, 0);
        this.spike5.setPos(-4.0F, 0.2F, -1.5F);
        this.spike5.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spike5, 0.27366763203903305F, -0.3127630032889644F, 0.7853981633974483F);
        this.beard = new ModelRenderer(this, 28, 15);
        this.beard.setPos(0.0F, 3.0F, -3.5F);
        this.beard.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.eye33 = new ModelRenderer(this, 8, 4);
        this.eye33.setPos(0.0F, -1.9F, 0.0F);
        this.eye33.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye35 = new ModelRenderer(this, 0, 4);
        this.eye35.setPos(0.0F, -1.9F, 0.0F);
        this.eye35.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike6 = new ModelRenderer(this, 32, 0);
        this.spike6.setPos(-4.0F, -1.6F, 1.8F);
        this.spike6.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spike6, 0.0F, 0.0F, -0.7853981633974483F);
        this.lowerTeeth2 = new ModelRenderer(this, 0, 56);
        this.lowerTeeth2.setPos(0.0F, -1.0F, -0.5F);
        this.lowerTeeth2.addBox(-3.0F, 0.0F, -6.0F, 6.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.upperTeeth1 = new ModelRenderer(this, 0, 31);
        this.upperTeeth1.setPos(0.0F, 3.0F, 0.0F);
        this.upperTeeth1.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, 13.0F, 0.0F);
        this.head.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 7.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.eye11 = new ModelRenderer(this, 8, 4);
        this.eye11.setPos(-2.0F, -3.9F, 2.0F);
        this.eye11.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 0, 15);
        this.mouth.setPos(0.0F, 3.0F, 3.5F);
        this.mouth.addBox(-3.5F, 0.0F, -7.0F, 7.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.3490658503988659F, 0.0F, 0.0F);
        this.bigEye = new ModelRenderer(this, 0, 25);
        this.bigEye.setPos(0.0F, 0.0F, -4.7F);
        this.bigEye.addBox(-3.0F, -3.0F, 0.0F, 6.0F, 5.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.spike2 = new ModelRenderer(this, 32, 0);
        this.spike2.setPos(4.0F, 0.2F, -1.5F);
        this.spike2.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spike2, 0.27366763203903305F, -0.3127630032889644F, 0.7853981633974483F);
        this.eye22 = new ModelRenderer(this, 8, 4);
        this.eye22.setPos(0.01F, -1.9F, 0.0F);
        this.eye22.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye32 = new ModelRenderer(this, 8, 4);
        this.eye32.setPos(0.01F, -1.9F, 0.0F);
        this.eye32.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.eye25 = new ModelRenderer(this, 0, 4);
        this.eye25.setPos(0.0F, -1.9F, 0.0F);
        this.eye25.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.glasses = new ModelRenderer(this, 28, 23);
        this.glasses.setPos(0.0F, -1.2F, -1.5F);
        this.glasses.addBox(-4.5F, -2.0F, -4.5F, 9.0F, 4.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.head.addChild(this.eye41);
        this.mouth.addChild(this.lowerTeeth1);
        this.head.addChild(this.upperTeeth2);
        this.eye43.addChild(this.eye44);
        this.eye23.addChild(this.eye24);
        this.eye33.addChild(this.eye34);
        this.mouth.addChild(this.lowerTeeth3);
        this.mouth.addChild(this.tongue);
        this.head.addChild(this.eye21);
        this.head.addChild(this.eye31);
        this.head.addChild(this.spike3);
        this.bigEye.addChild(this.bigPupil);
        this.head.addChild(this.spike4);
        this.head.addChild(this.upperTeeth3);
        this.eye11.addChild(this.eye12);
        this.eye13.addChild(this.eye14);
        this.eye22.addChild(this.eye23);
        this.eye41.addChild(this.eye42);
        this.eye42.addChild(this.eye43);
        this.head.addChild(this.spike1);
        this.eye14.addChild(this.eye15);
        this.eye44.addChild(this.eye45);
        this.eye12.addChild(this.eye13);
        this.head.addChild(this.spike5);
        this.mouth.addChild(this.beard);
        this.eye32.addChild(this.eye33);
        this.eye34.addChild(this.eye35);
        this.head.addChild(this.spike6);
        this.mouth.addChild(this.lowerTeeth2);
        this.head.addChild(this.upperTeeth1);
        this.head.addChild(this.eye11);
        this.head.addChild(this.mouth);
        this.head.addChild(this.bigEye);
        this.head.addChild(this.spike2);
        this.eye21.addChild(this.eye22);
        this.eye31.addChild(this.eye32);
        this.eye24.addChild(this.eye25);
        this.head.addChild(this.glasses);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
            int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.head).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(BeholderFamiliarEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
            float pNetHeadYaw, float pHeadPitch) {
        showModels(pEntity);

        float partialTicks = Minecraft.getInstance().getFrameTime();

        setRotateAngle(head, 0, 0, 0);

        setEyeRot(pEntity, partialTicks, ImmutableList.of(eye11, eye12, eye13, eye14), 0);
        setEyeRot(pEntity, partialTicks, ImmutableList.of(eye21, eye22, eye23, eye24), 1);
        setEyeRot(pEntity, partialTicks, ImmutableList.of(eye31, eye32, eye33, eye34), 2);
        setEyeRot(pEntity, partialTicks, ImmutableList.of(eye41, eye42, eye43, eye44), 3);

        Vector2f bigEyePos = pEntity.getBigEyePos(partialTicks);
        this.bigPupil.x = bigEyePos.x;
        this.bigPupil.y = bigEyePos.y - 0.5f;

        this.mouth.xRot = pEntity.getMouthRot(partialTicks);

        if (pEntity.isPartying()) {
            float eyeRot = -MathHelper.cos(pAgeInTicks * 0.2f) * toRads(15);
            ImmutableList.of(eye11, eye12, eye13, eye14, eye21, eye22, eye23, eye24, eye31, eye32, eye33, eye34, eye41,
                    eye42, eye43, eye44).forEach(e -> {
                        e.yRot = 0;
                        e.xRot = eyeRot;
                    });

            this.head.xRot = MathHelper.cos(pAgeInTicks * 0.2f) * toRads(20);
            this.head.yRot = MathHelper.cos(pAgeInTicks * 0.2f + PI) * toRads(20);
            this.head.zRot = MathHelper.cos(pAgeInTicks * 0.1f) * toRads(30);
        } else if (pEntity.isSitting()) {
            this.head.zRot = toRads(90);
            this.mouth.xRot = toRads(20);

            ImmutableList.of(eye11, eye12, eye13, eye14, eye21, eye22, eye23, eye24, eye31, eye32, eye33, eye34, eye41,
                    eye42, eye43, eye44).forEach(e -> {
                        e.yRot = 0;
                        e.xRot = 0;
                    });
            eye11.xRot = toRads(25);
            eye12.xRot = toRads(25);
            eye13.xRot = toRads(25);
            eye14.xRot = toRads(25);
            eye11.zRot = toRads(-40);
            eye12.zRot = toRads(-40);
            eye13.zRot = toRads(-40);
            eye14.zRot = toRads(-40);

            eye41.xRot = toRads(-10);
            eye42.xRot = toRads(-10);
            eye43.xRot = toRads(-10);
            eye44.xRot = toRads(-10);
            eye41.zRot = toRads(-40);
            eye42.zRot = toRads(-40);
            eye43.zRot = toRads(-40);
            eye44.zRot = toRads(-40);

            eye21.xRot = toRads(-20);
            eye22.xRot = toRads(-15);
            eye23.xRot = toRads(-10);
            eye24.xRot = toRads(-5);
            eye21.zRot = toRads(2);
            eye22.zRot = toRads(2);
            eye23.zRot = toRads(2);
            eye24.zRot = toRads(2);

            eye31.xRot = toRads(20);
            eye32.xRot = toRads(15);
            eye33.xRot = toRads(10);
            eye34.xRot = toRads(5);
            eye31.zRot = toRads(2);
            eye32.zRot = toRads(2);
            eye33.zRot = toRads(2);
            eye34.zRot = toRads(2);
        }
    }

    private void setEyeRot(BeholderFamiliarEntity entity, float partialTicks, List<ModelRenderer> models, int index) {
        Vector2f rotation = entity.getEyeRot(partialTicks, index);
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
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
