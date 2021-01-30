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

package com.github.klikli_dev.occultism.common.item.debug;

import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.fixes.EntityUUID;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class DebugWandItem extends Item {

    //region Initialization
    public DebugWandItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        if (!context.getWorld().isRemote) {
//            PlayerEntity player = context.getPlayer();
//
//            ItemStack spirit = new ItemStack(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get());
//            spirit.getItem().onCreated(spirit, context.getWorld(), context.getPlayer());
//            ItemHandlerHelper.giveItemToPlayer(player, spirit);
            context.getPlayer().sendMessage(new StringTextComponent(TextUtil.generateName()), Util.DUMMY_UUID);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target,
                                            Hand hand) {


        return ActionResultType.SUCCESS;
    }
    //endregion Overrides
}
