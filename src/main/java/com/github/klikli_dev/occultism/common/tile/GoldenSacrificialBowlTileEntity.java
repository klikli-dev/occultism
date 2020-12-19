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
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismParticles;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GoldenSacrificialBowlTileEntity extends SacrificialBowlTileEntity implements ITickableTileEntity {

    //region Fields
    public Ritual currentRitual;
    public UUID castingPlayerId;
    public PlayerEntity castingPlayer;
    public List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
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
        if (this.currentRitual != null && compound.contains("remainingAdditionalIngredientsSize")) {
            int size = compound.getByte("remainingAdditionalIngredientsSize");
            List<Ingredient> additionalIngredients = this.currentRitual.getAdditionalIngredients(this.world);
            if (size >= 0 && size <= additionalIngredients.size()) {
                this.remainingAdditionalIngredients = additionalIngredients.subList(
                        additionalIngredients.size() - size, additionalIngredients.size() - 1);
            }
            else {
                this.remainingAdditionalIngredients = new ArrayList<>(additionalIngredients);
            }
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
        if (this.currentRitual != null) {
            if (this.currentRitual.getAdditionalIngredients(this.world).size() > 0) {
                //we only store additional ingredients, if the ritual has any.
                compound.putByte("remainingAdditionalIngredientsSize",
                        (byte) this.remainingAdditionalIngredients.size());
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
            this.currentRitual = OccultismRituals.RITUAL_REGISTRY
                                         .getValue(new ResourceLocation(compound.getString("currentRitual")));
        }

        if (compound.contains("castingPlayerId")) {
            this.castingPlayerId = UUID.fromString(compound.getString("castingPlayerId"));
        }

        this.currentTime = compound.getInt("currentTime");
    }

    @Override
    public CompoundNBT writeNetwork(CompoundNBT compound) {
        if (this.currentRitual != null) {
            compound.putString("currentRitual", this.currentRitual.getRegistryName().toString());
        }
        if (this.castingPlayerId != null) {
            compound.putString("castingPlayerId", this.castingPlayerId.toString());
        }
        compound.putInt("currentTime", this.currentTime);
        return super.writeNetwork(compound);
    }

    @Override
    public void tick() {
        if (!this.world.isRemote && this.currentRitual != null) {
            this.restoreCastingPlayer();
            IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            if (!this.currentRitual.isValid(this.world, this.pos, this, this.castingPlayer,
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


            this.currentRitual
                    .update(this.world, this.pos, this, this.castingPlayer, handler.getStackInSlot(0),
                            this.currentTime);

            if (!this.currentRitual
                         .consumeAdditionalIngredients(this.world, this.pos, this.remainingAdditionalIngredients,
                                 this.currentTime)) {
                //if ingredients cannot be found, interrupt
                this.stopRitual(false);
                return;
            }

            if (this.currentRitual.totalSeconds >= 0 && this.currentTime >= this.currentRitual.totalSeconds)
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

            if (this.currentRitual == null) {
                //Identify the ritual in the ritual registry.

                Ritual ritual = OccultismRituals.RITUAL_REGISTRY.getValues().stream()
                                        .filter(r -> r.identify(world, pos, activationItem)).findFirst().orElse(null);

                if (ritual != null) {
                    if (ritual.isValid(world, pos, this, player, activationItem,
                            ritual.getAdditionalIngredients(world))) {
                        this.startRitual(player, activationItem, ritual);
                    }
                    else {
                        //if ritual is not valid, inform player.
                        player.sendStatusMessage(new TranslationTextComponent(ritual.getConditionsMessage()), true);
                        return false;
                    }
                }
                else {
                    player.sendStatusMessage(
                            new TranslationTextComponent(String.format("ritual.%s.does_not_exist", Occultism.MODID)),
                            true);
                    return false;
                }
            }
            else {
                this.stopRitual(false);
            }
        }
        return true;
    }

    public void startRitual(PlayerEntity player, ItemStack activationItem, Ritual ritual) {
        if (!this.world.isRemote) {
            this.currentRitual = ritual;
            this.castingPlayerId = player.getUniqueID();
            this.castingPlayer = player;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            this.remainingAdditionalIngredients = new ArrayList<>(ritual.getAdditionalIngredients(this.world));
            //place activation item in handler
            IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            handler.insertItem(0, activationItem.split(1), false);
            this.currentRitual.start(this.world, this.pos, this, player, handler.getStackInSlot(0));
            this.markDirty();
            this.markNetworkDirty();
        }
    }

    public void stopRitual(boolean finished) {
        if (!this.world.isRemote) {
            if (this.currentRitual != null && this.castingPlayer != null) {
                IItemHandler handler = this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
                if (finished) {
                    ItemStack activationItem = handler.getStackInSlot(0);
                    this.currentRitual.finish(this.world, this.pos, this, this.castingPlayer, activationItem);
                }
                else {
                    this.currentRitual.interrupt(this.world, this.pos, this, this.castingPlayer,
                            handler.getStackInSlot(0));
                    //Pop activation item back into world
                    InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(),
                            handler.extractItem(0, 1, false));
                }
            }
            this.currentRitual = null;
            this.castingPlayerId = null;
            this.castingPlayer = null;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.itemUseProvided = false;
            this.remainingAdditionalIngredients.clear();
            this.markDirty();
            this.markNetworkDirty();
        }
    }

    public boolean sacrificeFulfilled() {
        return !this.currentRitual.requiresSacrifice() || this.sacrificeProvided;
    }

    public boolean itemUseFulfilled() {
        return !this.currentRitual.requiresItemUse() || this.itemUseProvided;
    }

    public void notifySacrifice(LivingEntity entityLivingBase) {
        this.sacrificeProvided = true;
    }

    public void notifyItemUse(PlayerInteractEvent.RightClickItem event) {
        this.itemUseProvided = true;
    }
    //endregion Methods
}
