package com.klikli_dev.occultism.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.UUID;

public class OccultismExtraCodecs {
    public static final Codec<java.util.UUID> UUID = Codec.STRING.xmap(java.util.UUID::fromString, java.util.UUID::toString);

    public static <T> MapCodec<T> mapWithAlternative(final MapCodec<T> primary, final MapCodec<? extends T> alternative) {
        return Codec.mapEither(
                primary,
                alternative
        ).xmap(
                Either::unwrap,
                Either::left
        );
    }
}
