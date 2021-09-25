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
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

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
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getItemInHand();
        PlayerEntity player = context.getPlayer();
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!world.isClientSide) {
            if (player.isShiftKeyDown()) {
                TileEntity tileEntity = world.getBlockEntity(pos);
                if (tileEntity instanceof IStorageController) {
                    //if this is a storage controller, write the position into the block entity tag that will be used to spawn the tile entity.
                    stack.getOrCreateTagElement("BlockEntityTag")
                            .put("linkedStorageControllerPosition", GlobalBlockPos.from(tileEntity).serializeNBT());
                    player.displayClientMessage(
                            new TranslationTextComponent(this.getDescriptionId() + ".message.set_storage_controller"),
                            true);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().getCompound("BlockEntityTag")
                    .contains("linkedStorageControllerPosition")) {
            GlobalBlockPos globalPos = GlobalBlockPos.from(stack.getTagElement("BlockEntityTag")
                                                                   .getCompound("linkedStorageControllerPosition"));
            String formattedPosition =
                    TextFormatting.GOLD.toString() + TextFormatting.BOLD + globalPos.getPos().toString() +
                            TextFormatting.RESET;
            tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".tooltip.linked", formattedPosition));
        }
        else {
            tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".tooltip.unlinked"));
        }
    }
    //endregion Overrides
}
