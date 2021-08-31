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

package com.github.klikli_dev.occultism.common.tile;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfBindingItem;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismParticles;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GoldenSacrificialBowlBlockEntity extends SacrificialBowlBlockEntity {

    //region Fields
    public RitualRecipe currentRitualRecipe;
    public UUID castingPlayerId;
    public Player castingPlayer;
    public List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
    public List<ItemStack> consumedIngredients = new ArrayList<>();
    public boolean sacrificeProvided;
    public boolean itemUseProvided;
    public int currentTime;

    //endregion Fields

    //region Initialization
    public GoldenSacrificialBowlBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get(), worldPos, state);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.consumedIngredients.clear();
        if (this.currentRitualRecipe != null) {
            if (compound.contains("consumedIngredients")) {
                ListTag list = compound.getList("consumedIngredients", Constants.NBT.TAG_COMPOUND);
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
    public CompoundTag save(CompoundTag compound) {
        if (this.currentRitualRecipe != null) {
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
        return super.save(compound);
    }

    @Override
    public void loadNetwork(CompoundTag compound) {
        super.loadNetwork(compound);
        if (compound.contains("currentRitual")) {
            Optional<? extends Recipe<?>> recipe = this.level.getRecipeManager().byKey(new ResourceLocation(compound.getString("currentRitual")));
            recipe.map(r -> (RitualRecipe) r).ifPresent(r -> this.currentRitualRecipe = r);
        }

        if (compound.contains("castingPlayerId")) {
            this.castingPlayerId = compound.getUUID("castingPlayerId");
        }

        this.currentTime = compound.getInt("currentTime");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound) {
        if (this.currentRitualRecipe != null) {
            compound.putString("currentRitual", this.currentRitualRecipe.getId().toString());
        }
        if (this.castingPlayerId != null) {
            compound.putUUID("castingPlayerId", this.castingPlayerId);
        }
        compound.putInt("currentTime", this.currentTime);
        return super.saveNetwork(compound);
    }

    public void tick() {
        if (!this.level.isClientSide && this.currentRitualRecipe != null) {
            this.restoreCastingPlayer();

            if (this.remainingAdditionalIngredients == null) {
                this.restoreRemainingAdditionalIngredients();
                if (this.remainingAdditionalIngredients == null) {
                    Occultism.LOGGER
                            .warn("Could not restore remainingAdditionalIngredients during tick - level seems to be null. Will attempt again next tick.");
                    return;
                }
            }
            //if we ever have a ritual that depends on casting player for validity, we need to rework this
            //to involve casting player id with some good pre-check
            IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            if (!this.currentRitualRecipe.getRitual().isValid(this.level, this.getBlockPos(), this, this.castingPlayer,
                    handler.getStackInSlot(0), this.remainingAdditionalIngredients)) {
                //ritual is no longer valid, so interrupt
                this.stopRitual(false);
                return;
            }

            //no casting player or if we do not have a sacrifice yet, we cannot advance time
            if (this.castingPlayer == null || !this.sacrificeFulfilled() || !this.itemUseFulfilled()) {
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

            //Advance ritual time every second, based on the standard 20 tps.
            if (this.level.getGameTime() % 20 == 0)
                this.currentTime++;


            this.currentRitualRecipe
                    .getRitual()
                    .update(this.level, this.getBlockPos(), this, this.castingPlayer, handler.getStackInSlot(0),
                            this.currentTime);

            if (!this.currentRitualRecipe
                    .getRitual()
                    .consumeAdditionalIngredients(this.level, this.getBlockPos(), this.remainingAdditionalIngredients,
                            this.currentTime, this.consumedIngredients)) {
                //if ingredients cannot be found, interrupt
                this.stopRitual(false);
                return;
            }

            if (this.currentRitualRecipe.getDuration() >= 0 && this.currentTime >= this.currentRitualRecipe.getDuration())
                this.stopRitual(true);
        }
    }

    //endregion Overrides

    //region Methods
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
        if (!level.isClientSide) {
            ItemStack activationItem = player.getItemInHand(hand);
            if (activationItem == ItemStack.EMPTY)
                return false;

            if (activationItem.getItem() instanceof DummyTooltipItem) {
                ((DummyTooltipItem) activationItem.getItem()).performRitual(level, pos, this,
                        player, activationItem);
                return true;
            }

            if (this.currentRitualRecipe == null) {
                //Identify the ritual in the ritual registry.

                RitualRecipe ritualRecipe = this.level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream().filter(
                        r -> r.matches(level, pos, activationItem)
                ).findFirst().orElse(null);

                if (ritualRecipe != null) {
                    if (ritualRecipe.getRitual().isValid(level, pos, this, player, activationItem,
                            ritualRecipe.getIngredients())) {
                        this.startRitual(player, activationItem, ritualRecipe);
                    } else {
                        //if ritual is not valid, inform player.
                        player.displayClientMessage(new TranslatableComponent(ritualRecipe.getRitual().getConditionsMessage()), true);
                        return false;
                    }
                } else {
                    if (activationItem.getItem() instanceof BookOfBindingItem) {
                        //common error: people use unbound book, so we send a special message for those
                        player.displayClientMessage(
                                new TranslatableComponent(String.format("ritual.%s.book_not_bound", Occultism.MODID)),
                                false);
                    } else {
                        player.displayClientMessage(
                                new TranslatableComponent(String.format("ritual.%s.does_not_exist", Occultism.MODID)),
                                false);
                    }
                    return false;
                }
            } else {
                this.stopRitual(false);
            }
        }
        return true;
    }

    public void startRitual(Player player, ItemStack activationItem, RitualRecipe ritualRecipe) {
        if (!this.level.isClientSide) {
            this.currentRitualRecipe = ritualRecipe;
            this.castingPlayerId = player.getUUID();
            this.castingPlayer = player;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            this.consumedIngredients.clear();
            this.remainingAdditionalIngredients = new ArrayList<>(this.currentRitualRecipe.getIngredients());
            //place activation item in handler
            IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            handler.insertItem(0, activationItem.split(1), false);
            this.currentRitualRecipe.getRitual().start(this.level, this.getBlockPos(), this, player, handler.getStackInSlot(0));
            this.setChanged();
            this.markNetworkDirty();
        }
    }

    public void stopRitual(boolean finished) {
        if (!this.level.isClientSide) {
            if (this.currentRitualRecipe != null && this.castingPlayer != null) {
                IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
                if (finished) {
                    ItemStack activationItem = handler.getStackInSlot(0);
                    this.currentRitualRecipe.getRitual().finish(this.level, this.getBlockPos(), this, this.castingPlayer, activationItem);
                } else {
                    this.currentRitualRecipe.getRitual().interrupt(this.level, this.getBlockPos(), this, this.castingPlayer,
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
            this.setChanged();
            this.markNetworkDirty();
        }
    }

    public boolean sacrificeFulfilled() {
        return !this.currentRitualRecipe.requiresSacrifice() || this.sacrificeProvided;
    }

    public boolean itemUseFulfilled() {
        return !this.currentRitualRecipe.requiresItemUse() || this.itemUseProvided;
    }

    public void notifySacrifice(LivingEntity entityLivingBase) {
        this.sacrificeProvided = true;
    }

    public void notifyItemUse(PlayerInteractEvent.RightClickItem event) {
        this.itemUseProvided = true;
    }

    protected void restoreRemainingAdditionalIngredients() {
        if (this.level == null) {
            //this sets the signal that loading didn't go right -> will reattempt during tick()
            this.remainingAdditionalIngredients = null;
        } else {
            if (this.consumedIngredients.size() > 0) {
                this.remainingAdditionalIngredients = Ritual.getRemainingAdditionalIngredients(
                        this.currentRitualRecipe.getIngredients(), this.consumedIngredients);
            } else {
                this.remainingAdditionalIngredients = new ArrayList<>(this.currentRitualRecipe.getIngredients());
            }
        }

    }
    //endregion Methods
}
