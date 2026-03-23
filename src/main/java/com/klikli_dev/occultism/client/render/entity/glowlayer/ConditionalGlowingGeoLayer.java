// This is a fork of the software.bernie.geckolib.renderer.layer package,
// which only renders when the glowing layer file exists.

package com.klikli_dev.occultism.client.render.entity.glowlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.cache.object.BakedGeoModel;
import com.geckolib.cache.texture.AutoGlowingTexture;
import com.geckolib.renderer.GeoRenderer;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.util.ClientUtil;

public class ConditionalGlowingGeoLayer <T extends GeoAnimatable> extends GeoRenderLayer<T> {
    public ConditionalGlowingGeoLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    @Nullable
    protected RenderType getRenderType(T animatable) {
        if (!(animatable instanceof Entity entity))
            return AutoGlowingTexture.getRenderType(getTextureResource(animatable));

        boolean invisible = entity.isInvisible();
        Identifier texture = AutoGlowingTexture.getEmissiveResource(getTextureResource(animatable));

        if (Minecraft.getInstance().getResourceManager().getResource(texture).isEmpty())
            return null;

        if (invisible && !entity.isInvisibleTo(ClientUtil.getClientPlayer()))
            return RenderType.itemEntityTranslucentCull(texture);

        if (Minecraft.getInstance().shouldEntityAppearGlowing(entity)) {
            if (invisible)
                return RenderType.outline(texture);

            return AutoGlowingTexture.getOutlineRenderType(getTextureResource(animatable));
        }

        return invisible ? null : AutoGlowingTexture.getRenderType(getTextureResource(animatable));
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        renderType = getRenderType(animatable);

        if (renderType != null) {
            getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, renderType,
                    bufferSource.getBuffer(renderType), partialTick, 15728640, packedOverlay,
                    getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
        }
    }
}

