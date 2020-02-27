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

package com.github.klikli_dev.occultism.common.item;

import com.github.klikli_dev.occultism.api.common.data.ChalkGlyphType;
import com.github.klikli_dev.occultism.common.block.BlockChalkGlyph;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.registry.SoundRegistry;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChalk extends Item {
    //region Initialization
    public ItemChalk(String name) {
        this.setMaxStackSize(1);
        this.setMaxDamage(128);
        this.setNoRepair();
        ItemRegistry.registerItem(this, name);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face,
                                      float hitX, float hitY, float hitZ) {
        //Check if the chalk is replacing an existing glyph
        boolean isReplacing = world.getBlockState(pos).getBlock().isReplaceable(world, pos);

        //only do with authority
        if (!world.isRemote) {
            //only place if player clicked at a top face
            //only if the block can be placed or is replacing an existing block
            if ((face == EnumFacing.UP && BlockRegistry.CHALK_GLYPH.canPlaceBlockAt(world, pos.up())) || isReplacing) {
                ItemStack heldChalk = player.getHeldItem(hand);
                BlockPos placeAt = isReplacing ? pos : pos.up();

                world.setBlockState(placeAt, BlockRegistry.CHALK_GLYPH.getDefaultState()
                                                     .withProperty(BlockChalkGlyph.TYPE,
                                                             this.getChalkGlyphType(heldChalk))
                                                     .withProperty(BlockHorizontal.FACING,
                                                             player.getHorizontalFacing()));
                world.playSound(null, pos, SoundRegistry.CHALK, SoundCategory.BLOCKS, 0.5f,
                        1 + 0.5f * player.getRNG().nextFloat());
                if (!player.isCreative())
                    heldChalk.damageItem(1, player);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean canItemEditBlocks() {
        return true;
    }
    //endregion Overrides

    //region Methods
    public ChalkGlyphType getChalkGlyphType(ItemStack itemStack) {
        Item item = itemStack.getItem();

        if (item == ItemRegistry.CHALK_PURPLE)
            return ChalkGlyphType.PURPLE;
        if (item == ItemRegistry.CHALK_RED)
            return ChalkGlyphType.RED;
        if (item == ItemRegistry.CHALK_GOLD)
            return ChalkGlyphType.GOLD;

        return ChalkGlyphType.WHITE;
    }
    //endregion Methods
}
