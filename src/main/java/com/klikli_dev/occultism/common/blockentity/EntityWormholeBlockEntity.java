/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.common.blockentity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.block.EntityWormholeBlock;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@EventBusSubscriber(modid = Occultism.MODID)
public class EntityWormholeBlockEntity extends NetworkedBlockEntity implements Clearable {

    public long lastChangeTime;
    public ItemStacksResourceHandler itemStackHandler;
    protected boolean initialized = false;

    public EntityWormholeBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.ENTITY_WORMHOLE.get(), worldPos, state);
        this.itemStackHandler = new ItemStacksResourceHandler(1) {

            @Override
            public int insert(int slot, @NotNull ItemResource resource, int amount, @Nullable TransactionContext tx) {
                return resource.toStack().is(ItemTags.COMPASSES) ? super.insert(slot, resource, amount, tx) : 0;
            }

            @Override
            protected int getCapacity(int slot, ItemResource resource) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot, ItemStack previousContents) {
                if (EntityWormholeBlockEntity.this.level != null && !EntityWormholeBlockEntity.this.level.isClientSide()) {
                    EntityWormholeBlockEntity.this.lastChangeTime = EntityWormholeBlockEntity.this.level
                            .getGameTime();
                    EntityWormholeBlockEntity.this.setChanged();
                    EntityWormholeBlockEntity.this.markNetworkDirty();
                }
            }
        };
    }

    public EntityWormholeBlockEntity(BlockEntityType<?> BlockEntityTypeIn, BlockPos worldPos, BlockState state) {
        super(BlockEntityTypeIn, worldPos, state);
        this.itemStackHandler = new ItemStacksResourceHandler(1);
    }

    @SubscribeEvent
    public static void entityWormholeFishing(RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        FishingHook hook = player.fishing;

        if (!event.isCanceled() && !event.getLevel().isClientSide()
                && stack.getItem() instanceof FishingRodItem
                && hook != null && hook.getHookedIn() == null
                && hook.getDeltaMovement().equals(Vec3.ZERO)) {
            BlockState state = event.getLevel().getBlockState(hook.blockPosition());
            if (state.getBlock() instanceof EntityWormholeBlock wormhole) {
                wormhole.pullEntity((ServerLevel) event.getLevel(), hook.blockPosition(), state);
            }
        }
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        StorageUtil.dropInventoryItems(this);
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    public void loadNetwork(ValueInput input) {
        this.itemStackHandler.deserialize(input.childOrEmpty("inventory"));
        this.lastChangeTime = input.getLongOr("lastChangeTime", 0L);
    }

    @Override
    public void saveNetwork(ValueOutput output) {
        this.itemStackHandler.serialize(output.child("inventory"));
        output.putLong("lastChangeTime", this.lastChangeTime);
    }
}
