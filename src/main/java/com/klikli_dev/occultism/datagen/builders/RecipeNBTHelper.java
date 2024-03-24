package com.klikli_dev.occultism.datagen.builders;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.ItemStack;

public class RecipeNBTHelper {
    public static JsonObject serializeItemStack(ItemStack stack) {
        var res=ItemStack.CODEC.encodeStart(JsonOps.INSTANCE,stack);
        if(res.result().isPresent()) {
            JsonObject result = res.result().get().getAsJsonObject();
            if (result.has("Count"))
                result.remove("Count");
            if (stack.getCount() > 1)
                result.addProperty("count", stack.getCount());
            if (result.has("tag")) {
                result.add("nbt", result.get("tag"));
                result.remove("tag");
            }
            result.add("item",result.get("id"));
            result.remove("id");
            return result;
        }
        else throw new IllegalStateException("Could not serialize itemstack");
    }
}
