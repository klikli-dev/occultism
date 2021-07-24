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

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.network.chat.TranslatableComponent;

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
        ItemStack stack = context.getItem();
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (BlockEntity instanceof IStorageController) {
                    //if this is a storage controller, write the position into the block entity tag that will be used to spawn the tile entity.
                    stack.getOrCreateChildTag("BlockEntityTag")
                            .put("linkedStorageControllerPosition", GlobalBlockPos.from(BlockEntity).serializeNBT());
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
                               ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().getCompound("BlockEntityTag")
                    .contains("linkedStorageControllerPosition")) {
            GlobalBlockPos globalPos = GlobalBlockPos.from(stack.getChildTag("BlockEntityTag")
                                                                   .getCompound("linkedStorageControllerPosition"));
            String formattedPosition =
                    TextFormatting.GOLD.toString() + TextFormatting.BOLD + globalPos.getPos().toString() +
                            TextFormatting.RESET;
            tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip.linked", formattedPosition));
        }
        else {
            tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip.unlinked"));
        }
    }
    //endregion Overrides
}
