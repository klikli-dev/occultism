//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.klikli_dev.occultism.api.common.data;

import net.minecraft.util.StringRepresentable;

public enum ColorBlockState implements StringRepresentable {

    WHITE("white", 0),
    LIGHT_GRAY("light_gray", 1),
    GRAY("gray", 2),
    BLACK("black", 3),
    BROWN("brown", 4),
    RED("red", 5),
    ORANGE("orange", 6),
    YELLOW("yellow", 7),
    LIME("lime", 8),
    GREEN("green", 9),
    CYAN("cyan", 10),
    LIGHT_BLUE("light_blue", 11),
    BLUE("blue", 12),
    PURPLE("purple", 13),
    MAGENTA("magenta", 14),
    PINK("pink", 15),
    RAINBOW("rainbow", 16),
    VOID("void", 17);

    private final String name;
    private final int number;

    ColorBlockState(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }
}

