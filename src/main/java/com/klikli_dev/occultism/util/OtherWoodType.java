package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
public class OtherWoodType {
    public static final WoodType OTHERPLANKS = WoodType.register(new WoodType(Occultism.MODID + ":otherplanks", BlockSetType.OAK));
}