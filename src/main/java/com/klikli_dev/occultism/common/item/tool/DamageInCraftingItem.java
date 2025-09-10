package com.klikli_dev.occultism.common.item.tool;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Map;

public class DamageInCraftingItem extends Item {

    public DamageInCraftingItem(Properties properties) {
        super(properties);
        this.craftingRemainingItem = this;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {

        DataComponentMap components = itemStack.getComponents();
        boolean[] eternal = {false};
        components.forEach(comp -> {
            if (comp.toString().startsWith("forbidden_arcanus:modifier")
                    && comp.toString().contains("eternal")) {
                eternal[0] = true;
            }
        });
        if (eternal[0] || !itemStack.isDamageableItem())
            return itemStack.copy();

        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
        int unbLvl = 0;
        for (Map.Entry<Holder<Enchantment>, Integer> e : enchantments.entrySet()){
            if (e.getKey().is(Enchantments.UNBREAKING)){
                unbLvl = e.getValue();
            }
        }

        ItemStack remain = itemStack.copy();
        if (RandomSource.create().nextFloat() <= 1.0F /(unbLvl+1))
            remain.setDamageValue(itemStack.getDamageValue() + 1);
        return remain.getDamageValue() == remain.getMaxDamage() ? ItemStack.EMPTY : remain;
    }
}
