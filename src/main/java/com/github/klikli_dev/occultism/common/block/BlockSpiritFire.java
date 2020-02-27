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

import com.github.klikli_dev.occultism.crafting.recipe.RecipeSpiritfireConversion;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.SoundRegistry;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BlockSpiritFire extends Block {
    //region Fields
    public List<RecipeSpiritfireConversion> recipes = new ArrayList<>();
    //endregion Fields

    //region Initialization
    public BlockSpiritFire() {
        super(Material.FIRE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFire.AGE, 0));
        this.setTickRandomly(true);
        BlockRegistry.registerBlock(this, "spirit_fire", Material.FIRE, SoundType.STONE, 0, 0);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.TNT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockFire.AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (Integer) state.getValue(BlockFire.AGE);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 2)) {
            return;
        }

        if (!this.canPlaceBlockAt(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
        }

        if (!worldIn.isRemote) {
            this.convertItems(worldIn, pos, state);
        }

        Block block = worldIn.getBlockState(pos.down()).getBlock();
        boolean flag = block.isFireSource(worldIn, pos.down(), EnumFacing.UP);
        int i = state.getValue(BlockFire.AGE);
        if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos) && rand.nextFloat() < 0.2F + (float) i * 0.03F) {
            worldIn.setBlockToAir(pos);
        }
        else {
            if (i < 15) {
                state = state.withProperty(BlockFire.AGE, i + rand.nextInt(3) / 2);
                worldIn.setBlockState(pos, state, 4);
            }

            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));
            if (!flag) {
                if (!this.canNeighborCatchFire(worldIn, pos)) {
                    if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3) {
                        worldIn.setBlockToAir(pos);
                    }

                    return;
                }

                if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0) {
                    worldIn.setBlockToAir(pos);
                    return;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(24) == 0) {
            worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
                    (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS,
                    1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        int j1;
        double d7;
        double d12;
        double d17;
        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) &&
            !Blocks.FIRE.canCatchFire(worldIn, pos.down(), EnumFacing.UP)) {
            if (Blocks.FIRE.canCatchFire(worldIn, pos.west(), EnumFacing.EAST)) {
                for (j1 = 0; j1 < 2; ++j1) {
                    d7 = (double) pos.getX() + rand.nextDouble() * 0.10000000149011612D;
                    d12 = (double) pos.getY() + rand.nextDouble();
                    d17 = (double) pos.getZ() + rand.nextDouble();
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.FIRE.canCatchFire(worldIn, pos.east(), EnumFacing.WEST)) {
                for (j1 = 0; j1 < 2; ++j1) {
                    d7 = (double) (pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    d12 = (double) pos.getY() + rand.nextDouble();
                    d17 = (double) pos.getZ() + rand.nextDouble();
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.FIRE.canCatchFire(worldIn, pos.north(), EnumFacing.SOUTH)) {
                for (j1 = 0; j1 < 2; ++j1) {
                    d7 = (double) pos.getX() + rand.nextDouble();
                    d12 = (double) pos.getY() + rand.nextDouble();
                    d17 = (double) pos.getZ() + rand.nextDouble() * 0.10000000149011612D;
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.FIRE.canCatchFire(worldIn, pos.south(), EnumFacing.NORTH)) {
                for (j1 = 0; j1 < 2; ++j1) {
                    d7 = (double) pos.getX() + rand.nextDouble();
                    d12 = (double) pos.getY() + rand.nextDouble();
                    d17 = (double) (pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Blocks.FIRE.canCatchFire(worldIn, pos.up(), EnumFacing.DOWN)) {
                for (j1 = 0; j1 < 2; ++j1) {
                    d7 = (double) pos.getX() + rand.nextDouble();
                    d12 = (double) (pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    d17 = (double) pos.getZ() + rand.nextDouble();
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        else {
            for (j1 = 0; j1 < 3; ++j1) {
                d7 = (double) pos.getX() + rand.nextDouble();
                d12 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
                d17 = (double) pos.getZ() + rand.nextDouble();
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.provider.getDimensionType().getId() > 0 || !Blocks.PORTAL.trySpawnPortal(worldIn, pos)) {
            if (!worldIn.getBlockState(pos.down()).isTopSolid() && !this.canNeighborCatchFire(worldIn, pos)) {
                worldIn.setBlockToAir(pos);
            }
            else {
                worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
            }
        }

    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isTopSolid();
    }

    @Override
    public boolean requiresUpdates() {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockFire.AGE);
    }
    //endregion Overrides

    //region Methods
    public boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return world.getBlockState(pos).getBlock().isFlammable(world, pos, face);
    }

    public void registerRecipes() {
        this.recipes = ForgeRegistries.RECIPES.getValuesCollection().stream()
                               .filter(e -> e instanceof RecipeSpiritfireConversion)
                               .map(e -> (RecipeSpiritfireConversion) e).collect(Collectors.toList());
    }

    protected boolean canDie(World worldIn, BlockPos pos) {
        return worldIn.isRainingAt(pos) || worldIn.isRainingAt(pos.west()) || worldIn.isRainingAt(pos.east()) ||
               worldIn.isRainingAt(pos.north()) || worldIn.isRainingAt(pos.south());
    }

    protected void convertItems(World world, BlockPos pos, IBlockState state) {
        Vec3d center = Math3DUtil.getBlockCenter(pos);
        AxisAlignedBB box = new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5).offset(center);
        List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, box);
        boolean convertedAnyItem = false;
        for (EntityItem item : list) {
            RecipeSpiritfireConversion recipe = this.recipes.stream().filter(r -> r.isValid(item.getItem())).findFirst()
                                                        .orElse(null);
            if (recipe != null) {
                convertedAnyItem = true;
                item.getItem().shrink(1);

                ItemStack result = recipe.getRecipeOutput().copy();
                EntityItem resultEntity = new EntityItem(world, center.x, center.y + 0.5, center.z, result);
                resultEntity.setDefaultPickupDelay();

                world.spawnEntity(resultEntity);
            }
        }
        if (convertedAnyItem) {
            world.playSound(null, pos, SoundRegistry.START_RITUAL, SoundCategory.BLOCKS, 1, 1);
        }
    }

    private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
        EnumFacing[] var3 = EnumFacing.values();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            EnumFacing enumfacing = var3[var5];
            if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite())) {
                return true;
            }
        }

        return false;
    }
    //endregion Methods
}
