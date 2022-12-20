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

package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class DragonFamiliarRenderer extends MobRenderer<DragonFamiliarEntity, DragonFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/dragon_familiar.png");

    public DragonFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DRAGON)), 0.3f);
        this.addLayer(new DragonRendering.StickLayer(this));
        this.addLayer(new DragonRendering.SwordLayer(this));
    }


    @Override
    public ResourceLocation getTextureLocation(DragonFamiliarEntity entity) {
        return TEXTURES;
    }

    @EventBusSubscriber(bus = Bus.FORGE, modid = Occultism.MODID, value = Dist.CLIENT)
    private static class RenderText {

        @SubscribeEvent
        public static void renderText(RenderLivingEvent<DragonFamiliarEntity, DragonFamiliarModel> event) {
            if (!(event.getEntity() instanceof DragonFamiliarEntity))
                return;

            DragonFamiliarEntity dragon = (DragonFamiliarEntity) event.getEntity();
            float partialTicks = event.getPartialTick();
            float textTimer = dragon.getPetTimer() + partialTicks;
            if (textTimer >= DragonFamiliarEntity.MAX_PET_TIMER)
                return;

            float height = dragon.getBbHeight() + 0.5f;
            TranslatableComponent text = new TranslatableComponent("dialog.occultism.dragon.pet");
            PoseStack matrixStackIn = event.getPoseStack();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0, height + textTimer / 20, 0);

            matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            matrixStackIn.translate(Mth.sin(textTimer / 2) * 0.5, 0, 0);
            float size = (1 - textTimer / DragonFamiliarEntity.MAX_PET_TIMER) * 0.025f;
            matrixStackIn.scale(-size, -size, size);

            Matrix4f matrix = matrixStackIn.last().pose();
            Font font = event.getRenderer().getFont();
            font.drawInBatch(text, -font.width(text) / 2f, 0, 0xffffff, false, matrix,
                    event.getMultiBufferSource(), false, 0x000000, event.getPackedLight());
            matrixStackIn.popPose();
        }
    }
}
