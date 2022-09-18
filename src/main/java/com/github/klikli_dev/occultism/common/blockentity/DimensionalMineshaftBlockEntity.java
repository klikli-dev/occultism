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

package com.github.klikli_dev.occultism.common.blockentity;

import com.github.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import com.github.klikli_dev.occultism.common.misc.WeightedIngredient;
import com.github.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DimensionalMineshaftBlockEntity extends NetworkedBlockEntity implements MenuProvider {

    //region Fields
    public static final String MAX_MINING_TIME_TAG = "maxMiningTime";
    public static final int DEFAULT_MAX_MINING_TIME = 400;
    public static int DEFAULT_ROLLS_PER_OPERATION = 1;
    public static String ROLLS_PER_OPERATION_TAG = "rollsPerOperation";
    public LazyOptional<ItemStackHandler> inputHandler = LazyOptional.of(() -> new ItemStackHandler(1) {
        //region Overrides
        @Override
        protected void onContentsChanged(int slot) {
            DimensionalMineshaftBlockEntity.this.setChanged();
        }
        //endregion Overrides
    });
    public LazyOptional<ItemStackHandler> outputHandler = LazyOptional.of(() -> new ItemStackHandler(9) {
        //region Overrides
        @Override
        protected void onContentsChanged(int slot) {
            DimensionalMineshaftBlockEntity.this.setChanged();
        }
        //endregion Overrides
    });
    public LazyOptional<CombinedInvWrapper> combinedHandler =
            LazyOptional
                    .of(() -> new CombinedInvWrapper(this.inputHandler.orElseThrow(ItemHandlerMissingException::new),
                            this.outputHandler.orElseThrow(ItemHandlerMissingException::new)));
    public int miningTime;
    public int maxMiningTime = 0;
    public int rollsPerOperation = 0;
    protected Item currentInputType;
    protected List<WeightedIngredient> possibleResults;

    //endregion Fields
    //region Initialization
    public DimensionalMineshaftBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismTiles.DIMENSIONAL_MINESHAFT.get(), worldPos, state);
    }
    //endregion Initialization

    //region Static Methods
    public static void forceInitStackNBT(ItemStack stack, ServerLevel level) {
        stack.getItem().onCraftedBy(stack, level, FakePlayerFactory.getMinecraft(level));
    }

    public static int getMaxMiningTime(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null)
            return 0;
        int time = tag.getInt(MAX_MINING_TIME_TAG);
        return time <= 0 ? DEFAULT_MAX_MINING_TIME : time;
    }

    public static int getRollsPerOperation(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null)
            return 0;
        int rolls = tag.getInt(ROLLS_PER_OPERATION_TAG);
        return rolls <= 0 ? DEFAULT_ROLLS_PER_OPERATION : rolls;
    }

    //region Overrides
    @Override
    public Component getDisplayName() {
        return Component.literal(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(this.getType()).getPath());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction direction) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (direction == null) {
                //null is full access for machines or similar.
                return this.combinedHandler.cast();
            } else if (direction == Direction.UP) {
                return this.inputHandler.cast();
            } else {
                return this.outputHandler.cast();
            }
        }
        return super.getCapability(cap, direction);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.inputHandler.ifPresent((handler) -> handler.deserializeNBT(compound.getCompound("inputHandler")));
        this.outputHandler.ifPresent((handler) -> handler.deserializeNBT(compound.getCompound("outputHandler")));
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        this.inputHandler.ifPresent(handler -> compound.put("inputHandler", handler.serializeNBT()));
        this.outputHandler.ifPresent(handler -> compound.put("outputHandler", handler.serializeNBT()));
        super.saveAdditional(compound);
    }

    @Override
    public void loadNetwork(CompoundTag compound) {
        super.loadNetwork(compound);
        this.miningTime = compound.getInt("miningTime");
        this.maxMiningTime = compound.getInt("maxMiningTime");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound) {
        compound.putInt("miningTime", this.miningTime);
        compound.putInt("maxMiningTime", this.maxMiningTime);
        return super.saveNetwork(compound);
    }
    //endregion Overrides

    @Override
    public void setRemoved() {
        this.inputHandler.invalidate();
        this.outputHandler.invalidate();
        super.setRemoved();
    }

    public void tick() {
        if (!this.level.isClientSide) {
            IItemHandler inputHandler = this.inputHandler.orElseThrow(ItemHandlerMissingException::new);
            ItemStack input = inputHandler.getStackInSlot(0);

            boolean dirty = false;
            if (this.miningTime > 0) {
                this.miningTime--;

                if (this.miningTime == 0 && !this.level.isClientSide) {
                    this.mine();
                }

                if (input.getItem() != this.currentInputType) {
                    //If the item was removed manually or consumed, set mining time to 0, which prevents further processing
                    //and sets up for starting the next operation in the next tick
                    this.miningTime = 0;

                    //if the item was used up or switched, we also delete our result cache
                    this.possibleResults = null;
                }
                if (this.miningTime % 10 == 0)
                    dirty = true;
            } else if (!input.isEmpty()) {
                //if we're done with the last mining job, and we have valid input, start the next one.
                this.currentInputType = input.getItem();
                //ensure nbt is initialized, fixes issues with spawned miner spirits
                forceInitStackNBT(input, (ServerLevel) this.level);
                this.maxMiningTime = getMaxMiningTime(input);
                this.rollsPerOperation = getRollsPerOperation(input);
                this.miningTime = this.maxMiningTime;
                dirty = true;
            }

            if (dirty) {
                this.markNetworkDirty();
            }
        } else {
            if (this.miningTime > 0 && this.level.getGameTime() % 10 == 0) {
                this.level.addParticle(ParticleTypes.PORTAL, this.worldPosition.getX() + 0.5f,
                        this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5f, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new DimensionalMineshaftContainer(id, playerInventory, this);
    }
    //endregion Static Methods

    //region Methods
    public void mine() {
        ItemStackHandler inputHandler = this.inputHandler.orElseThrow(ItemHandlerMissingException::new);
        ItemStackHandler outputHandler = this.outputHandler.orElseThrow(ItemHandlerMissingException::new);

        if (this.possibleResults == null) {
            List<MinerRecipe> recipes = this.level.getRecipeManager()
                    .getRecipesFor(OccultismRecipes.MINER_TYPE.get(),
                            new RecipeWrapper(inputHandler), this.level);
            if (recipes == null || recipes.size() == 0) {
                this.possibleResults = new ArrayList<>();
            } else {
                this.possibleResults = recipes.stream().map(r -> r.getWeightedOutput()).collect(Collectors.toList());
            }
        }

        if (this.possibleResults.size() == 0)
            return;

        for (int i = 0; i < this.rollsPerOperation; i++) {
            Optional<WeightedIngredient> result = WeightedRandom.getRandomItem(this.level.random, this.possibleResults);
            //Important: copy the result, don't use it raw!
            result.ifPresent(r -> {
                ItemHandlerHelper.insertItemStacked(outputHandler, r.getStack().copy(), false);
            });
            //If there is no space, we simply continue. The otherworld miner spirit keeps working,
            // but the miner block entity simply discards the results
        }

        //damage and eventually consume item.
        ItemStack input = inputHandler.getStackInSlot(0);
        if (input.hurt(1, this.level.random, null)) {
            input.shrink(1);
            input.setDamageValue(0);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inputHandler.invalidate();
        this.outputHandler.invalidate();
        this.combinedHandler.invalidate();
    }

    //endregion Methods
}
