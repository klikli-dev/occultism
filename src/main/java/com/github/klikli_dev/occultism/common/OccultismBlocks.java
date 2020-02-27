package com.github.klikli_dev.occultism.common;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.BlockCandle;
import com.github.klikli_dev.occultism.common.item.ItemDebugWand;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismBlocks {

    public static DeferredRegister<Block> BLOCKS = new DeferredRegister(ForgeRegistries.BLOCKS, Occultism.MODID);
    //region Fields
    public static final RegistryObject<Block> PENTACLE = BLOCKS.register("candle_white", () -> new BlockCandle(
            Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.CLOTH).doesNotBlockMovement()
                    .hardnessAndResistance(0.1f, 0)));
    //endregion Fields
}
