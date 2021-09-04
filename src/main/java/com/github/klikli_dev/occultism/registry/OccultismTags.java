/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class OccultismTags {
    //region Fields

    // Block Tags
    public static final Tag<Block> CAVE_WALL_BLOCKS = makeBlockTag(new ResourceLocation(Occultism.MODID,"cave_wall_blocks"));
    public static final Tag<Block> NETHERRACK = makeBlockTag(new ResourceLocation(Occultism.MODID,"netherrack"));
    public static final Tag.Named<Block> CANDLES = makeBlockTag(new ResourceLocation("forge","candles"));

    //Item Tags
    public static final Tag<Item> ELYTRA = makeItemTag(new ResourceLocation(Occultism.MODID,"elytra"));
    public static final Tag<Item> FRUITS = makeItemTag(new ResourceLocation("forge","fruits"));

    //Entity Tags
    public static final Tag<EntityType<?>> AFRIT_ALLIES = makeEntityTypeTag(new ResourceLocation(Occultism.MODID,"afrit_allies"));
    public static final Tag<EntityType<?>> WILD_HUNT = makeEntityTypeTag(new ResourceLocation(Occultism.MODID,"wild_hunt"));

    public static final Tag<EntityType<?>> CHICKEN = makeEntityTypeTag(new ResourceLocation("forge","chicken"));
    public static final Tag<EntityType<?>> PARROTS = makeEntityTypeTag(new ResourceLocation("forge","parrots"));
    public static final Tag<EntityType<?>> PIGS = makeEntityTypeTag(new ResourceLocation("forge","pigs"));
    public static final Tag<EntityType<?>> COWS = makeEntityTypeTag(new ResourceLocation("forge","cows"));
    public static final Tag<EntityType<?>> VILLAGERS = makeEntityTypeTag(new ResourceLocation("forge","villagers"));
    public static final Tag<EntityType<?>> ZOMBIES = makeEntityTypeTag(new ResourceLocation("forge","zombies"));
    public static final Tag<EntityType<?>> BATS = makeEntityTypeTag(new ResourceLocation("forge","bats"));

    //endregion Fields

    //region Static Methods
    public static Tag.Named<Item> makeItemTag(String id) {
        return makeItemTag(new ResourceLocation(id));
    }

    public static Tag.Named<Item> makeItemTag(ResourceLocation id) {
        return ItemTags.bind(id.toString());
    }

    public static Tag.Named<Block> makeBlockTag(String id) {
        return makeBlockTag(new ResourceLocation(id));
    }

    public static Tag.Named<Block> makeBlockTag(ResourceLocation id) {
        return BlockTags.bind(id.toString());
    }

    public static Tag<EntityType<?>> makeEntityTypeTag(String id) {
        return makeEntityTypeTag(new ResourceLocation(id));
    }

    public static Tag<EntityType<?>> makeEntityTypeTag(ResourceLocation id) {
        return EntityTypeTags.getAllTags().getTag(id);
    }
    //endregion Static Methods
}
