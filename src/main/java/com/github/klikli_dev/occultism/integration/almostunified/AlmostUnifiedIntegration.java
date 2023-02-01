package com.github.klikli_dev.occultism.integration.almostunified;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class AlmostUnifiedIntegration {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("almostunified");
    }

    public static ItemStack getPreferredItemForTag(TagKey<Item> tag) {
        //TODO: enable once almostunified is updated
//        if (isLoaded()) {
//            return AlmostUnifiedHelper.getPreferredItemForTag(tag);
//        }
        return ItemStack.EMPTY;
    }

    public static class AlmostUnifiedHelper {
        public static ItemStack getPreferredItemForTag(TagKey<Item> tag) {
//            var asUnifyTag = UnifyTag.item(tag.location());
//            var item = AlmostUnified
//                    .getRuntime()
//                    .getReplacementMap()
//                    .getPreferredItemForTag(asUnifyTag, $ -> true);
//            if(item == null){
//                return new Ingredient.TagValue(tag).getItems().stream().findFirst().get();
//            }
//
//            return new ItemStack(ForgeRegistries.ITEMS.getValue(item));
            throw new UnsupportedOperationException();
        }
    }
}
