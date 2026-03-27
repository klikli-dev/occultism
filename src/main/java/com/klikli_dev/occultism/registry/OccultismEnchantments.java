package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.item.enchantment.Enchantment.enchantment;

public class OccultismEnchantments {
    public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_LEVEL_BASED =
            DeferredRegister.create(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Occultism.MODID);
    public static final ResourceKey<Enchantment> FRACTURE_SOUL = ResourceKey.create(
            Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Occultism.MODID, "fracture_soul"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        register(context, FRACTURE_SOUL, enchantment(Enchantment.definition(
            items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
            items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
            1, // weight
            5, // max level
            Enchantment.dynamicCost(10, 2),
            Enchantment.dynamicCost(18, 3),
            8, // anvil cost
            EquipmentSlotGroup.MAINHAND
        )));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }

    public static void register(IEventBus eventBus) {
        ENCHANTMENT_LEVEL_BASED.register(eventBus);
    }
}
