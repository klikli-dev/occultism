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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import com.klikli_dev.occultism.registry.OccultismCapabilities;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class FamiliarUtil {

    public static List<LivingEntity> getOwnerEnemies(LivingEntity owner, LivingEntity familiar, float range) {
        if (null == owner)
            return new ArrayList<>();

        LivingEntity revenge = owner.getLastHurtByMob();
        LivingEntity target = owner.getLastHurtMob();
        List<LivingEntity> enemies = new ArrayList<>();
        if (isClose(revenge, familiar, range))
            enemies.add(revenge);
        if (isClose(target, familiar, range))
            enemies.add(target);
        return enemies;
    }

    private static boolean isClose(LivingEntity e, LivingEntity familiar, float range) {
        return e != null && e != familiar && e.distanceToSqr(familiar) < range;
    }

    public static boolean isFamiliarEnabled(LivingEntity owner, EntityType<? extends IFamiliar> familiar) {
        return owner.getData(OccultismDataStorage.FAMILIAR_SETTINGS).isFamiliarEnabled(familiar);
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
        List<T> equipped = getAllEquippedFamiliars(owner, type, pred);
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
        return owner.level().getEntities(type, owner.getBoundingBox().inflate(10),
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
        var cap = owner.getCapability(CuriosCapability.INVENTORY);
        if(cap == null)
            return familiars;

        var curios = cap.getEquippedCurios();
        for (int i = 0; i < curios.getSlots(); i++) {
            IFamiliar familiar = FamiliarRingItem.getFamiliar(curios.getStackInSlot(i), owner.level());
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

    public static float toRads(float deg) {
        return (float) Math.toRadians(deg);
    }

    public static boolean isChristmas() {
        if (Occultism.CLIENT_CONFIG.visuals.disableHolidayTheming.get())
            return false;

        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 15)
                || calendar.get(Calendar.MONTH) == Calendar.JANUARY && calendar.get(Calendar.DAY_OF_MONTH) <= 15;
    }
}
