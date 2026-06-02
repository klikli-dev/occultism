/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.common.blockentity;

import com.klikli_dev.occultism.common.block.SpiritFireBlock;
import com.klikli_dev.occultism.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SacrificialBowlBlockEntity extends NetworkedBlockEntity implements Clearable {

    public long lastChangeTime;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(1) {

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(
                int slot) {

            Level level = SacrificialBowlBlockEntity.this.level;
            if (level != null && !level.isClientSide) {
                Block blockBellow = level.getBlockState(SacrificialBowlBlockEntity.this.getBlockPos().below()).getBlock();
                if (!(SacrificialBowlBlockEntity.this instanceof GoldenSacrificialBowlBlockEntity)
                        && (blockBellow instanceof SpiritFireBlock || blockBellow == OccultismBlocks.SPIRIT_CAMPFIRE.get())) {
                    var recipeInput = new SingleRecipeInput(this.getStackInSlot(0));
                    var recipe = level.getRecipeManager().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), recipeInput, level);
                    if (recipe.isPresent() && !recipeInput.item().is(OccultismBlocks.OTHERFLOWER.asItem())) {
                        super.extractItem(0, 1, false);
                        ItemStack result = recipe.get().value().assemble(recipeInput, level.registryAccess());
                        super.setStackInSlot(0, result);
                        level.playSound(null, SacrificialBowlBlockEntity.this.getBlockPos(), OccultismSounds.POOF.get(), SoundSource.BLOCKS, 1, 1);
                    }
                }

                SacrificialBowlBlockEntity.this.lastChangeTime = level.getGameTime();
                SacrificialBowlBlockEntity.this.setChanged();
                SacrificialBowlBlockEntity.this.markNetworkDirty();
            }
        }

    };
    protected boolean initialized = false;

    public SacrificialBowlBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.SACRIFICIAL_BOWL.get(), worldPos, state);
    }

    public SacrificialBowlBlockEntity(BlockEntityType<?> BlockEntityTypeIn, BlockPos worldPos, BlockState state) {
        super(BlockEntityTypeIn, worldPos, state);
    }


    @Override
    public void loadNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        this.itemStackHandler.deserializeNBT(provider, compound.getCompound("inventory"));
        this.lastChangeTime = compound.getLong("lastChangeTime");
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {

        compound.put("inventory", this.itemStackHandler.serializeNBT(provider));
        compound.putLong("lastChangeTime", this.lastChangeTime);

        //BlockEntity#saveAdditional code -> we must skip networked blockentity to split network vs save
        //super would call saveNetwork which would delete data for nbt heavy items
//        if (this.customPersistentData != null) {
//            tag.put("NeoForgeData", this.customPersistentData.copy());
//        }

        CompoundTag attachmentsTag = this.serializeAttachments(provider);
        if (attachmentsTag != null) {
            compound.put("neoforge:attachments", attachmentsTag);
        }
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        compound.put("inventory", this.serializeInventoryForNetwork(provider));
        compound.putLong("lastChangeTime", this.lastChangeTime);
        return compound;
    }

    public CompoundTag serializeInventoryForNetwork(HolderLookup.Provider provider) {
        ListTag nbtTagList = new ListTag();

        for (int i = 0; i < this.itemStackHandler.getSlots(); ++i) {
            var stack = this.itemStackHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == OccultismBlocks.STORAGE_CONTROLLER.asItem() ||
                        stack.getItem() == OccultismBlocks.STORAGE_CONTROLLER_DARK.asItem() ||
                        stack.getItem() == OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.asItem() ||
                        stack.getItem() == OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.asItem()
                ) {
                    stack = stack.copy();
                    stack.set(OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get(), CustomData.EMPTY);
                }

                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                nbtTagList.add(stack.save(provider, itemTag));
            }
        }

        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", this.itemStackHandler.getSlots());
        return nbt;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
            this.itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
