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

import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class RitualCatcherBlockEntity extends NetworkedBlockEntity implements Clearable {

    public long lastChangeTime;
    public ItemStacksResourceHandler itemStackHandler = new ItemStacksResourceHandler(1) {

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.is(OccultismItems.FLAME_AUTOMATION.get());
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            Level level = RitualCatcherBlockEntity.this.level;
            if (level != null && !level.isClientSide()) {
                RitualCatcherBlockEntity.this.lastChangeTime = level.getGameTime();
                RitualCatcherBlockEntity.this.setChanged();
                RitualCatcherBlockEntity.this.markNetworkDirty();
            }
        }
    };

    public RitualCatcherBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.RITUAL_CATCHER.get(), worldPos, state);
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
