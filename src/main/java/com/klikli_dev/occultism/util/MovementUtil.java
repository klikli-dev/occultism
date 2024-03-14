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

import com.klikli_dev.occultism.common.capability.DoubleJumpCapability;
import com.klikli_dev.occultism.common.effect.DoubleJumpEffect;
import com.klikli_dev.occultism.registry.OccultismCapabilities;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class MovementUtil {
    public static boolean doubleJump(Player player) {

        if (!allowDoubleJump(player)) {
            return false;
        }

        LazyOptional<DoubleJumpCapability> doubleJumpCapability = player.getCapability(OccultismCapabilities.DOUBLE_JUMP);
        int jumps = doubleJumpCapability.map(DoubleJumpCapability::getJumps).orElse(Integer.MAX_VALUE);
        if (jumps < DoubleJumpEffect.getMaxJumps(player)) {
            player.jumpFromGround();
            doubleJumpCapability.ifPresent(DoubleJumpCapability::addJump);
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

        ItemStack itemstack = player.getItemBySlot(EquipmentSlot.CHEST);
        //If player
        return !itemstack.is(OccultismTags.Items.ELYTRA) || (itemstack.getDamageValue() > 0 && !ElytraItem.isFlyEnabled(itemstack));
    }
}
