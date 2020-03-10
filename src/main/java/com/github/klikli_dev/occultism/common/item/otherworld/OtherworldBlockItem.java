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

package com.github.klikli_dev.occultism.common.item.otherworld;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Allows to show different textures and translation keys for HWYLA and in the inventory
 */
public class OtherworldBlockItem extends BlockItem {
    public OtherworldBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);

        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "simulated"),
                (stack, world, entity) -> {
                    boolean thirdEye = Minecraft.getInstance().player.isPotionActive(OccultismEffects.THIRD_EYE.get());
           return stack.getOrCreateTag().getBoolean("isInventoryItem") || thirdEye ? 1.0f : 0.0f;
        });
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        boolean thirdEye = Minecraft.getInstance().player.isPotionActive(OccultismEffects.THIRD_EYE.get());
        return stack.getOrCreateTag().getBoolean("isInventoryItem") || thirdEye ? this.getDefaultTranslationKey() : this.getTranslationKey();
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        stack.getOrCreateTag().putBoolean("isInventoryItem", true);
    }
}
