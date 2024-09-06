package com.klikli_dev.occultism.common.block.custom;

import com.klikli_dev.occultism.common.blockentity.OtherHangingSignBlockEntity;
import com.klikli_dev.occultism.common.blockentity.OtherSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class OtherWallHangingSignBlock extends WallHangingSignBlock {
    public OtherWallHangingSignBlock(Properties pProperties, WoodType pType) {
        super(pType, pProperties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OtherHangingSignBlockEntity(pPos, pState);
    }
}
