package com.klikli_dev.occultism.common.item.tool.ritual_satchel;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;

public record PentacleData(ResourceLocation multiblock, BlockPos anchor, Rotation facing, BlockPos target,
                           long timeWhenAdded) {
}
