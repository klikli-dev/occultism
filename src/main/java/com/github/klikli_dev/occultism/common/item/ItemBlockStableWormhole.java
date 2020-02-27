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
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockStableWormhole extends BlockItem {

    //region Fields
    public static final String TRANSLATION_KEY_BASE = "item." + Occultism.MODID + ".stable_wormhole";
    //endregion Fields

    //region Initialization
    public ItemBlockStableWormhole(Block block) {
        super(block);
        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "linked"), new LinkedPropertyGetter());
        ItemRegistry.registerItem(this, "stable_wormhole_item");
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Rarity getRarity(ItemStack stack) {
        return ItemNBTUtil.getTagCompound(stack).getCompoundTag("BlockEntityTag")
                       .hasKey("linkedStorageControllerPosition") ? Rarity.RARE : Rarity.COMMON;
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing,
                                      float hitX, float hitY, float hitZ) {

        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.isSneaking()) {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof IStorageController) {
                    //if this is a storage controller, write the position into the block entity tag that will be used to spawn the tile entity.
                    CompoundNBT itemTag =
                            stack.getTagCompound() != null ? stack.getTagCompound() : new CompoundNBT();
                    CompoundNBT entityTag = itemTag.getCompoundTag("BlockEntityTag");
                    entityTag.setTag("linkedStorageControllerPosition",
                            GlobalBlockPos.fromTileEntity(tileEntity).writeToNBT(new CompoundNBT()));
                    itemTag.setTag("BlockEntityTag", entityTag);
                    stack.setTagCompound(itemTag);
                    player.sendStatusMessage(
                            new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message.set_storage_controller"),
                            true);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (ItemNBTUtil.getTagCompound(stack).getCompoundTag("BlockEntityTag")
                    .hasKey("linkedStorageControllerPosition")) {
            GlobalBlockPos globalPos = GlobalBlockPos.fromNbt(stack.getTagCompound().getCompoundTag("BlockEntityTag")
                                                                      .getCompoundTag(
                                                                              "linkedStorageControllerPosition"));
            tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".tooltip.linked",
                    TextFormatting.GOLD.toString() + TextFormatting.BOLD.toString() + globalPos.getPos().toString() +
                    TextFormatting.RESET.toString()));
        }
        else {
            tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".tooltip.unlinked"));
        }
    }
    //endregion Overrides

    private static class LinkedPropertyGetter implements IItemPropertyGetter {
        //region Overrides
        @Override
        public float apply(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
            return ItemNBTUtil.getTagCompound(stack).getCompoundTag("BlockEntityTag")
                           .hasKey("linkedStorageControllerPosition") ? 1.0f : 0.0f;
        }
        //endregion Overrides
    }
}
