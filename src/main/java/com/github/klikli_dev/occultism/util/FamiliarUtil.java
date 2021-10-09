/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

import java.util.List;

import com.github.klikli_dev.occultism.common.entity.IFamiliar;
import com.github.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class FamiliarUtil {

    public static boolean isFamiliarEnabled(PlayerEntity player, EntityType<? extends IFamiliar> familiar) {
        return player.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).lazyMap(c -> c.isFamiliarEnabled(familiar))
                .orElse(false);
    }

    public static <T extends Entity & IFamiliar> boolean hasFamiliar(PlayerEntity player, EntityType<T> type) {
        return getFamiliar(player, type) != null;
    }

    public static <T extends Entity & IFamiliar> T getFamiliar(PlayerEntity player, EntityType<T> type) {
        T familiar = getEquippedFamiliar(player, type);
        if (familiar == null)
            familiar = getNearbyFamiliar(player, type);
        return familiar;
    }

    public static <T extends Entity & IFamiliar> T getNearbyFamiliar(PlayerEntity player, EntityType<T> type) {
        List<T> nearby = player.level.getEntities(type, player.getBoundingBox().inflate(10),
                e -> e.getFamiliarOwner() == player && e.isAlive());
        return nearby.isEmpty() ? null : nearby.get(0);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity & IFamiliar> T getEquippedFamiliar(PlayerEntity player, EntityType<T> type) {
        IItemHandlerModifiable curios = CuriosApi.getCuriosHelper().getEquippedCurios(player).orElse(null);
        if (curios == null)
            return null;

        for (int i = 0; i < curios.getSlots(); i++) {
            IFamiliar familiar = FamiliarRingItem.getFamiliar(curios.getStackInSlot(i), player.level);
            if (familiar != null && familiar.getEntity().getType() == type)
                return (T) familiar.getEntity();
        }

        return null;
    }
}
