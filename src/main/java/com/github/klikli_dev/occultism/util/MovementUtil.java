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

package com.github.klikli_dev.occultism.util;

import com.github.klikli_dev.occultism.common.capability.DoubleJumpCapability;
import com.github.klikli_dev.occultism.common.effect.DoubleJumpEffect;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.util.LazyOptional;

public class MovementUtil {
    public static boolean doubleJump(PlayerEntity player) {

        if (!allowDoubleJump(player)) {
            return false;
        }

        LazyOptional<DoubleJumpCapability> doubleJumpCapability = player.getCapability(OccultismCapabilities.DOUBLE_JUMP);
        int jumps = doubleJumpCapability.map(DoubleJumpCapability::getJumps).orElse(Integer.MAX_VALUE);
        if (jumps < DoubleJumpEffect.getMaxJumps(player)) {
            player.jump();
            doubleJumpCapability.ifPresent(DoubleJumpCapability::addJump);
            return true;
        }
        return false;
    }

    public static boolean allowDoubleJump(PlayerEntity player) {

        //If swimming, flying, on the ground(= normal jump) or mounted, no double jump
        boolean swimming = player.isInWater() || player.isInLava();
        if ( player.isOnGround() || player.isPassenger() || player.abilities.isFlying || swimming) {
            return false;
        }

        ItemStack itemstack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        //If player
        if(OccultismTags.ELYTRA.contains(itemstack.getItem()) && (itemstack.getDamage() <= 0 || ElytraItem.isUsable(itemstack))){
            return false;
        }

        return true;
    }
}
