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

package com.klikli_dev.occultism.api.common.data;


import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.Map;
import java.util.function.IntFunction;

public enum SortType implements StringRepresentable {
    AMOUNT,
    NAME,
    MOD;

    private static final Map<String, SortType> TYPES = new Object2ObjectArrayMap<>();
    public static final Codec<SortType> CODEC = Codec.stringResolver(SortType::getSerializedName, TYPES::get);
    public static final IntFunction<SortType> BY_ID = ByIdMap.continuous(Enum::ordinal, SortType.values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, SortType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);

    static {
        for (SortType type : values()) {
            TYPES.put(type.getSerializedName(), type);
        }
    }


    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    public SortType next() {
        return values()[(this.ordinal() + 1) % SortType.values().length];
    }

}
