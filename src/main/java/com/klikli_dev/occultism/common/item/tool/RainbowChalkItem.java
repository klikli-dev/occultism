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

package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.common.block.RainbowGlyphBlock;
import com.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class RainbowChalkItem extends Item {
    Supplier<RainbowGlyphBlock> glyphBlock;

    public RainbowChalkItem(Properties properties, Supplier<RainbowGlyphBlock> glyphBlock) {
        super(properties);
        this.glyphBlock = glyphBlock;
    }

    public Supplier<RainbowGlyphBlock> getGlyphBlock() {
        return this.glyphBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        boolean isReplacing = level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context));

        if (!level.isClientSide) {
            if (player.isCrouching()){ //brush job
                if (level.getBlockState(pos).getBlock() instanceof ChalkGlyphBlock || level.getBlockState(pos).getBlock() instanceof RainbowGlyphBlock) {
                    level.removeBlock(pos, false);
                    level.playSound(null, pos, OccultismSounds.BRUSH.get(), SoundSource.PLAYERS, 0.5f,
                            1 + 0.5f * context.getPlayer().getRandom().nextFloat());
                    return InteractionResult.SUCCESS;
                }
            }
            //only place if player clicked at a top face
            //only if the block can be placed or is replacing an existing block
            if ((context.getClickedFace() == Direction.UP
                    && this.glyphBlock.get().canSurvive(level.getBlockState(pos.above()), level, pos.above())) || isReplacing) {
                ItemStack heldChalk = context.getItemInHand();
                BlockPos placeAt = isReplacing ? pos : pos.above();

                boolean isSameChalkType = level.getBlockState(placeAt).getBlock() == this.glyphBlock.get();
                level.setBlockAndUpdate(placeAt,
                        this.glyphBlock.get().getStateForPlacement(new BlockPlaceContext(context)));

                level.playSound(null, pos, OccultismSounds.CHALK.get(), SoundSource.PLAYERS, 0.5f,
                        1 + 0.5f * player.getRandom().nextFloat());

                // do not consume durability if creative, or if same kind of chalk (= cycle through sings)
                if (!player.isCreative() && !isSameChalkType)
                    heldChalk.hurtAndBreak(1, player, player.getEquipmentSlotForItem(heldChalk));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
