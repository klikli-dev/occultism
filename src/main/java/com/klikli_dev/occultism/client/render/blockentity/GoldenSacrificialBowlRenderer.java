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

import com.klikli_dev.occultism.client.render.blockentity.state.GoldenSacrificialBowlRenderState;
import com.klikli_dev.occultism.common.block.SpiritAttunedCrystalBlock;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.klikli_dev.occultism.util.EntityUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class GoldenSacrificialBowlRenderer implements BlockEntityRenderer<SacrificialBowlBlockEntity, GoldenSacrificialBowlRenderState> {

    private final ItemModelResolver itemModelResolver;
    private final Map<EntityType<?>, LivingEntity> sacrificePreviewEntities = new HashMap<>();

    public GoldenSacrificialBowlRenderer(Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    public static float getScale(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem itemBlock) {
            if (itemBlock.getBlock() instanceof SpiritAttunedCrystalBlock)
                return 3.0f;
        }
        return 1.0f;
    }

    @Override
    public GoldenSacrificialBowlRenderState createRenderState() {
        return new GoldenSacrificialBowlRenderState();
    }

    @Override
    public void extractRenderState(SacrificialBowlBlockEntity blockEntity, GoldenSacrificialBowlRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumbling);

        ItemStack stack = blockEntity.itemStackHandler.getResource(0).toStack();
        Direction facing = blockEntity.getBlockState().hasProperty(BlockStateProperties.FACING) ?
                blockEntity.getBlockState().getValue(BlockStateProperties.FACING) : Direction.UP;
        long gameTime = blockEntity.getLevel() != null ? blockEntity.getLevel().getGameTime() : 0L;
        long lastChangeTime = blockEntity.lastChangeTime;

        renderState.itemStack = stack;
        renderState.facing = facing;
        renderState.gameTime = gameTime;
        renderState.lastChangeTime = lastChangeTime;

        // Update the pre-initialized ItemStackRenderState with the current item
        if (!stack.isEmpty()) {
            int seed = (int) (blockEntity.getBlockPos().asLong() & 0xFFFFFFFFL);
            this.itemModelResolver.updateForTopItem(renderState.itemStackRenderState, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed);
        }

        // GoldenSacrificialBowl-specific data
        if (blockEntity instanceof GoldenSacrificialBowlBlockEntity goldenBowl) {
            renderState.isGoldenBowl = true;

            var recipe = goldenBowl.getCurrentRitualRecipe();
            if (recipe != null) {
                renderState.itemUseFulfilled = goldenBowl.itemUseFulfilled();
                renderState.sacrificeFulfilled = goldenBowl.sacrificeFulfilled();
                renderState.recipeId = recipe.id().toString();
                renderState.requiresItemUse = recipe.value().requiresItemUse();
                renderState.requiresSacrifice = recipe.value().requiresSacrifice();
                renderState.sacrificeEntityType = null;
                renderState.sacrificeEntityRenderState = null;
                renderState.sacrificeEntityScale = 1.0F;

                // Get item to use for cycling animation
                if (renderState.requiresItemUse) {
                    // Cache invalidation: only rebuild if recipe changed
                    boolean recipeChanged = !Objects.equals(renderState.recipeId, renderState.cachedRecipeId);

                    if (recipeChanged) {
                        var items = recipe.value().getItemToUse().items().toList();
                        if (!items.isEmpty()) {
                            renderState.itemToUseStacks = items.stream()
                                    .map(holder -> new ItemStack(holder.value()))
                                    .toArray(ItemStack[]::new);
                            renderState.cachedRecipeId = renderState.recipeId;
                        }
                    }

                    // Compute cycling index in extract (called every frame)
                    if (renderState.itemToUseStacks != null && renderState.itemToUseStacks.length > 0) {
                        renderState.itemToUseIndex = renderState.itemToUseStacks.length == 1 ? 0 : (int) ((System.currentTimeMillis() / 2880) % renderState.itemToUseStacks.length);

                        // Pre-update render state for current index
                        if (!renderState.itemToUseStacks[renderState.itemToUseIndex].isEmpty()) {
                            this.itemModelResolver.updateForTopItem(renderState.itemToUseRenderState, renderState.itemToUseStacks[renderState.itemToUseIndex], ItemDisplayContext.FIXED, blockEntity.getLevel(), null, renderState.itemToUseIndex + 1);
                        }
                    }
                }

                if (renderState.requiresSacrifice && !renderState.sacrificeFulfilled) {
                    var entityType = EntityUtil.getEntityInTag(blockEntity.getLevel(), recipe.value().getEntityToSacrifice());
                    renderState.sacrificeEntityType = entityType;
                    LivingEntity entity = this.getSacrificePreviewEntity(entityType, blockEntity.getLevel());
                    if (entity != null) {
                        entity.setYRot(0);
                        entity.yRotO = 0;
                        entity.setYBodyRot(0);
                        entity.yBodyRotO = 0;
                        entity.setYHeadRot(0);
                        entity.yHeadRotO = 0;

                        BlockPos previewPos = blockEntity.getBlockPos().relative(facing, 2).above(3);
                        entity.setPos(previewPos.getX() + 0.5, previewPos.getY(), previewPos.getZ() + 0.5);

                        float maxSize = (float) Math.max(entity.getBbWidth(), Math.max(entity.getBbHeight(), entity.getBbWidth()));
                        renderState.sacrificeEntityScale = maxSize > 0 ? 0.5F / maxSize : 1.0F;
                        renderState.sacrificeEntityRenderState = Minecraft.getInstance().getEntityRenderDispatcher().extractEntity(entity, partialTick);
                        renderState.sacrificeEntityRenderState.lightCoords = LevelRenderer.getLightCoords(blockEntity.getLevel(), previewPos);
                    }
                }
            } else {
                renderState.itemUseFulfilled = true;
                renderState.sacrificeFulfilled = true;
                renderState.requiresItemUse = false;
                renderState.requiresSacrifice = false;
                renderState.sacrificeEntityType = null;
                renderState.sacrificeEntityRenderState = null;
                renderState.sacrificeEntityScale = 1.0F;
            }
        } else {
            renderState.isGoldenBowl = false;
        }
    }

    @Nullable
    private LivingEntity getSacrificePreviewEntity(@Nullable EntityType<?> entityType, @Nullable Level level) {
        if (entityType == null || level == null) {
            return null;
        }

        LivingEntity cached = this.sacrificePreviewEntities.get(entityType);
        if (cached != null && cached.level() == level && !cached.isRemoved()) {
            return cached;
        }

        if (entityType.create(level, EntitySpawnReason.COMMAND) instanceof LivingEntity created) {
            this.sacrificePreviewEntities.put(entityType, created);
            return created;
        }

        return null;
    }

    @Override
    public void submit(GoldenSacrificialBowlRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitCollector, CameraRenderState cameraRenderState) {
        ItemStack stack = renderState.itemStack;
        ItemStackRenderState stackRenderState = renderState.itemStackRenderState;
        Direction facing = renderState.facing;
        long gameTime = renderState.gameTime;
        long lastChangeTime = renderState.lastChangeTime;

        if (stack == null || stack.isEmpty() || facing == null || gameTime == 0) {
            return;
        }

        poseStack.pushPose();
        poseStack.pushPose();

        // Calculate bobbing offset
        double offset = Math.sin((gameTime - lastChangeTime) / 16.0) * 0.5 + 0.5;
        offset = offset / 4.0;

        double fixedOffset = 0.2;

        double xOffset = facing.getAxis() == Direction.Axis.X ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double yOffset = facing.getAxis() == Direction.Axis.Y ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;
        double zOffset = facing.getAxis() == Direction.Axis.Z ? (facing.getAxisDirection() == AxisDirection.POSITIVE ? offset + fixedOffset : -offset - fixedOffset) : 0.0;

        poseStack.translate(0.5 + xOffset, 0.5 + yOffset, 0.5 + zOffset);

        // Rotate item around Y axis using system time
        long systemTime = System.currentTimeMillis();
        float angle = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        // Scale
        float scale = getScale(stack) * 0.5f;
        poseStack.scale(scale, scale, scale);

        // Render the main item using the pre-created ItemStackRenderState
        stackRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        // Render item-to-use indicator for GoldenSacrificialBowl
        if (renderState.isGoldenBowl && !stack.isEmpty() && renderState.itemToUseStacks != null && renderState.itemToUseStacks.length > 0) {
            poseStack.pushPose();
            poseStack.translate(0, 3.2 - (0.5 + yOffset) / scale, 0);
            if (!renderState.itemUseFulfilled && renderState.requiresItemUse) {
                int index = renderState.itemToUseIndex;
                ItemStack itemStack = renderState.itemToUseStacks[index];
                if (!itemStack.isEmpty()) {
                    float scaleUse = getScale(itemStack) * 0.5F;
                    poseStack.scale(scaleUse, scaleUse, scaleUse);
                    renderState.itemToUseRenderState.submit(poseStack, submitCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                }
            }
            poseStack.popPose();
        }

        if (renderState.isGoldenBowl && renderState.requiresSacrifice && !renderState.sacrificeFulfilled && renderState.sacrificeEntityRenderState != null) {
            poseStack.pushPose();
            poseStack.translate(0, 3.2 - (0.5 + yOffset) / scale, 0);
            poseStack.scale(renderState.sacrificeEntityScale, renderState.sacrificeEntityScale, renderState.sacrificeEntityScale);
            EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            dispatcher.submit(renderState.sacrificeEntityRenderState, cameraRenderState, 0, 0, 0, poseStack, submitCollector);
            poseStack.popPose();
        }

        poseStack.popPose();

        poseStack.mulPose(facing.getRotation());

        poseStack.popPose();
    }
}
