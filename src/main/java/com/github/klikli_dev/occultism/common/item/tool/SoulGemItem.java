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

package com.github.klikli_dev.occultism.common.item.tool;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SoulGemItem extends Item {
    //region Initialization
    public SoulGemItem(Properties properties) {
        super(properties);
        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "has_entity"),
                (stack, world, entity) -> stack.getOrCreateTag().contains("entityData") ? 1.0f : 0.0f);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getItem();
        World world = context.getWorld();
        Direction facing = context.getFace();
        BlockPos pos = context.getPos();
        if (itemStack.getOrCreateTag().contains("entityData")) {
            //whenever we have an entity stored we can do nothing but release it
            if (!world.isRemote) {
                CompoundNBT entityData = itemStack.getTag().getCompound("entityData");
                LivingEntity entity = (LivingEntity) EntityUtil.entityFromNBT(world, entityData);

                facing = facing == null ? Direction.UP : facing;

                entity.setLocationAndAngles(pos.getX() + facing.getXOffset(), pos.getY() + facing.getYOffset(),
                        pos.getZ() + facing.getZOffset(), world.rand.nextInt(360), 0);
                world.addEntity(entity);
                itemStack.getTag().remove("entityData"); //delete entity from item
                player.swingArm(context.getHand());
                player.container.detectAndSendChanges();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        //This is called from PlayerEventHandler#onPlayerRightClickEntity, because we need to bypass sitting entities processInteraction
        if (target.world.isRemote)
            return false;

        //Do not allow bosses or players.
        if(!target.isNonBoss() || target instanceof PlayerEntity)
            return false;

        //Already got an entity in there.
        if(stack.getOrCreateTag().contains("entityData"))
            return false;

        //serialize entity
        stack.getTag().put("entityData", target.serializeNBT());
        //show player swing anim
        player.swingArm(hand);
        player.setHeldItem(hand, stack); //need to write the item back to hand, otherwise we only modify a copy
        target.remove(true);
        player.container.detectAndSendChanges();
        return true;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return stack.getOrCreateTag().contains("entityData") ? this.getTranslationKey() :
                       this.getTranslationKey() + "_empty";
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(stack.getOrCreateTag().contains("entityData")){
            EntityType<?> type = EntityUtil.entityTypeFromNbt(stack.getTag().getCompound("entityData"));
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip_filled", type.getName()));
        }
        else{
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip_empty"));
        }
    }

    //endregion Overrides
}
