/* MIT License */
package com.klikli_dev.occultism.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.phys.Vec2;

public class BeholderFamiliarRenderState extends LivingEntityRenderState {
    public final Vec2[] eyeRot = {new Vec2(0, 0), new Vec2(0, 0), new Vec2(0, 0), new Vec2(0, 0)};
    public float animationHeight;
    public float eatTimer;
    public boolean isEating;
    public boolean isSitting;
    public boolean isPartying;
    public boolean hasSpikes;
    public boolean hasTongue;
    public boolean hasBeard;
    public boolean hasBlacksmithUpgrade;
    public Vec2 bigEyePos = new Vec2(0, 0);
    public float mouthRot;
}
