package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSendPreviewedChalk;
import com.klikli_dev.occultism.registry.OccultismSounds;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.UUID;

public class AutoChalkItem extends Item {
    private final Map<UUID, ChalkData> targetChalks = Object2ObjectMaps.synchronize(new Object2ObjectArrayMap<>());

    public AutoChalkItem(Properties properties) {
        super(properties);
    }

    public Map<UUID, ChalkData> targetChalks() {
        return this.targetChalks;
    }

    public void setTargetChalk(UUID player, Holder<Block> block, BlockPos target, long timeWhenAdded) {
        this.targetChalks.put(player, new ChalkData(block, target, timeWhenAdded));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        boolean isReplacing = level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context));

        //TODO: handle existing chalk symbol -> should be treated like a valid preview block
        //      will also have to verify the correct chalk is available!

        if (level.isClientSide) {
            var preview = ModonomiconAPI.get().getCurrentPreviewMultiblock();
            if(preview == null || !preview.isAnchored()){
                player.sendSystemMessage(Component.translatable("AutoChalk only works if there is a pentacle previewed and fixed (with right-click) in world!").withStyle(ChatFormatting.RED));
                return InteractionResult.PASS;
            }

            var simulation = preview.multiblock().simulate(level, preview.anchor(), preview.facing(), false, false);

            var targetMatcher = simulation.getSecond().stream().filter(p -> p.getWorldPosition().equals(pos)).findFirst();
            if(targetMatcher.isEmpty()){
                player.sendSystemMessage(Component.translatable("AutoChalk only works when aiming at a previewed block.").withStyle(ChatFormatting.RED));
                return InteractionResult.PASS;
            }

            var blockHolder = targetMatcher.get().getStateMatcher().getDisplayedState(0).getBlockHolder();
            if(!(blockHolder.value() instanceof ChalkGlyphBlock)){
                player.sendSystemMessage(Component.translatable("The aimed-at preview block must be a chalk glyph.").withStyle(ChatFormatting.RED));
                return InteractionResult.PASS;
            }

            Networking.sendToServer(new MessageSendPreviewedChalk(targetMatcher.get().getStateMatcher().getDisplayedState(0).getBlockHolder(), pos));
        }

        if (!level.isClientSide) {

            var targetChalk = this.targetChalks.get(player.getUUID());
            if (targetChalk == null || targetChalk.timeWhenAdded < level.getGameTime() - 20) {
                player.sendSystemMessage(Component.translatable("AutoChalk only works if there is a pentacle previewed and fixed (with right-click) in world, and it is aimed at a previewed chalk block").withStyle(ChatFormatting.RED));
                return InteractionResult.FAIL;
            }

            if(!targetChalk.target().equals(pos)){
                //silent fail -> the player clicked at two blocks in quick succession, the second block did not send anything to the server as it is not a chalk block, so we ignore. Client side already sent an error message for that second click!
                return InteractionResult.FAIL;
            }

            if(!(targetChalk.block().value() instanceof ChalkGlyphBlock chalkGlyphBlock)){
                player.sendSystemMessage(Component.translatable("The aimed-at preview block must be a chalk glyph.").withStyle(ChatFormatting.RED));
                return InteractionResult.FAIL;
            }

            //only place if player clicked at a top face
            //only if the block can be placed or is replacing an existing block
            if ((context.getClickedFace() == Direction.UP
                    && chalkGlyphBlock.canSurvive(level.getBlockState(pos.above()), level, pos.above())) || isReplacing) {
                BlockPos placeAt = isReplacing ? pos : pos.above();

                level.setBlockAndUpdate(placeAt, chalkGlyphBlock.getStateForPlacement(new BlockPlaceContext(context)));

                level.playSound(null, pos, OccultismSounds.CHALK.get(), SoundSource.PLAYERS, 0.5f,
                        1 + 0.5f * player.getRandom().nextFloat());
            }
        }
        return InteractionResult.SUCCESS;
    }

    public record ChalkData(Holder<Block> block, BlockPos target, long timeWhenAdded) {
    }
}
