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
import com.github.klikli_dev.occultism.network.MessageDoubleJump;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.github.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventHandler {
    //region Static Methods
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().getItem() == Items.FLINT_AND_STEEL) {
            AxisAlignedBB box = new AxisAlignedBB(-1, -1, -1, 1, 1, 1)
                                        .offset(Math3DUtil.center(event.getPos()));
            List<ItemEntity> list = event.getWorld().getEntitiesWithinAABB(ItemEntity.class, box,
                    item -> item.getItem().getItem() == OccultismItems.DATURA.get());
            if (!list.isEmpty()) {
                BlockPos pos = event.getPos().offset(event.getFace());
                if (!event.getPlayer().canPlayerEdit(pos, event.getFace(), event.getItemStack())) {
                    return;
                }

                list.forEach(Entity::remove);
                World world = event.getWorld();
                if (world.isAirBlock(pos)) {
                    world.playSound(event.getPlayer(), pos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                            SoundCategory.PLAYERS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
                    world.setBlockState(pos, OccultismBlocks.SPIRIT_FIRE.get().getDefaultState(), 11);
                }
                event.getItemStack().damageItem(1, event.getPlayer(), (player) -> {
                    player.sendBreakAnimation(event.getHand());
                });
                event.setCanceled(true);
                event.getPlayer().swingArm(Hand.MAIN_HAND);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getItemStack().getItem() == OccultismItems.SOUL_GEM_ITEM.get() && event.getTarget() instanceof LivingEntity) {
            //called from here to bypass sitting entity's sit command.
            if (OccultismItems.SOUL_GEM_ITEM.get()
                        .itemInteractionForEntity(event.getItemStack(), event.getPlayer(), (LivingEntity) event.getTarget(),
                                event.getHand())){
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(final InputEvent.KeyInputEvent evt) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.isGameFocused() || evt.getAction() != GLFW_PRESS) {
            return;
        }

        if (minecraft.gameSettings.keyBindJump.isKeyDown()) {

            if (minecraft.player != null && MovementUtil.doubleJump(minecraft.player)) {
                OccultismPackets.sendToServer(new MessageDoubleJump());
            }


        }

    }
    //endregion Static Methods
}
