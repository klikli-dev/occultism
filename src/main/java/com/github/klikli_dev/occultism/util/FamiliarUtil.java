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

import com.github.klikli_dev.occultism.common.entity.IFamiliar;
import com.github.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FamiliarUtil {

    public static boolean isFamiliarEnabled(LivingEntity owner, EntityType<? extends IFamiliar> familiar) {
        return owner.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).lazyMap(c -> c.isFamiliarEnabled(familiar))
                .orElse(false);
    }

    public static <T extends Entity & IFamiliar> boolean hasFamiliar(LivingEntity owner, EntityType<T> type) {
        return hasFamiliar(owner, type, f -> true);
    }

    public static <T extends Entity & IFamiliar> boolean hasFamiliar(LivingEntity owner, EntityType<T> type,
                                                                     Predicate<T> pred) {
        return getFamiliar(owner, type, pred) != null;
    }

    public static <T extends Entity & IFamiliar> List<T> getAllFamiliars(LivingEntity owner, EntityType<T> type) {
        return getAllFamiliars(owner, type, f -> true);
    }

    public static <T extends Entity & IFamiliar> List<T> getAllFamiliars(LivingEntity owner, EntityType<T> type,
                                                                         Predicate<T> pred) {
        List<T> nearby = getAllNearbyFamiliars(owner, type, pred);
        List<T> equipped = getAllNearbyFamiliars(owner, type, pred);
        nearby.addAll(equipped);
        return nearby;
    }

    public static <T extends Entity & IFamiliar> T getFamiliar(LivingEntity owner, EntityType<T> type) {
        return getFamiliar(owner, type, f -> true);
    }

    public static <T extends Entity & IFamiliar> T getFamiliar(LivingEntity owner, EntityType<T> type,
                                                               Predicate<T> pred) {
        T familiar = getEquippedFamiliar(owner, type, pred);
        if (familiar == null)
            familiar = getNearbyFamiliar(owner, type, pred);
        return familiar;
    }

    public static <T extends Entity & IFamiliar> List<T> getAllNearbyFamiliars(LivingEntity owner, EntityType<T> type,
                                                                               Predicate<T> pred) {
        return owner.level.getEntities(type, owner.getBoundingBox().inflate(10),
                e -> pred.test(e) && e.getFamiliarOwner() == owner && e.isAlive());
    }

    public static <T extends Entity & IFamiliar> T getNearbyFamiliar(LivingEntity owner, EntityType<T> type,
                                                                     Predicate<T> pred) {
        List<T> nearby = getAllNearbyFamiliars(owner, type, pred);
        return nearby.isEmpty() ? null : nearby.get(0);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity & IFamiliar> List<T> getAllEquippedFamiliars(LivingEntity owner, EntityType<T> type,
                                                                                 Predicate<T> pred) {
        List<T> familiars = new ArrayList<>();
        var curios = CuriosApi.getCuriosHelper().getEquippedCurios(owner).orElse(null);
        if (curios == null)
            return familiars;

        for (int i = 0; i < curios.getSlots(); i++) {
            IFamiliar familiar = FamiliarRingItem.getFamiliar(curios.getStackInSlot(i), owner.level);
            if (familiar != null && familiar.getFamiliarEntity().getType() == type) {
                T fam = (T) familiar.getFamiliarEntity();
                if (pred.test(fam))
                    familiars.add(fam);
            }
        }

        return familiars;
    }

    public static <T extends Entity & IFamiliar> T getEquippedFamiliar(LivingEntity owner, EntityType<T> type,
                                                                       Predicate<T> pred) {
        List<T> familiars = getAllEquippedFamiliars(owner, type, pred);
        return familiars.isEmpty() ? null : familiars.get(0);
    }
}
