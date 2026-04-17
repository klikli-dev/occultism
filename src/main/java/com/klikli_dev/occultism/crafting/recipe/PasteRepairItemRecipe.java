package com.klikli_dev.occultism.crafting.recipe;

import com.google.common.collect.Sets;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.tags.EnchantmentTags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;

public class PasteRepairItemRecipe extends CustomRecipe {
    public static final PasteRepairItemRecipe INSTANCE = new PasteRepairItemRecipe();
    public static final MapCodec<PasteRepairItemRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, PasteRepairItemRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<PasteRepairItemRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public PasteRepairItemRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        return getItemsToCombine(input) != null;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input) {
        Pair<ItemStack, ItemStack> itemsToCombine = getItemsToCombine(input);
        if (itemsToCombine == null) {
            return ItemStack.EMPTY;
        }

        PasteRepairInput pasteRepairInput = this.getPasteRepairInput(input);
        if (pasteRepairInput != null) {
            return this.createPasteStack(pasteRepairInput.first, Math.min(pasteRepairInput.totalRemainingDurability(), pasteRepairInput.maxDamage()));
        }

        ItemStack first = itemsToCombine.getFirst();
        ItemStack second = itemsToCombine.getSecond();
        int durability = Math.max(first.getMaxDamage(), second.getMaxDamage());
        int remaining1 = first.getMaxDamage() - first.getDamageValue();
        int remaining2 = second.getMaxDamage() - second.getDamageValue();
        int remaining = remaining1 + remaining2 + durability * 5 / 100;
        ItemStack itemStack = new ItemStack(first.getItem());
        itemStack.set(DataComponents.MAX_DAMAGE, durability);
        itemStack.setDamageValue(Math.max(durability - remaining, 0));
        ItemEnchantments firstEnchants = EnchantmentHelper.getEnchantmentsForCrafting(first);
        ItemEnchantments secondEnchants = EnchantmentHelper.getEnchantmentsForCrafting(second);
        EnchantmentHelper.updateEnchantments(itemStack, newEnchantments -> {
            Set<Holder<Enchantment>> enchantments = Sets.union(firstEnchants.keySet(), secondEnchants.keySet());
            for (Holder<Enchantment> enchantment : enchantments) {
                if (enchantment.is(EnchantmentTags.CURSE)) {
                    int enchantLevel = Math.max(firstEnchants.getLevel(enchantment), secondEnchants.getLevel(enchantment));
                    newEnchantments.set(enchantment, enchantLevel);
                }
            }
        });
        return itemStack;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        PasteRepairInput pasteRepairInput = this.getPasteRepairInput(input);
        if (pasteRepairInput == null) {
            return CraftingRecipe.defaultCraftingReminder(input);
        }

        NonNullList<ItemStack> remainingItems = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        int leftoverDurability = pasteRepairInput.totalRemainingDurability() - pasteRepairInput.maxDamage();
        if (leftoverDurability <= 0) {
            return remainingItems;
        }

        if (pasteRepairInput.firstRemainingDurability() >= pasteRepairInput.secondRemainingDurability()) {
            remainingItems.set(pasteRepairInput.firstSlot, this.createPasteStack(pasteRepairInput.first, leftoverDurability));
        } else {
            remainingItems.set(pasteRepairInput.secondSlot, this.createPasteStack(pasteRepairInput.second, leftoverDurability));
        }

        return remainingItems;
    }

    @Override
    public @NotNull RecipeSerializer<PasteRepairItemRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Nullable
    private static Pair<ItemStack, ItemStack> getItemsToCombine(CraftingInput input) {
        if (input.ingredientCount() != 2) {
            return null;
        }

        ItemStack first = null;

        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getItem(i);
            if (!itemStack.isEmpty()) {
                if (first != null) {
                    return canCombine(first, itemStack) ? Pair.of(first, itemStack) : null;
                }

                first = itemStack;
            }
        }

        return null;
    }

    private static boolean canCombine(ItemStack first, ItemStack second) {
        return second.is(first.getItem())
                && first.getCount() == 1
                && second.getCount() == 1
                && first.has(DataComponents.MAX_DAMAGE)
                && second.has(DataComponents.MAX_DAMAGE)
                && first.has(DataComponents.DAMAGE)
                && second.has(DataComponents.DAMAGE);
    }

    private ItemStack createPasteStack(ItemStack source, int remainingDurability) {
        if (remainingDurability <= 0) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = source.copyWithCount(1);
        int clampedRemainingDurability = Math.min(remainingDurability, stack.getMaxDamage());
        stack.setDamageValue(stack.getMaxDamage() - clampedRemainingDurability);
        return stack;
    }

    @Nullable
    private PasteRepairInput getPasteRepairInput(CraftingInput input) {
        ItemStack first = ItemStack.EMPTY;
        ItemStack second = ItemStack.EMPTY;
        int firstSlot = -1;
        int secondSlot = -1;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (first.isEmpty()) {
                first = stack;
                firstSlot = i;
            } else if (second.isEmpty()) {
                second = stack;
                secondSlot = i;
            } else {
                return null;
            }
        }

        if (first.isEmpty() || second.isEmpty()) {
            return null;
        }

        if (!this.isRepairPaste(first) || !first.is(second.getItem())) {
            return null;
        }

        if (first.getCount() != 1 || second.getCount() != 1 || !first.isDamageableItem() || !second.isDamageableItem()) {
            return null;
        }

        return new PasteRepairInput(firstSlot, first, secondSlot, second);
    }

    private boolean isRepairPaste(ItemStack stack) {
        return stack.is(OccultismItems.NATURE_PASTE.get()) || stack.is(OccultismItems.GRAY_PASTE.get());
    }

    private record PasteRepairInput(int firstSlot, ItemStack first, int secondSlot, ItemStack second) {

        private int maxDamage() {
                return this.first.getMaxDamage();
            }

            private int firstRemainingDurability() {
                return this.first.getMaxDamage() - this.first.getDamageValue();
            }

            private int secondRemainingDurability() {
                return this.second.getMaxDamage() - this.second.getDamageValue();
            }

            private int totalRemainingDurability() {
                return this.firstRemainingDurability() + this.secondRemainingDurability();
            }
        }
}
