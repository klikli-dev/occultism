package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.integration.apothicenchanting.ApothicEnchantingIntegration;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class KnowledgeTabletItem extends Item {
    public KnowledgeTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            //here we use main hand item as selected slot
            if (player.isShiftKeyDown()) {
                player.giveExperiencePoints(ItemNBTUtil.getStoredXP(stack));
                ItemNBTUtil.setStoredXP(stack, 0);
            } else {
                //Apothic class just hold the function, dont need mod loaded to use this
                ItemNBTUtil.setStoredXP(stack,
                        ItemNBTUtil.getStoredXP(stack)
                                + ApothicEnchantingIntegration.getTotalExperiencePointsForLevel(player.experienceLevel)
                                + (int)(serverPlayer.experienceProgress * serverPlayer.getXpNeededForNextLevel()));
                serverPlayer.setExperiencePoints(0);
                serverPlayer.setExperienceLevels(0);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack)),
                ChatFormatting.GREEN.toString() + ItemNBTUtil.getStoredXP(pStack) + ChatFormatting.RESET));
    }
}
