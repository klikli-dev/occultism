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

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock.SimulateResult;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.klikli_dev.occultism.common.item.spirit.BookOfBindingItem;
import com.klikli_dev.occultism.common.ritual.Ritual;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismTiles;
import com.klikli_dev.occultism.util.EntityUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class GoldenSacrificialBowlBlockEntity extends SacrificialBowlBlockEntity {

    public RitualRecipe currentRitualRecipe;
    public ResourceLocation currentRitualRecipeId;
    public UUID castingPlayerId;
    public ServerPlayer castingPlayer;
    public List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
    public List<ItemStack> consumedIngredients = new ArrayList<>();
    public boolean sacrificeProvided;
    public boolean itemUseProvided;
    public int currentTime;

    public Consumer<RightClickItem> rightClickItemListener;
    public Consumer<LivingDeathEvent> livingDeathEventListener;


    public GoldenSacrificialBowlBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get(), worldPos, state);

        this.rightClickItemListener = this::onPlayerRightClickItem;
        this.livingDeathEventListener = this::onLivingDeath;

        this.itemStackHandler = new ItemStackHandler(1) {

            private ItemStack handleDummyInsert(int slot, @NotNull ItemStack stack, boolean simulate){
                var insertResult = super.insertItem(slot, stack, simulate);
                var activationItemStack = this.getStackInSlot(0);

                if (!simulate && insertResult.getCount() != stack.getCount() && stack.getItem() instanceof DummyTooltipItem activationItem) {
                    activationItem.performRitual(GoldenSacrificialBowlBlockEntity.this.level, GoldenSacrificialBowlBlockEntity.this.getBlockPos(), GoldenSacrificialBowlBlockEntity.this,
                            null, activationItemStack);
                    activationItemStack.shrink(1);
                }

                return insertResult;
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if(stack.getItem() instanceof DummyTooltipItem)
                    return handleDummyInsert(slot, stack, simulate);

                if (GoldenSacrificialBowlBlockEntity.this.getCurrentRitualRecipe() != null)
                    return stack;

                var ritualRecipe = GoldenSacrificialBowlBlockEntity.this.level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream().filter(
                        r -> r.matches(GoldenSacrificialBowlBlockEntity.this.level, GoldenSacrificialBowlBlockEntity.this.getBlockPos(), stack)
                ).findFirst().orElse(null);

                if (ritualRecipe == null)
                    return stack;

                var insertResult = super.insertItem(slot, stack, simulate);
                var activationItemStack = this.getStackInSlot(0);

                if (!simulate && insertResult.getCount() != stack.getCount() && ritualRecipe != null) {
                    if (ritualRecipe.getRitual().isValid(GoldenSacrificialBowlBlockEntity.this.level, GoldenSacrificialBowlBlockEntity.this.getBlockPos(), GoldenSacrificialBowlBlockEntity.this, null, activationItemStack,
                            ritualRecipe.getIngredients()))
                        GoldenSacrificialBowlBlockEntity.this.startRitual(null, activationItemStack, ritualRecipe);
                }

                return insertResult;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(
                    int slot) {
                if (!GoldenSacrificialBowlBlockEntity.this.level.isClientSide) {
                    GoldenSacrificialBowlBlockEntity.this.lastChangeTime = GoldenSacrificialBowlBlockEntity.this.level
                            .getGameTime();
                    GoldenSacrificialBowlBlockEntity.this.markNetworkDirty();
                }
            }

        };
    }

    // If we find pentacle that almost matches block in the world, then print help
    private static boolean helpWithPentacle(Level level, BlockPos pos, Player player) {
        Map<BlockPos, Block> pentacleDiff = null;
        Map<BlockPos, Block> bestPentacleDiff = null;

        var pentacleMultiblocks = level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get())
                .stream().map(RitualRecipe::getPentacleId).distinct().map(ModonomiconAPI.get()::getMultiblock).toList();

        Multiblock bestMatch = null;
        for (var pentacle : pentacleMultiblocks) {
            pentacleDiff = getDifference(pentacle, level, pos);
            if (bestPentacleDiff == null || bestPentacleDiff.size() > pentacleDiff.size()) {
                bestPentacleDiff = pentacleDiff;
                bestMatch = pentacle;
            }
        }

        if (bestPentacleDiff != null && !bestPentacleDiff.isEmpty() && bestPentacleDiff.size() < 4) {
            //tell player which pentacle he was probably trying to build
            player.displayClientMessage(
                    Component.translatable("ritual." + Occultism.MODID + ".pentacle_help",
                            Component.translatable(Util.makeDescriptionId("multiblock", bestMatch.getId())), pentacleDiffToComponent(bestPentacleDiff)),
                    false);
            return true;
        } else if (bestPentacleDiff != null && !bestPentacleDiff.isEmpty()) {
            //player probably doesn't have a pentacle at all
            player.displayClientMessage(
                    Component.translatable("ritual." + Occultism.MODID + ".pentacle_help.no_pentacle"), false);
            return true;
        }
        return false;
    }

    private static MutableComponent pentacleDiffToComponent(Map<BlockPos, Block> bestPentacleDiff) {
        var text = Component.literal("");

        for (Entry<BlockPos, Block> entry : bestPentacleDiff.entrySet()) {
            text.append(Component.translatable(entry.getValue().getDescriptionId()));
            text.append(Component.translatable("ritual." + Occultism.MODID + ".pentacle_help_at_glue"));
            BlockPos pos = entry.getKey();
            text.append(Component.literal("x: " + pos.getX() + ", y: " + pos.getY() + ", z: " + pos.getZ() + "\n"));
        }

        return text;
    }

    // If we find ritual with ingredients that almost matches bowls, then print help
    private static boolean helpWithRitual(Level level, BlockPos pos, Player player, ItemStack activationItem) {
        List<Ingredient> ritualDiff = null;
        List<Ingredient> bestRitualDiff = null;
        RitualRecipe bestRitual = null;

        var pentacleMultiblocks = level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get())
                .stream().map(RitualRecipe::getPentacleId).distinct().map(ModonomiconAPI.get()::getMultiblock);

        var pentacle = pentacleMultiblocks.filter(p -> p.validate(level, pos) != null).findFirst();

        if (pentacle.isEmpty())
            return false;


        for (RitualRecipe recipe : level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get())) {
            if (recipe.getPentacle() != pentacle.orElseThrow())
                continue;

            ritualDiff = new ArrayList<>(recipe.getIngredients());
            List<ItemStack> items = recipe.getRitual().getItemsOnSacrificialBowls(level, pos);

            boolean found = false;
            for (int i = ritualDiff.size() - 1; i >= 0; i--) {
                found = false;
                for (int j = 0; j < items.size(); j++) {
                    if (ritualDiff.get(i).test(items.get(j))) {
                        items.remove(j);
                        found = true;
                        break;
                    }
                }
                if (found)
                    ritualDiff.remove(i);
            }

            if (bestRitualDiff == null || bestRitualDiff.size() > ritualDiff.size()) {
                bestRitualDiff = ritualDiff;
                bestRitual = recipe;
            }
        }

        if (bestRitualDiff != null && !bestRitualDiff.isEmpty() && bestRitualDiff.size() < 4) {
            player.displayClientMessage(
                    Component.translatable("ritual." + Occultism.MODID + ".ritual_help", Component.translatable(bestRitual.getRitual().getStartedMessage()), ritualDiffToComponent(bestRitualDiff)),
                    false);

            return true;
        }

        return false;
    }

    private static Object ritualDiffToComponent(List<Ingredient> ritualDiff) {
        Random rand = new Random();
        var text = Component.literal("");

        for (Ingredient ingredient : ritualDiff) {
            if (ingredient.getItems().length == 0)
                continue;

            text.append(ingredient.getItems()[rand.nextInt(ingredient.getItems().length)].getDisplayName());
            text.append("\n");
        }

        return text;
    }

    public static Map<BlockPos, Block> getDifference(Multiblock multiblock, Level level, BlockPos pos) {
        Map<BlockPos, Block> minDifference = new HashMap<>();
        int minDiffSize = Integer.MAX_VALUE;

        Map<BlockPos, Block> difference;
        for (Rotation rot : Rotation.values()) {
            difference = new HashMap<>();
            Pair<BlockPos, Collection<SimulateResult>> sim = multiblock.simulate(level, pos, rot, false, false);

            for (SimulateResult result : sim.getSecond()) {
                if (!result.test(level, rot)) {
                    difference.put(result.getWorldPosition(), result.getStateMatcher().getDisplayedState(0).getBlock());
                }
            }

            if (difference.size() < minDiffSize) {
                minDifference = difference;
                minDiffSize = difference.size();
            }
        }

        return minDifference;
    }

    public RitualRecipe getCurrentRitualRecipe() {
        if (this.currentRitualRecipeId != null) {
            if (this.level != null) {
                Optional<? extends Recipe<?>> recipe = this.level.getRecipeManager().byKey(this.currentRitualRecipeId);
                recipe.map(r -> (RitualRecipe) r).ifPresent(r -> this.currentRitualRecipe = r);

                MinecraftForge.EVENT_BUS.addListener(this.rightClickItemListener);
                MinecraftForge.EVENT_BUS.addListener(this.livingDeathEventListener);

                this.currentRitualRecipeId = null;
            }
        }
        return this.currentRitualRecipe;
    }

    public void tick() {
        RitualRecipe recipe = this.getCurrentRitualRecipe();
        if (!this.level.isClientSide && recipe != null) {
            this.restoreCastingPlayer();

            if (this.remainingAdditionalIngredients == null) {
                this.restoreRemainingAdditionalIngredients();
                if (this.remainingAdditionalIngredients == null) {
                    Occultism.LOGGER
                            .warn("Could not restore remainingAdditionalIngredients during tick - level seems to be null. Will attempt again next tick.");
                    return;
                }
            }

            IItemHandler handler = this.lazyItemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            if (!recipe.getRitual().isValid(this.level, this.getBlockPos(), this, this.castingPlayer,
                    handler.getStackInSlot(0), this.remainingAdditionalIngredients)) {
                //ritual is no longer valid, so interrupt
                this.stopRitual(false);
                return;
            }

            //if we do not have a sacrifice yet, we cannot advance time
            if (!this.sacrificeFulfilled() || !this.itemUseFulfilled()) {
                if (this.level.random.nextInt(16) == 0) {
                    ((ServerLevel) this.level)
                            .sendParticles(OccultismParticles.RITUAL_WAITING.get(),
                                    this.getBlockPos().getX() + this.level.random.nextGaussian(),
                                    this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + this.level.random.nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                    ((ServerLevel) this.level)
                            .sendParticles(OccultismParticles.RITUAL_WAITING.get(),
                                    this.getBlockPos().getX() + this.level.random.nextGaussian(),
                                    this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + this.level.random.nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                }
                return;
            }

            //spawn particles in random intervals
            if (this.level.random.nextInt(16) == 0) {
                ((ServerLevel) this.level)
                        .sendParticles(ParticleTypes.PORTAL, this.getBlockPos().getX() + 0.5 + this.level.random.nextGaussian() / 3,
                                this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5 + this.level.random.nextGaussian() / 3, 5,
                                0.0, 0.0, 0.0,
                                0.0);
            }

            //Advance ritual time every second, based on the standard 20 tps, but taking into account duration multiplier
            if (this.level.getGameTime() % ((int) (20 * Occultism.SERVER_CONFIG.rituals.ritualDurationMultiplier.get())) == 0)
                this.currentTime++;

            recipe
                    .getRitual()
                    .update(this.level, this.getBlockPos(), this, this.castingPlayer, handler.getStackInSlot(0),
                            this.currentTime);

            if (!recipe
                    .getRitual()
                    .consumeAdditionalIngredients(this.level, this.getBlockPos(), this.remainingAdditionalIngredients,
                            this.currentTime, this.consumedIngredients)) {
                //if ingredients cannot be found, interrupt
                this.stopRitual(false);
                return;
            }

            if (recipe.getDuration() >= 0 && this.currentTime >= recipe.getDuration())
                this.stopRitual(true);
        }
    }

    public void restoreCastingPlayer() {
        //every 30 seconds try to restore the casting player
        if (this.castingPlayer == null && this.castingPlayerId != null &&
                this.level.getGameTime() % (20 * 30) == 0) {
            this.castingPlayer = EntityUtil.getPlayerByUuiDGlobal(this.castingPlayerId).orElse(null);
            this.setChanged();
            this.markNetworkDirty();
        }
    }

    public boolean activate(Level level, BlockPos pos, ServerPlayer serverPlayer, InteractionHand hand, Direction face) {
        if (!level.isClientSide) {
            ItemStack activationItem = serverPlayer.getItemInHand(hand);
            if (activationItem == ItemStack.EMPTY)
                return false;

            if (activationItem.getItem() instanceof DummyTooltipItem) {
                ((DummyTooltipItem) activationItem.getItem()).performRitual(level, pos, this,
                        serverPlayer, activationItem);
                return true;
            }

            if (this.getCurrentRitualRecipe() == null) {
                //Identify the ritual in the ritual registry.

                RitualRecipe ritualRecipe = this.level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream().filter(
                        r -> r.matches(level, pos, activationItem)
                ).findFirst().orElse(null);

                if (ritualRecipe != null) {
                    if (ritualRecipe.getRitual().isValid(level, pos, this, serverPlayer, activationItem,
                            ritualRecipe.getIngredients())) {
                        this.itemStackHandler.insertItem(0, activationItem.split(1), false);
                        this.startRitual(serverPlayer, activationItem, ritualRecipe);
                    } else {
                        //if ritual is not valid, inform player.
                        serverPlayer.displayClientMessage(Component.translatable(ritualRecipe.getRitual().getConditionsMessage()), true);
                        return false;
                    }
                } else {
                    if (activationItem.getItem() instanceof BookOfBindingItem) {
                        //common error: people use unbound book, so we send a special message for those
                        serverPlayer.displayClientMessage(
                                Component.translatable(String.format("ritual.%s.book_not_bound", Occultism.MODID)),
                                false);
                    } else {
                        if (!helpWithPentacle(level, pos, serverPlayer)) {
                            if (!helpWithRitual(level, pos, serverPlayer, activationItem)) {
                                serverPlayer.displayClientMessage(
                                        Component.translatable(String.format("ritual.%s.does_not_exist", Occultism.MODID)),
                                        false);
                            }
                        }
                    }
                    return false;
                }
            } else {
                this.stopRitual(false);
            }
        }
        return true;
    }

    public void startRitual(@Nullable ServerPlayer player, ItemStack activationItem, RitualRecipe ritualRecipe) {
        if (!this.level.isClientSide) {
            this.currentRitualRecipe = ritualRecipe;
            this.castingPlayerId = player == null? null : player.getUUID();
            this.castingPlayer = player;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            this.consumedIngredients.clear();
            this.remainingAdditionalIngredients = new ArrayList<>(this.currentRitualRecipe.getIngredients());
            //place activation item in handler
            this.currentRitualRecipe.getRitual().start(this.level, this.getBlockPos(), this, player, this.itemStackHandler.getStackInSlot(0));


            MinecraftForge.EVENT_BUS.addListener(this.rightClickItemListener);
            MinecraftForge.EVENT_BUS.addListener(this.livingDeathEventListener);

            this.setChanged();
            this.markNetworkDirty();
        }
    }

    public void stopRitual(boolean finished) {
        if (!this.level.isClientSide) {
            var recipe = this.getCurrentRitualRecipe();
            if (recipe != null) {
                IItemHandler handler = this.itemStackHandler;
                if (finished) {
                    ItemStack activationItem = handler.getStackInSlot(0);
                    recipe.getRitual().finish(this.level, this.getBlockPos(), this, this.castingPlayer, activationItem);
                } else {
                    recipe.getRitual().interrupt(this.level, this.getBlockPos(), this, this.castingPlayer,
                            handler.getStackInSlot(0));
                    //Pop activation item back into level
                    Containers.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),
                            handler.extractItem(0, 1, false));
                }
            }
            this.currentRitualRecipe = null;
            this.castingPlayerId = null;
            this.castingPlayer = null;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            if (this.remainingAdditionalIngredients != null)
                this.remainingAdditionalIngredients.clear();
            this.consumedIngredients.clear();

            MinecraftForge.EVENT_BUS.unregister(this.rightClickItemListener);
            MinecraftForge.EVENT_BUS.unregister(this.livingDeathEventListener);

            this.setChanged();
            this.markNetworkDirty();
        }
    }

    public boolean sacrificeFulfilled() {
        return !this.getCurrentRitualRecipe().requiresSacrifice() || this.sacrificeProvided;
    }

    public boolean itemUseFulfilled() {
        return !this.getCurrentRitualRecipe().requiresItemUse() || this.itemUseProvided;
    }

    public void notifySacrifice(LivingEntity entityLivingBase) {
        this.sacrificeProvided = true;
    }

    public void notifyItemUse(PlayerInteractEvent.RightClickItem event) {
        this.itemUseProvided = true;
    }

    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && this.getCurrentRitualRecipe() != null) {

            if (this.getBlockPos().distSqr(event.getPos()) <= Ritual.ITEM_USE_DETECTION_RANGE_SQUARE) {
                if (this.getCurrentRitualRecipe().getRitual().isValidItemUse(event)) {
                    this.notifyItemUse(event);
                }
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction direction) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.empty();
        }
        return super.getCapability(cap, direction);
    }

    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entityLivingBase = event.getEntity();
        if (!entityLivingBase.level().isClientSide && this.getCurrentRitualRecipe() != null) {
            //Limit to player kills
            if (event.getSource().getEntity() instanceof Player) {
                if (this.getBlockPos().distSqr(entityLivingBase.blockPosition()) <= Ritual.SACRIFICE_DETECTION_RANGE_SQUARE) {
                    if (this.getCurrentRitualRecipe().getRitual().isValidSacrifice(entityLivingBase)) {
                        this.notifySacrifice(entityLivingBase);
                    }
                }
            }
        }
    }

    protected void restoreRemainingAdditionalIngredients() {
        if (this.level == null) {
            //this sets the signal that loading didn't go right -> will reattempt during tick()
            this.remainingAdditionalIngredients = null;
        } else {
            if (this.consumedIngredients.size() > 0) {
                this.remainingAdditionalIngredients = Ritual.getRemainingAdditionalIngredients(
                        this.getCurrentRitualRecipe().getIngredients(), this.consumedIngredients);
            } else {
                this.remainingAdditionalIngredients = new ArrayList<>(this.getCurrentRitualRecipe().getIngredients());
            }
        }

    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.consumedIngredients.clear();
        if (this.currentRitualRecipeId != null || this.getCurrentRitualRecipe() != null) {
            if (compound.contains("consumedIngredients")) {
                ListTag list = compound.getList("consumedIngredients", Tag.TAG_COMPOUND);
                for (int i = 0; i < list.size(); i++) {
                    ItemStack stack = ItemStack.of(list.getCompound(i));
                    this.consumedIngredients.add(stack);
                }
            }
            this.restoreRemainingAdditionalIngredients();
        }
        if (compound.contains("sacrificeProvided")) {
            this.sacrificeProvided = compound.getBoolean("sacrificeProvided");
        }
        if (compound.contains("requiredItemUsed")) {
            this.itemUseProvided = compound.getBoolean("requiredItemUsed");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (this.getCurrentRitualRecipe() != null) {
            if (this.consumedIngredients.size() > 0) {
                ListTag list = new ListTag();
                for (ItemStack stack : this.consumedIngredients) {
                    list.add(stack.serializeNBT());
                }
                compound.put("consumedIngredients", list);
            }
            compound.putBoolean("sacrificeProvided", this.sacrificeProvided);
            compound.putBoolean("requiredItemUsed", this.itemUseProvided);
        }
        super.saveAdditional(compound);
    }

    @Override
    public void loadNetwork(CompoundTag compound) {
        super.loadNetwork(compound);
        if (compound.contains("currentRitual")) {
            this.currentRitualRecipeId = new ResourceLocation(compound.getString("currentRitual"));
        }

        if (compound.contains("castingPlayerId")) {
            this.castingPlayerId = compound.getUUID("castingPlayerId");
        }

        this.currentTime = compound.getInt("currentTime");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound) {
        RitualRecipe recipe = this.getCurrentRitualRecipe();
        if (recipe != null) {
            compound.putString("currentRitual", recipe.getId().toString());
        }
        if (this.castingPlayerId != null) {
            compound.putUUID("castingPlayerId", this.castingPlayerId);
        }
        compound.putInt("currentTime", this.currentTime);
        return super.saveNetwork(compound);
    }
}
