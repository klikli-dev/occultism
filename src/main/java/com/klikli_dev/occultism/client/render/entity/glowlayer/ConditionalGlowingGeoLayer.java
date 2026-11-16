// This is a fork of the software.bernie.geckolib.renderer.layer package,
// which only renders when the glowing layer file exists.

package com.klikli_dev.occultism.client.render.entity.glowlayer;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;
import com.geckolib.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class ConditionalGlowingGeoLayer<T extends GeoAnimatable, O, R extends GeoRenderState> extends AutoGlowingGeoLayer<T, O, R> {

    public ConditionalGlowingGeoLayer(GeoRenderer<T, O, R> renderer) {
        super(renderer);
    }

    @Override
    protected @Nullable RenderType getRenderType(R renderState) {
        Identifier emissiveTexture = RenderUtil.getEmissiveResource(this.renderer.getTextureLocation(renderState));

        // Only render if the emissive texture actually exists
        if (Minecraft.getInstance().getResourceManager().getResource(emissiveTexture).isEmpty())
            return null;

        if (renderState instanceof EntityRenderState entityRenderState && entityRenderState.isInvisible)
            return super.getRenderType(renderState);

        // GeckoLib's Windows emissive render type lacks OIT pipelines, which crashes with Improved Transparency.
        // RenderTypes.eyes is the OIT-capable equivalent GeckoLib uses on other platforms.
        return RenderTypes.eyes(emissiveTexture);
    }
}
