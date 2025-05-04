package com.klikli_dev.occultism.common.item.tool;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GrayPasteItem extends Item {

    public GrayPasteItem(Properties properties) {
        super(properties);
        this.craftingRemainingItem = this;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack remain = itemStack.copy();
        remain.setDamageValue(itemStack.getDamageValue() + 1);
        return remain.getDamageValue() == remain.getMaxDamage() ? ItemStack.EMPTY : remain;
    }
}
