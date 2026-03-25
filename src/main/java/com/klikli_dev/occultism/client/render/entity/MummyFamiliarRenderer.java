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

package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.MummyFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.MummyFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;


public class MummyFamiliarRenderer extends MobRenderer<MummyFamiliarEntity, LivingEntityRenderState, MummyFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/mummy_familiar.png");

    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "mummy_is_sitting"));
    private static final ContextKey<Integer> FIGHT_POSE = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "mummy_fight_pose"));
    private static final ContextKey<Float> CAPOW_ALPHA = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "mummy_capow_alpha"));
    private static final ContextKey<Vec3> CAPOW_POSITION = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "mummy_capow_position"));

    public MummyFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new MummyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_MUMMY)), 0.3f);
        this.addLayer(new KapowLayer(this, context));
        this.addLayer(new EyesLayer(this));
    }

    @Override
    public void extractRenderState(MummyFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
        reusedState.setRenderData(FIGHT_POSE, entity.getFightPose());
        if (entity.getFightPose() != -1) {
            reusedState.setRenderData(CAPOW_ALPHA, entity.getCapowAlpha(partialTick));
            reusedState.setRenderData(CAPOW_POSITION, entity.getCapowPosition(partialTick));
        }
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class EyesLayer extends RenderLayer<LivingEntityRenderState, MummyFamiliarModel> {

        private static final Identifier EYES = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/mummy_familiar_eyes.png");

        public EyesLayer(RenderLayerParent<LivingEntityRenderState, MummyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;

            Boolean isSitting = state.getRenderData(MummyFamiliarRenderer.IS_SITTING);
            boolean sitting = isSitting != null && isSitting;

            // For glowing eyes: when not sitting, use full-bright light; when sitting, use normal light
            int eyeLight = sitting ? lightCoords : (15 << 20 | 15 << 4);

            MummyFamiliarModel model = this.getParentModel();
            RenderLayer.renderColoredCutoutModel(model, EYES, poseStack, submitNodeCollector, eyeLight, state, -1, 0);
        }
    }

    private static class KapowLayer extends RenderLayer<LivingEntityRenderState, MummyFamiliarModel> {

        private static final Identifier KAPOW_TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/kapow.png");
        private static final Component KAPOW_TEXT = Component.translatable(
                "dialog.occultism.mummy.kapow");

        private static KapowModel model;
        private final MummyFamiliarRenderer renderer;

        public KapowLayer(MummyFamiliarRenderer renderer, EntityRendererProvider.Context context) {
            super(renderer);
            this.renderer = renderer;
            if (model == null) {
                model = new KapowModel(context.bakeLayer(OccultismModelLayers.KAPOW));
            }
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            Integer fightPose = state.getRenderData(MummyFamiliarRenderer.FIGHT_POSE);
            if (fightPose == null || fightPose == -1)
                return;

            Float alpha = state.getRenderData(MummyFamiliarRenderer.CAPOW_ALPHA);
            Vec3 capowPos = state.getRenderData(MummyFamiliarRenderer.CAPOW_POSITION);
            if (alpha == null || capowPos == null)
                return;

            poseStack.pushPose();
            float scale = 0.5f;
            poseStack.scale(scale, scale, scale);
            poseStack.translate(capowPos.x, -0.4 + capowPos.y, capowPos.z);

            // Render kapow sprite using bufferSource workaround for alpha-blended model
            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
            VertexConsumer kapowBuffer = bufferSource.getBuffer(model.renderType(KAPOW_TEXTURE));
            model.kapow.render(poseStack, kapowBuffer, lightCoords, OverlayTexture.NO_OVERLAY,
                    ((int) (alpha * 255) << 24) | 0x00FFFFFF);
            bufferSource.endBatch(model.renderType(KAPOW_TEXTURE));

            poseStack.pushPose();
            poseStack.scale(0.07f, 0.07f, 0.07f);
            poseStack.translate(0, -2.5, 0);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, 0, 20 * ((float) Math.PI / 180F)));
            Font font = this.renderer.getFont();
            int textColor = 0xff0000 | ((int) (alpha * 255) << 24);

            poseStack.pushPose();
            poseStack.translate(0, 0, -0.01);
            var matrix = poseStack.last().pose();
            font.drawInBatch(KAPOW_TEXT, -font.width(KAPOW_TEXT) / 2f, 0, textColor, true,
                    matrix, bufferSource, Font.DisplayMode.NORMAL, 0, lightCoords);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0, 0, 0.01);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, 180 * ((float) Math.PI / 180F), 0));
            matrix = poseStack.last().pose();
            font.drawInBatch(KAPOW_TEXT, -font.width(KAPOW_TEXT) / 2f, 0, textColor, true,
                    matrix, bufferSource, Font.DisplayMode.NORMAL, 0, lightCoords);
            poseStack.popPose();

            bufferSource.endBatch();

            poseStack.popPose();
            poseStack.popPose();
        }
    }

    public static class KapowModel extends Model {
        public ModelPart kapow;

        public KapowModel(ModelPart part) {
            super(part, (Identifier id) -> RenderTypes.entityTranslucent(id));
            this.kapow = part.getChild("kapow");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition mesh = new MeshDefinition();
            PartDefinition parts = mesh.getRoot();
            parts.addOrReplaceChild("kapow",
                    CubeListBuilder.create().texOffs(0, 0).
                            addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, false),
                    PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
            return LayerDefinition.create(mesh, 64, 32);
        }
    }
}
