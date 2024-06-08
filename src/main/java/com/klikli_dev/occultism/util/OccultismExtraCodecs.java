package com.klikli_dev.occultism.util;

import com.mojang.serialization.Codec;

import java.util.UUID;

public class OccultismExtraCodecs {
    public static final Codec<java.util.UUID> UUID = Codec.STRING.xmap(java.util.UUID::fromString, java.util.UUID::toString);
}
