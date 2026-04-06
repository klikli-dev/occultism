// This is a fork of the software.bernie.geckolib.renderer.layer package,
// which only renders when the glowing layer file exists.

package com.klikli_dev.occultism.client.render.entity.glowlayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;
import com.geckolib.util.RenderUtil;

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

        return super.getRenderType(renderState);
    }
}
