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

import net.minecraft.world.item.Item;

public class FamiliarRingItem extends Item {

    public FamiliarRingItem(Properties properties) {
        super(properties);
    }

    //TODO: enable once curios is available
//    private static Curio getCurio(ItemStack stack) {
//        ICurio icurio = stack.getCapability(CuriosCapability.ITEM);
//        if (icurio != null && icurio instanceof Curio curio) {
//            if (stack.getTag() != null && stack.getTag().contains("familiar")) {
//                curio.deserializeNBT(stack.getTag().getCompound("familiar"));
//                stack.getTag().remove("familiar");
//            }
//            return curio;
//        }
//        return null;
//    }
//
//    public static IFamiliar getFamiliar(ItemStack stack, Level level) {
//        Curio curio = getCurio(stack);
//        return curio == null ? null : curio.getFamiliar(level);
//    }
//
//    @Override
//    public int getMaxStackSize(ItemStack stack) {
//        //force generation of a name if it does not exist yet.
//        //this might get around loot tables caching the stack
//        ItemNBTUtil.getBoundSpiritName(stack);
//        return super.getMaxStackSize(stack);
//    }
//
//    @Override
//    public boolean isFoil(ItemStack pStack) {
//        if (FMLLoader.getDist() == Dist.CLIENT)
//            return DistHelper.isFoil(pStack);
//        return false;
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
//                                TooltipFlag flagIn) {
//        super.appendHoverText(stack, worldIn, tooltip, flagIn);
//        if (stack.getOrCreateTag().getBoolean("occupied")) {
//            DistHelper.appendHoverText(stack, worldIn, tooltip, flagIn);
//        } else {
//            tooltip.add(Component.translatable(
//                    stack.getDescriptionId() + ".tooltip.empty"));
//        }
//    }
//
//    @Override
//    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target,
//                                                  InteractionHand hand) {
//        if (!playerIn.level().isClientSide && target instanceof IFamiliar familiar) {
//            if ((familiar.getFamiliarOwner() == playerIn || familiar.getFamiliarOwner() == null) && getCurio(stack).captureFamiliar(playerIn.level(), familiar)) {
//                OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.CAPTURE);
//                CompoundTag tag = stack.getOrCreateTag();
//                tag.putBoolean("occupied", true);
//                ItemNBTUtil.setBoundSpiritName(stack, familiar.getFamiliarEntity().getDisplayName().getString());
//                return InteractionResult.SUCCESS;
//            }
//        }
//
//        return super.interactLivingEntity(stack, playerIn, target, hand);
//    }
//
//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//
//        ItemStack stack = pContext.getPlayer().getItemInHand(pContext.getHand());
//        if (!pContext.getPlayer().level().isClientSide && getCurio(stack).releaseFamiliar(pContext.getPlayer(), pContext.getLevel())) {
//            CompoundTag tag = stack.getOrCreateTag();
//            tag.putBoolean("occupied", false);
//            return InteractionResult.sidedSuccess(pContext.getPlayer().level().isClientSide);
//        }
//
//        return InteractionResult.CONSUME;
//    }
//
//    @Override
//    public void verifyTagAfterLoad(CompoundTag pCompoundTag) {
//        super.verifyTagAfterLoad(pCompoundTag);
//
//        if (pCompoundTag.contains("forHandleFamiliarTypeTag")) {
//            return; //avoid recursion via handleFamiliarTypeTag
//        }
//
//        this.handleFamiliarTypeTag(pCompoundTag);
//    }
//
//    public void handleFamiliarTypeTag(CompoundTag tag) {
//        //we create an item stack to be able to modify the tag & access the capabilities.
//        //that stack will not be put anywhere
//        var stack = new ItemStack(this);
//        tag.putBoolean("forHandleFamiliarTypeTag", true);
//        stack.setTag(tag); //avoid recursion via verifyTagAfterLoad.
//        tag.remove("forHandleFamiliarTypeTag");
//
//        var server = ServerLifecycleHooks.getCurrentServer();
//        var icurio = stack.getCapability(CuriosCapability.ITEM);
//
//        //if we have a familiar type, that means we got a ring from e.g. a loot table.
//        //  it has no actual familiar nbt data, just the type to spawn, so we need to create a new familiar.
//        // Test with: /give @p occultism:familiar_ring{familiarType:"occultism:greedy_familiar"}
//        if (stack.hasTag() && stack.getTag().contains("familiarType") && icurio instanceof Curio curio && server != null) {
//            try {
//                EntityType<?> type = EntityType.byString(stack.getTag().getString("familiarType")).orElse(null);
//                if (type != null) {
//                    var level = ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);
//                    var entity = type.create(level);
//                    var familiar = (IFamiliar) entity;
//                    if (familiar != null) {
//                        curio.setFamiliar(familiar);
//
//                        stack.getTag().putBoolean("occupied", true);
//                        //now we also need to create the "familiar" nbt tag
//                        stack.getTag().put("familiar", curio.serializeNBT());
//                    }
//                }
//
//                stack.getTag().remove("familiarType");
//            } catch (Exception e) {
//                //we're brutally ignoring it. if it fails, it fails.
//                //this is just in case we do not have a server/level.
//            }
//        }
//    }
//
//    public static class Curio implements ICurio, INBTSerializable<CompoundTag> {
//        private final ItemStack stack;
//        private IFamiliar familiar;
//        private CompoundTag nbt;
//
//        public Curio(ItemStack stack) {
//            this.stack = stack;
//        }
//
//        private boolean captureFamiliar(Level level, IFamiliar familiar) {
//            if (this.getFamiliar(level) != null)
//                return false;
//
//            //otherwise is added to world is serialized
//            familiar.getFamiliarEntity().onRemovedFromWorld();
//            this.setFamiliar(familiar);
//            this.getFamiliar(level).getFamiliarEntity().stopRiding();
//            this.getFamiliar(level).getFamiliarEntity().ejectPassengers();
//            this.getFamiliar(level).getFamiliarEntity().remove(Entity.RemovalReason.DISCARDED);
//            return true;
//        }
//
//        private boolean releaseFamiliar(Player player, Level level) {
//            if (this.getFamiliar(level) != null
//                    && !this.getFamiliar(level).getFamiliarEntity().isAddedToWorld()) {
//                EntityType.loadEntityRecursive(this.getFamiliar(level).getFamiliarEntity().serializeNBT(), level, e -> {
//                    e.setPos(player.getX(), player.getY(), player.getZ());
//                    //on release overwrite owner -> familiar rings can be used to trade familiars.
//                    ((IFamiliar) e).setFamiliarOwner(player);
//
//                    var name = ItemNBTUtil.getBoundSpiritName(this.stack);
//                    e.setCustomName(Component.literal(name)); //set the name from the ring. the reverse happens when ring is used on entity.
//
//                    level.addFreshEntity(e);
//                    return e;
//                });
//                this.setFamiliar(null);
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        public ItemStack getStack() {
//            return this.stack;
//        }
//
//        @Override
//        public void curioTick(SlotContext slotContext) {
//            Level level = slotContext.entity().level();
//            IFamiliar familiar = this.getFamiliar(level);
//
//            if (familiar != null) {
//                // after portal use the level is still the pre-teleport level, the familiar owner is not found on the next check
//                // hence, we update the level, if the familiar is in a ring
//                if (!familiar.getFamiliarEntity().isAddedToWorld())
//                    familiar.getFamiliarEntity().setLevel(level);
//
//                if (familiar.getFamiliarOwner() != slotContext.entity())
//                    return;
//                // Apply effects
//                if (!level.isClientSide && slotContext.entity().tickCount % 20 == 0 && familiar.isEffectEnabled(slotContext.entity()))
//                    for (MobEffectInstance effect : familiar.getFamiliarEffects())
//                        familiar.getFamiliarOwner().addEffect(effect);
//
//                // Tick
//                familiar.curioTick(slotContext.entity());
//            }
//        }
//
//        @Override
//        public CompoundTag serializeNBT() {
//            CompoundTag compound = new CompoundTag();
//            compound.putBoolean("hasFamiliar", this.familiar != null || this.nbt != null);
//            if (this.familiar != null)
//                compound.put("familiar", this.familiar.getFamiliarEntity().serializeNBT());
//            else if (this.nbt != null)
//                compound.put("familiar", this.nbt);
//
//            return compound;
//        }
//
//        @Override
//        public void deserializeNBT(CompoundTag compound) {
//            if (compound.getBoolean("hasFamiliar"))
//                this.nbt = compound.getCompound("familiar");
//        }
//
//        // Need this because we cannot deserialize the familiar in deserializeNBT()
//        // because we have no level at that point
//        private IFamiliar getFamiliar(Level level) {
//            if (this.familiar != null)
//                return this.familiar;
//            if (this.nbt != null) {
//                this.familiar = (IFamiliar) EntityType.loadEntityRecursive(this.nbt, level, Function.identity());
//                this.nbt = null;
//            }
//
//            return this.familiar;
//        }
//
//        private void setFamiliar(IFamiliar familiar) {
//            this.familiar = familiar;
//            this.nbt = null;
//        }
//
//    }
//
//    public static class DistHelper {
//
//        public static void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
//                                           TooltipFlag flagIn) {
//            if (worldIn != null) {
//                var familiar = getFamiliar(stack, worldIn);
//                if (familiar != null) {
//                    var type = familiar.getFamiliarEntity().getType();
//                    tooltip.add(Component.translatable(
//                            stack.getDescriptionId() + ".tooltip",
//                            TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack)),
//                            Component.translatable(
//                                    stack.getDescriptionId() + ".tooltip.familiar_type",
//                                    TextUtil.formatDemonType(type.getDescription(), type)
//                            ).withStyle(ChatFormatting.ITALIC)
//                    ));
//                }
//            }
//        }
//
//        public static boolean isFoil(ItemStack pStack) {
//            var level = Minecraft.getInstance().level;
//            if (level == null)
//                return false;
//
//            var familiar = getFamiliar(pStack, level);
//            if (familiar != null) {
//                return familiar.isEffectEnabled(Minecraft.getInstance().player);
//            }
//            return false;
//        }
//
//    }
}
