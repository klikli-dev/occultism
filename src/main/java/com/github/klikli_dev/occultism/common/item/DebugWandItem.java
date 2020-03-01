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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DebugWandItem extends Item {

    //region Initialization
    public DebugWandItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        //        World world = context.getWorld();
        //        PlayerEntity player = context.getPlayer();
        //        Hand hand = context.getHand();
        //        BlockPos pos = context.getPos();
        //        if (!world.isRemote) {
        //            ItemStack stack = player.getHeldItem(hand);
        //
        //            //test potion
        //            player.addPotionEffect(new EffectInstance(PotionRegistry.THIRD_EYE, 100));
        //
        //            BlockState state = world.getBlockState(pos);
        //            TileEntity tileEntity = world.getTileEntity(pos);
        //            if (tileEntity instanceof TileEntityStorageController) {
        //                TileEntityStorageController storageController = (TileEntityStorageController) tileEntity;
        //                stack.setTagInfo("linkedStorageControllerPos", LongNBT.of(storageController.getPos().toLong()));
        //                CompoundNBT tag;
        //                stack.setTagInfo("linkedStorageControllerDim", StringNBT.of(
        //                        storageController.getWorld().getDimension().getDimension().getType().getRegistryName()
        //                                .toString()));
        //                player.sendStatusMessage(new StringTextComponent("Linked Storage Controller"), true);
        //            }
        //            else if (tileEntity != null &&
        //                     TileEntityUtil.hasCapabilityOnAnySide(tileEntity, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
        //                if (stack.getOrCreateTag().contains("linkedStorageControllerPos")) {
        //                    BlockPos storageControllerPos = BlockPos.fromLong(
        //                            stack.getTag().getLong("linkedStorageControllerPos"));
        //                    var dimension = new ResourceLocation(stack.getTag().getString("linkedStorageControllerDim"));
        //                    TileEntity linkedTileEntity = world.getServer().getWorld(DimensionType.byName(dimension))
        //                                                          .getTileEntity(storageControllerPos);
        //                    if (linkedTileEntity instanceof TileEntityStorageController) {
        //                        TileEntityStorageController storageController = (TileEntityStorageController) linkedTileEntity;
        //                        MachineReference reference = MachineReference.fromTileEntity(tileEntity);
        //                        //generate a random name for the tile to test search
        //                        reference.customName = NameUtil.generateName();
        //                        storageController.linkedMachines.put(reference.globalPos, reference);
        //                        player.sendStatusMessage(new StringTextComponent(
        //                                String.format("Linked tile entity %s to storage controller.", state.getBlock().getTranslationKey())), true);
        //                    }
        //                }
        //            }
        //        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        //        IItemHandler itemHandler = playerIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN)
        //                                           .orElseThrow(() -> new RuntimeException(
        //                                                   "ITEM_HANDLER_CAPABILITY missing on player."));
        //        for (int i = 0; i <= itemHandler.getSlots(); i++) {
        //            ItemStack itemStack = itemHandler.getStackInSlot(i);
        //            if (itemStack.getItem() instanceof ItemBookOfCallingActive) {
        //                //Set a random uuid to test the glow effect
        //                if (ItemNBTUtil.getSpiritEntityUUID(itemStack) == null)
        //                    ItemNBTUtil.setSpiritEntityUUID(itemStack, UUID.randomUUID());
        //            }
        //        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target,
                                            Hand hand) {
        //        if (!(target instanceof EntitySpirit))
        //            return false;
        //
        //        EntitySpirit entitySpirit = (EntitySpirit) target;
        //        if (!entitySpirit.isTamed() || !entitySpirit.isOwner(player)) {
        //            entitySpirit.setTamedBy(player);
        //            player.sendStatusMessage(new TranslationTextComponent(
        //                    String.format("debug.%s.%s.spirit_tamed", Occultism.MODID, this.getRegistryName().getPath()),
        //                    target.getUniqueID().toString()), true);
        //        }
        //        else {
        //            stack.getOrCreateTag().putUniqueId(SPIRIT_UUID_TAG, target.getUniqueID());
        //            player.sendStatusMessage(new TranslationTextComponent(
        //                    String.format("debug.%s.%s.spirit_selected", Occultism.MODID, this.getRegistryName().getPath()),
        //                    target.getUniqueID().toString()), true);
        //        }
        //

        return true;
    }
    //endregion Overrides
}
