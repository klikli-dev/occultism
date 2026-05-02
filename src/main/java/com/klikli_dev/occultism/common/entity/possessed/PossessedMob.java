package com.klikli_dev.occultism.common.entity.possessed;

import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public interface PossessedMob {

    @Nullable
    default EntityType basedMob() {
        return null;
    }
}
