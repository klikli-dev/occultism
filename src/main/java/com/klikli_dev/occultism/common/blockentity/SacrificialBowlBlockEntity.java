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
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.RootCommitJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class SacrificialBowlBlockEntity extends NetworkedBlockEntity implements Clearable {

    public long lastChangeTime;
    public ItemStacksResourceHandler itemStackHandler = new ItemStacksResourceHandler(1) {
        private final RootCommitJournal spiritFireRecipeJournal = new RootCommitJournal(this::processSpiritFireRecipe);

        @Override
        protected int getCapacity(int slot, ItemResource resource) {
            return 1;
        }

        @Override
        public int insert(int slot, ItemResource resource, int amount, TransactionContext transaction) {
            int inserted = super.insert(slot, resource, amount, transaction);
            if (inserted > 0 && this.shouldProcessSpiritFire()) {
                this.spiritFireRecipeJournal.updateSnapshots(transaction);
            }
            return inserted;
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            Level level = SacrificialBowlBlockEntity.this.level;
            if (level != null && !level.isClientSide()) {
                SacrificialBowlBlockEntity.this.lastChangeTime = level.getGameTime();
                SacrificialBowlBlockEntity.this.setChanged();
                SacrificialBowlBlockEntity.this.markNetworkDirty();
            }
        }

        private boolean shouldProcessSpiritFire() {
            Level level = SacrificialBowlBlockEntity.this.level;
            if (!(level instanceof ServerLevel)) {
                return false;
            }

            Block blockBelow = level.getBlockState(SacrificialBowlBlockEntity.this.getBlockPos().below()).getBlock();
            return blockBelow instanceof SpiritFireBlock || blockBelow == OccultismBlocks.SPIRIT_CAMPFIRE.get();
        }

        private void processSpiritFireRecipe() {
            if (!(SacrificialBowlBlockEntity.this.level instanceof ServerLevel serverLevel) || !this.shouldProcessSpiritFire()) {
                return;
            }

            ItemStack currentStack = this.getResource(0).toStack(this.getAmountAsInt(0));
            if (currentStack.isEmpty() || currentStack.is(OccultismBlocks.OTHERFLOWER.asItem())) {
                return;
            }

            var recipeInput = new SingleRecipeInput(currentStack);
            var recipe = serverLevel.recipeAccess().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), recipeInput, serverLevel);
            if (recipe.isEmpty()) {
                return;
            }

            ItemStack result = recipe.get().value().assemble(recipeInput);
            if (result.isEmpty()) {
                return;
            }

            boolean converted = false;
            try (var tx = Transaction.openRoot()) {
                ItemResource currentResource = this.getResource(0);
                int extracted = super.extract(0, currentResource, 1, tx);
                if (extracted <= 0) {
                    return;
                }

                int inserted = super.insert(0, ItemResource.of(result), 1, tx);
                if (inserted > 0) {
                    tx.commit();
                    converted = true;
                }
            }

            if (converted) {
                serverLevel.playSound(null, SacrificialBowlBlockEntity.this.getBlockPos(), OccultismSounds.POOF.get(), SoundSource.BLOCKS, 1, 1);
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
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        StorageUtil.dropInventoryItems(this);
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    public void loadNetwork(ValueInput input) {
        this.itemStackHandler.deserialize(input.childOrEmpty("inventory"));
        this.lastChangeTime = input.getLongOr("lastChangeTime", 0L);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        this.itemStackHandler.serialize(output.child("inventory"));
        output.putLong("lastChangeTime", this.lastChangeTime);

        //BlockEntity#saveAdditional code -> we must skip networked blockentity to split network vs save
//        if (this.customPersistentData != null) {
//            output.store("NeoForgeData", CompoundTag.CODEC, this.customPersistentData.copy());
//        }
        if (this.level != null) {
            this.level.registryAccess();
        } else {
            RegistryAccess.Frozen var10000 = RegistryAccess.EMPTY;
        }

        ValueOutput attachments = output.child("neoforge:attachments");
        this.serializeAttachments(attachments);
        if (attachments.isEmpty()) {
            output.discard("neoforge:attachments");
        }
    }

    @Override
    public void saveNetwork(ValueOutput output) {
        this.serializeInventoryForNetwork(output.child("inventory"));
        output.putLong("lastChangeTime", this.lastChangeTime);
    }

    public void serializeInventoryForNetwork(ValueOutput output) {
        //This avoids deep nested nbt issues if a storage controller is placed in a bowl.
        //MC should really not freak out about it, yet it does :(
        var copy = new ItemStacksResourceHandler(this.itemStackHandler.size());

        for (int i = 0; i < this.itemStackHandler.size(); ++i) {
            var resource = this.itemStackHandler.getResource(i);
            if(resource.is(OccultismBlocks.STORAGE_CONTROLLER.get()) ||
                    resource.is(OccultismBlocks.STORAGE_CONTROLLER_DARK.get()) ||
                    resource.is(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get()) ||
                    resource.is(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get())
            ) {
                var stack = resource.toStack(); //creates a copy, safe to modify
                stack.set(OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get(), CustomData.EMPTY);
                this.itemStackHandler.set(i, ItemResource.of(stack), this.itemStackHandler.getAmountAsInt(i));
            }
        }

        copy.serialize(output);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemStackHandler.size(); i++) {
            this.itemStackHandler.set(i, ItemResource.of(ItemStack.EMPTY), 0);
        }
    }
}
