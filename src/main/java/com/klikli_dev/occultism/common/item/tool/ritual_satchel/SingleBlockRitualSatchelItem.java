package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelT1Container;
import com.klikli_dev.occultism.common.item.tool.ChalkItem;
import com.mojang.datafixers.util.Function4;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ComponentItemHandler;

/**
 * Places a single block of a ritual multiblock.
 */
public class SingleBlockRitualSatchelItem extends RitualSatchelItem {

    public SingleBlockRitualSatchelItem(Properties properties) {
        super(properties);
    }

    @Override
    protected Function4<Integer, Inventory, Container, Integer, AbstractContainerMenu> containerFactory() {
        return RitualSatchelT1Container::new;
    }

    protected InteractionResult useOnServerSide(UseOnContext context) {
        var targetPentacle = this.targetPentacles().get(context.getPlayer().getUUID());
        if (targetPentacle == null || targetPentacle.timeWhenAdded() < context.getLevel().getGameTime() - 5) {
            //no or outdated info
            return InteractionResult.FAIL;
        }

        if (!targetPentacle.target().equals(context.getClickedPos())) {
            //silent fail -> the player clicked at two blocks in quick succession, the second block did not send anything to the server as it is not a chalk block, so we ignore. Client side already sent an error message for that second click!
            return InteractionResult.FAIL;
        }

        var multiblock = ModonomiconAPI.get().getMultiblock(targetPentacle.multiblock());
        var simulation = multiblock.simulate(context.getLevel(), targetPentacle.anchor(), targetPentacle.facing(), false, false);

        var targetMatcher = simulation.getSecond().stream().filter(p -> p.getWorldPosition().equals(context.getClickedPos())).findFirst();
        if (targetMatcher.isEmpty()) {
            //should not happen, player clicked a non-preview block
            return InteractionResult.FAIL;
        }

        var statePredicate = targetMatcher.get().getStateMatcher().getStatePredicate();

        var inventory = new ComponentItemHandler(
                context.getItemInHand(),
                DataComponents.CONTAINER,
                context.getItemInHand().getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).getSlots()
        );

        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);

            BlockState blockStateToPlace = null;
            if (stack.getItem() instanceof BlockItem blockItem) {
                var block = blockItem.getBlock();
                var blockPlaceContext = blockItem.updatePlacementContext(new BlockPlaceContext(context));
                if (blockPlaceContext == null)
                    continue;

                blockStateToPlace = block.getStateForPlacement(blockPlaceContext);
            } else if (stack.getItem() instanceof ChalkItem chalkItem) {
                var chalkBlock = chalkItem.getGlyphBlock().get();
                blockStateToPlace = chalkBlock.getStateForPlacement(new BlockPlaceContext(context));
            }

            if (blockStateToPlace == null)
                continue;

            if (statePredicate.test(context.getLevel(), targetMatcher.get().getWorldPosition(), blockStateToPlace)) {
                //simulate item use
                stack.useOn(new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), stack, context.getHitResult()));
                inventory.setStackInSlot(i, stack); //to force an update of the container if the item was used up or stack size reduced.
                return InteractionResult.SUCCESS;
            }
        }

        context.getPlayer().displayClientMessage(Component.translatable(TranslationKeys.RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL).withStyle(ChatFormatting.YELLOW), true);

        return InteractionResult.FAIL;
    }
}
