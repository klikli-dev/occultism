package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismTags {
    //region Fields

    // Block Tags
    public static final ITag<Block> CAVE_WALL_BLOCKS = makeBlockTag(new ResourceLocation(Occultism.MODID,"cave_wall_blocks"));

    //Item Tags

    //Entity Tags
    public static final ITag<EntityType<?>> AFRIT_ALLIES = makeEntityTypeTag(new ResourceLocation(Occultism.MODID,"afrit_allies"));
    public static final ITag<EntityType<?>> WILD_HUNT = makeEntityTypeTag(new ResourceLocation(Occultism.MODID,"wild_hunt"));

    public static final ITag<EntityType<?>> CHICKEN = makeEntityTypeTag(new ResourceLocation("forge","chicken"));
    public static final ITag<EntityType<?>> PARROTS = makeEntityTypeTag(new ResourceLocation("forge","parrots"));
    public static final ITag<EntityType<?>> PIGS = makeEntityTypeTag(new ResourceLocation("forge","pigs"));
    public static final ITag<EntityType<?>> COWS = makeEntityTypeTag(new ResourceLocation("forge","cows"));
    public static final ITag<EntityType<?>> VILLAGERS = makeEntityTypeTag(new ResourceLocation("forge","villagers"));

    //endregion Fields

    //region Static Methods
    public static ITag.INamedTag<Item> makeItemTag(String id) {
        return makeItemTag(new ResourceLocation(id));
    }

    public static ITag.INamedTag<Item> makeItemTag(ResourceLocation id) {
        return ItemTags.makeWrapperTag(id.toString());
    }

    public static ITag.INamedTag<Block> makeBlockTag(String id) {
        return makeBlockTag(new ResourceLocation(id));
    }

    public static ITag.INamedTag<Block> makeBlockTag(ResourceLocation id) {
        return BlockTags.makeWrapperTag(id.toString());
    }

    public static ITag.INamedTag<EntityType<?>> makeEntityTypeTag(String id) {
        return makeEntityTypeTag(new ResourceLocation(id));
    }

    public static ITag.INamedTag<EntityType<?>> makeEntityTypeTag(ResourceLocation id) {
        return EntityTypeTags.getTagById(id.toString());
    }
    //endregion Static Methods
}
