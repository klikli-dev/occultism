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
import com.klikli_dev.occultism.common.item.tool.ritual_satchel.MultiBlockRitualSatchelItem;
import com.klikli_dev.occultism.common.ritual.Ritual;
import com.klikli_dev.occultism.crafting.recipe.OccultismRecipeManager;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.*;
import com.klikli_dev.occultism.registry.OccultismTags.Blocks;
import com.klikli_dev.occultism.util.EntityUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.RootCommitJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

import static com.klikli_dev.occultism.common.ritual.Ritual.SACRIFICIAL_BOWL_RANGE;

public class GoldenSacrificialBowlBlockEntity extends SacrificialBowlBlockEntity {

    public RecipeHolder<RitualRecipe> currentRitualRecipe;
    public Identifier currentRitualRecipeId;
    public UUID castingPlayerId;
    public ServerPlayer castingPlayer;
    public List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
    public List<ItemStack> consumedIngredients = new ArrayList<>();
    public boolean sacrificeProvided;
    public boolean itemUseProvided;
    public int currentTime;
    public int tier;
    public boolean ritualActive;

    public Consumer<RightClickItem> rightClickItemListener;
    public Consumer<LivingDeathEvent> livingDeathEventListener;


    public GoldenSacrificialBowlBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.GOLDEN_SACRIFICIAL_BOWL.get(), worldPos, state);

        this.rightClickItemListener = this::onPlayerRightClickItem;
        this.livingDeathEventListener = this::onLivingDeath;

        this.itemStackHandler = new ItemStacksResourceHandler(1) {

            private int handleDummyInsert(ItemResource resource, int amount, TransactionContext tx) {
                ItemStack stack = resource.toStack(amount);

                int inserted = super.insert(0, resource, amount, tx);

                if (inserted > 0 && stack.getItem() instanceof DummyTooltipItem activationItem) {
                    new RootCommitJournal(() -> {
                        activationItem.performRitual(GoldenSacrificialBowlBlockEntity.this.level, GoldenSacrificialBowlBlockEntity.this.getBlockPos(), GoldenSacrificialBowlBlockEntity.this,
                                null, this.getResource(0).toStack());
                        try (var extractTx = Transaction.openRoot()) {
                            super.extract(0, resource, 1, extractTx);
                            extractTx.commit();
                        }
                    }).updateSnapshots(tx);
                }

                return inserted;
            }

            @Override
            public int insert(int slot, ItemResource resource, int amount, TransactionContext tx) {
                if (resource.toStack().getItem() instanceof DummyTooltipItem)
                    return this.handleDummyInsert(resource, amount, tx);

                if (GoldenSacrificialBowlBlockEntity.this.getCurrentRitualRecipe() != null)
                    return 0;

                var ritualRecipe = getAllRitualRecipes(GoldenSacrificialBowlBlockEntity.this.level).stream().filter(
                        r -> r.value().matches(GoldenSacrificialBowlBlockEntity.this.level, GoldenSacrificialBowlBlockEntity.this.getBlockPos(), resource.toStack())
                ).findFirst().orElse(null);

                if (ritualRecipe == null)
                    return 0;

                int inserted = super.insert(slot, resource, amount, tx);

                if (inserted > 0) {
                    new RootCommitJournal(() -> {
                        var activationItemStack = this.getResource(0).toStack();
                        if (ritualRecipe.value().getRitual().isValid(GoldenSacrificialBowlBlockEntity.this.level, GoldenSacrificialBowlBlockEntity.this.getBlockPos(), GoldenSacrificialBowlBlockEntity.this, GoldenSacrificialBowlBlockEntity.this.castingPlayer, activationItemStack,
                                ritualRecipe.value().getIngredients()))
                            GoldenSacrificialBowlBlockEntity.this.startRitual(GoldenSacrificialBowlBlockEntity.this.castingPlayer, activationItemStack, ritualRecipe);
                    }).updateSnapshots(tx);
                }

                return inserted;
            }

            @Override
            protected int getCapacity(int slot, ItemResource resource) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot, ItemStack previousContents) {
                if (GoldenSacrificialBowlBlockEntity.this.level != null && !GoldenSacrificialBowlBlockEntity.this.level.isClientSide()) {
                    GoldenSacrificialBowlBlockEntity.this.lastChangeTime = GoldenSacrificialBowlBlockEntity.this.level
                            .getGameTime();
                    GoldenSacrificialBowlBlockEntity.this.setChanged();
                    GoldenSacrificialBowlBlockEntity.this.markNetworkDirty();
                }
            }

        };
    }

    // If we find pentacle that almost matches block in the world, then print help
    private static boolean helpWithPentacle(Level level, BlockPos pos, Player player) {
        Map<BlockPos, Block> pentacleDiff = null;
        Map<BlockPos, Block> bestPentacleDiff = null;
        Boolean hasPentacle = false;

        var pentacleMultiblocks = getAllRitualRecipes(level)
                .stream().map(r -> r.value().getPentacleId()).distinct().map(ModonomiconAPI.get()::getMultiblock).toList();

        Multiblock bestMatch = null;
        for (var pentacle : pentacleMultiblocks) {
            pentacleDiff = getDifference(pentacle, level, pos);
            if (bestPentacleDiff == null || bestPentacleDiff.size() > pentacleDiff.size()) {
                if (pentacleDiff.isEmpty()) {
                    hasPentacle = true;
                } else {
                    bestPentacleDiff = pentacleDiff;
                    bestMatch = pentacle;
                }
            }
        }

        if (bestPentacleDiff != null && !bestPentacleDiff.isEmpty() && bestPentacleDiff.size() < 10) {
            //tell player which pentacle he was probably trying to build
            player.sendSystemMessage(
                    Component.translatable("ritual." + Occultism.MODID + ".pentacle_help",
                            Component.translatable(Util.makeDescriptionId("multiblock", bestMatch.getId())), pentacleDiffToComponent(bestPentacleDiff)));
            return true;
        } else if (bestPentacleDiff != null && !bestPentacleDiff.isEmpty() && !hasPentacle) {
            //player probably doesn't have a pentacle at all
            player.sendSystemMessage(
                    Component.translatable("ritual." + Occultism.MODID + ".pentacle_help.no_pentacle"));
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
    private static boolean helpWithRitual(Level level, BlockPos pos, ServerPlayer player, ItemStack activationItem) {
        List<Ingredient> ritualDiff = null;
        List<Ingredient> bestRitualDiff = null;
        RitualRecipe bestRitual = null;

        var pentacleMultiblocks = getAllRitualRecipes(level)
                .stream().map(r -> r.value().getPentacleId()).distinct().map(ModonomiconAPI.get()::getMultiblock);

        var pentacle = pentacleMultiblocks.filter(p -> p.validate(level, pos) != null).toList();

        if (pentacle.isEmpty())
            return false;

        for (var recipe : getAllRitualRecipes(level)) {
            if (!pentacle.contains(recipe.value().getPentacle()))
                continue;

            ritualDiff = new ArrayList<>(recipe.value().getIngredients());
            List<ItemStack> items = recipe.value().getRitual().getItemsOnSacrificialBowls(level, pos);

            if (items.isEmpty()){
                player.sendSystemMessage(Component.translatable("ritual." + Occultism.MODID + ".empty_bowls"));
                return true;
            }

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
                bestRitual = recipe.value();
            }
        }

        if (bestRitualDiff != null && !bestRitualDiff.isEmpty() && bestRitualDiff.size() < 3) {
            player.sendSystemMessage(
                    Component.translatable("ritual." + Occultism.MODID + ".ritual_help", Component.translatable(bestRitual.getRitual().getStartedMessage(player)), ritualDiffToComponent(bestRitualDiff)));

            return true;
        }

        return false;
    }

    private static Object ritualDiffToComponent(List<Ingredient> ritualDiff) {
        Random rand = new Random();
        var text = Component.literal("");

        for (Ingredient ingredient : ritualDiff) {
            if (ingredient.items().findAny().isEmpty())
                continue;

            var items = ingredient.items().map(holder -> new ItemStack(holder.value())).toList();
            text.append(items.get(rand.nextInt(items.size())).getDisplayName());
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
                    difference.put(result.worldPosition(), result.stateMatcher().getDisplayedState(0).getBlock());
                }
            }

            if (difference.size() < minDiffSize) {
                minDifference = difference;
                minDiffSize = difference.size();
            }
        }

        return minDifference;
    }

    @SuppressWarnings("unchecked")
    public RecipeHolder<RitualRecipe> getCurrentRitualRecipe() {
        if (this.currentRitualRecipeId != null) {
            if (this.level != null) {
                var recipeKey = ResourceKey.create(Registries.RECIPE, this.currentRitualRecipeId);
                var recipe = OccultismRecipeManager.get().getRecipeByKey(OccultismRecipes.RITUAL_TYPE.get(), recipeKey, this.level);
                recipe.map(r -> r).ifPresent(r -> this.currentRitualRecipe = r);

                if (this.level instanceof ServerLevel) {
                    NeoForge.EVENT_BUS.addListener(this.rightClickItemListener);
                    NeoForge.EVENT_BUS.addListener(this.livingDeathEventListener);
                }

                this.currentRitualRecipeId = null;
            }
        }
        return this.currentRitualRecipe;
    }

    public int getSignal() {
        if(this.getCurrentRitualRecipe() == null)
            return 0;

        if(!this.sacrificeFulfilled())
            return 1;

        if(!this.itemUseFulfilled())
            return 2;


        return 8;
    }

    public int getTier(BlockState pBlockState){
        Block blockBowl = pBlockState.getBlock();

        if (blockBowl.equals(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                || blockBowl.equals(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get()))
            return 1;

        if (blockBowl.equals(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get())
                || blockBowl.equals(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get()))
            return 2;

        if (blockBowl.equals(OccultismBlocks.ELDRITCH_CHALICE.get())
                || blockBowl.equals(OccultismBlocks.CELESTIAL_CHALICE.get()))
            return 3;

        return 0;
    }

    public void tick() {
        RecipeHolder<RitualRecipe> recipe = this.getCurrentRitualRecipe();
        if (!this.level.isClientSide() && recipe != null) {
            this.restoreCastingPlayer();

            if (this.remainingAdditionalIngredients == null) {
                this.restoreRemainingAdditionalIngredients();
                if (this.remainingAdditionalIngredients == null) {
                    Occultism.LOGGER
                            .warn("Could not restore remainingAdditionalIngredients during tick - level seems to be null. Will attempt again next tick.");
                    return;
                }
            }

            if (!recipe.value().getRitual().isValid(this.level, this.getBlockPos(), this, this.castingPlayer,
                    this.itemStackHandler.getResource(0).toStack(), this.remainingAdditionalIngredients)) {
                //ritual is no longer valid, so interrupt
                this.stopRitual(false);
                return;
            }

            //if we do not have a sacrifice yet, we cannot advance time
            if (!this.sacrificeFulfilled() || !this.itemUseFulfilled()) {
                if(this.level.getGameTime() % 20 == 0)
                    this.level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());

                if (this.level.getRandom().nextInt(16) == 0) {
                    ((ServerLevel) this.level)
                            .sendParticles(OccultismParticles.RITUAL_WAITING.get(),
                                    this.getBlockPos().getX() + this.level.getRandom().nextGaussian(),
                                    this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + this.level.getRandom().nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                    ((ServerLevel) this.level)
                            .sendParticles(OccultismParticles.RITUAL_WAITING.get(),
                                    this.getBlockPos().getX() + this.level.getRandom().nextGaussian(),
                                    this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + this.level.getRandom().nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                    ((ServerLevel) this.level)
                            .sendParticles(OccultismParticles.RITUAL_WAITING.get(),
                                    this.getBlockPos().getX() + this.level.getRandom().nextGaussian(),
                                    this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + this.level.getRandom().nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                }
                return;
            }

            //spawn particles in random intervals
            if (this.level.getRandom().nextInt(16) == 0) {
                ((ServerLevel) this.level)
                        .sendParticles(ParticleTypes.PORTAL, this.getBlockPos().getX() + 0.5 + this.level.getRandom().nextGaussian() / 3,
                                this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5 + this.level.getRandom().nextGaussian() / 3, 5,
                                0.0, 0.0, 0.0,
                                0.0);
            }

            //spawn particles in bowl before consume next item
            if (this.level.getGameTime() % 5 == 0) {
                if (!this.remainingAdditionalIngredients.isEmpty()) {
                    recipe.value().getRitual().markNextIngredient(this.level, this.getBlockPos(), this.remainingAdditionalIngredients.getFirst(), this.getTier(this.getBlockState()));
                } else {
                    double gameTime = this.level.getGameTime() * 0.05;
                    double sin = Math.sin(gameTime) * 0.3;
                    double cos = Math.cos(gameTime) * 0.3;
                    Vec3 center = this.getBlockPos().getCenter();
                    ((ServerLevel) this.level)
                            .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x + cos, center.y + 0.2 + cos, center.z + sin,
                                    1, 0.0, 0.0, 0.0, 0.003);
                    if (this.getTier(this.getBlockState()) == 2) {
                        double sin2 = Math.sin(gameTime + (Math.PI * 0.5)) * 0.3;
                        double cos2 = Math.cos(gameTime + (Math.PI * 0.5)) * 0.3;
                        ((ServerLevel) this.level)
                                .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x + cos2, center.y + 0.2 + sin2, center.z + sin2,
                                        1, 0.0, 0.0, 0.0, 0.003);
                        ((ServerLevel) this.level)
                                .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x - cos2, center.y + 0.2 + cos2, center.z - sin2,
                                        1, 0.0, 0.0, 0.0, 0.003);
                        ((ServerLevel) this.level)
                                .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x - cos, center.y + 0.2 + sin, center.z - sin,
                                        1, 0.0, 0.0, 0.0, 0.003);
                    }
                }
            }

            //Advance ritual time every second, based on the standard 20 tps, but taking into account duration multiplier
            if (this.getTier(this.getBlockState()) == 1){ //golden bowl
                if (this.level.getGameTime() % ((int) (20 * Occultism.SERVER_CONFIG.rituals.ritualDurationMultiplier.get())) == 0){
                    this.currentTime++;
                }
            } else if (this.getTier(this.getBlockState()) == 2) {
                if (Occultism.SERVER_CONFIG.rituals.ritualDurationMultiplier.get() < 0.2) { //avoiding crash of divide by 0 with iesnium bowl
                    this.currentTime++;
                } else if (this.level.getGameTime() % ((int) (5 * Occultism.SERVER_CONFIG.rituals.ritualDurationMultiplier.get())) == 0) {
                    this.currentTime++;
                }
            } else {
                this.currentTime = recipe.value().getDuration();
            }

            recipe
                    .value().getRitual()
                    .update(this.level, this.getBlockPos(), this, this.castingPlayer, this.itemStackHandler.getResource(0).toStack(),
                            this.currentTime);

            if (!recipe
                    .value().getRitual()
                    .consumeAdditionalIngredients(this.level, this.getBlockPos(), this.remainingAdditionalIngredients,
                            this.currentTime, this.consumedIngredients)) {
                //if ingredients cannot be found, interrupt
                this.stopRitual(false);
                return;
            }

            if (recipe.value().getDuration() >= 0 && this.currentTime >= recipe.value().getDuration())
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

    public boolean activate(Level level, BlockPos pos, Player player, InteractionHand hand, Direction face) {
        if(hand == InteractionHand.OFF_HAND)
            return false; //prevent offhand activation which can actually cause interruption due to the second firing of activate

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack activationItem = player.getItemInHand(hand);
            if (activationItem == ItemStack.EMPTY)
                return false;

            if (activationItem.getItem() instanceof DummyTooltipItem) {
                ((DummyTooltipItem) activationItem.getItem()).performRitual(level, pos, this,
                        player, activationItem);
                return true;
            }

            if (this.getCurrentRitualRecipe() == null) {
                //Identify the ritual in the ritual registry.

                var ritualRecipe = getAllRitualRecipes(this.level).stream().filter(
                        r -> r.value().matches(level, pos, activationItem)
                ).findFirst().orElse(null);

                if (ritualRecipe != null) {
                    if (ritualRecipe.value().getRitual().isValid(level, pos, this, player, activationItem,
                            ritualRecipe.value().getIngredients())) {
                        this.castingPlayer = serverPlayer; // set casting player so the item stack handler insert code can access it
                        try (var tx = Transaction.openRoot()) {
                            this.itemStackHandler.insert(0, ItemResource.of(activationItem), 1, tx);
                            activationItem.shrink(1);
                            tx.commit();
                        }
                        //no need to start the ritual as insertItem calls it
//                        this.startRitual(serverPlayer, activationItem, ritualRecipe);
                    } else {
                        //if ritual is not valid, inform player.
                        player.sendSystemMessage(Component.translatable(ritualRecipe.value().getRitual().getConditionsMessage(serverPlayer)));
                        return false;
                    }
                } else {
                    if (activationItem.getItem() instanceof BookOfBindingItem) {
                        //common error: people use unbound book, so we send a special message for those
                        player.sendSystemMessage(
                                Component.translatable(String.format("ritual.%s.book_not_bound", Occultism.MODID)));
                    } else {
                        var otherActivation = getAllRitualRecipes(this.level).stream().filter(
                                r -> r.value().getRitual().identifyAnyActivation(level, pos)
                        ).findFirst().orElse(null);

                        if (otherActivation != null) {
                            var activationItems = otherActivation.value().getActivationItem().items()
                                    .map(holder -> new ItemStack(holder.value())).toList();
                            if (!activationItems.isEmpty()) {
                                String s = activationItems.getFirst().getDisplayName().getString();
                                player.sendSystemMessage(
                                        Component.translatable(String.format("ritual.%s.wrong_activation_item", Occultism.MODID)));
                                player.sendSystemMessage(
                                        Component.translatable(s.substring(1, s.length() - 1))
                                                .withStyle(activationItems.getFirst().getRarity().getStyleModifier()));
                            }
                        } else {
                            var firstRecipe = getAllRitualRecipes(this.level).stream().findFirst();
                            if (firstRecipe.isPresent() && firstRecipe.get().value().getRitual().getSacrificialBowls(level, pos).isEmpty()
                                    && !(activationItem.getItem() instanceof MultiBlockRitualSatchelItem)) {

                                var pentacle = firstRecipe.get().value().getPentacle();
                                var offset = pentacle.getOffset();
                                var size = pentacle.getSize();
                                var yBowlRangeTop = size.getY() - offset.getY() - 1;
                                var yBowlRangeBottom = offset.getY();
                                yBowlRangeTop++;
                                yBowlRangeBottom++;
                                Iterable<BlockPos> blocksToCheck = BlockPos.betweenClosed(
                                        pos.offset(-SACRIFICIAL_BOWL_RANGE, -yBowlRangeBottom, -SACRIFICIAL_BOWL_RANGE),
                                        pos.offset(SACRIFICIAL_BOWL_RANGE, yBowlRangeTop, SACRIFICIAL_BOWL_RANGE));
                                for (BlockPos posCheck : blocksToCheck) {
                                    if (level.getBlockState(posCheck).is(BlockTags.AIR)
                                            && !level.getBlockState(posCheck.below()).is(BlockTags.AIR)
                                            && !level.getBlockState(posCheck.below()).is(Blocks.CHALK_GLYPHS) ) {

                                        ((ServerLevel) level).sendParticles(OccultismParticles.BLACK_MARKER.get(),
                                                posCheck.getX() + 0.5, posCheck.getY() + 0.1, posCheck.getZ() + 0.5,
                                                1 ,0 ,0, 0 , 0.00);
                                    }
                                }

                                player.sendSystemMessage(
                                        Component.translatable(String.format("ritual.%s.no_bowls", Occultism.MODID)));
                            } else if (!helpWithPentacle(level, pos, player)) {
                                var otherPentacle = getAllRitualRecipes(this.level).stream().filter(
                                        r -> r.value().getRitual().identifyAnyPentacle(level, pos, activationItem)
                                ).findFirst().orElse(null);

                                if (otherPentacle != null) {
                                    player.sendSystemMessage(
                                            Component.translatable(String.format("ritual.%s.wrong_pentacle", Occultism.MODID)));
                                    player.sendSystemMessage(
                                            Component.translatable(Util.makeDescriptionId("multiblock", otherPentacle.value().getPentacleId())));
                                } else if (activationItem.getItem() instanceof MultiBlockRitualSatchelItem) {
                                    ((ServerLevel) level)
                                            .sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                                    10, 0.3, 0.3, 0.3, 0.03);

                                    level.playSound(null, this.getBlockPos(), OccultismSounds.POOF.get(), SoundSource.PLAYERS, 1, 3);

                                    player.sendOverlayMessage(
                                            Component.translatable(String.format("ritual.%s.put_in_satchel", Occultism.MODID)));
                                } else if (!helpWithRitual(level, pos, serverPlayer, activationItem)) {
                                    player.sendSystemMessage(
                                            Component.translatable(String.format("ritual.%s.does_not_exist", Occultism.MODID)));
                                }
                            }
                        }
                        return false;
                    }
                }
            } else {
                this.stopRitual(false);
            }
        }
        return true;
    }

    public boolean startRitual(@Nullable ServerPlayer player, ItemStack activationItem, RecipeHolder<RitualRecipe> ritualRecipe) {
        if (!this.level.isClientSide()) {
            this.currentRitualRecipe = ritualRecipe;
            this.castingPlayerId = player == null? null : player.getUUID();
            this.castingPlayer = player;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            this.consumedIngredients.clear();
            this.remainingAdditionalIngredients = new ArrayList<>(this.currentRitualRecipe.value().getIngredients());
            this.ritualActive=true;
            if(!this.currentRitualRecipe.value().getRitual().start(this.level, this.getBlockPos(), this, player, this.itemStackHandler.getResource(0).toStack())) {
                this.stopRitual(false, false); //do not show message as start will already do that
                return false;
            }

            NeoForge.EVENT_BUS.addListener(this.rightClickItemListener);
            NeoForge.EVENT_BUS.addListener(this.livingDeathEventListener);

            this.setChanged();
            this.markNetworkDirty();

            this.level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());

            if (player != null) {
                if (ritualRecipe.value().requiresSacrifice()) {
                    player.sendSystemMessage(Component.translatable(String.format("ritual.%s.sacrifice", Occultism.MODID)));
                    player.sendSystemMessage(Component.translatable(String.format(ritualRecipe.value().getEntityToSacrificeDisplayName())));
                }

                if (ritualRecipe.value().requiresItemUse()) {
                    player.sendSystemMessage(Component.translatable(String.format("ritual.%s.use_item", Occultism.MODID)));
                    var itemToUseItems = ritualRecipe.value().getItemToUse().items().map(holder -> new ItemStack(holder.value())).toList();
                    if (!itemToUseItems.isEmpty()) {
                        String s = itemToUseItems.getFirst().getDisplayName().getString();
                        player.sendSystemMessage(Component.translatable(s.substring(1, s.length() - 1)));
                    }
                }
            }
        }
        return true;
    }

    public void stopRitual(boolean finished){
        this.stopRitual(finished, true);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.stopRitual(false);
        super.preRemoveSideEffects(pos, state);
    }

    public void stopRitual(boolean finished, boolean showInterruptedMessage) {
        if (!this.level.isClientSide()) {
            var recipe = this.getCurrentRitualRecipe();
            if (recipe != null) {
                if (finished) {
                    ItemStack activationItem = this.itemStackHandler.getResource(0).toStack();
                    recipe.value().getRitual().finish(this.level, this.getBlockPos(), this, this.castingPlayer, activationItem);
                } else {
                    recipe.value().getRitual().interrupt(this.level, this.getBlockPos(), this, this.castingPlayer,
                            this.itemStackHandler.getResource(0).toStack(), showInterruptedMessage);
                    //Pop activation item back into level
                    try (var tx = Transaction.openRoot()) {
                        var resource = this.itemStackHandler.getResource(0);
                        if (!resource.isEmpty()) {
                            int extracted = this.itemStackHandler.extract(0, resource, 1, tx);
                            if (extracted > 0) {
                                Containers.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),
                                        resource.toStack(extracted));
                            }
                        }
                        tx.commit();
                    }
                    for (ItemStack consumed : this.consumedIngredients) {
                        Containers.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),
                                consumed);
                    }
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

            NeoForge.EVENT_BUS.unregister(this.rightClickItemListener);
            NeoForge.EVENT_BUS.unregister(this.livingDeathEventListener);
            this.ritualActive=false;
            this.setChanged();
            this.markNetworkDirty();

            this.level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
        }
    }

    public boolean sacrificeFulfilled() {
        return !this.getCurrentRitualRecipe().value().requiresSacrifice() || this.sacrificeProvided;
    }

    public boolean itemUseFulfilled() {
        return !this.getCurrentRitualRecipe().value().requiresItemUse() || this.itemUseProvided;
    }

    public void notifySacrifice(LivingEntity entityLivingBase) {
        this.sacrificeProvided = true;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide())
            this.markNetworkDirty();
    }

    public void notifyItemUse(RightClickItem event) {
        this.itemUseProvided = true;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide())
            this.markNetworkDirty();
    }

    public void onPlayerRightClickItem(RightClickItem event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide() && this.getCurrentRitualRecipe() != null) {

            if (this.getBlockPos().distSqr(event.getPos()) <= Ritual.ITEM_USE_DETECTION_RANGE_SQUARE) {
                if (this.getCurrentRitualRecipe().value().getRitual().isValidItemUse(event)) {
                    this.notifyItemUse(event);
                }
            }
        }
    }

    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entityLivingBase = event.getEntity();
        if (!entityLivingBase.level().isClientSide() && this.getCurrentRitualRecipe() != null) {
            //Limit to player kills
            if (event.getSource().getEntity() instanceof Player) {
                if (this.getBlockPos().distSqr(entityLivingBase.blockPosition()) <= Ritual.SACRIFICE_DETECTION_RANGE_SQUARE) {
                    if (this.getCurrentRitualRecipe().value().getRitual().isValidSacrifice(entityLivingBase)) {
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
                        this.getCurrentRitualRecipe().value().getIngredients(), this.consumedIngredients);
            } else {
                this.remainingAdditionalIngredients = new ArrayList<>(this.getCurrentRitualRecipe().value().getIngredients());
            }
        }

    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.consumedIngredients.clear();
        if (this.currentRitualRecipeId != null || this.getCurrentRitualRecipe() != null) {
            input.listOrEmpty("consumedIngredients", ItemStack.OPTIONAL_CODEC).forEach(stack -> {
                this.consumedIngredients.add(stack);
            });
            this.restoreRemainingAdditionalIngredients();
        }
        this.sacrificeProvided = input.getBooleanOr("sacrificeProvided", false);
        this.itemUseProvided = input.getBooleanOr("requiredItemUsed", false);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        if (this.getCurrentRitualRecipe() != null) {
            if (!this.consumedIngredients.isEmpty()) {
                var list = output.list("consumedIngredients", ItemStack.OPTIONAL_CODEC);
                for (ItemStack stack : this.consumedIngredients) {
                    list.add(stack);
                }
            }
            output.putBoolean("sacrificeProvided", this.sacrificeProvided);
            output.putBoolean("requiredItemUsed", this.itemUseProvided);
        }
        super.saveAdditional(output);
    }

    @Override
    public void loadNetwork(ValueInput input) {
        super.loadNetwork(input);
        input.getString("currentRitual").ifPresent(s -> this.currentRitualRecipeId = Identifier.parse(s));

        input.read("castingPlayerId", UUIDUtil.CODEC).ifPresent(uuid -> this.castingPlayerId = uuid);

        this.currentTime = input.getIntOr("currentTime", 0);
        this.ritualActive = input.getBooleanOr("ritualActive", false);
        this.sacrificeProvided = input.getBooleanOr("sacrificeProvided", false);
        this.itemUseProvided = input.getBooleanOr("requiredItemUsed", false);
    }

    @Override
    public void saveNetwork(ValueOutput output) {
        var recipe = this.getCurrentRitualRecipe();
        if (recipe != null) {
            output.putString("currentRitual", recipe.id().identifier().toString());
        }
        if (this.castingPlayerId != null) {
            output.store("castingPlayerId", UUIDUtil.CODEC, this.castingPlayerId);
        }
        output.putInt("currentTime", this.currentTime);
        output.putBoolean("ritualActive", this.ritualActive);
        output.putBoolean("sacrificeProvided", this.sacrificeProvided);
        output.putBoolean("requiredItemUsed", this.itemUseProvided);
        super.saveNetwork(output);
    }

    /**
     * Gets all ritual recipes from the recipe manager, filtering by type.
     * Uses OccultismRecipeManager for both server and client-side access.
     */
    @SuppressWarnings("unchecked")
    private static List<RecipeHolder<RitualRecipe>> getAllRitualRecipes(Level level) {
        return OccultismRecipeManager.get().getRecipesByType(OccultismRecipes.RITUAL_TYPE.get(), level).stream()
                .map(r -> r)
                .toList();
    }
}
