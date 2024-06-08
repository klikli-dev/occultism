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

import com.klikli_dev.occultism.Occultism;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public enum WorkAreaSize implements StringRepresentable {
    SMALL(16, "small"),
    MEDIUM(32, "medium"),
    LARGE(64, "large");

    private static final String TRANSLATION_KEY_BASE = "enum." + Occultism.MODID + ".work_area_size";

    private static final Map<String, WorkAreaSize> TYPES = new Object2ObjectArrayMap<>();
    public static final Codec<WorkAreaSize> CODEC = Codec.stringResolver(WorkAreaSize::getSerializedName, TYPES::get);
    public static final IntFunction<WorkAreaSize> BY_ID = ByIdMap.continuous(Enum::ordinal, WorkAreaSize.values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, WorkAreaSize> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);

    static {
        for (WorkAreaSize type : values()) {
            TYPES.put(type.getSerializedName(), type);
        }
    }


    private final int value;
    private final String translationKey;

    WorkAreaSize(int value, String translationKey) {
        this.value = value;
        this.translationKey = TRANSLATION_KEY_BASE + "." + translationKey;
    }

    //region Static Methods
    public static WorkAreaSize get(int value) {
        return TYPES.get(value);
    }

    //region Getter / Setter
    public int getValue() {
        return this.value;
    }
    //endregion Getter / Setter

    public String getDescriptionId() {
        return this.translationKey;
    }
    //endregion Static Methods

    public WorkAreaSize next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public boolean equals(int value) {
        return this.value == value;
    }

    @Override
    public String getSerializedName() {
        return this.translationKey;
    }
}
