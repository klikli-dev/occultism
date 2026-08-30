package com.klikli_dev.occultism.common.item.familiar;

import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jspecify.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface FamiliarCurio {
    static Curio getCurio(ItemStack stack) {
        ICurio icurio = stack.getCapability(CuriosCapability.ITEM);
        if (icurio instanceof Curio curio) {
            return curio;
        }
        return null;
    }

    static List<IFamiliar> getFamiliar(ItemStack stack, Level level) {
        Curio curio = getCurio(stack);
        return curio == null ? null : curio.getFamiliars(level);
    }

    default void handleFamiliarTypeTag(ItemStack pStack) {
        //if we have a familiar type, that means we got a ring from e.g. a loot table.
        //  it has no actual familiar nbt data, just the type to spawn, so we need to create a new familiar.
        // Test with: /give @p occultism:familiar_ring{familiarType:"occultism:greedy_familiar"}
        var server = ServerLifecycleHooks.getCurrentServer();
        if (pStack.has(OccultismDataComponents.FAMILIAR_TYPE) && server != null) {
            try {
                List<Identifier> list = pStack.get(OccultismDataComponents.FAMILIAR_TYPE);
                if (list != null) {
                    var curio = getCurio(pStack);
                    var level = server.getLevel(Level.OVERWORLD);
                    if (curio != null && level != null) {
                        for (Identifier identifier : list) {
                            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(identifier).orElse(null);
                            if (type != null) {
                                var entity = type.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
                                var familiar = (IFamiliar) entity;
                                if (familiar != null)
                                    curio.addFamiliar(level, familiar, server.registryAccess());
                            }
                        }
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

    default void familiarInventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (slot != null && (slot.isArmor() || equippedTool(itemStack, slot))
                    && owner instanceof LivingEntity living
                    && FamiliarCurio.getCurio(itemStack) instanceof Curio curio)
            curio.curioTick(new SlotContext(slot.name(), living, slot.getIndex(), false, true));
    }

    private boolean equippedTool(ItemStack itemStack, EquipmentSlot slot) {
        return itemStack.is(Tags.Items.TOOLS) && slot.getType().equals(EquipmentSlot.Type.HAND);
    }

    default InteractionResult familiarInteractLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (!playerIn.level().isClientSide() && target instanceof IFamiliar familiar) {
            Curio curio = FamiliarCurio.getCurio(stack);
            if ((familiar.getFamiliarOwner() == playerIn || familiar.getFamiliarOwner() == null) && curio != null
                    && curio.getFamiliars(playerIn.level()).size() < ItemNBTUtil.getMaxFamiliar(stack)
                    && curio.captureFamiliar(playerIn.level(), familiar)) {
                target.level().playSound(null, target.getOnPos(), OccultismSounds.POOF.get(), SoundSource.NEUTRAL, 1, 1);
                ((ServerLevel) target.level()).sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(),
                        target.getX(), target.getY() + target.getHitbox().getYsize()*0.8, target.getZ(),
                        15, 0.0, 0.0, 0.0, 0.01);
                OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.CAPTURE);
                stack.set(OccultismDataComponents.OCCUPIED, true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    default InteractionResult familiarUseOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        if (player == null)
            return InteractionResult.FAIL;
        Level level = player.level();
        ItemStack stack = player.getItemInHand(pContext.getHand());
        Curio curio = getCurio(stack);
        if (!level.isClientSide() && curio != null && !curio.getFamiliars(level).isEmpty() &&
                curio.releaseFamiliar(player, level, curio.getFamiliars(level).getFirst())) {
            stack.set(OccultismDataComponents.OCCUPIED, false);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    class Curio implements ICurio {
        private final ItemStack stack;
        private final List<IFamiliar> familiars = new ArrayList<>();
        private final List<CompoundTag> cachedNbt = new ArrayList<>();

        public Curio(ItemStack stack) {
            this.stack = stack;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public boolean captureFamiliar(Level level, IFamiliar familiar) {
            familiar.getFamiliarEntity().onRemovedFromLevel();
            this.addFamiliar(level, familiar, level.registryAccess());
            familiar.getFamiliarEntity().stopRiding();
            familiar.getFamiliarEntity().ejectPassengers();
            familiar.getFamiliarEntity().remove(Entity.RemovalReason.DISCARDED);
            return true;
        }

        public boolean releaseFamiliar(Player player, Level level, IFamiliar familiar) {
            if (familiar == null || familiar.getFamiliarEntity().isAddedToLevel())
                return false;

            var output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
            familiar.getFamiliarEntity().saveAsPassenger(output);
            EntityType.loadEntityRecursive(output.buildResult(), level, EntitySpawnReason.LOAD, e -> {
                e.setPos(player.getX(), player.getY(), player.getZ());
                ((IFamiliar) e).setFamiliarOwner(player);
                level.addFreshEntity(e);

                return e;
            });

            this.clearFamiliar(level, familiar, level.registryAccess());
            return true;
        }

        @Override
        public void curioTick(SlotContext slotContext) {
            LivingEntity entity = slotContext.entity();
            Level level = entity.level();

            for (IFamiliar familiar : this.getFamiliars(level)) {
                if (!familiar.getFamiliarEntity().isAddedToLevel())
                    familiar.getFamiliarEntity().setLevel(level);

                if (familiar.getFamiliarOwner() != entity)
                    continue;

                if (!level.isClientSide() && entity.tickCount % 20 == 0 && familiar.isEffectEnabled(entity)) {
                    for (MobEffectInstance effect : familiar.getFamiliarEffects())
                        entity.addEffect(effect);
                }

                familiar.curioTick(entity);
            }
        }

        // Need this because we cannot deserialize the familiar in deserializeNBT()
        // because we have no level at that point
        private List<IFamiliar> getFamiliars(Level level) {
            if (!this.familiars.isEmpty())
                return this.familiars;

            if (!level.isClientSide() && this.stack.getItem() instanceof FamiliarCurio familiarItem)
                familiarItem.handleFamiliarTypeTag(this.stack);

            var data = this.stack.get(OccultismDataComponents.FAMILIAR_DATA);
            var tag = data == null ? null : data.copyTag();
            if (tag != null) {
                this.deserializeNBT(level.registryAccess(), tag);
            }

            for (CompoundTag nbt : this.cachedNbt) {
                var familiar = (IFamiliar) EntityType.loadEntityRecursive(nbt, level, EntitySpawnReason.LOAD, e -> e);
                if (familiar != null)
                    this.familiars.add(familiar);
            }

            return this.familiars;
        }

        private void addFamiliar(Level level, IFamiliar familiar, HolderLookup.Provider provider) {
            this.getFamiliars(level);
            this.familiars.add(familiar);
            var output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);

            if (familiar.getFamiliarEntity().saveAsPassenger(output))
                this.cachedNbt.add(output.buildResult());

            this.stack.set(
                    OccultismDataComponents.FAMILIAR_DATA,
                    CustomData.of(this.serializeNBT(provider))
            );
        }

        private void clearFamiliar(Level level, IFamiliar familiar, HolderLookup.Provider provider) {
            this.getFamiliars(level);
            int index = this.familiars.indexOf(familiar);
            if (index < 0)
                return;

            this.familiars.remove(index);
            if (index < this.cachedNbt.size())
                this.cachedNbt.remove(index);

            this.stack.set(
                    OccultismDataComponents.FAMILIAR_DATA,
                    CustomData.of(this.serializeNBT(provider))
            );
        }

        public CompoundTag serializeNBT(HolderLookup.Provider provider) {
            CompoundTag compound = new CompoundTag();
            ListTag list = new ListTag();

            if (!this.familiars.isEmpty()) {
                for (IFamiliar familiar : this.familiars) {
                    var output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
                    if (familiar.getFamiliarEntity().saveAsPassenger(output))
                        list.add(output.buildResult());
                }

            } else {
                for (CompoundTag tag : this.cachedNbt)
                    list.add(tag.copy());
            }

            compound.put("familiars", list);
            return compound;
        }

        public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compound) {
            this.cachedNbt.clear();
            this.familiars.clear();
            ListTag list = compound.getListOrEmpty("familiars");
            for (int i = 0; i < list.size(); i++) {
                this.cachedNbt.add(list.getCompoundOrEmpty(i));
            }
        }

    }

    class DistHelper {

        public static void appendHoverText(ItemStack stack, Item.TooltipContext pContext, Consumer<Component> tooltip,
                                           TooltipFlag flagIn) {
            var level = Minecraft.getInstance().level; //we no longer get it handed over from MC, so we get i there
            if (level != null) {
                List<IFamiliar> familiarList = getFamiliar(stack, level);
                if (familiarList != null && !familiarList.isEmpty()) {
                    for (IFamiliar familiar : familiarList) {
                        var type = familiar.getFamiliarEntity().getType();
                        tooltip.accept(Component.translatable(
                                stack.getItem().getDescriptionId() + ".tooltip",
                                TextUtil.formatDemonName(familiar.getFamiliarEntity().getName().copy())));
                        tooltip.accept(Component.translatable(
                                stack.getItem().getDescriptionId() + ".tooltip.familiar_type",
                                type.getDescription()).withStyle(ChatFormatting.ITALIC));
                    }
                }
            }
        }

        public static boolean isFoil(ItemStack pStack) {
            var level = Minecraft.getInstance().level;
            if (level == null)
                return false;

            List<IFamiliar> familiarList = getFamiliar(pStack, level);
            if (familiarList != null)
                for (IFamiliar familiar : familiarList)
                    if (familiar != null && familiar.isEffectEnabled(Minecraft.getInstance().player))
                        return true;
            return false;
        }

    }
}
