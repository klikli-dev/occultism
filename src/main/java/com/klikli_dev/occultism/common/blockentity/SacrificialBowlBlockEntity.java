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
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class SacrificialBowlBlockEntity extends NetworkedBlockEntity {

    public long lastChangeTime;
    public ItemStacksResourceHandler itemStackHandler = new ItemStacksResourceHandler(1) {

        public int getSlotLimit(int slot) {
            return 1;
        }

        protected void onContentsChanged(int slot) {
            Level level = SacrificialBowlBlockEntity.this.level;
            if (level != null && !level.isClientSide()) {
                Block blockBellow = level.getBlockState(getBlockPos().below()).getBlock();
                if (!(SacrificialBowlBlockEntity.this instanceof GoldenSacrificialBowlBlockEntity)
                        && (blockBellow instanceof SpiritFireBlock || blockBellow == OccultismBlocks.SPIRIT_CAMPFIRE.get())) {

                    var recipeInput = new SingleRecipeInput(this.getResource(0).toStack());
                    var recipe = ((ServerLevel) level).recipeAccess().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), recipeInput, (ServerLevel) level);
                    if (recipe.isPresent() && !recipeInput.item().is(OccultismBlocks.OTHERFLOWER.asItem())) {
                        try (var tx = Transaction.openRoot()) {
                            this.extract(0, this.getResource(0), 1, tx);
                            ItemStack result = recipe.get().value().assemble(recipeInput);
                            this.insert(0, ItemResource.of(result), 1, tx);
                            tx.commit();
                        }
                        level.playSound(null, getBlockPos(), OccultismSounds.POOF.get(), SoundSource.BLOCKS, 1, 1);
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
    public void saveNetwork(ValueOutput output) {
        this.itemStackHandler.serialize(output.child("inventory"));
        output.putLong("lastChangeTime", this.lastChangeTime);
    }
}
