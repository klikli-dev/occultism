// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RecipeResultType<T extends RecipeResult>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
    public RecipeResultType(MapCodec<T> mapCodec) {
        this(mapCodec, ByteBufCodecs.fromCodecWithRegistries(mapCodec.codec()));
    }
}
