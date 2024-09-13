package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelContainer;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelT2Container;
import com.klikli_dev.occultism.common.container.satchel.SatchelInventory;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSendPreviewedPentacle;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.mojang.datafixers.util.Function4;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

/**
 * Places all blocks of a multiblock ritual with just one click.
 */
public class MultiBlockRitualSatchelItem extends RitualSatchelItem {

    public MultiBlockRitualSatchelItem(Properties properties) {
        super(properties);
    }

    @Override
    protected Function4<Integer, Inventory, Container, Integer, AbstractContainerMenu> containerFactory() {
        return RitualSatchelT2Container::new;
    }

    @Override
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

        boolean placedAnything = false;
        for(var targetMatcher : simulation.getSecond()){
            var localContext = new UseOnContext(context.getPlayer(), context.getHand(),
                    new BlockHitResult(targetMatcher.getWorldPosition().getCenter(), context.getClickedFace(), targetMatcher.getWorldPosition(), false));
            if (this.tryPlaceBlockForMatcher(localContext, targetMatcher)) {
                placedAnything = true;
            }
        }

        if(!placedAnything){
            context.getPlayer().displayClientMessage(Component.translatable(TranslationKeys.RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL).withStyle(ChatFormatting.YELLOW), true);
            return InteractionResult.FAIL;

        }

        return InteractionResult.SUCCESS;
    }
}
