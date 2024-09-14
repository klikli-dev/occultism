package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelT1Container;
import com.mojang.datafixers.util.Function4;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.UseOnContext;

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

        if (this.tryPlaceBlockForMatcher(context, targetMatcher.get())) {
            return InteractionResult.SUCCESS;
        }

        context.getPlayer().displayClientMessage(Component.translatable(TranslationKeys.RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL).withStyle(ChatFormatting.YELLOW), true);

        return InteractionResult.FAIL;
    }
}
