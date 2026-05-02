package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.integration.apothicenchanting.ApothicEnchantingIntegration;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class KnowledgeTabletItem extends Item {
    public KnowledgeTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, @NotNull InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            //here we use main hand item as selected slot
            if (serverPlayer.isShiftKeyDown()) {
                serverPlayer.giveExperiencePoints(ItemNBTUtil.getStoredXP(stack));
                ItemNBTUtil.setStoredXP(stack, 0);
            } else {
                int storeXP = ItemNBTUtil.getStoredXP(stack);
                float xpProgress = serverPlayer.experienceProgress * serverPlayer.getXpNeededForNextLevel();
                if (storeXP + xpProgress >= 0) {
                    serverPlayer.setExperiencePoints(0);
                    storeXP += (int) xpProgress;
                    boolean flag = true;
                    while (serverPlayer.experienceLevel > 0 && flag) {
                        //Apothic class just hold the function, don't need mod loaded to use this
                        int xpByLvl = ApothicEnchantingIntegration.getExperienceForLevel(serverPlayer.experienceLevel);
                        if (storeXP + xpByLvl > 0) {
                            storeXP += xpByLvl;
                            serverPlayer.experienceLevel -= 1;
                        } else {
                            ItemNBTUtil.setStoredXP(stack, Integer.MAX_VALUE);
                            serverPlayer.giveExperiencePoints(storeXP - Integer.MAX_VALUE);
                            flag = false;
                        }
                    }
                    ItemNBTUtil.setStoredXP(stack, storeXP);
                } else {
                    ItemNBTUtil.setStoredXP(stack, Integer.MAX_VALUE);
                    serverPlayer.giveExperiencePoints(storeXP - Integer.MAX_VALUE);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext,
                                @NotNull TooltipDisplay pTooltipDisplay, @NotNull Consumer<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.accept(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack)),
                ChatFormatting.GREEN.toString() + ItemNBTUtil.getStoredXP(pStack) + ChatFormatting.RESET));
    }
}
