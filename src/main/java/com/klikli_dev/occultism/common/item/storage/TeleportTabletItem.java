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

import com.klikli_dev.occultism.common.container.tablet.TabletInventory;
import com.klikli_dev.occultism.common.container.tablet.TeleportTabletContainer;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetContents;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TeleportUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.Consumer;

public class TeleportTabletItem extends Item {


    public TeleportTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, Player player, @NonNull InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            if (player.isShiftKeyDown()) {
                //here we use main hand item as selected slot
                int selectedSlot = hand == InteractionHand.MAIN_HAND ? player.getInventory().getSelectedSlot() : -1;

                serverPlayer.openMenu(
                        new SimpleMenuProvider((id, playerInventory, unused) -> {
                            return new TeleportTabletContainer(id, playerInventory,
                                    this.getInventory(serverPlayer, stack), selectedSlot);
                        }, stack.getDisplayName()), buffer -> {
                            buffer.writeVarInt(selectedSlot);
                        });
            } else { //Teleport logic
                if (stack.has(DataComponents.CONTAINER)) {
                    ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
                    if (contents  == ItemContainerContents.EMPTY)
                        return InteractionResult.FAIL;

                    ItemStack compass = contents.getStackInSlot(0);
                    TeleportTransition transition = this.getDestination((ServerLevel) level, player, compass);
                    if (transition == null)
                        return InteractionResult.FAIL;

                    if (compass.getOrDefault(DataComponents.CUSTOM_NAME, "") != "BACK") {
                        boolean checking = true;
                        NonNullList<ItemStack> items = NonNullList.create();
                        for (int i = 0 ; i < contents.getSlots() ; i++) {
                            ItemStack back = contents.getStackInSlot(i);
                            if (checking && back.getOrDefault(DataComponents.CUSTOM_NAME, Component.empty()).getString().equals("BACK")) {
                                LodestoneTracker target = new LodestoneTracker(Optional.of(GlobalPos.of(level.dimension(), player.blockPosition())), true);
                                back.set(DataComponents.LODESTONE_TRACKER, target);
                                checking = false;
                            }
                            items.add(back);
                        }
                        if (!checking)
                            Networking.sendToServer(new MessageSetContents(ItemContainerContents.fromItems(items), hand));
                    }

                    player.teleport(transition);
                    level.broadcastEntityEvent(player, (byte) 46);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pTooltipFlag);

        pTooltipAdder.accept(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack))));
    }


    public Container getInventory(ServerPlayer player, ItemStack stack) {
        return new TabletInventory(stack, TeleportTabletContainer.TABLET_SIZE);
    }


    public TeleportTransition getDestination(ServerLevel level, Entity entity, ItemStack compass) {
        TeleportUtil.TeleportDestination destination = TeleportUtil.findDestination(level, entity, compass);
        ResourceKey<Level> resourcekey = destination.level();
        BlockPos targetPos = destination.blockPos();
        Vec3 position = destination.position();

        //Level setting
        ServerLevel serverlevel = resourcekey == null ? null : level.getServer().getLevel(resourcekey);
        if (serverlevel == null)
            return null;
        //Resolve to a safe destination around the target block when needed
        if (position == null) {
            if (targetPos == null)
                targetPos = serverlevel.getRespawnData().pos();
            position = TeleportUtil.findSafeTeleportPosition(entity, serverlevel, targetPos);
        }
        if (position == null)
            return null;

        return new TeleportTransition(
                serverlevel,
                position,
                entity.getDeltaMovement(),
                entity.getYHeadRot(),
                entity.getXRot(),
                TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
        );
    }
}
