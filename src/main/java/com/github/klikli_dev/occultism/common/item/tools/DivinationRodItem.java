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

package com.github.klikli_dev.occultism.common.item.tools;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.divination.ScanManager;
import com.github.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.github.klikli_dev.occultism.network.MessageSetDivinationResult;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class DivinationRodItem extends Item {

    //region Fields
    public static final float NOT_FOUND = 7.0f;
    public static final float SEARCHING = 8.0f;
    //endregion Fields

    //region Initialization
    public DivinationRodItem(Properties properties) {
        super(properties);
        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "distance"),
                (stack, world, entity) -> {
                    if (!stack.getOrCreateTag().contains("distance") || stack.getTag().getFloat("distance") < 0)
                        return NOT_FOUND;
                    return stack.getTag().getFloat("distance");
                });
    }
    //endregion Initialization


    //region Overrides
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entityLiving, int count) {
        if (entityLiving.world.isRemote && entityLiving instanceof PlayerEntity) {
            ScanManager.instance.updateScan((PlayerEntity) entityLiving, false);
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getPos();
        ItemStack stack = context.getItem();

        if (player.isShiftKeyDown()) {
            BlockState state = world.getBlockState(pos);
            if (!state.getBlock().isAir(state, world, pos)) {
                Block block = this.getOtherBlock(state);
                if (block != null) {
                    if (!world.isRemote) {
                        String translationKey =
                                block instanceof IOtherworldBlock ? ((IOtherworldBlock) block).getUncoveredBlock()
                                                                            .getTranslationKey() : block.getTranslationKey();
                        stack.getOrCreateTag().putString("linkedBlockId", block.getRegistryName().toString());
                        player.sendMessage(
                                new TranslationTextComponent(this.getTranslationKey() + ".message.linked_block",
                                        new TranslationTextComponent(translationKey + ".name")));
                    }

                    world.playSound(player, player.getPosition(), OccultismSounds.TUNING_FORK.get(),
                            SoundCategory.PLAYERS,
                            1, 1);
                }
                else {
                    if (!world.isRemote) {
                        player.sendMessage(
                                new TranslationTextComponent(this.getTranslationKey() + ".message.no_link_found"));
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!player.isShiftKeyDown()) {
            if (stack.getOrCreateTag().contains("linkedBlockId")) {
                stack.getTag().putFloat("distance", SEARCHING);
                player.setActiveHand(hand);
                world.playSound(player, player.getPosition(), OccultismSounds.TUNING_FORK.get(), SoundCategory.PLAYERS,
                        1, 1);

                if (world.isRemote) {
                    ResourceLocation id = new ResourceLocation(stack.getTag().getString("linkedBlockId"));
                    ScanManager.instance.beginScan(player, ForgeRegistries.BLOCKS.getValue(id));
                }
            }
            else if (!world.isRemote) {
                player.sendMessage(new TranslationTextComponent(this.getTranslationKey() + ".message.no_linked_block"));
            }
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {
        if (!(entityLiving instanceof PlayerEntity))
            return stack;

        PlayerEntity player = (PlayerEntity) entityLiving;
        player.getCooldownTracker().setCooldown(this, 40);
        stack.getOrCreateTag().putFloat("distance", NOT_FOUND);
        if (world.isRemote) {
            BlockPos result = ScanManager.instance.finishScan(player);
            float distance = this.getDistance(player.getPositionVector(), result);
            stack.getTag().putFloat("distance", distance);
            OccultismPackets.sendToServer(new MessageSetDivinationResult(distance));

            if (result != null && player.isCreative()) {
                //Show debug visualization
                Occultism.SELECTED_BLOCK_RENDERER.selectBlock(result, System.currentTimeMillis() + 10000);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return ScanManager.SCAN_DURATION_TICKS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
        //player interrupted, so we can safely set not found on server
        stack.getOrCreateTag().putFloat("distance", NOT_FOUND);

        if (world.isRemote) {
            ScanManager.instance.cancelScan();
        }
        super.onPlayerStoppedUsing(stack, world, entityLiving, timeLeft);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        if (stack.getOrCreateTag().contains("linkedBlockId")) {
            ResourceLocation id = new ResourceLocation(stack.getTag().getString("linkedBlockId"));

            Block block = ForgeRegistries.BLOCKS.getValue(id);
            String translationKey = block instanceof IOtherworldBlock ? ((IOtherworldBlock) block).getUncoveredBlock()
                                                                                .getTranslationKey() : block.getTranslationKey();
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip.linked_block",
                    new TranslationTextComponent(translationKey)
                            .applyTextStyles(TextFormatting.BOLD, TextFormatting.ITALIC)));
        }
        else {
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip.no_linked_block"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    //endregion Overrides

    //region Methods
    public Block getOtherBlock(BlockState state) {
        //otherstone ore is linked to andesite.
        if (state.getBlock() == Blocks.ANDESITE || state.getBlock() == OccultismBlocks.OTHERSTONE_NATURAL.get()) {
            return OccultismBlocks.OTHERSTONE_NATURAL.get();
        }
        return null;
    }

    /**
     * Calculates the distance parameter representing the actual distance.
     *
     * @param playerPosition the player position.
     * @param result         the result position to get the distance to.
     * @return the distance parameter as used in the distance property, not the actual distance.
     */
    public float getDistance(Vec3d playerPosition, BlockPos result) {
        if (result == null)
            return NOT_FOUND;

        Vec3d resultCenter = Math3DUtil.center(result);
        Vec3d playerPosition2d = new Vec3d(playerPosition.x, 0, playerPosition.z);
        Vec3d resultCenter2d = new Vec3d(resultCenter.x, 0, resultCenter.z);
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
    //endregion Methods
}

