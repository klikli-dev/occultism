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
            if (familiar.getFamiliarOwner() == playerIn && getCurio(stack).captureFamiliar(playerIn.world, familiar)) {
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
        if (!playerIn.world.isRemote && getCurio(stack).releaseFamiliar(playerIn, worldIn)) {
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
            if (getFamiliar(world) != null)
                return false;
            setFamiliar(familiar);
            getFamiliar(world).getEntity().remove();
            return true;
        }

        private boolean releaseFamiliar(PlayerEntity player, World world) {
            if (getFamiliar(world) != null && getFamiliar(world).getFamiliarOwner() == player
                    && !getFamiliar(world).getEntity().isAddedToWorld()) {
                EntityType.loadEntityAndExecute(getFamiliar(world).getEntity().serializeNBT(), world, e -> {
                    e.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                    world.addEntity(e);
                    return e;
                });
                setFamiliar(null);
                return true;
            }
            return false;
        }

        @Override
        public void curioTick(String identifier, int index, LivingEntity entity) {
            World world = entity.world;
            if (getFamiliar(world) == null)
                return;

            if (!world.isRemote && entity.ticksExisted % 20 == 0 && getFamiliar(world).getFamiliarOwner() == entity)
                for (EffectInstance effect : getFamiliar(world).getFamiliarEffects())
                    getFamiliar(world).getFamiliarOwner().addPotionEffect(effect);
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("hasFamiliar", familiar != null || nbt != null);
            if (familiar != null)
                compound.put("familiar", familiar.getEntity().serializeNBT());
            else if (nbt != null)
                compound.put("familiar", nbt);

            return compound;
        }

        @Override
        public void deserializeNBT(CompoundNBT compound) {
            if (compound.getBoolean("hasFamiliar"))
                nbt = compound.getCompound("familiar");
        }

        // Need this because we cannot deserialize the familiar in deserializeNBT()
        // because we have no world at that point
        private IFamiliar getFamiliar(World world) {
            if (familiar != null)
                return familiar;
            if (nbt != null) {
                familiar = (IFamiliar) EntityType.loadEntityAndExecute(nbt, world, Function.identity());
                nbt = null;
            }

            return familiar;
        }

        private void setFamiliar(IFamiliar familiar) {
            this.familiar = familiar;
            nbt = null;
        }

    }

    private static class Provider implements ICapabilitySerializable<CompoundNBT> {

        private Curio curio;
        private LazyOptional<ICurio> instance = LazyOptional.of(this::get);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return CuriosCapability.ITEM.orEmpty(cap, instance);
        }

        @Override
        public CompoundNBT serializeNBT() {
            return get().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            get().deserializeNBT(nbt);
        }

        private Curio get() {
            if (curio == null)
                curio = new Curio();
            return curio;
        }

    }

}
