package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.multiblock.matcher.AnyMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.DisplayOnlyMatcher;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelContainer;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelT2Container;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    protected InteractionResult useOnClientSide(UseOnContext context) {
        //non-preview golden sacrifical bowl means we try to collect the ritual pentacle.
        if (context.getLevel().getBlockState(context.getClickedPos()).is(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                ||context.getLevel().getBlockState(context.getClickedPos()).is(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get())
                ||context.getLevel().getBlockState(context.getClickedPos()).is(OccultismBlocks.ELDRITCH_CHALICE.get()))
            return InteractionResult.SUCCESS;

        return super.useOnClientSide(context);
    }

    protected InteractionResult collectPentacle(UseOnContext context) {
        var pentacles = context.getLevel().getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream()
                //First deduplicate pentacles
                .collect(Collectors.toMap(
                        r -> r.value().getPentacle().getId(), // Use pentacle ID as the key
                        r -> r.value().getPentacle(), // Keep the pentacle as value, this is what we actually want
                        (existing, replacement) -> existing // In case of key collision, keep the existing value
                )).values().stream()
                //find the pentacles that are valid for the given golden bowl and store their rotation
                .map(pentacle -> Pair.of(pentacle, pentacle.validate(context.getLevel(), context.getClickedPos())))
                .filter(p -> p.getSecond() != null)
                .toList();

        var inventory = new ComponentItemHandler(
                context.getItemInHand(),
                DataComponents.CONTAINER,
                RitualSatchelContainer.SATCHEL_SIZE
        );

        for(var pentacle : pentacles){
            var simulation = pentacle.getFirst().simulate(context.getLevel(), context.getClickedPos(), pentacle.getSecond(), false, false);

            for (var targetMatcher : simulation.getSecond()) {

                if(targetMatcher.getStateMatcher().getType().equals(AnyMatcher.TYPE) || targetMatcher.getStateMatcher().getType().equals(DisplayOnlyMatcher.TYPE))
                    continue;

                //if we got here it means the block at the location of the matcher is a valid block for the pentacle.
                //however that may also be an "any" or "air" matcher.
                var blockState = context.getLevel().getBlockState(targetMatcher.getWorldPosition());

                if(blockState.isAir())
                    continue;

                if(!blockState.is(OccultismTags.Blocks.PENTACLE_MATERIALS))
                    continue;

                var blockEntity = context.getLevel().getBlockEntity(targetMatcher.getWorldPosition());

                var drops = Block.getDrops(blockState, (ServerLevel) context.getLevel(), targetMatcher.getWorldPosition(), blockEntity, null, ItemStack.EMPTY);

                //we might want to use blockdropsevent here (CommonHooks.handleBlockDrops), but it handles item entities, not items ..

                context.getLevel().setBlock(targetMatcher.getWorldPosition(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

                for(var drop : drops){
                    //try to put into satchel, if full -> player inv

                    var remainder = ItemHandlerHelper.insertItemStacked(inventory, drop, false);
                    if(!remainder.isEmpty())
                        ItemHandlerHelper.giveItemToPlayer(context.getPlayer(), remainder);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useOnServerSide(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).is(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                ||context.getLevel().getBlockState(context.getClickedPos()).is(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get())
                ||context.getLevel().getBlockState(context.getClickedPos()).is(OccultismBlocks.ELDRITCH_CHALICE.get()))
            return this.collectPentacle(context);

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
        for (var targetMatcher : simulation.getSecond()) {
            var localContext = new UseOnContext(context.getPlayer(), context.getHand(),
                    new BlockHitResult(targetMatcher.getWorldPosition().getCenter(), context.getClickedFace(), targetMatcher.getWorldPosition().above(), false));
            if (this.tryPlaceBlockForMatcher(localContext, targetMatcher)) {
                placedAnything = true;
            }
        }

        if (!placedAnything) {
            context.getPlayer().displayClientMessage(Component.translatable(TranslationKeys.RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL).withStyle(ChatFormatting.YELLOW), true);
            return InteractionResult.FAIL;

        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack))));
    }
}
