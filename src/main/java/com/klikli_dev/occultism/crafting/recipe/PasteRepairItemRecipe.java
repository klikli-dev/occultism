package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PasteRepairItemRecipe extends RepairItemRecipe {
    public static final RecipeSerializer<PasteRepairItemRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(PasteRepairItemRecipe::new);
    private static final RepairItemRecipe VANILLA_REPAIR = new RepairItemRecipe(CraftingBookCategory.MISC);

    public PasteRepairItemRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        return this.getPasteRepairInput(input) != null || super.matches(input, level);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.@NotNull Provider registries) {
        PasteRepairInput pasteRepairInput = this.getPasteRepairInput(input);
        if (pasteRepairInput == null) {
            return super.assemble(input, registries);
        }

        return this.createPasteStack(pasteRepairInput.first, Math.min(pasteRepairInput.totalRemainingDurability(), pasteRepairInput.maxDamage()));
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        PasteRepairInput pasteRepairInput = this.getPasteRepairInput(input);
        if (pasteRepairInput == null) {
            return this.defaultRemainingItems(input);
        }

        NonNullList<ItemStack> remainingItems = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        int leftoverDurability = pasteRepairInput.totalRemainingDurability() - pasteRepairInput.maxDamage();
        if (leftoverDurability > 0) {
            remainingItems.set(pasteRepairInput.secondSlot, this.createPasteStack(pasteRepairInput.second, leftoverDurability));
        }

        return remainingItems;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OccultismRecipes.REPAIR_ITEM.get();
    }

    private NonNullList<ItemStack> defaultRemainingItems(CraftingInput input) {
        return VANILLA_REPAIR.getRemainingItems(input);
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

    private static final class PasteRepairInput {
        private final int firstSlot;
        private final ItemStack first;
        private final int secondSlot;
        private final ItemStack second;

        private PasteRepairInput(int firstSlot, ItemStack first, int secondSlot, ItemStack second) {
            this.firstSlot = firstSlot;
            this.first = first;
            this.secondSlot = secondSlot;
            this.second = second;
        }

        private int maxDamage() {
            return this.first.getMaxDamage();
        }

        private int totalRemainingDurability() {
            return this.first.getMaxDamage() - this.first.getDamageValue() + this.second.getMaxDamage() - this.second.getDamageValue();
        }
    }
}
