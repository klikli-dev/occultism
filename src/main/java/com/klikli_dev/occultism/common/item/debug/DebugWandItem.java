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

package com.klikli_dev.occultism.common.item.debug;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DebugWandItem extends Item {

    //region Initialization
    public DebugWandItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public InteractionResult useOn(UseOnContext context) {

        if (!context.getLevel().isClientSide) {
            Player player = context.getPlayer();

//            ItemStack spirit = new ItemStack(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get());
//            spirit.getItem().onCreated(spirit, context.getLevel(), context.getPlayer());
//            ItemHandlerHelper.giveItemToPlayer(player, spirit);

//            context.getPlayer().sendSystemMessage(Component.literal(TextUtil.generateName()));

            //set up the foliot entity
//            BlockPos target = context.getClickedPos().above();
//            SpiritEntity spirit = OccultismEntities.MARID.get().create(context.getLevel());
//            spirit.absMoveTo(target.getX(), target.getY(), target.getZ(),
//                    context.getLevel().random.nextInt(360), 0);
//            spirit.setCustomName(Component.literal("Testguy"));
//            spirit.finalizeSpawn((ServerLevel) context.getLevel(), context.getLevel().getCurrentDifficultyAt(target),
//                    MobSpawnType.MOB_SUMMONED, null,
//                    null);
//            spirit.tame(context.getPlayer());
//            //set up the job
//            SpiritJob job = OccultismSpiritJobs.CRUSH_TIER4.get().create(spirit);
//            job.init();
//            spirit.setJob(job);
//
//            spirit.setSpiritMaxAge(60 * 60 * 3); //3 hours max age
//            context.getLevel().addFreshEntity(spirit);

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target,
                                                  InteractionHand hand) {


        return InteractionResult.SUCCESS;
    }
    //endregion Overrides
}
