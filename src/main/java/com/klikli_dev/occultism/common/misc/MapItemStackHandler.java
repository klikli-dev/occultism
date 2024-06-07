package com.klikli_dev.occultism.common.misc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class MapItemStackHandler implements IItemHandler, IItemHandlerModifiable, IMapItemHandlerModifiable, INBTSerializable<CompoundTag> {
    protected static final int VIRTUAL_SLOT = -1;

    //Note: liliandev (Neo Discord) in response to "not a string" error for our codec when using codec.unboundedmap:
    //unboundedMap turns a map into
    //{
    //"key1":{data},
    //"key2":{data},
    //[...]
    //}
    //and ItemStackKey Codec doesn't return things that can be turned into Strings. You would have to codec them as a list of key, value pairs and you can xmap that back to a map
    private static final Codec<Map<ItemStackKey, Integer>> MAP_CODEC = Codec.list(Codec.pair(ItemStackKey.CODEC.fieldOf("itemStackkey").codec(), Codec.INT.fieldOf("int").codec()))
            .xmap(
                    list -> list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)),
                    map -> map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue()))
                            .collect(Collectors.toList())
            );
    public static final Codec<MapItemStackHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    MAP_CODEC.fieldOf("keyToCountMap").forGetter(handler -> handler.keyToCountMap),
                    MAP_CODEC.fieldOf("keyToSlot").forGetter(handler -> handler.keyToSlot),
                    Codec.INT.listOf().fieldOf("emptySlots").forGetter(handler -> handler.emptySlots),
                    Codec.INT.fieldOf("nextSlot").forGetter(handler -> handler.nextSlotIndex),
                    Codec.INT.fieldOf("maxSlots").forGetter(handler -> handler.maxItemTypes),
                    Codec.LONG.fieldOf("totalItemCount").forGetter(handler -> handler.totalItemCount),
                    Codec.LONG.fieldOf("maxTotalItemCount").forGetter(handler -> handler.maxTotalItemCount)
            ).apply(instance, (keyToCountMap, keyToSlot, emptySlots, nextSlot, maxSlots, totalItemCount, maxTotalItemCount) ->
                    new MapItemStackHandler(new Object2IntOpenHashMap<>(keyToCountMap), HashBiMap.create(keyToSlot), emptySlots.stream().collect(Collectors.toCollection(Stack::new)), nextSlot, maxSlots, totalItemCount, maxTotalItemCount))
    );
    /**
     * The source of truth for contents of this handler.
     */
    protected Object2IntOpenHashMap<ItemStackKey> keyToCountMap;
    /**
     * Slot view for backwards compat with slot based item handlers.
     */
    protected BiMap<ItemStackKey, Integer> keyToSlot;
    /**
     * Temporarily store empty slots for reuse. This is necessary if we remove from the middle of keyToSlot.
     */
    protected Stack<Integer> emptySlots;
    /**
     * The next slot index to use if there are no empty slots.
     */
    protected int nextSlotIndex;
    /**
     * The maximum amount of different item types supported. This effectively is a max slot count due to the 1 slot per item type limit.
     */
    protected int maxItemTypes;
    /**
     * The total amount of items in the handler.
     */
    protected long totalItemCount;
    /**
     * The maximum allowed total amount of items in the handler.
     */
    protected long maxTotalItemCount;

    public MapItemStackHandler() {
        this(-1, -1);
    }


    public MapItemStackHandler(int maxItemTypes, long maxTotalItemCount) {
        this(new Object2IntOpenHashMap<>(), HashBiMap.create(), new Stack<>(), 0, maxItemTypes, 0, maxTotalItemCount);
    }

    public MapItemStackHandler(Object2IntOpenHashMap<ItemStackKey> keyToCountMap, BiMap<ItemStackKey, Integer> keyToSlot, Stack<Integer> emptySlots, int nextSlotIndex, int maxItemTypes, long totalItemCount, long maxTotalItemCount) {
        this.keyToCountMap = keyToCountMap;
        this.keyToSlot = keyToSlot;
        this.emptySlots = emptySlots;
        this.nextSlotIndex = nextSlotIndex;
        this.maxItemTypes = maxItemTypes;
        this.totalItemCount = totalItemCount;
        this.maxTotalItemCount = maxTotalItemCount;
    }

    public Object2IntOpenHashMap<ItemStackKey> keyToCountMap() {
        return this.keyToCountMap;
    }

    public long totalItemCount() {
        return this.totalItemCount;
    }

    public int maxItemTypes() {
        return this.maxItemTypes;
    }

    public void maxItemTypes(int maxItemTypes) {
        this.maxItemTypes = maxItemTypes;
    }

    public long maxTotalItemCount() {
        return this.maxTotalItemCount;
    }

    public void maxTotalItemCount(long maxTotalItemCount) {
        this.maxTotalItemCount = maxTotalItemCount;
    }

    @Override
    public int get(ItemStack stack) {
        return this.get(ItemStackKey.of(stack));
    }

    public int get(ItemStackKey key) {
        return this.keyToCountMap.getOrDefault(key, 0);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return (CompoundTag) CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        CODEC.parse(NbtOps.INSTANCE, nbt).resultOrPartial(e -> {
            throw new RuntimeException("Failed to decode MapItemStackHandler: " + e);
        }).ifPresent(handler -> {
            this.keyToCountMap = handler.keyToCountMap;
            this.keyToSlot = handler.keyToSlot;
            this.emptySlots = handler.emptySlots;
            this.nextSlotIndex = handler.nextSlotIndex;
            this.maxItemTypes = handler.maxItemTypes;
            this.totalItemCount = handler.totalItemCount;
            this.maxTotalItemCount = handler.maxTotalItemCount;
        });
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        //Note: This can go over the maxTotalItemCount limit because it would be too much of a hassle to enforce it here.
        //      that is because we can't just delete (=reduce count) items, nor can we deny setting a stack.
        //      maxItemTypes is self-enforced by not adding new slots here in any case

        var key = ItemStackKey.of(stack);

        var existingSlot = this.keyToSlot.get(key);

        //If the item type already exists in another slot, we cannot put it in this slot.
        //the handler has a strict one slot per type limit.
        if (existingSlot != null && existingSlot != slot)
            return;

        //if the item type does not exist already AND the slot is empty AND the slot is within the current slot amount, we can place it
        //if it is a higher slot we cannot add it, because otherwise we would grow the handler and create problems with iterators
        if (existingSlot == null && //no existing slot for the given type
                this.keyToSlot.inverse().get(slot) == null && //target slot is empty
                slot < this.nextSlotIndex && //target slot is within our current size
                !stack.isEmpty()) { //stack is not empty -> if it is we should not create a new slot, because no slot indicates empty

            this.keyToSlot.put(key, slot); //we do not call addToSlot() as that might choose another index from emptySlots
            this.emptySlots.remove((Integer) slot); //remove from emptyslots

            this.keyToCountMap.put(key, stack.getCount());

            this.totalItemCount += stack.getCount();

            this.onContentsChanged(key);
        }
        //If it does exist and is the same slot, we just update the count
        else if (existingSlot != null && existingSlot == slot) {
            var existing = this.keyToCountMap.getOrDefault(key, 0);
            this.totalItemCount -= existing;

            if (stack.isEmpty()) { //Setting an empty stack equals removal
                this.keyToCountMap.removeInt(key);
                this.removeFromSlots(key);
            } else {
                this.keyToCountMap.put(key, stack.getCount());
                this.totalItemCount += stack.getCount();
            }

            this.onContentsChanged(key);
        }
    }

    @Override
    public int getSlots() {
        //report at least one empty slot to allow inserting items, otherwise some handler code might not work.
        return this.nextSlotIndex + 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        var key = this.keyToSlot.inverse().get(slot);
        return key != null ? key.stack() : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        //we don't need to deal with slots as they are only a "view". Because item stacks are they underlying storage key we can just forward.
        return this.insertItem(stack, simulate);
    }

    /**
     * <p>
     * Inserts an ItemStack into the fitting slot and return the remainder.
     * The ItemStack <em>should not</em> be modified in this function!
     * </p>
     * Note: This behaviour is subtly different from {@link IFluidHandler#fill(FluidStack, IFluidHandler.FluidAction)}
     *
     * @param stack    ItemStack to insert. This must not be modified by the item handler.
     * @param simulate If true, the insertion is only simulated
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return an empty ItemStack).
     * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     * The returned ItemStack can be safely modified after.
     **/
    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        var key = ItemStackKey.of(stack);

        if (!this.isItemValid(VIRTUAL_SLOT, key))
            return stack;

        int existing = this.keyToCountMap.getOrDefault(key, 0);

        int limit = this.getStackLimit(stack);

        if (existing > 0) {
            //used in the original insert item of neoforge itemstackhandler, but not needed as the itemstackkey ensures stackability
//            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
//                return stack;

            limit -= existing;
        }

        if (existing == 0) {
            //enforce max item types, if this type is not already present
            if (this.maxItemTypes != -1 && this.keyToCountMap.size() >= this.maxItemTypes)
                return stack;
        }

        //enforce max total item count
        limit = Math.min(limit, Math.toIntExact(this.maxTotalItemCount - this.totalItemCount));

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing <= 0) {
                this.keyToCountMap.put(key, reachedLimit ? limit : stack.getCount());
                this.addToSlots(key);

            } else {
                this.keyToCountMap.put(key, existing + (reachedLimit ? limit : stack.getCount()));
            }
            this.totalItemCount += reachedLimit ? limit : stack.getCount();
            this.onContentsChanged(key);
        }

        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        this.validateSlotIndex(slot);

        var key = this.keyToSlot.inverse().get(slot);
        if (key == null)
            return ItemStack.EMPTY;

        var existing = this.keyToCountMap.getInt(key);

        if (existing <= 0)
            return ItemStack.EMPTY;

//        int toExtract = Math.min(amount, key.stack().getMaxStackSize());
        //extraction is not limited to stack sizes
        int toExtract = amount;

        if (existing <= toExtract) {
            if (!simulate) {
                this.keyToCountMap.removeInt(key);
                this.totalItemCount -= existing;

                this.removeFromSlots(key);

                this.onContentsChanged(key);

                return key.stack().copyWithCount(existing);
            } else {
                return key.stack().copyWithCount(existing);
            }
        } else {
            if (!simulate) {
                this.keyToCountMap.put(key, existing - toExtract);
                this.totalItemCount -= toExtract;
                this.onContentsChanged(key);
            }
            return key.stack().copyWithCount(toExtract);
        }
    }

    public @NotNull ItemStack extractItem(ItemStackKey key, int amount, boolean simulate) {
        var slot = this.keyToSlot.get(key);

        if (slot == null)
            return ItemStack.EMPTY;

        return this.extractItem(slot, amount, simulate);
    }

    public @NotNull ItemStack extractItem(ItemStack stack, int amount, boolean simulate) {
        var key = ItemStackKey.of(stack);
        return this.extractItem(key, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.isItemValid(slot, ItemStackKey.of(stack));
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStackKey key) {
        return true; //just like ItemStackHandler
    }

    /**
     * Add a new key to the slot view.
     * Only call if the key is not already present in keyToCountMap.
     */
    protected void addToSlots(ItemStackKey key) {
        if (!this.emptySlots.empty()) {
            var index = this.emptySlots.pop();
            this.keyToSlot.put(key, index);
        } else {
            this.keyToSlot.put(key, this.nextSlotIndex++);
        }
    }

    /**
     * Remove a key from the slot view.
     * Only call if the key is entirely removed from keyToCountMap.
     */
    protected void removeFromSlots(ItemStackKey key) {
        var index = this.keyToSlot.get(key);
        if (index != null) {
            this.keyToSlot.remove(key);
            this.emptySlots.push(index);
            //Note: We intentionally do not modify nextSlot here to avoid shrinking the handler.
        }
    }

    protected int getStackLimit(@NotNull ItemStack stack) {
        return this.getSlotLimit(VIRTUAL_SLOT);
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || !this.fitsInMaxSlots(slot))
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + (this.maxItemTypes != -1 ? this.maxItemTypes : Integer.MAX_VALUE) + ")");
    }

    protected boolean fitsInMaxSlots(int slot) {
        return this.maxItemTypes != -1 && slot < this.maxItemTypes;
    }

    protected void onContentsChanged(ItemStackKey key) {
    }
}
