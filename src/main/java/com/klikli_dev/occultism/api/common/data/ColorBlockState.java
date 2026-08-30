//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.klikli_dev.occultism.api.common.data;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.function.IntFunction;

public enum ColorBlockState implements StringRepresentable {
    WHITE,
    LIGHT_GRAY,
    GRAY,
    BLACK,
    BROWN,
    RED,
    ORANGE,
    YELLOW,
    LIME,
    GREEN,
    CYAN,
    LIGHT_BLUE,
    BLUE,
    PURPLE,
    MAGENTA,
    PINK,
    RAINBOW,
    VOID;

    public static final IntFunction<ColorBlockState> BY_ID = ByIdMap.continuous(Enum::ordinal, ColorBlockState.values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, ColorBlockState> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);
    private static final Map<String, ColorBlockState> TYPES = new Object2ObjectArrayMap<>();
    public static final Codec<ColorBlockState> CODEC = Codec.stringResolver(ColorBlockState::getSerializedName, TYPES::get);

    static {
        for (ColorBlockState type : values()) {
            TYPES.put(type.getSerializedName(), type);
        }
    }

    @Override
    public @NonNull String getSerializedName() {
        return this.name().toLowerCase();
    }

    public byte getNumber() {
        return (byte) this.ordinal();
    }

    public ColorBlockState next() {
        return values()[(this.ordinal() + 1) % ColorBlockState.values().length];
    }

    public ColorBlockState nextColored() {
        return this.ordinal() == lastColor().ordinal() ? firstColor() : values()[this.ordinal() + 1];
    }

    public ColorBlockState nextColorless() {
        return this.ordinal() == lastColorless().ordinal() ? firstColorless() : values()[this.ordinal() + 1];
    }

    public static ColorBlockState fromDye(ItemStack dyeStack) {
        String dye = "";
        for (TagKey<Item> tag : dyeStack.tags().toList()) {
            if (tag.location().toString().contains("c:dyes/")) {
                dye = tag.location().toString().replace("c:dyes/", "");
                break;
            }
        }
        return dye.isEmpty() ? fallback() : valueOf(dye.toUpperCase());
    }

    public static ColorBlockState fromChalk(Item chalk) {
        int i = chalk.toString().lastIndexOf(":") + 1;
        String color = chalk.toString().substring(i).replace("chalk_", "");
        return valueOf(color.toUpperCase());
    }

    private static ColorBlockState fallback() {
        return WHITE;
    }

    public static ColorBlockState firstColor() {
        return BROWN;
    }

    public static ColorBlockState lastColor() {
        return PINK;
    }

    public static ColorBlockState firstColorless() {
        return WHITE;
    }

    public static ColorBlockState lastColorless() {
        return BLACK;
    }

    public static boolean isColored(ColorBlockState cbs) {
        return cbs.ordinal() >= firstColor().ordinal() && cbs.ordinal() <= lastColor().ordinal();
    }

    public static boolean isColorless(ColorBlockState cbs) {
        return cbs.ordinal() >= firstColorless().ordinal() && cbs.ordinal() <= lastColorless().ordinal();
    }

    public static ColorBlockState getRandomColor(RandomSource randomSource) {
        return values()[randomSource.nextIntBetweenInclusive(
                firstColor().ordinal(), lastColor().ordinal())];
    }

    public static ColorBlockState getRandomColorless(RandomSource randomSource) {
        return values()[randomSource.nextIntBetweenInclusive(
                firstColorless().ordinal(), lastColorless().ordinal())];
    }
}

