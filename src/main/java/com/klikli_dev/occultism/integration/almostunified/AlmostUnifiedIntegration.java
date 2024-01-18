/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.almostunified;

import com.klikli_dev.theurgy.integration.almostunified.AlmostUnifiedIntegrationDummy;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public interface AlmostUnifiedIntegration {

    AlmostUnifiedIntegration instance = new AlmostUnifiedIntegrationDummy();

    static AlmostUnifiedIntegration get() {
        return instance;
    }


    boolean isLoaded();

    @Nullable Item getPreferredItemForTag(TagKey<Item> tag);
}
