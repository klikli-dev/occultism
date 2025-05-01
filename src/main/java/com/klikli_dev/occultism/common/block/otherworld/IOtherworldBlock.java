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

package com.klikli_dev.occultism.common.block.otherworld;

import com.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.klikli_dev.occultism.api.common.item.IOtherworldTool;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public interface IOtherworldBlock {

    Property<Boolean> UNCOVERED = BooleanProperty.create("uncovered");

    //region Getter / Setter
    Block getUncoveredBlock();

    Block getCoveredBlock();

    OtherworldBlockTier getTier();
    //endregion Getter / Setter

    default OtherworldBlockTier getPlayerHarvestTier(Player player, ItemStack tool) {
        OtherworldBlockTier toolTier = OtherworldBlockTier.NONE;
        OtherworldBlockTier effectTier = player.hasEffect(OccultismEffects.THIRD_EYE) ?
                OtherworldBlockTier.ONE : OtherworldBlockTier.NONE;
        OtherworldBlockTier staffTier = player.getOffhandItem().is(OccultismItems.TRUE_SIGHT_STAFF) || CuriosUtil.hasStaff(player) ?
                OtherworldBlockTier.TWO : OtherworldBlockTier.NONE;
        if (tool.getItem() instanceof IOtherworldTool) {
            toolTier = ((IOtherworldTool) tool.getItem()).getHarvestTier(tool);
        }
        if (tool.has(OccultismDataComponents.OTHERWORLD_TOOL_TIER)) {
            toolTier = OtherworldBlockTier.get(tool.get(OccultismDataComponents.OTHERWORLD_TOOL_TIER));
        }
        return OtherworldBlockTier.max(OtherworldBlockTier.max(toolTier, effectTier), staffTier);
    }

    default BlockState getHarvestState(Player player, BlockState state, ItemStack tool) {
        return this.getPlayerHarvestTier(player, tool).getLevel() >= this.getTier().getLevel() ? state.setValue(UNCOVERED,
                true) : state;
    }

    default ItemStack getItem(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(state.getValue(UNCOVERED) ? this.getUncoveredBlock() : this.getCoveredBlock(), 1);
    }

}
