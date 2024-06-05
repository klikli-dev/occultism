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

package com.klikli_dev.occultism.api.common.data;

import com.klikli_dev.occultism.util.BlockEntityUtil;
import com.klikli_dev.occultism.util.OccultismExtraStreamCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.Optional;

public class MachineReference implements INBTSerializable<CompoundTag> {
    public static final Codec<MachineReference> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GlobalBlockPos.CODEC.optionalFieldOf("extractGlobalPos").forGetter(m -> Optional.ofNullable(m.extractGlobalPos)),
            ResourceLocation.CODEC.optionalFieldOf("extractRegistryName").forGetter(m -> Optional.ofNullable(m.extractRegistryName)),
            Codec.BOOL.fieldOf("extractChunkLoaded").forGetter(m -> m.extractChunkLoaded),
            Direction.CODEC.fieldOf("extractFacing").forGetter(m -> m.extractFacing),
            GlobalBlockPos.CODEC.optionalFieldOf("insertGlobalPos").forGetter(m -> Optional.ofNullable(m.insertGlobalPos)),
            ResourceLocation.CODEC.optionalFieldOf("insertRegistryName").forGetter(m -> Optional.ofNullable(m.insertRegistryName)),
            Codec.BOOL.fieldOf("insertChunkLoaded").forGetter(m -> m.insertChunkLoaded),
            Direction.CODEC.fieldOf("insertFacing").forGetter(m -> m.insertFacing),
            Codec.STRING.fieldOf("customName").forGetter(m -> m.customName)
    ).apply(instance, MachineReference::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MachineReference> STREAM_CODEC = OccultismExtraStreamCodecs.composite(
            GlobalBlockPos.STREAM_CODEC,
            (m) -> m.extractGlobalPos,
            ResourceLocation.STREAM_CODEC,
            (m) -> m.extractRegistryName,
            ByteBufCodecs.BOOL,
            (m) -> m.extractChunkLoaded,
            Direction.STREAM_CODEC,
            (m) -> m.extractFacing,
            GlobalBlockPos.STREAM_CODEC,
            (m) -> m.insertGlobalPos,
            ResourceLocation.STREAM_CODEC,
            (m) -> m.insertRegistryName,
            ByteBufCodecs.BOOL,
            (m) -> m.insertChunkLoaded,
            Direction.STREAM_CODEC,
            (m) -> m.insertFacing,
            ByteBufCodecs.STRING_UTF8,
            (m) -> m.customName,
            MachineReference::new
    );

    //extract is a potentially separate output block entity
    public GlobalBlockPos extractGlobalPos;
    public ResourceLocation extractRegistryName;
    public boolean extractChunkLoaded;
    public Direction extractFacing = Direction.DOWN;
    //insert is the managed machine itself
    public GlobalBlockPos insertGlobalPos;
    public ResourceLocation insertRegistryName;
    public boolean insertChunkLoaded;
    public Direction insertFacing = Direction.UP;
    public String customName = "";
    protected ItemStack cachedExtractItemStack = ItemStack.EMPTY;
    protected Item cachedExtractItem = null;
    protected ItemStack cachedInsertItemStack = ItemStack.EMPTY;
    protected Item cachedInsertItem = null;

    public MachineReference() {

    }

    public MachineReference(GlobalBlockPos extractGlobalPos, ResourceLocation extractRegistryName, boolean extractChunkLoaded,
                            Direction extractFacing,
                            GlobalBlockPos insertGlobalPos, ResourceLocation insertRegistryName, boolean insertChunkLoaded,
                            Direction insertFacing) {
        this(extractGlobalPos, extractRegistryName, extractChunkLoaded, extractFacing, insertGlobalPos, insertRegistryName, insertChunkLoaded, insertFacing, "");
    }

    public MachineReference(Optional<GlobalBlockPos> extractGlobalPos, Optional<ResourceLocation> extractRegistryName, boolean extractChunkLoaded,
                            Direction extractFacing,
                            Optional<GlobalBlockPos> insertGlobalPos, Optional<ResourceLocation> insertRegistryName, boolean insertChunkLoaded,
                            Direction insertFacing,
                            String customName) {
        this(extractGlobalPos.orElse(null), extractRegistryName.orElse(null), extractChunkLoaded, extractFacing, insertGlobalPos.orElse(null), insertRegistryName.orElse(null), insertChunkLoaded, insertFacing, customName);
    }

    public MachineReference(GlobalBlockPos extractGlobalPos, ResourceLocation extractRegistryName, boolean extractChunkLoaded,
                            Direction extractFacing,
                            GlobalBlockPos insertGlobalPos, ResourceLocation insertRegistryName, boolean insertChunkLoaded,
                            Direction insertFacing,
                            String customName) {
        this.extractGlobalPos = extractGlobalPos;
        this.extractRegistryName = extractRegistryName;
        this.extractChunkLoaded = extractChunkLoaded;
        this.extractFacing = extractFacing;
        this.insertGlobalPos = insertGlobalPos;
        this.insertRegistryName = insertRegistryName;
        this.insertChunkLoaded = insertChunkLoaded;
        this.insertFacing = insertFacing;
        this.customName = customName;
    }

    /**
     * @param extractBlockEntity the block entity to extract from
     * @param insertBlockEntity  the block entity to insert into, this is the managed machine
     * @return
     */
    public static MachineReference from(BlockEntity extractBlockEntity, BlockEntity insertBlockEntity) {
        var extractPos = GlobalBlockPos.from(extractBlockEntity);
        BlockState extractState = extractBlockEntity.getLevel().getBlockState(extractPos.getPos());
        ItemStack extractItem = extractState.getBlock().getCloneItemStack(extractBlockEntity.getLevel(), extractPos.getPos(), extractState);
        boolean extractIsLoaded = extractBlockEntity.getLevel().isLoaded(extractPos.getPos());


        var insertPos = GlobalBlockPos.from(insertBlockEntity);
        BlockState insertState = extractBlockEntity.getLevel().getBlockState(insertPos.getPos());
        ItemStack insertItem = insertState.getBlock().getCloneItemStack(extractBlockEntity.getLevel(), insertPos.getPos(), insertState);
        boolean insertIsLoaded = insertBlockEntity.getLevel().isLoaded(insertPos.getPos());

        return new MachineReference(extractPos,
                BuiltInRegistries.ITEM.getKey(extractItem.getItem()), extractIsLoaded,
                Direction.DOWN,
                insertPos,
                BuiltInRegistries.ITEM.getKey(insertItem.getItem()), insertIsLoaded,
                Direction.UP
        );
    }

    public Item getExtractItem() {
        if (this.cachedExtractItem == null)
            this.cachedExtractItem = BuiltInRegistries.ITEM.get(this.extractRegistryName);
        return this.cachedExtractItem;
    }

    public ItemStack getExtractItemStack() {
        if (this.cachedExtractItemStack.isEmpty())
            this.cachedExtractItemStack = new ItemStack(this.getExtractItem());
        return this.cachedExtractItemStack;
    }

    public Item getInsertItem() {
        if (this.cachedInsertItem == null)
            this.cachedInsertItem = BuiltInRegistries.ITEM.get(this.insertRegistryName);
        return this.cachedInsertItem;
    }

    public ItemStack getInsertItemStack() {
        if (this.cachedInsertItemStack.isEmpty())
            this.cachedInsertItemStack = new ItemStack(this.getInsertItem());
        return this.cachedInsertItemStack;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return (CompoundTag) CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        var ref = CODEC.parse(NbtOps.INSTANCE, nbt).getOrThrow();
        this.extractGlobalPos = ref.extractGlobalPos;
        this.extractRegistryName = ref.extractRegistryName;
        this.extractChunkLoaded = ref.extractChunkLoaded;
        this.extractFacing = ref.extractFacing;
        this.insertGlobalPos = ref.insertGlobalPos;
        this.insertRegistryName = ref.insertRegistryName;
        this.insertChunkLoaded = ref.insertChunkLoaded;
        this.insertFacing = ref.insertFacing;
        this.customName = ref.customName;
    }

    public BlockEntity getExtractBlockEntity(Level level) {
        return BlockEntityUtil.get(level, this.extractGlobalPos);
    }

    public BlockEntity getInsertBlockEntity(Level level) {
        return BlockEntityUtil.get(level, this.insertGlobalPos);
    }

    public boolean isValidFor(Level level) {
        return this.getExtractBlockEntity(level) != null && this.getInsertBlockEntity(level) != null;
    }
}
