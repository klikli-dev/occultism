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
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
        //Force an immediate update tick if neighbours changed, this way
        world.scheduleUpdate(to, this, 0);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntitySacrificialBowl tile = (TileEntitySacrificialBowl) world.getTileEntity(pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing face, float hitX, float hitY, float hitZ) {

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityGoldenSacrificialBowl) {
            TileEntityGoldenSacrificialBowl tileEntityGoldenSacrificialBowl = (TileEntityGoldenSacrificialBowl) tileEntity;
            return tileEntityGoldenSacrificialBowl.activate(world, pos, player, hand, face);
        }
        return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityGoldenSacrificialBowl();
    }

    //endregion Overrides

    //region Methods
    @SubscribeEvent
    public void livingDeath(LivingDeathEvent event) {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
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
