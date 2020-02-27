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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.registry.PotionRegistry;
import com.github.klikli_dev.occultism.registry.RitualRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.NameUtil;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.UUID;

public class ItemDebugWand extends Item {

    //region Fields
    public static final String SPIRIT_UUID_TAG = "SpiritUUID";
    //endregion Fields

    //region Initialization
    public ItemDebugWand() {
        this.setMaxStackSize(1);
        ItemRegistry.registerItem(this, "debug_wand");
    }
    //endregion Initialization

    //region Overrides
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face,
                                      float hitX, float hitY, float hitZ) {
        //only do with authority
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);

            //test potion
            player.addPotionEffect(new PotionEffect(PotionRegistry.THIRD_EYE, 100));

            IBlockState state = world.getBlockState(pos);
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityStorageController) {
                TileEntityStorageController storageController = (TileEntityStorageController) tileEntity;
                //                List<BlockPos> stabilizers = storageController.findValidStabilizers();
                //                String result = Joiner.on(", ").join(stabilizers);
                //                player.sendStatusMessage(
                //                        new TextComponentString(String.format("Found %d valid stabilizers.", stabilizers.size())),
                //                        true);
                stack.setTagInfo("linkedStorageControllerPos", new NBTTagLong(storageController.getPos().toLong()));
                stack.setTagInfo("linkedStorageControllerDim",
                        new NBTTagInt(storageController.getWorld().provider.getDimension()));
                player.sendStatusMessage(new TextComponentString("Linked Storage Controller"), true);
            }
            else if (tileEntity != null &&
                     TileEntityUtil.hasCapabilityOnAnySide(tileEntity, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
                if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("linkedStorageControllerPos")) {
                    BlockPos storageControllerPos = BlockPos.fromLong(
                            stack.getTagCompound().getLong("linkedStorageControllerPos"));
                    int dimension = stack.getTagCompound().getInteger("linkedStorageControllerDim");

                    TileEntity linkedTileEntity = world.getMinecraftServer().getWorld(dimension)
                                                          .getTileEntity(storageControllerPos);
                    if (linkedTileEntity instanceof TileEntityStorageController) {
                        TileEntityStorageController storageController = (TileEntityStorageController) linkedTileEntity;
                        MachineReference reference = MachineReference.fromTileEntity(tileEntity);
                        //generate a random name for the tile to test search
                        reference.customName = NameUtil.generateName();
                        storageController.linkedMachines.put(reference.globalPos, reference);
                        player.sendStatusMessage(new TextComponentString(
                                String.format("Linked tile entity %s to storage controller.",
                                        tileEntity.getDisplayName().getFormattedText())), true);
                    }
                }
            }

            //Detect ritual shape on ritual glyphs
            //            if (state.getBlock() == BlockRegistry.CHALK_GLYPH && state
            //                                                                         .getValue(
            //                                                                                 BlockChalkGlyph.TYPE) == ChalkGlyphType.RITUAL) {
            //                this.verifyMultiblock(player, world, pos);
            //
            //            }
        }
        return EnumActionResult.SUCCESS;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        IItemHandler itemHandler = playerIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                EnumFacing.DOWN);
        for (int i = 0; i <= itemHandler.getSlots(); i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemBookOfCallingActive) {
                //Set a random uuid to test the glow effect
                if (ItemNBTUtil.getSpiritEntityUUID(itemStack) == null)
                    ItemNBTUtil.setSpiritEntityUUID(itemStack, UUID.randomUUID());
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target,
                                            EnumHand hand) {
        if (!(target instanceof EntitySpirit))
            return false;

        EntitySpirit entitySpirit = (EntitySpirit) target;
        if (!entitySpirit.isTamed() || !entitySpirit.isOwner(player)) {
            entitySpirit.setTamedBy(player);
            player.sendStatusMessage(new TextComponentTranslation(
                    String.format("debug.%s.%s.spirit_tamed", Occultism.MODID, this.getRegistryName().getPath()),
                    target.getUniqueID().toString()), true);
        }
        else {
            stack.getTagCompound().setUniqueId(SPIRIT_UUID_TAG, target.getUniqueID());
            player.sendStatusMessage(new TextComponentTranslation(
                    String.format("debug.%s.%s.spirit_selected", Occultism.MODID, this.getRegistryName().getPath()),
                    target.getUniqueID().toString()), true);
        }


        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer playerIn) {
        stack.setTagCompound(new NBTTagCompound());
    }
    //endregion Overrides
    //endregion Overrides

    //region Methods

    private void verifyMultiblock(EntityPlayer player, World world, BlockPos pos) {
        boolean matches = RitualRegistry.PENTACLE_DEBUG.getBlockMatcher().validate(world, pos);
        if (matches)
            player.sendStatusMessage(new TextComponentTranslation(
                            String.format("debug.%s.%s.glyphs_verified", Occultism.MODID, this.getRegistryName().getPath())),
                    true);
        else
            player.sendStatusMessage(new TextComponentTranslation(
                    String.format("debug.%s.%s.glyphs_not_verified", Occultism.MODID,
                            this.getRegistryName().getPath())), true);
    }
    //endregion Methods
}
