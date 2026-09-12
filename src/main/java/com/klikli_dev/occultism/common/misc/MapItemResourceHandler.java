package com.klikli_dev.occultism.common.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.misc.MapItemResourceHandler.Snapshot;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapItemResourceHandler extends SnapshotJournal<Snapshot> implements ResourceHandler<ItemResource>, IMapItemResourceHandler {
    protected static final int VIRTUAL_SLOT = -1;

    private static final Codec<Map<ItemStackKey, Integer>> MAP_CODEC = Codec.list(Codec.pair(ItemStackKey.CODEC.fieldOf("itemStackkey").codec(), Codec.INT.fieldOf("int").codec()))
            .xmap(
                    list -> list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)),
                    map -> map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue())).collect(Collectors.toList())
            );

    public static final Codec<MapItemResourceHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MAP_CODEC.fieldOf("keyToCountMap").forGetter(handler -> handler.serializeKeyToCountMap()),
            MAP_CODEC.fieldOf("keyToSlot").forGetter(handler -> handler.serializeKeyToSlotMap()),
            Codec.INT.listOf().fieldOf("emptySlots").forGetter(handler -> handler.emptySlots.intStream().boxed().toList()),
            Codec.INT.fieldOf("nextSlot").forGetter(handler -> handler.nextSlotIndex),
            Codec.INT.fieldOf("maxSlots").forGetter(handler -> handler.maxItemTypes),
            Codec.LONG.fieldOf("totalItemCount").forGetter(handler -> handler.totalItemCount),
            Codec.LONG.fieldOf("maxTotalItemCount").forGetter(handler -> handler.maxTotalItemCount)
    ).apply(instance, (keyToCountMap, keyToSlot, emptySlots, nextSlot, maxSlots, totalItemCount, maxTotalItemCount) -> new MapItemResourceHandler(
            toResourceCountMap(keyToCountMap),
            toResourceSlotMap(keyToSlot),
            toIntArrayList(emptySlots),
            nextSlot,
            maxSlots,
            totalItemCount,
            maxTotalItemCount
    )));

    protected Object2IntOpenHashMap<ItemResource> resourceToCountMap;
    protected Multimap<Item, ItemResource> itemToVariantsCache = HashMultimap.create();
    protected Object2IntOpenHashMap<ItemResource> resourceToSlot;
    protected Map<Integer, ItemResource> slotToResource;
    protected IntArrayList emptySlots;
    protected int nextSlotIndex;
    protected int maxItemTypes;
    protected long totalItemCount;
    protected long maxTotalItemCount;
    protected final List<Undo> undoLog = new ArrayList<>();
    protected int activeSnapshotCount;

    public MapItemResourceHandler() {
        this(-1, -1);
    }

    public MapItemResourceHandler(int maxItemTypes, long maxTotalItemCount) {
        this(new Object2IntOpenHashMap<>(), new Object2IntOpenHashMap<>(), new IntArrayList(), 0, maxItemTypes, 0, maxTotalItemCount);
    }

    public MapItemResourceHandler(Object2IntOpenHashMap<ItemResource> resourceToCountMap, Object2IntOpenHashMap<ItemResource> resourceToSlot, IntArrayList emptySlots, int nextSlotIndex, int maxItemTypes, long totalItemCount, long maxTotalItemCount) {
        this.resourceToCountMap = resourceToCountMap;
        this.resourceToSlot = resourceToSlot;
        this.resourceToSlot.defaultReturnValue(-1);
        this.slotToResource = new HashMap<>();
        resourceToSlot.object2IntEntrySet().forEach(entry -> this.slotToResource.put(entry.getIntValue(), entry.getKey()));
        this.emptySlots = new IntArrayList(emptySlots);
        this.nextSlotIndex = nextSlotIndex;
        this.maxItemTypes = maxItemTypes;
        this.totalItemCount = totalItemCount;
        this.maxTotalItemCount = maxTotalItemCount;
    }

    protected static Object2IntOpenHashMap<ItemResource> toResourceCountMap(Map<ItemStackKey, Integer> map) {
        Object2IntOpenHashMap<ItemResource> result = new Object2IntOpenHashMap<>();
        result.defaultReturnValue(0);
        map.forEach((key, value) -> result.put(ItemResource.of(key.stack()), value));
        return result;
    }

    protected static Object2IntOpenHashMap<ItemResource> toResourceSlotMap(Map<ItemStackKey, Integer> map) {
        Object2IntOpenHashMap<ItemResource> result = new Object2IntOpenHashMap<>();
        result.defaultReturnValue(-1);
        map.forEach((key, value) -> result.put(ItemResource.of(key.stack()), value));
        return result;
    }

    protected static IntArrayList toIntArrayList(List<Integer> list) {
        IntArrayList result = new IntArrayList(list.size());
        list.forEach(result::add);
        return result;
    }

    protected Map<ItemStackKey, Integer> serializeKeyToCountMap() {
        Map<ItemStackKey, Integer> result = new HashMap<>();
        this.resourceToCountMap.object2IntEntrySet().forEach(entry -> result.put(new ItemStackKey(entry.getKey().toStack()), entry.getIntValue()));
        return result;
    }

    protected Map<ItemStackKey, Integer> serializeKeyToSlotMap() {
        Map<ItemStackKey, Integer> result = new HashMap<>();
        this.resourceToSlot.object2IntEntrySet().forEach(entry -> result.put(new ItemStackKey(entry.getKey().toStack()), entry.getIntValue()));
        return result;
    }

    public Object2IntOpenHashMap<ItemResource> resourceToCountMap() {
        return this.resourceToCountMap;
    }

    public long totalItemCount() {
        return this.totalItemCount;
    }

    public int maxItemTypes() {
        return this.maxItemTypes;
    }

    public boolean hasMaxItemTypes() {
        return this.maxItemTypes != -1;
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
        return this.get(ItemResource.of(stack));
    }

    @Override
    public int get(ItemStackKey key) {
        return this.get(ItemResource.of(key.stack()));
    }

    public int get(ItemResource resource) {
        return this.resourceToCountMap.getOrDefault(resource, 0);
    }

    public CompoundTag serializeNBT(@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        ListTag keyToCountList = new ListTag();
        this.resourceToCountMap.forEach((resource, value) -> {
            try {
                CompoundTag entryTag = new CompoundTag();
                entryTag.put("itemStackkey", ItemStack.OPTIONAL_CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), resource.toStack()).getOrThrow());
                entryTag.putInt("int", value);
                keyToCountList.add(entryTag);
            } catch (Exception e) {
                Occultism.LOGGER.error("Failed to serialize ItemResource: {}", resource, e);
            }
        });
        nbt.put("keyToCountMap", keyToCountList);

        ListTag keyToSlotList = new ListTag();
        this.resourceToSlot.forEach((resource, slot) -> {
            try {
                CompoundTag entryTag = new CompoundTag();
                entryTag.put("itemStackkey", ItemStack.OPTIONAL_CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), resource.toStack()).getOrThrow());
                entryTag.putInt("int", slot);
                keyToSlotList.add(entryTag);
            } catch (Exception e) {
                Occultism.LOGGER.error("Failed to serialize ItemResource: {}", resource, e);
            }
        });
        nbt.put("keyToSlot", keyToSlotList);

        nbt.putIntArray("emptySlots", this.emptySlots.intStream().toArray());
        nbt.putInt("nextSlot", this.nextSlotIndex);
        nbt.putInt("maxSlots", this.maxItemTypes);
        nbt.putLong("totalItemCount", this.totalItemCount);
        nbt.putLong("maxTotalItemCount", this.maxTotalItemCount);
        return nbt;
    }

    public void deserializeNBT(@NotNull Provider provider, CompoundTag nbt) {
        ListTag keyToCountList = nbt.getListOrEmpty("keyToCountMap");
        this.resourceToCountMap = new Object2IntOpenHashMap<>();
        this.resourceToCountMap.defaultReturnValue(0);
        keyToCountList.forEach(tag -> {
            CompoundTag entryTag = (CompoundTag) tag;
            var stack = ItemStack.OPTIONAL_CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), entryTag.getCompoundOrEmpty("itemStackkey")).result().orElse(ItemStack.EMPTY);
            if (stack.isEmpty()) {
                return;
            }

            this.resourceToCountMap.put(ItemResource.of(stack), entryTag.getIntOr("int", 0));
        });

        ListTag keyToSlotList = nbt.getListOrEmpty("keyToSlot");
        this.resourceToSlot = new Object2IntOpenHashMap<>();
        this.resourceToSlot.defaultReturnValue(-1);
        this.slotToResource = new HashMap<>();
        keyToSlotList.forEach(tag -> {
            CompoundTag entryTag = (CompoundTag) tag;
            var stack = ItemStack.OPTIONAL_CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), entryTag.getCompoundOrEmpty("itemStackkey")).result().orElse(ItemStack.EMPTY);
            if (stack.isEmpty()) {
                return;
            }

            ItemResource resource = ItemResource.of(stack);
            int slot = entryTag.getIntOr("int", 0);
            this.resourceToSlot.put(resource, slot);
            this.slotToResource.put(slot, resource);
        });

        this.emptySlots = new IntArrayList(Arrays.stream(nbt.getIntArray("emptySlots").orElse(new int[0])).toArray());
        this.nextSlotIndex = nbt.getIntOr("nextSlot", 0);
        this.maxItemTypes = nbt.getIntOr("maxSlots", -1);
        this.totalItemCount = nbt.getLongOr("totalItemCount", 0L);
        this.maxTotalItemCount = nbt.getLongOr("maxTotalItemCount", -1L);
        this.undoLog.clear();
        this.itemToVariantsCache.clear();
    }

    @Override
    public int size() {
        if (!this.hasMaxItemTypes()) {
            return this.nextSlotIndex + 1;
        }

        return Math.min(this.maxItemTypes, this.nextSlotIndex + 1);
    }

    @Override
    public ItemResource getResource(int index) {
        ItemResource resource = this.slotToResource.get(index);
        return resource == null ? ItemResource.EMPTY : resource;
    }

    @Override
    public long getAmountAsLong(int index) {
        return this.resourceToCountMap.getOrDefault(this.getResource(index), 0);
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        if (!resource.isEmpty() && !this.isValid(index, resource)) {
            return 0;
        }

        return this.getSlotLimit(index, resource);
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return this.isItemValid(index, resource);
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        this.validateInsertIndex(index);
        return this.insert(resource, amount, transaction);
    }

    @Override
    public int insert(ItemResource resource, int amount, TransactionContext transaction) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        if (amount == 0) {
            return 0;
        }

        return this.insertInternal(resource, amount, transaction);
    }

    protected int insertInternal(ItemResource resource, int amount, TransactionContext transaction) {
        if (!this.isItemValid(VIRTUAL_SLOT, resource)) {
            return 0;
        }

        int existing = this.resourceToCountMap.getOrDefault(resource, 0);
        int limit = this.getStackLimit(resource);

        if (existing > 0) {
            limit -= existing;
        }

        if (existing == 0 && this.maxItemTypes != -1 && this.resourceToCountMap.size() >= this.maxItemTypes) {
            return 0;
        }

        if (this.maxTotalItemCount != -1) {
            limit = Math.min(limit, Math.toIntExact(this.maxTotalItemCount - this.totalItemCount));
        }

        if (limit <= 0) {
            return 0;
        }

        int inserted = Math.min(amount, limit);
        if (inserted <= 0) {
            return 0;
        }

        this.updateSnapshots(transaction);
        if (existing <= 0) {
            this.setCount(resource, inserted);
            this.addToSlots(resource);
        } else {
            this.setCount(resource, existing + inserted);
        }
        this.setTotalItemCount(this.totalItemCount + inserted);
        return inserted;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        this.validateSlotIndex(index);
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        if (amount == 0) {
            return 0;
        }

        ItemResource current = this.getResource(index);
        if (current.isEmpty() || !current.equals(resource)) {
            return 0;
        }

        return this.extractInternal(resource, amount, transaction);
    }

    @Override
    public int extract(ItemResource resource, int amount, TransactionContext transaction) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        if (amount == 0) {
            return 0;
        }

        return this.extractInternal(resource, amount, transaction);
    }

    protected int extractInternal(ItemResource resource, int amount, TransactionContext transaction) {
        int existing = this.resourceToCountMap.getInt(resource);
        if (existing <= 0) {
            return 0;
        }

        int extracted = Math.min(existing, amount);
        if (extracted <= 0) {
            return 0;
        }

        this.updateSnapshots(transaction);
        if (existing <= extracted) {
            this.setCount(resource, 0);
            this.setTotalItemCount(this.totalItemCount - existing);
            this.removeFromSlots(resource);
            return existing;
        }

        this.setCount(resource, existing - extracted);
        this.setTotalItemCount(this.totalItemCount - extracted);
        return extracted;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        this.setResourceInSlot(slot, ItemResource.of(stack), stack.getCount());
    }

    @Override
    public void setResourceInSlot(int slot, @NotNull ItemResource resource, int amount) {
        this.validateSlotIndex(slot);
        if (resource.isEmpty() || amount <= 0) {
            ItemResource previous = this.slotToResource.get(slot);
            if (previous != null) {
                int existing = this.resourceToCountMap.removeInt(previous);
                this.totalItemCount -= existing;
                this.removeFromSlots(previous);
                this.onContentsChanged(previous);
            }
            return;
        }

        int existingSlot = this.resourceToSlot.getOrDefault(resource, -1);
        if (existingSlot != -1 && existingSlot != slot) {
            return;
        }

        ItemResource previous = this.slotToResource.get(slot);
        if (previous != null && !previous.equals(resource)) {
            int existing = this.resourceToCountMap.removeInt(previous);
            this.totalItemCount -= existing;
            this.removeFromSlots(previous);
            this.onContentsChanged(previous);
        }

        if (existingSlot == -1 && previous == null && slot >= this.nextSlotIndex) {
            return;
        }

        if (existingSlot == -1) {
            this.resourceToSlot.put(resource, slot);
            this.slotToResource.put(slot, resource);
            this.emptySlots.rem(slot);
            if (slot == this.nextSlotIndex) {
                this.nextSlotIndex++;
            }
        }

        int oldCount = this.resourceToCountMap.getOrDefault(resource, 0);
        this.totalItemCount -= oldCount;
        this.resourceToCountMap.put(resource, amount);
        this.totalItemCount += amount;
        this.onContentsChanged(resource);
    }

    @Override
    public int getSlots() {
        return this.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return ItemUtil.getStack(this, slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return ItemTransferUtil.insertItem(this, slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        return ItemTransferUtil.insertItem(this, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemTransferUtil.extractItem(this, slot, amount, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(@NotNull ItemStackKey key, int amount, boolean simulate) {
        return this.extractItem(ItemResource.of(key.stack()), amount, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(@NotNull ItemStack stack, int amount, boolean simulate) {
        return this.extractItem(ItemResource.of(stack), amount, simulate);
    }

    public @NotNull ItemStack extractItem(@NotNull ItemResource resource, int amount, boolean simulate) {
        return ItemTransferUtil.extractItem(this, resource, amount, simulate);
    }

    @Override
    public @NotNull ItemStack extractItemIgnoreComponents(@NotNull ItemStack stack, int amount, boolean simulate) {
        Item item = stack.getItem();
        if (!this.itemToVariantsCache.containsKey(item)) {
            this.buildItemToVariantsCacheFor(item);
        }

        for (var resource : this.itemToVariantsCache.get(item)) {
            var extracted = this.extractItem(resource, amount, true);
            if (!extracted.isEmpty()) {
                return this.extractItem(resource, amount, simulate);
            }
        }

        return ItemStack.EMPTY;
    }

    protected void buildItemToVariantsCacheFor(Item item) {
        this.resourceToCountMap.keySet().stream().filter(resource -> resource.getItem() == item).forEach(resource -> this.itemToVariantsCache.put(item, resource));
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.getSlotLimit(slot, ItemResource.EMPTY);
    }

    protected int getSlotLimit(int slot, ItemResource resource) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.isItemValid(slot, ItemResource.of(stack));
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStackKey key) {
        return this.isItemValid(slot, ItemResource.of(key.stack()));
    }

    public boolean isItemValid(int slot, @NotNull ItemResource resource) {
        return true;
    }

    protected void addToSlots(ItemResource resource) {
        int index;
        if (!this.emptySlots.isEmpty()) {
            index = this.emptySlots.removeInt(this.emptySlots.size() - 1);
            int reusedSlot = index;
            this.recordUndo(() -> this.emptySlots.add(reusedSlot));
        } else {
            int previousNextSlotIndex = this.nextSlotIndex;
            this.recordUndo(() -> this.nextSlotIndex = previousNextSlotIndex);
            index = this.nextSlotIndex++;
        }

        int slot = index;
        int previousSlot = this.resourceToSlot.getInt(resource);
        if (previousSlot >= 0) {
            this.recordUndo(() -> this.resourceToSlot.put(resource, previousSlot));
        } else {
            this.recordUndo(() -> this.resourceToSlot.removeInt(resource));
        }
        this.resourceToSlot.put(resource, slot);

        ItemResource previousResource = this.slotToResource.get(slot);
        if (previousResource != null) {
            this.recordUndo(() -> this.slotToResource.put(slot, previousResource));
        } else {
            this.recordUndo(() -> this.slotToResource.remove(slot));
        }
        this.slotToResource.put(slot, resource);

        if (this.itemToVariantsCache.containsKey(resource.getItem())) {
            this.itemToVariantsCache.put(resource.getItem(), resource);
        }
    }

    protected void removeFromSlots(ItemResource resource) {
        int index = this.resourceToSlot.getInt(resource);
        if (index != -1) {
            this.recordUndo(() -> this.resourceToSlot.put(resource, index));
            this.resourceToSlot.removeInt(resource);

            ItemResource previousResource = this.slotToResource.get(index);
            if (previousResource != null) {
                this.recordUndo(() -> this.slotToResource.put(index, previousResource));
            } else {
                this.recordUndo(() -> this.slotToResource.remove(index));
            }
            this.slotToResource.remove(index);

            this.recordUndo(() -> this.emptySlots.rem(index));
            this.emptySlots.add(index);
        }

        this.itemToVariantsCache.remove(resource.getItem(), resource);
    }

    protected int getStackLimit(@NotNull ItemResource resource) {
        return this.getSlotLimit(VIRTUAL_SLOT, resource);
    }

    protected void validateInsertIndex(int slot) {
        if (slot < 0) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + (this.maxItemTypes != -1 ? this.maxItemTypes : Integer.MAX_VALUE) + ")");
        }

        if (this.hasMaxItemTypes() && slot >= this.maxItemTypes) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.maxItemTypes + ")");
        }
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.size()) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.size() + ")");
        }
    }

    protected void onContentsChanged(ItemResource resource) {
    }

    @Override
    protected Snapshot createSnapshot() {
        this.activeSnapshotCount++;
        return new Snapshot(this.undoLog.size());
    }

    @Override
    protected void releaseSnapshot(Snapshot snapshot) {
        this.activeSnapshotCount--;
    }

    @Override
    protected void revertToSnapshot(Snapshot snapshot) {
        for (int i = this.undoLog.size() - 1; i >= snapshot.undoMark(); i--) {
            this.undoLog.get(i).action().run();
        }
        this.undoLog.subList(snapshot.undoMark(), this.undoLog.size()).clear();
        this.itemToVariantsCache.clear();
    }

    @Override
    protected void onRootCommit(Snapshot originalState) {
        Set<ItemResource> changedResources = new HashSet<>();
        for (int i = originalState.undoMark(); i < this.undoLog.size(); i++) {
            ItemResource changedResource = this.undoLog.get(i).changedResource();
            if (changedResource != null) {
                changedResources.add(changedResource);
            }
        }
        this.undoLog.clear();
        changedResources.forEach(this::onContentsChanged);
    }

    protected void recordUndo(Runnable action) {
        this.recordUndo(null, action);
    }

    protected void recordUndo(ItemResource changedResource, Runnable action) {
        if (this.activeSnapshotCount == 0) {
            return;
        }

        this.undoLog.add(new Undo(changedResource, action));
    }

    protected void setCount(ItemResource resource, int count) {
        int previous = this.resourceToCountMap.getInt(resource);
        if (previous == count) {
            return;
        }

        if (previous == 0) {
            this.recordUndo(resource, () -> this.resourceToCountMap.removeInt(resource));
        } else {
            this.recordUndo(resource, () -> this.resourceToCountMap.put(resource, previous));
        }

        if (count == 0) {
            this.resourceToCountMap.removeInt(resource);
        } else {
            this.resourceToCountMap.put(resource, count);
        }
    }

    protected void setTotalItemCount(long totalItemCount) {
        long previous = this.totalItemCount;
        if (previous == totalItemCount) {
            return;
        }

        this.recordUndo(() -> this.totalItemCount = previous);
        this.totalItemCount = totalItemCount;
    }

    protected record Snapshot(int undoMark) {
    }

    protected record Undo(ItemResource changedResource, Runnable action) {
    }
}
