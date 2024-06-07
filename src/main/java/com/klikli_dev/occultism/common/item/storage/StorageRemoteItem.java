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

package com.klikli_dev.occultism.common.item.storage;

import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import javax.annotation.Nullable;
import java.util.List;

public class StorageRemoteItem extends Item implements MenuProvider {

    public StorageRemoteItem(Properties properties) {
        super(properties);
    }

    public static IStorageController getStorageController(ItemStack stack, Level level) {
        //Invalid item or cannot not get hold of server instance

        if (stack.isEmpty()) {
            return null;
        }
        //no storage controller linked
        if (!stack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER))
            return null;

        GlobalBlockPos globalPos = stack.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);
        BlockEntity blockEntity = BlockEntityUtil.get(level, globalPos);

        return blockEntity instanceof IStorageController ? (IStorageController) blockEntity : null;
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack pStack) {
        super.verifyComponentsAfterLoad(pStack);

        if(pStack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER))
            pStack.set(DataComponents.RARITY, Rarity.RARE);
        else
            pStack.set(DataComponents.RARITY, Rarity.COMMON);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getDescriptionId());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            ItemStack stack = context.getItemInHand();
            BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
            if (blockEntity instanceof IStorageController) {
                stack.set(OccultismDataComponents.LINKED_STORAGE_CONTROLLER, GlobalBlockPos.from(blockEntity));
                stack.set(DataComponents.RARITY, Rarity.RARE);
                context.getPlayer()
                        .sendSystemMessage(Component.translatable(this.getDescriptionId() + ".message.linked"));
            }
        }

        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide || !stack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER))
            return super.use(level, player, hand);

        GlobalBlockPos storageControllerPos = stack.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);
        Level storageControllerWorld = level.getServer().getLevel(storageControllerPos.getDimensionKey());

        //ensure TE is available
        if (!storageControllerWorld.hasChunkAt(storageControllerPos.getPos())) {
            player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".message.not_loaded"));
            return super.use(level, player, hand);
        }

        //then access it and if it fits, open UI
        if (storageControllerWorld.getBlockEntity(storageControllerPos.getPos()) instanceof IStorageController && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(this, buffer -> buffer.writeVarInt(player.getInventory().selected));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip"));
        if (pStack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER)) {
            GlobalBlockPos pos = pStack.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip.linked", pos.toString()));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        CuriosUtil.SelectedCurio selectedCurio = CuriosUtil.getStorageRemote(player);
        if (selectedCurio != null) {
            return new StorageRemoteContainer(id, playerInventory, selectedCurio.selectedSlot);
        } else {
            return null;
        }
    }
}

