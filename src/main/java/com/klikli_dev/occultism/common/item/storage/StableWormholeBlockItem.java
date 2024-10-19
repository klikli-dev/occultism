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

package com.klikli_dev.occultism.common.item.storage;

import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class StableWormholeBlockItem extends BlockItem {

    public StableWormholeBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack pStack) {
        super.verifyComponentsAfterLoad(pStack);

        if(pStack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER))
            pStack.set(DataComponents.RARITY, Rarity.RARE);
        else
            pStack.set(DataComponents.RARITY, Rarity.COMMON);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof IStorageController) {
                    //if this is a storage controller, write the position into the block entity tag that will be used to spawn the block entity.

                    //if we already have block entity data stored, we update the linkedStorageControllerPosition
                    //we don't do this if we do not have a block entity data, because an incomplete BE data causes a crash on serialization
                    if(stack.has(DataComponents.BLOCK_ENTITY_DATA)){
                        CustomData.update(DataComponents.BLOCK_ENTITY_DATA, stack, (data) -> {
                            data.put("linkedStorageControllerPosition", GlobalBlockPos.from(blockEntity).serializeNBT(level.registryAccess()));
                        });
                    }

                    stack.set(OccultismDataComponents.LINKED_STORAGE_CONTROLLER, GlobalBlockPos.from(blockEntity));
                    stack.set(DataComponents.RARITY, Rarity.RARE);

                    player.displayClientMessage(
                            Component.translatable(this.getDescriptionId() + ".message.set_storage_controller"),
                            true);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        if (pStack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER)) {
            GlobalBlockPos globalPos = pStack.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);

            String formattedPosition =
                    ChatFormatting.GOLD.toString() + ChatFormatting.BOLD + globalPos.getPos().toString() +
                            ChatFormatting.RESET;
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip.linked", formattedPosition));
        } else {
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip.unlinked"));
        }
    }

}
