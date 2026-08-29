package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.occultism.integration.apothicenchanting.ApothicEnchantingIntegration;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.player.AnvilCraftEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IesniumAnvilMenu extends AnvilMenu {
    @Nullable
    private String itemName;
    private boolean freeRenaming;
    // TODO: Fix the maximum level couldn't be exceeded by 1 in createResultInternal() when Apothic is present
    // For now, this solution fixes the functionality, but it will render incorrectly the level when max+1
    private ItemEnchantments apothicTempFix;

    public IesniumAnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
        this.freeRenaming = false;
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        final int cost = this.getCost();
        return (player.hasInfiniteMaterials() || player.experienceLevel >= cost / 2)
                && (cost > 0 || this.freeRenaming);
    }

    @Override
    protected void onTake(@NotNull Player player, @NotNull ItemStack stack) {
        if (this.apothicTempFix != null) {
            EnchantmentHelper.setEnchantments(stack, this.apothicTempFix);
            this.apothicTempFix = null;
        }

        ItemStack leftInput = this.inputSlots.getItem(0).copy();
        ItemStack rightInput = this.inputSlots.getItem(1).copy();

        AnvilCraftEvent.Pre preEvent = CommonHooks.fireAnvilCraftPre(this, player, stack, leftInput, rightInput);
        if (preEvent.isCanceled())
            return;

        final int cost = this.getCost();
        if (!player.hasInfiniteMaterials()) {
            if (ApothicEnchantingIntegration.isLoaded()) {
                player.giveExperiencePoints(-ApothicEnchantingIntegration.getTotalExperiencePointsForLevel(cost / 2));
            } else {
                player.giveExperienceLevels(-(cost / 2));
            }
        }

        if (this.repairItemCountCost > 0) {
            ItemStack addition = this.inputSlots.getItem(1);
            if (!addition.isEmpty() && addition.getCount() > this.repairItemCountCost) {
                addition.shrink(this.repairItemCountCost);
                this.inputSlots.setItem(1, addition);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        this.setCost(0);
        if (player instanceof ServerPlayer serverPlayer) {
            if (!StringUtil.isBlank(this.itemName) && !this.inputSlots.getItem(0).getHoverName().getString().equals(this.itemName)) {
                serverPlayer.getTextFilter().processStreamMessage(this.itemName);
            }
        }

        this.inputSlots.setItem(0, ItemStack.EMPTY);
        this.access.execute((level, pos) -> level.levelEvent(1030, pos, 0));
        CommonHooks.fireAnvilCraftPost(this, player, stack, leftInput, rightInput);
    }

    @Override
    protected void createResultInternal() {
        ItemStack leftInput = this.inputSlots.getItem(0);
        this.freeRenaming = false;
        this.setCost(1);
        int price = 0;
        long tax = 0L;
        int namingCost = 0;
        if (!leftInput.isEmpty() && EnchantmentHelper.canStoreEnchantments(leftInput)) {
            ItemStack result = leftInput.copy();
            ItemStack rightInput = this.inputSlots.getItem(1);
            Mutable leftEnchantments = new Mutable(EnchantmentHelper.getEnchantmentsForCrafting(result));
            tax += (long) leftInput.getOrDefault(DataComponents.REPAIR_COST, 0)
                    + (long) rightInput.getOrDefault(DataComponents.REPAIR_COST, 0) / 2;
            this.repairItemCountCost = 0;
            if (!rightInput.isEmpty()) {
                boolean rightIsBook = rightInput.has(DataComponents.STORED_ENCHANTMENTS);

                if (result.isDamageableItem() && leftInput.isValidRepairItem(rightInput)) {
                    int damage = Math.min(result.getDamageValue(), result.getMaxDamage() / 3);
                    if (damage <= 0) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.setCost(0);
                        return;
                    }

                    int materialCost;
                    int resultDamage;
                    for (materialCost = 0; damage > 0 && materialCost < rightInput.getCount(); materialCost++) {
                        resultDamage = result.getDamageValue() - damage;
                        result.setDamageValue(resultDamage);
                        damage = Math.min(result.getDamageValue(), result.getMaxDamage() / 3);
                        price++;
                    }

                    this.repairItemCountCost = materialCost;
                } else {
                    if (!rightIsBook && (!result.is(rightInput.getItem()) || !result.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.setCost(0);
                        return;
                    }

                    if (result.isDamageableItem() && !rightIsBook) {
                        int leftDurability = leftInput.getMaxDamage() - leftInput.getDamageValue();
                        int rightDurability = rightInput.getMaxDamage() - rightInput.getDamageValue();
                        int resultDurability = rightDurability + leftDurability + result.getMaxDamage() * 13 / 100;
                        int resultDamage = result.getMaxDamage() - resultDurability;
                        if (resultDamage < 0) {
                            resultDamage = 0;
                        }

                        if (resultDamage < result.getDamageValue()) {
                            result.setDamageValue(resultDamage);
                            price += 2;
                        }
                    }

                    ItemEnchantments rightEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(rightInput);
                    boolean isAnyEnchantmentCompatible = false;
                    boolean isAnyEnchantmentNotCompatible = false;
                    boolean useApothicTempFix = false;

                    for (Entry<Holder<Enchantment>> currentEnchantment : rightEnchantments.entrySet()) {
                        Holder<Enchantment> holder = currentEnchantment.getKey();
                        int activeEnchLvl = leftEnchantments.getLevel(holder);
                        int currentEnchLvl = currentEnchantment.getIntValue();
                        int resultEnchLvl = activeEnchLvl == currentEnchLvl ? currentEnchLvl + 1 : Math.max(currentEnchLvl, activeEnchLvl);
                        Enchantment enchantment = holder.value();
                        // Neo: Respect IItemExtension#supportsEnchantment - we also delegate the logic for Enchanted Books to this method.
                        // Though we still allow creative players to combine any item with any enchantment in the anvil here.
                        boolean compatible = leftInput.supportsEnchantment(holder)
                                || this.player.getAbilities().instabuild;

                        for (Holder<Enchantment> otherEnch : leftEnchantments.keySet()) {
                            if (!otherEnch.equals(holder) && !Enchantment.areCompatible(holder, otherEnch)) {
                                compatible = false;
                            }
                        }

                        if (!compatible) {
                            isAnyEnchantmentNotCompatible = true;
                        } else {
                            isAnyEnchantmentCompatible = true;
                            // +1 to enchantment level limit
                            if (ApothicEnchantingIntegration.isLoaded()) {
                                int maxLvl = ApothicEnchantingIntegration.getApothicMaxLevel(enchantment) + 1;
                                if (resultEnchLvl > maxLvl) {
                                    resultEnchLvl = Math.max(activeEnchLvl, currentEnchLvl);
                                    useApothicTempFix = true;
                                } else if (resultEnchLvl == maxLvl)
                                    useApothicTempFix = true;
                            } else {
                                if (resultEnchLvl > enchantment.getMaxLevel() + 1) {
                                    resultEnchLvl = Math.max(activeEnchLvl, currentEnchLvl);
                                }
                            }
                            leftEnchantments.set(holder, resultEnchLvl);
                            int enchCost = enchantment.getAnvilCost();
                            if (rightIsBook) {
                                enchCost = Math.max(1, enchCost / 2);
                            }
                            price += enchCost * resultEnchLvl;
                            if (leftInput.getCount() > 1) {
                                this.resultSlots.setItem(0, ItemStack.EMPTY);
                                this.setCost(0);
                                return;
                            }
                        }
                    }

                    if (isAnyEnchantmentNotCompatible && !isAnyEnchantmentCompatible) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.setCost(0);
                        return;
                    }

                    if (useApothicTempFix)
                        this.apothicTempFix = leftEnchantments.toImmutable();
                }
            }

            if (this.itemName != null && !StringUtil.isBlank(this.itemName)) {
                if (!this.itemName.equals(leftInput.getHoverName().getString())) {
                    this.freeRenaming = true;
                    namingCost++;
                    price++;
                    result.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
                }
            } else if (leftInput.has(DataComponents.CUSTOM_NAME)) {
                this.freeRenaming = true;
                namingCost++;
                price++;
                result.remove(DataComponents.CUSTOM_NAME);
            }

            //ignore tax if only renaming
            if (namingCost == price)
                tax = 0;

            int finalPrice = price <= 0 ? 0 : (int) Mth.clamp(tax + (long) price - namingCost, 0L, 2147483647L);
            this.setCost(finalPrice);
            if (price <= 0) {
                result = ItemStack.EMPTY;
            }

            if (this.getCost() >= 100 && !this.player.getAbilities().instabuild && !ApothicEnchantingIntegration.isLoaded()) {
                result = ItemStack.EMPTY;
            }

            if (!result.isEmpty()) {

                //only increase repair cost if the player do more than renaming
                if (namingCost != price) {
                    int baseCost = leftInput.getOrDefault(DataComponents.REPAIR_COST, 0)
                            + rightInput.getOrDefault(DataComponents.REPAIR_COST, 0);
                    baseCost = baseCost / 2;
                    if (price > 0) {
                        baseCost = calculateLowedIncreasedRepairCost(baseCost);
                    }

                    result.set(DataComponents.REPAIR_COST, baseCost);
                }
                EnchantmentHelper.setEnchantments(result, leftEnchantments.toImmutable());
            }

            this.resultSlots.setItem(0, result);
            this.broadcastChanges();
        } else {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.setCost(0);
        }

    }

    public static int calculateLowedIncreasedRepairCost(int oldRepairCost) {
        return (int) Math.min((long) oldRepairCost + 1L, 2147483647L);
    }

    public boolean setItemName(@NotNull String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
            if (this.getSlot(2).hasItem()) {
                ItemStack itemstack = this.getSlot(2).getItem();
                if (StringUtil.isBlank(s)) {
                    itemstack.remove(DataComponents.CUSTOM_NAME);
                } else {
                    itemstack.set(DataComponents.CUSTOM_NAME, Component.literal(s));
                }
            }

            this.createResult();
            return true;
        } else {
            return false;
        }
    }

    private static @Nullable String validateName(String name) {
        String filteredName = StringUtil.filterText(name);
        return filteredName.length() <= 50 ? filteredName : null;
    }
}
