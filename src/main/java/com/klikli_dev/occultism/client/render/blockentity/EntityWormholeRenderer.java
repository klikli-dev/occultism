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

package com.klikli_dev.occultism.client.render.blockentity;

import com.klikli_dev.occultism.common.block.EntityWormholeBlock;
import com.klikli_dev.occultism.common.blockentity.EntityWormholeBlockEntity;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class EntityWormholeRenderer implements BlockEntityRenderer<EntityWormholeBlockEntity> {

    public EntityWormholeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EntityWormholeBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        var handler = blockEntity.itemStackHandler;
        ItemStack stack = handler.getStackInSlot(0) == ItemStack.EMPTY ? ItemStack.EMPTY : OccultismItems.WORMHOLE_PORTAL.toStack();
        ItemStack stackNugget = OccultismItems.IESNIUM_NUGGET.toStack();
        BlockState state = blockEntity.getBlockState();

        var facing = state.hasProperty(BlockStateProperties.FACING) ?
                state.getValue(BlockStateProperties.FACING) : Direction.UP;
        var exitY = state.hasProperty(EntityWormholeBlock.EXIT_ROTATION_Y) ?
                state.getValue(EntityWormholeBlock.EXIT_ROTATION_Y) : 0;
        var exitX = state.hasProperty(EntityWormholeBlock.EXIT_ROTATION_X) ?
                state.getValue(EntityWormholeBlock.EXIT_ROTATION_X) : 0;
        var angleY = state.getBlock() instanceof EntityWormholeBlock wormholeBlock ? wormholeBlock.getExitRotY(state) : 0;

        poseStack.pushPose();

        poseStack.pushPose();

        // Adjust the translation based on the facing direction
        double xOffset = facing.getAxis() == Direction.Axis.X ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.03 : 0.97) : 0.5;
        double yOffset = facing.getAxis() == Direction.Axis.Y ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.03 : 0.97) : 0.5;
        double zOffset = facing.getAxis() == Direction.Axis.Z ? (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.03 : 0.97) : 0.5;

        poseStack.translate(xOffset, yOffset, zOffset);

        //rotate item to fit in frame
        float angle = facing.getAxis() == Direction.Axis.X ? 90 : 0;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        float angle2 = facing.getAxis() == Direction.Axis.Y ? 90 : 0;
        poseStack.mulPose(Axis.XP.rotationDegrees(angle2));

        //Fixed scale
        poseStack.scale(0.1F, 0.1F, 0.1F);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        //Exit yawl indicator
        BakedModel modelNugget = itemRenderer.getModel(stackNugget, blockEntity.getLevel(), null, 0);
        if (exitY != 0){
            Vec3 vec3;
            if (facing.getAxis() == Direction.Axis.Y) {
                vec3 = Vec3.directionFromRotation(0, (angleY+180) * 0.002F);
                poseStack.mulPose(Axis.ZP.rotationDegrees((angleY+180)));
            } else {
                vec3 = Vec3.directionFromRotation(0, angleY * 0.002F);
                poseStack.mulPose(Axis.ZN.rotationDegrees(angleY));
            }
            float nuggetOffset = facing.getAxis() == Direction.Axis.Y ?
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? -0.3F: 0.3F :
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.3F: -0.3F ;
            poseStack.translate(-4*vec3.x, -4*vec3.z, nuggetOffset);
            itemRenderer.render(stackNugget, ItemDisplayContext.FIXED, true, poseStack, buffer,
                    combinedLight, combinedOverlay, modelNugget);
            poseStack.translate(4*vec3.x, 4*vec3.z, -nuggetOffset);
        }

        //Exit pitch indicator
        if (exitX != 0) {
            ItemStack stackBlock = switch (exitX) {
                case 2 -> new ItemStack(Items.IRON_BLOCK);
                case 3 -> new ItemStack(Items.REDSTONE_BLOCK);
                case 4 -> new ItemStack(Items.DIAMOND_BLOCK);
                case 5 -> new ItemStack(Items.GOLD_BLOCK);
                default -> new ItemStack(Items.EMERALD_BLOCK);
            };
            poseStack.scale(0.25F, 0.25F, 0.25F);
            float blockOffset = facing.getAxis() == Direction.Axis.Y ?
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? -0.7F: 0.7F :
                    facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.7F: -0.7F ;
            poseStack.translate(0, 0, blockOffset);
            BakedModel modelBlock = itemRenderer.getModel(stackBlock, blockEntity.getLevel(), null, 0);
            itemRenderer.render(stackBlock, ItemDisplayContext.FIXED, true, poseStack, buffer,
                    combinedLight, combinedOverlay, modelBlock);
            poseStack.mulPose(Axis.ZP.rotationDegrees(45));
            itemRenderer.render(stackBlock, ItemDisplayContext.FIXED, true, poseStack, buffer,
                    combinedLight, combinedOverlay, modelBlock);
            poseStack.translate(0, 0, -blockOffset);
            poseStack.scale(4F, 4F, 4F);

        }


        //Rotating Portal
        long systemTime = System.currentTimeMillis(); //use system time to become independent of game time
        float angle3 = (systemTime / 16 ) % 360;
        poseStack.mulPose(Axis.ZP.rotationDegrees(angle3));
        poseStack.scale(7F, 7F, 7F);
        BakedModel model = itemRenderer.getModel(stack, blockEntity.getLevel(), null, 0);
        itemRenderer.render(stack, ItemDisplayContext.FIXED, true, poseStack, buffer,
                combinedLight, combinedOverlay, model);


        poseStack.popPose();

        poseStack.mulPose(facing.getRotation());

        poseStack.popPose();
    }
}
