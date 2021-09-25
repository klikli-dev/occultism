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

package com.github.klikli_dev.occultism.common.item.storage;

import com.github.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class StableWormholeBlockItem extends BlockItem {
    //region Initialization
    public StableWormholeBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    //endregion Initialization

    //region Overrides
    @Override
    public Rarity getRarity(ItemStack stack) {
        return stack.getOrCreateTag().getCompound("BlockEntityTag")
                .contains("linkedStorageControllerPosition") ? Rarity.RARE : Rarity.COMMON;
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
                    stack.getOrCreateTagElement("BlockEntityTag")
                            .put("linkedStorageControllerPosition", GlobalBlockPos.from(blockEntity).serializeNBT());
                    player.displayClientMessage(
                            new TranslatableComponent(this.getDescriptionId() + ".message.set_storage_controller"),
                            true);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().getCompound("BlockEntityTag")
                .contains("linkedStorageControllerPosition")) {
            GlobalBlockPos globalPos = GlobalBlockPos.from(stack.getTagElement("BlockEntityTag")
                    .getCompound("linkedStorageControllerPosition"));
            String formattedPosition =
                    ChatFormatting.GOLD.toString() + ChatFormatting.BOLD + globalPos.getPos().toString() +
                            ChatFormatting.RESET;
            tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip.linked", formattedPosition));
        } else {
            tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip.unlinked"));
        }
    }
    //endregion Overrides
}
