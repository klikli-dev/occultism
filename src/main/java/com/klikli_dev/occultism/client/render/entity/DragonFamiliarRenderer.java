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
import com.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import org.jspecify.annotations.Nullable;

public class DragonFamiliarRenderer extends MobRenderer<DragonFamiliarEntity, LivingEntityRenderState, DragonFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/dragon_familiar.png");

    /**
     * ContextKey used to store the dragon entity reference on the render state so it can be
     * accessed in event handlers where the entity is no longer passed directly.
     */
    static final ContextKey<DragonFamiliarEntity> DRAGON_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "dragon_familiar_entity"));

    public DragonFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DRAGON)), 0.3f);
        this.addLayer(new DragonRendering.StickLayer(this));
        this.addLayer(new DragonRendering.SwordLayer(this));
    }

    @Override
    public void extractRenderState(DragonFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(DRAGON_KEY, entity);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState renderState) {
        return TEXTURES;
    }

    @EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT)
    private static class RenderText {

        @SubscribeEvent
        public static void renderText(RenderLivingEvent.Post<DragonFamiliarEntity, LivingEntityRenderState, DragonFamiliarModel> event) {
            @Nullable DragonFamiliarEntity dragon = event.getRenderState().getRenderData(DRAGON_KEY);
            if (dragon == null)
                return;

            float partialTicks = event.getPartialTick();
            float textTimer = dragon.getPetTimer() + partialTicks;
            if (textTimer >= DragonFamiliarEntity.MAX_PET_TIMER)
                return;

            float height = dragon.getBbHeight() + 0.5f;
            var text = Component.translatable("dialog.occultism.dragon.pet");
            PoseStack matrixStackIn = event.getPoseStack();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0, height + textTimer / 20, 0);

            matrixStackIn.mulPose(net.minecraft.client.Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            matrixStackIn.translate(Mth.sin(textTimer / 2) * 0.5, 0, 0);
            float size = (1 - textTimer / DragonFamiliarEntity.MAX_PET_TIMER) * 0.025f;
            matrixStackIn.scale(-size, -size, size);

            Font font = event.getRenderer().getFont();
            int packedLight = event.getRenderState().lightCoords;
            event.getSubmitNodeCollector().submitText(
                    matrixStackIn,
                    -font.width(text) / 2f,
                    0,
                    text.getVisualOrderText(),
                    false,
                    Font.DisplayMode.NORMAL,
                    packedLight,
                    0xffffff,
                    0x000000,
                    0
            );
            matrixStackIn.popPose();
        }
    }
}
