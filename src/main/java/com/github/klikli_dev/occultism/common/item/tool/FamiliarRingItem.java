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

import com.github.klikli_dev.occultism.common.entity.IFamiliar;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class FamiliarRingItem extends Item {

    public FamiliarRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
            ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().getBoolean("occupied"))
            tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip",
                    TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target,
            InteractionHand hand) {
        if (!playerIn.level.isClientSide && target instanceof IFamiliar) {
            IFamiliar familiar = (IFamiliar) target;
            if ((familiar.getFamiliarOwner() == playerIn || familiar.getFamiliarOwner() == null) && this.getCurio(stack).captureFamiliar(playerIn.level, familiar)) {
                CompoundTag tag = stack.getOrCreateTag();
                tag.putBoolean("occupied", true);
                ItemNBTUtil.setBoundSpiritName(stack, familiar.getEntity().getDisplayName().getString());
                return InteractionResult.CONSUME;
            }
        }
        
        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (!playerIn.level.isClientSide && this.getCurio(stack).releaseFamiliar(playerIn, worldIn)) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putBoolean("occupied", false);
            return InteractionResultHolder.resultConsume(stack);
        }
        return super.use(worldIn, playerIn, handIn);
    }

    private Curio getCurio(ItemStack stack) {
        ICurio curio = stack.getCapability(CuriosCapability.ITEM).orElse(null);
        if (curio != null && curio instanceof Curio)
            return (Curio) curio;
        return null;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new Provider();
    }

    private static class Curio implements ICurio, INBTSerializable<CompoundTag> {
        private IFamiliar familiar;
        private CompoundTag nbt;

        private boolean captureFamiliar(Level level, IFamiliar familiar) {
            if (this.getFamiliar(level) != null)
                return false;
            this.setFamiliar(familiar);
            this.getFamiliar(level).getEntity().remove();
            return true;
        }

        private boolean releaseFamiliar(Player player, Level level) {
            if (this.getFamiliar(level) != null
                    && !this.getFamiliar(level).getEntity().isAddedToWorld()) {
                EntityType.loadEntityAndExecute(this.getFamiliar(level).getEntity().serializeNBT(), level, e -> {
                    e.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                    //on release overwrite owner -> familiar rings can be used to trade familiars.
                    ((IFamiliar)e).setFamiliarOwner(player);
                    level.addEntity(e);
                    return e;
                });
                this.setFamiliar(null);
                return true;
            }
            return false;
        }

        @Override
        public void curioTick(String identifier, int index, LivingEntity entity) {
            Level level = entity.level;
            IFamiliar familiar = this.getFamiliar(level);
            if (familiar == null || familiar.getFamiliarOwner() != entity)
                return;
            
            // Apply effects
            if (!level.isClientSide && entity.this.tickCount % 20 == 0)
                for (MobEffectInstance effect : familiar.getFamiliarEffects())
                    familiar.getFamiliarOwner().addPotionEffect(effect);
            
            // Tick
            familiar.curioTick(entity);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag compound = new CompoundTag();
            compound.putBoolean("hasFamiliar", this.familiar != null || this.nbt != null);
            if (this.familiar != null)
                compound.put("familiar", this.familiar.getEntity().serializeNBT());
            else if (this.nbt != null)
                compound.put("familiar", this.nbt);

            return compound;
        }

        @Override
        public void deserializeNBT(CompoundTag compound) {
            if (compound.getBoolean("hasFamiliar"))
                this.nbt = compound.getCompound("familiar");
        }

        // Need this because we cannot deserialize the familiar in deserializeNBT()
        // because we have no level at that point
        private IFamiliar getFamiliar(Level level) {
            if (this.familiar != null)
                return this.familiar;
            if (this.nbt != null) {
                this.familiar = (IFamiliar) EntityType.loadEntityAndExecute(this.nbt, level, Function.identity());
                this.nbt = null;
            }

            return this.familiar;
        }

        private void setFamiliar(IFamiliar familiar) {
            this.familiar = familiar;
            this.nbt = null;
        }

    }

    private static class Provider implements ICapabilitySerializable<CompoundTag> {

        private Curio curio;
        private LazyOptional<ICurio> instance = LazyOptional.of(this::get);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return CuriosCapability.ITEM.orEmpty(cap, this.instance);
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.get().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.get().deserializeNBT(nbt);
        }

        private Curio get() {
            if (this.curio == null)
                this.curio = new Curio();
            return this.curio;
        }

    }

}
