/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.common.container.storage;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.client.gui.storage.StorageControllerGuiBase;
import com.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.klikli_dev.occultism.common.misc.StorageControllerCraftingInventory;
import com.klikli_dev.occultism.common.misc.StorageControllerSlot;
import com.klikli_dev.occultism.network.Networking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class StorageControllerContainerBase extends AbstractContainerMenu implements IStorageControllerContainer {

    /**
     * Hack to only allow one player to open a container at a time.
     */
    public static ConcurrentMap<BlockPos, UUID> openContainers = new ConcurrentHashMap<>();
    public Inventory playerInventory;
    public Player player;
    protected ResultContainer result;
    protected StorageControllerCraftingInventory matrix;
    protected SimpleContainer orderInventory;
    protected RecipeHolder<CraftingRecipe> currentRecipe;
    /**
     * used to lock recipe while crafting
     */
    protected boolean recipeLocked = false;

    protected StorageControllerContainerBase(@Nullable MenuType<?> type, int id, Inventory playerInventory) {
        super(type, id);
        this.playerInventory = playerInventory;
        this.player = playerInventory.player;

        this.result = new ResultContainer();
        this.orderInventory = new SimpleContainer(1);
    }

    public static boolean canOpen(Player player, BlockPos pos) {
        if (!openContainers.containsKey(pos) || openContainers.get(pos).equals(player.getUUID())) {
            return true;
        }

        player.sendSystemMessage(Component.translatable(TranslationKeys.MESSAGE_CONTAINER_ALREADY_OPEN).withStyle(ChatFormatting.RED));
        return false;
    }

    public static void reserve(Player player, BlockPos pos) {
        openContainers.put(pos, player.getUUID());
    }

    @Override
    public GlobalBlockPos getStorageControllerGlobalBlockPos() {
        return GlobalBlockPos.from(
                (BlockEntity) this.getStorageController());
    }

    @Override
    public CraftingContainer getCraftMatrix() {
        return this.matrix;
    }

    @Override
    public void slotsChanged(Container inventoryIn) {
        if (this.recipeLocked) {
            //only allow matrix changes while we are not crafting
            return;
        }
        this.updateCraftingSlots(false);
        this.broadcastChanges();
        this.findRecipeForMatrix();
    }

    @Override
    public SimpleContainer getOrderSlot() {
        return this.orderInventory;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (player.level().isClientSide)
            return ItemStack.EMPTY;

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();

            IStorageController storageController = this.getStorageController();

            //index 0 is our crafting output, so we need to craft the item.
            if (index == 0) {
                this.craftShift(player, storageController);
                return ItemStack.EMPTY;
            } else if (storageController != null) {
                //insert item into controller
                int remainingItems = storageController.insertStack(slotStack, false);

                //get the stack of remaining items
                ItemStack remainingItemStack = remainingItems == 0 ? ItemStack.EMPTY : slotStack.copyWithCount(remainingItems);
                slot.set(remainingItemStack);

                //sync slots
                this.broadcastChanges();

                //get updated stacks from storage controller and send to client
                Networking.sendTo((ServerPlayer) player, storageController.getMessageUpdateStacks());

                if (!remainingItemStack.isEmpty()) {
                    slot.onTake(player, slotStack);
                }
                return ItemStack.EMPTY;
            }
        }
        return result;
    }

    @Override
    public void removed(Player playerIn) {
        this.updateCraftingSlots(false);
        this.updateOrderSlot(true); //only send network update on second call
        super.removed(playerIn);
        openContainers.values().removeIf(uuid -> uuid.equals(playerIn.getUUID()));
    }

    protected void setupPlayerInventorySlots() {
        int playerInventoryTop = 174;
        int playerInventoryLeft = 8 + StorageControllerGuiBase.ORDER_AREA_OFFSET;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    protected void setupCraftingGrid() {

        int craftingGridTop = 113;
        int craftingGridLeft = 37 + StorageControllerGuiBase.ORDER_AREA_OFFSET;
        int index = 0;
        //3x3 crafting grid
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(
                        new Slot(this.matrix, index++, craftingGridLeft + j * 18, craftingGridTop + i * 18));
            }
        }
    }

    protected void setupCraftingOutput() {
        int craftingOutputTop = 131;
        int craftingOutputLeft = 130 + StorageControllerGuiBase.ORDER_AREA_OFFSET;
        StorageControllerSlot slotCraftOutput = new StorageControllerSlot(this.playerInventory.player, this.matrix,
                this.result, this, 0, craftingOutputLeft, craftingOutputTop);
        this.addSlot(slotCraftOutput);
    }

    protected void setupOrderInventorySlot() {
        int orderSlotTop = 36;
        int orderSlotLeft = 13;
        this.addSlot(new Slot(this.orderInventory, 0, orderSlotLeft, orderSlotTop));
    }

    protected abstract void setupPlayerHotbar();

    protected void findRecipeForMatrixClient() {
        var optional =
                this.player.level().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, this.matrix, this.player.level());
        optional.ifPresentOrElse(iCraftingRecipe -> this.currentRecipe = iCraftingRecipe, () -> this.currentRecipe = null);
    }

    protected void findRecipeForMatrix() {
        //NOTE: if there are issues, set up a copy of this based on WorkBenchContainer func_217066_a / updateCraftingResult
        //      and call it onCraftingMatrixChanged(). Send slot packet!
        if (!this.player.level().isClientSide) {
            this.currentRecipe = null;
            ServerPlayer serverplayerentity = (ServerPlayer) this.player;
            ItemStack itemstack = ItemStack.EMPTY;
            var optional = this.player.level().getServer().getRecipeManager()
                    .getRecipeFor(RecipeType.CRAFTING, this.matrix,
                            this.player.level());
            if (optional.isPresent()) {
                var icraftingrecipe = optional.get();
                if (this.result.setRecipeUsed(this.player.level(), serverplayerentity, icraftingrecipe)) {
                    itemstack = icraftingrecipe.value().assemble(this.matrix, serverplayerentity.level().registryAccess());
                    this.currentRecipe = icraftingrecipe;
                }
            }

            this.result.setItem(0, itemstack);
            serverplayerentity.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, 0, 0, itemstack));
        }
    }

    protected void craftShift(Player player, IStorageController storageController) {
        if (this.matrix == null) {
            return;
        }

        this.findRecipeForMatrixClient();
        if (this.currentRecipe == null) {
            return;
        }

        //lock recipes to avoid modification while we shift craft
        this.recipeLocked = true;

        //copy the recipe stacks
        List<ItemStack> recipeCopy = new ArrayList<>(this.matrix.getContainerSize());
        for (int i = 0; i < this.matrix.getContainerSize(); i++) {
            recipeCopy.add(this.matrix.getItem(i).copy());
        }

        //Get the crafting result and abort if none
        ItemStack result = this.currentRecipe.value().assemble(this.matrix, player.level().registryAccess());
        if (result.isEmpty()) {
            return;
        }

        //get the stack size of the result
        int resultStackSize = result.getCount();
        List<ItemStack> resultList = new ArrayList<>();
        int crafted = 0;
        while (crafted + resultStackSize <= result.getMaxStackSize()) {
            //AFAIK this should not happen unless an outside mod intervenes with the inventory during crafting
            //but, in modpacks it definitely does happen, see https://github.com/klikli-dev/occultism/issues/212
            //so we exit early here.
            if (this.currentRecipe == null)
                break;

            ItemStack newResult = this.currentRecipe.value().assemble(this.matrix, player.level().registryAccess()).copy();
            if (newResult.getItem() != result.getItem())
                break;


            //exit if we can no longer insert
            if (!ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(this.playerInventory), newResult, true)
                    .isEmpty()) {
                break;
            }

            //if recipe is no longer fulfilled, stop
            if (!this.currentRecipe.value().matches(this.matrix, player.level())) {
                break;
            }

            //region onTake replacement for crafting

            //give to the player
            //historically we used ItemHandlerHelper.giveItemToPlayer(player, result); here
            //now we instead pre-merge the stack -> might prevent intervention by other mods.
            resultList.add(newResult);

            //get remaining items in the crafting matrix
            NonNullList<ItemStack> remainingCraftingItems = this.currentRecipe.value().getRemainingItems(this.matrix);
            for (int i = 0; i < remainingCraftingItems.size(); ++i) {

                ItemStack currentCraftingItem = remainingCraftingItems.get(i);
                ItemStack stackInSlot = this.matrix.getItem(i);

                //if we find an empty stack, shrink it to remove it.
                if (currentCraftingItem.isEmpty()) {
                    this.matrix.getItem(i).shrink(1);
                    continue;
                }

                //handle container item refunding
                if (!stackInSlot.getItem().getCraftingRemainingItem(stackInSlot).isEmpty()) {
                    ItemStack container = stackInSlot.getItem().getCraftingRemainingItem(stackInSlot);
                    if (!stackInSlot.isStackable()) {
                        stackInSlot = container;
                        this.matrix.setItem(i, stackInSlot);
                    } else {
                        //handle stackable container items
                        stackInSlot.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(player, container);
                    }
                } else if (!currentCraftingItem.isEmpty()) {
                    //if the slot is empty now we just place the crafting item in it
                    if (stackInSlot.isEmpty()) {
                        this.matrix.setItem(i, currentCraftingItem);
                    }
                    //handle "normal items" ie non-damagable
                    else if (!stackInSlot.isDamageableItem() && ItemStack.isSameItemSameComponents(stackInSlot, currentCraftingItem)) {
                        //Used to call grow here, but that causes dupes of unbreakable items
                        //removing it seems not to cause any harm?
                        //  currentCraftingItem.grow(stackInSlot.getCount());
                        this.matrix.setItem(i, currentCraftingItem);
                    }
                    //handle items that consume durability on craft
                    else if (ItemStack.isSameItem(stackInSlot, currentCraftingItem)) {
                        this.matrix.setItem(i, currentCraftingItem);
                    } else {
                        //last resort, try to place in player inventory or if that fails, drop.
                        ItemHandlerHelper.giveItemToPlayer(player, newResult);
                    }

                } else if (!stackInSlot.isEmpty()) {
                    //decrease the stack size in the matrix
                    this.matrix.removeItem(i, 1);
                    stackInSlot = this.matrix.getItem(i);
                }
            }
            //endregion onTake replacement for crafting


            crafted += resultStackSize;
            for (int i = 0; i < this.matrix.getContainerSize(); i++) {
                ItemStack stackInSlot = this.matrix.getItem(i);
                //if the stack is empty, refill from storage and then continue looping
                if (stackInSlot.isEmpty()) {
                    ItemStack recipeStack = recipeCopy.get(i);

                    ItemStackComparator comparator = !recipeStack.isEmpty() ? new ItemStackComparator(
                            recipeStack) : null;

                    ItemStack requestedItem = this.getStorageController().getOneOfMostCommonItem(comparator, false);
                    this.matrix.setItem(i, requestedItem);
                }
            }
            this.slotsChanged(this.matrix);
        }

        //now actually give to the players
        ItemStack finalResult = new ItemStack(result.getItem(), 0);
        finalResult.applyComponents(result.getComponents());
        for (ItemStack intermediateResult : resultList) {
            finalResult.setCount(finalResult.getCount() + intermediateResult.getCount());
        }
        ItemHandlerHelper.giveItemToPlayer(player, finalResult);

        this.broadcastChanges();

        //unlock crafting matrix
        this.recipeLocked = false;

        //update crafting matrix to handle container items / items that survive crafting
        this.slotsChanged(this.matrix);
        Networking.sendTo((ServerPlayer) player, this.getStorageController().getMessageUpdateStacks());

    }

}
