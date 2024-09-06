package com.klikli_dev.occultism.common.blockentity;

import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OtherSignBlockEntity extends SignBlockEntity {
    public OtherSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OccultismBlockEntities.OTHERPLANKS_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return OccultismBlockEntities.OTHERPLANKS_SIGN.get();
    }
}
