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

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.tile.TileEntityGoldenSacrificialBowl;
import com.github.klikli_dev.occultism.common.tile.TileEntitySacrificialBowl;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockGoldenSacrificialBowl extends Block {
    //region Fields
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.20, 1);
    //endregion Fields

    //region Initialization
    public BlockGoldenSacrificialBowl() {
        super(Material.ROCK);
        BlockRegistry.registerBlock(this, "golden_sacrificial_bowl", Material.ROCK, SoundType.STONE, 1.5f, 30);
        MinecraftForge.EVENT_BUS.register(this);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos to, Block block, BlockPos from) {
        //Force an immediate update tick if neighbours changed, this way
        world.scheduleUpdate(to, this, 0);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, BlockState state) {
        TileEntitySacrificialBowl tile = (TileEntitySacrificialBowl) world.getTileEntity(pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand,
                                    Direction face, float hitX, float hitY, float hitZ) {

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityGoldenSacrificialBowl) {
            TileEntityGoldenSacrificialBowl tileEntityGoldenSacrificialBowl = (TileEntityGoldenSacrificialBowl) tileEntity;
            return tileEntityGoldenSacrificialBowl.activate(world, pos, player, hand, face);
        }
        return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, BlockState state) {
        return new TileEntityGoldenSacrificialBowl();
    }

    //endregion Overrides

    //region Methods
    @SubscribeEvent
    public void livingDeath(LivingDeathEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!entityLivingBase.world.isRemote) {
            BlockPos pos = entityLivingBase.getPosition();
            int range = Ritual.SACRIFICE_DETECTION_RANGE;
            for (BlockPos positionToCheck : BlockPos.getAllInBoxMutable(pos.add(-range, -range, -range),
                    pos.add(range, range, range))) {
                TileEntity tileEntity = entityLivingBase.world.getTileEntity(positionToCheck);
                if (tileEntity instanceof TileEntityGoldenSacrificialBowl) {
                    TileEntityGoldenSacrificialBowl bowl = (TileEntityGoldenSacrificialBowl) entityLivingBase.world
                                                                                                     .getTileEntity(
                                                                                                             positionToCheck);
                    if (bowl.currentRitual != null && bowl.currentRitual.isValidSacrifice(entityLivingBase)) {
                        bowl.notifySacrifice(entityLivingBase);
                    }
                }
            }
        }
    }
    //endregion Methods
}
