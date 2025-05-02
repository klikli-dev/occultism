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

package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.divination.ScanManager;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.klikli_dev.occultism.integration.theurgy.TheurgyIntegration;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSetDivinationResult;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DivinationRodItem extends Item {

    public static final float NOT_FOUND = 7.0f;
    public static final float SEARCHING = 8.0f;
    public static Object[] blocktags = null;

    public DivinationRodItem(Properties properties) {
        super(properties);
    }


    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity.level().isClientSide && pLivingEntity instanceof Player player) {
            ScanManager.instance.updateScan(player, false);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        if (player.isShiftKeyDown()) {
            BlockState state = level.getBlockState(pos);
            if (!state.isAir()) {
                Block block = this.getOtherBlock(state, player.isCreative(), stack);
                if (block != null) {
                    if (!level.isClientSide) {
                        String translationKey =
                                block instanceof IOtherworldBlock ? ((IOtherworldBlock) block).getUncoveredBlock()
                                        .getDescriptionId() : block.getDescriptionId();
                        stack.set(OccultismDataComponents.DIVINATION_LINKED_BLOCK, block.builtInRegistryHolder());
                        player.sendSystemMessage(
                                Component.translatable(this.getDescriptionId() + ".message.linked_block",
                                        Component.translatable(translationKey)));
                    }

                    level.playSound(player, player.blockPosition(), OccultismSounds.TUNING_FORK.get(),
                            SoundSource.PLAYERS,
                            1, 1);
                } else {
                    if (!level.isClientSide) {
                        player.sendSystemMessage(
                                Component.translatable(this.getDescriptionId() + ".message.no_link_found"));
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.isShiftKeyDown()) {
            if (stack.has(OccultismDataComponents.DIVINATION_LINKED_BLOCK)) {
                stack.set(OccultismDataComponents.DIVINATION_DISTANCE, SEARCHING);
                player.startUsingItem(hand);
                level.playSound(player, player.blockPosition(), OccultismSounds.TUNING_FORK.get(), SoundSource.PLAYERS,
                        1, 1);

                if (level.isClientSide) {
                    ScanManager.instance.beginScan(player, stack.get(OccultismDataComponents.DIVINATION_LINKED_BLOCK).value());
                }
            } else if (!level.isClientSide) {
                player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".message.no_linked_block"));
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        if (!(entityLiving instanceof Player player))
            return stack;

        player.getCooldowns().addCooldown(this, 40);
        stack.set(OccultismDataComponents.DIVINATION_DISTANCE, NOT_FOUND);
        if (level.isClientSide) {
            BlockPos result = ScanManager.instance.finishScan(player);
            float distance = this.getDistance(player.position(), result);
            stack.set(OccultismDataComponents.DIVINATION_DISTANCE, distance);

            Networking.sendToServer(new MessageSetDivinationResult(result, distance));

            if (result != null) {
                stack.set(OccultismDataComponents.DIVINATION_POS, result);

                if (TheurgyIntegration.isLoaded()) {
                    //show nice particle if possible
                    TheurgyIntegration.spawnDivinationResultParticle(result, level, entityLiving);
                } else {
                    //otherwise fall back to our old renderer
                    Occultism.SELECTED_BLOCK_RENDERER.selectBlock(result, System.currentTimeMillis() + 10000);
                }
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity p_344979_) {
        return ScanManager.SCAN_DURATION_TICKS;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!stack.has(OccultismDataComponents.DIVINATION_POS))
            //player interrupted, so we can safely set not found on server, if we don't have a previous result
            stack.set(OccultismDataComponents.DIVINATION_DISTANCE, NOT_FOUND);
        else {
            //otherwise, restore distance from result
            //nice bonus: will update crystal status on every "display only" use.

            BlockPos result = stack.get(OccultismDataComponents.DIVINATION_POS);
            float distance = this.getDistance(pLivingEntity.position(), result);
            stack.set(OccultismDataComponents.DIVINATION_DISTANCE, distance);
        }


        if (level.isClientSide) {
            ScanManager.instance.cancelScan();

            //re-use old result
            if (stack.has(OccultismDataComponents.DIVINATION_POS)) {
                BlockPos result = stack.get(OccultismDataComponents.DIVINATION_POS);
                if (TheurgyIntegration.isLoaded()) {
                    //show nice particle if possible
                    TheurgyIntegration.spawnDivinationResultParticle(result, level, pLivingEntity);
                } else {
                    //otherwise fall back to our old renderer
                    Occultism.SELECTED_BLOCK_RENDERER.selectBlock(result, System.currentTimeMillis() + 10000);
                }
            }
        }

        super.releaseUsing(stack, level, pLivingEntity, pTimeCharged);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (pStack.has(OccultismDataComponents.DIVINATION_LINKED_BLOCK)) {
            Block block = pStack.get(OccultismDataComponents.DIVINATION_LINKED_BLOCK).value();
            String translationKey = block instanceof IOtherworldBlock ? ((IOtherworldBlock) block).getUncoveredBlock()
                    .getDescriptionId() : block.getDescriptionId();
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip.linked_block",
                    Component.translatable(translationKey)
                            .withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC)));
        } else {
            pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip.no_linked_block"));
        }
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    public Block getOtherBlock(BlockState state, boolean isCreative, ItemStack stack) {
        //otherstone ore is linked to andesite.
        if (state.getBlock() == Blocks.ANDESITE || state.getBlock() == OccultismBlocks.OTHERSTONE_NATURAL.get()
                || state.getBlock() == OccultismBlocks.OTHERSTONE.get()) {
            return OccultismBlocks.OTHERSTONE_NATURAL.get();
        }
        //Otherworld logs are linked to oak logs.
        if (state.getBlock() == Blocks.OAK_LOG || state.getBlock() == OccultismBlocks.OTHERWORLD_LOG_NATURAL.get()
                || state.getBlock() == OccultismBlocks.OTHERWORLD_LOG.get()) {
            return OccultismBlocks.OTHERWORLD_LOG_NATURAL.get();
        }
        //Stripped otherworld logs are linked to stripped oak logs.
        if (state.getBlock() == Blocks.STRIPPED_OAK_LOG || state.getBlock() == OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get()
                || state.getBlock() == OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get()) {
            return OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get();
        }
        //Otherworld leaves are linked to oak leaves.
        if (state.getBlock() == Blocks.OAK_LEAVES || state.getBlock() == OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get()
                || state.getBlock() == OccultismBlocks.OTHERWORLD_LEAVES.get()) {
            return OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get();
        }
        //iesnium ore is linked to netherrack.
        if (state.getBlock() == Blocks.NETHERRACK || state.getBlock() == OccultismBlocks.IESNIUM_ORE_NATURAL.get()
                || state.getBlock() == OccultismBlocks.IESNIUM_ORE.get()) {
            return OccultismBlocks.IESNIUM_ORE_NATURAL.get();
        }
        //otherflower ore is linked to any flower.
        if (Arrays.toString(state.getTags().toArray()).contains("flower")) {
            return OccultismBlocks.OTHERFLOWER_NATURAL.get();
        }
        //Otherglass are linked to end stone
        if (state.getBlock() == Blocks.END_STONE || state.getBlock() == OccultismBlocks.OTHERGLASS_NATURAL.get()
                || state.getBlock() == OccultismBlocks.OTHERGLASS.get()) {
            return OccultismBlocks.OTHERGLASS_NATURAL.get();
        }
        //check server config to link to any block that have c:ores
        if (Occultism.SERVER_CONFIG.itemSettings.anyOreDivinationRod.getAsBoolean()) {
            blocktags = state.getTags().toArray();
            for (Object tag : blocktags) {
                if (tag.equals(Tags.Blocks.ORES)) {
                    return state.getBlock();
                }
            }
        }
        //In creative or true sight staff allow to find the clicked block
        return isCreative || stack.is(OccultismItems.TRUE_SIGHT_STAFF)  ? state.getBlock() : null;
    }

    /**
     * Calculates the distance parameter representing the actual distance.
     *
     * @param playerPosition the player position.
     * @param result         the result position to get the distance to.
     * @return the distance parameter as used in the distance property, not the actual distance.
     */
    public float getDistance(Vec3 playerPosition, BlockPos result) {
        if (result == null)
            return NOT_FOUND;

        Vec3 resultCenter = Math3DUtil.center(result);
        Vec3 playerPosition2d = new Vec3(playerPosition.x, 0, playerPosition.z);
        Vec3 resultCenter2d = new Vec3(resultCenter.x, 0, resultCenter.z);
        double distance = playerPosition2d.distanceTo(resultCenter2d);

        if (distance < 6.0)
            return 0.0f;
        if (distance < 15.0)
            return 1.0f;
        if (distance < 25.0)
            return 2.0f;
        if (distance < 35.0)
            return 3.0f;
        if (distance < 45)
            return 4.0f;
        if (distance < 65)
            return 5.0f;
        return 6.0f;
    }
}

