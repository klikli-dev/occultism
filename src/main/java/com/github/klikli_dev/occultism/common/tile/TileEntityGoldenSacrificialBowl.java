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
import com.github.klikli_dev.occultism.client.particle.ModParticleType;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.network.MessageModParticle;
import com.github.klikli_dev.occultism.network.MessageParticle;
import com.github.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileEntityGoldenSacrificialBowl extends TileEntitySacrificialBowl implements ITickable {

    //region Fields
    public Ritual currentRitual;
    public UUID castingPlayerId;
    public EntityPlayer castingPlayer;
    public List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
    public boolean sacrificeProvided;
    public int currentTime;

    //endregion Fields

    //region Overrides
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (this.currentRitual != null && compound.hasKey("remainingAdditionalIngredientsSize")) {
            this.remainingAdditionalIngredients = this.currentRitual.additionalIngredients.subList(0,
                    compound.getByte("remainingAdditionalIngredientsSize") + 1);
        }
        if (compound.hasKey("sacrificeProvided")) {
            this.sacrificeProvided = compound.getBoolean("sacrificeProvided");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte("remainingAdditionalIngredientsSize", (byte) this.remainingAdditionalIngredients.size());
        compound.setBoolean("sacrificeProvided", this.sacrificeProvided);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        boolean shouldRefresh = super.shouldRefresh(world, pos, oldState, newState);
        if (!world.isRemote && shouldRefresh)
            this.stopRitual(false); //if block changed/was destroyed, interrupt the ritual.
        return shouldRefresh;
    }

    @Override
    public void readFromNetworkNBT(NBTTagCompound compound) {
        super.readFromNetworkNBT(compound);

        if (compound.hasKey("currentRitual")) {
            this.currentRitual = GameRegistry.findRegistry(Ritual.class)
                                         .getValue(new ResourceLocation(compound.getString("currentRitual")));
        }

        if (compound.hasKey("castingPlayerId")) {
            this.castingPlayerId = UUID.fromString(compound.getString("castingPlayerId"));
        }

        this.currentTime = compound.getInteger("currentTime");
    }

    @Override
    public NBTTagCompound writeToNetworkNBT(NBTTagCompound compound) {
        if (this.currentRitual != null) {
            compound.setString("currentRitual", this.currentRitual.getRegistryName().toString());
        }
        if (this.castingPlayerId != null) {
            compound.setString("castingPlayerId", this.castingPlayerId.toString());
        }
        compound.setInteger("currentTime", this.currentTime);
        return super.writeToNetworkNBT(compound);
    }

    @Override
    public void update() {
        if (!this.world.isRemote && this.currentRitual != null) {
            this.restoreCastingPlayer();

            if (!this.currentRitual.isValid(this.world, this.pos, this, this.castingPlayer,
                    this.itemStackHandler.getStackInSlot(0), this.remainingAdditionalIngredients)) {
                //ritual is no longer valid, so interrupt
                this.stopRitual(false);
                return;
            }

            //no casting player or if we do not have a sacrifice yet, we cannot advance time
            if (this.castingPlayer == null || (this.currentRitual.requiresSacrifice() && !this.sacrificeProvided)) {
                if (this.world.rand.nextInt(16) == 0) {
                    Occultism.network.sendToDimension(new MessageModParticle(ModParticleType.RITUAL_WAITING,
                                    this.pos.getX() + 0.5 + this.world.rand.nextGaussian() / 3, this.pos.getY() + 0.5,
                                    this.pos.getZ() + 0.5 + this.world.rand.nextGaussian() / 3),
                            this.world.provider.getDimension());
                }
                return;
            }

            //spawn particles in random intervals
            if (this.world.rand.nextInt(16) == 0) {
                Occultism.network.sendToDimension(new MessageParticle(EnumParticleTypes.PORTAL,
                                this.pos.getX() + 0.5 + this.world.rand.nextGaussian() / 3, this.pos.getY() + 0.5,
                                this.pos.getZ() + 0.5 + this.world.rand.nextGaussian() / 3),
                        this.world.provider.getDimension());
            }

            //Advance ritual time every second, based on the standard 20 tps.
            if (this.world.getTotalWorldTime() % 20 == 0)
                this.currentTime++;


            this.currentRitual
                    .update(this.world, this.pos, this, this.castingPlayer, this.itemStackHandler.getStackInSlot(0),
                            this.currentTime);

            if (!this.currentRitual
                         .consumeAdditionalIngredients(this.world, this.pos, this.remainingAdditionalIngredients,
                                 this.currentTime)) {
                //if ingredients cannot be found, interrupt
                this.stopRitual(false);
                return;
            }

            if (this.currentRitual.totalTime >= 0 && this.currentTime >= this.currentRitual.totalTime)
                this.stopRitual(true);
        }
    }
    //endregion Overrides

    //region Methods
    public void restoreCastingPlayer() {
        //every 30 seconds try to restore the casting player
        if (this.castingPlayer == null && this.castingPlayerId != null &&
            this.world.getTotalWorldTime() % (20 * 30) == 0) {
            this.castingPlayer = EntityUtil.getPlayerByUUID(this.castingPlayerId);
            this.syncToClient();
        }
    }

    public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
        if (!world.isRemote) {
            ItemStack activationItem = player.getHeldItem(hand);
            if (activationItem == ItemStack.EMPTY)
                return false;

            if (this.currentRitual == null) {
                //Identify the ritual in the ritual registry.
                Ritual ritual = GameRegistry.findRegistry(Ritual.class).getValuesCollection().stream()
                                        .filter(r -> r.identify(world, pos, activationItem)).findFirst().orElse(null);

                if (ritual != null) {
                    if (ritual.isValid(world, pos, this, player, activationItem, ritual.additionalIngredients)) {
                        this.startRitual(player, activationItem, ritual);
                    }
                    else {
                        //if ritual is not valid, inform player.
                        player.sendStatusMessage(new TextComponentTranslation(ritual.getConditionsMessage()), true);
                        return false;
                    }
                }
                else {
                    player.sendStatusMessage(
                            new TextComponentTranslation(String.format("ritual.%s.does_not_exist", Occultism.MODID)),
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

    public void startRitual(EntityPlayer player, ItemStack activationItem, Ritual ritual) {
        if (!this.world.isRemote) {
            this.currentRitual = ritual;
            this.castingPlayerId = player.getUniqueID();
            this.castingPlayer = player;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.remainingAdditionalIngredients = new ArrayList<>(ritual.additionalIngredients);
            //place activation item in handler
            this.itemStackHandler.insertItem(0, activationItem.splitStack(1), false);
            this.currentRitual.start(this.world, this.pos, this, player, this.itemStackHandler.getStackInSlot(0));
            this.syncToClient();
        }
    }

    public void stopRitual(boolean finished) {
        if (!this.world.isRemote) {
            if (this.currentRitual != null && this.castingPlayer != null) {
                if (finished) {
                    ItemStack activationItem = this.itemStackHandler.getStackInSlot(0);
                    this.currentRitual.finish(this.world, this.pos, this, this.castingPlayer, activationItem);
                }
                else {
                    this.currentRitual.interrupt(this.world, this.pos, this, this.castingPlayer,
                            this.itemStackHandler.getStackInSlot(0));
                    //Pop activation item back into world
                    InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(),
                            this.itemStackHandler.extractItem(0, 1, false));
                }
            }
            this.currentRitual = null;
            this.castingPlayerId = null;
            this.castingPlayer = null;
            this.currentTime = 0;
            this.sacrificeProvided = false;
            this.remainingAdditionalIngredients.clear();
            this.syncToClient();
        }
    }


    public void notifySacrifice(EntityLivingBase entityLivingBase) {
        this.sacrificeProvided = true;
    }
    //endregion Methods
}
