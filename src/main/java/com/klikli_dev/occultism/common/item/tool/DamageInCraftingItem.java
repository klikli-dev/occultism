package com.klikli_dev.occultism.common.item.tool;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

public class DamageInCraftingItem extends Item {

    public DamageInCraftingItem(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(ItemInstance instance) {
        int damage = instance.getOrDefault(DataComponents.DAMAGE, 0);
        int maxDamage = instance.getOrDefault(DataComponents.MAX_DAMAGE, 0);
        if (damage >= maxDamage - 1) {
            return null; // item would break
        }
        return new ItemStackTemplate(this, 1, DataComponentPatch.builder().set(DataComponents.DAMAGE, damage + 1).build());
    }
}
