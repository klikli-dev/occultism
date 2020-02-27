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
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.handler.GuiHandler;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Rarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemStorageRemote extends Item {
    //region Fields
    public static final String TRANSLATION_KEY_BASE = "item." + Occultism.MODID + ".storage_remote";
    //endregion Fields

    //region Initialization
    public ItemStorageRemote() {
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "linked"), new LinkedPropertyGetter());
        ItemRegistry.registerItem(this, "storage_remote");
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing,
                                      float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof IStorageController) {
                stack.setTagInfo("linkedStorageController",
                        GlobalBlockPos.fromTileEntity(tileEntity).writeToNBT(new CompoundNBT()));
            }
        }

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote || !ItemNBTUtil.getTagCompound(stack).hasKey("linkedStorageController"))
            return super.onItemRightClick(world, player, hand);

        GlobalBlockPos storageControllerPos = GlobalBlockPos.fromNbt(
                ItemNBTUtil.getTagCompound(stack).getCompoundTag("linkedStorageController"));
        World storageControllerWorld = world.getMinecraftServer().getWorld(storageControllerPos.getDimension());

        //ensure TE is available
        if (!storageControllerWorld.getChunk(storageControllerPos.getPos()).isLoaded()) {
            player.sendMessage(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message.not_loaded"));
            return super.onItemRightClick(world, player, hand);
        }

        //then access it and if it fits, open UI
        if (storageControllerWorld.getTileEntity(storageControllerPos.getPos()) instanceof IStorageController) {
            player.openGui(Occultism.instance, GuiHandler.GuiID.STORAGE_REMOTE.ordinal(), world, hand.ordinal(), 0, 0);

            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".tooltip"));
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("linkedStorageController")) {
            GlobalBlockPos pos = GlobalBlockPos
                                         .fromNbt(stack.getTagCompound().getCompoundTag("linkedStorageController"));
            tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".tooltip.linked", pos.toString()));
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ItemNBTUtil.getTagCompound(stack)
                       .hasKey("linkedStorageController") ? Rarity.RARE : Rarity.COMMON;
    }
    //endregion Overrides

    //region Static Methods
    public static IStorageController getStorageController(ItemStack stack, World world) {
        //Invalid item or cannot not get hold of server instance
        if (stack.isEmpty() || FMLCommonHandler.instance() == null ||
            FMLCommonHandler.instance().getMinecraftServerInstance() == null) {
            return null;
        }

        CompoundNBT compound = ItemNBTUtil.getTagCompound(stack);
        //no storage controller linked
        if (!compound.hasKey("linkedStorageController"))
            return null;

        GlobalBlockPos globalPos = GlobalBlockPos.fromNbt(compound.getCompoundTag("linkedStorageController"));
        TileEntity tileEntity = TileEntityUtil.get(world, globalPos);

        return tileEntity instanceof IStorageController ? (IStorageController) tileEntity : null;
    }
    //endregion Static Methods

    private static class LinkedPropertyGetter implements IItemPropertyGetter {
        //region Overrides
        @Override
        public float apply(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
            return ItemNBTUtil.getTagCompound(stack).hasKey("linkedStorageController") ? 1.0f : 0.0f;
        }
        //endregion Overrides
    }
}

