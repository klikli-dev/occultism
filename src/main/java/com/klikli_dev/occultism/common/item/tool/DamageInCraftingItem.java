package com.klikli_dev.occultism.common.item.tool;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Map.Entry;

public class DamageInCraftingItem extends Item {

    public DamageInCraftingItem(Properties properties) {
        super(properties);
    }

    public static DataComponentType<ItemEnchantments> getComponentType(ItemInstance instance) {
        return instance.is(Items.ENCHANTED_BOOK) ? DataComponents.STORED_ENCHANTMENTS : DataComponents.ENCHANTMENTS;
    }

    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(@NonNull ItemInstance instance) {

        //there seems no way to get patch from item instance, so we have to do this.
        //Should be fine though, no reason for other types to implement item instance AND be handed to this method.
        DataComponentPatch patch;
        if (instance instanceof ItemStack stack)
            patch = stack.getComponentsPatch();
        else if (instance instanceof ItemStackTemplate(
                Holder<Item> item, int count, DataComponentPatch dataComponentPatch
        ))
            patch = dataComponentPatch;
        else
            return new ItemStackTemplate(instance.typeHolder().value(), instance.count());

        var map = PatchedDataComponentMap.fromPatch(instance.typeHolder().components(), patch);

        boolean[] eternal = {false};
        map.forEach(comp -> {
            if (comp.toString().startsWith("forbidden_arcanus:modifier")
                    && comp.toString().contains("eternal")) {
                eternal[0] = true;
            }
        });

        boolean isDamageable = instance.has(DataComponents.MAX_DAMAGE) && !instance.has(DataComponents.UNBREAKABLE) && instance.has(DataComponents.DAMAGE);
        if (eternal[0] || isDamageable) {
            return new ItemStackTemplate(instance.typeHolder().value(), instance.count(), patch);
        }

        ItemEnchantments enchantments = instance.getOrDefault(getComponentType(instance), ItemEnchantments.EMPTY);
        int unbLvl = 0;
        for (Entry<Holder<Enchantment>, Integer> e : enchantments.entrySet()) {
            if (e.getKey().is(Enchantments.UNBREAKING)) {
                unbLvl = e.getValue();
            }
        }

        var maxDamage = map.getOrDefault(DataComponents.MAX_DAMAGE, 0);

        if (RandomSource.create().nextFloat() <= 1.0F / (unbLvl + 1))
            map.set(DataComponents.DAMAGE, Mth.clamp(map.getOrDefault(DataComponents.DAMAGE, 0), 0, maxDamage) + 1);


        return map.getOrDefault(DataComponents.DAMAGE, 0).intValue() == maxDamage ? null : new ItemStackTemplate(instance.typeHolder().value(), instance.count(), map.asPatch());
    }
}
