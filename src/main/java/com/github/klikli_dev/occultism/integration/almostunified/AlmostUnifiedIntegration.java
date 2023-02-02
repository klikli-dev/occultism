package com.github.klikli_dev.occultism.integration.almostunified;

import com.almostreliable.unified.AlmostUnified;
import com.almostreliable.unified.utils.UnifyTag;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;

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
            var asUnifyTag = UnifyTag.item(tag.location());
            var rl = AlmostUnified
                    .getRuntime()
                    .getReplacementMap()
                    .getPreferredItemForTag(asUnifyTag, $ -> true);
            return Registry.ITEM.getOptional(rl).orElse(null);
        }
    }
}
