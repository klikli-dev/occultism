package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

import javax.annotation.Nullable;

public class VitalityCompassItem extends Item {

    private final CompassWobble wobbleRandom = new CompassWobble();
    public static final float NOT_FOUND = 0;

    public VitalityCompassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        if (!target.isAlive())
            return InteractionResult.PASS;

        //This is called from PlayerEventHandler#onPlayerRightClickEntity, because we need to bypass sitting entities processInteraction
        if (target.level().isClientSide())
            return InteractionResult.PASS;

        if (target.getType().builtInRegistryHolder().is(Entities.VITALITY_COMPASS_DENY_LIST)) {
            player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".message.target_blocked", target.getName()));
            return InteractionResult.FAIL;
        } else {
            ItemNBTUtil.setSpiritEntityUUID(stack, target.getUUID());
            ItemNBTUtil.setBoundSpiritName(stack, target.getName().getString());
            player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".message.target_linked", target.getName()));
            player.swing(hand);
            player.setItemInHand(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
            player.inventoryMenu.broadcastChanges();
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pTooltipFlag);
        pTooltipAdder.accept(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack))));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (stack.has(OccultismDataComponents.SPIRIT_ENTITY_UUID)) {
            Entity target = level.getEntity(stack.get(OccultismDataComponents.SPIRIT_ENTITY_UUID));
            if (target != null && target.level().dimension() == entity.level().dimension()) {
                stack.set(OccultismDataComponents.COMPASS_ANGLE, this.getRotationTowardsCompassTarget(entity, target.blockPosition()));
            } else {
                stack.set(OccultismDataComponents.COMPASS_ANGLE, this.getRandomlySpinningRotation(entity.getId(), level.getGameTime()));
            }
        } else {
            stack.set(OccultismDataComponents.COMPASS_ANGLE, this.getRandomlySpinningRotation(entity.getId(), level.getGameTime()));
        }
    }

    private float getRotationTowardsCompassTarget(Entity entity, BlockPos pos) {
        double d0 = this.getAngleFromEntityToPos(entity, pos);
        double d1 = this.getWrappedVisualRotationY(entity);
        if (entity instanceof Player player) {
            if (player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
                return Mth.positiveModulo((float)d0, 1.0F);
            }
        }

        double d2 = 0.5 - (d1 - 0.25 - d0);
        return Mth.positiveModulo((float)d2, 1.0F);
    }

    private double getAngleFromEntityToPos(Entity entity, BlockPos pos) {
        Vec3 vec3 = Vec3.atCenterOf(pos);
        return Math.atan2(vec3.z() - entity.getZ(), vec3.x() - entity.getX()) / 6.2831854820251465;
    }

    private double getWrappedVisualRotationY(Entity entity) {
        return entity instanceof Player player?  Mth.positiveModulo((player.getYHeadRot() / 360.0F), 1.0): Mth.positiveModulo((entity.getVisualRotationYInDegrees() / 360.0F), 1.0);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    private float getRandomlySpinningRotation(int seed, long ticks) {
        if (this.wobbleRandom.shouldUpdate(ticks)) {
            this.wobbleRandom.update(ticks, Math.random());
        }

        double d0 = this.wobbleRandom.rotation + (double)((float)this.hash(seed) / 2.14748365E9F);
        return Mth.positiveModulo((float)d0, 1.0F);
    }

    private int hash(int value) {
        return value * 1327217883;
    }

    static class CompassWobble {
        double rotation;
        private double deltaRotation;
        private long lastUpdateTick;

        CompassWobble() {
        }

        boolean shouldUpdate(long ticks) {
            return this.lastUpdateTick != ticks;
        }

        void update(long ticks, double rotation) {
            this.lastUpdateTick = ticks;
            double d0 = rotation - this.rotation;
            d0 = Mth.positiveModulo(d0 + 0.5, 1.0) - 0.5;
            this.deltaRotation += d0 * 0.1;
            this.deltaRotation *= 0.8;
            this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0);
        }
    }
}
