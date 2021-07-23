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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.item.tool.ButcherKnifeItem;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootEventHandler {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        //Add butcher knife drops dynamically.
        //TODO: Consider doing a global loot table for that
        if (event.isRecentlyHit() && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity trueSource = (LivingEntity) event.getSource().getTrueSource();
            ItemStack knifeItem = trueSource.getHeldItem(InteractionHand.MAIN_HAND);
            if ( knifeItem.getItem() == OccultismItems.BUTCHER_KNIFE.get()) {
                List<ItemStack> loot = ButcherKnifeItem.getLoot(event.getEntityLiving(), knifeItem, trueSource);
                Random rand = event.getEntityLiving().getRNG();

                if (!loot.isEmpty()) {
                    for (ItemStack stack : loot) {
                        ItemStack copy = stack.copy();
                        copy.setCount(rand.nextInt(stack.getCount() + 1) + rand.nextInt(event.getLootingLevel() + 1));
                        Vector3d center = Math3DUtil.center(event.getEntityLiving().getPosition());
                        event.getDrops()
                                .add(new ItemEntity(event.getEntityLiving().level, center.x, center.y, center.z, copy));
                    }
                }
            }
        }
    }
}
