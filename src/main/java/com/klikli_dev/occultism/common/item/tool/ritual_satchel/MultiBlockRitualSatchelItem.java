package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
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
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        boolean isReplacing = level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context));


        var targetPentacle = this.targetPentacles().get(player.getUUID());
        if (targetPentacle == null || targetPentacle.timeWhenAdded() < level.getGameTime() - 20) {
            player.sendSystemMessage(Component.translatable("AutoChalk only works if there is a pentacle previewed and fixed (with right-click) in world, and it is aimed at a previewed chalk block").withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }

        if (!targetPentacle.target().equals(pos)) {
            //silent fail -> the player clicked at two blocks in quick succession, the second block did not send anything to the server as it is not a chalk block, so we ignore. Client side already sent an error message for that second click!
            return InteractionResult.FAIL;
        }

        var multiblock = ModonomiconAPI.get().getMultiblock(targetPentacle.multiblock());
        var simulation = multiblock.simulate(level, targetPentacle.anchor(), targetPentacle.facing(), false, false);

        boolean placedAnyChalk = false;
        for (var matcher : simulation.getSecond()) {
            //TODO: use of display state is precarious, we should probably instead match the matcher against available blocks in the bag/autochalk
            var blockHolder = matcher.getStateMatcher().getDisplayedState(0).getBlockHolder();
            if (!(blockHolder.value() instanceof ChalkGlyphBlock chalkGlyphBlock)) {
                continue;
            }

            //only place if player clicked at a top face
            //only if the block can be placed or is replacing an existing block
            if ((context.getClickedFace() == Direction.UP
                    && chalkGlyphBlock.canSurvive(level.getBlockState(matcher.getWorldPosition().above()), level, matcher.getWorldPosition().above())) || isReplacing) {
                BlockPos placeAt = isReplacing ? matcher.getWorldPosition() : matcher.getWorldPosition().above();

                level.setBlockAndUpdate(placeAt, chalkGlyphBlock.getStateForPlacement(new BlockPlaceContext(context)));

                placedAnyChalk = true;
            }
        }

        if (placedAnyChalk)
            level.playSound(null, pos, OccultismSounds.CHALK.get(), SoundSource.PLAYERS, 0.5f,
                    1 + 0.5f * player.getRandom().nextFloat());

        return InteractionResult.SUCCESS;
    }
}
