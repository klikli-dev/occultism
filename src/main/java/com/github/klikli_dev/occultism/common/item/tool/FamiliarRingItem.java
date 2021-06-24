/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.common.item.tool;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.klikli_dev.occultism.common.entity.IFamiliar;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class FamiliarRingItem extends Item {

    public FamiliarRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
            ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().getBoolean("occupied"))
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip",
                    TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target,
            Hand hand) {
        if (!playerIn.world.isRemote && target instanceof IFamiliar) {
            IFamiliar familiar = (IFamiliar) target;
            if (familiar.getFamiliarOwner() == playerIn && this.getCurio(stack).captureFamiliar(playerIn.world, familiar)) {
                CompoundNBT tag = stack.getOrCreateTag();
                tag.putBoolean("occupied", true);
                ItemNBTUtil.setBoundSpiritName(stack, familiar.getEntity().getDisplayName().getString());
                return ActionResultType.CONSUME;
            }
        }
        
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!playerIn.world.isRemote && this.getCurio(stack).releaseFamiliar(playerIn, worldIn)) {
            CompoundNBT tag = stack.getOrCreateTag();
            tag.putBoolean("occupied", false);
            return ActionResult.resultConsume(stack);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private Curio getCurio(ItemStack stack) {
        ICurio curio = stack.getCapability(CuriosCapability.ITEM).orElse(null);
        if (curio != null && curio instanceof Curio)
            return (Curio) curio;
        return null;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return new Provider();
    }

    private static class Curio implements ICurio, INBTSerializable<CompoundNBT> {
        private IFamiliar familiar;
        private CompoundNBT nbt;

        private boolean captureFamiliar(World world, IFamiliar familiar) {
            if (this.getFamiliar(world) != null)
                return false;
            this.setFamiliar(familiar);
            this.getFamiliar(world).getEntity().remove();
            return true;
        }

        private boolean releaseFamiliar(PlayerEntity player, World world) {
            if (this.getFamiliar(world) != null && this.getFamiliar(world).getFamiliarOwner() == player
                    && !this.getFamiliar(world).getEntity().isAddedToWorld()) {
                EntityType.loadEntityAndExecute(this.getFamiliar(world).getEntity().serializeNBT(), world, e -> {
                    e.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                    //on release overwrite owner -> familiar rings can be used to trade familiars.
                    ((IFamiliar)e).setFamiliarOwner(player);
                    world.addEntity(e);
                    return e;
                });
                this.setFamiliar(null);
                return true;
            }
            return false;
        }

        @Override
        public void curioTick(String identifier, int index, LivingEntity entity) {
            World world = entity.world;
            IFamiliar familiar = this.getFamiliar(world);
            if (familiar == null || familiar.getFamiliarOwner() != entity)
                return;
            
            // Apply effects
            if (!world.isRemote && entity.ticksExisted % 20 == 0)
                for (EffectInstance effect : familiar.getFamiliarEffects())
                    familiar.getFamiliarOwner().addPotionEffect(effect);
            
            // Tick
            familiar.curioTick(entity);
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("hasFamiliar", this.familiar != null || this.nbt != null);
            if (this.familiar != null)
                compound.put("familiar", this.familiar.getEntity().serializeNBT());
            else if (this.nbt != null)
                compound.put("familiar", this.nbt);

            return compound;
        }

        @Override
        public void deserializeNBT(CompoundNBT compound) {
            if (compound.getBoolean("hasFamiliar"))
                this.nbt = compound.getCompound("familiar");
        }

        // Need this because we cannot deserialize the familiar in deserializeNBT()
        // because we have no world at that point
        private IFamiliar getFamiliar(World world) {
            if (this.familiar != null)
                return this.familiar;
            if (this.nbt != null) {
                this.familiar = (IFamiliar) EntityType.loadEntityAndExecute(this.nbt, world, Function.identity());
                this.nbt = null;
            }

            return this.familiar;
        }

        private void setFamiliar(IFamiliar familiar) {
            this.familiar = familiar;
            this.nbt = null;
        }

    }

    private static class Provider implements ICapabilitySerializable<CompoundNBT> {

        private Curio curio;
        private LazyOptional<ICurio> instance = LazyOptional.of(this::get);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return CuriosCapability.ITEM.orEmpty(cap, this.instance);
        }

        @Override
        public CompoundNBT serializeNBT() {
            return this.get().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            this.get().deserializeNBT(nbt);
        }

        private Curio get() {
            if (this.curio == null)
                this.curio = new Curio();
            return this.curio;
        }

    }

}
