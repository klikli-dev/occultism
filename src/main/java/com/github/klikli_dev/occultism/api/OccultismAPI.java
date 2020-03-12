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

package com.github.klikli_dev.occultism.api;

import com.github.klikli_dev.occultism.api.common.misc.IButcherKnifeLoot;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class OccultismAPI {
    //region Fields
    public static final Map<Predicate<LivingEntity>, IButcherKnifeLoot> BUTCHER_KNIFE_LOOT = new HashMap<>();
    //endregion Fields

    //region Static Methods
    public static void commonSetup() {
        registerButcherKnifeLoot();
    }

    public static void registerButcherKnifeLoot() {
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof PigEntity, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 2)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof CowEntity, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 4)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof SheepEntity, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 2)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof LlamaEntity, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 3)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof HorseEntity, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 3)));
    }
}
