package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.multiblock.StateMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.AnyMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.DisplayOnlyMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.Matchers;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelContainer;
import com.klikli_dev.occultism.common.container.satchel.SatchelInventory;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSendPreviewedPentacle;
import com.mojang.datafixers.util.Function4;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public abstract class RitualSatchelItem extends Item {
    private final Map<UUID, PentacleData> targetPentacles = Object2ObjectMaps.synchronize(new Object2ObjectArrayMap<>());

    public RitualSatchelItem(Properties properties) {
        super(properties);
    }

    public Map<UUID, PentacleData> targetPentacles() {
        return this.targetPentacles;
    }

    public void setTargetPentacle(UUID player, ResourceLocation multiblock, BlockPos anchor, Rotation facing, BlockPos target, long timeWhenAdded) {
        this.targetPentacles.put(player, new PentacleData(multiblock, anchor, facing, target, timeWhenAdded));
    }

    protected void openMenu(ServerPlayer player, ItemStack stack) {
        int selectedSlot = player.getInventory().selected;

        player.openMenu(
                new SimpleMenuProvider((id, playerInventory, unused) -> {
                    return this.containerFactory().apply(id, playerInventory, this.getInventory(player, stack), selectedSlot);
                }, stack.getDisplayName()), buffer -> {
                    buffer.writeVarInt(selectedSlot);
                });
    }

    public Container getInventory(ServerPlayer player, ItemStack stack) {
        return new SatchelInventory(stack, RitualSatchelContainer.SATCHEL_SIZE);
    }

    /**
     * This identifies the pentacle preview and sends it to the server, where the child classes can handle it.
     */
    protected InteractionResult useOnClientSide(UseOnContext context){
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        var preview = ModonomiconAPI.get().getCurrentPreviewMultiblock();

        //First, verify if we have a valid preview in the world
        if (preview == null || !preview.isAnchored()) {
            player.displayClientMessage(Component.translatable(TranslationKeys.RITUAL_SATCHEL_NO_PREVIEW_IN_WORLD).withStyle(ChatFormatting.YELLOW), true);
            return InteractionResult.PASS;
        }

        var simulation = preview.multiblock().simulate(level, preview.anchor(), preview.facing(), false, false);

        //Then, check if we are targeting any block of the preview
        var targetMatcher = simulation.getSecond().stream().filter(p -> p.getWorldPosition().equals(pos)).findFirst();
        if (targetMatcher.isEmpty() ||
                targetMatcher.get().getStateMatcher().getType() == AnyMatcher.TYPE ||
                targetMatcher.get().getStateMatcher().getType() == DisplayOnlyMatcher.TYPE) {
            player.sendSystemMessage(Component.translatable(TranslationKeys.RITUAL_SATCHEL_NO_PREVIEW_BLOCK_TARGETED).withStyle(ChatFormatting.YELLOW));
            return InteractionResult.PASS;
        }

        //TODO: filter to occultism (ritual-used) pentacles? otherwise it attempts to build any modonomicon multiblock.
        //      should probably be on server, although abuse potential is low -> building other multiblocks is only a problem because they might be broken due to invalid state handling.

        Networking.sendToServer(new MessageSendPreviewedPentacle(
                preview.multiblock().getId(),
                preview.anchor(),
                preview.facing(),
                pos
        ));

        return InteractionResult.SUCCESS;
    }

    protected abstract Function4<Integer, Inventory, Container, Integer, AbstractContainerMenu> containerFactory();

    protected abstract InteractionResult useOnServerSide(UseOnContext context);

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND)
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));

        if (!player.isShiftKeyDown())
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));

        final ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            this.openMenu(serverPlayer, stack);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getHand() != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        if (context.getLevel().isClientSide() && context.getPlayer().isShiftKeyDown()) {
            //will allow to open satchel serverside
            return InteractionResult.SUCCESS;
        }

        if (context.getPlayer() instanceof ServerPlayer serverPlayer && !context.getLevel().isClientSide() && context.getPlayer().isShiftKeyDown()) {
            this.openMenu(serverPlayer, context.getItemInHand());
            return InteractionResult.SUCCESS;
        }

        if (context.getLevel().isClientSide) {
            return this.useOnClientSide(context);
        } else {
            return this.useOnServerSide(context);
        }
    }

}
