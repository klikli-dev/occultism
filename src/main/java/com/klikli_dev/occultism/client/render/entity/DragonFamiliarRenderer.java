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
import com.klikli_dev.occultism.client.render.entity.state.DragonFamiliarRenderState;
import com.klikli_dev.occultism.client.render.entity.DragonRendering.StickLayer;
import com.klikli_dev.occultism.client.render.entity.DragonRendering.SwordLayer;
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Post;
import org.jspecify.annotations.Nullable;

public class DragonFamiliarRenderer extends MobRenderer<DragonFamiliarEntity, DragonFamiliarRenderState, DragonFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/dragon_familiar.png");

    /**
     * ContextKey used to store the dragon entity reference on the render state so it can be
     * accessed in event handlers where the entity is no longer passed directly.
     */
    private final ItemModelResolver itemModelResolver;

    public DragonFamiliarRenderer(Context context) {
        super(context, new DragonFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DRAGON)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new StickLayer(this));
        this.addLayer(new SwordLayer(this));
    }

    @Override
    public void extractRenderState(DragonFamiliarEntity entity, DragonFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.dragonEntity = entity;
        reusedState.itemModelResolver = this.itemModelResolver;
    }

    @Override
    public DragonFamiliarRenderState createRenderState() {
        return new DragonFamiliarRenderState();
    }

    @Override
    public Identifier getTextureLocation(DragonFamiliarRenderState renderState) {
        return TEXTURES;
    }

    @EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT)
    private static class RenderText {

        @SubscribeEvent
        public static void renderText(Post<?, ?, ?> event) {
            if (!(event.getRenderState() instanceof DragonFamiliarRenderState dragonState)) {
                return;
            }

            @Nullable DragonFamiliarEntity dragon = dragonState.dragonEntity instanceof DragonFamiliarEntity entity ? entity : null;
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

            matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().camera.rotation());
            matrixStackIn.translate(Mth.sin(textTimer / 2) * 0.5, 0, 0);
            float size = (1 - textTimer / DragonFamiliarEntity.MAX_PET_TIMER) * 0.025f;
            matrixStackIn.scale(-size, -size, size);

            Font font = event.getRenderer().getFont();
            int packedLight = dragonState.lightCoords;
            event.getSubmitNodeCollector().submitText(
                    matrixStackIn,
                    -font.width(text) / 2f,
                    0,
                    text.getVisualOrderText(),
                    false,
                    DisplayMode.NORMAL,
                    packedLight,
                    0xffffff,
                    0x000000,
                    0
            );
            matrixStackIn.popPose();
        }
    }
}
