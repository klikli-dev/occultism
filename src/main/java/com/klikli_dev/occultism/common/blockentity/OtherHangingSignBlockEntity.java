package com.klikli_dev.occultism.common.blockentity;

import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OtherHangingSignBlockEntity extends SignBlockEntity {
    public OtherHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OccultismBlockEntities.OTHERPLANKS_HANGING_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return OccultismBlockEntities.OTHERPLANKS_HANGING_SIGN.get();
    }
}

