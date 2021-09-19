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
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventHandler {
    //region Static Methods
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        dancingFamiliars(event);
        boolean isFlintAndSteel = event.getItemStack().getItem() == Items.FLINT_AND_STEEL;
        boolean isFireCharge = event.getItemStack().getItem() == Items.FIRE_CHARGE;
        if (isFlintAndSteel || isFireCharge) {
            //find if there is any datura
            AABB box = new AABB(-1, -1, -1, 1, 1, 1)
                                        .move(Math3DUtil.center(event.getPos()));
            List<ItemEntity> list = event.getWorld().getEntitiesOfClass(ItemEntity.class, box,
                    item -> item.getItem().getItem() == OccultismItems.DATURA.get());
            if (!list.isEmpty()) {
                //if there is datura, check if we can edit the target face
                BlockPos pos = event.getPos().relative(event.getFace());
                if (!event.getPlayer().mayUseItemAt(pos, event.getFace(), event.getItemStack())) {
                    return;
                }

                //consume all datura
                list.forEach(e -> e.remove(Entity.RemovalReason.DISCARDED));

                Level level = event.getWorld();
                //if there is air, place block and play sound
                if (level.isEmptyBlock(pos)) {
                    //sound based on the item used
                    SoundEvent soundEvent =
                            isFlintAndSteel ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRECHARGE_USE;
                    level.playSound(event.getPlayer(), pos, soundEvent,
                            SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);

                    level.setBlock(pos, OccultismBlocks.SPIRIT_FIRE.get().defaultBlockState(), 11);
                }

                //now handle used item
                if (isFlintAndSteel) {
                    event.getItemStack().hurtAndBreak(1, event.getPlayer(), (player) -> {
                        player.broadcastBreakEvent(event.getHand());
                    });
                }
                else if (isFireCharge) {
                    event.getItemStack().shrink(1);
                }

                //finally, cancel original event to prevent real action and show use animation
                event.setCanceled(true);
                event.getPlayer().swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    private static void dancingFamiliars(PlayerInteractEvent.RightClickBlock event) {
        BlockState state = event.getWorld().getBlockState(event.getPos());
        if (!state.hasProperty(JukeboxBlock.HAS_RECORD) || state.get(JukeboxBlock.HAS_RECORD)
                || !(event.getItemStack().getItem() instanceof MusicDiscItem))
            return;
        if (event.getWorld()
                .getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(event.getPos()).grow(3),
                        e -> e instanceof IFamiliar && ((IFamiliar) e).getFamiliarOwner() == event.getPlayer())
                .isEmpty())
            return;
        OccultismAdvancements.FAMILIAR.trigger(event.getPlayer(), FamiliarTrigger.Type.PARTY);
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getItemStack().getItem() == OccultismItems.SOUL_GEM_ITEM.get() &&
            event.getTarget() instanceof LivingEntity) {
            //called from here to bypass sitting entity's sit command.
            if (OccultismItems.SOUL_GEM_ITEM.get()
                        .interactLivingEntity(event.getItemStack(), event.getPlayer(),
                                (LivingEntity) event.getTarget(),
                                event.getHand()) == InteractionResult.SUCCESS) {
                event.setCanceled(true);
            }
        }
    }
    //endregion Static Methods
}
