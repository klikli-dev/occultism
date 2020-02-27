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

package com.github.klikli_dev.occultism.common.item.tool;

import com.github.klikli_dev.occultism.api.OccultismAPI;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class ItemButcherKnife extends SwordItem {
    //region Initialization
    public ItemButcherKnife() {
        super(ToolMaterial.IRON);
        ItemRegistry.registerItem(this, "butcher_knife");
        MinecraftForge.EVENT_BUS.register(this);
    }
    //endregion Initialization

    //region Static Methods
    private static List<ItemStack> getLoot(LivingEntity entity) {
        List<ItemStack> loot = new ArrayList<>();
        for (Predicate<LivingEntity> predicate : OccultismAPI.BUTCHER_KNIFE_LOOT.keySet())
            if (predicate.test(entity))
                loot.addAll(OccultismAPI.BUTCHER_KNIFE_LOOT.get(predicate));
        return loot;
    }
    //endregion Static Methods

    //region Methods
    @SubscribeEvent
    public void livingDrop(LivingDropsEvent event) {
        Entity trueSource = event.getSource().getTrueSource();
        if (event.isRecentlyHit() && trueSource instanceof LivingEntity &&
            ((LivingEntity) trueSource).getHeldItemMainhand().getItem() == ItemRegistry.BUTCHER_KNIFE) {
            List<ItemStack> loot = getLoot(event.getEntityLiving());
            Random rand = event.getEntityLiving().getRNG();

            if (!loot.isEmpty()) {
                for (ItemStack stack : loot) {
                    ItemStack copy = stack.copy();
                    copy.setCount(rand.nextInt(stack.getCount() + 1) + rand.nextInt(event.getLootingLevel() + 1));
                    Vec3d center = Math3DUtil.getBlockCenter(event.getEntityLiving().getPosition());
                    event.getDrops()
                            .add(new ItemEntity(event.getEntityLiving().world, center.x, center.y, center.z, copy));
                }
            }
        }
    }
    //endregion Methods
}
