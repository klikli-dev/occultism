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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.ItemStack;

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
                .put(e -> e instanceof Pig, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 2)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof Cow, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 4)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof Sheep, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 2)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof Llama, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 3)));
        BUTCHER_KNIFE_LOOT
                .put(e -> e instanceof Horse, (killedEntity, knife, trueDamageSource) -> Collections.singletonList(
                        new ItemStack(OccultismItems.TALLOW.get(), 3)));
    }
}
