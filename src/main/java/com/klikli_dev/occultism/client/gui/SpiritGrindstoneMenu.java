package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.occultism.registry.OccultismBlocks;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

public class SpiritGrindstoneMenu extends AbstractContainerMenu {
    final Container repairSlots;
    private final float DURABILITY_MULTIPLIER_ON_REPAIR = 1.2F;
    private final Container resultSlots;
    private final ContainerLevelAccess access;
    private int xp;

    public SpiritGrindstoneMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(MenuType.GRINDSTONE, containerId);
        this.resultSlots = new ResultContainer();
        this.repairSlots = new SimpleContainer(2) {
            public void setChanged() {
                super.setChanged();
                SpiritGrindstoneMenu.this.slotsChanged(this);
            }
        };
        this.xp = -1;
        this.access = access;
        this.addSlot(new Slot(this.repairSlots, 0, 49, 19) {
            public boolean mayPlace(@NotNull ItemStack p_39607_) {
                return p_39607_.isDamageableItem() || EnchantmentHelper.hasAnyEnchantments(p_39607_) || p_39607_.has(DataComponents.REPAIRABLE);
            }
        });
        this.addSlot(new Slot(this.repairSlots, 1, 49, 40) {
            public boolean mayPlace(@NotNull ItemStack p_39616_) {
                return p_39616_.isDamageableItem() || EnchantmentHelper.hasAnyEnchantments(p_39616_) || p_39616_.has(DataComponents.REPAIRABLE);
            }
        });
        this.addSlot(new Slot(this.resultSlots, 2, 129, 34) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                CommonHooks.onGrindstoneTake(SpiritGrindstoneMenu.this.repairSlots, access, player, this::getExperienceAmount);
            }

            private int getExperienceAmount(Level level) {
                return SpiritGrindstoneMenu.this.xp > -1 ? SpiritGrindstoneMenu.this.xp :
                        this.getExperienceFromItem(SpiritGrindstoneMenu.this.repairSlots.getItem(0)) +
                                this.getExperienceFromItem(SpiritGrindstoneMenu.this.repairSlots.getItem(1));
            }

            private int getExperienceFromItem(ItemStack stack) {
                int l = 0;
                for (Entry<Holder<Enchantment>> holderEntry : EnchantmentHelper.getEnchantmentsForCrafting(stack).entrySet()) {
                    Holder<Enchantment> holder = holderEntry.getKey();
                    if (holder.is(EnchantmentTags.CURSE))
                        l += (holder.value()).getMinCost(holderEntry.getIntValue());
                }
                return l;
            }
        });

        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

    }

    public void slotsChanged(@NotNull Container inventory) {
        super.slotsChanged(inventory);
        if (inventory == this.repairSlots) {
            this.createResult();
        }

    }

    private void createResult() {
        this.xp = CommonHooks.onGrindstoneChange(this.repairSlots.getItem(0), this.repairSlots.getItem(1), this.resultSlots, -1);
        if (this.xp == Integer.MIN_VALUE) {
            this.resultSlots.setItem(0, this.computeResult(this.repairSlots.getItem(0), this.repairSlots.getItem(1)));
        }

        this.broadcastChanges();
    }

    private ItemStack computeResult(ItemStack inputItem, ItemStack additionalItem) {
        if (inputItem.isEmpty() && additionalItem.isEmpty()) {
            return ItemStack.EMPTY;
        } else if (inputItem.getCount() <= 1 && additionalItem.getCount() <= 1) {
            if (inputItem.isEmpty() || additionalItem.isEmpty()) {
                ItemStack itemstack = !inputItem.isEmpty() ? inputItem : additionalItem;
                return EnchantmentHelper.hasAnyEnchantments(itemstack) ? this.removeOnlyCursesFrom(itemstack.copy()) : ItemStack.EMPTY;
            } else {
                return this.mergeItems(inputItem, additionalItem);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    private ItemStack mergeItems(ItemStack inputItem, ItemStack additionalItem) {
        if (!inputItem.is(additionalItem.getItem())) {
            return ItemStack.EMPTY;
        } else {
            int i = Math.max(inputItem.getMaxDamage(), additionalItem.getMaxDamage());
            int i1 = 1;
            if (!inputItem.isDamageableItem() || !inputItem.has(DataComponents.REPAIRABLE)) {
                if (inputItem.getMaxStackSize() < 2 || !ItemStack.matches(inputItem, additionalItem))
                    return ItemStack.EMPTY;
                i1 = 2;
            }
            ItemStack itemstack = inputItem.copyWithCount(i1);
            if (itemstack.isDamageableItem()) {
                itemstack.set(DataComponents.MAX_DAMAGE, i);
                itemstack.setDamageValue((int) Math.max(0,
                        i - (inputItem.getMaxDamage() - inputItem.getDamageValue()
                                + additionalItem.getMaxDamage() - additionalItem.getDamageValue())
                                * this.DURABILITY_MULTIPLIER_ON_REPAIR));
                if (!additionalItem.has(DataComponents.REPAIRABLE))
                    itemstack.setDamageValue(inputItem.getDamageValue());
            }
            return this.removeOnlyCursesFrom(itemstack);
        }
    }

    private ItemStack removeOnlyCursesFrom(ItemStack item) {
        ItemEnchantments itemenchantments = EnchantmentHelper.updateEnchantments(item, (p_330066_) ->
                p_330066_.removeIf((p_344368_) -> p_344368_.is(EnchantmentTags.CURSE)));
        return item.is(Items.ENCHANTED_BOOK) && itemenchantments.isEmpty() ? item.transmuteCopy(Items.BOOK) : item;
    }

    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((p_39575_, p_39576_) -> this.clearContainer(player, this.repairSlots));
    }

    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, OccultismBlocks.SPIRIT_GRINDSTONE.get());
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            ItemStack itemstack2 = this.repairSlots.getItem(0);
            ItemStack itemstack3 = this.repairSlots.getItem(1);
            if (index == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0 && index != 1) {
                if (!itemstack2.isEmpty() && !itemstack3.isEmpty()) {
                    if (index >= 3 && index < 30) {
                        if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
