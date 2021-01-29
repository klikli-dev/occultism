/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

import com.github.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class CuriosUtil {
    public static boolean hasGoggles(PlayerEntity player){
        ItemStack helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if(helmet.getItem() instanceof OtherworldGogglesItem)
            return true;

        Optional<Boolean> hasGoggles = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(curiosHandler -> {

            Optional<Boolean> hasGogglesStack = curiosHandler.getStacksHandler(
                    SlotTypePreset.HEAD.getIdentifier()).map(slotHandler -> {
                IDynamicStackHandler stackHandler = slotHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if(stack.getItem() instanceof OtherworldGogglesItem){
                        return true;

                    }
                }
                return false;
            });
            return hasGogglesStack.orElse(false);
        });

        return hasGoggles.orElse(false);
    }

}
