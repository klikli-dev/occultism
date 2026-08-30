package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

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
        return this.getPasteRepairInput(input) != null || RepairItemRecipe.INSTANCE.matches(input, level);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input) {
        PasteRepairInput pasteRepairInput = this.getPasteRepairInput(input);
        if (pasteRepairInput == null) {
            return RepairItemRecipe.INSTANCE.assemble(input);
        }

        return this.createPasteStack(pasteRepairInput.first, Math.min(pasteRepairInput.totalRemainingDurability(), pasteRepairInput.maxDamage()));
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        PasteRepairInput pasteRepairInput = this.getPasteRepairInput(input);
        if (pasteRepairInput == null) {
            return super.getRemainingItems(input);
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
        return stack.is(OccultismItems.NATURE_PASTE.get())
                || stack.is(OccultismItems.GRAY_PASTE.get())
                || stack.is(OccultismItems.FLAMING_PASTE.get());
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
