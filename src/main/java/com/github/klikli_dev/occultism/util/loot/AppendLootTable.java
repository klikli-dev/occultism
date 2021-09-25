/*
 *
 * Copyright (c) 2016, David Quintana
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of project nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.klikli_dev.occultism.util.loot;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class AppendLootTable extends LootModifier {
    private final ResourceLocation lootTable;

    public AppendLootTable(ILootCondition[] lootConditions, ResourceLocation lootTable)
    {
        super(lootConditions);
        this.lootTable = lootTable;
    }

    boolean reentryPrevention = false;

    @Nonnull
    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
    {
        if (this.reentryPrevention)
            return generatedLoot;

        this.reentryPrevention = true;
        LootTable lootTable = context.getLootTable(this.lootTable);
        List<ItemStack> extras = lootTable.getRandomItems(context);
        generatedLoot.addAll(extras);
        this.reentryPrevention = false;

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AppendLootTable>
    {
        @Override
        public AppendLootTable read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition)
        {
            ResourceLocation lootTable = new ResourceLocation(JSONUtils.getAsString(object, "add_loot"));
            return new AppendLootTable(ailootcondition, lootTable);
        }

        @Override
        public JsonObject write(AppendLootTable instance)
        {
            JsonObject object = new JsonObject();
            object.addProperty("add_loot", instance.lootTable.toString());
            return object;
        }
    }
}
