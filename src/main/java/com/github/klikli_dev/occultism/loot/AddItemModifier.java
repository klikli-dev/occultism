/*
 * MIT License
 *
 * Copyright 2022 klikli-dev
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

package com.github.klikli_dev.occultism.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class AddItemModifier extends LootModifier {
    private final Item addedItem;
    private final int count;

    protected AddItemModifier(LootItemCondition[] conditionsIn, Item addedItemIn, int count) {
        super(conditionsIn);
        this.addedItem = addedItemIn;
        this.count = count;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(this.addedItem, count));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddItemModifier> {
        @Override
        public AddItemModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
            int count = GsonHelper.getAsInt(object, "count", 1);
            return new AddItemModifier(ailootcondition, addedItem, count);
        }

        @Override
        public JsonObject write(AddItemModifier instance) {
            var json = new JsonObject();
            json.addProperty("item", instance.addedItem.getRegistryName().toString());
            json.addProperty("count", instance.count);
            return json;
        }
    }
}
