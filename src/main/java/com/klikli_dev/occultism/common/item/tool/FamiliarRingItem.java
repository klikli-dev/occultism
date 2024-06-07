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

package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.function.Function;

public class FamiliarRingItem extends Item {

    public FamiliarRingItem(Properties properties) {
        super(properties);
    }

    private static Curio getCurio(ItemStack stack) {
        ICurio icurio = stack.getCapability(CuriosCapability.ITEM);
        if (icurio != null && icurio instanceof Curio curio) {
            return curio;
        }
        return null;
    }

    public static IFamiliar getFamiliar(ItemStack stack, Level level) {
        Curio curio = getCurio(stack);
        return curio == null ? null : curio.getFamiliar(level);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        //force generation of a name if it does not exist yet.
        //this might get around loot tables caching the stack
        ItemNBTUtil.getBoundSpiritName(stack);
        return super.getMaxStackSize(stack);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        if (FMLLoader.getDist() == Dist.CLIENT)
            return DistHelper.isFoil(pStack);
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        if (pStack.has(OccultismDataComponents.OCCUPIED) && FMLLoader.getDist() == Dist.CLIENT) {
            DistHelper.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        } else {
            pTooltipComponents.add(Component.translatable(
                    pStack.getDescriptionId() + ".tooltip.empty"));
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target,
                                                  InteractionHand hand) {
        if (!playerIn.level().isClientSide && target instanceof IFamiliar familiar) {
            if ((familiar.getFamiliarOwner() == playerIn || familiar.getFamiliarOwner() == null) && getCurio(stack).captureFamiliar(playerIn.level(), familiar)) {
                OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.CAPTURE);
                stack.set(OccultismDataComponents.OCCUPIED, true);
                ItemNBTUtil.setBoundSpiritName(stack, familiar.getFamiliarEntity().getDisplayName().getString());
                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        ItemStack stack = pContext.getPlayer().getItemInHand(pContext.getHand());
        if (!pContext.getPlayer().level().isClientSide && getCurio(stack).releaseFamiliar(pContext.getPlayer(), pContext.getLevel())) {
            stack.set(OccultismDataComponents.OCCUPIED, false);
            return InteractionResult.sidedSuccess(pContext.getPlayer().level().isClientSide);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack pStack) {
        super.verifyComponentsAfterLoad(pStack);

        this.handleFamiliarTypeTag(pStack);

    }

    public void handleFamiliarTypeTag(ItemStack pStack) {
        var server = ServerLifecycleHooks.getCurrentServer();
        var icurio = pStack.getCapability(CuriosCapability.ITEM);

        //if we have a familiar type, that means we got a ring from e.g. a loot table.
        //  it has no actual familiar nbt data, just the type to spawn, so we need to create a new familiar.
        // Test with: /give @p occultism:familiar_ring{familiarType:"occultism:greedy_familiar"}
        if (pStack.has(OccultismDataComponents.FAMILIAR_TYPE) && icurio instanceof Curio curio && server != null) {
            try {
                EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(pStack.get(OccultismDataComponents.FAMILIAR_TYPE)).orElse(null);
                if (type != null) {
                    var level = ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);
                    var entity = type.create(level);
                    var familiar = (IFamiliar) entity;
                    if (familiar != null) {
                        curio.setFamiliar(familiar, server.registryAccess());

                        pStack.set(OccultismDataComponents.OCCUPIED, true);
                        //now we also need to create the "familiar" component
                        pStack.set(OccultismDataComponents.FAMILIAR_DATA, CustomData.of(curio.serializeNBT(server.registryAccess())));
                    }
                }
                pStack.remove(OccultismDataComponents.FAMILIAR_TYPE);
            } catch (Exception e) {
                //we're brutally ignoring it. if it fails, it fails.
                //this is just in case we do not have a server/level.
            }
        }
    }

    public static class Curio implements ICurio, INBTSerializable<CompoundTag> {
        private final ItemStack stack;
        private IFamiliar familiar;
        private CompoundTag cachedNbt;

        public Curio(ItemStack stack) {
            this.stack = stack;
        }

        private boolean captureFamiliar(Level level, IFamiliar familiar) {
            if (this.getFamiliar(level) != null)
                return false;

            //otherwise is added to world is serialized
            familiar.getFamiliarEntity().onRemovedFromWorld();
            this.setFamiliar(familiar, level.registryAccess());
            this.getFamiliar(level).getFamiliarEntity().stopRiding();
            this.getFamiliar(level).getFamiliarEntity().ejectPassengers();
            this.getFamiliar(level).getFamiliarEntity().remove(Entity.RemovalReason.DISCARDED);
            return true;
        }

        private boolean releaseFamiliar(Player player, Level level) {
            if (this.getFamiliar(level) != null
                    && !this.getFamiliar(level).getFamiliarEntity().isAddedToWorld()) {
                EntityType.loadEntityRecursive(this.getFamiliar(level).getFamiliarEntity().serializeNBT(level.registryAccess()), level, e -> {
                    e.setPos(player.getX(), player.getY(), player.getZ());
                    //on release overwrite owner -> familiar rings can be used to trade familiars.
                    ((IFamiliar) e).setFamiliarOwner(player);

                    var name = ItemNBTUtil.getBoundSpiritName(this.stack);
                    e.setCustomName(Component.literal(name)); //set the name from the ring. the reverse happens when ring is used on entity.

                    level.addFreshEntity(e);
                    return e;
                });
                this.setFamiliar(null, level.registryAccess());
                return true;
            }
            return false;
        }

        @Override
        public ItemStack getStack() {
            return this.stack;
        }

        @Override
        public void curioTick(SlotContext slotContext) {
            Level level = slotContext.entity().level();
            IFamiliar familiar = this.getFamiliar(level);

            if (familiar != null) {
                // after portal use the level is still the pre-teleport level, the familiar owner is not found on the next check
                // hence, we update the level, if the familiar is in a ring
                if (!familiar.getFamiliarEntity().isAddedToWorld())
                    familiar.getFamiliarEntity().setLevel(level);

                if (familiar.getFamiliarOwner() != slotContext.entity())
                    return;
                // Apply effects
                if (!level.isClientSide && slotContext.entity().tickCount % 20 == 0 && familiar.isEffectEnabled(slotContext.entity()))
                    for (MobEffectInstance effect : familiar.getFamiliarEffects())
                        familiar.getFamiliarOwner().addEffect(effect);

                // Tick
                familiar.curioTick(slotContext.entity());
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider provider) {
            CompoundTag compound = new CompoundTag();
            compound.putBoolean("hasFamiliar", this.familiar != null || this.cachedNbt != null);
            if (this.familiar != null)
                compound.put("familiar", this.familiar.getFamiliarEntity().serializeNBT(provider));
            else if (this.cachedNbt != null)
                compound.put("familiar", this.cachedNbt);

            return compound;
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compound) {
            if (compound.getBoolean("hasFamiliar"))
                this.cachedNbt = compound.getCompound("familiar");
        }

        // Need this because we cannot deserialize the familiar in deserializeNBT()
        // because we have no level at that point
        private IFamiliar getFamiliar(Level level) {
            if (this.familiar != null)
                return this.familiar;

            var data = this.stack.get(OccultismDataComponents.FAMILIAR_DATA);
            var tag = data == null ?  null : data.getUnsafe();
            if (tag != null && (this.cachedNbt == null || !this.cachedNbt.equals(tag))) {
                this.deserializeNBT(level.registryAccess(), tag);
            }

            if (this.cachedNbt != null) {
                this.familiar = (IFamiliar) EntityType.loadEntityRecursive(this.cachedNbt, level, Function.identity());
                return this.familiar;
            }

            return this.familiar;
        }

        private void setFamiliar(IFamiliar familiar, HolderLookup.Provider provider) {
            this.familiar = familiar;

            this.cachedNbt = this.familiar != null ? this.familiar.getFamiliarEntity().serializeNBT(provider) : null;
            this.stack.set(OccultismDataComponents.FAMILIAR_DATA, CustomData.of(this.serializeNBT(provider)));
        }

    }

    public static class DistHelper {

        public static void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> tooltip,
                                           TooltipFlag flagIn) {
            var level = Minecraft.getInstance().level; //we no longer get it handed over from MC, so we get i there
            if (level != null) {
                var familiar = getFamiliar(stack, level);
                if (familiar != null) {
                    var type = familiar.getFamiliarEntity().getType();
                    tooltip.add(Component.translatable(
                            stack.getDescriptionId() + ".tooltip",
                            TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack)),
                            Component.translatable(
                                    stack.getDescriptionId() + ".tooltip.familiar_type",
                                    TextUtil.formatDemonType(type.getDescription(), type)
                            ).withStyle(ChatFormatting.ITALIC)
                    ));
                }
            }
        }

        public static boolean isFoil(ItemStack pStack) {
            var level = Minecraft.getInstance().level;
            if (level == null)
                return false;

            var familiar = getFamiliar(pStack, level);
            if (familiar != null) {
                return familiar.isEffectEnabled(Minecraft.getInstance().player);
            }
            return false;
        }

    }
}
