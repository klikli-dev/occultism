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

package com.github.klikli_dev.occultism.common.item;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.block.IOtherOre;
import com.github.klikli_dev.occultism.client.divination.ScanManager;
import com.github.klikli_dev.occultism.network.MessageSetDivinationResult;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.registry.SoundRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockStone;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDivinationRod extends Item {

    //region Fields
    public static final float NOT_FOUND = 7.0f;
    public static final float SEARCHING = 8.0f;
    //endregion Fields

    //region Initialization
    public ItemDivinationRod() {
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "distance"), new DistancePropertyGetter());
        ItemRegistry.registerItem(this, "divination_rod");
    }
    //endregion Initialization


    //region Overrides
    @Override
    public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing,
                                      float hitX, float hitY, float hitZ) {
        //if (!world.isRemote)
        {
            ItemStack stack = player.getHeldItem(hand);

            if (player.isSneaking()) {
                BlockState state = world.getBlockState(pos);
                if (!state.getBlock().isAir(state, world, pos)) {
                    Block block = this.getOtherBlock(state);
                    if (block != null) {
                        String translationKey = block instanceof IOtherOre ? ((IOtherOre) block).getUncoveredBlock()
                                                                                     .getTranslationKey() : block.getTranslationKey();
                        ItemNBTUtil.setString(stack, "linkedBlockId", block.getRegistryName().toString());
                        if (!world.isRemote) {
                            player.sendMessage(
                                    new TranslationTextComponent(this.getTranslationKey() + ".message.linked_block",
                                            new TranslationTextComponent(translationKey + ".name")));
                        }

                        world.playSound(player, player.getPosition(), SoundRegistry.TUNING_FORK, SoundCategory.PLAYERS,
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
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!player.isSneaking()) {
            if (ItemNBTUtil.getTagCompound(stack).hasKey("linkedBlockId")) {
                ItemNBTUtil.setFloat(stack, "distance", SEARCHING);
                player.setActiveHand(hand);
                world.playSound(player, player.getPosition(), SoundRegistry.TUNING_FORK, SoundCategory.PLAYERS, 1, 1);

                if (world.isRemote) {
                    ResourceLocation id = new ResourceLocation(ItemNBTUtil.getString(stack, "linkedBlockId"));
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
        ItemNBTUtil.setFloat(stack, "distance", NOT_FOUND);
        if (world.isRemote) {
            BlockPos result = ScanManager.instance.finishScan(player);
            float distance = this.getDistance(player.getPositionVector(), result);
            ItemNBTUtil.setFloat(stack, "distance", distance);
            Occultism.network.sendToServer(new MessageSetDivinationResult(distance));
            //Show debug visualization
            //            if (result != null) {
            //                Occultism.proxy.getClientData().selectBlock(result, System.currentTimeMillis() + 10000);
            //            }
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(final ItemStack stack) {
        return ScanManager.SCAN_DURATION_TICKS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
        //player interrupted, so we can safely set not found on server
        ItemNBTUtil.setFloat(stack, "distance", NOT_FOUND);

        if (world.isRemote) {
            ScanManager.instance.cancelScan();
        }
        super.onPlayerStoppedUsing(stack, world, entityLiving, timeLeft);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (ItemNBTUtil.getTagCompound(stack).hasKey("linkedBlockId")) {
            ResourceLocation id = new ResourceLocation(ItemNBTUtil.getString(stack, "linkedBlockId"));

            Block block = ForgeRegistries.BLOCKS.getValue(id);
            String translationKey = block instanceof IOtherOre ? ((IOtherOre) block).getUncoveredBlock()
                                                                         .getTranslationKey() : block.getTranslationKey();
            tooltip.add(I18n.format(this.getTranslationKey() + ".tooltip.linked_block",
                    TextFormatting.BOLD.toString() + TextFormatting.ITALIC.toString() +
                    I18n.format(translationKey + ".name") + TextFormatting.RESET.toString()));
        }
        else {
            tooltip.add(I18n.format(this.getTranslationKey() + ".tooltip.no_linked_block"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entityLiving, int count) {
        if (entityLiving.world.isRemote && entityLiving instanceof PlayerEntity) {
            ScanManager.instance.updateScan((PlayerEntity) entityLiving, false);
        }
    }
    //endregion Overrides

    //region Methods
    public Block getOtherBlock(BlockState state) {
        //otherstone ore is linked to andesite.
        if (state.getBlock() == Blocks.STONE && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.ANDESITE ||
            state.getBlock() == BlockRegistry.OTHERSTONE_ORE) {
            return BlockRegistry.OTHERSTONE_ORE;
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

        Vec3d resultCenter = Math3DUtil.getBlockCenter(result);
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

    private static class DistancePropertyGetter implements IItemPropertyGetter {
        //region Fields
        //endregion Fields

        //region Overrides
        @Override
        public float apply(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
            CompoundNBT compound = ItemNBTUtil.getTagCompound(stack);
            if (!compound.hasKey("distance") || compound.getFloat("distance") < 0)
                return NOT_FOUND;
            return compound.getFloat("distance");
        }
        //endregion Overrides
    }
}

