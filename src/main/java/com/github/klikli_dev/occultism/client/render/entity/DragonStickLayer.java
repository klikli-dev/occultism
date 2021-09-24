package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.DragonFamiliarEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Quaternion;

public class DragonStickLayer extends LayerRenderer<DragonFamiliarEntity, DragonFamiliarModel> {
    public DragonStickLayer(IEntityRenderer<DragonFamiliarEntity, DragonFamiliarModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
            DragonFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.hasStick())
            return;
        matrixStackIn.push();
        DragonFamiliarModel model = this.getEntityModel();
        model.body.translateRotate(matrixStackIn);
        model.neck1.translateRotate(matrixStackIn);
        model.neck2.translateRotate(matrixStackIn);
        model.head.translateRotate(matrixStackIn);
        model.jaw.translateRotate(matrixStackIn);

        matrixStackIn.translate(-0.08, -0.07, -0.15);
        matrixStackIn.rotate(new Quaternion(0, 0, -45, true));

        Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entitylivingbaseIn, new ItemStack(Items.STICK),
                ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
    }
}