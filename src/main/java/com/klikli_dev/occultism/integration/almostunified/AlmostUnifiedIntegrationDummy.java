// SPDX-FileCopyrightText: 2024 klikli_dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.almostunified;

import com.klikli_dev.theurgy.integration.almostunified.AlmostUnifiedIntegration;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class AlmostUnifiedIntegrationDummy implements AlmostUnifiedIntegration {
    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public @Nullable Item getPreferredItemForTag(TagKey<Item> tag) {
        return null;
    }
}
