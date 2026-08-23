/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.common.effect.DoubleJumpEffect;
import com.klikli_dev.occultism.common.entity.familiar.OtherworldBirdEntity;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

public class MovementUtil {
    public static boolean doubleJump(Player player) {

        if (!allowDoubleJump(player)) {
            return false;
        }

        int jumps = player.getData(OccultismDataStorage.DOUBLE_JUMP);
        if (jumps < DoubleJumpEffect.getMaxJumps(player)) {
            player.jumpFromGround();
            player.setData(OccultismDataStorage.DOUBLE_JUMP, jumps + 1);
            return true;
        }
        return false;
    }

    public static boolean allowDoubleJump(Player player) {

        //If swimming, flying, on the ground(= normal jump) or mounted, no double jump
        boolean swimming = player.isInWater() || player.isInLava();
        if (player.onGround() || player.isPassenger() || player.getAbilities().flying || swimming) {
            return false;
        }

        //If player is gliding and has no "wing" will still only glide, no double jump
        boolean wing = player.hasEffect(OccultismEffects.FIRE_WING)
                || FamiliarUtil.hasFamiliar(player, OccultismEntities.DRIKWING_FAMILIAR.get(), OtherworldBirdEntity::hasIesniumUpgrade);
        return !player.isFallFlying() || wing;
    }

    public static boolean allowCustomGlide(LivingEntity livingEntity) {
        if (livingEntity.onGround() || livingEntity.isPassenger() || livingEntity.hasEffect(MobEffects.LEVITATION)
                || livingEntity.isInWater() || livingEntity.isInLava()) {
            return false;
        }

        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            ItemStack itemStack = livingEntity.getItemBySlot(slot);
            if (itemStack.has(DataComponents.GLIDER)) {
                Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
                if (equippable != null && slot == equippable.slot() && !itemStack.nextDamageWillBreak())
                    return false;
            }
        }

        return livingEntity.hasEffect(OccultismEffects.FIRE_WING);
    }
}
