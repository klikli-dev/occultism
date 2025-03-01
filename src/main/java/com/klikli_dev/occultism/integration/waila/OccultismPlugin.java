package com.klikli_dev.occultism.integration.waila;

import com.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.klikli_dev.occultism.common.block.GoldenSacrificialBowlBlock;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.CuriosUtil;

import snownee.jade.api.*;

@WailaPlugin
public class OccultismPlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if(accessor!=null) {
                boolean hasGoggles = CuriosUtil.hasGoggles(accessor.getPlayer());
                if (accessor instanceof BlockAccessor blockAccessor) {
                    if (blockAccessor.getBlock() instanceof IOtherworldBlock otherworldBlock) {
                        if (otherworldBlock.getTier() == OtherworldBlockTier.ONE || hasGoggles) {
                            if (blockAccessor.getBlockState().getValue(IOtherworldBlock.UNCOVERED)) {
                                return registration.blockAccessor().from(blockAccessor).blockState(otherworldBlock.getUncoveredBlock().defaultBlockState()).build();
                            } else {
                                return registration.blockAccessor().from(blockAccessor).blockState(otherworldBlock.getCoveredBlock().defaultBlockState()).build();
                            }
                        } else {
                            return registration.blockAccessor().from(blockAccessor).blockState(otherworldBlock.getCoveredBlock().defaultBlockState()).build();
                        }
                    }
                }
            }
            return accessor;
        });

        registration.registerBlockComponent(SacrificialComponentProvider.INSTANCE, GoldenSacrificialBowlBlock.class);
        registration.registerEntityComponent(SpiritComponentProvider.INSTANCE, SpiritEntity.class);
    }

}
