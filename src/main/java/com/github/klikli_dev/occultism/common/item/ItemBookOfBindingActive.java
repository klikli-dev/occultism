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
import com.github.klikli_dev.occultism.api.common.item.IIngredientCopyNBT;
import com.github.klikli_dev.occultism.api.common.item.IIngredientModifyCraftingResult;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBookOfBindingActive extends Item implements IIngredientCopyNBT, IIngredientModifyCraftingResult {
    //region Fields
    //endregion Fields

    //region Initialization
    public ItemBookOfBindingActive(String name) {
        this.setMaxStackSize(1);
        ItemRegistry.registerItem(this, name);
    }
    //endregion Initialization

    //region Overrides
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String text = I18n.format("item." + Occultism.MODID + ".book_of_binding_active.tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack)));
        tooltip.add(text);
    }

    @Override
    public boolean shouldCopyNBT(ItemStack itemStack, IRecipe recipe, CraftingInventory inventory) {
        //only copy over name to book of calling
        return recipe.getRecipeOutput().getItem() instanceof ItemBookOfCallingActive;
    }

    @Override
    public void modifyResult(IRecipe recipe, CraftingInventory inventory, ItemStack result) {
        ItemNBTUtil.generateBoundSpiritName(result);
    }
    //endregion Overrides

    //region Static Methods


    //endregion Static Methods
}
