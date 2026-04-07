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
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
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
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.transfer.CombinedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.RootCommitJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DimensionalMineshaftBlockEntity extends NetworkedBlockEntity implements MenuProvider {

    private static final ResourceKey<Enchantment> EVILCRAFT_UNUSING_ENCHANTMENT = ResourceKey
            .create(Registries.ENCHANTMENT, Identifier.parse("evilcraft:unusing"));
    public static final String MAX_MINING_TIME_TAG = "maxMiningTime";
    public static final int DEFAULT_MAX_MINING_TIME = 400;
    public static int DEFAULT_ROLLS_PER_OPERATION = 1;
    public static String ROLLS_PER_OPERATION_TAG = "rollsPerOperation";

    // Internal handlers (mirrored behavior)
    public MineshaftInventory inputHandler = new MineshaftInventory(1, true);
    public MineshaftInventory outputHandler = new MineshaftInventory(9, false);

    // External capability-exposed handler (buffered/cached writes)
    public BufferedOutputHandler bufferedOutputHandler = new BufferedOutputHandler(outputHandler);

    // Combined handler now uses the buffered output to propagate safety
    public ResourceHandler<ItemResource> combinedHandler = new CombinedResourceHandler<>(this.inputHandler, this.bufferedOutputHandler);

    public boolean outputDirty = false;
    public int miningTime;
    public int maxMiningTime = 0;
    public int rollsPerOperation = 0;

    // Sync tracking
    public int lastSyncedMiningTime = -1;
    public int lastSyncedMaxMiningTime = -1;

    protected Item currentInputType;
    protected List<WeightedRecipeResult> possibleResults;

    //Enchantment cache
    private Holder<Enchantment> UNUSING;
    private Holder<Enchantment> EFFICIENCY;
    private Holder<Enchantment> FORTUNE;
    private Holder<Enchantment> SILK_TOUCH;
    private boolean cachedEnchantment = false;
    private final boolean bonusEfficiency = Occultism.SERVER_CONFIG.itemSettings.minerEfficiency.getAsBoolean();
    private final boolean bonusFortune = Occultism.SERVER_CONFIG.itemSettings.minerFortune.getAsBoolean();
    private final boolean bonusSilk = Occultism.SERVER_CONFIG.itemSettings.minerSilk.getAsBoolean();
    private final boolean saveMiner = Occultism.SERVER_CONFIG.itemSettings.minerOutputBeforeBreak.getAsBoolean();
    private ResourceHandler<ItemResource> handlerBelow = null;
    private BlockState cachedStateBelow = null;

    public DimensionalMineshaftBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.DIMENSIONAL_MINESHAFT.get(), worldPos, state);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        com.klikli_dev.occultism.util.StorageUtil.dropInventoryItems(this);
        super.preRemoveSideEffects(pos, state);
    }

    // region Inner Classes
    public class MineshaftInventory extends ItemStacksResourceHandler {
        private boolean isInput;
        public boolean suppressWrites = false;

        public MineshaftInventory(int size, boolean isInput) {
            super(size);
            this.isInput = isInput;
        }

        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return !this.isInput || mayPlace(resource.toStack());
        }

        public boolean mayPlace(ItemStack stack) {
            RecipeManager recipeManager = ((ServerLevel) DimensionalMineshaftBlockEntity.this.getLevel()).getServer().getRecipeManager();
            return RecipeUtil.isValidIngredient(recipeManager, OccultismRecipes.MINER_TYPE.get(), stack);
        }

        public @NotNull ItemStack getStackInSlot(int slot) {
            return this.getResource(slot).toStack(this.getAmountAsInt(slot));
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            DimensionalMineshaftBlockEntity.this.setChanged();
        }
    }

    public class BufferedOutputHandler implements ResourceHandler<ItemResource> {
        private final MineshaftInventory internal;
        private final RootCommitJournal outputDirtyJournal = new RootCommitJournal(() -> DimensionalMineshaftBlockEntity.this.outputDirty = true);

        public BufferedOutputHandler(MineshaftInventory internal) {
            this.internal = internal;
        }

        @Override
        public int size() {
            return internal.size();
        }

        @Override
        public @NotNull ItemResource getResource(int slot) {
            return internal.getResource(slot);
        }

        @Override
        public long getAmountAsLong(int slot) {
            return internal.getAmountAsLong(slot);
        }

        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return internal.isValid(slot, resource);
        }

        @Override
        public long getCapacityAsLong(int slot, ItemResource resource) {
            return internal.getCapacityAsLong(slot, resource);
        }

        @Override
        public int insert(int slot, ItemResource resource, int amount, TransactionContext transaction) {
            int inserted = internal.insert(slot, resource, amount, transaction);
            if (inserted > 0) {
                this.outputDirtyJournal.updateSnapshots(transaction);
            }
            return inserted;
        }

        @Override
        public int extract(int slot, ItemResource resource, int amount, TransactionContext transaction) {
            int extracted = internal.extract(slot, resource, amount, transaction);
            if (extracted > 0) {
                this.outputDirtyJournal.updateSnapshots(transaction);
            }
            return extracted;
        }
    }
    // endregion Inner Classes

    // region Static Methods
    public static void forceInitStackNBT(ItemStack stack, ServerLevel level) {
        stack.getItem().onCraftedBy(stack, FakePlayerFactory.getMinecraft(level));
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
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.inputHandler.deserialize(input.childOrEmpty("inputHandler"));
        this.outputHandler.deserialize(input.childOrEmpty("outputHandler"));
        this.updateBelowBlock();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        this.inputHandler.serialize(output.child("inputHandler"));
        this.outputHandler.serialize(output.child("outputHandler"));
        super.saveAdditional(output);
    }

    @Override
    public void loadNetwork(ValueInput input) {
        super.loadNetwork(input);
        this.miningTime = input.getIntOr("miningTime", 0);
        this.maxMiningTime = input.getIntOr("maxMiningTime", 0);
    }

    @Override
    public void saveNetwork(ValueOutput output) {
        this.lastSyncedMiningTime = this.miningTime;
        this.lastSyncedMaxMiningTime = this.maxMiningTime;
        output.putInt("miningTime", this.miningTime);
        output.putInt("maxMiningTime", this.maxMiningTime);
        super.saveNetwork(output);
    }

    public void tick() {
        if (this.level.hasNeighborSignal(this.getBlockPos())) {
            this.miningTime = 0;
            return;
        }
        if (!this.level.isClientSide()) {
            ItemStack input = this.inputHandler.getStackInSlot(0);

            if (!cachedEnchantment) {
                this.setCachedEnchantment();
                this.cachedEnchantment = true;
            }

            // handle unusing enchantment from evilcraft, see
            // https://github.com/klikli-dev/occultism/issues/909
            if (input.isEnchanted() && input.getMaxDamage() - input.getDamageValue() < 6 &&
                    UNUSING != null && input.getEnchantmentLevel(UNUSING) > 0) {
                this.miningTime = 0;
                return;
            }

            if (this.miningTime > 0) {

                int efficiency = bonusEfficiency && input.isEnchanted() ? input.getEnchantmentLevel(EFFICIENCY) : 0;
                if (efficiency > 0) {
                    int extra1 = this.level.getRandom().nextIntBetweenInclusive(0, efficiency);
                    int extra2 = this.level.getRandom().nextIntBetweenInclusive(0, efficiency);
                    efficiency = Math.min(extra1, extra2);
                }

                this.miningTime -= 1+efficiency;

                if (this.miningTime <= 0 && !this.level.isClientSide()) {
                    this.mine();
                }

                if (input.getItem() != this.currentInputType) {
                    this.miningTime = 0;
                    this.possibleResults = null;
                }

                // handled by delta check
            } else if (!input.isEmpty()) {

                this.currentInputType = input.getItem();

                forceInitStackNBT(input, (ServerLevel) this.level);
                this.maxMiningTime = getMaxMiningTime(input);
                this.rollsPerOperation = getRollsPerOperation(input);
                this.miningTime = this.maxMiningTime;
            }

            // Sync Logic
            boolean needsSync = false;

            // Sync if max mining time changed (e.g. new item with different stats)
            if (this.maxMiningTime != this.lastSyncedMaxMiningTime) {
                needsSync = true;
            }

            // Sync if mining active state changes (0 <-> >0)
            boolean wasActive = this.lastSyncedMiningTime > 0;
            boolean isActive = this.miningTime > 0;
            if (wasActive != isActive) {
                needsSync = true;
            }

            // Syncs if progress drifts by 10
            if (Math.abs(this.miningTime - this.lastSyncedMiningTime) >= 10) {
                needsSync = true;
            }
            // Also sync if we just finished (already covered by 0 check above? sorta).

            if (needsSync) {
                this.markNetworkDirty();
            }

            if (this.outputDirty) {
                this.setChanged();
                this.outputDirty = false;
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

    public void mine() {
        if (this.level == null)
            return;

        if (this.possibleResults == null) {
            ItemHandlerRecipeInput recipeInput = new ItemHandlerRecipeInput(this.inputHandler);
            List<RecipeHolder<MinerRecipe>> recipes = ((ServerLevel) this.level).getServer().getRecipeManager()
                    .recipeMap()
                    .getRecipesFor(OccultismRecipes.MINER_TYPE.get(), recipeInput, this.level)
                    .collect(Collectors.toList());
            if (recipes == null || recipes.size() == 0) {
                this.possibleResults = new ArrayList<>();
            } else {
                this.possibleResults = recipes.stream().map(r -> r.value().getWeightedResult())
                        .collect(Collectors.toList());
            }
        }

        if (this.possibleResults.size() == 0)
            return;

        ItemStack input = this.inputHandler.getStackInSlot(0);

        int fortune = bonusFortune && input.isEnchanted() ? input.getEnchantmentLevel(FORTUNE) : 0;
        if (fortune > 0) {
            int extra1 = this.level.getRandom().nextIntBetweenInclusive(0, fortune);
            int extra2 = this.level.getRandom().nextIntBetweenInclusive(0, fortune);
            int extra3 = this.level.getRandom().nextIntBetweenInclusive(0, fortune);
            fortune = Math.min(extra1, Math.min(extra2, extra3));
        }
        int silk = bonusSilk && input.isEnchanted() ? input.getEnchantmentLevel(SILK_TOUCH) : 0;

        List<ItemStack> batchedDrops = new ArrayList<>();

        for (int i = 0; i < this.rollsPerOperation + fortune; i++) {
            var result = WeightedRandom.getRandomItem(this.level.getRandom(), this.possibleResults, WeightedRecipeResult::weight);
            int finalSilk = silk > 0 ? 1 + this.level.getRandom().nextIntBetweenInclusive(0, silk) : 1;

            result.ifPresent(r -> {
                ItemStack finalResult = r.getStack().copy();
                finalResult.setCount(finalResult.getCount() * finalSilk);

                // Batching Logic: Check if we can merge with existing drop
                boolean merged = false;
                for (ItemStack existing : batchedDrops) {
                    if (ItemStack.isSameItemSameComponents(existing, finalResult)) {
                        existing.grow(finalResult.getCount());
                        merged = true;
                        break;
                    }
                }
                if (!merged) {
                    batchedDrops.add(finalResult);
                }
            });
        }

        // Insert batched drops
        ResourceHandler<ItemResource> currentHandler = this.getCurrentHandler();
        for (ItemStack drop : batchedDrops) {
            com.klikli_dev.occultism.util.ItemTransferUtil.insertItemStacked(currentHandler, drop, false);
        }

        // damage the item and move to output before breaking
        input.hurtAndBreak(1, (ServerLevel) this.level, (LivingEntity) null, (item) -> {
        });
        if (saveMiner && input.getMaxDamage() - 1 == input.getDamageValue()) {
            var minerCopy = input.copy();
            input.shrink(1);
            com.klikli_dev.occultism.util.ItemTransferUtil.insertItemStacked(currentHandler, minerCopy, false);
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

    private void setCachedEnchantment() {
        if (this.level != null) {
            UNUSING = this.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EVILCRAFT_UNUSING_ENCHANTMENT).orElse(null);
            EFFICIENCY = this.level.holderOrThrow(Enchantments.EFFICIENCY);
            FORTUNE = this.level.holderOrThrow(Enchantments.FORTUNE);
            SILK_TOUCH = this.level.holderOrThrow(Enchantments.SILK_TOUCH);
            cachedEnchantment = true;
        }
    }

    public void updateBelowBlock() {
        if (this.level != null) {
            this.cachedStateBelow = this.level.getBlockState(this.getBlockPos().below(2));
            // TODO: Port to new NeoForge transfer API (Capabilities.Item.BLOCK / ResourceHandler<ItemResource>)
            // The old IItemHandler capability system was replaced in NeoForge 26.1
            this.handlerBelow = null;
        }
    }

    private ResourceHandler<ItemResource> getCurrentHandler() {
        if (this.level == null)
            return null;

        if (this.level.getBlockState(this.getBlockPos().below()).is(OccultismBlocks.DIMENSIONAL_EXTRACTOR)) {
            if (this.cachedStateBelow != this.level.getBlockState(this.getBlockPos().below(2)))
                this.updateBelowBlock();
            return this.handlerBelow == null ? this.outputHandler : this.handlerBelow;
        }

        return this.outputHandler;
    }
}
