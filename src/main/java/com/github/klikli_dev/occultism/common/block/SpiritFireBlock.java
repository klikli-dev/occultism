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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.level.IBlockReader;
import net.minecraft.level.IWorld;
import net.minecraft.level.IWorldReader;
import net.minecraft.level.Level;
import net.minecraft.level.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SpiritFireBlock extends Block {
    //region Initialization
    public SpiritFireBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FireBlock.AGE, 0));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
                                          BlockPos currentPos, BlockPos facingPos) {
        return this.isValidPosition(stateIn, worldIn, currentPos) ?
                       this.getDefaultState().with(FireBlock.AGE, stateIn.get(FireBlock.AGE)) :
                       Blocks.AIR.getDefaultState();
    }

    @Override
    public void onBlockAdded(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (oldState.getBlock() != state.getBlock()) {
            if (!state.isValidPosition(worldIn, pos)) {
                worldIn.removeBlock(pos, false);
            }
            else {
                worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
            }
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return worldIn.getBlockState(blockpos).isSolidSide(worldIn, blockpos, Direction.UP) ||
               this.areNeighborsFlammable(worldIn, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
        //TODO: Test if spiritfire works, if there are issues, update tick based on vanilla FireBlock
        if (!worldIn.isAreaLoaded(pos, 2)) {
            return;
        }

        if (!state.isValidPosition(worldIn, pos)) {
            worldIn.removeBlock(pos, false);
        }

        //Call conversion
        if (!worldIn.isClientSide) {
            this.convertItems(worldIn, pos, state);
        }

        //copied from fire, minus spreading logic
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        BlockState other = worldIn.getBlockState(pos.down());
        boolean isOnFireSource = other.isFireSource(worldIn, pos.down(), Direction.UP);
        int i = state.get(FireBlock.AGE);
        if (!isOnFireSource && worldIn.isRaining() && this.canDie(worldIn, pos) &&
            rand.nextFloat() < 0.2F + (float) i * 0.03F) {
            worldIn.removeBlock(pos, false);
        }
        else {
            int j = Math.min(15, i + rand.nextInt(3) / 2);
            if (i != j) {
                state = state.with(FireBlock.AGE, j);
                worldIn.setBlockState(pos, state, 4);
            }

            if (!isOnFireSource) {
                worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
                if (!this.areNeighborsFlammable(worldIn, pos)) {
                    BlockPos blockpos = pos.down();
                    if (!worldIn.getBlockState(blockpos).isSolidSide(worldIn, blockpos, Direction.UP) || i > 3) {
                        worldIn.removeBlock(pos, false);
                    }

                    return;
                }

                if (i == 15 && rand.nextInt(4) == 0 && !this.canCatchFire(worldIn, pos.down(), Direction.UP)) {
                    worldIn.removeBlock(pos, false);
                    return;
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(24) == 0) {
            worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
                    (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundSource.BLOCKS,
                    1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos blockpos = pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (!this.canCatchFire(worldIn, blockpos, Direction.UP) &&
            !Block.hasSolidSideOnTop(worldIn, blockpos)) {
            if (this.canCatchFire(worldIn, blockpos.west(), Direction.EAST)) {
                for (int j = 0; j < 2; ++j) {
                    double d3 = (double) pos.getX() + rand.nextDouble() * (double) 0.1F;
                    double d8 = (double) pos.getY() + rand.nextDouble();
                    double d13 = (double) pos.getZ() + rand.nextDouble();
                    worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canCatchFire(worldIn, pos.east(), Direction.WEST)) {
                for (int k = 0; k < 2; ++k) {
                    double d4 = (double) (pos.getX() + 1) - rand.nextDouble() * (double) 0.1F;
                    double d9 = (double) pos.getY() + rand.nextDouble();
                    double d14 = (double) pos.getZ() + rand.nextDouble();
                    worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canCatchFire(worldIn, pos.north(), Direction.SOUTH)) {
                for (int l = 0; l < 2; ++l) {
                    double d5 = (double) pos.getX() + rand.nextDouble();
                    double d10 = (double) pos.getY() + rand.nextDouble();
                    double d15 = (double) pos.getZ() + rand.nextDouble() * (double) 0.1F;
                    worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canCatchFire(worldIn, pos.south(), Direction.NORTH)) {
                for (int i1 = 0; i1 < 2; ++i1) {
                    double d6 = (double) pos.getX() + rand.nextDouble();
                    double d11 = (double) pos.getY() + rand.nextDouble();
                    double d16 = (double) (pos.getZ() + 1) - rand.nextDouble() * (double) 0.1F;
                    worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canCatchFire(worldIn, pos.up(), Direction.DOWN)) {
                for (int j1 = 0; j1 < 2; ++j1) {
                    double d7 = (double) pos.getX() + rand.nextDouble();
                    double d12 = (double) (pos.getY() + 1) - rand.nextDouble() * (double) 0.1F;
                    double d17 = (double) pos.getZ() + rand.nextDouble();
                    worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        else {
            for (int i = 0; i < 3; ++i) {
                double d0 = (double) pos.getX() + rand.nextDouble();
                double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
                double d2 = (double) pos.getZ() + rand.nextDouble();
                worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FireBlock.AGE);
        super.fillStateContainer(builder);
    }
    //endregion Overrides

//region Static Methods
    private static int getTickCooldown(Random rand) {
        return 30 + rand.nextInt(10);
    }
//endregion Static Methods

    //region Methods
    public boolean canCatchFire(IBlockReader level, BlockPos pos, Direction face) {
        return level.getBlockState(pos).isFlammable(level, pos, face);
    }

    protected void convertItems(Level level, BlockPos pos, BlockState state) {
        Vector3d center = Math3DUtil.center(pos);
        AxisAlignedBB box = new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5).offset(center);
        List<ItemEntity> list = level.getEntitiesWithinAABB(ItemEntity.class, box);
        ItemStackFakeInventory fakeInventory =
                new ItemStackFakeInventory(ItemStack.EMPTY);
        boolean convertedAnyItem = false;
        for (ItemEntity item : list) {
            fakeInventory.setInventorySlotContents(0, item.getItem());
            Optional<SpiritFireRecipe> recipe =
                    level.getRecipeManager().getRecipe(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), fakeInventory, level);

            if (recipe.isPresent()) {
                convertedAnyItem = true;
                item.remove();

                ItemStack result = recipe.get().getCraftingResult(fakeInventory);
                InventoryHelper.spawnItemStack(level, center.x, center.y + 0.5, center.z, result);
            }
        }
        if (convertedAnyItem) {
            level.playSound(null, pos, OccultismSounds.START_RITUAL.get(), SoundSource.BLOCKS, 1, 1);
        }
    }

    protected boolean canDie(Level worldIn, BlockPos pos) {
        return worldIn.isRainingAt(pos) || worldIn.isRainingAt(pos.west()) || worldIn.isRainingAt(pos.east()) ||
               worldIn.isRainingAt(pos.north()) || worldIn.isRainingAt(pos.south());
    }

    private int getNeighborEncouragement(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos)) {
            return 0;
        }
        else {
            int i = 0;

            for (Direction direction : Direction.values()) {
                BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
                i = Math.max(blockstate.getFlammability(worldIn, pos.offset(direction), direction.getOpposite()), i);
            }

            return i;
        }
    }

    private boolean areNeighborsFlammable(IBlockReader worldIn, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (this.canCatchFire(worldIn, pos.offset(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }
    //endregion Methods
}
