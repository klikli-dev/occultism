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

package com.github.klikli_dev.occultism.handler;

import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class PlayerEventHandler {
    //region Methods
    @SubscribeEvent
    public void onPlaerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().getItem() == Items.FLINT_AND_STEEL) {
            AxisAlignedBB box = new AxisAlignedBB(-1, -1, -1, 1, 1, 1)
                                        .offset(Math3DUtil.getBlockCenter(event.getPos()));
            List<EntityItem> list = event.getWorld().getEntitiesWithinAABB(EntityItem.class, box,
                    item -> item.getItem().getItem() == ItemRegistry.DATURA);
            if (!list.isEmpty()) {
                BlockPos pos = event.getPos().offset(event.getFace());
                if (!event.getEntityPlayer().canPlayerEdit(pos, event.getFace(), event.getItemStack())) {
                    return;
                }

                list.forEach(Entity::setDead);
                World world = event.getWorld();
                if (world.isAirBlock(pos)) {
                    world.playSound(event.getEntityPlayer(), pos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                            SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
                    world.setBlockState(pos, BlockRegistry.SPIRIT_FIRE.getDefaultState(), 11);
                }
                event.getItemStack().damageItem(1, event.getEntityPlayer());
                event.setCanceled(true);
                event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    //endregion Methods
}
