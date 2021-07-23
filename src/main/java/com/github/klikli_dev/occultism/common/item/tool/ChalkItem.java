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

package com.github.klikli_dev.occultism.common.item.tool;

import com.github.klikli_dev.occultism.registry.OccultismSounds;
import com.github.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.Player;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.level.Level;

import java.util.function.Supplier;

public class ChalkItem extends Item {
//region Fields
Supplier<ChalkGlyphBlock> glyphBlock;
//endregion Fields

    //region Initialization
    public ChalkItem(Properties properties, Supplier<ChalkGlyphBlock> glyphBlock) {
        super(properties);
        this.glyphBlock = glyphBlock;
    }

    //endregion Initialization

    //region Overrides


    @Override
    public InteractionResult onItemUse(ItemUseContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        boolean isReplacing = level.getBlockState(pos).getBlock()
                                      .isReplaceable(state, new BlockItemUseContext(context));

        if (!level.isClientSide) {
            //only place if player clicked at a top face
            //only if the block can be placed or is replacing an existing block
            if ((context.getFace() == Direction.UP
                 && this.glyphBlock.get().isValidPosition(level.getBlockState(pos.up()), level, pos.up())) || isReplacing) {
                ItemStack heldChalk = context.getItem();
                BlockPos placeAt = isReplacing ? pos : pos.up();

                level.setBlockState(placeAt,
                        this.glyphBlock.get().getStateForPlacement(new BlockItemUseContext(context)));

                level.playSound(null, pos, OccultismSounds.CHALK.get(), SoundSource.PLAYERS, 0.5f,
                        1 + 0.5f * player.getRNG().nextFloat());

                if (!player.isCreative())
                    heldChalk.damageItem(1, player, t -> {
                    });
            }
        }
        return InteractionResult.SUCCESS;
    }

    //endregion Overrides

}
