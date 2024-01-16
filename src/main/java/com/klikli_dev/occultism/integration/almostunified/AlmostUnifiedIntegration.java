package com.klikli_dev.occultism.integration.almostunified;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;

public class AlmostUnifiedIntegration {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("almostunified");
    }

    public static Item getPreferredItemForTag(TagKey<Item> tag) {
        if (isLoaded()) {
            return AlmostUnifiedHelper.getPreferredItemForTag(tag);
        }

        return null;
    }

    public static class AlmostUnifiedHelper {
        public static Item getPreferredItemForTag(TagKey<Item> tag) {
            return AlmostUnifiedLookup.INSTANCE.getPreferredItemForTag(tag);
        }
    }
}
