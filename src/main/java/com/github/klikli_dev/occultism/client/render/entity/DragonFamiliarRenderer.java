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
import com.github.klikli_dev.occultism.common.entity.DragonFamiliarEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class DragonFamiliarRenderer extends MobRenderer<DragonFamiliarEntity, DragonFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/dragon_familiar.png");

    public DragonFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DragonFamiliarModel(), 0.3f);
        this.addLayer(new DragonRendering.StickLayer(this));
        this.addLayer(new DragonRendering.SwordLayer(this));
    }

    @Override
    public void render(DragonFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
                       IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
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
            float partialTicks = event.getPartialRenderTick();
            float textTimer = dragon.getPetTimer() + partialTicks;
            if (textTimer >= DragonFamiliarEntity.MAX_PET_TIMER)
                return;

            float height = dragon.getBbHeight() + 0.5f;
            IFormattableTextComponent text = new TranslationTextComponent("dialog.occultism.dragon.pet");
            MatrixStack matrixStackIn = event.getMatrixStack();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0, height + textTimer / 20, 0);
            matrixStackIn.mulPose(event.getRenderer().getDispatcher().cameraOrientation());
            matrixStackIn.translate(MathHelper.sin(textTimer / 2) * 0.5, 0, 0);
            float size = (1 - textTimer / DragonFamiliarEntity.MAX_PET_TIMER) * 0.025f;
            matrixStackIn.scale(-size, -size, size);

            Matrix4f matrix = matrixStackIn.last().pose();
            FontRenderer font = event.getRenderer().getDispatcher().getFont();
            font.drawInBatch(text, -font.width(text) / 2f, 0, 0xffffff, false, matrix,
                    event.getBuffers(), false, 0x000000, event.getLight());
            matrixStackIn.popPose();
        }
    }
}
