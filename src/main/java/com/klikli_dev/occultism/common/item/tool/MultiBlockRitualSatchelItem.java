package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.common.container.satchel.*;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSendPreviewedPentacle;
import com.klikli_dev.occultism.registry.OccultismContainers;
import com.klikli_dev.occultism.registry.OccultismSounds;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.UUID;

/**
 * Places a all blocks of a multiblock ritual with just one click.
 */
public class MultiBlockRitualSatchelItem extends Item {
    private final Map<UUID, PentacleData> targetPentacles = Object2ObjectMaps.synchronize(new Object2ObjectArrayMap<>());

    public MultiBlockRitualSatchelItem(Properties properties) {
        super(properties);
    }

    public Map<UUID, PentacleData> targetPentacles() {
        return this.targetPentacles;
    }

    public void setTargetPentacle(UUID player, ResourceLocation multiblock, BlockPos anchor, Rotation facing, BlockPos target, long timeWhenAdded) {
        this.targetPentacles.put(player, new PentacleData(multiblock, anchor, facing, target, timeWhenAdded));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(hand != InteractionHand.MAIN_HAND)
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));

        if(!player.isShiftKeyDown())
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));

        final ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            //here we use main hand item as selected slot
            int selectedSlot = player.getInventory().selected;

            serverPlayer.openMenu(
                    new SimpleMenuProvider((id, playerInventory, unused) -> {
                        return new RitualSatchelT2Container(id, playerInventory,
                                this.getInventory((ServerPlayer) player, stack), selectedSlot);
                    }, stack.getDisplayName()), buffer -> {
                        buffer.writeVarInt(selectedSlot);
                    });
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getHand() != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        boolean isReplacing = level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context));

        if(level.isClientSide() && player.isShiftKeyDown()){
            //will open satchel serverside
            return InteractionResult.SUCCESS;
        }

        if(!level.isClientSide() && player.isShiftKeyDown() && player instanceof ServerPlayer serverPlayer){
            //here we use main hand item as selected slot
            int selectedSlot = player.getInventory().selected;

            serverPlayer.openMenu(
                    new SimpleMenuProvider((id, playerInventory, unused) -> {
                        return new RitualSatchelT2Container(id, playerInventory,
                                this.getInventory((ServerPlayer) player, context.getItemInHand()), selectedSlot);
                    }, context.getItemInHand().getDisplayName()), buffer -> {
                        buffer.writeVarInt(selectedSlot);
                    });

            return InteractionResult.SUCCESS;
        }

        //TODO: handle existing chalk symbol -> should be treated like a valid preview block
        //      will also have to verify the correct chalk is available!

        if (level.isClientSide) {
            var preview = ModonomiconAPI.get().getCurrentPreviewMultiblock();
            if (preview == null || !preview.isAnchored()) {
                player.sendSystemMessage(Component.translatable("AutoChalk only works if there is a pentacle previewed and fixed (with right-click) in world!").withStyle(ChatFormatting.RED));
                return InteractionResult.PASS;
            }

            var simulation = preview.multiblock().simulate(level, preview.anchor(), preview.facing(), false, false);

            var targetMatcher = simulation.getSecond().stream().filter(p -> p.getWorldPosition().equals(pos)).findFirst();
            if (targetMatcher.isEmpty()) {
                player.sendSystemMessage(Component.translatable("AutoChalk only works when aiming at a previewed block.").withStyle(ChatFormatting.RED));
                return InteractionResult.PASS;
            }

            //TODO: use of display state is precarious, we should probably instead match the matcher against available blocks in the bag/autochalk
            var blockHolder = targetMatcher.get().getStateMatcher().getDisplayedState(0).getBlockHolder();
            if (!(blockHolder.value() instanceof ChalkGlyphBlock)) {
                player.sendSystemMessage(Component.translatable("The aimed-at preview block must be a chalk glyph.").withStyle(ChatFormatting.RED));
                return InteractionResult.PASS;
            }

            Networking.sendToServer(new MessageSendPreviewedPentacle(
                    preview.multiblock().getId(),
                    preview.anchor(),
                    preview.facing(),
                    pos
            ));
        }

        if (!level.isClientSide) {

            var targetPentacle = this.targetPentacles.get(player.getUUID());
            if (targetPentacle == null || targetPentacle.timeWhenAdded < level.getGameTime() - 20) {
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

        }
        return InteractionResult.SUCCESS;
    }

    public Container getInventory(ServerPlayer player, ItemStack stack) {
        return new SatchelInventory(stack, RitualSatchelContainer.SATCHEL_SIZE);
    }

    public record PentacleData(ResourceLocation multiblock, BlockPos anchor, Rotation facing, BlockPos target,
                               long timeWhenAdded) {
    }
}
