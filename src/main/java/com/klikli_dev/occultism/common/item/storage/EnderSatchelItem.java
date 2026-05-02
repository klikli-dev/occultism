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

package com.klikli_dev.occultism.common.item.storage;

import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class EnderSatchelItem extends Item {


    public EnderSatchelItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, @NotNull InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {

            if (player.isShiftKeyDown()) {
                if (ItemNBTUtil.getLikedPlayerName(stack) == null) {
                    ItemNBTUtil.setLikedPlayerName(stack, player.getName().getString());
                    ItemNBTUtil.setLinkedPlayerUUID(stack, serverPlayer.getUUID());
                    level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1F, 4F);
                } else {
                    ItemNBTUtil.setLikedPlayerName(stack, null);
                    ItemNBTUtil.setLinkedPlayerUUID(stack, null);
                    level.playSound(null, player.blockPosition(), OccultismSounds.POOF.get(), SoundSource.PLAYERS, 1F, 1F);
                }
            } else {
                String name = player.getName().getString();
                PlayerEnderChestContainer enderChestContainer = player.getEnderChestInventory();
                if (stack.has(OccultismDataComponents.LINKED_PLAYER_UUID)) {
                    UUID playerLinkedUUID = ItemNBTUtil.getLinkedPlayerUUID(stack);
                    name = ItemNBTUtil.getLikedPlayerName(stack);
                    if (playerLinkedUUID != null) {
                        for (ServerLevel eachLvl : Objects.requireNonNull(level.getServer()).getAllLevels()) {
                            if (eachLvl.getEntity(playerLinkedUUID) instanceof Player playerLinked) {
                                enderChestContainer = playerLinked.getEnderChestInventory();
                                break;
                            }
                        }
                    }
                }
                PlayerEnderChestContainer finalEnderChestContainer = enderChestContainer;
                player.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) ->
                        ChestMenu.threeRows(p_53124_, p_53125_, finalEnderChestContainer),
                        Component.translatable(OccultismItems.ENDER_SATCHEL.get().getDescriptionId() + ".chest_menu", name)));
                player.awardStat(Stats.OPEN_ENDERCHEST);
                level.playSound(null, player.blockPosition(), SoundEvents.ENDER_CHEST_OPEN, SoundSource.PLAYERS, 1F, 1F);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull TooltipDisplay pTooltipDisplay, @NotNull Consumer<Component> pTooltipAdder, @NotNull TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pTooltipFlag);

        pTooltipAdder.accept(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack))));
        if (ItemNBTUtil.getLikedPlayerName(pStack) != null) {
            pTooltipAdder.accept(Component.translatable(this.getDescriptionId() + ".tooltip_linked",
                    TextUtil.formatPlayerName(ItemNBTUtil.getLikedPlayerName(pStack))));
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return (ItemNBTUtil.getLikedPlayerName(stack) != null);
    }

}
