package com.github.klikli_dev.occultism.integration.almostunified;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;

import java.util.stream.Stream;

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
