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

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.crafting.recipe.input.ItemHandlerRecipeInput;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DimensionalMineshaftBlockEntity extends NetworkedBlockEntity implements MenuProvider {

    public static final ResourceKey<Enchantment> EVILCRAFT_UNUSING_ENCHANTEMENT = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("evilcraft:unusing"));
    public static final String MAX_MINING_TIME_TAG = "maxMiningTime";
    public static final int DEFAULT_MAX_MINING_TIME = 400;
    public static int DEFAULT_ROLLS_PER_OPERATION = 1;
    public static String ROLLS_PER_OPERATION_TAG = "rollsPerOperation";
    public ItemStackHandler inputHandler = new ItemStackHandler(1) {

        @Override
        protected void onContentsChanged(int slot) {
            DimensionalMineshaftBlockEntity.this.setChanged();
        }

    };

    public ItemStackHandler outputHandler = new ItemStackHandler(9) {

        @Override
        protected void onContentsChanged(int slot) {
            DimensionalMineshaftBlockEntity.this.setChanged();
        }

    };

    public CombinedInvWrapper combinedHandler = new CombinedInvWrapper(this.inputHandler, this.outputHandler);
    public int miningTime;
    public int maxMiningTime = 0;
    public int rollsPerOperation = 0;
    protected Item currentInputType;
    protected List<WeightedRecipeResult> possibleResults;

    public DimensionalMineshaftBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.DIMENSIONAL_MINESHAFT.get(), worldPos, state);
    }

    //region Static Methods
    public static void forceInitStackNBT(ItemStack stack, ServerLevel level) {
        stack.getItem().onCraftedBy(stack, level, FakePlayerFactory.getMinecraft(level));
    }

    public static int getMaxMiningTime(ItemStack stack) {
        return stack.getOrDefault(OccultismDataComponents.MAX_MINING_TIME, DEFAULT_MAX_MINING_TIME);
    }

    public static int getRollsPerOperation(ItemStack stack) {
        return stack.getOrDefault(OccultismDataComponents.ROLLS_PER_OPERATION, DEFAULT_ROLLS_PER_OPERATION);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType()).getPath());
    }


    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        this.inputHandler.deserializeNBT(provider, compound.getCompound("inputHandler"));
        this.outputHandler.deserializeNBT(provider, compound.getCompound("outputHandler"));
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        compound.put("inputHandler", this.inputHandler.serializeNBT(provider));
        compound.put("outputHandler", this.outputHandler.serializeNBT(provider));
        super.saveAdditional(compound, provider);
    }

    @Override
    public void loadNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadNetwork(compound, provider);
        this.miningTime = compound.getInt("miningTime");
        this.maxMiningTime = compound.getInt("maxMiningTime");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putInt("miningTime", this.miningTime);
        compound.putInt("maxMiningTime", this.maxMiningTime);
        return super.saveNetwork(compound, provider);
    }


    public void tick() {
        if(this.level.hasNeighborSignal(this.getBlockPos())){
            this.miningTime = 0;
            return;
        }
        if (!this.level.isClientSide) {
            ItemStack input = this.inputHandler.getStackInSlot(0);

            //handle unusing enchantment from evilcraft, see https://github.com/klikli-dev/occultism/issues/909
            if (input.getMaxDamage() - input.getDamageValue() < 6 &&
                    input.isEnchanted() &&
                    this.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EVILCRAFT_UNUSING_ENCHANTEMENT).isPresent() &&
                    input.getEnchantmentLevel(this.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EVILCRAFT_UNUSING_ENCHANTEMENT).get()) > 0) {
                this.miningTime = 0;
                return;
            }

            boolean dirty = false;
            if (this.miningTime > 0) {

                int efficiency = 0;
                if (Occultism.SERVER_CONFIG.itemSettings.minerEfficiency.getAsBoolean()) {
                    efficiency = input.isEnchanted() ? input.getEnchantmentLevel(this.level.holderOrThrow(Enchantments.EFFICIENCY)) : 0;

                    if (efficiency > 0) {
                        int extra1 = this.level.random.nextIntBetweenInclusive(0, efficiency);
                        int extra2 = this.level.random.nextIntBetweenInclusive(0, efficiency);
                        efficiency = Math.min(extra1, extra2);
                    }
                }

                for (int i = 0; i < 1 + efficiency; i++) {
                    this.miningTime--;
                }

                if (this.miningTime <= 0 && !this.level.isClientSide) {
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

    public void mine() {
        if (this.level == null)
            return;

        if (this.possibleResults == null) {
            List<RecipeHolder<MinerRecipe>> recipes = this.level.getRecipeManager()
                    .getRecipesFor(OccultismRecipes.MINER_TYPE.get(),
                            new ItemHandlerRecipeInput(this.inputHandler), this.level);
            if (recipes == null || recipes.size() == 0) {
                this.possibleResults = new ArrayList<>();
            } else {
                this.possibleResults = recipes.stream().map(r -> r.value().getWeightedResult()).collect(Collectors.toList());
            }
        }

        if (this.possibleResults.size() == 0)
            return;

        ItemStack input = this.inputHandler.getStackInSlot(0);

        int fortune = 0;
        if (Occultism.SERVER_CONFIG.itemSettings.minerFortune.getAsBoolean()) {
            fortune = input.isEnchanted() ? input.getEnchantmentLevel(this.level.holderOrThrow(Enchantments.FORTUNE)) : 0;

            if (fortune > 0) {
                int extra1 = this.level.random.nextIntBetweenInclusive(0, fortune);
                int extra2 = this.level.random.nextIntBetweenInclusive(0, fortune);
                int extra3 = this.level.random.nextIntBetweenInclusive(0, fortune);
                fortune = Math.min(extra1, Math.min(extra2, extra3));
            }
        }

        for (int i = 0; i < this.rollsPerOperation + fortune; i++) {
            var result = WeightedRandom.getRandomItem(this.level.random, this.possibleResults);
            //Important: copy the result, don't use it raw!
            result.ifPresent(r -> {
                ItemHandlerHelper.insertItemStacked(this.outputHandler, r.getStack().copy(), false);
            });
            //If there is no space, we simply continue. The otherworld miner spirit keeps working,
            // but the miner block entity simply discards the results
        }

        //damage the item and move to output before breaking
        input.hurtAndBreak(1, (ServerLevel) this.level, (LivingEntity) null, (item) -> {});
        if (Occultism.SERVER_CONFIG.itemSettings.minerOutputBeforeBreak.getAsBoolean() && input.getMaxDamage()-1 == input.getDamageValue()){
            var minerCopy = input.copy();
            input.hurtAndBreak(100, (ServerLevel) this.level, (LivingEntity) null, (item) -> {});
            ItemHandlerHelper.insertItemStacked(this.outputHandler, minerCopy,false);
        }
    }

    public int getRedstoneSignal() {
        ItemStack lamp = this.inputHandler.getStackInSlot(0);
        int signalI = 0;
        int signalO = 0;
        if (!lamp.isEmpty()) {
            signalI = (int) (10 * ((float) lamp.getItem().getDamage(lamp) / (lamp.getMaxDamage() - 1)));
            signalO++;
        }
        for (int i = 0; i < 9; i++) {
            if (!this.outputHandler.getStackInSlot(i).isEmpty())
                signalO++;
        }
        return Math.max(signalI, signalO);
    }
}
