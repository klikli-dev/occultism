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

import com.github.klikli_dev.occultism.common.container.storage.SatchelContainer;
import com.github.klikli_dev.occultism.common.container.storage.SatchelInventory;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class SatchelItem extends Item {
    //region Initialization
    public SatchelItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote && player instanceof ServerPlayerEntity) {
            //here we use main hand item as selected slot
            int selectedSlot = hand == Hand.MAIN_HAND ? player.inventory.currentItem : -1;

            NetworkHooks.openGui((ServerPlayerEntity) player,
                    new SimpleNamedContainerProvider((id, playerInventory, unused) -> {
                        return new SatchelContainer(id, playerInventory,
                                this.getInventory((ServerPlayerEntity) player, stack), selectedSlot);
                    }, stack.getDisplayName()), buffer -> {
                        buffer.writeVarInt(selectedSlot);
                    });

            NetworkHooks.openGui((ServerPlayerEntity) player,
                    new SimpleNamedContainerProvider((id, playerInventory, unused) -> {
                        return new SatchelContainer(id, playerInventory,
                                this.getInventory((ServerPlayerEntity) player, stack), selectedSlot);
                    }, stack.getDisplayName()), buffer -> {
                        buffer.writeVarInt(selectedSlot);
                    });
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    //endregion Overrides

    //region Methods
    public IInventory getInventory(ServerPlayerEntity player, ItemStack stack) {
        return new SatchelInventory(stack, SatchelContainer.SATCHEL_SIZE);
    }
    //endregion Methods
}
