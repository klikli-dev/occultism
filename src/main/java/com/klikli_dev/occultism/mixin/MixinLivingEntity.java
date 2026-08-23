/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.mixin;

import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(
            method = "canGlide",
            at = @At("RETURN"),
            cancellable = true)
    private void occultism$canGlide(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            LivingEntity entity = (LivingEntity) (Object) this;

            if (MovementUtil.allowCustomGlide(entity)) {
                cir.setReturnValue(true);
            }
        }
    }

    @ModifyVariable(
            method = "updateFallFlying",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    private int occultism$freeFallInterval(int freeFallInterval) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasEffect(OccultismEffects.FIRE_WING)) {
            return 1;
        }

        return freeFallInterval;
    }
}
