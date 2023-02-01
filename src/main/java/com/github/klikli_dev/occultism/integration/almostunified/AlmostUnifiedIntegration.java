package com.github.klikli_dev.occultism.integration.almostunified;

import com.almostreliable.unified.AlmostUnified;
import com.almostreliable.unified.utils.UnifyTag;
import com.github.klikli_dev.occultism.Occultism;
import com.klikli_dev.theurgy.entity.FollowProjectile;
import com.klikli_dev.theurgy.item.DivinationRodItem;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class AlmostUnifiedIntegration {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("almostunified");
    }

    public static ItemStack getPreferredItemForTag(TagKey<Item> tag) {
        if (isLoaded()) {
            return AlmostUnifiedHelper.getPreferredItemForTag(tag);
        }
        return ItemStack.EMPTY;
    }

    public static class AlmostUnifiedHelper {
        public static ItemStack getPreferredItemForTag(TagKey<Item> tag) {
            var asUnifyTag = UnifyTag.item(tag.location());
            var item = AlmostUnified
                    .getRuntime()
                    .getReplacementMap()
                    .getPreferredItemForTag(asUnifyTag, $ -> true);
            if(item == null){
                return new Ingredient.TagValue(tag).getItems().stream().findFirst().get();
            }

            return new ItemStack(ForgeRegistries.ITEMS.getValue(item));
        }
    }
}
