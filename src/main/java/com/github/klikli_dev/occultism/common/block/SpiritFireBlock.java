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

package com.github.klikli_dev.occultism.common.block;

import com.github.klikli_dev.occultism.crafting.recipe.ItemStackFakeInventory;
import com.github.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SpiritFireBlock extends BaseFireBlock {
    public SpiritFireBlock(Properties properties) {
        super(properties, 0);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    @Override
    protected boolean canBurn(BlockState pState) {
        return true;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof ItemEntity item) {
            ItemStackFakeInventory fakeInventory =
                    new ItemStackFakeInventory(ItemStack.EMPTY);
            fakeInventory.setItem(0, item.getItem());
            Optional<SpiritFireRecipe> recipe =
                    pLevel.getRecipeManager().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), fakeInventory, pLevel);

            if (recipe.isPresent()) {
                item.remove(RemovalReason.DISCARDED);

                ItemStack result = recipe.get().assemble(fakeInventory);
                Vec3 center = Math3DUtil.center(pPos);
                Containers.dropItemStack(pLevel, center.x, center.y + 0.5, center.z, result);

                pLevel.playSound(null, pPos, OccultismSounds.START_RITUAL.get(), SoundSource.BLOCKS, 1, 1);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return this.canSurvive(pState, pLevel, pCurrentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos below = pos.below();
        return worldIn.getBlockState(below).isFaceSturdy(worldIn, pos, Direction.UP);
    }
}
