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

package com.github.klikli_dev.occultism.common.item.storage;

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class StorageRemoteItem extends Item implements INamedContainerProvider {

    //region Initialization
    public StorageRemoteItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            ItemStack stack = context.getItem();
            TileEntity tileEntity = context.getWorld().getTileEntity(context.getPos());
            if (tileEntity instanceof IStorageController) {
                stack.setTagInfo("linkedStorageController", GlobalBlockPos.from(tileEntity).serializeNBT());
                context.getPlayer()
                        .sendMessage(new TranslationTextComponent(this.getTranslationKey() + ".message.linked"),
                                Util.DUMMY_UUID);
            }
        }

        return super.onItemUse(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote || !stack.getOrCreateTag().contains("linkedStorageController"))
            return super.onItemRightClick(world, player, hand);

        GlobalBlockPos storageControllerPos = GlobalBlockPos.from(
                stack.getTag().getCompound("linkedStorageController"));
        World storageControllerWorld = world.getServer().getWorld(storageControllerPos.getDimensionKey());

        //ensure TE is available
        if (!storageControllerWorld.isBlockLoaded(storageControllerPos.getPos())) {
            player.sendMessage(new TranslationTextComponent(this.getTranslationKey() + ".message.not_loaded"), Util.DUMMY_UUID);
            return super.onItemRightClick(world, player, hand);
        }

        //then access it and if it fits, open UI
        if (storageControllerWorld.getTileEntity(storageControllerPos.getPos()) instanceof IStorageController) {
            NetworkHooks.openGui((ServerPlayerEntity) player, this, buffer -> buffer.writeVarInt(player.inventory.currentItem));
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip"));
        if (stack.getOrCreateTag().contains("linkedStorageController")) {
            GlobalBlockPos pos = GlobalBlockPos.from(stack.getTag().getCompound("linkedStorageController"));
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip.linked", pos.toString()));
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return stack.getOrCreateTag().contains("linkedStorageController") ? Rarity.RARE : Rarity.COMMON;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new StorageRemoteContainer(id, playerInventory, playerInventory.currentItem);
    }
    //endregion Overrides

    //region Static Methods
    public static IStorageController getStorageController(ItemStack stack, World world) {
        //Invalid item or cannot not get hold of server instance

        if (stack.isEmpty()) {
            return null;
        }
        //no storage controller linked
        if (!stack.getOrCreateTag().contains("linkedStorageController"))
            return null;

        GlobalBlockPos globalPos = GlobalBlockPos.from(stack.getTag().getCompound("linkedStorageController"));
        TileEntity tileEntity = TileEntityUtil.get(world, globalPos);

        return tileEntity instanceof IStorageController ? (IStorageController) tileEntity : null;
    }
    //endregion Static Methods
}

