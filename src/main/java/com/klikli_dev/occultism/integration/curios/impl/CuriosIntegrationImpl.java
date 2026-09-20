// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.curios.impl;

import com.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.klikli_dev.occultism.common.item.familiar.FamiliarCurio;
import com.klikli_dev.occultism.common.item.storage.EnderSatchelItem;
import com.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.klikli_dev.occultism.common.item.tool.KnowledgeTabletItem;
import com.klikli_dev.occultism.integration.curios.CuriosIntegration;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Real Curios integration.
 *
 * <p>Excluded from the source set until Curios ships a build for the current
 * Minecraft version (see build.gradle). Re-enable the source set and the
 * runtime dependency once available.</p>
 */
public class CuriosIntegrationImpl implements CuriosIntegration {
    @Override
    public boolean isLoaded() {
        return ModList.get().isLoaded("curios");
    }

    @Override
    public boolean hasGoggles(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (OtherworldGogglesItem.isGogglesItem(helmet))
            return true;

        ICuriosItemHandler curiosHandler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (curiosHandler == null)
            return false;

        return curiosHandler.getCurios().values().stream()
                .map(ICurioStacksHandler::getStacks)
                .anyMatch(stackHandler -> contains(stackHandler, OtherworldGogglesItem::isGogglesItem));
    }

    @Override
    public boolean hasStaff(Player player) {
        if (player.getOffhandItem().is(OccultismItems.TRUE_SIGHT_STAFF))
            return true;

        ICuriosItemHandler curiosHandler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (curiosHandler == null)
            return false;

        return curiosHandler.getCurios().values().stream()
                .map(ICurioStacksHandler::getStacks)
                .anyMatch(stackHandler -> contains(stackHandler, stack -> stack.is(OccultismItems.TRUE_SIGHT_STAFF)));
    }

    @Override
    public ItemStack getBackpack(Player player) {
        ICuriosItemHandler curiosHandler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (curiosHandler == null)
            return ItemStack.EMPTY;

        for (String identifier : curiosHandler.getCurios().keySet()) {
            ItemStack stack = getSatchelItemFromSlot(curiosHandler, identifier);
            if (!stack.isEmpty()) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    protected static ItemStack getSatchelItemFromSlot(ICuriosItemHandler curiosHandler, String identifier) {
        if (curiosHandler == null) {
            return ItemStack.EMPTY;
        }

        ICurioStacksHandler slotHandler = curiosHandler.getStacksHandler(identifier).orElse(null);
        if (slotHandler == null) {
            return ItemStack.EMPTY;
        }

        IDynamicStackHandler stackHandler = slotHandler.getStacks();
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            if (stack.getItem() instanceof SatchelItem) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public SelectedCurio getStorageRemote(Player player) {
        int selectedSlot = player.getInventory().getSelectedSlot();
        ItemStack storageRemoteStack = player.getInventory().getSelectedItem();
        //if that is not a storage remote, get from curio
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = -1;
            storageRemoteStack = this.getStorageRemoteCurio(player);
        }

        //if not found, try to get from player inventory
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = this.getFirstStorageRemoteSlot(player);
            storageRemoteStack = selectedSlot >= 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a storage remote, proceed
        if (storageRemoteStack.getItem() instanceof StorageRemoteItem) {
            return new SelectedCurio(storageRemoteStack, selectedSlot);

        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStorageRemoteCurio(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.findCurios(stack -> stack.getItem() instanceof StorageRemoteItem)
                        .stream()
                        .map(SlotResult::stack)
                        .findFirst()
                        .orElse(ItemStack.EMPTY))
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public int getFirstBackpackSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof SatchelItem)
                return slot;
        }
        return -1;
    }

    @Override
    public int getFirstStorageRemoteSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof StorageRemoteItem)
                return slot;
        }
        return -1;
    }

    @Override
    public ItemStack getEnderSatchel(Player player) {
        ICuriosItemHandler curiosHandler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (curiosHandler == null)
            return ItemStack.EMPTY;

        for (String identifier : curiosHandler.getCurios().keySet()) {
            ItemStack stack = getEnderSatchelItemFromSlot(curiosHandler, identifier);
            if (!stack.isEmpty()) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    protected static ItemStack getEnderSatchelItemFromSlot(ICuriosItemHandler curiosHandler, String identifier) {
        if (curiosHandler == null) {
            return ItemStack.EMPTY;
        }

        ICurioStacksHandler slotHandler = curiosHandler.getStacksHandler(identifier).orElse(null);
        if (slotHandler == null) {
            return ItemStack.EMPTY;
        }

        IDynamicStackHandler stackHandler = slotHandler.getStacks();
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            if (stack.getItem() instanceof EnderSatchelItem) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private static boolean contains(IDynamicStackHandler stackHandler, Predicate<ItemStack> predicate) {
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            if (predicate.test(stackHandler.getStackInSlot(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getFirstEnderSatchelSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof EnderSatchelItem)
                return slot;
        }
        return -1;
    }

    @Override
    public ItemStack getXpTablet(Player player) {
        ICuriosItemHandler curiosHandler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (curiosHandler == null)
            return ItemStack.EMPTY;

        for (String identifier : curiosHandler.getCurios().keySet()) {
            ItemStack stack = getXpTabletItemFromSlot(curiosHandler, identifier);
            if (!stack.isEmpty()) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    protected static ItemStack getXpTabletItemFromSlot(ICuriosItemHandler curiosHandler, String identifier) {
        if (curiosHandler == null) {
            return ItemStack.EMPTY;
        }

        ICurioStacksHandler slotHandler = curiosHandler.getStacksHandler(identifier).orElse(null);
        if (slotHandler == null) {
            return ItemStack.EMPTY;
        }

        IDynamicStackHandler stackHandler = slotHandler.getStacks();
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            if (stack.getItem() instanceof KnowledgeTabletItem) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getEquippedCurioStacks(LivingEntity entity) {
        List<ItemStack> stacks = new ArrayList<>();

        var handler = CuriosApi.getCuriosInventory(entity).orElse(null);
        if (handler == null)
            return stacks;

        for (ICurioStacksHandler curios : handler.getCurios().values()) {
            var stackHandler = curios.getStacks();
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                stacks.add(stackHandler.getStackInSlot(i));
            }
        }
        return stacks;
    }

    @Override
    public void registerItemCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                CuriosCapability.ITEM, // capability to register for
                (itemStack, context) -> {
                    return new FamiliarCurio.Curio(itemStack);
                },
                // items to register for
                OccultismItems.FAMILIAR_RING.get(),
                OccultismItems.FAMILIAR_GLOVE.get(),
                OccultismItems.INFUSED_HELMET.get(),
                OccultismItems.INFUSED_CHESTPLATE.get(),
                OccultismItems.INFUSED_LEGGINGS.get(),
                OccultismItems.INFUSED_BOOTS.get(),
                OccultismItems.INFUSED_SPEAR.get(),
                OccultismItems.INFUSED_SWORD.get(),
                OccultismItems.INFUSED_SHOVEL.get(),
                OccultismItems.INFUSED_PICKAXE.get(),
                OccultismItems.INFUSED_AXE.get(),
                OccultismItems.INFUSED_HOE.get());
    }
}
