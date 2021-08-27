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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfBindingItem;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismParticles;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.EntityUtil;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.items.IItemHandler;

public class GoldenSacrificialBowlTileEntity extends SacrificialBowlTileEntity implements ITickableTileEntity {

    //region Fields
    public RitualRecipe currentRitualRecipe;
    public UUID castingPlayerId;
    public PlayerEntity castingPlayer;
    public List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
    public List<ItemStack> consumedIngredients = new ArrayList<>();
    public boolean sacrificeProvided;
    public boolean itemUseProvided;
    public int currentTime;

    //endregion Fields

    //region Initialization
    public GoldenSacrificialBowlTileEntity() {
        super(OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get());
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);

        this.consumedIngredients.clear();
        if (this.currentRitualRecipe != null) {
            if (compound.contains("consumedIngredients")) {
                ListNBT list = compound.getList("consumedIngredients", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < list.size(); i++) {
                    ItemStack stack = ItemStack.read(list.getCompound(i));
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
    public CompoundNBT write(CompoundNBT compound) {
        if (this.currentRitualRecipe != null) {
            if (this.consumedIngredients.size() > 0) {
                ListNBT list = new ListNBT();
                for (ItemStack stack : this.consumedIngredients) {
                    list.add(stack.serializeNBT());
                }
                compound.put("consumedIngredients", list);
            }
            compound.putBoolean("sacrificeProvided", this.sacrificeProvided);
            compound.putBoolean("requiredItemUsed", this.itemUseProvided);
        }
        return super.write(compound);
    }

    @Override
    public void readNetwork(CompoundNBT compound) {
        super.readNetwork(compound);
        if (compound.contains("currentRitual")) {
            Optional<? extends IRecipe<?>> recipe = this.world.getRecipeManager().getRecipe(new ResourceLocation(compound.getString("currentRitual")));
            recipe.map(r -> (RitualRecipe) r).ifPresent(r -> this.currentRitualRecipe = r);
        }

        if (compound.contains("castingPlayerId")) {
            this.castingPlayerId = compound.getUniqueId("castingPlayerId");
        }

        this.currentTime = compound.getInt("currentTime");
    }

    @Override
    public CompoundNBT writeNetwork(CompoundNBT compound) {
        if (this.currentRitualRecipe != null) {
            compound.putString("currentRitual", this.currentRitualRecipe.getId().toString());
        }
        if (this.castingPlayerId != null) {
            compound.putUniqueId("castingPlayerId", this.castingPlayerId);
        }
        compound.putInt("currentTime", this.currentTime);
        return super.writeNetwork(compound);
    }

    @Override
    public void tick() {
        if (!this.world.isRemote && this.currentRitualRecipe != null) {
            this.restoreCastingPlayer();

            if (this.remainingAdditionalIngredients == null) {
                this.restoreRemainingAdditionalIngredients();
                if (this.remainingAdditionalIngredients == null) {
                    Occultism.LOGGER
                            .warn("Could not restore remainingAdditionalIngredients during tick - world seems to be null. Will attempt again next tick.");
                    return;
                }
            }
            //if we ever have a ritual that depends on casting player for validity, we need to rework this
            //to involve casting player id with some good pre-check
            IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            if (!this.currentRitualRecipe.getRitual().isValid(this.world, this.pos, this, this.castingPlayer,
                    handler.getStackInSlot(0), this.remainingAdditionalIngredients)) {
                //ritual is no longer valid, so interrupt
                this.stopRitual(false);
                return;
            }

            //no casting player or if we do not have a sacrifice yet, we cannot advance time
            if (this.castingPlayer == null || !this.sacrificeFulfilled() || !this.itemUseFulfilled()) {
                if (this.world.rand.nextInt(16) == 0) {
                    ((ServerWorld) this.world)
                            .spawnParticle(OccultismParticles.RITUAL_WAITING.get(),
                                    this.pos.getX() + this.world.rand.nextGaussian(),
                                    this.pos.getY() + 0.5, this.pos.getZ() + this.world.rand.nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                    ((ServerWorld) this.world)
                            .spawnParticle(OccultismParticles.RITUAL_WAITING.get(),
                                    this.pos.getX() + this.world.rand.nextGaussian(),
                                    this.pos.getY() + 0.5, this.pos.getZ() + this.world.rand.nextGaussian(),
                                    3,
                                    0.0, 0.0, 0.0,
                                    0.0);
                }
                return;
            }

            //spawn particles in random intervals
            if (this.world.rand.nextInt(16) == 0) {
                ((ServerWorld) this.world)
                        .spawnParticle(ParticleTypes.PORTAL, this.pos.getX() + 0.5 + this.world.rand.nextGaussian() / 3,
                                this.pos.getY() + 0.5, this.pos.getZ() + 0.5 + this.world.rand.nextGaussian() / 3, 5,
                                0.0, 0.0, 0.0,
                                0.0);
            }

            //Advance ritual time every second, based on the standard 20 tps.
            if (this.world.getGameTime() % 20 == 0)
                this.currentTime++;


            this.currentRitualRecipe
                    .getRitual().update(this.world, this.pos, this, this.castingPlayer, handler.getStackInSlot(0),
                            this.currentTime);

            if (!this.currentRitualRecipe.getRitual()
                         .consumeAdditionalIngredients(this.world, this.pos, this.remainingAdditionalIngredients,
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
            this.world.getGameTime() % (20 * 30) == 0) {
            this.castingPlayer = EntityUtil.getPlayerByUuiDGlobal(this.castingPlayerId).orElse(null);
            this.markDirty();
            this.markNetworkDirty();
        }
    }

    public boolean activate(World world, BlockPos pos, PlayerEntity player, Hand hand, Direction face) {
        if (!world.isRemote) {
            ItemStack activationItem = player.getHeldItem(hand);
            if (activationItem == ItemStack.EMPTY)
                return false;
            
            if (activationItem.getItem() instanceof DummyTooltipItem) {
                ((DummyTooltipItem) activationItem.getItem()).performRitual(world, pos, this,
                        player, activationItem);
                return true;
            }

            if (this.currentRitualRecipe == null) {
                //Identify the ritual in the ritual registry.

                RitualRecipe ritualRecipe = this.world.getRecipeManager().getRecipesForType(OccultismRecipes.RITUAL_TYPE.get()).stream().filter(
                        r -> r.matches(world, pos, activationItem)
                ).findFirst().orElse(null);

                if (ritualRecipe != null) {
                    if (ritualRecipe.getRitual().isValid(world, pos, this, player, activationItem,
                            this.currentRitualRecipe.getIngredients())) {
                        this.startRitual(player, activationItem, this.currentRitualRecipe);
                    }
                    else {
                        //if ritual is not valid, inform player.
                        player.sendStatusMessage(new TranslationTextComponent(ritualRecipe.getRitual().getConditionsMessage()), true);
                        return false;
                    }
                }
                else {
                    if(activationItem.getItem() instanceof BookOfBindingItem){
                        //common error: people use unbound book, so we send a special message for those
                        player.sendStatusMessage(
                                new TranslationTextComponent(String.format("ritual.%s.book_not_bound", Occultism.MODID)),
                                false);
                    } else {
                        player.sendStatusMessage(
                                new TranslationTextComponent(String.format("ritual.%s.does_not_exist", Occultism.MODID)),
                                false);
                    }
                    return false;
                }
            }
            else {
                this.stopRitual(false);
            }
        }
        return true;
    }

    public void startRitual(PlayerEntity player, ItemStack activationItem, RitualRecipe ritualRecipe) {
        if (!this.world.isRemote) {
            this.currentRitualRecipe = ritualRecipe;
            this.castingPlayerId = player.getUniqueID();
            this.castingPlayer = player;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            this.consumedIngredients.clear();
            this.remainingAdditionalIngredients = this.currentRitualRecipe.getIngredients();
            //place activation item in handler
            IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            handler.insertItem(0, activationItem.split(1), false);
            this.currentRitualRecipe.getRitual().start(this.world, this.pos, this, player, handler.getStackInSlot(0));
            this.markDirty();
            this.markNetworkDirty();
        }
    }

    public void stopRitual(boolean finished) {
        if (!this.world.isRemote) {
            if (this.currentRitualRecipe != null && this.castingPlayer != null) {
                IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
                if (finished) {
                    ItemStack activationItem = handler.getStackInSlot(0);
                    this.currentRitualRecipe.getRitual().finish(this.world, this.pos, this, this.castingPlayer, activationItem);
                }
                else {
                    this.currentRitualRecipe.getRitual().interrupt(this.world, this.pos, this, this.castingPlayer,
                            handler.getStackInSlot(0));
                    //Pop activation item back into world
                    InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(),
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
            this.markDirty();
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
        if (this.world == null) {
            //this sets the signal that loading didn't go right -> will reattempt during tick()
            this.remainingAdditionalIngredients = null;
        }
        else {
            if (this.consumedIngredients.size() > 0) {
                this.remainingAdditionalIngredients = Ritual.getRemainingAdditionalIngredients(
                        this.currentRitualRecipe.getIngredients(), this.consumedIngredients);
            }
            else {
                this.remainingAdditionalIngredients = new ArrayList<>(this.currentRitualRecipe.getIngredients());
            }
        }

    }
    //endregion Methods
}
