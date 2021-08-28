/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;

import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Item class to represent rituals as items with tooltip - enables JEI search for rituals
 */
public class DummyTooltipItem extends Item {
    
    //region Initialization
    public DummyTooltipItem(Properties properties) {
        super(properties);
    }

    //endregion Initialization
    
    public void performRitual(World world, BlockPos pos, GoldenSacrificialBowlTileEntity tileEntity,
            PlayerEntity player, ItemStack activationItem) {
        String path = this.getRegistryName().getPath();
        String recipePath = path.contains("/") ? path.substring(path.indexOf("/")) : path;
        Optional<? extends IRecipe<?>> recipe = world.getRecipeManager().getRecipe(new ResourceLocation(this.getRegistryName().getNamespace(), recipePath));
        recipe.map(r -> (RitualRecipe) r).ifPresent(r -> r.getRitual().finish(world, pos, tileEntity, player, activationItem));
    }

    //region Overrides
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(stack.getTranslationKey() + ".tooltip"));
    }
    //endregion Overrides
}
