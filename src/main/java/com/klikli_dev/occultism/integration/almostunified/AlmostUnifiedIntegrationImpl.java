// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.almostunified;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

public class AlmostUnifiedIntegrationImpl implements AlmostUnifiedIntegration {
    public boolean isLoaded() {
        return ModList.get().isLoaded("almostunified");
    }

    @Nullable
    public Item getPreferredItemForTag(TagKey<Item> tag) {
        if (this.isLoaded()) {
            return AlmostUnifiedHelper.getPreferredItemForTag(tag);
        }

        return null;
    }

    public static class AlmostUnifiedHelper {
        @Nullable
        public static Item getPreferredItemForTag(TagKey<Item> tag) {
            return AlmostUnifiedLookup.INSTANCE.getPreferredItemForTag(tag);
        }
    }
}
