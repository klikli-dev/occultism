package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.DragonFamiliarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DragonStickLayer extends RenderLayer<DragonFamiliarEntity, DragonFamiliarModel> {
    public DragonStickLayer(RenderLayerParent<DragonFamiliarEntity, DragonFamiliarModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, DragonFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.hasStick())
            return;

        pMatrixStack.pushPose();
        DragonFamiliarModel model = this.getParentModel();
        model.body.translateAndRotate(pMatrixStack);
        model.neck1.translateAndRotate(pMatrixStack);
        model.neck2.translateAndRotate(pMatrixStack);
        model.head.translateAndRotate(pMatrixStack);
        model.jaw.translateAndRotate(pMatrixStack);

        pMatrixStack.translate(-0.08, -0.07, -0.15);
        pMatrixStack.mulPose(new Quaternion(0, 0, -45, true));

        Minecraft.getInstance().getItemInHandRenderer().renderItem(pLivingEntity, new ItemStack(Items.STICK),
                ItemTransforms.TransformType.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }
}